package oracle.adfdemo.view.feature.rich.graph;

import oracle.adf.view.faces.bi.component.graph.UIGraph;

import oracle.dss.dataView.ComponentHandle;
import oracle.dss.dataView.DataComponentHandle;
import oracle.dss.graph.CommonGraph;
import oracle.dss.graph.CustomMarkerTextCallback;

public class CustomMarkerTextCallbackSample 
{
  private UIGraph graph;

  public CustomMarkerTextCallbackSample()
  {
  }

  public void setGraph(UIGraph graph)
  {
    this.graph = graph;
  }

  public UIGraph getGraph()
  {
    if(graph == null)
    {
      graph = new UIGraph();
      CommonGraph g = (CommonGraph) graph.getImageView();
      g.setCustomMarkerTextCallback(new MyMarkerTextCallback());
    }
    return graph;
  }


  private class MyMarkerTextCallback implements CustomMarkerTextCallback {
  
    public String getMarkerText(String defaultMarkerText, ComponentHandle componentHandle)
    {
      Object value = ((DataComponentHandle)componentHandle).getValue(DataComponentHandle.UNFORMATTED_VALUE);
      return "Custom callback, value:" + value;
    }
  
  }
}
