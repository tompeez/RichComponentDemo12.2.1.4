/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/DrillingSample.java /main/3 2009/09/07 09:33:03 smasalia Exp $ */

/* Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hzhang      01/12/09 - Creation
 */

package oracle.adfdemo.view.feature.rich.graph;

import oracle.adf.view.faces.bi.event.DrillEvent;
import oracle.adf.view.faces.bi.model.GraphDataModel;

import oracle.adfdemo.view.feature.rich.graph.dataSource.DrillDataSource;
import oracle.dss.util.DataAccess;
import oracle.dss.util.DataDirector;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/DrillingSample.java /main/3 2009/09/07 09:33:03 smasalia Exp $
 *  @author  hzhang  
 *  @since   release specific (what release of product did this appear in)
 */
public class DrillingSample {
    private GraphDataModel m_model;
    private String m_outputText = "";

    public GraphDataModel getGraphDataModel() {
        if (m_model == null)
            m_model = new GraphDataModel(new DrillDataSource());
        return m_model;
    }
    
    public String getDrillResult() {
        return m_outputText;
    }

    /**
     * Listener that is called when a drill is about to be performed.
     * @param event the DrillEvent containing information about the drill
     */
    public void drillRequesting(DrillEvent event) {
        int edge = event.getEdge();
        int layer = event.getLayer();
        int slice = event.getSlice();

        String edgeStr;
        if (edge == DataDirector.ROW_EDGE)
            edgeStr = "row";
        else if (edge == DataDirector.ROW_EDGE)
            edgeStr = "column";
        else
            edgeStr = "page";

        m_outputText = "DrillRequestingListener: A drill on layer " + layer + " slice " +
                        slice + " of the " + edgeStr + " edge is about to occur.\n";
    }

    /**
     * Listener that is called after a drill is performed.
     * @param event the DrillEvent containing information about the drill
     */
    public void drillRequested(DrillEvent event) {
        int edge = event.getEdge();
        int layer = event.getLayer();
        int slice = event.getSlice();

        String edgeStr;
        if (edge == DataDirector.ROW_EDGE)
            edgeStr = "row";
        else if (edge == DataDirector.ROW_EDGE)
            edgeStr = "column";
        else
            edgeStr = "page";

        m_outputText = "DrillRequestedListener: A drill on layer " + layer + " slice " +
                        slice + " of the " + edgeStr + " edge has completed.\n" + m_outputText;
    }
}
