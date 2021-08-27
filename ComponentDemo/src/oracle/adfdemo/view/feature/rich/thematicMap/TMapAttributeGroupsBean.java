package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.geoMap.MapUtil;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adf.view.rich.model.NumberRange;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;


public class TMapAttributeGroupsBean {

    private ArrayList<StateData> m_stateData = new ArrayList<StateData>();
    private ArrayList<CountyData> m_countyData = new ArrayList<CountyData>();
    private CollectionModel m_stateModel;
    private CollectionModel m_countyModel;
    private String[] m_stateNames;
    private String[] m_stateAbbrevs;
    private String[] m_countyIds;


    private static final String UNEMPLOYMENT = "Unemployment Rate";
    private static final String DROPOUT = "High School Dropout Rate";
    private static final String SODAPOP = "Soda vs Pop";
    private static final String CAR_MAKE = "Most popular make of car";
    private static final String HIGH = "High";
    private static final String MEDIUM = "Medium";
    private static final String LOW = "Low";
    private static final String POP = "Pop";
    private static final String SODA = "Soda";
    private static final String COKE = "Coke";
    private static final String TOYOTA = "Toyota";
    private static final String HONDA = "Honda";
    private static final String FORD = "Ford";
    private static final String BMW = "BMW";
    private static final String VOLKSWAGEN = "Volkswagen";
    private static final String NONE = "None";

    private static final String AREA_FILL_COLOR_DROP_TARGET_ID =
        "areaFillColorMeasure";
    private static final String MARKER_TYPE_DROP_TARGET_ID =
        "markerTypeMeasure";
    private static final String MARKER_COLOR_DROP_TARGET_ID =
        "markerColorMeasure";
    private static final String MARKER_SIZE_DROP_TARGET_ID =
        "markerSizeMeasure";

    private static final String CLEAR_AREA_FILL_COLOR_BUTTON_ID =
        "clearAreaFillColorButton";
    private static final String CLEAR_MARKER_SHAPE_BUTTON_ID =
        "clearMarkerShapeButton";
    private static final String CLEAR_MARKER_COLOR_BUTTON_ID =
        "clearMarkerColorButton";
    private static final String CLEAR_MARKER_SIZE_BUTTON_ID =
        "clearMarkerSizeButton";

    private String areaFillColorMeasure;
    private String areaFillColorType;

    private String markerTypeMeasure;
    private String markerType;
    private String markerColorMeasure;
    private String markerColorType;

    private String markerSizeMeasure;
    private boolean showMarker;
    private double filterMin;
    private double filterMax;
    
    private boolean includeLegend = true;
    private String displayMode = "showHide";
    private boolean showSeparators = true;
    
    
    private NumberRange filterRange = new NumberRange(0.0, 100.0);


    public TMapAttributeGroupsBean() {
        m_stateNames = TMapUtils.stateNames;
        m_stateAbbrevs = TMapUtils.stateAbbrevs;
        m_countyIds = TMapUtils.countyIds;
        int size = m_stateNames.length;
        setAreaFillColorMeasure(NONE);
        setAreaFillColorType("");
        setMarkerTypeMeasure(NONE);
        setMarkerType("");
        setMarkerColorMeasure(NONE);
        setMarkerColorType("");

        setMarkerSizeMeasure(NONE);

        setShowMarker(true);
        setFilterMin(0.0);
        setFilterMax(100.0);

        Random rand = new Random(0);
        for (int i = 0; i < size; i++) {
            m_stateData.add(new StateData(m_stateNames[i], m_stateAbbrevs[i],
                                          rand.nextDouble(), rand.nextDouble(),
                                          rand.nextDouble(),
                                          rand.nextDouble()));
        }

        size = m_countyIds.length;
        for (int i = 0; i < size; i++) {
            m_countyData.add(new CountyData(m_countyIds[i],
                                            rand.nextDouble()));
        }

    }

    public CollectionModel getStatesModel() {
        if (m_stateModel == null) {
            m_stateModel = ModelUtils.toCollectionModel(m_stateData);
        }

        return m_stateModel;
    }

