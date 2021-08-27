/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.util.ExternalContextUtils;

public class PortletBean
{
  public PortletBean()
  {
  }

  public boolean isPortlet()
  {
    //TODO sobryan (dependancy=JSR-301)
    //For now this uses a class in the Trinidad Impl.  But as of JSR-301, it should be changed
    //to look for the appropriate request attribute instead.
    return ExternalContextUtils.isPortlet(FacesContext.getCurrentInstance().getExternalContext());
  }
}
