package oracle.adfdemo.view.visualdesigns;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.config.ConfigPropertyService;
import org.apache.myfaces.trinidad.config.TestPropertyValueProvider;
import org.apache.myfaces.trinidad.context.RequestContext;

public class VerticalPanelTabbedBean implements Serializable
{
  
  public void displayToggleIcon(ActionEvent event)
  {
  	RequestContext requestContext = RequestContext.getCurrentInstance();
    Map<String, Object> applicationMap = requestContext.getApplicationScopedConcurrentMap();
    applicationMap.put(_SHOW_TAB_TEXT_TOGGLE_BUTTON_PARAM, "true");

    RequestContext.getCurrentInstance().addPartialTarget(getPanelTabbedBinding());
  }

  public void hideToggleIcon(ActionEvent event)
  {
  	RequestContext requestContext = RequestContext.getCurrentInstance();
    Map<String, Object> applicationMap = requestContext.getApplicationScopedConcurrentMap();
    applicationMap.put(_SHOW_TAB_TEXT_TOGGLE_BUTTON_PARAM, "false");

    RequestContext.getCurrentInstance().addPartialTarget(getPanelTabbedBinding());
  }


  public void setPanelTabbedBinding(RichPanelTabbed panelTabbedBinding) 
  {
    _panelTabbedBinding = panelTabbedBinding;
  }

  public RichPanelTabbed getPanelTabbedBinding() {
  	return this._panelTabbedBinding;
  }

  private RichPanelTabbed _panelTabbedBinding;

  private static final String _SHOW_TAB_TEXT_TOGGLE_BUTTON_PARAM = "oracle.adf.view.rich.PANEL_TABBED_SHOW_TOGGLE_TEXT_ICON";
}