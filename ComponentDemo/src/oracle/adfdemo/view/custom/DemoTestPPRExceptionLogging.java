/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.custom;

import java.util.logging.Handler;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import oracle.adf.share.logging.ADFLogger;

import oracle.adfinternal.view.faces.config.rich.RegistrationConfigurator;
import oracle.adfinternal.view.faces.renderkit.rich.RichRenderKit;
import oracle.adfinternal.view.faces.util.rich.LoggerUtils;

/**
 * This bean is used as a backing bean for a selenium test in order to test
 * bug 5948106.  Durring a PPR it's getValue method will throw a runtime 
 * exception.  This is as designed in order to test the negative test case.
 * 
 * This bean should be session scope so that it has the proper lifecycle AND
 * can be cleaned up when the session using it is done.
 */
public class DemoTestPPRExceptionLogging implements HttpSessionBindingListener
{
  public DemoTestPPRExceptionLogging()
  {
    _handler = new ExceptionLogHandler();
    _LOGGER.addHandler(_handler);
  }
  
  /**
   * Returns a message on a non-ppr request and throws a runtime exception on
   * a ppr request.
   */
  public String getLogMessage()
  {
    if(RichRenderKit.isPprRequest(FacesContext.getCurrentInstance())) 
    {
      throw new RuntimeException("This is a PPR Request");
    }

    if(_lastMessage != null)
    {
      return _lastMessage;
    }
    else
    {
      return "No messages";
    }
  }
  
  public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent)
  {
    //do nothing
  }

  public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent)
  {
    //make sure the handler is removed from the logger to prevent memory leaks
    _LOGGER.removeHandler(_handler);
    _handler=null;
  }
  
  private Handler _handler;
  private String _lastMessage;
  
  //This needs to plug into a logger for RegistrationConfigurator
  //private static final ADFLogger _LOGGER = LoggerUtils.createADFLogger(RegistrationConfigurator.class);
  private static final ADFLogger _LOGGER = LoggerUtils.createADFLogger(RegistrationConfigurator.class);

  private class ExceptionLogHandler extends Handler
  {    
    public void publish(LogRecord record)
    {
      if(Level.SEVERE.equals(record.getLevel()))
      {
        _lastMessage = record.getMessage();
      }
    }

    public void flush()
    {
    }

    public void close()
    {
    }

    @Override
    public synchronized Level getLevel()
    {
      return Level.SEVERE;
    }
  }  
}
