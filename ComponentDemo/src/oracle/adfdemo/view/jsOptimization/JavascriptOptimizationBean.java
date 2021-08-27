/** Copyright (c) 2009, 2014, Oracle and/or its affiliates. All rights reserved. */

package oracle.adfdemo.view.jsOptimization;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class JavascriptOptimizationBean implements Serializable
{
  
  public String getCurrent()
  {
    return _optimization;
  }
  
  public void setSimple(ActionEvent ae) throws IOException
  {
    if (_optimization == _SIMPLE)
    {
      return;
    }
    _optimization = _SIMPLE;
    _issueRedirect();
  }
  
  public boolean isSimpleEnabled() 
  {
    if (_simpleExists == null)
    {
      _simpleExists = _resourcePrefixExits("jsopt/");
    }
    return _simpleExists;   
  }
  
  public boolean isAdvancedEnabled() 
  {
    if (_advancedExists == null)
    {
      _advancedExists = _resourcePrefixExits("adv/"); 
    }
    return _advancedExists;   
  }
  
  public boolean isAdvancedDebugEnabled() 
  {
    if (_advancedDebugExists == null)
    {
      _advancedDebugExists = _resourcePrefixExits("advdbg/"); 
    }
    return _advancedDebugExists;   
  }
  
  public void setAdvanced(ActionEvent ae) throws IOException
  {
    if (_optimization == _ADVANCED)
    {
      return;
    }
    _optimization = _ADVANCED;
    _issueRedirect();
  }
  
  public void setAdvancedDebug(ActionEvent ae) throws IOException
  {
    if (_optimization == _ADVANCED_DEBUG)
    {
      return;
    }
    _optimization = _ADVANCED_DEBUG;
    _issueRedirect();
  }
  
  public void setNone(ActionEvent ae) throws IOException
  {
    if (_optimization == _NONE)
    {
      return;
    }
    _optimization = _NONE;
    _issueRedirect();
  }
  
  private static void _issueRedirect() throws IOException
  {
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext ec = context.getExternalContext();
    
    String url = context.getApplication().getViewHandler().getActionURL(context, context.getViewRoot().getViewId());
    
    ec.redirect(ec.encodeActionURL(url));
    
    context.responseComplete();
  }
  
  private boolean _resourcePrefixExits(String prefix)
  {
    return Thread.currentThread().getContextClassLoader().getResource(prefix + _AGENT_CLASS) != null;
  }
    
  
  private String _optimization = _NONE; 
  
  private Boolean _simpleExists;
  private Boolean _advancedExists;
  private Boolean _advancedDebugExists;
  
  
  static final long serialVersionUID = 1L;
  
  private static final String _SIMPLE = "simple";
  private static final String _ADVANCED = "advanced";
  private static final String _ADVANCED_DEBUG = "advanced-debug";
  private static final String _NONE = "none";
  
  private static String _AGENT_CLASS = "oracle/adf/view/js/agent/AdfAgent.js";
  

}
