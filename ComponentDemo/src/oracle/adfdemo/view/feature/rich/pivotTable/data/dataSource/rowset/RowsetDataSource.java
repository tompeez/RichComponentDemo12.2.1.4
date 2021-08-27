/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/RowsetDataSource.java /main/6 2011/06/06 06:08:51 jievans Exp $ */

/* Copyright (c) 2006, 2011, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
      jievans   12/23/10 - bug 8577899: sorting broken for edited columns
      ccchow    01/19/10 - sync updated data with original data before sort
      dahmed    08/12/08 - 
      bmoroze   04/24/08 - 
      dahmed    01/21/08 - 
      bmoroze   11/16/07 - 
 */
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.rowset;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

import java.util.Set;

import javax.el.ELContext;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


import oracle.adf.model.dvt.util.transform.RowProjection;

import oracle.adfinternal.model.dvt.util.transform.ResultTable;
import oracle.adfinternal.model.dvt.util.transform.RowBasedCubicDataAccess;
import oracle.adfinternal.model.dvt.util.transform.total.AggSpec;
import oracle.adfinternal.view.faces.bi.renderkit.pivotTable.RichPivotTableRenderer;

import oracle.dss.util.DataDirector;
import oracle.dss.util.DataDirectorListener;
import oracle.dss.util.DataAvailableEvent;
import oracle.dss.util.DataAccessAdapter;
import oracle.dss.util.DataMap;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.CubeDataAccess;
import oracle.dss.util.CubeDataDirector;
import oracle.dss.util.DataAccess;
import oracle.dss.util.DataDirectorException;
import oracle.dss.util.DataException;
import oracle.dss.util.DataSource;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerMetadataMap;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.QDR;
import oracle.dss.util.QDRMember;
import oracle.dss.util.RelationalDataAccess;
import oracle.dss.util.RelationalDataDirector;
import oracle.dss.util.QDRSliceSortInfo;
import oracle.dss.util.SortInfo;


import org.apache.myfaces.trinidad.model.CollectionModel;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/RowsetDataSource.java /main/4 2010/01/23 19:03:14 ccchow Exp $
 *  @author  dahmed  
 *  @since   release specific (what release of product did this appear in)
 */
public class RowsetDataSource extends DataAccessAdapter implements CubeDataDirector, CubeDataAccess, RelationalDataDirector, RelationalDataAccess, DataSource
{                           
    // How this data source works, particularly for values edited by the user:
    //
    // m_data is the rowset initially passed in by the app, from which the DA is created.  We update m_data with the edited values, but not 
    // necessarily immediately--see below.  m_data can be Object[][] anytime, and can be CollectionModel if sorting is turned off.  
    // (moveUpdatedValuesToRowset() does not support CollectionModel.  This method is used only for sorting.  I believe none of our demos 
    // currently use CollectionModel.  TBD: fully support CollectionModel too if desired.)
    //
    // m_updatedValues is a map from quasi-QDR to edited value, which contains all the values edited by the user that haven't yet been added to 
    // the wrapped DA.  (Specifically, "quasi-QDR" means a set of stringified QDR entries like "Product:DVD".) 
    //
    // The DA (m_da) is a RowsetDataAccessDecorator, which wraps a RowBasedCubicDataAccess (m_cubicDA), which is created from m_data.  We have the 
    // decorator DA in order to support edited values without having to recreate the cubic DA on every single user edit.  m_da's setValue() method 
    // puts the value in the map (m_updatedValues), and its getValue() returns the updated value from the map if present, and calls the wrapped DA's 
    // getValue method otherwise.
    //
    // Even though this class (RowsetDataSource) implements DataAccess, it is not the DA, and doesn't override any of the dummy implementations in 
    // the DataAccessAdaptor superclass.  
    //
    // What needs to happen after a value is edited:
    //
    // - If the wrapped DA is not rebuilt immediately after the edit (see subsequent bullet), the new value must be placed in m_updatedValues so the 
    //   decorator DA will use it for future getValue calls.  Decorator DA's setValue() does this.
    // - The filter mins and maxes must be updated.  valueChanged() does this.  This is only needed if the app uses them, and can wait until the next 
    //   time the app needs them to be current.  So we don't call this method; we just expose it so the app can call it as needed, e.g. in a valueChanged listener.  
    // - The wrapped DA must be rebuilt with the new value so sorting will work.  Since sorting is the only thing that requires the *wrapped* DA to know 
    //   about the edited values, we can wait until the next sort (the next call to setSorts) to do this, for efficiency.  See following lists.
    //
    // What needs to happen when the wrapped DA is rebuilt to contain all edited values:
    //
    // - Put all updated values in m_data first.  This is expensive since it requires walking m_data, and the DA rebuild is the only thing that uses 
    //   m_data, so this can and should wait until the DA is rebuilt.
    // - Clear m_updatedValues, since the wrapper DA doesn't need the values if they're already in the wrapped DA, and since non-emptiness of this map 
    //   is a sentinel that the DA needs rebuilding before the next sort.
    //
    // What needs to happen when a sort happens (when setSorts() is called) :
    //
    // - If the wrapped DA hasn't been rebuilt since the last edit, i.e. if m_updatedValues is non-empty, then rebuild the wrapped DA (and all its 
    //   requirements--see above list).  setSorts, not some other method, must do this, since setSorts is a public method.  
    //
    // TODO: Many members of this class are public but probably shouldn't be.    
    
