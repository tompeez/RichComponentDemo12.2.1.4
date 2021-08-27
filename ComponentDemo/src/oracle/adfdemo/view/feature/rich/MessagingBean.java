/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Messaging bean used in the visual design demos.
 */
public class MessagingBean
{
  /**
   * Creates a single component-level and a single page-level FacesMessage to show to the user.
   */
  public void showOneComponentAndOnePageLevelMessage()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage(null, _getFacesMessage(FacesMessage.SEVERITY_ERROR));
    context.addMessage("dmoTpl:clientId1", _getFacesMessage(FacesMessage.SEVERITY_WARN));
  }

  /**
   * Creates two page-level FacesMessages to show to the user.
   */
  public void showTwoPageLevelMessages()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage(null, _getFacesMessage(FacesMessage.SEVERITY_ERROR));
    context.addMessage(null, _getFacesMessage(FacesMessage.SEVERITY_WARN));
  }

  /**
   * Creates one component-level FacesMessage to show to the user.
   */
  public void showOneComponentLevelMessage()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage("dmoTpl:clientId1", _getFacesMessage(FacesMessage.SEVERITY_WARN));
  }

  /**
   * Creates a list of component-level FacesMessages to show to the user where the maximum severity
   * is error.
   */
  public void showErrorMessagesListDialog()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage("dmoTpl:clientId1", _getFacesMessage(FacesMessage.SEVERITY_ERROR));
    context.addMessage("dmoTpl:clientId1", _getFacesMessage(FacesMessage.SEVERITY_WARN));
    context.addMessage("dmoTpl:clientId2", _getFacesMessage(FacesMessage.SEVERITY_INFO));
    context.addMessage("dmoTpl:clientId3", _getFacesMessage(FacesMessage.SEVERITY_ERROR));
  }

  /**
   * Creates a list of component-level FacesMessages to show to the user where the maximum severity
   * is warning.
   */
  public void showWarningMessagesListDialog()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage("dmoTpl:clientId1", _getFacesMessage(FacesMessage.SEVERITY_WARN));
    context.addMessage("dmoTpl:clientId1", _getFacesMessage(FacesMessage.SEVERITY_INFO));
    context.addMessage("dmoTpl:clientId2", _getFacesMessage(FacesMessage.SEVERITY_INFO));
    context.addMessage("dmoTpl:clientId3", _getFacesMessage(FacesMessage.SEVERITY_WARN));
  }

  /**
   * Creates a list of component-level FacesMessages to show to the user where the maximum severity
   * is info.
   */
  public void showInfoMessagesListDialog()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage("dmoTpl:clientId1", _getFacesMessage(FacesMessage.SEVERITY_INFO));
    context.addMessage("dmoTpl:clientId2", _getFacesMessage(FacesMessage.SEVERITY_INFO));
  }

  /**
   * Creates a list of component-level FacesMessages to show to the user.
   */
  public void showMessageListDialog()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage("dmoTpl:clientId1", _getFacesMessage(FacesMessage.SEVERITY_ERROR));
    context.addMessage("dmoTpl:clientId1", _getFacesMessage(FacesMessage.SEVERITY_WARN));
    context.addMessage("dmoTpl:clientId2", _getFacesMessage(FacesMessage.SEVERITY_WARN));
    context.addMessage("dmoTpl:clientId3", _getFacesMessage(FacesMessage.SEVERITY_INFO));
  }

  /**
   * Creates a list of component-level and page-level FacesMessages to show to the user.
   */
  public void showMessageListDialogWithGlobalMessages()
  {
    showMessageListDialog(); // adds the component-level FacesMessages

    // add the page-level FacesMessages
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage(null, _getFacesMessage(FacesMessage.SEVERITY_INFO));
  }

  /**
   * Creates a sample FacesMessage for the given severity.
   * @param severity the FacesMessage.Severity the message should be created with
   * @return the FacesMessage of the given severity
   */
  private FacesMessage _getFacesMessage(FacesMessage.Severity severity)
  {
    StringBuilder summary = new StringBuilder();
    StringBuilder detail = new StringBuilder();
    String severityText = "fatal";
    String severityColor = "red";
    if (FacesMessage.SEVERITY_ERROR.equals(severity))
    {
      severityText = "error";
    }
    if (FacesMessage.SEVERITY_WARN.equals(severity))
    {
      severityText = "warning";
      severityColor = "#FFCC33";
    }
    else if (FacesMessage.SEVERITY_INFO.equals(severity))
    {
      severityText = "info";
      severityColor = "blue";
    }
    summary.append("Text summarizing the ");
    summary.append(severityText);
    summary.append(" message.");
    detail.append("<html>Text describing the <span style='color: ");
    detail.append(severityColor);
    detail.append("; font-weight: bold;'>");
    detail.append(severityText);
    detail.append("</span> in detail.  If it's going to wrap it would look like this.</html>");
    return new FacesMessage(severity, summary.toString(), detail.toString());
  }
}
