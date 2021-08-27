package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import oracle.adf.share.logging.ADFLogger;

import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramBaseNode.DROPNODESTATE;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.model.SortableModel;

public abstract class RuleDiagramBaseModel implements RuleDiagramModel {
    private static ADFLogger _logger = ADFLogger.createADFLogger(RuleDiagramBaseModel.class);
    
    private List<RuleDiagramNode> _diagramNodes;
    private List<RuleDiagramNode> _diagramNodesWithDrops;
    private Map<Integer, Integer> _diagramNodesById;
    private Map<Integer, Integer> _externalIdLookupIndex;
    private List<RuleDiagramLink> _diagramLinks;
    private CollectionModel _diagramLinksCM;
    private CollectionModel _diagramNodesAllCM;
    private RowKeySet _conditionGroups;
    private RowKeySet _nodeSelections;
    private boolean _dirty = false;
    private int _groupCount = 0;
    private static RuleConjunctionType TOP_LEVEL_CONJUNCTION = RuleConjunctionType.AND;

    public RuleDiagramBaseModel() {
        initModelStorage();
    }

    private void initModelStorage() {
        _diagramNodes = new ArrayList<RuleDiagramNode>(10);
        _diagramNodesById = new HashMap<Integer, Integer>(10);
    }


    @Override
    public void addNode(RuleDiagramNode node) {
        addNode(node, null, null,false);
    }

    @Override
    public void addNode(RuleDiagramNode node, RuleDiagramNode peerNode, RuleConjunctionType conjunction,boolean addBefore) {
        
        if (node instanceof RuleDiagramGroupNode){
            incrementGroupCount();
        }
        else if (node instanceof RuleDiagramConditionNode) {
            //A condition node must always be in a group.
            RuleDiagramGroupNode containerGrp = null;
            if (peerNode == null) {
                //So create one as an OR group
                containerGrp = new RuleDiagramGroupNode(this,RuleConjunctionType.OR);
                this.addNode(containerGrp,null,null,addBefore);
            } else {
                containerGrp = (RuleDiagramGroupNode) findNodeById(peerNode.getRuleGroupId());
                if (containerGrp != null) {
                    int containeeCount = containerGrp.getChildDiagramNodes().size();
                    if (!containerGrp.getRuleGroupConjuctionTypeInternal().equals(conjunction)){
                        if (containeeCount <= 1){
                            //It's OK to change the conjunction type of the group in this case
                            containerGrp.setGroupConjunctionType(conjunction);
                        }
                        else {
                            //different conjunction type so we need a sub-group
                            RuleDiagramGroupNode containerparentGrp = containerGrp;
                            containerGrp = new RuleDiagramGroupNode(this,conjunction);
                            //Move the peer to the subGroup
                            //1. Work out the old insertion point of the node we're about to move
                            //   if this returns null it imples that the node was first in the grouo
                            RuleDiagramNode peersPeer = deducePeer(containerparentGrp,peerNode);
                            if (peersPeer == null){
                                peersPeer = containerparentGrp;
                            }
                            //2. Move the peer to the new group which will also clean up the 
                            //   old group and reset the peer nodes groupId
                            containerGrp.addDiagramNode(peerNode, null, false);
                            //3. Add in the new group into the same slot as used to be occupied by the peer 
                            containerparentGrp.addDiagramNode(containerGrp,peersPeer,false);
                            addNode(containerGrp,peersPeer,null,false);
                        }         
                    }
                }
                else {
                    containerGrp = new RuleDiagramGroupNode(this,RuleConjunctionType.OR);
                    this.addNode(containerGrp);
                }
            }
            containerGrp.addDiagramNode(node,peerNode,addBefore);
        }
        registerNode(node,peerNode,addBefore);
    }
    
    private void registerNode(RuleDiagramNode node,RuleDiagramNode peerNode,boolean addBefore){
        int addIndex = getRawNodeList().size();
        if (peerNode != null){
            addIndex = getNodeIdMap().get(peerNode.getRuleNodeId());
            if (!addBefore){
                addIndex++;  
            }
        }
        getRawNodeList().add(addIndex, node);
        getNodeIdMap().put(node.getRuleNodeId(), addIndex);
        rebaseNodeIndex(addIndex);
        setDirty(true);
    }
    
    private void rebaseNodeIndex(int rebaseFrom){
        if (rebaseFrom == 0){
            getNodeIdMap().clear();
        }
        for (int i=rebaseFrom;i<getRawNodeList().size();i++){
            int nodeId = ((RuleDiagramNode)getRawNodeList().get(i)).getRuleNodeId();
            getNodeIdMap().put(nodeId, i);
        }
        
    }

    
    /**
     * Used internally to access the undecorated array of nodes does not 
     * trigger the creation of drop nodes and links
     * @return List of RuleDiagramNode
     */
    @Override
    public List<RuleDiagramNode> getRawNodeList() {
        return _diagramNodes;
    }
    
