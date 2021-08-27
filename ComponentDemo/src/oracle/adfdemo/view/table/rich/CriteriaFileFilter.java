/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import java.io.FileFilter;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CriteriaFileFilter implements FileFilter
{
  public CriteriaFileFilter(
    String criteriaName,
    String criteriaWhen,
    String criteriaSize)
  {
    if (criteriaName != null && criteriaName.length() > 0)
    {
      String regex = criteriaName;

      // escape periods and interpret asterisks
      regex = regex.replaceAll("\\Q.\\E", "\\\\Q.\\\\E");
      regex = regex.replaceAll("\\Q*\\E", ".*");

      // add wildcard prefix
      if (!regex.startsWith(".*"))
        regex = ".*" + regex;

      // add wildcard suffix
      if (!regex.endsWith(".*"))
        regex = regex + ".*";

      // compile pattern
      _pattern = Pattern.compile(regex);
    }
    else
    {
      _pattern = null;
    }
    
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
  }
  
  public boolean accept(
    File file)
  {
    return acceptName(file) &&
           acceptSize(file) &&
           acceptWhen(file);
  }
  
  protected boolean acceptName(
    File file)
  {
    if (_pattern != null)
    {
      Matcher matcher = _pattern.matcher(file.getName());
      return matcher.matches();
    }
    
    return true;
  }
  
  protected boolean acceptSize(
    File file)
  {
    long sizeBytes = file.length();
    return (_minSizeBytes <= sizeBytes && sizeBytes < _maxSizeBytes);
  }

  protected boolean acceptWhen(
    File file)
  {
    long lastModifiedMillis = file.lastModified();
    long sinceLastModified = System.currentTimeMillis() - lastModifiedMillis;
    return (sinceLastModified < _maxSinceLastModified);
  }

  private final Pattern _pattern;
  private final long    _maxSinceLastModified;
  private final long    _maxSizeBytes;
  private final long    _minSizeBytes;
}
