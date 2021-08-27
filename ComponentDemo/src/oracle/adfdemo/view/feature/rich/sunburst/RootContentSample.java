package oracle.adfdemo.view.feature.rich.sunburst;


import oracle.adfdemo.view.feature.rich.hv.data.EmployeeData;

import org.apache.myfaces.trinidad.model.TreeModel;


public class RootContentSample {
  
   // Data Models: Instance needs to be consistent across requests
  private TreeModel MODEL = EmployeeData.newModel();


  public TreeModel getModel() {
    return MODEL;
  }

}
