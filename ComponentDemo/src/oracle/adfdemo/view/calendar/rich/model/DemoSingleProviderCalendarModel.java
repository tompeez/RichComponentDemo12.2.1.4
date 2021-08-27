/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.calendar.rich.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import oracle.adf.view.rich.model.CalendarActivity;
import oracle.adf.view.rich.model.CalendarModel;
import oracle.adf.view.rich.model.CalendarProvider;


/**
 * This is a demo class extending the CalendarModel abstract class,
 * it is a model with a single provider. Instances of this class
 * can be passed to the DemoCalendarModelWrapper to aggregate providers.
 */
public class DemoSingleProviderCalendarModel extends CalendarModel  implements Serializable
{

  public DemoSingleProviderCalendarModel(CalendarProvider provider)
  {
    _provider = provider;
    _providerList = Collections.singletonList(_provider);
  }

  /* Start CalendarModel APIs */


  @Override
  public CalendarProvider getProvider(String id)
  {
    if (_provider.getId().equals(id))
      return _provider;

    return null;
  }

  @Override
  public List<CalendarProvider> getProviders()
  {
    return _providerList;
  }


  /**
   * Return all the TimeCalendarActivities within the specified range.
   * @return
   */
  @Override
  public synchronized List<CalendarActivity> getTimeActivities(Date rangeStart,
                                                               Date rangeEnd,
                                                               TimeZone timeZone)
  {
    if (getProvider().getEnabled().equals(CalendarProvider.Enabled.ENABLED))
    {
      List<CalendarActivity> allTimeActivities = _getTimeActivitiesFromAll();
      return _getActivities(allTimeActivities, rangeStart, rangeEnd, timeZone);
    }


    return Collections.emptyList();
  }
  
  /**
   * Return all the Duration based CalendarActivities within the specified range.
   * @param rangeStart
   * @param rangeEnd
   * @param tz
   * @return
   */
  @Override
  public List<CalendarActivity> getDurationActivities(Date rangeStart, Date rangeEnd, TimeZone tz)
  {
    if (getProvider().getEnabled().equals(CalendarProvider.Enabled.ENABLED))
    {
      List<CalendarActivity> allDurationActivities = _getDurationActivitiesFromAll();
      return _getActivities(allDurationActivities, rangeStart, rangeEnd, tz);
    }

    return Collections.emptyList();
  }

  /**
   * <p>
   * Returns activities that intersect the range. The start date is inclusive, meaning if the
   * activity start date is an exact match to the range
   * start date it is included in the returned list. The end
   * date is exclusive, meaning if the activity start date is an exact match to the range
   * end date it is not included in the returned list.
   * <p>
   * <p>
   * If the start date is null, return all the activities before the end date (exclusive).
   * If the end date is null, return all the activities after the start date (inclusive).
   * If both are null,  return all activities
   * </p>
   * @param allActivities List of <code>CalendarActivity</code> objects to iterate through to see if
   * they are in the range
   * @return List of <code>CalendarActivity</code> objects that are in the _rangeStart and _rangeEnd.
   */
  private synchronized List<CalendarActivity> _getActivities(
    List<CalendarActivity> allActivities,
    Date                   rangeStart,
    Date                   rangeEnd,
    TimeZone               tz)
  {

    // if both ranges null return all activities
    if (rangeStart == null && rangeEnd == null)
    {
      return allActivities;
    }

    List<CalendarActivity> activities = new ArrayList <CalendarActivity>();
    Iterator<CalendarActivity> iterator = allActivities.iterator();

    while (iterator.hasNext())
    {
      CalendarActivity ca = iterator.next();
      if (CalendarModel.isActivityInRange(ca, tz, rangeStart, rangeEnd))
      {
        activities.add(ca);
      }
    }


    return activities;
  }


