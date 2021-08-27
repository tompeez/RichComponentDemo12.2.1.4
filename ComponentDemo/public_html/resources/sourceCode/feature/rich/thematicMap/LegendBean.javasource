package oracle.adfdemo.view.feature.rich.thematicMap;

import java.util.ArrayList;

import oracle.adfdemo.view.feature.rich.thematicMap.SampleMapData.MapData;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;


public class LegendBean {
  
  private String legendDisplay = "showHide";
  private String maxWidth = "25%";

  private boolean showSeparators = true;
  private boolean nestedLegend = true;
  private boolean textWrap = false;

  private boolean showRegions = true;
  private boolean showMarkers = false;
  private boolean showImages = false;

  protected static String[] usaStateRegions = {
    "East","West","West","Central","West","West","East","East","East","East","East",
    "West","West","Central","East","Central","Central","East","Central","East","East","East",
    "East","Central","East","Central","West","Central","West","East","East","West","East",
    "East","Central","East","Central","West","East","East","East","Central","East","Central",
    "West","East","East","West","East","Central","West"
  };
  protected static Double[] usaStateDensity = {
    94.37665,1.24462,56.27068,56.03711,239.1459,48.52474,738.0906,460.8232,9856.487,350.6088,168.4414,211.7982,18.96809,
    231.1037,180.9798,54.53834,34.8968,109.8954,104.9297,43.06858,594.7675,839.4333,174.8113,66.60985,63.23721,
    87.12241,6.797963,23.773,24.5994,147.0481,1195.49,16.97618,411.1942,196.1311,9.747583,282.3375,54.68847,
    39.91201,283.8983,1018.139,153.8675,10.7396,153.9013,96.25769,33.63634,67.8924,202.6084,101.1886,77.08536,
    105.0077,5.805003                                         
  };
  
  public LegendBean() {
  }
  
  public CollectionModel getCityDataModel() {
    return ModelUtils.toCollectionModel(SampleMapData.getSampleUsaCitiesData(8));
  } 
  
  public CollectionModel getAreaDataModel() {
    return ModelUtils.toCollectionModel(getSampleUsaRegionsData());
  }

  public static ArrayList<SampleMapData.MapData> getSampleUsaRegionsData() {
    return createData(SampleMapData.usaStates, 0, new Object[][]{usaStateRegions, usaStateDensity});  
  }
  
  protected static ArrayList<SampleMapData.MapData> createData(String[] ids, int value, Object[][] categories) {
    ArrayList<SampleMapData.MapData> sampleData = new ArrayList();
    for (int i = 0; i < ids.length; i++) {
      Object[] dataCategories = new Object[categories.length];
      for (int j = 0; j < categories.length; j++) {
        dataCategories[j] = categories[j][i];
      }
      sampleData.add(new MapData(ids[i], dataCategories, value , null));
    }                 
    return sampleData;
  }   

  public void setLegendDisplay(String legendDisplay) {
    this.legendDisplay = legendDisplay;
  }

  public String getLegendDisplay() {
    return legendDisplay;
  }
  
  public void setMaxWidth(String maxWidth) {
    this.maxWidth = maxWidth;
  }

  public String getMaxWidth() {
    return maxWidth;
  }
  
  public void setShowSeparators(boolean showSeparators) {
    this.showSeparators = showSeparators;
  }

  public boolean isShowSeparators() {
    return showSeparators;
  }

  public void setNestedLegend(boolean nestedLegend) {
    this.nestedLegend = nestedLegend;
  }

  public boolean isNestedLegend() {
    return nestedLegend;
  }
  
  public void setTextWrap(boolean textWrap) {
    this.textWrap = textWrap;
  }

  public boolean isTextWrap() {
    return textWrap;
  }

  public void setShowRegions(boolean showRegions) {
    this.showRegions = showRegions;
  }

  public boolean isShowRegions() {
    return showRegions;
  }

  public void setShowMarkers(boolean showMarkers) {
    this.showMarkers = showMarkers;
  }

  public boolean isShowMarkers() {
    return showMarkers;
  }

  public void setShowImages(boolean showImages) {
    this.showImages = showImages;
  }

  public boolean isShowImages() {
    return showImages;
  }
}