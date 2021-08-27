/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.messages;

import java.awt.Color;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.util.FacesMessageUtils;

/**
 * Sample editable value validators to demonstrate the messaging framework.
 */
public class MessageValidator
{
  

  public void clickAction(ActionEvent actionEvent)
  {
    FacesContext.getCurrentInstance().addMessage(null, 
                         FacesMessageUtils.getConfirmationMessage("Confirmation Message ", null));


  }
  
  public void clickActionAddTarget(ActionEvent actionEvent)
  {
    FacesContext.getCurrentInstance().addMessage("target", 
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning summary", "Warning detail"));


  }  
  
  
  /**
   * Validator for a single value.
   * @param context the Faces context
   * @param toValidate the component to validate
   * @param value the value to be validated
   */
  public void validateSingleValue(
    FacesContext context,
    UIComponent  toValidate,
    Object       value)
  {
    if (value != null)
    {
      String strVal = value.toString();
      String clientId = null;
      if (toValidate != null)
      {
        clientId = toValidate.getClientId(context);
      }

     // System.out.println("entered validator");
      if(strVal != null)
      {
        strVal = strVal.toLowerCase();

        if (strVal.contains("fatal"))
        {
          context.addMessage(
            clientId,
            new FacesMessage(FacesMessage.SEVERITY_FATAL,
              "Fatal message SUMMARY text.",
              "Fatal message DETAIL text."));
        }
        else if (strVal.contains("error"))
        {
          context.addMessage(
            clientId,
            new FacesMessage(FacesMessage.SEVERITY_ERROR,
              "Error message SUMMARY text.",
              "Error message DETAIL text."));
        }
        else if (strVal.contains("warning"))
        {
          context.addMessage(
            clientId,
            new FacesMessage(FacesMessage.SEVERITY_WARN,
              "Warning message SUMMARY text (Detail text null).",
              null));
        }
        else if (strVal.contains("confirmation"))
        {
          context.addMessage(
            clientId,
            FacesMessageUtils.getConfirmationMessage(
             null,
              "Confirmation message DETAIL text(Summary text null)."));
        }
        else if (strVal.contains("info"))
        {
          context.addMessage(
            clientId,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
              "Info message SUMMARY text (Detail text is empty string).",
              "  "));
        }
      }
    }
  }

  /**
   * Validator for a single value.
   * @param context the Faces context
   * @param toValidate the component to validate
   * @param value the value to be validated
   */
  public void validateSingleValueFormattedMessage(
    FacesContext context,
    UIComponent  toValidate,
    Object       value)
  {
    if (value != null)
    {
    String strVal = value.toString();
    String clientId = null;
    if (toValidate != null)
    {
      clientId = toValidate.getClientId(context);
    }

   // System.out.println("entered validator");

    if ("fatal".equalsIgnoreCase(strVal))
    {
      context.addMessage(
        clientId,
        new FacesMessage(FacesMessage.SEVERITY_FATAL,
          "Fatal message summary text.",
          "<html>This was returned after encountering a <b>fatal</b> value from the component.<p>This is more <i>fatal</i> error message in a new paragraph.</html>"));
    }
    if ("error".equalsIgnoreCase(strVal))
    {
      context.addMessage(
        clientId,
        new FacesMessage(FacesMessage.SEVERITY_ERROR,
          "Error message summary text.",
          "<html>This was returned after encountering an <b>error</b> value from the component.<p>This is an <i>error</i> list.<ul><li>error text item 1<li>error text item 2</html>"));
    }
    if ("warning".equalsIgnoreCase(strVal))
    {
      context.addMessage(
        clientId,
        new FacesMessage(FacesMessage.SEVERITY_WARN,
          "Warning message summary text.",
          "<html>This was returned after encountering a <b>warning</b> value from the component. This is a <a href=\"http://www.oracle.com\">link to oracle.</a> And some <i>italic</i> just for fun.</html>"));
    }
    if ("confirmation".equalsIgnoreCase(strVal))
    {
      context.addMessage(
        clientId,
        FacesMessageUtils.getConfirmationMessage(
          "Confirmation message summary text.",
          "<html>This was returned after encountering a <b>confirmation</b> value from the component.<p>This is more <i>confirmation</i> message text in a new paragraph.</html>"));
    }
    if ("info".equalsIgnoreCase(strVal))
    {
      context.addMessage(
        clientId,
        new FacesMessage(FacesMessage.SEVERITY_INFO,
          "Info message summary text.",
          "<html>This was returned after encountering an <b>info</b> message from the component.<p>This is an <i>info</i> list.<ul><li>info text item 1<li>info text item 2</html>"));
    }
    }
  }

