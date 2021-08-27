package oracle.adfdemo.view.feature.rich.hv;

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.hierarchyViewer.UIHierarchyViewer;

import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;

import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;

import oracle.adfdemo.view.feature.rich.hv.data.EmployeeData;

import oracle.adfdemo.view.feature.rich.hv.data.EmployeeNode;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;


public class PopupSample {
  
  protected UIHierarchyViewer _hvComponent;
  
  // Data Models: Instance needs to be consistent across requests
  private final TreeModel TREEMODEL = EmployeeData.newModel();
  
  protected RowKeySet _selectedRowKeys = null;
  protected String status = null;
  protected String _selectedMenuItem = null;
  protected EmployeeNode _selEm = null;
  protected Object _o = null;
  protected RichOutputFormatted outputFormatted = null;
  



  public TreeModel getHvModel() {
    return TREEMODEL;
  }

  public void setHvComponent(UIHierarchyViewer hvComponent) {
    this._hvComponent = hvComponent;
  }


  public UIHierarchyViewer getHvComponent() {
      return _hvComponent;
    }
  /**
   * Check if multiple nodes are selected
   * @return true if multiple nodes are selected
   */
  public boolean isMultipleSelection() {
    if(this._selectedRowKeys != null && this._selectedRowKeys.getSize() > 1) {
      return true;
    }
    return false;
  }
  /**
   * Get Selected HV Node(s) to display Context Menu Item
   * @return selected nodes string
   */
  public String getSelectedMenuItem() {
    if(this.isMultipleSelection()) {
      Iterator i = this._selectedRowKeys.iterator();
      StringBuffer buffer = new StringBuffer();
      while (i.hasNext()) {
        TREEMODEL.setRowKey(i.next());
        _selEm = (EmployeeNode)TREEMODEL.getRowData();
        String firstname = _selEm.getFirstName();
        buffer.append(firstname).append(",");
      }
      return buffer.toString().substring(0,buffer.length()-1);
    }
    //return selected menu item for single node selection
    return this._selectedMenuItem;
  }
  
  /**
   * Called when a commandMenuItem is clicked.  Updates the outputText with information about the menu item clicked.
   * @param actionEvent click action event
   */
  public void menuItemListener(ActionEvent actionEvent) {
    UIComponent component = actionEvent.getComponent();
    if (component instanceof RichCommandMenuItem) {
      RichCommandMenuItem cmi = (RichCommandMenuItem)component;
  
      // Add the text of the item into the status message
      StringBuilder s = new StringBuilder();
      s.append("You clicked on \"").append(cmi.getText()).append("\".  <br><br>");
      this.status = s.toString();
  
      // Update the status text component
      RequestContext.getCurrentInstance().addPartialTarget(this.outputFormatted);
    }
  }

  public void setSource(Object _o) {
    this._o = _o;
  }

  public Object getSource() {
    return _o;
  }

  public void setOutputFormatted(RichOutputFormatted outputFormatted) {
    this.outputFormatted = outputFormatted;
  }

  public RichOutputFormatted getOutputFormatted() {
    return outputFormatted;
  }

  public String getStatus() {
    return status;
  }

  public void setSelectedMenuItem(String _selectedMenuItem) {
    this._selectedMenuItem = _selectedMenuItem;
  }
}
