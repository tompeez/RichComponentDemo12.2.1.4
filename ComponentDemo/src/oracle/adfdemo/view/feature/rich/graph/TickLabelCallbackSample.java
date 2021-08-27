/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/TickLabelCallbackSample.java /main/3 2009/05/29 17:50:43 ytang Exp $ */

/* Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hzhang      01/09/09 - Creation
 */

package oracle.adfdemo.view.feature.rich.graph;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.view.faces.bi.component.graph.UIGraph;

import oracle.dss.graph.CommonGraph;
import oracle.dss.graph.DataTickLabelInfo;
import oracle.dss.graph.GraphConstants;
import oracle.dss.graph.TickLabelCallback;
import oracle.dss.graph.TickLabelInfo;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/TickLabelCallbackSample.java /main/3 2009/05/29 17:50:43 ytang Exp $
 *  @author  hzhang  
 *  @since   release specific (what release of product did this appear in)
 */
public class TickLabelCallbackSample {
    private UIGraph m_graph;
    
    /**
     * Returns an instance of UIGraph that uses a custom TickLabelCallback.
     * @return
     */
    public UIGraph getGraph() {
        if(m_graph == null) {
            m_graph = new UIGraph();
            CommonGraph graph = (CommonGraph) m_graph.getImageView();
            graph.setTickLabelCallback(new MyTickLabelCallback(), GraphConstants.X1TICKLABEL);
            graph.setTickLabelCallback(new MyTickLabelCallback(), GraphConstants.Y1TICKLABEL);
        }
        return m_graph;
    }
    
    public void setGraph(UIGraph graph) {
        m_graph = graph;
    }
    
    public List getBarGraphData() {
        List data = new ArrayList();
        for (Integer i = 0; i<12; i++){
            data.add(new Object[]{"Group " + i.toString(), 
                                  "Series 1", 
                                  new Double(10.0 + 10.0 * Math.sin(i.doubleValue()/6*Math.PI))});
        }
        return data;
    }
    
    /**
     * Implementation of TickLabelCallback that demonstrates how to use the TickLabelInfo and axisID
     * to produce a custom label.
     */
    private class MyTickLabelCallback implements TickLabelCallback {
        public String getTickLabel(TickLabelInfo tickLabelInfo, int axisID) {
            StringBuilder label = new StringBuilder();                        
            
            // Append the Axis Name to the label
            if(axisID == GraphConstants.X1TICKLABEL)
                label.append("X1 Axis: ");
            else if(axisID == GraphConstants.Y1TICKLABEL)
                label.append("Y1 Axis: ");
            // Append the value of the tick to the label
            if(tickLabelInfo instanceof DataTickLabelInfo) {
                DataTickLabelInfo dataTickLabelInfo = (DataTickLabelInfo) tickLabelInfo;
                label.append(dataTickLabelInfo.getValue());
            }
            return label.toString();
        }
    }
}

