/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import javax.faces.component.UIComponent;

import javax.faces.event.ValueChangeEvent;

import org.apache.myfaces.trinidad.context.RequestContext;

public class DemoPanelStretchLayoutBean
{
  public UIComponent getTopFacet()
  {
    return _topFacet;
  } 
  
  public void setTopFacet(UIComponent component)
  {
    _topFacet = component;
  }

  public UIComponent getStartFacet()
  {
    return _startFacet;
  } 
  
  public void setStartFacet(UIComponent component)
  {
    _startFacet = component;
  }  
  
  public UIComponent getCenterFacet()
  {
    return _centerFacet;
  } 
  
  public void setCenterFacet(UIComponent component)
  {
    _centerFacet = component;
  }

  public UIComponent getEndFacet()
  {
    return _endFacet;
  } 
  
  public void setEndFacet(UIComponent component)
  {
    _endFacet = component;
  }

  public UIComponent getBottomFacet()
  {
    return _bottomFacet;
  } 
  
  public void setBottomFacet(UIComponent component)
  {
    _bottomFacet = component;
  }

  public UIComponent getTop2Facet()
  {
    return _top2Facet;
  } 
  
  public void setTop2Facet(UIComponent component)
  {
    _top2Facet = component;
  }

  public UIComponent getStart2Facet()
  {
    return _start2Facet;
  } 
  
  public void setStart2Facet(UIComponent component)
  {
    _start2Facet = component;
  }  
  
  public UIComponent getCenter2Facet()
  {
    return _center2Facet;
  } 
  
  public void setCenter2Facet(UIComponent component)
  {
    _center2Facet = component;
  }

  public UIComponent getEnd2Facet()
  {
    return _end2Facet;
  } 
  
  public void setEnd2Facet(UIComponent component)
  {
    _end2Facet = component;
  }

  public UIComponent getBottom2Facet()
  {
    return _bottom2Facet;
  } 
  
  public void setBottom2Facet(UIComponent component)
  {
    _bottom2Facet = component;
  }

  public String getTimestamp()
  {
    return "" + System.currentTimeMillis();
  }

  public void hideTop()
  {
    _assignVisible(_topFacet, false);
    _assignVisible(_top2Facet, false);
  }

  public void hideStart()
  {
    _assignVisible(_startFacet, false);
    _assignVisible(_start2Facet, false);
  }

  public void hideCenter()
  {
    _assignVisible(_centerFacet, false);
    _assignVisible(_center2Facet, false);
  }

  public void hideEnd()
  {
    _assignVisible(_endFacet, false);
    _assignVisible(_end2Facet, false);
  }

  public void hideBottom()
  {
    _assignVisible(_bottomFacet, false);
    _assignVisible(_bottom2Facet, false);
  }

  public void showTop()
  {
    _assignVisible(_topFacet, true);
    _assignVisible(_top2Facet, true);
  }

  public void showStart()
  {
    _assignVisible(_startFacet, true);
    _assignVisible(_start2Facet, true);
  }

  public void showEnd()
  {
    _assignVisible(_endFacet, true);
    _assignVisible(_end2Facet, true);
  }

  public void showBottom()
  {
    _assignVisible(_bottomFacet, true);
    _assignVisible(_bottom2Facet, true);
  }

  public void visibilityModeChanged(ValueChangeEvent event)
  {
    if ((event.getOldValue() == null) && event.getNewValue().equals(false))
    {
      // false alarm
      return;
    }
    resetVisibilities();
  }

  public void resetVisibilities()
  {
    // Reset the visible valueExpressions back to their original settings:
    _assignVisible(_topFacet, true);
    _assignVisible(_startFacet, true);
    _assignVisible(_centerFacet, true);
    _assignVisible(_endFacet, true);
    _assignVisible(_bottomFacet, true);

    _assignVisible(_top2Facet, true);
    _assignVisible(_start2Facet, true);
    _assignVisible(_center2Facet, true);
    _assignVisible(_end2Facet, true);
    _assignVisible(_bottom2Facet, true);
  }

  private void _assignVisible(UIComponent component, boolean visible)
  {
    component.getAttributes().put("visible", visible);
  }


  private UIComponent _topFacet;
  private UIComponent _startFacet;
  private UIComponent _centerFacet;
  private UIComponent _endFacet;
  private UIComponent _bottomFacet;  
  private UIComponent _top2Facet;
  private UIComponent _start2Facet;
  private UIComponent _center2Facet;
  private UIComponent _end2Facet;
  private UIComponent _bottom2Facet;
}
