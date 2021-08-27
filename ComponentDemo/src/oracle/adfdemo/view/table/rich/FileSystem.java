/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import java.beans.IntrospectionException;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.Serializable;

import java.lang.reflect.Method;

import java.text.SimpleDateFormat;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ELResolver;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.ServletContext;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.ColumnSelectionEvent;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adfdemo.view.components.rich.DemoComponentSkin;

import org.apache.myfaces.trinidad.model.RowKeyPropertyModel;
import org.apache.myfaces.trinidad.model.RowKeyPropertyTreeModel;

import org.apache.myfaces.trinidad.component.UIXShowDetail;
import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.component.UIXTree;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.FocusEvent;
import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.event.RowKeySetChangeEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeyPropertyModel;
import org.apache.myfaces.trinidad.model.RowKeyPropertyTreeModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetTreeImpl;
import org.apache.myfaces.trinidad.model.SortCriterion;
import org.apache.myfaces.trinidad.model.TreeModel;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.ExternalContextUtils;
import org.apache.myfaces.trinidad.util.Service;


/**
 * This class encapsulates the models and methods necessary to provide a
 * functional file system browser to a Faces application. It provides a tree
 * model for navigating through a directory folder hierarchy, and a List to
 * represent the files in any given directory.
 */
public class FileSystem implements Serializable
{
  public FileSystem()
  {
  }

  public void handleColumnSelection(ColumnSelectionEvent event)
  {
    Collection<String> added = event.getAddedColumns();  
    Collection<String> removed = event.getRemovedColumns();  
    
    _LOG.info("added: " + added);
    _LOG.info("removed: " + removed);
    
    if (event.getSource() instanceof RichTable)
      _LOG.info("selected: "+ ((RichTable)event.getSource()).getSelectedColumns());
    else
      _LOG.info("selected: "+ ((RichTreeTable)event.getSource()).getSelectedColumns());
      
  }
  
  public void handleTreeContextMenu(ActionEvent event)
  {
    UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
    UIXTree tree = _folderTree;
    this.logAction(tree);
  }

  public void handleUpFolder(ActionEvent event)
  {
    UIXTree tree = _folderTree;
    Object selectedPath = _getFirstSelectedTreePath();
    if (selectedPath != null)
    {
      TreeModel model = getTreeModel();
      Object oldRowKey = model.getRowKey();
      try
      {
        model.setRowKey(selectedPath);
        Object parentRowKey = model.getContainerRowKey();
        if (parentRowKey != null)
        {
          _folderTree.getSelectedRowKeys().clear();
          _folderTree.getSelectedRowKeys().add(parentRowKey);

          /* only needed if we're using server-side repainting
             of the tree selection
          RequestContext adfContext = RequestContext.getCurrentInstance();
          // TODO: a full repaint is painful.  We ought to have
          // optimized paths for simply setting a client-side
          // selection.  See bug 5734958.
          adfContext.addPartialTarget(_folderTree);
          */

          // This is an example of how to force a single attribute
          // to repaint.  There's a couple of major problems with
          // this code:
          //  (1) It's *ugly*
          //  (2) It assumes that the client has an optimized
          //      setter for "selectedRowKeys" of tree.  It does,
          //      but if it didn't (or didn't in some circumstances)
          //      this would waste a roundtrip;  you really want
          //      the framework (which knows) to handle this
          FacesContext context =  FacesContext.getCurrentInstance();
          ExtendedRenderKitService erks =
            Service.getRenderKitService(context,
                                        ExtendedRenderKitService.class);
          String clientRowKey = _folderTree.getClientRowKeyManager().
            getClientRowKey(context, _folderTree, parentRowKey);
          String clientId = _folderTree.getClientId(context);

          StringBuilder builder = new StringBuilder();
          builder.append("AdfPage.PAGE.findComponent('");
          builder.append(clientId);
          builder.append("').setSelectedRowKeys({'");
          builder.append(clientRowKey);
          builder.append("':true});");
          erks.addScript(context, builder.toString());
        }
      }
      finally
      {
        model.setRowKey(oldRowKey);
      }

      // Only really needed if we're using server-side re-rendering
      // of the tree selection, but performing it here saves
      // a round-trip (just one, to fetch the table data, instead
      // of one to process the selection event only after which
      // the table data gets fetched!)
      _openFolder();
    }
  }

  public void onTableDragDropEnd(DropEvent de)
  {
    _LOG.info("\nDrag Drop End on the table"+de);

    FacesContext context =  FacesContext.getCurrentInstance();
    ExtendedRenderKitService erks =
      Service.getRenderKitService(context,
                                  ExtendedRenderKitService.class);

    StringBuilder sb = new StringBuilder();
    sb.append("alert('Drag Drop End on table:");
    sb.append(de.getDragComponent().getClientId(context));
    sb.append("');");
    erks.addScript(context, sb.toString());

  }

  public DnDAction onTableDrop(DropEvent de)
  {
    _LOG.info("\nDropped on the table"+de);
    _displayDropInfo(de);
    return de.getProposedAction();
  }

  public DnDAction onTreeDrop(DropEvent de)
  {
    _LOG.info("\nDropped on the tree"+de);
    _displayDropInfo(de);
    return de.getProposedAction();
  }

  private void _displayDropInfo(DropEvent de)
  {
    FacesContext context =  FacesContext.getCurrentInstance();
    ExtendedRenderKitService erks =
      Service.getRenderKitService(context,
                                  ExtendedRenderKitService.class);

    StringBuilder sb = new StringBuilder();
    sb.append("alert('Source Component:");
    sb.append(de.getDragComponent().getClientId(context));
    sb.append("\\n");
    sb.append("Source Keys:");
    sb.append(de.getTransferable().getData(DataFlavor.ROW_KEY_SET_FLAVOR));
    sb.append("\\n");
    sb.append("Target Component:").append(de.getDropComponent().getClientId(context));
    sb.append("\\n");
    sb.append("Target RowKey:").append(de.getDropSite());
    sb.append("');");
    erks.addScript(context, sb.toString());
  }

