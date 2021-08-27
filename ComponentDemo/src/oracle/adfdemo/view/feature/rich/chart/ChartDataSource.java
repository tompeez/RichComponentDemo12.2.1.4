package oracle.adfdemo.view.feature.rich.chart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

// TODO implement the flexible data model system
public class ChartDataSource {
  /**
   * Object representing the data for a single row of the model.
   */
  public static class ChartDataItem extends HashMap<String, Object> {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    
    public ChartDataItem(String series, Object group, Number value) {
      put("series", series);
      put("group", group);
      put("value", value);
    }    
    
    public ChartDataItem(String series, Object group, Object x, Number y) {
      put("series", series);
      put("group", group);
      put("x", x);
      put("y", y);
    }
    
    public ChartDataItem(String series, Object group, Number x, Number y, Number z) {
      put("series", series);
      put("group", group);
      put("x", x);
      put("y", y);
      put("z", z);
    }
    public ChartDataItem(String series, Object group, Number open, Number close, Number high, Number low, Number volume) {
      put("series", series);
      put("group", group);
      put("open", open);
      put("close", close);
      put("high", high);
      put("low", low);
      put("volume", volume);
    }
    
    public ChartDataItem(String region, String year, String brand, Number value) {
      put("region", region);
      put("year", year);
      put("brand", brand);
      put("value", value);
    }
    
    public ChartDataItem(String series, Object group, Number x, Number y, Number high, Number low) {
      put("series", series);
      put("group", group);
      put("high", high);
      put("low", low);
    }
  }
  
  public CollectionModel getEmptyData() {
    return ModelUtils.toCollectionModel(Collections.emptyList());
  }
  
  public CollectionModel getDefaultBarData() {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    dataItems.add(new ChartDataItem("Series 1", "Group A", 42));
    dataItems.add(new ChartDataItem("Series 1", "Group B", 34));
    dataItems.add(new ChartDataItem("Series 2", "Group A", 55));
    dataItems.add(new ChartDataItem("Series 2", "Group B", 30));
    dataItems.add(new ChartDataItem("Series 3", "Group A", 36));
    dataItems.add(new ChartDataItem("Series 3", "Group B", 50));
    dataItems.add(new ChartDataItem("Series 4", "Group A", 22));
    dataItems.add(new ChartDataItem("Series 4", "Group B", 46));
    dataItems.add(new ChartDataItem("Series 5", "Group A", 22));
    dataItems.add(new ChartDataItem("Series 5", "Group B", 46));
    return ModelUtils.toCollectionModel(dataItems);
  }  
 
