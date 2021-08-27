/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/TimeSelectorSample.java /main/6 2010/06/22 11:44:09 ytang Exp $ */

/* Copyright (c) 2009, 2010, Oracle and/or its affiliates. 
All rights reserved. */

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

import oracle.adfdemo.view.feature.rich.graph.data.TimeAxisDataSourceMonths;

import java.text.DateFormat;

import java.util.Date;

import javax.faces.event.AbortProcessingException;

import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.faces.bi.event.TimeSelectorEvent;
import oracle.adf.view.faces.bi.event.TimeSelectorListener;

import oracle.adf.view.faces.bi.model.GraphDataModel;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/TimeSelectorSample.java /main/6 2010/06/22 11:44:09 ytang Exp $
 *  @author  hzhang  
 *  @since   release specific (what release of product did this appear in)
 */
public class TimeSelectorSample {
    private GraphDataModel m_timeAxisModel = new GraphDataModel(new TimeAxisDataSourceMonths());
    public GraphDataModel getTimeAxisGraphDataModel()
    {
      return m_timeAxisModel;
    }
    
    private static java.sql.Date m_startDate = java.sql.Date.valueOf("2005-05-01");
    private static java.sql.Date m_endDate = java.sql.Date.valueOf("2005-11-30");
    public Date getTimeAxisStartDate() {
        return m_startDate;
    }
    public Date getTimeAxisEndDate() {
        return m_endDate;
    }    
    
    private UIGraph m_timeSelectorDetailGraph;
    public UIGraph getDetailGraph() {
        if(m_timeSelectorDetailGraph == null)
           m_timeSelectorDetailGraph = new UIGraph();
        
        return m_timeSelectorDetailGraph;
    }
    public void setDetailGraph(UIGraph graph) {
        m_timeSelectorDetailGraph = graph;
    }
    
    public void processTimeSelectorEvent(TimeSelectorEvent event)  throws AbortProcessingException
    {
      java.sql.Date startDate = new java.sql.Date(event.getStartDate().getTime());
      java.sql.Date endDate = new java.sql.Date(event.getEndDate().getTime());
      DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
      System.out.println("TimeSelectorEvent: " + formatter.format(startDate) + " to " + 
                         formatter.format(endDate));
      if (m_timeSelectorDetailGraph != null)
      {
        m_timeSelectorDetailGraph.setTimeRangeStart(startDate);
        m_timeSelectorDetailGraph.setTimeRangeEnd(endDate);
      }
    }     
}

