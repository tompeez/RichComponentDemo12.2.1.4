/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.navigator;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adfdemo.view.explorer.rich.data.FileExplorerDataFactory;
import oracle.adfdemo.view.explorer.rich.data.FileItem;
import oracle.adfdemo.view.explorer.rich.data.FileItemProperty;

import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.component.UIXTree;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetTreeImpl;
import org.apache.myfaces.trinidad.model.TreeModel;

public class FoldersNavigatorView extends BaseNavigatorView
{
  public FoldersNavigatorView()
  {
  }
  
  public FoldersNavigatorView(String type, String icon, String label, Map data)
  {
    super(type, icon, label, data);
  }
  
  /**
   * Returns a TreeModel which represents the folder hierarchy of a file system.
   * @return TreeModel
   */
  public TreeModel getFoldersTreeModel()
  {
    if (_foldersTreeModel == null)
    {
      _foldersTreeModel = _createFoldersTreeModel(); 
    }
    
    return _foldersTreeModel;
  }
  
  public TreeModel getRootlessFoldersTreeModel()
  {
    if (_nonRootedFoldersTreeModel == null)
    {
      _nonRootedFoldersTreeModel = _createNonRootedFoldersTreeModel(); 
    }
    
    return _nonRootedFoldersTreeModel;
  }
  
  /**
   * Returns the disclosed rowkeySet for the folders tree
   * @return
   */
  public RowKeySet getFoldersTreeDisclosedRowKeys()
  {
    if (_foldersTreeState == null)
    {
      // Create the PathSet that we'll use to store the initial
      // expansion state for the tree
      RowKeySet treeState = new RowKeySetTreeImpl();

      // RowKeySet requires access to the TreeModel for currency.
      TreeModel model = this.getFoldersTreeModel();
      treeState.setCollectionModel(model);

      // Make the model point at the root node
      Object oldRowKey = model.getRowKey();
      int oldIndex = model.getRowIndex();

      model.setRowKey(null);
      model.setRowIndex(0);

      treeState.setContained(true);

      // Restore keys
      model.setRowKey(oldRowKey);
      model.setRowIndex(oldIndex);

      _foldersTreeState = treeState;
    }

    return _foldersTreeState;
  }
  
  /**
   * Implement abstract method from parent
   * Refresh the navigators' models and components
   */
  public void refresh()
  {
    // Update Folders tree model
    _foldersTreeModel = _createFoldersTreeModel();
  }
  
  // Re-render the path display bar
  public void renderFoldersTree()
  {
    RequestContext.getCurrentInstance().
      addPartialTarget(_foldersTreeComponent);
  }
  
  public Object getSelectedFileItems()
  {
    Object focusRowKey = getFirstSelectedTreePath();
    if (focusRowKey == null)
    {
      // Check if Folders tree has focus node
      focusRowKey = _foldersTreeComponent.getFocusRowKey();
      if (focusRowKey == null)
      {
        return null;
      }
    }
    
    // If it does then get the file path from the focused object and 
    // ask factory to remove it    
    Object oldRK = _foldersTreeComponent.getRowKey();
    _foldersTreeComponent.setRowKey(focusRowKey);
    FileItem data = (FileItem)_foldersTreeComponent.getRowData();
    _foldersTreeComponent.setRowKey(oldRK);
    
    return data;
  }
  
  public Object getFirstSelectedTreePath()
  {
    RowKeySet selectedPaths = _foldersTreeComponent.getSelectedRowKeys();
    
    return _getFirstSelectedTreePath(selectedPaths);
  }

  /**
   * Show the selected folder in the content view
   * @param selectionEvent
   */
  public void showSelectedFolderContent(SelectionEvent selectionEvent)
  {
    openSelectedFolder();
  }
  
  /**
   * Copied and modified from FileSystem._openFolder method
   * Show the content of the selected item in Folders
   */
  public void openSelectedFolder()
  { 
    try
    {
      // Get first selected item from the tree
      Object selectedPath = getFirstSelectedTreePath();
      
      // Update the selection
      if (selectedPath != null)
      {
        // Get folders tree model and save old key
        TreeModel model = getFoldersTreeModel();
        Object oldRowKey = model.getRowKey();
        
        // Set selected row key to get the data
        model.setRowKey(selectedPath);
        FileItem data = (FileItem)model.getRowData();

        if (data != null)
        {
          _LOG.info("\nOpen selected folder: "+ data.getDisplayPathName());
          
          // Update the selected directory
          _feBean.setSelectedDirectory(data.getDisplayPathName());
          _feBean.setLastSelectedFileItem(data);
        }
        else
        {
          _LOG.warning("No data for path: " + selectedPath);
        }
        
        // Restore old key
        model.setRowKey(oldRowKey);
      }
      else
      {
        _LOG.warning("No selected path");
      }
    }
    catch (Throwable t)
    {
      _LOG.severe(t);
    }
  }