  public CollectionModel getExtendedBarData() {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    dataItems.add(new ChartDataItem("Series 1", "Group A", 42));
    dataItems.add(new ChartDataItem("Series 2", "Group A", 34));
    dataItems.add(new ChartDataItem("Series 3", "Group A", 55));
    dataItems.add(new ChartDataItem("Series 4", "Group A", 30));
    dataItems.add(new ChartDataItem("Series 5", "Group A", 36));
    dataItems.add(new ChartDataItem("Series 6", "Group A", 50));
    dataItems.add(new ChartDataItem("Series 7", "Group A", 22));
    dataItems.add(new ChartDataItem("Series 8", "Group A", 46));
    dataItems.add(new ChartDataItem("Series 9", "Group A", 22));
    dataItems.add(new ChartDataItem("Series 10", "Group A", 46));
    dataItems.add(new ChartDataItem("Series 11", "Group A", 42));
    dataItems.add(new ChartDataItem("Series 12", "Group A", 34));
    dataItems.add(new ChartDataItem("Series 13", "Group A", 55));
    dataItems.add(new ChartDataItem("Series 14", "Group A", 30));
    dataItems.add(new ChartDataItem("Series 15", "Group A", 36));
    dataItems.add(new ChartDataItem("Series 16", "Group A", 50));
    dataItems.add(new ChartDataItem("Series 17", "Group A", 22));
    dataItems.add(new ChartDataItem("Series 18", "Group A", 46));
    dataItems.add(new ChartDataItem("Series 19", "Group A", 22));
    dataItems.add(new ChartDataItem("Series 20", "Group A", 46));
    dataItems.add(new ChartDataItem("Series 21", "Group A", 42));
    dataItems.add(new ChartDataItem("Series 22", "Group A", 34));
    dataItems.add(new ChartDataItem("Series 23", "Group A", 55));
    dataItems.add(new ChartDataItem("Series 24", "Group A", 30));
    dataItems.add(new ChartDataItem("Series 25", "Group A", 36));
    dataItems.add(new ChartDataItem("Series 26", "Group A", 46));
    dataItems.add(new ChartDataItem("Series 27", "Group A", 42));
    dataItems.add(new ChartDataItem("Series 28", "Group A", 34));
    dataItems.add(new ChartDataItem("Series 29", "Group A", 55));
    dataItems.add(new ChartDataItem("Series 30", "Group A", 30));
    dataItems.add(new ChartDataItem("Series 31", "Group A", 36));
    dataItems.add(new ChartDataItem("Series 32", "Group A", 50));
    dataItems.add(new ChartDataItem("Series 33", "Group A", 22));
    dataItems.add(new ChartDataItem("Series 34", "Group A", 46));
    dataItems.add(new ChartDataItem("Series 35", "Group A", 22));
    dataItems.add(new ChartDataItem("Series 36", "Group A", 46));
    dataItems.add(new ChartDataItem("Series 37", "Group A", 42));
    dataItems.add(new ChartDataItem("Series 38", "Group A", 34));
    dataItems.add(new ChartDataItem("Series 39", "Group A", 55));
    dataItems.add(new ChartDataItem("Series 40", "Group A", 30));
    dataItems.add(new ChartDataItem("Series 41", "Group A", 36));
    dataItems.add(new ChartDataItem("Series 42", "Group A", 42));
    dataItems.add(new ChartDataItem("Series 43", "Group A", 34));
    dataItems.add(new ChartDataItem("Series 44", "Group A", 55));
    dataItems.add(new ChartDataItem("Series 45", "Group A", 30));
    dataItems.add(new ChartDataItem("Series 46", "Group A", 36));
    dataItems.add(new ChartDataItem("Series 47", "Group A", 50));
    dataItems.add(new ChartDataItem("Series 48", "Group A", 22));
    dataItems.add(new ChartDataItem("Series 49", "Group A", 46));
    dataItems.add(new ChartDataItem("Series 50", "Group A", 22));
    dataItems.add(new ChartDataItem("Series 51", "Group A", 46));
    dataItems.add(new ChartDataItem("Series 52", "Group A", 42));
    dataItems.add(new ChartDataItem("Series 53", "Group A", 34));
    dataItems.add(new ChartDataItem("Series 54", "Group A", 55));
    dataItems.add(new ChartDataItem("Series 55", "Group A", 30));
    dataItems.add(new ChartDataItem("Series 56", "Group A", 36));

    return ModelUtils.toCollectionModel(dataItems);
  }  
    
  public CollectionModel getDefaultLineData() {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    dataItems.add(new ChartDataItem("Series 1", "Group A", 74));
    dataItems.add(new ChartDataItem("Series 1", "Group B", 62));
    dataItems.add(new ChartDataItem("Series 1", "Group C", 70));
    dataItems.add(new ChartDataItem("Series 1", "Group D", 76));
    dataItems.add(new ChartDataItem("Series 1", "Group E", 66));

    dataItems.add(new ChartDataItem("Series 2", "Group A", 50));
    dataItems.add(new ChartDataItem("Series 2", "Group B", 38));
    dataItems.add(new ChartDataItem("Series 2", "Group C", 46));
    dataItems.add(new ChartDataItem("Series 2", "Group D", 54));
    dataItems.add(new ChartDataItem("Series 2", "Group E", 42));

    dataItems.add(new ChartDataItem("Series 3", "Group A", 34));
    dataItems.add(new ChartDataItem("Series 3", "Group B", 22));
    dataItems.add(new ChartDataItem("Series 3", "Group C", 30));
    dataItems.add(new ChartDataItem("Series 3", "Group D", 32));
    dataItems.add(new ChartDataItem("Series 3", "Group E", 26));

    dataItems.add(new ChartDataItem("Series 4", "Group A", 18));
    dataItems.add(new ChartDataItem("Series 4", "Group B", 6));
    dataItems.add(new ChartDataItem("Series 4", "Group C", 14));
    dataItems.add(new ChartDataItem("Series 4", "Group D", 22));
    dataItems.add(new ChartDataItem("Series 4", "Group E", 10));

    dataItems.add(new ChartDataItem("Series 5", "Group A", 3));
    dataItems.add(new ChartDataItem("Series 5", "Group B", 2));
    dataItems.add(new ChartDataItem("Series 5", "Group C", 3));
    dataItems.add(new ChartDataItem("Series 5", "Group D", 3));
    dataItems.add(new ChartDataItem("Series 5", "Group E", 2));
    return ModelUtils.toCollectionModel(dataItems);
  }  
  
