/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

package oracle.adfdemo.view.calendar.rich.model;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 */
public final class Day implements Serializable
{

  public Day(Calendar cal)
  {
    _init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
  }

  public Day(Date date, TimeZone tz)
  {
    Calendar cal = Calendar.getInstance(tz);
    cal.setTime(date);
    _init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
  }


  public Day(int year, int month, int dayOfMonth)
  {
    _init(year, month, dayOfMonth);
  }

  private void _init(int year, int month, int dayOfMonth)
  {
    _year = year;
    _month = month;
    _dayOfMonth = dayOfMonth;
  }

  public int getYear()
  {
    return _year;
  }

  public int getMonth()
  {
    return _month;
  }

  public int getDayOfMonth()
  {
    return _dayOfMonth;
  }

  public Calendar createCalendar(TimeZone tz, Locale loc)
  {
    Calendar cal = null;

    if (tz != null && loc != null)
    {
      cal = Calendar.getInstance(tz, loc);
    }
    else if (tz == null && loc != null)
    {
      cal = Calendar.getInstance(loc);
    }
    else if (loc == null && tz != null)
    {
      cal = Calendar.getInstance(tz);
    }
    else
    {
      cal = Calendar.getInstance();
    }

    updateCalendar(cal);
    return cal;
  }

  public Date createDate(TimeZone tz)
  {
    Calendar cal = createCalendar(tz, null);
    return cal.getTime();
  }

  public void updateCalendar(Calendar cal)
  {
    cal.set(Calendar.YEAR, _year);
    cal.set(Calendar.MONTH, _month);
    cal.set(Calendar.DAY_OF_MONTH, _dayOfMonth);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
  }
  
  @Override
  public boolean equals(Object object)
  {       
    if (this == object)
    {
      return true;
    }
    if (!(object instanceof Day))
    {
      return false;
    }
    
    final Day other = (Day) object;
    
    if ((_year != other._year) ||
        (_month != other._month) ||
        (_dayOfMonth != other._dayOfMonth))
    {
      return false;
    }
    
    return true;
  }

  @Override
  public int hashCode()
  {
    final int PRIME = 37;
    int result = 1;
    result = PRIME * result + _year;
    result = PRIME * result + _month;
    result = PRIME * result + _dayOfMonth;
    
    return result;
  }
  
  private int _year;
  private int _month;
  private int _dayOfMonth;

  private static class DayComparator
    implements Comparator<Day>
  {
    private DayComparator()
    {
    }

    public int compare(Day d1,
                       Day d2)
    {

      // TODO handle null?
      int d1DayOfMonth = d1.getDayOfMonth();
      int d1Month = d1.getMonth();
      int d1Year = d1.getYear();
      int d2DayOfMonth = d2.getDayOfMonth();
      int d2Month = d2.getMonth();
      int d2Year = d2.getYear();

      if (d1Year > d2Year)
        return 1;
      else if (d1Year < d2Year)
        return -1;

      // the years are the same, so check the month
      if (d1Month > d2Month)
        return 1;
      else if (d1Month < d2Month)
        return -1;

      // the months are the same, so check the day
      if (d1DayOfMonth > d2DayOfMonth)
        return 1;
      else if (d1DayOfMonth < d2DayOfMonth)
        return -1;

      // everything is the same, return 0
      return 0;
    }

    public static DayComparator getInstance()
    {
      return _INSTANCE;
    }

    private static final DayComparator _INSTANCE = new DayComparator();
  }

  public static Comparator<Day> getDayComparator()
  {
    return DayComparator.getInstance();
  }
}
