/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import java.util.ArrayList;

public class TableFooterTotalBean
  
{
  public TableFooterTotalBean()
  {
    _data = new TableTotalBean(_ROW_COUNT, false);    
  }
  
  public ArrayList<TableTotalBean.TableRow> getData()
  {
    return _data;
  }

  public double getTotalCost()
  {
    return _data.getTotalCost();
  }
  

  public double getTotalSales()
  {
    return _data.getTotalSales();
  }

  private TableTotalBean _data;
  private static final int _ROW_COUNT = 50;
  
}
