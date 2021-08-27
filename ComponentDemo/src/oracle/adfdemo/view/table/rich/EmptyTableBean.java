/* Copyright (c) 2007, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.table.rich;

import java.util.ArrayList;
import java.util.List;

public class EmptyTableBean
{
  public EmptyTableBean()
  {
  }

  public List getData()
  {
    return _data;
  }

  public String getFormattedEmptyText()
  {
    return "<html>&nbsp;Formatted empty text. <b>Right mouse click for context menu</b>.</html>";
  }
  
  public String getEmptyText()
  {
    return "No data to display";
  }  

  List _data = new ArrayList(0);
}