    /**
     * Used internally to access the map of nodes indexed by ID.
     * Clients should do the same lookup via the findNodeById() call
     * @see RuleDiagramBaseModel.findNodeById()
     * @return List of RuleDiagramNode
     */
    @Override
   public Map<Integer,Integer> getNodeIdMap() {
        return _diagramNodesById;
    }

    /**
     * Returns a list of all the Group nodes
     * @param topLevelOnly restrict the result list to just those group nodes that are themselves not members of a group
     * @return list of RuleDiagramGroupNode
     */
    private List<RuleDiagramGroupNode> getDiagramGroups(boolean topLevelOnly) {
        List<RuleDiagramGroupNode> groups = new ArrayList<RuleDiagramGroupNode>(_groupCount);
        for(RuleDiagramNode node : getRawNodeList()){
            if (node instanceof RuleDiagramGroupNode){
                if (topLevelOnly) {
                    if (node.getRuleGroupId() == null){
                        groups.add((RuleDiagramGroupNode)node);
                    }
                }
                else {
                    groups.add((RuleDiagramGroupNode)node);
                }
            }
        }
        return groups;
    }

    @Override
    public List<RuleDiagramLink> getDisplayDiagramLinks() {
        return _diagramLinks;
    }
    
    @Override
    public List<RuleDiagramNode> getDisplayDiagramNodes() {
        return _diagramNodesWithDrops;
    }
    
    
    public void setDisplayDiagramLinks(List<RuleDiagramLink> links){
        _diagramLinks = links;
    }

    @Override
    public RuleDiagramNode findNodeById(Integer nodeId) {
        RuleDiagramNode result = null;
        if (this.isDirty() || _externalIdLookupIndex == null || _externalIdLookupIndex.size() == 0 ){
            if (getNodeIdMap().containsKey(nodeId)){
                int resultIndex = getNodeIdMap().get(nodeId);
                result =  getRawNodeList().get(resultIndex);
            }
        }
        else {
            if (_externalIdLookupIndex.containsKey(nodeId)){
                int resultIndex = _externalIdLookupIndex.get(nodeId);
                result = _diagramNodesWithDrops.get(resultIndex);
            }
        }
            
        if (result == null) {
            _logger.warning("Unable to locate diagram node with id " + nodeId);            
        }
        return result;
    }
    

    /**
     * Remove a leaf node (condition) - may be called recursivly to remove a group node as well
     * if it turns out that the deleted leaf node was the only containee of the parent. 
     * However, this should not be called to delete a group with multiple children although it could be 
     * extended to do so if required.
     * @param node to delete
     */
    @Override
    public void removeNode(RuleDiagramNode node) {
        
        Integer containingGroupId = node.getRuleGroupId();
        if (containingGroupId != null) {
            RuleDiagramGroupNode containingGroup = (RuleDiagramGroupNode)this.findNodeById(containingGroupId);
            //if the containing group only has one child then we delete it as well
            if (containingGroup.getChildDiagramNodes().size() < 2){
                removeNode(containingGroup);
            }
            else {
                //remove this child
                containingGroup.deleteDiagramNode(node);
            }
        }
        
        //Reduce the overall group count if required
        if (node instanceof RuleDiagramGroupNode){
            decrementGroupCount();
        }
        
        //now remove from the internal model list
        int rebaseFrom = -1;
        ListIterator<RuleDiagramNode> iter =  getRawNodeList().listIterator();
        while (iter.hasNext()) {
            rebaseFrom++;
            RuleDiagramNode child = iter.next();
            if (child.equals(node)) {
                iter.remove();
                break;
            }
        }
        
        //And reset the search index
        rebaseNodeIndex(rebaseFrom);
        
        //Force a reset of the visual model on the next request     
        this.setDirty(true);
        
    }
    
    
    protected void incrementGroupCount(){
        _groupCount++;
    }
    
    protected void decrementGroupCount(){
        _groupCount++;
    }
    
    private void setDirty(boolean state) {
        _dirty = state;
    }

    public boolean isDirty() {
        return _dirty;
    }
    
