/* Copyright (c) 2007, 2013, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.feature.rich.geomap;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.model.GeoMapDataModel;
import oracle.adf.view.faces.bi.model.GeoObject;
import oracle.adf.view.faces.bi.model.GeoRowObject;
import oracle.adf.view.faces.bi.model.GeocodedObject;


/*
 * A sample backing bean that sets up the data models for a color theme, bar/pie theme,
 * lat/long point theme, and geocoded address point theme.
 */
public class GeoMapBinding {
    
    private boolean colorThemeRendered = false;
    private boolean pieThemeRendered = false;
    private boolean pointThemeRendered = false;
    private String autoZoomThemeId = "mapColorTheme1";
    
    // Regions used in the color and bar/pie themes. Assumes use of the MAP_STATES_NAME theme name.
    protected String m_names[] = new String[]{
        "Alabama",
        "Arizona",
        "Arkansas",
        "California",
        "Colorado",
        "Connecticut",
        "Delaware",
        "Florida",
        "Georgia",
        "Idaho",
        "Illinois",
        "Indiana",
        "Iowa",
        "Kansas",
        "Kentucky",
        "Louisiana ",
        "Maine",
        "Maryland",
        "Massachusetts",
        "Michigan",
        "Minnesota",
        "Mississippi",
        "Missouri",
        "Montana",
        "Nebraska"
    };
    
    // lat/long values of points to be displayed in the point theme.
    protected double m_locations[][] = new double [][] {
        {-122.2, 37.5}, {-105.2, 40.0}, {-72.8, 41.7}, 
        {-81.3, 28.4}, {-84.4, 33.8}, {-87.6, 41.8}, 
        {-71.2, 42.5}, {-83.1, 42.6}, {-93.3, 45.0},
        {-71.5, 42.7}, {-74.6, 40.6}, {-74.0, 40.8},
        {-80.9, 35.2}, {-83.0, 39.9}, {-75.4, 39.8},
        {-95.4, 29.7}, {-76.2, 36.7}, {-122.2, 47.6}
    };
    
    // Addresses of points to be geocoded and displayed in the point theme
    protected String m_addresses[] = new String[]{
        "500 Oracle Parkway\nRedwood City, CA",
        "4001 Discovery Drive\nBoulder, CO",
        "10 Waterside Drive\nFarmington, CT",
        "5955 TG Lee Boulevard\nOrlando, FL",
        "3353 Peachtree Road NE\nAtlanta, GA",
        "233 South Wacker Drive\nChicago, IL",
        "10 Van de Graaff Drive\nBurlington, MA",
        "3290 West Big Beaver Road\nTroy, MI",
        "900 Second Avenue South\nMinneapolis, MN",
        "1 Oracle Drive\nNashua, NH",
        "400 Crossing Boulevard\nBridgewater, NJ",
        "520 Madison Avenue\nNew York, NY",
        "2550 West Tyvola Road\nCharlotte, NC",
        "500 South Front Street\nColumbus, OH",
        "2501 Seaport Drive\nChester, PA",
        "1200 Smith Street\nHouston, TX",
        "1403 Greenbrier Parkway\nChesapeake, VA",
        };
    
    public GeoMapBinding() {
        super();
    }
    
    /*
     * Function that populates a GeoMapDataModel given an ArrayList of GeoRowObjects
     */
    protected GeoMapDataModel createDataModel(ArrayList data){
        GeoMapDataModel model = new GeoMapDataModel();
        model.setWrappedData(data);
        return model;
    }
    
    
    /*
     * Creates the data model for a color theme. 
     * All the regions listed in m_names get colored on the map based on a randomly assigned data value.
     */
    public GeoMapDataModel getColorModel() {
        String itemIds[] = new String[]{"Item1"};
        String itemLabels[] = new String[]{"Sales"};
        
        ArrayList geoRowArray = new ArrayList();
        
        // Create a GeoRowObject for each colored region
        for(int i=0; i<m_names.length; i++){
            String rowId = "Row" + String.valueOf(i);
            String location = m_names[i];
            
            // Generate a random data value
            double value = 1000*Math.random();
            GeoObject key = new GeoObject(rowId, location, location);
            GeoRowObject geoRow = new GeoRowObject(key, itemIds, itemLabels, new Double[]{value}, null);
            geoRowArray.add(geoRow);
        }
    
        // Populate a GeoMapDataModel with the theme data
        return createDataModel(geoRowArray);
    }
    
