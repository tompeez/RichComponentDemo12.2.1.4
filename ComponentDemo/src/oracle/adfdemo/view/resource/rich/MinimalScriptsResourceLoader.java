/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.resource.rich;

import java.net.URLConnection;

import org.apache.myfaces.trinidad.resource.AggregatingResourceLoader;
import org.apache.myfaces.trinidad.resource.ClassLoaderResourceLoader;

/**
 * A ResourceLoader which serves up a "minimal" version of the rich
 * client framework JS library, sufficient to allow RichObject, UIComponent,
 * UICommand and RichCommandButton instantiation.
 */
public class MinimalScriptsResourceLoader extends AggregatingResourceLoader
{
  public MinimalScriptsResourceLoader(
    String path)
  {
    super(path, _LIBRARIES, new ClassLoaderResourceLoader());
    // force a newline between libraries to avoid the syntax error when
    // the last line of one library contains a line comment "//"
    // and the first line of the next library starts with a
    // block comment "/*"
    setSeparator(_NEWLINE_SEPARATOR);
  }

  protected String getContentType(
    URLConnection conn)
  {
    return "text/javascript";
  }

  static private final String[] _LIBRARIES =
  {
    "oracle/adfinternal/view/rich/js/bootstrap/bootstrap.js",
    "oracle/adf/view/rich/js/assert/Assert.js",
    "oracle/adf/view/rich/js/base/RichObject.js",
    "oracle/adf/view/rich/js/base/Collections.js",
    "oracle/adf/view/js/component/UIComponent.js",
    "oracle/adf/view/js/component/UIComponents.js",  
    "oracle/adf/view/js/component/UICommandBase.js",
    "oracle/adf/view/js/component/UICommand.js",
    "oracle/adf/view/rich/js/component/rich/RichCommandButtonBase.js",
    "oracle/adf/view/rich/js/component/rich/RichCommandButton.js"  
  };

  static private final String _NEWLINE_SEPARATOR = "\n";
}
