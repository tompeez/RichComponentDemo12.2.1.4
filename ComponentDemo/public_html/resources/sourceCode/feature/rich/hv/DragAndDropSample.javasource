package oracle.adfdemo.view.feature.rich.hv;

import java.util.Map;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.hierarchyViewer.UIHierarchyViewer;

import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adfdemo.view.feature.rich.hv.data.EmployeeData;

import oracle.adfdemo.view.feature.rich.hv.data.EmployeeNode;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;
import org.apache.myfaces.trinidad.render.ClientRowKeyManager;


public class DragAndDropSample {
  
  protected UIHierarchyViewer _hvComponent;
  
  // Data Models: Instance needs to be consistent across requests
  private final TreeModel DATA_SIMPLE = EmployeeData.newModel();
  
  private String dropText = "Drop a node here";
  private String dragText = "Drag this text onto a node";


  public TreeModel getHvModel() {
    return DATA_SIMPLE;
  }

  public void setHvComponent(UIHierarchyViewer hvComponent) {
    this._hvComponent = hvComponent;
  }


  public UIHierarchyViewer getHvComponent() {
      return _hvComponent;
    }
  public DnDAction fromDropListener(DropEvent event) {
    Transferable transferable = event.getTransferable();
    DataFlavor<RowKeySet> dataFlavor = DataFlavor.getDataFlavor(RowKeySet.class);
    RowKeySet rowKeySet = transferable.getData(dataFlavor);
    if(rowKeySet == null || rowKeySet.getSize() <= 0)
        return DnDAction.NONE;
      
    // Build up the string that reports the drop information
    StringBuilder sb = new StringBuilder();
    
    // Start with the proposed action
    sb.append("Drag Operation: ");
    DnDAction proposedAction = event.getProposedAction();
    if(proposedAction == DnDAction.COPY) {
      sb.append("Copy<br>");
    }
    else if(proposedAction == DnDAction.LINK) {
      sb.append("Link<br>");
    }
    else if(proposedAction == DnDAction.MOVE) {
      sb.append("Move<br>");
    }
    
    // Then add the rowKeys of the nodes that were dragged
    sb.append("Nodes: ");
    UIComponent dragComponent = event.getDragComponent();
    for(Object rowKey : rowKeySet) {
      sb.append(((EmployeeNode)_hvComponent.getRowData(rowKey)).getFirstName()).append(' ');
    }
    
    // Remove the trailing ,
    sb.setLength(sb.length()-1);
    
    // Update the output text
    this.dropText = sb.toString();
    RequestContext.getCurrentInstance().addPartialTarget(event.getDropComponent());
    
    return event.getProposedAction();
  }
  public DnDAction toDropListener(DropEvent event) {
    Transferable transferable = event.getTransferable();
    DataFlavor<Object> dataFlavor = DataFlavor.getDataFlavor(Object.class);
    Object transferableObj = transferable.getData(dataFlavor);
    if(transferableObj == null)
        return DnDAction.NONE;
      
    // Build up the string that reports the drop information
    StringBuilder sb = new StringBuilder();
    
    // Start with the proposed action
    sb.append("Drag Operation: ");  
    DnDAction proposedAction = event.getProposedAction();
    if(proposedAction == DnDAction.COPY) {
      sb.append("Copy<br>");
    }
    else if(proposedAction == DnDAction.LINK) {
      sb.append("Link<br>");
    }
    else if(proposedAction == DnDAction.MOVE) {
      sb.append("Move<br>");
    }
    
    // Then add the rowKeys of the nodes that were dragged
    UIComponent dropComponent = event.getDropComponent();
    Object dropSite = event.getDropSite();
    if(dropSite instanceof Map) {
      String clientRowKey = (String) ((Map) dropSite).get("clientRowKey");
      //Object rowKey = getRowKey(dropComponent, clientRowKey);
      UIHierarchyViewer hv = (UIHierarchyViewer) dropComponent;
      ClientRowKeyManager crkm = hv.getClientRowKeyManager();
      Object rowKey = crkm.getRowKey(FacesContext.getCurrentInstance(), dropComponent, clientRowKey);
      if(clientRowKey != null) {
        sb.append("Drop Site: ");
        sb.append(((EmployeeNode)_hvComponent.getRowData(rowKey)).getFirstName()).append(' ');

      }
    }
    
    // Update the output text
    this.dragText = sb.toString();
    RequestContext.getCurrentInstance().addPartialTarget(event.getDragComponent());
    
    return event.getProposedAction();
  }
  
  
  public String getDropText() {
    return dropText;
  }

  public String getDragText() {
    return dragText;
  }
}
