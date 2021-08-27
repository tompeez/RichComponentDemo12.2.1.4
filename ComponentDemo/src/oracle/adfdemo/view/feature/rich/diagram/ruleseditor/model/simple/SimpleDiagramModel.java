package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.simple;


import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleConjunctionType;
import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramBaseModel;
import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramConditionNode;
import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramModel;
import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleExpressionType;
import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleValueType;

/**
 * Basic sample data model used for the demo 
 */
public class SimpleDiagramModel extends RuleDiagramBaseModel implements RuleDiagramModel {
    public SimpleDiagramModel() {
        super();
        initData();
    }
    
    private void initData(){
        RuleDiagramConditionNode node1 = new RuleDiagramConditionNode(this, "OrderQuantity", RuleExpressionType.LT, RuleValueType.INTEGER, 10);
        addNode(node1); 
        RuleDiagramConditionNode node2 =new RuleDiagramConditionNode(this, "CustomerClass", RuleExpressionType.EQ, RuleValueType.STRING,"Gold");
        addNode(node2);
        RuleDiagramConditionNode node3 =new RuleDiagramConditionNode(this, "CustomerClass", RuleExpressionType.EQ, RuleValueType.STRING,"Silver");
        addNode(node3,node2,RuleConjunctionType.OR,false);   
        
    }


}
