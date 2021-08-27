/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.model.DateListProvider;

public class USHolidayDateListProvider implements DateListProvider
{
  
  public USHolidayDateListProvider()
  {
    _usHolidays = new ArrayList<Date>();
    _usHolidays.add(_newDate("01.01.2007"));
    _usHolidays.add(_newDate("01.15.2007"));
    _usHolidays.add(_newDate("02.19.2007"));
    _usHolidays.add(_newDate("05.28.2007"));
    _usHolidays.add(_newDate("07.04.2007"));
    _usHolidays.add(_newDate("09.08.2007"));
    _usHolidays.add(_newDate("10.08.2007"));
    _usHolidays.add(_newDate("11.11.2007"));
    _usHolidays.add(_newDate("11.22.2007"));
    _usHolidays.add(_newDate("12.25.2007"));
    _usHolidays.add(_newDate("12.31.2007"));
  }

  public List<Date> getDateList(FacesContext context, Calendar base,
      Date rangeStart, Date rangeEnd)
  {
    
    List<Date> returnDates = new ArrayList<Date>();
    
    for (Date it : _usHolidays)
    {
      if(!it.before(rangeStart) && !it.after(rangeEnd)){
        base.setTime(it);
        returnDates.add(base.getTime());
      }
    }
    
    return returnDates;
  }

  private Date _newDate(String string)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
    Date ret = null;
    try
    {
      ret = sdf.parse(string);
    } catch (ParseException e)
    {
      e.printStackTrace();
    }
    return ret;
  }


  private List<Date> _usHolidays = null;
}