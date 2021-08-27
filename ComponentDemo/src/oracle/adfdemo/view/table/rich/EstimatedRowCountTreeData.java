/* Copyright (c) 2009, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.table.rich;

import org.apache.myfaces.trinidad.model.TreeModel;

public class EstimatedRowCountTreeData
{
  public EstimatedRowCountTreeData()
  {
    super();
  }
  
  public TreeModel getTreeModel()
  {
    if(null == _model)
    {
      TreeTableTestData data = new TreeTableTestData(false, true);
      _model = data.getTreeModel();
    }
    return _model;
  }
      
  private TreeModel _model;
  
}
