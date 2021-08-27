/*
** Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
*/
package oracle.adfdemo.view.webapp.rich;

import java.io.CharArrayWriter;

import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public final class CharArrayResponseWrapper extends HttpServletResponseWrapper
{
  public CharArrayResponseWrapper(HttpServletResponse response)
  {
    super(response);
    _writer = new CharArrayWriter();
  }
  
  public PrintWriter getWriter()
  {
    return new PrintWriter(_writer);
  }

  public String toString()
  {
    return _writer.toString();
  }
  private CharArrayWriter _writer;
}