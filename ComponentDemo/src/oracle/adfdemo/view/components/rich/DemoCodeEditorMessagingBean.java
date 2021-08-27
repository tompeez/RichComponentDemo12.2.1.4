/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import oracle.adf.view.rich.util.Marker;
import oracle.adf.view.rich.util.MarkerUtils;

import org.w3c.dom.Document;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class DemoCodeEditorMessagingBean implements Serializable
{

  public DemoCodeEditorMessagingBean()
  {
    _xmlValue =  DemoCodeEditorBean.__readFile(XML_PATH);
  }

  public void xmlvalidate(FacesContext context, UIComponent component, Object value) throws ValidatorException
  {    
    SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
    Schema schema = null;
    
    try
    {
      InputStream inputStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/components/codeEditorSampleFiles/adf-js-features.xsd");
      InputSource inputSource = new InputSource();
      try
      {
        Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        inputSource.setCharacterStream(reader);
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setNamespaceAware(true);
        documentFactory.setValidating(false);
        DocumentBuilder builder = documentFactory.newDocumentBuilder();
        Document document = builder.parse(inputSource);
        schema = factory.newSchema(new DOMSource(document));
      }
      catch(Exception ex)
      {
        throw ex;
      }
      finally
      {
        inputStream.close();
      }
    }
    catch(Exception e)
    {
      System.out.println("cannot read schema");
      return;
    }
    
    Validator validator = schema.newValidator();
    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                            "There is a parsing error", "There is a parsing error");
    ErrorHandler errorHandler = new DemoCodeEditorErrorHandler(message);
    validator.setErrorHandler(errorHandler);
    InputStream stream = null;
    SAXSource source = null;
    boolean hasError = false;
    try
    {
      stream = new ByteArrayInputStream(value.toString().getBytes());
      source = new SAXSource(new InputSource(stream));
      try
      {
        validator.validate(source);
      }
      catch (SAXException ex)
      {
        hasError = true;
      }
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
    finally
    {
      try
      {
        stream.close();
      }
      catch (Exception e)
      {
        System.out.println(e);
      }
    }

    if (hasError)
    {
      throw new ValidatorException(((DemoCodeEditorErrorHandler) errorHandler).getMessage());
    }
  }

  public void setStartLine(Integer startLine)
  {
    _startLine = startLine;
  }

  public Integer getStartLine()
  {
    return _startLine;
  }

  public void setStartColumn(Integer startColumn)
  {
    _startColumn = startColumn;
  }

  public Integer getStartColumn()
  {
    return _startColumn;
  }

  public void setEndLine(Integer endLine)
  {
    _endLine = endLine;
  }

  public Integer getEndLine()
  {
    return _endLine;
  }

  public void setEndColumn(Integer endColumn)
  {
    _endColumn = endColumn;
  }

  public Integer getEndColumn()
  {
    return _endColumn;
  }

  public void setMessage(String _message)
  {
    this._message = _message;
  }

  public String getMessage()
  {
    return _message;
  }
  
  public String getXmlValue()
  {
    return _xmlValue;
  }

  public void setXmlValue(String _xmlValue)
  {
    this._xmlValue = _xmlValue;
  }  
  
  public void addMarker(ActionEvent ae)
  {
    Marker marker = new Marker(_severity, _startLine, _startColumn, _endLine, _endColumn, _message);
    _markers.add(marker);
    submit(ae);
  }
  
  public void clearMarkers(ActionEvent ae)
  {
    _markers.clear();
  }

  public void submit(ActionEvent ae)
  {
    if (!_markers.isEmpty())
    {
      FacesMessage message = new FacesMessage(null, null);
      for (Marker marker : _markers)
      {
        message = MarkerUtils.addCodeEditorMarker(message, marker);
      }
      FacesContext.getCurrentInstance().addMessage("dmoTpl:idCodeEditor", message);
    }
  }

  public void reset(ActionEvent ae)
  {
    _xmlValue =  DemoCodeEditorBean.__readFile(XML_PATH);
    clearMarkers(ae);
  }


  public void setSeverity(FacesMessage.Severity severity)
  {
    _severity = severity;
  }

  public FacesMessage.Severity getSeverity()
  {
    return _severity;
  }
  
  public SelectItem[] getLevels()
  {
    return levelItems;
  }

  private static SelectItem[] levelItems = new SelectItem[4];

  static
  {
    levelItems[0] = new SelectItem(FacesMessage.SEVERITY_FATAL, "fatal");
    levelItems[1] = new SelectItem(FacesMessage.SEVERITY_ERROR, "error");
    levelItems[2] = new SelectItem(FacesMessage.SEVERITY_WARN, "warning");
    levelItems[3] = new SelectItem(FacesMessage.SEVERITY_INFO, "info");
  }

  private FacesMessage.Severity _severity = FacesMessage.SEVERITY_ERROR;
  private Integer _startLine;
  private Integer _startColumn;
  private Integer _endLine;
  private Integer _endColumn;
  private String _message = null;
  private String _xmlValue = null;
  private List<Marker> _markers = new ArrayList<Marker>();
  private static String XML_PATH = "/components/codeEditorSampleFiles/adf-js-features-default.xml";//"/WEB-INF/faces-config.xml";

  @SuppressWarnings("compatibility:-3788911651424289607")
  private static final long serialVersionUID = 1L;

  private static class DemoCodeEditorErrorHandler
    implements ErrorHandler
  {
    private FacesMessage _message = null;
    
    public DemoCodeEditorErrorHandler(FacesMessage message)
    {
      _message = message;
    }
    
    public void fatalError(SAXParseException e)
      throws SAXException
    {
      Marker marker = new Marker(FacesMessage.SEVERITY_FATAL, 
                                 e.getLineNumber(), e.getColumnNumber(), 
                                 null, null, e.getMessage() );
      _message = MarkerUtils.addCodeEditorMarker(_message, marker);
    }

    public void error(SAXParseException e)
      throws SAXException
    {
      Marker marker = new Marker(FacesMessage.SEVERITY_ERROR, 
                                 e.getLineNumber(), e.getColumnNumber(), 
                                 null, null, e.getMessage() );
      _message = MarkerUtils.addCodeEditorMarker(_message, marker);
    }

    public void warning(SAXParseException e)
      throws SAXException
    {
      Marker marker = new Marker(FacesMessage.SEVERITY_WARN, 
                                 e.getLineNumber(), e.getColumnNumber(), 
                                 null, null, e.getMessage() );
      _message = MarkerUtils.addCodeEditorMarker(_message, marker);
    }
    
    public FacesMessage getMessage()
    {
      return _message;
    }
  }
}
