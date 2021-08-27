/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

public class DemoNavigationItem extends DemoNavigationItemBase implements Serializable
{
  public DemoNavigationItem()
  {
    super();
  }

  public void setOutcome(String outcome)
  {
    _outcome = outcome;
  }

  public String getOutcome()
  {
    return _outcome;
  }

  public void setViewId(String viewId)
  {
    _viewId = viewId;
  }

  public String getViewId()
  {
    return _viewId;
  }

  @SuppressWarnings("compatibility:-3193220106284143485")
  private static final long serialVersionUID = 1L;

  private String _outcome     = null;
  private String _viewId      = null;
}