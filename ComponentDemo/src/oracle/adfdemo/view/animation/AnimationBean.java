/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.animation;

import java.io.Serializable;

import javax.faces.event.ActionEvent;

import oracle.adfdemo.view.components.rich.DemoComponentSkin;

public class AnimationBean implements Serializable
{
  public boolean isAnimationEnabled()
  {
    return _animationEnabled;
  }

  public void setAnimationEnabled(boolean enabled)
  {
    _animationEnabled = enabled;
  }

  public void animationMenuAction(ActionEvent ae)
  {
    setAnimationEnabled(!isAnimationEnabled());
    DemoComponentSkin.reloadThePage();
  }

  private static final long serialVersionUID = 1L;

  private boolean _animationEnabled = true;
}
