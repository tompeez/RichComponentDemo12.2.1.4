/*
** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved.
**
**345678901234567890123456789012345678901234567890123456789012345678901234567890
*/
package oracle.adfdemo.view.util.rich;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;

/*
** A simple demo bean for the date picker components demos.
*/

public class DateDemoUtils
{
  // Generate a List of Date objects representing the dates that
  // should be disabled for the given range.
  public static List <Date> getDateList(
    FacesContext context,
    Calendar base,
    Date rangeStart,
    Date rangeEnd
    )
  {
    ArrayList<Date> dList = new ArrayList();
    base = (Calendar) base.clone();
    base.setTime(rangeStart);

    // add holidays for the very first month
    base.setTime(rangeStart);
    _addMonthDays(dList, base);

    // now, to make things easier, start every following month on the first
    base.set(Calendar.DATE, 1);
    int currMonth = base.get(Calendar.MONTH) + 1;
    base.set(Calendar.MONTH, currMonth);
    // If we just set the month to December+1, Calendar is smart enough to
    // increment us to January of next year.
    if (currMonth > Calendar.DECEMBER)
      currMonth = Calendar.JANUARY;

    // loop until we get to the end of the range
    Date currTime = base.getTime();
    while (currTime.before(rangeEnd) || currTime.equals(rangeEnd))
    {
      // add in holidays for this month
      _addMonthDays(dList, base);
      // and move on to the first of next month
      base.set(Calendar.MONTH, ++currMonth);
      if (currMonth > Calendar.DECEMBER)
        currMonth = Calendar.JANUARY;
      base.set(Calendar.DATE, 1);
      currTime = base.getTime();
    }
    return dList;
  }

  public static Set getWeekend()
  {
    Set wkend = new java.util.HashSet();
    wkend.add("Sun");
    wkend.add("sat");
    return wkend;
  }

  private static void _addMonthDays(
      List<Date> dList,
      Calendar curr)
  {
    int weekday = curr.get(Calendar.DAY_OF_WEEK);
    int date = curr.get(Calendar.DATE);
    // Just disable a couple of holidays
    // Yes, I know this is a grossly U.S.-centric demo. Sorry!
    // Other Monday holidays would be done along the same lines
    // as the few demoed here.
    int month = curr.get(Calendar.MONTH);
    if (month == 0)
    {
      // New Years
      curr.set(Calendar.DATE, _followingMonday(weekday, date, 1));
      dList.add(curr.getTime());
    }
    else if (month == 4)
    {
      // Memorial Day
      // The Memorial Day holiday is the last Monday in May

      // translate to a Monday
      date += Calendar.MONDAY - weekday;

      // Move up to the last Monday
      while (date < 25)
        date += 7;

      curr.set(Calendar.DATE, date);
      dList.add(curr.getTime());
    }
    else if (month == 6)
    {
      // July 4th
      curr.set(Calendar.DATE, _followingMonday(weekday, date, 4));
      dList.add(curr.getTime());
    }
    else if (month == 8)
    {
      // Labor day
      // The Labor Day holiday is the first Monday in September
      // translate to a Monday
      date += Calendar.MONDAY - weekday;

      if (date < 1)
        date += 7;

      // Move back to the first Monday
      while (date > 7)
        date -= 7;

      curr.set(Calendar.DATE, date);
      dList.add(curr.getTime());
    }
    else if (month == 10)
    {
      // Veterans/Armistice day
      curr.set(Calendar.DATE, 11);
      dList.add(curr.getTime());

      // In the U.S. Thanksgiving is the 4th Thursday in November

      // translate to a Thursday
      date += Calendar.THURSDAY - weekday;

      // if already past Thanksgiving, go back to it
      if (date > 28) date -= 7;

      // slide up to Thanksgiving
      while (date < 22)
        date += 7;

      curr.set(Calendar.DATE, date);
      dList.add(curr.getTime());
      // what the heck, call the day after a holiday too!
      curr.set(Calendar.DATE, date + 1);
      dList.add(curr.getTime());
    }
    else if (month == 11)
    {
      // Christmas
      curr.set(Calendar.DATE, _followingMonday(weekday, date, 25));
      dList.add(curr.getTime());
    }
  }

  // This is not a complete Monday holiday method, it only moves the holiday to
  // the next Monday if the holiday falls on a weekend. This is only useful for
  // holidays that are tied to a particular date (e.g. New Years, July 4th,
  // Christmas).
  // A complete method is left as an exercise for the interested reader.
  private static int _followingMonday(
    int weekday,
    int date,
    int target)
  {
    // translate the weekday to target date
    weekday = weekday - (date - target);

    while (weekday > 7)
      weekday -= 7;

    while (weekday <= 0)
      weekday += 7;

    if (Calendar.SUNDAY == weekday)
      target++;
    else if (Calendar.SATURDAY == weekday)
      target += 2;

    return target;
  }
}

