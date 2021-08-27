/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/LinkStylesSample.java /bibeans_root/3 2014/04/28 23:24:22 jramanat Exp $ */

/* Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jramanat    01/11/13 - Creation
 */

package oracle.adfdemo.view.feature.rich.diagram;

import oracle.adfdemo.view.feature.rich.diagram.data.CircleData;

import org.apache.myfaces.trinidad.model.CollectionModel;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/LinkStylesSample.java /bibeans_root/3 2014/04/28 23:24:22 jramanat Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class LinkStylesSample {
  private final int size = 4;
  private CollectionModel nodes;
  private CollectionModel links;
  private int linkWidth = 1;
  private String startConnector="none";
  private String endConnector="arrow";

  public void setLinkWidth(int linkWidth) {
    this.linkWidth = Math.max(1, linkWidth);
  }

  public int getLinkWidth() {
    return linkWidth;
  }

  public CollectionModel getNodes() {
    if (nodes == null) {
      nodes = CircleData.getNodes(size, CircleData.Coloring.RAINBOW);
    }
    return nodes;
  }
  
  public CollectionModel getLinks() {
    if (links == null) {
      links = CircleData.getRingLinks(size, CircleData.Coloring.RAINBOW);
    }
    return links;
  }

  public void setStartConnector(String startConnector) {
    this.startConnector = startConnector;
  }

  public String getStartConnector() {
    return startConnector;
  }

  public void setEndConnector(String endConnector) {
    this.endConnector = endConnector;
  }

  public String getEndConnector() {
    return endConnector;
  }
}
