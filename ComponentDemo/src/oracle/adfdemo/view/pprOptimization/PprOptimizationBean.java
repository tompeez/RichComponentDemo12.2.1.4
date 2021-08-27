/** Copyright (c) 2009, 2014, Oracle and/or its affiliates. All rights reserved. */

package oracle.adfdemo.view.pprOptimization;

import java.io.Serializable;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adfdemo.view.components.rich.DemoComponentSkin;

public class PprOptimizationBean implements Serializable
{
  public PprOptimizationBean()
  {
    setPprOptimizationEnabled(true);
  }

  public boolean isPprOptimizationEnabled()
  {
    String pprOptimization = (String)_getApplicationMap().get(_PPR_OPTIMIZATION_PROP);
      
    return "on".equalsIgnoreCase(pprOptimization);
  }

  public void setPprOptimizationEnabled(boolean enabled)
  {
    _getApplicationMap().put(_PPR_OPTIMIZATION_PROP, (enabled) ? "on" : "off");
  }

  public void pprOptimizationMenuAction(ActionEvent ae)
  {
    setPprOptimizationEnabled(!isPprOptimizationEnabled());
    DemoComponentSkin.reloadThePage();
  }
  
  private Map<String, Object> _getApplicationMap()
  {
    return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
  }

  private static final String _PPR_OPTIMIZATION_PROP = 
                                                    "org.apache.myfaces.trinidad.PPR_OPTIMIZATION";
  private static final long serialVersionUID = 1L;
}
