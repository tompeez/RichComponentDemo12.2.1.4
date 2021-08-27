/* Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
      bglazer   10/12/12 - added Diagram stacking demo
      bglazer   10/12/12 - data for stacking demo
 */

package oracle.adfdemo.view.feature.rich.diagram.data;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/data/AttrGroupsData.java /bibeans_root/1 2012/10/12 19:13:50 bglazer Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class AttrGroupsData {

  public static CollectionModel getNodes(int size, int groups) {
    List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < size; i++) {
      Map<String, Object> node = new HashMap<String, Object>();
      node.put("id", "N" + i);
      node.put("group", "Group " + ((i % groups) + 1));
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
      links.add(link);
    }
    return ModelUtils.toCollectionModel(links);
  }
}