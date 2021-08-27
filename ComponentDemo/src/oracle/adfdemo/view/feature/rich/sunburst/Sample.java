package oracle.adfdemo.view.feature.rich.sunburst;

import oracle.adf.view.faces.bi.component.sunburst.UISunburst;

import org.apache.myfaces.trinidad.model.RowKeySet;

import oracle.adfdemo.view.feature.rich.treemap.BaseSample;


public class Sample extends BaseSample {
  // Components
  private UISunburst sunburst;
  
  // Attributes
  private String rotation = "off";
  private int startAngle = 90;

  public Sample() {
    // Different default value than base class
    this.labelDisplay = "auto";
  }
  
  public void setSunburst(UISunburst sunburst) {    
    this.sunburst = sunburst;
  }

  public UISunburst getSunburst() {
    return sunburst;
  }

  public String getSelectionState() {
    if(sunburst != null) {
      RowKeySet selectedRowKeys = sunburst.getSelectedRowKeys();
      if(selectedRowKeys != null && selectedRowKeys.size() > 0) {
        StringBuilder s = new StringBuilder();
        s.append("Selected Nodes: ");
        s.append(convertToString(selectedRowKeys, sunburst));
        return s.toString();
      }
    }
    
    return "No Nodes Selected";
  }

  public void setRotation(String rotation) {
    this.rotation = rotation;
  }

  public String getRotation() {
    return rotation;
  }

  public void setStartAngle(int startAngle) {
    this.startAngle = startAngle;
  }

  public int getStartAngle() {
    return startAngle;
  }
}
