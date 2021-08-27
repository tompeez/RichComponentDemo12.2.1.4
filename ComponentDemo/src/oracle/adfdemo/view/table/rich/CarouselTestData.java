/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;

import java.util.Locale;

import javax.el.ELContext;
import javax.el.ELResolver;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.component.UIXCollection;
import org.apache.myfaces.trinidad.context.RequestContext;

public class CarouselTestData
{
  public CarouselTestData()
  {
  }

  public void pprField(ActionEvent event)
  {
    // clear out the displayRow first
    UIComponent parentComponent = event.getComponent().getParent();
    UIXCollection pprCarousel = (UIXCollection)parentComponent.findComponent("carouselPPR");

    // get the rowkey
    EditableValueHolder edit = (EditableValueHolder)parentComponent.findComponent("itemKeyValue");
    Object rowKey = edit.getValue();

    if (rowKey != null)
    {
      EditableValueHolder select = (EditableValueHolder)parentComponent.findComponent("fieldName");
      Object selectValue = select.getValue();

      if (selectValue != null)
      {
        EditableValueHolder fieldEdit =
          (EditableValueHolder)parentComponent.findComponent("fieldValue");
        Object fieldVal = fieldEdit.getValue();

        if (fieldVal != null)
        {
          Object oldRowKey = pprCarousel.getRowKey();
          try
          {
            pprCarousel.setRowKey(new Integer(rowKey.toString()));

            Object rowObject = pprCarousel.getRowData();
            FacesContext context = FacesContext.getCurrentInstance();
            ELResolver resolver = context.getApplication().getELResolver();
            ELContext elContext = context.getELContext();
            fieldVal =
              _convertFieldDataType(
                resolver.getType(elContext, rowObject, selectValue.toString()), fieldVal);
            resolver.setValue(elContext, rowObject, selectValue.toString(), fieldVal);

            RequestContext.getCurrentInstance().addPartialTarget(
              pprCarousel.findComponent(selectValue.toString()));
          }
          finally
          {
            pprCarousel.setRowKey(oldRowKey);
          }
        }
      }
    }
  }

  private Object _convertFieldDataType(Class clazz, Object value)
  {
    if (clazz == Date.class)
    {
      try
      {
        value =
            DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse(value.toString());
      }
      catch (ParseException e)
      {
        System.out.println("error parsing date string");
      }
    }
    else if (clazz == Integer.class || clazz == int.class)
    {
      value = new Integer(value.toString());
    }
    else if (clazz == Boolean.class || clazz == boolean.class)
    {
      value = new Boolean(value.toString());
    }
    return value;
  }
}
