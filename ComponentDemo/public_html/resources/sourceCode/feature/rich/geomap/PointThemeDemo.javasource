/* Copyright (c) 2007, 2012, Oracle and/or its affiliates. 
All rights reserved. */
package oracle.adfdemo.view.feature.rich.geomap;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.component.geoMap.DataContent;
import oracle.adf.view.faces.bi.component.geoMap.MapInfoCallbackObject;
import oracle.adf.view.faces.bi.component.geoMap.PointContent;
import oracle.adf.view.faces.bi.component.geoMap.UIGeoMap;
import oracle.adf.view.faces.bi.model.GeoMapDataModel;
import oracle.adf.view.faces.bi.model.GeoObject;
import oracle.adf.view.faces.bi.model.GeoRowObject;
import oracle.adf.view.rich.component.rich.RichPoll;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSlider;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectItem;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOrderShuttle;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DropEvent;
import oracle.adf.view.rich.model.NumberRange;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.PollEvent;

public class PointThemeDemo {
    
    // Arrays to keep track of max and min values for the sliders 
    // used to filter the data
    private double m_max_vals[];
    private double m_min_vals[];
    
    // Array used to keep track of which filters are enabled
    private boolean m_filters[];
    
    // Array used to keep track of the ranges indicated in the quick filters
    private NumberRange [] m_dataRanges;

    private boolean m_pageGoingUp;
    private boolean m_isPageByYear;
    private boolean m_enableAnimation;
    private boolean m_isSalesEnabled;
    private boolean m_isProfitEnabled;
    private boolean m_isYearEnabled;  
    private int m_pollInterval;
    private Number m_yearPage;
    private Number m_sizeMultiplier;
    private String m_pageDropDownValue;
    private String m_sizeFactor;
    private String m_colorFactor;
    private String m_pageFactor;
    private String m_borderColorFactor;
    private String m_opacityFactor;

    private UIGeoMap m_map;
    private RichInputNumberSlider m_yearSlider;
    private RichPoll m_pagingPoll;
    private GeoMapDataModel m_mapModel;
    private RichOutputText m_fillColorDropTarget;
    private RichOutputText m_sizeFactorDropTarget;
    private RichOutputText m_pageEdgeDropTarget;
    private RichOutputText m_salesDragSource;
    private RichOutputText m_profitDragSource;
    private RichOutputText m_yearDragSource;
    private RichSelectOrderShuttle m_columnSelectorShuttle;
    private RichSelectOneChoice m_pageDropDown;
    private RichSelectBooleanCheckbox m_animationCheckBox;
    private RichCommandButton m_prevPageButton;
    private RichCommandButton m_nextPageButton;
    private RichPanelBox m_selectedColumnsPanelBox;

    // Constants
    private int NUM_FILTERS = 2;
    private int SALES_KEY = 0;
    private int PROFIT_KEY = 1;
    private int SALES_INDEX_OFFSET = 0;
    private int PROFIT_INDEX_OFFSET = 3;
    private int NUM_YEARS = 3;
    private int START_YEAR = 2006;
    private String NONE = "None";
    private String SALES = "Sales";
    private String PROFIT = "Profit";
    private String AVERAGE = "Average";


    public PointThemeDemo() {

        setSizeFactor(NONE);
        setColorFactor(NONE);
        setBorderColorFactor(NONE);
        setOpacityFactor(NONE);
        setIsPageByYear(false);
        setYearPage(new Integer(2006));
        setSizeMultiplier(new Double(1.0));
        setPollInterval(-1);
        setPageDropDownValue(AVERAGE);
        this.m_enableAnimation = false;        
        this.m_pageFactor = "";
        this.m_isSalesEnabled = false;
        this.m_isProfitEnabled = false;
        this.m_isYearEnabled = false;
        this.m_pageGoingUp = true;
        this.m_max_vals = new double[] {40000.0,200.0};
        this.m_min_vals = new double[] {10000.0,25.0};
        this.m_filters = new boolean[] {false, false};
        this.m_dataRanges = new NumberRange[] {new NumberRange(m_min_vals[0], m_max_vals[0]),
            new NumberRange(m_min_vals[1], m_max_vals[1])};
    }
    
