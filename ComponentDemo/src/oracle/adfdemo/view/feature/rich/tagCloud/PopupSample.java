package oracle.adfdemo.view.feature.rich.tagCloud;

import java.util.Map;


import oracle.adf.view.faces.bi.component.tagCloud.UITagCloud;

import oracle.adfdemo.view.feature.rich.tagCloud.data.TagCloudData;

import org.apache.myfaces.trinidad.model.CollectionModel;

public class PopupSample {
  private UITagCloud _tagCloud;
  private CollectionModel _tagCloudItems;
  private String selectedWord;

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
    if (_tagCloudItems == null) {
      _tagCloudItems = TagCloudData.getInstance().getSocialNetworksData();
    }
  }

  public void setSelectedWord(String _selectedWord) {
    this.selectedWord = _selectedWord;
  }

  public String getSelectedWord() {
    return this.selectedWord;
  }
}
