/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.util.Map;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.ProcessMenuModel;

/** 
 * The class overrides getCurrentViewId, the model no longer 
 * gets the current viewId by calling
 * FacesContext.getCurrentInstance().getViewRoot().getViewId().
 * 
 * The focus path is based on an id, which does not need to be a view id.
 */
public class DemoTrainIdMenuModel extends ProcessMenuModel 
{
  public DemoTrainIdMenuModel()
  {
    super();
  }

  @Override
  protected String getCurrentViewId()
  {    
    return getFocusId();
  }
  
  public String getFocusId()
  {
    RequestContext rc = RequestContext.getCurrentInstance();
    Map scope = rc.getPageFlowScope();
    String id = (String)scope.get(_idKey);
    if (id == null)
    {
      id = ((DemoNavigationItemId)getRowData(0)).getId();
      _setCurrentId(id);
    }
    return id;
  }
  
  public void setIdKey(String key)
  {
    if (key != null)
      _idKey = key;
    
  }

  private void _setCurrentId(
    String id)
  {    
    RequestContext rc = RequestContext.getCurrentInstance();
    Map scope = rc.getPageFlowScope();
    scope.put(_idKey, id);
  }



  private String _idKey = DemoNavigationItemId.DEFAULT_ID_KEY;
 
}