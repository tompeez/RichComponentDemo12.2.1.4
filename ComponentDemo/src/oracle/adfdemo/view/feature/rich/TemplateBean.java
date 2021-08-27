/** Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichButton;

import org.apache.myfaces.trinidad.component.UIXGroup;
import org.apache.myfaces.trinidad.context.RequestContext;

public class TemplateBean
{
  private RichSelectOneRadio appNavigation;
  private Boolean renderNoButton = false;
  private String brandingMaxwidth;
  private RichSelectOneRadio brandingWidth;
  private RichSelectBooleanCheckbox fillMoreContents;
  private RichSelectBooleanCheckbox startWidth;
  private RichSelectBooleanCheckbox endWidth;
  private RichSelectBooleanCheckbox hideFooter;
 
  public void setStartWidth(RichSelectBooleanCheckbox startWidth)
  {
    this.startWidth = startWidth;
  }

  public RichSelectBooleanCheckbox getStartWidth()
  {
    return startWidth;
  }

  public void setEndWidth(RichSelectBooleanCheckbox endWidth)
  {
    this.endWidth = endWidth;
  }

  public RichSelectBooleanCheckbox getEndWidth()
  {
    return endWidth;
  }
  
  public void setRenderNoButton(Boolean renderNoButton)
  {
    this.renderNoButton = renderNoButton;
  }

  public Boolean getRenderNoButton()
  {
    return renderNoButton;
  }

  public void setBrandingMaxwidth(String brandingMaxwidth)
  {
    this.brandingMaxwidth = brandingMaxwidth;
  }

  public String getBrandingMaxwidth()
  {
    return brandingMaxwidth;
  }

  public TemplateBean()
  {
  }

  public void setAppNavigation(RichSelectOneRadio appNavigation)
  {
    this.appNavigation = appNavigation;
  }

  public RichSelectOneRadio getAppNavigation()
  {
    return appNavigation;
  }

  public void setBrandingWidth(RichSelectOneRadio brandingWidth)
  {
    this.brandingWidth = brandingWidth;
  }

  public RichSelectOneRadio getBrandingWidth()
  {
    return brandingWidth;
  }

  public void setHideFooter(RichSelectBooleanCheckbox hideFooter)
  {
    this.hideFooter = hideFooter;
  }

  public RichSelectBooleanCheckbox getHideFooter()
  {
    return hideFooter;
  }

  public void appNavigationChanged(ValueChangeEvent valueChangeEvent)
  {
    if(valueChangeEvent.getSource().equals(getAppNavigation()))
    {
      RichSelectOneRadio radio = (RichSelectOneRadio)valueChangeEvent.getSource();
      if( ! ((String)radio.getValue()).contains("noButton") )
      {
        renderNoButton = false;
      }
      else
      {
        renderNoButton = true;
      }
    }
  }

  public void setFillMoreContents(RichSelectBooleanCheckbox fillMoreContents)
  {
    this.fillMoreContents = fillMoreContents;
  }

  public RichSelectBooleanCheckbox getFillMoreContents()
  {
    return fillMoreContents;
  }

  public void buttonClicked(ActionEvent actionEvent)
  {
    RequestContext adfContext = RequestContext.getCurrentInstance();
    RichButton button = (RichButton)actionEvent.getSource();
    
    RichPanelGroupLayout pgLayout = (RichPanelGroupLayout)button.getParent();
    for(UIComponent comp:pgLayout.getChildren())
    {
      ((RichButton)comp).setStyleClass("AFAppNavbarButton");
    }
    button.setStyleClass(button.getStyleClass() + " p_AFSelected");
    adfContext.addPartialTarget(button.getParent());
  }

}
