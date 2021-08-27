/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/ZoomingSample.java /bibeans_root/2 2014/04/28 23:24:22 jramanat Exp $ */

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
    jramanat    03/21/12 - Creation
 */

package oracle.adfdemo.view.feature.rich.diagram;

import oracle.adf.view.faces.bi.component.diagram.UIDiagramLinks;
import oracle.adf.view.faces.bi.component.diagram.UIDiagramNodes;

import oracle.adfdemo.view.feature.rich.diagram.data.CircleData;

import org.apache.myfaces.trinidad.model.CollectionModel;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/ZoomingSample.java /bibeans_root/2 2014/04/28 23:24:22 jramanat Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class ZoomingSample {
  private double minZoom = 0;
  private double maxZoom = 2;
  private int size = 40;

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

  public void setSize(int size) {
    this.size = size;
  }

  public int getSize() {
    return size;
  }
  
  public CollectionModel getNodes() {
    return CircleData.getNodes(size, CircleData.Coloring.TWO_COLOR_GRADIENT);
  }
}
