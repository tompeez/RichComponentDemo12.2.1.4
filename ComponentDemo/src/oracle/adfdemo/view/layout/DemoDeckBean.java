/** Copyright (c) 2013, 2018, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.layout;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.layout.RichDeck;

import oracle.adf.view.rich.event.DisplayChangeEvent;

import org.apache.myfaces.trinidad.context.RequestContext;


/**
 * Managed bean for a deck demo.
 */
public class DemoDeckBean implements Serializable
{
  /**
   * Default constructor.
   */
  public DemoDeckBean()
  {
  }

  public String getTransitionPreset()
  {
    return _transitionPreset;
  }

  public void setTransitionPreset(String transitionPreset)
  {
    _transitionPreset = transitionPreset;
  }

  public String getJumpChild()
  {
    if (_jumpChild == null)
    {
      RichDeck deck =
        (RichDeck)FacesContext.getCurrentInstance().getViewRoot().findComponent("dmoTpl:deck1");
      _jumpChild = deck.getChildren().get(0).getId();
    }
    return _jumpChild;
  }

  public void setJumpChild(String jumpChild)
  {
    _jumpChild = jumpChild;
  }

  public void setBackNavigate(String transitionType)
  {
    _backNavigate = transitionType;
  }

  public String getBackNavigate()
  {
    return _backNavigate;
  }

  public void setForwardNavigate(String transitionType)
  {
    _forwardNavigate = transitionType;
  }

  public String getForwardNavigate()
  {
    return _forwardNavigate;
  }

  /**
   * Animate to the child referenced by jumpChild.
   * @param e the ActionEvent associated with the action
   */
  public void animateToJumpChild(ActionEvent e)
  {
    UIComponent eventComponent = e.getComponent();
    RichDeck deck = (RichDeck)eventComponent.findComponent("deck1");

    // Set the backNavigate and forwardNavigate values to the chosen preset:
    if ("fade".equals(_transitionPreset))
    {
      _backNavigate = NavigateType.FADE.toString();
      _forwardNavigate = NavigateType.FADE.toString();
    }
    else if ("flipHorizontal".equals(_transitionPreset))
    {
      _backNavigate = NavigateType.FLIP_END.toString();
      _forwardNavigate = NavigateType.FLIP_START.toString();
    }
    else if ("flipVertical".equals(_transitionPreset))
    {
      _backNavigate = NavigateType.FLIP_DOWN.toString();
      _forwardNavigate = NavigateType.FLIP_UP.toString();
    }
    else if ("flipEnd".equals(_transitionPreset))
    {
      _backNavigate = NavigateType.FLIP_END.toString();
      _forwardNavigate = NavigateType.FLIP_END.toString();
    }
    else if ("flipStart".equals(_transitionPreset))
    {
      _backNavigate = NavigateType.FLIP_START.toString();
      _forwardNavigate = NavigateType.FLIP_START.toString();
    }
    else if ("flipDown".equals(_transitionPreset))
    {
      _backNavigate = NavigateType.FLIP_DOWN.toString();
      _forwardNavigate = NavigateType.FLIP_DOWN.toString();
    }
    else if ("flipUp".equals(_transitionPreset))
    {
      _backNavigate = NavigateType.FLIP_UP.toString();
      _forwardNavigate = NavigateType.FLIP_UP.toString();
    }
    else if ("slideHorizontal".equals(_transitionPreset))
    {
      _backNavigate = NavigateType.SLIDE_END.toString();
      _forwardNavigate = NavigateType.SLIDE_START.toString();
    }
    else if ("slideVertical".equals(_transitionPreset))
    {
      _backNavigate = NavigateType.SLIDE_DOWN.toString();
      _forwardNavigate = NavigateType.SLIDE_UP.toString();
    }
    else if ("slideEnd".equals(_transitionPreset))
    {
      _backNavigate = NavigateType.SLIDE_END.toString();
      _forwardNavigate = NavigateType.SLIDE_END.toString();
    }
    else if ("slideStart".equals(_transitionPreset))
    {
      _backNavigate = NavigateType.SLIDE_START.toString();
      _forwardNavigate = NavigateType.SLIDE_START.toString();
    }
    else if ("slideDown".equals(_transitionPreset))
    {
      _backNavigate = NavigateType.SLIDE_DOWN.toString();
      _forwardNavigate = NavigateType.SLIDE_DOWN.toString();
    }
    else if ("slideUp".equals(_transitionPreset))
    {
      _backNavigate = NavigateType.SLIDE_UP.toString();
      _forwardNavigate = NavigateType.SLIDE_UP.toString();
    }
    else if ("none".equals(_transitionPreset))
    {
      _backNavigate = NavigateType.NONE.toString();
      _forwardNavigate = NavigateType.NONE.toString();
    }
    else if ("bogus".equals(_transitionPreset))
    {
      _backNavigate = "bogus";
      _forwardNavigate = "bogus";
    }
    else if ("all".equals(_transitionPreset))
    {
      // Start at the beginning if we've gone through all of the types already:
      if (_currentAllIndex == TRANSITION_TYPE_COUNT)
        _currentAllIndex = 0;

      // Get the current navigation type:
      NavigateType currentNavigateType = TRANSITION_TYPE_VALUES[_currentAllIndex++];

      // Set the back and forward navigations to the current transition type:
      _backNavigate = currentNavigateType.toString();
      _forwardNavigate = currentNavigateType.toString();
    }

    // Update the displayedChild:
    deck.setDisplayedChild(_jumpChild);

    // Add this component as a partial target:
    RequestContext rc = RequestContext.getCurrentInstance();
    rc.addPartialTarget(deck);
  }

  public void updateJumpChild(DisplayChangeEvent event)
  {
    _jumpChild = event.getNewChildId();
  }

  private String _backNavigate     = "none";
  private String _forwardNavigate  = "none";
  private String _transitionPreset = "none";
  private String _jumpChild        = null;
  private int    _currentAllIndex  = 0;

  private static enum NavigateType
  {
    FADE("fade"),
    FLIP_END("flipEnd"),
    FLIP_START("flipStart"),
    FLIP_DOWN("flipDown"),
    FLIP_UP("flipUp"),
    SLIDE_END("slideEnd"),
    SLIDE_START("slideStart"),
    SLIDE_DOWN("slideDown"),
    SLIDE_UP("slideUp"),
    NONE("none");

    private final String _value;
    private NavigateType(String value)
    {
      _value = value;
    }
    public String toString()
    {
      return _value;
    }
  }
  private final static NavigateType[] TRANSITION_TYPE_VALUES = NavigateType.values();
  private final static int TRANSITION_TYPE_COUNT = TRANSITION_TYPE_VALUES.length;

  @SuppressWarnings("compatibility:626968197676187235")
  private static final long serialVersionUID = 1L;
}
