/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


public class DemoForm
{
  /**
   * @param event the ActionEvent associated with the action
   */
  public void actionListener(ActionEvent event)
  {
    UIComponent comp = event.getComponent();
    String buttonText = comp.getAttributes().get("text").toString();
    setButtonText(buttonText);
  }
  
  public void setValue(String value)
  {
    this._value = value;
  }

  public String getValue()
  {
    return _value;
  }

  public void setButtonText(String buttonText)
  {
    this._buttonText = buttonText;
  }

  public String getButtonText()
  {
    return _buttonText;
  }
  
  
  private String _value;
  private String _buttonText;  
}
