/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.table.rich;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.layout.RichToolbar;

import org.apache.myfaces.trinidad.context.RequestContext;

public class TableTabScroll
{
  public TableTabScroll()
  {
    Method [] methods = TableData.class.getDeclaredMethods();
    try
    {
      _data = new ArrayList<TableData>(_DATA_SIZE);
      for(int i = 1; i<=_DATA_SIZE; i++)
      {
        TableData tableData = new TableData();
        _data.add(i-1, tableData);
        for(int j = 0, col = 1; j< methods.length; j++)
        {
          Method method = methods[j];
          if(method.getName().startsWith("setCol"))
          {
            method.invoke(tableData, new Object[]{"row"+i+"col"+(col++)});
          }
        }
      }
    }
    catch(IllegalAccessException iae)
    {
      
    }
    catch(InvocationTargetException ite)
    {
      
    }
  }

  public ArrayList<TableData> getData()
  {
    return _data;
  }

  public int getCurrentGroup()
  {
    return _currentGroup;
  }

  public boolean isClickToEdit()
  {
    return _clickToEdit;
  }

  public void setClickToEdit(boolean clickToEdit)
  {
    _clickToEdit = clickToEdit;
    _table.setEditingMode(_clickToEdit?"clickToEdit":"editAll");
    RequestContext.getCurrentInstance().addPartialTarget(_table);
  }

  public void showAllGroups(ActionEvent e)
  {
    _currentGroup = 0;
    RequestContext.getCurrentInstance().addPartialTarget(_table);
    RequestContext.getCurrentInstance().addPartialTarget(_linksToolbar);
  }
  
  public void showGroup1(ActionEvent e)
  {
    _currentGroup = 1;
    RequestContext.getCurrentInstance().addPartialTarget(_table);
    RequestContext.getCurrentInstance().addPartialTarget(_linksToolbar);
  }

  public void showGroup2(ActionEvent e)
  {
    _currentGroup = 2;
    RequestContext.getCurrentInstance().addPartialTarget(_table);
    RequestContext.getCurrentInstance().addPartialTarget(_linksToolbar);
  }

  public void showGroup3(ActionEvent e)
  {
    _currentGroup = 3;
    RequestContext.getCurrentInstance().addPartialTarget(_table);
    RequestContext.getCurrentInstance().addPartialTarget(_linksToolbar);
  }

  public void showGroup4(ActionEvent e)
  {
    _currentGroup = 4;
    RequestContext.getCurrentInstance().addPartialTarget(_table);
    RequestContext.getCurrentInstance().addPartialTarget(_linksToolbar);
  }

  public void showGroup5(ActionEvent e)
  {
    _currentGroup = 5;
    RequestContext.getCurrentInstance().addPartialTarget(_table);
    RequestContext.getCurrentInstance().addPartialTarget(_linksToolbar);
  }

  public void showGroup6(ActionEvent e)
  {
    _currentGroup = 6;
    RequestContext.getCurrentInstance().addPartialTarget(_table);
    RequestContext.getCurrentInstance().addPartialTarget(_linksToolbar);
  }

  public void showGroup7(ActionEvent e)
  {
    _currentGroup = 7;
    RequestContext.getCurrentInstance().addPartialTarget(_table);
    RequestContext.getCurrentInstance().addPartialTarget(_linksToolbar);
  }

  public void setTable(RichTable table)
  {
    this._table = table;
  }

  public RichTable getTable()
  {
    return _table;
  }

  public void setLinksToolbar(RichToolbar toolbar)
  {
    this._linksToolbar = toolbar;
  }

  public RichToolbar getLinksToolbar()
  {
    return _linksToolbar;
  }

