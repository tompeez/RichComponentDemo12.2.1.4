/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.io.Serializable;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.util.FacesMessageUtils;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.bean.PropertyKey;
import org.apache.myfaces.trinidad.component.UIXComponent;
import org.apache.myfaces.trinidad.component.UIXNavigationHierarchy;
import org.apache.myfaces.trinidad.context.RequestContext;

public class DemoCommandNavigationItemBean implements Serializable
{
  /**
   * Changes the selected state of all of the navigation items in the
   * parent component so that the clicked navigation item becomes
   * selected and the others become deselected.
   * @param event the ActionEvent associated with the action
   */
  public void navigationItemAction(ActionEvent event)
  {
    UIComponent actionItem = event.getComponent();
    UIComponent parent = actionItem.getParent();
    while (! (parent instanceof UIXNavigationHierarchy) )
    {
      parent = parent.getParent();
      if (parent == null)
      {
        System.err.println(
          "Unexpected component hierarchy, no UIXNavigationHierarchy found.");
        return;
      }
    }

    List<UIComponent> children = parent.getChildren();
    for (UIComponent child : children)
    {
      FacesBean childFacesBean = ((UIXComponent) child).getFacesBean();
      FacesBean.Type type = childFacesBean.getType();
      PropertyKey selectedKey = type.findKey("selected");
      if (selectedKey != null)
      {
        childFacesBean.setProperty(selectedKey, (child == actionItem));
      }
    }

    RequestContext adfContext = RequestContext.getCurrentInstance();
    adfContext.addPartialTarget(parent);
  }

  /**
   * Retrieves a slash that is drawn from start-bottom to end-top.
   * @return a slash that is drawn from start-bottom to end-top
   */
  public String getBidiForwardSlash()
  {
    RequestContext rc = RequestContext.getCurrentInstance();
    if (rc.isRightToLeft())
    {
      return "\\";
    }
    return "/";
  }

  /**
   * Retrieves a slash that is drawn from start-top to end-bottom.
   * @return a slash that is drawn from start-top to end-bottom
   */
  public String getBidiBackSlash()
  {
    RequestContext rc = RequestContext.getCurrentInstance();
    if (rc.isRightToLeft())
    {
      return "/";
    }
    return "\\";
  }

  /**
   * Handler for the sample tab close button.
   * @param event the ActionEvent associated with the action
   */
  public void closeTab(ActionEvent event)
  {
    FacesContext.getCurrentInstance().addMessage(
      null,
      FacesMessageUtils.getConfirmationMessage(
        "Close Tab",
        "You clicked the sample tab close button."));
  }

  /**
   * Handler for the sample tab creation button.
   * @param event the ActionEvent associated with the action
   */
  public void createTab(ActionEvent event)
  {
    FacesContext.getCurrentInstance().addMessage(
      null,
      FacesMessageUtils.getConfirmationMessage(
        "Create Tab",
        "You clicked the sample tab create button."));
  }

  public String getTabCloseImageNormal()
  {
    return "/images/closeDialog_n.png";
  }

  public String getTabCloseImageHover()
  {
    return "/images/closeDialog_mo.png";
  }

  public String getTabCloseImageDepressed()
  {
    return "/images/closeDialog_md.png";
  }

  public String getTabCloseImageDisabled()
  {
    return "/images/closeDialog_d.png";
  }

  public String getTabCreateImageNormal()
  {
    RequestContext rc = RequestContext.getCurrentInstance();
    if (rc.isRightToLeft())
    {
      return "/images/new_ena_rtl.png";
    }
    return "/images/new_ena.png";
  }

  public String getTabCreateImageHover()
  {
    RequestContext rc = RequestContext.getCurrentInstance();
    if (rc.isRightToLeft())
    {
      return "/images/new_ovr_rtl.png";
    }
    return "/images/new_ovr.png";
  }

  public String getTabCreateImageDepressed()
  {
    RequestContext rc = RequestContext.getCurrentInstance();
    if (rc.isRightToLeft())
    {
      return "/images/new_dwn_rtl.png";
    }
    return "/images/new_dwn.png";
  }

  public String getTabCreateImageDisabled()
  {
    RequestContext rc = RequestContext.getCurrentInstance();
    if (rc.isRightToLeft())
    {
      return "/images/new_dis_rtl.png";
    }
    return "/images/new_dis.png";
  }

  private static final long serialVersionUID = 1L;
}
