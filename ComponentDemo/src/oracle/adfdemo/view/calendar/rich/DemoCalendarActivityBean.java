/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.calendar.rich;

import java.io.Serializable;

import java.text.DecimalFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import oracle.adf.view.rich.model.CalendarActivity;
import oracle.adf.view.rich.model.CalendarProvider;

import oracle.adfdemo.view.calendar.rich.model.Day;
import oracle.adfdemo.view.calendar.rich.model.DemoCalendarActivity;
import oracle.adfdemo.view.calendar.rich.model.DemoCalendarModelWrapper;

import org.apache.myfaces.trinidad.context.RenderingContext;


public class DemoCalendarActivityBean  
  implements Serializable
{
  public DemoCalendarActivityBean(DemoCalendarActivity activity,
                                  TimeZone tz,
                                  DemoCalendarModelWrapper model)
  {
    _tz = tz;
    _activity = activity;
    _model = model;
    
    _allDay = (CalendarActivity.TimeType.ALLDAY.equals(_activity.getTimeType()));
    
    if (CalendarActivity.TimeType.DURATION.equals(_activity.getTimeType()))
    {
      long duration = _activity.getDuration(_activity.getStartDate(tz), tz);
      _durHours = Integer.valueOf((int)(duration / _SIXTY_MINUTES_IN_MILLIS));
      _durMinutes = Integer.valueOf((int)((duration - _durHours * _SIXTY_MINUTES_IN_MILLIS)/ _SIXTY_SECONDS_IN_MILLIS));
    }
  }

  public boolean isAllDay()
  {
    return _allDay;
  }

  public boolean isTime()
  {
    return CalendarActivity.TimeType.TIME.equals(_activity.getTimeType());
  }

  public boolean isDuration()
  {
    return CalendarActivity.TimeType.DURATION.equals(_activity.getTimeType());
  }

  public void setAllDay(boolean allDay)
  {
    _allDay = allDay;
  }
 
  public boolean isRecurring()
  {
    if (_activity.getRecurring() == CalendarActivity.Recurring.RECURRING)
    {
      return true;
    }

    return false;
  }

  public boolean isReminder()
  {
    if (_activity.getReminder() == CalendarActivity.Reminder.ON)
    {
      return true;
    }

    return false;
  }
  public void setReminder (boolean reminder)
  {
    _activity.setReminder(reminder ? CalendarActivity.Reminder.ON : 
                                     CalendarActivity.Reminder.OFF);
  }

  public void setFrom(Date from)
  {
    _activity.setStartDay(new Day(from, _tz));
    _activity.setStart(from);
  }

  public Date getFrom()
  {
    return _activity.getStartDate(_tz);
  }

  public Date getTo()
  {
    return _activity.getEndDate(_tz);
  }
  
  public void setTo(Date to)
  {
    // We want the 'to' to be the following day midnight, so add one day here.
    Day toDay = new Day(to, _tz);
    Calendar cal = toDay.createCalendar(_tz, null);
    cal.add(Calendar.DAY_OF_YEAR, 1);
    _activity.setEndDay(new Day(cal));
    _activity.setEnd(to);
  }
  
  public void setDurHours(Integer hours)
  {
    _durHours = hours;
  }
  public Integer getDurHours()
  {
    return _durHours;    
  }

  public void setDurMinutes(Integer minutes)
  {
    _durMinutes = minutes;
  }
  public Integer getDurMinutes()
  {
    return _durMinutes;    
  }

  public void setRecurring (boolean isRecurring)
  {
    if (isRecurring)
    {
      _activity.setRecurring(CalendarActivity.Recurring.RECURRING);
    }
    else
    {
      if (_activity.getRecurring() == CalendarActivity.Recurring.RECURRING)
      {
        _activity.setRecurring(CalendarActivity.Recurring.CHANGED);
      }
      else
      {
        _activity.setRecurring(CalendarActivity.Recurring.SINGLE);
      }
    }
  }

  public String getTitle ()
  {
    return _activity.getTitle();
  }

  public String getProviderId()
  {
    return _activity.getProvider().getId();
  }

  public String getProviderName()
  {
    return _activity.getProvider().getDisplayName();
  }

  public void setProviderId(String newProviderId)
  {    
    CalendarProvider np = _model.getProvider(newProviderId);
    
    if (np != null)
      _activity.setProvider(np);        
  }

  public String getId()
  {
    return _activity.getId();
  }

  public String getLocation()
  {
    return _activity.getLocation();
  }
  
  public Map<String, Object> getCustomAttributes()
  {
    return _activity.getCustomAttributes();
  }  

  public void setTitle(String title)
  {
    _activity.setTitle(title);
  }

  public void setLocation(String location)
  {
    _activity.setLocation(location);
  }

  public void setActivity(DemoCalendarActivity activity)
  {
    _activity = activity;
  }

  public DemoCalendarActivity getActivity()
  {
    return _activity;
  }
  
  public String getDurationText()
  {
    // get the total activity duration, starting from the start date to end date(exclusive)
    long durationMillis = _getTotalActivityDuration(getActivity(), _tz); 
        
    // TODO not NLS friendly
    StringBuffer fmtString = new StringBuffer();
    long hours = durationMillis / (1000 * 60 * 60);
    long minutes = (durationMillis - hours * (1000 * 60 * 60)) / (1000 * 60);
    
    if (hours == 0)
    {
      // For duration < 60 mins, display duration as x mins
      fmtString.append(minutes)
               .append(" ")
        .append((minutes > 1)? "minutes" : "minute");
    }
    else
    {
      // for duration > 60, display as x hrs, and minutes represented in x as fractional hour
      float totalHours = hours + (minutes / 60F);
      
      fmtString.append(_DURATION_FORMATTER.format(totalHours))
               .append(" ")
               .append((hours == 1 && minutes == 0)? "hour": "hours"); 
    }
    
    return fmtString.toString();
  }
  
  /**
   * Gets the total duration of the TimeType.DURATION activity by summing up durations reported against individual 
   * dates between activity start and end date (exclusive)
   * @param activity
   * @param tz
   * @return
   */
  private long _getTotalActivityDuration(CalendarActivity activity, TimeZone tz)
  {
    if (!CalendarActivity.TimeType.DURATION.equals(activity.getTimeType()))
      return 0L;

    long totalDuration = 0L;   
    
    Calendar activeCal = Calendar.getInstance(tz);
    activeCal.setTime(activity.getStartDate(tz));
    
    Calendar endCal = Calendar.getInstance(tz);
    endCal.setTime(activity.getEndDate(tz));  
    
    do
    {
      totalDuration += Math.max(0, activity.getDuration(activeCal.getTime(), tz));
      
      // increment the calendar
      activeCal.add(Calendar.DAY_OF_YEAR, 1); 
    } while (activeCal.before(endCal));
    
    return totalDuration;
  }
  
  // Formatter for printing duration text
  private static final DecimalFormat _DURATION_FORMATTER = new DecimalFormat("#.##");
  
  private static final int _SIXTY_SECONDS_IN_MILLIS = 60000;  
  private static final int _SIXTY_MINUTES_IN_MILLIS = 60 * _SIXTY_SECONDS_IN_MILLIS;

  private DemoCalendarModelWrapper _model;
  private DemoCalendarActivity     _activity = null;
  private boolean                  _allDay = false;
  private TimeZone                 _tz;
  private Integer                  _durHours = 0;
  private Integer                  _durMinutes = 0;
}
