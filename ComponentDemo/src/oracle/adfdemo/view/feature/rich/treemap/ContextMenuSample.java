package oracle.adfdemo.view.feature.rich.treemap;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.treemap.UITreemap;
import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;

import org.apache.myfaces.trinidad.context.RequestContext;


public class ContextMenuSample implements Serializable {
  private UITreemap treemap;
  private String status;
  private RichOutputFormatted outputFormatted;

  public ContextMenuSample() {
  }

  public void setTreemap(UITreemap treemap) {
    this.treemap = treemap;
  }

  public UITreemap getTreemap() {
    return treemap;
  }

  public String getSelectionState() {
    if (treemap != null) {
      return Sample.convertToString(treemap.getSelectedRowKeys(), treemap);
    } else
      return null;
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

  /**
   * Called when a commandMenuItem is clicked.  Updates the outputText with information about the menu item clicked.
   * @param actionEvent
   */
  public void menuItemListener(ActionEvent actionEvent) {
    UIComponent component = actionEvent.getComponent();
    if (component instanceof RichCommandMenuItem) {
      RichCommandMenuItem cmi = (RichCommandMenuItem)component;

      // Add the text of the item into the status message
      StringBuilder s = new StringBuilder();
      s.append("You clicked on \"").append(cmi.getText()).append("\".  <br><br>");
      this.status = s.toString();

      // Update the status text component
      RequestContext.getCurrentInstance().addPartialTarget(this.outputFormatted);
    }
  }
}
