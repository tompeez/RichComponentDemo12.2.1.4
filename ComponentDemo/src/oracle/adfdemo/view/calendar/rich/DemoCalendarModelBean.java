/* Copyright (c) 2008, 2018, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.calendar.rich;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.model.CalendarActivity;
import oracle.adf.view.rich.model.CalendarProvider;

import oracle.adfdemo.view.calendar.rich.model.Day;
import oracle.adfdemo.view.calendar.rich.model.DemoCalendarActivity;
import oracle.adfdemo.view.calendar.rich.model.DemoCalendarModelWrapper;
import oracle.adfdemo.view.calendar.rich.model.DemoCalendarProvider;
import oracle.adfdemo.view.calendar.rich.model.DemoSingleProviderCalendarModel;


/**
 * The demo model will provide sample CalendarActivities that shows:
 * <ul>
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
public class DemoCalendarModelBean
  implements Serializable
{
  public DemoCalendarModelBean()
  {
    CalendarProvider provider0 = new DemoCalendarProvider("me", "My Cal");
    CalendarProvider provider1 = new DemoCalendarProvider("Sushil", "Sushil");
    CalendarProvider provider2 = new DemoCalendarProvider("Ashwin", "Ashwin");
    CalendarProvider provider3 = new DemoCalendarProvider("Rahul", "Rahul");
    CalendarProvider provider4 = new DemoCalendarProvider("Arjun", "Arjun");
    CalendarProvider provider5 = new DemoCalendarProvider("Sriram", "Sriram");
    CalendarProvider provider6 = new DemoCalendarProvider("Praveen", "Praveen");

    List <CalendarProvider> providers = new ArrayList<CalendarProvider>();
    providers.add(provider0);
    providers.add(provider1);
    providers.add(provider2);
    providers.add(provider3);
    providers.add(provider4);
    providers.add(provider5);
    providers.add(provider6);

    DemoSingleProviderCalendarModel model = new DemoSingleProviderCalendarModel(provider0);
    _modelWrapper = new DemoCalendarModelWrapper();
    _modelWrapper.addCalendarModel( model);

    // Add regular CalendarActivities
    _addBasicActivities(model);
    _addRecurringActivities(model);

    // Add all day activities
    _addAllDayActivities(model);

    // Add duration activities
    _addDurationActivities(model);
      
    _addAllDayUSHoliday (model);
    // Add multiple days activites
    _addMultipleDaysActivities(model);
    // Add activities from different models

    // Add some activities in 2014 January 
    _addActivitiesInJan2014(model);

    // create other models
    DemoSingleProviderCalendarModel model1 = new DemoSingleProviderCalendarModel(provider1);
    _addActivitiesToModel(model1, 1);
    // Activities for testing allDayActivityOrder attribute - tagged holiday
    // For demo purposes, use the same provider so we can color code all holidays the same color
    // See DemoCalendarInstanceStylesBean__initActivityStylesAndProviderData() if this changes so the activityStyles are kept in sync
    HashSet<String> holidayTags = new HashSet<String> ();
    holidayTags.add ("holiday");
    holidayTags.add (provider1.getId());
    _addAlldayActivity (model1, null, "Holiday", 0, 2, holidayTags);    

    // Add several duration activities
    _addNumDurationActivities(model1, 0.5F, 4, 10);
    _addDurationActivity(model1, null, "5 mins multi-day task", -4, 8, 0.0835F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model1, null, "15 mins multi-day task", 5, 2, 0.25F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);

    _modelWrapper.addCalendarModel(model1);


    DemoSingleProviderCalendarModel model2 = new DemoSingleProviderCalendarModel(provider2);
    _addActivitiesToModel(model2, 2);
    // Activities for testing allDayActivityOrder attribute - tagged absence
    // For demo purposes, use the same provider so we can color code all absences the same color
    // See DemoCalendarInstanceStylesBean_initActivityStylesAndProviderData() if this changes so the activityStyles are kept in sync
    HashSet<String> absenceTags = new HashSet <String> ();
    absenceTags.add ("absence");
    absenceTags.add (provider2.getId ());
    _addAlldayActivity (model2, null, "Absence", 0, 2, absenceTags);    
    _addDurationActivity(model2, null, "15 mins multi-day task", 1, 7, 0.25F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addNumDurationActivities(model2, 0.25F, 1, 4);

    _modelWrapper.addCalendarModel(model2);

    DemoSingleProviderCalendarModel model3 = new DemoSingleProviderCalendarModel(provider3);
    _addActivitiesToModel(model3, 3);
    _modelWrapper.addCalendarModel(model3);

    DemoSingleProviderCalendarModel model4 = new DemoSingleProviderCalendarModel(provider4);
    _addActivitiesToModel(model4, 4);
    _modelWrapper.addCalendarModel(model4);

    DemoSingleProviderCalendarModel model5 = new DemoSingleProviderCalendarModel(provider5);
    _modelWrapper.addCalendarModel(model5);

    DemoSingleProviderCalendarModel model6 = new DemoSingleProviderCalendarModel(provider6);
    _addActivitiesToModel(model6, 6);
    _modelWrapper.addCalendarModel(model6);
  }

  // Public APIs

  public DemoCalendarModelWrapper getCalendarModel()
  {
    return _modelWrapper;
  }

  public int getAndIncreaseActivityIdCounter()
  {
    int id = _activityIdCounter;
    _activityIdCounter++;
    return id;
  }

  public int getActivityIdCounter()
  {
    return _activityIdCounter;
  }

  public void setActivityIdCounter(int activityIdCounter)
  {
    this._activityIdCounter = activityIdCounter;
  }

  // Protected methods
  // Methods to get an activity title - DemoCalendarArabicModelBean override
  // these to provide full RTL strings
  protected String getAllDayActivityTitle(String titleAddition, long numDays,
                                        Calendar endCalendar)
  {
    String title = "allday " + getActivityIdCounter ();

    if (numDays > 1)
    {

      // for days > 1 we show on which day this activity ends. We subtract one day off since we
      // calculate an all day activity that ends on the 27th as being at midnight of the 27th,
      // and thus it doesn't actually show up on the 27th. It 'ends' on the 26th, just before midnight.
      // therefore the text says the 26th instead of the 27th.
      endCalendar.add(Calendar.DAY_OF_YEAR, -1);
      title = title + " ends on " +
              DateFormat.getDateInstance(DateFormat.SHORT).format(endCalendar.getTime());
      endCalendar.add(Calendar.DAY_OF_YEAR, 1);

    }

    if (titleAddition != null)
      title = title + " " + titleAddition;
    return title;
  }

  protected String getDailyRecurringDurationTitle(int i)
  {
    return "Task " + getActivityIdCounter() +
                      " recurring daily " + (i+1);
  }

  protected String getDailyRecurringActivityTitle(int i)
  {
    return "time " + getActivityIdCounter() +
                      " recurring daily " + (i+1);
  }
  protected String getMonthlyRecurringActivityTitle(int i)
  {
    return "time " + getActivityIdCounter() +
                      " recurring monthly " + (i+1);
  }

  protected String getTimedActivityTitle(String titleAddition,
                                       Calendar startCalendar,
                                       Calendar endCalendar)
  {
    return getActivityTitle("time ", titleAddition, startCalendar, endCalendar);
  }

  protected String getDurationActivityTitle(String titleAddition, int numDays, Calendar endCalendar)
  {
    String title = "Task " + getActivityIdCounter();
    
    if (numDays > 0)
    {
      // for days > 0 we show on which day this activity ends. We subtract one day off since we
      // calculate an all day activity that ends on the 27th as being at midnight of the 27th,
      // and thus it doesn't actually show up on the 27th. It 'ends' on the 26th, just before midnight.
      // therefore the text says the 26th instead of the 27th.
      endCalendar.add(Calendar.DAY_OF_YEAR, -1);
      title = title + " ends on " + DateFormat.getDateInstance(DateFormat.SHORT).format(endCalendar.getTime());
      endCalendar.add(Calendar.DAY_OF_YEAR, 1);
    }

    if (titleAddition != null)
      title = title + " " + titleAddition;
    
    return title;   
  }
  
  protected String getActivityTitle(String titlePrefix,
                                    String titleAddition,
                                    Calendar startCalendar,
                                    Calendar endCalendar)
  {
    String title = titlePrefix + getActivityIdCounter();

    int c1DayOfYear = startCalendar.get(Calendar.DAY_OF_YEAR);
    int c1Year = startCalendar.get(Calendar.YEAR);
    int c2DayOfYear = endCalendar.get(Calendar.DAY_OF_YEAR);
    int c2Year = endCalendar.get(Calendar.YEAR);

    if (c1DayOfYear != c2DayOfYear || c1Year != c2Year)
    {
      DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
      
      title = title + " ends at " +
              formatter.format(endCalendar.getTime()) + " " +
              endCalendar.getTimeZone().getDisplayName(true, TimeZone.SHORT);
    }

    if (titleAddition != null)
    {
      title = title + " " + titleAddition;
    }
    return title;
  }


  protected String getWeeklyRecurringActivityTitle(int i)
  {
    return "time " + getActivityIdCounter() +
                      " recurring weekly " + (i+1);
  }


  // Helper methods


  private void _addTimeActivity(
    DemoSingleProviderCalendarModel model,
    String            location,
    String            titleAddition,
    int               startAddDays,
    int               startHour,
    int               startMin,
    int               endAddDays,
    int               endAddHours,
    int               endAddMins)
  {
    _addTimeActivity(model, location, titleAddition,
                     startAddDays, startHour, startMin,
                     endAddDays, endAddHours, endAddMins,
                     CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
  }

  /**
   * The title will have syntax
   *  <provider Id> activity <activity Id> <type and details of activity>
   */
  private void _addTimeActivity(
    DemoSingleProviderCalendarModel model,
    String            location,
    String            titleAddition,
    int               startAddDays,
    int               startHour,
    int               startMin,
    int               endAddDays,
    int               endAddHours,
    int               endAddMins,
    CalendarActivity.Recurring recurring,
    CalendarActivity.Reminder  reminder)
  {
    CalendarProvider  provider = model.getProvider();
    Calendar startCalendar = _createCalendarFromToday(startAddDays, startHour, startMin);
    Calendar endCalendar = _createCalendar(startCalendar, endAddDays, endAddHours, endAddMins);
    DemoCalendarActivity ca = new DemoCalendarActivity(provider, String.valueOf(_activityIdCounter), startCalendar.getTime(), endCalendar.getTime(), TimeZone.getDefault());
    ca.setLocation(location);

    String title = getTimedActivityTitle(titleAddition, startCalendar, endCalendar);

    ca.setTitle(title);

    ca.setRecurring(recurring);
    ca.setReminder(reminder);
    model.addTimeCalendarActivity(ca);
    _activityIdCounter++;
  }


  /**
   * The title will have syntax
   *  <provider Id> activity <activity Id> <type and details of activity>
   */
  private void _addAlldayActivity(
    DemoSingleProviderCalendarModel model,
    String        location,
    String        titleAddition,
    int           startAddDays,
    int           numDays,
    Set<String>   tags)
  {

    CalendarProvider provider = model.getProvider();

    // set the start time as 1pm to make sure that start hours on all day events don't affect
    // rendering
    Calendar startCalendar = _createCalendarFromToday(startAddDays, 13, 0);

    Calendar endCalendar = _createCalendar(startCalendar, numDays, 1, 0);

    String title = getAllDayActivityTitle(titleAddition, numDays, endCalendar);

    DemoCalendarActivity ca = new DemoCalendarActivity(provider, String.valueOf(_activityIdCounter), new Day(startCalendar), new Day(endCalendar), TimeZone.getDefault());
    ca.setLocation(location);
    ca.setTitle(title);
    if (tags != null)
      ca.setTags(tags);

    model.addAllDayCalendarActivity(ca);
    _activityIdCounter++;
  }
  
  /**
   * The title will have syntax:  <provider Id> activity <activity Id> <type and details of activity>
   * @param model
   * @param location
   * @param titleAddition
   * @param startAddDays
   * @param startHour
   * @param startMin
   * @param endAddDays
   * @param endAddHours
   * @param endAddMins
   * @param recurring
   * @param reminder
   */
  private void _addDurationActivity(
    DemoSingleProviderCalendarModel model,
    String            location,
    String            titleAddition,
    int               startAddDays,
    int               endAddDays,
    float             durationHours,
    CalendarActivity.Recurring recurring,
    CalendarActivity.Reminder  reminder)
  {
    CalendarProvider  provider = model.getProvider();
    Calendar startCalendar = _createCalendarFromToday(startAddDays, 0, 0);
    Calendar endCalendar = _createCalendar(startCalendar, endAddDays + 1, 0, 0);
    DemoCalendarActivity ca = new DemoCalendarActivity(provider, String.valueOf(_activityIdCounter), 
                                                       new Day(startCalendar), new Day(endCalendar), 
                                                       (int)(durationHours * 60 * 60 * 1000), TimeZone.getDefault());
    ca.setLocation(location);

    String title = getDurationActivityTitle(titleAddition, endAddDays, endCalendar);

    ca.setTitle(title);

    ca.setRecurring(recurring);
    ca.setReminder(reminder);
    model.addDurationCalendarActivity(ca);
    _activityIdCounter++;
  }

  /**
   * <p>
   * Add _numOfRegularActivities of CalendarActivities
   * The title will have syntax
   *  <provider Id> activity <activity Id> <type and details of activity>
   *
   * The method will add these types of activities:
   *  Start at today's date at 9 am current timezone.
   *  Overlapped activities
   *  A lot of time activities.
   *  </p>
   */
  private void _addBasicActivities(DemoSingleProviderCalendarModel model)
  {
    // Create CalendarActivity for current day at 9 with an hour duration.
    _addTimeActivity(model, null, null, 0, 9, 0, 0, 1, 0);

    // Create a 2 hour overlapped activity from 9 am today activity
    _addTimeActivity(model, null, null, 0, 9, 40, 0, 2, 0);

    // Almost all day activity
    _addTimeActivity(model, null, null, 2, 0, 0, 0, 24, 0);

    // Add several time activities
    _addNumActivities(model, 8, 3);
    
    // Add several duration activities
    _addNumDurationActivities(model, 1, 2, 20);

    // activity that ends at midnight
    _addTimeActivity(model, null,
                     "ends at midnight",
                     2, 23, 0, 0, 1, 0,
                     CalendarActivity.Recurring.SINGLE,
                     CalendarActivity.Reminder.OFF);

    // activity less than 24h but go across midnight
    _addTimeActivity (model, null, " spans across midnight", -7, 18, 0,
                      0, 14, 0);

    // activity that spans 7 days
    _addTimeActivity (model, null, " spans full week", -7, 0, 0, 14, 0, 0);
    
    // activity that starts on previous month to test bug 12804556
    Calendar cal = Calendar.getInstance();
    int currdate = cal.get (Calendar.DATE);
    _addTimeActivity (model, null, " spans end of previous month",  -1 * (currdate + 1), 23, 0, 0, 4, 0);
  }

  // add a whole bunch of time activities
  private void _addNumActivities(DemoSingleProviderCalendarModel model, int startHour, int frequency)
  {
    // loop to create a lot of one hour activities starting the day before today start at 8 am
    for(int i=1; i <= 30; i++)
    {
      int days = (i-2) * frequency;
      _addTimeActivity(model, null, null, days, startHour, 0, 0, 1, 0,
                       CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.ON);
    }
  }

  // add a whole bunch of duration activities
  private void _addNumDurationActivities(DemoSingleProviderCalendarModel model, float durationHours, int frequency, int count)
  {
    // loop to create a lot of one hour activities starting the day before today start at 8 am
    for(int i=1; i <= count; i++)
    {
      int days = (i-2) * frequency;
      
      _addDurationActivity(model, null, null, days, 0, durationHours, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    }
  }
  
  private void _addRecurringActivities(DemoSingleProviderCalendarModel model)
  {
    DemoCalendarActivity ca = null;
    Calendar startCalendar = null;
    Calendar endCalendar = null;
    CalendarProvider provider = model.getProvider();
    int numOfRecurringActivities = 10;

    // recurring activities daily for 10 days since today date start at 3 pm
    for (int i=0; i < numOfRecurringActivities; i++)
    {
      startCalendar = _createCalendarFromToday(i, 15, 0);
      endCalendar = _createCalendar(startCalendar, 0, 1, 0);
      Date startDate = startCalendar.getTime();
      Date endDate = endCalendar.getTime();
      ca = new DemoCalendarActivity(provider, String.valueOf(_activityIdCounter),
                                    startDate, endDate, TimeZone.getDefault());
      ca.setTitle(getDailyRecurringActivityTitle(i));
      ca.setLocation("2op1176");
      ca.setRecurring(CalendarActivity.Recurring.RECURRING);
      ca.setReminder(CalendarActivity.Reminder.ON);
      model.addTimeCalendarActivity(ca);
      _activityIdCounter++;
    }
    
    // recurring 30 min tasks daily for 5 days starting today
    for (int i=0; i < 5; i++)
    {
      startCalendar = _createCalendarFromToday(i, 0, 0);
      endCalendar = _createCalendar(startCalendar, 1, 0, 0);
      ca = new DemoCalendarActivity(provider, String.valueOf(_activityIdCounter), 
                                    new Day(startCalendar), new Day(endCalendar), 
                                    (int)(0.5 * 60 * 60 * 1000), TimeZone.getDefault());
      ca.setTitle(getDailyRecurringDurationTitle(i));
      ca.setLocation("2op1176");
      ca.setRecurring(CalendarActivity.Recurring.RECURRING);
      ca.setReminder(CalendarActivity.Reminder.ON);
      model.addDurationCalendarActivity(ca);
      _activityIdCounter++;
    }

    // recurring activies weekly for 10 weeks since today date start at 4:30 pm
    for (int i=0; i < numOfRecurringActivities; i++)
    {
      startCalendar = _createCalendarFromToday(i * 7, 16, 30);
      endCalendar = _createCalendar(startCalendar, 0, 1, 0);
      Date startDate = startCalendar.getTime();
      Date endDate = endCalendar.getTime();
      ca = new DemoCalendarActivity(provider,  String.valueOf(_activityIdCounter),
                                    startDate, endDate, TimeZone.getDefault());
      ca.setTitle(getWeeklyRecurringActivityTitle(i));
      ca.setRecurring(CalendarActivity.Recurring.CHANGED);

      model.addTimeCalendarActivity(ca);
      _activityIdCounter++;
    }

    // recurring activies monthly for 10 months since today date start at 6:15 pm
    for (int i=0; i < numOfRecurringActivities; i++)
    {
      startCalendar = getCalendarInstanceForToday(null, null);
      startCalendar.set(Calendar.HOUR_OF_DAY, 18);
      startCalendar.set(Calendar.MINUTE, 15);
      startCalendar.set(Calendar.SECOND, 0);
      startCalendar.set(Calendar.MILLISECOND, 0);
      // TODO unsafe calendar set, check if this will roll over the new year properly
      startCalendar.add(Calendar.MONTH, i);
      endCalendar = getCalendarInstanceForToday(null, null);
      endCalendar.setTime(startCalendar.getTime());
      endCalendar.add(Calendar.HOUR_OF_DAY, 1);

      Date startDate = startCalendar.getTime();
      Date endDate = endCalendar.getTime();
      ca = new DemoCalendarActivity(provider,  String.valueOf(_activityIdCounter),
                                    startDate, endDate, TimeZone.getDefault());
      ca.setTitle(getMonthlyRecurringActivityTitle(i));
      model.addTimeCalendarActivity(ca);
      _activityIdCounter++;
    }


    // Add an activity that starts late in the day
    _addTimeActivity(model, null, null, 1, 22, 30, 0, 1, 0,
                     CalendarActivity.Recurring.CHANGED, CalendarActivity.Reminder.OFF);

    // Add several time activities
    _addTimeActivity(model, null, null, 2, 0, 0, 6, 0, 0,
                     CalendarActivity.Recurring.RECURRING, CalendarActivity.Reminder.ON);
  }


  /**
   * Add all day Activities start at 9 am
   * The title will have syntax
   *  <provider Id> activity <activity Id> <type and details of activity>
   */
  private void _addAllDayActivities(DemoSingleProviderCalendarModel model)
  {
    _addAlldayActivity(model, null, null, 0, 1, null);
    _addAlldayActivity(model, null, null, 1, 3, null);            
  }

  /**
   *
   * @param model
   */
  private void _addDurationActivities(
    DemoSingleProviderCalendarModel model)
  {
    // add single day tasks
    _addDurationActivity(model, null, "1 hr 45 mins task", -10, 0, 1.75F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "30 mins task", -6, 0, 0.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "1 hr 30 mins task", -6, 0, 1.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "1 hr 30 mins task", -4, 0, 1.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "2 hrs 30 mins task", -3, 0, 2.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "1 hr 30 mins task", -3, 0, 1.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "1 hr task", -2, 0, 1, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "4 hrs task", -1, 0, 4, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);

    _addDurationActivity(model, null, "1 hr task", 0, 0, 1, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "1 hr 30 mins task", 0, 0, 1.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "30 mins task", 0, 0, 0.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "30 mins task", 0, 0, 0.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "2 hrs 30 mins task", 0, 0, 2.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "1 hr 30 mins task", 1, 0, 1.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "30 mins task", 1, 0, 0.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "30 mins task", 1, 0, 0.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "5 hrs task", 1, 0, 5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "2 hrs task", 2, 0, 2, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "1 hr 30 mins task", 2, 0, 1.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "1 hr 30 mins task", 2, 0, 1.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    
    // add mutli day tasks
    _addDurationActivity(model, null, "2 hrs repeating task", 0, 3, 2, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);    
    _addDurationActivity(model, null, "30 mins repeating task", 1, 5, 0.5F, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);    
    _addDurationActivity(model, null, "1 hr repeating task", 3, 5, 1, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);    
  }
  
  /**
   * Add an all day activity for some US holiday in this month
   * so it will show in list view
   */
  private void _addAllDayUSHoliday (DemoSingleProviderCalendarModel model)
  {
    CalendarProvider provider = model.getProvider();

    Calendar todayCal = getCalendarInstanceForToday (null, null);
    Calendar startCalendar = getCalendarInstanceForToday(null, null);
    startCalendar.set (Calendar.YEAR, todayCal.get (Calendar.YEAR));
    startCalendar.set (Calendar.HOUR_OF_DAY, 1);
    
    // Holidays are: Jan 1, Feb 14, Mar 17, Apr 16, May 1, Jun 14, Jul 4, Aug 26, Sep 17, Oct 6, Nov 11, Dec 25    
    // Selection contains one holiday per month, taken from:
    // http://en.wikipedia.org/wiki/Public_holidays_in_the_United_States
    switch (todayCal.get (Calendar.MONTH))
    {
      case Calendar.JANUARY :
        startCalendar.set (Calendar.DATE, 1);
        break;
      case Calendar.FEBRUARY :
        startCalendar.set (Calendar.DATE, 14);
        break;
      case Calendar.MARCH :
        startCalendar.set (Calendar.DATE, 17);
        break;
      case Calendar.APRIL :
        startCalendar.set (Calendar.DATE, 16);
        break;
      case Calendar.MAY :
        startCalendar.set (Calendar.DATE, 1);
        break;
      case Calendar.JUNE :
        startCalendar.set (Calendar.DATE, 14);
        break;
      case Calendar.JULY :
        startCalendar.set (Calendar.DATE, 4);
        break;
      case Calendar.AUGUST :
        startCalendar.set (Calendar.DATE, 26);
        break;
      case Calendar.SEPTEMBER :
        startCalendar.set (Calendar.DATE, 17);
        break;
      case Calendar.OCTOBER :
        startCalendar.set (Calendar.DATE, 6);
        break;
      case Calendar.NOVEMBER :
        startCalendar.set (Calendar.DATE, 11);
        break;
      case Calendar.DECEMBER :
        startCalendar.set (Calendar.DATE, 25);
        break;
    }

    Calendar endCalendar = _createCalendar(startCalendar, 1, 1, 0);

    String title = getAllDayActivityTitle("US Holiday", 1, endCalendar);

    DemoCalendarActivity ca = new DemoCalendarActivity(provider, 
                                                       String.valueOf(_activityIdCounter), 
                                                       new Day(startCalendar), 
                                                       new Day(endCalendar), 
                                                       TimeZone.getDefault());
    ca.setTitle(title);
    model.addAllDayCalendarActivity(ca);
    _activityIdCounter++;    
  }
  
  /**
   * Add activities that span multiple days start at the day after today date at
   * 9 am.
   * The title will have syntax
   *  <provider Id> activity <activity Id> <type and details of activity>
   */
  private void _addMultipleDaysActivities(DemoSingleProviderCalendarModel model)
  {
    // add activity that lasts 3 days start at 9 am the day after today date
    _addTimeActivity(model, null, null, 1, 9, 0, 3, -3, 0);
    _addTimeActivity(model, null, null, -2, 14, 0, 2, 2, 0);

    // add 5 identical overlapping activities that lasts 7 days 
    _addTimeActivity(model, null, null, -8, 14, 0, 7, 2, 0);
    _addTimeActivity(model, null, null, -8, 14, 0, 7, 2, 0);
    _addTimeActivity(model, null, null, -8, 14, 0, 7, 2, 0);
    _addTimeActivity(model, null, null, -8, 14, 0, 7, 2, 0);
    _addTimeActivity(model, null, null, -8, 14, 0, 7, 2, 0);

    _addTimeActivity(model, "Yosemite", null, 3, 9, 0, 2, 7, 0,
                     CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.ON);
    _addTimeActivity(model, "Europe", null, 3, 15, 0, 2, 0, 0,
                     CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.ON);
    // add a ways out to test what happens when you only have 'overflow' activity on certain day
    _addTimeActivity(model, null, null, 36, 17, 0, 5, +2, 15);

    _addDurationActivity(model, null, "2 hrs multi-day task", -2, 2, 2, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
    _addDurationActivity(model, null, "1 hr multi-day task", -3, 5, 1, CalendarActivity.Recurring.SINGLE, CalendarActivity.Reminder.OFF);
  }

  /**
   * Add Calendar activities to a model
   */
  private void _addActivitiesToModel(
    DemoSingleProviderCalendarModel model,
    int offset)
  {

    // Add lots of conflicting CalendarActivities
    _addBasicActivitiesConflicting(model, offset);
    _addNumActivities(model, 8 + offset, 3 + offset);
    _addAllDayActivities(model);
        
  }

  /**
   * Add activities for conflicting model.
   * The title will have sytax:
   *  <provider Id> activity <activity Id> <type and details of activity>
   * @param model
   */
  private void _addBasicActivitiesConflicting(DemoSingleProviderCalendarModel model,
                                              int daysAfterToday)
  {
    // create several conflicting actvities at daysAfterToday days
    // after today date start at 8 am
    int startHour = 8;
    int startMins = 0;

    for(int i=1; i <= 5; i++)
    {
      _addTimeActivity(model, null,
                       "conflicting with activity " + String.valueOf(_activityIdCounter-1),
                       daysAfterToday, startHour, startMins, 0, 1, 0);

      startMins = startMins + 30;

      if (startMins == 60)
      {
        startMins = 0;
        startHour++;
      }
    }
  }

  private void _addActivitiesInJan2014(DemoSingleProviderCalendarModel model) 
  {
    int actCount = 0;
    Calendar calJan1 = Calendar.getInstance();
    calJan1.set(Calendar.DATE, 1);
    calJan1.set(Calendar.MONTH, 0);
    calJan1.set(Calendar.YEAR, 2014);
    calJan1.set(Calendar.HOUR_OF_DAY, 0);
    calJan1.set(Calendar.MINUTE, 0);
    calJan1.set(Calendar.SECOND, 0);
    calJan1.set(Calendar.MILLISECOND, 0);

    // Jan 1 2014 - allDay (Activity #1)     
    Calendar startCal = (Calendar) calJan1.clone();  
    Calendar endCal = (Calendar) calJan1.clone();
    endCal.add(Calendar.DATE, 1);
    endCal.add(Calendar.MILLISECOND, -1);
    DemoCalendarActivity ca = new DemoCalendarActivity(model.getProvider(), 
                                                       String.valueOf(_activityIdCounter++), 
                                                       startCal.getTime(), 
                                                       endCal.getTime(), 
                                                       TimeZone.getDefault());
    ca.setTitle("Activity #" + ++actCount);
    model.addAllDayCalendarActivity(ca);

    // Jan 1 2014 - allDay (Activity #2)     
    ca = new DemoCalendarActivity(model.getProvider(), 
                                  String.valueOf(_activityIdCounter++), 
                                  startCal.getTime(), 
                                  endCal.getTime(), 
                                  TimeZone.getDefault());
    ca.setTitle("Activity #" + ++actCount);
    model.addAllDayCalendarActivity(ca);

    // Jan 1 2014 - allDay (Activity #3)     
    ca = new DemoCalendarActivity(model.getProvider(), 
                                  String.valueOf(_activityIdCounter++), 
                                  startCal.getTime(), 
                                  endCal.getTime(), 
                                  TimeZone.getDefault());
    ca.setTitle("Activity #" + ++actCount);
    model.addAllDayCalendarActivity(ca);

    // Jan 1 2014 - allDay (Activity #4)     
    ca = new DemoCalendarActivity(model.getProvider(), 
                                  String.valueOf(_activityIdCounter++), 
                                  startCal.getTime(), 
                                  endCal.getTime(), 
                                  TimeZone.getDefault());
    ca.setTitle("Activity #" + ++actCount);
    model.addAllDayCalendarActivity(ca);

    // Jan 1 2014 - timed 8:00 - 9:00 (Activity #5)     
    startCal.set(Calendar.HOUR_OF_DAY, 8);
    endCal.set(Calendar.HOUR_OF_DAY, 9);
    endCal.set(Calendar.MINUTE, 0);
    endCal.set(Calendar.SECOND, 0);
    endCal.set(Calendar.MILLISECOND, 0);
    ca = new DemoCalendarActivity(model.getProvider(), 
                                  String.valueOf(_activityIdCounter++), 
                                  startCal.getTime(), 
                                  endCal.getTime(), 
                                  TimeZone.getDefault());
    ca.setTitle("Activity #" + ++actCount);
    model.addTimeCalendarActivity(ca);
        
    // Jan 1 2014 - timed 10:00 - 11:30 (Activity #6)     
    startCal.set(Calendar.HOUR_OF_DAY, 10);
    endCal.set(Calendar.HOUR_OF_DAY, 11);
    endCal.set(Calendar.MINUTE, 30);
    ca = new DemoCalendarActivity(model.getProvider(), 
                                  String.valueOf(_activityIdCounter++), 
                                  startCal.getTime(), 
                                  endCal.getTime(), 
                                  TimeZone.getDefault());
    ca.setTitle("Activity #" + ++actCount);
    model.addTimeCalendarActivity(ca);

    // Jan 1 2014 - timed 10:15 - 11:30 (Activity #7)     
    startCal.set(Calendar.HOUR_OF_DAY, 10);
    startCal.set(Calendar.MINUTE, 15);
    endCal.set(Calendar.HOUR_OF_DAY, 11);
    endCal.set(Calendar.MINUTE, 30);
    ca = new DemoCalendarActivity(model.getProvider(), 
                                  String.valueOf(_activityIdCounter++), 
                                  startCal.getTime(), 
                                  endCal.getTime(), 
                                  TimeZone.getDefault());
    ca.setTitle("Activity #" + ++actCount);
    model.addTimeCalendarActivity(ca);

    // Jan 1 2014 - timed 12:00 - 15:30 (Activity #8)     
    startCal.set(Calendar.HOUR_OF_DAY, 12);
    startCal.set(Calendar.MINUTE, 0);
    endCal.set(Calendar.HOUR_OF_DAY, 15);
    endCal.set(Calendar.MINUTE, 30);
    ca = new DemoCalendarActivity(model.getProvider(), 
                                  String.valueOf(_activityIdCounter++), 
                                  startCal.getTime(), 
                                  endCal.getTime(), 
                                  TimeZone.getDefault());
    ca.setTitle("Activity #" + ++actCount);
    model.addTimeCalendarActivity(ca);

    
    startCal.set(Calendar.DATE, 2);
    startCal.set(Calendar.MONTH, 0);
    startCal.set(Calendar.YEAR, 2014);
    startCal.set(Calendar.HOUR_OF_DAY, 0);
    startCal.set(Calendar.MINUTE, 0);
    startCal.set(Calendar.SECOND, 0);
    startCal.set(Calendar.MILLISECOND, 0);
    endCal = (Calendar) startCal.clone();
    endCal.add(Calendar.DATE, 1);
    endCal.add(Calendar.MILLISECOND, -1);
    
    // Jan 2 2014 - allDay      (Activity #9)     
    ca = new DemoCalendarActivity(model.getProvider(), 
                                  String.valueOf(_activityIdCounter++), 
                                  startCal.getTime(), 
                                  endCal.getTime(), 
                                  TimeZone.getDefault());
    ca.setTitle("Activity #" + ++actCount);
    model.addAllDayCalendarActivity(ca);

    // Jan 2 2014 - allDay      (Activity #10)     
    ca = new DemoCalendarActivity(model.getProvider(), 
                                  String.valueOf(_activityIdCounter++), 
                                  startCal.getTime(), 
                                  endCal.getTime(), 
                                  TimeZone.getDefault());
    ca.setTitle("Activity #" + ++actCount);
    model.addAllDayCalendarActivity(ca);
    
    // Jan 2 2014 - timed 8:00 - 9:00 (Activity #11)  
    startCal.set(Calendar.HOUR_OF_DAY, 8);
    endCal.set(Calendar.HOUR_OF_DAY, 9);
    endCal.set(Calendar.MINUTE, 0);
    endCal.set(Calendar.SECOND, 0);
    endCal.set(Calendar.MILLISECOND, 0);
    ca = new DemoCalendarActivity(model.getProvider(), 
                                  String.valueOf(_activityIdCounter++), 
                                  startCal.getTime(), 
                                  endCal.getTime(), 
                                  TimeZone.getDefault());
    ca.setTitle("Activity #" + ++actCount);
    model.addTimeCalendarActivity(ca);
  
    // Jan 2 2014 - timed 9:15 - 12:30 (Activity #12)     
    startCal.set(Calendar.HOUR_OF_DAY, 9);
    startCal.set(Calendar.MINUTE, 15);
    endCal.set(Calendar.HOUR_OF_DAY, 12);
    endCal.set(Calendar.MINUTE, 30);
    ca = new DemoCalendarActivity(model.getProvider(), 
                                  String.valueOf(_activityIdCounter++), 
                                  startCal.getTime(), 
                                  endCal.getTime(), 
                                  TimeZone.getDefault());
    ca.setTitle("Activity #" + ++actCount);
    model.addTimeCalendarActivity(ca);


  }
    
    
  // creates a calendar relative to today
  // for example if I want to create a calendar 3 days from today for 2:30pm, I'd call
  // _createCalendar(3, 14, 30);
  // that's 3 for 3 days, 14 for 2pm, and 30 for 30 minutes.
  private Calendar _createCalendarFromToday( int addDays, int hourOfDay, int minutes)
  {
    Calendar cal = getCalendarInstanceForToday(null, null);
    cal.add(Calendar.DAY_OF_YEAR, addDays);
    cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
    cal.set(Calendar.MINUTE, minutes);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);

    return cal;
  }

  // creates a calendar relative to the start calendar
  // for example if I want to create a calendar for 1 hour 15 minutes past the start, I'd call
  // _createCalendar(starCalendar, 0, 1, 15);
  private Calendar _createCalendar(Calendar startCalendar, int addDays, int addHours, int addMinutes)
  {
    Calendar cal = getCalendarInstanceForToday(null, null);
    cal.setTime(startCalendar.getTime());
    cal.add(Calendar.DAY_OF_YEAR, addDays);
    cal.add(Calendar.HOUR_OF_DAY, addHours);
    cal.add(Calendar.MINUTE, addMinutes);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);

    return cal;
  }

  // Print Calendar info to help debugging
  // TODO remove later
  private void _printCalendarFields(Calendar calendar)
  {
    System.out.println("=========================================");
    System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
    System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
    System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
    System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
    System.out.println("DATE: " + calendar.get(Calendar.DATE));
    System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
    System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
    System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
    System.out.println("DAY_OF_WEEK_IN_MONTH: "
                       + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
    System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
    System.out.println("HOUR: " + calendar.get(Calendar.HOUR_OF_DAY));
    System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
    System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
    System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
    System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
    System.out.println("=========================================");
  }
  
  public static Calendar getCalendarInstanceForToday(TimeZone tz, Locale locale)
  {
    String fixDate = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("oracle.adfdemo.view.calendar.rich.DemoCalendarModelBean._FIX_TODAY_DATE");
    
    if (tz == null)
      tz = TimeZone.getDefault();
    
    if (locale == null)
      locale = Locale.getDefault(Locale.Category.FORMAT);
        
    Calendar today = Calendar.getInstance(tz, locale);
    
    // If the application context-parameter is set, fix the date to 15-Nov-2015
    if (fixDate != null)
      today.set(2015, 10, 16);
    
    return today;
  }

  private DemoCalendarModelWrapper _modelWrapper;
  private int _activityIdCounter = 1; // This should be monotonically increasing
}