    public void removeAnyAssociatedLinks(RuleDiagramNode node) {
        ListIterator<RuleDiagramLink> iter = getDisplayDiagramLinks().listIterator();
        while (iter.hasNext()) {
            RuleDiagramLink link = iter.next();
            if ((link.getLinkConjunction() != null) &&
                (link.getStartNode().equals(node) || link.getEndNode().equals(node))) {
                iter.remove();
            }
        }
    }
    
    
    /** 
     *Express the ruleset as a readable string
     */
    @Override
     public List<String> getFormattedRules(){
        StringBuilder b = new StringBuilder();
        Iterator groupIter = getDiagramGroups(true).iterator();
        while (groupIter.hasNext()){
            RuleDiagramGroupNode group = (RuleDiagramGroupNode)groupIter.next();
            b.append(group.getFormattedNodeExpression());
            if (groupIter.hasNext()){
                b.append(' ');
                b.append(TOP_LEVEL_CONJUNCTION.toString());
                b.append(' ');
            }
        }        
        _logger.info("Rule: " + b.toString());
        return Collections.singletonList(b.toString());
    }

    private RuleDiagramNode deducePeer(RuleDiagramGroupNode containerparentGrp, RuleDiagramNode peerNode) {
        RuleDiagramNode impliedPeer = null; 
        boolean locatedTarget = false;
        for (int i=(containerparentGrp.getChildDiagramNodes().size()-1);i >= 0;i--){
            RuleDiagramNode child = containerparentGrp.getChildDiagramNodes().get(i);
            if (locatedTarget){
                if (!child.getRuleNodeType().equals("DROPTARGET")){
                    impliedPeer = child;
                    break;
                }
            }
            else if (peerNode.equals(child)){
                locatedTarget = true;
                
            }
        }
        
        return impliedPeer;
    }
    
    /**
     * Returns true if the supplied node target is valid for dropping
     * in this impl that just means nodes that are RuleDiagramDropNodes
     * @param nodeId
     * @return
     */
    public Boolean isValidDropNode(Integer nodeId) {
        boolean droppable = false;
        if (nodeId != null){
            RuleDiagramNode node = findNodeById(nodeId);
            if (node != null && node instanceof RuleDiagramDropNode){
                droppable = true;
            }
        }
        
        return droppable;
        
    }
    
    @Override
    public void addDroppedCondition(Integer dropNodeId, String fact, String factValueType, Object value, String expression, String conjunction, boolean beforeTarget){
        
        RuleExpressionType exprtype = RuleExpressionType.valueOf(expression.trim().toUpperCase());
        RuleValueType valtype = RuleValueType.valueOf(factValueType.trim().toUpperCase()); 
        RuleConjunctionType conjtype = null;
        
        if (dropNodeId != null){
            RuleDiagramNode node = findNodeById(dropNodeId);    
            if (node instanceof RuleDiagramDropNode){
               RuleDiagramNode peerNode = node.getDropOwnerNode(); 
                   
               if (conjunction == null){
                    RuleDiagramGroupNode owningGroup = (RuleDiagramGroupNode)findNodeById(peerNode.getRuleGroupId());
                    if (owningGroup != null){
                        conjtype = RuleConjunctionType.valueOf(owningGroup.getRuleGroupConjuctionType()); 
                    }
                    else{
                        //The peer has no group so we're at the top level
                        conjtype = TOP_LEVEL_CONJUNCTION;
                    }
               }
               else {
                   conjtype = RuleConjunctionType.valueOf(conjunction.trim().toUpperCase());
               }
               
               
               RuleDiagramConditionNode condition = new RuleDiagramConditionNode(this,fact,exprtype,valtype,value);
               addNode(condition, peerNode, conjtype, beforeTarget);
               this.setDirty(true);
               
            }            
        }
        else{
            //Dropped into the empty space on the diagram 
            RuleDiagramConditionNode condition = new RuleDiagramConditionNode(this,fact,exprtype,valtype,value);
            addNode(condition);
            this.setDirty(true);
        }
    }
        
    /**
     * Returns the keys of all of the nodes classified as groups.  These are containers for ANDed or ORed conditions 
     * @return Keys of Groups to show pre-expanded in the diagram
     */
    @Override
    public RowKeySet getConditionGroups(){
        if (_conditionGroups == null){
            ensureDisplayModel();
        }
        return _conditionGroups;
    }
    
    /**
     * Returns the keys of the selected nodes.
     * @return Keys of selected node
     */
    @Override
    public RowKeySet getNodeSelections(){
      //  if (_nodeSelections == null){
      //      _nodeSelections = new RowKeySetImpl();
      //  }
        return _nodeSelections;
    }
    public void setNodeSelections(RowKeySet selections){
        _nodeSelections = selections;
    }
    