    public CollectionModel getCountiesModel() {
        if (m_countyModel == null) {
            m_countyModel = ModelUtils.toCollectionModel(m_countyData);
        }

        return m_countyModel;
    }

    public String getStateFillColorColumn() {

        if (!getStatesModel().isRowAvailable())
            getStatesModel().setRowIndex(0);

        StateData rowData = (StateData)getStatesModel().getRowData();
        String currentColumnName = getAreaFillColorMeasure();
        if (currentColumnName.equalsIgnoreCase(NONE)) {
            setAreaFillColorType("");
            // return non-null just to prevent possible NPE
            return rowData.getStringValue(SODAPOP);
        }
        return rowData.getStringValue(currentColumnName);
    }

    public String getMarkerTypeColumn() {

        if (!getStatesModel().isRowAvailable())
            getStatesModel().setRowIndex(0);

        StateData rowData = (StateData)getStatesModel().getRowData();
        String currentColumnName = getMarkerTypeMeasure();
        if (currentColumnName.equalsIgnoreCase(NONE)) {
            setMarkerType("");
            return rowData.getStringValue(SODAPOP);
        }
        return rowData.getStringValue(currentColumnName);
    }

    public String getMarkerColorColumn() {

        if (!getStatesModel().isRowAvailable())
            getStatesModel().setRowIndex(0);

        StateData rowData = (StateData)getStatesModel().getRowData();
        String currentColumnName = getMarkerColorMeasure();
        if (currentColumnName.equalsIgnoreCase(NONE)) {
            return rowData.getStringValue(SODAPOP);
        }
        return rowData.getStringValue(currentColumnName);
    }
    
    private StateData getCurrentStateData() {
      if (!getStatesModel().isRowAvailable())
          getStatesModel().setRowIndex(0);

      StateData rowData = (StateData)getStatesModel().getRowData();
      return rowData;
    }
    
    private String getCurrentStateDataValue(String columnName) {
      StateData rowData = getCurrentStateData();

      if (columnName.equalsIgnoreCase(NONE)) {
//          return rowData.getStringValue(SODAPOP);
          return null;
      }
      return rowData.getStringValue(columnName);      
    }
    
    private double getCurrentStateDoubleValue(String columnName) {
      StateData rowData = getCurrentStateData();
      if (columnName.equalsIgnoreCase(NONE)) {
          return 0;
      }
      return rowData.getDoubleValue(columnName);      
    }

    public double getMarkerSizeColumn() {
//        if (!getStatesModel().isRowAvailable())
//            getStatesModel().setRowIndex(0);
//
//        StateData rowData = (StateData)getStatesModel().getRowData();
//        StateData rowData = getCurrentStateData();
//        String currentColumnName = getMarkerSizeMeasure();
//        if (currentColumnName.equalsIgnoreCase(NONE)) {
//            return 2;
//        }
//        return 2 + 4 * rowData.getDoubleValue(currentColumnName);
        return 2 + 4*getCurrentStateDoubleValue(getMarkerSizeMeasure());
    }

    public String getColor() {
        return "#CFCFCF";
    }

    public Color getDefaultColorObj() {
        return MapUtil.convertToColor(getColor());
    }

    public String getUnemploymentText() {
        return UNEMPLOYMENT;
    }

    public String getSodapopText() {
        return SODAPOP;
    }

    public String getCarText() {
        return CAR_MAKE;
    }

    public String getDropoutText() {
        return DROPOUT;
    }

    public String getStateAbbr() {
        StateData rowData = (StateData)getStatesModel().getRowData();
        return rowData.getAbbr();

    }

    private static String convertSodaPopName(double value) {
        // names for carbonated beverages
        if (value < 0.4) {
            return POP;
        } else if (value < 0.8) {
            return SODA;
        } else {
            return COKE;
        }
    }


    private void pprAreaDataLayer() {
        UIComponent areaDataLayer =
            FacesContext.getCurrentInstance().getViewRoot().findComponent("demoTemplate").findComponent("demoTemplate:tm1:al1:dataLayer");
        RequestContext.getCurrentInstance().addPartialTarget(areaDataLayer);
    }

