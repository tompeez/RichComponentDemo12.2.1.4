/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.data;

import java.io.File;
import java.io.Serializable;

import java.text.DateFormat;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import oracle.adf.view.rich.datatransfer.SimpleTransferData;

public class FileItem implements Serializable, Comparable<FileItem>, Cloneable
{
  public static String  PATH_SEPARATOR = File.separator;
  
  public static String getChildPropertyName()
  {
    return _FILE_LIST_CHILD_PROPERTY;
  }
  
  public static String getChildDirPropertyName()
  {
    return _DIR_LIST_CHILD_PROPERTY;
  }
  
  /**
   * Do not allow client to create empty FileItem
   */
  private FileItem()
  {
  }
  
  /**
   * Creates a new fake FileItem
   */
  public FileItem(String name, Object[] fileItems)
  {
    this(name);
    
    for (int i=0; i<fileItems.length; i++)
    {
      addToFileList((FileItem)fileItems[i]);
    }
  }
  
  /**
   * Creates a new FileItem instance from a parent abstract name and a child name string.
   */
  public FileItem(FileItem parent, String name)
  {
    this(name);
    _parentFile = parent;
    FileItemProperty property = getProperty();
    property.put(FileItemProperty.NAME_FILE_PROPERTY, name);
  }
  
  /**
   * Creates a new FileItem instance from a parent pathname string and a child pathname string.
   */
  public FileItem(String name, Map property)
  {
    this(name);
    getProperty().addNewProperties(property);
  }
  
  /**
   * Creates a new FileItem instance by name
   */
  public FileItem(String name)
  {
    FileItemProperty property = getProperty();
    property.put(FileItemProperty.NAME_FILE_PROPERTY, name);
    
    // get current date
    DateFormat dateFormat = FileItemProperty.getLastModifiedFormat();
    Date date = new java.util.Date();
    String datetime = dateFormat.format(date);
    
    // Update created and Last Modified
    property.put(FileItemProperty.LAST_MODIFIED_FILE_PROPERTY, 
      datetime);    
    property.put(FileItemProperty.CREATED_FILE_PROPERTY, 
      datetime);
    property.put(FileItemProperty.NAME_FILE_PROPERTY, name);
  }

  // Utility methods
  
  public int compareTo(FileItem o)
  {
    return (o.getPathName().compareToIgnoreCase(this.getPathName()));
  }
  
  public String toString()
  {
    return (this.getPathName());
  }
  
  @Override
  public boolean equals(Object o)
  {
    if (o == this)
    {
      return true;
    }
    else if (o instanceof FileItem)
    {
      FileItem fileItem = (FileItem)o;
      return (this.getPathName().equals(fileItem.getPathName()));
    }
    else
    {
      return false;
    }
  }
  
  @Override
  public int hashCode()
  {
    int hashCode = (getPathName() != null)
                     ? getPathName().hashCode()
                     : 23;
    hashCode *= 37;
    hashCode += getName().hashCode();
      
    return  hashCode;
  }
  
  @Override
  public Object clone() 
  {
    FileItem copyFileItem = new FileItem(getName(), getProperty());
    LinkedList<FileItem> cloneList = 
      (LinkedList<FileItem>)((LinkedList<FileItem>)getFileList()).clone();
    copyFileItem.setFileList(cloneList);    
    
    return copyFileItem;
  }
  
  // Properties accessors
  
  public FileItemProperty getProperty()
  {
    if (_property== null)
    {
      _property = new FileItemProperty();
    }
    
    return _property;
  }
  
  public void setProperty(FileItemProperty property)
  {
    _property = property;
  }
  
  public void setParentFile(FileItem parent)
  {
    _parentFile = parent;
  }

  public FileItem getParentFile()
  {
    return _parentFile;
  }

  public String getParentPathName()
  {
    if (_parentFile != null)
    {
      return _parentFile.getPathName();
    }
    else
    {
      return null;
    }
  }
  
