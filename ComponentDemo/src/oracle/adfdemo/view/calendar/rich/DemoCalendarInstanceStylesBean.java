/* Copyright (c) 2008, 2018, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.calendar.rich;

import java.awt.Color;

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
import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.CopyOnWriteArraySet;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichCalendar;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.model.CalendarActivity;
import oracle.adf.view.rich.model.CalendarModel;
import oracle.adf.view.rich.model.CalendarProvider;
import oracle.adf.view.rich.util.CalendarActivityRamp;
import oracle.adf.view.rich.util.CalendarSkinInstanceStyles;
import oracle.adf.view.rich.util.InstanceStyles;

import oracle.adfdemo.view.calendar.rich.model.DemoCalendarModelWrapper;
import oracle.adfdemo.view.calendar.rich.model.DemoCalendarProvider;

import oracle.adfinternal.view.faces.renderkit.rich.CalendarRenderer;
import oracle.adfinternal.view.faces.renderkit.rich.DateRenderUtils;

import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.style.Selector;
import org.apache.myfaces.trinidad.style.Style;
import org.apache.myfaces.trinidadinternal.style.util.CSSUtils;


/**
 * Bean for returning InstanceStyles and related data.
 *
 * For get*FromRamp() methods, this returns CalendarActivityRamp instances that were hardcoded
 * For get*() methods, this creates the built-in markers beginning with "AFCalendarCategoryX"
 *   which have selectors defined in Alta skin and simple skin (fallback colors, pre-Alta).
 *
 * This bean should be stored in view scope because users can change skin and the cache will
 * need to be rebuilt. If user navigates across calendar views, it re-uses the cached instanceStyles.
 */
public class DemoCalendarInstanceStylesBean {
  //  public constants

  //  constructors (if public)

  public DemoCalendarInstanceStylesBean()
  {
    CalendarSkinInstanceStyles style = null;
    for (int i = 1; i <= 12; i++)
    {
      if (i == 1)
      {
        style = new CalendarSkinInstanceStyles(null);
      } else {
        style = new CalendarSkinInstanceStyles(new String[] { _CALENDAR_CATEGORY_MARKER + i });
      }
      _instanceStylesFromSkin.add(style);
    }
  }
  //  public static methods
  //  public nested class

  public static class ProviderData implements Serializable {
    public ProviderData(CalendarProvider provider, Color color, List<String> providerDetails)
    {
      _provider = provider;
      _color = color;
      _url = providerDetails.get(0);
      _role = providerDetails.get(1);
    }

    public void setColor(Color _color)
    {
      this._color = _color;
    }

    public Color getColor()
    {
      return _color;
    }

    public boolean isEnabled()
    {
      return _provider.getEnabled().equals(CalendarProvider.Enabled.ENABLED);
    }

    public void setEnabled(boolean enabled)
    {
      if (enabled)
        _provider.setEnabled(CalendarProvider.Enabled.ENABLED);
      else
        _provider.setEnabled(CalendarProvider.Enabled.DISABLED);
    }

    public void setUrl(String url)
    {
      _url = url;
    }

    public String getUrl()
    {
      return _url;
    }

    public void setRole(String role)
    {
      _role = role;
    }

    public String getRole()
    {
      return _role;
    }

    private CalendarProvider _provider;
    private Color _color;
    private String _url = null;
    private String _role = null;
  }


  //  public methods

  /**
   * @return Map of Set<String activityTags> -> InstanceStyles for that activity
   */
  public Map<Set<String>, InstanceStyles> getActivityStyles(List<CalendarProvider> providerList, RichCalendar calendar)
  {
    if (RenderingContext.getCurrentInstance() != null)
    {
      if (_activityStyles == null)
      {
        int numProviders = providerList.size();
        _activityStyles = new HashMap<Set<String>, InstanceStyles>(numProviders);
        _providerData = new ConcurrentHashMap<CalendarProvider, ProviderData>(numProviders);
        _initActivityStylesAndProviderData(calendar, providerList, getDefaultOrderProviderColors(), _providerData,
                                           _activityStyles, true);
      }
    } /*end-if rendering context is not null*/
    return _activityStyles;
  }

