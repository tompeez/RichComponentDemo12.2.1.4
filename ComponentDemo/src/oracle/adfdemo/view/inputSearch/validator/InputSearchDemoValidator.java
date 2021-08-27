/* Copyright (c) 2016, 2019, Oracle and/or its affiliates. 
All rights reserved.*/

package oracle.adfdemo.view.inputSearch.validator;


import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.input.RichInputSearch;

import oracle.adfdemo.view.inputSearch.model.DemoSearchModel;
import oracle.adfdemo.view.inputSearch.model.Employee;


public class InputSearchDemoValidator implements Validator
{
  @Override
  public void validate(FacesContext context, UIComponent component, Object value)
  {
    if (!(component instanceof RichInputSearch) || value == null)
    {
      return;
    }

    Set<Object> values = (Set<Object>) value;
    if (values.isEmpty())
    {
      return;
    }

    value = values.iterator().next();
    RichInputSearch inputSearch = (RichInputSearch) component;
    String valueAttr = inputSearch.getValueAttribute();

    Multimap<String, String> attributeValueFilter =
      MultimapBuilder.hashKeys().arrayListValues().build();
    for (Object val : values)
    {
      attributeValueFilter.put(valueAttr, val.toString());
    }
    List<Employee> employees = (List<Employee>)
                                DemoSearchModel.getInstance().getEmployees(attributeValueFilter);

    Set<Object> copyValues = new LinkedHashSet<Object>(values);
    for (Employee e : employees)
    {
      copyValues.remove(e.getId());
    }

    if (!copyValues.isEmpty())
    {
      String errorMsg = "Your current selection of " + copyValues.iterator().next() + " is no longer available";
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg, errorMsg);
      throw new ValidatorException(message);
    }
  }
}
