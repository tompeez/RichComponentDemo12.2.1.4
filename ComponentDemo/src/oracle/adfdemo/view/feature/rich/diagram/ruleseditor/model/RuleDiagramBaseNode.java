package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model;

import java.util.HashMap;
import java.util.Map;

import oracle.adf.share.logging.ADFLogger;

import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramModel;
import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramNode;

public abstract class RuleDiagramBaseNode implements RuleDiagramNode {
    private static ADFLogger _logger = ADFLogger.createADFLogger(RuleDiagramBaseNode.class);
    private static int _nodeHWM = -1;
    private static final String[] COMPASS = {"W","N","E","S"};

    protected enum ATTR {
        NODEID,
        NODETYPE,
        GROUPID,
        FACT,
        EXPRESSION,
        VALUE,
        VALUETYPE,
        GROUPCONJUNCTIONTYPE,
        DROPNODEW,
        DROPNODEN,
        DROPNODEE,
        DROPNODES,
        OWNERNODE,
        DROPNODESTATE,
        DROPCONJUNCTIONTYPE,
        DROPNODEORIENTATION
    }

    protected enum DROPNODESTATE {
        UNDETERMINED,
        HANGING,
        CONCRETE,
        SUPRESSED
    }

    private Map<ATTR, Object> _attributes;
    private RuleDiagramModel _model;

    protected RuleDiagramModel getModel() {
        return _model;
    }

    public RuleDiagramBaseNode(RuleDiagramModel model) {
        this.setAttributes(new HashMap<ATTR, Object>());
        this.setAttributeInternal(ATTR.NODEID, new Integer(generateUniqueId()));
        _model = model;
    }

    @Override
    public boolean equals(Object obj) {
        boolean match = false;
        if (obj instanceof RuleDiagramNode) {
            match = (((RuleDiagramNode) obj).getRuleNodeId().compareTo(getRuleNodeId()) == 0);
        }
        return match;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("Diagram Node ID: ");
        output.append(this.getAttributeInternal(ATTR.NODEID));
        output.append(", Type:");
        output.append(this.getAttributeInternal(ATTR.NODETYPE));
        return output.toString();
    }

    @Override
    public RuleExpressionType getRuleExpression() {
        return (RuleExpressionType) getAttributeInternal(ATTR.EXPRESSION, false);
    }
    
    @Override
    public String getRuleExpressionLabel() {
        RuleExpressionType exprType = getRuleExpression();
        if (exprType != null) {
            return exprType.getLabel();
        } else {
            return null;
        }
    }

    @Override
    public String getRuleFact() {
        return (String) getAttributeInternal(ATTR.FACT, false);
    }

    @Override
    public Integer getRuleGroupId() {
        // Group NodeId may be null
        return (Integer) getAttributeInternal(ATTR.GROUPID, false);
    }

    @Override
    public Integer getRuleNodeId() {
        return (Integer) getAttributeInternal(ATTR.NODEID);
    }

    @Override
    public String getRuleNodeType() {
        RuleNodeType type = (RuleNodeType) getAttributeInternal(ATTR.NODETYPE);
        if (type != null) {
            return type.toString();
        }
        return null;
    }


    @Override
    public String getRuleValue() {
        Object value = getAttributeInternal(ATTR.VALUE, false);
        if (value != null) {
            return value.toString();
        } else {
            return null;
        }
    }

    @Override
    public String getRuleValueType() {
        RuleValueType type = (RuleValueType) getAttributeInternal(ATTR.VALUETYPE, false);
        if (type != null) {
            return type.toString();
        }
        return null;
    }

    @Override
    public String getRuleGroupConjuctionType() {
        RuleConjunctionType type = (RuleConjunctionType) getAttributeInternal(ATTR.GROUPCONJUNCTIONTYPE, false);
        if (type != null) {
            return type.toString();
        }
        return null;
    }

    @Override
    public String getDropConjuctionType() {
        RuleConjunctionType type = (RuleConjunctionType) getAttributeInternal(ATTR.DROPCONJUNCTIONTYPE, false);
        if (type != null) {
            return type.toString();
        }
        return null;
    }

    @Override
    public void setRuleExpression(String value) {
        setRuleExpressionType(RuleExpressionType.valueOf(value.trim().toUpperCase()));
    }

    @Override
    public void setRuleFact(String value) {
        setAttributeInternal(ATTR.FACT, value);
    }

