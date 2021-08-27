/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/SelectionSample.java /main/5 2011/08/30 10:45:08 amdai Exp $ */

/* Copyright (c) 2009, 2011, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hzhang      08/31/09 - Creation
 */
package oracle.adfdemo.view.feature.rich.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import oracle.adf.view.faces.bi.component.graph.DataSelection;
import oracle.adf.view.faces.bi.component.graph.GraphSelection;
import oracle.adf.view.faces.bi.event.graph.SelectionEvent;
import oracle.adf.view.faces.bi.model.KeyMap;


/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/SelectionSample.java /main/2 2010/03/24 07:15:48 hzhang Exp $
 *  @author  hzhang
 *  @since   release specific (what release of product did this appear in)
 */
public class SelectionSample {
  public void selectionListener(SelectionEvent selectionEvent) {
    StringBuilder eventInfo = new StringBuilder();

    Set<? extends GraphSelection> selectionSet = selectionEvent.getGraphSelection();
    eventInfo.append(convertSelectionStateToString(selectionSet));
    
    // Store on the selection string
    m_selectionInfo = eventInfo.toString();
  }
  
  /**
   * Returns the selection state as a formatted String with one selected data point per line.
   * @param selectionSet
   * @return
   */
  public static String convertSelectionStateToString(Set<? extends GraphSelection> selectionSet) {
    StringBuilder selectionState = new StringBuilder();
    for(GraphSelection selection: selectionSet) {
      if(selection instanceof DataSelection) {
        DataSelection ds = (DataSelection) selection;
        Set seriesKeySet = ds.getSeriesKey().keySet();
        for(Object key : seriesKeySet) {
          selectionState.append(key).append(": ").append(ds.getSeriesKey().get((String)key));
        }
        
        List<KeyMap> groupKeys = ds.getGroupKeys();
        for(KeyMap groupKey : groupKeys) {
          Set groupKeySet = groupKey.keySet();
          for(Object key : groupKeySet) {
            selectionState.append("; ").append(key).append(": ").append(groupKey.get((String)key));
          }
        }
        
        selectionState.append("<br>");
      }
    }
    
    return selectionState.toString();
  }

  private String m_selectionInfo = "Select a marker to see information here.  Multiple objects can be selected by holding CTRL while selecting.";
  public String getSelectionInfo() {
    return m_selectionInfo;
  }
  
  private String graphType = "bubbleGraph";
  public String getGraphType() {
      return graphType;
  }
  
  public void setGraphType(String type) {
      graphType = type;
  }
  
  private List graphList;
  public List getGraphList() {
      graphList = new ArrayList();
      SelectItem graph = new SelectItem("bubbleGraph", "Bubble Graph");
      graphList.add(graph);       
      graph = new SelectItem("scatterGraph", "Scatter Graph");
      graphList.add(graph);
      return graphList;
  }
  
  private String graphTestType = "areaGraph";
  public String getGraphTestType() {
      return graphTestType;
  }
    
  public void setGraphTestType(String type) {
      graphTestType = type;
  }
  
  private List graphTestList;
  public List getGraphTestList() {
      graphTestList = new ArrayList();
      SelectItem graph = new SelectItem("areaGraph", "Area Graph");
      graphTestList.add(graph);
      graph = new SelectItem("barGraph", "Bar Graph");
      graphTestList.add(graph);
      graph = new SelectItem("bubbleGraph", "Bubble Graph");
      graphTestList.add(graph);
      graph = new SelectItem("lineGraph", "Line Graph");
      graphTestList.add(graph);
      graph = new SelectItem("pieGraph", "Pie Graph");
      graphTestList.add(graph);
      graph = new SelectItem("scatterGraph", "Scatter Graph");
      graphTestList.add(graph);
      graph = new SelectItem("stackedBarGraph", "Stacked Bar Graph");
      graphTestList.add(graph);
      graph = new SelectItem("3dBarGraph", "3D Bar Graph");
      graphTestList.add(graph);
      graph = new SelectItem("3dPieGraph", "3D Pie Graph");
      graphTestList.add(graph);
      graph = new SelectItem("stockGraph", "Stock Graph");
      graphTestList.add(graph);
      return graphTestList;
  }
}
