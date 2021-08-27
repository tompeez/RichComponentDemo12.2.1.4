/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

package oracle.adfdemo.view.explorer.rich.validator;

import java.awt.List;

import java.util.ArrayList;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.trinidad.validator.ClientValidator;

public class ShuttleValidator implements Validator, ClientValidator
{
  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException
  {
    ArrayList topActors = (ArrayList) value;
    
    if (topActors.size() > 5)
      throw new ValidatorException(new FacesMessage("Please limit your selection to 5 actors!", ""));
  }

  public Collection<String> getClientImportNames()
  {
    return null;
  }

  public String getClientLibrarySource(FacesContext context)
  {
    return context.getExternalContext().getRequestContextPath() + 
    "/fileExplorer/jsLibs/shuttleValidator.js";
  }

  public String getClientScript(FacesContext context, UIComponent component)
  {
    return null;
  }

  public String getClientValidation(FacesContext context, UIComponent component)
  {
    return ("new ShuttleValidator()");
  }

}
