/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/data/PivotFilterBarListOfValuesModel.java /main/3 2011/12/23 08:32:06 jayturne Exp $ */

/* Copyright (c) 2009, 2011, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ccchow      05/05/09 - remove unused methods
    ccchow      05/01/09 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/data/PivotFilterBarListOfValuesModel.java /main/2 2010/05/21 12:49:31 jayturne Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotFilterBar.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.ColumnDescriptor;
import oracle.adf.view.rich.model.ListOfValuesModel;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;
import oracle.adf.view.rich.model.TableModel;

import oracle.dss.util.DataAccess;
import oracle.dss.util.DataDirector;
import oracle.dss.util.DataException;
import oracle.dss.util.LayerMetadataMap;
import oracle.dss.util.MetadataMap;

import org.apache.myfaces.trinidad.logging.TrinidadLogger;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

/**
 * This ListOfValuesModel implementation is used by the AttributeDescriptor implementation.
 * It supplies the list of values for each of the PivotFilterBar's filters, and keeps track
 * of the currently selected value of each filter. To accomplish this, this class interacts
 * directly with the DataAccess and DataDirector instances of the data model.
 */
public class PivotFilterBarListOfValuesModel extends ListOfValuesModel
{
    private static final String DUMMY_COLUMN_NAME = "dummy";
    private static final int WIDTH = 100;
    
    private DataAccess m_dataAccess;
    private DataDirector m_dataDirector;
    private int[] m_depthIndex;
    private int m_depth;
    private Object m_selected;

    //the number of members at this layer
    private int m_itemCount = 0;
    //cache of member metadata
    private Object[] m_members;
    //flags indicating what members have been cached
    private boolean[] m_membersCached;

    private TableModel m_tableModel;
    private List m_list;

    private List m_selectItems;
    
    public PivotFilterBarListOfValuesModel(DataDirector dataDirector, DataAccess dataAccess, int[] depthIndex, int depth)
    {
        m_dataDirector = dataDirector;
        m_dataAccess = dataAccess;
        m_depthIndex = depthIndex;
        m_depth = depth;
        
        //initialize and save the item count at construction 
        //time to avoid possible synchronization problems when looking for an old 
        //value during an update after the data has changed (the getItemCount
        //method used to always call the DataAccess)
        
        //If the hIndex at this physical layer is -1, then there are no members
        //  for this layer.  
        if (m_depthIndex[m_depth] != -1)
        {
            try 
            {
                m_itemCount = m_dataAccess.getMemberSiblingCount(DataDirector.PAGE_EDGE, m_depthIndex, m_depth);

                int[] hpos = m_dataAccess.getEdgeCurrentHPos(DataDirector.PAGE_EDGE);
                int _current = hpos[m_depth];
                Object _label = m_dataAccess.getMemberMetadata(DataDirector.PAGE_EDGE, m_depthIndex, m_depth, _current, MetadataMap.METADATA_LONGLABEL);

                PivotFilterBarItemKey _key = new PivotFilterBarItemKey();
                _key.setIndex(_current);
                _key.setLabel(_label.toString());
                
                HashMap _map = new HashMap(1);
                _map.put(DUMMY_COLUMN_NAME, _key);
                m_selected = _map;
            }
            catch (DataException e)
            {
                _LOG.warning(e);
            }
        }
        
        //BUG FIX #2430300: make sure that the item count is >= 0
        if (m_itemCount < 0)
            m_itemCount = 0;
        
        //BUG FIX #2345062: cache member metadata to avoid possible synchronization 
        //problems when looking for an old value during an update after the data 
        //has changed (the getData method used to always call the DataAccess)
        
        //member metadata cache
        m_members = new Object[m_itemCount];
        //flags indicating if a member has been cached yet
        m_membersCached = new boolean[m_itemCount];
        for (int i = 0; i < m_itemCount; i++)
        {
            m_members[i] = null;
            m_membersCached[i] = false;
        }
    }

    public Object getSelected()
    {
        if (m_selected == null)
            return null;
        else
            return ((HashMap)m_selected).get(DUMMY_COLUMN_NAME);
    }
    
