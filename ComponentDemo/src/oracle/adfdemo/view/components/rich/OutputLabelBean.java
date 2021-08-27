/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import oracle.adf.view.rich.component.rich.output.RichOutputLabel;

public class OutputLabelBean
{
  private RichOutputLabel _component;
  private RichOutputLabel _clientComponent;
  private RichOutputLabel _bindingComponent;
  
  public OutputLabelBean()
  {
    _bindingComponent = new RichOutputLabel();
    _bindingComponent.setAccessKey('b');
    _bindingComponent.setValue("Binding a component");
    _bindingComponent.setFor("inputBinding");
    _bindingComponent.setClientComponent(true);
    _bindingComponent.setShowRequired(true);
  }

  public RichOutputLabel getComponent()
  {
    return _component;
  }

  public void setComponent(RichOutputLabel component)
  {
    _component = component;
  }
  
  public RichOutputLabel getBindingComponent()
  {
    return _bindingComponent;
  }

  public void setBindingComponent(RichOutputLabel component)
  {
    _bindingComponent = component;
  }
  
  public RichOutputLabel getClientComponent()
  {
    return _clientComponent;
  }

  public void setClientComponent(RichOutputLabel clientComponent)
  {
    _clientComponent = clientComponent;
  }
}