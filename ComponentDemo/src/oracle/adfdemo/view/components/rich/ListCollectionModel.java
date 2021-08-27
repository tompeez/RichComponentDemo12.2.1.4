/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import java.util.concurrent.atomic.AtomicInteger;

import javax.faces.model.DataModelEvent;
import javax.faces.model.DataModelListener;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.model.CollectionModel;


/**
 * A collectionModel based off of a list. 
 * 
 * This class was designed for use with an iterator (repeater) to support the adding and removing of 
 * rows, which is not supported when using an iterator with a regular List as your CollectionModel.
 */
public class ListCollectionModel extends CollectionModel
{

  public ListCollectionModel()
  {
    this(null);
  }

  public ListCollectionModel(List list)
  {
    setWrappedData(list);  
  }

  ///////////// CollectionModel Overrides

  @Override
  public Object getRowKey()
  {
    if (!isRowAvailable())
    {
      return null;  
    }
    return _listOfKeys.get(getRowIndex());
  }

  @Override
  public void setRowKey(Object newKey)
  {
    if (newKey == null)
      setRowIndex(-1);
    
    setRowIndex(_listOfKeys.indexOf(newKey));
  }

  ///////////// DataModel Overrides

  @Override
  public boolean isRowAvailable()
  {
    if (_listOfKeys == null) 
      return false;
    else if (_index >= 0 && _index < _listOfKeys.size()) 
        return true;
    else
      return false;
  }

  @Override
  public int getRowCount()
  {
    if (_listOfKeys == null)
      return -1;
    return _listOfKeys.size();
  }

  @Override
  public Object getRowData()
  {
    if (_listOfKeys == null) 
      return null;

    if (!isRowAvailable()) 
      throw new IllegalArgumentException();

    Object key = _listOfKeys.get(_index);    
    return _mapOfItems.get(key);
  }

  @Override
  public int getRowIndex()
  {
    return _index;
  }

  @Override
  public void setRowIndex(int rowIndex)
  {
    if (rowIndex < -1) 
      throw new IllegalArgumentException();

    int old = _index;
    _index = rowIndex;
    if (_listOfKeys == null) 
      return;

    DataModelListener[] listeners = getDataModelListeners();
    if ((old != _index) && (listeners != null)) 
    {
      Object rowData = null;
      if (isRowAvailable()) 
        rowData = getRowData();

      DataModelEvent event = new DataModelEvent(this, _index, rowData);
      int n = listeners.length;
      for (int i = 0; i < n; i++) 
      {
        if (null != listeners[i]) 
          listeners[i].rowSelected(event);
      }
    }
  }

  @Override
  public Object getWrappedData()
  {
    //reconstruct and return list
    List returnList = new ArrayList();
    for (Iterator i=_listOfKeys.iterator(); i.hasNext();)
    {
      Object key = i.next();
      Object item = _mapOfItems.get(key); 
      returnList.add(item);
    }
    return returnList;    
  }

  /**
   * Data provided to this method must be a List object.
   */
  @Override
  public void setWrappedData(Object data)
  {
    assert(data instanceof List);
    if (data == null) 
    {
      _listOfKeys = null;
      _mapOfItems = null;
      setRowIndex(-1);
    } 
    else 
    {
      List listData = (List) data;
      _listOfKeys = new ArrayList(listData.size());
      _mapOfItems = new HashMap(listData.size());

      for (Iterator i=listData.iterator(); i.hasNext();)
      {
        Object item = i.next();

        //make keys for each item
        Object keyObject = _counter.getAndIncrement();

        //make a map of keys -> items
        _mapOfItems.put(keyObject, item);

        //make a list of keys
        _listOfKeys.add(keyObject);
      }
      _index = -1;
      setRowIndex(0);
    }
  }
  
  public void add(int addRowIndex, Object newItem)
  {
    if (addRowIndex < 0 || addRowIndex > _listOfKeys.size())
    {
      _LOG.warning("Trying to add row at invalid spot, index " + addRowIndex);
      return;
    }
    
    Object newKey = _counter.getAndIncrement();
    _mapOfItems.put(newKey, newItem);
    _listOfKeys.add(addRowIndex, newKey);
  }
  
  public void remove(int removeRowIndex)
  {
    if (!isRowAvailable(removeRowIndex))
    {
      _LOG.warning("Trying to delete invalid row " + removeRowIndex);
      return;
    }
    Object key = _listOfKeys.get(removeRowIndex);
    _mapOfItems.remove(key);
    _listOfKeys.remove(removeRowIndex);
  }
  
  // The current row index
  private int _index = -1;
  
  private List _listOfKeys;
  private Map _mapOfItems;
  private AtomicInteger _counter = new AtomicInteger();
  private static final ADFLogger _LOG = ADFLogger.createADFLogger(ListCollectionModel.class);
}
