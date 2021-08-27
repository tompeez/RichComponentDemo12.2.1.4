/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    This class is used as filter for searching FileItem

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.data;

import java.text.ParseException;

import java.util.Date;

/**
 * Copied from CriteriaFileFilter class
 */
public class CriteriaFileItemFilter
{
  private CriteriaFileItemFilter()
  {
  }

  public CriteriaFileItemFilter(String criteriaWhen, 
                                String criteriaSize, 
                                String criteriaType,
                                Date criteriaFromDate)
  {          
    if ("week".equals(criteriaWhen))
    {
      _maxSinceLastModified = 1000L * 60L * 60L * 24L * 7L;
    }
    else if ("month".equals(criteriaWhen))
    {
      _maxSinceLastModified = 1000L * 60L * 60L * 24L * 31L;
    }
    else if ("year".equals(criteriaWhen))
    {
      _maxSinceLastModified = 1000L * 60L * 60L * 24L * 365L;
    }
    else if ("specify".equals(criteriaWhen))
    {
      _maxSinceLastModified = criteriaFromDate.getTime();
    }
    else
    {
      _maxSinceLastModified = Long.MAX_VALUE;
    }

    if ("small".equals(criteriaSize))
    {
      _minSizeBytes = 0L;
      _maxSizeBytes = (long)Math.pow(2, 10);
    }
    else if ("medium".equals(criteriaSize))
    {
      _minSizeBytes = 0L;
      _maxSizeBytes = (long)Math.pow(2, 20);
    }
    else if ("large".equals(criteriaSize))
    {
      _minSizeBytes = (long)Math.pow(2, 20);
      _maxSizeBytes = Long.MAX_VALUE;
    }
    else
    {
      _minSizeBytes = 0L;
      _maxSizeBytes = Long.MAX_VALUE;
    }
    
    _type = criteriaType;
  }
  
  public boolean accept(
    FileItem file)
  {
    return (acceptSize(file) && acceptWhen(file) && 
            acceptType(file));
  }

  // Helper methods and members
  
  protected boolean acceptSize(
    FileItem file)
  {
    String sizeString = 
      (String)file.getProperty().get(FileItemProperty.SIZE_FILE_PROPERTY);
    
    if (sizeString == null)
    {
      return true;  
    }
    
    long sizeBytes = Long.parseLong(sizeString);
    return (_minSizeBytes <= sizeBytes && sizeBytes < _maxSizeBytes);
  }

  protected boolean acceptWhen(
    FileItem file)
  {
    String lastModifedString =  
      (String)file.getProperty().get(FileItemProperty.LAST_MODIFIED_FILE_PROPERTY);
    if (lastModifedString == null ||
       "".equalsIgnoreCase(lastModifedString) == true)
    {
      return true;
    }
   
    try
    {
      Date lastModifiedDate = 
        FileItemProperty.getLastModifiedFormat().parse(lastModifedString);
      long lastModifiedMillis = lastModifiedDate.getTime();
      long sinceLastModified = 
        System.currentTimeMillis() - lastModifiedMillis;
      
      return (sinceLastModified < _maxSinceLastModified);
    }
    catch (ParseException pe)
    {
      pe.printStackTrace();
      return false;
    }
  }
  
  protected boolean acceptType(
    FileItem file)
  {
    if (file.getType() == null )
    {
      return true;
    }
    else if (_type != null)
    {
      if (file.getProperty().getFileType().toString().equalsIgnoreCase(_type) == false)
      {
        return false;
      }      
    }    
    return true;    
  }

  private long    _maxSinceLastModified;
  private long    _maxSizeBytes;
  private long    _minSizeBytes;
  private String  _type;
}
