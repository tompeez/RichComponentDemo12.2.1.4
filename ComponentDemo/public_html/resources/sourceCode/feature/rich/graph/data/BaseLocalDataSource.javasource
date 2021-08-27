/*
** Copyright (c) 2007, 2009, Oracle and/or its affiliates. 
** All rights reserved. 
**
*/
// Base class for LocalDataSources
package oracle.adfdemo.view.feature.rich.graph.data;

import java.util.Enumeration;
import java.util.Vector;


import oracle.dss.util.ColumnOutOfRangeException;
import oracle.dss.util.CubeDataAccess;
import oracle.dss.util.CubeDataDirector;
import oracle.dss.util.DataAccess;
import oracle.dss.util.DataAccessAdapter;
import oracle.dss.util.DataAvailableEvent;
import oracle.dss.util.DataChangedEvent;
import oracle.dss.util.DataDirector;
import oracle.dss.util.DataDirectorException;
import oracle.dss.util.DataDirectorListener;
import oracle.dss.util.DataMap;
import oracle.dss.util.DataSource;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerMetadataMap;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.RelationalDataAccess;
import oracle.dss.util.RelationalDataDirector;
import oracle.dss.util.RowOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;
import oracle.dss.util.WaitData;
import oracle.dss.util.WaitDataAvailableEvent;


