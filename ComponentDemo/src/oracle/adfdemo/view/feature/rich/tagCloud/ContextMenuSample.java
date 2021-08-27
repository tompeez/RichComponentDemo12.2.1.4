package oracle.adfdemo.view.feature.rich.tagCloud;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.tagCloud.UITagCloud;

import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;

import oracle.adfdemo.view.feature.rich.tagCloud.data.TagCloudData;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;

public class ContextMenuSample {
  private UITagCloud _tagCloud;
  private CollectionModel _tagCloudItems;
  private String selectedTagMenu;
  private String status;
  private RichOutputFormatted outputFormatted;

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

  public String getStatus() {
    return status;
  }

  public void setOutputFormatted(RichOutputFormatted outputFormatted) {
    this.outputFormatted = outputFormatted;
  }

  public RichOutputFormatted getOutputFormatted() {
    return outputFormatted;
  }

  private void createModel() {
    if (_tagCloudItems == null) {
      _tagCloudItems = TagCloudData.getInstance().getSocialNetworksData();
    }
  }

  public void setSelectedTagMenu(String _selectedTagMenu) {
    //Get the current data from the model to access the JSON map
    Map rowData = (Map) _tagCloud.getCurrentRowData();
    if (rowData != null) {
      this.selectedTagMenu = (String) rowData.get("word");
    }
  }

  public String getSelectedTagMenu() {
    //return selected menu item
    return this.selectedTagMenu;
  }

  /**
   * Called when a commandMenuItem is clicked.  Updates the outputText with information about the menu item clicked.
   * @param actionEvent
   */
  public void menuItemListener(ActionEvent actionEvent) {
    UIComponent component = actionEvent.getComponent();
    if (component instanceof RichCommandMenuItem) {
      RichCommandMenuItem cmi = (RichCommandMenuItem) component;

      // Add the text of the item into the status message
      StringBuilder s = new StringBuilder();
      s.append("You clicked on <b> ").append(cmi.getText()).append(" </b>.  <br><br>");
      this.status = s.toString();

      // Update the status text component
      RequestContext.getCurrentInstance().addPartialTarget(this.outputFormatted);
    }
  }

}