    public void setSelected(Object obj)
    {
        if (obj instanceof PivotFilterBarItemKey)
        {
            PivotFilterBarItemKey _key = (PivotFilterBarItemKey)obj;
            int _index = _key.getIndex();

            try
            {
                int[] hpos = m_dataAccess.getEdgeCurrentHPos(DataDirector.PAGE_EDGE);
                hpos[m_depth] = _index;
                
                m_dataDirector.changeEdgeCurrentHPos(DataDirector.PAGE_EDGE, hpos, m_depth);
            }
            catch (Exception ex)
            {
                _LOG.warning(ex);
            }            
    
            HashMap _map = new HashMap(1);
            _map.put(DUMMY_COLUMN_NAME, obj);
            m_selected = _map;
        }
    }
    
    public QueryDescriptor getQueryDescriptor()
    {
        return null;
    }
    
    public QueryModel getQueryModel()
    {
        return null;
    }
    
    public TableModel getTableModel()
    {
        if (m_tableModel == null)
            m_tableModel = new PageItemTableModel();
        
        return m_tableModel;
    }

    public boolean isAutoCompleteEnabled()
    {
        return false;
    }

    /**
     * Returns the value of the component given the selectedRow from the
     * table of the dropdown or a selectedRowKey from the search and select
     * dialog, without pushing the selected value into the model. To indicate
     * that a row has been selected, the method valueSelected() will be called.
     *
     * This method is typically called during the Apply Request Values phase.
     * @param selectedRow
     * @return value corresponding to the lov component
     */
    @Override    
    public Object getValueFromSelection(Object selectedRow)
    {
        HashMap _selected = null;
        if (selectedRow instanceof RowKeySetImpl)
        {
            RowKeySetImpl _set = (RowKeySetImpl)selectedRow;
            Object _rowKey = _set.iterator().next();
            CollectionModel _model = getTableModel().getCollectionModel();
            _model.setRowKey(_rowKey);
            Object _obj = _model.getRowData();
            _selected = (HashMap)_obj;
        }
        else if (selectedRow instanceof List)
        {
            Object _obj = ((List)selectedRow).get(0);
            _selected = (HashMap)_obj;
        }        

        return _selected.get(DUMMY_COLUMN_NAME);
    }

    public void performQuery(QueryDescriptor descriptor)
    {        
    }
    
    /**
    * Called at the end of the Invoke Application phase.
    *
    * This method sets up the model such that results/related fields
    * could show the relevant data. Also, this method results in
    * value to be added to the most-recently-used or favorites list.
    */
    @Override    
    public void valueSelected(Object selected)
    {
    }
    
    public List getItems()
    {
        if (m_list == null)
        {
            m_list = new ArrayList();
            for (int i = 0; i < this.m_itemCount; i++)
            {
                m_list.add(getRowData(i));
            }
        }
        
        return m_list;
    }

    public List getSelectItems()
    {
        if (m_selectItems == null)
            m_selectItems = new SelectItemList();
        
        return m_selectItems;
    }

    public List getRecentItems()
    {
        return null;
    }
    
    public List autoCompleteValue(Object value)
    {
        return null;
    }

    private Object getRowData(int index)
    {
        Object obj = null;
        
        //BUG FIX #2430300: if the cache is null or length 0, just return null
        
        //If the hIndex at this physical layer is -1, then there are no members
        //  for this layer.  
        if (m_depthIndex[m_depth] != -1 &&
            m_membersCached != null &&
            m_membersCached.length > 0)
        {
            //BUG FIX #2345062: if the member has been cached, return it; otherwise 
            //get it from the DataAccess and cache it
            //BUG FIX #2430300: make sure the index is valid
            if (index >= 0 && 
                index < m_membersCached.length &&
                m_membersCached[index])
            {
                obj = m_members[index];
            }
            else
            {
            
                try 
                {
                    obj = m_dataAccess.getMemberMetadata(DataDirector.PAGE_EDGE, m_depthIndex, m_depth, index, MetadataMap.METADATA_LONGLABEL);
                }
                catch (DataException e)
                {
                    _LOG.warning(e);
                }

                //BUG FIX #2430300: make sure the index is valid
                if (index >= 0 && index < m_membersCached.length)
                {
                    PivotFilterBarItemKey _key = new PivotFilterBarItemKey();
                    _key.setIndex(index);
                    _key.setLabel(obj.toString());

                    // TODO: Put indent info in Key so the label can be render correctly
                    
                    HashMap _map = new HashMap(1);
                    _map.put(DUMMY_COLUMN_NAME, _key);
                    obj = _map;
                    //store the metadata in the cache
                    m_members[index] = obj;
                    //mark the flag to indicate that this member has been cached
                    m_membersCached[index] = true;
                }
            }
        }
        
        return obj;
    }
    