    // Set up the data points to show on the GeoMap
    public GeoMapDataModel getPointModel() {
        // each row has its own ID and locationLabel
        String [] rowIDs = new String[] {"Boston", "Dallas", "Denver", "Los Angeles",
            "New York", "Seattle", "Calgary", "Chicago", "Edmonton", "Mexico City"};
        String [] locationLabels = new String[] {"Boston", "Dallas", "Denver", "Los Angeles",
            "New York", "Seattle", "Calgary", "Chicago", "Edmonton", "Mexico City"};
        double [] latitudes = new double[] {42.35, 32.80, 39.75, 34.05,
            40.45, 47.6, 51.03, 41.84, 53.57, 19.05};
        double [] longitudes = new double[] {-71.07, -96.79,-104.91, -118.24,
            -74, -122.32, -114.05, -87.68, -113.52, -99.37};
        
        // all rows share the same itemIDs, itemLabels
        String [] itemIDs = new String[] {"Sales 2006", "Sales 2007", "Sales 2008",
            "Profits 2006", "Profits 2007", "Profits 2008",
            "Cost 2006", "Cost 2007", "Cost 2008",
            "Qty Sold 2006", "Qty Sold 2007", "Qty Sold 2008"};
        String [] itemLabels = new String[] {"Sales 2006", "Sales 2007", "Sales 2008",
            "Profits 2006", "Profits 2007", "Profits 2008",
            "Cost 2006", "Cost 2007", "Cost 2008",
            "Qty Sold 2006", "Qty Sold 2007", "Qty Sold 2008"};

        // each row has it's own array of values
        Double value1[] = new Double[] {10000.0, 14000.0, 20000.0, 
                                        100.0, 125.0, 175.0};
        Double value2[] = new Double[] {12000.0, 10000.0, 14000.0,
                                        80.0, 50.0, 30.0};
        Double value3[] = new Double[] {14000.0, 20000.0, 16000.0,
                                        25.0, 50.0, 75.0};
        Double value4[] = new Double[] {16000.0, 20000.0, 30000.0,
                                        50.0, 100.0, 175.0};
        Double value5[] = new Double[] {25000.0, 20000.0, 10000.0,
                                        150.0, 100.0, 45.0};
        Double value6[] = new Double[] {40000.0, 30000.0, 35000.0,
                                        125.0, 75.0, 90.0};
        Double value7[] = new Double[] {30000.0, 22000.0, 25000.0,
                                        200.0, 150.0, 160.0};
        Double value8[] = new Double[] {28000.0, 15000.0, 20000.0,
                                        175.0, 100.0, 125.0};
        Double value9[] = new Double[] {35000.0, 37000.0, 40000.0,
                                        140.0, 165.0, 180.0};
        Double value10[] = new Double[] {24000.0, 15000.0, 12000.0,
                                         75.0, 60.0, 35.0};
                
        // move the array data in each row into a 2-D array
        Double[][] values = new Double[][] {value1, value2, value3, value4, value5,
                                            value6, value7, value8, value9, value10};
        
        ArrayList data = new ArrayList();
        for(int i=0; i<rowIDs.length;i++) {
            GeoObject key = new GeoObject(rowIDs[i], locationLabels[i], 
                                          longitudes[i], latitudes[i]);
            // since only the first of the formatted values array is used, the 
            // fifth argument we pass to the rowObj constructor is just the value
            GeoRowObject rowObj = new GeoRowObject(key, itemIDs, itemLabels,
                      values[i], new String[] {values[i][0].toString()});
            data.add(rowObj);
        }

        GeoMapDataModel model = new GeoMapDataModel();        
        model.setWrappedData(data);
        
        m_mapModel = model;
        return model;        
    }

