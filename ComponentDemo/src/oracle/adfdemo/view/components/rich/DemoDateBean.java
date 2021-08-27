/*
** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved.
**
**345678901234567890123456789012345678901234567890123456789012345678901234567890
*/
package oracle.adfdemo.view.components.rich;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.util.Set;

import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.model.DateListProvider;

import oracle.adfdemo.view.util.rich.DateDemoUtils;


/*
** A simple demo bean for the date picker components demos.
*/

public class DemoDateBean implements DateListProvider
{
  // Generate a List of Date objects representing the dates that
  // should be disabled for the given range.
  public List <Date> getDateList(
    FacesContext context,
    Calendar base,
    Date rangeStart,
    Date rangeEnd
    )
  {
    return DateDemoUtils.getDateList(context, base, rangeStart, rangeEnd);
  }
  
  public Set getWeekend()
  {
    Set wkend = new java.util.HashSet();
    wkend.add("Sun");
    wkend.add("sat");
    return wkend;
  }
  
}

