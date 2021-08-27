/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/ContextMenuSample.java /main/3 2010/03/29 07:56:45 hzhang Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hzhang      01/19/10 - Creation
 */

package oracle.adfdemo.view.feature.rich.graph;

import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.graph.DataSelection;
import oracle.adf.view.faces.bi.component.graph.GraphSelection;
import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.faces.bi.model.KeyMap;
import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;

import org.apache.myfaces.trinidad.context.RequestContext;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/ContextMenuSample.java /main/3 2010/03/29 07:56:45 hzhang Exp $
 *  @author  hzhang
 *  @since   release specific (what release of product did this appear in)
 */
public class ContextMenuSample {
  
  private RichOutputFormatted m_outputFormatted;
  public RichOutputFormatted getOutputFormatted() {
    if(m_outputFormatted == null)
      m_outputFormatted = new RichOutputFormatted();
    
    return m_outputFormatted;
  }
  public void setOutputFormatted(RichOutputFormatted text) {
    m_outputFormatted = text;
  }
  
  private String m_status = "Click Menu Item for Status";
  public String getStatus() {
    return m_status;
  }
  
  private UIGraph m_graph;
  public UIGraph getGraph() {
    if(m_graph == null)
      m_graph = new UIGraph();
    
    return m_graph;
  }
  public void setGraph(UIGraph graph) {
    m_graph = graph;
  } 
  public String getCurrentSeriesId() {
    if(m_graph != null) {
      Set<? extends GraphSelection> set = m_graph.getSelection();
      if(set != null && !set.isEmpty()) {
        GraphSelection selection = set.iterator().next();
        if(selection instanceof DataSelection) {
          DataSelection dataSelection = (DataSelection) selection;
          KeyMap seriesKey = dataSelection.getSeriesKey();
          Set seriesKeySet = seriesKey.keySet();
          for(Object key : seriesKeySet) {
            return seriesKey.get((String)key);
          }
        }
      }
    }
    return null;
  }
  
  /**
   * Called when a commandMenuItem is clicked.  Updates the outputText with information about the menu item clicked.
   * @param actionEvent
   */
  public void menuItemListener(ActionEvent actionEvent) {
    UIComponent component = actionEvent.getComponent();
    if(component instanceof RichCommandMenuItem) {
      RichCommandMenuItem cmi = (RichCommandMenuItem) component;
      
      // Add the text of the item into the status message
      StringBuilder s = new StringBuilder();
      s.append("You clicked on \"").append(cmi.getText()).append("\".  <br><br>");
      
      // If graph data is selected, add that too
      Set<? extends GraphSelection> selectionSet = m_graph.getSelection();
      if(!selectionSet.isEmpty()) {
        // Write out the selection state
        s.append("The current graph selection is: <br>");
        s.append(SelectionSample.convertSelectionStateToString(selectionSet));
      }

      m_status = s.toString();      
      RequestContext.getCurrentInstance().addPartialTarget(m_outputFormatted);
    }
  }
}