    // configure the points based on the different settings in the conditional
    // formatting, filtering, and paging components
    public Object processPointInfo(DataContent dataContent) {
        String pointHTML = null;

        double salesFigure = 0; 
        double profitFigure = 0; 
        
        // determine what page of data to display 
        // i.e., data from a given year, or an average across all years
        if(getEnableAnimation()) {
            int year = getYearPage().intValue();
            salesFigure = dataContent.getValues()[(year-START_YEAR)+SALES_INDEX_OFFSET];
            profitFigure = dataContent.getValues()[(year-START_YEAR)+PROFIT_INDEX_OFFSET];
        } else if(!getIsPageByYear() || (getPageDropDownValue().equals(AVERAGE))) {            
            // average across all years when there is no specific year selected
            for(int i=0;i<NUM_YEARS;i++) {
                salesFigure += dataContent.getValues()[SALES_INDEX_OFFSET+i] ;
                profitFigure += dataContent.getValues()[PROFIT_INDEX_OFFSET+i] ;
            }
            salesFigure = salesFigure/NUM_YEARS;
            profitFigure = profitFigure/NUM_YEARS;            
        } else if(getIsPageByYear()) {
            int year = Integer.valueOf(getPageDropDownValue()).intValue();
            salesFigure = dataContent.getValues()[(year-START_YEAR)+SALES_INDEX_OFFSET];
            profitFigure = dataContent.getValues()[(year-START_YEAR)+PROFIT_INDEX_OFFSET];        
        } 
        
        String sizeFactor = getSizeFactor();
        double size = 5;
        if(sizeFactor.equals(SALES)) {
            size = ((salesFigure - m_min_vals[SALES_KEY])/(m_max_vals[SALES_KEY]-m_min_vals[SALES_KEY]) + 1)*10;
        } else if (sizeFactor.equals(PROFIT)) {
            size = ((profitFigure - m_min_vals[PROFIT_KEY])/(m_max_vals[PROFIT_KEY]-m_min_vals[PROFIT_KEY]) + 1)*10;
        }
        size = size * getSizeMultiplier().doubleValue();
                
        double opacity = 0.0;
        if(getOpacityFactor().equals(SALES)) {
            opacity = ((salesFigure - m_min_vals[SALES_KEY])/(m_max_vals[SALES_KEY]-m_min_vals[SALES_KEY]));
        } else if (getOpacityFactor().equals(PROFIT)) {
            opacity = ((profitFigure - m_min_vals[PROFIT_KEY])/(m_max_vals[PROFIT_KEY]-m_min_vals[PROFIT_KEY]));
        } else if (getOpacityFactor().equals(NONE)){
            opacity = 1;
        }
        
        // limit how opaque a point can be so that it doesn't disappear altogether
        if(opacity<=0.1){opacity=0.1;}
                
        String color = "000000";
        if(getColorFactor().equals(SALES)) {
            color = determineRGBColor(salesFigure, SALES_KEY, 0);
        } else if(getColorFactor().equals(PROFIT)) {
            color = determineRGBColor(profitFigure, PROFIT_KEY, 0);
        }
        
        String borderColor = null;
        String bcFactor = getBorderColorFactor();
        if(bcFactor.equals(SALES)) {
            borderColor = determineRGBColor(salesFigure, SALES_KEY, 0);
        } else if(bcFactor.equals(PROFIT)) {
            borderColor = determineRGBColor(profitFigure, PROFIT_KEY, 0);
        } else if(bcFactor.equals(NONE)) {
            borderColor = color;
        }

        pointHTML = "<div style=\"border: 5px #"+ borderColor;
        pointHTML += " solid; background-color: #"+color+"; width : "+size;
        pointHTML += "px; height : "+size+"px;";
        pointHTML += "opacity:" + opacity + ";filter:alpha(opacity=" + 100*opacity + ")\"> </div>";

        double maxThresh = 0.0;
        double minThresh = 0.0;
        double value = 0.0;

        // if the data value is outside the range specified by any 
        // of the filters then we don't produce any html for that point
        // essentially not rendering that point
        for(int i=0;i<NUM_FILTERS;i++) {
            maxThresh = m_dataRanges[i].getMaximum().doubleValue();
            minThresh = m_dataRanges[i].getMinimum().doubleValue();
            if(i==SALES_KEY) {
                value = salesFigure;
            } else if(i==PROFIT_KEY) {
                value = profitFigure;
            }
            if(m_filters[i] && ((value>maxThresh) || (value<minThresh)) ) {
                pointHTML = null;
            }
        }

        String selectHTML = null; 
        String hoverHTML = null;
        PointContent pContent = new PointContent(pointHTML, selectHTML, hoverHTML);
        return pContent;
    }

