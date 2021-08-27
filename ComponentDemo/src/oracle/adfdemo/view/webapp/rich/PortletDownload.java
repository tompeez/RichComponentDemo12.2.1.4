/* Copyright (c) 2009, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.webapp.rich;

import java.io.IOException;

import java.io.Writer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PortletDownload
  extends HttpServlet
{
  public PortletDownload()
  {
    super();
  }
  
  @Override
  public void doGet(
    HttpServletRequest  request,
    HttpServletResponse response)
    throws IOException, ServletException
  {
    
    Object portletResponse = request.getAttribute("javax.portlet.response");
    Class clazz = portletResponse.getClass();
    Method method;
    try
    {
      method = clazz.getMethod("setProperty", String.class, String.class);
      method.invoke(portletResponse, "Content-Disposition", "attachment;filename=test.txt");
    }
    catch (NoSuchMethodException e)
    {
      System.err.println("Could not find method setProperty on " + clazz.toString());
    }
    catch (IllegalAccessException e)
    {
      System.err.println("IllegalAccessException:  " + e.getMessage());
    }
    catch (InvocationTargetException e)
    {
      System.err.println("InvocationTargetException:  " + e.getMessage());
    }
    
    response.setContentType("text/plain");
    
    Writer out = response.getWriter();
    out.write("Test successful");
    out.close();
  }  
}