  /**
   * Validator for booleans.
   * @param context the Faces context
   * @param toValidate the component to validate
   * @param value the value to be validated
   */
  public void validateBooleanValue(
    FacesContext context,
    UIComponent  toValidate,
    Object       value)
  {
    String text = (String)toValidate.getAttributes().get("text");
    if ("fatal".equalsIgnoreCase(text))
    {
      if (Boolean.TRUE.equals(value))
      {
        context.addMessage(
          toValidate.getClientId(context),
          new FacesMessage(FacesMessage.SEVERITY_FATAL,
            "Fatal message summary text.",
            "This was returned after receiving a value of true on the item labeled fatal."));

      }
    }
    if ("error".equalsIgnoreCase(text))
    {
      if (Boolean.TRUE.equals(value))
      {
        context.addMessage(
          toValidate.getClientId(context),
          new FacesMessage(FacesMessage.SEVERITY_ERROR,
            "Error message summary text.",
            "This was returned after receiving a value of true on the item labeled error."));
      }
    }
    if ("warning".equalsIgnoreCase(text))
    {
      if (Boolean.TRUE.equals(value))
      {
        context.addMessage(
          toValidate.getClientId(context),
          new FacesMessage(FacesMessage.SEVERITY_WARN,
            "This is an warning message.",
            "This was returned after receiving a value of true on the item labeled warning."));
      }
    }
    if ("confirmation".equalsIgnoreCase(text))
    {
      if (Boolean.TRUE.equals(value))
      {
        context.addMessage(
          toValidate.getClientId(context),
          FacesMessageUtils.getConfirmationMessage(
            "This is an confirmation message.",
            "This was returned after receiving a value of true on the item labeled confirmation."));
      }
    }
    if ("info".equalsIgnoreCase(text))
    {
      if (Boolean.TRUE.equals(value))
      {
        context.addMessage(
          toValidate.getClientId(context),
          new FacesMessage(FacesMessage.SEVERITY_INFO,
            "Info message summary text.",
            "This was returned after receiving a value of true on the item labeled info."));
      }
    }
  }

  /**
   * Validator for multiple values.
   * @param context the Faces context
   * @param toValidate the component to validate
   * @param value the value to be validated
   */
  public void validateMultipleValues(
    FacesContext context,
    UIComponent  toValidate,
    Object       value)
  {
    if (value != null)
    {
      List valueList = (List)value;
      for (Object i: valueList)
      {
        this.validateSingleValue(context, toValidate, i);
      }
    }
  }

  /**
   * Validator for multiple values.
   * @param context the Faces context
   * @param toValidate the component to validate
   * @param value the value to be validated
   */
  public void validateMultipleValuesFormattedMessage(
    FacesContext context,
    UIComponent  toValidate,
    Object       value)
  {
    if (value != null)
    {
      List valueList = (List)value;
      for (Object i: valueList)
      {
        this.validateSingleValueFormattedMessage(context, toValidate, i);
      }
    }
  }

  /**
   * Validator for multiple global messages.
   * @param context the Faces context
   * @param toValidate the component to validate
   * @param value the value to be validated
   */
  public void validateMultipleGlobalMessages(
    FacesContext context,
    UIComponent  toValidate,
    Object       value)
  {
    if (value != null)
    {
      List valueList = (List)value;
      for (Object i: valueList)
      {
        this.validateSingleValue(context, null, i);
      }
    }
  }

  /**
   * Validator for multiple global messages.
   * @param context the Faces context
   * @param toValidate the component to validate
   * @param value the value to be validated
   */
  public void validateMultipleGlobalFormattedMessages(
    FacesContext context,
    UIComponent  toValidate,
    Object       value)
  {
    if (value != null)
    {
      List valueList = (List)value;
      for (Object i: valueList)
      {
        this.validateSingleValueFormattedMessage(context, null, i);
      }
    }
  }

