/* Copyright (c) 2009, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.table.rich.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import oracle.adf.view.rich.event.ActiveDataListener;
import oracle.adf.view.rich.model.ActiveDataModel;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeyPropertyTreeModel;


/**
 * Implementation of an updateable TreeModel which inserts/updates/removes
 * rows in underlying Java Lists.  The insert/update/delete operations
 * apply to each child collection. So in order to mutate a child collection,
 * first navigate to the child collection by calling the TreeModel navigation APIs
 * (<code>setRowKey, setRowIndex, enterContainer, exitContainer</code>) then call the
 * <code>UpdateableModel</code> APIs to insert/update/delete rows.
 * The model also implements <code>ActiveDataModel</code> with
 * <code>ActiveDataPolicy.PPR</code> and fires <code>ActiveDataEvent</code>
 * in response to insert/update/delete operations
 * @param <T> type of the wrapped model (must implement java.util.List)
 */
public class UpdateableTreeModel<T extends List>
  extends RowKeyPropertyTreeModel
  implements ActiveDataModel, UpdateableModel

{
  public UpdateableTreeModel(T model, String childProperty,
                             String rowKeyProperty)
  {
    super();
    setChildProperty(childProperty);
    this.setRowKeyProperty(rowKeyProperty);
    setWrappedData(_createChildModel(model));    
  }


  /**
   * Overrides ChildPropertyTreeModel.createChildModel(). 
   * Converts childData into a UpdateableCollectionModel.
   * 
   * @param childData the data to convert. This can be a List or array.
   */
  @Override
  protected CollectionModel createChildModel(Object childData)
  {
    UpdateableCollectionModel<T> childModel = _createChildModel((T)childData);
    childModel.setRowIndex(-1);
    return childModel;
  }
  
  /**
   * Return the child model for the current row (siblings of the current row)
   * @return
   */
  public UpdateableModel getChildModel()
  {
    return _getChildModel();
  }
  
  /**
   * Return the child model for the row indentified by rowKey (siblings of the row 
   * identified by rowKey)
   * @return
   */
  public UpdateableModel getChildModel(Object rowKey)
  {
    Object oldKey = getRowKey();
    UpdateableModel childModel = null;
    try
    {
      setRowKey(rowKey);
      if (isRowAvailable())
        childModel = _getChildModel();
    }
    finally
    {
      setRowKey(oldKey);
    }
    return childModel;
  }
  
  /**
   * Insert a row before the current row in the current child model.
   * To insert a row, call <code>setRowKey(rowKey)</code> to move to the desired row in a child model
   * (insertion point) and call <code>insertRow()</code>
   * @param rowData
   * @return
   */
  public void insertRow(Object rowData)
  {
    _getChildModel().insertRow(rowData);
  }

  /**
   * Insert a row before the row with the given rowKey in the current child model.
   * To insert a row in a child model, call <code>setRowKey(rowKey)</code> to move to the child model and call 
   * <code>insertRow()</code>
   * @param rowData
   * @param rowKey the row key for the insertion point in the current child model.  If this 
   * parameter is null,  the new row is appended to the current child model
   * @return
   */
  public void insertRow(Object rowKey, Object rowData)
  {
    _getChildModel().insertRow(rowKey == null ? null : _getChildKey((List)rowKey), rowData);
  }
  

  /**
   * Delete the "current" row in  the current child model.  To delete a row, call 
   * <code>setRowKey()</code> to move to the row in a given child model followed by
   * <code>deleteRow(</code>
   */
  public void deleteRow()
  {
    _getChildModel().deleteRow();
  }

 
  /**
   * Delete the row with the given rowKey in  the current child model.  To delete in a child model
   * a row, call <code>setRowKey()</code> to the child model followed by
   * <code>deleteRow(</code>
   */
   public void deleteRow(Object rowKey)
  {
    _getChildModel().deleteRow(_getChildKey((List)rowKey));
  }

  /**
   * Update the current row in the current child model.  To update a row,
   * call <code>setRowKey()</code> to move to a row in a child model,  followed by
   * <code>updateRow()</code>
   * @param rowData
   */
  public void updateRow(Object rowData)
  {
    _getChildModel().updateRow(rowData);
  }

  /**
   * Update the given row row in the current child model.  To update a row,
   * call <code>setRowKey()</code> to move to a child model,  followed by
   * <code>updateRow()</code>
   * @param rowData
   */
  public void updateRow(Object rowKey, Object rowData)
  {
    _getChildModel().updateRow(_getChildKey((List)rowKey), rowData);
  }


  //
  // ActiveDataModel APIs
  //

  public int getCurrentChangeCount()
  {
    return 0;
  }

  public ActiveDataModel.ActiveDataPolicy getActiveDataPolicy()
  {
    return ActiveDataModel.ActiveDataPolicy.PPR;
  }

  public void stopActiveData(Collection<Object> rowKeys,
                             ActiveDataListener listener)
  {
    _adListener = null;
  }

  public void startActiveData(Collection<Object> rowKeys,
                              int startChangeCount,
                              ActiveDataListener listener)
  {
    _adListener = listener;
    
    if (_adListener != null)
    {
      UpdateableCollectionModel<T> wrappedData = (UpdateableCollectionModel<T>)getWrappedData();
      if (wrappedData != null)
      {
        wrappedData.startActiveData(null, 0, _adListener);
      }
    }
  }


  private Object _getChildKey(List path)
  {
    return path.get(path.size() - 1);
  }

  /**
   * This wraps the "current" child collection with an UpdateableCollectionModel if necessary
   * @return
   */
  private UpdateableModel _getChildModel()
  {
    Object rowKey = getRowKey();
    
    try
    {
      int index = getRowIndex();
      if (null == rowKey)  
        return (UpdateableCollectionModel<T>)getWrappedData();
      else
      {
        Object parentKey = getContainerRowKey(rowKey);
        if (null == parentKey)
          return (UpdateableCollectionModel<T>)getWrappedData();
        else
        {
          setRowKey(parentKey);
          Object parentRow =  getRowData();
          Object childData = getChildData(parentRow);
          UpdateableModel childModel = null;
          if (childData != null)
          {
            childModel = _createChildModel((T)childData);
            ((CollectionModel)childModel).setRowIndex(index);
          }
          return childModel;
        }
      }
    }
    finally
    {
      setRowKey(rowKey);      
    }
  }

  /**
   * Create an UpdateableCollectionModel for the "current" childData in the tree.
   * Override the keyPath methods to return key paths specific for the tree.
   * Also hook up the ActiveData listener for the child model
   * @param childData
   * @return
   */
  private UpdateableCollectionModel<T> _createChildModel(T childData)
  {
    UpdateableCollectionModel<T> childModel =
      new UpdateableCollectionModel<T>(childData, getRowKeyProperty())
    {
      /**
       * Return the key path for the rowKey.  The "current"
       * node should be a sibling of the rowKey
       */
      @Override
      protected Object getKeyPath(Object rowKey)
      {
        Object currKey = UpdateableTreeModel.this.getRowKey();        
        List<Object> path = 
          new ArrayList<Object>((List<Object>)currKey);
        
        path.set(path.size()-1, rowKey);
        return path;
      }
      
      /**
       * Return the path for the "current" node
       */
      @Override
      protected Object getKeyPath()
      {
        return UpdateableTreeModel.this.getRowKey();
      }
      
    };
    if (null != _adListener)
    {
      childModel.startActiveData(null, 0, _adListener);
    }
    return childModel;
  }
  
  private ActiveDataListener _adListener;
}
