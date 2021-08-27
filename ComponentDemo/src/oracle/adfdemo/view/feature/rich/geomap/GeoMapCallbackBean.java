/* Copyright (c) 2007, 2012, Oracle and/or its affiliates. 
All rights reserved. */
package oracle.adfdemo.view.feature.rich.geomap;

import java.text.NumberFormat;

import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.geoMap.DataContent;
import oracle.adf.view.faces.bi.component.geoMap.LegendContent;
import oracle.adf.view.faces.bi.component.geoMap.LegendItem;
import oracle.adf.view.faces.bi.component.geoMap.MapInfoCallbackObject;
import oracle.adf.view.faces.bi.component.geoMap.PointContent;

/*
 * Sample backing bean that helps demonstrate the use of the various callback attributes
 * of the map themes.
 */
public class GeoMapCallbackBean {
    
    /*
     * Sample of a callback for overriding the default text of a theme's InfoWindow. This
     * method gets assigned to the infoWindowCallback attribute. 
     * (See geoMapCustomInfoWindow.jspx)
     */
    public String getInfoWindowText(MapInfoCallbackObject callbackObj) {
        String rowId = callbackObj.getRowId();
        String themeId = callbackObj.getThemeId();

        String outputHTML = "You selected <i>" + rowId + " of " + themeId + "</i>";
        return outputHTML;
    }
    
    /*
     * Sample of a callback that specifies custom HTML to show at each point in a point theme.
     * This method gets assigned to the customPointCallback attribute.
     * (See geoMapCustomPointCallback.jspx)
     */
    public PointContent processPointInfo(DataContent dataContent) {
       NumberFormat numFormat = NumberFormat.getNumberInstance();
       numFormat.setMaximumFractionDigits(3);
        
       double value = dataContent.getValues()[0];
       String pointHTML = "<div style=\"background-color:yellow; border:#000000 1px solid;\">" + numFormat.format(value) + "</div>";
       String selectHTML = "<div style=\"background-color:white; border:#000000 1px solid;\">" + numFormat.format(value) + "</div>"; 

       PointContent pContent = new PointContent(pointHTML, selectHTML, null);

       return pContent;
    }
    
    /*
     * Sample of a callback for overriding the legend display if a customPointCallback 
     * is used for the point theme. This method gets assigned to the customLegendCallback attribute.
     * (See geoMapCustomPointCallback.jspx)
     */
    public LegendContent getLegendContent() 
    {
        LegendContent legendContent = new LegendContent();
        LegendItem legendItem1 = new LegendItem("Point Values", "<img src='/dvt-faces-demo/resources/images/geoMap/mansion.gif' alt=\"no image\"/>");
        legendContent.addLegendItem(legendItem1);
        return legendContent;
    }
}
