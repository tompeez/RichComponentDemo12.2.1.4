package oracle.adfdemo.view.feature.rich.hv;

import oracle.adf.view.faces.bi.component.hierarchyViewer.UIHierarchyViewer;

import oracle.adfdemo.view.feature.rich.hv.data.EmployeeData;

import org.apache.myfaces.trinidad.model.TreeModel;


public class PanelCardSample {
  
  private String panelCardEffect = "immediate";
  private String contentDelivery = "lazy";

  protected UIHierarchyViewer _hvComponent;
  
  // Data Models: Instance needs to be consistent across requests
  private final TreeModel DATA_SIMPLE = EmployeeData.newModel();


  public TreeModel getHvModel() {
    return DATA_SIMPLE;
  }

  public void setHvComponent(UIHierarchyViewer hvComponent) {
    this._hvComponent = hvComponent;
  }


  public UIHierarchyViewer getHvComponent() {
      return _hvComponent;
    }

  public void setPanelCardEffect(String panelCardEffect) {
    this.panelCardEffect = panelCardEffect;
  }

  public String getPanelCardEffect() {
    return panelCardEffect;
  }
  public void setContentDelivery(String m_contentDelivery) {
    this.contentDelivery = m_contentDelivery;
  }

  public String getContentDelivery() {
    return contentDelivery;
  }
}
