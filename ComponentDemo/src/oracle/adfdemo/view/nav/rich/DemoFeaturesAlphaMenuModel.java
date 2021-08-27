/** Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import org.apache.myfaces.trinidad.model.ViewIdPropertyMenuModel;

public class DemoFeaturesAlphaMenuModel extends DemoBaseAlphaMenuModel implements Serializable
{
  public String getRootNodeText()
  {
    return "Features";
  }

  public ViewIdPropertyMenuModel getGroupedMenuModel()
  {
    return new DemoCombinedMenuModel();
  }

  @SuppressWarnings("compatibility:2000545929866459155")
  private static final long serialVersionUID = 1L;
}