  /**
   * Validator for colors.
   * @param context the Faces context
   * @param toValidate the component to validate
   * @param value the value to be validated
   */
  public void validateColorValue(
    FacesContext context,
    UIComponent  toValidate,
    Object       value)
  {
    if (value != null)
    {
    Color color = (Color)value;
    boolean red = color.getRed() > 0;
    boolean green = color.getGreen() > 0;
    boolean blue = color.getBlue() > 0;
    StringBuilder sb = new StringBuilder();
    sb.append('#');
    sb.append(_getHexColorComponent(color.getRed()));
    sb.append(_getHexColorComponent(color.getGreen()));
    sb.append(_getHexColorComponent(color.getBlue()));
    String hexColor = sb.toString().toUpperCase();
    sb = new StringBuilder();
    sb.append("<html>This message was generated because you chose the color <span style='");
    sb.append("border: 1px solid black; background-color: ");
    sb.append(hexColor);
    sb.append(";'>&nbsp;&nbsp;&nbsp;&nbsp;</span> ");
    sb.append(hexColor);
    sb.append(" which has ");
    sb.append(red ? "some" : "no");
    sb.append(" <span style='color: red;'>red</span>, ");
    sb.append(green ? "some" : "no");
    sb.append(" <span style='color: green;'>green</span>, and ");
    sb.append(blue ? "some" : "no");
    sb.append(" <span style='color: blue;'>blue</span>.</html>");

    if (red && green && blue)
    {
      context.addMessage(
        toValidate.getClientId(context),
        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal message summary text.", sb.toString()));
    }
    else if (red && green)
    {
      context.addMessage(
        toValidate.getClientId(context),
        new FacesMessage(FacesMessage.SEVERITY_ERROR, "This is a error message.", sb.toString()));
    }
    else if (red && blue)
    {
      context.addMessage(
        toValidate.getClientId(context),
        new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning message summary text.", sb.toString()));
    }
    else if (green && blue)
    {
      context.addMessage(
        toValidate.getClientId(context),
        FacesMessageUtils.getConfirmationMessage("Confirmation message summary text.", sb.toString()));
    }
    else if (red)
    {
      context.addMessage(
        toValidate.getClientId(context),
        new FacesMessage(FacesMessage.SEVERITY_INFO, "This is a info message.", sb.toString()));
    }
    }
  }

  /**
   * Validator for dates.
   * @param context the Faces context
   * @param toValidate the component to validate
   * @param value the value to be validated
   */
  public void validateDateValue(
    FacesContext context,
    UIComponent  toValidate,
    Object       value)
  {
    if (value != null)
    {

    Date date = (Date)value;
    SimpleDateFormat f = new SimpleDateFormat("yyyy");
    String year = f.format(date);

    if ("2000".equals(year))
    {
      context.addMessage(
        toValidate.getClientId(context),
        new FacesMessage(FacesMessage.SEVERITY_FATAL,
          "Fatal message summary text.",
          "This was returned after encountering a date where the year is 2000."));

    }
    if ("2001".equals(year))
    {
      context.addMessage(
        toValidate.getClientId(context),
        new FacesMessage(FacesMessage.SEVERITY_ERROR,
          "Error message summary text.",
          "This was returned after encountering a date where the year is 2001."));

    }
    if ("2002".equals(year))
    {
      context.addMessage(
        toValidate.getClientId(context),
        new FacesMessage(FacesMessage.SEVERITY_WARN,
          "Warning message summary text.",
          "This was returned after encountering a date where the year is 2002."));

    }
    if ("2003".equals(year))
    {
      context.addMessage(
        toValidate.getClientId(context),
        FacesMessageUtils.getConfirmationMessage(
          "Confirmation message summary text.",
          "This was returned after encountering a date where the year is 2003."));

    }
    if ("2004".equals(year))
    {
      context.addMessage(
        toValidate.getClientId(context),
        new FacesMessage(FacesMessage.SEVERITY_INFO,
          "This is a info message.",
          "This was returned after encountering a date where the year is 2004."));
    }

    }
  }

  /**
   * Validator for numbers.
   * @param context the Faces context
   * @param toValidate the component to validate
   * @param value the value to be validated
   */
  public void validateNumberValue(
    FacesContext context,
    UIComponent  toValidate,
    Object       value)
  {
    if (value != null)
    {

    int numVal = ((Number)value).intValue();

    if (numVal == 1)
    {
      context.addMessage(
        toValidate.getClientId(context),
        new FacesMessage(FacesMessage.SEVERITY_FATAL,
          "Fatal message summary text.",
          "This was returned after getting the number 1."));

    }
    if (numVal == 2)
    {
      context.addMessage(
        toValidate.getClientId(context),
        new FacesMessage(FacesMessage.SEVERITY_ERROR,
          "Error message summary text.",
          "This was returned after getting the number 2."));

    }
    if (numVal == 3)
    {
      context.addMessage(
        toValidate.getClientId(context),
        new FacesMessage(FacesMessage.SEVERITY_WARN,
          "Warning message summary text.",
          "This was returned after getting the number 3."));

    }
    if (numVal == 4)
    {
      context.addMessage(
        toValidate.getClientId(context),
        FacesMessageUtils.getConfirmationMessage(
          "Confirmation message summary text.",
          "This was returned after getting the number 4."));

    }
    if (numVal == 5)
    {
      context.addMessage(
        toValidate.getClientId(context),
        new FacesMessage(FacesMessage.SEVERITY_INFO,
          "This is a info message.",
          "This was returned after getting the number 5."));
    }

    }
  }


  /**
   * Converts an integer color component into a 2-digit hexidecimal color component.
   * @param colorComponent the integer color component
   * @return the 2-digit hexideciaml color component
   */
  private static String _getHexColorComponent(int colorComponent)
  {
    String hex = Integer.toString(colorComponent, 16);
    if (hex.length() == 1)
    {
      hex = "0" + hex;
    }
    return hex;
  }

  public void setIntValue(int intValue)
  {
    this._intValue = intValue;
  }

  public int getIntValue()
  {
    return _intValue;
  }

  public void setVal(String val)
  {
    this._val = val;
  }

  public String getVal()
  {
    return _val;
  }
  


  private int _intValue = 1;    
  private String _val = null;
}
