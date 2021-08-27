package oracle.adfdemo.view.table.rich;

import java.io.Serializable;

import org.apache.myfaces.trinidad.component.UIXTree;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetTreeImpl;
import org.apache.myfaces.trinidad.model.TreeModel;

public class TreeExportData
  implements Serializable
{

  public TreeExportData()
  {
    super();
  }

  public TreeModel getTreeModel()
  {
    if (_model == null)
    {
      _model = TreeTableTestData.createTreeModel(TreeTableTestData.createTestData(10, true), false, false);
    }
    return _model;
  }

  public RowKeySet getTreeDisclosedRowKeys()
  {
    _treeDisclosedRowKeys = new RowKeySetTreeImpl();
    _treeDisclosedRowKeys.setCollectionModel((CollectionModel) getTreeModel());
    Object oldKey = _tree.getRowKey();
    try
    {
      _tree.setRowKey(null);
      _tree.setRowIndex(0);
      _treeDisclosedRowKeys.add(_tree.getRowKey());
    }
    finally
    {
      _tree.setRowKey(oldKey);
    }
    return _treeDisclosedRowKeys;
  }

  public void setTree(UIXTree tree)
  {
    _tree = tree;
  }

  public UIXTree getTree()
  {
    return _tree;
  }


  transient private TreeModel _model;
  private UIXTree _tree;
  private static final long serialVersionUID = 1L;
  private RowKeySet _treeDisclosedRowKeys = null;

}
