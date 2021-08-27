/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class AtomBean 
{
  String name;
  String symbol;
  int number;
  String group;
  String visible = "true";
  String selected = "false";

  public String action()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    FacesMessage message = new FacesMessage("Clicked on Chemical " + getName());
    context.addMessage(null, message);
    return null;
  }

  public AtomBean()
  {
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getSymbol()
  {
    return symbol;
  }

  public void setSymbol(String symbol)
  {
    this.symbol = symbol;
  }

  public int getNumber()
  {
    return number;
  }

  public void setNumber(int number)
  {
    this.number = number;
  }

  public String getGroup()
  {
    return group;
  }

  public void setGroup(String group)
  {
    this.group = group;
  }

  public String getVisible()
  {
    return visible;
  }

  public void setVisible(String visible)
  {
    this.visible = visible;
  }

  public String getSelected()
  {
    return selected;
  }

  public void setSelected(String selected)
  {
    this.selected = selected;
  }
}