  public static class TableData
  {
    private String _col1;
    private String _col2;
    private String _col3;
    private String _col4;
    private String _col5;
    private String _col6;
    private String _col7;
    private String _col8;
    private String _col9;
    private String _col10;
    private String _col11;
    private String _col12;
    private String _col13;
    private String _col14;
    private String _col15;
    private String _col16;
    private String _col17;
    private String _col18;
    private String _col19;
    private String _col20;
    private String _col21;
    private String _col22;
    private String _col23;
    private String _col24;
    private String _col25;
    private String _col26;
    private String _col27;
    private String _col28;
    private String _col29;
    private String _col30;
    private String _col31;
    private String _col32;
    private String _col33;
    private String _col34;
    private String _col35;
    private String _col36;
    private String _col37;
    private String _col38;
    private String _col39;
    private String _col40;
    private String _col41;
    private String _col42;
    private String _col43;
    private String _col44;
    private String _col45;
    private String _col46;
    private String _col47;
    private String _col48;
    private String _col49;
    private String _col50;
    private String _col51;
    private String _col52;

    public void setCol1(String col1)
    {
      this._col1 = col1;
    }

    public String getCol1()
    {
      return _col1;
    }

    public void setCol2(String col2)
    {
      this._col2 = col2;
    }

    public String getCol2()
    {
      return _col2;
    }

    public void setCol3(String col3)
    {
      this._col3 = col3;
    }

    public String getCol3()
    {
      return _col3;
    }

    public void setCol4(String col4)
    {
      this._col4 = col4;
    }

    public String getCol4()
    {
      return _col4;
    }

    public void setCol5(String col5)
    {
      this._col5 = col5;
    }

    public String getCol5()
    {
      return _col5;
    }

    public void setCol6(String col6)
    {
      this._col6 = col6;
    }

    public String getCol6()
    {
      return _col6;
    }

    public void setCol7(String col7)
    {
      this._col7 = col7;
    }

    public String getCol7()
    {
      return _col7;
    }

    public void setCol8(String col8)
    {
      this._col8 = col8;
    }

    public String getCol8()
    {
      return _col8;
    }

    public void setCol9(String col9)
    {
      this._col9 = col9;
    }

    public String getCol9()
    {
      return _col9;
    }

    public void setCol10(String col10)
    {
      this._col10 = col10;
    }

    public String getCol10()
    {
      return _col10;
    }

    public void setCol11(String col11)
    {
      this._col11 = col11;
    }

    public String getCol11()
    {
      return _col11;
    }

    public void setCol12(String col12)
    {
      this._col12 = col12;
    }

    public String getCol12()
    {
      return _col12;
    }

    public void setCol13(String col13)
    {
      this._col13 = col13;
    }

    public String getCol13()
    {
      return _col13;
    }

    public void setCol14(String col14)
    {
      this._col14 = col14;
    }

    public String getCol14()
    {
      return _col14;
    }

    public void setCol15(String col15)
    {
      this._col15 = col15;
    }

    public String getCol15()
    {
      return _col15;
    }

    public void setCol16(String col16)
    {
      this._col16 = col16;
    }

    public String getCol16()
    {
      return _col16;
    }

    public void setCol17(String col17)
    {
      this._col17 = col17;
    }

    public String getCol17()
    {
      return _col17;
    }

    public void setCol18(String col18)
    {
      this._col18 = col18;
    }

    public String getCol18()
    {
      return _col18;
    }

    public void setCol19(String col19)
    {
      this._col19 = col19;
    }

    public String getCol19()
    {
      return _col19;
    }

    public void setCol20(String col20)
    {
      this._col20 = col20;
    }

    public String getCol20()
    {
      return _col20;
    }

    public void setCol21(String col21)
    {
      this._col21 = col21;
    }

    public String getCol21()
    {
      return _col21;
    }

    public void setCol22(String col22)
    {
      this._col22 = col22;
    }

    public String getCol22()
    {
      return _col22;
    }

    public void setCol23(String col23)
    {
      this._col23 = col23;
    }

    public String getCol23()
    {
      return _col23;
    }

    public void setCol24(String _col24)
    {
      this._col24 = _col24;
    }

    public String getCol24()
    {
      return _col24;
    }

