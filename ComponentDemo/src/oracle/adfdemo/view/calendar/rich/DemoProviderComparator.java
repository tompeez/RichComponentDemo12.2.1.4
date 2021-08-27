/** Copyright (c) 2008, 2018, Oracle and/or its affiliates. All rights reserved. */

package oracle.adfdemo.view.calendar.rich;

import java.io.Serializable;

import oracle.adf.view.rich.model.CalendarProvider;

import oracle.adf.view.rich.util.ProviderComparator;

public class DemoProviderComparator 
  extends ProviderComparator
  implements Serializable
{
  public DemoProviderComparator ()
  {
    
  }

  @Override
  public int compare(CalendarProvider calendarProvider, CalendarProvider calendarProvider2)
  {
    return calendarProvider.getDisplayName().compareTo(calendarProvider2.getDisplayName());
  }
}
