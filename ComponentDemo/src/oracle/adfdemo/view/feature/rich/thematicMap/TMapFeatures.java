package oracle.adfdemo.view.feature.rich.thematicMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.Set;

import javax.faces.model.SelectItem;

import oracle.adf.view.faces.bi.component.thematicMap.UIAreaDataLayer;
import oracle.adf.view.faces.bi.component.thematicMap.UIPointDataLayer;
import oracle.adf.view.faces.bi.component.thematicMap.UIThematicMap;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;


public class TMapFeatures {
  private List m_basemapNames = new ArrayList();
  private List m_layerNames = new ArrayList();
  private String m_basemap = "usa";
  private String m_layer = "states";
  private String m_areaLocationChild = "area";
  private CollectionModel m_stateModel;
  private CollectionModel m_countyModel;
  private CollectionModel m_countryModel;
  private CollectionModel m_continentModel;
  private CollectionModel m_regionModel;
  private CollectionModel m_usaCitiesModel;
  private CollectionModel m_worldCitiesModel;
  
  private boolean m_showAreaPattern = false;
  private String m_pointGradientEffect = "none";
  private String m_areaGradientEffect = "none";
  
  private String m_markerShape = "circle";

  private String m_animationOnDisplay = "alphaFade";
  private String m_animationOnMapChange = "alphaFade";
  private String m_animationOnAreaDataLayerChange = "alphaFade";
  private String m_animationOnPointDataLayerChange = "alphaFade";

  private String m_selectionMode = "multiple";
  private String m_selectionModePdl = "multiple";
  private String m_selection = "";
  private String m_selectionPdl = "";
  
  private boolean m_disablePan = false;
  private boolean m_disableZoom = false;
  private String m_initialZooming = "none";
  
  private String m_contentDelivery = "whenAvailable";
  private String m_pdlEmptyText ="No Data";
  private String m_adlEmptyText = "No Data";
  private String m_alLabelDisplay = "auto";
  private String m_alLabelStyle = "";
  private String m_alLabelType = "short";
  
  // for area data layer areas/markers
  private String m_adlLabelDisplay = "off";
  private double m_adlOpacity = 1.0;
  private double m_adlScaleX = 1.0;
  private double m_adlScaleY = 1.0;
  private String m_adlLabelStyle = "";
  private String m_adlLabelPosition = "center";
  
  // for point data layer markers
  private String m_pdlLabelDisplay = "off";
  private double m_pdlOpacity = 1.0;
  private double m_pdlScaleX = 1.0;
  private double m_pdlScaleY = 1.0;
  private String m_pdlLabelStyle = "";
  private String m_pdlLabelPosition = "center";

  private String m_controlPanelBehavior = "initCollapsed";
  private String m_tooltipDisplay = "auto";

  private int m_areaDataPercent = 80;
  private int m_oldAreaDataPercent = -1;
  private int m_pointDataPercent = 5;
  private int m_oldPointDataPercent = -1;
  
  private String m_legendDisplay = "showHide";
  private boolean m_showLegendSeparators = false;
  
  private double m_maxZoom = 6.0;

  public TMapFeatures() {
    super();
    m_basemapNames = new ArrayList<SelectItem>();
    String[] basemaps = {
      "usa", "world", "africa", "asia", "australia", "europe", "northAmerica", "southAmerica", "apac", "emea",
      "latinAmerica", "usaAndCanada", "worldRegions"
    };
    for (String basemap : basemaps) {
      SelectItem item = new SelectItem(basemap, basemap);
      m_basemapNames.add(item);
    }
    m_showAreaPattern = false;
  }
  
  public void setMaxZoom(double maxZoom) {
      m_maxZoom = maxZoom;
  }
  
  public double getMaxZoom() {
    return m_maxZoom;
  }

  public void setBasemapNames(List basemapNames) {
    m_basemapNames = basemapNames;
  }

  public List getBasemapNames() {
    return m_basemapNames;
  }
  
  public void setLegendDisplay(String value) {
    m_legendDisplay = value;
  }

  public String getLegendDisplay() {
    return m_legendDisplay;
  }
  
  public void setShowLegendSeparators(boolean value) {
    m_showLegendSeparators = value;
  }

  public boolean getShowLegendSeparators() {
    return m_showLegendSeparators;
  }
  
  public void setAreaDataPercent(int value) {
    m_areaDataPercent = value;
  }

  public int getAreaDataPercent() {
    return m_areaDataPercent;
  }
  
  public void setPointDataPercent(int value) {
    m_pointDataPercent = value;
  }

  public int getPointDataPercent() {
    return m_pointDataPercent;
  }
  
