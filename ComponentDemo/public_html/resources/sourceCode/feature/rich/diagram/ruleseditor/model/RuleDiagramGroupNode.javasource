package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java.util.ListIterator;
import java.util.logging.Level;

import oracle.adf.share.logging.ADFLogger;

import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramBaseNode.ATTR;
import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramBaseNode.DROPNODESTATE;

public class RuleDiagramGroupNode extends RuleDiagramBaseNode {
    private static ADFLogger _logger = ADFLogger.createADFLogger(RuleDiagramGroupNode.class);
    private List<RuleDiagramNode> _children;


    public RuleDiagramGroupNode(RuleDiagramModel model, RuleConjunctionType conjunction) {
        super(model);
        setRuleNodeType(RuleNodeType.GROUP);
        setGroupConjunctionType(conjunction);
        generateDropNodes();
    }

    public List<RuleDiagramNode> getChildDiagramNodes() {
        return _children;
    }

    public void addDiagramNode(RuleDiagramNode node, RuleDiagramNode peerNode, boolean addBefore) {
        if (_children == null) {
            _children = new ArrayList<RuleDiagramNode>(3);
        }

        StringBuilder configMsg = new StringBuilder("Group: ");
        configMsg.append(this.getRuleNodeId());
        configMsg.append(" Size ");
        configMsg.append(_children.size());
        if (_children.size() > 0) {
            configMsg.append(". Existing nodes [");
            for (RuleDiagramNode pnode : _children) {
                configMsg.append('{');
                configMsg.append(pnode.toString());
                configMsg.append('}');
            }
            configMsg.append("]");
        }
        configMsg.append(". Inserting Node:");
        configMsg.append(node.toString());
        if (peerNode != null) {
            configMsg.append(", with peer:");
            configMsg.append(peerNode.toString());
        }


        //check to see if this node is already allocated to another group. If so we must remove it from that group and
        //add it to this one, if not, just add it to this
        Integer oldGrpId = node.getRuleGroupId();
        if (oldGrpId != null && oldGrpId.intValue() > 0) {
            RuleDiagramGroupNode oldGrp = (RuleDiagramGroupNode) getModel().findNodeById(oldGrpId);
            oldGrp.deleteDiagramNode(node);
        }
        node.setRuleGroupId(getRuleNodeId());


        int addIndex = -1;
        // Work out where to insert.
        // If the peer is actually this group then insert at the start
        // if the peer is a child then insert before or after that as required
        // if no peer is supplied, add to the end.
        if (peerNode != null && peerNode.equals(this)) {
            addIndex = 0;
        } else {
            addIndex = _children.size();
            if (peerNode != null) {
                if (_children.size() > 0) {
                    for (int i = 0; i < _children.size(); i++) {
                        if (_children.get(i).equals(peerNode)) {
                            addIndex = i;
                            break;
                        }
                    }
                    if (!addBefore) {
                        addIndex++;
                    }
                }
            }
        }

        _children.add(addIndex, node);
    }

    public void deleteDiagramNode(RuleDiagramNode node) {
        ListIterator<RuleDiagramNode> iter = _children.listIterator();
        while (iter.hasNext()) {
            RuleDiagramNode child = iter.next();
            if (child.equals(node)) {
                iter.remove();
                break;
            }
        }
    }

    protected RuleConjunctionType getRuleGroupConjuctionTypeInternal() {
        return (RuleConjunctionType) getAttributeInternal(ATTR.GROUPCONJUNCTIONTYPE);
    }

    /**
     * Within a group we have a set of real nodes which represent conditions or further 
     * groups. Each of those has up to 4 drop nodes which also have to be linked. 
     * When two real nodes are linked it is via one of these drop nodes
     */
    protected void addImpliedLinksAndDropNodes() {

        //build the links afresh based on the conjunction defined
        RuleDiagramNode previousNode = null;
        RuleConjunctionType conjType = getRuleGroupConjuctionTypeInternal();
        int realNodeCount = getChildDiagramNodes().size();
        for (int i = 0; i < realNodeCount; i++) {
            boolean isFirst = (i==0);
            boolean isLast = (i==(realNodeCount-1));
            
            RuleDiagramNode node = getChildDiagramNodes().get(i);
            
            //Add this node to the display set
            getModel().getDisplayDiagramNodes().add(node);
            
            //Do we have a link to create between the dropnode of the last real node in this group and this one?
            if (previousNode != null){
                RuleDiagramLink link =
                    new RuleDiagramLink(getModel(), previousNode, node, conjType);
                getModel().getDisplayDiagramLinks().add(link);
            }
            
            //Process the associated drop nodes from west to south. Each of these needs to be evaluated to determine if
            //it needs to be diaplayed and what the style of link should be 
            realizeDropNode(node.getDropTargetW(),node,1,conjType,isFirst,isLast);
                
            realizeDropNode(node.getDropTargetN(),node,2,conjType,isFirst,isLast);
            
            //The next two positions may have been rendered concrete
            if (realizeDropNode(node.getDropTargetE(),node,3,conjType,isFirst,isLast)){
                previousNode = node.getDropTargetE();
            }
            if (realizeDropNode(node.getDropTargetS(),node,4,conjType,isFirst,isLast)){
                previousNode = node.getDropTargetS();
            }
            
        }

    }
    
    private boolean realizeDropNode(RuleDiagramDropNode dropNode, RuleDiagramNode realNode, int dropSlot, RuleConjunctionType type, boolean firstGroupNode, boolean lastGroupNode){
        boolean concrete = false;
        
        if (!firstGroupNode){
            if ((type.equals(RuleConjunctionType.OR) && dropSlot == 1)||(type.equals(RuleConjunctionType.AND) && dropSlot == 2)){
                //Don't create a link or add to the model 
                dropNode.setDropNodeState(DROPNODESTATE.SUPRESSED);
                return concrete;
            } 
        }
        if (!lastGroupNode){
            if ((type.equals(RuleConjunctionType.OR) && dropSlot == 3)||(type.equals(RuleConjunctionType.AND) && dropSlot == 4)){
                //This is a dropnode that links two real nodes
                dropNode.setDropNodeState(DROPNODESTATE.CONCRETE);
                concrete = true;
                RuleDiagramLink link = new RuleDiagramLink(getModel(),realNode,dropNode,type);
                getModel().getDisplayDiagramLinks().add(link);
                getModel().getDisplayDiagramNodes().add(dropNode);
                return concrete;
            }
        }
        //This is therefore just a standard drop target 
        dropNode.setDropNodeState(DROPNODESTATE.HANGING);
        RuleDiagramLink link = new RuleDiagramLink(getModel(),realNode,dropNode);
        getModel().getDisplayDiagramLinks().add(link);
        getModel().getDisplayDiagramNodes().add(dropNode);
        
        return concrete;
    }

    public String getFormattedNodeExpression() {
        StringBuilder b = new StringBuilder("(");
        Iterator childIter = getChildDiagramNodes().iterator();
        while (childIter.hasNext()) {
            RuleDiagramNode child = (RuleDiagramNode) childIter.next();
            if (child.getRuleNodeType().equals("GROUP") || child.getRuleNodeType().equals("CONDITION")) {
                if (b.length() > 1) {
                    b.append(' ');
                    b.append(this.getRuleGroupConjuctionType());
                    b.append(' ');
                }
            }
            b.append(child.getFormattedNodeExpression());
        }

        b.append(')');
        return b.toString();
    }


    @Override
    public String toString() {
        return super.toString() + "[" + this.getRuleGroupConjuctionType() + "]";
    }


}
