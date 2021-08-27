/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.layout;

import java.io.Serializable;

import java.util.Arrays;

import java.util.List;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.component.rich.layout.RichDecorativeBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelSpringboard;

import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;

import org.apache.myfaces.trinidad.context.Agent;
import org.apache.myfaces.trinidad.context.RequestContext;
import oracle.adf.view.rich.event.SpringboardChangeEvent;

import org.apache.myfaces.trinidad.event.DisclosureEvent;


/**
 * Managed bean for springboard demo.
 */
public class DemoSpringboardBean implements Serializable
{
  /**
   * Default constructor.
   */
  public DemoSpringboardBean()
  {

  }

  public void logoutKillSession(ActionEvent event) 
  {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
    session.invalidate();
  }
  
  // Waits for 2 seconds so that the animation can complete.
  // This is a temporary solution.
  // TODO delete once we implement the springboard correctly:
  // 1. strip does not get PPR'd
  // 2. content gets fetched, and we hold on to it until the animation is complete.
  public void wait(DisclosureEvent event)
  {
    // Sleep so we can see the animation occur.
    System.out.println("DemoSpringboardBean wait 2000 ms. Make the animation faster than this. See fun1.css");
    try
    {
      Thread.sleep(2000);
    }
    catch (InterruptedException e)
    {
    }
  } 
  
  public String getDisplayMode()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    RichPanelSpringboard springboard = (RichPanelSpringboard)context.getViewRoot().findComponent("panelSpringboardId");
    String displayMode = springboard.getDisplayMode();
    System.out.println(displayMode);
    return displayMode;
  }
  
  public String getHelloWorld()
  {
    return "Hello World";
  }

  public final List<String> getIconCss()
  {
    return _sIconCss;
  }

  public String getSelectedName()
  {
    return _selectedName;
  }

  public void setSelectedName(String selectedName)
  {
    _selectedName = selectedName;
  }

  private String _selectedName;

  private static final List<String> _sIconCss = Arrays.asList(new String[]
      { "/images/springboard_opportunities.png",
        "/images/springboard_leads.png",
        "/images/springboard_customers.png",
        "/images/springboard_sales.png", "/images/springboard_social.png",
        "/images/springboard_directory.png",
        "/images/springboard_time.png", "/images/springboard_expenses.png",
        "/images/springboard_personal.png",
        "/images/springboard_career.png" });


  
  //TODO from here down used on the springboard app demo, move to new file?
  public void themeMenuAction(ActionEvent ae)
  {
    RichCommandMenuItem menuItem = (RichCommandMenuItem) ae.getComponent();
    _theme = menuItem.getText();
  }

  public String getTheme()
  {
    return _theme;
  }
  public String getAppStyleClass()
  {
    RequestContext context = RequestContext.getCurrentInstance();
    Agent agent = context.getAgent();
    String platformName = agent.getPlatformName(); 
    String displayMode = getAppDisplayMode();
    
    if(displayMode.equals("strip"))
    {
      if("iphone".equalsIgnoreCase(platformName))
      { 
        return "springboard-strip-mode-size-touch";
      }
      else
      {
        return "springboard-strip-mode-size";
      }
    }
    else  
    {
      if("iphone".equalsIgnoreCase(platformName))
      { 
        return "springboard-grid-mode-size-touch";
      }
      else 
      {
        return "springboard-grid-mode-size";
      }
    }
  }
  
  public String getAppDisplayMode()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    RichPanelSpringboard springboard = (RichPanelSpringboard)context.getViewRoot().findComponent(_springboardId);

    String displayMode = springboard.getDisplayMode();
    return displayMode;
  }

  public void immediateMenuAction(ActionEvent ae)
  {
    RichCommandMenuItem menuItem = (RichCommandMenuItem) ae.getComponent();
    _immediate = Boolean.valueOf(menuItem.getText());
  }
  
  public void setImmediate(boolean _immediate)
  {
    this._immediate = _immediate;
  }

  public boolean isImmediate()
  {
    return _immediate;
  }
  
  public void springboardChangeListener(SpringboardChangeEvent evt)
  {
    System.out.println("springboard listener new displayMode = " + evt.getNewDisplayMode());
  }
  
  public void disclosureListener(DisclosureEvent evt)
  {
    System.out.println("disclosure = " + evt);
  }
  
  private boolean _immediate = false;
  String _theme = "dark";
  String _springboardId = "object-navigator";
  
  private static final long serialVersionUID = 1L;

}
