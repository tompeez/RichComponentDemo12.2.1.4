/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import java.util.ArrayList;

public class TableTotalBean  extends ArrayList<TableTotalBean.TableRow>
{
  public TableTotalBean()
  {
    _init(_ROW_COUNT, true);
  }

  public TableTotalBean(int rowCount, boolean includeTotalRow)
  {
    _init(rowCount, includeTotalRow);
  }
  
  private void _init(int size, boolean includeTotalRow)
  {
    for(int row = 0; row < size; row++)    
    { 
      if(0 == ((row+1) % _SUB_TOTAL_ROWS))
      {
        add(new TableRow(_SUB_TOTAL_NAME, 
                  "", "", _getCostsSubtotal(row-1), _getSalesSubtotal(row-1), TableRow.RowKind.subtotal));        
      }
      else
        add(new TableRow("name"+row, Integer.toString(row), 
                  Integer.toString(row*10+1), Math.random()*50000.0, Math.random()*100000.0, TableRow.RowKind.data));
    }
    
    if(includeTotalRow)
      add(new TableRow(_TOTAL_NAME,   
              "", "", getTotalCost(), getTotalSales(), TableRow.RowKind.total));        
  }
  
  private double _getCostsSubtotal(int row)
  {
    double total = 0.;
    for(int r = row; r >= 0; r--)
    {
      TableRow rowData = get(r) ;
      if(!rowData.isDataRow())
        break;
      total += rowData.getCost();
    }
    return total;
  }
  

  private double _getSalesSubtotal(int row)
  {
    double total = 0.;
    for(int r = row; r >= 0; r--)
    {
      TableRow rowData = get(r) ;
      if(!rowData.isDataRow())
        break;
      total += rowData.getSale();
    }
    return total;
  }
  
  public double getTotalCost()
  {
    double total = 0.;
    for(int r = 0; r < size(); r++)
    {
      TableRow rowData = get(r) ;
      if(rowData.isDataRow())
        total += rowData.getCost();
    }
    return total;
  }
  

  public double getTotalSales()
  {
    double total = 0.;
    for(int r = 0; r < size(); r++)
    {
      TableRow rowData = get(r) ;
      if(rowData.isDataRow())
        total += rowData.getSale();
    }
    return total;
  }
  
  
  public static class TableRow
  {
    public enum RowKind {data, total, subtotal};

    public TableRow(String name, String id1, String id2, 
                    double cost, double sale, RowKind rowKind)
    {
      this._name = name;
      this._id1 = id1;
      this._id2 = id2;
      this._cost = cost;
      this._sale = sale;
      this._rowKind = rowKind;
    }

    public void setName(String name)
    {
      this._name = name;
    }

    public String getName()
    {
      return _name;
    }

    public void setId1(String id1)
    {
      this._id1 = id1;
    }

    public String getId1()
    {
      return _id1;
    }

    public void setId2(String id2)
    {
      this._id2 = id2;
    }

    public String getId2()
    {
      return _id2;
    }

    public void setCost(double cost)
    {
      this._cost = cost;
    }

    public double getCost()
    {
      return _cost;
    }

    public void setSale(double sale)
    {
      this._sale = sale;
    }

    public double getSale()
    {
      return _sale;
    }
    
    public void setRowKind(RowKind totalRow)
    {
      this._rowKind = totalRow;
    }
    
    public RowKind getRowKind()
    {
      return this._rowKind;
    }

    public boolean isTotalRow()
    {
      return this._rowKind.equals(RowKind.total);
    }
    
    public boolean isSubTotalRow()
    {
      return this._rowKind.equals(RowKind.subtotal);
    }
    
    public boolean isDataRow()
    {
      return this._rowKind.equals(RowKind.data);
    }
    
    public String getAlign()
    {
      return !isDataRow() ? "right" : "start";
    }
    
    private String _name;
    private String _id1;
    private String _id2;
    private double _cost;
    private double _sale;    
    private RowKind _rowKind;
  }
  
  private static final int _ROW_COUNT = 12;
  private static final int _SUB_TOTAL_ROWS = 5;
  private static final String _SUB_TOTAL_NAME = "Subtotal";
  private static final String _TOTAL_NAME = "Total";
  
}
