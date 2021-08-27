/** Copyright (c) 2008, 2018, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.SortableModel;

public class UnknownCountDataBig
{
  public CollectionModel getCollectionModel()
  {
    TableTestData data = new TableTestData(200, false);
    
    return new SortableModel(data)
    {
      public int getRowCount(){return -1;}
    };
  }
}