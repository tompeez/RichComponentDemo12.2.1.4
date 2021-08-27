/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/SparkchartSample.java /main/7 2015/05/14 14:17:52 mguirgui Exp $ */

/* Copyright (c) 2009, 2015, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hzhang      02/25/09 - Creation
 */

package oracle.adfdemo.view.feature.rich.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import oracle.adf.view.faces.bi.event.ClickEvent;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/SparkchartSample.java /main/7 2015/05/14 14:17:52 mguirgui Exp $
 *  @author  hzhang
 *  @since   11.1.2.0.0
 */
public class SparkchartSample {

    private String listenerText = "not clicked";
    private int counter = 0;

    /**
     * Collection to be passed to an iterator within the spark chart. Each
     * row contains Open and Close prices for a stock.
     * @return
     */
  public List <Map <String,Object>> getCollection() {
    List <Map <String,Object>> list = new ArrayList <Map <String,Object>>();
    
    // Generate some sort of stock data
    double open[] =  {5,   6,   4.5, 6.3, 4.1, 7.6, 8.4, 11.5, 10.5, 11.3};
    double close[] = {6.8, 4.2, 6.7, 4.5, 7.1, 8.6, 10.4, 10.0, 12.5, 14.5};
    for(int i=0; i<open.length; i++) 
    {
      Map <String,Object> row = new HashMap <String,Object>();
      row.put("open", open[i]); 
      row.put("close", close[i]);
      list.add(row);
    }
    return list;
  }
  
  /**
   * Collection to be passed to an iterator within the spark chart. Each
   * row contains Open and Close prices for a stock.  The data is irregularly
   * spaced.
   * @return
   */
  public List <Map <String,Object>> getIrregularTimeData() {
    List <Map <String,Object>> list = new ArrayList <Map <String,Object>>();
    
    // Generate some sort of stock data
    double values[] = {35.8, 15.2, 14.5, 54.5, 5.1};
    Date date1 = java.sql.Date.valueOf("2008-01-01");
    Date date2 = java.sql.Date.valueOf("2008-02-01");
    Date date3 = java.sql.Date.valueOf("2008-02-02");
    Date date4 = java.sql.Date.valueOf("2008-05-01");
    Date date5 = java.sql.Date.valueOf("2008-09-01");
    Date dates[] = {date1, date2, date3, date4, date5};
    for(int i=0; i<values.length; i++) 
    {
      Map <String,Object> row = new HashMap <String,Object>();
      row.put("value", values[i]);
      row.put("date", dates[i]);
      list.add(row);
    }
    return list;
  }
  
  /**
   * Collection to be passed to a table, which contains a spark chart.  Each
   * row represent a particular stock and its prices over time.
   * @return
   */
  public List <List <Object>> getTableData() {
    List <List <Object>> list = new ArrayList <List <Object>>();
    
    // Create a random number generator with a constant seed
    Random rand = new Random(5);
    
    String stocks[] = {"ORCL", "AAPL", "MSFT", "YHOO", "CSCO", "PALM", "GOOG", "SAP", "HPQ", "IBM", "INTC", "RIMM"};
    Arrays.sort(stocks); // sort to look nice
    for(int i=0; i<stocks.length; i++) 
    {
      List <Object> row = new ArrayList <Object>();
      row.add(stocks[i]);
      for(int j=0; j<6; j++) {
        // Let the value vary between 40 and 70 (for simplicity)
        row.add((rand.nextDouble()*30) + 40);
      }
      list.add(row);
    }
    return list;
  }

  public void clickListener(ClickEvent ce) {
    this.listenerText = "chart clicked " + (++this.counter);
  }

  public void setListenerText(String clickListenerString) {
    this.listenerText = clickListenerString;
  }

  public String getListenerText() {
    return listenerText;
  }
}


