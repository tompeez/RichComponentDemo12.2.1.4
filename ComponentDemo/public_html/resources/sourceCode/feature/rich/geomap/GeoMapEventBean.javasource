/* Copyright (c) 2007, 2009, Oracle and/or its affiliates. 
All rights reserved. */
package oracle.adfdemo.view.feature.rich.geomap;

import java.text.NumberFormat;

import java.util.Iterator;

import java.util.LinkedList;
import java.util.List;

import oracle.adf.view.faces.bi.component.geoMap.DataContent;
import oracle.adf.view.faces.bi.event.MapClickActionEvent;
import oracle.adf.view.faces.bi.event.MapSelectionEvent;

/*
 * Sample backing bean that helps demonstrate the use of the various event listener
 * attributes for the map and map themes.
 */
public class GeoMapEventBean{
    String m_address = null;
    String m_location = null;
    String m_value = null;
    List m_collection = null;

    public GeoMapEventBean() {
    }

    public String getAddress() {
        return m_address;
    }
    
    public String getLocation () {
        return m_location;
    }
    
    public String getValue () {
        return m_value;
    }
    
    /*
     * Sample of an event listener for theme click events. Information about the event
     * gets stored and can be used to customize theme popup dialogs. This method gets assigned 
     * to the clickListener attribute.
     * (See geoMapPopupDialog.jspx)
     */
    public void processClickAction(MapClickActionEvent event) {      
        m_address = "Address: " + event.getDataContent().getLocationName();
        NumberFormat numFormat = NumberFormat.getNumberInstance();
        numFormat.setMaximumFractionDigits(4);
        String strLocationX = numFormat.format(event.getLocationX());
        String strLocationY = numFormat.format(event.getLocationY());
        m_location = "Longitude: " + strLocationX + " Latitude: " + strLocationY;
        m_value = "Value: " + event.getDataContent().getValues()[0];
    }

    /*
     * Sample of an event listener for theme selection events. This method gets assigned 
     * to the selectionListener attribute.
     * (See geoMapSelectionEvent.jspx)
     */
    public void processSelection(MapSelectionEvent mapSelectionEvent) { 
        Iterator iter = mapSelectionEvent.getIterator();
        if (m_collection == null)
            m_collection = new LinkedList();
        m_collection.clear();
        while (iter.hasNext()) 
        {
            DataContent dt = (DataContent) iter.next();
            String locationName = dt.getLocationName();
            String themeName = dt.getThemeId();
            if (mapSelectionEvent.getSelectionMode().equals(MapSelectionEvent.SELECTIONMODE_SELECTED))
                m_collection.add(themeName + ": " + locationName);            
            else
                m_collection.remove(themeName + ": " + locationName);
        }
    }
    
    
    public List getData(){
        return m_collection;
    }
}
