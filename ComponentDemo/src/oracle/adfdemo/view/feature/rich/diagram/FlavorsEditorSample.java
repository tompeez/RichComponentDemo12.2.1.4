package oracle.adfdemo.view.feature.rich.diagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.diagram.UIDiagram;
import oracle.adf.view.faces.bi.component.diagram.UIDiagramNodes;
import oracle.adf.view.faces.bi.component.util.CompUtils;
import oracle.adf.view.faces.bi.event.diagram.DiagramCreateNodeEvent;
import oracle.adf.view.faces.bi.event.diagram.DiagramDeleteNodeEvent;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichImage;
import oracle.adf.view.rich.datatransfer.SimpleTransferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class FlavorsEditorSample {
    private CollectionModel nodesModel;
    private RowKeySet selectedNodes = new RowKeySetImpl();
    private UIDiagramNodes nodesComp;

    private CollectionModel paletteNodes;

    private Map<String, Object> newNode = null;

    private int newNodeCounter = 1000;

    private boolean editing = false;
    private UIDiagram diagrammer;

    private boolean readOnly = false;
    private boolean nodeReadOnly = false;
    private RowKeySet m_disclosedNodes = new RowKeySetImpl();
    
    private static final String NODE_TYPE_2 = "Node2";
    private static final String NODE_TYPE_3 = "Node3";
    private static final String NODE_TYPE_4 = "Node4";
    private static final String NODE_TYPE_5 = "Node5";

    protected static final String NODE_ID = "Nodeid";
    protected static final String CONTAINER_ID = "Containerid";
    protected static final String LINK_ID = "Linkid";

    private static final String START_NODE = "Startnode";
    private static final String END_NODE = "Endnode";

    public FlavorsEditorSample() {
        super();
    }
    
    public CollectionModel getNodes() {
        ensureModelsExist();
        return nodesModel;
    }

    private void ensureModelsExist() {
        if (nodesModel == null) {
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
        
        nodes.add(createFlowNode(1, "Blue 1", NODE_TYPE_2));
        nodes.add(createFlowNode(2, "Blue 2", NODE_TYPE_2));
        
        nodes.add(createFlowNode(3, "Red 1", NODE_TYPE_3));
        nodes.add(createFlowNode(4, "Red 2", NODE_TYPE_3));

        // Disclose containers
        m_disclosedNodes.add(0);
        m_disclosedNodes.add(1);
        m_disclosedNodes.add(2);
        m_disclosedNodes.add(3);
        
        nodesModel = ModelUtils.toCollectionModel(nodes);
    }
    
    public RowKeySet getDisclosedNodes() {
      ensureModelsExist();
      return m_disclosedNodes;
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
    }

    public void cancelNodeCreate(ActionEvent event) {
        refreshDiagram();
    }

    public void nodesSelected(SelectionEvent selectionEvent) {
        selectedNodes.removeAll(selectionEvent.getRemovedSet());
        selectedNodes.addAll(selectionEvent.getAddedSet());
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

    public UIDiagramNodes getUIDiagramNodes() {
        return nodesComp;
    }

    public void setUIDiagramNodes(UIDiagramNodes nodes) {
        nodesComp = nodes;
    }

    private void reset() {
        nodesModel = null;
        selectedNodes.clear();
        if (nodesComp != null) {
            nodesComp.setSelectedRowKeys(selectedNodes);
        }
    }
    
    public String[] getDropFlavors() {
      List<String> dropFlavors = new ArrayList<String>();
      dropFlavors.add("javax.faces.component.UIComponent/Human");
      dropFlavors.add("javax.faces.component.UIComponent/Robot");      
      return (String[])dropFlavors.toArray(new String[dropFlavors.size()]);
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
        if (NODE_TYPE_2.equals(paletteId) || NODE_TYPE_3.equals(paletteId))
          m_disclosedNodes.add(nodesModel.getRowCount());

        newNode = node;
        submitNodeCreate(null);
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

        CollectionModel nodesCollectionModel = (CollectionModel)diagramNodes.getValue();
        Collection nodeData = (Collection)nodesCollectionModel.getWrappedData();

        Set<Object> removeNodeIds = new HashSet<Object>();

        for (Map<String, Object> removeNode : nodesToRemove) {
            removeNodeIds.add(removeNode.get(NODE_ID));
        }
        //remove data from HV after above loop so that row keys don't change during above loop
        for (Map<String, Object> removeNode : nodesToRemove) {
            nodeData.remove(removeNode);
            selectedNodes.clear();
        }

    }

    public void setNewNode(Map<String, Object> newNode) {
        this.newNode = newNode;
    }

    public Map<String, Object> getNewNode() {
        return newNode;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isReadOnly() {
        return readOnly;
    }
    public void setNodeReadOnly(boolean nodeReadOnly) {
        this.nodeReadOnly = nodeReadOnly;
    }

    public boolean isNodeReadOnly() {
        return nodeReadOnly;
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
        UIComponent dropComp = dropEvent.getDropComponent();
        UIDiagramNodes diagramNodes;
        if (dropComp instanceof UIDiagram) {
          UIDiagram diagram = (UIDiagram) dropComp;
          diagramNodes =
                (UIDiagramNodes)CompUtils.getChildComponentByClass(diagram, UIDiagramNodes.class);
            
        }
        else {
          diagramNodes = (UIDiagramNodes)dropComp;
        }        
        CollectionModel nodesCollectionModel = (CollectionModel)diagramNodes.getValue();
        Collection nodeData = (Collection)nodesCollectionModel.getWrappedData();

        Map dropSite = (Map)dropEvent.getDropSite();
        String dropTargetClientRowKey = (String)dropSite.get("clientRowKey");
        Map<String, Object> dropTargetEmployee = getDropTargetEmployee(dropTargetClientRowKey, diagramNodes);

        List<Map<String, Object>> dataToAdd = new ArrayList<Map<String, Object>>();

        if (dropEvent.getDragComponent() instanceof RichPanelGroupLayout) {
            RichPanelGroupLayout comp = (RichPanelGroupLayout)dropEvent.getDragComponent();
            String paletteId = (String)comp.getAttributes().get("paletteId");
            addNewNode(paletteId, dropTargetEmployee);
        } else if (dropEvent.getDragComponent() instanceof UIDiagramNodes) {
          SimpleTransferable transfer = (SimpleTransferable)dropEvent.getTransferable();
          RowKeySet keySet = transfer.getData(RowKeySet.class);
          dataToAdd = getDropInfo(nodesCollectionModel, keySet);
        } else if (dropEvent.getDragComponent() instanceof RichImage) {
          if ("imageA".equals(((RichImage)dropEvent.getDragComponent()).getLongDescURL()) )
            addNewNode(NODE_TYPE_2, dropTargetEmployee);
          else if ("imageB".equals(((RichImage)dropEvent.getDragComponent()).getLongDescURL()))
            addNewNode(NODE_TYPE_3, dropTargetEmployee);
          else if ("imageC".equals(((RichImage)dropEvent.getDragComponent()).getLongDescURL()))
            addNewNode(NODE_TYPE_4, dropTargetEmployee);            
          else
            addNewNode(NODE_TYPE_5, dropTargetEmployee);
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
        nodes.add(createPaletteNode(NODE_TYPE_2, "Blue"));
        nodes.add(createPaletteNode(NODE_TYPE_3, "Red"));
        nodes.add(createPaletteNode(NODE_TYPE_4, "Blue"));
        nodes.add(createPaletteNode(NODE_TYPE_5, "Red")); 
        return nodes;
    }

    private Map<String, Object> createPaletteNode(String type, String label) {
        Map<String, Object> node = new HashMap<String, Object>();
        node.put("Type", type);
        node.put("Label", label);
        return node;
    }
}