    public void setCol25(String col25)
    {
      this._col25 = col25;
    }

    public String getCol25()
    {
      return _col25;
    }

    public void setCol26(String col26)
    {
      this._col26 = col26;
    }

    public String getCol26()
    {
      return _col26;
    }

    public void setCol27(String col27)
    {
      this._col27 = col27;
    }

    public String getCol27()
    {
      return _col27;
    }

    public void setCol28(String col28)
    {
      this._col28 = col28;
    }

    public String getCol28()
    {
      return _col28;
    }

    public void setCol29(String col29)
    {
      this._col29 = col29;
    }

    public String getCol29()
    {
      return _col29;
    }

    public void setCol30(String col30)
    {
      this._col30 = col30;
    }

    public String getCol30()
    {
      return _col30;
    }

    public void setCol31(String _col31)
    {
      this._col31 = _col31;
    }

    public String getCol31()
    {
      return _col31;
    }

    public void setCol32(String col32)
    {
      this._col32 = col32;
    }

    public String getCol32()
    {
      return _col32;
    }

    public void setCol33(String col33)
    {
      this._col33 = col33;
    }

    public String getCol33()
    {
      return _col33;
    }

    public void setCol34(String col34)
    {
      this._col34 = col34;
    }

    public String getCol34()
    {
      return _col34;
    }

    public void setCol35(String col35)
    {
      this._col35 = col35;
    }

    public String getCol35()
    {
      return _col35;
    }

    public void setCol36(String col36)
    {
      this._col36 = col36;
    }

    public String getCol36()
    {
      return _col36;
    }

    public void setCol37(String col37)
    {
      this._col37 = col37;
    }

    public String getCol37()
    {
      return _col37;
    }

    public void setCol38(String col38)
    {
      this._col38 = col38;
    }

    public String getCol38()
    {
      return _col38;
    }

    public void setCol39(String col39)
    {
      this._col39 = col39;
    }

    public String getCol39()
    {
      return _col39;
    }

    public void setCol40(String col40)
    {
      this._col40 = col40;
    }

    public String getCol40()
    {
      return _col40;
    }

    public void setCol41(String col41)
    {
      this._col41 = col41;
    }

    public String getCol41()
    {
      return _col41;
    }

    public void setCol42(String col42)
    {
      this._col42 = col42;
    }

    public String getCol42()
    {
      return _col42;
    }

    public void setCol43(String col43)
    {
      this._col43 = col43;
    }

    public String getCol43()
    {
      return _col43;
    }

    public void setCol44(String col44)
    {
      this._col44 = col44;
    }

    public String getCol44()
    {
      return _col44;
    }

    public void setCol45(String col45)
    {
      this._col45 = col45;
    }

    public String getCol45()
    {
      return _col45;
    }

    public void setCol46(String col46)
    {
      this._col46 = col46;
    }

    public String getCol46()
    {
      return _col46;
    }

    public void setCol47(String col47)
    {
      this._col47 = col47;
    }

    public String getCol47()
    {
      return _col47;
    }

    public void setCol48(String col48)
    {
      this._col48 = col48;
    }

    public String getCol48()
    {
      return _col48;
    }

    public void setCol49(String col49)
    {
      this._col49 = col49;
    }

    public String getCol49()
    {
      return _col49;
    }

    public void setCol50(String col50)
    {
      this._col50 = col50;
    }

    public String getCol50()
    {
      return _col50;
    }

    public void setCol51(String col51)
    {
      this._col51 = col51;
    }

    public String getCol51()
    {
      return _col51;
    }

    public void setCol52(String col52)
    {
      this._col52 = col52;
    }

    public String getCol52()
    {
      return _col52;
    }
  }
  
  private ArrayList<TableData> _data;
  private static final int _DATA_SIZE = 500;
  private int _currentGroup = 0;
  private boolean _clickToEdit = true;
  private RichTable _table;
  private RichToolbar _linksToolbar; 
}
