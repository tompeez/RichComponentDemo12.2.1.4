/** Copyright (c) 2008, 2018, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import java.util.ArrayList;

import java.util.List;

import oracle.adfdemo.view.table.rich.TableTestData.FileData;

public class WidgetDataSet
  extends TableTestData
{
  public WidgetDataSet()
  {
    super(5, true);
    
    List listMouse = new ArrayList();
    boolean add = listMouse.add("Mouse");
    List listSpeaker = new ArrayList();
    add = listSpeaker.add("Speaker");
    
    FileData row0 = this.get(0);
    FileData row1 = this.get(1);
    FileData row2 = this.get(2);
    FileData row3 = this.get(3);
    FileData row4 = this.get(4);
    
    row0.setName("aardvark");
    row0.setSelectManyValue1(listMouse);
    row1.setName("baboon");
    row1.setIsDirectory(true);
    row2.setName("cassowary");
    row3.setName("dolphin");
    row3.setSelectManyValue1(listSpeaker);
    row4.setName("elephant");
    row4.setIsDirectory(true);
  }
}
