/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/AlertBean.java */

/* Copyright (c) 2010, 2011, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ytang      11/09/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/AlertBean.java
 *  @author  ytang  
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.graph;


import java.awt.Color;

import java.util.Vector;

import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.faces.bi.model.GraphDataModel;

import oracle.dss.dataView.ComponentHandle;
import oracle.dss.dataView.DataComponentHandle;
import oracle.dss.dataView.LocalXMLDataSource;
import oracle.dss.graph.CommonGraph;
import oracle.dss.graph.CustomToolTipCallback;
import oracle.dss.graph.managers.GraphStyle;
import oracle.dss.rules.DiscriminatorRule;
import oracle.dss.rules.RuleBundle;
import oracle.dss.rules.discriminator.NumberValueDiscriminator;
import oracle.dss.rules.discriminator.PositionDiscriminator;
import oracle.dss.rules.discriminator.ValueDiscriminator;
import oracle.dss.util.ColumnComponentInfo;


/**
 * Waterfall Chart Demo
 */
public class WaterfallBean {
  
    
    private static final Color RED = new Color(255, 0, 0);
    private static final Color GREEN = new Color(64, 153, 51);
    private static final Color DARK_BLUE = new Color(0, 0, 187);
    private static final Color LIGHT_GRAY = Color.LIGHT_GRAY;
       
    
    public void setGraph(UIGraph graph) 
    {
    }
    
    public UIGraph getGraph() 
    {
        // Initialize the graph
        UIGraph graph = new UIGraph();
        
        // Set the series color for the series for the legend and tooltips
        graph.getSeriesSet().getSeries(1,true).setColor(LIGHT_GRAY);
        graph.getSeriesSet().getSeries(2,true).setColor(GREEN);
        graph.getSeriesSet().getSeries(3,true).setColor(RED);
        graph.getSeriesSet().getSeries(4,true).setColor(DARK_BLUE);
        
        // Update the tooltips
        graph.setCustomToolTipCallback(new CustomToolTipCallbackImpl());
        
        
        // Internal Code to hide legend series        
        CommonGraph cg = ((CommonGraph)graph.getImageView());
        // Make sure the legend hides the bar value series
        cg.setLegendSeriesCount(3);
        cg.setLegendSeriesStart(1);
        
        return graph;
    }
    
    
    /**
    * Source data, where the first and last values are the actual values, and the middle values are increments.
    */
    private static final double[] DATA = {100, -10, 20, -5, -1, -2, -3, 2, 7, 108};
    
    private GraphDataModel m_model;
    public GraphDataModel getGraphDataModel() {
        if (m_model == null)
            m_model = new GraphDataModel(getDataModel(DATA));
        return m_model;
    }
    
    /**
    * Helper function to create a DataAccess given a list of values and increments.
    */
    private static LocalXMLDataSource getDataModel(double[] sourceData) {
        String[] columnLabels = new String[sourceData.length];
        
        // Set up the column labels and row labels
        Object[] rowLabels = {"Float Value", "Bar Value", "Increase", "Decrease", "Total"};
        for(int i=0; i<columnLabels.length; i++) {
            columnLabels[i] = "Day " + i;
        }
        
        // Override the first and last labels
        columnLabels[0] = "Initial";
        columnLabels[columnLabels.length-1] = "Total";
        
        // Set up the data
        Double[][] data = new Double[columnLabels.length][rowLabels.length];
        double currValue = 0;
        for(int i=0; i<columnLabels.length; i++) {
            // Read the data from the data source:
            
            // First and last value: show entire bars
            // Middle values: show increments only
            if(i == 0 || i == sourceData.length-1) 
            data[i][0] = 0.0;
            else // Incremental value
            data[i][0] = currValue;
            
            // Set the bar value
            data[i][1] = sourceData[i];
            
            // Also set the placeholder values for the legend
            data[i][2] = 0.0;
            data[i][3] = 0.0;
            data[i][4] = 0.0;
            
            // Update the current value
            currValue += sourceData[i];
        }
        
        return new LocalXMLDataSource(columnLabels, rowLabels, data);
    }
    
    /**
    * Set bar colors with conditional formatting
    */
    public Vector getConditionalFormattingVector() {
        // Color rising and falling markers differently.  More options are available via conditional formatting
        RuleBundle bundle = new RuleBundle();
        DiscriminatorRule dr = new DiscriminatorRule();
        GraphStyle gs = new GraphStyle();
        //create rule for fill color red if value is negative
        dr.setDiscriminator(new NumberValueDiscriminator(0.0, ValueDiscriminator.LT));
        gs.setFillColor(RED); 
        dr.setFixedMergeable(gs);
        bundle.addRule(dr);
        //create rule for fill color green if value is positive
        dr = new DiscriminatorRule();
        dr.setDiscriminator(new NumberValueDiscriminator(0.0, ValueDiscriminator.GT));
        gs = new GraphStyle();
        gs.setFillColor(GREEN);
        dr.setFixedMergeable(gs);
        bundle.addRule(dr);
        //create rule for fill color dark blue for starting bar
        dr = new DiscriminatorRule();
        ColumnComponentInfo cci_start = new ColumnComponentInfo(0);
        dr.setDiscriminator(new PositionDiscriminator(cci_start));
        gs.setFillColor(DARK_BLUE); 
        dr.setFixedMergeable(gs);
        bundle.addRule(dr);  
        //create rule for fill color dark blue for ending bar
        dr = new DiscriminatorRule();
        ColumnComponentInfo cci_end = new ColumnComponentInfo(DATA.length-1);
        dr.setDiscriminator(new PositionDiscriminator(cci_end));
        gs.setFillColor(DARK_BLUE); 
        dr.setFixedMergeable(gs);
        bundle.addRule(dr);
        
        //put bundle in vector and set on graph
        Vector styleVector = new Vector();
        styleVector.addElement(bundle);
        return styleVector;
    }
    
    /**
    * Updates the tooltips to be more appropriate for a waterfall chart.
    */
    private static class CustomToolTipCallbackImpl implements CustomToolTipCallback {
        public String getToolTipText(String string, ComponentHandle componentHandle) {
            if(componentHandle instanceof DataComponentHandle) {
                DataComponentHandle dh = (DataComponentHandle) componentHandle;
                
                // If a placeholder series, return nothing
                int series = dh.getSeries();
                if(series != 1)
                return null;
                
                int group = dh.getGroup();
                if(group == 0) 
                return "Start Value: " + DATA[group];
                else if(group == DATA.length-1)
                return "End Value: " + DATA[group];
                else
                return "Change: " + DATA[group];
            }
            // Otherwise return default
            return string;
        }
    }
}
