/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/PopupSample.java /main/4 2010/08/20 07:11:48 byoshimo Exp $ */

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
    hzhang      03/05/09 - Creation
 */

package oracle.adfdemo.view.feature.rich.graph;

import java.util.List;
import java.util.Set;

import oracle.adf.view.faces.bi.component.gauge.UIGauge;
import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.faces.bi.model.KeyMap;
import oracle.dss.graph.DataType;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/PopupSample.java /main/4 2010/08/20 07:11:48 byoshimo Exp $
 *  @author  hzhang  
 *  @since   release specific (what release of product did this appear in)
 */
public class PopupSample {    
  private UIGraph m_graph;
  private UIGauge m_gauge;
  
  private KeyMap m_seriesKey;
  private List<KeyMap> m_groupKeys;
  
  public UIGraph getGraph() {
      if(m_graph == null)
         m_graph = new UIGraph();
      return m_graph;
  }
  
  public void setGraph(UIGraph graph) {
      m_graph = graph;
  }
  
  public UIGauge getGauge() {
      if(m_gauge == null)
         m_gauge = new UIGauge();
      return m_gauge;
  }
  
  public void setGauge(UIGauge gauge) {
      m_gauge = gauge;
  }
  
  public KeyMap getSeriesKey() {
    return m_seriesKey;
  }
  
  public void setSeriesKey(KeyMap key) {
    m_seriesKey = key;
  }
  
  public List<KeyMap> getGroupKeys() {
    return m_groupKeys;
  }
  
  public void setGroupKeys(List<KeyMap> keys) {
    m_groupKeys = keys;
  }
  
  public String getPopupString() {
    if(m_seriesKey == null || m_groupKeys == null)
      return "No Context";
    
    StringBuilder ret = new StringBuilder();
    Set seriesKeySet = m_seriesKey.keySet();
    for(Object key : seriesKeySet) {
      ret.append(key).append(": ").append(m_seriesKey.get((String)key)).append("<br>");
    }
    
    // Just show the first column label
    KeyMap groupKey = m_groupKeys.get(0);
    Set groupKeySet = groupKey.keySet();
    ret.append(groupKey.get((String)groupKeySet.iterator().next()));
    
    return ret.toString();
  }

  private int m_gaugeValue = 0;
  public int getGaugeValue() {
    // Reestablish context
    m_graph.setDataKey(m_seriesKey, m_groupKeys);
    // Fetch the x value
    Object val = m_graph.getDataValue(DataType.X_VALUE);
    if(val instanceof Number) {
      Number number = (Number) val;
      m_gaugeValue = (int) number.doubleValue();
    }
    return m_gaugeValue;
  }
  
  private int m_quotaValue = 0;
  public int getQuotaValue() {
    // Reestablish context
    m_graph.setDataKey(m_seriesKey, m_groupKeys);
    // Fetch the y value
    Object val = m_graph.getDataValue(DataType.Y_VALUE);
    if(val instanceof Number) {
      Number number = (Number) val;
      m_quotaValue = (int) number.doubleValue();
    }
    return m_quotaValue;
  }
}

