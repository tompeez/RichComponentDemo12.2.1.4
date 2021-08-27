/* Copyright (c) 2009, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.table.rich;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.model.RowKeyPropertyModel;
import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;


public class TableInsertUpdateDelete
{
  public TableInsertUpdateDelete()
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
          TableRow newRow = _createRow();
          List<TableRow> childList = _getChildCollection();
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
          TableRow newRow = _createRow();
          List<TableRow> childList = _getChildCollection();
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

  public void insertStart(ActionEvent actionEvent)
  {
    TableRow newRow = _createRow();
    List<TableRow> childList = _getChildCollection();
    childList.add(0, newRow);
    RequestContext.getCurrentInstance().addPartialTarget(_component);
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
        TableRow row = (TableRow) _model.getRowData();
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
          List<TableRow> childList = _getChildCollection();
          childList.remove(childIndex);
          _component.getSelectedRowKeys().remove(rowKey);
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
    return _createModel();
  }

  public void setComponent(UIXTable component)
  {
    this._component = component;
  }

  public UIXTable getComponent()
  {
    return _component;
  }


  private TableRow _createRow()
  {
    int count = _model.getRowCount();
    return new TableRow("name" + count, count * 100,
                        Math.random() * 100000., Math.random() * 1000000.);
  }

  private CollectionModel _createModel()
  {
    if (_model == null)
    {
      List<TableRow> rows = new ArrayList<TableRow>(_INITIAL_ROW_COUNT);
      for (int r = 0; r < _INITIAL_ROW_COUNT; r++)
      {
        rows.add(new TableRow("name" + r, r * 100, Math.random() * 100000.,
                              Math.random() * 1000000.));
      }

      _model = new RowKeyPropertyModel(rows, _KEY_FIELD);
    }
    return _model;
  }

  private List<TableRow> _getChildCollection()
  {
    if (_model != null)
      return (List<TableRow>) _model.getWrappedData();

    return null;
  }

  private UIXTable _component;
  private CollectionModel _model;
  private static final int _INITIAL_ROW_COUNT = 10;
  private static final String _KEY_FIELD = "key";

  public static class TableRow
  {
    public TableRow(String name, int id, double cost, double sale)
    {
      super();
      this._name = name;
      this._id = id;
      this._cost = cost;
      this._sale = sale;
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

    private String _key = UUID.randomUUID().toString();
    private String _name;
    private int _id;
    private double _cost;
    private double _sale;
  }
}
