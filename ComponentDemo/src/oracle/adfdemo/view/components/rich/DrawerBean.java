/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;

import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;

import org.apache.myfaces.trinidad.change.AttributeComponentChange;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;


public class DrawerBean
{
  

  public void themeMenuAction(ActionEvent ae)
  {
    RichCommandMenuItem menuItem = (RichCommandMenuItem) ae.getComponent();
    _theme = menuItem.getText();
  }

  public String getTheme()
  {
    return _theme;
  }

  String _theme = "dark";
  
  /**
   * @param event the ActionEvent associated with the action
   */
  public void toggle(ActionEvent event)
  {
    UIComponent comp = event.getComponent();
    _closeItem(comp, "sdi1");
    _closeItem(comp, "sdi2");
    _closeItem(comp, "sdi3");
    
    RichShowDetailItem item = (RichShowDetailItem)comp.findComponent("sdi4");
    boolean disclosed = item.isDisclosed();
    _setItemValue(item, !disclosed);
  }
  
  private void _closeItem(UIComponent eventComp, String id)
  {
    RichShowDetailItem item = (RichShowDetailItem)eventComp.findComponent(id);
    
    if (item.isDisclosed())
    {
      _setItemValue(item, false);
    }
  }
    
  private void _setItemValue(RichShowDetailItem item, boolean value)
  {
    item.setDisclosed(value);
    AttributeComponentChange aa = new AttributeComponentChange("disclosed", Boolean.valueOf(value));
    RequestContext adfContext = RequestContext.getCurrentInstance();
    adfContext.getChangeManager().addComponentChange(FacesContext.getCurrentInstance(), item, aa);
  }
  

  public void setWidth(String _width)
  {
    this._width = _width;
  }

  public String getWidth()
  {
    return _width;
  }  
  
  public void widthListener(DisclosureEvent de)
  {
    if (de.isExpanded())
    {
      RichShowDetailItem di = (RichShowDetailItem)de.getSource();
      boolean stretchChildren = RichShowDetailItem.STRETCH_CHILDREN_FIRST.equals(di.getStretchChildren());
      
      if (stretchChildren)
      {
        setWidth("75%");
      }
      else
      {

        setWidth(null);
      }
    }
  }
  private String _width = null;
}
