/** Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import org.apache.myfaces.trinidad.model.ViewIdPropertyMenuModel;

public class DemoStyleAlphaMenuModel extends DemoBaseAlphaMenuModel implements Serializable
{
  public String getRootNodeText()
  {
    return "Styles";
  }

  public ViewIdPropertyMenuModel getGroupedMenuModel()
  {
    return new DemoStyleMenuModel();
  }

  @SuppressWarnings("compatibility:220875034170110754")
  private static final long serialVersionUID = 1L;
}
