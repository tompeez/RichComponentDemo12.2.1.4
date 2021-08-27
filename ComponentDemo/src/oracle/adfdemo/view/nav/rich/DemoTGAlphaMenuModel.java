/** Copyright (c) 2009, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import org.apache.myfaces.trinidad.model.ViewIdPropertyMenuModel;

public class DemoTGAlphaMenuModel extends DemoBaseAlphaMenuModel implements Serializable
{
  public String getRootNodeText()
  {
    return "Components and Other Tags";
  }

  public ViewIdPropertyMenuModel getGroupedMenuModel()
  {
    return new DemoTGGroupMenuModel();
  }

  @SuppressWarnings("compatibility:7039553056312995603")
  private static final long serialVersionUID = 1L;
}
