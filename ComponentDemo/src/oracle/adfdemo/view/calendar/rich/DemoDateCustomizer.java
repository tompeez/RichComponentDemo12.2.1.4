/** Copyright (c) 2008, 2019, Oracle and/or its affiliates. All rights reserved. */

package oracle.adfdemo.view.calendar.rich;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.el.ELContext;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.data.RichCalendar;
import oracle.adf.view.rich.util.DateCustomizer;

import oracle.adfdemo.view.calendar.rich.model.Day;

public class DemoDateCustomizer 
  extends DateCustomizer
  implements Serializable
{
  public DemoDateCustomizer ()
  {
    Calendar cal = DemoCalendarModelBean.getCalendarInstanceForToday(null, null);
    cal.add(Calendar.DAY_OF_YEAR, 20);   

    _targetDay = new Day(cal);

    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();

    _veCal = facesContext.getApplication().getExpressionFactory().createValueExpression(
                                                    elContext,
                                                   _DATE_VAR,
                                                   Calendar.class);
    _veStat = facesContext.getApplication().getExpressionFactory().createValueExpression(
                                                    elContext,
                                                    _DATE_VARSTATUS,
                                                    Map.class);

  }
  
  @Override
  public String format(Date date, String key, Locale locale, TimeZone tz)
  {
    
    if ("af|calendar::month-grid-cell-header-misc".equals(key) ||
        "af|calendar::day-header-row-misc".equals (key) || 
        "af|calendar::week-header-cell-misc".equals (key) ||
        "af|calendar::list-day-of-week-column-misc".equals (key))
    {
      Calendar cal = Calendar.getInstance(tz, locale);
      cal.setTime(date);
      
      int firstDayOfWeek = cal.getFirstDayOfWeek();
      int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
      
      if (firstDayOfWeek == dayOfWeek)
        return "Week " + cal.get(Calendar.WEEK_OF_YEAR);
      
      Calendar today = Calendar.getInstance (tz, locale);
      if ((today.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) && 
          (today.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) &&
          (today.get(Calendar.DATE) == cal.get(Calendar.DATE)))
        return "Carpe diem";
      
      return null;
      
    }
    else if ("af|calendar::month-grid-cell-header-day-link".equals(key) || 
             "af|calendar::week-header-day-link".equals(key) ||
             "af|calendar::day-header-row".equals(key) ||
             "af|calendar::list-day-of-week-column".equals(key) ||
             "af|calendar::list-day-of-month-link".equals(key))
    {
      
      Day day = new Day(date, tz);
      long targetMillis = _targetDay.createDate(tz).getTime();
      long dateMillis = day.createDate(tz).getTime();
      long diff = dateMillis - targetMillis;
      
      //24 hours * 60 minutes * 60seconds * 1000 = 86400000;
      // if days isn't a long, then the return value is an int, which can be too small. For example
      // if days is the int 28, then the return value is negative, 
      // but if it's the long 28, then it's fine
      // This is demo code only, this code doesn't work quite right for daylight savings.
      long days =  diff/86400000;
     
      if (days > 0)
        return "+" + String.valueOf(days);
          
      String val = String.valueOf(days);
      
      if ("af|calendar::week-header-day-link".equals(key))
        val = val + "(W)";
      else if ("af|calendar::day-header-row".equals(key))
        val = val + "(D)";
      else if ("af|calendar::list-day-of-week-column".equals(key))
        val = val + "(L1)";
      else if ("af|calendar::list-day-of-month-link".equals(key))
        val = val + "(L2)";
        
      return val;
    }
    else if ("af|calendar::toolbar-display-range:day".equals(key))
    {
      DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, locale);
      format.setTimeZone(tz);
      
      return "custom DAY: " + format.format(date);
    }
    else if ("af|calendar::toolbar-display-range:month".equals(key))
    {
      String monthYearPattern = "MMMM yyyy";
      DateFormat format = new SimpleDateFormat(monthYearPattern, locale);
      format.setTimeZone(tz);
      return "custom MONTH: " + format.format(date);
    }
    else if ("af|calendar::time-text:day".equals(key) ||
             "af|calendar::time-text:week".equals(key)) 
    {
      Calendar cal = Calendar.getInstance(tz, locale);
      cal.setTime(date);
      
      return "Hour " + (cal.get(Calendar.HOUR_OF_DAY) + 1);
    }
    else if ("af|calendar::activity-time-text:day".equals(key) ||
             "af|calendar::activity-time-text:week".equals(key) ||
             "af|calendar::activity-time-text:month".equals(key) ||
             "af|calendar::activity-time-text:list".equals(key))
    {
      DateFormat dateformat = DateFormat.getTimeInstance(DateFormat.MEDIUM, locale);
      dateformat.setTimeZone(tz);

      return dateformat.format(date);
    }
    else if ("af|calendar::activity-duration-text:day".equals(key) ||
             "af|calendar::activity-duration-text:week".equals(key) ||
             "af|calendar::activity-duration-text:month".equals(key) || 
             "af|calendar::activity-duration-text:list".equals(key))
    {
      DateFormat dateformat = new SimpleDateFormat("(HH:mm)", locale);
      dateformat.setTimeZone(tz);

      return dateformat.format(date);
    }
    else if ("af|calendar::all-day-activity-prev:day".equals(key) ||
             "af|calendar::all-day-activity-prev:week".equals(key) ||
             "af|calendar::all-day-activity-prev:month".equals(key) ||
             "af|calendar::all-day-activity-prev:list".equals(key)) 
    {
      DateFormat dateformat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
      dateformat.setTimeZone(tz);

      return dateformat.format(date) + " (All Day/Duration)";
    }
    else if ("af|calendar::all-day-activity-next:day".equals(key) ||
             "af|calendar::all-day-activity-next:week".equals(key) ||
             "af|calendar::all-day-activity-next:month".equals(key) ||
             "af|calendar::all-day-activity-next:list".equals(key)) 
    {
      DateFormat dateformat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
      dateformat.setTimeZone(tz);

      return dateformat.format(date)  + " (All Day/Duration)";
    }
    else if ("af|calendar::multi-day-activity-prev:day".equals(key) ||
             "af|calendar::multi-day-activity-prev:week".equals(key) ||
             "af|calendar::multi-day-activity-prev:month".equals(key) ||
             "af|calendar::multi-day-activity-prev:list".equals(key)) 
    {
      DateFormat dateTimeformat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
      dateTimeformat.setTimeZone(tz);

      return dateTimeformat.format(date) + " (Multi Day)";
    }
    else if ("af|calendar::multi-day-activity-next:day".equals(key) ||
             "af|calendar::multi-day-activity-next:week".equals(key) ||
             "af|calendar::multi-day-activity-next:month".equals(key) ||
             "af|calendar::multi-day-activity-next:list".equals(key)) 
    {
      DateFormat dateTimeformat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
      dateTimeformat.setTimeZone(tz);

      return dateTimeformat.format(date) + " (Multi Day)";
    }
    else if ("af|calendar::month-overflow-link".equals(key))
    {
      return "+{0} appointments";
    }
    
    return null;      
  }

  @Override
  public String formatRange(Date startDate, Date endDate, String key, Locale locale, TimeZone tz)
  {
    if ("af|calendar::toolbar-display-range:week".equals(key))
    {
      DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, locale);
      format.setTimeZone(tz);
      
      return "custom WEEK: " + format.format(startDate)+ " - " + format.format(endDate);
    }
    else if ("af|calendar::toolbar-display-range:list".equals(key))
    {
      DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, locale);
      format.setTimeZone(tz);
      
      return "custom LIST: " + format.format(startDate)+ " - " + format.format(endDate);
    }
    else if ("af|calendar::activity-time-text:list".equals(key))
    {
      DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, locale);
      format.setTimeZone(tz);

      return format.format(startDate)+ " - " + format.format(endDate);
    }
    return null;
  }

  @Override
  public String getInlineStyle(Date date, String key, Locale locale, TimeZone tz)
  {
    if ("af|calendar::day-all-day-activity-area".equals (key) ||
        "af|calendar::day-timed-activity-area".equals (key) ||
        "af|calendar::week-all-day-activity-area".equals (key) ||
        "af|calendar::week-timed-activity-area".equals (key) ||
        "af|calendar::month-grid-cell".equals(key) ||
        "af|calendar::list-row".equals(key))

    {
      Calendar curCal = Calendar.getInstance (tz, locale);
      curCal.setTime (date);

      if (_getUSHoliday (curCal) != null)
        return "background-color: #fafaeb;";
    }
    return null;
  }

  public String getDateHeaderDesc ()
  {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();

    Calendar ccal = (Calendar) _veCal.getValue(elContext);

    if (ccal != null)
    {
      Map<String, String> status = (Map<String, String>) _veStat.getValue (elContext);
      String view = status.get ("view");
  
      return (RichCalendar.VIEW_MONTH.equals (view) ? _getShortUSHoliday(ccal) : 
                                                      _getUSHoliday (ccal));
    }
    return null;
  }

  public boolean isUSHoliday ()
  {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();

    Calendar ccal = (Calendar) _veCal.getValue(elContext);
    return ((ccal != null) && _getUSHoliday (ccal) != null);
  }

  private static String _getUSHoliday (Calendar curCal)
  {
    if (curCal != null)
    {
      int curmonth = curCal.get (Calendar.MONTH);
      int curdate = curCal.get (Calendar.DATE);    
      int holdate = _US_HOLIDAY_DATES[curmonth];
    
      if (holdate == curdate)
        return _US_HOLIDAY_NAMES[curmonth];
    }
    return null;
  }

  private static String _getShortUSHoliday (Calendar curCal)
  {
    if (curCal != null)
    {
      int curmonth = curCal.get (Calendar.MONTH);
      int curdate = curCal.get (Calendar.DATE);    
      int holdate = _US_HOLIDAY_DATES[curmonth];
    
      if (holdate == curdate)
        return _US_SHORT_HOLIDAY_NAMES[curmonth];
    }
    return null;
  }

  // Holidays are: Jan 1, Feb 14, Mar 17, Apr 16, May 1, Jun 14, Jul 4, Aug 26, Sep 17, Oct 6, Nov 11, Dec 25
  // Selection contains one holiday per month, taken from:
  // http://en.wikipedia.org/wiki/Public_holidays_in_the_United_States

  // Array indexed by month, containing the date which is a holiday for that month
   private static final int[] _US_HOLIDAY_DATES = {1, 14, 17, 16, 1, 14, 4, 26, 17, 7, 11, 25};

  // Array indexed by month, containing the holiday name for that month
   private static final String[] _US_HOLIDAY_NAMES =
     {
       "New Years Day",
       "Valentine's Day",
       "St. Patrick's Day",
       "Emancipation Day",
       "May Day",
       "Flag Day",
       "Independence Day",
       "Women's Equality Day",
       "Constitution Day",
       "German-American Day",
       "Veterans Day",
       "Christmas Day"
     };

  // Array indexed by month, containing the short holiday name for that month, for use in month view
   private static final String[] _US_SHORT_HOLIDAY_NAMES =
     {
       "New Year",
       "Valentine",
       "St. Patrick's",
       "Emancipation",
       "May Day",
       "Flag Day",
       "Independence",
       "Women's Equality",
       "Constitution",
       "German-American",
       "Veterans",
       "Christmas"
     };


  // EL-variable for stamping
  private static final String _DATE_VAR = "#{dateVar}";
  private static final String _DATE_VARSTATUS = "#{dateVarStatus}";

  private ValueExpression _veCal;
  private ValueExpression _veStat;
  
  private Day _targetDay;
}
