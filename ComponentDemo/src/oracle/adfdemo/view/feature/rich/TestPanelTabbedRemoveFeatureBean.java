/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.event.ItemEvent;


public class TestPanelTabbedRemoveFeatureBean
{
  private RichPanelTabbed panelTabbed1; //static
  private RichPanelTabbed panelTabbed2; //dynamic
  private RichPanelTabbed panelTabbed3; //allExceptLast

  public void setPanelTabbed1(RichPanelTabbed panelTabbed1)
  {
    this.panelTabbed1 = panelTabbed1;
  }

  public RichPanelTabbed getPanelTabbed1()
  {
    return panelTabbed1;
  }

  public void setPanelTabbed2(RichPanelTabbed panelTabbed2)
  {
    this.panelTabbed2 = panelTabbed2;
  }

  public RichPanelTabbed getPanelTabbed2()
  {
    return panelTabbed2;
  }

  public void setPanelTabbed3(RichPanelTabbed panelTabbed3)
  {
    this.panelTabbed3 = panelTabbed3;
  }

  public RichPanelTabbed getPanelTabbed3()
  {
    return panelTabbed3;
  }

  /**
   * Remove current selected tab
   * This method can be called as an actionEvent from commandToolbarButton
   */
  public String removeCurrentPanelTab1Tab()
  {
    // get the currently disclosed tab and close it here
    // close is seen on client through use of partialTrigger
    List<UIComponent> children = panelTabbed1.getChildren();
    for (UIComponent child: children)
    {
      RichShowDetailItem tabChild = (RichShowDetailItem) child;
      if (tabChild.isDisclosed())
      {
        tabChild.setVisible(false);
        return null;
      }
    }
    return null;
  }

  public void staticPanelTabItemListener(ItemEvent itemEvent)
  {
    if (itemEvent.getType().equals(ItemEvent.Type.remove))
    {
      Object item = itemEvent.getSource();
      if (item instanceof RichShowDetailItem)
      {
       RichShowDetailItem tabItem = (RichShowDetailItem) item;
       tabItem.setVisible(false);
      }
    }
  }

  /**
   * Search through panelTabbeds children for matching child and remove it. This method can be 
   * called from an itemEventListener. It only works with a static panelTabbed, where children are 
   * known on the server.
   */
  public void removeTab(RichPanelTabbed panelTabbed, String tabId)
  {
    // get the currently disclosed tab and close it here
    // close is seen on client through use of partialTrigger
    List<UIComponent> children = panelTabbed.getChildren();
    for (UIComponent child: children)
    {
      RichShowDetailItem tabChild = (RichShowDetailItem) child;
      if (tabChild.getId().equals(tabId))
      {
        tabChild.setVisible(false);
        return;
      }
    }
  }
  
  /**
   * Finds and removes child by its client Id by calling findComponent. This method can be 
   * called from an itemEventListener. It only works with a static panelTabbed, where children are 
   * known on the server.
   */
  public void removeTab(String clientId)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    UIViewRoot view = context.getViewRoot();
    UIComponent child = view.findComponent(clientId);
    if (child instanceof RichShowDetailItem)
    {
     RichShowDetailItem tabChild = (RichShowDetailItem) child;
     tabChild.setVisible(false);
    }
  }

}


