/* Copyright (c) 2009, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.layout;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.fragment.RichPageTemplate;
import oracle.adf.view.rich.event.DialogEvent;

import org.apache.myfaces.trinidad.context.RequestContext;


public class DemoPageTemplateModelHandler
{
  public void removeKey(ActionEvent event)
  {
    _model.getContextVariables().remove(_keyToRemove);
    RequestContext.getCurrentInstance().addPartialTarget(
      _getPageTemplate(event.getComponent()).getParent());
  }

  public void setKeyToRemove(String keyToRemove)
  {
    _keyToRemove = keyToRemove;
  }

  public void validateNewKey(
    FacesContext context,
    UIComponent  component,
    Object       value
    ) throws ValidatorException
  {
    if (_model.getContextVariables().containsKey(value))
    {
      throw new ValidatorException(
        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Key already in use", "Key already in use"));
    }
  }

  public void handleDialogEvent(DialogEvent event)
  {
    if (event.getOutcome() == DialogEvent.Outcome.ok)
    {
      _model.getContextVariables().put(_newKey, _newValue);
      RequestContext.getCurrentInstance().addPartialTarget(
        _getPageTemplate(event.getComponent()).getParent());
    }
  }

  public final void viewIdChanged(ActionEvent event)
  {
    _model.setCurrentViewId(_viewId);
    RequestContext.getCurrentInstance().addPartialTarget(
      _getPageTemplate(event.getComponent()).getParent());
  }

  public final void setModel(DemoPageTemplateModel model)
  {
    _model = model;
  }

  public final DemoPageTemplateModel getModel()
  {
    return _model;
  }

  public final void setNewKey(String newKey)
  {
    _newKey = newKey;
  }

  public final String getNewKey()
  {
    return _newKey;
  }

  public final void setNewValue(String newValue)
  {
    _newValue = newValue;
  }

  public final String getNewValue()
  {
    return _newValue;
  }

  public List<String> getKeys()
  {
    return new ArrayList<String>(_model.getContextVariables().keySet());
  }

  public String getViewId()
  {
    return _viewId == null ? _model.getCurrentViewId() : _viewId;
  }

  public final void setViewId(String viewId)
  {
    _viewId = viewId;
  }

  private RichPageTemplate _getPageTemplate(UIComponent component)
  {
    for (UIComponent c = component; c != null; c = c.getParent())
    {
      if (c instanceof RichPageTemplate)
      {
        return (RichPageTemplate)c;
      }
    }
    return null;
  }

  private DemoPageTemplateModel _model;
  private String _keyToRemove;
  private String _newKey;
  private String _newValue;
  private String _viewId;
}
