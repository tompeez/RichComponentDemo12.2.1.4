/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

package oracle.adfdemo.view.resource.rich;

import org.apache.myfaces.trinidad.resource.RegexResourceLoader;

/**
 * A resource loader implementation which loads resources
 * for the rich demos.
 *
 * @author Andy Schwartz
 */
public class DemoResourceLoader extends RegexResourceLoader
{
  public DemoResourceLoader()
  {
    register("(/.*minimal.*\\.js)", 
      new MinimalScriptsResourceLoader("/adfdemo/minimal.js"));
  }

}
