package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model;

import java.util.List;
import java.util.Map;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;


public interface RuleDiagramModel {
    public List<RuleDiagramLink> getDisplayDiagramLinks();  
    public List<RuleDiagramNode> getDisplayDiagramNodes();  
    public CollectionModel getDiagramNodesModel();
    public CollectionModel getDiagramLinksModel(); 
    public RowKeySet getConditionGroups();
    public RowKeySet getNodeSelections();
    public void setNodeSelections(RowKeySet selections); 
    
    public RuleDiagramNode findNodeById(Integer nodeId);
    public void addNode(RuleDiagramNode node);
    public void addNode(RuleDiagramNode node,RuleDiagramNode peerNode,RuleConjunctionType conjunction,boolean addBefore);
    public void removeNode(RuleDiagramNode node);
    
    public List<String> getFormattedRules();
    
    // For model builders
    public List<RuleDiagramNode> getRawNodeList();
    public Map<Integer,Integer> getNodeIdMap();
    public void removeAnyAssociatedLinks(RuleDiagramNode node);
    
    public Boolean isValidDropNode(Integer nodeId);
    public void addDroppedCondition(Integer dropNodeId, String fact, String factValueType, Object value, String expression,  String conjunction, boolean beforeTarget);
    
}