    public RowsetDataSource()
    {                 
    }                 

    public void setData(Object data) {
        m_data = data;
    }

    public Object getData() {
        return m_data;
    }

    public HashMap<String,FilterSpec> getFilterSpecs() {
        return m_filterSpecs;
    }
    
    public void setFilterSpecs(HashMap<String,FilterSpec> filterSpecs) {
        m_filterSpecs = filterSpecs;
    }

    public Object[][] fetchData() {
        HashMap<String,FilterSpec> filters = m_filterSpecs;
        ArrayList<Object[]> rows = new ArrayList<Object[]>();
        if(m_data instanceof CollectionModel)
        {
            CollectionModel model = (CollectionModel)m_data;

            FacesContext context = FacesContext.getCurrentInstance();
            ELContext elContext = context.getELContext();
            context.getExternalContext().getRequestMap().put("dvt_sample_collectionModel", model);
            
            String[] columns = getColumns();
            int rowCount = model.getRowCount();
            int colCount = columns.length;
            for(int r=0;r<rowCount;r++)
            {
                Object[] row = new Object[colCount];
                for(int c=0;c<colCount;c++) {
                    String elExpr = "#{request.dvt_sample_collectionModel." + m_columns[c] + "}";
                    row[c] = elContext.getELResolver().getValue(elContext, null, elExpr);                                        
                }
                if(!filterRow(filters, row))
                  rows.add(row);                
            }
        } else {
            Object[][] data = (Object[][])m_data;
            for(int r=0;r<data.length;r++) 
            {
                Object[] row = data[r];
                if(!filterRow(filters, row))
                  rows.add(row);
            }   
        }
        if(rows.size()==0)
        {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"No data selected.  Please modify the rowset datasource filters in order to display more data.",""));
        }

        return rows.toArray(new Object[rows.size()][]);
    }

    protected boolean filterRow(HashMap<String,FilterSpec> filterSpecs, Object[] row) {
        boolean bFilterRow = false;
        
        for (FilterSpec filter : filterSpecs.values()) {
            if(!filter.includeRow(row))
            {
                bFilterRow = true;
                break;
            }
        }
        return bFilterRow;
    }
    
    public FilterSpec getFilterSpec(String dataAttribute) {
        return getFilterSpecs().get(dataAttribute);
    }

    public void setDataAttributes(ArrayList<String> dataAttributes) {
        m_dataAttributes = dataAttributes;
    }

    public ArrayList<String> getDataAttributes() {
        return m_dataAttributes;
    }

    public void setColumns(String[] columns) {
        m_columns = columns;
    }

    public String[] getColumns() {
        return m_columns;
    }

    public ArrayList<String> getColumnLayout() {
        return m_colLayout;
    }

    public void setColumnLayout(ArrayList<String> colLayout) {
        m_colLayout = colLayout;
    }

    public ArrayList<String> getRowLayout() {
        return m_rowLayout;
    }

    public void setColumnSectionLayout(ArrayList<String> colSectionLayout) {
        m_colSectionLayout = colSectionLayout;
    }
 
    public ArrayList<String> getColumnSectionLayout() {
        return m_colSectionLayout;
    }

    public void setRowSectionLayout(ArrayList<String> rowSectionLayout) {
        m_rowSectionLayout = rowSectionLayout;
    }
    
    public ArrayList<String> getRowSectionLayout() {
        return m_rowSectionLayout;
    }
    public void setRowLayout(ArrayList<String> rowLayout) {
        m_rowLayout = rowLayout;
    }

    public AggSpec[][] getAggSpecs() {
        return m_aggSpecs;
    }

    public void setAggSpecs(AggSpec[][] aggSpecs) {
        m_aggSpecs = aggSpecs;
    }

    public boolean isTotalsEnabled() {
        return m_totalsEnabled;
    }

    public void setTotalsEnabled(boolean totalsEnabled) {
        m_totalsEnabled=totalsEnabled;
    }
    
    // Called only by CSV demo code (not by ptEdit demo or by internal code).  
    // TODO: When cleaning up CSV demo, review what this method does and whether it's the correct thing.  It discards all 
    // edited values done since the last sort, without first moving them to the wrapped DA, which seems clearly wrong.
    public void refreshData(boolean preserveSort) throws DataException{
        m_da = null;
        m_updatedValues.clear();
        getDataAccess(); // bootstrap the new data access before applying sorts. Recreates m_da and m_cubicDA.
        if(preserveSort)
            setSorts(getSorts());
    }
    
    public DataAccess getDataAccess() {
        // Test data column projections (no connections needed)
        
        try
        {
            if(m_da==null)
            {
                Object [][] data = fetchData();
                
                String layout[][];
                if((m_rowSectionLayout!=null) && (m_colSectionLayout!=null))
                    layout = new String[][] {m_colLayout.toArray(new String[0]), 
                                             m_rowLayout.toArray(new String[0]), 
                                             m_rowSectionLayout.toArray(new String[0]), 
                                             m_colSectionLayout.toArray(new String[0])}; // TODO: is the order really col, row, row, col?  Has this been tested?
                else
                    layout = new String[][] {m_colLayout.toArray(new String[0]), m_rowLayout.toArray(new String[0])};

                HashMap<String,Double> dataAttributeMinimums = new HashMap<String,Double>();
                HashMap<String,Double> dataAttributeMaximums = new HashMap<String,Double>();

                ArrayList<String> dataAttributes = new ArrayList<String>();                                
                
                for(int i=0;i<m_dataAttributes.size();i++) {
                    String dataAttribute = m_dataAttributes.get(i);
                    FilterSpec filter = getFilterSpecs().get(dataAttribute);
                    if(filter!=null)
                    {  
                        // only show data attribute if filter is enabled. 
                        if(filter.isDataEnabled())
                        {
                            dataAttributeMinimums.put(dataAttribute,filter.getMin().doubleValue());        
                            dataAttributeMaximums.put(dataAttribute,filter.getMax().doubleValue());            
                            dataAttributes.add(dataAttribute);
                        } 
                    } else
                        dataAttributes.add(dataAttribute);
                        
                }
                
                RowProjection proj = new BaseProjectionImpl(layout, dataAttributes.toArray(new String[0]), data, m_columns);
                AggSpec[][] aggSpecs = null;
                if(isTotalsEnabled())
                    aggSpecs = getAggSpecs();
                m_cubicDA = new RowBasedCubicDataAccess(new ResultTable(proj,aggSpecs));
                // wrap the data access with a decorator, so that we can add values to DataAccess
                m_da = new RowsetDataAccessDecorator(m_cubicDA, getFilterSpecs(), m_updatedValues);
            }
            return m_da;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /** 
     * Determine whether the QDR matches the row (has all the same dim/member pairs other than the measure dim).
     * If so, find the column of the row containg the measure corresponding to the QDR, update it with the specified new value, and return true.  
     * If not, return false without changing anything.  
     */
    public boolean updateMatch(QDR qdr, Object[] row, String[] columns, String dataAttribute, Object newValue) {
        String measureDim = qdr.getMeasureDim();
        
        // Determine whether the QDR matches the row.  If not, return false.  If so, keep going.
        for (String key : qdr.keySet()) {
            // dont compare measure dim
            if(!key.equals(measureDim))
            {
                String member = qdr.getDimMember(key).toString();
                for(int j=0;j<columns.length;j++) {
                    if(key.equals(columns[j])) {
                        if(!row[j].equals(member))
                            return false;
                    }
                }
            }
        }
        
        // Update the appropriate measure in the row and return true.
        for(int j=0;j<columns.length;j++) {
            if(dataAttribute.equals(columns[j])) {
                if(newValue instanceof Number)
                  row[j] = new Double(((Number)newValue).doubleValue());
                else 
                  row[j] = newValue;
            }
        }        
        return true;
    }    

    /**
     * This method updates the mins and maxes for all filters, in response to a value change.  This method is not called automatically.  
     * The app should call it if it needs the mins and maxes to be current (i.e. if it uses them), perhaps from a valueChanged listener.
     * 
     * @return a boolean indicating whether the max or min changed as a result of the value change.
     */
    // Currently this method is called only by the CSV demo, not by ptEdit.jspx (or by this class).
    // 
    // TODO: Verify and fix the following bug when we fix up the CSV demo, which is the only demo that uses the limits.  
    // This method doesn't appear to handle the following cases:
    // - The new value is higher than the old max AND the old value was the old min.
    // - The new value is lower than the old min AND the old value was the old max.
    // To fix this, it looks like the else-clause needs to be a standalone block so that it runs even if one of the previous if's ran.  
    // 
    // TODO: see apparent additional bug noted below.  What else is broken here?
    public boolean valueChanged(QDR qdr, Object newObjectValue, Object oldObjectValue) {
        boolean bLimitChanged = false;
        if(newObjectValue instanceof Number) {
            String dataAttribute = getDataAttribute(qdr);
                                    
            Number newValue = (Number)newObjectValue;
            Number oldValue = (Number)oldObjectValue;

            // update the limits
            for (FilterSpec filter : getFilterSpecs().values()) {
                if(filter.getLabel().equals(dataAttribute)) {
                    long _newValue = newValue.longValue();
                    if(_newValue > filter.getMax().longValue())
                    {
                        if(filter.getMax().equals(filter.getRangeMax()))
                            filter.setRangeMax(new Long(_newValue));
                        filter.setMax(new Long(_newValue));
                        
                        bLimitChanged = true;
                    } else if(_newValue < filter.getMin().longValue())
                    {
                        if(filter.getRangeMin().equals(filter.getRangeMin())) // TODO: bug? should first be getMin?
                            filter.setRangeMin(new Long(_newValue));
                        filter.setMin(new Long(_newValue));
                        bLimitChanged = true;
                    } else {
                        
                        // if the old value was the max/min, then 
                        // determine the the new max/min
                        if(oldValue!=null)
                        {
                            long _oldValue = oldValue.longValue();
                            if(_oldValue==filter.getMax().longValue()) {
                                // find new maximum, skipping over current cell value that still hasn't changed. 
                                long newMax = computeDataAttributeMaxValue(qdr, dataAttribute,_newValue);
                                filter.setMax(new Long(newMax));
                                if(filter.getRangeMax().equals(oldValue))
                                    filter.setRangeMax(filter.getMax());
                                bLimitChanged = true;                            
                            } else if(_oldValue==filter.getMin().longValue()){
                                // find new mimimum, skipping over current cell value that still hasn't changed. 
                                long newMin = computeDataAttributeMinValue(qdr, dataAttribute,_newValue);
                                filter.setMin(new Long(newMin));
                                if(filter.getRangeMin().equals(oldValue))
                                    filter.setRangeMin(filter.getMax());
                                bLimitChanged = true;                                                        
                            }
                        }
                    }                
                }
            }
        }
        return bLimitChanged;
    }

    // find the new max for the data attribute, but skip the updatingQDR, since it still has its old value
    // pass in the new value of the updating cell, which may be the new max
    protected long computeDataAttributeMaxValue(QDR updatingQDR, String dataAttribute, long newValue) {
        return computeDataAttributeLimit(updatingQDR, dataAttribute, newValue, true);
    }

    // find the new min for the data attribute, but skip the updatingQDR, since it still has its old value
    // pass in the new value of the updating cell, which may be the new min
    protected long computeDataAttributeMinValue(QDR updatingQDR, String dataAttribute, long newValue) {
        return computeDataAttributeLimit(updatingQDR, dataAttribute, newValue, false);
    }

    protected long computeDataAttributeLimit(QDR updatingQDR, String dataAttribute, long newValue, boolean isMax) {
        // iterate over the entire data set looking for data attribute values that are larger
        DataAccess da = getDataAccess();
        try {
            int colExtent = da.getEdgeExtent(DataDirector.COLUMN_EDGE);
            int rowExtent = da.getEdgeExtent(DataDirector.ROW_EDGE);
            for(int c=0;c<colExtent;c++) {
                for(int r=0;r<rowExtent;r++) {
                    QDR qdr = da.getValueQDR(r,c,DataAccess.QDR_WITH_PAGE);
                    String _dataAttribute = getDataAttribute(qdr);
                    // only try to find max values for the same data attribute. 
                    if(_dataAttribute!=null && _dataAttribute.equals(dataAttribute)) {
                        // skip the qdr/cell value being updated, since it still has its old value, which 
                        // was the old max/min.  
                        if(!qdr.equals(updatingQDR))
                        {
                            Object value = da.getValue(r,c,DataMap.DATA_VALUE);
                            if(value instanceof Number) {
                                double _value = ((Number)value).doubleValue();
                                if(isMax)
                                {
                                    if(_value>newValue)
                                        newValue = Math.round(_value);
                                } else
                                {
                                    if(_value<newValue)
                                        newValue = Math.round(_value);
                                }
                                    
                            }
                        }
                    }
                }
            }
        } catch ( DataException e) {
            e.printStackTrace();
        }
        return newValue;
    }

    protected String getDataAttribute(QDR qdr) {
        QDRMember member = qdr.getDimMember(qdr.getMeasureDim());
        if(member!=null)
            return member.toString();
        else
            return null;
    }
    
    public Object getProperty(String property) throws DataDirectorException {
        if(property.equals(DataDirector.PROP_SORTS_SUPPORTED))
            return Boolean.TRUE;
        return super.getProperty(property);
    }

    public SortInfo[] getSorts() 
    {
        return m_sortInfo;
    }
    
    // TBD: ideally sorts would work like pivots, where the work of rebuilding the DA is delayed until the next call to getDA().
    // To do that:
    // - The inner try block would be replaced with {m_pendingSortCriteria = sliceInfo; return true;} // is it OK to return true rather than returning m_cubicDA.sort()?  If not can't do this.
    // - Add to top of getDA(), before the if(m_da==null) test:  {if (m_pendingSortCriteria != null && !m_updatedValues.isEmpty()) moveUpdatedValuesToRowset();}
    // - Add to end of getDA(), just before the return stmt: {if (m_pendingSortCriteria != null) m_cubicDA.sort(sliceInfo.edge, sliceInfo.slice, sliceInfo.direction, sliceInfo.isNullsFirst(), sliceInfo.isGrouped());}
    public boolean setSorts(SortInfo[] sortInfo) throws LayerOutOfRangeException, DataDirectorException {
        m_sortInfo = sortInfo;
        if (sortInfo != null)
        {
            for (int i = 0; i < sortInfo.length; i++)
            {
                if (sortInfo[i] instanceof QDRSliceSortInfo)
                {
                    QDRSliceSortInfo sliceInfo = (QDRSliceSortInfo)sortInfo[i];
                    // This is the only type we do right now: turn this into a call on the data access
                    int edge = sliceInfo.getEdge();
                    if (edge > -1)
                    {
                        int[] directions = sliceInfo.getDirection();
                        if (directions != null && directions.length > 0)
                        {
                            int direction = directions[0];
                            int slice = sliceInfo.getSlice();
                            if (slice != -1)
                            {    
                                try {
                                    if (!m_updatedValues.isEmpty()) {
                                        moveUpdatedValuesToRowset();
                                        getDataAccess(); // bootstrap the new cubic DataAccess (which is null after previous call) to contain the udpated values that the previous call moved to the rowset.
                                    }
                                    m_cubicDA.sort(edge, slice, direction, sliceInfo.isNullsFirst(), sliceInfo.isGrouped());
                                } catch(DataException e) {
                                    throw new DataDirectorException("Sort Failed", e);
                                }
                            }
                        }
                    }
                }
            }
        }              
        return false;
    }

    /**
     * Moves edited values from m_updatedValues (used by the decorator DA) to the rowset (used by 
     * the wrapped cubic DA).  Nulls the DA so that the next call to getDataAccess() will rebuild 
     * it with the updated rowset.  Clears m_updatedValues, since the values are now being 
     * tracked by the wrapped DA.
     */
    private void moveUpdatedValuesToRowset() {
        for (Map<String,Object> map : m_updatedValues.values()) {
            QDR qdr = (QDR)map.get("qdr");
            updateValueInRowset(qdr, map.get("value"));
        }
        m_da = null;
        m_updatedValues.clear();
    }

    /**
     * Finds the row for the specified QDR in the rowset, and updates the correct measure in that row with the specified value.
     */
    private void updateValueInRowset(QDR qdr, Object newObjectValue) {
        // TBD: obviously this doesn't handle m_data being a CollectionModel, which this class otherwise supports.  Do we want to support that?
        if(m_data instanceof Object[][]) {
            String dataAttribute = getDataAttribute(qdr);
            for (Object[] row : (Object[][])m_data) {
                if(updateMatch(qdr, row, m_columns, dataAttribute, newObjectValue))
                    break;
            }
        }
    }

    // Unlike sorts, pivots don't need updated values to be in the wrapped DA, since they are not used in the pivot operation.  
    // So pivots just clear the DA so the next call to getDataAccess() will rebuild it, without first moving edits into the wrapped DA.
    public boolean pivot(int fromEdge, int toEdge, int fromLayer, int toLayer, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException
    {        
        // so next call to getDataAccess() will rebuild it.
        m_da=null;
        m_cubicDA=null;
        
        m_sortInfo = null;
        ArrayList<String> toList = getEdgeLayout(toEdge);
        ArrayList<String> fromList = getEdgeLayout(fromEdge);
        switch(flags) {
            case DataDirector.PIVOT_MOVE_AFTER:
            {
                String tLayer = toList.get(toLayer);
                String fLayer = fromList.remove(fromLayer);
                toList.add(toList.indexOf(tLayer) + 1, fLayer);                
                break;
            }
            case DataDirector.PIVOT_MOVE_TO:
            {
                String fLayer = fromList.remove(fromLayer);
                toList.add(fLayer);                
                break;
            }
            case DataDirector.PIVOT_MOVE_BEFORE:
            {
                String tLayer = toList.get(toLayer);
                String fLayer = fromList.remove(fromLayer);
                toList.add(toList.indexOf(tLayer), fLayer);                
                break;
            }
            case DataDirector.PIVOT_SWAP:
            {
                String fLayer = fromList.get(fromLayer);
                String tLayer = toList.get(toLayer);
                fromList.set(fromLayer,tLayer);
                toList.set(toLayer,fLayer);
                break;
            }
        }
        return true;        
    }

    public ArrayList<String> getEdgeLayout(int edge) {
        if(edge == DataDirector.ROW_EDGE)
            return m_rowLayout;
        else if(edge == DataDirector.COLUMN_EDGE)
            return m_colLayout;
        else if(edge == RichPivotTableRenderer.COLUMN_SECTION_EDGE)
            return m_colSectionLayout;
        else if(edge == RichPivotTableRenderer.ROW_SECTION_EDGE)
            return m_rowSectionLayout;
        else
            return null;
    }

    public boolean pivotOK(int fromEdge, int toEdge, int fromLayer, int toLayer, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException
    {        
        return true;        
    }

    // METHODS

    // Other constructors will allow custom local data cubes to be set up,
    // and other methods will allow them to be filled with data more efficiently
    public DataDirector createDataDirector()
    {
        return this;
    }
    
    public CubeDataDirector createCubeDataDirector() {
        return this;
    }
    
    public RelationalDataDirector createRelationalDataDirector() {
        return this;
    }
    
    public void addDataDirectorListener(DataDirectorListener l) {
        listener = l;
        l.viewDataAvailable(new DataAvailableEvent(this, this.getDataAccess()));
    }

    public void removeDataDirectorListener(DataDirectorListener l) {
    }
    
    public Object clone() {
        return null;
    }
        
    public DataMap getDataMap() {
        return new DataMap("");
    }

    public MetadataMap getMetadataMap(int edge, int layer) throws EdgeOutOfRangeException, LayerOutOfRangeException {
        return new MetadataMap((String)null);
    }

    public DataMap getSupportedDataMap()
    {   
        String supported[] = {DataMap.DATA_UNFORMATTED};
        return new DataMap(supported);
    }
    
    public MetadataMap getSupportedMetadataMap()
    {
        String supported[] = {MetadataMap.METADATA_LONGLABEL,
                              MetadataMap.METADATA_DRILLSTATE};
        return new MetadataMap(supported);
    }
    
    public LayerMetadataMap getSupportedLayerMetadataMap()
    {
        String supported[] = {LayerMetadataMap.LAYER_METADATA_LONGLABEL};
        return new LayerMetadataMap(supported);
    }

    private Map<Set<String>,Map<String,Object>> m_updatedValues = new HashMap<Set<String>,Map<String,Object>>();
    
    protected DataDirectorListener listener;
    protected ArrayList<String> m_colLayout = null;
    protected ArrayList<String> m_rowLayout = null;    
    protected ArrayList<String> m_colSectionLayout = null;
    protected ArrayList<String> m_rowSectionLayout = null;    
    protected ArrayList<String> m_dataAttributes = null;
    protected String[] m_columns = null;
    protected AggSpec[][] m_aggSpecs = null;
    protected Object m_data = null; // the Object[][] or CollectionModel originally set on us, from which the DA is created.  (TBD: not all methods of this class currently support CollectionModel.)
    protected DataAccess m_da = null; // the decorator that wraps m_cubicDA
    protected RowBasedCubicDataAccess m_cubicDA = null; // wrapped by m_da
    protected SortInfo[] m_sortInfo = null;
    HashMap<String,FilterSpec> m_filterSpecs=null;
    boolean m_totalsEnabled=false;
}
