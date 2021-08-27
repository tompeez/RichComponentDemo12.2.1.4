/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich.activedata;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.faces.model.DataModelListener;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.event.ActiveDataEntry;
import oracle.adf.view.rich.event.ActiveDataListener;
import oracle.adf.view.rich.event.ActiveDataUpdateEvent;
import oracle.adf.view.rich.model.ActiveDataModel;

import org.apache.myfaces.trinidad.model.TreeModel;

/**
 * ActiveTreeData implementation for the "active data" visual design demo.
 */
public class ActiveTreeData extends TreeModel implements ActiveDataModel
{
  /**
   * Constructor.
   */
  public ActiveTreeData()
  {
    _baseString = "Tree Node";
    _interval = 500; // milliseconds
    _multiRow = true;

    // update row values
    _updateRows();
  }

  /**
   * Implementation of TreeModel API:
   * Tests to see if the row identified by getRowData() is a container element.
   * Use {@link #isContainerEmpty} to see if the current container element actually has children, or
   * is an empty container.
   * @return true if the current element may contain children
   */
  public boolean isContainer()
  {
    return false;
  }

  /**
   * Implementation of TreeModel API:
   * Tests to see if the current container element actually has children.
   * This could be more efficient than calling {@link #enterContainer} followed by
   * {@link #getRowCount}.
   * This method is permitted to return false even if the container is actually empty.
   * This method should only be called if {@link #isContainer} returns true.
   * @return true if the current container element has no children. If there is any doubt as to
   *         whether or not the container has children, this method should return false
   */
  public boolean isContainerEmpty()
  {
    return true;
  }

  /**
   * Implementation of TreeModel API:
   * This Collection changes to reflect the children of the current rowData, and the current rowData
   * changes to be null.
   * The current rowIndex becomes -1. This method should only be called if {@link #isContainer()}
   * returns true.
   * {@link #getRowCount} can be used to get the number of children.
   */
  public void enterContainer()
  {
  }

  /**
   * Implementation of TreeModel API:
   * Pops back up to the parent collection.
   * The current rowData becomes the rowData of the parent.
   * This Collection will change to include the children of the new rowData.
   */
  public void exitContainer()
  {
  }

  /**
   * Implementation of TreeModel API:
   * Gets the rowKey of a given child row's container row.
   * <pre>
   * |-Root1 (rowKey="r1", containerRowKey=null)
   * |  |-Folder1 (rowKey="r1f1", containerRowKey="r1")
   * |  |  |-Node1 (rowKey="r1f1n1", containerRowKey="r1f1")
   * |  |  |-Node2 (rowKey="r1f1n2", containerRowKey="r1f1")
   * </pre>
   * @param childRowKey the rowKey of the child row
   * @return the rowKey of the container, or null if the child is a root row
   */
  public Object getContainerRowKey(Object childRowKey)
  {
    return null;
  }

  public Object getValue()
  {
    return _row0.get(_ATTRIBUTE_NAME);
  }

  /**
   * Return the active data policy of the model
   * 
   * @return the active data policy of the model
   * @see ActiveDataPolicy
   */
  public ActiveDataPolicy getActiveDataPolicy()
  {
    return ActiveDataPolicy.ACTIVE;
  }
    
  /**
   * Called by the DataUpdateManager to notify the ActiveDataModel to start delivering
   * change notifications for the collections of the containers identified by 
   * the rowKeys
   * 
   * @param rowKeys the container row keys of the collection, from which the
   * ActiveDataListener receives active events. Pass null for root collection.  
   * @param startChangeCount Change count to start sending active data from.
   * @param listener ActiveDataListener for the ActiceDataModel to use when notifying
   * the DataUpdateManager of changes
   *
   * @throws IllegalStateException if ActiveDataPolicy is STATIC
   * @throws IllegalArgumentException if <code>listener</code> is <code>null</code> or
   * the <code>startChangeCount</code> is greater than the current change count.
   * @see #stopActiveData
   */
  public synchronized void startActiveData(
    Collection<Object> rowKeys, 
    int startChangeCount, 
    ActiveDataListener listener)
  {
    if(getActiveDataPolicy() == ActiveDataPolicy.STATIC)
    {
      _LOGGER.severe("Attempt to start active data for STATIC ActiveDataPolicy");
      throw new IllegalArgumentException("Attempt to start active data for STATIC ActiveDataPolicy");              
    }
    
    _LOGGER.info("Start active data on MockActiveDataModel with listener:" + listener);

    if (listener == null)
    {
      _LOGGER.severe("Attempt to start active data on MockActiveDataModel with null listener");
      throw new IllegalArgumentException("null DataUpdateListener");
    }

    if(rowKeys == null || rowKeys.isEmpty())
    {//root
      _addActiveDataKeyPathMap(null, listener);
    }
    else
    {
      for(Object key : rowKeys)
      {
        List keyPath = (List)key;
        _addActiveDataKeyPathMap(keyPath, listener);
      }
    }  

    //Only start the timer once
    if (_activeDataKeyPathMap.size() == 1)
    {
      _timerTask = new ValueUpdater();
      _TIMER.schedule(_timerTask, _interval, _interval);
    }      
  }