  /**
   * <p>
   * Returns activities that intersect the range. The start date is inclusive, meaning if the
   * activity start date is an exact match to the range
   * start date it is included in the returned list. The end
   * date is exclusive, meaning if the activity start date is an exact match to the range
   * end date it is not included in the returned list.
   * <p>
   * <p>
   * This method will generate a List of CalendarActivity objects where TimeType is ALLDAY.
   * The activities must be returned in sorted order by start date.
   * </p>
   * <p>
   * If the start date is null, return all the activities before the end date.
   * If the end date is null, return all the activities after the start date.
   * If both are null,  return all activities
   * </p>
   * <p>
   * Activities should be stable per request. Meaning between the time
   * the model is asked for activities in a range, and the time the activities are rendered,
   * the activity should not change. For example the start and end time should not change,
   * the timeType should not change, etc.
   * </p>
   * @param rangeStart range start date (inclusive)
   * @param rangeEnd range end date (exclusive)
   * @param timeZone the timeZone
   * @return List of <code>CalendarActivity</code> objects.
   */
  @Override
  public synchronized List<CalendarActivity> getAllDayActivities(Date rangeStart,
                                                                 Date rangeEnd,
                                                                 TimeZone timeZone)
  {
    if (getProvider().getEnabled().equals(CalendarProvider.Enabled.ENABLED))
    {
      List<CalendarActivity> allAlldayActivities = _getAllDayActivitiesFromAll();
      return _getActivities(allAlldayActivities, rangeStart, rangeEnd, timeZone);
    }

    return Collections.emptyList();
  }


  /**
   * Return a particular activty
   * @param providerId
   * @param activityId
   * @return
   */
  @Override
  public synchronized CalendarActivity getActivity(String providerId,
                                                   String activityId,
                                                   Date rangeStart,
                                                   Date rangeEnd,
                                                   TimeZone timezone)
  {
    List<CalendarActivity> activities = _idToActivitiesMap.get(activityId);
    if (activities != null)
    {
      Iterator<CalendarActivity> it = activities.iterator();
      while(it.hasNext())
      {
        CalendarActivity ca = it.next();
        if(ca.getProvider().getId().equalsIgnoreCase(providerId))
        {
          return ca;
        }
      }
    }

    return null;
  }


  /* End CalendarModel APIs */

  /* Start DemoSingleProviderCalendarModel APIs */


  /**
   * Return the provider id of the CalendarModel
   * @return
   */
  public CalendarProvider getProvider()
  {
    return _provider;
  }

  /**
   * Remove an AllDay activity fromt the master list
   * @param activity
   */
  public void removeAllDayCalendarActivity (CalendarActivity activity)
  {
    this._idToActivitiesMap.remove(activity.getId());
    this._getAllDayActivitiesFromAll().remove(activity);
  }

  /**
   * Remove time activity from master list
   * @param activity
   */
  public void removeTimeCalendarActivity (CalendarActivity activity)
  {
    this._idToActivitiesMap.remove(activity.getId());
    this._getTimeActivitiesFromAll().remove(activity);
  }

  /**
   * Remove time activity from master list
   * @param activity
   */
  void removeDurationCalendarActivity(CalendarActivity activity)
  {
    this._idToActivitiesMap.remove(activity.getId());
    this._getDurationActivitiesFromAll().remove(activity);
  }
  
  /**
   * Add activity to the model.
   * @param activity
   */
  public void addTimeCalendarActivity(DemoCalendarActivity activity)
  {
    CalendarActivityComparator comp = new CalendarActivityComparator();

    synchronized (_timeActivitiesFromAllModel)
    {
      // get time activities and add them to all time activities list
      if (_timeActivitiesFromAllModel.isEmpty())
      {
        // Add it to list of all activities
        this._timeActivitiesFromThisModel.add(activity);

        // sort
        Collections.sort(this._timeActivitiesFromThisModel, comp);
      }
      else
      {
        // add time activities from the other model
        _timeActivitiesFromAllModel.add(activity);

        // sort
        Collections.sort(this._timeActivitiesFromAllModel, comp);
      }
    }

    // Next check Id
    String id = activity.getId();
    List<CalendarActivity> activities = _idToActivitiesMap.get(id);
    if (activities == null)
    {
      activities = new ArrayList<CalendarActivity>();
      _idToActivitiesMap.put(id, activities);
    }
    activities.add(activity);
  }

  /**
   * Add activity to the model.
   * @param activity
   */
  public void addAllDayCalendarActivity(DemoCalendarActivity activity)
  {
    CalendarActivityComparator comp = new CalendarActivityComparator();

    synchronized (_allDayActivitiesFromAllModel)
    {
      // get all day activities and add them to all all-day activities list
      if (_allDayActivitiesFromAllModel.isEmpty())
      {
        // Add it to list of all activities
        this._allDayActivitiesFromThisModel.add(activity);

        // sort
        Collections.sort(this._allDayActivitiesFromThisModel, comp);
      }
      else
      {
        _allDayActivitiesFromAllModel.add(activity);

        // sort
        Collections.sort(this._allDayActivitiesFromAllModel, comp);
      }
    }

    // Next check Id
    String id = activity.getId();
    List<CalendarActivity> activities = _idToActivitiesMap.get(id);
    if (activities == null)
    {
      activities = new ArrayList<CalendarActivity>();
      _idToActivitiesMap.put(id, activities);
    }
    activities.add(activity);
  }

