/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/data/TwoColorData.java /bibeans_root/2 2012/03/16 22:57:38 jramanat Exp $ */

/* Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jramanat    02/29/12 - Creation
 */

package oracle.adfdemo.view.feature.rich.diagram.data;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/data/TwoColorData.java /bibeans_root/2 2012/03/16 22:57:38 jramanat Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class TwoColorData {
  public static final String EVEN_COLOR = "#003366";
  public static final String ODD_COLOR = "#CC3300";

  private static boolean evenLinks = true;
  private static boolean oddLinks = true;
  private static boolean evenNodes = true;
  private static boolean oddNodes = true;

  public static CollectionModel getNodes(int size) {
    List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < size; i++) {
      Map<String, Object> node = new HashMap<String, Object>();
      node.put("id", "N" + i);
      boolean odd = (i & 1) == 1;
      node.put("color", odd ? ODD_COLOR : EVEN_COLOR);
      node.put("selectable", ((odd && oddNodes) || (!odd && evenNodes)));
      nodes.add(node);
    }
    return ModelUtils.toCollectionModel(nodes);
  }
  
  public static CollectionModel getLinks(int size) {
    List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < size; i++) {
      Map<String, Object> link = new HashMap<String, Object>();
      link.put("id", "L" + i);
      link.put("start", "N" + i);
      link.put("end", "N" + ((2*i+1)%size));
      boolean odd = (i & 1) == 1;
      link.put("color", odd ? ODD_COLOR : EVEN_COLOR);
      link.put("selectable", ((odd && oddLinks) || (!odd && evenLinks)));
      links.add(link);
    }
    return ModelUtils.toCollectionModel(links);
  }

  public static void setEvenLinks(boolean evenLinks) {
    TwoColorData.evenLinks = evenLinks;
  }

  public static boolean isEvenLinks() {
    return evenLinks;
  }

  public static void setOddLinks(boolean oddLinks) {
    TwoColorData.oddLinks = oddLinks;
  }

  public static boolean isOddLinks() {
    return oddLinks;
  }

  public static void setEvenNodes(boolean evenNodes) {
    TwoColorData.evenNodes = evenNodes;
  }

  public static boolean isEvenNodes() {
    return evenNodes;
  }

  public static void setOddNodes(boolean oddNodes) {
    TwoColorData.oddNodes = oddNodes;
  }

  public static boolean isOddNodes() {
    return oddNodes;
  }  
}
