package oracle.adfdemo.view.feature.rich.tagCloud;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

import oracle.adf.view.faces.bi.component.tagCloud.UITagCloud;
import oracle.adf.view.faces.bi.component.tagCloud.UITagCloudItem;

import oracle.adfdemo.view.feature.rich.tagCloud.data.TagCloudData;

import org.apache.myfaces.trinidad.model.CollectionModel;

public class AttributeGroupsSample {
  private UITagCloud _tagCloud;
  private CollectionModel _tagCloudItems;
  private String _hideAndShowBehavior = "none";
  private String _hoverBehavior = "none";
  private boolean _legendSource = true;

  
  public void setTagCloud(UITagCloud tagCloud) {
    this._tagCloud = tagCloud;
  }
  
  public UITagCloud getTagCloud() {
    return this._tagCloud;
  }
  
  public void setItems(CollectionModel tagCloudItems) {
    this._tagCloudItems = tagCloudItems;
  }
  
  public CollectionModel getModel() {
    return this.getItems();
  }
  
  public CollectionModel getItems() {
    createModel();
    return this._tagCloudItems;
  }
  
  public String getHideAndShowBehavior() {
    return _hideAndShowBehavior;
  }

  public void setHideAndShowBehavior(String hideAndShowBehavior) {
    _hideAndShowBehavior = hideAndShowBehavior;
  }

  public String getHoverBehavior() {
    return _hoverBehavior;
  }

  public void setHoverBehavior(String hoverBehavior) {
    _hoverBehavior = hoverBehavior;
  }
  
  public boolean getLegendSource() {
    return _legendSource;
  }
  
  public void setLegendSource(boolean legendSource) {
    _legendSource = legendSource;
  }

  private void createModel() {
    if(_tagCloudItems == null) {
      _tagCloudItems = TagCloudData.getInstance().getCensusData();
    }
  }
  
  public void reset(DisclosureEvent actionEvent) {
    _tagCloud = null;
    _tagCloudItems = null;
  }
}
