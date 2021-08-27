/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich.validate;

import java.util.Date;

import org.apache.myfaces.trinidad.model.DateListProvider;

public class DateRestrictionValidatorBean
{

  public String getCountry()
  {
    return _country;
  }
  public void setCountry(String country)
  {
    this._country = country;
  }
  public DateListProvider getNationalHolidays()
  {
    return _nationalHolidays;
  }
  public void setNationalHolidays(DateListProvider nationalHolidays)
  {
    this._nationalHolidays = nationalHolidays;
  }
  public Date getTestInvalidDays()
  {
    return _testInvalidDays;
  }
  public void setTestInvalidDays(Date testInvalidDays)
  {
    this._testInvalidDays = testInvalidDays;
  }
  public Date getTestInvalidDaysOfWeek()
  {
    return _testInvalidDaysOfWeek;
  }
  public void setTestInvalidDaysOfWeek(Date testInvalidDaysOfWeek)
  {
    this._testInvalidDaysOfWeek = testInvalidDaysOfWeek;
  }
  public Date getTestInvalidMonth()
  {
    return _testInvalidMonth;
  }
  public void setTestInvalidMonth(Date testInvalidMonth)
  {
    this._testInvalidMonth = testInvalidMonth;
  }
  public Date getTestInvalidHolidays()
  {
    return _testInvalidHolidays;
  }
  public void setTestInvalidHolidays(Date testInvalidHolidays)
  {
    this._testInvalidHolidays = testInvalidHolidays;
  }
  
  private DateListProvider _nationalHolidays = null;
  private String _country = null;
  private Date _testInvalidDays = new Date(1167724800000L); // Jan 2, 2007
  private Date _testInvalidDaysOfWeek = null;
  private Date _testInvalidMonth = null;
  private Date _testInvalidHolidays = new Date(1167724800000L); // Jan 2, 2007
}