    // pick a color from a color spectrum based on a point's value, relative
    // to the value of the other points
    private String determineRGBColor (double value, int key, int colorScheme) {
        
        int r = 0;
        int g = 0;
        int b = 0;
        int c = 0;
        String color = "";
        String s1 = null;
        String s2 = null;
        String s3 = null;
        
        double range = (m_max_vals[key]-m_min_vals[key]);

        // blue to red color theme        
        if(colorScheme == 0) {
            r = (int) ( (((value - m_min_vals[key])/ range)*(208-19))+19);
            g = (int) ( (((value - m_min_vals[key])/ range)*(21-88))+88);
            b = (int) ( (((value - m_min_vals[key])/ range)*(21-176))+176);
        } else { // yellow to red color theme
            r = 243;
            g = (int) ( (((value - m_min_vals[key])/ range)*(43-187))+187);
            b = (int) ( (((value - m_min_vals[key])/ range)*(43-37))+37);
        }
        s1 = Integer.toHexString(r);            
        s2 = Integer.toHexString(g);
        s3 = Integer.toHexString(b);
        if(r<=15) { s1="0"+s1; }
        if(g<=15) { s2="0"+s2; }
        if(b<=15) { s3="0"+s3; }
        color = s1+s2+s3;
        return color;  
/*        
            // green to red color scheme
            //r = (int) ( (((val1 - m_min_vals[SALES_KEY])/ range)*(224-8))+8);
            //g = (int) ( (((val1 - m_min_vals[SALES_KEY])/ range)*(12-160))+160);
            //b = (int) ( (((val1 - m_min_vals[SALES_KEY])/ range)*(12-23))+23);
*/
    }

    public void setSizeFactor(String sizeFactor) {
        this.m_sizeFactor = sizeFactor;
    }

    public String getSizeFactor() {
        return m_sizeFactor;
    }

    public void setColorFactor(String colorFactor) {
        this.m_colorFactor = colorFactor;
    }

    public String getColorFactor() {
        return m_colorFactor;
    }
    
    public void setBorderColorFactor(String borderColorFactor) {
        this.m_borderColorFactor = borderColorFactor;
    }
    
    public String getBorderColorFactor() {
        return m_borderColorFactor;
    }
    
    public void setOpacityFactor(String opacityFactor) {
        this.m_opacityFactor = opacityFactor;
    }
    
    public String getOpacityFactor() {
        return m_opacityFactor;
    }
    
    public NumberRange getSalesRange() {
        return m_dataRanges[SALES_KEY];
    }

    public NumberRange getProfitRange() {
        return m_dataRanges[PROFIT_KEY];
    }

    public void setSalesRange(NumberRange value) {
        m_dataRanges[SALES_KEY] = value;
    }
    
    public void setProfitRange(NumberRange value) {
        m_dataRanges[PROFIT_KEY] = value;
    }

    public void setSalesFilter(boolean salesFilter) {
        m_filters[SALES_KEY] = salesFilter;
    }

    public boolean getSalesFilter() {
        return m_filters[SALES_KEY];
    }

    public void setProfitFilter(boolean profitFilter) {
        m_filters[PROFIT_KEY] = profitFilter;
    }

    public boolean getProfitFilter() {
        return m_filters[PROFIT_KEY];
    }
    
    public double getDataMaximum_0() {
        return m_max_vals[0];
    }
    
    public double getDataMinimum_0() {
        return m_min_vals[0];
    }
    
    public double getDataMaximum_1() {
        return m_max_vals[1];
    }
    
    public double getDataMinimum_1() {
        return m_min_vals[1];
    }
    
    public void setMap(UIGeoMap map) {
        ElocationBean.setGeoMapConnections(map);
        
        this.m_map = map;
    }
    
    public UIGeoMap getMap() {
        return m_map;
    }

    public void setIsPageByYear(boolean pageByYear) {
        this.m_isPageByYear = pageByYear;
    }

    public boolean getIsPageByYear() {
        return m_isPageByYear;
    }
    
    public void setYearPage(Number year) {
        this.m_yearPage = year;
    }
    
    public Number getYearPage() {
        return m_yearPage;
    }

    // when we receive a poll event, cycle to the next page
    public void processPoll(PollEvent pollEvent) {
        
        int year = getYearPage().intValue();
        //System.out.println(year + " - " + goingUp);
        if ((year < 2008) && m_pageGoingUp) {
            setYearPage(new Integer(++year));
        } else if((year > 2006) && !m_pageGoingUp) {
            setYearPage(new Integer(--year));
        } else if(year==2008){
            setYearPage(new Integer(--year));
            m_pageGoingUp = false;
        } else if(year == 2006) {
            setYearPage(new Integer(++year));
            m_pageGoingUp = true;
        }

        RequestContext rc = RequestContext.getCurrentInstance();
        rc.addPartialTarget(m_yearSlider); 
        rc.addPartialTarget(m_map);        
    }
    
