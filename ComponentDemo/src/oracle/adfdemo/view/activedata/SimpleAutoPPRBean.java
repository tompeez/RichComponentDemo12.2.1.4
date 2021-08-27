/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.activedata;

public class SimpleAutoPPRBean extends MockActiveDataModel
{
  public SimpleAutoPPRBean()
  {
    super("A value that is updated on the server, every second: ", 1000, false, false);
  }
}
