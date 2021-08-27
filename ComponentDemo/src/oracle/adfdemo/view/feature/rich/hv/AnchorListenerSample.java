package oracle.adfdemo.view.feature.rich.hv;

import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.hierarchyViewer.UIHierarchyViewer;

import oracle.adfdemo.view.feature.rich.hv.data.EmployeeData;

import oracle.adfdemo.view.feature.rich.hv.data.EmployeeNode;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.TreeModel;


public class AnchorListenerSample {
  
  protected UIHierarchyViewer _hvComponent;
  
  // Data Models: Instance needs to be consistent across requests
  private TreeModel MODEL = EmployeeData.newModel();


  public TreeModel getHvModel() {
    return MODEL;
  }

  public void setHvComponent(UIHierarchyViewer hvComponent) {
    this._hvComponent = hvComponent;
  }


  public UIHierarchyViewer getHvComponent() {
      return _hvComponent;
    }
  public void doSetAnchor(ActionEvent actionEvent) {
      // Add event code here...

      UIHierarchyViewer hv = (UIHierarchyViewer)actionEvent.getSource();
      TreeModel model = (TreeModel)hv.getValue();
      EmployeeNode em = (EmployeeNode)model.getRowData();

      MODEL = new ChildPropertyTreeModel(em, "children");
  }
  public void doNavigateUp(ActionEvent actionEvent) {
      UIHierarchyViewer hv = (UIHierarchyViewer)actionEvent.getSource();
      TreeModel model = (TreeModel)hv.getValue();
      EmployeeNode em = (EmployeeNode) model.getRowData();

      if (em.getParent() != null)
        MODEL = new ChildPropertyTreeModel(em.getParent(), "children");
  } 
}
