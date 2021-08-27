/* Copyright (c) 2006, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.activedata;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.faces.model.DataModelListener;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.activedata.ActiveModelContext;
import oracle.adf.view.rich.event.ActiveDataEntry;
import oracle.adf.view.rich.event.ActiveDataListener;
import oracle.adf.view.rich.event.ActiveDataUpdateEvent;
import oracle.adf.view.rich.model.ActiveDataModel;

import org.apache.myfaces.trinidad.model.CollectionModel;

/**
 * ActiveDataModel base class implementation that fires value updates with a value of
 * <code>baseString</code> + <i>current counter</i> every <code>interval</code> milliseconds
 */
public abstract class MockActiveDataModel extends CollectionModel implements ActiveDataModel
{
  public MockActiveDataModel(
    String baseString,
    long interval,
    boolean multiRow,
    boolean rawHTML)
  {
    _baseString = baseString;
    _interval = interval;
    _multiRow = multiRow;
    _rawHTML = rawHTML;

    // update the manually updated row2
    if (multiRow && !_rawHTML)
      _updateRows(_manualUpdateCounter, true);

    // update dynamic row values
    _updateRows(_autoUpdateCounter, false);
  }
  
  public ValueRow getFirstRow()
  {
    // put first row into scope
    _rowIndex = 0;
    return _row0;
  }

  public ValueRow getLastRow()
  {
    if (!_multiRow)
      return getFirstRow();
    else if (_rawHTML)
    {
      _rowIndex = 1;
      return _row1;
    }
    else
    {
      // put manual update row into scope
      _rowIndex = 2;
      return _row2;
    }
  }

