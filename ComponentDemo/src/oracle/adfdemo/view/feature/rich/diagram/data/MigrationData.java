/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/data/MigrationData.java /bibeans_root/2 2012/04/11 23:51:29 jramanat Exp $ */

/* Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved. */

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

package oracle.adfdemo.view.feature.rich.diagram.data;

import java.awt.Color;

import java.io.BufferedReader;
import java.io.InputStream;

import java.io.InputStreamReader;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import java.util.StringTokenizer;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/data/MigrationData.java /bibeans_root/2 2012/04/11 23:51:29 jramanat Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class MigrationData {
  private static boolean parsed = false;
  private static List<String> states;
  private static Map<String, Map<String, Integer>> migrationData;
  private static Map<String, Integer> incomingData;
  private static Map<String, Integer> outgoingData;
  private static Map<String, Integer> populationData;
  
  public static CollectionModel getNetMigrationNodes() {
    parseData();
    List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < states.size(); i++) {
      String state = states.get(i);
      Map<String, Object> node = createNode(state);
      int net = (Integer)node.get("net");
      node.put("shortDesc", "Net migration:" + Math.abs(net) + (net > 0 ? " incoming" : " outgoing"));
      node.put("color", net > 0 ? "#2C6700" : "#E81104");
      nodes.add(node);
    }
    return ModelUtils.toCollectionModel(nodes);
  }

  public static CollectionModel getNetMigrationByStateNodes(int index) {
    parseData();
    List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < states.size(); i++) {
      String state = states.get(i);
      Map<String, Object> node = createNode(state);
      if (i == index) {
        int net = (Integer)node.get("net");
        node.put("shortDesc", "Net migration:" + Math.abs(net) + (net > 0 ? " incoming" : " outgoing"));
        node.put("color", net > 0 ? "#2C6700" : "#E81104");
      }
      else {
        int incoming = migrationData.get(states.get(index)).get(states.get(i));
        int outgoing = migrationData.get(states.get(i)).get(states.get(index));
        int net = incoming - outgoing;
        if (net > 0) {
          node.put("shortDesc", "Net migration from " + states.get(i) + " to " + states.get(index) + ":" + net);
          node.put("color", "#2C6700");
        }
        else {
          node.put("shortDesc", "Net migration from " + states.get(index) + " to " + states.get(i) + ":" + Math.abs(net));
          node.put("color", "#E81104");
        }
      }
      nodes.add(node);
    }
    return ModelUtils.toCollectionModel(nodes);
  }

  public static CollectionModel getIncomingMigrationNodes(int index) {
    parseData();
    List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < states.size(); i++) {
      String state = states.get(i);
      Map<String, Object> node = createNode(state);
      if (i == index) {
        node.put("shortDesc", "Incoming migration:" + node.get("incoming"));
      }
      else {
        int incoming = migrationData.get(states.get(index)).get(states.get(i));
        if (incoming > 0) {
          node.put("shortDesc", "Incoming migration from " + states.get(i) + " to " + states.get(index) + ":" + incoming);
        }
      }
      node.put("color", "#000000");
      nodes.add(node);
    }
    return ModelUtils.toCollectionModel(nodes);
  }  

  public static CollectionModel getOutgoingMigrationNodes(int index) {
    parseData();
    List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < states.size(); i++) {
      String state = states.get(i);
      Map<String, Object> node = createNode(state);
      if (i == index) {
        node.put("shortDesc", "Outgoing migration:" + node.get("outgoing"));
      }
      else {
        int outgoing = migrationData.get(states.get(i)).get(states.get(index));
        if (outgoing > 0) {
          node.put("shortDesc", "Outgoing migration from " + states.get(index) + " to " + states.get(i) + ":" + outgoing);
        }
      }
      node.put("color", "#000000");
      nodes.add(node);
    }
    return ModelUtils.toCollectionModel(nodes);
  }  

  private static Map<String, Object> createNode(String state) {
    Map<String, Object> node = new HashMap<String, Object>();
    node.put("id", state);
    node.put("population", populationData.get(state));
    node.put("sqrtPopulation", Math.sqrt(populationData.get(state)));
    node.put("cbrtPopulation", Math.cbrt(populationData.get(state)));
    node.put("incoming", incomingData.get(state));
    node.put("outgoing", outgoingData.get(state));
    node.put("net", incomingData.get(state) - outgoingData.get(state));
    return node;
  }

  public static CollectionModel getNetMigrationLinks() {
    parseData();
    List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < states.size(); i++) {
      for (int j = i + 1; j < states.size(); j++) {
        int iToJ = migrationData.get(states.get(j)).get(states.get(i));
        int jToI = migrationData.get(states.get(i)).get(states.get(j));
        if (iToJ != jToI) {
          Map<String, Object> link = new HashMap<String, Object>();
          if (iToJ > jToI) {
            link.put("from", states.get(i));
            link.put("to", states.get(j));
            link.put("migration", iToJ - jToI);
          }
          else {
            link.put("from", states.get(j));
            link.put("to", states.get(i));
            link.put("migration", jToI - iToJ);            
          }
          link.put("color", "#2C6700");
          link.put("width", 1);
          links.add(link);
        }
      }
    }
    return ModelUtils.toCollectionModel(links);
  }

  public static CollectionModel getNetMigrationByStateLinks(int index) {
    parseData();
    String state = states.get(index);    
    List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < states.size(); i++) {
      if (i != index) {
        int incoming = migrationData.get(state).get(states.get(i));
        int outgoing = migrationData.get(states.get(i)).get(state);
        int net = incoming - outgoing;
        Map<String, Object> link = new HashMap<String, Object>();
        link.put("from", net > 0 ? states.get(i) : state);
        link.put("to", net > 0 ? state : states.get(i));
        link.put("migration", Math.abs(net));
        link.put("color", net > 0 ? "#2C6700" : "#E81104");
        link.put("width", 3);
        links.add(link);
      }
    }
    return ModelUtils.toCollectionModel(links);    
  }

  public static CollectionModel getIncomingMigrationLinks(int index) {
    parseData();
    String state = states.get(index);    
    List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < states.size(); i++) {
      if (i != index) {
        int migration = migrationData.get(state).get(states.get(i));
        if (migration > 0) {
          Map<String, Object> link = new HashMap<String, Object>();
          link.put("from", states.get(i));
          link.put("to", state);
          link.put("migration", migration);   
          link.put("color", "#2C6700");
          link.put("width", 3);
          links.add(link);
        }
      }
    }
    return ModelUtils.toCollectionModel(links);    
  }
  
  public static CollectionModel getOutgoingMigrationLinks(int index) {
    parseData();
    String state = states.get(index);    
    List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < states.size(); i++) {
      if (i != index) {
        int migration = migrationData.get(states.get(i)).get(state);
        if (migration > 0) {
          Map<String, Object> link = new HashMap<String, Object>();
          link.put("from", state);
          link.put("to", states.get(i));
          link.put("migration", migration);   
          link.put("color", "#E81104");
          link.put("width", 3);
          links.add(link);
        }
      }
    }
    return ModelUtils.toCollectionModel(links);    
  }

  private static void parseData() {
    if (!parsed) {
      try {
        migrationData = new HashMap<String, Map<String, Integer>>();
        incomingData = new HashMap<String, Integer>();
        outgoingData = new HashMap<String, Integer>();
        populationData = new HashMap<String, Integer>();
        InputStream input = MigrationData.class.getResourceAsStream("migrationData.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String firstLine = br.readLine();
        states = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(firstLine, ",");
        st.nextToken(); // Current State
        st.nextToken(); // Population
        st.nextToken(); // Changed State
        while (st.hasMoreTokens()) {
          states.add(st.nextToken());
        }
        String usLine = br.readLine();
        st = new StringTokenizer(usLine, ",");
        st.nextToken(); // United States
        st.nextToken(); // Population
        st.nextToken(); // Changed State
        for (int i = 0; i < states.size(); i++) {
          String token = st.nextToken();
          int value = Integer.valueOf(token);
          outgoingData.put(states.get(i), value);
        }
        String stateLine = null;
        while ((stateLine = br.readLine()) != null) {
          st = new StringTokenizer(stateLine, ",");
          String state = st.nextToken();
          populationData.put(state, Integer.valueOf(st.nextToken()));
          incomingData.put(state, Integer.valueOf(st.nextToken()));
          Map<String, Integer> incoming = new HashMap<String, Integer>();
          for (int i = 0; i < states.size(); i++) {
            String token = st.nextToken();
            if (!token.equals("N/A")) {
              int value = Integer.valueOf(token);
              incoming.put(states.get(i), value);
            }
          }
          migrationData.put(state, incoming);
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      finally {
        parsed = true;
      }
    }
  }
}
