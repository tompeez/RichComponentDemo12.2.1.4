/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.activedata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.faces.model.DataModelListener;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.event.ActiveDataEntry;
import oracle.adf.view.rich.event.ActiveDataListener;
import oracle.adf.view.rich.event.ActiveDataUpdateEvent;
import oracle.adf.view.rich.model.ActiveDataModel;

import org.apache.myfaces.trinidad.model.TreeModel;


public class MockTreeActiveDataModel extends TreeModel implements ActiveDataModel
{
  public MockTreeActiveDataModel()
  {
    _baseString = "Tree Node";
    _interval = 1000;
    _multiRow = true;

    // update row values
    _updateRows();
  }
  
  public boolean isContainer()
  {
    return false;
  }
  
  public boolean isContainerEmpty()
  {
    return true;
  }
  
  public void enterContainer()
  {
  }
  
  public void exitContainer()
  {
  }
  
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
   * 
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
  public synchronized void startActiveData(Collection<Object> rowKeys, 
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
    
    if(_listeners.contains(listener))
    {
      _LOGGER.severe("Attempt to start active data for the same listener again");
      throw new IllegalArgumentException("Attempt to start active for same DataUpdateListener");        
    }
        
    _listeners.add(listener);  
    
    //Only start the timer once
    if(_listeners.size() == 1)
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
  public void stopActiveData(Collection<Object> rowKeys, 
                             ActiveDataListener listener)
  {
    _LOGGER.info("Stop active data on MockActiveDataModel");
    
    if (!_listeners.contains(listener))
      throw new IllegalArgumentException("stopActiveData called for the unregistered  listener");
    
    _listeners.remove(listener);
    
    if(_listeners.isEmpty())
    {
      // cancel the current timerTask when there's no listener
      _timerTask.cancel();    
    }
  }

  /**
   * Returns the current monotonically increasing change count for this ActiveDataModel
   * @return The current change count
   */
  public synchronized int getCurrentChangeCount()
  {
    return _counter;
  }

  public Object getRowKey()
  {
    if (_rowIndex == -1)
      return null;
    else
    {
      ArrayList key = new ArrayList(1);
      key.add(String.valueOf(_rowIndex));
      return key;
    }
  }

  /**
   * Finds the row with the matching key and makes it current
   * @param key the rowKey, previously obtained from {@link #getRowKey}.
   */
  public void setRowKey(Object key)
  {
    _rowIndex = -1;
    if (key != null)
    {
      List keys = (List)key;
      if("0".equals(keys.get(0)))
        _rowIndex = 0;
      else if(_multiRow && "1".equals(keys.get(0)))
        _rowIndex = 1;
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

  public  int getRowIndex()
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
      _row0 = (Map)newRow;
    else if ((_rowIndex == 1) && _multiRow)
      _row1 = (Map)newRow;
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
    switch(_counter%3)
    {
    case 0: 
      entry = new MockActiveDataEntry(_ATTRIBUTE_NAMES, getValue());   
      break;
    case 1:
      entry = new MockActiveRemoveDataEntry();   
      break;
    default:
      entry = new MockActiveInsertDataEntry(getValue());   
      break;
    }
    
    List<ActiveDataEntry> updateList = Collections.singletonList(entry);
    ActiveDataUpdateEvent event = 
      new MockActiveDataUpdateEvent(this, _counter, updateList);
     
    for(ActiveDataListener listener : _listeners)
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
  
  private final List<ActiveDataListener> _listeners = new LinkedList<ActiveDataListener>();
  private final long _interval;
  private final String _baseString;
  private final boolean _multiRow;

  private int _counter;
  private int _rowIndex;
  private Map _row0;
  private Map _row1;

  private ValueUpdater _timerTask;

  // TimerTask instance
  private class ValueUpdater extends TimerTask
  {
    public void run()
    {
      MockTreeActiveDataModel.this._updateActiveData();
    }
  }
  
  private static class MockActiveDataEntry extends ActiveDataEntry
  {
    public MockActiveDataEntry(Collection<String> attributeNames, Object value)
    {
      _attributeNames = attributeNames.toArray(new String[attributeNames.size()]);
      _value = value;
    }
    
    /**
     * @return ChangeType of this entry
     */
    public ChangeType getChangeType()
    {
      return ChangeType.UPDATE;
    }
    
    /**
     * Get the names of attributes whose values are changed
     * 
     * @return names of attributes whose values are changed
     */  
    public String[] getAttributeNames()
    {
      return _attributeNames;
    }
    
    /**
     * Get the key path
     * 
     * @return the key path
     */    
    public Object[] getKeyPath()
    {
      ArrayList key = new ArrayList(1);
      key.add("0");
      return new Object[]{key};
    }
    
    /**
     * Get the key path of the insert location
     * 
     * @return the key path of the insert location
     */
    public Object[] getInsertKeyPath()
    {
      return null;
    }
    
    /**
     * Get the value of the attribute
     * 
     * @return the value of the attribute
     */    
    public Object getAttributeValue(String name)
    {      
      return _value;
    }
      
    /**
     * Get the formatted value of the attribute
     * 
     * @return the formatted value of the attribute
     */    
    public Object getFormattedAttributeValue(String name)
    {      
      return _value;
    }
    
    private String[] _attributeNames;
    private Object _value;
  }

  private static class MockActiveInsertDataEntry extends ActiveDataEntry
  {
    public MockActiveInsertDataEntry(Object value)
    {
      _value = value;      
    }
    
    /**
     * @return ChangeType of this entry
     */
    public ChangeType getChangeType()
    {
      return ChangeType.INSERT;
    }
    
    /**
     * Get the key path
     * 
     * @return the key path
     */
    public Object[] getKeyPath()
    {
      ArrayList key = new ArrayList(1);
      key.add("1");
      return new Object[]{key};  
    }
    
    /**
     * Get the key path of the insert location
     * 
     * @return the key path of the insert location
     */
    public Object[] getInsertKeyPath()
    {
       return new Object[]{null};
    }

    /**
     * Get the names of attributes whose values are changed
     * 
     * @return names of attributes whose values are changed
     */  
    public String[] getAttributeNames()
    {
      return null;
    }
    
    /**
     * Get the value of the attribute
     * 
     * @return the value of the attribute
     */    
    public Object getAttributeValue(String name)
    {
      return _value;
    }

    /**
     * Get the formatted value of the attribute
     * 
     * @return the formatted value of the attribute
     */    
    public Object getFormattedAttributeValue(String name)
    {      
      return _value;
    }    
    
    private Object _value;    
  }

  private static class MockActiveRemoveDataEntry extends ActiveDataEntry
  {
    /**
     * @return ChangeType of this entry
     */
    public ChangeType getChangeType()
    {
      return ChangeType.REMOVE;
    }
    
    /**
     * Get the key path
     * 
     * @return the key path
     */
    public Object[] getKeyPath()
    {
      ArrayList key = new ArrayList(1);
      key.add("1");
      return new Object[]{key};  
    }

    /**
     * Get the key path of the insert location
     * 
     * @return the key path of the insert location
     */
    public Object[] getInsertKeyPath()
    {
      return null;
    }
    
    /**
     * Get the collection of names of attributes whose values are changed
     * 
     * @return collection of names of attributes whose values are changed
     */
    public Collection<String> getUpdatedAttributeNames()
    {
      return null;
    }
    
    /**
     * Get the names of attributes whose values are changed
     * 
     * @return names of attributes whose values are changed
     */  
    public String[] getAttributeNames()
    {
      return null;
    }    
    
    /**
     * Get the value of the attribute
     * 
     * @return the value of the attribute
     */    
    public Object getAttributeValue(String name)
    {      
      return null;
    }

    /**
     * Get the formatted value of the attribute
     * 
     * @return the formatted value of the attribute
     */    
    public Object getFormattedAttributeValue(String name)
    {
      return null;
    }    
  }

  private static class MockActiveDataUpdateEvent extends ActiveDataUpdateEvent
  {
    public MockActiveDataUpdateEvent(Object source, int eventId, 
                               List<ActiveDataEntry> changeList)
    {
      super(source);
      
      _changeList = changeList;
      _eventId = eventId;
    }
    
    /**
    * Get the change list of this event
    * 
    * @return the change list of this event
    */
    public List<ActiveDataEntry> getChangeList()
    {    
      return _changeList;
    }
    
    /**
    * Get the event id
    * @return the event id
    */
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

  private static final ADFLogger _LOGGER = ADFLogger.createADFLogger(MockTreeActiveDataModel.class);

  private static final String _ATTRIBUTE_NAME = "value";
  private static final List<String> _ATTRIBUTE_NAMES = Collections.singletonList(_ATTRIBUTE_NAME);

  // create daemon Timer
  private static final Timer _TIMER = new Timer(true);
}