  public CollectionModel getDefaultAreaData() {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    dataItems.add(new ChartDataItem("Series 1", "Group A", 74));
    dataItems.add(new ChartDataItem("Series 1", "Group B", 42));
    dataItems.add(new ChartDataItem("Series 1", "Group C", 70));
    dataItems.add(new ChartDataItem("Series 1", "Group D", 46));

    dataItems.add(new ChartDataItem("Series 2", "Group A", 50));
    dataItems.add(new ChartDataItem("Series 2", "Group B", 58));
    dataItems.add(new ChartDataItem("Series 2", "Group C", 46));
    dataItems.add(new ChartDataItem("Series 2", "Group D", 54));

    dataItems.add(new ChartDataItem("Series 3", "Group A", 34));
    dataItems.add(new ChartDataItem("Series 3", "Group B", 22));
    dataItems.add(new ChartDataItem("Series 3", "Group C", 30));
    dataItems.add(new ChartDataItem("Series 3", "Group D", 32));

    dataItems.add(new ChartDataItem("Series 4", "Group A", 18));
    dataItems.add(new ChartDataItem("Series 4", "Group B", 6));
    dataItems.add(new ChartDataItem("Series 4", "Group C", 14));
    dataItems.add(new ChartDataItem("Series 4", "Group D", 22));
    return ModelUtils.toCollectionModel(dataItems);
  }  
  
  public CollectionModel getDefaultComboData() {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    dataItems.add(new ChartDataItem("Series 1", "Group A", 42));
    dataItems.add(new ChartDataItem("Series 1", "Group B", 34));
    dataItems.add(new ChartDataItem("Series 2", "Group A", 55));
    dataItems.add(new ChartDataItem("Series 2", "Group B", 30));
    dataItems.add(new ChartDataItem("Series 3", "Group A", 36));
    dataItems.add(new ChartDataItem("Series 3", "Group B", 50));
    dataItems.add(new ChartDataItem("Series 4", "Group A", 22));
    dataItems.add(new ChartDataItem("Series 4", "Group B", 46));
    dataItems.add(new ChartDataItem("Series 5", "Group A", 22));
    dataItems.add(new ChartDataItem("Series 5", "Group B", 46));
    return ModelUtils.toCollectionModel(dataItems);
  }  
  
  public CollectionModel getDefaultPieData() {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    dataItems.add(new ChartDataItem("Series 1", "Group A", 42));
    dataItems.add(new ChartDataItem("Series 2", "Group A", 55));
    dataItems.add(new ChartDataItem("Series 3", "Group A", 36));
    dataItems.add(new ChartDataItem("Series 4", "Group A", 22));
    dataItems.add(new ChartDataItem("Series 5", "Group A", 22));
    return ModelUtils.toCollectionModel(dataItems);
  }  
  
  
  public CollectionModel getDefaultRangeData() {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    dataItems.add(new ChartDataItem("Series 1", "Group A", null, null, 42, 32));
    dataItems.add(new ChartDataItem("Series 1", "Group B", null, null, 52, 42));
    dataItems.add(new ChartDataItem("Series 1", "Group C", null, null, 62, 52));
    dataItems.add(new ChartDataItem("Series 1", "Group D", null, null, 68, 48));
    dataItems.add(new ChartDataItem("Series 1", "Group E", null, null, 50, 32));
    return ModelUtils.toCollectionModel(dataItems);
  }  
  
  
  private double slices = 12;
  public void setSlices(double slices) {
    this.slices = slices;
  }

