/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/HighlightingSample.java /bibeans_root/3 2014/04/28 23:24:22 jramanat Exp $ */

/* Copyright (c) 2012, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jramanat    03/26/12 - Creation
 */
package oracle.adfdemo.view.feature.rich.diagram;

import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.diagram.UIDiagramLinks;
import oracle.adf.view.faces.bi.component.diagram.UIDiagramNodes;

import org.apache.myfaces.trinidad.model.CollectionModel;

import oracle.adfdemo.view.feature.rich.diagram.data.CircleData;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/HighlightingSample.java /bibeans_root/3 2014/04/28 23:24:22 jramanat Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class HighlightingSample {
  private int size = 40;
  private String nodeHighlightBehavior = "none";
  private String linkHighlightBehavior = "none";
  private RowKeySet highlightedNodes = new RowKeySetImpl();
  private RowKeySet highlightedLinks = new RowKeySetImpl();
  private UIDiagramNodes diagramNodes;
  private UIDiagramLinks diagramLinks;

  public void setSize(int size) {
    this.size = size;
    highlightedNodes.clear();
    highlightedLinks.clear();
  }

  public int getSize() {
    return size;
  }
  
  public CollectionModel getNodes() {
    return CircleData.getNodes(size, CircleData.Coloring.TWO_COLOR_GRADIENT);
  }
  
  public CollectionModel getLinks() {
    return CircleData.getLatticeLinks(size, CircleData.Coloring.TWO_COLOR_GRADIENT);
  }

  public void setNodeHighlightBehavior(String nodeHighlightBehavior) {
    this.nodeHighlightBehavior = nodeHighlightBehavior;
  }

  public String getNodeHighlightBehavior() {
    return nodeHighlightBehavior;
  }

  public void setLinkHighlightBehavior(String linkHighlightBehavior) {
    this.linkHighlightBehavior = linkHighlightBehavior;
  }

  public String getLinkHighlightBehavior() {
    return linkHighlightBehavior;
  }
  
  public RowKeySet getHighlightedNodes() {
    return highlightedNodes;
  }
  
  public RowKeySet getHighlightedLinks() {
    return highlightedLinks;
  }
  
  public boolean isNodeHighlighted() {
    RowKeySet selectedNodes = diagramNodes.getSelectedRowKeys();
    return highlightedNodes.containsAll(selectedNodes);
  }
  
  public boolean isLinkHighlighted() {
    RowKeySet selectedLinks = diagramLinks.getSelectedRowKeys();
    return highlightedLinks.containsAll(selectedLinks);
  }
  
  public void toggleNodeHighlight(ActionEvent actionEvent) {
    RowKeySet selectedNodes = diagramNodes.getSelectedRowKeys();
    if (highlightedNodes.containsAll(selectedNodes)) {
      highlightedNodes.removeAll(selectedNodes);
    }
    else {
      highlightedNodes.addAll(selectedNodes);
    }
    RequestContext.getCurrentInstance().addPartialTarget(diagramNodes.getParent());
  }
  
  public void toggleLinkHighlight(ActionEvent actionEvent) {
    RowKeySet selectedLinks = diagramLinks.getSelectedRowKeys();
    if (highlightedLinks.containsAll(selectedLinks)) {
      highlightedLinks.removeAll(selectedLinks);
    }
    else {
      highlightedLinks.addAll(selectedLinks);
    }
    RequestContext.getCurrentInstance().addPartialTarget(diagramLinks.getParent());
  }

  public void clearHighlighted(ActionEvent actionEvent) {
    highlightedNodes.clear();
    highlightedLinks.clear();
  }

  public void setDiagramNodes(UIDiagramNodes diagramNodes) {
    this.diagramNodes = diagramNodes;
  }

  public UIDiagramNodes getDiagramNodes() {
    return diagramNodes;
  }

  public void setDiagramLinks(UIDiagramLinks diagramLinks) {
    this.diagramLinks = diagramLinks;
  }

  public UIDiagramLinks getDiagramLinks() {
    return diagramLinks;
  }
}
