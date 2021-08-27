/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

public class DemoTimestamp
{
  /**
   * Retrieves the current server timestamp.
   * @return the current server timestamp
   */
  public String getTimestamp()
  {
    return ""+System.currentTimeMillis();
  }

  public boolean getRandomFlag()
  {
    return Math.random() < 0.5;
  }
}
