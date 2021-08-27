/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich;

import java.beans.IntrospectionException;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;

import java.util.List;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.nav.RichCommandNavigationItem;
import oracle.adf.view.rich.component.rich.nav.RichNavigationPane;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.ItemEvent;

import oracle.adfdemo.view.table.rich.AtomBean;


public class TestNavigationPaneItemRemoveFeatureBean
{
  private RichNavigationPane navPane1; //static

  public void setNavPane1(RichNavigationPane navPane1)
  {
    this.navPane1 = navPane1;
  }

  public RichNavigationPane getNavPane1()
  {
    return navPane1;
  }


  /**
   * Remove current selected tab. Called by test file. Requires binding navPane as navPane1.
   * This method can be called as an actionEvent from commandToolbarButton
   */
  public String removeCurrentPanelTab1Tab()
  {
    // get the currently disclosed tab and close it here
    // close is seen on client through use of partialTrigger
    List<UIComponent> children = navPane1.getChildren();
    for (UIComponent child: children)
    {
      RichCommandNavigationItem tabChild = (RichCommandNavigationItem) child;
      if (tabChild.isSelected())
      {
        tabChild.setVisible(false);
        return null;
      }
    }
    return null;
  }

  /**
   * navPane item event listener. Used by test file.
   */
  public void staticNavPaneTabItemListener(ItemEvent itemEvent)
  {
    if (itemEvent.getType().equals(ItemEvent.Type.remove))
    {
      Object item = itemEvent.getSource();
      if (item instanceof RichCommandNavigationItem)
      {
       RichCommandNavigationItem tabItem = (RichCommandNavigationItem) item;
       tabItem.setVisible(false);
      }
    }
  }
}