    public void setYearSlider(RichInputNumberSlider yearSlider) {
        this.m_yearSlider = yearSlider;
    }

    public RichInputNumberSlider getYearSlider() {
        return m_yearSlider;
    }
    
    public int getPollInterval() {
        return m_pollInterval;
    }
    
    public void setPollInterval(int interval) {
        this.m_pollInterval = interval;
    }

    public void setPagingPoll(RichPoll pagingPoll) {
        this.m_pagingPoll = pagingPoll;
    }

    public RichPoll getPagingPoll() {
        return m_pagingPoll;
    }
    
    public boolean getEnableAnimation() {
        return m_enableAnimation;
    }
    
//    public void setEnableAnimation(boolean enable) {
//        this.m_enableAnimation = enable;
//        if(this.m_enableAnimation) {
//            setPollInterval(1000);
//        } else {
//            setPollInterval(-1);
//        }
//
//        RequestContext.getCurrentInstance().addPartialTarget(getPageDropDown());        
//        RequestContext.getCurrentInstance().addPartialTarget(getPrevPageButton());                
//        RequestContext.getCurrentInstance().addPartialTarget(getNextPageButton());    
//        RequestContext.getCurrentInstance().addPartialTarget(getPagingPoll());
//        RequestContext.getCurrentInstance().addPartialTarget(getYearSlider());
//        //        RequestContext.getCurrentInstance().addPartialTarget(getMap());
//    }
    
    public void enableAnimationListener(ValueChangeEvent valueChangeEvent) {
        boolean enable = (Boolean)valueChangeEvent.getNewValue();
        this.m_enableAnimation = enable;
        if(this.m_enableAnimation) {
            setPollInterval(1000);
        } else {
            setPollInterval(-1);
        }

        RequestContext rc = RequestContext.getCurrentInstance();
        rc.addPartialTarget(getPageDropDown());        
        rc.addPartialTarget(getPrevPageButton());                
        rc.addPartialTarget(getNextPageButton());    
        rc.addPartialTarget(getPagingPoll());
        rc.addPartialTarget(getYearSlider());
        //        RequestContext.getCurrentInstance().addPartialTarget(getMap());
    }

    public String processInfoWindow(MapInfoCallbackObject mapInfoCallbackObject) {

        String rowID = mapInfoCallbackObject.getRowId();
        GeoRowObject row = findRowByID(rowID);
        Double[] data = row.getValues();

        // put the sales info into a table
        String HTML = "<table border='1'>";
        HTML += "<tr> <td> Year </td> <td> Sales </td> </tr>";
        
        for(int i=0; i<NUM_YEARS; i++) {
            double val1 = data[SALES_INDEX_OFFSET+i].doubleValue();
            String color = determineRGBColor(val1, SALES_KEY, 0);

            HTML += "<tr> <td>" + (START_YEAR+i) + "</td> <td bgcolor=#" + 
                    color + ">" + data[SALES_INDEX_OFFSET+i] + "</td></tr>";
        }
        HTML += "</table>";
        return HTML;

    }
    
    private GeoRowObject findRowByID(String rowID) {
        ArrayList <GeoRowObject> list = m_mapModel.getArrayList();
        for(GeoRowObject row : list) { // for each row in the list...
            if(row.getKey().getRowId().equals(rowID)) {
                return row;
            }
        }
        return null;
    }
        
    // method used to reset all columns selected by column selector        
    private void clearEnabledDimensionsAndMeasures() {
        setIsSalesEnabled(false);
        setIsProfitEnabled(false);
        setIsYearEnabled(false);
    }
    
    public void setPageFactor(String s) {
        this.m_pageFactor = s;
        if(this.m_pageFactor.equals("Year")) {
            m_isPageByYear = true;
        } else {
            m_isPageByYear = false;
        }

        RequestContext rc =RequestContext.getCurrentInstance();
        rc.addPartialTarget(getYearSlider());
        rc.addPartialTarget(getPageDropDown());
        rc.addPartialTarget(getAnimationCheckBox());            
    }
    
    public String getPageFactor() {
        return this.m_pageFactor;
    }

    public void setFillColorDropTarget(RichOutputText fillColorDropTarget) {
        this.m_fillColorDropTarget = fillColorDropTarget;
    }

    public RichOutputText getFillColorDropTarget() {
        return m_fillColorDropTarget;
    }

    public void setSizeFactorDropTarget(RichOutputText sizeFactorDropTarget) {
        this.m_sizeFactorDropTarget = sizeFactorDropTarget;
    }