  /**
   * @return Map of Set<String activityTags> -> InstanceStyles for that activity
   */
  public Map<Set<String>, InstanceStyles> getActivityStylesFromRamps(List<CalendarProvider> providerList,
                                                                     RichCalendar calendar)
  {
    if (_activityStyles == null)
    {
      int numProviders = providerList.size();
      _activityStyles = new HashMap<Set<String>, InstanceStyles>(numProviders);
      _providerData = new ConcurrentHashMap<CalendarProvider, ProviderData>(numProviders);
      _initActivityStylesAndProviderData(calendar, providerList, _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS,
                                         _providerData, _activityStyles, false);
    }
    return _activityStyles;
  }

  public List<Color> getColorData()
  {
    RenderingContext rc = RenderingContext.getCurrentInstance();
    if (rc != null)
      _initColorDataAndDefaultOrderProviderColorsFromSkin();
    return _colorData;
  }

  public List<Color> getColorDataFromRamps()
  {
    _colorDataFromRamps = CalendarActivityRamp.getRampColorKeys();
    return _colorDataFromRamps;
  }

  public List<Color> getDefaultOrderProviderColors()
  {
    if (RenderingContext.getCurrentInstance() != null)
      _initColorDataAndDefaultOrderProviderColorsFromSkin();
    return _defaultOrderProviderColorsFromSkin;
  }

  public static List<Color> getDefaultOrderProviderColorsFromRamps()
  {
    return _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS;
  }

  public Map<CalendarProvider, ProviderData> getProviderData(List<CalendarProvider> providerList, RichCalendar calendar)
  {
    if (RenderingContext.getCurrentInstance() != null)
    {
      if (_providerData == null)
      {
        int numProviders = providerList.size();
        _activityStyles = new HashMap<Set<String>, InstanceStyles>(numProviders);
        _providerData = new ConcurrentHashMap<CalendarProvider, ProviderData>(numProviders);
        _initActivityStylesAndProviderData(calendar, providerList, getDefaultOrderProviderColors(), _providerData,
                                           _activityStyles, true);
      }
    }
    return _providerData;
  }

  public Map<CalendarProvider, ProviderData> getProviderDataFromRamps(List<CalendarProvider> providerList,
                                                                      RichCalendar calendar)
  {
    if (_providerData == null)
    {
      int numProviders = providerList.size();
      _activityStyles = new HashMap<Set<String>, InstanceStyles>(numProviders);
      _providerData = new ConcurrentHashMap<CalendarProvider, ProviderData>(numProviders);
      _initActivityStylesAndProviderData(calendar, providerList, _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS,
                                         _providerData, _activityStyles, false);
    }
    return _providerData;
  }


  public void providerColorChange(String providerId, Color oldColor, Color newColor, boolean useSkin)
  {
    // TODO - should I be creating this every time? used to use (getModelBean().getTags()[0]
    Set<String> providerSet = new CopyOnWriteArraySet<String>();
    providerSet.add(providerId);
    Map<Set<String>, InstanceStyles> activityStyles = _activityStyles;
    InstanceStyles newStyle =
      useSkin ? _skinInstanceStyleFromColorMap.get(newColor) : CalendarActivityRamp.getActivityRamp(newColor);
    activityStyles.put(providerSet, newStyle);

    // Replace styles for {holiday, providerIdOld} or {absence, providerIdOld}
    Set<String> holidaytags = new CopyOnWriteArraySet<String>();
    holidaytags.add(providerId);
    holidaytags.add("holiday");
    InstanceStyles holidaystyles = activityStyles.get(holidaytags);
    if (holidaystyles != null && newStyle != null)
    {
      activityStyles.remove(holidaytags);
      activityStyles.put(holidaytags, newStyle);
    }

    Set<String> absencetags = new CopyOnWriteArraySet<String>();
    absencetags.add(providerId);
    absencetags.add("absence");
    InstanceStyles absencestyles = activityStyles.get(absencetags);
    if (absencestyles != null && newStyle != null)
    {
      activityStyles.remove(absencetags);
      activityStyles.put(absencetags, newStyle);
    }
  }
  //  protected static methods
  //  protected methods
  //  private static methods
  //  private methods

