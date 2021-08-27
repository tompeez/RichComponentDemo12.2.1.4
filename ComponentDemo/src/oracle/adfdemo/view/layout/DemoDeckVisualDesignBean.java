/** Copyright (c) 2013, 2018, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.layout;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.event.PollEvent;

import oracle.adf.view.rich.component.rich.layout.RichDeck;

import oracle.adf.view.rich.event.DisplayChangeEvent;

import org.apache.myfaces.trinidad.context.RequestContext;

/**
 * Managed bean for the deck visual design demo.
 */
public class DemoDeckVisualDesignBean implements Serializable
{
  public DemoDeckVisualDesignBean()
  {
  }

  // -- Start of auto-advance deck methods --

  public String getAutoAdvChild()
  {
    if (_autoAdvChild == null)
    {
      RichDeck deck =
        (RichDeck)FacesContext.getCurrentInstance().getViewRoot().findComponent("dmoTpl:autoAdvDeck");
      _autoAdvChild = deck.getChildren().get(0).getId();
    }
    return _autoAdvChild;
  }

  public void setAutoAdvChild(String autoAdvChild)
  {
    _autoAdvChild = autoAdvChild;
  }

  public void updateAutoAdvChild(DisplayChangeEvent event)
  {
    _autoAdvChild = event.getNewChildId();
  }

  /**
   * Action listener for the "auto advance" manual jump buttons.
   * @param e the ActionEvent associated with the action
   */
  public void autoAdvManualJump(ActionEvent e)
  {
    _animateDisplayedChild("dmoTpl:autoAdvDeck", _autoAdvChild);
  }

  /**
   * Poll listener for the "auto advance" deck.
   * @param e the PollEvent associated with the polling
   */
  public void autoAdvHandlePoll(PollEvent e)
  {
    String displayedChild = getAutoAdvChild();
    int displayedChildNumber = Integer.valueOf(displayedChild.substring(_AUTO_ADV_CHILD_PREFIX.length()));
    if (_AUTO_ADV_CHILD_COUNT == displayedChildNumber)
      displayedChildNumber = 0;
    setAutoAdvChild(_AUTO_ADV_CHILD_PREFIX + ++displayedChildNumber);
    _animateDisplayedChild("dmoTpl:autoAdvDeck", _autoAdvChild);
  }

  // -- End of auto-advance deck methods --

  // -- Start of generic deck methods --

  public void setGenericDeckSearchExpression(String searchExpression)
  {
    _genericDeckSearchExpression = searchExpression;
  }

  public void setGenericChild(String genericChild)
  {
    _genericChild = genericChild;
  }

  public String getGenericBackNavigate()
  {
    return _genericBackNavigate;
  }

  public void setGenericBackNavigate(String genericBackNavigate)
  {
    _genericBackNavigate = genericBackNavigate;
  }

  public String getGenericForwardNavigate()
  {
    return _genericForwardNavigate;
  }

  public void setGenericForwardNavigate(String genericForwardNavigate)
  {
    _genericForwardNavigate = genericForwardNavigate;
  }

  public long getTimeStamp()
  {
    return System.currentTimeMillis();
  }
  /**
   * Action listener for the "auto advance" manual jump buttons.
   * @param e the ActionEvent associated with the action
   */
  public void animateToGenericChild(ActionEvent e)
  {
    _animateDisplayedChild(_genericDeckSearchExpression, _genericChild);

    // Reset values to null so they won't be carried over (this is a shared generic utility):
    _genericChild                = null;
    _genericDeckSearchExpression = null;
  }

  // -- End of generic deck methods --

  /**
   * Animate the display of a deck child.
   */
  private void _animateDisplayedChild(String searchExpression, String newDisplayedChild)
  {
    RichDeck deck =
      (RichDeck)FacesContext.getCurrentInstance().getViewRoot().findComponent(searchExpression);

    // Update the displayedChild:
    deck.setDisplayedChild(newDisplayedChild);

    // Add this component as a partial target:
    RequestContext rc = RequestContext.getCurrentInstance();
    rc.addPartialTarget(deck);
  }

  private String _autoAdvChild                = null;
  private String _genericChild                = null;
  private String _genericDeckSearchExpression = null;
  private String _genericBackNavigate         = null;
  private String _genericForwardNavigate      = null;

  private static final String _AUTO_ADV_CHILD_PREFIX = "autoAdvChild";
  private static final int    _AUTO_ADV_CHILD_COUNT  = 4;

  @SuppressWarnings("compatibility:-6625324503752553560")
  private static final long serialVersionUID = 1L;
}
