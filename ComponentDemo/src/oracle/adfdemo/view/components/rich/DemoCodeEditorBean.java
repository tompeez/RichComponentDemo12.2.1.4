/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;

import java.io.StringWriter;
import java.io.Writer;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.input.RichCodeEditor;


public class DemoCodeEditorBean implements Serializable
{

  public DemoCodeEditorBean()
  {
    _javascriptValue =  __readFile(JS_PATH);
    _xmlValue =  __readFile(XML_PATH);
    _groovyValue =  __readFile(GROOVY_PATH);
  }

  static String __readFile(String filePath)
  {
    try
    {
      InputStream inputStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(filePath);
      Writer writer = new StringWriter();
      char[] buffer = new char[1024];
      try
      {
        Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        int n = 0;
        while ((n = reader.read(buffer)) != -1)
        {
          writer.write(buffer, 0, n);
        }
      }
      catch(Exception ex)
      {
        throw ex;
      }
      finally
      {
        inputStream.close();
      }
      return writer.toString();
    }
    catch(Exception e)
    {
      System.out.println(e);
      return "couldn't open file with path " + filePath;
    }
  }


  public void resetCodeEditorValue(ActionEvent ae)
  {
    if (RichCodeEditor.LANGUAGE_XML.equals(_language))
      _xmlValue =  __readFile(XML_PATH);
    else if (RichCodeEditor.LANGUAGE_JAVASCRIPT.equals(_language))
      _javascriptValue =  __readFile(JS_PATH);
    else
      _groovyValue = __readFile(GROOVY_PATH);
  }

  public String getCodeEditorValue()
  {
    if (RichCodeEditor.LANGUAGE_XML.equals(_language))
      return _xmlValue;
    else if (RichCodeEditor.LANGUAGE_JAVASCRIPT.equals(_language))
      return _javascriptValue;
    else
      return _groovyValue;
  }

  public void setCodeEditorValue(String avalue)
  {
    if (RichCodeEditor.LANGUAGE_XML.equals(_language))
      _xmlValue = avalue;
    else if (RichCodeEditor.LANGUAGE_JAVASCRIPT.equals(_language))
      _javascriptValue = avalue;
    else
    _groovyValue = avalue;
  }

  public String getCodeEditorValue2()
  {
    return _codeEditorValue2;
  }

  public void setCodeEditorValue2(String avalue)
  {
    _codeEditorValue2 = avalue;
  }


  public void setLanguage(String language)
  {
    _language = language;
  }

  public String getLanguage()
  {
    return _language;
  }

  public SelectItem[] getLanguages()
  {
    return languageItems;
  }

  private static String[] _sLanguages = new String[]{
          RichCodeEditor.LANGUAGE_JAVASCRIPT,
          RichCodeEditor.LANGUAGE_XML,
          RichCodeEditor.LANGUAGE_GROOVY};

  private static SelectItem[] languageItems = new SelectItem[_sLanguages.length];

  static
  {
    int i = 0;

    for (i = 0; i < _sLanguages.length; i++ )
    {
      String language = _sLanguages[i];
      languageItems[i] = new SelectItem(language);
    }
  }

  private String _language = RichCodeEditor.LANGUAGE_JAVASCRIPT;

  private String _javascriptValue = null;
  private String _xmlValue = null;
  private String _groovyValue = null;

  private String _codeEditorValue2 = null;
  private static String JS_PATH = "/components/codeEditorSampleFiles/TestFileUtils.js";
  private static String XML_PATH = "/components/codeEditorSampleFiles/adf-js-features-default.xml";
  private static String GROOVY_PATH = "/components/codeEditorSampleFiles/TestGroovyExample.groovy";

  @SuppressWarnings("compatibility:-3788911651424289607")
  private static final long serialVersionUID = 1L;

}
