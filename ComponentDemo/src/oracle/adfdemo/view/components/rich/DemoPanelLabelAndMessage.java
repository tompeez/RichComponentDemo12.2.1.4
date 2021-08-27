/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class DemoPanelLabelAndMessage
{
  /**
   * Creates a FacesMessage.
   * @param event the ActionEvent associated with the action
   */
  public void createFacesMessage(ActionEvent event)
  {
    FacesMessage parentMessage = new FacesMessage(
      FacesMessage.SEVERITY_ERROR,
      "This is a faces message.",
      "You clicked on a button that simply shows a sample faces message.");
    FacesContext facesContext = FacesContext.getCurrentInstance();
    facesContext.addMessage(null, parentMessage);
  }
}
