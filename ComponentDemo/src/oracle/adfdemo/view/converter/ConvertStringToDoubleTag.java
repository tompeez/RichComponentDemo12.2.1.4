/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.converter;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.webapp.ConverterELTag;

import javax.servlet.jsp.JspException;

public class ConvertStringToDoubleTag extends ConverterELTag {

    public ConvertStringToDoubleTag() {
    }

    public void setBinding(ValueExpression binding) throws JspException {
        this._binding = binding;
    }

    protected Converter createConverter() throws JspException {
        StringToDoubleConverter converter = null;
        // If "binding" is set, use it to create a converter instance.
        if (_binding != null) {
            try {
                converter =
                        (StringToDoubleConverter)_binding.getValue(FacesContext.getCurrentInstance().getELContext());
                if (converter != null) {
                    return converter;
                }
            } catch (Exception e) {
                throw new JspException("Exception creating converter using binding",
                                       e);
            }
        }
        Application app = FacesContext.getCurrentInstance().getApplication();
        converter =
                (StringToDoubleConverter)app.createConverter(StringToDoubleConverter.CONVERTER_ID);
        return converter;
    }

    public void release() {
        super.release();
        _binding = null;
    }

    private ValueExpression _binding = null;
}