    public DnDAction dropListener(DropEvent dropEvent) {
        // Add event code here...
        RichOutputText dragSource =
            (RichOutputText)dropEvent.getDragComponent();
        RichOutputText dropTarget =
            (RichOutputText)dropEvent.getDropComponent();

        String dropId = dropTarget.getId();
        String dragValue = (String)dragSource.getValue();

        if (dropId.equals(AREA_FILL_COLOR_DROP_TARGET_ID)) {
            setAreaFillColorMeasure(dragValue);
            setAreaFillColorType("color");
        } else if (dropId.equals(MARKER_TYPE_DROP_TARGET_ID)) {
            setMarkerTypeMeasure(dragValue);
            setMarkerType("shape");
        } else if (dropId.equals(MARKER_COLOR_DROP_TARGET_ID)) {
            setMarkerColorMeasure(dragValue);
            setMarkerColorType("color");
        } else if (dropId.equals(MARKER_SIZE_DROP_TARGET_ID)) {
            setMarkerSizeMeasure(dragValue);
        }


        pprAreaDataLayer();

        return DnDAction.COPY;
    }


    public void clearMeasure(ActionEvent actionEvent) {
        // Add event code here...
        UIComponent button = actionEvent.getComponent();

        String id = button.getId();
        if (id.equals(CLEAR_AREA_FILL_COLOR_BUTTON_ID)) {
            setAreaFillColorType("");
            setAreaFillColorMeasure(NONE);
        } else if (id.equals(CLEAR_MARKER_SHAPE_BUTTON_ID)) {
            setMarkerType("");
            setMarkerTypeMeasure(NONE);
        } else if (id.equals(CLEAR_MARKER_COLOR_BUTTON_ID)) {
            setMarkerColorType("");
            setMarkerColorMeasure(NONE);
        } else if (id.equals(CLEAR_MARKER_SIZE_BUTTON_ID)) {
            setMarkerSizeMeasure(NONE);
        }
        pprAreaDataLayer();
    }


    public void setAreaFillColorMeasure(String stateFillColorMeasure) {
        this.areaFillColorMeasure = stateFillColorMeasure;
    }

    public String getAreaFillColorMeasure() {
        return areaFillColorMeasure;
    }

    public void setAreaFillColorType(String stateFillColorType) {
        this.areaFillColorType = stateFillColorType;
    }

    public String getAreaFillColorType() {
        return areaFillColorType;
    }


    public boolean getShowAreaFillLegend() {
        return getAreaFillColorType().equals("color");
    }

    public void setMarkerTypeMeasure(String markerTypeMeasure) {
        this.markerTypeMeasure = markerTypeMeasure;
    }

    public String getMarkerTypeMeasure() {
        return markerTypeMeasure;
    }

    public void setMarkerType(String markerType) {
        this.markerType = markerType;
    }

    public String getMarkerType() {
        return markerType;
    }

    public void setMarkerColorMeasure(String markerColorMeasure) {
        this.markerColorMeasure = markerColorMeasure;
    }

    public String getMarkerColorMeasure() {
        return markerColorMeasure;
    }

    public void setMarkerColorType(String markerColor) {
        this.markerColorType = markerColor;
    }

    public String getMarkerColorType() {
        return markerColorType;
    }


    public boolean getShowMarkerShapeLegend() {
        return getMarkerType().equals("shape");
    }


    public boolean getShowMarkerColorLegend() {
        return getMarkerColorType().equals("color");
    }

    public void setMarkerSizeMeasure(String markerSizeMeasure) {
        this.markerSizeMeasure = markerSizeMeasure;
    }

    public String getMarkerSizeMeasure() {
        return markerSizeMeasure;
    }

    public void setShowMarker(boolean showMarker) {
        this.showMarker = showMarker;
    }

    public boolean getShowMarker() {
      StateData rowData = getCurrentStateData();
      
        return showMarker;
    }

    public void setFilterMin(double filterMin) {
        this.filterMin = filterMin;
    }

    public double getFilterMin() {
        return filterMin;
    }

    public void setFilterMax(double filterMax) {
        this.filterMax = filterMax;
    }

