/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/EmployeeBean.java /bibeans_root/3 2015/04/28 09:21:05 breliu Exp $ */

/* Copyright (c) 2012, 2015, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jramanat    03/12/12 - Creation
 */
package oracle.adfdemo.view.feature.rich.diagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import oracle.adfdemo.view.feature.rich.hv.data.EmployeeData;

import oracle.adfdemo.view.feature.rich.hv.data.EmployeeNode;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/EmployeeBean.java /bibeans_root/3 2015/04/28 09:21:05 breliu Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class EmployeeBean {
  private CollectionModel m_nodes;
  private CollectionModel m_links;
  private String m_contentDelivery = "lazy";
  
  public EmployeeBean() {
    super();
  }
  
  public CollectionModel getNodes() {
    if (m_nodes == null) {
      parseModel();
    }
    return m_nodes;
  }
  
  public CollectionModel getLinks() {
    if (m_links == null) {
      parseModel();
    }
    return m_links;    
  }
  
  private void parseModel() {
    List<Map<String, String>> nodes = new ArrayList<Map<String, String>>();
    List<Map<String, String>> links = new ArrayList<Map<String, String>>();
    parseEmployee(EmployeeData.getRoot(), nodes, links);
    m_nodes = ModelUtils.toCollectionModel(nodes);
    m_links = ModelUtils.toCollectionModel(links);      
  }
  
  private void parseEmployee(EmployeeNode current, List<Map<String, String>> nodes, List<Map<String, String>> links) {
    List<String> attrs = current.attribute_names;
    Map<String, String> emp = new HashMap<String, String>();
    nodes.add(emp);
    for (int i = 0; i < attrs.size(); i++) {
      String n = attrs.get(i);
      emp.put(n, current.getAttribute(n));
    }
    if (current.getParent() != null) {
      Map<String, String> link = new HashMap<String, String>();      
      link.put("start", current.getParent().getPersonId());
      link.put("end", current.getPersonId());
      links.add(link);
    }
    List<EmployeeNode> nl = current.getChildren();
    for (int i = 0; i < nl.size(); i++) {
      parseEmployee(nl.get(i), nodes, links);

    }
  }

  public void setContentDelivery(String contentDelivery) {
    m_contentDelivery = contentDelivery;
  }

  public String getContentDelivery() {
    return m_contentDelivery;
  }
}