    @Override
    public void setRuleGroupId(Integer groupNodeId) {
        setAttributeInternal(ATTR.GROUPID, groupNodeId);

        if (!(this instanceof RuleDiagramDropNode)) {
            //Set the GroupIds of the Drop nodes as well
            for (int i = 0; i < 4; i++) {
                RuleDiagramNode dropNode = (RuleDiagramNode) getAttributeInternal(ATTR.valueOf("DROPNODE" + COMPASS[i]), false);
                dropNode.setRuleGroupId(groupNodeId);
            }
        }
    }

    @Override
    public final void setRuleNodeId(Integer nodeId) {
        _logger.severe("Ignoring attempt to update node ID which cannot be mutated");
    }

    @Override
    public void setRuleValue(Object value) {
        setAttributeInternal(ATTR.VALUE, value);
    }

    @Override
    public final void setRuleValueType(String type) {
        setRuleValueType(RuleValueType.valueOf(type.trim().toUpperCase()));
    }

    @Override
    public final void setRuleNodeType(String type) {
        setRuleNodeType(RuleNodeType.valueOf(type.trim().toUpperCase()));
    }


    @Override
    public void setRuleGroupConjuctionType(String type) {
        setGroupConjunctionType(RuleConjunctionType.valueOf(type.trim().toUpperCase()));
    }

    @Override
    public String getFormattedNodeExpression() {
        return "";
    }

    @Override
    public RuleDiagramDropNode getDropTargetW() {
        return (RuleDiagramDropNode) getAttributeInternal(ATTR.DROPNODEW, true);
    }

    @Override
    public RuleDiagramDropNode getDropTargetN() {
        return (RuleDiagramDropNode) getAttributeInternal(ATTR.DROPNODEN, true);
    }

    @Override
    public RuleDiagramDropNode getDropTargetE() {
        return (RuleDiagramDropNode) getAttributeInternal(ATTR.DROPNODEE, true);
    }

    @Override
    public RuleDiagramDropNode getDropTargetS() {
        return (RuleDiagramDropNode) getAttributeInternal(ATTR.DROPNODES, true);
    }


    @Override
    public RuleDiagramNode getDropOwnerNode() {
        return (RuleDiagramNode) getAttributeInternal(ATTR.OWNERNODE, true);
    }

    protected void setRuleExpressionType(RuleExpressionType type) {
        setAttributeInternal(ATTR.EXPRESSION, type);
    }

    protected void setRuleValueType(RuleValueType type) {
        setAttributeInternal(ATTR.VALUETYPE, type);
    }

    protected void setRuleNodeType(RuleNodeType type) {
        setAttributeInternal(ATTR.NODETYPE, type);
    }

    protected void setGroupConjunctionType(RuleConjunctionType type) {
        setAttributeInternal(ATTR.GROUPCONJUNCTIONTYPE, type);
    }


    protected Object getAttributeInternal(ATTR attr) {
        return getAttributeInternal(attr, true);
    }

    protected Object getAttributeInternal(ATTR attr, boolean checked) {
        if (getAttributes().containsKey(attr)) {
            return getAttributes().get(attr);
        } else {
            if (checked) {
                _logger.severe("Attribute " + attr + " not found");
            }
            return null;
        }
    }

    protected void setAttributeInternal(ATTR attr, Object value) {
        getAttributes().put(attr, value);
    }


    private void setAttributes(Map<RuleDiagramBaseNode.ATTR, Object> attributes) {
        _attributes = attributes;
    }

    private Map<RuleDiagramBaseNode.ATTR, Object> getAttributes() {
        return _attributes;
    }

    /*
     * OK this is only a DEMO so we won't need to overflow to a long
     * @return
     */
    private int generateUniqueId() {
        return (++_nodeHWM);
    }

    /**
     * Build the drop nodes that will surround each real node.  There are 4 in total to allow the user to drop
     * either an OR or and AND before or after the node
     * These nodes are held internally to the main node that needs the drop links and actually linked up during the
     * final collectionmodel assembly stage. Not all of these nodes will be exposed all of the time
     */
    protected void generateDropNodes() {
        //Create the drop nodes and relate it to this one
        //Nodes are added in a clockwise order from 9am OR-AND-OR-AND
        for (int i = 0; i < 4; i++) {
            RuleConjunctionType type = (i % 2 == 1) ? RuleConjunctionType.AND : RuleConjunctionType.OR;
            ATTR storeAs = ATTR.valueOf("DROPNODE" + COMPASS[i]);
            setAttributeInternal(storeAs, new RuleDiagramDropNode(getModel(), this, COMPASS[i], type));
        }
    }
    
    @Override
    public String getDropNodePosition() {
        return null;
    }
    
    @Override
    public String getDropNodeType(){
        return null;
    }

}
