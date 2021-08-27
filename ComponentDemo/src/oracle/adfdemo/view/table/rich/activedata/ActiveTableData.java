/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich.activedata;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModelListener;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.event.ActiveDataEntry;
import oracle.adf.view.rich.event.ActiveDataEntry.ChangeType;
import oracle.adf.view.rich.event.ActiveDataListener;
import oracle.adf.view.rich.event.ActiveDataUpdateEvent;
import oracle.adf.view.rich.model.ActiveDataModel;

import org.apache.myfaces.trinidad.component.UIXNavigationHierarchy;
import org.apache.myfaces.trinidad.model.CollectionModel;

/**
 * ActiveTableData implementation for the "active data" visual design demo.
 */
public class ActiveTableData extends CollectionModel implements ActiveDataModel
{
  // avalable active data operations
  private enum ActiveOperation
  {
    INSERT(true), UPDATE(false), DELETE(false);
  
 
    private ActiveOperation(boolean supportsNull)
    {
      _supportsNull = supportsNull;
    }    
   
    /** Returns an random Operation that supports the key.  This is really just checking for null cases*/     
    public static ActiveOperation getRandomOperation(String key)
    {
      ActiveOperation operation;
      
      // loop until we have an operation that can handle the key
      do
      {
        operation = VALUES[_getIntBetweenZeroAnd(VALUES.length - 1)];
        
        if (operation == DELETE) operation = UPDATE;
        
      } while ((key == null) && !operation._supportsNull);
      
      return operation;
    }
    
    private static final ActiveOperation[] VALUES = ActiveOperation.class.getEnumConstants();
    
    private final boolean _supportsNull;
  }
  
  
  /**
   * Constructor.
   */
  public ActiveTableData()
  {
    _interval = 1000; // milliseconds

    // update row values
    _initializeRows();
    
    Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    
    _currencyFormatter = NumberFormat.getCurrencyInstance(locale);
  }

  /**
   * Demo facet name for the active demo visual design demo switcher.
   * @return null or the name of the facet the user wishes to see
   */
  public String getActiveDataDemoFacetName()
  {
    return _activeDataDemoFacetName;
  }

  /**
   * Changes the demo facet name for the table demo.
   * @param event the ActionEvent associated with the action
   */
  public void showTableDemo(ActionEvent event)
  {
    _showDemo(event, _DEMO_NAME_TABLE);
  }

  /**
   * Changes the demo facet name for the tree demo.
   * @param event the ActionEvent associated with the action
   */
  public void showTreeDemo(ActionEvent event)
  {
    _showDemo(event, _DEMO_NAME_TREE);
  }

  /**
   * Changes the demo facet name for the treeTable demo.
   * @param event the ActionEvent associated with the action
   */
  public void showTreeTableDemo(ActionEvent event)
  {
    _showDemo(event, _DEMO_NAME_TREETABLE);
  }

