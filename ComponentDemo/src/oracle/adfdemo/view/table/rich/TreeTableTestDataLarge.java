/* Copyright (c) 2009, 2018, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.table.rich;

import java.util.List;

import org.apache.myfaces.trinidad.model.TreeModel;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.SortCriterion;
import org.apache.myfaces.trinidad.model.SortableModel;

public class TreeTableTestDataLarge extends TreeTableTestData
{
  public TreeTableTestDataLarge()
  {
    super();
  }
  

   public TreeModel getTreeModel()
  {
    if(_model != null)
      return _model;
    _model = createTreeModel(createTestData(_LARGE_FIRST_LEVEL_SIZE, false), _unknownRowCount, _estimatedRowCount);
    return _model;
  }
  
  public static TreeModel createTreeModel(List<TreeNode> root,
                                   final boolean unknownRowCount,
                                   final boolean estimatedRowCount)
  {
    return new ChildPropertyTreeModel(root, _CONTAINER_KEY)
    {
      @Override
      public boolean isContainer()
      {
        TreeNode currNode = (TreeNode) getRowData();
        return null != currNode.getChildren() &&
          currNode.getChildren().size() > 0;
      }

      @Override
      public int getRowCount()
      {
        if (unknownRowCount)
        {
          return -1;
        }
        else if (estimatedRowCount)
        {
          return _LARGE_FIRST_LEVEL_SIZE * 2;
        }
        else
        {
          return super.getRowCount();
        }
      }

      @Override
      protected CollectionModel createChildModel(Object childData)
      {

        CollectionModel childModel = new SortableModel(childData)
        {
          @Override
          public int getRowCount()
          {
            if (unknownRowCount)
            {
              return -1;
            }
            else if (estimatedRowCount)
            {
              return _LARGE_FIRST_LEVEL_SIZE * 2;
            }
            else
            {
              return super.getRowCount();
            }
          }
        };
        childModel.setRowIndex(-1);
        return childModel;
      }

      @Override
      public void setSortCriteria(List<SortCriterion> criteria)
      {
        super.setSortCriteria(criteria);
        if (null == getRowKey())
        {
          _rootCriteria = criteria;
        }
      }
      private List<SortCriterion> _rootCriteria;
    };
  }

  private static int _LARGE_FIRST_LEVEL_SIZE = 70;
  private TreeModel _model;
}
