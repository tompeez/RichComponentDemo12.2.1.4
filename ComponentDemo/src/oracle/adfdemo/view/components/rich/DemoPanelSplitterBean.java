/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.myfaces.trinidad.context.RequestContext;

public class DemoPanelSplitterBean implements Serializable
{
  public UIComponent getFirstFacet()
  {
    FacesContext fc = FacesContext.getCurrentInstance();
    return fc.getViewRoot().findComponent("dmoTpl:firstChild");
  }

  public UIComponent getSecondFacet()
  {
    FacesContext fc = FacesContext.getCurrentInstance();
    return fc.getViewRoot().findComponent("dmoTpl:secondChild");
  }

  public boolean getUpdateFirst()
  {
    return _updateFirst;
  }

  public void setUpdateFirst(boolean value)
  {
    _updateFirst = value;
  }

  public boolean getUpdateSecond()
  {
    return _updateSecond;
  }

  public void setUpdateSecond(boolean value)
  {
    _updateSecond = value;
  }

  public boolean getFirstRendered()
  {
    return _firstRendered;
  }

  public void setFirstRendered(boolean value)
  {
    _firstRendered = value;
  }

  public boolean getSecondRendered()
  {
    return _secondRendered;
  }

  public void setSecondRendered(boolean value)
  {
    _secondRendered = value;
  }

  public boolean getFirstVisible()
  {
    return _firstVisible;
  }

  public void setFirstVisible(boolean value)
  {
    UIComponent firstFacet = getFirstFacet();
    _firstVisible = value;
    firstFacet.getAttributes().put("visible", _firstVisible);
    RequestContext context = RequestContext.getCurrentInstance();
    context.addPartialTarget(firstFacet);
  }

  public boolean getSecondVisible()
  {
    return _secondVisible;
  }

  public void setSecondVisible(boolean value)
  {
    UIComponent secondFacet = getSecondFacet();
    _secondVisible = value;
    secondFacet.getAttributes().put("visible", _secondVisible);
    RequestContext context = RequestContext.getCurrentInstance();
    context.addPartialTarget(secondFacet);
  }

  public void updateFacets()
  {
    RequestContext context = RequestContext.getCurrentInstance();

    UIComponent firstFacet = getFirstFacet();
    UIComponent secondFacet = getSecondFacet();
    if (_updateFirst)
      context.addPartialTarget(firstFacet);
    if (_updateSecond)
      context.addPartialTarget(secondFacet);
  }

  public void visibilityModeChanged(ValueChangeEvent event)
  {
    if ((event.getOldValue() == null) && event.getNewValue().equals(false))
    {
      // false alarm
      return;
    }
    _firstVisible = true;
    _secondVisible = true;
    UIComponent firstFacet = getFirstFacet();
    UIComponent secondFacet = getSecondFacet();
    firstFacet.getAttributes().put("visible", _firstVisible);
    secondFacet.getAttributes().put("visible", _secondVisible);
    RequestContext context = RequestContext.getCurrentInstance();
    context.addPartialTarget(firstFacet.getParent().getParent());
  }

  private boolean _updateFirst;
  private boolean _updateSecond;

  private boolean _firstRendered  = true;
  private boolean _secondRendered = true;

  private boolean _firstVisible  = true;
  private boolean _secondVisible = true;

  @SuppressWarnings("compatibility:3368269413117329234")
  private static final long serialVersionUID = 1L;
}
