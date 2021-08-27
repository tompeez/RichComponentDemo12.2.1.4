/** Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import org.apache.myfaces.trinidad.model.ViewIdPropertyMenuModel;

public class DemoVisualDesignsAlphaMenuModel extends DemoBaseAlphaMenuModel implements Serializable
{
  public String getRootNodeText()
  {
    return "Visual Designs";
  }

  public ViewIdPropertyMenuModel getGroupedMenuModel()
  {
    return new DemoVisualDesignsMenuModel();
  }

  @SuppressWarnings("compatibility:7008420552921375938")
  private static final long serialVersionUID = 1L;
}
