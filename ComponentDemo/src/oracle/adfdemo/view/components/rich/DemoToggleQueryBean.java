/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import javax.faces.event.ActionEvent;

public class DemoToggleQueryBean
{
  
  public void quick2Advanced(ActionEvent event)
  {
    _quickQueryVisible = !_quickQueryVisible;
  }

  public void advanced2Quick(ActionEvent event)
  {
    _quickQueryVisible = !_quickQueryVisible;
  }
      
  public boolean isQuickQueryVisible()
  {
    return _quickQueryVisible;
  }

  public boolean isQueryVisible()
  {
    return !_quickQueryVisible;
  }
    
  public String getVisibleComponent()
  {
    if(_quickQueryVisible)
      return "quick";
    else 
      return "advanced";
  }

  private boolean _quickQueryVisible = true;
}
