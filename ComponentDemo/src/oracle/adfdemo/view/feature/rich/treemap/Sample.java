package oracle.adfdemo.view.feature.rich.treemap;

import oracle.adf.view.faces.bi.component.treemap.UITreemap;

import org.apache.myfaces.trinidad.model.RowKeySet;


public class Sample extends BaseSample {
  // Component Attrs
  private String layout = "squarified";
  private String groupLabelDisplay = "header";
  private String textHAlign = "center";
  private String textVAlign = "center";
  private String titleHAlign = "start";
  private String groupLabelStyle;
  private String isolate = "on";
  private String groupGaps = "outer";
  private String useNodeColor = "off";

  // Components
  private UITreemap treemap;

  public void setLayout(String layout) {
    this.layout = layout;
  }

  public String getLayout() {
    return layout;
  }

  public void setGroupLabelDisplay(String groupLabelDisplay) {
    this.groupLabelDisplay = groupLabelDisplay;
  }

  public String getGroupLabelDisplay() {
    return groupLabelDisplay;
  }


  public void setTextHAlign(String textHAlign) {
    this.textHAlign = textHAlign;
  }

  public String getTextHAlign() {
    return textHAlign;
  }

  public void setTextVAlign(String textVAlign) {
    this.textVAlign = textVAlign;
  }

  public String getTextVAlign() {
    return textVAlign;
  }

  public void setTitleHAlign(String titleHAlign) {
    this.titleHAlign = titleHAlign;
  }

  public String getTitleHAlign() {
    return titleHAlign;
  }

  public void setGroupLabelStyle(String groupLabelStyle) {
    this.groupLabelStyle = groupLabelStyle;
  }

  public String getGroupLabelStyle() {
    return groupLabelStyle;
  }
  
  public void setIsolate(String isolate) {
    this.isolate = isolate;
  }

  public String getIsolate() {
    return isolate;
  }

  public void setTreemap(UITreemap treemap) {
    this.treemap = treemap;
  }

  public UITreemap getTreemap() {
    return treemap;
  }

  public String getSelectionState() {
    if (treemap != null) {
      RowKeySet selectedRowKeys = treemap.getSelectedRowKeys();
      if (selectedRowKeys != null && selectedRowKeys.size() > 0) {
        StringBuilder s = new StringBuilder();
        s.append("Selected Nodes: ");
        s.append(convertToString(selectedRowKeys, treemap));
        return s.toString();
      }
    }

    return "No Nodes Selected";
  }

  public void setGroupGaps(String groupGaps) {
    this.groupGaps = groupGaps;
  }

  public String getGroupGaps() {
    return groupGaps;
  }

  public void setUseNodeColor(String useNodeColor) {
    this.useNodeColor = useNodeColor;
  }

  public String getUseNodeColor() {
    return useNodeColor;
  }
}
