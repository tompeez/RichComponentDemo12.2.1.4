package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model;

import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramBaseNode.ATTR;

public class RuleDiagramConditionNode extends RuleDiagramBaseNode {
    public RuleDiagramConditionNode(RuleDiagramModel ruleDiagramModel) {
        super(ruleDiagramModel);
        setRuleNodeType(RuleNodeType.CONDITION);
        generateDropNodes();
    }
    
    public RuleDiagramConditionNode(RuleDiagramModel ruleDiagramModel,String fact,RuleExpressionType expressionType,RuleValueType valueType,Object value) {
        super(ruleDiagramModel);
        setRuleNodeType(RuleNodeType.CONDITION);
        setRuleFact(fact);
        setRuleExpressionType(expressionType);
        setRuleValueType(valueType);
        setRuleValue(value);  
        generateDropNodes();

    }
    
    
    @Override
    public String getFormattedNodeExpression(){
        RuleExpressionType exprtype = (RuleExpressionType)getAttributeInternal(ATTR.EXPRESSION);
        StringBuilder b = new StringBuilder(getRuleFact());
        b.append(' ');
        b.append(exprtype.getValue());
        b.append(' ');
        boolean encase = false;
        if (getRuleValueType().equals("STRING")){
            encase = true;
            b.append('"');
        }
        b.append(exprtype.getPrefix());
        if (exprtype.showValue()){
            b.append(getRuleValue());
        }
        b.append(exprtype.getSuffix());
        if (encase){
            b.append('"');
        }
        return b.toString();
    }
    
    @Override
    public String toString(){
        StringBuilder output = new StringBuilder(super.toString());
        output.append('(');
        output.append(this.getAttributeInternal(ATTR.FACT));
        output.append(')');
        return output.toString();
    }
    
}
