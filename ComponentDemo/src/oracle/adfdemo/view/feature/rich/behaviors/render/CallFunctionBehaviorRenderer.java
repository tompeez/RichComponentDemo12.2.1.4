/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich.behaviors.render;

import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.render.ClientBehaviorRenderer;

import oracle.adfdemo.view.feature.rich.behaviors.CallFunctionBehavior;


public class CallFunctionBehaviorRenderer
  extends ClientBehaviorRenderer
{
  @Override
  public String getScript(
    ClientBehaviorContext clientBehaviorContext,
    ClientBehavior        clientBehavior)
  {
    String function = ((CallFunctionBehavior)clientBehavior).getFunction();
    return new StringBuilder(24 + function.length())
      .append("return ")
      .append(function)
      .append("(componentEvent);")
      .toString();
  }
}