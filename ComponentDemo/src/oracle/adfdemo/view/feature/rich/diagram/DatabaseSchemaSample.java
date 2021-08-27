package oracle.adfdemo.view.feature.rich.diagram;


    import java.util.ArrayList;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    import org.apache.myfaces.trinidad.model.CollectionModel;
    import org.apache.myfaces.trinidad.model.ModelUtils;
    import org.apache.myfaces.trinidad.model.RowKeySet;
    import org.apache.myfaces.trinidad.model.RowKeySetImpl;

    public class DatabaseSchemaSample {
      private CollectionModel m_nodes;
      private CollectionModel m_links;
      private HashMap<String, String> m_nodeLabels = new HashMap<String, String>();
      private RowKeySet m_disclosedNodes = new RowKeySetImpl();
      
      public DatabaseSchemaSample() {
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
          nodes.add(createNode("N0", null, "DB_1", "#4E80BC"));
          nodes.add(createNode("N1", null, "DB_2", "#4E80BC"));
          nodes.add(createNode("N2", null, "DB_1", "#4E80BC"));
          nodes.add(createNode("N3", null, "OBIEE_1", "#4E80BC"));
          
          nodes.add(createNode("N4", "N1", "Model_1", "#4E80BC"));
          nodes.add(createNode("N5", "N1", "Model_2", "#4E80BC"));
          nodes.add(createNode("N6", "N1", "Project_1", "#4E80BC"));
          nodes.add(createNode("N7", "N1", "Model_3", "#4E80BC"));
          nodes.add(createNode("N7_2", "N1", "Model_4", "#4E80BC"));
          
          nodes.add(createNode("N8", "N6", "Folder_1", "#4E80BC")); 
          
          nodes.add(createNode("N9", "N8", "Folder_2", "#4E80BC"));
          nodes.add(createNode("N10", "N8", "Mapping_4", "#4E80BC"));
          nodes.add(createNode("N11", "N8", "Mapping_5", "#4E80BC"));
          nodes.add(createNode("N12", "N8", "Mapping_6", "#4E80BC"));
          
          nodes.add(createNode("N13", "N9", "Mapping_1", "#4E80BC"));      
          nodes.add(createNode("N14", "N9", "Mapping_2", "#4E80BC"));      
          nodes.add(createNode("N15", "N9", "Mapping_3", "#4E80BC"));
            
          nodes.add(createNode("N16", "N13", "MapComponent_1a", "#4E80BC"));        
          nodes.add(createNode("N17", "N13", "MapComponent_1b", "#4E80BC"));        
          nodes.add(createNode("N18", "N13", "MapComponent_2", "#4E80BC"));        
          nodes.add(createNode("N19", "N13", "MapComponent_3", "#4E80BC"));
              
          nodes.add(createNode("N20", "N19", "ConnectorPoint_F", "#4E80BC"));
          
          nodes.add(createNode("N21", "N20", "Column_A", "#FFBF00"));          
          nodes.add(createNode("N22", "N20", "Column_B", "#00AF4F"));
          
          nodes.add(createNode("N23", "N7", "Datastore_1", "#4E80BC"));
          nodes.add(createNode("N24", "N7", "Datastore_2", "#4E80BC"));
          nodes.add(createNode("N25", "N7", "Datastore_3", "#4E80BC"));

          nodes.add(createNode("N26", "N23", "Column_1", "#FFBF00"));
          nodes.add(createNode("N27", "N23", "Column_2", "#00AF4F"));
          
          nodes.add(createNode("N28", "N2", "Schema_1", "#4E80BC"));          
          nodes.add(createNode("N29", "N2", "Schema_2", "#4E80BC"));
          
          nodes.add(createNode("N30", "N28", "Table_1", "#4E80BC"));
          nodes.add(createNode("N31", "N28", "Table_2", "#4E80BC"));
          nodes.add(createNode("N32", "N28", "Table_3", "#4E80BC"));

          nodes.add(createNode("N33", "N30", "Column_A", "#FFBF00"));
          nodes.add(createNode("N34", "N30", "Column_B", "#00AF4F"));
          
          nodes.add(createNode("N35", "N3", "PhysicalCatalog", "#4E80BC"));
          nodes.add(createNode("N36", "N3", "BusinessModel1", "#4E80BC"));
          nodes.add(createNode("N37", "N3", "CatalogFolder", "#4E80BC"));
          
          nodes.add(createNode("N38", "N35", "Database_1", "#4E80BC"));
            
          nodes.add(createNode("N39", "N38", "OBI_EE Table_1", "#4E80BC"));      
          nodes.add(createNode("N40", "N38", "OBI_EE Table_2", "#4E80BC"));      
          nodes.add(createNode("N41", "N38", "OBI_EE Table_3", "#4E80BC"));      
          nodes.add(createNode("N42", "N38", "OBI_EE Table_4", "#4E80BC"));      
          nodes.add(createNode("N43", "N38", "OBI_EE Table_5", "#4E80BC"));
          
          nodes.add(createNode("N44", "N36", "LogicalFolder1", "#4E80BC"));
            
          nodes.add(createNode("N45", "N44", "Mapping_1", "#4E80BC"));      
          nodes.add(createNode("N46", "N44", "Mapping_2", "#4E80BC"));      
          nodes.add(createNode("N47", "N44", "Mapping_3", "#4E80BC"));              
          nodes.add(createNode("N48", "N44", "LogicalTable_1", "#4E80BC"));      
          nodes.add(createNode("N49", "N44", "LogicalTable_2", "#4E80BC"));      
          nodes.add(createNode("N50", "N44", "LogicalTable_3", "#4E80BC")); 
          
          nodes.add(createNode("N51", "N37", "Report_1", "#4E80BC"));

          // Disclose containers
          m_disclosedNodes.add(1);
          m_disclosedNodes.add(2);
          m_disclosedNodes.add(3);
          m_disclosedNodes.add(6);
          m_disclosedNodes.add(7);
          m_disclosedNodes.add(9);
          m_disclosedNodes.add(10);
          m_disclosedNodes.add(14);
          m_disclosedNodes.add(20);
          m_disclosedNodes.add(21);
          m_disclosedNodes.add(24);
          m_disclosedNodes.add(29);
          m_disclosedNodes.add(31);
          m_disclosedNodes.add(36);
          m_disclosedNodes.add(37);
          m_disclosedNodes.add(38);
          m_disclosedNodes.add(39);
          m_disclosedNodes.add(45);
          
      
          List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
          links.add(createLink("L0", "N0", "N4", "#FF0000"));
          links.add(createLink("L1", "N0", "N5", "#FF0000"));
          links.add(createLink("L2", "N4", "N16", "#4E80BC"));
          links.add(createLink("L3", "N4", "N17", "#4E80BC"));
          links.add(createLink("L4", "N4", "N14", "#4E80BC"));
          links.add(createLink("L5", "N4", "N15", "#4E80BC"));      
          links.add(createLink("L6", "N5", "N10", "#4E80BC"));
          links.add(createLink("L7", "N5", "N11", "#4E80BC"));
          links.add(createLink("L8", "N5", "N12", "#4E80BC"));
          links.add(createLink("L9", "N16", "N18", "#4E80BC"));
          links.add(createLink("L10", "N17", "N18", "#4E80BC"));
          links.add(createLink("L11", "N18", "N20", "#4E80BC"));
          links.add(createLink("L12", "N20", "N23", "#4E80BC"));
          
          links.add(createLink("L13", "N13", "N24", "#4E80BC"));
          links.add(createLink("L14", "N14", "N25", "#4E80BC"));
          links.add(createLink("L15", "N10", "N7_2", "#4E80BC"));
          links.add(createLink("L16", "N11", "N7_2", "#4E80BC"));
          links.add(createLink("L17", "N12", "N7_2", "#4E80BC"));
          
          links.add(createLink("L18", "N23", "N30", "#FF0000"));
          links.add(createLink("L19", "N24", "N31", "#FF0000"));
          links.add(createLink("L20", "N25", "N32", "#FF0000"));
          links.add(createLink("L21", "N7_2", "N29", "#FF0000"));
          
          links.add(createLink("L22", "N30", "N39", "#FF0000"));
          links.add(createLink("L23", "N31", "N40", "#FF0000"));
          links.add(createLink("L24", "N32", "N41", "#FF0000"));
          links.add(createLink("L25", "N29", "N42", "#FF0000"));
          links.add(createLink("L26", "N29", "N43", "#FF0000"));
          
          links.add(createLink("L27", "N39", "N45", "#4E80BC"));
          links.add(createLink("L28", "N40", "N46", "#4E80BC"));
          links.add(createLink("L29", "N41", "N46", "#4E80BC"));
          links.add(createLink("L30", "N42", "N47", "#4E80BC"));
          links.add(createLink("L31", "N43", "N47", "#4E80BC"));
          
          links.add(createLink("L32", "N45", "N48", "#4E80BC"));
          links.add(createLink("L34", "N46", "N49", "#4E80BC"));
          links.add(createLink("L35", "N47", "N50", "#4E80BC"));
          
          links.add(createLink("L36", "N48", "N51", "#4E80BC"));
          links.add(createLink("L37", "N49", "N51", "#4E80BC"));
          links.add(createLink("L38", "N50", "N51", "#4E80BC"));
      
          m_nodes = ModelUtils.toCollectionModel(nodes);
          m_links = ModelUtils.toCollectionModel(links);
        }
      }
      
      private Map<String, Object> createNode(String nodeId, String containerId, String label, String color) {
        Map<String, Object> node = new HashMap<String, Object>();
        node.put("id", nodeId);
        node.put("container", containerId);
        node.put("label", label);
        node.put("color", color);
        m_nodeLabels.put(nodeId, label);
        return node;
      }

      private Map<String, Object> createLink(String linkId, String startNode, String endNode, String color) {
        Map<String, Object> link = new HashMap<String, Object>();
        link.put("id", linkId);
        link.put("start", startNode);
        link.put("end", endNode);
        link.put("color", color);
        link.put("description", "from " + m_nodeLabels.get(startNode) + " to " + m_nodeLabels.get(endNode));
        return link;
      }

      public RowKeySet getDisclosedNodes() {
        ensureModelExists();
        return m_disclosedNodes;
      }
    }


