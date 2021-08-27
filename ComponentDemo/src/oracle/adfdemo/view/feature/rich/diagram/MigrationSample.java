/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/MigrationSample.java /bibeans_root/3 2013/03/15 16:56:49 jramanat Exp $ */

/* Copyright (c) 2012, 2013, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jramanat    04/09/12 - Creation
 */

package oracle.adfdemo.view.feature.rich.diagram;

import oracle.adf.view.faces.bi.component.diagram.UIDiagramLinks;
import oracle.adf.view.faces.bi.component.diagram.UIDiagramNodes;

import oracle.adfdemo.view.feature.rich.diagram.data.MigrationData;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/MigrationSample.java /bibeans_root/3 2013/03/15 16:56:49 jramanat Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class MigrationSample {
  private static final String NET_MIGRATION = "Net Migration (All States)";
  private static final String NET_MIGRATION_BY_STATE = "Net Migration by State";
  private static final String INCOMING_BY_STATE = "Incoming Migration by State";
  private static final String OUTGOING_BY_STATE = "Outgoing Migration by State";
  private UIDiagramNodes diagramNodes;
  private UIDiagramLinks diagramLinks;
  private String linkMode = NET_MIGRATION;
  private int selectedIndex = 0;
  private RowKeySet selectedRowKeys;

  public MigrationSample() {
  }

  public CollectionModel getNodes() {
    if (NET_MIGRATION.equals(linkMode)) {
      return MigrationData.getNetMigrationNodes();
    }
    if (NET_MIGRATION_BY_STATE.equals(linkMode)) {
      return MigrationData.getNetMigrationByStateNodes(getSelectedIndex());
    }
    else if (INCOMING_BY_STATE.equals(linkMode)) {
      return MigrationData.getIncomingMigrationNodes(getSelectedIndex());
    }
    else if (OUTGOING_BY_STATE.equals(linkMode)) {
      return MigrationData.getOutgoingMigrationNodes(getSelectedIndex());
    }
    return null;
  }

  public CollectionModel getLinks() {
    if (NET_MIGRATION.equals(linkMode)) {
      return MigrationData.getNetMigrationLinks();
    }
    if (NET_MIGRATION_BY_STATE.equals(linkMode)) {
      return MigrationData.getNetMigrationByStateLinks(getSelectedIndex());
    }
    else if (INCOMING_BY_STATE.equals(linkMode)) {
      return MigrationData.getIncomingMigrationLinks(getSelectedIndex());
    }
    else if (OUTGOING_BY_STATE.equals(linkMode)) {
      return MigrationData.getOutgoingMigrationLinks(getSelectedIndex());
    }
    return null;
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

  public String getSelectionMode() {
    return NET_MIGRATION.equals(linkMode) ? "none" : "single";
  }

  public void setLinkMode(String linkMode) {
    this.linkMode = linkMode;
  }

  public String getLinkMode() {
    return linkMode;
  }

  public int getSelectedIndex() {
    return selectedIndex;
  }

  public void selectNode(SelectionEvent selectionEvent) {
    if (selectionEvent.getAddedSet().size() == 1) {
      selectedIndex = (Integer)selectionEvent.getAddedSet().toArray()[0];
      RequestContext.getCurrentInstance().addPartialTarget(diagramNodes.getParent());
    }
  }
 
  public String getHighlightBehavior() {
    return NET_MIGRATION.equals(linkMode) ? "nodeAndIncomingLinks" : "none";
  }
}
