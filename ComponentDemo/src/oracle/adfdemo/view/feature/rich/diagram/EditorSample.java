package oracle.adfdemo.view.feature.rich.diagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.diagram.UIDiagram;
import oracle.adf.view.faces.bi.component.diagram.UIDiagramLinks;
import oracle.adf.view.faces.bi.component.diagram.UIDiagramNodes;
import oracle.adf.view.faces.bi.component.util.CompUtils;
import oracle.adf.view.faces.bi.event.diagram.DiagramChangeLinkEvent;
import oracle.adf.view.faces.bi.event.diagram.DiagramCreateLinkEvent;
import oracle.adf.view.faces.bi.event.diagram.DiagramCreateNodeEvent;
import oracle.adf.view.faces.bi.event.diagram.DiagramDeleteLinkEvent;
import oracle.adf.view.faces.bi.event.diagram.DiagramDeleteNodeEvent;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.datatransfer.SimpleTransferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class EditorSample {
    private CollectionModel nodesModel;
    private CollectionModel linksModel;
    private RowKeySet selectedNodes = new RowKeySetImpl();
    private RowKeySet selectedLinks = new RowKeySetImpl();
    private UIDiagramNodes nodesComp;
    private UIDiagramLinks linksComp;

    private CollectionModel paletteNodes;
    private CollectionModel paletteLinks;

    private RichPopup nodeCreatePopup = null;
    private RichPopup linkExistsPopup = null;

    private Map<String, Object> newNode = null;
    private Map<String, Object> newLink = null;

    private int newNodeCounter = 1000;
    private int newLinkCounter = 1000;

    private boolean editing = false;
    private UIDiagram diagrammer;

    private boolean readOnly = false;
    private boolean linkReadOnly = false;
    private boolean nodeReadOnly = false;
    private boolean showCreatePopup = false;

    private List<EditorEventBean> eventQueue = new ArrayList();
    private RichTable table;

    private CollectionModel m_ddRegionTableModel;
    
    private static final String NODE_TYPE_1 = "Node1";
    private static final String NODE_TYPE_2 = "Node2";
    private static final String NODE_TYPE_3 = "Node3";
    private static final String NODE_TYPE_4 = "Node4";
    private static final String NODE_TYPE_5 = "Node5";
  
    private static final String LINK_TYPE_1 = "Link1";
    private static final String LINK_TYPE_2 = "Link2";
    private static final String LINK_TYPE_3 = "Link3";

    protected static final String NODE_ID = "Nodeid";
    protected static final String CONTAINER_ID = "Containerid";
    protected static final String LINK_ID = "Linkid";

    private static final String START_NODE = "Startnode";
    private static final String END_NODE = "Endnode";

    public EditorSample() {
        super();
    }
    
    public CollectionModel getNodes() {
        ensureModelsExist();
        return nodesModel;
    }

    public CollectionModel getLinks() {
        ensureModelsExist();
        return linksModel;
    }

    private void ensureModelsExist() {
        if (nodesModel == null || linksModel == null) {
            reset();
            generateFlowModels();
        }
        diagrammer.getFacesBean().setProperty(UIDiagram.PAN_X_KEY, null);
        diagrammer.getFacesBean().setProperty(UIDiagram.PAN_Y_KEY, null);
        diagrammer.getFacesBean().setProperty(UIDiagram.ZOOM_KEY, null);
    }

    private void generateFlowModels() {
        // Create Nodes
        List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
        nodes.add(createFlowNode(0, "Internet", NODE_TYPE_1));
        
        nodes.add(createFlowNode(1, "Router", NODE_TYPE_2));
        nodes.add(createFlowNode(2, "Wifi Router", NODE_TYPE_2));
        nodes.add(createFlowNode(3, "Switch", NODE_TYPE_2));
        
        nodes.add(createFlowNode(4, "Server", NODE_TYPE_3));
        nodes.add(createFlowNode(5, "PC", NODE_TYPE_3));
        nodes.add(createFlowNode(6, "PC", NODE_TYPE_3));
        nodes.add(createFlowNode(7, "Laptop", NODE_TYPE_3));
        nodes.add(createFlowNode(8, "Laptop", NODE_TYPE_3));
        nodes.add(createFlowNode(9, "Robot", NODE_TYPE_4));
        // Create Links
        List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
        links.add(createFlowLink(0, 0, 1, LINK_TYPE_3));
        links.add(createFlowLink(1, 1, 2, LINK_TYPE_1));
        links.add(createFlowLink(2, 1, 4, LINK_TYPE_1));
        links.add(createFlowLink(3, 1, 3, LINK_TYPE_1));
        links.add(createFlowLink(4, 3, 5, LINK_TYPE_1));
        links.add(createFlowLink(5, 3, 6, LINK_TYPE_1));
        links.add(createFlowLink(6, 2, 7, LINK_TYPE_2));
        links.add(createFlowLink(7, 2, 8, LINK_TYPE_2));
        links.add(createFlowLink(8, 2, 9, LINK_TYPE_2));
        links.add(createFlowLink(9, 9, 2, LINK_TYPE_2));
        links.add(createFlowLink(10, 1, 0, LINK_TYPE_3));
        nodesModel = ModelUtils.toCollectionModel(nodes);
        linksModel = ModelUtils.toCollectionModel(links);
    }

    private Map<String, Object> createFlowNode(int nodeid, String label, String type) {
        Map<String, Object> node = new HashMap<String, Object>();
        node.put(NODE_ID, createNodeId(nodeid));
        node.put(CONTAINER_ID, null);
        node.put("Label", label);
        node.put("Type", type);
        return node;
    }

    private Map<String, Object> createFlowLink(int linkid, int startnode, int endnode, String type) {
        Map<String, Object> link = new HashMap<String, Object>();
        link.put(LINK_ID, createLinkId(linkid));
        link.put(START_NODE, createNodeId(startnode));
        link.put(END_NODE, createNodeId(endnode));
        link.put("Type", type);
        return link;
    }

    private String createLinkId(int i) {
        return "L" + i;
    }

    private String createNodeId(int i) {
        return "N" + i;
    }

    public void nodesSelectedEdit(SelectionEvent selectionEvent) {
        nodesSelected(selectionEvent);
    }

    public void refreshDiagram() {
        diagrammer.getFacesBean().setProperty(UIDiagram.PAN_X_KEY, null);
        diagrammer.getFacesBean().setProperty(UIDiagram.PAN_Y_KEY, null);
        diagrammer.getFacesBean().setProperty(UIDiagram.ZOOM_KEY, null);
        RequestContext.getCurrentInstance().addPartialTarget(diagrammer);
    }

    public void submitNodeCreate(ActionEvent event) {
        Collection nodes = (Collection)nodesModel.getWrappedData();
        nodes.add(newNode);

        refreshDiagram();

        addTableEvent(new EditorEventBean("Node Added: " + printNode(newNode)));

        RichPopup popup = getNodeCreatePopup();
        popup.hide();
        // newNode = null;
    }

    public void cancelNodeCreate(ActionEvent event) {
        refreshDiagram();

        RichPopup popup = getNodeCreatePopup();
        popup.hide();
        // newNode = null;
    }

    public boolean blockIfLinkExists(Map<String, Object> link) {

        Object start = link.get(START_NODE);
        Object end = link.get(END_NODE);

        Collection links = (Collection)linksModel.getWrappedData();
        for (Object currentLinkObj : links) {
            Map<String, Object> currentLink = (Map<String, Object>)currentLinkObj;
            if (start.equals(currentLink.get(START_NODE)) && end.equals(currentLink.get(END_NODE)) ) {
                newLink = link;
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                this.getLinkExistsPopup().show(hints);
                this.refreshDiagram();
                return true;
            }
        }

        return false;
    }


    public void linkExistsOk(ActionEvent event) {
        RichPopup popup = getLinkExistsPopup();
        popup.hide();
    }

    public void nodesSelected(SelectionEvent selectionEvent) {
        selectedNodes.removeAll(selectionEvent.getRemovedSet());
        selectedNodes.addAll(selectionEvent.getAddedSet());
    }

    public void linksSelected(SelectionEvent selectionEvent) {
        selectedLinks.removeAll(selectionEvent.getRemovedSet());
        selectedLinks.addAll(selectionEvent.getAddedSet());
    }

    public List getEditableNodes() {
        List editableNodes = new ArrayList();
        for (Object node : selectedNodes) {
            if (nodesModel.isRowAvailable(node)) {
                editableNodes.add((Map)nodesModel.getRowData((Integer)node));
            }
        }
        return editableNodes;
    }

    public void editBegin(ActionEvent event) {
        this.editing = true;
    }

    public void submitChanges(ActionEvent event) {
        this.editing = false;
        Object oldKey = nodesComp.getRowKey();
        for (Object node : selectedNodes) {
            nodesComp.setRowKey(node);
            refreshDiagram();

        }

        nodesComp.setRowKey(oldKey);
    }

    public void cancelChanges(ActionEvent event) {

        this.editing = false;
    }

    public boolean getEditing() {
        return editing;
    }

    public RowKeySet getSelectedNodes() {
        return selectedNodes;
    }

    public RowKeySet getSelectedLinks() {
        return selectedLinks;
    }

    public UIDiagramNodes getUIDiagramNodes() {
        return nodesComp;
    }

    public void setUIDiagramNodes(UIDiagramNodes nodes) {
        nodesComp = nodes;
    }

    public UIDiagramLinks getUIDiagramLinks() {
        return linksComp;
    }

    public void setUIDiagramLinks(UIDiagramLinks links) {
        linksComp = links;
    }

    private void reset() {
        nodesModel = null;
        linksModel = null;
        selectedNodes.clear();
        selectedLinks.clear();
        if (nodesComp != null) {
            nodesComp.setSelectedRowKeys(selectedNodes);
        }
        if (linksComp != null) {
            linksComp.setSelectedRowKeys(selectedLinks);
        }
    }


    public void setDiagrammer(UIDiagram diagrammer) {
        this.diagrammer = diagrammer;
    }

    public void nodeCreateListener(DiagramCreateNodeEvent event) {
        String paletteId = event.getPaletteId();
        Object containerKey = event.getParentKey();
        Map<String, Object> parentData = null;
        if (containerKey != null) {
            Object oldKey = nodesModel.getRowKey();
            nodesModel.setRowKey(containerKey);
            parentData = (Map<String, Object>)nodesModel.getRowData();
            nodesModel.setRowKey(oldKey);
        }
        addNewNode(paletteId, parentData);
    }

    protected void addNewNode(String paletteId, Map<String, Object> parentData) {
        RichPopup popup = getNodeCreatePopup();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();

        
        int i = newNodeCounter++;

        Map<String, Object> node = new HashMap<String, Object>();
        node.put(NODE_ID, createNodeId(i));
        node.put("Type", paletteId);
        node.put("Label", getPaletteNodeLabel(paletteId));
        node.put("NewNode", "true");

        if (parentData != null) {
            node.put(CONTAINER_ID, parentData.get(NODE_ID));
        } else {
            node.put(CONTAINER_ID, null);
        }

        newNode = node;

        if (showCreatePopup) {
            popup.show(hints);
        } else {
            submitNodeCreate(null);
        }
    }

    private void addNodeEndpoint(Map<String, Object> link, String type, Object nodeKey) {
        Object oldKey = nodesModel.getRowKey();
        nodesModel.setRowKey(nodeKey);
        Object nid = ((Map<String, Object>)nodesModel.getRowData()).get(NODE_ID);
        link.put(type, nid);
        nodesModel.setRowKey(oldKey);
    }

    public void nodeDeleteListener(DiagramDeleteNodeEvent event) {
        RowKeySet keys = event.getKeySet();

        ArrayList<Map<String, Object>> remove = new ArrayList<Map<String, Object>>();
        Object oldKey = nodesModel.getRowKey();
        for (Object key : keys) {
            nodesModel.setRowKey(key);
            remove.add((Map<String, Object>)nodesModel.getRowData());
        }
        nodesModel.setRowKey(oldKey);
        removeNodes(diagrammer, remove);

        refreshDiagram();

    }

    public void linkDeleteListener(DiagramDeleteLinkEvent event) {
        RowKeySet keys = event.getKeySet();

        ArrayList<Map<String, Object>> remove = new ArrayList<Map<String, Object>>();
        Object oldKey = linksModel.getRowKey();
        for (Object key : keys) {
            linksModel.setRowKey(key);
            remove.add((Map<String, Object>)linksModel.getRowData());
        }
        linksModel.setRowKey(oldKey);
        Collection linkData = (Collection)linksModel.getWrappedData();
        for (Map<String, Object> link : remove) {
            linkData.remove(link);

            addTableEvent(new EditorEventBean("Link Deleted: " + printLink(link)));

        }
        refreshDiagram();

    }

    public void linkUpdateListener(DiagramChangeLinkEvent event) {
        Object linkKey = event.getLinkRowKey();

        Object oldKey = linksModel.getRowKey();
        linksModel.setRowKey(linkKey);
        Map<String, Object> link = (Map<String, Object>)linksModel.getRowData();
        linksModel.setRowKey(oldKey);

        Map<String, Object> proposedLink = new HashMap<String, Object>(link);

        DiagramChangeLinkEvent.EndpointType type = event.getEndpointType();
        String nodeType = null;
        if (DiagramChangeLinkEvent.EndpointType.SOURCE.equals(type)) {
            nodeType = START_NODE;
        } else {
            nodeType = END_NODE;
        }
        addNodeEndpoint(proposedLink, nodeType, event.getNewEndpointRowKey());

        if (blockIfLinkExists(proposedLink)) {
            return;
        }

        addNodeEndpoint(link, nodeType, event.getNewEndpointRowKey());

        refreshDiagram();

        addTableEvent(new EditorEventBean("Link Updated: " + printLink(link)));

    }

    public void linkCreateListener(DiagramCreateLinkEvent event) {
        newLink = new HashMap<String, Object>();

        Object srcKey = event.getSourceRowKey();
        Object destKey = event.getDestRowKey();

        int linkid = newLinkCounter++;

        addNodeEndpoint(newLink, START_NODE, srcKey);
        addNodeEndpoint(newLink, END_NODE, destKey);

        newLink.put(LINK_ID, createLinkId(linkid));
        newLink.put("Type", event.getPaletteId());
        if (blockIfLinkExists(newLink)) {
            return;
        }

        Collection links = (Collection)linksModel.getWrappedData();
        links.add(newLink);

        refreshDiagram();

        addTableEvent(new EditorEventBean("Link Created: " + printLink(newLink)));

    }

    public String printLink(Map<String, Object> link) {
        return String.format("%s from %s to %s", (String)link.get(LINK_ID), (String)link.get(START_NODE),
                             (String)link.get(END_NODE));
    }


    public String printNode(Map<String, Object> node) {
        return String.format("%s", (String)node.get(NODE_ID));
    }

    public UIDiagram getDiagrammer() {
        return diagrammer;
    }


    public Map<String, Object> createNodeCopy(Object node) {
        Map<String, Object> map = (Map<String, Object>)node;
        return new HashMap<String, Object>(map);
    }

    public void removeNodes(UIDiagram diagram, ArrayList<Map<String, Object>> nodesToRemove) {
        UIDiagramNodes diagramNodes =
            (UIDiagramNodes)CompUtils.getChildComponentByClass(diagram, UIDiagramNodes.class);
        UIDiagramLinks diagramLinks =
            (UIDiagramLinks)CompUtils.getChildComponentByClass(diagram, UIDiagramLinks.class);

        CollectionModel nodesCollectionModel = (CollectionModel)diagramNodes.getValue();
        Collection nodeData = (Collection)nodesCollectionModel.getWrappedData();

        CollectionModel linksCollectionModel = (CollectionModel)diagramLinks.getValue();
        Collection linkData = (Collection)linksCollectionModel.getWrappedData();

        Set<Object> removeNodeIds = new HashSet<Object>();

        for (Map<String, Object> removeNode : nodesToRemove) {
            removeNodeIds.add(removeNode.get(NODE_ID));
        }


        Map<Object, Set<Map<String, Object>>> savedLinks = new HashMap<Object, Set<Map<String, Object>>>();

        Set<Map<String, Object>> removeLinks = new HashSet<Map<String, Object>>();
        for (Object linkObj : linkData) {
            Map<String, Object> link = (Map<String, Object>)linkObj;
            Object start = link.get(START_NODE);
            Object end = link.get(END_NODE);
            Set<Object> foundNodeIds = new HashSet<Object>();
            if (removeNodeIds.contains(start)) {
                foundNodeIds.add(start);
            }
            if (removeNodeIds.contains(end)) {
                foundNodeIds.add(end);
            }
            for (Object foundNodeId : foundNodeIds) {
                Set<Map<String, Object>> links = savedLinks.get(foundNodeId);
                if (links == null) {
                    Set<Map<String, Object>> newList = new HashSet<Map<String, Object>>();
                    savedLinks.put(foundNodeId, newList);
                }
                savedLinks.get(foundNodeId).add(link);
                removeLinks.add(link);
            }
        }
        for (Map<String, Object> removeLink : removeLinks) {
            selectedLinks.clear();
        }
        //remove data from HV after above loop so that row keys don't change during above loop
        for (Map<String, Object> removeNode : nodesToRemove) {
            nodeData.remove(removeNode);
            selectedNodes.clear();

            addTableEvent(new EditorEventBean("Node Deleted: " + printNode(removeNode)));

        }

    }

    public void setNodeCreatePopup(RichPopup nodeCreatePopup) {
        this.nodeCreatePopup = nodeCreatePopup;
    }

    public RichPopup getNodeCreatePopup() {
        return nodeCreatePopup;
    }

    public void setNewNode(Map<String, Object> newNode) {
        this.newNode = newNode;
    }

    public Map<String, Object> getNewNode() {
        return newNode;
    }

    public void setLinkExistsPopup(RichPopup linkExistsPopup) {
        this.linkExistsPopup = linkExistsPopup;
    }

    public RichPopup getLinkExistsPopup() {
        return linkExistsPopup;
    }

    public void setNewLink(Map<String, Object> newLink) {
        this.newLink = newLink;
    }

    public Map<String, Object> getNewLink() {
        return newLink;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setLinkReadOnly(boolean linkReadOnly) {
        this.linkReadOnly = linkReadOnly;
    }

    public boolean isLinkReadOnly() {
        return linkReadOnly;
    }

    public void setNodeReadOnly(boolean nodeReadOnly) {
        this.nodeReadOnly = nodeReadOnly;
    }

    public boolean isNodeReadOnly() {
        return nodeReadOnly;
    }

    public List<EditorEventBean> getEventQueue() {
        return eventQueue;
    }


    public void clearTable(ActionEvent actionEvent) {
        eventQueue.clear();
    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }

    private void addTableEvent(EditorSample.EditorEventBean editorEventBean) {
        RequestContext.getCurrentInstance().addPartialTarget(table);
        eventQueue.add(editorEventBean);
    }

    public void setShowCreatePopup(boolean showCreatePopup) {
        this.showCreatePopup = showCreatePopup;
    }

    public boolean isShowCreatePopup() {
        return showCreatePopup;
    }
    
    protected Map<String, Object> getDropTargetEmployee(String dropTargetClientRowKey, UIDiagramNodes diagramNodes) {
        CollectionModel nodesCollectionModel = (CollectionModel)diagramNodes.getValue();
        Map<String, Object> dropTargetEmployee = null;
        if (dropTargetClientRowKey != null) {
            Object dropTargetRowKey = 
                        diagramNodes.getClientRowKeyManager().getRowKey(FacesContext.getCurrentInstance(), diagramNodes,
                                                                        dropTargetClientRowKey);
            nodesCollectionModel.setRowKey(dropTargetRowKey);
            dropTargetEmployee = (Map<String, Object>)nodesCollectionModel.getRowData();
        }
        return dropTargetEmployee;
    }
    
        
    protected List<Map<String, Object>> getDropInfo(CollectionModel collectionModel, RowKeySet keySet) {
        List<Map<String, Object>> dataToAdd = new ArrayList<Map<String, Object>>();
        Iterator iterator = keySet.iterator();
        while (iterator.hasNext()) {
            Object rowKey = iterator.next();
            collectionModel.setRowKey(rowKey);
            Map<String, Object> rowData = (Map<String, Object>)collectionModel.getRowData();
    
            // Now change each marker based on the DropEvent's proposed action
            if (rowData == null)
                continue;
            dataToAdd.add(rowData);
        }
        return dataToAdd;
    }
    
    public DnDAction diagramDropListener(DropEvent dropEvent) {
        DnDAction proposedAction = dropEvent.getProposedAction();
        UIDiagram diagram = (UIDiagram)dropEvent.getDropComponent();
        UIDiagramNodes diagramNodes =
            (UIDiagramNodes)CompUtils.getChildComponentByClass(diagram, UIDiagramNodes.class);
        CollectionModel nodesCollectionModel = (CollectionModel)diagramNodes.getValue();
        Collection nodeData = (Collection)nodesCollectionModel.getWrappedData();

        Map dropSite = (Map)dropEvent.getDropSite();
        String dropTargetClientRowKey = (String)dropSite.get("clientRowKey");
        Map<String, Object> dropTargetEmployee = getDropTargetEmployee(dropTargetClientRowKey, diagramNodes);

        List<Map<String, Object>> dataToAdd = new ArrayList<Map<String, Object>>();

        if (dropEvent.getDragComponent() instanceof RichTable) {
            RichTable table = (RichTable)dropEvent.getDragComponent();
            CollectionModel colModel = (CollectionModel) table.getValue();
            Collection collection = (Collection)colModel.getWrappedData();

            SimpleTransferable transfer = (SimpleTransferable)dropEvent.getTransferable();
            RowKeySet keySet = transfer.getData(RowKeySet.class);
            dataToAdd = getDropInfo(colModel, keySet);

            //remove data from table after above loop so that row keys don't change during above loop
            for (int ii = dataToAdd.size() - 1; ii >= 0; ii--) {
                collection.remove(dataToAdd.get(ii));
            }
            RequestContext currentInstance = RequestContext.getCurrentInstance();
            currentInstance.addPartialTarget(table);
        } else if (dropEvent.getDragComponent() instanceof RichPanelGroupLayout) {
            RichPanelGroupLayout comp = (RichPanelGroupLayout)dropEvent.getDragComponent();
            String paletteId = (String)comp.getAttributes().get("paletteId");
            addNewNode(paletteId, dropTargetEmployee);
        } else if (dropEvent.getDragComponent() instanceof UIDiagramNodes) {
          // don't support node containers for now
          /*
            SimpleTransferable transfer = (SimpleTransferable)dropEvent.getTransferable();
            RowKeySet keySet = transfer.getData(RowKeySet.class);
            dataToAdd = getDropInfo(nodesCollectionModel, keySet);
          */
          return proposedAction;
        }
        
        for (Map<String, Object> data : dataToAdd) {
            if (data != dropTargetEmployee) {
              if (dropTargetEmployee != null) { 
                data.put(CONTAINER_ID, dropTargetEmployee.get(NODE_ID));
              } else {
                data.put(CONTAINER_ID, null);
              }
            }
            if (proposedAction == DnDAction.MOVE) {
                nodeData.add(data);
            }
        }

        refreshDiagram();
        return proposedAction;
    }

    public String getPaletteNodeLabel(String paletteId) {
        if (paletteNodes == null)
            paletteNodes = ModelUtils.toCollectionModel(getPaletteNodeList());
        
        String label = "No Label";
        for (int i = 0; i < paletteNodes.getRowCount(); i++) {
          Map<String, Object> el = (Map<String, Object>)paletteNodes.getRowData(i);
          String type = el.get("Type").toString();
          if (paletteId.equals(type)) {
              label = el.get("Label").toString();
              break;
          }
        }  

        return label;
    }
  
    public CollectionModel getPaletteNodes() {
        if (paletteNodes == null) {
            paletteNodes = ModelUtils.toCollectionModel(getPaletteNodeList());
        }
        return paletteNodes;
    }

    public List getPaletteNodeList() {
        List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
        nodes.add(createPaletteNode(NODE_TYPE_1, "Internet"));
        nodes.add(createPaletteNode(NODE_TYPE_2, "Router/Switch"));
        nodes.add(createPaletteNode(NODE_TYPE_3, "PC/Laptop "));
        nodes.add(createPaletteNode(NODE_TYPE_4, "Model 001"));
        nodes.add(createPaletteNode(NODE_TYPE_5, "Model 002")); 
        return nodes;
    }

    private Map<String, Object> createPaletteNode(String type, String label) {
        Map<String, Object> node = new HashMap<String, Object>();
        node.put("Type", type);
        node.put("Label", label);
        return node;
    }

    public CollectionModel getPaletteLinks() {
        if (paletteLinks == null) {
            List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
            links.add(createPaletteLink(LINK_TYPE_1, "Wired"));
            links.add(createPaletteLink(LINK_TYPE_2, "Wireless"));
            paletteLinks = ModelUtils.toCollectionModel(links);
        }
        return paletteLinks;
    }


    private Map<String, Object> createPaletteLink(String type, String label) {
        Map<String, Object> node = new HashMap<String, Object>();
        node.put("Type", type);
        node.put("Label", label);
        return node;
    }

    public CollectionModel getDragAndDropRegionTableModel() {
        if (m_ddRegionTableModel == null) {
            m_ddRegionTableModel = ModelUtils.toCollectionModel(new ArrayList<Object>());
        }

        return m_ddRegionTableModel;
    }

    public DnDAction tableDrop(DropEvent dropEvent) {
        DnDAction proposedAction = DnDAction.NONE;

        boolean bRefreshHv = false;
        if (dropEvent.getDragComponent() instanceof UIDiagramNodes) {
            proposedAction = dropEvent.getProposedAction();
            UIDiagramNodes diagramNodes = (UIDiagramNodes) dropEvent.getDragComponent();
            UIDiagram diagram = (UIDiagram)diagramNodes.getParent();
            CollectionModel model = (CollectionModel)diagramNodes.getValue();
            Collection nodeData = (Collection)model.getWrappedData();

            SimpleTransferable transfer = (SimpleTransferable)dropEvent.getTransferable();
            Object obj = transfer.getData(Object.class);
            RowKeySet keySet = transfer.getData(RowKeySet.class);

            CollectionModel colModel = (CollectionModel)((RichTable)dropEvent.getDropComponent()).getValue();
            Collection collection = (Collection)colModel.getWrappedData();

            ArrayList<Map<String, Object>> hvDataToRemove = new ArrayList<Map<String, Object>>();
            Iterator iterator = keySet.iterator();
            while (iterator.hasNext()) {
                Object rowKey = iterator.next();
                model.setRowKey(rowKey);
                Map<String, Object> rowData = (Map<String, Object>)model.getRowData();

                // Now change each marker based on the DropEvent's proposed action
                if (rowData == null)
                    continue;

                if (proposedAction == DnDAction.COPY) {
                    collection.add(createNodeCopy(rowData));
                } else if (proposedAction == DnDAction.LINK) {
                    collection.add(createNodeCopy(rowData));
                } else if (proposedAction == DnDAction.MOVE) {
                    hvDataToRemove.add(rowData);
                    bRefreshHv = true;
                }
            }
            // Add to table
            for (Map<String, Object> removeNode : hvDataToRemove) {
                collection.add(removeNode);
            }
            // Remove from diagram
            removeNodes(diagram, hvDataToRemove);
        }

        if (bRefreshHv) {
            RequestContext currentInstance = RequestContext.getCurrentInstance();
            currentInstance.addPartialTarget(dropEvent.getDragComponent());
            refreshDiagram();
        }
        return proposedAction;
    }

    public class EditorEventBean {
        private String text;
        private Date date;

        public EditorEventBean(String text) {
            this.text = text;
            this.date = new java.util.Date();
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Date getDate() {
            return date;
        }
    }
}



