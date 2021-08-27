/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.explorer.rich.bean;

import java.text.DateFormat;
import java.util.Map;
import java.util.Date;

import java.util.HashMap;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adfdemo.view.explorer.rich.data.FileItemProperty;

import org.apache.myfaces.trinidad.context.RequestContext;

public class NewFileItemBean
{
  public static String SAVE_NEW_FILE_ITEM = "Save New File Item";
  public static String CANCEL_NEW_FILE_ITEM = "Cancel New File Item";
  
  public NewFileItemBean()
  {
  }
  
  public void cancelNewFile()
  {
    RequestContext.getCurrentInstance().returnFromDialog(CANCEL_NEW_FILE_ITEM, 
                                                         null);
  }
  
  public void saveNewFile()
  {
    // Update the properties map
    Map propertiesMap = new HashMap();
    
    propertiesMap.put(FileItemProperty.NAME_FILE_PROPERTY, 
      this.getName());
    propertiesMap.put(FileItemProperty.READ_ONLY_FILE_PROPERTY, 
      Boolean.toString(isReadOnly()));
    propertiesMap.put(FileItemProperty.HIDDEN_FILE_PROPERTY, 
      Boolean.toString(this.isHidden()));
      
    DateFormat dateFormat = FileItemProperty.getLastModifiedFormat();
    Date date = new java.util.Date();
    String datetime = dateFormat.format(date);
    propertiesMap.put(FileItemProperty.LAST_MODIFIED_FILE_PROPERTY, 
      datetime);
    
    propertiesMap.put(FileItemProperty.CREATED_FILE_PROPERTY, 
      datetime);
    
    propertiesMap.put(FileItemProperty.SIZE_FILE_PROPERTY, 
      Integer.toString(getSize()));
    
    propertiesMap.put(FileItemProperty.ICON_FILE_PROPERTY, 
    null);
    
    propertiesMap.put(FileItemProperty.SHARED_FILE_PROPERTY, 
    Boolean.toString(this.isShared()));
    
    propertiesMap.put(FileItemProperty.TYPE_FILE_PROPERTY, 
     this.getType());
      
    propertiesMap.put(FileItemProperty.DESCRIPTION_FILE_PROPERTY, 
    this.getDescription());
    propertiesMap.put(FileItemProperty.KEYWORDS_FILE_PROPERTY, 
    this.getKeywords());
    
    RequestContext.getCurrentInstance().returnFromDialog(SAVE_NEW_FILE_ITEM, 
                                                         propertiesMap);
  }
  
  // Public accessors 

  public void setName(String name)
  {
    this._name = name;
    
    // set file type
    String type = getType();
    this.setType(type);
  }

  public String getName()
  {
    if (_name == null)
    {
      ResourceBundle fileExplorerBundle = 
        ResourceBundle.getBundle(FileExplorerBean.FILE_EXPLORER_BUNDLE_NAME, 
                  FacesContext.getCurrentInstance().getViewRoot().getLocale());
      
      _name = fileExplorerBundle.getString("navigator.newfile");    
    }
    return _name;
  }

  public void setDescription(String description)
  {
    this._description = description;
  }

  public String getDescription()
  {
    return _description;
  }

  public void setKeywords(String keywords)
  {
    this._keywords = keywords;
  }

  public String getKeywords()
  {
    return _keywords;
  }

  public void setShared(boolean shared)
  {
    this._shared = shared;
  }

  public boolean isShared()
  {
    return _shared;
  }

  public void setReadOnly(boolean readOnly)
  {
    this._readOnly = readOnly;
  }

  public boolean isReadOnly()
  {
    return _readOnly;
  }

  public void setHidden(boolean hidden)
  {
    this._hidden = hidden;
  }

  public boolean isHidden()
  {
    return _hidden;
  }
  
  public void setSize(int size)
  {
    this._size = size;
  }

  public int getSize()
  {
    return _size;
  }

  public String getType()
  {
    if (_type == null)
    {
      String fileExt = getName().substring(getName().lastIndexOf(".")+1);
      if (fileExt != null)
      {
        fileExt = fileExt.toLowerCase();
        Map fileExtensionToTypeEnumMap = FileItemProperty.getFileExtensionToTypeEnumMap();
        FileItemProperty.FileItemType fileItemTypeClass = 
          (FileItemProperty.FileItemType)fileExtensionToTypeEnumMap.get(fileExt);
        
        _type = FileItemProperty.FileItemType.MISCFILE.toString();
          
        if (fileItemTypeClass != null)
        {
          _type = fileItemTypeClass.toString();
        }
      }
      else
      {
        _type = FileItemProperty.FileItemType.MISCFILE.toString();
      }      
    }
    
    return _type;
  }
  
  public void setType(String type)
  {
    this._type = type;
  }

  public String getCurrentFilePath()
  {
    if (_currentFilePath == null)
    {
      _currentFilePath = (String)AdfFacesContext.getCurrentInstance().
      getPageFlowScope().get("displayedDirectory");
    }
    return _currentFilePath;
  }
  
  // Helper methods and member variables

  private String _name = null;
  private String _description = "";
  private String _keywords = "";
  private boolean _shared = false;
  private boolean _readOnly = false;
  private boolean _hidden = false;
  private int _size = 10;
  private String _type = null;
  private String _currentFilePath = null;
}
