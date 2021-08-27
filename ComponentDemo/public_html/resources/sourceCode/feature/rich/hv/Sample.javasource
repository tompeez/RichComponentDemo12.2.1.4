package oracle.adfdemo.view.feature.rich.hv;

import oracle.adf.view.faces.bi.component.hierarchyViewer.UIHierarchyViewer;

import oracle.adfdemo.view.feature.rich.hv.data.EmployeeData;
import oracle.adfdemo.view.feature.rich.hv.data.EmployeeNode;

import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.model.TreeModel;


public class Sample {
  
  private static final String EMPLOYEE_DATA = "Employee";
  private static final String EMPTY_DATA = "Empty Data";
  
  public int _showLevels = 1;
  
  private String controlPanelBehavior = "initCollapsed"; 

  protected UIHierarchyViewer _hvComponent;
  
  private Object _currentRowKey;

  
  
  // Data Models: Instance needs to be consistent across requests
  private final TreeModel DATA_SIMPLE = EmployeeData.newModel();
  
  private String dataType = "Employee";
  
  private String emptyText = "Empty Hierarchy Viewer";
  
  private String nodeSelection = "multiple";
  private String panning = "auto";
  private String zooming = "auto";
  
  private String clickBehavior = "focus";
  private String nodeShape = "roundedRect";
  
  private String linkType = "orthogonalRounded";
  private String linkStyle = "solid";
  private boolean linkLabel = false;


  public TreeModel getNullData() {
    return null;
  }

  
  public TreeModel getSimpleData() {
    return DATA_SIMPLE;
  }

  public TreeModel getHvModel() {
    if (EMPLOYEE_DATA.equals(dataType)) {
      return getSimpleData();
    }
    else {
      return getNullData();
    }
  }

  public void setHvComponent(UIHierarchyViewer hvComponent) {
    this._hvComponent = hvComponent;
    }
    
    
  public void setControlPanelBehavior(String controlPanelBehavior) {
      this.controlPanelBehavior = controlPanelBehavior;
    }
    
  public String getControlPanelBehavior() {
      return this.controlPanelBehavior;
    }

  public UIHierarchyViewer getHvComponent() {
      return _hvComponent;
    }
    
  public void setShowLevels(int showLevels) {       
        this._showLevels = showLevels;
        UIHierarchyViewer hv = getHvComponent();
        hv.setDisclosedRowKeys(null);
    }

  public int getShowLevels() {
        return _showLevels;
    }
    
  public void setDataType(String dataType) {
      this.dataType = dataType;
    }

  public String getDataType() {
      return this.dataType;
    }
  
  public void setEmptyText(String emptyText) {
      this.emptyText = emptyText;
    }
  
  public String getEmptyText() {
      return this.emptyText;
    }

  public void setNodeSelection(String nodeSelection) {
    this.nodeSelection = nodeSelection;
    if (nodeSelection.equals("multiple") || _hvComponent.getSelectedRowKeys() == null){
      return;
    }
    else if (nodeSelection.equals("none")){
      _hvComponent.setSelectedRowKeys(null);
    }
    else if (nodeSelection.equals("single") && _hvComponent.getSelectedRowKeys().getSize() > 1){
      RowKeySet rowKeySet = _hvComponent.getSelectedRowKeys();
      for (Object rowKey : rowKeySet) {
        RowKeySetImpl keyset = new RowKeySetImpl(); 
        keyset.add(rowKey);
        _hvComponent.setSelectedRowKeys(keyset);
        return;
      }
    }
  }

  public String getNodeSelection() {
    return nodeSelection;
  }

  public void setPanning(String panning) {
    this.panning = panning;
  }

  public String getPanning() {
    return panning;
  }

  public void setZooming(String zooming) {
    this.zooming = zooming;
  }

  public String getZooming() {
    return zooming;
  }


  public void setLinkLabel(boolean linkLabel) {
    this.linkLabel = linkLabel;
  }

  public boolean getLinkLabel() {
    return linkLabel;
  }

  public void setClickBehavior(String clickBehavior) {
    this.clickBehavior = clickBehavior;
  }

  public String getClickBehavior() {
    return clickBehavior;
  }

  public void setNodeShape(String nodeShape) {
    this.nodeShape = nodeShape;
  }

  public String getNodeShape() {
    return nodeShape;
  }

  public void setLinkType(String linkType) {
    this.linkType = linkType;
  }

  public String getLinkType() {
    return linkType;
  }

  public void setLinkStyle(String linkStyle) {
    this.linkStyle = linkStyle;
  }

  public String getLinkStyle() {
    return linkStyle;
  }
  public String getSelectionState() {
    RowKeySet rowKeySet = _hvComponent.getSelectedRowKeys();
    StringBuilder s = new StringBuilder();
    if (rowKeySet != null) {
      for (Object rowKey : rowKeySet) {
        s.append(((EmployeeNode)_hvComponent.getRowData(rowKey)).getFirstName()).append(' ');
      }
      if (s.length() > 0) {
        s.setLength(s.length() - 1);
      }
    }
    return s.toString();
  }
}
