/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.data;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import oracle.adfdemo.view.explorer.rich.bean.FileExplorerBean;

public class FileItemProperty extends HashMap
{
  /**
   * Possible types of FileItem
   */
  public enum FileItemType
  {
    DOCFILE,
    FOLDER,
    HTMLFILE,
    IMAGEFILE,
    JSCRIPTFILE,
    MISCFILE,
    PDFFILE,
    TXTFILE,
    XLSFILE,
    XMLFILE,
    ZIPFILE
  }

  // Constants for file properties
  public static final String NAME_FILE_PROPERTY           = "name";
  public static final String CREATED_FILE_PROPERTY        = "created";
  public static final String READ_ONLY_FILE_PROPERTY      = "readOnly";
  public static final String HIDDEN_FILE_PROPERTY         = "hidden";
  public static final String LAST_MODIFIED_FILE_PROPERTY  = "lastModified";
  public static final String SIZE_FILE_PROPERTY           = "size";
  public static final String ICON_FILE_PROPERTY           = "icon";
  public static final String SHARED_FILE_PROPERTY         = "shared";
  public static final String TYPE_FILE_PROPERTY           = "type";
  public static final String DESCRIPTION_FILE_PROPERTY    = "description";
  public static final String KEYWORDS_FILE_PROPERTY       = "keywords";
  
  /**
   * Default constructor
   */
  public FileItemProperty()
  {
  }
  
  // Maps accessors
  
  public static Map<FileItemType, String> getFileItemTypeToIconMap()
  {
    return _fileItemTypeToIconMap;
  }
  
  public static Map<FileItemType, String> getTypeEnumToStringMap()
  {
    return _typeEnumToStringMap;
  }

  public static Map<String, FileItemProperty.FileItemType> getFileExtensionToTypeEnumMap()
  {
    return _fileExtensionToTypeEnumMap;
  }
  
  /**
   * Return the Date format for FileItemProperty
   * @return the SimpleDateFormat for Date
   */
  public static SimpleDateFormat getLastModifiedFormat()
  {
    return _LAST_MODIFIED_FORMAT;
  }
  
  public void addNewProperties(Map mapProperties)
  {
    Iterator keyValuePairs = mapProperties.entrySet().iterator();
    for (int i = 0; i < mapProperties.size(); i++)
    {
      Map.Entry entry = (Map.Entry) keyValuePairs.next();
      Object key = entry.getKey();
      Object value = entry.getValue();
      
      this.put(key, value);
    }
  }
  
  // Properties accessors
  
  public String getIcon()
  {
    String icon = (String)this.get(ICON_FILE_PROPERTY);
      
    if (icon == null)
    {
      // check file type
      icon = _fileItemTypeToIconMap.get(getFileType());
      if (icon == null)
      {
        icon = "/fileExplorer/images/file_ena.png";
      }
    }
    
    return icon;
  }
  
  public FileItemType getFileType()
  {
    String type = (String)get(TYPE_FILE_PROPERTY);
    
    FileItemType fileItemType = null;
    
    try
    {
      if(type == null)
      {
        String name = (String)this.get(NAME_FILE_PROPERTY);
        assert(name != null);
        
        int extIndex = name.lastIndexOf(".");
        
        if (extIndex != -1)
        {
          String extension = name.substring(name.lastIndexOf(".")+1);    
          fileItemType = _fileExtensionToTypeEnumMap.get(extension);
          
          if(fileItemType == null)
          {
            fileItemType = FileItemType.MISCFILE;
          }
        }
        else
        {
          fileItemType = FileItemType.MISCFILE;
        }
      }
      else
      {
        fileItemType = FileItemType.valueOf(type);
      }
    }
    catch (Exception e)
    {
      fileItemType = FileItemType.MISCFILE;
    }
    
    return fileItemType;
  }
  
  public void setFileType(FileItemType fileItemType)
  {
    this.put(TYPE_FILE_PROPERTY, fileItemType.toString());
  }
  
  public void setLastModifed(long lastModified)
  {
    String lastModifiedString =  _getFormattedDate(lastModified);
    this.put(LAST_MODIFIED_FILE_PROPERTY, lastModifiedString);
  }
  
  // Helper methods and member variables
  
  // Return a usable formatted date
  private String _getFormattedDate(long numeric)
  {
    Date d;
    d = new Date(numeric);
    return _LAST_MODIFIED_FORMAT.format(d);
  }
  
  // Return the readable string based on byte length
  private Long _getFileSize(long length)
  {
    long size = 1;
    size = length/1024;
    if (size < 1)
      size = 1;

    return new Long(size);
  }
  
  // Initialize maps
  