  public void select(SelectionEvent event)
  {
    logRowKeySets(event);
  }

  // Toggles the disclosure state
  public void toggle(RowDisclosureEvent event)
  {
    logRowKeySets(event);
  }
  
  public void onTreeFocus(FocusEvent evt)
  {
    _focusRowKey = ((UIXTree)evt.getSource()).getRowKey();
    _LOG.info("Focus change: "+_focusRowKey);
  }
    
  public Object getDefaultFocusRowKey()
  {
    return _focusRowKey;
  }
  
  public RowKeySet getTreeDisclosedRowKeys()
  {
    if(_folderTree == null)
      _folderTree = (UIXTree) _getComponent();
    if(_folderTree != null && !_treeInit)
    {
      _treeInit = true;
      _treeDisclosedRowKeys = new RowKeySetTreeImpl();
      _treeDisclosedRowKeys.setCollectionModel((CollectionModel)_folderTree.getValue());
      Object oldKey = _folderTree.getRowKey();
      try
      {
        _folderTree.setRowKey(null);
        _folderTree.setRowIndex(0);
        _treeDisclosedRowKeys.add(_folderTree.getRowKey());
      }
      finally
      {
        _folderTree.setRowKey(oldKey);
      }
    }
    return _treeDisclosedRowKeys;
  }

  public boolean isAccordionDiscloseNone()
  {
    return false;
  }

  public String launchProperties()
  {
    UIXTable table = getFolderTable();
    Map rowData = (Map)table.getRowData();

    RequestContext context = RequestContext.getCurrentInstance();
    Map pageflow = context.getPageFlowScope();
    pageflow.put("lastLaunch", new Date());

    pageflow.put("file", rowData);

    return "dialog:properties";
  }

  // Properties action listener
  public void returnFromProperties(ReturnEvent event)
  {
    _renderFolderTableResponse();
  }

  // Cancel out of the dialog path dialog
  public String cancelPath()
  {
    // Null return value since we are canceling
    RequestContext.getCurrentInstance().returnFromDialog(null, null);

    // No action outcome needed
    return null;
  }

  // Return a value from the path dialog
  public String selectPath()
  {
    // Return the entered value.
    RequestContext.getCurrentInstance().returnFromDialog("Hello, world!",
                                                          null);

    // No action outcome needed
    return null;
  }
  // An action which is invoked in response to tree selection
  // changes.  Updates the folder table in response.
  public void treeOpenFolder(SelectionEvent event)
  {
    _openFolder();
  }

