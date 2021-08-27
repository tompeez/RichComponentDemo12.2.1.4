/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich.validate;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.webapp.ValidatorELTag;

import javax.servlet.jsp.JspException;

public class ValidateSSNTag extends ValidatorELTag {

    public ValidateSSNTag() {
    }

    public void setBinding(ValueExpression binding) throws JspException {
        this._binding = binding;
    }

    protected Validator createValidator() throws JspException {
        SSNValidator validator = null;
        // If "binding" is set, use it to create a converter instance.
        if (_binding != null) {
            try {
                validator =
                        (SSNValidator)_binding.getValue(FacesContext.getCurrentInstance().getELContext());
                if (validator != null) {
                    return validator;
                }
            } catch (Exception e) {
                throw new JspException("Exception creating converter using binding",
                                       e);
            }
        }
        Application app = FacesContext.getCurrentInstance().getApplication();
        validator =
                (SSNValidator)app.createValidator(SSNValidator.VALIDATOR_ID);
        return validator;
    }

    public void release() {
        super.release();
        _binding = null;
    }

    private ValueExpression _binding = null;
}