    class SelectItemList extends ArrayList
    {
        public int size()
        {
            return m_itemCount;
        }
        
        public Object get(int index)
        {
            HashMap _map = (HashMap)getRowData(index);
            PivotFilterBarItemKey _key = (PivotFilterBarItemKey)_map.get(DUMMY_COLUMN_NAME);
            
            SelectItem _item = new SelectItem();
            _item.setLabel(_key.getLabel());
            _item.setValue(_key);
            
            return _item;
        }
    }

    class ColumnDescriptorImpl extends ColumnDescriptor
    {
        private String _label = null;
        
        public AttributeDescriptor.ComponentType getComponentType()
        {
            return AttributeDescriptor.ComponentType.inputComboboxListOfValues;
        }
        
        public String getDescription()
        {
            return getLabel();
        }
        
        public String getFormat()
        {
            return null;
        }
        
        public String getLabel()
        {
            if (_label == null)
            {
                try
                {
                    _label = (String)m_dataAccess.getLayerMetadata(DataDirector.PAGE_EDGE, m_depth, LayerMetadataMap.LAYER_METADATA_LONGLABEL);
                }
                catch (Exception ex)
                {
                    _LOG.warning(ex);                  
                }
            }
            
            return _label;
        }
        
        public int getWidth()
        {
            return WIDTH;
        }
        
        public int getLength()
        {
            return WIDTH;
        }
        
        public int getMaximumLength()
        {
            return WIDTH;
        }
        
        public Object getModel()
        {
            return PivotFilterBarListOfValuesModel.this;
        }
        
        public String getAlign()
        {
            return "center";
        }
        
        public String getName()
        {
            return DUMMY_COLUMN_NAME;
        }
        
        public Class getType()
        {
            return Object.class;
        }
        
        public boolean isReadOnly()
        {
            return false;
        }
        
        public boolean isRequired()
        {
            return false;
        }
        
        public Set<AttributeDescriptor.Operator> getSupportedOperators()
        {
            return null;   
        }
    }
    
    class PageItemCollectionModel extends CollectionModel
    {
        private int m_current = -1;
        
        public int getRowCount()
        {
            return m_itemCount;
        }
        
        public void setRowIndex(int index)
        {
            m_current = index;
        }
        
        public int getRowIndex()
        {
            return m_current;
        }
        
        public boolean isRowAvailable()
        {
            return (m_current != -1 && m_current < m_itemCount);
        }
        
        public Object getRowData(int rowIndex)
        {
            if (rowIndex > -1 && rowIndex < m_itemCount)
                return PivotFilterBarListOfValuesModel.this.getRowData(rowIndex);
            else
                return null;
        }

        public Object getWrappedData()
        {
            return getItems();
        }
        
        public void setWrappedData(Object data)
        {
        }
        
        public Object getRowData()
        {
            return getRowData(m_current);
        }
        
        public Object getRowKey()
        {
            if (isRowAvailable())
                return getRowIndex();
            else
                return null;
        }        

        public void setRowKey(Object rowKey)
        {
            setRowIndex(_toRowIndex(rowKey));            
        }

        private int _toRowIndex(Object rowKey)
        {
            if (rowKey == null)
                return -1;
            
            try
            {
                return ((Integer)rowKey).intValue();
            }
            catch (ClassCastException e)
            {
                _LOG.warning(e);
                return -1;
            }
        }
    }

    class PageItemTableModel extends TableModel
    {
        private CollectionModel _collectionModel;
        private List<ColumnDescriptor> _descriptors;
        
        public CollectionModel getCollectionModel()
        {
            if (_collectionModel == null)
                _collectionModel = new PageItemCollectionModel();
            
            return _collectionModel;
        }

        public List<ColumnDescriptor> getColumnDescriptors()
        {
            if (_descriptors == null)
            {
                _descriptors = new ArrayList<ColumnDescriptor>(1);
                _descriptors.add(new ColumnDescriptorImpl());
            }
            return _descriptors;
        }        
    }

    static private final TrinidadLogger _LOG = TrinidadLogger.createTrinidadLogger(PivotFilterBarListOfValuesModel.class);
}
