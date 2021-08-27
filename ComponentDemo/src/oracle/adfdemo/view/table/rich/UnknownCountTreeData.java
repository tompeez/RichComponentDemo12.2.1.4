/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.table.rich;

import org.apache.myfaces.trinidad.model.TreeModel;

public class UnknownCountTreeData
{
  public UnknownCountTreeData()
  {
  }

  public TreeModel getTreeModel()
  {
    if(null == _model)
    {
      TreeTableTestData data = new TreeTableTestData(true, false);
      _model = data.getTreeModel();
    }
    return _model;
  }
      
  private TreeModel _model;
}
