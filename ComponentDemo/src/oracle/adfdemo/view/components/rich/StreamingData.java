/** Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Demo data for the af:streaming demo page. The data is to be loaded asynchronously.
 *
 * @see StreamingDataFetcher
 */
public class StreamingData
  implements Serializable
{
  public StreamingData(int index)
  {
    _index = index;

    _data = _populateData();
  }

  public List<Serializable> getData()
  {
    return _data;
  }

  public int getIndex()
  {
    return _index;
  }

  private List<Serializable> _populateData()
  {
    ArrayList<Serializable> data = new ArrayList<Serializable>();

    switch(_index)
    {
      case 0: _popuplateData1(data); break;
      case 1: _popuplateData2(data); break;
      case 2: _popuplateData3(data); break;
      case 3: _popuplateData4(data); break;
      case 4: _popuplateData5(data); break;
      case 5: default: _popuplateData6(data); break;
    }

    return Collections.unmodifiableList(data);
  }

  private void _popuplateData1(ArrayList<Serializable> data)
  {
    // Stocks
    data.add(new Stock("Dow Jones Industrial Average", "DOW J", 16151.51));
    data.add(new Stock("NASDAQ Composite", "NASDAQ", 4526.02));
    data.add(new Stock("Apple Inc.", "AAPL", 97.39));
    data.add(new Stock("Alphabet Inc.", "GOOG", 700.56));
    data.add(new Stock("International Business Machines Corporation", "IBM", 131.17));
    data.add(new Stock("Microsoft Corporation", "MSFT", 51.64));
    data.add(new Stock("Oracle Corporation", "ORCL", 34.08));
    data.add(new Stock("SAP SE", "SAP", 78.43));
  }

  private void _popuplateData2(ArrayList<Serializable> data)
  {
    // Sales
    data.add(new Sales("Jane Doe", 120045.34));
    data.add(new Sales("John Smith", 90400.23));
    data.add(new Sales("Becky Lou", 89123.72));
    data.add(new Sales("Don Grouse", 82100.12));
  }

  private void _popuplateData3(ArrayList<Serializable> data)
  {
    // Tasks
    data.add(new Task("Setup Thursday sales meeting"));
    data.add(new Task("Review quarterly figures"));
    data.add(new Task("Submit expense reports"));
    data.add(new Task("Call Fred in research"));
    data.add(new Task("Schedule team meeting"));
  }

  private void _popuplateData4(ArrayList<Serializable> data)
  {
    // Links
    data.add(new Link("Oracle", "http://www.oracle.com"));
    data.add(new Link("MyOracle", "http://my.oracle.com"));
    data.add(new Link("Zimbra", "https://stbeehive.oracle.com/zimbra/"));
    data.add(new Link("OTN ADF",
      "http://www.oracle.com/technetwork/developer-tools/adf/overview/index.html"));
  }

  private void _popuplateData5(ArrayList<Serializable> data)
  {
    // Vacations
    data.add(new Vacation("Jon Mark", _createDate(2, 0, 0), _createDate(2, 0, 0)));
    data.add(new Vacation("Lucy Mi", _createDate(6, 0, 0), _createDate(8, 0, 0)));
    data.add(new Vacation("Richard Simms", _createDate(5, 0, 0), _createDate(6, 0, 0)));
  }

  private void _popuplateData6(ArrayList<Serializable> data)
  {
    // Events
    data.add(new Event("Quarter review", _createDate(1, 11, 0)));
    data.add(new Event("Sales call", _createDate(7, 15, 30)));
    data.add(new Event("Team lunch", _createDate(14, 12, 0)));
  }

  private Date _createDate(
    int numDaysFromToday,
    int hour,
    int min)
  {
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DATE, numDaysFromToday);
    c.set(Calendar.HOUR_OF_DAY, hour);
    c.set(Calendar.MINUTE, min);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);
    return c.getTime();
  }

  /**
   * Demo data providing simulated sales information for sales employees
   */
  public static class Sales
    implements Serializable
  {
    public Sales(
      String name,
      double  amount)
    {
      this._name = name;
      this._amount = amount;
    }

    public String getName()
    {
      return _name;
    }

    public double getAmount()
    {
      return _amount;
    }

    @SuppressWarnings("compatibility:972789411021762112")
    private static final long serialVersionUID = 1L;

    private final String _name;
    private final double _amount;
  }

  /**
   * Demo data providing stock information
   */
  public static class Stock
    implements Serializable
  {
    public Stock(
      String name,
      String symbol,
      double  value)
    {
      this._name = name;
      this._symbol = symbol;
      this._value = value;
    }

    public String getName()
    {
      return _name;
    }

    public String getSymbol()
    {
      return _symbol;
    }

    public double getValue()
    {
      return _value;
    }

    @SuppressWarnings("compatibility:-6503244969012156942")
    private static final long serialVersionUID = 1L;

    private final String _name;
    private final String _symbol;
    private final double _value;
  }

  /**
   * Demo data providing bookmark-like links
   */
  public static class Link
    implements Serializable
  {
    public Link(
      String name,
      String url)
    {
      this._name = name;
      this._url = url;
    }

    public String getName()
    {
      return _name;
    }

    public String getUrl()
    {
      return _url;
    }

    @SuppressWarnings("compatibility:-3644082474861402386")
    private static final long serialVersionUID = 1L;

    private final String _name;
    private final String _url;
  }

  /**
   * Demo data providing simulated tasks that need to be completed
   */
  public static class Task
    implements Serializable
  {
    public Task(
      String name)
    {
      this._name = name;
    }

    public String getName()
    {
      return _name;
    }

    @SuppressWarnings("compatibility:-3354042626257955230")
    private static final long serialVersionUID = 1L;

    private final String _name;
  }

  /**
   * Demo data class providing simulated upcoming employee vacation schedule information
   */
  public static class Vacation
    implements Serializable
  {
    public Vacation(
      String name,
      Date   startDate,
      Date   endDate)
    {
      this._name = name;
      this._startDate = startDate;
      this._endDate = endDate;
    }

    public String getName()
    {
      return _name;
    }

    public Date getStartDate()
    {
      return _startDate;
    }

    public Date getEndDate()
    {
      return _endDate;
    }

    @SuppressWarnings("compatibility:3730481744593768448")
    private static final long serialVersionUID = 1L;

    private final String _name;
    private final Date _startDate;
    private final Date _endDate;
  }

  public static class Event
    implements Serializable
  {
    public Event(
      String name,
      Date   date)
    {
      this._name = name;
      this._date = date;
    }

    public String getName()
    {
      return _name;
    }

    public Date getDate()
    {
      return _date;
    }

    @SuppressWarnings("compatibility:6087516120481200763")
    private static final long serialVersionUID = 1L;

    private final String _name;
    private final Date _date;
  }

  @SuppressWarnings("compatibility:362836957499206295")
  private static final long serialVersionUID = -1605917882221825092L;

  /**
   * Index, used to differentiate what demo data to load
   */
  private final int _index;

  /**
   * The demo data
   */
  private final List<Serializable> _data;
}
