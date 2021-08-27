/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.webapp;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.help.ResourceBundleHelpProvider;


/**
 * This class extends ResourceBundleHelpProvider. This is needed to create an implementation for
 * the demo that will return a url.  In this case, for the demo, the url is just hardwired
 * to a value.
 */
public class DemoHelpProvider extends ResourceBundleHelpProvider
{
  public DemoHelpProvider()
  {
  }
  
  @Override
  protected String getExternalUrl(FacesContext context, UIComponent component, String topicId)
  {
    if (topicId == null)
      return null;
    
    if (topicId.contains("TOPICID_ALL") ||
        topicId.contains("TOPICID_DEFN_URL") ||
        topicId.contains("TOPICID_INSTR_URL") ||
        topicId.contains("TOPICID_URL"))
    {
      FacesContext ctx = FacesContext.getCurrentInstance();
      String contextPath = ctx.getExternalContext().getRequestContextPath();
      return contextPath + "/feature/helpClient.jspx";
    }
    else
      return null;
  }
}
