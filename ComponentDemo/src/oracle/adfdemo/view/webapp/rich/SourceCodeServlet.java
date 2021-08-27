/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.webapp.rich;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class SourceCodeServlet extends HttpServlet
{
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
  {
  String webPage = req.getServletPath();
  
  // remove the '*.source' suffix that maps to this servlet
  int source = webPage.indexOf(".source");
  webPage = webPage.substring(0, source);

  //remove "/faces" mapping
  webPage = StringUtils.remove(webPage, "/faces");

  // get the actual file location of the requested resource
  ServletContext context = getServletConfig().getServletContext();  
  String realPath = context.getRealPath(webPage);

  // getRealPath returns null for Weblogic cause war file is
  // not expanded.
  if (realPath == null)
  {
    try
    {
      java.net.URL url = context.getResource(webPage);
      realPath = url.getPath();
    }
    catch (Exception e)
    {
      throw new RuntimeException("Unable to get the Webapp Root directory.", e);        
    }
  }

  // output an HTML page
  res.setContentType("text/plain");

  // print some html
  ServletOutputStream out = res.getOutputStream();

  // print the file
  InputStream in = null;
  try 
  {
      in = new BufferedInputStream(new FileInputStream(realPath));
      int ch;
      while ((ch = in.read()) !=-1) 
      {
          out.print((char)ch);
      }
  }
  finally {
      if (in != null) in.close();  // very important
  }
}

}