  /**
   * On drop listener for the folders tree
   * @param dropEvent
   * @return DnDAction
   */
  public DnDAction onTreeDrop(DropEvent dropEvent)
  {
    // Log it 
    _LOG.info("\nDropped on the folders tree with DropEvent: " + dropEvent);
    _feBean.logDropInfo(dropEvent);
    
    try
    {
      // Get drag source component. 
      // Since for this example we know only table can drop to this handler 
      // (with model value of "fileModel") we could cast it to UIXTable
      UIXTable dragSource = (UIXTable)dropEvent.getDragComponent();
    
      // Get source FileItem
      FileItem sourceFileItem = (FileItem)dragSource.getSelectedRowData();    
      if (sourceFileItem != null)
      {        
        // Get target node
        Object targetRowKey = dropEvent.getDropSite();
        
        if(targetRowKey == null)
        {
          _LOG.info("\nDrop on Folders tree not supported");
          return DnDAction.NONE;
        }
        
        Object oldRK = _foldersTreeComponent.getRowKey();
        _foldersTreeComponent.setRowKey(targetRowKey);
        FileItem targetData = (FileItem)_foldersTreeComponent.getRowData();
        _foldersTreeComponent.setRowKey(oldRK);

        // Move the dragged file          
        _feBean.getHeaderManager().moveFileItemTo(sourceFileItem, 
                                                  targetData.getPathName());
      }
      else
      {
        _LOG.info("\nDrop on Folders fail causing no action");
        return DnDAction.NONE;      
      }

      // Refresh navigator via its manager
      _feBean.refreshAllManagers();
    }
    catch (Throwable t)
    {
      _LOG.severe(t);
      return DnDAction.NONE; 
    }
    
    return DnDAction.MOVE;
  }
  
  /**
   * Sync the folders tree o show the right disclosed and selected nodes
   * @param path
   */
  public void autoSyncFoldersTree(String path)
  {
    try
    {
      // Get tree's disclosed row keys
      RowKeySet disclosedRks = _foldersTreeComponent.getDisclosedRowKeys();

      // Get tree's selected row keys
      RowKeySet selectedRks = _foldersTreeComponent.getSelectedRowKeys();
      if (selectedRks == null)
      {
        selectedRks = new RowKeySetTreeImpl();
        selectedRks.setCollectionModel(getFoldersTreeModel());
        _foldersTreeComponent.setSelectedRowKeys(selectedRks);
      }

      // Get the RowKeySet for the topicURL from the topic-rowkey map
      Object selectedRowKey = getDestinationToRowKey().get(path);
      if (selectedRowKey == null)
      {
        // reset the selected row keys
        selectedRks.removeAll();
      }
      else
      {
        // Check if we need to update tree with new selected row key
        if (selectedRks.contains(selectedRowKey))
        {
          // if it does then simply return
          return;
        }

        // else update the selected row keys
        selectedRks.removeAll();
        selectedRks.add(selectedRowKey);

        // update disclosed rowkeys
        _addAncestorTreeNodesOfSelectedNode(_foldersTreeComponent, 
                                            selectedRowKey, 
                                            disclosedRks);
      }
    }
    catch (Exception e)
    {
      _LOG.warning(e.getMessage());
    }
    
  }
  
  /**
   * Returns the map to folders path and corresponding row key
   * @return Map of path to Row Key
   */
  public Map<String, Object> getDestinationToRowKey()
  {
    // Make sure we index the current TreeModel
    getFoldersTreeModel();
    return _destToRowKey;
  }
  
  // Components accessors methods
  
  public UIXTree getFoldersTreeComponent()
  {
    return _foldersTreeComponent;
  }
  
  public void setFoldersTreeComponent(UIXTree foldersTreeComponent)
  {
    _foldersTreeComponent = foldersTreeComponent;
  }
  
  // Helper Method and members
  
