/* Copyright (c) 2009, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.table.rich.model; 

import java.util.ArrayList;
import java.util.Collection;

import java.util.Collections;
import java.util.List;

import java.util.Vector;

import oracle.adf.view.rich.event.ActiveDataEntry;
import oracle.adf.view.rich.event.ActiveDataListener;
import oracle.adf.view.rich.event.ActiveDataUpdateEvent;
import oracle.adf.view.rich.model.ActiveDataModel;

import org.apache.myfaces.trinidad.model.RowKeyPropertyModel;

/**
 * Implementation of an updateable CollectionModel which inserts/updates/removes
 * rows in an underlying Java List.
 * The model also implements <code>ActiveDataModel</code> with
 * <code>ActiveDataPolicy.PPR</code> and fires <code>ActiveDataEvent</code>
 * in response to insert/update/delete operations
 * @param <T> type of the wrapped model (must implement java.util.List)
 */
public class UpdateableCollectionModel<T extends List>
  extends RowKeyPropertyModel
  implements ActiveDataModel, UpdateableModel
{

  public UpdateableCollectionModel(T model, String rowKeyProperty)
  {
    super(model, rowKeyProperty);
  }


  /**
   * Insert a row before the current row
   * @param rowData
   */
  public void insertRow(Object rowData)
  {
    Object currentRowKey = getRowKey();
    if (currentRowKey == null)
    {
      _append(rowData);
      return;
    }

    if (isRowAvailable())    
    {
      int index = getRowIndex();
      Object newKey = null;
      if (index >= 0)
      {
        Object currKey = getKeyPath();
        _getWrappedData().add(index, rowData);
        newKey = getRowKey(rowData);
        newKey = getKeyPath(newKey);
        _fireModelChangeEvent(ActiveDataEntry.ChangeType.INSERT_BEFORE,
                              newKey, currKey);
      }
    }
  }
    
  /**
   * Insert a row before the row with the given rowKey.
   * @param rowKey row key for the insertion point. If this parameter is null,
   * the new row is appened to the model
   * @param rowData
   */
  public void insertRow(Object rowKey, Object rowData)
  {
    
    if (rowKey == null)
    {
      _append(rowData);
      return;
    }
    
    Object oldKey = getRowKey();
    try
    {
      setRowKey(rowKey);
      if (isRowAvailable())
        insertRow(rowData);
    }
    finally
    {
      setRowKey(oldKey);
    }
  }

  /**
   * Delete the "current" row
   */
  public void deleteRow()
  {
    int index = getRowIndex();

    if (index >= 0)
    {
      Object rowKey = getKeyPath();
      _getWrappedData().remove(index);
      _fireModelChangeEvent(ActiveDataEntry.ChangeType.REMOVE, rowKey,
                            null);
    }
  }

  /**
   * Delete the row with the given row key
   * @param rowKey row key for the row to delete
   */
  public void deleteRow(Object rowKey)
  {
    Object oldKey = getRowKey();
    try
    {
      setRowKey(rowKey);
      if (isRowAvailable())
        deleteRow();
    }
    finally
    {
      setRowKey(oldKey);
    }
  }

  /**
   * Delete the rows with the given row keys.  This method fires a single
   * active data delete event for all the deleted rows
   * @param rowKeys
   */
  public void deleteRows(Object[] rowKeys)
  {
    Object oldKey = getRowKey();
    try
    {
      Vector<Object> availableRows = new Vector<Object>(rowKeys.length);
      for (int key = 0; key < rowKeys.length; key++)
      {
        setRowKey(rowKeys[key]);
        if (isRowAvailable())
        {
          int index = getRowIndex();
          if (index >= 0)
          {
            availableRows.add(getKeyPath());
            _getWrappedData().remove(index);
          }
        }
      }
      
      if (availableRows.size() > 0)
      {
        _fireModelChangeEvent(ActiveDataEntry.ChangeType.REMOVE, availableRows.toArray(),
                          new Object[availableRows.size()]);
      }
    }
    finally
    {
      setRowKey(oldKey);
    }
  }

  /**
   * Update the "current" row
   * @param rowData an object containing updated row data
   */
  public void updateRow(Object rowData)
  {
    int rowIndex = getRowIndex();
    
    if (rowIndex >= 0)
    {
      _getWrappedData().set(rowIndex, rowData);
      Object rowKey = getKeyPath();
      _fireModelChangeEvent(ActiveDataEntry.ChangeType.UPDATE, rowKey,
                            null);
    }
  }

  /**
   * Update the row with the given row key
   * @param rowKey
   * @param rowData
   */
  public void updateRow(Object rowKey, Object rowData)
  {
    Object oldKey = getRowKey();
    try
    {
      setRowKey(rowKey);
      if (isRowAvailable())
        updateRow(rowData);        
    }
    finally
    {
      setRowKey(oldKey);
    }
  }

  protected Object getKeyPath(Object rowKey)
  {
    return rowKey;
  }

  protected Object getKeyPath()
  {
    return getRowKey();
  }
  

  //
  // ActiveDataModel APIs
  //
  public ActiveDataModel.ActiveDataPolicy getActiveDataPolicy()
  {
    return ActiveDataModel.ActiveDataPolicy.PPR;
  }

  public void startActiveData(Collection<Object> rowKeys,
                              int startChangeCount,
                              ActiveDataListener listener)
  {
    _adListener = listener;
  }

  public void stopActiveData(Collection<Object> rowKeys,
                             ActiveDataListener listener)
  {
    _adListener = null;
  }

  public int getCurrentChangeCount()
  {
    return 0;
  }

  private Object _append(Object rowData)
  {
    int count = getRowCount();
    Object oldKey = getRowKey();
    Object lastKey = null;
    Object newKey = null;
    try
    {
      if (count > 0)
      {
        setRowIndex(count - 1);
        lastKey = getRowKey();
      }
      newKey = getRowKey(rowData);
      _getWrappedData().add(rowData);          
      newKey = getKeyPath(newKey);
      lastKey = getKeyPath(lastKey);
      _fireModelChangeEvent(ActiveDataEntry.ChangeType.INSERT_AFTER,
                            newKey, lastKey);    
    }
    finally
    {
      setRowKey(oldKey);
    }
    
    return newKey;
  }

  private void _fireModelChangeEvent(ActiveDataEntry.ChangeType changeType,
                                     Object newKey,
                                     Object anchorKey)
  {
    if (_adListener != null)
    {
      ActiveDataEntryImpl entry = new ActiveDataEntryImpl(changeType, anchorKey, newKey);
      List<ActiveDataEntry> changeList = new ArrayList<ActiveDataEntry>(1);
      changeList.add(entry);
      ActiveDataUpdateEventImpl event = new ActiveDataUpdateEventImpl(this, changeList);
      _adListener.dataChanged(event);
    }
  }

  private void _fireModelChangeEvent(ActiveDataEntry.ChangeType changeType,
                                     Object[] newKey,
                                     Object[] anchorKey)
  {
    if (_adListener != null)
    {
      if (newKey.length == anchorKey.length && newKey.length > 0)
      {
        List<ActiveDataEntry> changeList = new ArrayList<ActiveDataEntry>(newKey.length);
        for (int i = 0; i < newKey.length; i++)
        {
          changeList.add(new ActiveDataEntryImpl(changeType, anchorKey[i], newKey[i]));
        }
        ActiveDataUpdateEventImpl event = new ActiveDataUpdateEventImpl(this, changeList);
        _adListener.dataChanged(event);
      }
    }
  }
  
  private T _getWrappedData()
  {
    return (T) getWrappedData();
  }
  
  private static class ActiveDataEntryImpl extends ActiveDataEntry
  {
    public ActiveDataEntryImpl(ActiveDataEntry.ChangeType changeType, Object anchorKey, Object key)
    {
      _changeType = changeType;
      _anchorKey = anchorKey;
      _key = key;
    }

    @Override
    public String[] getAttributeNames()
    {
      return new String[0];
    }

    @Override
    public ActiveDataEntry.ChangeType getChangeType()
    {
      return _changeType;
    }

    @Override
    public Object getAttributeValue(String name)
    {
      return null;
    }

    @Override
    public Object getFormattedAttributeValue(String name)
    {
      return null;
    }

    @Override
    public Object[] getInsertKeyPath()
    {
      return new Object[] {_anchorKey};
    }

    @Override
    public Object[] getKeyPath()
    {
      return new Object[] {_key};
    }
    
    private ActiveDataEntry.ChangeType _changeType;
    private Object _anchorKey;
    private Object _key;
    
  }
  
  private static class ActiveDataUpdateEventImpl extends ActiveDataUpdateEvent
  {

    public ActiveDataUpdateEventImpl(Object source, List<ActiveDataEntry> changeList)
    {
      super(source);
      _changeList = changeList;
    }
    
    @Override
    public List<ActiveDataEntry> getChangeList()
    {
      return _changeList;
    }

    @Override
    public int getEventId()
    {
      return 0;
    }

    @Override
    public long getEventTime()
    {
      return System.currentTimeMillis() / 1000L;
    }
    private List<ActiveDataEntry> _changeList = Collections.emptyList();
  }
  
  private ActiveDataListener _adListener;
  
}
