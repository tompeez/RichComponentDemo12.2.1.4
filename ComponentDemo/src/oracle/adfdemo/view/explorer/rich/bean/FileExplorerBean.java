/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    This class is used as backing bean for the File Explorer demo that provides model for 
    navigating through a directory folder hierarchy, and a List to represent the files in any 
    given directory.
    This class will delegate works to other classes as necessary

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.bean;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adfdemo.view.explorer.rich.data.FileExplorerDataFactory;
import oracle.adfdemo.view.explorer.rich.data.FileItem;
import oracle.adfdemo.view.explorer.rich.manager.ContentViewManager;
import oracle.adfdemo.view.explorer.rich.manager.HeaderManager;
import oracle.adfdemo.view.explorer.rich.manager.NavigatorManager;

import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class FileExplorerBean
{
  public static final String FILE_EXPLORER_BUNDLE_NAME = 
    "oracle.adfdemo.view.explorer.rich.resource.FileExplorerBundle";
  
  public FileExplorerBean()
  {
    _initFileExplorerBean();
  }

  /**
   * Get the HeaderManager
   * @return HeaderManager
   */  
  public HeaderManager getHeaderManager()
  {
    return _headerManager;
  }

  /**
   * Get the Navigator Manager
   * @return NavigatorManager
   */
  public NavigatorManager getNavigatorManager()
  {
    return _navManager;
  }

  /**
   * Get the content view manager
   * @return ContentViewManager
   */
  public ContentViewManager getContentViewManager()
  {
    return _cvManager;
  }

  /**
   * Get the data factory for File Explorer 
   * @return FileExplorerDataFactory
   */
  public FileExplorerDataFactory getDataFactory()
  {
    return _datafactory;
  }
  
  /**
   * Show drop event information in java script alert
   * @param de
   */
   public void displayDropInfo(DropEvent de)
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
  
 /**
   * Log the drop event information in log file
   * @param de
   */
  public void logDropInfo(DropEvent de)
  {
    FacesContext context =  FacesContext.getCurrentInstance();
    
    StringBuilder sb = new StringBuilder();
    sb.append("Source Component:");
    sb.append(de.getDragComponent().getClientId(context));
    sb.append("\\n");
    sb.append("Source Keys:");
    sb.append(de.getTransferable().getData(DataFlavor.ROW_KEY_SET_FLAVOR));
    sb.append("\\n");
    sb.append("Target Component:").append(de.getDropComponent().getClientId(context));
    sb.append("\\n");
    sb.append("Target RowKey:").append(de.getDropSite());
    sb.append("\\n");
    
    _LOG.log(sb.toString());
  }
  
  /**
   * Return the File Explorer resource bundle
   */
  public ResourceBundle getFileExplorerBundle()
  {
    return _fileExplorerBundle;
  }

  /**
   * Returns the path of the currently selected directory.
   */
  public String getSelectedDirectory()
  {    
    return _selectedDirectory;
  }

  /**
   * Sets the path of the currently selected directory.
   */
  public void setSelectedDirectory(String selectedDirectory)
  {
    // Update history back
    if (_selectedDirectory != null)
    {
      getHeaderManager().getNavPathBackStack().add(_selectedDirectory);
    }
    
    // Update current selected directory path 
    _selectedDirectory = selectedDirectory;
    
    // reset table selection
    this.getContentViewManager().resetTableSelection();
      
    // refresh content view manager
    this.getContentViewManager().refresh();
    
    // refresh header manager
    this.getHeaderManager().refresh();
  }
  
  /**
   * Set the last selected fileItem from either Navigator or 
   * Content View manager
   */
  public void setLastSelectedFileItem(FileItem lastSelectedFileItem)
  {
    this._lastSelectedFileItem = lastSelectedFileItem;
  }

  // Returns the last selected fileItem from either Navigator or Content View manager
  public FileItem getLastSelectedFileItem()
  {
    return _lastSelectedFileItem;
  }
  
  /**
   * Ask all managers to refresh themselves
   */
  public void refreshAllManagers()
  {
    // refresh navigators, header, and content views via their managers
    getNavigatorManager().refresh();
    getHeaderManager().refresh();
    getContentViewManager().refresh();
  }
  
  /**
   * Launch the property page for last selected file/folder
   */
  public String launchProperties()
  { 
    // Add the last selected FileItem to the PageFlowScope
    AdfFacesContext.getCurrentInstance().
      getPageFlowScope().put("lastSelectedFileItem", 
                             this.getLastSelectedFileItem());
    
    // Add current selected path in ADFFAcesContext PageFlowScope
    AdfFacesContext.getCurrentInstance().
      getPageFlowScope().put("displayedDirectory", 
                             this.getSelectedDirectory());
    
    return "dialog:fileItemProperties";
  }

  /**
   * Show FileItem properties event listener
   */
  public void returnFromProperties(ReturnEvent event)
  {
    // log  it
    _LOG.info("\nCome Back From File Item Properties Dialog");
  }
  
  // Helper methods and member variables
  
  // Set the header manager
  private void _setHeaderManager(HeaderManager manager)
  {
    _headerManager = manager;
  }

  // Set the Navigator Manager
  private void _setNavigatorManager(NavigatorManager manager)
  {
    _navManager = manager;
  }

  // Set the content view manager
  private void _setContentViewManager(ContentViewManager manager)
  {
    _cvManager = manager;
  }

  // Set the data factory for File Explorer 
  private void _setDataFactory(FileExplorerDataFactory datafactory)
  {
    _datafactory = datafactory;
  }
  
  // Initialize the class
  private void _initFileExplorerBean()
  {
    _setDataFactory(new FileExplorerDataFactory(this));
    _setHeaderManager(new HeaderManager(this));
    _setNavigatorManager(new NavigatorManager(this));
    _setContentViewManager(new ContentViewManager(this));
  }
  
  // Member variables
  
  private HeaderManager _headerManager = null;
  private NavigatorManager _navManager = null;
  private ContentViewManager _cvManager = null;
  
  private FileExplorerDataFactory _datafactory = null;

  private String _selectedDirectory = null;
  private FileItem _lastSelectedFileItem = null;
  
  ResourceBundle _fileExplorerBundle = 
  ResourceBundle.getBundle(FILE_EXPLORER_BUNDLE_NAME, 
              FacesContext.getCurrentInstance().getViewRoot().getLocale());
  
  private static final ADFLogger _LOG =
    ADFLogger.createADFLogger(FileExplorerBean.class);
}

