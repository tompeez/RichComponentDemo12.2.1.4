/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    This class is used to generate Java object proxies for Fiel Explorer data.

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import java.util.Map;

import oracle.adfdemo.view.explorer.rich.bean.FileExplorerBean;


public class FileExplorerDataFactory
{
  private FileExplorerDataFactory()
  {
  }
  
  public FileExplorerDataFactory(FileExplorerBean feBean)
  {
    _feBean = feBean;
  }
  
   // Public methods
  
  /**
   * Returns a List representing the Files in a directory.
   * Only directories will be returned, so this method should be used
   * to get the files to be displayed in a level of a navigation tree.
   * @return List of FileItem
   */
  public List<FileItem> getChildDirectoryList()
  {
    return getFileItemList();
  }
  
  /**
   * Returns a List of Maps representing the Files in a directory.
   * @return List of FileItem
   */
  public List<FileItem> getChildFileList(String pathName)
  {
    List<FileItem> fileList = java.util.Collections.emptyList();
    
    getFileItemList();

    if (pathName != null)
    { 
      // Get FileItem from the map
      FileItem fileItem = _pathToFileItem.get(pathName);
      
      if (fileItem != null)
      {
        fileList = fileItem.getFileList();
      }
    }
    
    return fileList;
  }
  
  /**
   * Return the List of FileItem as main data source
   * @return List of FileItem
   */
  public List<FileItem> getFileItemList()
  {
    if (_fileItemList == null)
    {
      _fileItemList = _createChildFileList();  
    }
    
    return _fileItemList;
  }
  
  /**
   * Add new FileItem to desired path and index the file list again
   * @param newFileItem
   * @param currentPath
   */
  public void addNewFileItem(FileItem newFileItem, String currentPath)
  {
    FileItem parentFileItemFromIndex = null;
    if (currentPath != null && (currentPath.equals("") == false))
    {
      parentFileItemFromIndex
        = _pathToFileItem.get(currentPath);
      assert(parentFileItemFromIndex != null);
     
      parentFileItemFromIndex.addToFileList(newFileItem);   
    }
    else
    {
      _fileItemList.add(newFileItem);
    }
    
    _indexFileItemList();
  }
  
  /**
   * Delete a FileItem and index the file list again
   * @param deleteFileItem
   */
  public void deleteSelectedFileItem(FileItem deleteFileItem)
  {
    FileItem parentFileItemFromIndex = 
      _pathToFileItem.get(deleteFileItem.getParentFile().getPathName());
    
    if (parentFileItemFromIndex == null)
    {
      // we are the root
      _fileItemList.remove(deleteFileItem);
    }
    else
    {
      parentFileItemFromIndex.removeFileItem(deleteFileItem);    
    }
    
    _indexFileItemList();
  }
  
  /**
   * Returns Absolute path to FileItem map
   * @return a Map of absolute path-fileItem pairs
   */
  public Map<String, FileItem> getPathToFileItem()
  {
    return _pathToFileItem;
  }

  /**
   * Returns file item name to List of file items
   * @return a Map of name to list of file items with that same name pairs
   */
  public Map<String, List<FileItem>> getNameToFileItems()
  {
    return _nameToFileItems;
  }
  
  
  // Helper methods and member variables
  
  private void _indexFileItemList()
  {
    _nameToFileItems = new HashMap<String, List<FileItem>>();
    _pathToFileItem = new HashMap<String, FileItem>();
    
    Object[] fileItemsArray = _fileItemList.toArray();
    for (int i=0; i<fileItemsArray.length; i++)
    {
      _indexFileItemList((FileItem)fileItemsArray[i]);
    }
  }
  
  private void _indexFileItemList(FileItem parent)
  {
    if (parent.getName() != null && 
        ("".equalsIgnoreCase(parent.getName()) != true))
    {
      List<FileItem> fileItems = 
        _nameToFileItems.get(parent.getName());
      if (fileItems == null)
      {
        fileItems = new ArrayList<FileItem>();
      }
      fileItems.add(parent);
      
      _nameToFileItems.put(parent.getName().toLowerCase(), fileItems);
    }
      
    if (parent.getPathName() != null && 
        parent.getPathName().equalsIgnoreCase("") != true)
    {
      _pathToFileItem.put(parent.getPathName(), parent);
    }
    
    if (!parent.hasFileList())
    {
      return;
    }

    // Iterate through the children:     
    Object[] children = parent.getFileList().toArray();
    for (int i = 0; i < children.length; i++)
    {
      // Set the url for the child
      _indexFileItemList((FileItem)children[i]);
    }
  }

