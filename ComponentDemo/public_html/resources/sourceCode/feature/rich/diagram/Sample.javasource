/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/Sample.java /bibeans_root/12 2016/03/24 13:48:18 breliu Exp $ */

/* Copyright (c) 2012, 2016, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    bglazer     10/12/12 - added Diagram stacking demo
    jramanat    02/07/12 - Creation
 */

package oracle.adfdemo.view.feature.rich.diagram;

import java.util.Map;

import oracle.adf.view.faces.bi.component.diagram.UIDiagram;
import oracle.adf.view.faces.bi.component.diagram.UIDiagramLinks;
import oracle.adf.view.faces.bi.component.diagram.UIDiagramNodes;

import oracle.adfdemo.view.feature.rich.diagram.data.AttrGroupsData;
import oracle.adfdemo.view.feature.rich.diagram.data.CircleData;
import oracle.adfdemo.view.feature.rich.diagram.data.TwoColorData;

import org.apache.myfaces.trinidad.component.UIXCollection;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/Sample.java /bibeans_root/12 2016/03/24 13:48:18 breliu Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class Sample {
  private static final String CIRCLE_DATA = "Circle";
  private static final String TWO_COLOR_DATA = "Two Color";
  private static final String EMPTY_DATA = "Empty Data";
  
  // Generic Faces Attrs
  private String contentDelivery = "immediate";

  // Data Model Attrs
  private String dataType = "Circle";
  private int size = 80;

  // Component Attrs
  private String animationOnDisplay = "alphaFade";
  private int animationDuration = 500;
  private String emptyText = "Empty Diagram";
  private String selectionMode = "multiple";
  
  private boolean nodesSelectable = true;
  private boolean linksSelectable = true;
  
  private String rolloverBehavior = "none";
  private String nodeHighlightBehavior = "none";
  private String linkHighlightBehavior = "none";

  private String noteWindowMessage = null;


  private UIDiagram diagram = null;
  private UIDiagramNodes diagramNodes = null;
  private UIDiagramLinks diagramLinks = null;

  private double minZoom = 0;
  private double maxZoom = 2;

  private String controlPanelBehavior = "initCollapsed";
  private String panning = "auto";
  private String panDirection = "auto";
  private String zooming = "auto";
    
  public CollectionModel getCircleNodes() {
    return CircleData.getNodes(size, CircleData.Coloring.CIRCULAR_GRADIENT);
  }
  
  public CollectionModel getCircleLinks() {
    return CircleData.getLinks(size, CircleData.Coloring.CIRCULAR_GRADIENT);
  }
  
  public CollectionModel getSmallCircleNodes() {
    return CircleData.getNodes(40, CircleData.Coloring.CIRCULAR_GRADIENT);
  }
  
  public CollectionModel getSmallCircleLinks() {
    return CircleData.getLinks(40, CircleData.Coloring.CIRCULAR_GRADIENT);
  }

  public CollectionModel getMediumCircleNodes() {
    return CircleData.getNodes(80, CircleData.Coloring.CIRCULAR_GRADIENT);
  }
  
  public CollectionModel getMediumCircleLinks() {
    return CircleData.getLinks(80, CircleData.Coloring.CIRCULAR_GRADIENT);
  }

  public CollectionModel getLargeCircleNodes() {
    return CircleData.getNodes(200, CircleData.Coloring.CIRCULAR_GRADIENT);
  }
  
  public CollectionModel getLargeCircleLinks() {
    return CircleData.getLinks(200, CircleData.Coloring.CIRCULAR_GRADIENT);
  }

  public CollectionModel getTwoColorNodes() {
    return TwoColorData.getNodes(size);
  }
  
  public CollectionModel getTwoColorLinks() {
    return TwoColorData.getLinks(size);
  }

  public CollectionModel getSmallTwoColorNodes() {
    return TwoColorData.getNodes(40);
  }
  
  public CollectionModel getSmallTwoColorLinks() {
    return TwoColorData.getLinks(40);
  }

  public CollectionModel getMediumTwoColorNodes() {
    return TwoColorData.getNodes(80);
  }
  
  public CollectionModel getMediumTwoColorLinks() {
    return TwoColorData.getLinks(80);
  }

  public CollectionModel getLargeTwoColorNodes() {
    return TwoColorData.getNodes(200);
  }
  
  public CollectionModel getLargeTwoColorLinks() {
    return TwoColorData.getLinks(200);
  }
  
  public CollectionModel getMediumFiveAttrGroupsNodes() {
    return AttrGroupsData.getNodes(80, 5);
  }
  
  public CollectionModel getMediumAttrGroupsLinks() {
    return AttrGroupsData.getLinks(80);
  }

  public CollectionModel getNodes() {
    if (CIRCLE_DATA.equals(dataType)) {
      return getCircleNodes();
    }
    else if (TWO_COLOR_DATA.equals(dataType)) {
      return getTwoColorNodes();
    }
    else {
      return null;
    }
  }
  
  public CollectionModel getLinks() {
    if (CIRCLE_DATA.equals(dataType)) {
      return getCircleLinks();
    }
    else if (TWO_COLOR_DATA.equals(dataType)) {
      return getTwoColorLinks();
    }
    else {
      return null;
    }
  }
  
  /**
   * Converts the rowKeySet into a string of node text labels.
   * @param rowKeySet
   * @param collection
   * @return
   */
  public static String convertToString(RowKeySet rowKeySet, UIXCollection collection) {
    StringBuilder s = new StringBuilder();
    if (rowKeySet != null) {
      for (Object rowKey : rowKeySet) {
        Map<String, Object> rowData = (Map<String, Object>)collection.getRowData(rowKey);
        s.append(rowData.get("id")).append(' ');
      }

      // Remove the trailing space
      if (s.length() > 0) {
        s.setLength(s.length() - 1);
      }
    }
    return s.toString();
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getSize() {
    return size;
  }

  public void setContentDelivery(String contentDelivery) {
    this.contentDelivery = contentDelivery;
  }

  public String getContentDelivery() {
    return contentDelivery;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getDataType() {
    return dataType;
  }

  public void setAnimationOnDisplay(String animationOnDisplay) {
    this.animationOnDisplay = animationOnDisplay;
  }

  public String getAnimationOnDisplay() {
    return animationOnDisplay;
  }

  public void setEmptyText(String emptyText) {
    this.emptyText = emptyText;
  }

  public String getEmptyText() {
    return emptyText;
  }

  public void setSelectionMode(String selectionMode) {
    this.selectionMode = selectionMode;
  }

  public String getSelectionMode() {
    return selectionMode;
  }

  public void setNoteWindowMessage(String noteWindowMessage) {
    this.noteWindowMessage = noteWindowMessage;
  }

  public String getNoteWindowMessage() {
    return noteWindowMessage;
  }

  public void setRolloverBehavior(String rolloverBehavior) {
    this.rolloverBehavior = rolloverBehavior;
  }

  public String getRolloverBehavior() {
    return rolloverBehavior;
  }

  public void setDiagram(UIDiagram diagram) {
    this.diagram = diagram;
  }

  public UIDiagram getDiagram() {
    return diagram;
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
  
  public String getSelectionState() {
    return Sample.getSelectionState(diagramNodes, diagramLinks);
  }
  
  static String getSelectionState(UIDiagramNodes diagramNodes, UIDiagramLinks diagramLinks) {
    if (diagramNodes != null || diagramLinks != null) {
      StringBuilder state = new StringBuilder();
      if (diagramNodes != null) {
        String nodeString = Sample.convertToString(diagramNodes.getSelectedRowKeys(), diagramNodes);
        state.append(nodeString);
      }
      if (diagramLinks != null) {
        String linkString = Sample.convertToString(diagramLinks.getSelectedRowKeys(), diagramLinks);
        if (state.length() > 0 && linkString.length() > 0) {
          state.append(' ');
        }
        state.append(linkString);
      }
      return state.toString();
    } 
    else {
      return null;
    }    
  }

  public void setAnimationDuration(int animationDuration) {
    this.animationDuration = animationDuration;
  }

  public int getAnimationDuration() {
    return animationDuration;
  }

  public void setMinZoom(double minZoom) {
    this.minZoom = Math.min(minZoom, this.maxZoom);
  }

  public double getMinZoom() {
    return minZoom;
  }

  public void setMaxZoom(double maxZoom) {
    this.maxZoom = maxZoom > 0 ? maxZoom : this.maxZoom;
    this.minZoom = Math.min(this.maxZoom, this.minZoom);
  }

  public double getMaxZoom() {
    return maxZoom;
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
  
  public void setControlPanelBehavior(String controlPanelBehavior) {
    this.controlPanelBehavior = controlPanelBehavior;
  }
  
  public String getControlPanelBehavior() {
    return this.controlPanelBehavior;
  }

  public void setPanning(String panning) {
    diagram.getFacesBean().setProperty(UIDiagram.PAN_X_KEY, null);
    diagram.getFacesBean().setProperty(UIDiagram.PAN_Y_KEY, null);
    diagram.getFacesBean().setProperty(UIDiagram.ZOOM_KEY, null);
    this.panning = panning;
  }

  public String getPanning() {
    return panning;
  }

  public void setZooming(String zooming) {
    diagram.getFacesBean().setProperty(UIDiagram.PAN_X_KEY, null);
    diagram.getFacesBean().setProperty(UIDiagram.PAN_Y_KEY, null);
    diagram.getFacesBean().setProperty(UIDiagram.ZOOM_KEY, null);
    this.zooming = zooming;
  }

  public String getZooming() {
    return zooming;
  }

  public void setNodesSelectable(boolean nodesSelectable) {
    this.nodesSelectable = nodesSelectable;
  }

  public boolean isNodesSelectable() {
    return nodesSelectable;
  }

  public void setLinksSelectable(boolean linksSelectable) {
    this.linksSelectable = linksSelectable;
  }

  public boolean isLinksSelectable() {
    return linksSelectable;
  }

  public void setPanDirection(String panDirection) {
    diagram.getFacesBean().setProperty(UIDiagram.PAN_X_KEY, null);
    diagram.getFacesBean().setProperty(UIDiagram.PAN_Y_KEY, null);
    diagram.getFacesBean().setProperty(UIDiagram.ZOOM_KEY, null);
    this.panDirection = panDirection;
  }

  public String getPanDirection() {
    return panDirection;
  }
}
