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

public class GermanHolidayDateListProvider implements DateListProvider
{
  
  public GermanHolidayDateListProvider()
  {
    _germanHolidays = new ArrayList<Date>();
    _germanHolidays.add(_newDate("01.01.2007"));
    _germanHolidays.add(_newDate("06.04.2007"));
    _germanHolidays.add(_newDate("09.04.2007"));
    _germanHolidays.add(_newDate("01.05.2007"));
    _germanHolidays.add(_newDate("17.05.2007"));
    _germanHolidays.add(_newDate("07.06.2007"));
    _germanHolidays.add(_newDate("03.10.2007"));
    _germanHolidays.add(_newDate("01.11.2007"));
    _germanHolidays.add(_newDate("25.12.2007"));
    _germanHolidays.add(_newDate("26.12.2007"));
  }

  public List<Date> getDateList(FacesContext context, Calendar base,
      Date rangeStart, Date rangeEnd)
  {
    
    List<Date> returnDates = new ArrayList<Date>();
    
    for (Date it : _germanHolidays)
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
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
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


  private List<Date> _germanHolidays = null;
}