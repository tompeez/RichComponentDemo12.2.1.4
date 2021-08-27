/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.converter;

import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.myfaces.trinidad.convert.ClientConverter;

public class StringToDoubleConverter implements Converter, ClientConverter
{
  public static final String CONVERTER_ID = "oracle.adfdemo.stringToDoubleConverter";
  
  public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
  {
    Double d = null;
    if("ONE".equals(arg2))
      d = new Double(1);
    else if("TWO".equals(arg2))
      d = new Double(2);
    else if("THREE".equals(arg2))
      d = new Double(3);
    else if("FOUR".equals(arg2))
      d = new Double(4);
    else if("FIVE".equals(arg2))
      d = new Double(5);
    else if("SIX".equals(arg2))
      d = new Double(6);
    else if("SEVEN".equals(arg2))
      d = new Double(7);
    else if("EIGHT".equals(arg2))
      d = new Double(8);
    else if("NINE".equals(arg2))
      d = new Double(9);
    else if("TEN".equals(arg2))
      d = new Double(10);
    else
      d = new Double(arg2);
    return d;
  }

  public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
  {
    if(arg2 instanceof Double){
      String dou =((Double) arg2).toString();
      if(dou.equals("1.0"))
        dou = "ONE";
      else if(dou.equals("2.0"))
        dou = "TWO";
      else if(dou.equals("3.0"))
        dou = "THREE";
      else if(dou.equals("4.0"))
        dou = "FOUR";
      else if(dou.equals("5.0"))
        dou = "FIVE";
      else if(dou.equals("6.0"))
        dou = "SIX";
      else if(dou.equals("7.0"))
        dou = "SEVEN";
      else if(dou.equals("8.0"))
        dou = "EIGHT";
      else if(dou.equals("9.0"))
        dou = "NINE";
      else if(dou.equals("10.0"))
        dou = "TEN";
      return dou;
    }
    else
      return arg2.toString();
  }

  public String getClientConversion(FacesContext arg0, UIComponent arg1)
  {
    return ("new SimpleStringToDoubleConverter()");
  }

  public Collection<String> getClientImportNames()
  {
    return null;
  }

  public String getClientLibrarySource(
      FacesContext context)
     {
       return context.getExternalContext().getRequestContextPath() + 
               "/jsLibs/simpleStringToDoubleConverter.js";    
     }
  public String getClientScript(FacesContext arg0, UIComponent arg1)
  {
    return null;
  }

}