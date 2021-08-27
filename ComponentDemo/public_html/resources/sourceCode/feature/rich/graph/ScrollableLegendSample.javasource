/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/ScrollableLegendSample.java /main/4 2009/07/20 20:23:05 srkalyan Exp $ */

/* Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hzhang      01/08/09 - Creation
 */

package oracle.adfdemo.view.feature.rich.graph;

import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.faces.bi.model.DataModel;
import oracle.adf.view.faces.bi.model.GraphDataModel;

import oracle.dss.dataView.LocalXMLDataSource;
import oracle.dss.graph.CommonGraph;
import oracle.dss.graph.GraphConstants;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/ScrollableLegendSample.java /main/4 2009/07/20 20:23:05 srkalyan Exp $
 *  @author  hzhang
 *  @since   release specific (what release of product did this appear in)
 */
public class ScrollableLegendSample {    
    private DataModel m_scrollableLegendData;
    
    public DataModel getScrollableLegendData() {
        if(m_scrollableLegendData == null) {
            // Set up a data model with a large number of series
            int numGroups = 1;
            int numSeries = 50;
            Object[] colLabels = new Object[numGroups];
            Object[] rowLabels = new Object[numSeries];
            Object[][] data = new Object[numGroups][numSeries];
            for(int j=0; j<numGroups; j++) {
                colLabels[j] = "Group " + (j+1);
                for(int i=0; i<numSeries; i++) {
                    rowLabels[i] = "Series " + (i+1);
                    data[j][i] = Math.random() * 1000.0;
                }
            }
            LocalXMLDataSource dataSource = new LocalXMLDataSource(colLabels, rowLabels, data);
            m_scrollableLegendData = new GraphDataModel(dataSource);
        }
        return m_scrollableLegendData;
    }    
}
