package oracle.adfdemo.view.feature.rich.tagCloud.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Random;

import oracle.adfdemo.view.feature.rich.dvt.data.CensusData;
import oracle.adfdemo.view.feature.rich.dvt.data.GlobalGDP;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.SortCriterion;
import org.apache.myfaces.trinidad.model.RowKeyPropertyModel;

public class TagCloudData {
  private static Object[][] SOCIAL_NETWORKS = new Object[][] {
    { "Facebook", 76.8, "www.facebook.com" }, { "YouTube", 66.4, "www.youtube.com" },
    { "Twitter", 32.8, "www.twitter.com" }, { "Instagram", 28.5, "www.instagram.com" },
    { "Google+", 22.7, "www.google.com" }, { "LinkedIn", 16.6, "www.linkedin.com" },
    { "SnapChat", 14.2, "www.snapchat.com" }, { "Tumblr", 11.5, "www.tumblr.com" }, { "Vine", 11.1, "www.vine.com" },
    { "WhatsApp", 6.8, "www.whatsapp.com" }, { "reddit", 6.2, "www.redditt.com" }, { "Flickr", 5.4, "www.flickr.com" },
    { "Pinterest", 1.5, "www.pinterest.com" }
  };

  private static String[] COLORS = { "#267db3", "#ed6647", "#8561c8" };

  public static TagCloudData getInstance() {
    return new TagCloudData();
  }

  private boolean _sorted = false;
  private String dataSource = "";
  private Random random = new Random();
  private CollectionModel _model = null;

  public CollectionModel getSocialNetworksData() {
    List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < SOCIAL_NETWORKS.length; i++) {
      itemList.add(createMap("word", SOCIAL_NETWORKS[i][0], "frequency", SOCIAL_NETWORKS[i][1], "color", "#333333",
                             "shortDesc",
                             SOCIAL_NETWORKS[i][0] + ": " + String.format("%.2f", SOCIAL_NETWORKS[i][1]) +
                             "% of respondents", "destination", "https://" + SOCIAL_NETWORKS[i][2]));
    }
    dataSource = "social";
    //Create a CollectionModel whose row keys are defined by a unique data property ('word') in the model.
    //Drag and Drop demo requires this CollectionModel with rowKey based on data, so that add/remove operation does not affect the model.
    _model = new RowKeyPropertyModel(itemList, "word");
    return _model;
  }

  public CollectionModel getCensusData() {
    List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();

    List<CensusData.DataItem> censusList = CensusData.getAllStateData();
    for (int i = 0; i < censusList.size(); i++) {
      double freq = (double) censusList.get(i).getPopulation() / 1000000;
      itemList.add(createMap("word", censusList.get(i).getName(), "frequency", freq, "color", "#333333", "shortDesc",
                             censusList.get(i).getName() + ": " + String.format("%.2f", freq) + " million",
                             "destination",
                             "https://www.google.com/?gws_rd=ssl#q=" + censusList.get(i).getName() + "+census"));
    }
    dataSource = "Census";
    _model = ModelUtils.toCollectionModel(itemList);
    return _model;
  }

  public CollectionModel getGDPData() {
    List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
    Random random = new Random();
    for (int i = 0; i < GlobalGDP.COUNTRIES.length; i++) {
      itemList.add(createMap("word", GlobalGDP.COUNTRY_NAMES[i], "frequency", (double) GlobalGDP.GDP[i], "color",
                             "#333333", "shortDesc",
                             GlobalGDP.COUNTRY_NAMES[i] + ": " + GlobalGDP.FORMATTED_GDP[i] + " million USD",
                             "destination",
                             "https://www.google.com/?gws_rd=ssl#q=" + GlobalGDP.COUNTRY_NAMES[i] + "+GDP"));
    }
    dataSource = "GDP";
    _model = ModelUtils.toCollectionModel(itemList);
    return _model;
  }

  public void setRandomValue() {
    if (_model == null || _model.getRowCount() == 0)
      return;

    int count = _model.getRowCount();
    for (int idx = 0; idx < count; idx++) {
      Map<String, Object> item = (Map<String, Object>) _model.getRowData(idx);
      double freq = Math.random() * 100;
      item.put("frequency", freq);
      String shortDesc = item.get("word") + ": ";
      if (dataSource.equals("social")) {
        shortDesc += String.format("%.2f", freq) + "% of respondents";
      } else if (dataSource.equals("Census")) {
        shortDesc += String.format("%.2f", freq) + " million";
      } else if (dataSource.equals("GDP")) {
        shortDesc += String.format("%.2f", freq) + " million USD";
      }
      item.put("shortDesc", shortDesc);
    }
    sortData();
  }

  public void setRandomColor() {
    if (_model == null || _model.getRowCount() == 0)
      return;

    int count = _model.getRowCount();
    for (int idx = 0; idx < count; idx++) {
      Map<String, Object> item = (Map<String, Object>) _model.getRowData(idx);
      item.put("color", COLORS[random.nextInt(3)]);
    }
  }

  public void setDefaultColor() {
    if (_model == null || _model.getRowCount() == 0)
      return;

    int count = _model.getRowCount();
    for (int idx = 0; idx < count; idx++) {
      Map<String, Object> item = (Map<String, Object>) _model.getRowData(idx);
      item.put("color", "#333333");
    }
  }

  public void addData(List<Map<String, Object>> list) {
    if (_model == null)
      return;

    Collection items = (Collection) _model.getWrappedData();
    items.addAll(list);
    sortData();
  }

  public void removeData(List<Map<String, Object>> list) {
    if (_model == null || _model.getRowCount() == 0)
      return;

    Collection items = (Collection) _model.getWrappedData();
    items.removeAll(list);
    sortData();
  }

  public CollectionModel getSortedData(boolean sorted) {
    _sorted = sorted;
    sortData();
    return _model;
  }


  private void sortData() {
    if (_model == null || _model.getRowCount() == 0)
      return;

    _model.setSortCriteria(null);
    if (_sorted) {
      List<SortCriterion> sortCriterions = new ArrayList<SortCriterion>();
      SortCriterion sortCriterion = new SortCriterion("frequency", false);
      sortCriterions.add(sortCriterion);
      _model.setSortCriteria(sortCriterions);
    }
  }

  private Map<String, Object> createMap(Object... values) {
    Map<String, Object> map = new HashMap<String, Object>();
    for (int i = 0; i < values.length; i += 2) {
      map.put(values[i].toString(), values[i + 1]);
    }
    return map;
  }
}