    public double getFilterMax() {
        return filterMax;
    }

    public void setFilterRange(NumberRange filterRange) {
        this.filterRange = filterRange;
    }

    public NumberRange getFilterRange() {
        return filterRange;
    }

    public void setIncludeLegend(boolean includeLegend) {
        this.includeLegend = includeLegend;
    }

    public boolean isIncludeLegend() {
        return includeLegend;
    }

    public void setDisplayMode(String displayMode) {
        this.displayMode = displayMode;
    }

    public String getDisplayMode() {
        return displayMode;
    }

    public void setShowSeparators(boolean showSeparators) {
        this.showSeparators = showSeparators;
    }

    public boolean isShowSeparators() {
        return showSeparators;
    }


    public static class StateData {
        private String m_name;
        private String m_abbr;
        private double m_unemployment;
        private double m_dropout;
        private double m_sodapop;
        private double m_car;
        private HashMap<String, String> m_stringValues =
            new HashMap<String, String>();
        private HashMap<String, Double> m_doubleValues =
            new HashMap<String, Double>();

        StateData(String name, String abbr, double unemployment,
                  double dropout, double sodapop, double car) {
            m_name = name;
            m_abbr = abbr;
            m_unemployment = unemployment;
            m_dropout = dropout;
            m_sodapop = sodapop;
            m_car = car;
            initHash();
        }

        public void setName(String name) {
            this.m_name = name;
        }

        public String getName() {
            return m_name;
        }

        public String getStringValue(String key) {
            return m_stringValues.get(key);
        }

        public double getDoubleValue(String key) {
            return m_doubleValues.get(key).doubleValue();
        }

        //      public Color getDefaultColorObj() {
        //        return MapUtil.convertToColor("#ff00ff");
        //      }


        private void initHash() {
            // unemployment rates
            if (m_unemployment < 0.33) {
                m_stringValues.put(UNEMPLOYMENT, LOW);
            } else if (m_unemployment < 0.67) {
                m_stringValues.put(UNEMPLOYMENT, MEDIUM);
            } else {
                m_stringValues.put(UNEMPLOYMENT, HIGH);
            }
            m_doubleValues.put(UNEMPLOYMENT, m_unemployment);

            // high school dropout rates
            if (m_dropout < 0.33) {
                m_stringValues.put(DROPOUT, LOW);
            } else if (m_dropout < 0.67) {
                m_stringValues.put(DROPOUT, MEDIUM);
            } else {
                m_stringValues.put(DROPOUT, HIGH);
            }
            m_doubleValues.put(DROPOUT, m_dropout);

            // names for carbonated beverages
            m_stringValues.put(SODAPOP, convertSodaPopName(m_sodapop));
            m_doubleValues.put(SODAPOP, m_sodapop);

            // popular makes of cars
            if (m_car < 0.2) {
                m_stringValues.put(CAR_MAKE, TOYOTA);
            } else if (m_car < 0.4) {
                m_stringValues.put(CAR_MAKE, HONDA);
            } else if (m_car < 0.6) {
                m_stringValues.put(CAR_MAKE, FORD);
            } else if (m_car < 0.8) {
                m_stringValues.put(CAR_MAKE, BMW);
            } else {
                m_stringValues.put(CAR_MAKE, VOLKSWAGEN);
            }
            m_doubleValues.put(CAR_MAKE, m_car);
        }

        public void setAbbr(String abbr) {
            this.m_abbr = abbr;
        }

        public String getAbbr() {
            return m_abbr;
        }
    }

    public static class CountyData {
        private String m_countyId;
        private double m_sodapop;
        private HashMap<String, String> m_values =
            new HashMap<String, String>();

        CountyData(String countyId, double sodapop) {
            m_countyId = countyId;
            m_sodapop = sodapop;

            m_values.put(SODAPOP, convertSodaPopName(m_sodapop));
        }


        public void setCountyId(String countyId) {
            this.m_countyId = countyId;
        }

        public String getCountyId() {
            return m_countyId;
        }

        public String getSodapopType() {
            return m_values.get(SODAPOP);
        }
    }
}
