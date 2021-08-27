package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model;

/**
 * Interface defining a basic node for display in the diagram
 * Depending on the node type, some of these attributes will be 
 * no-ops but the interface has to define all the attributes as 
 * the diagram nodes need to all be based on the same data shape
 * Attributes such as the types are exposed as String as this is how the UI components will 
 * need them. However, enumerations are provided to help validate the selections
 */
public interface RuleDiagramNode {
    public Integer getRuleNodeId();
    public void setRuleNodeId(Integer nodeId);
    public String getRuleNodeType();
    public void setRuleNodeType(String type);
    public Integer getRuleGroupId();
    public void setRuleGroupId(Integer groupNodeId);
    public String getRuleFact();
    public void setRuleFact(String value); 
    public String getRuleExpressionLabel();
    public RuleExpressionType getRuleExpression();
    public void setRuleExpression(String value);  
    public Object getRuleValue();
    public void setRuleValue(Object value);  
    public String getRuleValueType();
    public void setRuleValueType(String type);
    public String getRuleGroupConjuctionType();
    public void setRuleGroupConjuctionType(String type);
    public String getDropConjuctionType();
    public String getFormattedNodeExpression();
    public RuleDiagramNode getDropOwnerNode();
    public RuleDiagramDropNode getDropTargetW();
    public RuleDiagramDropNode getDropTargetN();
    public RuleDiagramDropNode getDropTargetE();
    public RuleDiagramDropNode getDropTargetS();
    public String getDropNodePosition();
    public String getDropNodeType();
}