  private Object _getRow0Value()
  {
    return _row0.getValue();
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
   * change notifications.
   * @param startChangeCount Change count to start sending active data from.
   * @param listener ActiveDateListener for the ActiceDataModel to use when notifying
   * the DataUpdateManager of changes
   * @throws IllegalStateException if called on an already started ActiveDataModel
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
   * change notifications.  
   * @throws IllegalStateException If called on an ActiveDataModel that was never
   * started by calling <code>startActiveData</code>
   * @see #startActiveData
   */
  public synchronized void stopActiveData(Collection<Object> rowKeys, ActiveDataListener listener)
  {
    _LOGGER.severe("Stop active data on MockActiveDataModel");
    
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
    return _autoUpdateCounter + _manualUpdateCounter;
  }

  public Object getRowKey()
  {
    if (_rowIndex == -1)
      return null;
    else
      return String.valueOf(_rowIndex);
  }

  /**
   * Finds the row with the matching key and makes it current
   * @param key the rowKey, previously obtained from {@link #getRowKey}.
   */
  public void setRowKey(Object key)
  {
    if("0".equals(key))
      _rowIndex = 0;
    else if(_multiRow && "1".equals(key))
      _rowIndex = 1;
    else if(_multiRow && !_rawHTML && "2".equals(key))
      _rowIndex = 2;
    else
      _rowIndex = -1;
  }

  public boolean isRowAvailable()
  {
    int rowIndex = _rowIndex;
    
    return (rowIndex == 0) ||
           (_multiRow && (rowIndex == 1)) ||
           (_multiRow && !_rawHTML && (rowIndex == 2));
  }

  public int getRowCount()
  {
    return (_multiRow)
             ? (_rawHTML)
               ? 2
               : 3
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
    else if ((_rowIndex == 2) && _multiRow && !_rawHTML)
    {
      return _row2;
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

  public void setWrappedData(Object newRow)
  {
    ValueRow newValueRow = (ValueRow)newRow;
    
    if (_rowIndex == 0)
      _row0 = newValueRow;
    else if (_multiRow)
    {
     if ((_rowIndex == 1) && _multiRow)
       _row1 = newValueRow;
     else
       if (_rowIndex == 2 && _multiRow && !_rawHTML)
         _row2 = newValueRow;
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

  private boolean _isCommandVisible(int counter)
  {
    return ((counter / 6)) % 2 == 0;
  }
  
  private MockActiveDataEntry _createFirstRowUpdateEntery(List<String> changedAttrs)
  {
    return new MockActiveDataEntry(changedAttrs, _row0, "0");
  }

  /**
   * Manually increments the state of the second row
   */
  public String manualUpdate()
  {
    _manualUpdateCounter++;
    List<String> changedAttrs = _updateRows(_manualUpdateCounter, true);
    _deliverActiveDataEvent(new MockActiveDataEntry(changedAttrs, _row2, "2"));
    
    return null;
  }
  
  

  private List<String> _updateRows(int counter, boolean updateRow2)
  {
    String baseImageURI = "/images/folder.gif";
    boolean imageURIChanged = false;
        
    // toggle the image every three changes
    imageURIChanged = (counter % 3 == 0);

    // We change the icon every other update (which happens when _counter%3==0),
    // so we use _counter%6
    if (counter % 6 == 0)
    {
      baseImageURI = "/images/file.gif";
    }
    
    boolean wasVisible = _isCommandVisible(counter - 3);
    boolean isVisible = _isCommandVisible(counter);
    boolean visibilityChanged = (wasVisible != isVisible);
       
    if (_rawHTML)
    {
      String htmlText = _baseString + 
                        " <img src=\"/adf-richclient-demo-context-root" +
                        baseImageURI +
                        "\"> " +
                        counter;
      
      _row0 =  new ValueRow(htmlText, baseImageURI, isVisible);
      if (_multiRow)
        _row1 = _row0;
    }
    else
    {
      if (updateRow2)
        _row2 = new ValueRow("Manually updated row 2:" + counter, baseImageURI, isVisible);
      else
      {
        if (_multiRow)
        {
          _row0 = new ValueRow(_baseString + " on row 0:" + counter, baseImageURI, isVisible);
          _row1 = new ValueRow(_baseString + " on row 1:" + counter, baseImageURI, isVisible);
        }
        else
        {
          _row0 = new ValueRow(_baseString + counter, baseImageURI, isVisible);
        }
      }
    }
    
    // determine which attributes have changed
    List<String> changedAttrs = (imageURIChanged)
                                  ? (visibilityChanged)
                                    ? _ALL_ATTRIBUTE_NAMES
                                    : _VALUE_AND_IMAGE_URI_ATTRIBUTE_NAMES
                                  : (visibilityChanged)
                                    ? _VALUE_AND_VISIBILITY_ATTRIBUTE_NAMES
                                    : _VALUE_ONLY_ATTRIBUTE_NAMES;
    
    return changedAttrs;
  }

  private synchronized void _updateActiveData()
  {
    _autoUpdateCounter++;

    // update row content
    List<String> changedAttrs = _updateRows(_autoUpdateCounter, false);

    ActiveDataEntry entry = null;
    
    switch(_autoUpdateCounter % 3)
    {
    case 0:
      entry = _createFirstRowUpdateEntery(changedAttrs);
      break;
    case 1:
      entry = new MockActiveRemoveDataEntry();
      break;
    case 2:
      Object[] insertPath = (_multiRow && !_rawHTML)
                              ? new Object[]{"0"}
                              : new Object[]{null};
      
      entry = new MockActiveInsertDataEntry(insertPath, _getRow0Value());   
      break;
    }
    
    _deliverActiveDataEvent(entry);
  }
  
  private void _deliverActiveDataEvent(ActiveDataEntry entry)
  {
    if (entry != null)
    { 
      List<ActiveDataEntry> updateList = Collections.singletonList(entry);
      
      ActiveDataUpdateEvent event = 
        new MockActiveDataUpdateEvent(this, getCurrentChangeCount(), updateList);
       
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
  }
  
  private final List<ActiveDataListener> _listeners = new LinkedList<ActiveDataListener>();
  private final long _interval;
  private final String _baseString;
  private final boolean _multiRow;
  private final boolean _rawHTML;

  private int _autoUpdateCounter;
  private int _manualUpdateCounter;
  private int _rowIndex;
  private ValueRow _row0;
  private ValueRow _row1;
  private ValueRow _row2;

  private ValueUpdater _timerTask;

  // TimerTask instance
  private class ValueUpdater extends TimerTask
  {
    public void run()
    {
      MockActiveDataModel.this._updateActiveData();
    }
  }
  
  private static class MockActiveDataEntry extends ActiveDataEntry
  {
    public MockActiveDataEntry(Collection<String> attributeNames, ValueRow row, String rowKey)
    {
      _attributeNames = attributeNames.toArray(new String[attributeNames.size()]);
      _row = row;
      _rowKey  = rowKey;
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
      return new Object[] {_rowKey};  
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
      return _row.getProperty(name);
    }
      
    /**
     * Get the formatted value of the attribute
     * 
     * @return the formatted value of the attribute
     */    
    public Object getFormattedAttributeValue(String name)
    {      
      return getAttributeValue(name);
    }
    
    public String toString()
    {
      return super.toString() + 
             " attributeNames:" + Arrays.toString(_attributeNames) +
             " attributeData:" + _row;
    }
    
    private final String[] _attributeNames;
    private final ValueRow _row;
    private final String   _rowKey;
  }

  private static class MockActiveInsertDataEntry extends ActiveDataEntry
  {
    public MockActiveInsertDataEntry(Object[] insertPath, Object value)
    {
      _insertPath = insertPath;
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
      return new Object[] {"1"};  
    }
    
    /**
     * Get the key path of the insert location
     * 
     * @return the key path of the insert location
     */
    public Object[] getInsertKeyPath()
    {
      return (Object[])_insertPath.clone();
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
    
    private final Object[] _insertPath;
    private final Object _value;    
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
      return new String[] {"1"};  
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
    
    public String toString()
    {
      return super.toString() + " eventId:" + _eventId + " changeList:" + _changeList;
    }
    
    private final List<ActiveDataEntry> _changeList;
    private final int _eventId;
  }
  
  public class ValueRow
  {
    public ValueRow(String value, String imageURI, boolean commandVisible)
    {
      this.value = value;
      this.imageURI = imageURI;
      this.commandVisible = commandVisible;
    }
    
    public String getValue()
    {
      return (String)getProperty(_VALUE_ATTRIBUTE_NAME);
    }
    public void setValue(String value)
    {
      //
    }

    public String getImageURI()
    {
      return (String)getProperty(_IMAGE_URI_ATTRIBUTE_NAME);
    }

    public Boolean getCommandVisible()
    {
      return (Boolean)getProperty(_COMMAND_VISIBLE_ATTRIBUTE_NAME);
    }
    
    public Object getProperty(String propertyName)
    {
      ActiveModelContext activeModelContext = ActiveModelContext.getActiveModelContext();
      
      if (_LOGGER.isInfo())
        _LOGGER.info("Get MockActiveDataModel property:" + propertyName + " on component " + 
                     activeModelContext.getCurrentActiveComponent());

      if (activeModelContext.isComponentInterestedInActiveData())
      {
        Object currRowKey = MockActiveDataModel.this.getRowKey();
        Object[] currPath = (currRowKey != null)
                              ? new Object[]{currRowKey}
                              : null;
        
        activeModelContext.addActiveModelInfo(MockActiveDataModel.this,
                                              currPath,
                                              propertyName);
      }
      
      if (_VALUE_ATTRIBUTE_NAME.equals(propertyName))
        return value;
      else if (_IMAGE_URI_ATTRIBUTE_NAME.equals(propertyName))
        return imageURI;
      else if (_COMMAND_VISIBLE_ATTRIBUTE_NAME.equals(propertyName))
        return commandVisible;
      else
        return null;
    }
    
    public String toString()
    {
      return "[value:" + value +
             ", imageURI:" + imageURI + 
             ", commandVisible:" +commandVisible + "]";
    }
    
    public final String value;
    public final String  imageURI;
    public final boolean commandVisible;
  }

  private static final ADFLogger _LOGGER = ADFLogger.createADFLogger(MockActiveDataModel.class);

  private static final String _VALUE_ATTRIBUTE_NAME = "value";
  private static final String _IMAGE_URI_ATTRIBUTE_NAME = "imageURI";
  private static final String _COMMAND_VISIBLE_ATTRIBUTE_NAME = "commandVisible";
  private static final List<String> _VALUE_ONLY_ATTRIBUTE_NAMES = Collections.singletonList(_VALUE_ATTRIBUTE_NAME);
  private static final List<String> _VALUE_AND_IMAGE_URI_ATTRIBUTE_NAMES = 
                             Arrays.asList(_VALUE_ATTRIBUTE_NAME, _IMAGE_URI_ATTRIBUTE_NAME);
  private static final List<String> _VALUE_AND_VISIBILITY_ATTRIBUTE_NAMES = 
                             Arrays.asList(_VALUE_ATTRIBUTE_NAME, _COMMAND_VISIBLE_ATTRIBUTE_NAME);
  private static final List<String> _ALL_ATTRIBUTE_NAMES = 
                             Arrays.asList(_VALUE_ATTRIBUTE_NAME,
                                           _IMAGE_URI_ATTRIBUTE_NAME,
                                           _COMMAND_VISIBLE_ATTRIBUTE_NAME);
    
  // create daemon Timer
  private static final Timer _TIMER = new Timer(true);
}