    /*
     * Creates the data model for a bar or pie graph theme.
     * The graphs are displayed at each region listed in m_names.
     */
    public GeoMapDataModel getBarPieModel() {
        String itemIds[] = new String[]{"Item1", "Item2", "Item3"};
        String itemLabels[] = new String[]{"Furniture Sales", "Appliance Sales", "Clothing Sales"};

        ArrayList geoRowArray = new ArrayList();
        
        // Create a GeoRowObject for each graph
        for(int i=0; i<m_names.length; i++){
            String rowId = "Row" + String.valueOf(i);
            String location = m_names[i];
            
            // Generate random data values
            double value1 = 1000*Math.random();
            double value2 = 1000*Math.random();
            double value3 = 1000*Math.random();
            Double value[] = new Double[]{value1, value2, value3};
            
            GeoObject key = new GeoObject(rowId, location, location);
            GeoRowObject geoRow = new GeoRowObject(key, itemIds, itemLabels, value, null);
            geoRowArray.add(geoRow);
        }

        // Populate a GeoMapDataModel with the theme data
        return createDataModel(geoRowArray);
    }
    
    /*
     * Creates the data model for a point theme.
     * Each point is specified by lat/long values listed in m_locations and is assigned a random data value and category.
     */
    public GeoMapDataModel getPointModel() {
        String itemIds[] = new String[]{"Item1"};
        String itemLabels[] = new String[]{"Value"};
        String categories[] = new String[]{"Category1", "Category2", "Category3"};
        Random generator = new Random();
        
        ArrayList geoRowArray = new ArrayList();
        
        // Create a GeoRowObject for each point
        for(int i=0; i<m_locations.length; i++){
            String rowId = "Row" + String.valueOf(i);
            String loc = "Location #"  + String.valueOf(i);
            double latitude = m_locations[i][1];
            double longitude = m_locations[i][0];
            
            // Generate a random data value
            double value = 1000 * Math.random();
            GeoObject key = new GeoObject(rowId, loc, longitude, latitude);
            GeoRowObject geoRow = new GeoRowObject(key, itemIds, itemLabels, new Double[]{value}, null);
            geoRow.setCategory(categories[generator.nextInt(categories.length)]);
            geoRowArray.add(geoRow);
        }

        // Populate a GeoMapDataModel with the theme data
        return createDataModel(geoRowArray);
    }

    /*
     * Creates the data model for a geocoded point theme.
     * Each point is specified by an address listed in m_addresses and is assigned a random data value and category.
     */
    public GeoMapDataModel getHouseModel() {
        String countryCode = "USA";
        String itemIds[] = new String[]{"Item1"};
        String itemLabels[] = new String[]{"Value"};
        String categories[] = new String[]{"Category1", "Category2", "Category3"};
        Random generator = new Random();
        
        ArrayList geoRowArray = new ArrayList();
        
        // Create a GeoRowObject for each point
        for(int i=0; i<m_addresses.length; i++){
            String rowId = "Row" + String.valueOf(i);
            String loc = "Location #"  + String.valueOf(i);
            
            // Generate a random data value
            double value = 1000*Math.random();
            
            GeocodedObject geoAddress = new GeocodedObject(loc, rowId, countryCode, m_addresses[i]);
            GeoRowObject geoRow = new GeoRowObject(geoAddress, itemIds, itemLabels, new Double[]{value}, null);
            geoRow.setCategory(categories[generator.nextInt(categories.length)]);
            geoRowArray.add(geoRow);
        }

        // Populate a GeoMapDataModel with the theme data
        return createDataModel(geoRowArray);
    }

    public void checkboxListener(ValueChangeEvent event) {
        List<Object> newValue = (List<Object>) event.getNewValue();
        if(newValue==null)
        {
            pieThemeRendered = false;
            colorThemeRendered = false;
            pointThemeRendered = false;
        }
        else {
            if (newValue.contains("pie"))
                pieThemeRendered = true;
            else
                pieThemeRendered = false;
             if (newValue.contains("color"))
                colorThemeRendered = true;
            else
               colorThemeRendered = false;
             if (newValue.contains("point"))
                pointThemeRendered = true;
            else
                pointThemeRendered = false;
            
        }
    }

    public boolean getColorThemeRendered() {
        return colorThemeRendered;
    }
    
    public boolean getPieThemeRendered() {
        return pieThemeRendered;
    }
    
    public boolean getPointThemeRendered() {
        return pointThemeRendered;
    }
    
    public String getAutoZoomThemeId() {
        if (!colorThemeRendered) {
            if (pointThemeRendered)
                autoZoomThemeId = "pointTheme";
            else if (pieThemeRendered)
                autoZoomThemeId = "mapPieTheme1";
        } else
            autoZoomThemeId = "mapColorTheme1";
        
        return autoZoomThemeId;
    }
}