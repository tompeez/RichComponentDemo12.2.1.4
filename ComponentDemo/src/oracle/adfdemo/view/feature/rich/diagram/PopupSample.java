/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/PopupSample.java /bibeans_root/2 2014/04/28 23:24:22 jramanat Exp $ */

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
    jramanat    11/27/12 - Creation
 */

package oracle.adfdemo.view.feature.rich.diagram;

import oracle.adfdemo.view.feature.rich.diagram.data.CircleData;

import org.apache.myfaces.trinidad.model.CollectionModel;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/PopupSample.java /bibeans_root/2 2014/04/28 23:24:22 jramanat Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class PopupSample {
  private final int size = 4;
  private Object popupSourceId;
  private CollectionModel nodes;
  private CollectionModel links;

  public void setPopupSourceId(Object popupSourceId) {
    this.popupSourceId = popupSourceId;
  }

  public Object getPopupSourceId() {
    return popupSourceId;
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
}