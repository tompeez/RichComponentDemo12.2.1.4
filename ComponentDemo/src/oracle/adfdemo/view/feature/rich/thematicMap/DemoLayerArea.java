package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.geom.Rectangle2D;

import oracle.adf.view.faces.bi.component.thematicMap.mapProvider.LayerArea;

public class DemoLayerArea extends LayerArea {

  private String id;
  private String shortLabel;
  private String longLabel;
  private Rectangle2D labelBox;
  private String path;
  private String parent;
  
  public DemoLayerArea(String id, String shortLabel, String longLabel, Rectangle2D labelBox, String path, String parent) {
    this.id = id;
    this.shortLabel = shortLabel;
    this.longLabel = longLabel;
    this.labelBox = labelBox;
    this.path = path;
    this.parent = parent;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getShortLabel() {
    return shortLabel;
  }

  @Override
  public String getLongLabel() {
    return longLabel;
  }

  @Override
  public Rectangle2D getLabelBox() {
    return labelBox;
  }
  
  @Override
  public String getPath() {
    return path;
  }
  
  public void setPath(String path) {
    this.path = path;
  }

  @Override
  public String getParent() {
    return parent;
  }
}
