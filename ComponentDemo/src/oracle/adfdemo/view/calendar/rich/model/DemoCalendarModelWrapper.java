/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.calendar.rich.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import oracle.adf.view.rich.model.CalendarActivity;
import oracle.adf.view.rich.model.CalendarModel;
import oracle.adf.view.rich.model.CalendarProvider;


/**
 *   TODO synchronization
 * This wraps DemoSingleProviderCalendarModel instances
 */
public class DemoCalendarModelWrapper
  extends CalendarModel  implements Serializable
{
  public DemoCalendarModelWrapper()
  {
  }

  /* Start CalendarModel APIs */


  @Override
  public CalendarProvider getProvider(String id)
  {
    for (CalendarProvider provider: _providers)
    {
      if (id.equals(provider.getId()))
        return provider;
    }

    return null;
  }

  @Override
  public List<CalendarProvider> getProviders()
  {
    return _providers;
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
    List<CalendarActivity> all = new ArrayList<CalendarActivity>();

    for (DemoSingleProviderCalendarModel model:_models.values())
    {
        List<CalendarActivity> activities = model.getTimeActivities(rangeStart, rangeEnd, timeZone);
        all.addAll(activities);
    }

    // sort
    Collections.sort(all, new DemoSingleProviderCalendarModel.CalendarActivityComparator());

    return all;

  }

  /**
   * Return the AllDayCalendarActivities within the specified range.
   * @return
   */
  @Override
  public synchronized List<CalendarActivity> getAllDayActivities(Date rangeStart, Date rangeEnd, TimeZone timeZone)
  {
    List<CalendarActivity> all = new ArrayList<CalendarActivity>();

    for (DemoSingleProviderCalendarModel model:_models.values())
    {
      List<CalendarActivity> activities = model.getAllDayActivities(rangeStart, rangeEnd, timeZone);
      all.addAll(activities);
    }

    // sort
    Collections.sort(all, new DemoSingleProviderCalendarModel.CalendarActivityComparator());

    return all;
  }

  /**
   * Return duration based activities within the specified date range
   * @param rangeStart
   * @param rangeEnd
   * @param tz
   * @return
   */
  @Override
  public synchronized List<CalendarActivity> getDurationActivities(Date rangeStart, Date rangeEnd, TimeZone tz)
  {
    List<CalendarActivity> durationActivities = new ArrayList<CalendarActivity>();

    for (DemoSingleProviderCalendarModel model:_models.values())
    {
      List<CalendarActivity> activities = model.getDurationActivities(rangeStart, rangeEnd, tz);
      durationActivities.addAll(activities);
    }

    // sort
    Collections.sort(durationActivities, new DemoSingleProviderCalendarModel.CalendarActivityComparator());

    return durationActivities;
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
                                                   TimeZone timeZone)
  {
    DemoSingleProviderCalendarModel model = _models.get(providerId);

    if (model != null)
    {
      return model.getActivity(providerId, activityId, rangeStart, rangeEnd, timeZone);
    }

    return null;
  }


  /* End CalendarModel APIs */

  /* Start DemoSingleProviderCalendarModel APIs */


  /**
   * Remove an AllDay activity fromt the master list
   * @param activity
   */
  public void removeAllDayCalendarActivity (CalendarActivity activity)
  {
    String providerId = activity.getProvider().getId();
    DemoSingleProviderCalendarModel model = _models.get(providerId);

    if (model != null)
    {
      model.removeAllDayCalendarActivity(activity);
    }
  }

  /**
   * Remove time activity from master list
   * @param activity
   */
  public void removeTimeCalendarActivity (CalendarActivity activity)
  {
    String providerId = activity.getProvider().getId();
    DemoSingleProviderCalendarModel model = _models.get(providerId);

    if (model != null)
    {
      model.removeTimeCalendarActivity(activity);
    }
  }
  
  /**
   * Remove time activity from master list
   * @param activity
   */
  public void removeDurationCalendarActivity (CalendarActivity activity)
  {
    String providerId = activity.getProvider().getId();
    DemoSingleProviderCalendarModel model = _models.get(providerId);

    if (model != null)
    {
      model.removeDurationCalendarActivity(activity);
    }
  }

  /**
   * Add activity to the model.
   * @param activity
   */
  public void addTimeCalendarActivity(DemoCalendarActivity activity)
  {
    String providerId = activity.getProvider().getId();
    DemoSingleProviderCalendarModel model = _models.get(providerId);

    if (model != null)
    {
      model.addTimeCalendarActivity(activity);
    }
  }

  /**
   * Add activity to the model.
   * @param activity
   */
  public void addAllDayCalendarActivity(DemoCalendarActivity activity)
  {
    String providerId = activity.getProvider().getId();
    DemoSingleProviderCalendarModel model = _models.get(providerId);

    if (model != null)
    {
      model.addAllDayCalendarActivity(activity);
    }
  }
  
  /**
   * Add activity to the model.
   * @param activity
   */
  public void addDurationCalendarActivity(DemoCalendarActivity activity)
  {
    String providerId = activity.getProvider().getId();
    DemoSingleProviderCalendarModel model = _models.get(providerId);

    if (model != null)
    {
      model.addDurationCalendarActivity(activity);
    }
  }
  
  /**
   * Notify the model that an activity has changed providers, allowing the model to clear out old data. 
   * @param activity
   * @param oldProviderId
   * @param newProviderId
   */
  public synchronized void notifyActivityProviderChange (DemoCalendarActivity activity, String oldProviderId, String newProviderId)
  {
    DemoSingleProviderCalendarModel oldProvider = _models.get (oldProviderId);
    DemoSingleProviderCalendarModel newProvider = _models.get (newProviderId);
  
    if (CalendarActivity.TimeType.ALLDAY.equals(activity.getTimeType()))
    {
      oldProvider.removeAllDayCalendarActivity(activity);
      newProvider.addAllDayCalendarActivity(activity);      
    }
    else
    {
      oldProvider.removeTimeCalendarActivity(activity);
      newProvider.addTimeCalendarActivity(activity);
    }
  }
  
  /**
   * Add another CalendarModel to be included.
   * Get the Time and AllDay activities and store them.
   * @param model
   */
  public void addCalendarModel(DemoSingleProviderCalendarModel model)
  {
    CalendarProvider provider = model.getProvider();
    _providers.add(provider);
    _models.put(provider.getId(), model);
  }


  private List<CalendarProvider> _providers = new ArrayList<CalendarProvider>();
  private Map <String, DemoSingleProviderCalendarModel> _models = new ConcurrentHashMap<String, DemoSingleProviderCalendarModel>();
}