  public void setControlPanelBehavior(String value) {
    m_controlPanelBehavior = value;
  }

  public String getControlPanelBehavior() {
    return m_controlPanelBehavior;
  }
  
  public void setTooltipDisplay(String value) {
    m_tooltipDisplay = value;
  }

  public String getTooltipDisplay() {
    return m_tooltipDisplay;
  }
  
  public void setContentDelivery(String value) {
    m_contentDelivery = value;
  }

  public String getContentDelivery() {
    return m_contentDelivery;
  }
  
  public void setPdlEmptyText(String value) {
    m_pdlEmptyText = value;
  }

  public String getPdlEmptyText() {
    return m_pdlEmptyText;
  }
  
  public void setAdlEmptyText(String value) {
    m_adlEmptyText = value;
  }

  public String getAdlEmptyText() {
    return m_adlEmptyText;
  }
  
  public void setAlLabelDisplay(String value) {
    m_alLabelDisplay = value;
  }

  public String getAlLabelDisplay() {
    return m_alLabelDisplay;
  }
  
  public void setAlLabelStyle(String value) {
    m_alLabelStyle = value;
  }

  public String getAlLabelStyle() {
    return m_alLabelStyle;
  }
  
  public void setAlLabelType(String value) {
    m_alLabelType = value;
  }

  public String getAlLabelType() {
    return m_alLabelType;
  }
  

  public void setAdlLabelDisplay(String value) {
    m_adlLabelDisplay = value;
  }

  public String getAdlLabelDisplay() {
    return m_adlLabelDisplay;
  }
  
  public void setAdlLabelStyle(String value) {
    m_adlLabelStyle = value;
  }

  public String getAdlLabelStyle() {
    return m_adlLabelStyle;
  }
  
  public void setAdlLabelPosition(String value) {
    m_adlLabelPosition = value;
  }

  public String getAdlLabelPosition() {
    return m_adlLabelPosition;
  }
  
  public void setPdlLabelDisplay(String value) {
    m_pdlLabelDisplay = value;
  }

  public String getPdlLabelDisplay() {
    return m_pdlLabelDisplay;
  }
  
  public void setPdlLabelStyle(String value) {
    m_pdlLabelStyle = value;
  }

  public String getPdlLabelStyle() {
    return m_pdlLabelStyle;
  }
  
  public void setPdlLabelPosition(String value) {
    m_pdlLabelPosition = value;
  }

  public String getPdlLabelPosition() {
    return m_pdlLabelPosition;
  }
  
  public void setAdlOpacity(double value) {
    m_adlOpacity = value;
  }

  public double getAdlOpacity() {
    return m_adlOpacity;
  }
  
  public void setAdlScaleX(double value) {
    m_adlScaleX = value;
  }

  public double setAdlScaleX() {
    return m_adlScaleX;
  }
  public void setAdlScaleY(double value) {
    m_adlScaleY = value;
  }

  public double getAdlScaleY() {
    return m_adlScaleY;
  }
  
  public void setPdlOpacity(double value) {
    m_pdlOpacity = value;
  }

  public double getPdlOpacity() {
    return m_pdlOpacity;
  }
  
  public void setPdlScaleX(double value) {
    m_pdlScaleX = value;
  }

  public double setPdlScaleX() {
    return m_pdlScaleX;
  }
  public void setPdlScaleY(double value) {
    m_pdlScaleY = value;
  }

  public double getPdlScaleY() {
    return m_pdlScaleY;
  }
  
  
  public void setPointGradientEffect(boolean effect) {
    if (effect)
      m_pointGradientEffect = "auto";
    else
      m_pointGradientEffect = "none";
  }

  public boolean getPointGradientEffect() {
    if (m_pointGradientEffect.equals("auto"))
      return true;
    else
      return false;
  }
  
  public void setAreaGradientEffect(boolean effect) {
    if (effect)
      m_areaGradientEffect = "auto";
    else
      m_areaGradientEffect = "none";
  }

  public boolean getAreaGradientEffect() {
    if (m_areaGradientEffect.equals("auto"))
      return true;
    else
      return false;
  }
  

  public void setDisablePan(boolean applyPan) {
    if (applyPan != m_disablePan) {
      m_disablePan = applyPan;
    }
  }

  public boolean getDisablePan() {
    return m_disablePan;
  }

  public void setDisableZoom(boolean applyZoom) {
    if (applyZoom != m_disableZoom) {
      m_disableZoom = applyZoom;
    }
  }

  public boolean getDisableZoom() {
    return m_disableZoom;
  }
  
  public void setMarkerShape(String shape) {
    m_markerShape = shape;
  }