  /**
   * Add activity to the model.
   * @param activity
   */
  public void addDurationCalendarActivity(DemoCalendarActivity activity)
  {
    CalendarActivityComparator comp = new CalendarActivityComparator();

    synchronized (_durationActivitiesFromAllModel)
    {
      // get duration activities and add them to all duration activities list
      if (_durationActivitiesFromAllModel.isEmpty())
      {
        // Add it to list of all activities
        this._durationActivitiesFromThisModel.add(activity);

        // sort
        Collections.sort(this._durationActivitiesFromThisModel, comp);
      }
      else
      {
        _durationActivitiesFromAllModel.add(activity);

        // sort
        Collections.sort(this._durationActivitiesFromAllModel, comp);
      }
    }

    // Next check Id
    String id = activity.getId();
    List<CalendarActivity> activities = _idToActivitiesMap.get(id);
    if (activities == null)
    {
      activities = new ArrayList<CalendarActivity>();
      _idToActivitiesMap.put(id, activities);
    }
    activities.add(activity);
  }

  /**
   * Return AllDayCalendarActivities from the models
   * @return
   */
  private List<CalendarActivity> _getAllDayActivitiesFromAll()
  {
    if (this._allDayActivitiesFromAllModel.isEmpty())
    {
      return this._allDayActivitiesFromThisModel;
    }
    else
    {
      return this._allDayActivitiesFromAllModel;
    }
  }

  /**
   * Return TimeCalendarActivities from the models
   * @return
   */
  private List<CalendarActivity> _getTimeActivitiesFromAll()
  {
    if (this._timeActivitiesFromAllModel.isEmpty())
    {
      return this._timeActivitiesFromThisModel;
    }
    else
    {
      return this._timeActivitiesFromAllModel;
    }
  }
  
  /**
   * Return TimeCalendarActivities from the models
   * @return
   */
  private List<CalendarActivity> _getDurationActivitiesFromAll()
  {
    if (this._durationActivitiesFromAllModel.isEmpty())
    {
      return this._durationActivitiesFromThisModel;
    }
    else
    {
      return this._durationActivitiesFromAllModel;
    }
  }

  /* End DemoSingleProviderCalendarModel APIs */

  /* Private members and helpers */


  /**
   * Comparator class for CalendarActivity.
   * This Comparator class will evaluate these fields in order:
   *  startTime
   *  endTIme
   */
  static class CalendarActivityComparator implements Comparator<CalendarActivity>
  {
    /**
     * If you are sorting just time or just allday activities you can use this constructor, but
     * if you are soring both time and allday activities then use the constructor that takes a
     * timezone since you need to know where to sort all day activities relative to time activities.
     */
    public CalendarActivityComparator( )
    {
      _tz = TimeZone.getDefault();
    }

    /**
     * If you are soring both time and allday activities then use this constructor
     * since you need to know where to sort all day activities relative to time activities.
     */
    public CalendarActivityComparator(TimeZone tz)
    {
      if (tz == null)
        throw new NullPointerException("timeZone cannot be null");
      _tz = tz;
    }

    private TimeZone _tz;

    public int compare(CalendarActivity activity1, CalendarActivity activity2)
    {
       Date start1 = activity1.getStartDate(_tz);
       Date start2 = activity2.getStartDate(_tz);

       return start1.compareTo(start2);
    }

  }

  private CalendarProvider _provider;
  private List<CalendarProvider> _providerList;
  private final List<CalendarActivity> _allDayActivitiesFromThisModel =
    new ArrayList<CalendarActivity>();
  private final List<CalendarActivity> _allDayActivitiesFromAllModel =
    new ArrayList<CalendarActivity>();
  private final List<CalendarActivity> _timeActivitiesFromThisModel =
    new ArrayList<CalendarActivity>();
  private final List<CalendarActivity> _timeActivitiesFromAllModel =
    new ArrayList<CalendarActivity>();
  private final List<CalendarActivity> _durationActivitiesFromThisModel =
    new ArrayList<CalendarActivity>();
  private final List<CalendarActivity> _durationActivitiesFromAllModel =
    new ArrayList<CalendarActivity>();
  private final Map<String, List<CalendarActivity>> _idToActivitiesMap =
    new HashMap<String, List<CalendarActivity>>();
}