  public String getParentDisplayPathName()
  {
    if (_parentFile != null)
    {
      return _parentFile.getDisplayPathName();
    }
    else
    {
      return null;
    }
  }
  
  public void setName(String name)
  {
    FileItemProperty property = getProperty();
    property.put(FileItemProperty.NAME_FILE_PROPERTY, name);
  }

  public String getName()
  {
    return ((String)getProperty().get(FileItemProperty.NAME_FILE_PROPERTY));
  }
  
  public String getIcon()
  {
    return getProperty().getIcon();
  }
  
  public String getType()
  {
     return FileItemProperty.getTypeEnumToStringMap().get(getProperty().getFileType());
  }
  
  public List<FileItem> getFileList()
  {
    if (_fileList == null)
    {
      _fileList = new LinkedList<FileItem>();
    }
    
    return _fileList;
  }
  
  public void setFileList(List<FileItem> fileList)
  {
    _fileList = fileList;
  }
  
  public boolean hasFileList()
  {
    return (getFileList().size() != 0);
  }
  
  public List<FileItem> getDirectoryChildList()
  {
    List<FileItem> childDirList = new LinkedList<FileItem>();

    List<FileItem> fileList = getFileList();

    // Get only directory type of FileItem
    for (FileItem fileItem: fileList)
    {
      if (fileItem.isDirectory())
      {
        childDirList.add(fileItem);
      }
    }
    
    return childDirList;
  }
  
  public void setFake(boolean fake)
  {
    this._fake = fake;
  }

  public boolean isFake()
  {
    return _fake;
  }
  
  /**
   * Return the child FileITem objects
   * @return int number of child FileItem
   */
  public int contains()
  {
    return (getFileList().size());
  }
  
  /**
   * Add new FileItem to the list of FileItem
   * @param item
   * @return the added FileItem
   */
  public FileItem addToFileList(FileItem item)
  {
    if (item == null)
    {
      return item;
    }
    
    item.setParentFile(this);
    
    getFileList().add(item);
    
    return item;
  }
  
  /**
   * Return the absolute path name for the FileItem 
   * @return String
   */
  public String getPathName ()
  {
    StringBuilder pathName = new StringBuilder();
    if (this.getParentFile() != null && 
        (this.getParentFile().isFake() == false))
    {
      pathName.append(this.getParentFile().getPathName());
      pathName.append(FileItem.PATH_SEPARATOR);
    }
    
    pathName.append(this.getName());
    
    return pathName.toString();
  }

  /**
   * Return the absolute path name for the FileItem including the fake FileItem
   * if any
   * @return String
   */
  public String getDisplayPathName ()
  {
    StringBuilder pathName = new StringBuilder();

    // Ignore fake parent
    if (this.getParentFile() != null)
    {
      pathName.append(this.getParentFile().getDisplayPathName());
      pathName.append(FileItem.PATH_SEPARATOR);
    }
    
    pathName.append(this.getName());
    
    return pathName.toString();
  }
  
  /**
   * Check if the FIleItem is type of Folder
   * @return
   */
  public boolean isDirectory()
  {
    return (getProperty().getFileType() == FileItemProperty.FileItemType.FOLDER);
  }
  
  /**
   * Remove a child FIleItem form the list
   * @param fileItem
   */
  public void removeFileItem(FileItem fileItem)
  { 
    fileItem.setParentFile(null);
    getFileList().remove(fileItem);
  }
  
  // Helper methods and member variables
  
  private FileItemProperty _property = new FileItemProperty();
  private FileItem _parentFile = null;
  private List<FileItem> _fileList = null;
  private boolean _fake = false;
  
  // Constant for retrieving file list from a given node
  private static final String _FILE_LIST_CHILD_PROPERTY = "fileList";
  private static final String _DIR_LIST_CHILD_PROPERTY = "directoryChildList";
}