  /**
   * Called by the DataUpdateManager to notify the ActiveDataModel to stop delivering
   * change notifications for the collections of the container identified by the 
   * given rowKeys
   * 
   * @param rowKeys the container row keys of the collections, from which the
   * @param listener the ActiveDataListener to be removed
   * ActiveDataListener receives active events. Pass null for root collection. 
   * 
   * @throws IllegalStateException If called on an ActiveDataModel that was never
   * started by calling <code>startActiveData</code>
   * @see #startActiveData
   */
  public synchronized void stopActiveData(Collection<Object> rowKeys, 
                                          ActiveDataListener listener)
  {
    _LOGGER.info("Stop active data on MockActiveDataModel");

    if(rowKeys == null || rowKeys.isEmpty())
    {//root
      _removeActiveDataKeyPathMap(null, listener);
    }
    else
    {
      for(Object key : rowKeys)
      {
        List keyPath = (List)key;
        _removeActiveDataKeyPathMap(keyPath, listener);
      }
    }

    if (_activeDataKeyPathMap.isEmpty())
    {
      // cancel the current timerTask when there's no listener
      _timerTask.cancel();    
    }
  }

  /**
   * Returns the current monotonically increasing change count for this ActiveDataModel.
   * @return The current change count
   */
  public synchronized int getCurrentChangeCount()
  {
    return _counter;
  }

  public Object getRowKey()
  {
    if (_rowIndex == -1)
    {
      return null;
    }
    else
    {
      ArrayList key = new ArrayList(1);
      key.add(String.valueOf(_rowIndex));
      return key;
    }
  }

  /**
   * Finds the row with the matching key and makes it current.
   * @param key the rowKey, previously obtained from {@link #getRowKey}.
   */
  public void setRowKey(Object key)
  {
    _rowIndex = -1;
    if (key != null)
    {
      List keys = (List)key;
      if ("0".equals(keys.get(0)))
      {
        _rowIndex = 0;
      }
      else if (_multiRow && "1".equals(keys.get(0)))
      {
        _rowIndex = 1;
      }
    }
  }

  public boolean isRowAvailable()
  {
    return _rowIndex == 0 || ((_rowIndex == 1) && _multiRow);
  }

  public int getRowCount()
  {
    return (_multiRow)
             ? 2
             : 1;
  }

  public java.lang.Object getRowData()
  {
    if (_rowIndex == 0)
    {
      return _row0;
    }
    else if ((_rowIndex == 1) && _multiRow)
    {
      return _row1;
    }
    else
    {
      return null;
    }
  }

  public int getRowIndex()
  {
    return _rowIndex;
  }

  public void setRowIndex(int rowIndex)
  {
    _rowIndex = rowIndex;
  }

  public Object getWrappedData()
  {
    return getRowData();
  }

  public  void setWrappedData(Object newRow)
  {
    if (_rowIndex == 0)
    {
      _row0 = (Map)newRow;
    }
    else if ((_rowIndex == 1) && _multiRow)
    {
      _row1 = (Map)newRow;
    }
  }

  public void addDataModelListener(DataModelListener p1)
  {
  }

  public javax.faces.model.DataModelListener[] getDataModelListeners()
  {
    return new DataModelListener[0];
  }

  public void removeDataModelListener(DataModelListener p1)
  {
  }

  private void _updateRows()
  {
    if (_multiRow)
    {
      _row0 = Collections.singletonMap(_ATTRIBUTE_NAME, _baseString + " on row 0:" + _counter);
      _row1 = Collections.singletonMap(_ATTRIBUTE_NAME, _baseString + " on row 1:" + _counter);
    }
    else
    {
      _row0 = Collections.singletonMap(_ATTRIBUTE_NAME, _baseString + _counter);
    }
  }