  public String getMarkerShape() {
    return m_markerShape;
  }

  public void setBasemap(String basemap) {
    this.m_basemap = basemap;
    this.getLayerNames();
  }

  public String getBasemap() {
    return m_basemap;
  }

  public void setShowAreaPattern(boolean showPattern) {
    m_showAreaPattern = showPattern;
  }

  public boolean getShowAreaPattern() {
    return m_showAreaPattern;
  }

  public List getLayerNames() {
    List<String> layerNames = new ArrayList<String>();
    List layerNamesItem = new ArrayList();
    if (m_basemap.equalsIgnoreCase("usa")) {
      layerNames.add("country");
      layerNames.add("states");
      layerNames.add("counties");
    } else if (m_basemap.equalsIgnoreCase("world")) {
      layerNames.add("continents");
      layerNames.add("countries");
    } else if (m_basemap.equalsIgnoreCase("africa") || m_basemap.equalsIgnoreCase("asia") ||
               m_basemap.equalsIgnoreCase("australia") || m_basemap.equalsIgnoreCase("europe") ||
               m_basemap.equalsIgnoreCase("northAmerica") || m_basemap.equalsIgnoreCase("southAmerica")) {
      layerNames.add("continent");
      layerNames.add("countries");
    } else if (m_basemap.equalsIgnoreCase("apac") || m_basemap.equalsIgnoreCase("emea") ||
               m_basemap.equalsIgnoreCase("latinAmerica") || m_basemap.equalsIgnoreCase("usaAndCanada")) {
      layerNames.add("region");
      layerNames.add("countries");
    } else if (m_basemap.equalsIgnoreCase("worldRegions")) {
      layerNames.add("regions");
      layerNames.add("countries");
    }

    for (String layer : layerNames) {
      SelectItem item = new SelectItem(layer, layer);
      layerNamesItem.add(item);
    }

    if (!checkLayersSame(layerNamesItem, layerNames)) {
      m_layer = layerNames.get(0);
    }
    m_layerNames = layerNamesItem;
    return m_layerNames;
  }

  public void setLayerNames(List l) {
    m_layerNames = l;
  }

  public void setLayer(String layer) {
    m_layer = layer;
  }

  public String getLayer() {
    return m_layer;
  }

  public void setAreaLocationChild(String child) {
    m_areaLocationChild = child;
  }

  public String getAreaLocationChild() {
    return m_areaLocationChild;
  }

  public void setAnimationOnPointDataLayerChange(String animationOnPointDataLayerChange) {
    m_animationOnPointDataLayerChange = animationOnPointDataLayerChange;
  }

  public String getAnimationOnPointDataLayerChange() {
    return m_animationOnPointDataLayerChange;
  }

  public void setAnimationOnDisplay(String animationOnDisplay) {
    m_animationOnDisplay = animationOnDisplay;
  }

  public String getAnimationOnDisplay() {
    return m_animationOnDisplay;
  }

  public void setAnimationOnMapChange(String animationOnMapChange) {
    m_animationOnMapChange = animationOnMapChange;
  }

  public String getAnimationOnMapChange() {
    return m_animationOnMapChange;
  }

  public void setAnimationOnAreaDataLayerChange(String animationOnAreaDataLayerChange) {
    m_animationOnAreaDataLayerChange = animationOnAreaDataLayerChange;
  }
  
  public void setInitialZooming(boolean zooming) {
    m_initialZooming = zooming ? "auto" : "none";
  }
  
  public boolean getInitialZooming() {
    return m_initialZooming.equals("auto");
  }

  public String getAnimationOnAreaDataLayerChange() {
    return m_animationOnAreaDataLayerChange;
  }

  public void setSelectionMode(String selectionMode) {
    m_selectionMode = selectionMode;
  }

  public String getSelectionMode() {
    return m_selectionMode;
  }

  public String getSelectionString() {
    return "Selected Areas: " + m_selection;
  }

  public void processSelection(SelectionEvent selectionEvent) {
    UIAreaDataLayer dataLayer = (UIAreaDataLayer) selectionEvent.getSource();
    CollectionModel model = (CollectionModel) dataLayer.getValue();
    RowKeySet selectedSet = dataLayer.getSelectedRowKeys();

    StringBuilder infoString = new StringBuilder();
    Iterator i = selectedSet.iterator();
    while (i.hasNext()) {
      Object rowKey = i.next();
      model.setRowKey(rowKey);
      if (model.getRowData() instanceof SampleMapData.MapData) {
        SampleMapData.MapData selected = (SampleMapData.MapData) model.getRowData();
        infoString.append(selected.getId()).append("; ");
      }
    }
    m_selection = infoString.toString();
  }
  