  private UIComponent _getComponent()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    Application app = context.getApplication();
    ELResolver elRes = app.getELResolver();
    Object editor = elRes.getValue(context.getELContext(), null, "editor");
    return (UIComponent)elRes.getValue(context.getELContext(), editor, "component");
    
  }

  private DemoComponentSkin _getDemoSkin()
  {
    if (_demoComponentSkin == null)
    {
      FacesContext facesCtx = FacesContext.getCurrentInstance(); 
      ExternalContext ectx = facesCtx.getExternalContext(); 
      Map<String, Object> sessionParamsVar = ectx.getSessionMap();
      _demoComponentSkin = (DemoComponentSkin)sessionParamsVar.get("demoSkin");
    }
    
    return _demoComponentSkin;
  }

  private void _setFolderImageUrl(Map map)
  {
    if (_getDemoSkin().isAltaBased())
      map.put(_ICON_FILE_PROPERTY, _ALTA_FOLDER_IMAGE_URI);
    else
      map.put(_ICON_FILE_PROPERTY, _FOLDER_IMAGE_URI);
  }
  
  private void _setStatusImageUrl(Map map)
  {
    if (_getDemoSkin().isAltaBased())
      map.put(_STICON_FILE_PROPERTY, _ALTA_STFOLDER_IMAGE_URI);
    else
      map.put(_STICON_FILE_PROPERTY, _STFOLDER_IMAGE_URI);
  }  

  private void _setNodeImageUrl(Map map)
  {
    if (_getDemoSkin().isAltaBased())
      map.put(_ICON_FILE_PROPERTY, _ALTA_NODE_IMAGE_URI);
    else
      map.put(_ICON_FILE_PROPERTY, _NODE_IMAGE_URI);
  }

  private Object _getFirstSelectedTreePath()
  {
    RowKeySet selectedPaths = _folderTree.getSelectedRowKeys();
    if (selectedPaths == null)
      return null;

    Iterator iter = selectedPaths.iterator();
    if (!iter.hasNext())
      return null;

    return iter.next();
  }

  private void _openFolder()
  {
// Example of how to get the active component id:
//    FacesContext context = FacesContext.getCurrentInstance();
//    // get the active component
//    UIViewRoot root = context.getViewRoot();
//    String activeId = (String)root.getAttributes().get("activeComponentId");
//    // then use findComponent(activeId) to get a handle to the active component

    try
    {
      // Find the folder table component using the ActionEvent source
      UIXTree tree = _folderTree;
      assert(tree != null);

      Object selectedPath = _getFirstSelectedTreePath();
      // Update the selection
      if (selectedPath != null)
      {
        TreeModel model = getTreeModel();

        Object oldRowKey = model.getRowKey();

        model.setRowKey(selectedPath);

        Map data = (Map)model.getRowData();

        if (data != null)
        {
          setSelectedDirectory((String)data.get(_PATH_FILE_PROPERTY));
        }
        else
        {
          _LOG.warning("No data for path: " + selectedPath);
        }

        model.setRowKey(oldRowKey);
      }
      else
      {
        _LOG.warning("No selected path");
      }

      //Re-render the path display
      _renderPathDisplayResponse();

      _resetTableSelection();
      _renderFolderTableResponse();
    }
    catch (Throwable t)
    {
      _LOG.severe(t);
    }
  }

  // An action which is invoked in response to targetPath context menu.
  public void retrieveTargetPath(ActionEvent event)
  {
    // Find the folder table component using the ActionEvent source
    UIComponent component = event.getComponent();
    UIXTree tree = (UIXTree)component.findComponent(":folderTree");
    assert(tree != null);
    String targetPath = (String)tree.getRowKey();
    FacesContext context = FacesContext.getCurrentInstance();
    String jsToEval = "alert('Confirmation from Backing Bean; target Path is - "+
                  targetPath +
                  ".');";
    ExtendedRenderKitService service =
      Service.getRenderKitService(context, ExtendedRenderKitService.class);
    service.addScript(context, jsToEval);
  }

  // An action which logs the currently selected file
  public void openFolder(ActionEvent event)
  {
    // Find the folder table component using the ActionEvent source
    UIXTable table = getFolderTable();

    // Extract the data for the first selected row (if there is one)
    Map rowData = _getFirstSelectedRow(table);
    if (rowData != null)
    {
      Boolean isDir = (Boolean)rowData.get(_IS_DIRECTORY_FILE_PROPERTY);

      if (Boolean.TRUE.equals(isDir))
      {
        String path = (String)rowData.get(_PATH_FILE_PROPERTY);

        // Update our selected directory state
        setSelectedDirectory(path);
      }
    }
    _resetTableSelection();
    _renderFolderTableResponse();
  }

  // An action which moves a file
  public void moveFile(ActionEvent event)
  {
    UIXTable table = getFolderTable();
    String targetRowKey = (String)table.getRowKey();
    assert(targetRowKey != null);
    Map attributes = table.getAttributes();
    String destinationRowKey = (String)attributes.get("destinationRowKey");
    assert(destinationRowKey != null);

    // TODO actually move the file
  }

  // An action which copies a file
  public void copyFile(ActionEvent event)
  {
    UIXTable table = getFolderTable();

    String targetRowKey = (String)table.getRowKey();
    assert(targetRowKey != null);

    Map attributes = table.getAttributes();
    String destinationRowKey = (String)attributes.get("destinationRowKey");
    assert(destinationRowKey != null);

    // TODO actually copy the file
  }

  /**
   * Action for navigating up one level out of the currently selected
   * directory.
   */
  public void navigateOut(ActionEvent event)
  {
    String currentDirectory = getSelectedDirectory();
    if (currentDirectory != null)
    {
      File currentFile = new File(currentDirectory);
      if (currentFile != null)
      {
        String parentPath = currentFile.getParentFile().getAbsolutePath();

        // Prevent navigation above root directory
        if (parentPath != null &&
            parentPath.startsWith(_rootDirectory.getAbsolutePath()))
        {
          setSelectedDirectory(parentPath);
        }
      }
    }
    _renderFolderTableResponse();
  }

  /**
   * Returns a TreeModel which represents the folder hierarchy of a file system.
   * The TreeModel will be rooted at the path specified by the
   * "rootDirectoryPath".
   */
  public TreeModel getTreeModel()
  {
    if (_treeModel == null)
    {
      try
      {
        _treeModel = _createTreeModel();
      }
      catch (IntrospectionException e)
      {
        // =-=ags Initialize _treeModel with null model instance?
        _LOG.severe(e);
      }
    }
    return _treeModel;
  }

  public RowKeySet getTreeState()
  {
    if (_treeState == null)
    {
      // Create the PathSet that we'll use to store the initial
      // expansion state for the tree
      RowKeySet treeState = new RowKeySetTreeImpl();

      // RowKeySet requires access to the TreeModel for currency.
      TreeModel model = getTreeModel();
      treeState.setCollectionModel(model);

      // Make the model point at the root node
      Object oldRowKey = model.getRowKey();
      int oldIndex = model.getRowIndex();

      model.setRowKey(null);
      model.setRowIndex(0);

      treeState.setContained(true);

      model.setRowKey(oldRowKey);
      model.setRowIndex(oldIndex);

      _treeState = treeState;
    }

    return _treeState;
  }

  /**
   * Returns a CollectionModel lwhich represents the files in the currently
   * selected directory.
   */
  public CollectionModel getTableModel() throws IntrospectionException
  {
    // return cached table model when available
    // if no cached instance exists, then create one
    if (_fileTableModel == null)
      _fileTableModel = _createTableModel(getSelectedDirectory());

    return _fileTableModel;
  }

  /**
   * Returns the file representing the root directory in this file system. If no
   * path has been set, it will default to the root directory of the web
   * application in which
   * it is running.
   */
  public File getRootDirectory()
  {
    if (_rootDirectory == null)
    {
      // default to web context root for now, so
      // we have something to browse if no managed
      // bean property was specified
      final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
      if(ExternalContextUtils.isPortlet(ec))
      {
        _rootDirectory = _getPortletRootDirectory(ec);
      }
      else
      {
        _rootDirectory = _getServletRootDirectory(ec);
      }
    }

    return _rootDirectory;
  }

  /**
   * Sets the path representing the root directory of this file system. This
   * should be set before using the bean, such as through a managed bean
   * property.
   */
  public void setRootDirectoryPath(String path)
  {
    _rootDirectory = new File(path);
    _treeModel = null;
  }

  /**
   * Returns the path of the currently selected directory.
   */
  public String getSelectedDirectory()
  {
    return _selected;
  }

  /**
   * Sets the path of the currently selected directory.
   */
  public void setSelectedDirectory(String selected)
  {
    String selectedDir = getSelectedDirectory();
    if (selectedDir != selected || !selectedDir.equals(selected) || (_fileTableModel == null))
    {
      _fileTableModel = _createTableModel(selected);
    }
    _selected = selected;
  }

  /**
   * Returns the path of the currently selected directory modified for display purposes.
   */
  public String getDisplayedDirectory()
  {
    String selectedDir = getSelectedDirectory();

    if (selectedDir != null)
    {
      String rootDir = getRootDirectory().toString();

      int i;
      for ( i=0; i < rootDir.length(); i++)
      {
        if (rootDir.charAt(i) != selectedDir.charAt(i))
          break;
      }

      StringBuffer buffer = new StringBuffer(selectedDir.substring(i, selectedDir.length()));
      for (int j = 0; j < buffer.length(); j++)
      {
        char c = buffer.charAt(j);
        if (c == File.separatorChar)
          buffer.replace(j,j+1," > ");
      }

      buffer.insert(0, "My Files");

      return buffer.toString();
    }
    else
    {
      return "none";
    }
  }



  /**
   * Returns a List of Maps representing the Files in a directory.
   * All files will be returned, and this method should be used
   * to get the files to be displayed in a file table list.
   */
  public List getChildFileList(File directory)
  {
    if (directory == null || !directory.exists() || !directory.isDirectory())
      return new ArrayList(); //changed to show nothing rather than error msg to handle "nothing-yet-selected" case

    File[] files = directory.listFiles();
    if ((files == null) || (files.length == 0))
      return _EMPTY_DIRECTORY_LIST;

    ArrayList directoryList = new ArrayList(files.length);

    // Add an entry for each file
    for (int i = 0; i < files.length; i++)
    {
      File childFile = files[i];

      Map entry = new HashMap(19);
      entry.put(_NAME_FILE_PROPERTY, childFile.getName());
      entry.put(_PATH_FILE_PROPERTY, childFile.getPath());
      entry.put(_SIZE_FILE_PROPERTY, _getFileSize(childFile.length()));
      entry.put(_LAST_MODIFIED_FILE_PROPERTY,
                _getFormattedDate(childFile.lastModified()));

      if (childFile.isDirectory())
      {
        entry.put(_IS_DIRECTORY_FILE_PROPERTY, Boolean.TRUE);
        _setFolderImageUrl(entry);
        entry.put(_TYPE_FILE_PROPERTY, _FOLDER_TYPE);
      }
      else
      {
        entry.put(_IS_DIRECTORY_FILE_PROPERTY, Boolean.FALSE);
        entry.put(_ICON_FILE_PROPERTY, _FILE_IMAGE_URI);
        entry.put(_TYPE_FILE_PROPERTY, _DOCUMENT_TYPE);
      }

      directoryList.add(entry);
    }

    // Sort the directory list
    Collections.sort(directoryList, _FILE_ENTRY_COMPARATOR);

    return directoryList;
  }

  /**
   * Returns a List of Maps representing the Files in a directory.
   * Only directories will be returned, so this method should be used
   * to get the files to be displayed in a level of a navigation tree.
   */
  public List getChildDirectoryList(File directory)
  {
    return _getChildDirectoryList(directory, false);
  }

  /**
   * Returns a List of Maps representing the Files in a directory.
   * Unlike getChildDirectoryList(), getImmediateChildDirectoryList()
   * only returns information about immediate children of the specified
   * directory.  This allows the returns List to be marshalled back
   * to the client without recursively sending data for all subtrees.
   */
  public List getImmediateChildDirectoryList(File directory)
  {
    return _getChildDirectoryList(directory, true);
  }

  public void doShowFolders(ActionEvent event)
  {
    UIComponent component = event.getComponent();
    UIXShowDetail item = (UIXShowDetail)component.findComponent(
      FileSystem._ID_FOLDERS_ITEM);
    if (item != null)
    {
      // accordion is present
      boolean itemDisclosedState = item.isDisclosed();
      _setAccordionPaneDisclosed(itemDisclosedState, item,
          FileSystem._ID_SEARCH_ITEM, FileSystem._ID_REPORTS_ITEM);
    }
  }

  public void doShowSearch(ActionEvent event)
  {
    UIComponent component = event.getComponent();
    UIXShowDetail item = (UIXShowDetail)component.findComponent(
      FileSystem._ID_SEARCH_ITEM);
    if (item != null)
    {
      // accordion is present
      boolean itemDisclosedState = !item.isDisclosed(); // toggle
      _setAccordionPaneDisclosed(itemDisclosedState, item,
        FileSystem._ID_FOLDERS_ITEM, FileSystem._ID_REPORTS_ITEM);
    }
  }

  public void doShowReports(ActionEvent event)
  {
    UIComponent component = event.getComponent();
    UIXShowDetail item = (UIXShowDetail)component.findComponent(
      FileSystem._ID_REPORTS_ITEM);
    if (item != null)
    {
      // accordion is present
      boolean itemDisclosedState = !item.isDisclosed(); // toggle
      _setAccordionPaneDisclosed(itemDisclosedState, item,
        FileSystem._ID_FOLDERS_ITEM, FileSystem._ID_SEARCH_ITEM);
    }
  }

  public void doSearch(ActionEvent event)
  {
    UIXTable folderTable = getFolderTable();
    String target = _criteriaTarget;

    if (target != null)
    {
      if (target.startsWith("My Files"))
      {
        target = target.substring("My Files".length());
      }

      if (target.startsWith("/"))
      {
        target = target.substring("/".length());
      }

      File searchRoot = new File(getRootDirectory(), target);
      FileFilter filter = new CriteriaFileFilter(_criteriaName, _criteriaWhen,
                                                 _criteriaSize);
      List allFiles = new ArrayList();
      File[] files = searchRoot.listFiles(filter);
      // TODO: recursive search
      allFiles.addAll(_getFileList(files));
      _fileTableModel = _createTableModel(allFiles);
    }

    RequestContext adfContext = RequestContext.getCurrentInstance();
    adfContext.addPartialTarget(folderTable);
  }

  public void doClear(ActionEvent event)
  {
    UIXTable table = getFolderTable();
    _fileTableModel = null;
    RequestContext adfContext = RequestContext.getCurrentInstance();
    adfContext.addPartialTarget(table);
  }

  private void _setAccordionPaneDisclosed(boolean actionItemDisclosedState,
    UIXShowDetail actionItem, String item2ID, String item3ID)
  {
    RequestContext adfContext = RequestContext.getCurrentInstance();
    UIXShowDetail item2 =
      (UIXShowDetail)actionItem.findComponent(item2ID);
    UIXShowDetail item3 =
      (UIXShowDetail)actionItem.findComponent(item3ID);
    if (actionItemDisclosedState)
    {
      // disclose the action item without worries
      _setItemDisclosedAndAddPartialTarget(actionItem, true, adfContext);
    }
    else
    {
      if ( item2.isDisclosed() ||  item3.isDisclosed() )
      {
        // only undisclose the action item if one or more others are disclosed
        _setItemDisclosedAndAddPartialTarget(actionItem, false, adfContext);
      }
    }
  }

  private void _setItemDisclosedAndAddPartialTarget(UIXShowDetail item,
    boolean newDisclosed, RequestContext context)
  {
    if (newDisclosed != item.isDisclosed())
    {
      // only do this if different from what it was already
      item.setDisclosed(newDisclosed);
      context.addPartialTarget(item);
    }
  }


  // Returns the child directories of the specified directory.
  private List _getChildDirectoryList(
    File    directory,
    boolean immediate
    )
  {
    if (!directory.exists() || !directory.isDirectory())
      return _NO_SUCH_DIRECTORY_LIST;

    // List files, usinga FilenameFilter to filter
    // down to directories
    File[] files = directory.listFiles(_DIRECTORY_FILTER);
    if ((files == null) || (files.length == 0))
    {
      // Return a non-nul value so that we avoid repeatedly listing
      // this empty directory.
      return Collections.EMPTY_LIST;
    }

    ArrayList directoryList = new ArrayList(files.length);

    // Add an entry for each file
    for (int i = 0; i < files.length; i++)
    {
      File childFile = files[i];

      Map entry = new Directory();
      entry.put(_NAME_FILE_PROPERTY, childFile.getName());
      entry.put(_SIZE_FILE_PROPERTY, _getFileSize(childFile.length()));
      entry.put(_LAST_MODIFIED_FILE_PROPERTY,
                _getFormattedDate(childFile.lastModified()));

      entry.put(_IS_DIRECTORY_FILE_PROPERTY, Boolean.TRUE);
      _setFolderImageUrl(entry);
      
      if (Math.random() > 0.5)
        _setStatusImageUrl(entry);
      else
        entry.put (_STICON_FILE_PROPERTY, _BLANK_IMAGE_URI);
      entry.put(_PATH_FILE_PROPERTY, childFile.getPath());

      if (!immediate)
      {
        // Only include a reference to the child directory list
        // for non-immediate case, since we don't want to
        // recurse into subdirectories.
        entry.put(_FILE_LIST_CHILD_PROPERTY,
                  new DeferredDirectoryList(childFile));
      }

      directoryList.add(entry);
    }

    // Sort the directory list
    Collections.sort(directoryList, _FILE_ENTRY_COMPARATOR);

    return directoryList;
  }

  // Creates the TreeModel
  private TreeModel _createTreeModel()
    throws IntrospectionException
  {
    File rootDirectory = getRootDirectory();
    Map rootData = new HashMap(19);
    rootData.put(_NAME_FILE_PROPERTY, "My Files");
    rootData.put(_PATH_FILE_PROPERTY, rootDirectory.getPath());
    _setFolderImageUrl(rootData);
    _setStatusImageUrl(rootData);

    List rootChildren = getChildDirectoryList(rootDirectory);
    rootData.put(_FILE_LIST_CHILD_PROPERTY, rootChildren);

    ArrayList rootList = new ArrayList(1);
    rootList.add(rootData);

    return new FileSystemTreeModel(rootList,
                                            (String)_FILE_LIST_CHILD_PROPERTY,
                                            (String)_NAME_FILE_PROPERTY)
      {
        @Override
        public boolean isContainer()
        {
          boolean isContainer = super.isContainer();
          if(!isContainer)
          {
            Map row = (Map)getRowData();
            _setNodeImageUrl(row);

          }
          return isContainer;
        }
      };
  }

  // return the readable string based on byte length
  private Long _getFileSize(long length)
  {
    long size = 1;
    size = length/1024;
    if (size < 1)
      size = 1;

    return new Long(size);
  }

  // return a usable formatted date
  private String _getFormattedDate(long numeric)
  {
    Date d = new Date(numeric);
    return _LAST_MODIFIED_FORMAT.format(d);
  }

  // Used to add a message entry to a directory list
  // instead of a file list
  private static final void _addMessageFile(
    List   files,
    String message
    )
  {
    Map map = new HashMap(1);
    map.put(_NAME_FILE_PROPERTY, message);
    map.put(_IS_MESSAGE_FILE_PROPERTY, Boolean.TRUE);

    files.add(map);
  }

  // Returns the data associated with the first selected
  // row in the specified table
  private static Map _getFirstSelectedRow(
    UIXTable table
    )
  {
    Map row = null;

    Iterator selection = table.getSelectedRowKeys().iterator();

    if ((selection != null) && selection.hasNext())
    {
      // Save away the current row index
      int oldIndex = table.getRowIndex();

      // Get the data for the first selected row
      table.setRowKey((String)selection.next());
      row = (Map)table.getRowData();

      // Restore the current row index
      table.setRowIndex(oldIndex);
    }

    return row;
  }

  private void logAction(UIXTree tree)
  {
    Object currKey = tree.getRowKey();
    StringBuilder keyStr = new StringBuilder();
    for(Object key : (List)currKey)
    {
      keyStr.append(key.toString()+"|");
    }
    _LOG.info("Currency: " + keyStr);
  }

  private void logRowKeySets(RowKeySetChangeEvent event)
  {
    RowKeySet sel = event.getAddedSet();
    RowKeySet unSel = event.getRemovedSet();

    _LOG.info("Added: " + sel + "\nRemoved: " + unSel);
  }

  private void _resetTableSelection()
  {
    getFolderTable().getSelectedRowKeys().clear();
  }

  // Re-render the folder table
  private void _renderFolderTableResponse()
  {
    RequestContext adfContext = RequestContext.getCurrentInstance();
    adfContext.addPartialTarget(getFolderTable());
  }

  // Re-render the path display bar
  private void _renderPathDisplayResponse()
  {
    RequestContext adfContext = RequestContext.getCurrentInstance();
    adfContext.addPartialTarget(getPathDisplay());
  }

  // Creates a table model instance representing the
  // files in the selected directory
  private CollectionModel _createTableModel(String selectedDirectory)
  {
    File selectedDirFile = (selectedDirectory != null)
                              ? new File(selectedDirectory)
                              : null;
    List fileList = getChildFileList(selectedDirFile);
    return _createTableModel(fileList);
  }

  private CollectionModel _createTableModel(List fileList)
  {
    return new RowKeyPropertyModel(fileList, (String)_NAME_FILE_PROPERTY);
  }

  private List _getFileList(
    File[]  files)
  {
    if (files == null)
      return Collections.EMPTY_LIST;

    ArrayList fileList = new ArrayList(files.length);

    // Add an entry for each file
    for (int i = 0; i < files.length; i++)
    {
      File childFile = files[i];

      Map entry = new Directory();
      entry.put(_NAME_FILE_PROPERTY, childFile.getName());
      entry.put(_SIZE_FILE_PROPERTY, _getFileSize(childFile.length()));
      entry.put(_LAST_MODIFIED_FILE_PROPERTY,
                _getFormattedDate(childFile.lastModified()));
      entry.put(_PATH_FILE_PROPERTY, childFile.getPath());

      if (childFile.isDirectory())
      {
        entry.put(_IS_DIRECTORY_FILE_PROPERTY, Boolean.TRUE);
        _setFolderImageUrl(entry);
        entry.put(_TYPE_FILE_PROPERTY, _FOLDER_TYPE);
      }
      else
      {
        entry.put(_IS_DIRECTORY_FILE_PROPERTY, Boolean.FALSE);
        entry.put(_ICON_FILE_PROPERTY, _FILE_IMAGE_URI);
        entry.put(_TYPE_FILE_PROPERTY, _DOCUMENT_TYPE);
      }

      fileList.add(entry);
    }

    return fileList;
  }

  static private final File _getPortletRootDirectory(final ExternalContext ec)
  {
    //Use reflection here because we need to not have the Portal classes even
    //as a BUILD dependancy for this project.  While maven handles this
    //dependancy just fine, we have found that people are taking the demo source
    //war file and importing it into an IDE without the porlet API's being
    //available.

    // TODO sobryan (dependancy=OpenSource)
    // Can we tell people that if they want to do this that they need to add
    // the portlet api to thier build path?  If so, we can change this to access
    // the portlet api directly.  It's probably not appropriate to include the
    // portlet-api.jar here and it should remain a "provided" api.
    try
    {
      String path = null;
      Object portletContext = ec.getContext();
      Method getRealPath = portletContext.getClass().getMethod("getRealPath",
                                                   new Class[] {String.class});
      path = (String)getRealPath.invoke(portletContext, new Object[] {"/"});
      
      if (path == null)
      {
        Method getResource = portletContext.getClass().getMethod("getResource",
                                                    new Class[] {String.class});
        java.net.URL url = (java.net.URL) getResource.invoke(portletContext,
                                                           new Object[] {"/"});
        path = url.getPath();
      }
        
      return new File(path);
    }
    catch (Exception e)
    {
      //We should never get here.  If we do, something is VERY VERY wrong
      throw new RuntimeException("Unable to get the Portlet Root directory.", e);
    }
  }

  static private final File _getServletRootDirectory(final ExternalContext ec)
  {
    ServletContext context = (ServletContext)ec.getContext();
    String path = context.getRealPath("/");

    // Weblogic does not unwar its war file. getRealPath() returns null
    // on war files.
    if (path == null)
    {
      try
      {
        java.net.URL url = context.getResource("/");
        path = url.getPath();
      }
      catch (Exception e)
      {
        throw new RuntimeException("Unable to get the Webapp Root directory.", e);        
      }
    }
    return new File(path);
  }

  // private variables

  private File      _rootDirectory;
  private String    _selected;
  private TreeModel _treeModel;
  private RowKeySet   _treeState;

  // Constant for retrieving file list from a given node
  private static final String _FILE_LIST_CHILD_PROPERTY = "fileList";

  // Constants for file properties
  private static final Object _NAME_FILE_PROPERTY          = "name";
  private static final Object _LAST_MODIFIED_FILE_PROPERTY = "lastModified";
  private static final Object _SIZE_FILE_PROPERTY          = "size";
  private static final Object _IS_DIRECTORY_FILE_PROPERTY  = "isDirectory";
  private static final Object _IS_MESSAGE_FILE_PROPERTY    = "isMessage";
  private static final Object _PATH_FILE_PROPERTY          = "path";
  private static final Object _ICON_FILE_PROPERTY          = "icon";
  private static final Object _STICON_FILE_PROPERTY        = "statusIcon";
  private static final Object _TYPE_FILE_PROPERTY          = "type";
  

  // Some directory lists for non-existent/empty directories
  private static List _NO_SUCH_DIRECTORY_LIST = null;
  private static List _EMPTY_DIRECTORY_LIST = null;

  private static final SimpleDateFormat _LAST_MODIFIED_FORMAT =
    new SimpleDateFormat("MM/dd/yyyy h:mm a");

  private static final String _FILE_IMAGE_URI = "/images/file.gif";
  private static final String _FOLDER_IMAGE_URI = "/afr/folder_ena.png";
  private static final String _OPEN_FOLDER_IMAGE_URI = "/afr/folderopen_ena.png";
  private static final String _STFOLDER_IMAGE_URI = "/images/status_warning.png";
  private static final String _NODE_IMAGE_URI = "/afr/node_ena.png";
  private static final String _BLANK_IMAGE_URI = "/images/blank.png";
  
  /* for alta */
  private static final String _ALTA_FOLDER_IMAGE_URI = "/images/alta_v1/folder_ena.png";
  private static final String _ALTA_OPEN_FOLDER_IMAGE_URI = "/images/alta_v1/folderopen_ena.png";
  private static final String _ALTA_STFOLDER_IMAGE_URI = "/images/alta_v1/warning_status.png";
  private static final String _ALTA_NODE_IMAGE_URI = "/images/alta_v1/node_ena.png";

  // Values for _TYPE_FILE_PROPERTY
  private static final String _FOLDER_TYPE = "folder";
  private static final String _DOCUMENT_TYPE = "document";

  static
  {
    // Set up our stub directory lists
    _NO_SUCH_DIRECTORY_LIST = new ArrayList(1);
    _addMessageFile(_NO_SUCH_DIRECTORY_LIST,
                     "Directory does not exist or is not a directory.");

    _EMPTY_DIRECTORY_LIST = new ArrayList(1);
    _addMessageFile(_EMPTY_DIRECTORY_LIST, "Empty");

  }


  // All this does is expose an action method so that
  // clicking on a tree node can set the selected directory
  public class Directory extends HashMap
  {
    public Directory()
    {
      super(19);
    }

    public String viewDirectory()
    {
      setSelectedDirectory((String)get(_PATH_FILE_PROPERTY));
      return null;
    }
  }

  // Put as a child in a directory object, so that its own children
  // can be fetched lazily.
  // There's definitely some performance work to be done here,
  // as well as worries about stale file references
  private class DeferredDirectoryList extends AbstractList
  {
    public DeferredDirectoryList(File parentFile)
  {
    _parentPath = parentFile.getPath();
  }

  public int size()
  {
    if (_childDirectoryList == null)
        _childDirectoryList = getChildDirectoryList(new File(_parentPath));

    return (_childDirectoryList == null)
             ? 0
         : _childDirectoryList.size();
  }

  public Object get(int index)
  {
    if (_childDirectoryList == null)
        _childDirectoryList = getChildDirectoryList(new File(_parentPath));

    return (_childDirectoryList == null)
             ? null
         : _childDirectoryList.get(index);
  }
  
  public Object set(int index, Object element)
  {
    return (_childDirectoryList == null) ? null :
      _childDirectoryList.set(index, element);
  }

  private List _childDirectoryList;
  private String _parentPath;
  }

  // A filter which returns only directories
  private static class DirectoryFilter implements FilenameFilter
  {
     public boolean accept(File dir, String name)
     {
       File file = new File(dir, name);

       return file.isDirectory();
     }
  }

  // A comparator for sorting file entry Maps
  private static class FileEntryComparator implements Comparator
  {
    public FileEntryComparator()
    {
      _sortProperty = null;  
    }
    
    public FileEntryComparator(Object sortProperty)
    {
      _sortProperty = sortProperty;
    }
    public int compare(Object o1, Object o2)
    {
      Map file1 = (Map)o1;
      Map file2 = (Map)o2;

      // Directories should appear before non-directory files
      boolean isDir1 = _isDirectory(file1);
      boolean isDir2 = _isDirectory(file2);

      if (isDir1 && !isDir2)
        return -1;
      if (!isDir1 && isDir2)
        return 1;

      // If both entries are directories or both entries
      // are files, sort by name if _sortProperty == null
      // otherwise sort by _sortProperty
      if (_sortProperty == null)
      {
        String name1 = _getName(file1);
        String name2 = _getName(file2);
        return name1.compareTo(name2);
      }
      else
      {
        Comparable comp1 = (Comparable) file1.get(_sortProperty);
        Comparable comp2 = (Comparable) file2.get(_sortProperty);
        return comp1.compareTo(comp2);        
      }
    }

    public boolean equals(Object obj)
    {
      return super.equals(obj);
    }

    private static boolean _isDirectory(Map file)
    {
      Object value = file.get(_IS_DIRECTORY_FILE_PROPERTY);
      if ((value == null) || (Boolean.FALSE == value))
        return false;

      return true;
    }

    private static String _getName(Map file)
    {
      return (String)file.get(_NAME_FILE_PROPERTY);
    }
    
    private Object _sortProperty;
  }

  public String getCriteriaName()
  {
    return _criteriaName;
  }

  public void setCriteriaName(String criteriaName)
  {
    _criteriaName = criteriaName;
  }

  public String getCriteriaContents()
  {
    return _criteriaContents;
  }

  public void setCriteriaContents(String criteriaContents)
  {
    _criteriaContents = criteriaContents;
  }

  public String getCriteriaTarget()
  {
    return _criteriaTarget;
  }

  public void setCriteriaTarget(String criteriaTarget)
  {
    _criteriaTarget = criteriaTarget;
  }

  public String getCriteriaWhen()
  {
    return _criteriaWhen;
  }

  public void setCriteriaWhen(String criteriaWhen)
  {
    _criteriaWhen = criteriaWhen;
  }

  public String getCriteriaSize()
  {
    return _criteriaSize;
  }

  public void setCriteriaSize(String criteriaSize)
  {
    _criteriaSize = criteriaSize;
  }

  public UIComponent getPathDisplay()
  {
    return _pathDisplay;
  }

  public void setPathDisplay(UIComponent pathDisplay)
  {
    _pathDisplay = pathDisplay;
  }

  public UIXTable getFolderTable()
  {
    return _folderTable;
  }

  public void setFolderTable(UIXTable folderTable)
  {
    _folderTable = folderTable;
  }

  public UIXTree getFolderTree()
  {
    return _folderTree;
  }

  public void setFolderTree(UIXTree folderTree)
  {
    _folderTree = folderTree;
  }


  /**
   * Extends RowKeyPropertyModel and adds custom sorting to handle
   * files and directories (see FileEntryComparator)
   */
  public static class FileSystemModel extends RowKeyPropertyModel
  {
    public FileSystemModel(Object model, String rowKeyProperty)
    {
      super(model, rowKeyProperty);
    }
    
    @Override
    public void setSortCriteria(List criteria)
    {
      if ((criteria == null) || (criteria.size() == 0))
        return;

      _sortCriterion = (SortCriterion) criteria.get(0);
      _sort(_sortCriterion.getProperty(), _sortCriterion.isAscending());
    }    
   
    @Override
    public List getSortCriteria()
    {
      return (_sortCriterion == null)? Collections.EMPTY_LIST:
             Collections.singletonList(_sortCriterion);
    }

    private void _sort(String property, boolean isAscending)
    {
      Comparator comp = new FileEntryComparator(property);
      
      if (!isAscending)
        comp = new Inverter(comp);
      List model = (List)getWrappedData();      
      Collections.sort(model, comp);      
    }
    
    private static final class Inverter
      implements Comparator
    {
      public Inverter(Comparator comp)
      {
        _comp = comp;
      }

      public int compare(Object o1, Object o2)
      {
        return _comp.compare(o2, o1);
      }

      private final Comparator _comp;
    }

    private SortCriterion _sortCriterion;    
  }
  
  /**
   * Extends RowKeyPropertyTreeModel and adds custom sorting logic (see FileSystemModel)
   */
  public static class FileSystemTreeModel extends RowKeyPropertyTreeModel
  {
    public FileSystemTreeModel(Object instance, String childProperty, 
                                   String rowKeyProperty)
    {
      super();
      setWrappedData(new FileSystemModel(instance, rowKeyProperty));
      this.setRowKeyProperty(rowKeyProperty);
      setChildProperty(childProperty);
    }

    @Override
    protected CollectionModel createChildModel(Object childData)
    {
      CollectionModel model = 
        new FileSystemModel(childData, getRowKeyProperty());
      model.setRowIndex(-1);

      // Propagate the sort criteria to child models.
      // Caution: Because of the way ChildPropertyTreeModel works, this is very inefficient.
      if(null != rootCriteria)
      {
        model.setSortCriteria(rootCriteria);
      }    
      return model;
    }
    
    @Override
    public void setSortCriteria(List<SortCriterion> criteria)
    {
      super.setSortCriteria(criteria);
      if(null == getRowKey())
      {
        rootCriteria= criteria;
      }
    }        
    
    protected List<SortCriterion> rootCriteria = null;
  }
  
  private DemoComponentSkin _demoComponentSkin = null;
  private UIComponent _pathDisplay;
  private UIXTable _folderTable;
  private UIXTree  _folderTree;

  private String _criteriaName;
  private String _criteriaContents;
  private String _criteriaTarget = "My Files";
  private String _criteriaWhen = "any";
  private String _criteriaSize = "any";
  
  private Object _focusRowKey = null;
  private boolean _treeInit = false;
  RowKeySet _treeDisclosedRowKeys = null;

  // cache the table model so it's not re-created each call to getTableModel()
  private CollectionModel _fileTableModel = null;

  private static final String _ID_FOLDERS_ITEM = ":foldersAccordionItem";
  private static final String _ID_SEARCH_ITEM = ":searchAccordionItem";
  private static final String _ID_REPORTS_ITEM = ":reportsAccordionItem";
  private static final long serialVersionUID = 1L;    

  private static final FilenameFilter _DIRECTORY_FILTER =
    new DirectoryFilter();

  private static final Comparator _FILE_ENTRY_COMPARATOR =
    new FileEntryComparator();

  private static final ADFLogger _LOG =
    ADFLogger.createADFLogger(FileSystem.class);
}
