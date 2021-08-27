/* Copyright (c) 2009, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.table.rich;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.model.RowKeyPropertyModel;
import org.apache.myfaces.trinidad.model.RowKeyPropertyTreeModel;

import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.component.UIXTree;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;


public class TreeTableInsertUpdateDelete
{
  public TreeTableInsertUpdateDelete()
  {
    super();
  }

  public void insertBefore(ActionEvent actionEvent)
  {
    RowKeySet selection = _component.getSelectedRowKeys();
    if (selection.getSize() > 0)
    {
      Object rowKey = selection.iterator().next();
      Object key = _component.getRowKey();

      try
      {
        _model.setRowKey(rowKey);
        int childIndex = _model.getRowIndex();
        if (childIndex >= 0)
        {
          TreeNode newRow = _createNode(null);
          List<TreeNode> childList = _getChildCollection(rowKey);
          childList.add(childIndex, newRow);
          RequestContext.getCurrentInstance().addPartialTarget(_component);
        }
      }
      finally
      {
        _component.setRowKey(key);
      }
    }
  }

  public void insertAfter(ActionEvent actionEvent)
  {
    RowKeySet selection = _component.getSelectedRowKeys();
    if (selection.getSize() > 0)
    {
      Object rowKey = selection.iterator().next();
      Object key = _component.getRowKey();

      try
      {
        _model.setRowKey(rowKey);
        int childIndex = _model.getRowIndex();
        if (childIndex >= 0)
        {
          TreeNode newRow = _createNode(null);
          List<TreeNode> childList = _getChildCollection(rowKey);
          if (childIndex == _model.getRowCount() - 1)
            childList.add(newRow);
          else
            childList.add(childIndex + 1, newRow);
          RequestContext.getCurrentInstance().addPartialTarget(_component);
        }
      }
      finally
      {
        _component.setRowKey(key);
      }
    }
  }

  public void insertEnd(ActionEvent actionEvent)
  {
    Object key = _component.getRowKey();

    try
    {
      int lastRowIndex = _model.getRowCount();
      _model.setRowIndex(lastRowIndex);
      TreeNode newRow = _createNode(null);
      List<TreeNode> childList = _getChildCollection(_model.getRowKey());
      childList.add(newRow);
      RequestContext.getCurrentInstance().addPartialTarget(_component);
    }
    finally
    {
      _component.setRowKey(key);
    }
  }

  public void insertStart(ActionEvent actionEvent)
  {
    RowKeySet selection = _component.getSelectedRowKeys();
    if (selection.getSize() > 0)
    {
      Object rowKey = selection.iterator().next();
      Object key = _component.getRowKey();

      try
      {
        _model.setRowKey(rowKey);
        int childIndex = _model.getRowIndex();
        if (childIndex >= 0)
        {
          TreeNode newRow = _createNode(null);
          List<TreeNode> childList = _getChildCollection(rowKey);
          childList.add(0, newRow);
          RequestContext.getCurrentInstance().addPartialTarget(_component);
        }
      }
      finally
      {
        _component.setRowKey(key);
      }
    }
  }

  public void update(ActionEvent actionEvent)
  {
    RowKeySet selection = _component.getSelectedRowKeys();
    if (selection.getSize() > 0)
    {
      Object rowKey = selection.iterator().next();
      Object key = _component.getRowKey();
      try
      {
        _model.setRowKey(rowKey);
        TreeNode row = (TreeNode) _model.getRowData();
        row.setCost(Math.round(Math.random() * 100000.0));
        row.setSale(Math.round(Math.random() * 1000000.0));
        RequestContext.getCurrentInstance().addPartialTarget(_component);
      }
      finally
      {
        _component.setRowKey(key);
      }
    }
  }

  public void delete(ActionEvent actionEvent)
  {
    RowKeySet selection = _component.getSelectedRowKeys();
    if (selection.getSize() > 0)
    {
      Object rowKey = selection.iterator().next();
      Object key = _component.getRowKey();

      try
      {
        _model.setRowKey(rowKey);
        int childIndex = _model.getRowIndex();
        if (childIndex >= 0)
        {
          List<TreeNode> childList = _getChildCollection(rowKey);
          childList.remove(childIndex);
          _component.getSelectedRowKeys().remove(rowKey);
          _component.getDisclosedRowKeys().remove(rowKey);
          RequestContext.getCurrentInstance().addPartialTarget(_component);
        }
      }
      finally
      {
        _component.setRowKey(key);
      }

    }
  }

  public void insertExpanded(ActionEvent actionEvent)
  {
    RowKeySet selection = _component.getSelectedRowKeys();
    if (selection.getSize() > 0)
    {    
      Object rowKey = selection.iterator().next();
      Object key = _component.getRowKey();

      try
      {
        _model.setRowKey(rowKey);
        int childIndex = _model.getRowIndex();        
        if (childIndex >= 0)
        {        
          List<TreeNode> children = new ArrayList<TreeNode> () 
          {
            {
              add(_createNode(null));
              add(_createNode(null));
              add(_createNode(null));
            }
          };
          TreeNode newNode = _createNode(children);
          List<TreeNode> childList = _getChildCollection(rowKey);
          childList.add(childIndex, newNode);
          
          List<String> path = new ArrayList<String>((List<String>)rowKey);
          path.set(path.size()-1, newNode.getKey());
          _component.getDisclosedRowKeys().add(path);
          RequestContext.getCurrentInstance().addPartialTarget(_component);
        }
      }
      finally
      {
        _component.setRowKey(key);
      }
    }
  }

  public CollectionModel getModel()
  {
    return _createTreeModel();
  }

  public void setComponent(UIXTree component)
  {
    this._component = component;
  }

  public UIXTree getComponent()
  {
    return _component;
  }


  private TreeNode _createNode(List<TreeNode> children)
  {
    int id = (int) Math.floor(Math.random() * 100);
    return new TreeNode("node" + id, id,
                        Math.random() * 100000., Math.random() * 1000000., children);
  }

  private TreeModel _createTreeModel()
  {
    if (_model == null)
    {
      List<TreeNode> nodes = createTestData(_DEFAULT_FIRST_LEVEL_SIZE);
      _model = new RowKeyPropertyTreeModel(nodes, _CONTAINER_KEY, _ROW_KEY);
    }
    return _model;
  }
  
  
  public List<TreeNode> createTestData(int firstLevelSize)
  {
    List<TreeNode> root = new ArrayList<TreeNode>();
    for(int i = 0; i < firstLevelSize; i++)
    {
      List<TreeNode> level1 = new ArrayList<TreeNode>();
      for(int j = 0; j < i; j++)
      {
        List<TreeNode> level2 = new ArrayList<TreeNode>();
        for(int k=0; k<j; k++)
        {
          TreeNode z = _createNode(null);
          level2.add(z);
        }
        TreeNode c = _createNode(level2);
        level1.add(c);
      }
      TreeNode n = _createNode(level1);
      root.add(n);
    }
    return root;
  }
  

  private List<TreeNode> _getChildCollection(Object rowKey)
  {
    Object key = _component.getRowKey();
    List<TreeNode> childCollection;
    TreeNode parentNode;
    try
    {
      Object parentKey = _model.getContainerRowKey(rowKey); 
      if (parentKey == null)
      {
        childCollection = (List<TreeNode>)((RowKeyPropertyModel)_model.getWrappedData()).getWrappedData();
      } 
      else
      {
        _model.setRowKey(parentKey);
        parentNode = (TreeNode)_model.getRowData();
        childCollection = parentNode.getChildren();
      }
    }
    finally
    {
      _component.setRowKey(key);
    }
    return childCollection;
  }

  private UIXTree _component;
  private TreeModel _model;
  private static int _DEFAULT_FIRST_LEVEL_SIZE = 10;
  private static final String _CONTAINER_KEY = "children";
  private static final String _ROW_KEY = "key";

  public static class TreeNode
  {
    public TreeNode(String name, int id, double cost, double sale, List<TreeNode> children)
    {
      super();
      _name = name;
      _id = id;
      _cost = cost;
      _sale = sale;
      _children = children;
    }

    public void setKey(String key)
    {
      this._key = key;
    }

    public String getKey()
    {
      return _key;
    }

    public void setName(String name)
    {
      this._name = name;
    }

    public String getName()
    {
      return _name;
    }

    public void setId(int id)
    {
      this._id = id;
    }

    public int getId()
    {
      return _id;
    }

    public void setCost(double cost)
    {
      this._cost = cost;
    }

    public double getCost()
    {
      return _cost;
    }

    public void setSale(double sale)
    {
      this._sale = sale;
    }

    public double getSale()
    {
      return _sale;
    }

    public void setChildren(List<TreeNode> children)
    {
      this._children = children;
    }

    public List<TreeNode> getChildren()
    {
      return _children;
    }

    private List<TreeNode> _children;
    private String _key = UUID.randomUUID().toString();
    private String _name;
    private int _id;
    private double _cost;
    private double _sale;

  }
}
