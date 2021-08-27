/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/dataSource/DrillDataSource.java /main/3 2010/07/07 11:14:17 ytang Exp $ */

/* Copyright (c) 2009, 2010, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    smasalia    09/04/09 - new standalone Drillable datasource
    smasalia    09/04/09 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/dataSource/DrillDataSource.java /main/3 2010/07/07 11:14:17 ytang Exp $
 *  @author  smasalia
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.graph.dataSource;


import oracle.dss.util.DataDirector;
import oracle.dss.util.DataDirectorListener;
import oracle.dss.util.DataAvailableEvent;
import oracle.dss.util.DataChangedEvent;
import oracle.dss.util.DataAccessAdapter;
import oracle.dss.util.DataMap;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.LayerMetadataMap;
import oracle.dss.util.CubeDataAccess;
import oracle.dss.util.CubeDataDirector;
import oracle.dss.util.DataSource;
import oracle.dss.util.RelationalDataAccess;
import oracle.dss.util.RelationalDataDirector;
import oracle.dss.util.LayoutAccess;
import oracle.dss.util.LayoutContext;
import oracle.dss.util.QDR;

import java.util.Iterator;
import java.util.ArrayList;

import oracle.dss.util.ColumnOutOfRangeException;
import oracle.dss.util.DataDirectorException;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.RowOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;


public class DrillDataSource extends DataAccessAdapter
                                   implements DataSource,
                                              CubeDataAccess, CubeDataDirector,
                                              RelationalDataAccess, RelationalDataDirector,
                                              LayoutAccess, LayoutContext
{                           // DrillDataSource
    // Dimension names
    private static final String MEASURE   = "Measure";
    private static final String TIME      = "Time";
    private static final String GEOGRAPHY = "Geography";
    private static final String MAKE= "CarMake";
    private static final String HONDA= "Honda";
    private static final String BMW= "BMW";
    private static final String MB= "Mercedes-Benz";
    private static final String SERIES_3= "Series 3";
    private static final String SERIES_5= "Series 5";
    private static final String SERIES_7= "Series 7";
    private static final String LONDON= "London";
    private static final String TOTAL= "Total";
    
    // Edge vectors holding information about dimensions
    private ArrayList m_vEdgeLayers = new ArrayList(4);

    // Listeners for the data changes
    private ArrayList m_dataDirectorListeners = new ArrayList();

    // The dimensions
    protected String[] m_dimensions = {MEASURE, TIME, TOTAL, GEOGRAPHY, MAKE, BMW, MB, HONDA, SERIES_3, SERIES_5, SERIES_7, LONDON};
    // Dimension members
    protected String[][] m_members = {
                                        {"Sales"},
                                        {"2007"},
                                        {"Total"},
                                        {"London", "Paris"},
                                        {"BMW","Mercedes-Benz","Honda"},
                                        {"Series 3","Series 5","Series 7"},
                                        {"E-class","S-class","ML-class","G-class"},
                                        {"Accord","Insight","Fit","Pilot","Element"},
                                        {"X3","Z3","M3","325xi"},
                                        {"525xi","535xi","M5"},
                                        {"760Li","740d"},
                                        {"North","South"}
                                   };

    // Current Page slice
    private int m_currentPage = 0;

    private double [][]data;
    
    private int m_rowDrillLevel = 0;
    private int m_colDrillLevel = 0;

    public DrillDataSource()
    {                       // default constructor
        // Initialize the layout
        ArrayList rowLayers = new ArrayList();
        rowLayers.add(MAKE);

        
        ArrayList columnLayers = new ArrayList();
        columnLayers.add(TOTAL);
        columnLayers.add(MEASURE);
                    
        ArrayList pageLayers = new ArrayList();
        pageLayers.add(TIME);
        
        ArrayList hiddenLayers = new ArrayList();
        
        m_vEdgeLayers.add(columnLayers);
        m_vEdgeLayers.add(rowLayers);
        m_vEdgeLayers.add(pageLayers);
        m_vEdgeLayers.add(hiddenLayers);

        // fill in the data array
        try{
            data = new double[getEdgeExtent(DataDirector.ROW_EDGE)][getEdgeExtent(DataDirector.COLUMN_EDGE)];
            for (int row = 0; row < getEdgeExtent(DataDirector.ROW_EDGE); row++)
                for (int col = 0; col < getEdgeExtent(DataDirector.COLUMN_EDGE); col++)
                    data[row][col] = (double)((row%3)*20+(col%4)*25+200);
        }catch(EdgeOutOfRangeException e){
            e.printStackTrace();
        }

    }                       // default cconstructor

    public RelationalDataDirector createRelationalDataDirector()
    {
        return this;
    }
    public DataDirector createDataDirector()
    {
        return this;
    }
    
    public CubeDataDirector createCubeDataDirector() 
    {
        return this;
    }


    public void addDataDirectorListener(DataDirectorListener listener)
    {
        m_dataDirectorListeners.add(listener);
        // Notify the listener that data is available
        if (listener != null)
        {
            listener.viewDataAvailable(new DataAvailableEvent(this, this));
        }
    }
    
    public void removeDataDirectorListener(DataDirectorListener l) 
    { 
        m_dataDirectorListeners.remove(l);
    }

    // Notifies all DataDirectorListeners that view data is available
    private void notifyDataDirectorListeners()
    {
        for (Iterator e = m_dataDirectorListeners.iterator(); e.hasNext(); )
        {
            DataDirectorListener listener = (DataDirectorListener)e.next();
            listener.viewDataAvailable(new DataAvailableEvent(this, this));
        }
    }

   

    
    /**
     * Retrieves the total number of members on the given edge.
     * cursor.
     *
     * @param   edge Edge of interest.
     * @return   The total number of members on the given edge.
     *
     * @status New
     */
    public int getEdgeExtent(int edge) throws EdgeOutOfRangeException 
    {   
        // Get the number of layers for that edge
        int layerCount = getLayerCount(edge);
        int extent = 1;
        
        ArrayList edgeLayers = (ArrayList)m_vEdgeLayers.get(edge);

        for (Iterator e = edgeLayers.iterator(); e.hasNext();)
        {
            String layerName = (String)e.next();
            
            String[] members = getLayerMembers(layerName);
            
            if (members != null)
            {
                extent = extent * members.length;
            }
        }

        return extent;
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
     *
     * @status New
     */
    public int getEdgeCount() 
    {
        // Returns the number of edges (usually 3 + hidden)
        return m_vEdgeLayers.size();
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
     * @status New
     */
    public int getLayerCount(int edge) throws EdgeOutOfRangeException
    {
        int edgeCount = getEdgeCount();
        
        if (edge >= edgeCount)
        {
            throw new EdgeOutOfRangeException(edge, edgeCount);
        }
        
        // If the edge constant is data element edge, return the number of Measures
        if (edge == DataDirector.DATA_ELEMENT_EDGE)
        {
            return m_members[0].length;
        }

        ArrayList edgeLayers = (ArrayList)m_vEdgeLayers.get(edge);
        
        return (edgeLayers.size());
    }


    /**
     * Retrieves the specified type of metadata for a member at the specified
     * location.
     *
     * @param edge  A constant that represents the edge in which to look for
     *              the member metadata.
     *              The constants end with <code>_EDGE</code> in
     *              <code>DataDirector</code>.
     * @param layer The zero-based index of the layer for which metadata is
     *              requested.
     *              Uses a depth computation to identify the
     *              location of the layer or layer member;
     *              0 is the outer-most layer on the edge.
     *              The value for this parameter can be obtained by calling
     *              <code>getMemberLogicalLayer</code>.
     * @param type  A constant that specifies the kind of metadata that
     *              you want.
     *              Valid <code>type</code> values are defined in the
     *              <code>LayerMetadataMap</code>.
     *
     * @return The requested metadata.
     *         This implementation returns <coded>null</code>.
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is too large.
     * @throws LayerOutOfRangeException If <code>layer</code> is negative or
     *             too large.
     *
     * @see LayerMetadataMap
     * @see DataDirector#COLUMN_EDGE
     * @see DataDirector#PAGE_EDGE
     * @see DataDirector#ROW_EDGE
     *
     * @status New
     */
    public Object getLayerMetadata(int edge, int layer, String type) throws EdgeOutOfRangeException, LayerOutOfRangeException 
    {
        int layerCount = getLayerCount(edge);
        
        ArrayList edgeLayers = (ArrayList)m_vEdgeLayers.get(edge);
        
        if (layer < 0 || layer >= layerCount)
        {
            throw new LayerOutOfRangeException(layer, layerCount);
        }
        // Return layer name
        return (String)edgeLayers.get(layer);
    }


    
    /**
     *      * Retrieves the label for the specified column.
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
     * @status New     
     */
     public Object getSliceLabel(int edge, int slice, String type) 
                     throws EdgeOutOfRangeException, SliceOutOfRangeException 
     {
         int edgeExtent = getEdgeExtent(edge);
         
         // Check if the slice is out of range
         if (slice < 0 || slice >= edgeExtent)
             throw new SliceOutOfRangeException(slice, edgeExtent);

         // Get the number of layers for that edge
         int layerCount = getLayerCount(edge);

         String label = "";            
            
         for (int i = 0; i < getLayerCount(edge); i++)
         {
             try
             {
                 label = label + getMemberMetadata(edge, i, slice, type) + " ";
             }
             catch (Exception e)
             {
                 e.printStackTrace();
             }
         }

         return label.trim();
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
     * @throws SliceOutOfRangeException If <code>slice</code> is negative or
     *              too large.
     *
     * @status New
     */
    public Object getMemberMetadata(int edge, int layer, int slice, String type) 
            throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException
    {
        //System.out.println("edge:" + edge + " layer:" + layer + " slice:" +slice + " type:" + type);
        int edgeExtent = getEdgeExtent(edge);
         
        // Check if the slice is out of range
        if (slice < 0 || slice >= edgeExtent)
             throw new SliceOutOfRangeException(slice, edgeExtent);

        if (type.equals(MetadataMap.METADATA_INDENT))
        {
            return new Integer(0);
        }
        
        if(type.equals(MetadataMap.METADATA_VIEWSTYLE)) {
            return null;
        }
        
        if(type.equals(MetadataMap.METADATA_DATE)) 
            return null;
     
        if(type.equals(MetadataMap.METADATA_DRILL_PARENT_LONGLABEL))
        {
            String layerName = (String)getLayerMetadata(edge,layer,LayerMetadataMap.LAYER_METADATA_NAME);
            if (layerName.equals(GEOGRAPHY))
                return TOTAL;
            else if (layerName.equals(BMW))
                return BMW;
            else if (layerName.equals(MB))
                return MB;
            else if (layerName.equals(HONDA))
                return HONDA;
            else if (layerName.equals(SERIES_3))
                return SERIES_3;
            else if (layerName.equals(SERIES_5))
                return SERIES_5;
            else if (layerName.equals(SERIES_7))
                return SERIES_7;
            else if (layerName.equals(LONDON))
                return LONDON;
            return null;
        }

        // Otherwise, return the member name     
        // Get the member extent
        int extent = getMemberExtent(edge, layer, slice);
        String layerName = (String)getLayerMetadata(edge, layer, type);

        String[] members = getLayerMembers(layerName);
    
        if (members != null)
        {
            // Get the member at this layer at this slice
            int memberIndex = (slice/extent) % members.length;
        
            if (memberIndex < members.length)
            {
                return members[memberIndex];
            }
        }

        return null;
    }

    
    /**
     * Retrieves the specified type of metadata for the member at a
     * specified location.
     * For example, this method is useful for retrieving labels for display in
     * views.
     * <p>
     * The user of this method makes the following calls:
     * <ul>
     * <li> Calls <code>getMemberSiblingCount</code> to retrieve the number of
     * sibling members for a specified tree of members.
     * <li> Calls this method once for each sibling member to retrieve the
     * specified metadata for that member.
     * </ul>
     * The call for this method uses the same <code>hPos</code> and
     * <code>memberLayer</code> parameters as found in
     * <code>getMemberSiblingCount</code> to specify the parent tree of members.
     * Each call of this method also uses the <code>hIndex</code> parameter
     * that indicates the specific sibling member for which to return metadata.
     * Valid values for <code>hIndex</code> range from 0 to
     * <code>(getMemberSiblingCount() - 1)</code>.
     *
     * @param edge   A constant that represents the edge of interest.
     *               The constants end with <code>_EDGE</code> in
     *               <code>DataDirector</code>.
     * @param hPos   An array of relative indexes, organized by logical layer,
     *               for the parent tree of members above the sibling member
     *               that is specified by <code>hIndex</code>. The array
     *               includes entries for layer 0 through at least the layer
     *               that is specified by <code>memberLayer</code>.
     * @param memberLayer The zero-based index into the <code>hPos</code> array
     *               for which the call should address the set of sibling
     *               members.
     * @param hIndex The zero-based index of a sibling member relative to its
     *               siblings. This index specifies the sibling member for which
     *               to return the specified metadata.
     * @param type   A constant that specifies the kind of metadata to return.
     *               Valid <code>type</code> values are defined in
     *               <code>MetadataMap</code>.
     *
     * @return  The requested metadata for the member at the specified location.
     *          This implementation returns <coded>null</code>.
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is too large.
     * @throws LayerOutOfRangeException If <code>memberLayer</code> is too
     *             large.
     * @throws SliceOutOfRangeException If any slice in <code>hPos</code>
     *             is too large, or if the <code>hIndex</code> is too large.
     *
     * @see DataDirector#COLUMN_EDGE
     * @see DataDirector#PAGE_EDGE
     * @see DataDirector#ROW_EDGE
     *
     * @status New
     */
    public Object getMemberMetadata(int edge, int[] hPos, int memberLayer, int hIndex, String type) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException
    {
        String layerName = (String)getLayerMetadata(edge, memberLayer, LayerMetadataMap.LAYER_METADATA_NAME);
        String[] members = getLayerMembers(layerName);
        
        if ((members != null) && (hIndex < members.length))
        {
            return members[hIndex];
        }

        return null;
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
     * @status New
     */
    public int getSliceMemberCount(int edge, int slice) throws EdgeOutOfRangeException, SliceOutOfRangeException 
    {
        int edgeExtent = getEdgeExtent(edge);
         
        // Check if the slice is out of range
        if (slice < 0 || slice >= edgeExtent)
        {
            throw new SliceOutOfRangeException(slice, edgeExtent);
        }
             
        return getLayerCount(edge);
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
     * @status New
     */
    public int getMemberExtent(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException 
    {
        int edgeExtent = getEdgeExtent(edge);
         
        // Check if the slice is out of range
        if (slice < 0 || slice >= edgeExtent)
             throw new SliceOutOfRangeException(slice, edgeExtent);

        // Get the number of layers for that edge
        int layerCount = getLayerCount(edge);
        int memberCount = 0;
        int extent = 1;
        String[] members = null;

        // Calculate the extent for the members at that layer        
        for (int i=layerCount-1; i>layer; i--)
        {
            String layerName = (String)getLayerMetadata(edge, i, LayerMetadataMap.LAYER_METADATA_NAME);
            members = getLayerMembers(layerName);
            memberCount = members.length;
            
            if (members != null)
            {
                extent = extent * memberCount;
            }
        }
         
        return extent;
    }

    /**
     * Retrieves the absolute number of the first slice that a member spans.
     * Views use this method to determine where to start painting
     * a member label in a row or column header.
     * Views pass the same parameter
     * values to this method and to <code>getMemberExtent</code>.
     * <p>
     *
     * @param edge  A constant that represents the edge in which to look for
     *              the <code>layer</code> and <code>slice</code>.
     *              The constants end with <code>_EDGE</code> in
     *              <code>DataDirector</code>.
     * @param layer The zero-based index of the physical layer of the member
     *              whose first slice will be returned.
     *              Valid values are zero to total layers at the specified
     *              slice.
     * @param slice An absolute index (zero-based) that indicates the location
     *              along the specified edge of the member whose starting slice
     *              will be returned.
     *              Each member in the innermost layer has a unique slice.
     *              In outer layers, any slice that the outer member spans
     *              can be used.
     *
     * @return The absolute index of the first slice that the member spans.
     *         This implementation returns zero.
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is too large.
     * @throws LayerOutOfRangeException If <code>layer</code> is negative or
     *                too large.
     * @throws SliceOutOfRangeException If <code>slice</code> is negative or
     *                too large.
     *
     * @see #getMemberExtent
     * @see DataDirector#COLUMN_EDGE
     * @see DataDirector#PAGE_EDGE
     * @see DataDirector#ROW_EDGE
     *
     * @status New
     */
    public int getMemberStartSlice(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException 
    {
        int extent = getMemberExtent(edge, layer, slice);
        
        // Get the starting slice of the member at this layer at this slice
        return (slice - (slice % extent));    
    }

    /**
     * Retrieves the size (expressed in layers that are perpendicular to
     * the specified edge orientation) of a member at the specified
     * layer, slice, and edge.
     * In other words, this method returns the following information depending
     * upon the specified edge:
     * <ul>
     * <li>
     * Returns the "height" of a member in the Crosstab's column edge header,
     * that is, the number of heading rows for the member.
     * <li>
     * Returns the "width" of a member in the Crosstab's row edge header, that
     * is the number of heading columns for the member.
     * </ul>
     *<p>
     * The sum of all the members' values for this method, within a specified
     * slice, should not exceed the value that <code>getLayerCount</code>
     * returns for the specified edge.
     * <P>
     * This method is useful only in cases of asymmetry where the members
     * on an edge do not all cover one layer and have a depth of 1.
     * On a symmetric edge, all members have a member depth of 1.
     * <p>
     *
     * @param edge  A constant that represents the edge in which to look for
     *              the <code>layer</code> and <code>slice</code>.
     *              The constants end with <code>_EDGE</code> in
     *              <code>DataDirector</code>.
     * @param layer The zero-based index of the starting physical
     *              layer in which to look for the slice,
     *              as returned by <code>getMemberStartLayer</code>.
     * @param slice An absolute index (zero-based) that indicates the location
     *              of a slice along the specified edge.
     *              Each member in the innermost layer has a unique slice.
     *              In outer layers, any slice that the
     *              outer member spans can be used.
     *
     * @return    The depth (that is, the number of physical layers) that the
     *            specified member covers.
     *            This implementation returns zero.
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is too large.
     * @throws LayerOutOfRangeException If <code>layer</code> is negative or
     *            too large.
     * @throws SliceOutOfRangeException If <code>slice</code> is negative or
     *            too large.
     *
     * @see DataDirector#COLUMN_EDGE
     * @see DataDirector#PAGE_EDGE
     * @see DataDirector#ROW_EDGE
     *
     * @status Documented
     */
    public int getMemberDepth(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException 
    {
        return 1;
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
     * @status New
     */
    public int getEdgeCurrentSlice(int edge) throws EdgeOutOfRangeException
    {
        return m_currentPage;
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
     * @status New
     */
    public boolean changeEdgeCurrentSlice(int edge, int slice) throws EdgeOutOfRangeException, SliceOutOfRangeException, DataDirectorException
    {
        if (slice < 0 || slice >= getEdgeExtent(DataDirector.PAGE_EDGE))
            return false;
        m_currentPage = slice;
        
        // Notify the listeners that page has changed
        for (Iterator e = m_dataDirectorListeners.iterator(); e.hasNext(); )
        {
            DataDirectorListener listener = (DataDirectorListener)e.next();
            listener.viewDataChanged(new DataChangedEvent(this, this, DataChangedEvent.PAGE_CHANGE, true, false, false, true));
        }

        return true;
    }


    /**
     * Retrieves, for the specified edge, the hierarchical address of the
     * location that is defined by the implementor as the "current" location.
     * The row and column edges of view do not require support
     * for the concept of a current location.
     * This concept is usually associated with a paging control on the page
     * edge of a view.
     *
     * @param edge  A constant that represents the edge of interest.
     *              The constants end with <code>_EDGE</code> in
     *              <code>DataDirector</code>.
     *
     * @return The hPos array that represents the current location on the
     *         specified edge. When this method is called for the page edge,
     *         the hPos that represents the page that is currently displayed
     *         in a Crosstab.
     *         This implementation returns <coded>null</code>.
     *
     * @throws EdgeOutOfRangeException If the parameter <code>edge</code> is
     *                                 not valid.
     *
     * @see DataDirector#COLUMN_EDGE
     * @see DataDirector#PAGE_EDGE
     * @see DataDirector#ROW_EDGE
     *
     * @status New
     */
    public int[] getEdgeCurrentHPos(int edge) throws EdgeOutOfRangeException 
    {
        try
        {
            return getMemberHPos(edge, getLayerCount(edge)-1, getEdgeCurrentSlice(edge));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves the hierarchical address for an absolute address that is
     * specified as edge, physical layer, and slice.
     * This method constructs an array that can be used in creating
     * the <code>hPos</code> parameter in calls such as
     * <code>getMemberSiblingCount</code> and
     * <code>getMemberMetadata</code>.
     * <p>
     * As an example, this method can retrieve an array of indexes from a
     * page edge.
     * The array includes an index for each layer in the page edge.
     * Consider pivoting the following column header to a page edge.
     * <P>
     * <Table border = 1>
     * <tr><td colspan=7>June</td><td colspan=5>July</td> </tr>
     * <tr><td colspan=3>Tents</td><td colspan=4>Canoes</td>
     * <td colspan=3>Tents</td><td colspan=2>Canoes</td></tr>
     * <tr><td>NY</td><td>Boston</td><td>LA</td>
     * <td>Toronto</td><td>Winnepeg</td><td>NY</td><td>LA</td>
     * <td>NY</td><td>Boston</td><td>LA</td>
     * <td>Toronto</td><td>Winnepeg</td></tr>
     * </Table>
     * <p>
     * If the current page displayed June, Canoes, and Winnepeg, this
     * method would return the array {0,1,1}.
     * June is at index 0 in layer 0.
     * Canoes is at index 1 in layer 1 under June.
     * Winnepeg is at index 1 in layer 2 under Canoes.
     * To find the number of members under Canoes under June,
     * call <code>getMemberSiblingCount</code> with the array {0,1,1} and the
     * <code>memberLayer</code> parameter 2.
     *
     * @param edge  A constant that represents the edge in which to look for
     *              the <code>slice</code>.
     *              The constants end with <code>_EDGE</code> in
     *              <code>DataDirector</code>.
     * @param layer The zero-based index of the physical layer of the member
     *              whose hierarchical address will be returned.
     * @param slice An absolute index (zero-based) along an edge that indicates
     *              the location of the slice whose hierarchical location will
     *              be returned.
     *              Each member in the innermost layer has a unique slice.
     *              In outer layers, any slice that the
     *              outer member spans can be used.
     *
     * @return An array of relative indexes. There is one index for each
     *         layer in the edge up to and including the layer argument,
     *         with -1 representing non-starting layers when a member happens
     *         to span more than one layer.
     *         This implementation returns <coded>null</code>.
     *
     * @see DataDirector#COLUMN_EDGE
     * @see DataDirector#PAGE_EDGE
     * @see DataDirector#ROW_EDGE
     *
     * @status New
     */
    public int[] getMemberHPos(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException 
    {
        int[] hpos = new int[layer+1];
        
        for (int l=0; l<=layer; l++)
        {
            int extent = getMemberExtent(edge, l, slice);
            String layerName = (String)getLayerMetadata(edge, l, LayerMetadataMap.LAYER_METADATA_NAME);
            String[] members = getLayerMembers(layerName);
    
            if (members != null)
            {
                // Get the member index at this layer at this slice
                hpos[l] = (slice/extent) % members.length;
            }
        }
        
        return hpos;
    }

    /**
     * Retrieves the count of all the sibling members at a specified logical
     * layer based on a series of relative indexes for each logical layer down
     * to the specified logical layer.
     * This method is useful in populating a page control for a view.
     *
     * For an example, consider the following column header.
     * <P>
     * <Table border = 1>
     * <tr><td colspan=7>June</td><td colspan=5>July</td> </tr>
     * <tr><td colspan=3>Tents</td><td colspan=4>Canoes</td>
     * <td colspan=3>Tents</td><td colspan=2>Canoes</td></tr>
     * <tr><td>NY</td><td>Boston</td><td>LA</td>
     * <td>Toronto</td><td>Winnepeg</td><td>NY</td><td>LA</td>
     * <td>NY</td><td>Boston</td><td>LA</td>
     * <td>Toronto</td><td>Winnepeg</td></tr>
     * </Table>
     * <p>
     * A caller can use this method to find the number of
     * Geography values (that is, sibling members) under the members
     * July and Tents. This count is 3 (for NY, Boston, and LA). The <code>hPos</code> and <code>memberLayer</code>
     * parameters for this example are as follows:
     * <ul> <li>
     * <code>hPos</code>, an array of relative indexes, holds an entry
     * for July, Tents, and any one of the siblings that are to be counted.
     * A valid <code>hPos</code> in this example must contain indexes for at
     * least layers 0, 1, and 2. Valid arrays for this example could be any of
     * the following: {1,0,0} or {1,0,1} or {1,0,2).
     * <li>
     * <code>memberLayer</code>, which identifies the layer at which to count
     * the siblings, must be 2. </ul>
     * <p>
     * Note: The value that is returned by this method provides the upper limit
     * to the <code>hIndex</code> that a caller passes in
     * <code>getMemberMetadata</code>.
     * <p>
     * This implementation returns 0.
     *
     * @param edge A constant that represents the edge of interest.
     *             The constants end with <code>_EDGE</code> in
     *             <code>DataDirector</code>.
     * @param hPos An array that contains an index for the member values
     *             at each layer up to and including the layer of the sibling
     *             members that are to be counted.
     *             These indexes are relative to the number of
     *             members at each layer.
     *             When a member spans more than one layer, any layers beyond
     *             the starting layer should have their place held by a
     *             -1 index.
     * @param memberLayer The index into the <code>hPos</code> array for which
     *                    the call should return the sibling member count.
     *
     * @return The number of sibling members that are found on the specified
     *         edge at the location that is specified by <code>hPos</code> and
     *         <code>memberLayer</code>.
     *         In the example, this method returns 3 (for the siblings NY,
     *         Boston, and LA).
     *         
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is too large.
     * @throws LayerOutOfRangeException If <code>memberLayer</code> is too
     *             large.
     * @throws SliceOutOfRangeException If any slice in <code>hPos</code>
     *             is too large.
     *
     * @see #getMemberMetadata
     * @see DataDirector#COLUMN_EDGE
     * @see DataDirector#PAGE_EDGE
     * @see DataDirector#ROW_EDGE
     *
     * @status New
     */
    public int getMemberSiblingCount(int edge, int[] hPos, int memberLayer) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException 
    {
        String layerName = (String)getLayerMetadata(edge, memberLayer, LayerMetadataMap.LAYER_METADATA_NAME);
        String[] members = getLayerMembers(layerName);

        if (members != null)
        {
            return members.length;
        }

        return 0;
    }

    
    /**
     * Indicates whether the specified pivot operation would be valid for the
     * current data layout.
     * This method is called before the <code>pivot</code> method and does not
     * perform the actual pivot.
     * The data provider determines its own rules for valid pivot operations.
     * <p>
     * This implementation returns <code>false</code>.
     * 
     * @param fromEdge  A constant that indicates the edge from which a layer
     *                  should be pivoted. Valid constants end with
     *                  <code>_EDGE</code> in this interface.
     * @param toEdge    A constant that indicates the edge to which a layer
     *                  should be pivoted. Valid constants end with
     *                  <code>_EDGE</code> in this interface.
     * @param fromLayer The zero-based index of the physical layer on the
     *                  <code>fromEdge</code> that should be pivoted or swapped.
     *                  The outermost layer on the edge is 0.
     *                  Note that a Table object will report columns as its
     *                  layers.
     * @param toLayer   The zero-based index of the physical layer on the
     *                  <code>toEdge</code> that should be pivoted or swapped.
     *                  The outermost layer on the edge is 0.
     *                  Note that a Table object will report columns as its
     *                  layers.
     * @param flags     A constant that indicates the type of pivot to perform.
     *                  The constants begin with <code>PIVOT_</code>
     *                  in this interface.
     *
     * @return <code>true</code> if the pivot is legal according to the data
     *                           provider's rules,
     *         <code>false</code> if the pivot is not legal according to the
     *                           data provider's rules.
     *
     * @throws EdgeOutOfRangeException If either edge is too
     *                  large.
     * @throws LayerOutOfRangeException If either depth is negative or too
     *                  large.
     * @throws DataDirectorException If a data provider error occurs.
     *
     * @see #COLUMN_EDGE
     * @see #DATA_ELEMENT_EDGE
     * @see #PAGE_EDGE
     * @see #ROW_EDGE
     * @see #PIVOT_EDGES
     * @see #PIVOT_MOVE_AFTER
     * @see #PIVOT_MOVE_BEFORE
     * @see #PIVOT_SWAP
     *
     * @status New
     */
    public boolean pivotOK(int fromEdge, int toEdge, int fromLayer, int toLayer, int flags) 
        throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException
    {  
        // Check if edges and layers are in range
        int edgeCount = getEdgeCount();
        
        int fromEdgeLayerCount = getLayerCount(fromEdge);
        int toEdgeLayerCount = getLayerCount(toEdge);
        
        if (fromEdge >= edgeCount)
        {
            throw new EdgeOutOfRangeException(fromEdge, edgeCount);
        }
        
        if (toEdge >= edgeCount)
        {
            throw new EdgeOutOfRangeException(toEdge, edgeCount);
        }

        if (fromLayer < 0 || fromLayer > fromEdgeLayerCount)
        {
            throw new LayerOutOfRangeException(fromLayer, fromEdgeLayerCount);
        }
           
        if (toLayer < 0 || toLayer > toEdgeLayerCount)
        {
            throw new LayerOutOfRangeException(toLayer, toEdgeLayerCount);
        }
    
        if (fromEdge < 0 || toEdge < 0)
            return false;

        // In all other cases, allow pivot    
        return true;
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
     * @status New     
     */
    public boolean pivot(int fromEdge, int toEdge, int fromLayer, int toLayer, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException 
    {
        // Check if edges and layers are in range
        int edgeCount = getEdgeCount();
        
        int fromEdgeLayerCount = getLayerCount(fromEdge);
        int toEdgeLayerCount = getLayerCount(toEdge);

        if (fromEdge >= edgeCount)
        {
            throw new EdgeOutOfRangeException(fromEdge, edgeCount);
        }

        if (toEdge >= edgeCount)
        {
            throw new EdgeOutOfRangeException(toEdge, edgeCount);
        }

        if (fromLayer < 0 || fromLayer > fromEdgeLayerCount)
        {
            throw new LayerOutOfRangeException(fromLayer, fromEdgeLayerCount);
        }
           
        if (toLayer < 0 || toLayer > toEdgeLayerCount)
        {
            throw new LayerOutOfRangeException(toLayer, toEdgeLayerCount);
        }
        
        // Perform pivot
        ArrayList fromEdgeLayers = (ArrayList)m_vEdgeLayers.get(fromEdge);
        ArrayList toEdgeLayers = (ArrayList)m_vEdgeLayers.get(toEdge);
        
        // Get layer names
        String fromLayerName = null;
        String toLayerName = null;
        
        if (fromLayer < fromEdgeLayers.size())
        {
            fromLayerName = (String)fromEdgeLayers.get(fromLayer);
        }
        
        if (toLayer < toEdgeLayers.size())
        {
            toLayerName = (String)toEdgeLayers.get(toLayer);
        }
        
        switch (flags)
        {
            case DataDirector.PIVOT_MOVE_BEFORE:
                fromEdgeLayers.remove(fromLayer);
                toEdgeLayers.add(toLayer,fromLayerName);
                break;
            case DataDirector.PIVOT_MOVE_AFTER:
                if (fromEdge != toEdge)
                {
                   toLayer++;
                }
                fromEdgeLayers.remove(fromLayer);
                toEdgeLayers.add(toLayer,fromLayerName);
                break;
            case DataDirector.PIVOT_SWAP:
                fromEdgeLayers.set(fromLayer, toLayerName);
                toEdgeLayers.set(toLayer, fromLayerName);
                break;
        }
        
        //System.out.println("From layers: " + fromEdgeLayers + "; To Layers: " + toEdgeLayers);
        
        // Notify the listeners that the data has changed as a result of the pivot operation  
        for (Iterator e = m_dataDirectorListeners.iterator(); e.hasNext(); )
        {
            DataDirectorListener listener = (DataDirectorListener)e.next();
            listener.viewDataChanged(new DataChangedEvent(this, this, DataChangedEvent.PIVOT_CHANGE, true, true, true, true));
        }
        return true;
    } 
     
    /**
     * Drills in the data, from a single member.
     *
     * @param edge   The edge to drill.
     * @param layer  The layer to drill. The layer identifies the
     *                  location of the dimension or dimension member; 0 is the
     *                  outer-most dimension on the edge.
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
        //System.out.println("edge:" + edge + " layer:" + layer + " slice:" +slice + " flags:" + flags);
        boolean drill = false;
        if ( edge == DataDirector.ROW_EDGE ){
            if ( m_rowDrillLevel == 0 ){
                if (flags == DataDirector.DRILL_REPLACE)
                {
                    String name = MAKE;
                    switch (slice)
                    {
                        case 0: name = BMW; break;
                        case 1: name = MB; break;
                        case 2: name = HONDA; break;
                    }
                    ArrayList edgeLayers = (ArrayList)m_vEdgeLayers.get(edge);
                    edgeLayers.remove(layer);
                    edgeLayers.add(layer,name);
                    try
                    {
                        data = new double[getEdgeExtent(DataDirector.ROW_EDGE)][getEdgeExtent(DataDirector.COLUMN_EDGE)];
                        for (int row = 0; row < getEdgeExtent(DataDirector.ROW_EDGE); row++)
                            for (int col = 0; col < getEdgeExtent(DataDirector.COLUMN_EDGE); col++)
                                data[row][col] = Math.round(Math.random()*100);
                    }
                    catch(EdgeOutOfRangeException e)
                    {
                        e.printStackTrace();
                    }
                    drill = true;
                }
            }
            else if ( m_rowDrillLevel == 1 ){
                if (flags == DataDirector.DRILL_REPLACE)
                {
                    String name = BMW;
                    switch (slice)
                    {
                        case 0: name = SERIES_3; break;
                        case 1: name = SERIES_5; break;
                        case 2: name = SERIES_7; break;
                    }
                    ArrayList edgeLayers = (ArrayList)m_vEdgeLayers.get(edge);
                    edgeLayers.remove(layer);
                    edgeLayers.add(layer,name);
                    try
                    {
                        data = new double[getEdgeExtent(DataDirector.ROW_EDGE)][getEdgeExtent(DataDirector.COLUMN_EDGE)];
                        for (int row = 0; row < getEdgeExtent(DataDirector.ROW_EDGE); row++)
                            for (int col = 0; col < getEdgeExtent(DataDirector.COLUMN_EDGE); col++)
                                data[row][col] = Math.round(Math.random()*100);
                    }
                    catch(EdgeOutOfRangeException e)
                    {
                        e.printStackTrace();
                    }
                    drill = true;
                }
                else if (flags == DataDirector.DRILL_BACK)
                {
                    ArrayList edgeLayers = (ArrayList)m_vEdgeLayers.get(edge);
                    edgeLayers.remove(layer);
                    edgeLayers.add(layer,MAKE);
                    try
                    {
                        data = new double[getEdgeExtent(DataDirector.ROW_EDGE)][getEdgeExtent(DataDirector.COLUMN_EDGE)];
                        for (int row = 0; row < getEdgeExtent(DataDirector.ROW_EDGE); row++)
                            for (int col = 0; col < getEdgeExtent(DataDirector.COLUMN_EDGE); col++)
                                data[row][col] = (double)((row%3)*20+(col%4)*25+200);
                    }
                    catch(EdgeOutOfRangeException e)
                    {
                        e.printStackTrace();
                    }
                    drill = true;
                }
            }
            else if ( m_rowDrillLevel == 2 ){
                if (flags == DataDirector.DRILL_BACK)
                {
                    ArrayList edgeLayers = (ArrayList)m_vEdgeLayers.get(edge);
                    edgeLayers.remove(layer);
                    edgeLayers.add(layer,BMW);
                    try
                    {
                        data = new double[getEdgeExtent(DataDirector.ROW_EDGE)][getEdgeExtent(DataDirector.COLUMN_EDGE)];
                        for (int row = 0; row < getEdgeExtent(DataDirector.ROW_EDGE); row++)
                            for (int col = 0; col < getEdgeExtent(DataDirector.COLUMN_EDGE); col++)
                                data[row][col] = (double)((row%3)*20+(col%4)*25+200);
                    }
                    catch(EdgeOutOfRangeException e)
                    {
                        e.printStackTrace();
                    }
                    drill = true;
                }
            }
            
            if (flags == DataDirector.DRILL_REPLACE)
                m_rowDrillLevel ++;
            else if (flags == DataDirector.DRILL_BACK)
                m_rowDrillLevel --;
        }
        else {
            if ( m_colDrillLevel == 0 ){
                if (flags == DataDirector.DRILL_REPLACE)
                {
                    String name = GEOGRAPHY;
                    ArrayList edgeLayers = (ArrayList)m_vEdgeLayers.get(edge);
                    edgeLayers.remove(layer);
                    edgeLayers.add(layer,name);
                    try
                    {
                        data = new double[getEdgeExtent(DataDirector.ROW_EDGE)][getEdgeExtent(DataDirector.COLUMN_EDGE)];
                        for (int row = 0; row < getEdgeExtent(DataDirector.ROW_EDGE); row++)
                            for (int col = 0; col < getEdgeExtent(DataDirector.COLUMN_EDGE); col++)
                                data[row][col] = Math.round(Math.random()*100);
                    }
                    catch(EdgeOutOfRangeException e)
                    {
                        e.printStackTrace();
                    }
                    drill = true;
                }
            }
            else if ( m_colDrillLevel == 1 ){
                if (flags == DataDirector.DRILL_REPLACE)
                {
                    ArrayList edgeLayers = (ArrayList)m_vEdgeLayers.get(edge);
                    edgeLayers.remove(layer);
                    edgeLayers.add(layer,LONDON);
                    try
                    {
                        data = new double[getEdgeExtent(DataDirector.ROW_EDGE)][getEdgeExtent(DataDirector.COLUMN_EDGE)];
                        for (int row = 0; row < getEdgeExtent(DataDirector.ROW_EDGE); row++)
                            for (int col = 0; col < getEdgeExtent(DataDirector.COLUMN_EDGE); col++)
                                data[row][col] = (double)((row%3)*20+(col%4)*25+200);
                    }
                    catch(EdgeOutOfRangeException e)
                    {
                        e.printStackTrace();
                    }
                    drill = true;
                }
                else if (flags == DataDirector.DRILL_BACK){
                    ArrayList edgeLayers = (ArrayList)m_vEdgeLayers.get(edge);
                    edgeLayers.remove(layer);
                    edgeLayers.add(layer,TOTAL);
                    try
                    {
                        data = new double[getEdgeExtent(DataDirector.ROW_EDGE)][getEdgeExtent(DataDirector.COLUMN_EDGE)];
                        for (int row = 0; row < getEdgeExtent(DataDirector.ROW_EDGE); row++)
                            for (int col = 0; col < getEdgeExtent(DataDirector.COLUMN_EDGE); col++)
                                data[row][col] = (double)((row%3)*20+(col%4)*25+200);
                    }
                    catch(EdgeOutOfRangeException e)
                    {
                        e.printStackTrace();
                    }
                    drill = true;
                }
            }
            else if ( m_colDrillLevel == 2 ){
                if (flags == DataDirector.DRILL_BACK)
                {
                    ArrayList edgeLayers = (ArrayList)m_vEdgeLayers.get(edge);
                    edgeLayers.remove(layer);
                    edgeLayers.add(layer,GEOGRAPHY);
                    try
                    {
                        data = new double[getEdgeExtent(DataDirector.ROW_EDGE)][getEdgeExtent(DataDirector.COLUMN_EDGE)];
                        for (int row = 0; row < getEdgeExtent(DataDirector.ROW_EDGE); row++)
                            for (int col = 0; col < getEdgeExtent(DataDirector.COLUMN_EDGE); col++)
                                data[row][col] = (double)((row%3)*20+(col%4)*25+200);
                    }
                    catch(EdgeOutOfRangeException e)
                    {
                        e.printStackTrace();
                    }
                    drill = true;
                }
            }
            if (flags == DataDirector.DRILL_REPLACE)
                m_colDrillLevel ++;
            else if (flags == DataDirector.DRILL_BACK)
                m_colDrillLevel --;
            
            
        }
        if (drill)
        {
            // Notify the listeners that the data has changed 
            for (Iterator e = m_dataDirectorListeners.iterator(); e.hasNext(); )
            {
                DataDirectorListener listener = (DataDirectorListener)e.next();
                listener.viewDataChanged(new DataChangedEvent(this, this, true, true, true, true));
            }
        }
        return false;
    }
    
    /**
     * Indicates whether a drill operation from a single selected member
     * would be legal.
     * This method is called before the <code>drill</code> method and does not
     * perform the actual drill.
     *
     * @param edge   A constant that indicates the edge to drill.
     *               Valid constants end with <code>_EDGE</code>
     *               in this interface.
     * @param layer  The zero-based index of the physical layer of the member
     *               that is to be drilled.
     *               The outermost layer on the edge is 0.
     * @param slice  The zero-based index (along the specified edge) of the
     *               slice that represents the members that are to be drilled.
     *               Each member in the innermost layer has a unique slice.
     *               In outer layers, any slice that the member spans
     *               can be used.
     * @param flags  @see #drill
     *
     * @return <code>true</code> if the specified drilling operation is legal,
     *         <code>false</code> if the specified operation is not legal.
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is too large.
     * @throws LayerOutOfRangeException If <code>layer</code> is negative or
     *                  too large.
     * @throws SliceOutOfRangeException If <code>slice</code> is negative or
     *                  too large.
     * @throws DataDirectorException If an error occurs during the drilling
     *                  operation.
     *
     * @see #COLUMN_EDGE
     * @see #PAGE_EDGE
     * @see #ROW_EDGE
     *
     * @status Documented
     */
    public boolean drillOK(int edge, int layer, int slice, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException, DataDirectorException {
    	// Check if the requested data is drill state or indent
    	if ((flags == DataDirector.DRILL_REPLACE)||(flags == DataDirector.DRILL_BACK))
        {
            String layerName = (String)getLayerMetadata(edge, layer, LayerMetadataMap.LAYER_METADATA_NAME);
            if ((flags == DataDirector.DRILL_REPLACE)){
                if ( layerName.equals(MAKE) ||
                     layerName.equals(TOTAL)||
                     layerName.equals(BMW)){
                    return true;
                }
                if ( layerName.equals(GEOGRAPHY) && slice == 0 ){
                    return true;
                }
            }
            else if ( flags == DataDirector.DRILL_BACK ){
                if(layerName.equals(TOTAL)||
                   layerName.equals(HONDA)||
                   layerName.equals(BMW)||
                   layerName.equals(MB)||
                   layerName.equals(SERIES_3)||
                   layerName.equals(SERIES_5)||
                   layerName.equals(SERIES_7)||
                   layerName.equals(LONDON))
                 return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a data value for the specified row and column intersection.
     * <P>This implementation returns null.
     *
     * @param row The row to get.
     * @param col The column to get.
     * @param type Type of data to return, such as formatted or unformatted.
     *             Valid <code>type</code> values are defined in the DataMap.
     *
     * @return The specified type of information for the specified row and
     *         column location.
     *         This implementation returns <coded>null</code>.
     *
     * @throws RowOutOfRangeException If <code>row</code> is negative or too
     *                                large.
     * @throws ColumnOutOfRangeException If <code>column</code> is negative or
     *                                too large.
     *
     * @see DataMap
     *
     * @status New
     */
    public Object getValue(int row, int col, String type) throws RowOutOfRangeException, ColumnOutOfRangeException
    {
//        return new Double(Math.random());
        return new Double(data[row][col]);
    }

    
    
    
    /*** LayoutContext interface methods ***
    /**
     * Create and return an object implementing the LayoutAccess interface
     * @deprecated replaced by {@link #createCubeLayoutAccess()}
     * @return new Layout implementing object
     * @status New
     */
    @Deprecated public LayoutAccess createLayoutAccess()
    {
        return createCubeLayoutAccess();
    }

    /**
     * Creates an implementation of the <code>LayoutAccess</code> interface
     * that presents data with a cube layout.
     *
     * @return An object that implements the <code>LayoutAccess</code>
     *         interface.
     *
     * @status New
     */
    public LayoutAccess createCubeLayoutAccess()
    {
        return this;
    }    

    /**
     * Creates an implementation of the <code>LayoutAccess</code> interface
     * that presents data with a relational (table) layout.
     *
     * @return An object that implements the <code>LayoutAccess</code>
     *         interface.
     *
     * @status New
     */
    public LayoutAccess createRelationalLayoutAccess()
    {
        return createLayoutAccess();
    }
        
    /**
     * Update the implementing data source with the changed layout
     * information in the given LayoutAccess object.
     *
     * @param la LayoutAccess object to use for update
     * @param update <code>true</code> if the caller is done with all updates
     * @status New
     */
    public boolean applyLayoutAccess(LayoutAccess la, boolean update)
    {
        // fill in the data array
        try{
        data = new double[getEdgeExtent(DataDirector.ROW_EDGE)][getEdgeExtent(DataDirector.COLUMN_EDGE)];
        for (int row = 0; row < getEdgeExtent(DataDirector.ROW_EDGE); row++)
            for (int col = 0; col < getEdgeExtent(DataDirector.COLUMN_EDGE); col++)
                data[row][col] = (double)((row%4)*25+(col%3)*20+100);
        }catch(EdgeOutOfRangeException e){
            e.printStackTrace();
        }

        // Notify the listeners that the data has changed
        for (Iterator e = m_dataDirectorListeners.iterator(); e.hasNext(); )
        {
            DataDirectorListener listener = (DataDirectorListener)e.next();
            listener.viewDataChanged(new DataChangedEvent(this, this, true, true, true, true));
        }

        // Do nothing for now
        return true;
    }
    /*** End LayoutContext interface methods ***

    
    /*** LayoutAccess interface methods ***
    /**
     * Turn on and off cursor evaluation or other performance flags as
     * defined by the implementer.
     *
     * @param on turn on if <code>true</code>
     * @status New
     */
    public void setCursorEvaluation(boolean on)
    {
        // Do nothing
    }
    
    /**
     * Return the singleton object used to track layout changes in this LayoutAccess.
     *
     * @return Object that implements oracle.dss.util.DataSource, from which callers can obtain DataDirector/DataAccess objects that provide the structure of the current dimension layout.
     * @status New
     */
     public DataSource getDataSource()
     {
         return this;
     }

    /**
     * Update (or create if necessary) the layout data source based on a new list of measures.  These measure names should be a subset of the ones returned by using getMeasures with a MetadataMap.METADATA_VALUE type.
     * @deprecated 
     * @param measures New measures to use in layout data source.
     * @status New
     */
    @Deprecated public void setMeasures(String[] measures)
    {}

    /**
     * Get the list of measures chosen for this query available for layout.
     * @deprecated
     * @param type MetadataMap type of labels given to the measures returned.
     * @return List of measures available in the layout query.
     * @status New
     */
    @Deprecated public String[] getMeasures(String type)
    {
        // Return members of the Measure dimension
        return m_members[0];
    }
    /**
     * @status New
     */    
    public QDR getMemberQDR(int edge, int layer, int slice, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException 
    {
        Object label = null;
        try
        {
            label = getMemberMetadata(edge, layer, slice, MetadataMap.METADATA_VALUE);
        }
        catch (Exception de)
        {
            label = "";
        }
        
        QDR qdr = new QDR(label.toString(), label.toString());
        
        return qdr;
    }

    /**
     * @status New
     */
    public QDR getLayerQDR(int edge, int slice, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException 
    {
        Object label = null;
        try
        {
            label = getMemberMetadata(edge, slice, 0, MetadataMap.METADATA_VALUE);
        }
        catch (Exception de)
        {
            label = "";
        }
        
        QDR qdr = new QDR(label.toString(), label.toString());
        
        return qdr;
    }
    /**
     * Return the edge (as defined in the DataDirector interface) indicating the location of the currently-defined "measure dimension" in the layout query.
     *
     * @return Edge indicating measure dimension location.
     * @status New
     */
    public int getMeasureEdge()
    {
        int i=0;
        for (Iterator e = m_vEdgeLayers.iterator(); e.hasNext(); i++)
        {
            ArrayList layers = (ArrayList)e.next();
            if (layers.contains(MEASURE))
                return i;
        }
        return -1;
    }

    /**
     * Return the layer indicating the location (along with the getMeasureEdge()) of the currently-defined "measure dimension" in the layout query.
     *
     * @return Layer indicating measure dimension location.
     * @status New
     */
     public int getMeasureLayer()
     {
        for (Iterator e = m_vEdgeLayers.iterator(); e.hasNext();)
        {
            ArrayList layers = (ArrayList)e.next();
            if (layers.contains(MEASURE))
                return (layers.indexOf(MEASURE));
        }
        return -1;     
     }
 
    /**
     * Set a specific layout expressed in a two-dimensional array
     * (edges x layers).  Each element of the array represents a value
     * returned by dataAccess.getLayerMetadata(edge, layer, LayerMetadataMap.LAYER_METADATA_NAME)
     * and is placed in the array argument in the new location that the element
     * should have after setting the new layout.
     *
     * @param layout two dimensional array of getLayerMetadata(edge, layer, LayerMetadataMap.LAYER_METADATA_NAME)
     *               values in their new location.  The first dimension of this argument represents
     *               edges, and the second represents the layers within those edges
     *               Any edges that should not have elements should have their place held
     *               with a zero element (zero layer) subarray at the edge location.
     * @status New
     */
    public void setLayout(String[][] layout)
    {
        int edgeCount = getEdgeCount();
        
        for (int i=0; i<layout.length; i++)
        {
            String[] newEdgeLayout = layout[i];
            if (i < m_vEdgeLayers.size())
            {
                ArrayList vEdgeLayers = (ArrayList)m_vEdgeLayers.get(i);
                //System.out.println("Old edge layers: " + vEdgeLayers);
                
                // Remove old layers
                vEdgeLayers.clear();
                
                // Add new layers
                for (int j=0; j<newEdgeLayout.length; j++)
                {
                    vEdgeLayers.add(newEdgeLayout[j]);
                }
                //System.out.println("New edge layers: " + vEdgeLayers);   
            }
        }
        
        // fill in the data array
        try{
        data = new double[getEdgeExtent(DataDirector.ROW_EDGE)][getEdgeExtent(DataDirector.COLUMN_EDGE)];
        for (int row = 0; row < getEdgeExtent(DataDirector.ROW_EDGE); row++)
            for (int col = 0; col < getEdgeExtent(DataDirector.COLUMN_EDGE); col++)
                data[row][col] = (double)((row%4)*25+(col%3)*20+100);
        }catch(EdgeOutOfRangeException e){
            e.printStackTrace();
        }

        // Notify the listeners that the data has changed 
        for (Iterator e = m_dataDirectorListeners.iterator(); e.hasNext(); )
        {
            DataDirectorListener listener = (DataDirectorListener)e.next();
            listener.viewDataChanged(new DataChangedEvent(this, this, true, true, true, true));
        }

    }
    
    /**
     * Clean up the LayoutAccess implementation.  To be called by users
     * when they no longer need the LayoutAccess object
     * @status New
     */
    public void release()
    {
    }
    
    public boolean isSpecialDimension(int edge, int layer, String type) throws EdgeOutOfRangeException, LayerOutOfRangeException
    {     
        if(type == LayoutAccess.MEASURE )
        {    
            //Get the layers at the specified edge
            ArrayList layers = (ArrayList)m_vEdgeLayers.get(edge);
            
            //If any layer contains MEASURE and the layer matches specified layer, return true.
            if (layers.contains(MEASURE))
                 return (layers.indexOf(MEASURE) == layer);
        }
        if(type == LayoutAccess.TIME_DIMENSION)
        {    
            //Get the layers at the specified edge
            ArrayList layers = (ArrayList)m_vEdgeLayers.get(edge);
            
            //If any layer contains MEASURE and the layer matches specified layer, return true.
            if (layers.contains(TIME))
                 return (layers.indexOf(TIME) == layer);
        }
        return false;
    }

    /*** End LayoutAccess interface methods ***/
    
    // Return the member array given the dimension name
    protected String[] getLayerMembers(String layerName)
    {
        for (int i=0; i<m_dimensions.length; i++)
        {
            if (m_dimensions[i].equals(layerName))
            {
                return m_members[i];
            }
        }
        
        return null;
    }
    
    public DataMap getSupportedDataMap()
    {
        String supported[] = {DataMap.DATA_UNFORMATTED,
                              DataMap.DATA_FORMATTED};
        return (new DataMap(supported));
    }
    
    public MetadataMap getSupportedMetadataMap()
    {
        //return null;
       String supported[] =  {MetadataMap.METADATA_VIEWSTYLE,
                              MetadataMap.METADATA_LONGLABEL,
                              MetadataMap.METADATA_MEDIUMLABEL,
                              MetadataMap.METADATA_SHORTLABEL,
                              MetadataMap.METADATA_DATE,
                              MetadataMap.METADATA_VALUE,
                              MetadataMap.METADATA_DRILLSTATE };
       return (new MetadataMap(supported));
    }
    
    public LayerMetadataMap getSupportedLayerMetadataMap()
    {
        //return null;
       String supported[] =  {LayerMetadataMap.LAYER_METADATA_LONGLABEL,
                              LayerMetadataMap.LAYER_METADATA_MEDIUMLABEL,
                              LayerMetadataMap.LAYER_METADATA_SHORTLABEL,
                              LayerMetadataMap.LAYER_METADATA_NAME};
       return (new LayerMetadataMap(supported));
    }

    public QDR getSliceQDR(int edge, int slice, int flags) throws EdgeOutOfRangeException, SliceOutOfRangeException {
        if (slice < 0)
            return null;

        QDR qdr = new QDR();
		// Add edge location to QDR
		addEdgeToQDR(qdr, edge, slice);
        
        if (edge != PAGE_EDGE && flags == QDR_WITH_PAGE)
            addEdgeToQDR(qdr, PAGE_EDGE, m_currentPage);

		return qdr;
    }

  public QDR getValueQDR(int row, int column, int flags) throws RowOutOfRangeException, ColumnOutOfRangeException {
    if ((row < 0) || (column < 0))
        return null;

    QDR qdr = new QDR();
    try {
  		// Add column edge location to QDR            
	    addEdgeToQDR(qdr, DataDirector.COLUMN_EDGE, column);
    
	    // Add row edge location to QDR            
	    addEdgeToQDR(qdr, DataDirector.ROW_EDGE, row);
        
        if (flags == QDR_WITH_PAGE)
            addEdgeToQDR(qdr, PAGE_EDGE, m_currentPage);

		}
		catch (Exception e){
			e.printStackTrace();
		}
		return qdr;
  }


    	// Internal helper method for getQDR() that adds the current edge location to the QDR
	protected void addEdgeToQDR(QDR qdr, int edge, int slice) throws EdgeOutOfRangeException {
		String layerName;
		try {
			// for each dimension at an edge location, find all of the dimension values and add the
			// dimension-value pair to the QDR
			for (int layer = 0; layer < getLayerCount(edge); layer++) {
                try {
                    layerName = (String)getLayerMetadata(edge, layer, LayerMetadataMap.LAYER_METADATA_NAME);
				} catch (LayerOutOfRangeException e) {
					layerName = null;
				}
				if (layerName != null)
                {
                    String member = (String)getMemberMetadata(edge, layer, slice, MetadataMap.METADATA_VALUE);
                    if (member != null)
        				qdr.addDimMemberPair(layerName, member);
                }

                // if the layer is deep (>1) then advance past the null layers
            	int depth = getMemberDepth(edge,layer,slice);
	    		if(depth>1)
		    	   layer=layer+(depth-1);
			}
//            qdr.setMeasureDim((String)getLayerMetadata(edge, getLayerCount(edge)-1, LayerMetadataMap.LAYER_METADATA_NAME));
            qdr.setMeasureDim((String)(getLayerMetadata(getMeasureEdge(), getMeasureLayer(), LayerMetadataMap.LAYER_METADATA_NAME)));
		} catch (SliceOutOfRangeException ire) {
			ire.printStackTrace();
		} catch (LayerOutOfRangeException dre) {
			dre.printStackTrace();
		}
	}


} // end of LayoutLocalDataSource class