  // TODO: MODIFY THE CODE WHEN ACTUAL DATA AVAILABLE
  private List<FileItem> _createChildFileList()
  {
    _fileItemList = new LinkedList<FileItem>();
    
    // Add just chiild files
    for (int i=0; i<10; i++)
    {
      FileItem folder1 = new FileItem("Folder"+ i);
      FileItemProperty newProp = folder1.getProperty();
      newProp.setFileType(FileItemProperty.FileItemType.FOLDER);

      FileItem file1 = new FileItem("File" + i + ".doc");
      FileItemProperty newProp2 = file1.getProperty();
      newProp2.setFileType(FileItemProperty.FileItemType.DOCFILE);
      newProp2.put(FileItemProperty.SIZE_FILE_PROPERTY, "10");
      folder1.addToFileList(file1);
      
      file1 = new FileItem("File" + i + ".html");
      newProp2 = file1.getProperty();
      newProp2.setFileType(FileItemProperty.FileItemType.HTMLFILE);
      newProp2.put(FileItemProperty.SIZE_FILE_PROPERTY, "10");
      folder1.addToFileList(file1);
      
      file1 = new FileItem("File" + i + ".pdf");
      newProp2 = file1.getProperty();
      newProp2.setFileType(FileItemProperty.FileItemType.PDFFILE);
      newProp2.put(FileItemProperty.SIZE_FILE_PROPERTY, "10");
      folder1.addToFileList(file1);
      
      file1 = new FileItem("File" + i + ".xls");
      newProp2 = file1.getProperty();
      newProp2.setFileType(FileItemProperty.FileItemType.XLSFILE);
      newProp2.put(FileItemProperty.SIZE_FILE_PROPERTY, "10");
      folder1.addToFileList(file1);
      
      _fileItemList.add(folder1);
    }
    
    // Add child folders and files
    for (int j=10; j<21; j++)
    {
      FileItem folder2 = new FileItem("Folder"+j); 
      FileItemProperty newProp2 = folder2.getProperty();
      newProp2.setFileType(FileItemProperty.FileItemType.FOLDER);
      folder2.setProperty(newProp2);
      
      FileItem file2 = new FileItem("File" + j + ".doc");
      newProp2 = file2.getProperty();
      newProp2.setFileType(FileItemProperty.FileItemType.DOCFILE);
      newProp2.put(FileItemProperty.SIZE_FILE_PROPERTY, "10");
      folder2.addToFileList(file2);
      
      file2 = new FileItem("File" + j + ".js");
      newProp2 = file2.getProperty();
      newProp2.setFileType(FileItemProperty.FileItemType.JSCRIPTFILE);
      newProp2.put(FileItemProperty.SIZE_FILE_PROPERTY, "10");
      folder2.addToFileList(file2);
      
      for (int k=0; k<3;  k++)
      {
        FileItem folder3 = new FileItem("Subfolder"+j + "-" + k);
        newProp2 = folder3.getProperty();
        newProp2.setFileType(FileItemProperty.FileItemType.FOLDER);
        folder2.addToFileList(folder3);
        
        file2 = new FileItem("File" + j + "-" + k + ".jpg");
        newProp2 = file2.getProperty();
        newProp2.setFileType(FileItemProperty.FileItemType.IMAGEFILE);
        newProp2.put(FileItemProperty.SIZE_FILE_PROPERTY, "100");
        folder3.addToFileList(file2);
      }
      
      _fileItemList.add(folder2);
    }
    
    // Add just files
    for (int k=21; k<26; k++)
    {      
      FileItem file3 = new FileItem("File" + k + ".txt");
      FileItemProperty newProp = file3.getProperty();
      newProp.setFileType(FileItemProperty.FileItemType.TXTFILE);
      newProp.put(FileItemProperty.SIZE_FILE_PROPERTY, "50");
      
      _fileItemList.add(file3);
    }
    
    _indexFileItemList();
    
    return _fileItemList;
  }
  
  // Member variables  
  private FileExplorerBean _feBean = null;
  private List<FileItem> _fileItemList = null;
  private HashMap<String, FileItem> _pathToFileItem = null;
  private HashMap<String, List<FileItem>> _nameToFileItems = null;
}
