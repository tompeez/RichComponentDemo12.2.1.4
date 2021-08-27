package oracle.adfdemo.view.feature.rich.graph;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.graph.UIGraph;

import oracle.dss.graph.CommonGraph;

/**
 * A request-scoped backing bean used to manage a handle to a UIGraph used in the PieGraphFeatures demo
 */
public class PieGraphBindingBean {

  private UIGraph _uiGraph;

  public void setGraph(UIGraph graph)
  {
    this._uiGraph = graph;

    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
    
    PieGraphFeaturesBean pieGraphFeaturesBean = (PieGraphFeaturesBean) context.getSessionMap().get("pieGraphFeatures");
    if(pieGraphFeaturesBean != null)
      updatePieRotation(pieGraphFeaturesBean.getPieRotation());
  }

  public UIGraph getGraph()
  {
    return _uiGraph;
  }
  
  public void updatePieRotation(int angle)
  {
    CommonGraph graph = (CommonGraph) this._uiGraph.getImageView();
    graph.setPieRotation(angle);    
  }
}
