/** Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.offline;

/**
 * Bean used to store state for the Offline outputMode demo
 */
public class OfflineBean
{
  public synchronized void setToggleMode(String toggleMode)
  {
    _toggleMode = toggleMode;
  }
  
  public synchronized String getToggleMode()
  {
    return _toggleMode;
  }
  
  private String _toggleMode = "ButtonBar";
}