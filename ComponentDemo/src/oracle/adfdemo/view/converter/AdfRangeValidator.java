/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.converter;

import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.model.NumberRange;

import org.apache.myfaces.trinidad.validator.ClientValidator;

public class AdfRangeValidator implements Validator, ClientValidator
{

  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException
  {
    NumberRange nr = (NumberRange) value;
    double max = nr.getMaximum().doubleValue();
    double min = nr.getMinimum().doubleValue();
    
    if (max-min ==2)
      throw new ValidatorException(new FacesMessage("not a good range!"));
  }

  public Collection<String> getClientImportNames()
  {
    return null;
  }

  public String getClientLibrarySource(FacesContext context)
  {
    return context.getExternalContext().getRequestContextPath() + 
    "/jsLibs/adfRangeValidator.js";
  }

  public String getClientScript(FacesContext context, UIComponent component)
  {
    return null;
  }

  public String getClientValidation(FacesContext context, UIComponent component)
  {
    return ("new AdfRangeValidator()");
  }

}