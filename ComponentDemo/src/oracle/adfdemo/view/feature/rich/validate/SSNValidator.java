/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich.validate;

import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.trinidad.util.LabeledFacesMessage;
import org.apache.myfaces.trinidad.validator.ClientValidator;


/**
 * <p>Social Security number validator.</p>
 * <p>This is a trivial implementation that checks the number starts with 123.</p>
 *
 */
public class SSNValidator implements Validator, ClientValidator
{
    
  public static final String VALIDATOR_ID = "oracle.adfdemo.SSNValidate";


  public void validate(
    FacesContext context,
    UIComponent component,
    Object value
    ) throws ValidatorException
  {
    if (((Integer)value).toString().startsWith("123"))
      return;
      
    throw new ValidatorException(_getMessage(component));
  }

  public String getClientLibrarySource(
   FacesContext context)
  {
    return context.getExternalContext().getRequestContextPath() + 
            "/jsLibs/ssnValidator.js";    
  }

  public String getClientValidation(
    FacesContext context,
   UIComponent component)
  {
    // in a real app the messages would be translated
    return ("new SSNValidator('Invalid social security number.','Value \"{1}\" must start with \"123\".')");
  }

  
  public Collection<String> getClientImportNames()
  {
    return null;
  }

  public String getClientScript(
   FacesContext context,
   UIComponent component)
  {
    return null;
  }

  private LabeledFacesMessage _getMessage(
   UIComponent component)
  {
    // Using the LabeledFacesMessage allows the <af:messages> component to
    // properly prepend the label as a link.
    LabeledFacesMessage lfm =
      new LabeledFacesMessage(FacesMessage.SEVERITY_ERROR,
                              _SUMMARY_ERROR_TEXT, _DETAIL_ERROR_TEXT);
    if (component != null)
    {
      Object label = null;
      label = component.getAttributes().get("label");
      if (label == null)
        label = component.getValueExpression("label");
      if (label != null)
        lfm.setLabel(label);
    }
    return lfm;
  }

  private static final String _SUMMARY_ERROR_TEXT = "Invalid social security number.";
  private static final String _DETAIL_ERROR_TEXT
    = "Value \"{1}\" must start with \"123\".";    


}
