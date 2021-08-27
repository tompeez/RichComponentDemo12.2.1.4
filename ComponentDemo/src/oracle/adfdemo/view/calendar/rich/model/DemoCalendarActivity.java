package oracle.adfdemo.view.calendar.rich.model;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import oracle.adf.view.rich.model.CalendarActivity;
import oracle.adf.view.rich.model.CalendarProvider;


// TODO - this should be overriding isReadOnly,
// and implementing all the setters including setEndDate and setStartDate
public class DemoCalendarActivity 
  extends CalendarActivity 
  implements Serializable
{
  public static final String STATUS = "status";
  public static final String PRIORITY = "priority";
  public static final String ACCESS = "access";
  

  /**
   * Possible priority of calendar activity.
   */
  public enum Priority
  {
    LOW,
    MEDIUM,
    HIGH
  }

  /**
   * Possible status of calendar activity
   */
  public enum Status
  {
    CANCELLED,
    TENTATIVE,
    CONFIRMED
  }

  /**
   * Access or class privilege information of calendar activity.
   */
  public enum Access
  {
    PRIVATE,
    CONFIDENTIAL,
    PUBLIC
  }


  public DemoCalendarActivity(CalendarProvider provider, String id, Date startDate, Date endDate, TimeZone tz)
  {
    _startDay = new Day(startDate, tz);
    _endDay =  new Day(endDate, tz);
    _startDate = startDate;
    _endDate = endDate;
    _timeType = TimeType.TIME;
    _init(provider, id);
  }

  public DemoCalendarActivity(CalendarProvider provider, String id, Day startDay, Day endDay, TimeZone tz)
  {
    _startDay = startDay;
    _endDay = endDay;
    _startDate = startDay.createDate(tz);
    _endDate = endDay.createDate(tz);
    _timeType = TimeType.ALLDAY;
    _init(provider, id);
  }

  public DemoCalendarActivity(CalendarProvider provider, String id, Day startDay, Day endDay, long durationInMillis, TimeZone tz)
  {
    _startDay = startDay;
    _endDay = endDay;
    _startDate = startDay.createDate(tz);
    _endDate = endDay.createDate(tz);
    _timeType = TimeType.DURATION;
    
    Calendar startCal = startDay.createCalendar(tz, null);
    Calendar endCal = endDay.createCalendar(tz, null);
    
    do 
    {
      setDuration(startCal.getTime(), tz, durationInMillis);
      
      startCal.add(Calendar.DATE, 1);
    } while(startCal.before(endCal));

    _init(provider, id);
  }

  @Override
  public void setTags(Set<String> tags)
  {
    _tags = tags;
  }


  @Override
  public boolean isReadOnly()
  {
    return false;
  }

  @Override
  public Map<String, Object> getCustomAttributes()
  {
    return _customAttrs;
  }
  
  @Override
  public Set <String> getTags()
  {
    return _tags;
  }

  @Override
  public String getId()
  {
    return _id;
  }

  @Override
  public Date getEndDate(TimeZone tz)
  {
    if (isAllDayOrDuration())
    {
      // the doc states this should return a new date instance
      return _endDay.createDate(tz);
    }

    // the doc states this should return a new date instance
    return (Date) _endDate.clone();
  }

  @Override
  public Date getStartDate(TimeZone tz)
  {
    if (isAllDayOrDuration())
    {
      // the doc states this should return a new date instance
      return _startDay.createDate(tz);
    }

    // the doc states this should return a new date instance
    return (Date)_startDate.clone();
  }

  @Override
  public long getDuration(Date date, TimeZone tz)
  {
    DateDurationMap dateToDurationMap = new DateDurationMap(this._durations, tz);
    Calendar cal = Calendar.getInstance(tz);
    cal.setTime(date);
    
    Long durationMillis = dateToDurationMap.get(new Day(cal));
    
    return (durationMillis != null)? durationMillis : 0L;
  }

  @Override
  public void setStartDate(Date startDate, TimeZone tz)
  {
    // TODO - setting 2 dates every time.
    _startDay = new Day(startDate, tz);
    _startDate = new Date(startDate.getTime());
  }

  @Override
  public void setEndDate(Date endDate, TimeZone tz)
  {
    // TODO - setting 2 dates every time.
    _endDay = new Day(endDate, tz);
    _endDate = new Date(endDate.getTime());
  }
  
  @Override
  public void setDuration(Date date, TimeZone tz, long millis)
  {
    DateDurationMap dateToDurationMap = new DateDurationMap(this._durations, tz);

    Calendar cal = Calendar.getInstance(tz);
    cal.setTime(date);
    dateToDurationMap.put(new Day(cal), Long.valueOf(millis));
    
    this._durations = dateToDurationMap.toString();
  }

  @Override
  public void setTitle(String title)
  {
    _title = title;
  }

  @Override
  public String getTitle()
  {
    return _title;
  }

  @Override
  public void setTimeType(TimeType timeType)
  {
    _timeType = timeType;
  }

  @Override
  public TimeType getTimeType()
  {
    return _timeType;
  }

  @Override
  public CalendarProvider getProvider()
  {
    return _provider;
  }

  public void setProvider(CalendarProvider  p)
  {
    _tags.remove(_provider.getId());
    _provider = p;
    _tags.add (p.getId());
  }

  @Override
  public void setLocation(String location)
  {
    _location = location;
  }

  @Override
  public String getLocation()
  {
    return _location;
  }


  @Override
  public void setRecurring(Recurring recurring)
  {
    _recurring = recurring;
  }

  @Override
  public Recurring getRecurring()
  {
    return _recurring;
  }

  @Override
  public void setReminder(CalendarActivity.Reminder reminder)
  {
    _reminder = reminder;
  }

  @Override
  public CalendarActivity.Reminder getReminder()
  {
    return _reminder;
  }



  public void setStart(Date startDate)
  {
    // TODO - should we remove this and only allow setStartDate? Same for all setStartDay, setEnd, etc
    _startDate = startDate;
  }

  public Date getStart()
  {
    return _startDate;
  }

  public void setEnd(Date endDate)
  {
    _endDate = endDate;
  }

  public Date getEnd()
  {
    return _endDate;
  }

  public void setStartDay(Day startDay)
   {
     _startDay = startDay;
   }

   public Day getStartDay()
   {
     return _startDay;
   }

   public void setEndDay(Day endDay)
   {
     _endDay = endDay;
   }

   public Day getEndDay()
   {
     return _endDay;
   }

  public boolean isAllDayOrDuration()
  {
    return TimeType.ALLDAY.equals(getTimeType()) ||
             TimeType.DURATION.equals(getTimeType());
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("\n     Title: ").append(getTitle());
    builder.append("\n        Id: ").append(getId());
    builder.append("\nProviderId: ").append(getProvider().getId());

    return builder.toString();
  }

  private void _init(CalendarProvider provider, String id)
  {

    _id = id;
    _provider = provider;
    _tags = new CopyOnWriteArraySet<String>();
    _tags.add(provider.getId());
    
    _customAttrs.put(STATUS, Status.TENTATIVE);    
    _customAttrs.put(PRIORITY, Priority.MEDIUM);   
    _customAttrs.put(ACCESS, Access.CONFIDENTIAL);
    
  }

  private class DateDurationMap extends ConcurrentHashMap<Day, Long>
  {
    public DateDurationMap(String encodedString, TimeZone tz)
    {
      if (encodedString != null)
      {
        // DAY_OF_YEAR-YYYY:DURATION;
        String[] dateToDurations = encodedString.split(";");
        
        Calendar cal = Calendar.getInstance(tz);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        for (String dateToDuration : dateToDurations)
        {
          String[] splits = dateToDuration.split(":");
          String[] ddyy = splits[0].split("/");
          
          if (ddyy.length == 1 && dateToDurations.length == 1)
          {
            try
            {
              this._duration = Long.valueOf(ddyy[0], 10);
            }
            catch (NumberFormatException nfe)
            {
              this._duration = 0L;
            }
            
            break;
          }
          
          cal.set(Calendar.DAY_OF_YEAR, Integer.valueOf(ddyy[0]));
          cal.set(Calendar.YEAR, Integer.valueOf(ddyy[1]));
          
          super.put(new Day(cal), Long.valueOf(splits[1]));
        }
      }
      else
        this._duration = 0L;
      
      this._tz = tz;
    }
    
    @Override
    public Long put(Day key, Long value)
    {
      this._duration = null;
      return super.put(key, value);
    }
    
    @Override
    public Long get(Object key)
    {
      if (this._duration != null)
        return this._duration;
      else
        return super.get(key);
    }
    
    @Override
    public String toString()
    {
      if (this._duration != null)
        return this._duration.toString();
      
      StringBuilder sb = new StringBuilder();      
      Calendar cal = Calendar.getInstance(this._tz);

      for (Map.Entry<Day, Long> entry : this.entrySet())
      {
        entry.getKey().updateCalendar(cal);
        Long millis = entry.getValue();
        
        sb.append(cal.get(Calendar.DAY_OF_YEAR))
          .append("/")
          .append(cal.get(Calendar.YEAR))
          .append(":")
          .append(millis)
          .append(";");
      }
      
      return sb.toString();
    }
      
    // In case the activity stores a single common number
    private Long     _duration = null;
    private TimeZone _tz;
  }
  
  private Date _startDate;
  private Date _endDate;
  private Day _startDay;
  private Day _endDay;
  private String _durations;
  private TimeType _timeType;
  private String _id;
  private CalendarProvider _provider;
  private String _location;
  private String _title;
  private Set <String> _tags;
  private CalendarActivity.Recurring _recurring = Recurring.SINGLE;
  private CalendarActivity.Reminder _reminder = Reminder.OFF;
  private Map <String, Object> _customAttrs = new ConcurrentHashMap<String, Object>();
}