  private synchronized void _updateActiveData()
  {
    _counter++;

    // update row content
    _updateRows();

    ActiveDataEntry entry = null;
    switch (_counter%3)
    {
      case 0:
      {
        entry = new MockActiveDataEntry(_ATTRIBUTE_NAMES, getValue(), "0");
        break;
      }
      case 1:
      {
        entry = new MockActiveRemoveDataEntry("1");
        break;
      }
      default:
      {
        entry = new MockActiveInsertDataEntry(getValue(), "1");
      }
    }

    List<ActiveDataEntry> updateList = Collections.singletonList(entry);
    ActiveDataUpdateEvent event =
      new MockActiveDataUpdateEvent(this, _counter, updateList);

    for(Map.Entry<List, Set<ActiveDataListener>> currEntry :
          _activeDataKeyPathMap.entrySet())
    {
      List keyPath = currEntry.getKey();
      Set<ActiveDataListener> listeners = _activeDataKeyPathMap.get(keyPath);
      for (ActiveDataListener listener : listeners)
      {
        _LOGGER.info("Notify listeners on MockActiveDataModel on new data:" + updateList);
        try
        {
          listener.dataChanged(event);
        }
        catch (Throwable e)
        {
          _LOGGER.severe(e);
        }
      }
    }
  }


  /**
   * add keypath->listener entry to the _activeDataKeyPathMap
   */
  private synchronized void _addActiveDataKeyPathMap(List keyPath, 
                                        ActiveDataListener activeDataListener) 
  {
    Set<ActiveDataListener> listeners = _activeDataKeyPathMap.get(keyPath);
    
    if (listeners != null && listeners.contains(activeDataListener))
    {
      _LOGGER.severe("Attempt to start active data for the same listener again");
      throw new IllegalArgumentException("Attempt to start active for same DataUpdateListener");        
    }
    
    if(listeners == null)
    {
      listeners = new HashSet<ActiveDataListener>();
      _activeDataKeyPathMap.put(keyPath, listeners);
    }
    
    listeners.add(activeDataListener);          
  }


  /**
   * remove keypath->listener entry to the _activeDataKeyPathMap
   */
  private synchronized void _removeActiveDataKeyPathMap(List keyPath, 
                                      ActiveDataListener activeDataListener)
  {
    Set<ActiveDataListener> listeners = _activeDataKeyPathMap.get(keyPath);
    
    if (listeners == null || !listeners.contains(activeDataListener))
    {
      throw new IllegalArgumentException("stopActiveData called for the unregistered  listener");
    }
    
    listeners.remove(activeDataListener);      
  }
  
  private final Map<List, Set<ActiveDataListener>> 
    _activeDataKeyPathMap = new HashMap<List, Set<ActiveDataListener>>();
  private final long _interval;
  private final String _baseString;
  private final boolean _multiRow;

  private int _counter;
  private int _rowIndex;
  private Map _row0;
  private Map _row1;

  private ValueUpdater _timerTask;

  /**
   * Implementation of a timer task.
   */
  private class ValueUpdater extends TimerTask
  {
    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run()
    {
      ActiveTreeData.this._updateActiveData();
    }
  }

  /**
   * Implementation of an active data change entry.
   */
  private static class MockActiveDataEntry extends ActiveDataEntry
  {
    /**
     * Setting constructor.
     * @param attributeNames the collection of attributes to update
     * @param value the map of values that may be updated if their corresponding keys are attributes
     * @param rowKey the key of the row to get these new values
     */
    public MockActiveDataEntry(Collection<String> attributeNames, Object value, String rowKey)
    {
      _attributeNames = attributeNames.toArray(new String[attributeNames.size()]);
      _value = value;
      _rowKey = rowKey;
    }

    /**
     * Gets the ChangeType for this entry.
     * @return ChangeType of this entry
     */
    @Override
    public ChangeType getChangeType()
    {
      return ChangeType.UPDATE;
    }

    /**
     * Gets the names of attributes whose values are changed.
     * @return names of attributes whose values are changed
     */
    @Override
    public String[] getAttributeNames()
    {
      return _attributeNames;
    }

    /**
     * Gets the key path.
     * @return the key path
     */
    @Override
    public Object[] getKeyPath()
    {
      ArrayList key = new ArrayList(1);
      key.add(_rowKey);
      return new Object[]{key};
    }

    /**
     * Gets the key path of the insert location.
     * @return the key path of the insert location
     */
    @Override
    public Object[] getInsertKeyPath()
    {
      return null;
    }

    /**
     * Gets the value of the attribute.
     * @return the value of the attribute
     */
    @Override
    public Object getAttributeValue(String name)
    {      
      return _value;
    }

    /**
     * Get the formatted value of the attribute.
     * @return the formatted value of the attribute
     */
    @Override
    public Object getFormattedAttributeValue(String name)
    {      
      return _value;
    }

    private String[] _attributeNames;
    private Object _value;
    private String _rowKey;
  }

