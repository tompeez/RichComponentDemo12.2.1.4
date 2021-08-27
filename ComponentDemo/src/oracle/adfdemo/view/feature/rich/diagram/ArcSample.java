package oracle.adfdemo.view.feature.rich.diagram;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ArcSample {
  private static final float SATURATION = .9f;
  private static final float BRIGHTNESS = .9f;
  private static final int DEFAULT_NODE_SIZE = 5;
  
  private CollectionModel m_nodes;
  private CollectionModel m_links;
  
    
  public ArcSample() {
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
    try {
      List<Map<String, String>> nodes = new ArrayList<Map<String, String>>();
      List<Map<String, String>> links = new ArrayList<Map<String, String>>();

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(getClass().getResourceAsStream("/oracle/adfdemo/view/feature/rich/diagram/data/miserables.xml"));
      doc.getDocumentElement ().normalize ();
      Element docElem = doc.getDocumentElement();      
      NodeList nl = docElem.getChildNodes();
      
      for (int i = 0; i < nl.getLength(); i++) {
        Node n = nl.item(i);
        if ("links".equals(n.getNodeName())) {
          NodeList linkElements = ((Element)n).getElementsByTagName("e");
          parseLinks(linkElements, links);
        }
        if ("nodes".equals(n.getNodeName())) {
          NodeList nodeElements = ((Element)n).getElementsByTagName("e");
          parseNodes(nodeElements, nodes);
        }
      }
      populateLinkDesc(links, nodes);
      calculateNodeSize(links, nodes);
      Collections.sort(nodes, new NodeComparator());
      m_nodes = ModelUtils.toCollectionModel(nodes);
      m_links = ModelUtils.toCollectionModel(links);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private void populateLinkDesc(List<Map<String, String>> links, List<Map<String, String>> nodes) {
    for (int i = 0; i < links.size(); i++) {
      Map<String, String> link = links.get(i);
      int start = Integer.parseInt(link.get("start"));      
      int end = Integer.parseInt(link.get("end"));
      link.put("desc", nodes.get(start).get("name") + " to " + nodes.get(end).get("name"));
    }
  }
  private void calculateNodeSize(List<Map<String, String>> links, List<Map<String, String>> nodes) {
    for (int i = 0; i < links.size(); i++) {
      Map<String, String> link = links.get(i);
      String start = link.get("start");
      String end = link.get("end");
      for (int j = 0; j < nodes.size(); j++) {
        Map<String, String> node = nodes.get(j);
        if (end.equals(node.get("id")) || start.equals(node.get("id"))) {
          int size = Integer.parseInt(node.get("size"));
          node.put("size", Integer.toString(++size));
        }
      }
    }  
  }
    
  private void parseNodes(NodeList nodeElements, List<Map<String, String>> nodes) {
    int size = nodeElements.getLength();
    for (int i = 0; i < size; i++) {
      Node nodeElem = nodeElements.item(i);
      if (nodeElem.getNodeType() == Node.ELEMENT_NODE) {
        Map<String, String> node = new HashMap<String, String>();
        nodes.add(node);      
        
        String name = getXMLNodeTextValue(nodeElem, "nodeName");
        String group = getXMLNodeTextValue(nodeElem, "group");
        node.put("id", String.valueOf(i));
        node.put("name", name);
        node.put("group", group);
        node.put("color", "#" + Integer.toHexString(Color.HSBtoRGB(Integer.parseInt(group)*1.0f/11, SATURATION, BRIGHTNESS) & 0xFFFFFF));
        node.put("size", Integer.toString(DEFAULT_NODE_SIZE));
      }
    }
  }
  
  private void parseLinks(NodeList linkElements, List<Map<String, String>> links) {
    int size = linkElements.getLength();
    for (int i = 0; i < size; i++) {
      Node linkElem = linkElements.item(i);
      if (linkElem.getNodeType() == Node.ELEMENT_NODE) {
        Map<String, String> link = new HashMap<String, String>();
        links.add(link);
        String source = getXMLNodeTextValue(linkElem, "source");
        String target = getXMLNodeTextValue(linkElem, "target");
        String value = getXMLNodeTextValue(linkElem, "value");
        link.put("start", source);
        link.put("end", target);
        link.put("width", value);
      }      
    }        
  }
  
  private String getXMLNodeTextValue(Node node, String tag) {
    NodeList nl = ((Element)node).getElementsByTagName(tag);
    Element el = (Element) nl.item(0);
    NodeList textList = el.getChildNodes();
    String text = (textList.item(0)).getNodeValue().trim();
    return text;
  }

  private class NodeComparator implements Comparator<Map<String, String>> {
    @Override
    public int compare(Map<String, String> n1, Map<String, String> n2) {
      int gr1 = Integer.parseInt(n1.get("group"));
      int gr2 = Integer.parseInt(n2.get("group"));
      if (gr1 == gr2) {
        int size1 = Integer.parseInt(n1.get("size"));
        int size2 = Integer.parseInt(n2.get("size"));
        return (size2 - size1);
      }
      else
        return (gr2 - gr1);
    }
  }
}
