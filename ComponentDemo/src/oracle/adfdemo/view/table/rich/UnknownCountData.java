/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.SortableModel;

public class UnknownCountData
{
  public CollectionModel getCollectionModel()
  {
    TableTestData data = new TableTestData(15, false);
    
    return new SortableModel(data)
    {
      public int getRowCount(){return -1;}
    };
  }
  
}