  // Create TreeModel for folders data binding
  private TreeModel _createFoldersTreeModel()
  {
    List treeNodesList = new ArrayList();
    
    // Ask the FileExplorerBean for the data factory
    FileExplorerDataFactory factory = _feBean.getDataFactory();
    
    // Get list of folders from Data Factory
    List<FileItem> childDirList = factory.getChildDirectoryList();
    
    // Create root FileItem
    FileItem rootDir = _createRootFileItem(childDirList);
    
    treeNodesList.add(rootDir);
    
    // create the TreeModel using ChildPropertyTreeModel
    TreeModel treeModel = new ChildPropertyTreeModel(treeNodesList, 
                                          FileItem.getChildDirPropertyName());      
    
    // Index it
    _indexTreeModel(treeModel);
    
    return treeModel;
  }
  
  private TreeModel _createNonRootedFoldersTreeModel()
  {    
    // Ask the FileExplorerBean for the data factory
    FileExplorerDataFactory factory = _feBean.getDataFactory();
    
    // Get list of folders from Data Factory
    List<FileItem> childDirList = factory.getChildDirectoryList();    
    
    // create the TreeModel using ChildPropertyTreeModel
    TreeModel treeModel = new ChildPropertyTreeModel(childDirList, 
                                          FileItem.getChildDirPropertyName());      
    
    // Index it
    _indexTreeModel(treeModel);
    
    return treeModel;
  }
      
  private FileItem _createRootFileItem(List<FileItem> childDirList)
  {
    String myFiles = 
      _feBean.getFileExplorerBundle().getString("navigator.myfiles");
    FileItem rootFileItem = new FileItem(myFiles, childDirList.toArray());

    // set fake
    rootFileItem.setFake(true);

    FileItemProperty property = rootFileItem.getProperty();
    property.setFileType(FileItemProperty.FileItemType.FOLDER);
    rootFileItem.setProperty(property);

    return rootFileItem;
  }
  
  private Object _getFirstSelectedTreePath(RowKeySet selectedPaths)
  {
    if (selectedPaths == null)
    {
      return null;
    }
    
    Iterator iter = selectedPaths.iterator();
    if (!iter.hasNext())
    {
      return null;
    }
    
    return iter.next();
  }
  
  private void _indexTreeModel(TreeModel treeModel)
  {
    if (treeModel == null)
    {
      return;
    }

    _destToRowKey = new HashMap<String, Object>();

    RowKeySet treeState = new RowKeySetTreeImpl(true);
    // RowKeySet requires access to the TreeModel for currency.
    treeState.setCollectionModel(treeModel);

    // Make the model point at the root node
    Object oldRowKey = treeModel.getRowKey();
    int oldIndex = treeModel.getRowIndex();

    try
    {
      // Adds every rowKey to this set. This method executes in constant time.
      treeState.addAll();

      // iterates through all tree state.
      Iterator selection = treeState.iterator();
      Object rowKey = null;
      while (selection.hasNext())
      {
        // Get the data from each rowkey
        rowKey = selection.next();
        treeModel.setRowKey(rowKey);
        
        FileItem data = (FileItem) treeModel.getRowData();

        // store it in the maps
        if (data.getPathName() != null && data.getPathName() != "")
        {
          _destToRowKey.put(data.getDisplayPathName(), rowKey);
        }
      }
    }
    catch (Exception e)
    {
      _destToRowKey = null;
      _LOG.warning("\nError when indexing the Folders TreeModel");
      _LOG.warning(e.getLocalizedMessage(), e);
    }
    finally
    {
      // Return the state of the model
      treeModel.setRowKey(oldRowKey);
      treeModel.setRowIndex(oldIndex);
    }
  }
  
  //Helper method to crawl ancestors of a particualr node and add them to the rowkeyset
  private void _addAncestorTreeNodesOfSelectedNode(UIXTree tree, 
                                                   Object selectedRowKey, 
                                                   RowKeySet rowkeySet)
  {
    List<Object> nodePath = 
      new ArrayList<Object>(tree.getAllAncestorContainerRowKeys(selectedRowKey));
    nodePath.add(selectedRowKey);

    int size = nodePath.size();
    for (int i = 0; i < size; i++)
    {
      Object subkey = nodePath.get(i);
      rowkeySet.add(subkey);
    }
  }
  

  private UIXTree _foldersTreeComponent;
  private TreeModel _foldersTreeModel;
  private TreeModel _nonRootedFoldersTreeModel;
  private RowKeySet _foldersTreeState;
  private Map<String, Object> _destToRowKey = null;
  
  private static final ADFLogger _LOG =
    ADFLogger.createADFLogger(FoldersNavigatorView.class);
}
