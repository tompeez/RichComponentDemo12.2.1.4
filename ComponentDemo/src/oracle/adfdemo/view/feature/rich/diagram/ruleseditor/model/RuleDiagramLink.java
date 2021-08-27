package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model;

import oracle.adf.share.logging.ADFLogger;

public class RuleDiagramLink {
    private static ADFLogger _logger = ADFLogger.createADFLogger(RuleDiagramLink.class);
    
    private RuleDiagramModel _model;
    private RuleDiagramNode _startNode;
    private RuleDiagramNode _endNode;
    private RuleConjunctionType _conjunction;
    private boolean _topLevel;

    public RuleDiagramLink(RuleDiagramModel model, RuleDiagramNode start, RuleDiagramNode end) {
        initLink( model, start, end, null,false );
    }
    
    public RuleDiagramLink(RuleDiagramModel model, RuleDiagramNode start, RuleDiagramNode end, RuleConjunctionType conjunction ) {
        initLink( model, start, end, conjunction,false );
    }
    
    public RuleDiagramLink(RuleDiagramModel model, RuleDiagramNode start, RuleDiagramNode end, RuleConjunctionType conjunction, boolean topLevel ) {
        initLink( model, start, end, conjunction, topLevel);
    }
    
    private void initLink(RuleDiagramModel model, RuleDiagramNode start, RuleDiagramNode end, RuleConjunctionType conjunction, boolean topLevel  ){
        _model = model;
        _startNode = start;
        _endNode = end;
        _conjunction = conjunction;
        _topLevel = topLevel;
    }
    
    
    public Integer getStartNodeId(){
        return getStartNode().getRuleNodeId();
    }
    
    public void setStartNodeId(Integer nodeId){
        findAndUseNode(_startNode,nodeId);
    }
    
    public Integer getEndNodeId(){
        return getEndNode().getRuleNodeId();
    }
    
    public void setEndNodeId(Integer nodeId){
        findAndUseNode(_endNode,nodeId);
    }
    
    public String getLinkLabel(){
        if (_conjunction != null){
            return _conjunction.toString();
        }
        else{
            return "";
        }
    }
    
    protected RuleDiagramNode getStartNode(){
        return _startNode;
    }
    
    protected void setStartNode(RuleDiagramNode node) {
        setNodeIfValid(_startNode,node);
    }
    
    protected RuleDiagramNode getEndNode(){
        return _endNode;
    }

    protected void setEndNode(RuleDiagramNode node) {
        setNodeIfValid(_endNode,node);
    }
    
    protected RuleConjunctionType getLinkConjunction(){
        return _conjunction;
    }

    
    private void findAndUseNode(RuleDiagramNode targetNode,Integer nodeId){
        if(nodeId != null){
            RuleDiagramNode node = _model.findNodeById(nodeId);
            targetNode = node;
        }
        else{
            String errm = "Error: Node ID reference is null, unable to resolve to real node reference";
            _logger.severe(errm);
            throw new RuleDiagramException(errm);            
        }
        
    }
    
    private void setNodeIfValid(RuleDiagramNode targetNode,RuleDiagramNode node){
        if (node != null){
            targetNode = node;
        }
        else{
            String errm = "Error: Unable to set node reference to empty value. Links must have both ends defined.";
            _logger.severe(errm);
            throw new RuleDiagramException(errm);
        }   
    }  
    
    public void setTopLevel(boolean _topLevel) {
        this._topLevel = _topLevel;
    }

    public boolean isTopLevel() {
        return _topLevel;
    }
    
}
