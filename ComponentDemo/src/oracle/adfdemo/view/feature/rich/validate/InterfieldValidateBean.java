/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich.validate;

import java.io.Serializable;

import java.util.Date;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputDate;


public class InterfieldValidateBean
  implements Serializable
{
  //  public constants

  //  constructors (if public)

  public InterfieldValidateBean()
  {
  }
  // public static methods
  // public nested class

  // public methods

  /////////////////////////////////
  // Methods related to Phase validation
  ///////////////////////////////

  public void phaseValidate(PhaseEvent phaseEvent)
  {
    if (phaseEvent.getPhaseId() != PhaseId.UPDATE_MODEL_VALUES)
      return;

    if (_phaseStartDate != null && _phaseEndDate != null)
    {
      Date d1 = (Date) _phaseStartDate.getValue();
      Date d2 = (Date) _phaseEndDate.getValue();

      if (d1.compareTo(d2) > 0)
      {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        _addRangeErrorMsg(facesContext, _phaseStartDate, "Phase Listener");
        _phaseStartDate.setValid(Boolean.FALSE); 
        facesContext.renderResponse();
      }
    }
  }

  public PhaseId getPhaseId()
  {
    return PhaseId.UPDATE_MODEL_VALUES;
  }

  ///////////////////////////////////////////////////
  //  Methods related to valueChange validation
  //////////////////////////////////////////////////
  public void validateOnValueChange (ValueChangeEvent event)
  {
    Date d1 = (Date) this._valueChangeStartDate.getValue();
    Date d2 = (Date) this._valueChangeEndDate.getValue();

    if (d1.compareTo(d2) > 0)
    {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      _addRangeErrorMsg(facesContext, _valueChangeStartDate, "ValueChangeEvent listener");
      _valueChangeStartDate.setValid(Boolean.FALSE); 
      facesContext.renderResponse();
    }
  }


  ////////////////////// 
  //  Getters and Setters
  ////////////////////////
  public void setPhaseStartDate(RichInputDate _phaseStartDate)
  {
    this._phaseStartDate = _phaseStartDate;
  }

  public RichInputDate getPhaseStartDate()
  {
    return _phaseStartDate;
  }

  public void setPhaseEndDate(RichInputDate _phaseEndDate)
  {
    this._phaseEndDate = _phaseEndDate;
  }

  public RichInputDate getPhaseEndDate()
  {
    return _phaseEndDate;
  }

  public void setValueChangeStartDate(RichInputDate date)
  {
    this._valueChangeStartDate = date;
  }

  public RichInputDate getValueChangeStartDate()
  {
    return this._valueChangeStartDate;
  }
  public void setValueChangeEndDate(RichInputDate date)
  {
    this._valueChangeEndDate = date;
  }

  public RichInputDate getValueChangeEndDate()
  {
    return this._valueChangeEndDate;
  }


  // public variables (strongly discouraged)
  // protected static methods
  // protected methods
  // protected variables (strongly discouraged)
  // package static methods (discouraged)
  // package methods (discouraged)
  // package variables (discouraged)
  // private static methods

  // private methods

  private static void _addRangeErrorMsg(FacesContext fc, RichInputDate d, String src)
  {
    FacesMessage msg = 
      new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid range", 
                       "Detected by " + src + 
                       " : Start date must be less than or equal to End date");
    FacesContext facesContext = FacesContext.getCurrentInstance();
    facesContext.addMessage(d.getClientId(facesContext), msg);
  }

  // non-public nested classes

  // private variables

  RichInputDate _valueChangeStartDate;
  RichInputDate _valueChangeEndDate;
  RichInputDate _phaseStartDate;
  RichInputDate _phaseEndDate;

  // private constants

}
