/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.view.rich.context.WindowIdProviderBase;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.context.WindowManager;

public class TestWindowIdProvider extends WindowIdProviderBase
{
  public TestWindowIdProvider()
  {
  }

  public String getCurrentWindowId(FacesContext context)
  {
    ExternalContext extContext = context.getExternalContext();
    WindowManager wm = RequestContext.getCurrentInstance().getWindowManager();
    return wm.getCurrentWindow(extContext).getId();
  }

  @Override
  public boolean isWindowIdAvailable(FacesContext context)
  {
    return true;
  }

  public String windowOpened(
     FacesContext context, String baseWindowId)
  {
    return windowOpened(context.getExternalContext(), baseWindowId, false);
  }
  
  public String windowOpened(ExternalContext externalContext, String baseWindowId, boolean isDialog)
  {
    HttpServletRequest servletRequest = (HttpServletRequest)externalContext.getRequest();

    String url = servletRequest.getRequestURI();
     
    // for non-active demo pages, we still want to return a null url so that the client side 
    // doesn't need to do a redirect. The redirect will only happen if the page url contains
    // the string "activeData".  
    // this also means that in order for active data to work in a demo page, the page
    // has to have "activeData" in its url.
    if ((url!=null)&&(url.indexOf("activeData")<0)) 
    {
      return null;
    }
    else
    {
      // we want to return the url, because we want to make it behave the same way as the ADFc 
      // in which case we do a redirect on client for the active data to start.
    
      String query = "" + servletRequest.getQueryString();
      String prefix = "dummy=5";
    
      // add on our attribute first
      query = (query != null)
                ? prefix + "&" + query
               : prefix;
    
      return url + "?" + query;
    }
  }
}