    public RichOutputText getSizeFactorDropTarget() {
        return m_sizeFactorDropTarget;
    }

    public void setPageEdgeDropTarget(RichOutputText pageEdgeDropTarget) {
        this.m_pageEdgeDropTarget = pageEdgeDropTarget;
    }

    public RichOutputText getPageEdgeDropTarget() {
        return m_pageEdgeDropTarget;
    }
    
    public void setIsSalesEnabled(boolean b) {
        this.m_isSalesEnabled = b;
        RequestContext.getCurrentInstance().addPartialTarget(getSalesDragSource());
    }
    
    public boolean getIsSalesEnabled() {
        return this.m_isSalesEnabled;
    }
    
    public void setIsProfitEnabled(boolean b) {
        this.m_isProfitEnabled = b;
        RequestContext.getCurrentInstance().addPartialTarget(getProfitDragSource());
    }
    
    public boolean getIsProfitEnabled() {
        return this.m_isProfitEnabled;
    }

    public void setIsYearEnabled(boolean b) {
        this.m_isYearEnabled = b;
        RequestContext.getCurrentInstance().addPartialTarget(getYearDragSource());
    }
    
    public boolean getIsYearEnabled() {
        return this.m_isYearEnabled;
    }

    public void setSalesDragSource(RichOutputText salesDragSource) {
        this.m_salesDragSource = salesDragSource;
    }

    public RichOutputText getSalesDragSource() {
        return m_salesDragSource;
    }

    public void setProfitDragSource(RichOutputText profitDragSource) {
        this.m_profitDragSource = profitDragSource;
    }

    public RichOutputText getProfitDragSource() {
        return m_profitDragSource;
    }

    public void setYearDragSource(RichOutputText yearDragSource) {
        this.m_yearDragSource = yearDragSource;
    }

    public RichOutputText getYearDragSource() {
        return m_yearDragSource;
    }

    public void setColumnSelectorShuttle(RichSelectOrderShuttle columnSelectorShuttle) {
        this.m_columnSelectorShuttle = columnSelectorShuttle;
    }

    public RichSelectOrderShuttle getColumnSelectorShuttle() {
        return m_columnSelectorShuttle;
    }

    public void setPageDropDown(RichSelectOneChoice pageDropDown) {
        this.m_pageDropDown = pageDropDown;
    }

    public RichSelectOneChoice getPageDropDown() {
        return m_pageDropDown;
    }
    
    public void setPageDropDownValue(String s) {
        this.m_pageDropDownValue = s;
    }
    
    public String getPageDropDownValue() {
        return this.m_pageDropDownValue;
    }

    public void handlePreviousPageClick(ActionEvent actionEvent) {
        // Get current value in drop down, find its index in list, 
        // get previous index, set drop down value to value in previous index
        // refresh drop down and map
        RichSelectOneChoice pageDropDown = getPageDropDown();
        List<UIComponent> l = pageDropDown.getChildren();
        RichSelectItem r;
        int i;
        for(i=0; i<l.size();i++) {
            r = (RichSelectItem) l.get(i);
            if(r.getValue().equals(getPageDropDownValue())) {
                break;
            }
        }
        if(i==0) {
            i=l.size();
        }
        r = (RichSelectItem) l.get(i-1);
        setPageDropDownValue((String)r.getValue());
        
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.addPartialTarget(getPageDropDown());        
        rc.addPartialTarget(getMap());        
    }

    public void handleNextPageClick(ActionEvent actionEvent) {
        // Get current value in drop down, find its index in list, 
        // get next index, set drop down value to value in next index
        // refresh drop down and map
        RichSelectOneChoice pageDropDown = getPageDropDown();
        List<UIComponent> l = pageDropDown.getChildren();
        RichSelectItem r;
        int i;
        for(i=0; i<l.size();i++) {
            r = (RichSelectItem) l.get(i);
            if(r.getValue().equals(getPageDropDownValue())) {
                break;
            }
        }
        if(i==l.size()-1) {
            i=-1;
        }
        r = (RichSelectItem) l.get(i+1);
        setPageDropDownValue((String)r.getValue());
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.addPartialTarget(getPageDropDown());        
        rc.addPartialTarget(getMap());          
    }
    
    public void setSizeMultiplier(Number mult) {
        this.m_sizeMultiplier = mult;
    }
    
