/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich.behaviors;

import javax.faces.component.behavior.ClientBehaviorBase;

public class CallFunctionBehavior
  extends ClientBehaviorBase
{
  public String getFunction()
  {
    return _function;
  }

  public void setFunction(String function)
  {
    this._function = function;
  }

  @Override
  public String getRendererType()
  {
    return "call-function";
  }

  private String _function;
}