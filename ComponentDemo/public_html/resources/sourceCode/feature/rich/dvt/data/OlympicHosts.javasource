package oracle.adfdemo.view.feature.rich.dvt.data;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.faces.component.UIComponent;

import oracle.adf.view.faces.bi.component.chart.UIChartBase;
import oracle.adf.view.faces.bi.component.thematicMap.UIAreaDataLayer;

import oracle.adf.view.rich.component.rich.layout.RichPanelDashboard;

import oracle.adfdemo.view.feature.rich.thematicMap.SampleMapData;
import oracle.adfdemo.view.feature.rich.thematicMap.SampleMapData.MapData;

import oracle.adfdemo.view.feature.rich.thematicMap.SampleMapData.MapDataComparator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class OlympicHosts {

  private static String[] hostCountries = {
    "GRC", "FRA", "USA", "GRC", "GBR", "SWE", "BEL", "FRA", "FRA", "CHE", "NLD", "USA", "USA", "DEU", "DEU", "CHE",
    "GBR", "NOR", "FIN", "ITA", "AUS", "SWE", "USA", "ITA", "AUT", "JPN", "FRA", "MEX", "JPN", "DEU", "AUT", "CAN",
    "USA", "RUS", "BTH", "USA", "CAN", "PRK", "FRA", "ESP", "NOR", "USA", "JPN", "AUS", "USA", "GRC", "ITA", "CHN",
    "CAN", "GBR", "RUS", "BRA", "PRK", "JPN"
  };

  private static String[] hostContinents = {
    "europe", "europe", "northAmerica", "europe", "europe", "europe", "europe", "europe", "europe", "europe", "europe",
    "northAmerica", "northAmerica", "europe", "europe", "europe", "europe", "europe", "europe", "europe", "australia",
    "europe", "northAmerica", "europe", "europe", "asia", "europe", "northAmerica", "asia", "europe", "europe",
    "northAmerica", "northAmerica", "asia", "europe", "northAmerica", "northAmerica", "asia", "europe", "europe",
    "europe", "northAmerica", "asia", "australia", "northAmerica", "europe", "europe", "asia", "northAmerica", "europe",
    "asia", "southAmerica", "asia", "asia"
  };

  private static String[] hostSeason = {
    "Summer", "Summer", "Summer", "Summer", "Summer", "Summer", "Summer", "Winter", "Summer", "Winter", "Summer",
    "Winter", "Summer", "Winter", "Summer", "Winter", "Summer", "Winter", "Summer", "Winter", "Summer", "Summer",
    "Winter", "Summer", "Winter", "Summer", "Winter", "Summer", "Winter", "Summer", "Winter", "Summer", "Winter",
    "Summer", "Winter", "Summer", "Winter", "Summer", "Winter", "Summer", "Winter", "Summer", "Winter", "Summer",
    "Winter", "Summer", "Winter", "Summer", "Winter", "Summer", "Winter", "Summer", "Winter", "Summer"
  };

  private static int[] hostYears = {
    1896, 1900, 1904, 1906, 1908, 1912, 1920, 1924, 1924, 1928, 1928, 1932, 1932, 1936, 1936, 1948, 1948, 1952, 1952,
    1956, 1956, 1956, 1960, 1960, 1964, 1964, 1968, 1968, 1972, 1972, 1976, 1976, 1980, 1980, 1984, 1984, 1988, 1988,
    1992, 1992, 1994, 1996, 1998, 2000, 2002, 2004, 2006, 2008, 2010, 2012, 2014, 2016, 2018, 2020
  };

  private static String[] hostCities = {
    "Athens", "Paris", "St. Louis", "Athens", "London", "Stockholm", "Antwerp", "Chamonix", "Paris", "St. Moritz",
    "Amsterdam", "Lake Placid", "Los Angeles", "Garmisch-Partenkirchen", "Berlin", "St. Moritz", "London", "Oslo",
    "Helsinki", "Cortina d'Ampezzo", "Melbourne", "Stockholm", "Squaw Valley", "Rome", "Innsbruck", "Tokyo", "Grenoble",
    "Mexico City", "Sapporo", "Munich", "Innsbruck", "Montreal", "Lake Placid", "Moscow", "Sarajevo", "Los Angeles",
    "Calgary", "Seoul", "Albertville", "Barcelona", "Lillehammer", "Atlanta", "Nagano", "Sydney", "Salt Lake City",
    "Athens", "Turin", "Beijing", "Vancouver", "London", "Sochi", "Rio de Janeiro", "Pyeongchang", "Tokyo"
  };

  private String season = "All";
  private String selectedSeason = season;
  private String selectedCountry = null;
  private RichPanelDashboard hostsByYear;
  private UIAreaDataLayer adl;
  private UIChartBase barChart;

  public OlympicHosts() {
  }

  public CollectionModel getOlympicHostsByYear() {
    List<MapData> hostsByYear = new ArrayList<MapData>();
    for (int i = 0; i < hostCountries.length; i++) {
      if ((selectedSeason.equals("All") || selectedSeason.equals(hostSeason[i])) &&
          (selectedCountry == null || selectedCountry.equals(hostCountries[i]))) {
        hostsByYear.add(new MapData(hostCountries[i], new Object[] { hostContinents[i], hostSeason[i], hostCities[i] },
                                    hostYears[i],
                                    "Winter".equals(hostSeason[i]) ? new Color(38, 125, 179) :
                                    new Color(250, 213, 92)));
      }
    }
    return ModelUtils.toCollectionModel(hostsByYear);
  }

  public CollectionModel getOlympicHostsByFrequency() {
    List<MapData> hostsByYear = new ArrayList<MapData>();
    for (int i = 0; i < hostCountries.length; i++) {
      if ((season.equals("All") || season.equals(hostSeason[i]))) {
        hostsByYear.add(new MapData(hostCountries[i], new Object[] { hostContinents[i], hostSeason[i], hostCities[i] },
                                    hostYears[i],
                                    "Winter".equals(hostSeason[i]) ? new Color(38, 125, 179) :
                                    new Color(250, 213, 92)));
      }
    }

    Map<String, MapData> hostsByFreq = new HashMap<String, MapData>();
    for (MapData host : hostsByYear) {
      Object[] cats = host.getCategories();
      String id = host.getId() + cats[1];
      MapData freqHost = hostsByFreq.get(id);
      if (freqHost == null) {
        hostsByFreq.put(id, new MapData(host.getId(), cats, 1, host.getColor()));
      } else {
        freqHost.setValue(freqHost.getValue() + 1);
        hostsByFreq.put(id, freqHost);
      }
    }
    List<MapData> freqList = new ArrayList<MapData>(hostsByFreq.values());
    Collections.sort(freqList, new MapDataComparator());
    Collections.reverse(freqList);
    return ModelUtils.toCollectionModel(freqList);
  }

  public CollectionModel getOlympicHostsByFrequencyForMap() {
    List<MapData> hostsByYear = new ArrayList<MapData>();
    for (int i = 0; i < hostCountries.length; i++) {
      if ((season.equals("All") || season.equals(hostSeason[i]))) {
        hostsByYear.add(new MapData(hostCountries[i], new Object[] { hostContinents[i], hostSeason[i], hostCities[i] },
                                    hostYears[i],
                                    "Winter".equals(hostSeason[i]) ? new Color(38, 125, 179) :
                                    new Color(250, 213, 92)));
      }
    }

    Map<String, MapData> hostsByFreq = new HashMap<String, MapData>();
    for (MapData host : hostsByYear) {
      Object[] cats = host.getCategories();
      String id = host.getId();
      MapData freqHost = hostsByFreq.get(id);
      if (freqHost == null) {
        hostsByFreq.put(id, new MapData(host.getId(), cats, 1, host.getColor()));
      } else {
        freqHost.setValue(freqHost.getValue() + 1);
        if (!cats[1].equals(freqHost.getCategories()[1]))
          freqHost.setColor(new Color(104, 193, 130));
        hostsByFreq.put(id, freqHost);
      }
    }
    return ModelUtils.toCollectionModel(new ArrayList<MapData>(hostsByFreq.values()));
  }

  public void setSeason(String season) {
    this.season = season;
    this.selectedSeason = season;
  }

  public String getSeason() {
    return this.season;
  }

  public void setLayout(RichPanelDashboard table) {
    this.hostsByYear = table;
  }

  public RichPanelDashboard getLayout() {
    return this.hostsByYear;
  }

  public void setDataLayer(UIAreaDataLayer adl) {
    this.adl = adl;
  }

  public UIAreaDataLayer getDataLayer() {
    return this.adl;
  }

  public void setBarChart(UIChartBase barChart) {
    this.barChart = barChart;
  }

  public UIChartBase getBarChart() {
    return this.barChart;
  }

  public void processSelection(SelectionEvent event) {
    RequestContext rc = RequestContext.getCurrentInstance();
    UIComponent source = (UIComponent) event.getSource();
    RowKeySet selectedSet = null;
    CollectionModel model = null;
    if (source instanceof UIAreaDataLayer) {
      UIAreaDataLayer dataLayer = (UIAreaDataLayer) source;
      model = (CollectionModel) dataLayer.getValue();
      selectedSet = dataLayer.getSelectedRowKeys();
      rc.addPartialTarget(this.barChart);
    } else if (source instanceof UIChartBase) {
      UIChartBase chart = (UIChartBase) source;
      selectedSet = chart.getSelectedRowKeys();
      model = (CollectionModel) chart.getValue();
      rc.addPartialTarget(this.adl);
    }
    // get the selected country id from the data model
    if (selectedSet != null && selectedSet.size() > 0) {
      Iterator it = selectedSet.iterator();
      if (it.hasNext()) {
        Object rowKey = it.next();
        model.setRowKey(rowKey);
        if (model.getRowData() instanceof SampleMapData.MapData) {
          SampleMapData.MapData selected = (SampleMapData.MapData) model.getRowData();
          selectedCountry = selected.getId();
          if (source instanceof UIChartBase)
            selectedSeason = (String)selected.getCategories()[1];
          else
            selectedSeason = season;
        }
      }
    } else {
      selectedCountry = null;
      selectedSeason = season;
    }

    RowKeySet syncRowKeys = new RowKeySetImpl();
    if (source instanceof UIAreaDataLayer) {
      model = (CollectionModel) barChart.getValue();
      for (int i = 0; i < model.getRowCount(); i++) {
        model.setRowIndex(i);
        if (model.getRowData() instanceof SampleMapData.MapData) {
          MapData currRow = (SampleMapData.MapData) model.getRowData();
          if (currRow.getId().equals(selectedCountry))
            syncRowKeys.add(model.getRowKey());
        }
      }
      barChart.setSelectedRowKeys(syncRowKeys);
    } else if (source instanceof UIChartBase) {
      model = (CollectionModel) adl.getValue();
      for (int i = 0; i < model.getRowCount(); i++) {
        model.setRowIndex(i);
        if (model.getRowData() instanceof SampleMapData.MapData) {
          MapData currRow = (SampleMapData.MapData) model.getRowData();
          if (currRow.getId().equals(selectedCountry)) {
            syncRowKeys.add(model.getRowKey());
            break;
          }
        }
      }
      adl.setSelectedRowKeys(syncRowKeys);
    }

    // update table
    rc.addPartialTarget(this.hostsByYear);
  }
}