  private void _initColorDataAndDefaultOrderProviderColorsFromSkin()
  {
    if (_colorData == null || _defaultOrderProviderColorsFromSkin == null)
    {
      _colorData = new ArrayList<Color>(_NUMBER_INSTANCE_STYLES_FROM_SKIN);
      _skinInstanceStyleFromColorMap = new ConcurrentHashMap<Color, CalendarSkinInstanceStyles>();

      // Skin defines colors in the provider order, e.g. AFCalendarCategory1, AFCalendarCategory2
      // It is sufficient to re-use the same list as colorData
      for (int i = 0; i < _NUMBER_INSTANCE_STYLES_FROM_SKIN; i++)
      {
        CalendarSkinInstanceStyles styles = _instanceStylesFromSkin.get(i);
        Color representativeColor = styles.getRepresentativeColor();
        if (representativeColor != null)
        {
          _colorData.add(representativeColor);
          _skinInstanceStyleFromColorMap.put(representativeColor, styles);
        }
      }
      _defaultOrderProviderColorsFromSkin = _colorData;
    }
  }

  private void _initActivityStylesAndProviderData(RichCalendar calendar, List<CalendarProvider> providerList,
                                                  List<Color> providerOrderColors,
                                                  Map<CalendarProvider, ProviderData> providerData,
                                                  Map<Set<String>, InstanceStyles> activityStyles, boolean useSkin)
  {
    int numColors = providerOrderColors.size();
    int numProviders = providerList.size();

    Map<String, List<String>> providerDetails = _getProviderDetails(calendar);

    for (int i = 0; i < numProviders; i++)
    {
      Color color = providerOrderColors.get(i % numColors);

      InstanceStyles styles =
        useSkin ? _skinInstanceStyleFromColorMap.get(color) : CalendarActivityRamp.getActivityRamp(color);

      Set<String> tags = new CopyOnWriteArraySet<String>();
      CalendarProvider provider = providerList.get(i);
      tags.add(provider.getId());
      activityStyles.put(tags, styles);
      ProviderData data = new ProviderData(provider, color, providerDetails.get(provider.getId()));
      providerData.put(provider, data);
    }

    // Activities created for allDayActivityOrder need to set tags {provider, holiday/absence}
    Set<String> holidaytags = new CopyOnWriteArraySet<String>();
    holidaytags.add(providerList.get(1).getId());
    InstanceStyles holidaystyles = activityStyles.get(holidaytags);
    holidaytags.add("holiday");
    activityStyles.put(holidaytags, holidaystyles);

    Set<String> absencetags = new CopyOnWriteArraySet<String>();
    absencetags.add(providerList.get(2).getId());
    InstanceStyles absencestyles = activityStyles.get(absencetags);
    absencetags.add("absence");
    activityStyles.put(absencetags, absencestyles);
  }

