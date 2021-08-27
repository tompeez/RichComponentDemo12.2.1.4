/* Copyright (c) 2007, 2010, Oracle and/or its affiliates. 
All rights reserved. */
package oracle.adfdemo.view.feature.rich.geomap;

import java.util.ArrayList;

import javax.faces.component.UIComponent;

import oracle.adf.view.faces.bi.model.GeoMapDataModel;
import oracle.adf.view.faces.bi.model.GeoObject;
import oracle.adf.view.faces.bi.model.GeoRowObject;


/*
 * Supplementary backing bean to the main GeoMapBinding. Use this when creating new
 * sample pages to define data models that differ from the standard set of examples.
 */
public class ThemeDataResource extends GeoMapBinding {
    
    public GeoMapDataModel getCountryModel(){
        String countryNames[] = new String[]{
             "Australia",
             "Brazil",
             "Canada",
             "China",
             "Egypt",
             "France",
             "Germany",
             "Italy",
             "Japan",
             "Mexico",
             "Nigeria",
             "Russia",
             "Spain"
         };
        String itemIds[] = new String[]{"Item1"};
        String itemLabels[] = new String[]{"Sales"};
        
        ArrayList geoRowArray = new ArrayList();
        
        // Create a GeoRowObject for each colored region
        for(int i=0; i<countryNames.length; i++){
            String rowId = "Row" + String.valueOf(i);
            String location = countryNames[i];
            
            // Generate a random data value
            double value = 1000*Math.random();
            GeoObject key = new GeoObject(rowId, location, location);
            GeoRowObject geoRow = new GeoRowObject(key, itemIds, itemLabels, new Double[]{value}, null);
            geoRowArray.add(geoRow);
        }
        
        // Populate a GeoMapDataModel with the theme data
        return createDataModel(geoRowArray);
    }
    
    public GeoMapDataModel getContinentsModel(){
        String continentNames[] = new String[]{
            "Africa",
            "Antarctica",
            "Asia",
            "Australia",
            "Europe",
            "North America",
            "South America"
        };
        
        String itemIds[] = new String[]{"Item1"};
        String itemLabels[] = new String[]{"Sales"};
        
        ArrayList geoRowArray = new ArrayList();
        
        // Create a GeoRowObject for each colored region
        for(int i=0; i<continentNames.length; i++){
            String rowId = "Row" + String.valueOf(i);
            String location = continentNames[i];
            
            // Generate a random data value
            double value = 1000*Math.random();
            GeoObject key = new GeoObject(rowId, location, location);
            GeoRowObject geoRow = new GeoRowObject(key, itemIds, itemLabels, new Double[]{value}, null);
            geoRowArray.add(geoRow);
        }
        
        // Populate a GeoMapDataModel with the theme data
        return createDataModel(geoRowArray);
    }
}
