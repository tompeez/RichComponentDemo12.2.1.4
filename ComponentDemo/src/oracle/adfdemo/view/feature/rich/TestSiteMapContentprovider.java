/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.context.SiteMapContentProvider;


public class TestSiteMapContentprovider extends SiteMapContentProvider
{
  public Iterable<SiteMapContentProvider.SiteMapEntry> getSiteMapEntries(FacesContext context)
  {
    ArrayList<SiteMapContentProvider.SiteMapEntry> entries = new ArrayList<SiteMapContentProvider.SiteMapEntry>(3);
    
    entries.add(new TestSiteMapEntry("http://www.oracle.com", null, null, null));
    entries.add(new TestSiteMapEntry("http://my.test.org?param=one&param2=two", new Date(System.currentTimeMillis()), 
                                     ChangeFrequency.ALWAYS, 1.0f));
    entries.add(new TestSiteMapEntry("http://jdevadf.oracle.com", new Date(System.currentTimeMillis()), 
                                     null, 0.5f));
    return entries;
  }
  
  
  public static class TestSiteMapEntry extends SiteMapContentProvider.SiteMapEntry
  {
    TestSiteMapEntry (String loc, Date lastModified, ChangeFrequency frequency, Float priority)
    {
      _loc  = loc;
      _lastModified = lastModified;
      _frequency = frequency;
      _priority = priority;
    }
    
    @Override
    public String getLocation()
    {
      return _loc;
    }
    
    @Override
    public Date getLastModified()
    {
      return _lastModified;
    }
    
    @Override
    public ChangeFrequency getChangeFrequency()
    {
      return _frequency;
    }
    
    @Override
    public Float getPriority()
    {
      return _priority;
    }
    
    private String _loc;
    private Date _lastModified;
    private ChangeFrequency _frequency;
    private Float _priority;
  }
}
