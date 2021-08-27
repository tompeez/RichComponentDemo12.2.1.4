package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model;

import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramBaseNode.ATTR;
import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramBaseNode.DROPNODESTATE;

public class RuleDiagramDropNode extends RuleDiagramBaseNode {
    public RuleDiagramDropNode(RuleDiagramModel model, RuleDiagramNode partnerNode, String orientation, RuleConjunctionType type) {
        super(model);
        setRuleNodeType(RuleNodeType.DROPTARGET);  
        setAttributeInternal(ATTR.DROPCONJUNCTIONTYPE, type);
        setAttributeInternal(ATTR.OWNERNODE, partnerNode);
        setAttributeInternal(ATTR.DROPNODEORIENTATION, orientation);
    }
    
    protected void setDropNodeState(DROPNODESTATE state){
        setAttributeInternal(ATTR.DROPNODESTATE, state);
    }
    
    protected DROPNODESTATE getDropNodeState(){
        return (DROPNODESTATE)getAttributeInternal(ATTR.DROPNODESTATE, false);
    }
    
    @Override
    public String getDropNodePosition() {
        return (String)getAttributeInternal(ATTR.DROPNODEORIENTATION);
    }
    
    @Override
    public String getDropNodeType(){
        String typeString = null;
        if (getDropNodeState().equals(DROPNODESTATE.HANGING)){
            typeString = "H";
        }
        else if (getDropNodeState().equals(DROPNODESTATE.CONCRETE)){
            typeString = "C";
        }
        else{
            typeString = "S";
        }
        
        
        return typeString + ((RuleConjunctionType)getAttributeInternal(ATTR.DROPCONJUNCTIONTYPE)).toString();
    }

    
    @Override
    public String toString(){
        StringBuilder output = new StringBuilder(super.toString());
        output.append('(');
        output.append(this.getDropConjuctionType());
        output.append(')');
        return output.toString();
    }
}
