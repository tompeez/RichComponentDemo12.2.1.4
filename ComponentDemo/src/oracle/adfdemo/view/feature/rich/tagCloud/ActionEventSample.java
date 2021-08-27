package oracle.adfdemo.view.feature.rich.tagCloud;

import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.tagCloud.UITagCloud;
import oracle.adf.view.faces.bi.component.tagCloud.UITagCloudItem;

import oracle.adfdemo.view.feature.rich.tagCloud.data.TagCloudData;

import org.apache.myfaces.trinidad.model.CollectionModel;

public class ActionEventSample {
  private UITagCloud _tagCloud;
  private CollectionModel _tagCloudItems;
  private String _action = "feature.tagCloudFeatures";
  private String _actionString; 
  
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
  
  private void createModel() {
    if(_tagCloudItems == null) {
      _tagCloudItems = TagCloudData.getInstance().getSocialNetworksData();
    }
  }
  
  public void setAction(String action) {
    _action = action;
  }
  
  public String getAction() {
    return _action;
  }

  public void actionListener(ActionEvent actionEvent) {
    UITagCloudItem item = (UITagCloudItem)actionEvent.getSource();
    _actionString = "  Action Listener triggered on: <b>" + item.getLabel() + "<\b>";
  }
  
  public String getActionListenerString(){
    return _actionString;
  }
  
  public void setActionListenerString(String text){
    _actionString = text;
  }
}
