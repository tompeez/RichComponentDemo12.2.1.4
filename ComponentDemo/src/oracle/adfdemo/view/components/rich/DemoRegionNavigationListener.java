/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;

import javax.faces.event.ActionListener;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.RegionNavigationEvent;
import oracle.adf.view.rich.event.RegionNavigationListener;

/**
 * Demo implementation of the RegionNavigationListener.
 */
public class DemoRegionNavigationListener implements RegionNavigationListener                                                     
{
  /**
   * Default constructor.
   */
  public DemoRegionNavigationListener()
  {
  }

  /**
   * Implementation of the RegionNavigationEvent listener.
   * @param event the RegionNavigationEvent
   */
  public void processRegionNavigation(RegionNavigationEvent event)
  {
    _oldViewId = event.getOldViewId();
    _newViewId = event.getNewViewId();
    AdfFacesContext afc = AdfFacesContext.getCurrentInstance();
    afc.addPartialTarget(_oldViewIdComponent);
    afc.addPartialTarget(_newViewIdComponent);
  }

  /**
   * Retrieves the most recent old view ID.
   * @return the old view ID
   */
  public String getOldViewId()
  {
    if (_oldViewId == null)
    {
      return "n/a";
    }
    return _oldViewId;
  }

  /**
   * Retrieves the most recent new view ID.
   * @return the new view ID
   */
  public String getNewViewId()
  {
    if (_newViewId == null)
    {
      return "n/a";
    }
    return _newViewId;
  }

  /**
   * Specifies the component that displays the most recent old view ID.
   * @param component the component that displays the most recent old view ID
   */
  public void setOldViewIdComponent(UIComponent component)
  {
    this._oldViewIdComponent = component;
  }

  /**
   * Retrieves the component that displays the most recent old view ID.
   * @return the component that displays the most recent old view ID
   */
  public UIComponent getOldViewIdComponent()
  {
    return _oldViewIdComponent;
  }

  /**
   * Specifies the component that displays the most recent new view ID.
   * @param component the component that displays the most recent new view ID
   */
  public void setNewViewIdComponent(UIComponent component)
  {
    this._newViewIdComponent = component;
  }

  /**
   * Retrieves the component that displays the most recent new view ID.
   * @return the component that displays the most recent new view ID
   */
  public UIComponent getNewViewIdComponent()
  {
    return _newViewIdComponent;
  }
  
  private String _oldViewId;
  private String _newViewId;
  private UIComponent _oldViewIdComponent;
  private UIComponent _newViewIdComponent;
}