  public void setSelectionModePdl(String selectionMode) {
    m_selectionModePdl = selectionMode;
  }

  public String getSelectionModePdl() {
    return m_selectionModePdl;
  }

  public String getSelectionPdlString() {
    return "Selected Points: " + m_selectionPdl;
  }

  public void processSelectionPdl(SelectionEvent selectionEvent) {
    UIPointDataLayer dataLayer = (UIPointDataLayer) selectionEvent.getSource();
    CollectionModel model = (CollectionModel) dataLayer.getValue();
    RowKeySet selectedSet = dataLayer.getSelectedRowKeys();

    StringBuilder infoString = new StringBuilder();
    Iterator i = selectedSet.iterator();
    while (i.hasNext()) {
      Object rowKey = i.next();
      model.setRowKey(rowKey);
      if (model.getRowData() instanceof SampleMapData.MapData) {
        SampleMapData.MapData selected = (SampleMapData.MapData) model.getRowData();
        infoString.append(selected.getId()).append("; ");
      }
    }
    m_selectionPdl = infoString.toString();
  }

  public CollectionModel getAreaDataModel() {
    CollectionModel areaDataModel = null;
    if (m_layer != null) {
      if (m_layer.equalsIgnoreCase("states")) {
        if (m_stateModel == null || m_areaDataPercent != m_oldAreaDataPercent)
          m_stateModel = ModelUtils.toCollectionModel(SampleMapData.getSampleUsaStatesData(m_areaDataPercent));
        areaDataModel = m_stateModel;
      } else if (m_layer.equalsIgnoreCase("counties")) {
        if (m_countyModel == null || m_areaDataPercent != m_oldAreaDataPercent)
          m_countyModel = ModelUtils.toCollectionModel(SampleMapData.getSampleUsaCountiesData(m_areaDataPercent));
        areaDataModel = m_countyModel;
      } else if (m_layer.equalsIgnoreCase("countries") || m_layer.equalsIgnoreCase("country")) {
        if (m_countryModel == null || m_areaDataPercent != m_oldAreaDataPercent)
          m_countryModel = ModelUtils.toCollectionModel(SampleMapData.getSampleWorldCountriesData(m_areaDataPercent));
        areaDataModel = m_countryModel;
      } else if (m_layer.equalsIgnoreCase("continents") || m_layer.equalsIgnoreCase("continent")) {
        if (m_continentModel == null || m_areaDataPercent != m_oldAreaDataPercent)
          m_continentModel = ModelUtils.toCollectionModel(SampleMapData.getSampleWorldContinentsData(m_areaDataPercent));
        areaDataModel = m_continentModel;
      } else if (m_layer.equalsIgnoreCase("regions") || m_layer.equalsIgnoreCase("region")) {
        if (m_regionModel == null || m_areaDataPercent != m_oldAreaDataPercent)
          m_regionModel = ModelUtils.toCollectionModel(SampleMapData.getSampleWorldRegionsData(m_areaDataPercent));
        areaDataModel = m_regionModel;
      }
      //update the old to the new value
      m_oldAreaDataPercent = m_areaDataPercent;
    }
    return areaDataModel;
  }

  public CollectionModel getCityDataModel() {
    CollectionModel pointModel = null;
    if (m_basemap.equalsIgnoreCase("usa")) {
      if (m_usaCitiesModel == null || m_pointDataPercent != m_oldPointDataPercent)
        m_usaCitiesModel = ModelUtils.toCollectionModel(SampleMapData.getSampleUsaCitiesData(m_pointDataPercent));
      pointModel = m_usaCitiesModel;
    } else {
      if (m_worldCitiesModel == null || m_pointDataPercent != m_oldPointDataPercent)
        m_worldCitiesModel = ModelUtils.toCollectionModel(SampleMapData.getSampleWorldCitiesData(m_pointDataPercent));
      pointModel = m_worldCitiesModel;
    }
    //update the old to the new value
    m_oldPointDataPercent = m_pointDataPercent;
    return pointModel;
  }


  private boolean checkLayersSame(List l, List name) {
    if (name.contains(m_layer)) {
      return true;
    }
    if (m_layerNames.size() != l.size()) {
      return false;
    } else {
      for (int i = 0; i < m_layerNames.size(); i++) {
        SelectItem refItem = (SelectItem) m_layerNames.get(i);
        SelectItem newItem = (SelectItem) l.get(i);
        if (!refItem.getValue().equals(newItem.getValue())) {
          return false;
        }
      }
      return true;
    }
  }
}
