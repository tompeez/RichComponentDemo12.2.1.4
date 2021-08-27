/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

package oracle.adfdemo.view.explorer.rich.converter;

import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.myfaces.trinidad.convert.ClientConverter;

public class RatingConverter implements Converter, ClientConverter 
{
  public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
  {
    Double d = null;
    if ("Needs Significant Improvement".equals(arg2))
      d = new Double(0);
    else if ("1".equals(arg2))
      d = new Double(1);
    else if ("2".equals(arg2))
      d = new Double(2);
    else if ("3".equals(arg2))
      d = new Double(3);
    else if ("4".equals(arg2))
      d = new Double(4);
    else if ("Average".equals(arg2))
      d = new Double(5);
    else if ("6".equals(arg2))
      d = new Double(6);
    else if ("7".equals(arg2))
      d = new Double(7);
    else if ("8".equals(arg2))
      d = new Double(8);
    else if ("9".equals(arg2))
      d = new Double(9);
    else if ("The Best!".equals(arg2))
      d = new Double(10);
    else
      d = new Double(arg2);
    return d;
  }

  public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) 
  {
    if (arg2 instanceof Double) 
    {
      String dou = ((Double)arg2).toString();
      if (dou.equals("0.0"))
        dou = "Needs Significant Improvement";
      if (dou.equals("1.0"))
        dou = "1";
      else if (dou.equals("2.0"))
        dou = "2";
      else if (dou.equals("3.0"))
        dou = "3";
      else if (dou.equals("4.0"))
        dou = "4";
      else if (dou.equals("5.0"))
        dou = "Average";
      else if (dou.equals("6.0"))
        dou = "6";
      else if (dou.equals("7.0"))
        dou = "7";
      else if (dou.equals("8.0"))
        dou = "8";
      else if (dou.equals("9.0"))
        dou = "9";
      else if (dou.equals("10.0"))
        dou = "The Best!";
      return dou;
    } 
    else
    {
      return arg2.toString();
    }
  }

  public String getClientConversion(FacesContext arg0, UIComponent arg1) 
  {
    return ("new RatingConverter()");
  }

  public Collection<String> getClientImportNames() 
  {
    return null;
  }

  public String getClientLibrarySource(FacesContext context) 
  {
    return context.getExternalContext().getRequestContextPath() + 
           "/fileExplorer/jsLibs/ratingConverter.js";
  }

  public String getClientScript(FacesContext arg0, UIComponent arg1) 
  {
    return null;
  }
}
