/** Copyright (c) 2009, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetTreeImpl;

public class DiscloseTreePanelAccordion
{
  public DiscloseTreePanelAccordion()
  {
  }

  private RowKeySet _keySet = new RowKeySetTreeImpl(true);

  public void setKeySet(RowKeySet _keySet)
  {
    this._keySet = _keySet;
  }

  public RowKeySet getKeySet()
  {
    return _keySet;
  }
}