  /**
   * Changes the demo to show the specified facet.
   * @param event the ActionEvent associated with the action
   * @param demoFacetName the name of the facet to show in the demo switcher
   */
  private void _showDemo(ActionEvent event, String demoFacetName)
  {
    _activeDataDemoFacetName = demoFacetName;
    UIComponent actionItem = event.getComponent();
    UIComponent parent = actionItem.getParent();
    while (! (parent instanceof UIXNavigationHierarchy) )
    {
      parent = parent.getParent();
      if (parent == null)
      {
        System.err.println(
          "Unexpected component hierarchy, no UIXNavigationHierarchy found.");
        return;
      }
    }
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
    if (getActiveDataPolicy() == ActiveDataPolicy.STATIC)
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

    boolean startTimer;
    
    List<ActiveDataListener> listeners = _listeners;
    
    // even though we use a CopyOnWriteArrayLIst, we are performing compound operations, so we need to synchronize
    synchronized (listeners)
    {
      if (listeners.contains(listener))
      {
        _LOGGER.severe("Attempt to start active data for the same listener again");
        throw new IllegalArgumentException("Attempt to start active for same DataUpdateListener");        
      }

      listeners.add(listener);  

      // Only start the timer once
      startTimer = listeners.size() == 1;
    }
    
    if (startTimer)
    {
      _timerTask = new ValueUpdater();
      _TIMER.schedule(_timerTask, 0, _interval);
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

    boolean cancelTimer;
    
    List<ActiveDataListener> listeners = _listeners;

    // even though we use a CopyOnWriteArrayLIst, we are performing compound operations, so we need to synchronize
    synchronized (listeners)
    {
      if (!listeners.contains(listener))
      {
        throw new IllegalArgumentException("stopActiveData called for the unregistered  listener");
      }

      listeners.remove(listener);

      // cancel the current timerTask when there are no more listeners
      cancelTimer = listeners.isEmpty();
    }
    
    if (cancelTimer)
    {
      _timerTask.cancel();    
    }
  }

  /**
   * Returns the current monotonically increasing change count for this ActiveDataModel.
   * @return The current change count
   */
  public int getCurrentChangeCount()
  {
    return _tableData.getChangeCount();
  }

  public Object getRowKey()
  {
    return _rowKey;
  }

  /**
   * Finds the row with the matching key and makes it current.
   * @param key the rowKey, previously obtained from {@link #getRowKey}.
   */
  public void setRowKey(Object key)
  {
    _rowKey = (String)key;
  }

  public boolean isRowAvailable()
  {
    String rowKey = _rowKey;
    
    if (rowKey == null)
    {
      return false;
    }
    else
    {    
      return _tableData.containsKey(rowKey);
    }
  }

  public int getRowCount()
  {
    return _tableData.getSize();
  }

  public java.lang.Object getRowData()
  {
    String rowKey = _rowKey;

    if (rowKey == null)
    {
      return null;
    }
    else
    {      
      return _tableData.getRowData(rowKey);
    }
  }

  public int getRowIndex()
  {
    return _tableData.getRowIndex(_rowKey);
  }

  public void setRowIndex(int rowIndex)
  {
    if (rowIndex < 0)
    {
      if (rowIndex == -1)
      {
        _rowKey = null;
      }
      else
      {
        throw new IllegalArgumentException("Negative row index:" + rowIndex);
      }
    }
    else
    {
      _rowKey = _tableData.getRowKey(rowIndex);
    }
  }

  public Object getWrappedData()
  {
    return getRowData();
  }

  public void setWrappedData(Object newRow)
  {
    String rowKey = _rowKey;

    if (rowKey == null)
    {
      throw new IllegalStateException("No current row");
    }
    else
    {      
      _tableData.updateRowData(rowKey, (Map<String, String>)newRow);
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

  private void _initializeRows()
  {
    for (int rowIndex = 0; rowIndex < _INTTIAL_ROW_COUNT; rowIndex++)
    {    
      Map<String, String> newRow = _createFullRow();
      _tableData.appendRowData(newRow);
    }
  }
  
  /**
   * Creates a new full row to handle the insert and initialization cases
   * @return
   */
  private Map<String, String> _createFullRow()
  {
    Map<String, String> emptyMap = Collections.emptyMap();
    
    return _createPartialRow(_ATTRIBUTE_NAMES_ALL, emptyMap);
  }

  /**
   * Creates a (potentially) partial row containing the random values for the specified attributes, not duplicating
   * any values in the no-duplicate map
   */
  private Map<String, String> _createPartialRow(Collection<String> updatedFields, Map<String, String> dontDuplicate)
  {
    Map<String, String> partialRowData = new HashMap<String, String>();

    boolean imageUpdated = false;
    
    // update the changed fields
    for (String updatedField : updatedFields)
    {
      // skip the image field, we will handle it specially, since the value is derived from the price attribute
      if (_ATTRIBUTE_NAME_IMAGE.equals(updatedField))
      {
        imageUpdated = true;
      }
      else
      {
        String oldValue = dontDuplicate.get(updatedField);
        
        // get a new value, not duplicating the old value
        String newValue = _getDifferentValue(_ATTRIBUTE_VALUE_ARRAYS.get(updatedField), oldValue);
        
        partialRowData.put(updatedField, newValue);
      }
    }
    
    // handle updating the price direction image after the new price value has been updated
    if (imageUpdated)
    {
      String oldPrice = dontDuplicate.get(_ATTRIBUTE_NAME_PRICE);
      String newPrice = partialRowData.get(_ATTRIBUTE_NAME_PRICE);

      String priceDirectionUrl = _getPriceDirectionImagePath(oldPrice, newPrice);
      partialRowData.put(_ATTRIBUTE_NAME_IMAGE, priceDirectionUrl);
    }
    
    return partialRowData;
  }
  
  /**
   * Determines the up or down icon to use to show the direction that the price is moving
   * @param oldPriceString
   * @param newPriceString
   * @return
   */
  private String _getPriceDirectionImagePath(String oldPriceString, String newPriceString)
  {
    String imagePath;
    
    if (oldPriceString == null)
    {
      imagePath = "";
    }
    else
    {
      int oldPrice = Integer.parseInt(oldPriceString);
      int newPrice = Integer.parseInt(newPriceString);
 
      imagePath = (newPrice > oldPrice) ?  _UP_IMAGE_PATH : _DOWN_IMAGE_PATH;
    }
    
    return imagePath;
  }

  /**
   * Creates an updated row with random values for the specified fields, merged over the existing row
   * @param rowKey the row index (stringified becomes the row key)
   * @param updatedFields fields to update
   */
  private Map<String, String> _createUpdatedRow(String rowKey, Collection<String> updatedFields)
  {
    Map<String, String> oldRow = _tableData.getRowData(rowKey);
    
    if (oldRow == null)
      throw new IllegalStateException(rowKey + " not in table data");
    
    Map<String, String> partialRow = _createPartialRow(updatedFields, oldRow);

    // Use the old data as the base and then merge the partial row on top of it   
    Map<String, String> updatedRow = new HashMap<String, String>(oldRow);
    updatedRow.putAll(partialRow);
        
    return updatedRow;
  }    

  /**
   * Perform a random active data operation and notify the listeners
   * @return
   */
  private synchronized void _doRandomActiveData()
  {
    ActiveDataUpdateEvent event = _performRandomActiveData();
    
    //System.out.println("Change event:" + event);
    
    _fireActiveDataEvent(event);
  }

  /**
   * Perform a random active data operation and return the event describing the operation.
   * @return
   */
  private ActiveDataUpdateEvent _performRandomActiveData()
  {
    ActiveDataUpdateEvent event;

    // we need to synchronize on the table, since we want to guarantee that the random key we get back is
    // still in the table when we perform the operation
    synchronized (_tableData)
    {
      // get a random key for the table, which might be null
      String affectedKey = _tableData.getRandomKey();
    
      // retrieve an operation that supports the key (for null keys, this will be insert)
      ActiveOperation activeOperation = ActiveOperation.getRandomOperation(affectedKey);            

      //int rowIndex = _tableData.getRowIndex(affectedKey);
      //System.out.println("Change event:" + activeOperation + " at index " + rowIndex);
            
      switch (activeOperation)
      {
        case INSERT:
          event = _insertRow(affectedKey);
          break;
        
        case UPDATE:
          event = _updateRow(affectedKey);
          break;
    
        case DELETE:
          event = _removeRow(affectedKey);
          break;
              
        default:
          throw new IllegalStateException("Unkown case:" + activeOperation);
      }
    }
    
    return event;
  }

  /**
   * Fire an ActiveDataUpdateEvent to all of our listeners
   * @param event
   */
  private void _fireActiveDataEvent(ActiveDataUpdateEvent event)
  {
    // We use a CopyOnWriteArrayList so that we don't have to synchronize event delivery    
    for (ActiveDataListener listener : _listeners)
    {
      _LOGGER.info("Notify listeners on MockActiveDataModel on new data:" + event);
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

  /** Convenince for creating single-entry active data events */
  private ActiveDataUpdateEvent _createActiveDataEvent(ActiveDataEntry entry, int changeCount)
  {
    List<ActiveDataEntry> updateList = Collections.singletonList(entry);
  
    return new MockActiveDataUpdateEvent(this, changeCount, updateList);
  }
  
  /**
   * Update a row in the table and return the ActiveDataEntry
   * @return
   */
  private ActiveDataUpdateEvent _updateRow(String updatedRowKey)
  {    
    // pick attributes to update
    int choice = _getIntBetweenZeroAnd(_CHANGED_ATTRIBUTES.size() - 1);
    
    List<String> attributesToUpdate = _CHANGED_ATTRIBUTES.get(choice);
    
    // update the changed attributes    
    Map<String, String> newRowData = _createUpdatedRow(updatedRowKey, attributesToUpdate);
    
    int changeCount = _tableData.updateRowData(updatedRowKey, newRowData);
    
    // create and return change event
    ActiveDataEntry entry = new MockActiveDataEntry(newRowData, attributesToUpdate, updatedRowKey, _currencyFormatter);
    
    return _createActiveDataEvent(entry, changeCount);
  }

  /**
   * Remove a row from the table and return the ActiveDataEntry
   * @return
   */
  private ActiveDataUpdateEvent _removeRow(String removedRowKey)
  {
    // hanlde case where we remove the current "active" row
    if (removedRowKey.equals(_rowKey))
      _rowKey = null;
    
    int changeCount = _tableData.removeRowByKey(removedRowKey);
        
    // create and return change event
    ActiveDataEntry entry = new MockActiveRemoveDataEntry(removedRowKey);
    
    return _createActiveDataEvent(entry, changeCount);    
  }

  /**
   * Remove a row from the table and return the ActiveDataEntry
   * @return
   */
  private ActiveDataUpdateEvent _insertRow(String insertBeforeKey)
  {     
    // create the row data
    Map<String, String> newRow = _createFullRow();
    
    // we need to synchronize inserting the row and getting the resulting change count to ensure that
    // the change count matches the operation
    int changeCount;
    
    String newRowKey;
    String insertLocationKey;
    boolean insertBefore;
    
    synchronized (_tableData)
    {
      if (insertBeforeKey != null)
      {
        insertLocationKey = insertBeforeKey;
        insertBefore      = true;
      }
      else
      {
        // change from insertBefore to insertAfter in the append case so that we always use a key that the
        // client might have encoded.
        // We need to get the last rowKey before we append the row below
        insertLocationKey = _tableData.getLastRowKey();
        insertBefore      = false;       
      }

      newRowKey = _tableData.insertRowData(insertBeforeKey, newRow);
      
      
      changeCount = _tableData.getChangeCount();
    }
        
    // create and return change event
    ActiveDataEntry entry =  new MockActiveInsertDataEntry(newRow, _ATTRIBUTE_NAMES_ALL, newRowKey,
                                                           insertLocationKey, insertBefore, _currencyFormatter);
    
    return _createActiveDataEvent(entry, changeCount);    
  }
  
  /**
   * Returns a random valu from the specified array, not duplicating the value passed in
   * @param availableValues Array of values to choose from
   * @param dontDuplicate Value to not duplicate when computing the new value
   * @return The random value from availableValues
   */
  private String _getDifferentValue(String[] availableValues, String dontDuplicate)
  {
    String randomValue;
    
    do
    {
      randomValue = availableValues[_getIntBetweenZeroAnd(availableValues.length - 1)];
    } while (randomValue.equals(dontDuplicate));
    
    return randomValue;
  }

  /**
   * Gets a psuedorandom int between zero and the specified upper limit.
   * @param upperLimit the largest int that might be returned
   * @return zero, upperLimit, or any int between zero and upperLimit
   */
  private static int _getIntBetweenZeroAnd(int upperLimit)
  {
    return (int)Math.round(upperLimit * Math.random());
  }

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
      // perform a random active data operation
      ActiveTableData.this._doRandomActiveData();
    }
  }

  /**
   * Implementation of an active data change entry.
   */
   private static class MockActiveDataEntry extends ActiveDataEntry
   {
    /**
     * Setting constructor.
     * @param row the new row's values
     * @param changedAttributes, the attributes that have changed
     * @param rowKey the new row's key
     * @param currencyFormatter formatter to use for prices
     */
    public MockActiveDataEntry(
      Map<String, String> row, Collection<String> changedAttributes, String rowKey, NumberFormat currencyFormatter)
    {
      _row = row;
      _changedAttributes = changedAttributes;
      _rowKey = rowKey;
      _currencyFormatter = currencyFormatter;
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
     * Gets the key path.
     * @return the key path
     */
    @Override
    public Object[] getKeyPath()
    {
      return new String[] {_rowKey};  
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
     * Gets the names of attributes whose values are changed.
     * @return names of attributes whose values are changed
     */
    @Override
    public String[] getAttributeNames()
    {
      String[] changedAttributes = new String[_changedAttributes.size()];
      
      return _changedAttributes.toArray(changedAttributes);
    }

    /**
     * Gets the value of the attribute.
     * @return the value of the attribute
     */
    @Override
    public Object getAttributeValue(String name)
    {
      return _row.get(name);
    }

    /**
     * Gets the formatted value of the attribute.
     * @return the formatted value of the attribute
     */
    @Override
    public Object getFormattedAttributeValue(String name)
    {
      String value = _row.get(name);
      
      if (_ATTRIBUTE_NAME_PRICE.equals(name))
      {
        return _currencyFormatter.format(Long.parseLong(value));
      }
      else
      {
        return value;
      }
    }
    
    @Override
    public String toString()
    {
      return "MockActiveDataEntry " +
             " changeType:" + getChangeType() + 
             " rowKey:" + _rowKey +
             " changedAttributes:" + _changedAttributes;
    }

    private static final long serialVersionUID = 1L;

    private final Map<String, String> _row;
    private final String _rowKey;
    private final Collection<String> _changedAttributes;
    private final NumberFormat _currencyFormatter;
  }

  /**
   * Implementation of an active data insert entry that always inserts before the given key.
   */
  private static class MockActiveInsertDataEntry extends MockActiveDataEntry
  {
    /**
     * Setting constructor.
     * @param row the new row's values
     * @param changedAttributes, the attributes that have changed
     * @param rowKey the new row's key
     * @param insertKey The key to perform the insert relative to
     * @param insertBefore true if the insertion should be before, instead of after the insertKey
     * @param currencyFormatter formatter to use for prices
     */
    public MockActiveInsertDataEntry(
      Map<String, String> row,
      Collection<String> changedAttributes,
      String rowKey,
      String insertKey,
      boolean insertBefore,
      NumberFormat currencyFormatter)
    {
      super(row, changedAttributes, rowKey, currencyFormatter);
      
      if (insertKey == rowKey)
        throw new IllegalArgumentException("Attempting to insert new row:" + rowKey + " relative to itself");
      
      _insertBefore = insertBefore;
      _insertKey    = insertKey;
    }

    /**
     * Gets the ChangeType for this entry.
     * @return ChangeType of this entry
     */
    @Override
    public ChangeType getChangeType()
    {
      return (_insertBefore) ? ChangeType.INSERT_BEFORE : ChangeType.INSERT_AFTER;
    }

    /**
     * Gets the key path of the insert location.
     * @return the key path of the insert location
     */
    @Override
    public Object[] getInsertKeyPath()
    {
      return new Object[]{_insertKey};
    }

    @Override
    public String toString()
    {
      return super.toString() +
             " insert location:" + _insertKey +  
             " insertBefore:" + _insertBefore;
    }
    
    private final String  _insertKey;
    private final boolean _insertBefore;
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
      return new String[] {_rowKey};
    }

    /**
     * Gets the key path of the insert location.
     * @return the key path of the insert location
     */
    @Override
    public Object[] getInsertKeyPath()
    {
      // this is a remove, so no insert path
      return null;
    }

    /**
     * Gets the names of attributes whose values are changed.
     * @return names of attributes whose values are changed
     */
    @Override
    public String[] getAttributeNames()
    {
      // removes don't have any attributes
      return null;
    }

    /**
     * Gets the value of the attribute.
     * @return the value of the attribute
     */
    @Override
    public Object getAttributeValue(String name)
    {
      // removes don't have any attributes
      return null;
    }

    /**
     * Gets the formatted value of the attribute.
     * @return the formatted value of the attribute
     */
    @Override
    public Object getFormattedAttributeValue(String name)
    {
      // removes don't have any attributes
      return null;
    }

    @Override
    public String toString()
    {
      return "MockActiveRemoveDataEntry " +
             " changeType:" + getChangeType() + 
             " rowKey:" + _rowKey;
    }

    private static final long serialVersionUID = 1L;

    private final String _rowKey;
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

      _changeList = Collections.unmodifiableList(changeList);
      _eventId = eventId;
      _eventTime = System.currentTimeMillis();;
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

    @Override
    public long getEventTime() 
    {
      return _eventTime;
    }

    @Override
    public String toString()
    {
      return "MockActiveDataUpdateEvent " + 
             " event Id:" + _eventId +
             " event time:" + _eventTime +
             " changes:" + _changeList;
    }

    private static final long serialVersionUID = 1L;

    private final List<ActiveDataEntry> _changeList;
    private final int _eventId;
    private final long _eventTime;
  }

  private static final ADFLogger _LOGGER = ADFLogger.createADFLogger(ActiveTableData.class);

  private static final int _INTTIAL_ROW_COUNT = 8;

  private static final String _ATTRIBUTE_NAME_DESCRIPTION = "description";
  private static final String _ATTRIBUTE_NAME_PRICE = "price";
  private static final String _ATTRIBUTE_NAME_DETAIL = "detail";
  private static final String _ATTRIBUTE_NAME_IMAGE = "image";

  // map of attribute name to available values for randomization
  private static final Map<String, String[]> _ATTRIBUTE_VALUE_ARRAYS = new HashMap<String, String[]>();

  // List of possible changed attributes
  private static final List<List<String>> _CHANGED_ATTRIBUTES = new ArrayList<List<String>>();

  // since the image is derived from price, changing the price, implicitly changes the image
  private static final List<String> _ATTRIBUTE_NAMES_ALL = 
                              Arrays.asList(_ATTRIBUTE_NAME_DESCRIPTION, _ATTRIBUTE_NAME_PRICE,
                                            _ATTRIBUTE_NAME_DETAIL, _ATTRIBUTE_NAME_IMAGE);
  
  private static final String _DOWN_IMAGE_PATH = "/images/accordion_arrow_down.png";
  private static final String _UP_IMAGE_PATH = "/images/accordion_arrow_up.png";

  // create daemon Timer
  private static final Timer _TIMER = new Timer(true);
  
  private final static String _DEMO_NAME_TABLE = "table";
  private final static String _DEMO_NAME_TREE = "tree";
  private final static String _DEMO_NAME_TREETABLE = "treeTable";

  static
  {
    // build the sets of data to randomize
    String[] details = {
        "<strong>Text</strong>",
        "Read only <strong>text</strong>",
        "<strong>Text text</strong>",
        "<strong>Text</strong>",
        "<strong>Text</strong> only",
        "Read only <em>data</em>",
        "<em>Data</em> <strong>text</strong>",
        "<em>Data</em>"
      };

    String[] prices = {
        "78000",
        "53000",
        "21000",
        "583000",
        "1000000",
        "1200",
        "336981",
        "209000"
      };

    String[] descriptions = {
      "Some <strong>text</strong> to <em>describe</em> the object",
      "Some <strong>text</strong> <em>describing</em> the object",
      "Some <strong>text</strong>",
      "<strong>Text</strong> <em>describing</em> the object",
      "<em>Description</em>",
      "A very long <em>description</em> that may be truncated",
      "Another <em>Description</em>",
      "Some more <strong>text</strong>"
    };
      
    _ATTRIBUTE_VALUE_ARRAYS.put(_ATTRIBUTE_NAME_DETAIL, details);
    _ATTRIBUTE_VALUE_ARRAYS.put(_ATTRIBUTE_NAME_PRICE, prices);
    _ATTRIBUTE_VALUE_ARRAYS.put(_ATTRIBUTE_NAME_DESCRIPTION, descriptions);
    
    // build the sets of changed properties to switch between
    // Since Image is derived from price, we always add image wherever price is specified
    List<String> descriptionAndPriceAttributes =
                              Arrays.asList(_ATTRIBUTE_NAME_DESCRIPTION, _ATTRIBUTE_NAME_PRICE, _ATTRIBUTE_NAME_IMAGE);


    List<String> descriptionAndDetailAttributes =
                                Arrays.asList(_ATTRIBUTE_NAME_DESCRIPTION, _ATTRIBUTE_NAME_DETAIL);
      
    List<String> priceAndDetailAttributes =
                                Arrays.asList(_ATTRIBUTE_NAME_PRICE, _ATTRIBUTE_NAME_DETAIL, _ATTRIBUTE_NAME_IMAGE);
                                  
    List<String> descriptionOnlyAttribute = Collections.singletonList(_ATTRIBUTE_NAME_DESCRIPTION);
  
    List<String> priceOnlyAttribute = Arrays.asList(_ATTRIBUTE_NAME_PRICE, _ATTRIBUTE_NAME_IMAGE);
  
    List<String> detailOnlyAttribute = Collections.singletonList(_ATTRIBUTE_NAME_DETAIL);
    
    Collections.addAll(_CHANGED_ATTRIBUTES,
                       descriptionAndPriceAttributes, descriptionAndDetailAttributes,
                       priceAndDetailAttributes, descriptionOnlyAttribute,
                       priceOnlyAttribute, detailOnlyAttribute,
                       _ATTRIBUTE_NAMES_ALL);
  }

  /**
   * Class managing thread-safe manipulation of the data for this table and the associated change counts.
   */
  private static class TableData
  {
    public synchronized String getRowKey(int index)
    {
      if (index < 0)
      {
        return null;
      }
      else
      {
        int size = getSize();
        
        if (index >= size)
        {
          return null;       
        }
      }
      
      return _rowKeys.get(index);
    }

    public synchronized int getRowIndex(String key)
    {
      return _rowKeys.indexOf(key);
    }

    /**
     * Returns a random key, currently in this table data, or null.  If the table is empty, this will always return
     * null.  If the caller needs to guarantee that the key exists in the table during some follow up operation, the
     * caller needs to put bith the retrieval and the manipulation in the same sycnrhonization block.
     */
    public synchronized String getRandomKey()
    {
      int rowCount = getSize();
      
      if (rowCount == 0)
        return null;
      
      int index =  _getIntBetweenZeroAnd(rowCount);
      
      // replace values past the end with null
      if (index == rowCount)
        return null;
      
      
      return _rowKeys.get(index);
    }
    
    public synchronized String getLastRowKey()
    {
      List<String> rowKeys = _rowKeys;
      
      int size = rowKeys.size();
      
      if (size == 0)
      {
        return null;
      }
      else
      {
        return rowKeys.get(size - 1);
      }
    }

    public synchronized int getSize()
    {
      return _rowKeys.size();
    }
    
    public synchronized boolean containsKey(String key)
    {
      return _rowKeys.contains(key);
    }

    /**
     * Returns a shallow copy of the data for the specified key, or null if there is no data
     * @param key
     * @return
     */
    public synchronized Map<String, String> getRowData(String key)
    {
      Map<String, String> rowData = _tableData.get(key);
      
      if (rowData != null)
      {
        // copy the data before returning it
        return new HashMap<String, String>(rowData);
      }
      else
      {
        return null;
      }
    }
    
    /**
     * Returns the current change count
     * @return
     */
    public synchronized int getChangeCount()
    {
      return _changeCount;
    }

    public synchronized int updateRowData(String key, Map<String, String> data)
    {
      if (!containsKey(key))
      {
        throw new IllegalStateException("Update key:" + key + " does not exist in table data");
      }
      
      _tableData.put(key, data);
      
      _changeCount++;
      
      return _changeCount;
    }

    /**
     * Appends the data to the table, returning the rowKey for the newly inserted data
     * 
     * If the caller also needs the
     * change count for this change, the caller needs to sycnhronize on the TableData instance around the insertion
     * and the retrieval of the resulting changeCount.
     *
     * @param data
     * @return
     * @see #getChangeCount
     */
    public synchronized String appendRowData(Map<String, String> data)
    {
      // calculate the new key and update the change count
      String insertedKey = _getInsertedRowKey();

      // append to the end of the list
      _rowKeys.add(insertedKey);
      
      // update the data
      _tableData.put(insertedKey, data);
      
      return insertedKey;
    }

    /**
     * Inserts the new row data before the specified key, returning the new key.
     * 
     * If the caller also needs the
     * change count for this change, the caller needs to sycnhronize on the TableData instance around the insertion
     * and the retrieval of the resulting changeCount.
     * 
     * @param beforeKey Key to insert before, or null to append
     * @param data
     * @return The key of the newly inserted row
     * @see #getChangeCount
     */
    public synchronized String insertRowData(String beforeKey, Map<String, String> data)
    {      
      // before null, means append
      if (beforeKey == null)
      {
        return appendRowData(data);
      }
      else
      {
        String insertedKey = _getInsertedRowKey();
 
        // insert the new row at the correct index
        int insertIndex = getRowIndex(beforeKey);
        
        if (insertIndex == -1)
        {
          throw new IllegalStateException("Update key:" + beforeKey + " does not exist in table data");
        }
         
        _rowKeys.add(insertIndex, insertedKey);
       
        // add the new data
        _tableData.put(insertedKey, data);
        
        return insertedKey;
      }
    }

    /**
     * Returns the next rowKey to use.  We use the changeCount in order to avoid reusing keys after inserts
     * and removals
     * @return
     */
    private synchronized String _getInsertedRowKey()
    {
      _changeCount++;
      
      return String.valueOf(_changeCount);
    }

    /**
     * Removes a row, returning the new changeCount
     * @param key
     * @return
     */
    public synchronized int removeRowByKey(String key)
    {
      // remove the ordered key entry
      boolean removed = _rowKeys.remove(key);
      
      if (!removed)
        throw new IllegalStateException("Unknown key:" + key);

      // remove the data
      _tableData.remove(key);

      _changeCount++;
      
      return _changeCount;
    }

    // ordered list of keys for which we have data
    private final List<String> _rowKeys = new ArrayList<String>();

    // map of keys to Maps representing row key-value data
    private final Map<String, Map<String, String>> _tableData = new HashMap<String, Map<String, String>>();
    
    private int _changeCount = 0;
  }
  
  private final TableData _tableData = new TableData();
  
  // use a CopyOnWriteArrayList so that we don't have to synchronize event delivery
  private final List<ActiveDataListener> _listeners = new CopyOnWriteArrayList<ActiveDataListener>();
  private final long _interval;
  private final NumberFormat _currencyFormatter;
  
  private volatile String _rowKey;
  private volatile String _activeDataDemoFacetName = _DEMO_NAME_TABLE;

  private volatile ValueUpdater _timerTask;
  
}

