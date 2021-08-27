/** Copyright (c) 2009, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import javax.faces.event.ActionEvent;

public class TGToggle implements Serializable
{
  public TGToggle()
  {
  }

  public void setGrouping(String grouping)
  {
    _grouping = grouping;
  }

  public String getGrouping()
  {
    return _grouping;
  }

  public String getText()
  {
    return _grouping == "group" ? "Alphabetical" : "By Group";
  }

  public String getIcon()
  {
    return _grouping == "group" ? "/images/sortasc_ena.png" : "/images/group_ena.png";
  }

  public String getIconDisabled()
  {
    return _grouping == "group" ? "/images/sortasc_dis.png" : "/images/group_dis.png";
  }

  public String getIconHover()
  {
    return _grouping == "group" ? "/images/sortasc_ovr.png" : "/images/group_ovr.png";
  }

  public String getIconDown()
  {
    return _grouping == "group" ? "/images/sortasc_dwn.png" : "/images/group_dwn.png";
  }

  public void toggle(ActionEvent ae)
  {
    if(getGrouping().equals("group"))
      setGrouping("alphabetical");
    else
      setGrouping("group");
  }

  private String _grouping = "group";

  @SuppressWarnings("compatibility:-2108131714173001157")
  private static final long serialVersionUID = 1L;
}
