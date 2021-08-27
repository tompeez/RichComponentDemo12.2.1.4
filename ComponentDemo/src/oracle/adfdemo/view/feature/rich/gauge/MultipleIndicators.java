/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gauge/MultipleIndicators.java /main/3 2009/11/13 13:17:18 teclee Exp $ */

/* Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ytang       05/27/09 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gauge/MultipleIndicators.java /main/3 2009/11/13 13:17:18 teclee Exp $
 *  @author  ytang   
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gauge;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.view.faces.bi.component.gauge.UIGauge;

import oracle.adf.view.faces.bi.event.ClickEvent;

import oracle.dss.dataView.ComponentHandle;
import oracle.dss.dataView.DataComponentHandle;
import oracle.dss.gauge.CommonGauge;
import oracle.dss.gauge.DataSpecification;
import oracle.dss.gauge.GaugeAttributes;
import oracle.dss.gauge.ThresholdComponentHandle;


public class MultipleIndicators {

    private UIGauge m_gauge = null;

    public UIGauge getGauge() {

        m_gauge = new UIGauge();

        CommonGauge gauge = (CommonGauge)m_gauge.getImageView();
        
        // Sets the data specification
        DataSpecification dataSpec = gauge.getDataSpecification();
        
        // Sets the metric names
        ArrayList<String> metricNames = new ArrayList<String>();
        metricNames.add("Sales A");
        metricNames.add("Sales B");
        dataSpec.setMetrics(metricNames);
        
        // Sets the threshold names
        ArrayList<String> thresholdNames = new ArrayList<String>();
        thresholdNames.add("Quota");
        thresholdNames.add("Target");
        dataSpec.setThresholds(thresholdNames);
        
        // Sets the text describes the threshold area
        gauge.getThreshold().setText(0, "Low");
        gauge.getThreshold().setText(1, "Safe");
        gauge.getThreshold().setText(2, "High");
        
        // Sets the min and max
        dataSpec.setMaximum("Stock");
        dataSpec.setMinimum("Minimum");
        
        // Sets the indicator color so that it is easier to distinguish
        gauge.getIndicator().setFillColor(Color.red, 0);
        gauge.getIndicator().setFillColor(Color.blue, 1);
        gauge.getIndicator().setType(GaugeAttributes.IT_NEEDLE, 0);
        gauge.getIndicator().setType(GaugeAttributes.IT_NEEDLE, 1);
        gauge.getIndicator().setBarFillColor(Color.red, 0);
        gauge.getIndicator().setBarFillColor(Color.blue, 1);

        return m_gauge;
    }
    
    public void processClick(ClickEvent clickEvent){
        ComponentHandle handle = clickEvent.getComponentHandle();
        
        // process click event
        if (handle instanceof DataComponentHandle){
            DataComponentHandle dch = (DataComponentHandle)handle;
            System.out.println( dch.getName() + " is clicked" );
            System.out.println( "Data from column "+ dch.getColumn() + " row " + dch.getRow() );
        }
        if (handle instanceof ThresholdComponentHandle){
            ThresholdComponentHandle tch = (ThresholdComponentHandle)handle;
            System.out.println( "Threshold index "+ tch.getIndex() );
        }
    }

    public void setGauge(UIGauge gauge) {
    }
    
    public List getGaugeTabularData(){
        
        // Sets up a tabular data
        List data = new ArrayList();
        String[] colLabels = new String[] { "Minimum", "Quota", "Sales A", "Sales B", "Target", "Stock" };
        String[] rowLabels = new String[] { "Atlanta", "Boston", "Chicago" };
        double[][] values = new double[][]{ 
        // Atlanta, Boston, Chicago
        { 0, 0, 0 },    // Minimum
        { 10, 10, 15 }, // Quota
        { 55, 5, 24 },  // Sales A
        { 30, 70, 60 }, // Sales B
        { 50, 60, 40 }, // Target
        {100, 120, 130} // Stock
        };
        for (int c = 0; c < colLabels.length; c++) {
            for (int r = 0; r < rowLabels.length; r++) {
                data.add(new Object[] { colLabels[c], rowLabels[r], new Double(values[c][r]) });
            }
        }
        return data;
    }
}
