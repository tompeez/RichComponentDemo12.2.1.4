/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

public class DemoGridLayoutBean
{
  public void wireGridBooleansAction(ActionEvent event)
  {
    if (checkValue == null)
      checkValue = new ArrayList<String>();
    
    if (coffee)
      checkValue.add(setofValues[0]);
    if (tea)
      checkValue.add(setofValues[1]);
    if (orangeJuice)
      checkValue.add(setofValues[2]);
    if (wine)
      checkValue.add(setofValues[3]);
    if (milk)
      checkValue.add(setofValues[4]);
    if (fizz)
      checkValue.add(setofValues[5]);
    if (beer)
      checkValue.add(setofValues[6]);
    if (lemonade)
      checkValue.add(setofValues[7]);

  }

  public void wireGridBooleansValue(ValueChangeEvent event)
  {
    if (checkValue == null)
      checkValue = new ArrayList<String>();
    
    _pruneList(event, ((RichSelectBooleanCheckbox) event.getComponent())
        .getText());

  }

  private void _pruneList(ValueChangeEvent event, String value)
  {

    if (event.getNewValue() == Boolean.TRUE)
      checkValue.add(value);
    else if (event.getNewValue() == Boolean.FALSE)
      checkValue.remove(value);
  }

  public void setCoffee(boolean coffee)
  {
    this.coffee = coffee;
  }

  public boolean isCoffee()
  {
    return coffee;
  }

  public void setTea(boolean tea)
  {
    this.tea = tea;
  }

  public boolean isTea()
  {
    return tea;
  }

  public void setOrangeJuice(boolean orangeJuice)
  {
    this.orangeJuice = orangeJuice;
  }

  public boolean isOrangeJuice()
  {
    return orangeJuice;
  }

  public void setWine(boolean wine)
  {
    this.wine = wine;
  }

  public boolean isWine()
  {
    return wine;
  }

  public void setCheckValue(List<String> checkValue)
  {
    this.checkValue = checkValue;
  }

  public List<String> getCheckValue()
  {
    return checkValue;
  }

  public void setMilk(boolean milk)
  {
    this.milk = milk;
  }

  public boolean isMilk()
  {
    return milk;
  }

  public void setFizz(boolean fizz)
  {
    this.fizz = fizz;
  }

  public boolean isFizz()
  {
    return fizz;
  }

  public void setBeer(boolean beer)
  {
    this.beer = beer;
  }

  public boolean isBeer()
  {
    return beer;
  }

  public void setLemonade(boolean lemonade)
  {
    this.lemonade = lemonade;
  }

  public boolean isLemonade()
  {
    return lemonade;
  }

  private boolean coffee;

  private boolean tea;

  private boolean orangeJuice;

  private boolean wine;

  private boolean milk;

  private boolean fizz;

  private boolean beer;

  private boolean lemonade;

  private List<String> checkValue;

  private static final String[] setofValues =
  { "coffee", "tea", "orangeJuice", "wine", "milk", "fizz", "beer", "lemonade" };

}
