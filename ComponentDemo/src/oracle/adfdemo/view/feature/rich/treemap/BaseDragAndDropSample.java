package oracle.adfdemo.view.feature.rich.treemap;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.sunburst.UISunburst;
import oracle.adf.view.faces.bi.component.treemap.UITreemap;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adfdemo.view.feature.rich.treemap.data.TreeNode;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.render.ClientRowKeyManager;


/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/DragAndDropSample.java /main/4 2010/03/24 07:15:48 hzhang Exp $
 *  @author  hzhang
 *  @since   release specific (what release of product did this appear in)
 */
public class BaseDragAndDropSample {    
  private String dropText = "Drop a node here";
  private String dragText = "Drag this text onto a node";

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
      sb.append(getLabel(dragComponent, rowKey)); 
      sb.append(", ");
    }
    
    // Remove the trailing ,
    sb.setLength(sb.length()-2);
    
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
      Object rowKey = getRowKey(dropComponent, clientRowKey);
      if(rowKey != null) {
        sb.append("Drop Site: ");
        sb.append(getLabel(dropComponent, rowKey));
      }
    }
    
    // Update the output text
    this.dragText = sb.toString();
    RequestContext.getCurrentInstance().addPartialTarget(event.getDragComponent());
    
    return event.getProposedAction();
  }
  
  private String getLabel(UIComponent component, Object rowKey) {
    if(component instanceof UITreemap) {
      UITreemap treemap = (UITreemap) component;
      TreeNode rowData = (TreeNode) treemap.getRowData(rowKey);
      return rowData.getText();
    }
    else if(component instanceof UISunburst) {
      UISunburst sunburst = (UISunburst) component;
      TreeNode rowData = (TreeNode) sunburst.getRowData(rowKey);
      return rowData.getText();
    }
    
    return null;
  }
  
  private Object getRowKey(UIComponent component, String clientRowKey) {
    if(component instanceof UITreemap) {
      UITreemap treemap = (UITreemap) component;
      ClientRowKeyManager crkm = treemap.getClientRowKeyManager();
      return crkm.getRowKey(FacesContext.getCurrentInstance(), component, clientRowKey);
    }
    else if(component instanceof UISunburst) {
      UISunburst sunburst = (UISunburst) component;
      ClientRowKeyManager crkm = sunburst.getClientRowKeyManager();
      return crkm.getRowKey(FacesContext.getCurrentInstance(), component, clientRowKey);
    }
    
    return null;
  }

  public String getDropText() {
    return dropText;
  }

  public String getDragText() {
    return dragText;
  }
}
