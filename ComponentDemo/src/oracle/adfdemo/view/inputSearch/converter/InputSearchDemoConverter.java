/* Copyright (c) 2016, 2019, Oracle and/or its affiliates. 
All rights reserved.*/

package oracle.adfdemo.view.inputSearch.converter;


import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import oracle.adf.view.rich.component.rich.input.RichInputSearch;

import oracle.adfdemo.view.inputSearch.model.DemoSearchModel;
import oracle.adfdemo.view.inputSearch.model.SearchModelBase;


public class InputSearchDemoConverter implements Converter
{
  @Override
  public Object getAsObject(FacesContext facesContext, UIComponent component, String value)
  {
    Converter defaultConverter = _getConverterByType(facesContext, component);
    return defaultConverter == null ?
            value :
            defaultConverter.getAsObject(facesContext, component, value);
  }

  @Override
  public String getAsString(FacesContext facesContext, UIComponent component, Object value)
  {
    if ((component instanceof RichInputSearch) && value != null)
    {
      RichInputSearch inputSearch = (RichInputSearch) component;
      String valueAttr = inputSearch.getValueAttribute();
      List<String> displayAttrs = inputSearch.getDisplayAttributes();

      Map<Integer, Map<String, Object>> selectedData = inputSearch.getSelection();
      if (selectedData != null && !selectedData.isEmpty())
      {
        Map<String, Object> suggestion = selectedData.get(value);
        Map<String, Object> suggestionObj = (Map<String, Object>) suggestion.get("data");
        assert value.equals(suggestionObj.get(valueAttr));

        StringBuilder convertedString = new StringBuilder(displayAttrs.size() * 10);
        for (String displayAttr : displayAttrs)
        {
          convertedString.append(suggestionObj.get(displayAttr)).append(" ");
        }
        return convertedString.substring(0, convertedString.length() - 1);
      }

      // Resolve the display attributes by querying the model
      Multimap<String, String> attributeValueFilter =
        MultimapBuilder.hashKeys().arrayListValues().build();
      attributeValueFilter.put(valueAttr, value.toString());

      List<SearchModelBase> employees = (List<SearchModelBase>)
        DemoSearchModel.getInstance().getEmployees(attributeValueFilter);
      if (employees != null && employees.size() == 1)
      {
        SearchModelBase employee = employees.get(0);
        StringBuilder convertedString = new StringBuilder(displayAttrs.size() * 10);
        for (String displayAttr : displayAttrs)
        {
          convertedString.append(employee.getProperty(displayAttr)).append(" ");
        }
        return convertedString.substring(0, convertedString.length() - 1);
      }
    }
    return (value != null) ? value.toString() : null;
  }

  private Converter _getConverterByType(FacesContext context, UIComponent component)
  {
    if (component instanceof RichInputSearch)
    {
      RichInputSearch inputSearch = (RichInputSearch) component;
      if ("id".equals(inputSearch.getValueAttribute()))
      {
        // The valueAttribute in case of the demo pages is "id" which is a Integer
        // field in Employee.java
        return context.getApplication().createConverter(Integer.class);
      }
    }
    return null;
  }
}
