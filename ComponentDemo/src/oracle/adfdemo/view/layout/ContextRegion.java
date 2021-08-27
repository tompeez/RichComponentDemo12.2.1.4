/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.layout;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

/**
 * Managed bean for the "context region" visual design demo.
 */
public class ContextRegion
{
  /**
   * Handler for disclosure changes on the global context showDetail.
   * @param event the disclosure event
   */
  public void globalDisclosureHandler(DisclosureEvent event)
  {
    // update the disclosed state so we can update everything that depends on it:
    _contextGlobalDisclosed = event.isExpanded();

    // redraw the layout container so the entire page layout can adjust to the new sizes:
    AdfFacesContext afc = AdfFacesContext.getCurrentInstance();
    afc.addPartialTarget(getDisclosureLayoutContainer());
  }

  /**
   * Handler for disclosure changes on the context 1 showDetail.
   * @param event the disclosure event
   */
  public void context1DisclosureHandler(DisclosureEvent event)
  {
    // update the disclosed state so we can update everything that depends on it:
    _context1Disclosed = event.isExpanded();

    // redraw the layout container so the entire page layout can adjust to the new sizes:
    AdfFacesContext afc = AdfFacesContext.getCurrentInstance();
    afc.addPartialTarget(getDisclosureLayoutContainer());
  }

  /**
   * Handler for disclosure changes on the context 2 showDetail.
   * @param event the disclosure event
   */
  public void context2DisclosureHandler(DisclosureEvent event)
  {
    // update the disclosed state so we can update everything that depends on it:
    _context2Disclosed = event.isExpanded();

    // redraw the layout container so the entire page layout can adjust to the new sizes:
    AdfFacesContext afc = AdfFacesContext.getCurrentInstance();
    afc.addPartialTarget(getDisclosureLayoutContainer());
  }

  /**
   * Retrieves the number of pixels tall that the global header should be.
   * @return the number of pixels tall that the global header should be
   */
  public int getHeaderGlobalSize()
  {
    if (_contextGlobalDisclosed)
    {
      return _HEADER_GLOBAL_SIZE_DISCLOSED;
    }
    return _HEADER_GLOBAL_SIZE_UNDISCLOSED;
  }

  /**
   * Retrieves the number of pixels tall that the primary layer header should be.
   * @return the number of pixels tall that the primary layer header should be
   */
  public int getHeader1Size()
  {
    if (_context1Disclosed)
    {
      return _HEADER_1_SIZE_DISCLOSED;
    }
    return _HEADER_1_SIZE_UNDISCLOSED;
  }

  /**
   * Retrieves the number of pixels tall that the secondary layer header should be.
   * @return the number of pixels tall that the secondary layer header should be
   */
  public int getHeader2Size()
  {
    if (_context2Disclosed)
    {
      return _HEADER_2_SIZE_DISCLOSED;
    }
    return _HEADER_2_SIZE_UNDISCLOSED;
  }

  /**
   * Specifies whether the context global showDetail is disclosed.
   * @param contextGlobalDisclosed whether the context global showDetail is disclosed
   */
  public void setContextGlobalDisclosed(boolean contextGlobalDisclosed)
  {
    _contextGlobalDisclosed = contextGlobalDisclosed;
  }

  /**
   * Retrieves whether the context global showDetail is disclosed.
   * @return whether the context global showDetail is disclosed
   */
  public boolean getContextGlobalDisclosed()
  {
    return _contextGlobalDisclosed;
  }

  /**
   * Specifies whether the context 1 showDetail is disclosed.
   * @param context1Disclosed whether the context 1 showDetail is disclosed
   */
  public void setContext1Disclosed(boolean context1Disclosed)
  {
    _context1Disclosed = context1Disclosed;
  }

  /**
   * Retrieves whether the context 1 showDetail is disclosed.
   * @return whether the context 1 showDetail is disclosed
   */
  public boolean getContext1Disclosed()
  {
    return _context1Disclosed;
  }

  /**
   * Specifies whether the context 2 showDetail is disclosed.
   * @param context2Disclosed whether the context 2 showDetail is disclosed
   */
  public void setContext2Disclosed(boolean context2Disclosed)
  {
    _context2Disclosed = context2Disclosed;
  }

  /**
   * Retrieves whether the context 2 showDetail is disclosed.
   * @return whether the context 2 showDetail is disclosed
   */
  public boolean getContext2Disclosed()
  {
    return _context2Disclosed;
  }

  /**
   * Retrieves the disclosure layout container component.
   * @return the disclosure layout container component
   */
  public UIComponent getDisclosureLayoutContainer()
  {
    if (_disclosureLayoutContainer == null)
    {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      UIComponent document = facesContext.getViewRoot().getChildren().get(0);
      _disclosureLayoutContainer = document;
    }
    return _disclosureLayoutContainer;
  }

  private boolean _contextGlobalDisclosed = true;
  private boolean _context1Disclosed = true;
  private boolean _context2Disclosed = true;
  private UIComponent _disclosureLayoutContainer = null;

  private static final int _HEADER_GLOBAL_SIZE_DISCLOSED = 105;
  private static final int _HEADER_GLOBAL_SIZE_UNDISCLOSED = 62;
  private static final int _HEADER_1_SIZE_DISCLOSED = 80;
  private static final int _HEADER_1_SIZE_UNDISCLOSED = 45;
  private static final int _HEADER_2_SIZE_DISCLOSED = 105;
  private static final int _HEADER_2_SIZE_UNDISCLOSED = 70;
}
