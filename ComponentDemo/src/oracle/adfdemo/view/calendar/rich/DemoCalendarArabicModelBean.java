
/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.calendar.rich;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 * The demo model will provide sample CalendarActivities that shows:
 * <ul>
 *
 * <li>the calendar should have a reasonable number of activities when
 * initially viewed.</li>
 * <li>Need to show: all day events; all day events that span multiple days;
 * timed events; timed events that span multiple days;
 * day/week view - conflicting events;
 * month view - enough events on a single to day to trigger overflow.</li>
 * <li>support changing timezones for testing - add a drop down to change
 * the tz.</li>
 * <li>support of multiple providers</li>
 * </ul>
 */
public class DemoCalendarArabicModelBean extends DemoCalendarModelBean  implements Serializable
{

  // Methods to get an activity title - DemoCalendarArabicModelBean override
  // these to provide full RTL strings
  protected String getAllDayActivityTitle(String titleAddition, long numDays,
                                        Calendar endCalendar)
  {
    String title = "\u0648 " + getActivityIdCounter ();
    
    if (numDays > 1)
    { 
      
      // for days > 1 we show on which day this activity ends. We subtract one day off since we
      // calculate an all day activity that ends on the 27th as being at midnight of the 27th, 
      // and thus it doesn't actually show up on the 27th. It 'ends' on the 26th, just before midnight.
      // therefore the text says the 26th instead of the 27th.
      endCalendar.add(Calendar.DAY_OF_YEAR, -1);
      title = title + " \u0648 \u0648 \u0648 \u0648 \u0648 \u0648 " + 
              DateFormat.getDateInstance(DateFormat.SHORT, _araLocale).format(endCalendar.getTime());
      endCalendar.add(Calendar.DAY_OF_YEAR, 1);

    }

    // Ignore titleAddition, it just adds characters and we are testing for 
    // Bidi handling
//    if (titleAddition != null)
//      title = title + " " + titleAddition;
    return title;
  }
  
  protected String getDailyRecurringDurationTitle(int i)
  { 
    // TODO 
    return super.getDailyRecurringDurationTitle(i);
  }
  
  protected String getDurationActivityTitle(String titleAddition, int numDays, Calendar endCalendar)
  { 
    // TODO  
    return super.getDurationActivityTitle(titleAddition, numDays, endCalendar);
  }
  
  protected String getDailyRecurringActivityTitle(int i)
  {
    return "\u0646 " + getActivityIdCounter() +
                      " \u0646 \u0646 \u0646 \u0646 " + (i+1);
  }
  protected String getMonthlyRecurringActivityTitle(int i)
  {
    return "\u062a " + getActivityIdCounter() +
                      " \u062a \u062a \u062a \u062a " + (i+1);
  }

  protected String getTimedActivityTitle(String titleAddition,
                                       Calendar startCalendar,
                                       Calendar endCalendar)
  {
    String title = "\u0642 " + getActivityIdCounter();
    

    int c1DayOfYear = startCalendar.get(Calendar.DAY_OF_YEAR);
    int c1Year = startCalendar.get(Calendar.YEAR);
    int c2DayOfYear = endCalendar.get(Calendar.DAY_OF_YEAR);
    int c2Year = endCalendar.get(Calendar.YEAR);

    if (c1DayOfYear != c2DayOfYear || c1Year != c2Year)
    {  
      DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, _araLocale);
      title = title + " \u0642 \u0642 \u0642 \u0642 \u0642 " + 
              formatter.format(endCalendar.getTime()) + " " +
              endCalendar.getTimeZone().getDisplayName(true, TimeZone.SHORT); 
    }
    
    // Ignore titleAddition, it just adds characters and we are testing for 
    // Bidi handling
    //    if (titleAddition != null)
    //      title = title + " " + titleAddition;
    return title;
  }


  protected String getWeeklyRecurringActivityTitle(int i)
  {
    return "\u0644 " + getActivityIdCounter() +
                      " \u0644 \u0644 \u0644 \u0644 \u0644 " + (i+1);
  }
  
  private static final Locale _araLocale = new Locale("ar");
}

