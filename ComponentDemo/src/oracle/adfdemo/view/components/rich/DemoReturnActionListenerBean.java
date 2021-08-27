/*
** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved.
**
**345678901234567890123456789012345678901234567890123456789012345678901234567890
*/
package oracle.adfdemo.view.components.rich;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.util.Set;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.model.DateListProvider;

import oracle.adfdemo.view.util.rich.DateDemoUtils;

import org.apache.myfaces.trinidad.component.UIXInput;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.LaunchEvent;
import org.apache.myfaces.trinidad.event.ReturnEvent;


/*
** A simple demo bean for the returnActionListener demo.
*/

public class DemoReturnActionListenerBean
{
  public UIXInput getInput()
  {
    return _input;
  }

  public void setInput(UIXInput input)
  {
    _input = input;
  }

  /* the value of the two parameters that you will add together in the dialog */

  public Integer getValue1()
  {
    return _value1;
  }

  public void setValue1(Integer value1)
  {
    _value1 = value1;
  }

  public Integer getValue2()
  {
    return _value2;
  }

  public void setValue2(Integer value2)
  {
    _value2 = value2;
  }


  /* This gets called by the commandButton since its launchListener is bound
   * to this method */

  public void addParameter(LaunchEvent event)
  {
    // Pass an integer into the dialog.
    Object inputFieldValue = getInput().getValue();
    if (inputFieldValue != null)
    {
      try
      {
        Integer inputFieldInteger =
          Integer.valueOf(inputFieldValue.toString());
        // When the getDialogParameters() method has added parameters to a Map, 
        // those parameters also become available in pageFlowScope, and 
        // any page in the dialog process can get the values out of pageFlowScope 
        // by referring to the processScope objects via EL expressions.
        // e.g., #{pageFlowScope.value}
        
        
        event.getDialogParameters().put("value", inputFieldInteger);
      }
      catch (Exception e)
      {
      }
    }
  }
  
  /* This gets called by the commandButton since its returnListener is bound
   * to this method. The returnActionListener can set 
   * the returnValue on the ReturnEvent */
  public void returned(ReturnEvent event)
  {
    if (event.getReturnValue() != null)
    {
      getInput().setSubmittedValue(null);
      getInput().setValue(event.getReturnValue());

      RequestContext afContext = RequestContext.getCurrentInstance();
      afContext.addPartialTarget(getInput());
    }

  }


  private UIXInput _input;
  private Integer _value1;
  private Integer _value2;

}

