/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewExpiredException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import oracle.adf.view.rich.context.ExceptionHandler;

public class TestExceptionHandler
  extends ExceptionHandler
{
  public TestExceptionHandler()
  {
  }


  public void handleException(FacesContext context, Throwable t, 
                              PhaseId id) throws Throwable
  {/* Commented out.  We no longer need to handle ViewExpiredExceptions
      in the demo, as the default ViewExpiredException handling is
      perfectly sufficient.
    if ((t instanceof ViewExpiredException) &&
        (id == PhaseId.RESTORE_VIEW))
    {
      String viewId = ((ViewExpiredException) t).getViewId();
      if (viewId == null)
        viewId = "/index.jspx";
      
      UIViewRoot vr = context.getApplication().getVSHOUDLiewHandler().createView(context, viewId);
      context.setViewRoot(vr);
      
      context.addMessage(null,
                         new FacesMessage("View expired",
                                          "The state of this page had expired;  please resubmit."));
    }
    else
*/
    {
      throw t;
    }
  }
}
