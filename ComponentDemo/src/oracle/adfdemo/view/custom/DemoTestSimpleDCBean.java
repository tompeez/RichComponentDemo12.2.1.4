/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.custom;

import javax.faces.event.ActionEvent;

public class DemoTestSimpleDCBean
{
  public DemoTestSimpleDCBean()
  {
  }

  public void actionFired(ActionEvent actionEvent)
  {
    System.out.println("ACTION EVENT: " + actionEvent);
  }
}