  public double getSlices() {
    return slices;
  }    
  
  public CollectionModel getExtendedPieData() {
      List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
      
      dataItems.add(new ChartDataItem("Series 1", "Group A", 42));
      dataItems.add(new ChartDataItem("Series 2", "Group A", 55));
      dataItems.add(new ChartDataItem("Series 3", "Group A", 36));
      dataItems.add(new ChartDataItem("Series 4", "Group A", 22));
      dataItems.add(new ChartDataItem("Series 5", "Group A", 22));
      dataItems.add(new ChartDataItem("Series 6", "Group A", 14));
      dataItems.add(new ChartDataItem("Series 7", "Group A", 5));
      dataItems.add(new ChartDataItem("Series 8", "Group A", 10));
      dataItems.add(new ChartDataItem("Series 9", "Group A", 33));
      dataItems.add(new ChartDataItem("Series 10", "Group A", 17));
      dataItems.add(new ChartDataItem("Series 11", "Group A", 50));
      dataItems.add(new ChartDataItem("Series 12", "Group A", 3));
      return ModelUtils.toCollectionModel(dataItems);
  }
      
  public CollectionModel getDynamicPieData() {
    Random random = new Random(24);
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
 
    for(int i = 1; i <= slices; i++){
      dataItems.add(new ChartDataItem("Series " + i, "Group A", random.nextInt(100)));
    }

    return ModelUtils.toCollectionModel(dataItems);
  }  
  
  public CollectionModel getDefaultScatterData() {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    dataItems.add(new ChartDataItem("Series 1", "Group A", 15, 15));
    dataItems.add(new ChartDataItem("Series 1", "Group B", 25, 43));
    dataItems.add(new ChartDataItem("Series 1", "Group C", 25, 25));
    
    dataItems.add(new ChartDataItem("Series 2", "Group A", 25, 15));
    dataItems.add(new ChartDataItem("Series 2", "Group B", 55, 45));
    dataItems.add(new ChartDataItem("Series 2", "Group C", 57, 47));
    
    dataItems.add(new ChartDataItem("Series 3", "Group A", 17, 36));
    dataItems.add(new ChartDataItem("Series 3", "Group B", 32, 52));
    dataItems.add(new ChartDataItem("Series 3", "Group C", 26, 28));
    
    dataItems.add(new ChartDataItem("Series 4", "Group A", 38, 22));
    dataItems.add(new ChartDataItem("Series 4", "Group B", 43, 43));
    dataItems.add(new ChartDataItem("Series 4", "Group C", 58, 36));
    return ModelUtils.toCollectionModel(dataItems);
  }  
  
  public CollectionModel getDefaultBubbleData() {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    dataItems.add(new ChartDataItem("Series 1", "Group A", 15, 25, 5));
    dataItems.add(new ChartDataItem("Series 1", "Group B", 25, 30, 12));
    dataItems.add(new ChartDataItem("Series 1", "Group C", 25, 45, 12));
    
    dataItems.add(new ChartDataItem("Series 2", "Group A", 15, 15, 8));
    dataItems.add(new ChartDataItem("Series 2", "Group B", 20, 35, 14));
    dataItems.add(new ChartDataItem("Series 2", "Group C", 40, 55, 35));
    
    dataItems.add(new ChartDataItem("Series 3", "Group A", 10, 10, 8));
    dataItems.add(new ChartDataItem("Series 3", "Group B", 18, 55, 10));
    dataItems.add(new ChartDataItem("Series 3", "Group C", 40, 50, 18));
    
    dataItems.add(new ChartDataItem("Series 4", "Group A", 8, 20, 6));
    dataItems.add(new ChartDataItem("Series 4", "Group B", 11, 30, 8));
    dataItems.add(new ChartDataItem("Series 4", "Group C", 30, 40, 15));
    return ModelUtils.toCollectionModel(dataItems);
  }  
  
