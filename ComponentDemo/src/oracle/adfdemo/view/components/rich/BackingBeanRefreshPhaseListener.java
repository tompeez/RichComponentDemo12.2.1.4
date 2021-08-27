/* Copyright (c) 2009, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.components.rich;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;


/**
 * This class implements a <code>PhaseListener</code> that detects a view (page) navigation and
 * resets the any requestScope-d beans to mimic the behavior of a backing scoped beans.
 */
public class BackingBeanRefreshPhaseListener implements PhaseListener
{
  public BackingBeanRefreshPhaseListener()
  {
    super();
  }

  public void afterPhase(PhaseEvent phaseEvent)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    String afterViewId = context.getViewRoot().getViewId();
    
    Map requestMap = context.getExternalContext().getRequestMap();
    String beforeViewId = (String) requestMap.get(_BEFORE_VIEW_ID);
    
    if ((beforeViewId != afterViewId) && (beforeViewId != null) && !beforeViewId.equals(afterViewId))
    {
      // reset all the backing bean scoped beans stored in the requestMap. Should this be done only 
      // when PPR Navigation is enabled?
      _resetBackingBeans(context);
      
    }
    
  }

  
  /**
   * get the viewId the user is currently on (before Invoke Application) and store it in the 
   * requestMap.
   * 
   * @param phaseEvent
   */
  public void beforePhase(PhaseEvent phaseEvent)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    String beforeViewID = context.getViewRoot().getViewId();
    
    Map requestMap = context.getExternalContext().getRequestMap();
    requestMap.put(_BEFORE_VIEW_ID, beforeViewID);
  }

  private void _resetBackingBeans(FacesContext context)
  {
    ComponentEditorHandler ceh = (ComponentEditorHandler) context.getExternalContext().getRequestMap().get("editor");
    if (ceh != null)
    {
      ceh.reinitialize();
    }
  }
  
  /**
   * Only care about the Invoke Application phase, as this is primarily used to detect a page 
   * navigation.
   * @return
   */
  public PhaseId getPhaseId()
  {
    return PhaseId.INVOKE_APPLICATION;
  }
  
  private static final String _BEFORE_VIEW_ID = 
    "oracle.adfdemo.view.components.rich.ViewRefreshPhaseListener.BEFORE_VIEW_ID";


}