    /**
     * Constructs or refreshes the CollectionModels used 
     * by the diagram for the nodes and links. 
     * This process works off of the base node list and constructs a full CollectionModel which includes the 
     * artificial drop nodes as well as the core condition nodes ad groups. 
     * The links collection then includes all of the required links to stitch this all together.
     */
    private void ensureDisplayModel(){
        //Reset the Links
        if (getDisplayDiagramLinks() != null){
            getDisplayDiagramLinks().clear();
        }
        else {
            setDisplayDiagramLinks(new ArrayList<RuleDiagramLink>((getRawNodeList().size()*4)));
        }
        
        //Reset the display nodes
        if (_diagramNodesWithDrops != null){
            _diagramNodesWithDrops.clear();
        }
        else {
            _diagramNodesWithDrops = new ArrayList<RuleDiagramNode>((getRawNodeList().size()*5));
        }
        
        //Reset the Condition Groups
        if (_conditionGroups != null){
            _conditionGroups.removeAll();
        }
        else {
            _conditionGroups = new RowKeySetImpl();
        }
        
        
        //First create the top level group entries 
        //These are implicitly ANDed and OR nodes are not allowed
        List<RuleDiagramGroupNode> tlgroups = getDiagramGroups(true);
        int tlGroupCount = tlgroups.size();
        RuleDiagramNode lastTLJoiner = null;
        int processingGroup = 0;
        for (RuleDiagramGroupNode tlgroup : tlgroups){            
            getDisplayDiagramNodes().add(tlgroup);
            
            //Check to see if there is a hanging join to create a link to 
            if (lastTLJoiner != null){
                RuleDiagramLink link = new RuleDiagramLink(this,lastTLJoiner,tlgroup,RuleConjunctionType.AND);
                getDisplayDiagramLinks().add(link);      
            }
            
            if (processingGroup == (tlGroupCount -1)){
                addHangingGroupDropNode(tlgroup,tlgroup.getDropTargetS());
            }
            else {
                //In this case we create a concrete drop node to join this node
                //with the next
                RuleDiagramDropNode concreteDrop = tlgroup.getDropTargetS();
                concreteDrop.setDropNodeState(DROPNODESTATE.CONCRETE);
                getDisplayDiagramNodes().add(concreteDrop);
                RuleDiagramLink link = new RuleDiagramLink(this,tlgroup,concreteDrop,RuleConjunctionType.AND);
                getDisplayDiagramLinks().add(link); 
                lastTLJoiner = concreteDrop;
                
            }
            
            processingGroup++;
        }
        
        //Process each group and recalc the links and dropnodes within it
        List<RuleDiagramGroupNode> groups = getDiagramGroups(false);
        for (RuleDiagramGroupNode group : groups){            
            group.addImpliedLinksAndDropNodes();
        }

        // Now the displayNodes list is complete so we need to index it
        if (_externalIdLookupIndex != null){
            _externalIdLookupIndex.clear();
        }
        else {
            _externalIdLookupIndex = new HashMap<Integer,Integer>(_diagramNodesWithDrops.size());
        }
        
        for (int i=0;i<_diagramNodesWithDrops.size();i++){
            int indexNodeId = _diagramNodesWithDrops.get(i).getRuleNodeId();
            _externalIdLookupIndex.put(indexNodeId, i);
        }
        
        //Sort out the collectionModels for Display
        _diagramNodesAllCM = ModelUtils.toCollectionModel(_diagramNodesWithDrops);
        _diagramLinksCM = ModelUtils.toCollectionModel(_diagramLinks);
        
        //Set the rowkeys of the groups to ensure that they are all expanded
        int progress = 0;
        int nodeCount = _diagramNodesAllCM.getEstimatedRowCount();
        for (int i = 0; i < nodeCount; i++){
            _diagramNodesAllCM.setRowIndex(i);
            RuleDiagramNode chkNode = (RuleDiagramNode)_diagramNodesAllCM.getRowData();
            if (chkNode.getRuleNodeType().equals("GROUP")){
                _conditionGroups.add(_diagramNodesAllCM.getRowKey());
                progress++;
            }
            if (progress >= _groupCount){
                break;
            }
        }
        
        //finally unset the dirty flag
        setDirty(false);
        }
    private void addHangingGroupDropNode(RuleDiagramNode group, RuleDiagramDropNode dropNode){
        dropNode.setDropNodeState(DROPNODESTATE.HANGING);
        RuleDiagramLink link = new RuleDiagramLink(this,group,dropNode);
        getDisplayDiagramLinks().add(link);
        getDisplayDiagramNodes().add(dropNode);
    }

    @Override 
    public CollectionModel getDiagramNodesModel(){
        if (_diagramNodesAllCM == null || isDirty()){
            ensureDisplayModel();
        }
        return _diagramNodesAllCM;   
    }

    @Override
    public CollectionModel getDiagramLinksModel(){
        if (_diagramLinksCM == null || isDirty()){
            ensureDisplayModel();
        }
        return _diagramLinksCM;
    
    }
}