  private static Map<FileItemType, String> _initializeFileItemTypeToIconMap()
  {
    Map<FileItemType, String> fileItemTypeToIcon = new HashMap<FileItemType, String>();
    
    fileItemTypeToIcon.put(FileItemType.DOCFILE, "/fileExplorer/images/docfile_qualifier.png");
    fileItemTypeToIcon.put(FileItemType.FOLDER, "/fileExplorer/images/folderclosed_qualifier.png");
    fileItemTypeToIcon.put(FileItemType.HTMLFILE, "/fileExplorer/images/htmlfile_qualifier.png");
    fileItemTypeToIcon.put(FileItemType.IMAGEFILE, "/fileExplorer/images/bitmapfile_qualifier.png");
    fileItemTypeToIcon.put(FileItemType.JSCRIPTFILE, "/fileExplorer/images/javascriptfile_qualifier.png");
    fileItemTypeToIcon.put(FileItemType.MISCFILE, "/fileExplorer/images/defaultfile_qualifier.png");
    fileItemTypeToIcon.put(FileItemType.PDFFILE, "/fileExplorer/images/pdffile_qualifier.png");
    fileItemTypeToIcon.put(FileItemType.TXTFILE, "/fileExplorer/images/txtfile_qualifier.png");
    fileItemTypeToIcon.put(FileItemType.XLSFILE, "/fileExplorer/images/xlsfile_qualifier.png");
    fileItemTypeToIcon.put(FileItemType.XMLFILE, "/fileExplorer/images/xmlfile_qualifier.png");
    fileItemTypeToIcon.put(FileItemType.ZIPFILE, "/fileExplorer/images/zipfile_qualifier.png");
    
    return fileItemTypeToIcon;
  }
  
  private static Map<FileItemProperty.FileItemType, String> _initalizeTypeEnumToStringMap()
  {
    Map<FileItemType, String> typeEnumToStringMap = 
        new HashMap<FileItemType, String>();
    
    ResourceBundle fileExplorerBundle = 
      ResourceBundle.getBundle(FileExplorerBean.FILE_EXPLORER_BUNDLE_NAME, 
                               FacesContext.getCurrentInstance().getViewRoot().getLocale());
    
    typeEnumToStringMap.put(FileItemType.DOCFILE, 
                             fileExplorerBundle.getString("filetype.doc"));
    typeEnumToStringMap.put(FileItemType.FOLDER, 
                             fileExplorerBundle.getString("filetype.folder"));
    typeEnumToStringMap.put(FileItemType.HTMLFILE, 
                             fileExplorerBundle.getString("filetype.html"));
    typeEnumToStringMap.put(FileItemType.IMAGEFILE, 
                             fileExplorerBundle.getString("filetype.img"));
    typeEnumToStringMap.put(FileItemType.JSCRIPTFILE, 
                             fileExplorerBundle.getString("filetype.jscript"));
    typeEnumToStringMap.put(FileItemType.MISCFILE, 
                             fileExplorerBundle.getString("filetype.misc"));
    typeEnumToStringMap.put(FileItemType.PDFFILE, 
                             fileExplorerBundle.getString("filetype.pdf"));
    typeEnumToStringMap.put(FileItemType.TXTFILE, 
                             fileExplorerBundle.getString("filetype.txt"));
    typeEnumToStringMap.put(FileItemType.XLSFILE, 
                             fileExplorerBundle.getString("filetype.xls"));
    typeEnumToStringMap.put(FileItemType.XMLFILE, 
                             fileExplorerBundle.getString("filetype.xml"));
    typeEnumToStringMap.put(FileItemType.ZIPFILE, 
                             fileExplorerBundle.getString("filetype.zip"));
    
    return typeEnumToStringMap;
  }
  
  private static Map<String, FileItemProperty.FileItemType> _initializeFileExtensionToTypeEnumMap()
  {
    Map<String, FileItemType> fileExtensionToTypeEnumMap = 
      new HashMap<String, FileItemType>();
    
    fileExtensionToTypeEnumMap.put("doc",
                                    FileItemType.DOCFILE);
    fileExtensionToTypeEnumMap.put("html",
                                    FileItemType.HTMLFILE);
    fileExtensionToTypeEnumMap.put("jpg",
                                    FileItemType.IMAGEFILE);
    fileExtensionToTypeEnumMap.put("png",
                                    FileItemType.IMAGEFILE);
    fileExtensionToTypeEnumMap.put("js",
                                    FileItemType.JSCRIPTFILE);
    fileExtensionToTypeEnumMap.put("pdf",
                                    FileItemType.PDFFILE);
    fileExtensionToTypeEnumMap.put("txt",
                                    FileItemType.TXTFILE);
    fileExtensionToTypeEnumMap.put("xls",
                                    FileItemType.XLSFILE);
    fileExtensionToTypeEnumMap.put("txt",
                                    FileItemType.TXTFILE);
    fileExtensionToTypeEnumMap.put("zip",
                                    FileItemType.ZIPFILE);
    fileExtensionToTypeEnumMap.put("tar",
                                    FileItemType.ZIPFILE);    
    return fileExtensionToTypeEnumMap;
  }
  
  // Date format for last modifed property of a file
  private static final SimpleDateFormat _LAST_MODIFIED_FORMAT =
    new SimpleDateFormat("MM/dd/yyyy h:mm a");
  
  // Maps
  
  private static Map<FileItemType, String> _fileItemTypeToIconMap = 
    _initializeFileItemTypeToIconMap();
  
  private static Map<FileItemType, String> _typeEnumToStringMap = 
    _initalizeTypeEnumToStringMap();
  
  private static Map<String, FileItemType> _fileExtensionToTypeEnumMap = 
    _initializeFileExtensionToTypeEnumMap();
}