  public CollectionModel getDefaultStockData() {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    dataItems.add(new ChartDataItem("ORCL", new GregorianCalendar(2011, 3, 1).getTime(), 20, 22, 23, 19, 2000000));
    dataItems.add(new ChartDataItem("ORCL", new GregorianCalendar(2011, 5, 1).getTime(), 22, 24, 24, 19, 4000000));
    dataItems.add(new ChartDataItem("ORCL", new GregorianCalendar(2011, 6, 1).getTime(), 24, 22, 24, 19, 1000000));
    dataItems.add(new ChartDataItem("ORCL", new GregorianCalendar(2011, 7, 1).getTime(), 22, 26, 28, 19, 3000000));
    dataItems.add(new ChartDataItem("ORCL", new GregorianCalendar(2011, 8, 1).getTime(), 18, 22, 24, 16, 8000000));
    dataItems.add(new ChartDataItem("ORCL", new GregorianCalendar(2011, 9, 1).getTime(), 20, 22, 22, 19, 9000000));
    dataItems.add(new ChartDataItem("ORCL", new GregorianCalendar(2011, 11, 1).getTime(), 23, 28, 29, 23, 5000000));

    return ModelUtils.toCollectionModel(dataItems);
  }  
    
  public CollectionModel getHourlyTimeData() {
    return getTimeData(4, 10, 2013, 6, 14, Calendar.HOUR, 3);
  }  
  
  public CollectionModel getDailyTimeData() {
    return getTimeData(3, 10, 2013, 6, 1, Calendar.DATE, 5);
  } 
  
  public CollectionModel getMonthlyTimeData() {
    return getTimeData(2, 10, 2013, 7, 1, Calendar.MONTH, 1);
  }  
  
  public CollectionModel getYearlyTimeData() {
    return getTimeData(3, 10, 2013, 0, 1, Calendar.YEAR, 1);
  }
  
  public CollectionModel getDailyStockData() {
    return getPortfolioData(4, 1096, 2013, 6, 1, Calendar.DATE, 1);
  }
  
  public CollectionModel getWeeklyStockData() {
    return getPortfolioData(4, 157, 2013, 6, 1, Calendar.DATE, 7);
  }
  
  public Date getStockViewportMinimum() {
    return new GregorianCalendar(2016, 0, 1).getTime();
  }
  
  public Date getStockViewportMaximum() {
    return new GregorianCalendar(2016, 5, 8).getTime();
  }
  
