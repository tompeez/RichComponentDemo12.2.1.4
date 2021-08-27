/** Copyright (c) 2008, 2019, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.io.Serializable;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.validator.ValidatorException;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailHeader;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.AutoSaveEvent;
import oracle.adf.view.rich.event.ContextInfoEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.model.NumberRange;
import oracle.adf.view.rich.model.SelectItemSeparator;

import org.apache.myfaces.trinidad.component.UIXEditableValue;
import org.apache.myfaces.trinidad.component.UIXValue;
import org.apache.myfaces.trinidad.convert.DateTimeConverter;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class DemoInputBean
  implements Serializable
{
  public DemoInputBean()
  {
    _manyInitialValues.add("bean");
    _manyInitialValues.add("leaf");
    _manyInitialValues.add("moo");
    _manyInitialValues.add("orange");

    Calendar cal = _getCalendar();
    cal.add(Calendar.YEAR, -1);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    _minDate = cal.getTime();
    cal.add(Calendar.YEAR, 2);
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    cal.set(Calendar.MILLISECOND, 999);
    
    _maxDate = cal.getTime();

    cal.set (Calendar.YEAR, 4712);
    cal.set (Calendar.MONTH, 11);
    cal.set (Calendar.DATE, 31);
    cal.set (Calendar.HOUR_OF_DAY, 0);
    cal.set (Calendar.MINUTE, 0);
    cal.set (Calendar.SECOND, 0);
    cal.set (Calendar.MILLISECOND, 0);
    
    _maximumDateValue =  cal.getTime();
    cal.clear();
    cal.set(2005, 1, 1);
    _date2 = cal.getTime();

    cal.clear();
    cal.set(2005, 3, 8);
    _dateInSummer = cal.getTime();

    cal.clear();
    cal.set(2005, 11, 13);
    _dateInWinter = cal.getTime();

    cal.clear();
    cal.set(1999, 11, 31);
    _y2kDate = cal.getTime();

  }

  public void setMaximumDateValue(Date _maximumDateValue)
  {
    this._maximumDateValue = _maximumDateValue;
  }

  public Date getMaximumDateValue()
  {
    return _maximumDateValue;
  }

  public String getValue()
  {
    return _value;
  }

  public void setValue(String avalue)
  {
    _value = avalue;
  }


  public String getCodeEditorValue()
  {
    return _codeEditorValue;
  }

  public void setCodeEditorValue(String avalue)
  {
    _codeEditorValue = avalue;
  }

  public String getCodeEditorValue2()
  {
    return _codeEditorValue2;
  }

  public void setCodeEditorValue2(String avalue)
  {
    _codeEditorValue2 = avalue;
  }

  public String getRichValue()
  {
    return _richValue;
  }

  public void setRichValue(String avalue)
  {
    _richValue = avalue;
  }

  public String getRichSectionedValue()
  {
    return _richSectionedValue;
  }

  public void setRichSectionedValue(String avalue)
  {
    _richSectionedValue = avalue;
  }

  public String getRichInsertFragment()
  {
    return _RICH_INSERT_VALUE;
  }

  public void resetRichValue()
  {
    _richValue = _DEFAULT_RICH_VALUE;
  }

  public void resetRichValue2()
  {
    _richValue2 = _DEFAULT_RICH_VALUE;
  }

  public String getRichValue2()
  {
    return _richValue2;
  }

  public void setRichValue2(String avalue2)
  {
    _richValue2 = avalue2;
  }

  public UploadedFile getFile()
  {
    return _file;
  }

  public void setValue(UploadedFile avalue)
  {
    _file = avalue;
  }

  public String getRadioValue()
  {
    return _radiovalue;
  }

  public String getRadioValue2()
  {
    return _radiovalue2;
  }

  public void setIntRange(NumberRange intRange)
  {
    this._intRange = intRange;
  }

  public NumberRange getIntRange()
  {
    return _intRange;
  }

  public void setIntSlider(Integer intSlider)
  {
    this._intSlider = intSlider;
  }

  public Integer getIntSlider()
  {
    return _intSlider;
  }

  public Integer getIntSpinbox()
  {
    return _intSpinbox;
  }

  public void setIntSpinbox(Integer avalue)
  {
    _intSpinbox = avalue;
  }

  public void setIntSpinbox2(Integer intSpinbox2)
  {
    this._intSpinbox2 = intSpinbox2;
  }

  public Integer getIntSpinbox2()
  {
    return _intSpinbox2;
  }

  public void setIntSpinbox3(Integer intSpinbox3)
  {
    this._intSpinbox3 = intSpinbox3;
  }

  public Integer getIntSpinbox3()
  {
    return _intSpinbox3;
  }

  public void setIntSpinbox4(Integer intSpinbox4)
  {
    this._intSpinbox4 = intSpinbox4;
  }

  public Integer getIntSpinbox4()
  {
    return _intSpinbox4;
  }

  public void setRangeSlider1(NumberRange rangeSlider)
  {
    this._rangeSlider1 = rangeSlider;
  }

  public NumberRange getRangeSlider1()
  {
    return _rangeSlider1;
  }

  public void setDoubleSlider1(Double doubleSlider1)
  {
    this._doubleSlider1 = doubleSlider1;
  }

  public Double getDoubleSlider1()
  {
    return _doubleSlider1;
  }

  public void setDoubleSpinbox3(Double doubleSpinbox3)
  {
    this._doubleSpinbox3 = doubleSpinbox3;
  }

  public Double getDoubleSpinbox3()
  {
    return _doubleSpinbox3;
  }

  public void setDoubleSpinbox2(Double doubleSpinbox2)
  {
    this._doubleSpinbox2 = doubleSpinbox2;
  }

  public Double getDoubleSpinbox2()
  {
    return _doubleSpinbox2;
  }

  public Number getDoubleSpinbox()
  {
    return _doubleSpinbox;
  }

  public void setDoubleSpinbox(Double avalue)
  {
    _doubleSpinbox = avalue;
  }

  public void setRadioValue(String avalue)
  {
    _radiovalue = avalue;
  }

  public void setRadioValue2(String avalue)
  {
    _radiovalue2 = avalue;
  }

  public String getCompactSelectOneChoiceValue()
  {
    return (_compactOneChoiceValue != null) ? _compactOneChoiceValue : "Monitor";
  }

  public void setCompactSelectOneChoiceValue(String value)
  {
    _compactOneChoiceValue = value;
  }

  /**
   * returns true if PPRNavigation is on.
   */
  public boolean isPPRNavigationOn()
  {
    return !isPPRNavigationOff();
  }

  /**
   * Method listener for af:autoSaveBehavior tag. It will listen for
   * AutoSaveEvent of either AutoSaveType.SAVE or AutoSaveType.DELETE
   */
  public void autoSaveEvent(AutoSaveEvent val)
  {

  }

  /**
   * returns true if PPRNavigation is off.
   */
  public boolean isPPRNavigationOff()
  {
    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
    String option = context.getInitParameter(_PPR_NAVIGATION_OPTIONS_PARAM);
    return ("off".equalsIgnoreCase(option));
  }

  private final String _PPR_NAVIGATION_OPTIONS_PARAM = "oracle.adf.view.rich.pprNavigation.OPTIONS";

  public void handlePanelHeaderSwitcher(ValueChangeEvent e)
  {
    UIComponent SelectOneChoiceIconCompact = (RichSelectOneChoice) e.getSource();
    RichPanelHeader header = (RichPanelHeader) SelectOneChoiceIconCompact.getParent();
    Object newValue = e.getNewValue();
    String newText = (newValue == null ? "" : newValue.toString());
    header.setText(newText);
    header.setIcon((String) _selectOneChoiceIconMap.get(newText));
  }

  public void handleShowDetailHeaderSwitcher(ValueChangeEvent e)
  {
    UIComponent SelectOneChoiceIconCompact = (RichSelectOneChoice) e.getSource();
    RichShowDetailHeader header = (RichShowDetailHeader) SelectOneChoiceIconCompact.getParent();
    Object newValue = e.getNewValue();
    String newText = (newValue == null ? "" : newValue.toString());
    header.setText(newText);
    header.setIcon((String) _selectOneChoiceIconMap.get(newText));
  }

  public String getChoiceValue()
  {
    return _choicevalue;
  }

  public void setChoiceValue(String value)
  {
    _choicevalue = value;
  }

  public String getChoiceValue16()
  {
    return _choicevalue16;
  }

  public void setChoiceValue16(String value)
  {
    _choicevalue16 = value;
  }

  public String getChoiceInsertText()
  {
    return _choiceinserttext;
  }

  public void setChoiceInsertText(String value)
  {
    _choiceinserttext = value;
  }

  public String getInsertBeanText()
  {
    return _insertbeantext;
  }

  public void setInsertBeanText(String value)
  {
    _insertbeantext = value;
  }

  public String getSelectItemValue()
  {
    return _selectitemvalue;
  }

  public void setSelectItemValue(String value)
  {
    _selectitemvalue = value;
  }

  public Date getDate()
  {
    return _date;
  }

  public void setDate(Date value)
  {
    _date = value;
  }

  public Date getDate2()
  {
    return _date2;
  }

  public void setDate2(Date value)
  {
    _date2 = value;
  }

  public Date getNullDate()
  {
    return _nullDate3;
  }

  public void setNullDate(Date value)
  {
    _nullDate3 = value;
  }

  public void setDate3(Date date3)
  {
    this._date3 = date3;
  }

  public Date getDate3()
  {
    return _date3;
  }

  public void setDate4(Date date4)
  {
    this._date4 = date4;
  }

  public Date getDate4()
  {
    return _date4;
  }

  public void setDate5(Date date5)
  {
    this._date5 = date5;
  }

  public Date getDate5()
  {
    return _date5;
  }

  public void setDate6(Date date6)
  {
    this._date6 = date6;
  }

  public Date getDate6()
  {
    return _date6;
  }

  public void setDate7(Date date7)
  {
    this._date7 = date7;
  }

  public Date getDate7()
  {
    return _date7;
  }

  public void setDate8(Date date8)
  {
    this._date8 = date8;
  }

  public Date getDate8()
  {
    return _date8;
  }

  public void setDate9(Date date9)
  {
    this._date9 = date9;
  }

  public Date getDate9()
  {
    return _date9;
  }

  public void setTime(Date time)
  {
    this._time = time;
  }

  public Date getTime()
  {
    return _time;
  }

  public void resetDate(ActionEvent a)
  {
    setTime(new Date());
  }

  public void handleTime(ValueChangeEvent e)
  {
    Date newDate = (Date) e.getNewValue();
    Date oldDate = (Date) e.getOldValue();
    ((UIXEditableValue) e.getComponent()).setValue(_getNewTimeFromDate(newDate, oldDate));
  }

  private Date _getNewTimeFromDate(Date newDate, Date oldDate)
  {
    TimeZone tz = AdfFacesContext.getCurrentInstance().getTimeZone();

    if (tz == null)
      tz = TimeZone.getDefault();

    Calendar newcal = Calendar.getInstance(tz);
    newcal.setTime(newDate);

    Calendar cal = Calendar.getInstance(tz);
    cal.setTime(oldDate);

    cal.set(Calendar.HOUR_OF_DAY, newcal.get(Calendar.HOUR_OF_DAY));
    cal.set(Calendar.MINUTE, newcal.get(Calendar.MINUTE));
    cal.set(Calendar.SECOND, newcal.get(Calendar.SECOND));

    return cal.getTime();
  }

  private Date _getNewTime(Date date, int hours, int minutes, int seconds)
  {
    TimeZone tz = AdfFacesContext.getCurrentInstance().getTimeZone();

    if (tz == null)
      tz = TimeZone.getDefault();

    Calendar cal = Calendar.getInstance(tz);
    cal.setTime(date);

    cal.set(Calendar.HOUR_OF_DAY, hours);
    cal.set(Calendar.MINUTE, minutes);
    cal.set(Calendar.SECOND, seconds);

    return cal.getTime();
  }

  public void handleAllday(ValueChangeEvent e)
  {
    UIComponent comp = e.getComponent();
    RichInputText start = (RichInputText) comp.findComponent("time1");
    RichInputText end = (RichInputText) comp.findComponent("time2");

    Date startDate = (Date) start.getValue();
    Date endDate = (Date) end.getValue();

    start.resetValue();
    end.resetValue();

    setTime1(_getNewTime(startDate, 0, 0, 0));
    setTime2(_getNewTime(endDate, 23, 59, 59));
  }

  public void setDate10(Date date10)
  {
    this._date10 = date10;
  }

  public Date getDate10()
  {
    return _date10;
  }

  public void setDate11(Date date11)
  {
    this._date11 = date11;
  }

  public Date getDate11()
  {
    return _date11;
  }

  public void setDate12(Date date12)
  {
    this._date12 = date12;
  }

  public Date getDate12()
  {
    return _date12;
  }

  public void setDate13(Date date13)
  {
    this._date13 = date13;
  }

  public Date getDate13()
  {
    return _date13;
  }

  public void setDate14(Date date14)
  {
    this._date14 = date14;
  }

  public Date getDate14()
  {
    return _date14;
  }

  public void setDateInSummer(Date dateInSummer)
  {
    this._dateInSummer = dateInSummer;
  }

  public Date getDateInSummer()
  {
    return _dateInSummer;
  }

  public void setDateInWinter(Date dateInWinter)
  {
    this._dateInWinter = dateInWinter;
  }

  public Date getDateInWinter()
  {
    return _dateInWinter;
  }

  public void setMinDate(Date minDate)
  {
    this._minDate = minDate;
  }

  public Date getMinDate()
  {
    return _minDate;
  }

  public void setMaxDate(Date maxDate)
  {
    this._maxDate = maxDate;
  }

  public Date getMaxDate()
  {
    return _maxDate;
  }

  public void setY2kDate(Date y2kDate)
  {
    this._y2kDate = y2kDate;
  }

  public Date getY2kDate()
  {
    return _y2kDate;
  }

  public void setEstTimeZone(TimeZone estTimeZone)
  {
    this._estTimeZone = estTimeZone;
  }

  public TimeZone getEstTimeZone()
  {
    return _estTimeZone;
  }

  public void setGmtTimeZone(TimeZone gmtTimeZone)
  {
    this._gmtTimeZone = gmtTimeZone;
  }

  public TimeZone getGmtTimeZone()
  {
    return _gmtTimeZone;
  }

  public void setManyInitialValues(List manyInitialValues)
  {
    this._manyInitialValues = manyInitialValues;
  }

  public List getManyInitialValues()
  {
    return _manyInitialValues;
  }

  public String getListValue()
  {
    return _listvalue;
  }

  public void setListValue(String value)
  {
    _listvalue = value;
  }

  public Integer getListValue2()
  {
    return _listvalue2;
  }

  public void setListValue2(Integer value)
  {
    _listvalue2 = value;
  }

  public List getManyListValue()
  {
    return _manylistvalue;
  }

  public String getManyListString()
  {
    if (_manylistvalue != null)
      return _manylistvalue.toString();
    return null;
  }

  public void setManyListValue(List value)
  {
    _manylistvalue = value;
  }

  public void setValues(List<String> values)
  {
    this._values = values;
  }

  public List<String> getValues()
  {
    return _values;
  }

  public List getManyListValue1()
  {
    return _manylistvalue1;
  }

  public String getManyListString1()
  {
    if (_manylistvalue1 != null)
      return _manylistvalue1.toString();
    return null;
  }

  public void setManyListValue1(List value)
  {
    _manylistvalue1 = value;
  }

  public Object[] getManyListValue2()
  {
    return _manylistvalue2;
  }

  public SelectItem[] getSelectItems()
  {
    if (_selectItems == null)
    {
      _selectItems = new SelectItem[4];

      SelectItem[] colas = new SelectItem[2];
      colas[0] = new SelectItem("coke", "coke", null, false);
      colas[1] = new SelectItem("pepsi", "pepsi", null, false);
      _selectItems[0] = new SelectItemGroup("fizz", "cola", false, colas);
      _selectItems[0].setValue("fizz");
      _selectItems[1] = new SelectItemSeparator();
      _selectItems[2] = new SelectItem("barley", "beer", null, true);
      _selectItems[3] = new SelectItem("lemon", "lemonade", null, false);
    }

    return _selectItems;
  }

  public SelectItem[] getSelectItemsWithNoSelectionItem()
  {
    if (_selectItemsWithNoSel == null)
    {
      _selectItemsWithNoSel = new SelectItem[6];

      _selectItemsWithNoSel[0] = new SelectItem("none", "[No Selection]", null, false, false, true);
      _selectItemsWithNoSel[1] = new SelectItem("coke", "coke", null, false);
      _selectItemsWithNoSel[2] = new SelectItem("pepsi", "pepsi", null, false);
      _selectItemsWithNoSel[3] = new SelectItemSeparator();
      _selectItemsWithNoSel[4] = new SelectItem("barley", "beer", null, true);
      _selectItemsWithNoSel[5] = new SelectItem("lemon", "lemonade", null, false);
    }

    return _selectItemsWithNoSel;
  }

  public SelectItem[] getSelectItems2()
  {
    if (_selectItems2 == null)
    {
      _selectItems2 = new SelectItem[4];

      _selectItems2[0] = new SelectItem(new Integer(1), "1", null, false);
      _selectItems2[1] = new SelectItem(new Integer(20), "20", null, false);
      _selectItems2[2] = new SelectItem(new Integer(50), "50", null, false);
      _selectItems2[3] = new SelectItem(new Integer(300), "300", null, false);
    }

    return _selectItems2;
  }

  public String createString(Object[] arr)
  {
    if (arr != null)
    {
      String values = "[";

      for (int i = 0; i < arr.length; i++)
      {
        values = values + arr[i];
        if (i < arr.length - 1)
          values = values + ",";
      }

      values = values + "]";

      return values;
    }

    return null;
  }

  public String getManyListString2()
  {

    return createString(_manylistvalue2);
  }

  public void setManyListValue2(Object[] value)
  {
    _manylistvalue2 = value;
  }

  public String[] getManyListValue3()
  {
    return _manylistvalue3;
  }

  public String getManyListString3()
  {
    return createString(_manylistvalue3);
  }

  public void setManyListValue3(String[] value)
  {
    _manylistvalue3 = value;
  }

  public List getManyListValue4()
  {
    if (_manylistvalue4 == null)
    {
      _manylistvalue4 = new ArrayList(2);

      _manylistvalue4.add(new Long(1));
      _manylistvalue4.add(new Long(300));
    }
    return _manylistvalue4;
  }

  public String getManyListString4()
  {
    if (_manylistvalue4 != null)
      return _manylistvalue4.toString();
    return null;
  }

  public void setManyListValue4(List value)
  {
    _manylistvalue4 = value;
  }

  public List getManyListValue5()
  {
    return _manylistvalue5;
  }

  public String getManyListString5()
  {
    if (_manylistvalue5 != null)
      return _manylistvalue5.toString();
    return null;
  }

  public void setManyListValue5(List value)
  {
    _manylistvalue5 = value;
  }

  public boolean getBoolean()
  {
    return _boolean;
  }

  public void setBoolean(boolean aBoolean)
  {
    _boolean = aBoolean;
  }

  public boolean getBoolean2()
  {
    return _boolean2;
  }

  public void setBoolean2(boolean aBoolean)
  {
    _boolean2 = aBoolean;
  }

  public boolean getBoolean3()
  {
    return _boolean3;
  }

  public void setBoolean3(boolean aBoolean)
  {
    _boolean3 = aBoolean;
  }

  public boolean getBoolean4()
  {
    return _boolean4;
  }

  public void setBoolean4(boolean aBoolean)
  {
    _boolean4 = aBoolean;
  }

  public boolean getBoolean5()
  {
    return _boolean5;
  }

  public void setBoolean5(boolean aBoolean)
  {
    _boolean5 = aBoolean;
  }

  public boolean getBoolean6()
  {
    return _boolean6;
  }

  public void setBoolean6(boolean aBoolean)
  {
    _boolean6 = aBoolean;
  }

  public Boolean getTriState()
  {
    return _TriState;
  }

  public void setTriState(Boolean aBoolean)
  {
    _TriState = aBoolean;
  }


  public void setSuggestedValues(List values)
  {
    _suggestedValues = values;
  }

  public List getSuggestedValues()
  {
    if (_suggestedValues == null)
    {
      _suggestedValues = getDefaultCityList();
    }

    return _suggestedValues;
  }

  public void setSuggestedValues2(List values)
  {
    _suggestedValues2 = values;
  }

  public List getSuggestedValues2()
  {
    if (_suggestedValues2 == null)
    {
      _suggestedValues2 = getDefaultCityList();
    }

    return _suggestedValues2;
  }

  public void setSuggestedValues3(List values)
  {
    _suggestedValues3 = values;
  }

  public List getSuggestedValues3()
  {
    if (_suggestedValues3 == null)
    {
      _suggestedValues3 = new ArrayList();

      // if I just add Date objects directly then
      //in the add() method below values.contains() returns false
      // when I want it to return true.
      // to make sure that doesn't happen use the converter

      FacesContext context = FacesContext.getCurrentInstance();
      UIViewRoot view = context.getViewRoot();
      UIXValue formcontrol2 = (UIXValue) view.findComponent("formcontrol2");
      Converter converter = formcontrol2.getConverter();
      if (converter == null)
        converter = context.getApplication().createConverter(Date.class);
      Calendar cal = _getCalendar();
      cal.set(2005, 4, 14);
      String date1 = converter.getAsString(context, formcontrol2, cal.getTime());
      cal.clear();
      cal.set(2001, 6, 21);
      String date2 = converter.getAsString(context, formcontrol2, cal.getTime());
      _suggestedValues3.add(converter.getAsObject(context, formcontrol2, date1));
      _suggestedValues3.add(converter.getAsObject(context, formcontrol2, date2));
    }

    return _suggestedValues3;
  }

  public void setPreferredTimeZone(TimeZone _preferredTimeZone)
  {
    this._preferredTimeZone = _preferredTimeZone;

    // 8788871 - inputdate does not update when underlying timezone updated
    // This is as if the user picked a timezone, and it sets a local value
    // which takes precedence over the value from the server/EL-binding.
    // Clearing out the timezone on the converters removes the local value
    // so the server's value (preferredTimeZone) will be used when rendering.
    DateTimeConverter conv1 = (DateTimeConverter) _boundDate1.getConverter();
    conv1.setTimeZone(null);

    DateTimeConverter conv2 = (DateTimeConverter) _boundDate2.getConverter();
    conv2.setTimeZone(null);

  }

  public TimeZone getPreferredTimeZone()
  {
    return _preferredTimeZone;
  }

  public void setPreferredTimeZoneId(String _preferredTimeZoneId)
  {
    if (_supportedTzs.contains(_preferredTimeZoneId))
    {
      TimeZone tz = TimeZone.getTimeZone(_preferredTimeZoneId);
      setPreferredTimeZone(tz);
      this._preferredTimeZoneId = _preferredTimeZoneId;
    }
    else
    {
      _LOG.warning("setUncommonTimeZoneId asked to set unrecognized timezone id " +
                   _preferredTimeZoneId + ", ignoring.");
    }
  }

  public String getPreferredTimeZoneId()
  {
    return _preferredTimeZoneId;
  }

  public String getChatText()
  {
    if (_chatText == null)
    {
      SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
      _chatText = "[" + ft.format(new Date()) + "] <b style='color:red'>Leslie Hann</b> Hello!";
    }

    return _chatText;
  }

  public void setChatMsg(String msg)
  {
    SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
    _chatText +=
      "<br/>[" + ft.format(new Date()) + "] <b style='color:blue'>Jamie Vogan</b> " + msg;
  }

  public String getChatMsg()
  {
    return "";
  }

  public void setBoundDate1(RichInputDate _boundDate1)
  {
    this._boundDate1 = _boundDate1;
  }

  public RichInputDate getBoundDate1()
  {
    return _boundDate1;
  }

  public void setBoundDate2(RichInputDate _boundDate2)
  {
    this._boundDate2 = _boundDate2;
  }

  public RichInputDate getBoundDate2()
  {
    return _boundDate2;
  }


  public void change(ValueChangeEvent vce)
  {
    Object newValue = vce.getNewValue();

    UIComponent comp = vce.getComponent();
    List suggestedValues = (List) comp.getAttributes().get("suggestedValues");

    add(newValue, suggestedValues, 7);
  }

  public void changeAddressType(ValueChangeEvent vce)
  {

    setChoiceValue16(vce.getNewValue().toString());
    FacesContext.getCurrentInstance().renderResponse();
  }

  public void fileUploaded(ValueChangeEvent event)
  {
    Object eventValue = event.getNewValue();
    _LOG.warning("Handling the following ValueChangeEvent: " + event);
    if (eventValue != null && eventValue instanceof UploadedFile)
    {
      /*
      UploadedFile file = (UploadedFile) event.getNewValue();
      FacesContext context = FacesContext.getCurrentInstance();

//      FacesMessage message = new FacesMessage(
//         "Uploaded file " + file.getFilename() +
//         " (" + file.getLength() + " bytes)");
//      context.addMessage(event.getComponent().getClientId(context), message);
*/
    }
  }

  public static List getDefaultCityList()
  {
    List values = new ArrayList();
    values.add("Las Vegas");
    values.add("Los Angeles");
    values.add("San Diego");
    values.add("San Francisco");
    values.add("Seattle");
    return values;
  }

  public static void add(Object newValue, List values, int maxLength)
  {
    if (values != null)
    {
      if (values.contains(newValue))
      {
        values.remove(newValue);
      }

      values.add(0, newValue);


      int size = values.size();
      if (size > maxLength)
      {
        for (int i = size - 1; i > maxLength - 1; i--)
          values.remove(i);
      }

    }
  }

  public void contextInfoListener(ContextInfoEvent cie)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    context.getApplication().getNavigationHandler().handleNavigation(context, null, "guide");
  }

  public void contextInfoListener2(ContextInfoEvent cie)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    UIComponent source = (UIComponent) cie.getSource();
    String alignId = source.getClientId(context);
    UIComponent parent = source.getParent();

    // don't open the popup if the parent isn't valid
    if (parent instanceof EditableValueHolder)
    {
      if (!((EditableValueHolder) parent).isValid())
        return;
    }

    String popupId = source.findComponent("binky5").getClientId(context);

    StringBuilder script = new StringBuilder();
    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ").append("if (!popup.isPopupVisible()) { ").append("var hints = {}; ").append("hints[AdfRichPopup.HINT_ALIGN_ID] = '").append(alignId).append("'; ").append("hints[AdfRichPopup.HINT_ALIGN] = AdfRichPopup.ALIGN_BEFORE_START; ").append("popup.show(hints);}");
    ExtendedRenderKitService erks =
      Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
    erks.addScript(context, script.toString());
  }

  public void reset(ActionEvent action)
  {
    FacesMessage parentMessage =
      new FacesMessage(FacesMessage.SEVERITY_INFO, "This is a faces message.",
                       "This is the actionListener for the commandLink. The value has been reset automatically with the resetActionListener to: " +
                       getValue());
    FacesContext facesContext = FacesContext.getCurrentInstance();
    facesContext.addMessage(null, parentMessage);
  }

  public void resetForm(ActionEvent action)
  {
    FacesMessage parentMessage =
      new FacesMessage(FacesMessage.SEVERITY_INFO, "This is a faces message.",
                       "This is the restListener for the action event type. " +
                       "The editable values in the parent form have been reset to: " + getValue());
    FacesContext facesContext = FacesContext.getCurrentInstance();
    facesContext.addMessage(null, parentMessage);
  }

  public void resetPopupClosed(PopupCanceledEvent event)
  {
    FacesMessage parentMessage =
      new FacesMessage(FacesMessage.SEVERITY_INFO, "This is a faces message.",
                       "This is the restListener for the popupCanceled event type. " +
                       "The editable values in the popup have been reset to: " + getValue());
    FacesContext facesContext = FacesContext.getCurrentInstance();
    facesContext.addMessage(null, parentMessage);
  }

  public String getMixedStr1()
  {
    return _mixedStr1;
  }

  public String getMixedStr2()
  {
    return _mixedStr2;
  }

  public String getMixedStr3()
  {
    return _mixedStr3;
  }

  public String getRtlStr1()
  {
    return _rtlStr1;
  }

  public String getRtlStr2()
  {
    return _rtlStr2;
  }

  public String getRtlStr3()
  {
    return _rtlStr3;
  }

  public String getCreditCardNumber()
  {
    return this._ccNumber;
  }

  public void setCreditCardNumber(String cc)
  {
    this._ccNumber = cc;
  }

  public boolean getShowTime()
  {
    return _showTime;
  }

  public void setShowTime(boolean showTime)
  {
    _showTime = showTime;
  }

  public void useDateConverter()
  {
    setShowTime(false);
  }

  public void useDateTimeConverter()
  {
    setShowTime(true);
  }

  private Calendar _getCalendar()
  {
    Calendar cal = Calendar.getInstance();
    TimeZone tz = AdfFacesContext.getCurrentInstance().getTimeZone();
    if (tz != null)
      cal.setTimeZone(tz);
    return cal;
  }

  public void validateServerOnly(FacesContext context, UIComponent toValidate, Object value)
    throws ValidatorException
  {
    if (value == null || value.toString().trim().isEmpty())
      return;

    if (!"server".equals(value))
    {
      throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                    "This is an error message.",
                                                    "The only legal value is the word 'server'."));
    }
  }
  
  public void obstinateValidator(FacesContext facesContext, UIComponent uIComponent, Object value)
  {
    if (value == null)
      return;

    StringBuilder msg = new StringBuilder();
    msg.append("Entered the value ").append(value).append(" is invalid.").append(" Only an empty value is valid.");
    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                  "Obstinate Validator", msg.toString()));
  }
  
  public void setTime1(Date _time1)
  {
    this._time1 = _time1;
  }

  public Date getTime1()
  {
    return _time1;
  }

  public void setTime2(Date _time2)
  {
    this._time2 = _time2;
  }

  public Date getTime2()
  {
    return _time2;
  }

  public void setSubformValue1(String _subformValue1)
  {
    this._subformValue1 = _subformValue1;
  }

  public String getSubformValue1()
  {
    return _subformValue1;
  }

  public void setSubformValue2(String _subformValue2)
  {
    this._subformValue2 = _subformValue2;
  }

  public String getSubformValue2()
  {
    return _subformValue2;
  }

  public void setSubformValue3(String _subformValue3)
  {
    this._subformValue3 = _subformValue3;
  }

  public String getSubformValue3()
  {
    return _subformValue3;
  }

  public void setSubformValue4(String _subformValue4)
  {
    this._subformValue4 = _subformValue4;
  }

  public String getSubformValue4()
  {
    return _subformValue4;
  }
  public String getToolbar()
  {
    return this._toolbar;
  }
  public void setToolbar(String toolbar)
  {
    this._toolbar = toolbar;
  }
  
  private transient UploadedFile _file;
  private String _value;
  private String _subformValue1;
  private String _subformValue2;
  private String _subformValue3;
  private String _subformValue4;
  private String _chatText;
  private String _ccNumber = "1234567890123456";
  private String _codeEditorValue = _DEFAULT_CODE_EDITOR_VALUE;
  private String _codeEditorValue2 = _DEFAULT_CODE_EDITOR_VALUE;
  private String _richValue = _DEFAULT_RICH_VALUE;
  private String _richValue2 = _DEFAULT_RICH_VALUE;
  private String _richSectionedValue = _RICH_SECTIONED_VALUE;
  private String _radiovalue;
  private String _radiovalue2;
  private String _choicevalue;
  private String _choicevalue16 = "US";
  private String _choiceinserttext = "Some Text.";
  private String _insertbeantext = "Some \"Backing Bean <text>\"";
  private String _compactOneChoiceValue;
  private String _selectitemvalue;
  private String _preferredTimeZoneId = "Asia/Tokyo";
  private Date _date = new Date();
  private Date _date2;
  private Date _nullDate3;
  private Date _date3 = new Date();
  private Date _date4 = new Date();
  private Date _date5 = new Date();
  private Date _date6 = new Date();
  private Date _date7 = new Date();
  private Date _date8 = new Date();
  private Date _date9 = new Date();
  private Date _date10 = new Date();
  private Date _date11 = new Date();
  private Date _date12 = new Date();
  private Date _date13 = new Date();
  private Date _date14 = new Date();
  private Date _dateInSummer;
  private Date _dateInWinter;
  private Date _y2kDate;
  private Date _minDate;
  private Date _maxDate;
  private Date _maximumDateValue;
  private Date _time = new Date();
  private Date _time1 = new Date();
  private Date _time2 = new Date();
  private String _listvalue;
  private Integer _listvalue2;
  private NumberRange _intRange = new NumberRange(new Integer(2), new Integer(4));
  private Integer _intSlider = 5;
  private Integer _intSpinbox = 1979;
  private Integer _intSpinbox2 = 2010;
  private Integer _intSpinbox3 = 2010;
  private Integer _intSpinbox4 = 2010;
  private Double _doubleSpinbox = 25.0;
  private Double _doubleSpinbox2 = 2000.0;
  private Double _doubleSpinbox3 = 1.0;
  private Double _doubleSlider1 = 4.0;
  private NumberRange _rangeSlider1 = new NumberRange(new Double(2), new Double(6));
  private boolean _boolean = false;
  private boolean _boolean2 = false;
  private boolean _boolean3 = false;
  private boolean _boolean4 = false;
  private boolean _boolean5 = false;
  private boolean _boolean6 = false;
  private boolean _showTime = true;
  private Boolean _TriState;
  private List _manyInitialValues = new ArrayList();
  private List _manylistvalue;
  private List _manylistvalue1;
  private transient Object[] _manylistvalue2;
  private String[] _manylistvalue3;
  private List _manylistvalue4;
  private List _manylistvalue5;
  private List<String> _values = Arrays.asList(new String[] { "A" });
  private SelectItem[] _selectItems;
  private SelectItem[] _selectItems2;
  private SelectItem[] _selectItemsWithNoSel;
  private List _suggestedValues;
  private List _suggestedValues2;
  private List _suggestedValues3;
  private List<String> _supportedTzs = Arrays.asList(TimeZone.getAvailableIDs());
  private TimeZone _estTimeZone = TimeZone.getTimeZone("America/New_York");
  private TimeZone _gmtTimeZone = TimeZone.getTimeZone("GMT");
  private TimeZone _preferredTimeZone = TimeZone.getTimeZone("Asia/Tokyo");
  private transient RichInputDate _boundDate1;
  private transient RichInputDate _boundDate2;
  private String _mixedStr1 = "a1a \u0627\u0646\u062a a2a \u0642\u0627\u0644 a3a.123";
  private String _mixedStr2 = "b1b \u0627\u0644\u0645 b2b \u0632\u064a\u062f b3b..234";
  private String _mixedStr3 = "c1c \u0645\u0639\u0644 c2c \u0648\u0645\u0627\u062a c3c...345";
  private String _rtlStr1 = "\u0627\u0646\u062a\u0642\u0627\u0644.123";
  private String _rtlStr2 = "\u0627\u0644\u0645\u0632\u064a\u062f..234";
  private String _rtlStr3 = "\u0645\u0639\u0644\u0648\u0645\u0627\u062a...345";
  private static final HashMap _selectOneChoiceIconMap;

  private static final String _DEFAULT_CODE_EDITOR_VALUE = "var foo = 2;";
  private static final String _DEFAULT_RICH_VALUE =
    "<font color=\"blue\" face=\"Comic Sans MS,Comic Sans,cursive\" size=\"4\">Hello</font> world.<br/>This <em>is</em> <b>for<sup>matt</sup>ed</b> text!!!";
  private static final String _RICH_INSERT_VALUE =
    "<p align=\"center\" style=\"border: 1px solid gray; margin: 5px; padding: 5px;\">" +
    "<font size=\"4\"><span style=\"font-family: Comic Sans MS,Comic Sans,cursive;\">Store Hours</span></font><br/>\n" +
    "<font size=\"1\">Monday through Friday 'til 8:00 pm</font><br/>\n" +
    "<font size=\"1\">Saturday &amp; Sunday 'til 5:00 pm</font>" + "</p>";

  private static final String _RICH_SECTIONED_VALUE =
    "<div>\n" + "    <h2>\n" + "      <a id=\"Introduction\"></a>Introduction</h2>\n" +
    "    <p>\n" +
    "      The ADF Table component is used to display a list of structured data. For example,\n" +
    "      if we have a data structure called Person that has two properties - firstname and\n" +
    "      lastname, we could use a Table with two columns - one for firstname, and the other\n" +
    "      for lastname - to display a list of Person objects.\n" + "    </p>\n" + "  </div>\n" +
    "  <div>\n" + "    <h2>\n" + "      <a id=\"The_Table_Model\"></a>The Table Model</h2>\n" +
    "    <p>\n" +
    "      The ADF Table component uses a model to access the data in the underlying list.\n" +
    "      The specific model class is <code>oracle.adf.view.rich.model.CollectionModel</code>.\n" +
    "      You may also use other model instances, e.g., <code>java.util.List</code>, array,\n" +
    "      and <code>javax.faces.model.DataModel</code>. The Table will automatically convert\n" +
    "      the instance into a CollectionModel.\n" + "    </p>\n" + "  </div>\n" + "  <div>\n" +
    "    <h2>\n" + "      <a id=\"Columns\"></a>Columns</h2>\n" + "    <p>\n" +
    "      The immediate children of a Table component must all be <code>&lt;af:column&gt;</code>\n" +
    "      components. Each visible ADF Column component creates a separate column in the Table.\n" +
    "    </p>\n" + "    <div>\n" + "      <h3>\n" + "        <a id=\"Headers\"></a>Headers</h3>\n" +
    "      <p>\n" +
    "        Use the &quot;header&quot; facet on a Column to create the column header. You can\n" +
    "        also use the &quot;headerText&quot; attribute to set the column header. The following\n" +
    "        example creates a two-column table with the column headers - &quot;Firstname&quot;\n" +
    "        and &quot;Lastname&quot;:\n" + "      </p>\n" + "    </div>\n" + "    <div>\n" +
    "      <h3>\n" + "        <a id=\"Data\"></a>Data</h3>\n" + "      <p>\n" +
    "        The child components of each Column display the data for each row in that column.\n" +
    "        The Column does not create child components per row; instead, each child is repeatedly\n" +
    "        rendered (stamped) once per row. Because of this stamping behavior, some components\n" +
    "        many not work inside the table. Anything that is just pure output, with no behavior,\n" +
    "        will work without problems, as will components that don't &quot;mutate&quot; even\n" +
    "        as they deliver events (for example, command components are fine). Components that\n" +
    "        mutate their state are affected, but any that implement EditableValueHolder are\n" +
    "        supported (this includes all form input controls from the JSF specification as well\n" +
    "        as ADF Faces input controls), as are UIXShowDetail components.\n" + "      </p>\n" +
    "    </div>\n" + "    <div>\n" + "      <h3>\n" +
    "        <a id=\"Formatting\"></a>Formatting</h3>\n" + "      <p>\n" +
    "        The Column component supports the following attributes related to formatting:\n" +
    "        align, width and noWrap\n" + "      </p>\n" + "    </div>\n" + "    <div>\n" +
    "      <h3>\n" + "        <a id=\"Column_Groups\"></a>Column Groups</h3>\n" + "      <p>\n" +
    "        <code>&lt;af:column&gt; </code>tags can be nested to produce groups of columns.\n" +
    "        The header of a column group spans across all the columns it contains. The following\n" +
    "        example creates a column group that has the header &quot;Name&quot; and contains\n" +
    "        two sub columns with headers &quot;First&quot; and &quot;Last&quot;:\n" +
    "      </p>\n" + "     </div>\n" + "   </div>";
  private String _toolbar = "all";
  static {
    _selectOneChoiceIconMap = new HashMap();
    _selectOneChoiceIconMap.put("A Very Long Page Header", "/images/guy.gif");
    _selectOneChoiceIconMap.put("A Shorter Hearder", "/images/smily-normal.gif");
    _selectOneChoiceIconMap.put("A Shorter Hearder", "/images/smily-normal.gif");
    _selectOneChoiceIconMap.put("Subheader A", "/images/guy.gif");
    _selectOneChoiceIconMap.put("Subheader B", "/images/smily-normal.gif");
    _selectOneChoiceIconMap.put("SubSubheader", "/images/guy.gif");
    _selectOneChoiceIconMap.put("SubSubheader 2", "/images/smily-normal.gif");
    _selectOneChoiceIconMap.put(null, "/images/guy.gif");
  }

  private static final ADFLogger _LOG = ADFLogger.createADFLogger(DemoInputBean.class);

  @SuppressWarnings("compatibility:-935921992672925192")
  private static final long serialVersionUID = 1L;
}
