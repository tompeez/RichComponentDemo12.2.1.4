package oracle.adfdemo.view.feature.rich.diagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class ContainerPaddingSample {
  private CollectionModel m_nodes;
  private CollectionModel m_links;
  private RowKeySet m_disclosedNodes = new RowKeySetImpl();
  
  public ContainerPaddingSample() {
    super();
  }
  
  public CollectionModel getNodes() {
    ensureModelExists();
    return m_nodes;
  }
  
  public CollectionModel getLinks() {
    ensureModelExists();
    return m_links;
  }
  
  private void ensureModelExists() {
    if (m_nodes == null || m_links == null) {
      List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
      nodes.add(createNode("N0", null, "Database1", "#C0E0FF", "/resources/images/diagram/emicons/database24.png"));
      nodes.add(createNode("N1", null, "Database2", "#C0E0FF", "/resources/images/diagram/emicons/database24.png"));
      
      nodes.add(createNode("N2", "N0", "Table1", "#C0E0FF", "/resources/images/diagram/emicons/appServer24.png"));
      nodes.add(createNode("N3", "N0", "Table2", "#C0E0FF", "/resources/images/diagram/emicons/appServer24.png"));
      
      nodes.add(createNode("N4", "N1", "Table3", "#C0E0FF", "/resources/images/diagram/emicons/appServer24.png"));
      nodes.add(createNode("N5", "N1", "Table4", "#C0E0FF", "/resources/images/diagram/emicons/appServer24.png"));
      
      nodes.add(createNode("N6", "N2", "Column1", "#C0E0FF", "/resources/images/diagram/emicons/httpServer24.png"));
      nodes.add(createNode("N7", "N2", "Column2", "#C0E0FF", "/resources/images/diagram/emicons/httpServer24.png"));
      
      nodes.add(createNode("N8", "N3", "Column3", "#C0E0FF", "/resources/images/diagram/emicons/httpServer24.png"));
      nodes.add(createNode("N9", "N3", "Column4", "#C0E0FF", "/resources/images/diagram/emicons/httpServer24.png"));
      
      nodes.add(createNode("N10", "N6", "Row1", "#C0E0FF", "/resources/images/diagram/emicons/listener24.png"));
      nodes.add(createNode("N11", "N6", "Row2", "#C0E0FF", "/resources/images/diagram/emicons/listener24.png"));
      nodes.add(createNode("N12", "N6", "Row3", "#C0E0FF", "/resources/images/diagram/emicons/listener24.png"));

      // Disclose containers
      m_disclosedNodes.add(0);
      m_disclosedNodes.add(1);
      m_disclosedNodes.add(2);
      m_disclosedNodes.add(3);
      m_disclosedNodes.add(6);
      
  
      List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
  
      m_nodes = ModelUtils.toCollectionModel(nodes);
      m_links = ModelUtils.toCollectionModel(links);
    }
  }
  
  private Map<String, Object> createNode(String nodeId, String containerId, String label, String color, String icon) {
    Map<String, Object> node = new HashMap<String, Object>();
    node.put("id", nodeId);
    node.put("container", containerId);
    node.put("label", label);
    node.put("color", color);
    node.put("icon", icon);
    return node;
  }

  private Map<String, Object> createLink(String linkId, String startNode, String endNode, String color) {
    Map<String, Object> link = new HashMap<String, Object>();
    link.put("id", linkId);
    link.put("start", startNode);
    link.put("end", endNode);
    link.put("color", color);
    return link;
  }

  public RowKeySet getDisclosedNodes() {
    ensureModelExists();
    return m_disclosedNodes;
  }
}