    public Number getSizeMultiplier() {
        return m_sizeMultiplier;
    }

    public DnDAction fillColorDropListener(DropEvent dropEvent) {
        RichOutputText r = (RichOutputText) dropEvent.getDragComponent();
        if(!r.getValue().equals("Year")) {
            setColorFactor((String)r.getValue());
            getFillColorDropTarget().setValue(r.getValue());
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.addPartialTarget(getFillColorDropTarget());        
            rc.addPartialTarget(getMap());          
        }
        
        return DnDAction.NONE;
    }

    public DnDAction sizeFactorDropListener(DropEvent dropEvent) {
        RichOutputText r = (RichOutputText) dropEvent.getDragComponent();
        if(!r.getValue().equals("Year")) {
            setSizeFactor((String)r.getValue());
            getSizeFactorDropTarget().setValue(r.getValue());
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.addPartialTarget(getSizeFactorDropTarget());        
            rc.addPartialTarget(getMap());          
        }
        
        return DnDAction.NONE;
    }    

    public DnDAction pageEdgeDropListener(DropEvent dropEvent) {
        RichOutputText r = (RichOutputText) dropEvent.getDragComponent();
        if(r.getValue().equals("Year") || r.getValue().equals(NONE)) {
            setPageFactor((String)r.getValue());
            getPageEdgeDropTarget().setValue(r.getValue());
            RequestContext rc = RequestContext.getCurrentInstance();
            if(r.getValue().equals(NONE)) {
                setPageDropDownValue(AVERAGE);
                rc.addPartialTarget(getPageDropDown());
            }
            rc.addPartialTarget(getPageEdgeDropTarget());        
            rc.addPartialTarget(getPrevPageButton());                
            rc.addPartialTarget(getNextPageButton());                        
//            RequestContext.getCurrentInstance().addPartialTarget(getMap());          
        }
        
        return DnDAction.NONE;
    }

    public void setAnimationCheckBox(RichSelectBooleanCheckbox animationCheckBox) {
        this.m_animationCheckBox = animationCheckBox;
    }

    public RichSelectBooleanCheckbox getAnimationCheckBox() {
        return m_animationCheckBox;
    }
    
    public RichCommandButton getPrevPageButton() {
        return this.m_prevPageButton;
    }
    
    public void setPrevPageButton(RichCommandButton b) {
        this.m_prevPageButton = b;
    }

    public RichCommandButton getNextPageButton() {
        return this.m_nextPageButton;
    }
    
    public void setNextPageButton(RichCommandButton b) {
        this.m_nextPageButton = b;
    }    
    
    private void resetPointTheme() {
        setColorFactor("<Drag Column here>");
        setSizeFactor("<Drag Column here>");
        setPageFactor("<Drag Column here>");
        getFillColorDropTarget().setValue("<Drag Column here>");
        getSizeFactorDropTarget().setValue("<Drag Column here>");
        getPageEdgeDropTarget().setValue("<Drag Column here>");
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.addPartialTarget(getMap());          
        rc.addPartialTarget(getFillColorDropTarget());                  
        rc.addPartialTarget(getSizeFactorDropTarget());        
        rc.addPartialTarget(getPageEdgeDropTarget());                

        rc.addPartialTarget(getPrevPageButton());                
        rc.addPartialTarget(getNextPageButton());            
        rc.addPartialTarget(getPageDropDown());            
        rc.addPartialTarget(getAnimationCheckBox());                    
    }

    public void processColumnSelectorPopup(DialogEvent dialogEvent) {
        clearEnabledDimensionsAndMeasures();
        resetPointTheme();
        List<String> columns = (List) getColumnSelectorShuttle().getValue();

        RequestContext.getCurrentInstance().addPartialTarget(getSelectedColumnsPanelBox());                    
        if(columns == null) {
            return;
        }

        if(columns.contains(SALES)){
            setIsSalesEnabled(true);
        }
        
        if(columns.contains(PROFIT)) {
            setIsProfitEnabled(true);
        }
        
        if(columns.contains("Year")) {
            setIsYearEnabled(true);
        }                      
        
    }

    public void setSelectedColumnsPanelBox(RichPanelBox selectedColumnsPanelBox) {
        this.m_selectedColumnsPanelBox = selectedColumnsPanelBox;
    }

    public RichPanelBox getSelectedColumnsPanelBox() {
        return m_selectedColumnsPanelBox;
    }
}
