/* Copyright (c) 2008, 2018, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.calendar.rich;

import java.awt.Color;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichCalendar;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.CalendarDropSite;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.el.ELUtils;
import oracle.adf.view.rich.event.CalendarActivityDurationChangeEvent;
import oracle.adf.view.rich.event.CalendarActivityEvent;
import oracle.adf.view.rich.event.CalendarDisplayChangeEvent;
import oracle.adf.view.rich.event.CalendarEvent;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DropEvent;
import oracle.adf.view.rich.event.MouseButton;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.event.TriggerType;
import oracle.adf.view.rich.model.CalendarActivity;
import oracle.adf.view.rich.model.CalendarModel;
import oracle.adf.view.rich.model.CalendarProvider;
import oracle.adf.view.rich.util.DateCustomizer;
import oracle.adf.view.rich.util.InstanceStyles;

import oracle.adf.view.rich.util.ProviderComparator;

import oracle.adfdemo.view.calendar.rich.model.Day;
import oracle.adfdemo.view.calendar.rich.model.DemoCalendarActivity;
import oracle.adfdemo.view.calendar.rich.model.DemoCalendarModelWrapper;

import oracle.adfinternal.view.faces.renderkit.rich.CalendarRenderer;

import oracle.adfinternal.view.faces.renderkit.rich.DateRenderUtils;

import org.apache.myfaces.trinidad.change.AttributeComponentChange;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


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
public class DemoCalendarBean implements Serializable
{
  // TODO people can easily change the end time to be after the start time, make sure the
  // demo handles this, the dialogListener should be checking this I guess.

  // TODO remove this hard coded id
  public static final String _CALENDAR_COMPONENT_ID = "dmoTpl:cal";

  public DemoCalendarBean()
  {
  }

  public void serialize(ActionEvent e)
  {
    // test calendar serialization
    ObjectOutputStream out = null;
    try
    {
      ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
      out = new ObjectOutputStream(byteOutput);
      out.writeObject(this);
      out.close();
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
  }

  public DemoCalendarModelWrapper getCalendarModel()
  {
    return getModelBean().getCalendarModel();
  }

  /**
   * Listener for the DisplayChangeEvent
   */
  public void displayChangeListener(CalendarDisplayChangeEvent dce)
  {
    System.out.println("displayChangeListener " + dce);

    String view = dce.getNewView();
    System.out.println("     displayChangeListener view = " + view);

    Date activeDay = dce.getNewActiveDay();
    System.out.println("     displayChangeListener activeDay = " + activeDay);

  }  

  public void activityListener(CalendarActivityEvent ae)
  {
    CalendarActivity activity = ae.getCalendarActivity();

    if (activity == null)
    {
      // no activity with that id is found in the model
      System.out.println("No activity with event " + ae.toString());
      setCurrActivity(null);
      return;
    }

    setCurrActivity(new DemoCalendarActivityBean((DemoCalendarActivity)activity, getTimeZone(), getCalendarModel()));
  }

  public void activityClickListener(CalendarActivityEvent ae)
  {    
    if (ae.getTriggerType().equals(TriggerType.MOUSE))
    {
      int clickCount = ae.getClickCount();
      MouseButton btn = ae.getButton();
    
      FacesMessage facesMessage = new FacesMessage("Mouse " + btn.name() + " button clicked. " +
        "Click count = " + clickCount);
      
      FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
  }
  
  public void activityDurationChangeListener(CalendarActivityDurationChangeEvent ae)
  {
    CalendarActivity activity = ae.getCalendarActivity();

    if (activity == null)
    {
      // no activity with that id is found in the model
      System.out.println("No activity with event " + ae.toString());
      setCurrActivity(null);

      // Since the user has acted on an activity that couldn't be found, ppr the page so
      // that they no longer see the activity
      RequestContext adfContext = RequestContext.getCurrentInstance();
      adfContext.addPartialTarget(getCalendarComponent());
      return;
    }

    DemoCalendarActivity demoActivity = ((DemoCalendarActivity)activity);
    TimeZone tz = getTimeZone();
    
    if (CalendarActivity.TimeType.DURATION.equals(demoActivity.getTimeType()))
    {
      demoActivity.setDuration(ae.getTriggerDate(), tz, ae.getNewDuration());
    }
    else
    {
      demoActivity.setEndDate(ae.getNewEndDate(), tz);  
    }    
    
    setCurrActivity(new DemoCalendarActivityBean(demoActivity, tz, getCalendarModel()));
  }

  public DnDAction handleDrop(DropEvent dropEvent)
  {
    return _handleDrop(dropEvent, false);
  }

  public DnDAction handleDropWithPopup(DropEvent dropEvent)
  {
    return _handleDrop(dropEvent, true);
  }

  public DnDAction _handleDrop(DropEvent dropEvent, boolean showPopup)
  {
    Transferable transferable = dropEvent.getTransferable();
    CalendarDropSite dropSite = (CalendarDropSite)dropEvent.getDropSite();
    Date dropSiteDate = dropSite.getDate();
    CalendarActivity.TimeType timeType = dropSite.getTimeType();

    CalendarActivity activity = (CalendarActivity)transferable.getData(DataFlavor.getDataFlavor(CalendarActivity.class));

    // If we have a calendar activitity that we are moving within the same view
    if(activity != null)
    {
      _handleCalendarActivityDrop(dropEvent, dropSiteDate, activity, showPopup);
      return dropEvent.getProposedAction();
    }

    // See if we have a drag and drop of a row to create a new activity.
    DataFlavor<RowKeySet> rowKeySetFlavor = DataFlavor.getDataFlavor(RowKeySet.class, "DnDAcivityModel");
    RowKeySet rowKeySet = transferable.getData(rowKeySetFlavor);
    if (rowKeySet != null)
    {
      _handleRowKeyDrop(dropEvent, transferable, dropSiteDate, timeType, rowKeySet, showPopup);
      return dropEvent.getProposedAction();
    }

    return DnDAction.NONE;
  }

  public void newActivity()
  {
    _newActivityType = CalendarActivity.TimeType.TIME;

    RichCalendar calendar = getCalendarComponent();
    RichPopup popup = (RichPopup)calendar.getFacet("create");
    RichPopup.PopupHints hints = new RichPopup.PopupHints();
    hints.add(RichPopup.PopupHints.HintTypes.HINT_ALIGN_ID, calendar)
          .add(RichPopup.PopupHints.HintTypes.HINT_LAUNCH_ID, calendar)
          .add(RichPopup.PopupHints.HintTypes.HINT_ALIGN, RichPopup.PopupHints.AlignTypes.ALIGN_OVERLAP);
    
    popup.show(hints);
  }
  
  public void newTask()
  {
    _newActivityType = CalendarActivity.TimeType.DURATION;

    RichCalendar calendar = getCalendarComponent();
    RichPopup popup = (RichPopup)calendar.getFacet("create");
    RichPopup.PopupHints hints = new RichPopup.PopupHints();
    hints.add(RichPopup.PopupHints.HintTypes.HINT_ALIGN_ID, calendar)
          .add(RichPopup.PopupHints.HintTypes.HINT_LAUNCH_ID, calendar)
          .add(RichPopup.PopupHints.HintTypes.HINT_ALIGN, RichPopup.PopupHints.AlignTypes.ALIGN_OVERLAP);
    popup.show(hints);
  }
  
  private void _handleCalendarActivityDrop(
    DropEvent dropEvent,
    Date dropSiteDate,
    CalendarActivity activity,
    boolean showPopup)
  {
    DemoCalendarActivity demoActivity = ((DemoCalendarActivity)activity);

    // If this is a timed event
    Date startDate = demoActivity.getStart();
    TimeZone tz = getTimeZone();
    Calendar startCal = Calendar.getInstance(tz);
    startCal.setTime(startDate);

    Calendar dropCal = Calendar.getInstance(tz);
    dropCal.setTime(dropSiteDate);

    int startDayOfYear = startCal.get(Calendar.DAY_OF_YEAR);
    int startHour = startCal.get(Calendar.HOUR_OF_DAY);
    int startMin = startCal.get(Calendar.MINUTE);
    int dropDayOfYear = dropCal.get(Calendar.DAY_OF_YEAR);
    int dropHour = dropCal.get(Calendar.HOUR_OF_DAY);
    int dropMin = dropCal.get(Calendar.MINUTE);

    // move the start date to the new time
    if(startDayOfYear != dropDayOfYear)
    {
      startCal.set(Calendar.DAY_OF_YEAR, dropDayOfYear);
      startCal.set(Calendar.YEAR, dropCal.get(Calendar.YEAR));
      startCal.set(Calendar.MONTH, dropCal.get(Calendar.MONTH));
    }

    // move this activity to the new location
    if(demoActivity.isAllDayOrDuration())
    {
      _proposedStartDate = startCal.getTime();

      // Get the original start day
      Day startDay = demoActivity.getStartDay();

      // Calcuate new end day by using the new start day and the original delta
      Day endDay = demoActivity.getEndDay();
      Calendar endCal = Calendar.getInstance(tz);

      startCal = Calendar.getInstance(tz);
      endDay.updateCalendar(endCal);
      startDay.updateCalendar(startCal);

      long delta = endCal.getTime().getTime() -  startCal.getTime().getTime();
      Date endDate = new Date(_proposedStartDate.getTime() + delta);

      if(showPopup && CalendarActivity.Recurring.RECURRING.equals(demoActivity.getRecurring()))
      {
        _proposedEndDate = endDate;
        _addOpenPopupScript(FacesContext.getCurrentInstance(),
                            "dmoTpl:moveActivityPopup", null);
      }
      else
      {       
        if (CalendarActivity.TimeType.DURATION.equals(demoActivity.getTimeType()))
        {
          // reset the hh:mm:ss.mmm part of the drop calendar
          new Day(dropCal).updateCalendar(dropCal);
          long dropSite0HrMillis = dropCal.getTimeInMillis();
          long activityStart0HrMillis = startCal.getTimeInMillis();
          
          long daysShifted = (dropSite0HrMillis - activityStart0HrMillis) / _ALL_DAY_MILLIS; 
          
          // daysShifted is -ve if the activity is moved to a earlier date, +ve if moved to later date, 
          // 0 in case of DnD within the same day. 
          if (daysShifted != 0) 
          {         
            // Offset all the dates in the duration map by delta
            Map<Date, Long> newDateToDurationMapping = new HashMap<Date, Long>();
            Calendar newDateCal = Calendar.getInstance(tz);
            while(startCal.compareTo(endCal) < 0)
            {
              Date date = startCal.getTime();
              long durationMillis = demoActivity.getDuration(date, tz);
              
              newDateCal.setTime(date);
              newDateCal.add(Calendar.DATE, (int)daysShifted);              
              newDateToDurationMapping.put(newDateCal.getTime(), durationMillis);
              
              // Reset the duration of this day
              demoActivity.setDuration(date, tz, 0);
  
              startCal.add(Calendar.DATE, 1);
            }
            
            // Update the activity with offset dates
            for(Map.Entry<Date, Long> entry : newDateToDurationMapping.entrySet())
            {
              demoActivity.setDuration(entry.getKey(), tz, entry.getValue());
            }
          }
        }
        
        // update to the new start and end day
        demoActivity.setStartDay(new Day(_proposedStartDate, tz));
        demoActivity.setEndDay(new Day(endDate, tz));
      }
    }
    else
    {
      String view = ((RichCalendar)dropEvent.getDropComponent()).getView();

      if((RichCalendar.VIEW_DAY.equals(view) || RichCalendar.VIEW_WEEK.equals(view)) &&
         CalendarActivity.TimeType.TIME.equals(activity.getTimeType()))
      {
        if(startHour != dropHour)
          startCal.set(Calendar.HOUR_OF_DAY, dropHour);

        if(dropMin != startMin)
        {
          if(dropMin == 0 && startMin >=30)
              startCal.add(Calendar.MINUTE, -30);
          else if(dropMin == 30 && startMin <30)
              startCal.add(Calendar.MINUTE, 30);
        }
      }

      Date endDate = demoActivity.getEnd();
      long delta = endDate.getTime() - startDate.getTime();

      startDate = startCal.getTime();
      endDate = new Date(startDate.getTime() + delta);

      if(showPopup && CalendarActivity.Recurring.RECURRING.equals(demoActivity.getRecurring()))
      {
        _proposedStartDate = startDate;
        _proposedEndDate = endDate;
        _addOpenPopupScript(FacesContext.getCurrentInstance(),
                            "dmoTpl:moveActivityPopup", null);
      }
      else
      {
        demoActivity.setStart(startDate);
        demoActivity.setEnd(endDate);
      }
    }

    setCurrActivity(new DemoCalendarActivityBean((DemoCalendarActivity)activity, getTimeZone(), getCalendarModel()));
  }
  
  public Date getStaticActiveDay()
  {
    return DemoCalendarModelBean.getCalendarInstanceForToday(null, null).getTime();
  }

  private void _handleRowKeyDrop(
    DropEvent dropEvent,
    Transferable transferable,
    Date dropSiteDate,
    CalendarActivity.TimeType timeType,
    RowKeySet rowKeySet,
    boolean showPopup)
  {
    // Get the model for the dragged component.
    CollectionModel dragModel = transferable.getData(CollectionModel.class);
    if (dragModel != null)
    {
      String view = ((RichCalendar)dropEvent.getDropComponent()).getView();
      boolean isUnknown = CalendarActivity.TimeType.UNKNOWN.equals(timeType);

      Calendar dropCal = Calendar.getInstance(getTimeZone());
      dropCal.setTime(dropSiteDate);

      if(RichCalendar.VIEW_MONTH.equals(view))
      {
        // default the activity to 10 am in the month view
        dropCal.add(Calendar.HOUR_OF_DAY, 10);
        dropSiteDate = dropCal.getTime();
      }

      // Set the row key for this model using the row key from the transferrable.
      Object currKey = rowKeySet.iterator().next();
      dragModel.setRowKey(currKey);

      // And now get the actual data from the dragged model.
      DnDActivityData dnDDemoData = (DnDActivityData)dragModel.getRowData();

      // Create a new activity with the data.
      DemoCalendarActivity newActivity =
        _createNewActivity(dropSiteDate, isUnknown ? CalendarActivity.TimeType.ALLDAY : timeType, null);
      newActivity.setTitle(dnDDemoData.getTitle());

      if(dnDDemoData.isRecurring())
        newActivity.setRecurring(CalendarActivity.Recurring.RECURRING);

      if(showPopup)
      {
        _addOpenPopupScript(FacesContext.getCurrentInstance(),
                            "dmoTpl:createDndExternalPopup", null);
      }
      else
      {
        _addActivityToModel(newActivity);
      }
    }
  }

  public void moveDnDDialogListener (DialogEvent event)
  {
    DialogEvent.Outcome outcome = event.getOutcome();

    if (outcome == DialogEvent.Outcome.ok)
    {
      DemoCalendarActivityBean activityBean = getCurrActivity();
      DemoCalendarActivity activity = activityBean.getActivity();

      if(activity.isAllDayOrDuration())
      {
        TimeZone tz = getTimeZone();
        activity.setStartDay(new Day(_proposedStartDate, tz));
        activity.setEndDay(new Day(_proposedEndDate, tz));
      }
      else
      {
        activity.setStart(_proposedStartDate);
        activity.setEnd(_proposedEndDate);
      }

      RichCalendar calendar = getCalendarComponent();
      RequestContext adfContext = RequestContext.getCurrentInstance();
      adfContext.addPartialTarget(calendar);
    }
  }

  public void createDnDExternalDialogListener (DialogEvent event)
  {
    DialogEvent.Outcome outcome = event.getOutcome();

    if (outcome == DialogEvent.Outcome.ok)
    {
      DemoCalendarActivityBean activityBean = getNewActivity();
      // append to model
      _addActivityToModel(activityBean.getActivity());

      // Check all day flag
      _checkAllDay(activityBean);

      RichCalendar calendar = getCalendarComponent();
      RequestContext adfContext = RequestContext.getCurrentInstance();
      adfContext.addPartialTarget(calendar);
    }
  }


  protected RichCalendar getCalendarComponent()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    UIComponent root = context.getViewRoot();
    return (RichCalendar) root.findComponent(DemoCalendarBean._CALENDAR_COMPONENT_ID);
  }

  // This is the popupFetchListener for the create popup.
  // This will create a new activity with the information that was saved in the calendarListener,
  // which was called first.
  // The activity does not get added to the model until the user clicks ok on the popup - this
  // is done in the dialogListener.
  public void createPopupListener (PopupFetchEvent event)
  {
    if (_calendarEventInfo != null)
    {
      if (CalendarActivity.TimeType.UNKNOWN.equals(_newActivityType))
        _createNewActivity(_calendarEventInfo.getTriggerDate(), _calendarEventInfo.getTimeType(), _calendarEventInfo.getProviderId());        
      else
        _createNewActivity(_calendarEventInfo.getTriggerDate(), _newActivityType, _calendarEventInfo.getProviderId());

      _calendarEventInfo = null;
      _newActivityType = CalendarActivity.TimeType.UNKNOWN;
    }
  }

  // Helpers
  private void _initModel()
  {
    if (this.isUseArabic())
      _modelBean = new DemoCalendarArabicModelBean();
    else
      _modelBean = new DemoCalendarModelBean();
  }


  private void _checkAllDay(DemoCalendarActivityBean activityBean)
  {
    // if its all day but the current activity is not then change it
    DemoCalendarActivity activity = activityBean.getActivity();
    boolean isActivityAllDay = CalendarActivity.TimeType.ALLDAY.equals(activity.getTimeType());

    if (activityBean.isAllDay() && !isActivityAllDay)
    {
      changeTimeToAllDayCalendarActivity(activity);
    }
    // else if its not all-day but the current activtiy is, then changhe it
    else if(!activityBean.isAllDay() && isActivityAllDay)
    {
      changeAllDayToTimeCalendarActivity(activity);
    }
  }

  private void _fixDuration(DemoCalendarActivityBean activityBean)
  {
    DemoCalendarActivity activity = activityBean.getActivity();
    boolean isDuration = CalendarActivity.TimeType.DURATION.equals(activity.getTimeType());
    
    if (isDuration)
    {
      TimeZone tz = getTimeZone();
      Integer hours = activityBean.getDurHours();
      Integer minutes = activityBean.getDurMinutes();
        
      long durationInMillis = hours * _SIXTY_MINUTES_IN_MILLIS + minutes * _SIXTY_SECONDS_IN_MILLIS;
      
      // update all the days with the new duration
      Calendar startCal = Calendar.getInstance(tz);
      startCal.setTime(activity.getStartDate(tz));
      
      Calendar endCal = Calendar.getInstance(tz);
      endCal.setTime(activity.getEndDate(tz));
      
      do
      {
        activity.setDuration(startCal.getTime(), tz, durationInMillis);
        startCal.add(Calendar.DATE, 1);
      }
      while (startCal.compareTo(endCal) < 0 );      
    }    
  }

  public void createDialogListener (DialogEvent event)
  {
    DialogEvent.Outcome outcome = event.getOutcome();

    if (outcome == DialogEvent.Outcome.ok)
    {
      DemoCalendarActivityBean activityBean = getNewActivity();
      // append to model
      _addActivityToModel(activityBean.getActivity());

      // Check all day flag
      _checkAllDay(activityBean);
      
      // update duration if TimeType.DURATION
      _fixDuration(activityBean);

      RichCalendar calendar = getCalendarComponent();
      RequestContext adfContext = RequestContext.getCurrentInstance();
      adfContext.addPartialTarget(calendar);
    }
  }

  private void _addActivityToModel(DemoCalendarActivity activity)
  {
    DemoCalendarModelWrapper model = getCalendarModel();

    if (CalendarActivity.TimeType.ALLDAY.equals(activity.getTimeType()))
    {
      model.addAllDayCalendarActivity(activity);
    }
    else if (CalendarActivity.TimeType.TIME.equals(activity.getTimeType()))
    {
      model.addTimeCalendarActivity(activity);
    }
    else
    {
      model.addDurationCalendarActivity(activity);
    }
  }


  public void editDialogListener (DialogEvent event)
  {
    DialogEvent.Outcome outcome = event.getOutcome();

    if (outcome == DialogEvent.Outcome.ok)
    {
      DemoCalendarActivityBean activityBean = getCurrActivity();
      
      // Check all day flag
      _checkAllDay(activityBean);
      
      // update duration if TimeType.DURATION
      _fixDuration(activityBean);
      
      RichCalendar calendar = getCalendarComponent();
      RequestContext adfContext = RequestContext.getCurrentInstance();
      adfContext.addPartialTarget(calendar);
    }    
  }

  public void providerChanged (ValueChangeEvent vce)
  {
    String oldProviderId = (String) vce.getOldValue ();
    String newProviderId = (String) vce.getNewValue();
    
    DemoCalendarModelWrapper model = getCalendarModel();   
    model.notifyActivityProviderChange (getCurrActivity ().getActivity(), oldProviderId, newProviderId);
    
  }
  public void deleteListener (DialogEvent event)
  {
    DialogEvent.Outcome outcome = event.getOutcome();

    if (outcome == DialogEvent.Outcome.ok)
    {

      DemoCalendarActivityBean currActivityBean = getCurrActivity();
      DemoCalendarModelWrapper model = getCalendarModel();

      // TODO: NEED TO CHANGE THIS TO GET CALENDAR ACTVITIY FROM EVENT
      CalendarActivity activity = model.getActivity(currActivityBean.getProviderId(),
                                                    currActivityBean.getId(),
                                                    null,
                                                    null,
                                                    getTimeZone());
      if (CalendarActivity.TimeType.ALLDAY.equals(activity.getTimeType()))
      {
        model.removeAllDayCalendarActivity(activity);
      }
      else if (CalendarActivity.TimeType.TIME.equals(activity.getTimeType()))
      {
        model.removeTimeCalendarActivity(activity);
      }
      else
      {
        model.removeDurationCalendarActivity(activity);
      }

      setCurrActivity(null);
      RequestContext adfContext = RequestContext.getCurrentInstance();
      adfContext.addPartialTarget(getCalendarComponent());
    }
  }


  /**
   * Listener for the CalendarEvent. This event gets sent when the user acts on free space
   * on the calendar. In the demo, we have popups for create and contextMenu. If the user
   * clicked free space, create a new activity, otherwise save off the trigger date and timeType
   * info in case the end user picks 'create' in the context menu. In that case we
   * create a new activity with the saved state in the popupFetchListener on the create popup.
   * Then if the user presses ok in the create popup, the activity is added to the model.
   * @see #createPopupListener
   */
  public void calendarListener(CalendarEvent ce)
  {
    CalendarActivity.TimeType timeType = ce.getTimeType();
    Date triggerDate = (Date)ce.getTriggerDate().clone();

    TriggerType tt = ce.getTriggerType();

    if (TriggerType.MOUSE.equals(tt))
    {
      // TODO see todo in createPopupListener for some additional info
      this._createNewActivity(triggerDate, timeType, ce.getProviderId());
      _calendarEventInfo = null;
    }
    else
    {
      _calendarEventInfo = new CalendarEventInfo(triggerDate, timeType, ce.getProviderId());
    }
  }

  /**
   * Get the DemoCalendarModelBean to instantiate model
   * @return instance of DemoCalendarModelBean
   */
  public DemoCalendarModelBean getModelBean()
  {
    if (_modelBean == null)
      _initModel();
    return _modelBean;
  }

  private void _addOpenPopupScript(FacesContext context, String popupId, String alignId)
  {
    StringBuilder script = new StringBuilder();
    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ")
          .append("if (!popup.isPopupVisible()) { ")
          .append("var hints = {}; ");
    if (alignId != null)
      script.append("hints[AdfRichPopup.HINT_ALIGN_ID] = '").append(alignId).append("'; ") ;

    script.append("hints[AdfRichPopup.HINT_ALIGN] = AdfRichPopup.ALIGN_AFTER_START; ")
          .append("popup.show(hints);}");

    ExtendedRenderKitService erks =
      Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
    erks.addScript(context, script.toString());
  }

  public String getView()
  {
    return _view;
  }

  public void setCurrActivity(DemoCalendarActivityBean currActivity)
  {
    _currActivity = currActivity;
  }

  public void setNewActivity(DemoCalendarActivityBean newActivity)
  {
    _newActivity = newActivity;
  }

  public DemoCalendarActivityBean getCurrActivity()
  {
    return _currActivity;
  }

  public DemoCalendarActivityBean getNewActivity()
  {
    return _newActivity;
  }

  /**
   * Set time zone and refresh the calendar component
   * @param timeZone
   */
  public void setTimeZoneString(String timeZone)
  {
    TimeZone tz = null;

    if (timeZone != null)
      tz = TimeZone.getTimeZone(timeZone);
    else
      tz = getDefaultTimeZone();

    setTimeZone(tz);
  }

  /**
   * Return time zone info
   * @return
   */
  public String getTimeZoneString()
  {
    TimeZone tz = getTimeZone();
    return tz.getID();
  }


  /**
   * Set the timeZone
   * @param _timeZone
   */
  public void setTimeZone(TimeZone _timeZone)
  {
    this._timeZone = _timeZone;
  }

  /**
   * Get the timeZone
   * @return
   */
  public TimeZone getTimeZone()
  {
    return _timeZone;
  }

  // TODO - the demo shouldn't use this, it should use an iterator for the legend area
  public List<CalendarProvider> getProviders()
  {
    return getModelBean().getCalendarModel().getProviders();
  }

  /**
   * Change Time activity to all day.
   * @param activity
   */
  public void changeTimeToAllDayCalendarActivity(CalendarActivity activity)
  {
    DemoCalendarActivity ca = (DemoCalendarActivity)activity;
    ca.setTimeType(CalendarActivity.TimeType.ALLDAY);

    DemoCalendarModelWrapper model = getCalendarModel();

    model.removeTimeCalendarActivity(ca);
    model.addAllDayCalendarActivity(ca);
  }

  /**
   * change all day activity to Time Activity
   * @param activity
   */
  public void changeAllDayToTimeCalendarActivity(CalendarActivity activity)
  {
    DemoCalendarActivity ca = (DemoCalendarActivity)activity;
    ca.setTimeType(CalendarActivity.TimeType.TIME);

    DemoCalendarModelWrapper model = getCalendarModel();

    model.removeAllDayCalendarActivity(ca);
    model.addTimeCalendarActivity(ca);
  }


  public void providerColorChange(ValueChangeEvent vce)
  {
    UIComponent component = vce.getComponent();
    String providerId = component.getAttributes().get("providerId").toString();
    Color newColor = (Color)vce.getNewValue();
    Color oldColor = (Color)vce.getOldValue();    
    
    _getInstanceStylesBean().providerColorChange (providerId, oldColor, newColor, true);
    
    RequestContext adfContext = RequestContext.getCurrentInstance();
    adfContext.addPartialTarget(getCalendarComponent());
  }

  public void providerFromRampsColorChange(ValueChangeEvent vce)
  {
    UIComponent component = vce.getComponent();
    String providerId = component.getAttributes().get("providerId").toString();
    Color newColor = (Color)vce.getNewValue();
    Color oldColor = (Color)vce.getOldValue();    
    
    _getInstanceStylesBean().providerColorChange (providerId, oldColor, newColor, false);
    
    RequestContext adfContext = RequestContext.getCurrentInstance();
    adfContext.addPartialTarget(getCalendarComponent());
  }

  public void providerEnabledChange(ValueChangeEvent vce)
  {
    RequestContext adfContext = RequestContext.getCurrentInstance();
    adfContext.addPartialTarget(getCalendarComponent());
  }

  public List getColorData()
  {
    return (_getInstanceStylesBean().getColorData());
  }

  public List getColorDataFromRamps()
  {
    return (_getInstanceStylesBean().getColorDataFromRamps());
  }

  public Map<Set<String>, InstanceStyles> getActivityStyles()
  {
    return _getInstanceStylesBean().getActivityStyles (getProviders(), getCalendarComponent());
  }
  
  public Map<Set<String>, InstanceStyles> getActivityStylesFromRamps()
  {
    return _getInstanceStylesBean().getActivityStylesFromRamps (getProviders(), getCalendarComponent());
  }
  

  /* Helpers and private members */

  // create a new activity with the triggerDate and timeType information.
  // if it is allday, then the endDate is midnight.
  // This is called from the popupFetchListener just before the create popup shows.
  private DemoCalendarActivity _createNewActivity(
    Date                       triggerDate,
    CalendarActivity.TimeType  timeType,
    String                     providerId)
  {
    // Get latest activity id
    int activityId = getModelBean().getAndIncreaseActivityIdCounter();

    // if it is all day, then set the endDay to be midnight.
    TimeZone tz = getTimeZone();    
    Calendar endCal = Calendar.getInstance(tz);
    endCal.setTime(triggerDate);
    boolean isAllDay = CalendarActivity.TimeType.ALLDAY.equals(timeType);
    boolean isUnknown = CalendarActivity.TimeType.UNKNOWN.equals(timeType);

    if (isAllDay || isUnknown)
    {
      // default is 1 day for allDay or unknown for end date
      endCal.add(Calendar.DAY_OF_YEAR, 1);
    }
    else
    {
      // default is 1 hour for timed activities for end date
      endCal.add(Calendar.HOUR_OF_DAY, 1);
    }

    DemoCalendarActivity activity = null;
    CalendarProvider provider = (providerId != null)? getModelBean().getCalendarModel().getProvider(providerId) : 
                                                      getModelBean().getCalendarModel().getProviders().get(0);
    
    if (CalendarActivity.TimeType.DURATION.equals(timeType))
    {
      activity = new DemoCalendarActivity(provider,
                               String.valueOf(activityId),
                               new Day(triggerDate, tz), new Day(endCal), _SIXTY_MINUTES_IN_MILLIS, tz); 
    }
    else if (CalendarActivity.TimeType.ALLDAY.equals(timeType))
    {
      activity = new DemoCalendarActivity(provider,
                               String.valueOf(activityId),
                               new Day(triggerDate, tz), new Day(endCal), tz); 
    } 
    else
    {
      activity = new DemoCalendarActivity(provider,
                               String.valueOf(activityId),
                               triggerDate, endCal.getTime(), tz); 
    }

    // for this demo, if the timetype is unknown like it would be when creating an activity by
    // clicking on the List or Month view,
    // then we will set it to ALLDAY. If an app developer was dragging/dropping a calendar activity
    // from elsewhere, he'd probably copy the timeType from the activity into the new activity.
    if (isAllDay || isUnknown)
      activity.setTimeType(CalendarActivity.TimeType.ALLDAY);
    else
      activity.setTimeType(timeType);

    setNewActivity(new DemoCalendarActivityBean(activity, tz, getCalendarModel()));

    return activity;
  }

  public DateCustomizer getDateCustomizer()
  {
    return _dateCustomizer;
  }
  
  public ProviderComparator getProviderComparator()
  {
    return _providerComparator;
  }

  public Date getProposedStartDate()
  {
    return _proposedStartDate;
  }

  public void setUseArabic(boolean _useArabic)
  {
    this._useArabic = _useArabic;
  }

  public boolean isUseArabic()
  {
    return _useArabic;
  }

  public Map<CalendarProvider, DemoCalendarInstanceStylesBean.ProviderData> getProviderData()
  {
    return (_getInstanceStylesBean().getProviderData (getProviders(), getCalendarComponent()));
  }

  public Map<CalendarProvider, DemoCalendarInstanceStylesBean.ProviderData> getProviderDataFromRamps()
  {
    return (_getInstanceStylesBean().getProviderDataFromRamps (getProviders(), getCalendarComponent()));
  }


  public void addNew30MinActivity() 
  {
    // create a 30 minute activity 2:00AM to 2:30AM
    Calendar todayCal = DemoCalendarModelBean.getCalendarInstanceForToday(null, null);
    todayCal.set(Calendar.HOUR_OF_DAY, 2);
    todayCal.set(Calendar.MINUTE, 0);
    todayCal.set(Calendar.SECOND, 0);
    todayCal.set(Calendar.MILLISECOND, 0);
    
    DemoCalendarActivity newActivity = _createNewActivity(todayCal.getTime(), CalendarActivity.TimeType.TIME, null);
    todayCal.add(Calendar.MINUTE, 30);
    
    newActivity.setEnd(todayCal.getTime());
    newActivity.setTitle("New 30 minute activity");

    _addActivityToModel(newActivity);
  }

  public void addNew15MinActivity() 
  {
    // create a 15 minute activity 2:30AM to 2:45AM
    Calendar todayCal = DemoCalendarModelBean.getCalendarInstanceForToday(null, null);
    todayCal.set(Calendar.HOUR_OF_DAY, 2);
    todayCal.set(Calendar.MINUTE, 30);
    todayCal.set(Calendar.SECOND, 0);
    todayCal.set(Calendar.MILLISECOND, 1);
      
    DemoCalendarActivity newActivity = _createNewActivity(todayCal.getTime(), CalendarActivity.TimeType.TIME, null);
    todayCal.add(Calendar.MINUTE, 15);
      
    newActivity.setEnd(todayCal.getTime());
    newActivity.setTitle("New 15 minute activity");

    _addActivityToModel(newActivity);
  }

  public void addNew10MinActivity() 
  {
    // create a 10 minute activity 2:45AM to 2:55AM
    Calendar todayCal = DemoCalendarModelBean.getCalendarInstanceForToday(null, null);
    todayCal.set(Calendar.HOUR_OF_DAY, 2);
    todayCal.set(Calendar.MINUTE, 45);
    todayCal.set(Calendar.SECOND, 0);
    todayCal.set(Calendar.MILLISECOND, 1);
        
    DemoCalendarActivity newActivity = _createNewActivity(todayCal.getTime(), CalendarActivity.TimeType.TIME, null);
    todayCal.add(Calendar.MINUTE, 10);
        
    newActivity.setEnd(todayCal.getTime());
    newActivity.setTitle("New 10 minute activity");

    _addActivityToModel(newActivity);
  }

  public void addNew5MinActivity() 
  {
    // create a 5 minute activity 2:55AM to 3:00AM
    Calendar todayCal = DemoCalendarModelBean.getCalendarInstanceForToday(null, null);
    todayCal.set(Calendar.HOUR_OF_DAY, 2);
    todayCal.set(Calendar.MINUTE, 55);
    todayCal.set(Calendar.SECOND, 0);
    todayCal.set(Calendar.MILLISECOND, 1);
          
    DemoCalendarActivity newActivity = _createNewActivity(todayCal.getTime(), CalendarActivity.TimeType.TIME, null);
    todayCal.add(Calendar.MINUTE, 5);
          
    newActivity.setEnd(todayCal.getTime());
    newActivity.setTitle("New 5 minute activity");

    _addActivityToModel(newActivity);
  }

  public void switchToWeekView() 
  {
    _view = RichCalendar.VIEW_WEEK;
  }

  public void switchToDayView() 
  {
    _view = RichCalendar.VIEW_DAY;
  }

  public void setHourZoom(String hourZoom) 
  {
    this._hourZoom = hourZoom;
  }

  public String getHourZoom() 
  {    
    return _hourZoom;
  }

  public void clearNewActivities() 
  {
    Calendar startRange = DemoCalendarModelBean.getCalendarInstanceForToday(null, null);
    startRange.set(Calendar.HOUR_OF_DAY, 2);
    startRange.set(Calendar.MINUTE, 0);
    startRange.set(Calendar.SECOND, 0);
    startRange.set(Calendar.MILLISECOND, 0);

    Calendar endRange = DemoCalendarModelBean.getCalendarInstanceForToday(null, null);
    endRange.set(Calendar.HOUR_OF_DAY, 3);
    endRange.set(Calendar.MINUTE, 0);
    endRange.set(Calendar.SECOND, 0);
    endRange.set(Calendar.MILLISECOND, 0);

    DemoCalendarModelWrapper model = getCalendarModel();
    List<CalendarActivity> activities = model.getTimeActivities(startRange.getTime(), endRange.getTime(), getTimeZone());
    
    for(CalendarActivity act : activities) 
    {
      model.removeTimeCalendarActivity(act);
    }
  }
  
  public String getCurrentProviderRole()
  {
    return getProviderData().get(_getCurrentStampedCalendarProvider()).getRole();
  }
  
  public String getCurrentProviderHoursForRange()
  {
    CalendarProvider curProvider = _getCurrentStampedCalendarProvider();
    RichCalendar calendar = getCalendarComponent();
    CalendarModel model = calendar.getValue();
    String view = calendar.getView();
    
    TimeZone tz = calendar.getTimeZone();
    if (tz == null)
    {
      tz = AdfFacesContext.getCurrentInstance().getTimeZone();

      if (tz == null)
        tz = TimeZone.getDefault();
    }

    Locale locale = RequestContext.getCurrentInstance().getFormattingLocale();
    if (locale == null)
      locale = FacesContext.getCurrentInstance()
                           .getViewRoot()
                           .getLocale();

    // get the range to get CalendarActivity from the model
    String listType = calendar.getListType();
    int listCount = calendar.getListCount();
    Date activeDay = _getActiveDay(calendar, tz, locale);
    String startDayOfWeek = calendar.getStartDayOfWeek();

    CalendarRenderer.DateRange range =
      CalendarRenderer.getDateRange(activeDay, view, listType, listCount, startDayOfWeek, tz, locale);
    
    List<CalendarActivity> timeActivities = model.getTimeActivities(range.startDate, range.endDate, tz);
    List<CalendarActivity> durationActivities = model.getDurationActivities(range.startDate, range.endDate, tz);
    List<CalendarActivity> allDayActivities = model.getAllDayActivities(range.startDate, range.endDate, tz);

    long durationInMins = 0;

    if (_isTimeTypeEnabled(calendar, CalendarActivity.TimeType.TIME))
      durationInMins += _getTotalDurationByType(curProvider, tz, locale, range, timeActivities, CalendarActivity.TimeType.TIME);

    if (_isTimeTypeEnabled(calendar, CalendarActivity.TimeType.DURATION))
      durationInMins += _getTotalDurationByType(curProvider, tz, locale, range, durationActivities, CalendarActivity.TimeType.DURATION);

    if (_isTimeTypeEnabled(calendar, CalendarActivity.TimeType.ALLDAY))
      durationInMins += _getTotalDurationByType(curProvider, tz, locale, range, allDayActivities, CalendarActivity.TimeType.ALLDAY);
    
    return String.format("%1.2f hours", (durationInMins / 60.0));
  }
  
  private static boolean _isTimeTypeEnabled(
    RichCalendar              component,
    CalendarActivity.TimeType type)
  {
    String[] showActivityTypes = component.getShowActivityTypes();

    if (showActivityTypes == null)
    {
      // Allow all activity types by default
      return true;
    }

    boolean isEnabled = false;

    for(String allowedType : showActivityTypes)
    {
      if (allowedType.equalsIgnoreCase(type.toString()))
      {
        isEnabled = true;
        break;
      }
    }

    return isEnabled;
  }
  
  /**
   * Return the day which the user is viewing
   */
  private static Date _getActiveDay(RichCalendar calendar, TimeZone tz, Locale locale)
  {
    Date activeDay = calendar.getActiveDay();

    if (activeDay == null)
    {
      Calendar activeCal = Calendar.getInstance(tz, locale);
      DateRenderUtils.setCalToNoon(activeCal);
      return activeCal.getTime();
    }

    return activeDay;
  }
  
  private CalendarProvider _getCurrentStampedCalendarProvider()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    Application app = context.getApplication();
    
    return (CalendarProvider)app.getExpressionFactory().createValueExpression(context.getELContext(),
                                                        "#{dateVarStatus.provider}",
                                                        CalendarProvider.class).getValue(context.getELContext());
  }

  private long _getTotalDurationByType(
    CalendarProvider curProvider, 
    TimeZone tz, 
    Locale locale,
    CalendarRenderer.DateRange range, 
    List<CalendarActivity> activities,
    CalendarActivity.TimeType timeType)
  {
    long durationInMinutes = 0;

    Calendar startDate = Calendar.getInstance(tz, locale);
    startDate.setTime(range.startDate);

    Calendar endDate = Calendar.getInstance(tz, locale);
    endDate.setTime(range.endDate);

    for (CalendarActivity activity: activities)
    {
      // Skip other providers
      // Note: This would mean n * m looping, but for demo purposes we do not care to keep things simple
      // A production app would be expected to execute this only once and cache it for the given range,
      if (!curProvider.equals(activity.getProvider()))
        continue;

      Calendar aStartDate = Calendar.getInstance(tz, locale);
      aStartDate.setTime(activity.getStartDate(tz));

      Calendar aEndDate = Calendar.getInstance(tz, locale);
      aEndDate.setTime(activity.getEndDate(tz));

      if (startDate.equals(aStartDate) || startDate.after(aStartDate))
      {
        aStartDate = startDate;
      }

      if (endDate.equals(aEndDate) || endDate.before(aEndDate))
      {
        aEndDate = endDate;
      }

      if (timeType == CalendarActivity.TimeType.TIME)
      {
        // Take time in minutes
        durationInMinutes += (aEndDate.getTimeInMillis() - aStartDate.getTimeInMillis()) / 60000L;
      }
      else if (timeType == CalendarActivity.TimeType.ALLDAY)
      {
        long days = (aEndDate.getTimeInMillis() - aStartDate.getTimeInMillis()) / 86400000L;
        durationInMinutes += days * 480;
      }
      else if (timeType == CalendarActivity.TimeType.DURATION)
      {
        Calendar currentDate = Calendar.getInstance(tz, locale);
        currentDate.setTime(range.startDate);
        
        while (currentDate.before(endDate))
        {
           String porviderId = activity.getProvider().getId();

           // Take time in minutes
           durationInMinutes += activity.getDuration(currentDate.getTime(), tz) / 60000;
           currentDate.add(Calendar.DAY_OF_YEAR, 1);
        }
      }
    }

    return durationInMinutes;
  }


  // inner class to hold the state of the CalendarEvent that we retrieve in our calendarListener
  // method. This will later be used in the actionListener for the create context menu.
  private static class CalendarEventInfo implements Serializable
  {
    public CalendarEventInfo(
      Date                       triggerDate,
      CalendarActivity.TimeType  timeType,
      String                     providerId)
    {
      _triggerDate = triggerDate;
      _timeType    = timeType;
      _providerId  = providerId;
    }


    // we save the 'state' of a CalendarEvent if the triggerType is CONTEXT_MENU
    private Date                       _triggerDate;
    private CalendarActivity.TimeType  _timeType;
    private String                     _providerId;

    public Date getTriggerDate()
    {
      return _triggerDate;
    }

    public CalendarActivity.TimeType getTimeType()
    {
      return _timeType;
    }
    
    public String getProviderId()
    {
      return _providerId;
    }
  }

  private static TimeZone getDefaultTimeZone()
  {
    return TimeZone.getTimeZone("America/Los_Angeles");
  }

  public ArrayList<DnDActivityData> getDndActivitiesValues()
  {
    if (_dndActivitiesValues == null)
    {
      _dndActivitiesValues = new ArrayList<DnDActivityData>(3);
      _dndActivitiesValues.add(new DnDActivityData("New Activity", "loc1", false));
      _dndActivitiesValues.add(new DnDActivityData("Another Activity", "loc2", false));
      _dndActivitiesValues.add(new DnDActivityData("Some Other Activity", "loc3", false));
      _dndActivitiesValues.add(new DnDActivityData("Recurring Acitivity", "loc4", true));
    }
    return _dndActivitiesValues;
  }

  public SelectItem[] getPrioritySelectItems()
  {
    if (_prioritySelectItems == null)
    {
      _prioritySelectItems = new SelectItem[3];

      _prioritySelectItems[0] = new SelectItem(DemoCalendarActivity.Priority.LOW, "Low", null, false);
      _prioritySelectItems[1] = new SelectItem(DemoCalendarActivity.Priority.MEDIUM, "Medium", null, false);
      _prioritySelectItems[2] = new SelectItem(DemoCalendarActivity.Priority.HIGH, "High", null, false);
    }

    return _prioritySelectItems;
  }

   public List getProviderSelectItems ()
   {
     if (_providerSelectItems == null)
     {
       List <CalendarProvider> providerList =  this.getProviders();       
       _providerSelectItems = new ArrayList<SelectItem> (providerList.size());
       for (CalendarProvider p: providerList)
       {
         SelectItem s = new SelectItem (p.getId());
         _providerSelectItems.add (s);
       }
     }  
     return _providerSelectItems;
   }

  public SelectItem[] getAccessSelectItems()
  {
    if (_accessSelectItems == null)
    {
      _accessSelectItems = new SelectItem[3];

      _accessSelectItems[0] = new SelectItem(DemoCalendarActivity.Access.PRIVATE, "Private", null, false);
      _accessSelectItems[1] = new SelectItem(DemoCalendarActivity.Access.CONFIDENTIAL, "Confidential", null, false);
      _accessSelectItems[2] = new SelectItem(DemoCalendarActivity.Access.PUBLIC, "Public", null, false);
    }

    return _accessSelectItems;
  }

  public SelectItem[] getStatusSelectItems()
  {
    if (_statusSelectItems == null)
    {
      _statusSelectItems = new SelectItem[3];

      _statusSelectItems[0] = new SelectItem(DemoCalendarActivity.Status.CANCELLED, "Cancelled", null, false);
      _statusSelectItems[1] = new SelectItem(DemoCalendarActivity.Status.TENTATIVE, "Tentative", null, false);
      _statusSelectItems[2] = new SelectItem(DemoCalendarActivity.Status.CONFIRMED, "Confirmed", null, false);
    }

    return _statusSelectItems;
  }



  public static class DnDActivityData implements Serializable
  {
    private String _title;
    private String _location;
    private boolean _recurring;

    DnDActivityData(String title, String location, boolean recurring)
    {
      this._title = title;
      this._recurring = recurring;
      this._location = location;
    }

    public String getTitle()
    {
      return _title;
    }

    public boolean isRecurring()
    {
      return _recurring;
    }

    public String getLocation()
    {
      return _location;
    }
  }

  /**
   * @return The default order of colors for providers
   */
  public List<Color> getDefaultOrderProviderColors()
  {
    return (_getInstanceStylesBean().getDefaultOrderProviderColors());
  }

  /**
   * @return The default order of colors for providers using CalendarActivityRamps
   */
  public static List<Color> getDefaultOrderProviderColorsFromRamps()
  {
    return DemoCalendarInstanceStylesBean.getDefaultOrderProviderColorsFromRamps ();
  }
  
  /**
   * DemoCalendarInstanceStylesBean returns the view-specific data, e.g. activity styles
   */
  private static DemoCalendarInstanceStylesBean _getInstanceStylesBean ()
  {
    AdfFacesContext adfctxt = AdfFacesContext.getCurrentInstance();  
    DemoCalendarInstanceStylesBean bean =  (DemoCalendarInstanceStylesBean)adfctxt.getViewScope().get("calendarViewStylesBean");  
    if (bean == null)
    {
      bean = new DemoCalendarInstanceStylesBean();
      adfctxt.getViewScope().put("calendarViewStylesBean", bean);
    }
    return bean;    
  }

  
  //*********************************************************************************************
  // START code used in the demo for adding a date picker where we're pushing new values
  // to the component
  //*********************************************************************************************

  public Date getActiveDayCompInstance()
  {    
    RichCalendar calendar = getCalendarComponent();
    return calendar.getActiveDay();
  }
  

  public void setActiveDayCompInstance(Date activeDay)
  {    
    // In certain situations, for example when the built in toolbar buttons are used, 
    // the 'view' and/or 'activeDay' attribute is updated by the framework. However when these 
    // attributes are set by the framework they are only set locally on the
    // component, and the component no longer pulls the value from the EL expression.
    // As long as a local value is set any updates to the back end are ignored.
    //
    // Therefore, in cases where you need to update these attributes you have 2 choices. In both
    // cases you need to add an attributeComponentChange for the change manager. The choices are:
    // 
    //   1. to pull from the back end you have to have a displayChangeListener 
    //      which nulls out the local value on the component so that the component will 
    //      pull data from the back end.
    //   2. push the new value onto the component instance. The code in this method implements this
    //      strategy.
    
    // update the value on the component instance
    RichCalendar calendar = getCalendarComponent();
    calendar.setActiveDay(activeDay);
    
    // Implicitly record a change for 'activeDay' attribute    
    AttributeComponentChange aa =
                    new AttributeComponentChange(RichCalendar.ACTIVE_DAY_KEY.getName(), activeDay);
    RequestContext reqContext = RequestContext.getCurrentInstance();
    FacesContext context = FacesContext.getCurrentInstance();
    reqContext.getChangeManager().addComponentChange(context, calendar, aa); 
  }    
  
  //*********************************************************************************************
  // END code used in the demo for adding a date picker where we're pushing new values
  // to the component
  //*********************************************************************************************
  
  
  //*********************************************************************************************
  // START code used in the demo for adding a date picker where we're pulling the
  // active day from the back end
  //*********************************************************************************************
  private Date _activeDay;

  public void setActiveDay(Date activeDay)
  {
    _activeDay = activeDay;
  }

  public Date getActiveDay()
  {
    return _activeDay;
  }
  
  public void switchToJan1() 
  {
    Calendar cal = DemoCalendarModelBean.getCalendarInstanceForToday(null, null);
    
    cal.set(Calendar.YEAR, 2014);
    cal.set(Calendar.MONTH, 0);
    cal.set(Calendar.DATE, 1);
    cal.set(Calendar.HOUR, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    
    setActiveDay(cal.getTime());
  }
  
  public void displayChangeActiveDayListener(CalendarDisplayChangeEvent dce)
  {
    // In certain situations, for example when the built in toolbar buttons are used, 
    // the 'view' and/or 'activeDay' attribute is updated by the framework. However when these 
    // attributes are set by the framework they are only set locally on the
    // component, and the component no longer pulls the value from the EL expression.
    // As long as a local value is set any updates to the back end are ignored.
    //
    // Therefore, in cases where you need to update these attributes you have 2 choices. In both
    // cases you need to add an attributeComponentChange for the change manager. The choices are:
    // 
    //   1. to pull from the back end you have to have a displayChangeListener 
    //      which nulls out the local value on the component so that the component will 
    //      pull data from the back end.The code in this method implements this strategy.
    //   2. push the new value onto the component instance. 
    
    setActiveDay( dce.getNewActiveDay());   
    
    // null out the value on the component instance
    RichCalendar calendar = getCalendarComponent();
    calendar.setActiveDay(null);
    
    // Implicitly record a change for 'activeDay' attribute    
    AttributeComponentChange aa =
                    new AttributeComponentChange(RichCalendar.ACTIVE_DAY_KEY.getName(), null);
    RequestContext reqContext = RequestContext.getCurrentInstance();
    FacesContext context = FacesContext.getCurrentInstance();
    reqContext.getChangeManager().addComponentChange(context, calendar, aa); 

  }        
  //*********************************************************************************************
  // END code used in the demo for adding a date picker where we're pulling the
  // active day from the back end
  //*********************************************************************************************
  
  private static final long _ALL_DAY_MILLIS = 24*60*60*1000;
  private static final int _SIXTY_SECONDS_IN_MILLIS = 60000;  
  private static final int _SIXTY_MINUTES_IN_MILLIS = 60 * _SIXTY_SECONDS_IN_MILLIS;
  
  // used to populate selectItems for status, priority, and access
  private SelectItem[] _statusSelectItems;
  private List<SelectItem> _providerSelectItems;
  private SelectItem[] _prioritySelectItems;
  private SelectItem[] _accessSelectItems;
  
  private static ArrayList<DnDActivityData> _dndActivitiesValues;
    
  private DemoCalendarActivityBean _currActivity;
  private DemoCalendarActivityBean _newActivity;
  private String _view = null;
  private String _hourZoom = "1";
  private TimeZone _timeZone = getDefaultTimeZone();
  private DemoCalendarModelBean _modelBean;

  private CalendarEventInfo _calendarEventInfo;

  private DateCustomizer _dateCustomizer = new DemoDateCustomizer();
  
  private ProviderComparator _providerComparator = new DemoProviderComparator();

  private Date _proposedStartDate;
  private Date _proposedEndDate;

  private boolean _useArabic = false;
  private CalendarActivity.TimeType _newActivityType = CalendarActivity.TimeType.UNKNOWN;
}