  private Map<String, List<String>> _getProviderDetails(RichCalendar calendar)
  {
    Map<String, List<String>> providerDetails = new HashMap<String, List<String>>();

    List<String> provider0 = new ArrayList<String>();
    provider0.add("/images/people/7_F.jpg");
    provider0.add("Retail Staff");

    providerDetails.put("me", provider0);

    List<String> provider1 = new ArrayList<String>();
    provider1.add("/images/people/7_M.jpg");
    provider1.add("Engineer");
    providerDetails.put("Sushil", provider1);

    List<String> provider2 = new ArrayList<String>();
    provider2.add("/images/people/0_M.jpg");
    provider2.add("Engineer");
    providerDetails.put("Ashwin", provider2);

    List<String> provider3 = new ArrayList<String>();
    provider3.add("/images/people/4_M.jpg");
    provider3.add("Engineer");
    providerDetails.put("Rahul", provider3);

    List<String> provider4 = new ArrayList<String>();
    provider4.add("/images/people/14_M.jpg");
    provider4.add("Engineer");
    providerDetails.put("Arjun", provider4);

    List<String> provider5 = new ArrayList<String>();
    provider5.add("/images/people/3_M.jpg");
    provider5.add("Engineer");
    providerDetails.put("Sriram", provider5);

    List<String> provider6 = new ArrayList<String>();
    provider6.add("/images/people/5_M.jpg");
    provider6.add("Engineer");
    providerDetails.put("Praveen", provider6);

    return providerDetails;
  }

  //  non-public nested classes

  //  private variables
  private List<Color> _colorData;

  // These point to the last-used objects, and can be code-populated or skin-populated
  // They are used when the RenderingContext isn't available to deduce their values, e.g.
  // during decode.
  // _activityStyles: maps  Set<String activityTags> -> InstanceStyles object
  // _providerData: Maps provider to its ProviderData, info about displaying it.
  private Map<Set<String>, InstanceStyles> _activityStyles = null;
  private Map<CalendarProvider, ProviderData> _providerData = null;

  // Used by code-populated, pre-Alta callers.
  private static final List<Color> _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS = new ArrayList<Color>(12);
  static {
    CalendarActivityRamp redRamp = CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.RED);
    CalendarActivityRamp orangeRamp = CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.ORANGE);
    CalendarActivityRamp blueRamp = CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.BLUE);
    CalendarActivityRamp greenRamp = CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.GREEN);
    CalendarActivityRamp goldRamp = CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.GOLD);
    CalendarActivityRamp tealRamp = CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.TEAL);
    CalendarActivityRamp lavendarRamp = CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.LAVENDAR);
    CalendarActivityRamp seaweedRamp = CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.SEAWEED);
    CalendarActivityRamp indigoRamp = CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.INDIGO);
    CalendarActivityRamp plumRamp = CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.PLUM);
    CalendarActivityRamp limeRamp = CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.LIME);
    CalendarActivityRamp midnightblueRamp =
      CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.MIDNIGHTBLUE);

    _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS.add(redRamp.getRepresentativeColor());
    _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS.add(orangeRamp.getRepresentativeColor());
    _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS.add(blueRamp.getRepresentativeColor());
    _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS.add(greenRamp.getRepresentativeColor());
    _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS.add(goldRamp.getRepresentativeColor());
    _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS.add(tealRamp.getRepresentativeColor());
    _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS.add(lavendarRamp.getRepresentativeColor());
    _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS.add(seaweedRamp.getRepresentativeColor());
    _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS.add(indigoRamp.getRepresentativeColor());
    _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS.add(plumRamp.getRepresentativeColor());
    _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS.add(limeRamp.getRepresentativeColor());
    _DEFAULT_ORDER_PROVIDERCOLORS_FROM_RAMPS.add(midnightblueRamp.getRepresentativeColor());
  }
  private List<Color> _colorDataFromRamps;

  // Used by skin-populated, post-Alta callers
  private List<Color> _defaultOrderProviderColorsFromSkin = null;
  private Map<Color, CalendarSkinInstanceStyles> _skinInstanceStyleFromColorMap;
  private List<CalendarSkinInstanceStyles> _instanceStylesFromSkin =
    new ArrayList<CalendarSkinInstanceStyles>(_NUMBER_INSTANCE_STYLES_FROM_SKIN);


  //  private constants
  private static final String _CALENDAR_CATEGORY_MARKER = "AFCalendarCategory";
  private static final int _NUMBER_INSTANCE_STYLES_FROM_SKIN = 12;

}

