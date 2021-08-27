package oracle.adfdemo.view.feature.rich.diagram.tutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class TutorialBikeSample {
  
  private List<Map<String, Object>> _nodes;
  private List<Map<String, Object>> _links;
  private List<Map<String, Object>> _containerNodes;
  private List<Map<String, Object>> _containerLinks;
  private RowKeySet disclosedNodes = new RowKeySetImpl();
  
  public TutorialBikeSample() {
    populateData();
  }
  
  public CollectionModel getNodes() {
    return ModelUtils.toCollectionModel(_nodes);
  }
  
  public CollectionModel getLinks() {
    return ModelUtils.toCollectionModel(_links);
  }
  
  public CollectionModel getContainerNodes() {
    return ModelUtils.toCollectionModel(_containerNodes);
  }
  
  public CollectionModel getContainerLinks() {
    return ModelUtils.toCollectionModel(_containerLinks);
  }  
  
  public RowKeySet getDisclosedNodes() {
    return disclosedNodes;
  }  
  
  private void populateData() {
    _nodes = populateNodes();
    _links = populateLinks();
    _containerNodes = populateContainerNodes();
    _containerLinks = populateContainerLinks();
    populateLinkDesc(_links, _nodes);
    populateLinkDesc(_containerLinks, _containerNodes);
  }
  
  private List<Map<String, Object>> populateNodes() {
    List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
    nodes.add(createNode("N0", null, "Start", "Start", 0.00f));
    nodes.add(createNode("N1", null, "Parts", "Handle Bar", 69.98f));
    nodes.add(createNode("N2", null, "Parts", "Frame", 999.98f));
    nodes.add(createNode("N3", null, "Parts", "Gears", 499.98f));
    nodes.add(createNode("N4", null, "Parts", "Wheels", 249.98f));  
    nodes.add(createNode("N5", null, "Parts", "Tires", 49.45f));
    nodes.add(createNode("N6", null, "End", "See you later ...", 0.00f));
    return nodes;    
  }
    
  private List<Map<String, Object>> populateLinks() {
    List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
    links.add(createLink("L0", "N0", "N1", null, "#D2691E"));
    links.add(createLink("L1", "N1", "N2", null, "#3CB371"));
    links.add(createLink("L2", "N2", "N3", null, "#3CB371"));
    links.add(createLink("L3", "N3", "N4", null, "#3CB371"));
    links.add(createLink("L4", "N4", "N5", null, "#3CB371"));
    links.add(createLink("L5", "N5", "N6", null, "#D2691E"));
    return links;
  }
  
  private List<Map<String, Object>> populateContainerNodes() {
    List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
                                                                            
    nodes.add(createNode("N0", null, "Complete", "Wheel", 249.98f));
    nodes.add(createNode("N1", "N0", "Complete", "Brake", 0.00f));
    nodes.add(createNode("N2", "N0", "Complete", "Sprocket/Cog",  0.00f));

    nodes.add(createNode("N3", null, "Complete", "DriveTrain", 0.00f));
    nodes.add(createNode("N4", "N3", "Complete", "Front derailer", 0.00f));
    nodes.add(createNode("N5", "N3", "Complete", "Rear derailer", 0.00f));
    
    nodes.add(createNode("N6", null, "NoLinks", "Handle bar",  69.98f));
    nodes.add(createNode("N7", "N6", "NoLinks", "Brake lever", 0.00f));
    nodes.add(createNode("N8", "N7", "NoLinks", "Brake cable", 0.00f));
    nodes.add(createNode("N9", "N6", "NoLinks", "Shifter", 0.00f));
    nodes.add(createNode("N10", "N9", "NoLinks", "Shifter cable", 0.00f));

    disclosedNodes.add(0);
    disclosedNodes.add(3);
    disclosedNodes.add(6);
    disclosedNodes.add(7);      
    disclosedNodes.add(9);    
    return nodes;
  }
  
  private List<Map<String, Object>> populateContainerLinks() {
    List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
    links.add(createLink("L0","N8", "N1", "Top", "#3CB371"));
    links.add(createLink("L1", "N10", "N5", "Side", "#3CB371"));
    links.add(createLink("L2", "N5", "N2", "Side", "#3CB371"));        
    return links;
  }  
  
  
  private Map<String, Object> createNode(String nodeId, String containerId, String nodeType, String nodeLabel, float nodePrice) {
    Map<String, Object> node = new HashMap<String, Object>();
    node.put("id", nodeId);
    if (containerId != null)
      node.put("container", containerId);    
    node.put("type", nodeType);
    node.put("label", nodeLabel);
    node.put("price", nodePrice);
    return node;
  }
  
  private Map<String, Object> createLink(String linkId, String startNode, String endNode, String linkType, String linkColor) {
    Map<String, Object> link = new HashMap<String, Object>();
    link.put("id", linkId);
    link.put("start", startNode);
    link.put("end", endNode);
    if (linkType != null)
      link.put("type", linkType);
    link.put("color", linkColor);
    return link;
  }
  
  private void populateLinkDesc(List<Map<String, Object>> links, List<Map<String, Object>> nodes) {
    for (int i = 0; i < links.size(); i++) {
      Map<String, Object> link = links.get(i);
      String startId = (String)link.get("start");
      String endId = (String)link.get("end");
      int start = Integer.parseInt(startId.substring(1));      
      int end = Integer.parseInt(endId.substring(1));
      link.put("desc", nodes.get(start).get("label") + " to " + nodes.get(end).get("label"));
    }
  }  
}
