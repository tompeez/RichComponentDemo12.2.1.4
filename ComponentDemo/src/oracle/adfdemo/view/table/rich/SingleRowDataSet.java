/** Copyright (c) 2008, 2018, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import java.util.ArrayList;

import java.util.List;

import oracle.adfdemo.view.table.rich.TableTestData.FileData;

public class SingleRowDataSet
  extends TableTestData
{
  public SingleRowDataSet()
  {
    super(1, true);
    
    List listMouse = new ArrayList();
    boolean add = listMouse.add("Mouse");
    List listSpeaker = new ArrayList();
    add = listSpeaker.add("Speaker");
    
    FileData row0 = this.get(0);
    
    row0.setName("banana");
    row0.setSelectManyValue1(listMouse);
  }
}