  /**
   * Implementation of an active data insert entry.
   */
  private static class MockActiveInsertDataEntry extends ActiveDataEntry
  {
    /**
     * Setting constructor.
     * @param value the new row's values
     * @param rowKey the new row's key
     */
    public MockActiveInsertDataEntry(Object value, String rowKey)
    {
      _value = value;
      _rowKey = rowKey;
    }

    /**
     * Gets the ChangeType for this entry.
     * @return ChangeType of this entry
     */
    @Override
    public ChangeType getChangeType()
    {
      return ChangeType.INSERT;
    }

    /**
     * Gets the key path.
     * @return the key path
     */
    @Override
    public Object[] getKeyPath()
    {
      ArrayList key = new ArrayList(1);
      key.add(_rowKey);
      return new Object[]{key};  
    }

    /**
     * Gets the key path of the insert location.
     * @return the key path of the insert location
     */
    @Override
    public Object[] getInsertKeyPath()
    {
       return new Object[]{null};
    }

    /**
     * Gets the names of attributes whose values are changed.
     * @return names of attributes whose values are changed
     */
    @Override
    public String[] getAttributeNames()
    {
      return null;
    }

    /**
     * Gets the value of the attribute.
     * @return the value of the attribute
     */
    @Override
    public Object getAttributeValue(String name)
    {
      return _value;
    }

    /**
     * Gets the formatted value of the attribute.
     * @return the formatted value of the attribute
     */
    @Override
    public Object getFormattedAttributeValue(String name)
    {
      return _value;
    }

    private Object _value;
    private String _rowKey;
  }

  /**
   * Implementation of an active data remove entry.
   */
  private static class MockActiveRemoveDataEntry extends ActiveDataEntry
  {
    /**
     * Constructor.
     * @param rowKey the key of the row to remove
     */
    public MockActiveRemoveDataEntry(String rowKey)
    {
      _rowKey = rowKey;
    }

    /**
     * Gets the ChangeType for this entry.
     * @return ChangeType of this entry
     */
    @Override
    public ChangeType getChangeType()
    {
      return ChangeType.REMOVE;
    }

    /**
     * Gets the key path.
     * @return the key path
     */
    @Override
    public Object[] getKeyPath()
    {
      ArrayList key = new ArrayList(1);
      key.add(_rowKey);
      return new Object[]{key};
    }

    /**
     * Gets the key path of the insert location.
     * @return the key path of the insert location
     */
    @Override
    public Object[] getInsertKeyPath()
    {
      return null;
    }

    /**
     * Gets the collection of names of attributes whose values are changed.
     * @return collection of names of attributes whose values are changed
     */
    public Collection<String> getUpdatedAttributeNames()
    {
      return null;
    }

    /**
     * Gets the names of attributes whose values are changed.
     * @return names of attributes whose values are changed
     */
    @Override
    public String[] getAttributeNames()
    {
      return null;
    }

    /**
     * Gets the value of the attribute.
     * @return the value of the attribute
     */
    @Override
    public Object getAttributeValue(String name)
    {
      return null;
    }

    /**
     * Gets the formatted value of the attribute.
     * @return the formatted value of the attribute
     */
    @Override
    public Object getFormattedAttributeValue(String name)
    {
      return null;
    }

    private String _rowKey;
  }

  /**
   * Implementation of an active data update entry.
   */
  private static class MockActiveDataUpdateEvent extends ActiveDataUpdateEvent
  {
    /**
     * Setting constructor.
     * @param source
     * @param eventId
     * @param changeList
     */
    public MockActiveDataUpdateEvent(
      Object source,
      int eventId, 
      List<ActiveDataEntry> changeList)
    {
      super(source);

      _changeList = changeList;
      _eventId = eventId;
    }

    /**
     * Gets the change list of this event.
     * @return the change list of this event
     */
    @Override
    public List<ActiveDataEntry> getChangeList()
    {    
      return _changeList;
    }

    /**
     * Gets the event id.
     * @return the event id
     */
    @Override
    public int getEventId()
    {
      return _eventId;
    }

    public long getEventTime() 
    {
      return System.currentTimeMillis();
    }

    private final List<ActiveDataEntry> _changeList;
    private final int _eventId;
  }

  private static final ADFLogger _LOGGER = ADFLogger.createADFLogger(ActiveTreeData.class);

  private static final String _ATTRIBUTE_NAME = "value";
  private static final List<String> _ATTRIBUTE_NAMES = Collections.singletonList(_ATTRIBUTE_NAME);

  // create daemon Timer
  private static final Timer _TIMER = new Timer(true);
}

