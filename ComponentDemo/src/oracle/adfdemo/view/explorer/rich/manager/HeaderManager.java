/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.manager;

import java.util.Map;
import java.util.ResourceBundle;

import java.util.Stack;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adfdemo.view.components.rich.DemoComponentSkin;
import oracle.adfdemo.view.explorer.rich.bean.FileExplorerBean;
import oracle.adfdemo.view.explorer.rich.bean.NewFileItemBean;

import oracle.adfdemo.view.explorer.rich.data.FileItem;

import oracle.adfdemo.view.explorer.rich.data.FileItemProperty;

import oracle.adfdemo.view.explorer.rich.navigator.SearchNavigatorView;

import org.apache.myfaces.trinidad.component.UIXPanel;
import org.apache.myfaces.trinidad.component.UIXTree;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.LaunchEvent;
import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.model.TreeModel;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class HeaderManager
{
  public HeaderManager(FileExplorerBean feBean)
  {
    _feBean = feBean;
  }

  /**
   * Refresh the header' models and components
   */
  public void refresh()
  {
    // Add toolbox as parital targets
    RequestContext adfContext = RequestContext.getCurrentInstance();
    adfContext.addPartialTarget(_headerToolbox);
  }

  /**
   * Create new File by using te dialog framework
   * @return the navigation string started with "dialog:"
   */
  public String createNewFile()
  {
    // Add current selected path in ADFFAcesContext Page Flow Scope
    AdfFacesContext.getCurrentInstance().getPageFlowScope().put("displayedDirectory",
                                                                _feBean.getSelectedDirectory());

    // Show new File dialog popup
    return "dialog:createNewFile";
  }

  /**
   * Handle creation of new Directory
   * @param actionEvent
   */
  public void createNewDir(ActionEvent actionEvent)
  {
    // Create new FileItem of type DIRECTORY and
    // ask FileExplorerBean to add it to current selected folder
    String newDirName =
      _feBean.getFileExplorerBundle().getString("navigator.newfolder");
    FileItem newFileItem = new FileItem(newDirName);

    // set type to "Folder"
    FileItemProperty newFileItemProperty = newFileItem.getProperty();
    newFileItemProperty.setFileType(FileItemProperty.FileItemType.FOLDER);

    // Add new FileItem
    _feBean.getDataFactory().addNewFileItem(newFileItem,
                                            NavigatorManager.removeRootFileName(_feBean.getSelectedDirectory()));

    // Refresh navigators, header, and content views via their managers
    _feBean.refreshAllManagers();
  }

  /**
   * Add parameters to new File dialog
   */
  public void addParametersToNewFile(LaunchEvent launchEvent)
  {
    // Add current selected path in ADFFAcesContext Page Flow Scope
    launchEvent.getDialogParameters().put("displayedDirectory",
                                          _feBean.getSelectedDirectory());
  }

  /**
   * This is the return listener from new file dialog popup
   */
  public void returnFromNewFile(ReturnEvent returnEvent)
  {
    String returnValue = (String) returnEvent.getReturnValue();

    // Check if user click Cancel
    if (NewFileItemBean.CANCEL_NEW_FILE_ITEM.equals(returnValue) == true)
    {
      return;
    }

    // Get the new file properties
    Map properties = returnEvent.getReturnParameters();

    // create new FileItem instance
    String newFileName =
      (String) properties.get(FileItemProperty.NAME_FILE_PROPERTY);
    FileItem newFileItem = new FileItem(newFileName, properties);

    // Add new FileItem
    _feBean.getDataFactory().addNewFileItem(newFileItem,
                                            NavigatorManager.removeRootFileName(_feBean.getSelectedDirectory()));

    // refresh navigators, header, and content views via their managers
    _feBean.refreshAllManagers();
  }

  /**
   * Should disabled delete command
   * @return if delete command should be disabled
   */
  public boolean getDisabledDeleteItem()
  {
    return (_feBean.getLastSelectedFileItem() == null);
  }

  /**
   * Delete last selected item
   * @param actionEvent
   */
  public void deleteItem(ActionEvent actionEvent)
  {
    // Ask for selected item from FileExplorerBean
    FileItem selectedFileItem = _feBean.getLastSelectedFileItem();
    if (selectedFileItem == null)
    {
      return;
    }
    else
    {
      // Check if we are deleting a folder
      if (selectedFileItem.isDirectory())
      {
        _feBean.setSelectedDirectory(null);
      }
    }

    this.deleteSelectedFileItem(selectedFileItem);
  }


  /**
   * Delete last selected item
   * @param dialogEvent
   */
  public void deleteItemFromDialog(DialogEvent dialogEvent)
  {
    // Check if we click yes
    if (dialogEvent.getOutcome() != DialogEvent.Outcome.yes)
    {
      return;
    }

    // Ask for selected item from FileExplorerBean
    FileItem selectedFileItem = _feBean.getLastSelectedFileItem();
    if (selectedFileItem == null)
    {
      return;
    }
    else
    {
      // Check if we are deleting a folder
      if (selectedFileItem.isDirectory())
      {
        _feBean.setSelectedDirectory(null);
      }
    }

    this.deleteSelectedFileItem(selectedFileItem);
  }

  /**
   * Delete last selected item helper method that takes FileItem as input
   * argument
   * @param selectedFileItem
   */
  public void deleteSelectedFileItem(FileItem selectedFileItem)
  {
    // Ask data factory to update the file item list
    _feBean.getDataFactory().deleteSelectedFileItem(selectedFileItem);

    // Refresh navigators, header, and content views via their managers
    _feBean.refreshAllManagers();
  }

  /**
   * Should disable move item to parent
   * @return Should disable move item to parent
   */
  public boolean getDisabledMoveToParent()
  {
    return false;
  }

  /**
   * Move selected item to parent directory if possible
   * @param actionEvent
   */
  public void moveSelectedItemToParent(ActionEvent actionEvent)
  {
    try
    {
      // Ask for selected item
      FileItem selectedFileItem = _feBean.getLastSelectedFileItem();

      if (selectedFileItem == null)
      {
        return;
      }

      // Get the parent
      FileItem parentDir = selectedFileItem.getParentFile();
      assert (parentDir != null);

      // Get parent target path
      String targetPath =
        NavigatorManager.removeRootFileName(parentDir.getParentFile().getPathName());

      this.moveFileItemTo(selectedFileItem, targetPath);
    }
    catch (Throwable t)
    {
      _LOG.severe(t);
    }

    // refresh navigators, header, and content views via their managers
    _feBean.refreshAllManagers();
  }

  /**
   * Move selected item to particular target path
   * @param selectedFileItem
   * @param targetPath
   */
  public void moveFileItemTo(FileItem selectedFileItem, String targetPath)
  {
    // Delete from current parent item
    this.deleteSelectedFileItem(selectedFileItem);

    // Change the parent FileItem of selected item
    _feBean.getDataFactory().addNewFileItem(selectedFileItem, targetPath);
  }

  /**
   * Should disable copy command
   * @return
   */
  public boolean getDisabledCopyItem()
  {
    return (_feBean.getLastSelectedFileItem() == null);
  }

  /**
   * Copy selected item
   * @param actionEvent
   */
  public void copySelectedItem(ActionEvent actionEvent)
  {
    // Ask for selected item
    FileItem selectedFileItem = _feBean.getLastSelectedFileItem();
    if (selectedFileItem == null)
    {
      return;
    }

    FileItem cloneItem = (FileItem) selectedFileItem.clone();

    // Store reference of it to the PageFlowScope
    AdfFacesContext.getCurrentInstance().getPageFlowScope().put("copiedFileItem",
                                                                cloneItem);

  }

  /**
   * Should disabled paste command
   * @return
   */
  public boolean getDisabledPasteItem()
  {
    FileItem copiedItem =
      (FileItem) AdfFacesContext.getCurrentInstance().getPageFlowScope().get("copiedFileItem");

    return (copiedItem == null);
  }

  /**
   * Paste copied item to current displayed location
   * @param actionEvent
   */
  public void pasteItemToCurrentLocation(ActionEvent actionEvent)
  {
    // Restore reference of it to the PageFlowScope
    FileItem copiedFileItem =
      (FileItem) AdfFacesContext.getCurrentInstance().getPageFlowScope().get("copiedFileItem");

    // Check if we have File Item to be pasted
    if (copiedFileItem == null)
    {
      return;
    }

    // Add it to current selected directory
    _feBean.getDataFactory().addNewFileItem(copiedFileItem,
                                            NavigatorManager.removeRootFileName(_feBean.getSelectedDirectory()));

    // Reset copied item in page flow scope
    AdfFacesContext.getCurrentInstance().getPageFlowScope().put("copiedFileItem",
                                                                null);

    // Refresh all managers
    _feBean.refreshAllManagers();
  }

  /**
   * Handle user click on refresh page
   * @return
   */
  public String doRefreshPage()
  {
    // Perform a redirect to ensure that the entire page gets reloaded from the server:
    DemoComponentSkin.reloadThePage();
    return null;
  }

  /**
   * Should disable navigatoe to previous file
   * @return
   */
  public boolean getDisabledNavBack()
  {
    if (_navPathBackStack.empty() == true)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  /**
   * Handle nav back action event
   * @param actionEvent
   */
  public void handleNavBack(ActionEvent actionEvent)
  {
    _updateHistoryStacks(_navPathBackStack, _navPathForwardStack);
  }

  /**
   *  Should disable navigatoe to next file
   * @return
   */
  public boolean getDisabledNavForward()
  {
    if (_navPathForwardStack.empty() == true)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  /**
   * Handle nav forward action event
   * @param actionEvent
   */
  public void handleNavForward(ActionEvent actionEvent)
  {
    _updateHistoryStacks(_navPathForwardStack, _navPathBackStack);
  }

  /**
   * Should disable show up one folder command
   * @return
   */
  public boolean getDisabledUpOneFolder()
  {
    if (_feBean.getSelectedDirectory() == null)
    {
      return true;
    }
    else
    {
      // Ask Navigator Manager for selected item
      FileItem selectedFileItem = _feBean.getLastSelectedFileItem();

      if (selectedFileItem == null)
      {
        return true;
      }

      return false;
    }
  }

  /**
   * Handle up one folder action
   * @param actionEvent
   */
  public void handleUpOneFolder(ActionEvent actionEvent)
  {
    UIXTree folderTree =
      _feBean.getNavigatorManager().getFoldersNavigator().getFoldersTreeComponent();
    Object selectedPath =
      _feBean.getNavigatorManager().getFoldersNavigator().getFirstSelectedTreePath();

    if (selectedPath != null)
    {
      TreeModel model =
        _feBean.getNavigatorManager().getFoldersNavigator().getFoldersTreeModel();
      Object oldRowKey = model.getRowKey();
      try
      {
        model.setRowKey(selectedPath);
        Object parentRowKey = model.getContainerRowKey();
        if (parentRowKey != null)
        {
          folderTree.getSelectedRowKeys().clear();
          folderTree.getSelectedRowKeys().add(parentRowKey);

          // This is an example of how to force a single attribute
          // to repaint. It assumes that the client has an optimized
          // setter for "selectedRowKeys" of tree.
          FacesContext context = FacesContext.getCurrentInstance();
          ExtendedRenderKitService erks =
            Service.getRenderKitService(context,
                                        ExtendedRenderKitService.class);
          String clientRowKey =
            folderTree.getClientRowKeyManager().getClientRowKey(context,
                                                                folderTree,
                                                                parentRowKey);
          String clientId = folderTree.getClientId(context);

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
      _feBean.getNavigatorManager().getFoldersNavigator().openSelectedFolder();
    }

  }

  /**
   * Validate path display
   * @param context
   * @param toValidate
   * @param value
   */
  public void validatePathDisplay(FacesContext context,
                                  UIComponent toValidate, Object value)
  {
    String path = (String) value;

    Object selectedRowKey =
      _feBean.getNavigatorManager().getFoldersNavigator().getDestinationToRowKey().get(path);
    if (selectedRowKey == null)
    {
      // check if the current location is the default
      ResourceBundle fileExplorerBundle = _feBean.getFileExplorerBundle();
      String noneLocation = fileExplorerBundle.getString("global.none");

      if ((path != null) && path.equals(noneLocation))
      {
        return;
      }

      context.addMessage(null,
                         new FacesMessage(FacesMessage.SEVERITY_WARN, _feBean.getFileExplorerBundle().getString("error.invalidpathsummary"),
                                          _feBean.getFileExplorerBundle().getString("error.invalidpathdetail")));
    }
  }

  /**
   * Returns the path of the currently selected directory modified for display purposes.
   * @return a String showing the current locaton of selected node in the file tree
   */
  public String getDisplayedDirectory()
  {
    String selectedDir = _feBean.getSelectedDirectory();

    if (selectedDir == null)
    {
      ResourceBundle fileExplorerBundle = _feBean.getFileExplorerBundle();
      selectedDir = fileExplorerBundle.getString("global.none");
    }
    return selectedDir;
  }

  /**
   * Update selected directory property
   * @param displayedDirectory
   */
  public void setDisplayedDirectory(String displayedDirectory)
  {
    // Check for empty or null string
    if (displayedDirectory == null || "".equals(displayedDirectory))
    {
      return;
    }

    // Ask Folders Navigator to auto sync disclosed and selected nodes
    _feBean.getNavigatorManager().getFoldersNavigator().autoSyncFoldersTree(displayedDirectory);

    // Ask folders tree to be refreshed
    _feBean.getNavigatorManager().getFoldersNavigator().renderFoldersTree();

    // Get desired FileItem
    FileItem desiredFileItem =
      _feBean.getDataFactory().getPathToFileItem().get(NavigatorManager.removeRootFileName(displayedDirectory));
    if (desiredFileItem == null)
    {
      return;
    }

    // Set desiredFileItem
    _feBean.setLastSelectedFileItem(desiredFileItem);

    // Update FileExplorerBean selected directory property
    _feBean.setSelectedDirectory(displayedDirectory);
  }

  /**
   * Return the nav back stack
   * @return
   */
  public Stack getNavPathBackStack()
  {
    return _navPathBackStack;
  }

  /**
   * Return nav forward stack
   * @return
   */
  public Stack getNavPathForwardStack()
  {
    return _navPathForwardStack;
  }

  /**
   * Handle QueryEvent generated from quick search
   * @param event
   */
  public void executeQuickSearch(ActionEvent event)
  {
    // Get Search Navigator
    SearchNavigatorView searchNavigator =
      _feBean.getNavigatorManager().getSearchNavigator();

    // Ask Search Navigator to show the search area
    searchNavigator.getShowDetailItem().setDisclosed(true);

    // Set the File Type search criteria
    if (_quickSearchCriteriaType != null)
    {
      searchNavigator.setSearchCriteriaType(_quickSearchCriteriaType);
    }

    // Set the File Item name criteria
    if (_quickSearchInput != null && "".equals(_quickSearchInput) == false)
    {
      searchNavigator.setSearchCriteriaName(_quickSearchInput);
    }

    // Then ask Search Navigator to execute Search
    searchNavigator.doRealSearchForFileItem();

    // refresh Search Navigator
    _feBean.getNavigatorManager().refresh();
  }

  /**
   * Set the File Type for quick search
   * @param quickSearchCriteriaType
   */
  public void setQuickSearchCriteriaType(String quickSearchCriteriaType)
  {
    this._quickSearchCriteriaType = quickSearchCriteriaType;
  }

  /**
   * Returns the File Type for quick search
   * @return
   */
  public String getQuickSearchCriteriaType()
  {
    return this._quickSearchCriteriaType;
  }

  /**
   * Set the quick search input text
   * @param quickSearchInput
   */
  public void setQuickSearchInput(String quickSearchInput)
  {
    this._quickSearchInput = quickSearchInput;
  }

  /**
   * Get the quick search input text
   * @return
   */
  public String getQuickSearchInput()
  {
    return _quickSearchInput;
  }

  // JSF Components accessors

  public void setPathDisplay(UIComponent pathDisplay)
  {
    _pathDisplay = pathDisplay;
  }

  public UIComponent getPathDisplay()
  {
    return _pathDisplay;
  }

  public void setHeaderToolbox(UIXPanel headerToolbox)
  {
    this._headerToolbox = headerToolbox;
  }

  public UIXPanel getHeaderToolbox()
  {
    return _headerToolbox;
  }


  // Helper methods and member variables

  private void _updateHistoryStacks(Stack fromStack, Stack toStack)
  {
    // Get target path from the source stack
    String pathName = (String) fromStack.pop();

    // Get current topic file
    String currentDirPath = _feBean.getSelectedDirectory();

    toStack.push(currentDirPath);

    // Save the current path from stack
    _feBean.setSelectedDirectory(pathName);
  }

  private FileExplorerBean _feBean = null;
  private UIComponent _pathDisplay = null;
  private UIXPanel _headerToolbox = null;
  private Stack _navPathForwardStack = new Stack();
  private Stack _navPathBackStack = new Stack();
  private String _quickSearchCriteriaType;
  private String _quickSearchInput;

  private static final ADFLogger _LOG =
    ADFLogger.createADFLogger(HeaderManager.class);
}