public class BaseLocalDataSource extends DataAccessAdapter 
implements CubeDataDirector, CubeDataAccess, RelationalDataDirector, RelationalDataAccess, DataSource, Cloneable
{                           // BaseLocalDataSource

    // DATA
    protected int rowCount;
    protected int columnCount;    
    private int maxRowCount;
    private int maxColumnCount;
    protected int pageCount;
    private int currentPage;
    private int m_fetchedRowCount;

    protected Object cellData[][];
    protected String rowLabels[];
    protected String columnLabels[];
    protected String pageLabels[];
    
    public BaseLocalDataSource( double defaultData[][], int rows, int columns, int pages )
    {                       // default constructor

        int row, col;
		Integer Num;

        // Construct a simple default data set with one page, specified rows and columns

        pageCount   = pages;
        rowCount    = rows;
        columnCount = columns;
        maxRowCount = rows;
        maxColumnCount = columns;
        m_fetchedRowCount = rowCount;

        currentPage = 0;

        cellData = new Object[rowCount][columnCount];
        for (row = 0; row < rowCount; row++)
            for (col = 0; col < columnCount; col++)
                cellData[row][col] = new Double(defaultData[row][col]);

        rowLabels = new String[rowCount];
        for ( row = 0; row < rowCount; row++ )
		{
		    Num = new Integer( row + 1 );
            rowLabels[row] = "row " + Num.toString();
		}

        columnLabels = new String[columnCount];
        for ( col = 0; col < columnCount; col++ )
		{
		    Num = new Integer( col + 1 );
            columnLabels[col] = "col " + Num.toString();
		}

        pageLabels = new String[pageCount];
        for (int page = 0; page<pageCount; page++)
        {
            Num = new Integer(page + 1);
            pageLabels[page] = "Page " + Num.toString();
        }
    }                       // default constructor

    public Object clone() {
        return this.clone();
    }

    public RelationalDataDirector createRelationalDataDirector() {
        return this;
    }
    
    public DataDirector createDataDirector()
    {
        return this;
    }
    public CubeDataDirector createCubeDataDirector() {
        return this;
    }
    
    public DataAccess getDataAccess() throws DataDirectorException
    {
        return this;
    }
    
    //simulate lazy data fetch (1 row at a time)
    public void doLazyDataFetch()
    {
        if (m_fetchedRowCount == rowCount)
            m_fetchedRowCount = 0;
        else
            m_fetchedRowCount++;
        
        for (Enumeration e = m_dataDirectorListeners.elements(); e.hasMoreElements(); )
        {
            DataDirectorListener listener = (DataDirectorListener)e.nextElement();
            listener.waitDataAvailable(new WaitDataAvailableEvent(this));
        }
    }
    
    //decrement row count
    public void decrementRowCount()
    {
        if (rowCount-1 > -1)
            rowCount--;
        else
            rowCount = maxRowCount;
        
        for (Enumeration e = m_dataDirectorListeners.elements(); e.hasMoreElements(); )
        {
            DataDirectorListener listener = (DataDirectorListener)e.nextElement();
            listener.viewDataChanged(new DataChangedEvent(this, this, true, true, true, false));
        }
    }
    
    //decrement column count
    public void decrementColumnCount()
    {
        if (columnCount-1 > -1)
            columnCount--;
        else
            columnCount = maxColumnCount;
        
        for (Enumeration e = m_dataDirectorListeners.elements(); e.hasMoreElements(); )
        {
            DataDirectorListener listener = (DataDirectorListener)e.nextElement();
            listener.viewDataChanged(new DataChangedEvent(this, this, true, true, true, false));
        }
    }
    
    /**
     * Pivots data. Operations include:
     * <ul><li>Moving dimensions or dimension members from one edge to
     *         another</li>
     * <li>Moving dimensions or dimension members from one location on an
     *     edge to another location on the same edge</li>
     * <li>Swapping the locations of two dimensions</li>
     * <li>Swapping two edges
     * </ul>
     *
     * @param fromEdge  The edge to pivot from.
     * @param toEdge    The edge to pivot to.
     * @param fromLayer The layer to pivot from. The layer identifies the
     *                  location of the dimension or dimension member; 0 is the
     *                  outer-most dimension on the edge.
     * @param toLayer   The layer to pivot to.
     * @param flags     Constant that indicates the type of pivot to perform.
     *                  The constants begin with PIVOT_ in this interface.
     *
     * @return <code>true</code> if the pivot is successful,
     *         <code>false</code> if not.
     * @throws EdgeOutOfRangeException If either edge is too
     *                  large.
     * @throws LayerOutOfRangeException If either layer is negative or
     *                  too large.
     * @throws DataDirectorException If a data provider error occurs.
     *
     * @status Needs change API rework
     */
    public boolean pivot(int fromEdge, int toEdge, int fromLayer, int toLayer, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException {
        return false;
    }

    /**
     * Drills in the data, from a single member.
     *
     * @param edge   The edge to drill.
     * @param layer  The layer to drill. The layer identifies the
     *                  location of the dimension or dimension member; 0 is the
     *                       *                  outer-most dimension on the edge.
     * @param slice  The slice to drill. The slice is zero-based. Each
     *                  dimension member in the innermost dimension has a
     *                  unique slice. In outer dimensions, any slice that the
     *                  outer dimension spans can be used.
     * @param flags  Constant that indicates the type of drill.
     *                  The constants begin with DRILLABLE_ in this interface.
     *
     * @return <code>true</code> If drilling is successful,
     *         <code>false</code> if not.
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is too large.
     * @throws LayerOutOfRangeException If <code>layer</code> is negative or
     *                  too large.
     * @throws SliceOutOfRangeException If <code>slice</code> is negative or
     *                  too large.
     * @throws DataDirectorException If a data provider error occurs.
     *
     * @status Needs change API change
     */
    public boolean drill(int edge, int layer, int slice, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException, DataDirectorException {
        return true;
    }
    
    /**
     * Retrieves the total number of members on the given edge.
     * cursor.
     *
     * @param   edge Edge of interest.
     * @return   The total number of members on the given edge.
     *
     * @status Needs change API change
     */
    public int getEdgeExtent(int edge) throws EdgeOutOfRangeException {
        switch (edge) {
            case DataDirector.COLUMN_EDGE:
                return columnCount;
            case DataDirector.ROW_EDGE:
                return rowCount;
            default:
                return pageCount;
        }
    }
    
    /**
     * Return the total number of edges in the data source. 
     * Note: the views will consider all edges beyond 0 and 1 (column/
     * row) to be page edges, and some of the methods retrieving page
     * counts, etc., may involve view calculations from more basic data
     * source information.  The views assume that the data source will
     * ascribe column/row meaning to the first two edges on any query.
     * 
     * @return the total number of pages of data to display
     */
    public int getEdgeCount() 
    {
        return 3;
    }

    /**
     * Retrieves a data value for the specified row and column intersection.
     *
     * @param row The row to get.
     * @param col The column to get.
     * @param type Type of data to return, such as formatted or unformatted.
     *             Valid <code>type</code> values are defined in the DataMap.
     *
     * @return The specified type of information for the specified row and
     *         column location.
     *
     * @throws RowOutOfRangeException If <code>row</code> is negative or too
     *                                large.
     * @throws ColumnOutOfRangeException If <code>column</code> is negative or
     *                                too large.
     *
     * @see DataMap
     *
     * @status Documented
     */
    public Object getValue(int aRow, int aColumn, String type) throws RowOutOfRangeException, ColumnOutOfRangeException {
        if (aRow < 0 || aRow >= rowCount) {
            throw new RowOutOfRangeException(aRow, rowCount);
        }
        if (aColumn < 0 || aColumn >= columnCount) {
            throw new ColumnOutOfRangeException(aColumn, columnCount);
        }
        
        if (aRow >= m_fetchedRowCount)
        {
            //System.out.println("NOT FETCHED: col = " + aColumn + ", row = " + aRow);
            return new WaitData("waiting for data...");
        }
        
        return cellData[aRow][aColumn];
    }


    public boolean setValue(Object aValue,
        int    aRow,           // zero-based row
        int    aColumn,        // zero-based column
        String     type)          // what type of value to set: ignored for now - data only
         throws RowOutOfRangeException, ColumnOutOfRangeException
    {
        if (aRow < 0 || aRow >= rowCount)
            throw new RowOutOfRangeException(aRow, rowCount);
        if (aColumn < 0 || aColumn >= columnCount)
            throw new ColumnOutOfRangeException(aColumn, columnCount);
        cellData[ aRow][ aColumn] = aValue;
        return true;
    }

    /**
     * Retrieves the label for the specified column.
     *
     * @param   edge Edge of interest.
     * @param slice The slice whose label is desired.
     * @param type A constant that specifies the kind of metadata that you want.
     *             Valid <code>type</code> values are defined in the MetadataMap.
     *
     * @return The label (or metadata) for the location on the specified edge.
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is out of range.
     * @throws SliceOutOfRangeException If <code>slice</code> is negative or
     *         too large.
     *
     * @status Needs change API change
     */
    public Object getSliceLabel(int edge, int slice, String type) throws EdgeOutOfRangeException, SliceOutOfRangeException {
        switch (edge) {
            case DataDirector.COLUMN_EDGE:
                if (slice < 0 || slice >= columnCount) 
                    throw new SliceOutOfRangeException(slice, columnCount);
                return columnLabels[slice];
            case DataDirector.ROW_EDGE:
                if (slice < 0 || slice >= rowCount)
                    throw new SliceOutOfRangeException(slice, rowCount);
                return rowLabels[slice];
            case DataDirector.PAGE_EDGE:
            default:
                if (slice < 0 || slice >= pageCount)
                    throw new SliceOutOfRangeException(slice, pageCount);
                return pageLabels[(int) slice];
        }            
    }

    /**
     * Retrieves the number of the current slice.
     * The number that this method returns is not the regular index of the
     * page dimension. Instead, page numbers begin at 1 and are incremented
     * as in the following example:
     * <p>
     * Suppose that you have a Time dimension and a City dimension on the page
     * edge. Suppose the City dimension has three members.
     * The first five pages are as follows:
     * <P>
     * <ul><li>0. Time member 0; City member 0</li>
     *     <li>1. Time member 0; City member 1</li>
     *     <li>2. Time member 0; City member 2</li>
     *     <li>3. Time member 1; City member 0</li>
     *     <li>4. Time member 1; City member 1</li>
     * </ul>
     *
     * @return The current slice of the edge.
     * @throws EdgeOutOfRangeException If <code>edge</code> is out of range.
     *
     * @status Needs change API change
     */
    public int getEdgeCurrentSlice(int edge) throws EdgeOutOfRangeException
    {
        return currentPage;
    }

    /**
     * Changes the current slice of data on the given edge.
     *
     * @param edge The edge on which to make the change.
     * @param slice The slice to make current. 
     * Page numbers begin at zero and are incremented as in the following example:
     * <p>
     * Suppose that you have a Time dimension and a City dimension on the page
     * edge. Suppose the City dimension has three members.
     * The first five pages are as follows:
     * <P>
     * <ul><li>0. Time member 0; City member 0</li>
     *     <li>1. Time member 0; City member 1</li>
     *     <li>2. Time member 0; City member 2</li>
     *     <li>3. Time member 1; City member 0</li>
     *     <li>4. Time member 1; City member 1</li>
     * </ul>
     *
     * @return <code>true</code> if the page change is successful,
     *         <code>false</code> if not.
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is too large.
     * @throws SliceOutOfRangeException If <code>slice</code> is too large.
     * @throws DataDirectorException If a data provider error occurs.
     *
     * @status Needs change API change
     */
    public boolean changeEdgeCurrentSlice(int edge, int slice) throws EdgeOutOfRangeException, SliceOutOfRangeException, DataDirectorException {
        if (slice < 0 || slice >= pageCount)
            return false;
        currentPage =  slice;
        //m_fetchedRowCount = 0;
        return true;
    }

    /**
     * Retrieves the number of layers on the specified edge.
     *
     * @param edge A constant that represents the edge of interest.
     *             The constants are defined in the DataDirector implementation.
     *
     * @return The number of layers on the specified edge.
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is out of range.
     * @status Needs change API change
     */
    public int getLayerCount(int edge) throws EdgeOutOfRangeException
    {
        if (edge==DataDirector.PAGE_EDGE)
            return 0;
        else
            return 1;
    }

    /**
     * Retrieves the number of logical layers at the specified location on the
     * specified edge.
     * This method is useful in cases of asymmetry where the dimensions
     * on an edge do not all span the same layers.
     *
     * @param edge A constant that represents the edge of interest. Constants
     *             are defined in the DataDirector implementation.
     * @param slice The slice that identifies a location along the edge of
     *              interest. The slice is zero-based. Each
     *                  member in the innermost layer has a
     *                  unique slice. In outer layers, any slice that the
     *                  outer layer spans can be used.
     *
     * @return    The number of logical layers at the specified location on the
     *            specified edge.
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is out of range.
     * @throws SliceOutOfRangeException If <code>slice</code> is negative or too
     *              large.
     *
     * @status Needs change API change
     */
    public int getSliceMemberCount(int edge, int slice) throws EdgeOutOfRangeException, SliceOutOfRangeException {
        if (edge==DataDirector.PAGE_EDGE)
            return 0;
        else
            return 1;
    }


    /**
     * Retrieves a piece of metadata for a dimension member.
     * 
     * @param edge A constant that represents the edge of interest.
     *             The constants are defined in the DataDirector implementation.
     * @param layer The layer of the member of interest.
     *              Valid values are zero to total layers at the specified
     *              slice.
     * @param slice The location along the edge.
     *              The slice is zero-based. Each
     *                   member in the innermost layer has a
     *                  unique slice. In outer layers, any slice that the
     *                  outer member spans can be used.
     * @param type A constant that specifies the kind of metadata that you want.
     *             Valid <code>type</code> values are defined in the MetadataMap.
     *
     * @return The requested metadata.
     *
     * @throws LayerOutOfRangeException If <code>layer</code> is negative or
     *              too large.
     * @throws SlieOutOfRangeException If <code>slice</code> is negative or
     *              too large.
     *
     * @status Needs change API change
     */
    public Object getMemberMetadata(int edge, int layer, int slice, String type) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        if (type.equals(MetadataMap.METADATA_DRILLSTATE)) {
            switch ((int)slice) {
                case 0:
                    return new Integer(1);
                case 1:
                    return new Integer(2);
            }
            return new Integer(0);
        }
        if (type.equals(MetadataMap.METADATA_INDENT)) 
            return new Integer((int)slice);

        if (type.equals(MetadataMap.METADATA_VIEWSTYLE))
        {
            return null;
        }
        
        if (type.equals(MetadataMap.METADATA_DATE))
        {
            return null;
        }
        if (type.equals(MetadataMap.METADATA_DRILL_PARENT_DATE))
        {
            return null;
        }
        if (type.equals(MetadataMap.METADATA_DRILL_PARENT_DATESPAN))
        {
            return null;
        }
        if (type.equals(MetadataMap.METADATA_DATESPAN))
        {
            return null;
        }
        Object obj = null;
        switch (edge) {
            case DataDirector.COLUMN_EDGE:
                obj = columnLabels[(int)slice];
            break;
            case DataDirector.ROW_EDGE:
                obj = rowLabels[(int)slice];
            break;
            case DataDirector.PAGE_EDGE:
                obj = pageLabels[(int)slice];
            break;
        };
        return obj;            
    }
            
    /**
     * Retrieves the number of slices that a member spans.
     * Views use this method to determine the width of a member
     * in a column header or the height of a member in
     * a row header when not in outline form.
     *
     * @param edge A constant that represents the edge of interest.
     *             The constants are defined in the DataDirector implementation.
     * @param layer The layer of the dimension of interest.
     *              Valid values are zero to total layer at the specified
     *              slice.
     * @param slice The location along the edge.
     *              The slice is zero-based. Each
     *                  member in the innermost layer has a
     *                  unique slice. In outer layers, any slice that the
     *                  outer member spans can be used.
     *
     * @return The number of slices that the member spans at the
     *         specified location.
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is too large.
     * @throws LayerOutOfRangeException If <code>layer</code> is negative or
     *               too large
     * @throws SliceOutOfRangeException If <code>slice</code> is negative or
     *               too large
     *
     * @see #getMemberStartSlice
     *
     * @status Needs change API change
     */
    public int getMemberExtent(int edge, int depth, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        return 1;
    }

    /**
     * Retrieves the number of layers that a member spans at the specified
     * location on the specified edge.
     * On the column edge, this is the number of heading rows that the specified
     * member spans.
     * On the row edge, this is the number of heading columns that the specified
     * member spans.
     * <P>
     * This method is useful only in cases of asymmetry where the members
     * on an edge do not all span one layer and have a depth of 1.
     * Normally, all members have a member depth of 1.
     *
     * @param edge  A constant that represents the edge of interest.
     *             The constants are defined in the DataDirector implementation.
     * @param layer The starting layer of the member, as returned by
     *              <code>getMemberStartLayer</code>.
     * @param slice The location along the edge.
     *              The slice is zero-based. Each
     *                  member in the innermost layer has a
     *                  unique slice. In outer layers, any slice that the
     *                  outer member spans can be used.
     *
     * @return    The number of layers that the specified member spans.
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is too large.
     * @throws LayerOutOfRangeException If <code>layer</code> is negative or
     *            too large.
     * @throws SliceOutOfRangeException If <code>slice</code> is negative or
     *            too large.
     *
     * @status Needs change API change
     */
    public int getMemberDepth(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException
    {
        return 1;
    }

    /**
     * Retrieves the starting slice of a member that spans a number of layers 
     * on the specified edge.
     * <P>
     * This method is useful only in cases of asymmetry where the members
     * on an edge do not all span one layer.
     * Normally, all members have a member start layer equal to their layer.
     *
     * @param edge  A constant that represents the edge of interest.
     *             The constants are defined in the DataDirector implementation.
     * @param layer The layer of the member
     * @param slice The location along the edge.
     *              The slice is zero-based. Each
     *                  member in the innermost dimension has a
     *                  unique slice. In outer layers, any slice that the
     *                  outer member spans can be used.
     *
     * @return    The layer at which the specified member starts its layer span.
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is too large.
     * @throws LayerOutOfRangeException If <code>layer</code> is negative or
     *            too large.
     * @throws SliceOutOfRangeException If <code>slice</code> is negative or
     *            too large.
     *
     * @status New
     */
    public int getMemberStartLayer(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        return layer;
    }

    /**
     * NEW
     * Register a listener to the data source for changes.
     *
     * @param l listener
     */
    public void addDataDirectorListener(DataDirectorListener l)
    {
        // Notify the listener that data is available
        if (l != null)
        {
            m_dataDirectorListeners.addElement(l);
            l.viewDataAvailable(new DataAvailableEvent(this, this));
        }
    }

    /**
     * NEW
     * Remove a listener from the data source.
     *
     * @param l listener
     */
    public void removeDataDirectorListener(DataDirectorListener l) 
    {
        if (l != null)
        {
            m_dataDirectorListeners.removeElement(l);
        }
    }
    
    public boolean isFetched(int[] startSlice, int[] endSlice) throws SliceOutOfRangeException
    {
        if (startSlice == null || endSlice == null)
            return false;
        
        if (startSlice.length < 1 || endSlice.length < 1)
            return false;
        
        if (startSlice[DataDirector.COLUMN_EDGE] < 0 ||
            startSlice[DataDirector.COLUMN_EDGE] >= columnCount)
        {
            throw new SliceOutOfRangeException(startSlice[DataDirector.COLUMN_EDGE],
                                               columnCount-1);
        }
        
        if (endSlice[DataDirector.COLUMN_EDGE] < 0 ||
            endSlice[DataDirector.COLUMN_EDGE] >= columnCount)
        {
            throw new SliceOutOfRangeException(endSlice[DataDirector.COLUMN_EDGE],
                                               columnCount-1);
        }
        
        boolean result = true;
        
        if (startSlice.length >= 2 && endSlice.length >= 2)
        {
            if (startSlice[DataDirector.ROW_EDGE] < 0 ||
                startSlice[DataDirector.ROW_EDGE] >= rowCount)
            {
                throw new SliceOutOfRangeException(startSlice[DataDirector.ROW_EDGE],
                                                   rowCount-1);
            }
            
            if (endSlice[DataDirector.ROW_EDGE] < 0 ||
                endSlice[DataDirector.ROW_EDGE] >= rowCount)
            {
                throw new SliceOutOfRangeException(endSlice[DataDirector.ROW_EDGE],
                                                   rowCount-1);
            }
            
            if (endSlice[DataDirector.ROW_EDGE] > m_fetchedRowCount - 1)
                result = false;
        }
        
        if (startSlice.length >= 3 && endSlice.length >= 3)
        {
            if (startSlice[DataDirector.PAGE_EDGE] < 0 ||
                startSlice[DataDirector.PAGE_EDGE] >= pageCount)
            {
                throw new SliceOutOfRangeException(startSlice[DataDirector.PAGE_EDGE],
                                                   pageCount-1);
            }
            
            if (endSlice[DataDirector.PAGE_EDGE] < 0 ||
                endSlice[DataDirector.PAGE_EDGE] >= pageCount)
            {
                throw new SliceOutOfRangeException(endSlice[DataDirector.PAGE_EDGE],
                                                   pageCount-1);
            }
        }
        
        return result;
    }
    
    public boolean forceFetch(int[] startSlice, int[] endSlice) throws SliceOutOfRangeException
    {
        if (startSlice == null || endSlice == null)
            return false;
        
        if (startSlice.length < 1 || endSlice.length < 1)
            return false;
        
        if (startSlice[DataDirector.COLUMN_EDGE] < 0 ||
            startSlice[DataDirector.COLUMN_EDGE] >= columnCount)
        {
            throw new SliceOutOfRangeException(startSlice[DataDirector.COLUMN_EDGE],
                                               columnCount-1);
        }
        
        if (endSlice[DataDirector.COLUMN_EDGE] < 0 ||
            endSlice[DataDirector.COLUMN_EDGE] >= columnCount)
        {
            throw new SliceOutOfRangeException(endSlice[DataDirector.COLUMN_EDGE],
                                               columnCount-1);
        }
        
        if (startSlice.length >= 2 && endSlice.length >= 2)
        {
            if (startSlice[DataDirector.ROW_EDGE] < 0 ||
                startSlice[DataDirector.ROW_EDGE] >= rowCount)
            {
                throw new SliceOutOfRangeException(startSlice[DataDirector.ROW_EDGE],
                                                   rowCount-1);
            }
            
            if (endSlice[DataDirector.ROW_EDGE] < 0 ||
                endSlice[DataDirector.ROW_EDGE] >= rowCount)
            {
                throw new SliceOutOfRangeException(endSlice[DataDirector.ROW_EDGE],
                                                   rowCount-1);
            }
            
            m_fetchedRowCount = endSlice[DataDirector.ROW_EDGE] + 1;
            //System.out.println("forceFetch to row " + (m_fetchedRowCount - 1) +
            //                   "/" + (rowCount - 1) + " on page " + currentPage);
        }
        
        if (startSlice.length >= 3 && endSlice.length >= 3)
        {
            if (startSlice[DataDirector.PAGE_EDGE] < 0 ||
                startSlice[DataDirector.PAGE_EDGE] >= pageCount)
            {
                throw new SliceOutOfRangeException(startSlice[DataDirector.PAGE_EDGE],
                                                   pageCount-1);
            }
            
            if (endSlice[DataDirector.PAGE_EDGE] < 0 ||
                endSlice[DataDirector.PAGE_EDGE] >= pageCount)
            {
                throw new SliceOutOfRangeException(endSlice[DataDirector.PAGE_EDGE],
                                                   pageCount-1);
            }
        }
        
        return true;
    }
    
    public DataMap getSupportedDataMap()
    {
        String supported[] = {DataMap.DATA_UNFORMATTED};
        return (new DataMap(supported));
    }
    
    public MetadataMap getSupportedMetadataMap()
    {
        String supported[] =  {MetadataMap.METADATA_LONGLABEL,
                               MetadataMap.METADATA_MEDIUMLABEL,
                               MetadataMap.METADATA_SHORTLABEL,
                               MetadataMap.METADATA_DATE,
                               MetadataMap.METADATA_VIEWSTYLE,
                               MetadataMap.METADATA_VALUE}; // TODO hugh update
        return (new MetadataMap(supported));
    }
    
    public LayerMetadataMap getSupportedLayerMetadataMap()
    {
         
        String supported[] = { LayerMetadataMap.LAYER_METADATA_LONGLABEL,
                              LayerMetadataMap.LAYER_METADATA_MEDIUMLABEL,
                              LayerMetadataMap.LAYER_METADATA_SHORTLABEL};
        return (new LayerMetadataMap(supported));
    } 
    
    // Listeners for the data changes
    private Vector m_dataDirectorListeners = new Vector();
}                           // BaseLocalDataSource