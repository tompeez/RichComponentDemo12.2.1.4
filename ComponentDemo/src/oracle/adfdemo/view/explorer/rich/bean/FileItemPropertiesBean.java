/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.explorer.rich.bean;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adfdemo.view.explorer.rich.data.FileItem;

import oracle.adfdemo.view.explorer.rich.data.FileItemProperty;

import org.apache.myfaces.trinidad.context.RequestContext;

public class FileItemPropertiesBean
{
  public FileItemPropertiesBean()
  {
    // Get the last selected FileItem from the PageFlowScope
    _lastSelectedFileItem = (FileItem)AdfFacesContext.getCurrentInstance().
      getPageFlowScope().get("lastSelectedFileItem");
    
    // Get current selected path from ADFFAcesContext PageFlowScope
    _displayedDir = (String)AdfFacesContext.getCurrentInstance().
      getPageFlowScope().get("displayedDirectory");
    
  }
  
  public String getIcon()
  {
    return _lastSelectedFileItem.getIcon();
  }
  
  public String getName()
  {
    return _lastSelectedFileItem.getName();
  }
  
  public String getCreated()
  {
    return (String)_lastSelectedFileItem.getProperty().get(FileItemProperty.CREATED_FILE_PROPERTY);
  }

  public String getLastModified()
  {
    return (String)_lastSelectedFileItem.getProperty().get(FileItemProperty.LAST_MODIFIED_FILE_PROPERTY);
  }

  public String getSize()
  {
    return (String)_lastSelectedFileItem.getProperty().get(FileItemProperty.SIZE_FILE_PROPERTY);
  }

  public String getType()
  {
    return _lastSelectedFileItem.getType();
  }
  
  public String getLocation()
  {
    return _displayedDir;
  }
  
  public int getContains()
  {
    return _lastSelectedFileItem.getFileList().size();
  }

  public String getDescription()
  {
    return (String)_lastSelectedFileItem.getProperty().get(FileItemProperty.DESCRIPTION_FILE_PROPERTY);
  }

  public String getKeywords()
  {
    return (String)_lastSelectedFileItem.getProperty().get(FileItemProperty.KEYWORDS_FILE_PROPERTY);
  }
  
  public boolean isReadOnly()
  {
    String readOnly = (String)_lastSelectedFileItem.getProperty().get(FileItemProperty.READ_ONLY_FILE_PROPERTY);
    return Boolean.getBoolean(readOnly);
  }

  public boolean isHidden()
  {
    String readOnly = (String)_lastSelectedFileItem.getProperty().get(FileItemProperty.HIDDEN_FILE_PROPERTY);
    return Boolean.getBoolean(readOnly);
  }

  public boolean isShared()
  {
    String readOnly = (String)_lastSelectedFileItem.getProperty().get(FileItemProperty.SHARED_FILE_PROPERTY);
    return Boolean.getBoolean(readOnly);
  }
  
  public void closePropertiesDialog()
  {
    // Close popup dialog
    RequestContext.getCurrentInstance().returnFromDialog(null, null);
  }
  
  private FileItem _lastSelectedFileItem;
  private String _displayedDir;
}