  public CollectionModel getYearlyIrregularTimeData() {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2011, 7, 27).getTime(), 55));
    dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2012, 7, 27).getTime(), 60));
    dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2013, 7, 27).getTime(), 75));
    dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2015, 1, 27).getTime(), 70));
    dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2016, 1, 27).getTime(), 35));

    dataItems.add(new ChartDataItem("Series 2", new GregorianCalendar(2011, 7, 27).getTime(), 45));
    dataItems.add(new ChartDataItem("Series 2", new GregorianCalendar(2012, 7, 27).getTime(), 40));
    dataItems.add(new ChartDataItem("Series 2", new GregorianCalendar(2013, 7, 27).getTime(), 65));
    dataItems.add(new ChartDataItem("Series 2", new GregorianCalendar(2015, 1, 27).getTime(), 65));
    dataItems.add(new ChartDataItem("Series 2", new GregorianCalendar(2016, 1, 27).getTime(), 45));
    
    return ModelUtils.toCollectionModel(dataItems);
  }
  
  public CollectionModel getYearlySingleTimeData() {
      List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
      dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2010, 1, 27).getTime(), 35));
      dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2011, 1, 27).getTime(), 55));
      dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2012, 1, 27).getTime(), 60));
      dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2013, 1, 27).getTime(), 75));
      dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2014, 1, 27).getTime(), 65));
      dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2015, 1, 27).getTime(), 55));
      dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2016, 1, 27).getTime(), 85));
      dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2017, 1, 27).getTime(), 70));
      dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2018, 1, 27).getTime(), 75));
      dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2019, 1, 27).getTime(), 45));
      dataItems.add(new ChartDataItem("Series 1", new GregorianCalendar(2020, 1, 27).getTime(), 50));
      
      return ModelUtils.toCollectionModel(dataItems);
  }
    
  public CollectionModel getYearlyMixedFrequencyTimeData() {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    dataItems.add(new ChartDataItem("Series 1", "Group 1", new GregorianCalendar(2011, 7, 27).getTime(), 55));
    dataItems.add(new ChartDataItem("Series 1", "Group 2", new GregorianCalendar(2013, 7, 27).getTime(), 60));
    dataItems.add(new ChartDataItem("Series 1", "Group 3", new GregorianCalendar(2015, 7, 27).getTime(), 75));
    dataItems.add(new ChartDataItem("Series 1", "Group 4", new GregorianCalendar(2017, 7, 27).getTime(), 70));
    dataItems.add(new ChartDataItem("Series 1", "Group 5", new GregorianCalendar(2019, 7, 27).getTime(), 35));

    dataItems.add(new ChartDataItem("Series 2", "Group 1", new GregorianCalendar(2012, 1, 27).getTime(), 45));
    dataItems.add(new ChartDataItem("Series 2", "Group 2", new GregorianCalendar(2013, 7, 27).getTime(), 40));
    dataItems.add(new ChartDataItem("Series 2", "Group 3", new GregorianCalendar(2014, 7, 27).getTime(), 65));
    dataItems.add(new ChartDataItem("Series 2", "Group 4", new GregorianCalendar(2016, 1, 27).getTime(), 65));
    dataItems.add(new ChartDataItem("Series 2", "Group 5", new GregorianCalendar(2020, 7, 27).getTime(), 45));
    
    dataItems.add(new ChartDataItem("Series 3", "Group 1", new GregorianCalendar(2010, 7, 27).getTime(), 15));
    dataItems.add(new ChartDataItem("Series 3", "Group 2", new GregorianCalendar(2012, 7, 27).getTime(), 20));
    dataItems.add(new ChartDataItem("Series 3", "Group 3", new GregorianCalendar(2014, 7, 27).getTime(), 35));
    dataItems.add(new ChartDataItem("Series 3", "Group 4", new GregorianCalendar(2016, 7, 27).getTime(), 30));
    dataItems.add(new ChartDataItem("Series 3", "Group 5", new GregorianCalendar(2018, 7, 27).getTime(), 55));
    return ModelUtils.toCollectionModel(dataItems);
  }
  
  /**
   * Generates a random data model whose values are between 0 and 100.
   * @param numSeries the number of series in the model
   * @param numGroups the number of groups in the model
   * @param seed the seed for the random number generator
   * @return the CollectionModel
   */
  public CollectionModel getData(int numSeries, int numGroups, int seed) {
    Random random = new Random(seed);
    
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    for(int seriesIndex=1; seriesIndex<=numSeries; seriesIndex++) {
      for(int groupIndex=1; groupIndex<=numGroups; groupIndex++) {
        dataItems.add(new ChartDataItem("Series " + seriesIndex, "Group " + groupIndex, random.nextInt(100)));
      }
    }
    
    return ModelUtils.toCollectionModel(dataItems);
  }
  
  /**
   * Generates a random scatter chart data model whose values are between 0 and 100.
   * @param numSeries the number of series in the model
   * @param numGroups the number of groups in the model
   * @param seed the seed for the random number generator
   * @return the CollectionModel
   */
  public CollectionModel getScatterData(int numSeries, int numGroups, int seed) {
    Random random = new Random(seed);
    
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    for(int seriesIndex=1; seriesIndex<=numSeries; seriesIndex++) {
      for(int groupIndex=1; groupIndex<=numGroups; groupIndex++) {
        dataItems.add(new ChartDataItem("Series " + seriesIndex, "Group " + groupIndex, random.nextInt(100), random.nextInt(100)));
      }
    }
    
    return ModelUtils.toCollectionModel(dataItems);
  }
  
  /**
   * Generates a random bubble chart data model whose values are between 0 and 100.
   * @param numSeries the number of series in the model
   * @param numGroups the number of groups in the model
   * @param seed the seed for the random number generator
   * @return the CollectionModel
   */
  public CollectionModel getBubbleData(int numSeries, int numGroups, int seed) {
    Random random = new Random(seed);
    
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    for(int seriesIndex=1; seriesIndex<=numSeries; seriesIndex++) {
      for(int groupIndex=1; groupIndex<=numGroups; groupIndex++) {
        dataItems.add(new ChartDataItem("Series " + seriesIndex, "Group " + groupIndex, random.nextInt(100), random.nextInt(100), random.nextInt(100)));
      }
    }
    
    return ModelUtils.toCollectionModel(dataItems);
  }
  
  public CollectionModel getTimeData(int numSeries, int numGroups, 
                                     int startYear, int startMonth, int startDate, 
                                     int dateField, int addCount) 
  {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    GregorianCalendar cal = new GregorianCalendar(startYear, startMonth, startDate);
    for(int group=0; group<numGroups; group++) {
      for(int series=0; series<numSeries; series++) {
        dataItems.add(new ChartDataItem("Series " + (series+1), cal.getTime(), getValue()));
      }
      cal.add(dateField, addCount);
    }
    
    return ModelUtils.toCollectionModel(dataItems);
  }
  
  public CollectionModel getPortfolioData(int numSeries, int numGroups, 
                                     int startYear, int startMonth, int startDate, 
                                     int dateField, int addCount) 
  {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    GregorianCalendar cal;
    double curValue;
    for(int series=0; series<numSeries; series++) {
      cal = new GregorianCalendar(startYear, startMonth, startDate);
      curValue = 100;
      for(int group=0; group<numGroups; group++) {
        dataItems.add(new ChartDataItem("Investor " + (series+1), cal.getTime(), curValue));
        cal.add(dateField, addCount);
        curValue = getNextValue(curValue, 10);
      }
    }
    
    return ModelUtils.toCollectionModel(dataItems);
  }  
  
  private double getValue() {
    return Math.random() * 100;
  }
  
  private Random random = new Random(23);
  
  /**
   * Returns the next random value based on normal distribution
   * @param curValue
   * @param std Standard deviation
   * @return
   */
  private double getNextValue(double curValue, double std) {
    if (curValue == 0)
      return 0;
    else
      return Math.max(curValue + random.nextGaussian() * std, 0);
  }

  
  public CollectionModel getBrandData() {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    dataItems.add(new ChartDataItem("EMEA", "2000", "Hardware", 2547864));
    dataItems.add(new ChartDataItem("EMEA", "2000", "Software", 2584233));
    dataItems.add(new ChartDataItem("EMEA", "2001", "Hardware", 3654783));
    dataItems.add(new ChartDataItem("EMEA", "2001", "Software", 3875466));
    dataItems.add(new ChartDataItem("EMEA", "2002", "Hardware", 4568734));
    dataItems.add(new ChartDataItem("EMEA", "2002", "Software", 4206743));
    dataItems.add(new ChartDataItem("North America", "2000", "Hardware", 4898735));
    dataItems.add(new ChartDataItem("North America", "2000", "Software", 4233768));
    dataItems.add(new ChartDataItem("North America", "2001", "Hardware", 5867432));
    dataItems.add(new ChartDataItem("North America", "2001", "Software", 4464531));
    dataItems.add(new ChartDataItem("North America", "2002", "Hardware", 6545421));
    dataItems.add(new ChartDataItem("North America", "2002", "Software", 7651321));
    return ModelUtils.toCollectionModel(dataItems);
  }    
  
  public CollectionModel getCustomerServiceData() {
    List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
    dataItems.add(new ChartDataItem("Total Claims", "Email", 750));
    dataItems.add(new ChartDataItem("Total Claims", "in Person", 100));
    dataItems.add(new ChartDataItem("Total Claims", "Live Chat", 250));
    dataItems.add(new ChartDataItem("Total Claims", "Phone", 310));
    dataItems.add(new ChartDataItem("Number of Complaints", "Email", 150));
    dataItems.add(new ChartDataItem("Number of Complaints", "in Person", 9));
    dataItems.add(new ChartDataItem("Number of Complaints", "Live Chat", 62));
    dataItems.add(new ChartDataItem("Number of Complaints", "Phone", 100));
    return ModelUtils.toCollectionModel(dataItems);
  }  
}
