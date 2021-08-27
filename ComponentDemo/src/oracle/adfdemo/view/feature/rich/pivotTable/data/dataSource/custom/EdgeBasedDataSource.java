/*
** Copyright (c) 2007, 2009, Oracle and/or its affiliates. 
** All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom;


import java.util.Enumeration;
import java.util.Vector;


import oracle.dss.util.DataSource;
import oracle.dss.util.DataAccess;
import oracle.dss.util.DataDirector;
import oracle.dss.util.CubeDataAccess;
import oracle.dss.util.CubeDataDirector;
import oracle.dss.util.DataAccessAdapter;
import oracle.dss.util.RelationalDataDirector;
import oracle.dss.util.QDR;
import oracle.dss.util.DataMap;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.LayerMetadataMap;
import oracle.dss.util.DataDirectorListener;
import oracle.dss.util.DataAvailableEvent;
import oracle.dss.util.DataChangedEvent;

import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge.BaseEdge;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge.Edge;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge.PageEdge;
import oracle.dss.util.ColumnOutOfRangeException;
import oracle.dss.util.DataDirectorException;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.RowOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;

/**
 * The common base class for the four data source samples.
 * This class performs the following tasks:
 * <ul> <li>
 * Extends <code>oracle.dss.util.DataAccessAdapter</code> and provides a
 * default implementation of the following data retrieval interfaces from the
 * <code>oracle.dss.util</code> package: <code>DataSource</code>,
 * <code>DataAccess</code>, and <code>DataDirector</code>.
 * <li>
 * Delegates edge-specific methods from the data retrieval interfaces
 * (<code>DataAccess</code> and <code>DataDirector</code> in the
 * <code>oracle.dss.util</code> package) to the <code>Edge</code> and
 * <code>PageEdge</code> interfaces of the
 * <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge</code> package.
 * The edges that implement <code>Edge</code> and <code>PageEdge</code>
 * are set on <code>EdgeBasedDataSource</code> by the methods
 * <code>setRowEdge</code>, <code>setColEdge</code>, and
 * <code>setPageEdge</code>.
 * <li>
 * Provides a base implementation for the <code>getQDR</code> method that is
 * built from the methods of the <code>DataAccess</code> interface of the
 * <code>oracle.dss.util</code> package.
 * <li>
 * Provides a base implementation for maintaining and notifying a list of data
 * listeners.
 * </ul>
 * <p>
 ******************************************************************</p>
 * <p> All data source samples in the <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom</code>
 * package extend <code>EdgeBasedDataSource</code>. In these samples,
 * each edge is independent to illustrate the
 * different types of edges that can be exposed through the
 * <code>DataAccess</code>
 * and <code>DataDirector</code> interfaces.</p>
 *
 * <p>  The data source samples use the following type of edges that implement
 * the <code>Edge</code> interface of the
 * <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge</code> package:
 * <ul> <li>
 * <code>SingleLayerEdge</code>, which displays a single layer on a row or
 * column edge.</li>
 * <li>
 * <code>AsymmetricEdge</code>, which displays multiple symmetric or asymmetric
 * layers on a row or column edge. </li> </ul> </p>
 * <p>
 * The data source samples also use the following type of edges that
 * implement the <code>PageEdge</code> interface of the
 * <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge</code> package:
 * <ul> <li>
 * <code>SymmetricPageEdge</code>, which displays multiple symmetric layers on a
 * row, column, or page edge. </li>
 * <li>
 * <code>AsymmetricPageEdge</code>, which displays multiple symmetric or
 * asymmetric layers on a row, column, or page edge. </li> </ul> </p>
 * <p>
 * Definition - Symmetric Edge is an edge that consists of unrelated layers
 * as shown in the following table.
 * <p> <pre>
 *             ____________________________________________________________
 * geography  | Massachusetts | Massachusetts | Rhode Island | Rhode Island|
 *            |---------------|---------------|--------------|-------------|
 * product    | Audio         | Video         | Audio        | Video       |
 *             ------------------------------------------------------------
 * </pre> </p>
 * <p>
 * Definition - Asymmetric Edge is an edge that consists of related and
 * unrelated layers as shown in the table below.
 * <p> <pre>
 *          ________________________________________________________________
 * state   |Mass.   | Mass.     | Rhode Island | Rhode Island | Connecticut |
 *         |--------|-----------|--------------|--------------|             |
 * city    |Boston  | Worcester | Providence   | Kingston     |             |
 *         ------------------------------------------------------------------
 * </pre> </p>
 *
 *<p>
 * The sample data sources can be found in the
 * <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom</code> package. Edges for the sample
 * data sources are found in the
 * <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge</code> package.
 * A list of the sample data sources and their edges follows:
 * <ul> <li>
 * <code>SimpleDataSource</code> <pre>
 *      row edge    = SingleLayerEdge
 *      column edge = SingleLayerEdge </pre>
 *
 * <li> <code>PagingDataSource</code> <pre>
 *      row edge    = SingleLayerEdge
 *      column edge = SingleLayerEdge
 *      page edge   = SymmetricPageEdge </pre>
 *
 * <li> <code>DrillablePagingDataSource</code> <pre>
 *      row edge    = SingleLayerEdge
 *      column edge = SingleLayerEdge
 *      page edge   = SymmetricPageEdge </pre>
 *
 * <li> <code>AsymmetricDrillablePagingDataSource</code> <pre>
 *      row edge    = AsymmetricEdge
 *      column edge = AsymmetricEdge
 *      page edge   = AsymmetricPageEdge </pre> </ul>
 * 
 * <p>
 *
 * The following is the mapping between the methods of this class and the
 * methods of the <code>Edge</code> interface in the
 * <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge</code> package.
 * <p><pre>
 * EdgeBasedDataSource.getEdgeExtent(edge)
 *       == Edge.getExtent()</pre>
 * <p><pre>
 * EdgeBasedDataSource.getLayerMetadata(edge,layer,type)
 *       == Edge.getLayerMetadata(layer,type) </pre>
 * <p><pre>
 * EdgeBasedDataSource.getSliceLabel(edge,slice)
 *       == Edge.getSliceLabel(slice)</pre>
 * <p><pre>
 * EdgeBasedDataSource.getLayerCount(edge)
 *       == Edge.getLayerCount()</pre>
 * <p><pre>
 * EdgeBasedDataSource.getSliceMemberCount(edge,slice)
 *       == Edge.getSliceMemberCount(slice)</pre>
 * <p><pre>
 * EdgeBasedDataSource.getMemberDepth(edge,layer,slice)
 *       == Edge.getMemberDepth(layer,slice) </pre>
 * <p><pre>
 * EdgeBasedDataSource.getMemberStartLayer(edge,layer,slice)
 *       == Edge.getMemberStartLayer(layer,slice) </pre>
 * <p><pre>
 * EdgeBasedDataSource.getLayerMetadata(edge,layer,type)
 *       == Edge.getLayerMetadata(layer,type) </pre>
 * <p><pre>
 * EdgeBasedDataSource.getMemberExtent(edge,layer,slice)
 *       == Edge.getMemberExtent(layer,slice) </pre>
 * <p><pre>
 * EdgeBasedDataSource.getMemberStartSlice(edge,layer,slice)
 *       == Edge.getMemberStartSlice(layer,slice) </pre>
 * <p><pre>
 * EdgeBasedDataSource.getMemberMetadata(edge,layer,slice,type)
 *       == Edge.getMemberMetadata(layer,slice,type) </pre>
 *
 * <p>
 * The following is the mapping between the methods of this class and the
 * methods of the <code>PageEdge</code> interface in the
 * <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge</code> package.
 *
 * <p><pre>
 * EdgeBasedDataSource.getLayerMetadata(edge,layer,type)
 *       == Edge.getLayerMetadata(layer,type) </pre>
 * <p><pre>
 * EdgeBasedDataSource.getMemberSiblingCount(edge,hPos,memberLayer)
 *       == Edge.getMemberSiblingCount(hPos,memberLayer) </pre>
 * <p><pre>
 * EdgeBasedDataSource.getMemberMetadata(edge,hPos,memberLayer,hIndex,type)
 *       == Edge.getMemberMetadata(hPos,memberLayer,hIndex,type) </pre>
 * <p><pre>
 * EdgeBasedDataSource.getEdgeCurrentHPos(edge)
 *       == Edge.getEdgeCurrentHPos() </pre>
 * <p><pre>
 * EdgeBasedDataSource.getNextHPos(edge)
 *       == Edge.getNextHPos() </pre>
 * <p><pre>
 * EdgeBasedDataSource.getPrevHPos(edge)
 *       == Edge.getPrevHPos() </pre>
 * <p><pre>
 * EdgeBasedDataSource.getFirstHPos(edge)
 *       == Edge.getFirstHPos() </pre>
 * <p><pre>
 * EdgeBasedDataSource.getLastHPos(edge)
 *       == Edge.getLastHPos() </pre>
 * <p><pre>
 * EdgeBasedDataSource.changeEdgeCurrentSlice(edge,hPos,maxLayerSpecified)
 *       == Edge.getLastHPos() </pre>
 *
 * <p>
 * Note: This class uses an edge-based model where all edge logic is performed
 * by edge classes.
 * This approach is used in the data source samples to illustrate how to render
 * different types of edges.  When developing your own data source, you can
 * choose to use any of the following approaches for implementation:
 * <ul>
 * <li>All edges in a single DataSource class
 * <li>All edges in a single Edge class
 * <li>A different edge in several Edge classes
 * <li>Any other coding structure that you find useful
 * </ul>
 *
 * 
 * @hidden
 */
public class EdgeBasedDataSource extends DataAccessAdapter implements DataSource, CubeDataAccess, CubeDataDirector, RelationalDataDirector {

	/**
	 * Constructor.
	 *
	 */
	public EdgeBasedDataSource() {
	}

		
	/**
	 * Retrieves the row edge of the <code>EdgeBasedDataSource</code>.
	 *
	 * @return The row edge as an Edge interface
	 *
	 * @see oracle.dss.samples.Edge
	 *
	 */
	public Edge getRowEdge(){
		return m_rowEdge;
	}

	/**
	 * Specifies the row edge of the <code>EdgeBasedDataSource</code>.
	 *
	 * @param rowEdge  An Edge interface that will be used to render the row edge.
	 *
	 * @see oracle.dss.samples.Edge
	 * 
	 */
	public void setRowEdge(Edge rowEdge){
		m_rowEdge = rowEdge;
	}

	/**
	 * Retrieves the column edge of the <code>EdgeBasedDataSource</code>.
	 *	
	 * @return The column edge as an Edge interface.
	 *
	 * @see oracle.dss.samples.Edge
	 * 
	 */
	public Edge getColEdge(){
		return m_colEdge;
	}

	/**
	 * Specifies the column edge of the <code>EdgeBasedDataSource</code>.
	 *
	 * @param colEdge  An Edge interface that will be used to render the col edge.
	 *
	 * @see oracle.dss.samples.Edge
	 */
	public void setColEdge(Edge colEdge){
		m_colEdge = colEdge;
	}

	/**
	 * Retrieves the page edge of the <code>EdgeBasedDataSource</code>.
   *
	 * @return The page edge as an Edge interface.
	 *
	 * @see oracle.dss.samples.PageEdge
	 */
	public PageEdge getPageEdge(){
		return m_pageEdge;
	}

	/**
	 * Specifies the page edge of the <code>EdgeBasedDataSource</code>.
   *
	 * @param pageEdge  An Edge interface that will be used to render the page
   *                  edge.
	 *
	 * @see oracle.dss.samples.PageEdge
	 *
	 */
	public void setPageEdge(PageEdge pageEdge){
		m_pageEdge = pageEdge;
	}

	// internal helper method that translates an edge index into an Edge interface
	protected BaseEdge getBaseEdge(int edge){
		switch(edge){
			case DataDirector.COLUMN_EDGE:
				return m_colEdge;
			case DataDirector.ROW_EDGE:
				return m_rowEdge;
			case DataDirector.PAGE_EDGE:
				return m_pageEdge;
			default:
				return null;
		}
 	}

	// internal helper method that translates an edge index into an Edge interface
	private Edge getEdge(int edge){
		switch(edge){
			case DataDirector.COLUMN_EDGE:
				return m_colEdge;
			case DataDirector.ROW_EDGE:
				return m_rowEdge;
			default:
				return null;
		}
 	}
  
	//////////////////////////////////////////////////////
	// oracle.dss.util.DataSource interface methods
	public CubeDataDirector createCubeDataDirector() {
		return this;
 	}
    
        public DataDirector createDataDirector()
        {
            return this;
        }
	public RelationalDataDirector createRelationalDataDirector() {
		return this;
	}
        
    public DataAccess getDataAccess() throws DataDirectorException
    {
        return this;
    }

	///////////////////////////////////////////////////////////////////////////////
	// oracle.dss.util.DataAccess methods that do not delegate to the edges

	public int getEdgeCount() {
		int count =0;
 		if(m_colEdge!=null)
 			count++;
  	
 		if(m_rowEdge!=null)
 			count++;
 		
 		if(m_pageEdge!=null)
 			count++;
  	
		return count;
 	}
		
  /**
   * Retrieves a QDR object that matches the specified position in the data
   * cursor. 
   *
   * @param row    Row in the data cursor.
   * @param column Column in the data cursor.
   * @param flags  A constant that indicates whether or not to include page edge
   *               information, if the QDR is not for the page edge.  Constants
   *               begin with QDR_ and are defined in DataAccess.
   *
   * @return    <code>QDR</code> object that refers to the data at the
   *            specified position.
   *
   */
  public QDR getValueQDR(int row, int column, int flags) throws RowOutOfRangeException, ColumnOutOfRangeException {
    QDR qdr = new QDR();
    try {
        if (flags == QDR_WITH_PAGE)
        {
            // Add current page edge location to QDR            
	        addPageEdgeToQDR(qdr);
        }
        
  		// Add column edge location to QDR            
	    addEdgeToQDR(qdr, DataDirector.COLUMN_EDGE, column);
    
	    // Add row edge location to QDR            
	    addEdgeToQDR(qdr, DataDirector.ROW_EDGE, row);
		}
		catch (Exception e){ 
			e.printStackTrace();
		}
		return qdr;
  }        


  /**
   * Retrieves a QDR object that matches the specified position on the edge
   * cursor.
   *
   * @param edge  A constant that represents the edge of interest.
   *              The constants end with <code>_EDGE</code> in
   *              util.DataDirector.
   * @param slice An absolute index (zero-based) that indicates the location
   *              of the slice aong the specified <code>edge</code>.
   * @param flags  A constant that indicates whether or not to include page edge
   *               information, if the QDR is not for the page edge.  Constants
   *               begin with QDR_ and are defined in DataAccess.
   *
   * @return    <code>QDR</code> object that refers to the data at the
   *            specified position.
   *
   * @see DataDirector#COLUMN_EDGE
   * @see DataDirector#PAGE_EDGE
   * @see DataDirector#ROW_EDGE
   *
   */
  public QDR getSliceQDR(int edge, int slice, int flags) throws EdgeOutOfRangeException, SliceOutOfRangeException {
    QDR qdr = new QDR();
        if (edge != DataDirector.PAGE_EDGE && flags == QDR_WITH_PAGE) {
			// Add current page edge location to QDR            
			addPageEdgeToQDR(qdr);
		}
        		
		// Add edge location to QDR            
		addEdgeToQDR(qdr, edge, slice);
		
		return qdr;
  }

  /**
   * Retrieves a QDR object that represents the data value for the member at the
   * specified edge, layer, and slice.
   *
   * @param edge  A constant that represents the edge of interest.
   *              The constants end with <code>_EDGE</code> in
   *              util.DataDirector.
   * @param layer A zero-based index that represents the physical layer of
   *              the member.
   * @param slice An absolute index (zero-based) that indicates the location,
   *              along the specified edge, of a slice that belongs to the
   *              member for which the QDR object is to be retrieved.
   * @param flags  A constant that indicates whether or not to include page edge
   *               information, if the QDR is not for the page edge.  Constants
   *               begin with QDR_ and are defined in DataAccess.
   *
   * @return    <code>QDR</code> object that refers to the data at the
   *            specified position.
   *
   * @see DataDirector#COLUMN_EDGE
   * @see DataDirector#PAGE_EDGE
   * @see DataDirector#ROW_EDGE
   *
   */
  public QDR getMemberQDR(int edge, int layer, int slice, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
    QDR qdr = new QDR();
        try
        {
            if (edge != PAGE_EDGE && flags == QDR_WITH_PAGE)
                addPageEdgeToQDR(qdr);
        
            int sliceMemberCount = getSliceMemberCount(edge, slice);
            int layerCount = layer + 1;
            if (sliceMemberCount < layerCount)
                layerCount = sliceMemberCount;
            for( int i = 0; i < layerCount; i+=getMemberDepth(edge,i,slice) ) 
            {
                String layerName = (String)getLayerMetadata(edge, i, LayerMetadataMap.LAYER_METADATA_NAME);
                qdr.addDimMemberPair(layerName, (String)getMemberMetadata(edge, i, slice, MetadataMap.METADATA_VALUE));
            }    
 		} catch (Exception e) {
 			e.printStackTrace();
			qdr = null;
		}
		return qdr;
  }

  /**
   * Retrieves a QDR object that represents the specified member using
   * hierarchical addressing.
   *
   * @param edge        A constant that represents the edge of interest.
   *                    The constants end with <code>_EDGE</code> in
   *                    util.DataDirector.
   * @param hPos        An array of relative indexes that are organized by
   *                    logical layer. The indexes start at layer 0 and extend
   *                    up to the layer that is specified in
   *                    <code>memberLayer</code>.
   * @param memberLayer The zero-based index into the <code>hPos</code> array
   *                    that indicates the layer in which to find the member.
   * @param flags  A constant that indicates whether or not to include page edge
   *               information, if the QDR is not for the page edge.  Constants
   *               begin with QDR_ and are defined in DataAccess.
   *
   * @return    <code>QDR</code> object that refers to the data at the
   *            specified position.
   *
   * @see DataDirector#COLUMN_EDGE
   * @see DataDirector#PAGE_EDGE
   * @see DataDirector#ROW_EDGE
   *
   */
  public QDR getMemberQDR(int edge, int[] hPos, int memberLayer, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
  	if(edge!=DataDirector.PAGE_EDGE)
  		return null;
  	QDR qdr = new QDR();
  	try {
  		for(int i=0;i<=memberLayer;i++){
	  		String layer = (String)getLayerMetadata(edge, i,LayerMetadataMap.LAYER_METADATA_NAME);
  			String member = (String)getMemberMetadata(edge, hPos,i,hPos[i],MetadataMap.METADATA_VALUE);
  			qdr.addDimMemberPair(layer,member);
  		}
  	} catch (Exception e){
  		e.printStackTrace();
  		qdr=null;
  	}
  	return qdr;
  }

	/**
   * Retrieves a QDR object that matches the specified member's position
   * using hierarchical addressing.
   *
   * @param edge   A constant that represents the edge of interest.
   *               The constants end with <code>_EDGE</code> in
   *               util.DataDirector.
   * @param layer  The zero-based index of the logical layer for which to
   *               return the QDR.
   * @param flags  A constant that indicates whether or not to include page edge
   *               information, if the QDR is not for the page edge.  Constants
   *               begin with QDR_ and are defined in DataAccess.
   *
   * @return    <code>QDR</code> object that refers to the data at the
   *            specified position.
   *
   * @see DataDirector#COLUMN_EDGE
   * @see DataDirector#PAGE_EDGE
   * @see DataDirector#ROW_EDGE
   *
   */
  public QDR getLayerQDR(int edge, int layer, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException {
        QDR qdr = new QDR();
        
        if (edge != PAGE_EDGE && flags == QDR_WITH_PAGE)
            addPageEdgeToQDR(qdr);
        
		try{      	
			String dim = (String)getLayerMetadata(edge, layer, LayerMetadataMap.LAYER_METADATA_NAME);					      	
            qdr.addDimMemberPair(dim, (String)null);
 		} catch (Exception e) {
 			e.printStackTrace();
			return null;
		}
        return qdr;
  }        

	// Internal helper method for getQDR() that adds the page edge to the QDR
	protected void addPageEdgeToQDR(QDR qdr) throws EdgeOutOfRangeException {
		if(m_pageEdge==null)
			return;
			
		String layerName;
		try {
			int[] hPos = m_pageEdge.getEdgeCurrentHPos();
			for(int i=0;i<hPos.length;i++){
				if(hPos[i]!=-1){
					try {
						layerName = (String)m_pageEdge.getLayerMetadata(i, LayerMetadataMap.LAYER_METADATA_NAME);
					} catch (LayerOutOfRangeException e) {
						layerName = null;
					}
					if (layerName != null)
						qdr.addDimMemberPair(layerName, (String)m_pageEdge.getMemberMetadata(hPos, i, hPos[i], MetadataMap.METADATA_VALUE));
				}
			}
		} catch (SliceOutOfRangeException ire) {
			ire.printStackTrace();
		} catch (LayerOutOfRangeException dre) {
			dre.printStackTrace();
		}
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
				qdr.addDimMemberPair(layerName, (String)getMemberMetadata(edge, layer, slice, MetadataMap.METADATA_VALUE));
    		       
                // if the layer is deep (>1) then advance past the null layers
            	int depth = getMemberDepth(edge,layer,slice);
	    		if(depth>1)
		    	   layer=layer+(depth-1);
			}
		} catch (SliceOutOfRangeException ire) {
			ire.printStackTrace();
		} catch (LayerOutOfRangeException dre) {
			dre.printStackTrace();
		}
	}

	// This method should be overridden by classes that extend EdgeBasedDataSource. 
	// This implementation generates the QDR and returns it as a string. This string
	// can only be rendered in Crosstab and Table, since Graph requires numeric values
	// to display charts.
	public Object getValue(int row, int col, String type) throws RowOutOfRangeException, ColumnOutOfRangeException {  					
	  try {
		    QDR qdr = getValueQDR(row, col, DataAccess.QDR_WITH_PAGE);
		    // return the dimension-value pairs as a string
 		    return qdr.getDimMemberPairs();
		}
		catch (Exception e){
			e.printStackTrace();
		}
    return null;		
	}

	///////////////////////////////////////////////////////////////////////////////
	// oracle.dss.util.DataAccess methods that delegate to the BaseEdge interface

	public Object getLayerMetadata(int edge, int layer, String type) throws EdgeOutOfRangeException, LayerOutOfRangeException {
		BaseEdge e = getBaseEdge(edge);
		if(e!=null)
			return e.getLayerMetadata(layer,type);
		else // call the super class for the correct default
			return super.getLayerMetadata(edge, layer,type);
	}

  public int getMemberSiblingCount(int edge, int[] hPos, int memberLayer) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
		BaseEdge e = getBaseEdge(edge);
		if(e!=null)
			return e.getMemberSiblingCount(hPos, memberLayer);
		else // call the super class for the correct default
			return super.getMemberSiblingCount(edge, hPos, memberLayer);
	}

  public Object getMemberMetadata(int edge, int[] hPos, int memberLayer, int hIndex, String type) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
		BaseEdge e = getBaseEdge(edge);
		if(e!=null)
			return e.getMemberMetadata(hPos, memberLayer, hIndex,type);
		else // call the super class for the correct default
			return super.getMemberMetadata(edge, hPos,memberLayer,hIndex,type);
	}
	
  public int getLayerCount(int edge) throws EdgeOutOfRangeException {
		BaseEdge e = getBaseEdge(edge);
		if(e!=null)
			return e.getLayerCount();
		else // call the super class for the correct default
			return super.getLayerCount(edge);
	}


	///////////////////////////////////////////////////////////////////////////////
	// oracle.dss.util.DataAccess methods that delegate to the Edge interface

  public int getEdgeExtent(int edge) throws EdgeOutOfRangeException {
		BaseEdge e = getBaseEdge(edge);		
		if(e!=null){
			return e.getExtent();
		} else // call the super class for the correct default
			return super.getEdgeExtent(edge);
  }

  public int getSliceMemberCount(int edge, int slice) throws EdgeOutOfRangeException, SliceOutOfRangeException {
		Edge e = getEdge(edge);
		if(e!=null)
			return e.getSliceMemberCount(slice);
		else // call the super class for the correct default
			return super.getSliceMemberCount(edge, slice);
	}

  public int getMemberDepth(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
		Edge e = getEdge(edge);
		if(e!=null)
			return e.getMemberDepth(layer,slice);
		else // call the super class for the correct default
			return super.getMemberDepth(edge, layer,slice);
	}

  public int getMemberStartLayer(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
		Edge e = getEdge(edge);
		if(e!=null)
			return e.getMemberStartLayer(layer,slice);
		else // call the super class for the correct default
			return super.getMemberStartLayer(edge, layer,slice);
	}

  public int getMemberExtent(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
		Edge e = getEdge(edge);
		if(e!=null)
			return e.getMemberExtent(layer,slice);
		else // call the super class for the correct default
			return super.getMemberExtent(edge, layer,slice);
	}
  
  public int getMemberStartSlice(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
		Edge e = getEdge(edge);
		if(e!=null)
			return e.getMemberStartSlice(layer,slice);
		else // call the super class for the correct default
			return super.getMemberStartSlice(edge, layer,slice);
	}
  
	public Object getMemberMetadata(int edge, int layer, int slice, String type) throws EdgeOutOfRangeException, SliceOutOfRangeException, LayerOutOfRangeException {
		Edge e = getEdge(edge);
		if(e!=null)
			return e.getMemberMetadata(layer,slice,type);
		else // call the super class for the correct default
			return super.getMemberMetadata(edge, layer,slice,type);
	}

	public int[] getMemberHPos(int edge, int layer, int slice) throws EdgeOutOfRangeException, SliceOutOfRangeException, LayerOutOfRangeException {
	    BaseEdge e = getBaseEdge(edge);
		if (e!=null)
        {
            if (edge == DataDirector.PAGE_EDGE)
            {
                return ((PageEdge)e).getEdgeCurrentHPos();
            }
            else
                return ((Edge)e).getMemberHPos(layer,slice);
        }
        else // call the super class for the correct default
			return super.getMemberHPos(edge, layer,slice);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// oracle.dss.util.DataAccess methods for labels are currently only used by Graphs	
  
  public Object getSliceLabel(int edge, int slice, String type) throws EdgeOutOfRangeException, SliceOutOfRangeException {		
		Edge e = getEdge(edge);
		if(e!=null)
			return e.getSliceLabel(slice);
		else // call the super class for the correct default
			return super.getSliceLabel(edge, slice, type);
  }
    
	//////////////////////////////////////////////////////////////////////////////////
	// oracle.dss.util.DataDirector methods that delegate to the PageEdge interface

  public int getEdgeCurrentSlice(int edge) throws EdgeOutOfRangeException {
 		return super.getEdgeCurrentSlice(edge);
	}

  public boolean changeEdgeCurrentHPos(int edge, int[] hPos, int maxLayerSpecified) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException, DataDirectorException {
		if(edge!=DataDirector.PAGE_EDGE)
			throw new EdgeOutOfRangeException("Edge must be page edge");
		PageEdge e = getPageEdge();
		if(e!=null){
  		boolean bRet = e.changeEdgeCurrentHPos(hPos, maxLayerSpecified);
			
			// Notify the DataDirector listeners (typically Graph, Table, or Crosstab) that the page has changed.  
			notifyListeners(new DataChangedEvent(this, this, DataChangedEvent.PAGE_CHANGE, true, false, false, true));
			
			return bRet;
		} else // call the super class for the correct default
  		return super.changeEdgeCurrentHPos(edge, hPos, maxLayerSpecified);
	}

  public int[] getEdgeCurrentHPos(int edge) throws EdgeOutOfRangeException{
		if(edge!=DataDirector.PAGE_EDGE)
			throw new EdgeOutOfRangeException("Edge must be page edge");
		PageEdge e = getPageEdge();
		if(e!=null)
			return e.getEdgeCurrentHPos();
		else // call the super class for the correct default
			return super.getEdgeCurrentHPos(edge);  	
  }

  public int[] getFirstHPos(int edge) throws EdgeOutOfRangeException{
		if(edge!=DataDirector.PAGE_EDGE)
			throw new EdgeOutOfRangeException("Edge must be page edge");
		PageEdge e = getPageEdge();
		if(e!=null)
			return e.getFirstHPos();
		else // call the super class for the correct default
			return super.getFirstHPos(edge);  	
  }

  public int[] getLastHPos(int edge) throws EdgeOutOfRangeException{
		if(edge!=DataDirector.PAGE_EDGE)
			throw new EdgeOutOfRangeException("Edge must be page edge");
		PageEdge e = getPageEdge();
		if(e!=null)
			return e.getLastHPos();
		else // call the super class for the correct default
			return super.getLastHPos(edge);  	
  }

  public int[] getNextHPos(int edge, int[] hPos) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException{
		if(edge!=DataDirector.PAGE_EDGE)
			throw new EdgeOutOfRangeException("Edge must be page edge");
		PageEdge e = getPageEdge();
		if(e!=null)
			return e.getNextHPos(hPos);
		else // call the super class for the correct default
			return super.getNextHPos(edge,hPos);  	
  }

  public int[] getPrevHPos(int edge, int[] hPos) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException{
		if(edge!=DataDirector.PAGE_EDGE)
			throw new EdgeOutOfRangeException("Edge must be page edge");
		PageEdge e = getPageEdge();
		if(e!=null)
			return e.getPrevHPos(hPos);
		else // call the super class for the correct default
			return super.getPrevHPos(edge,hPos);  	
	}  

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// DataDirector methods to add/remove listeners on this DataSource

	public void addDataDirectorListener(DataDirectorListener l) {
		m_listeners.addElement(l);
		
		// Notify the new listener that data is available.  This method passes a DataAccess interface
		// to the listener, so the listener can start accessing data.  The data availability notification
		// can be delayed if data is not available yet, but this DataSource always has available data.  
		l.viewDataAvailable(new DataAvailableEvent(this, this));
	}
	
	public void removeDataDirectorListener(DataDirectorListener l) {
		if (m_listeners.contains(l))
			m_listeners.removeElement(l);
	}

	/**
	 * A helper method that is used to notify listeners of data changes.
	 *
	 * @param dataChangedEvent   A DataChangedEvent that describes a change
   * in this data source.
	 *
	 * @see oracle.dss.util.DataChangedEvent
	 *
	 */
	protected void notifyListeners(DataChangedEvent dataChangedEvent){
		Enumeration e = m_listeners.elements();
		while (e.hasMoreElements()) {
	   		DataDirectorListener l = (DataDirectorListener)e.nextElement();
     			l.viewDataChanged(dataChangedEvent);
		}
	}

    /**
     * Override getSupportedDataMap in ModelAdapter to return supported 
     * data layer types
     *
     */
    public DataMap getSupportedDataMap()
    {   
        String supported[] = {DataMap.DATA_UNFORMATTED};
        return new DataMap(supported);
    }
    
     /**
     * Override getSupportedDataMap in ModelAdapter to return supported 
     * Metadata layer types
     *
     */
    public MetadataMap getSupportedMetadataMap()
    {
        String supported[] = {MetadataMap.METADATA_LONGLABEL,
                              MetadataMap.METADATA_MEDIUMLABEL,
                              MetadataMap.METADATA_SHORTLABEL, 
                              MetadataMap.METADATA_INDENT,
                              MetadataMap.METADATA_VALUE,
                              MetadataMap.METADATA_DRILLSTATE};
        return new MetadataMap(supported);
    }
    
    /**
     * Override getSupportedDataMap in ModelAdapter to return supported 
     * LayerMetadata layer types
     *
     */
    public LayerMetadataMap getSupportedLayerMetadataMap()
    {   
        String supported[] = {LayerMetadataMap.LAYER_METADATA_LONGLABEL,
                              LayerMetadataMap.LAYER_METADATA_MEDIUMLABEL,
                              LayerMetadataMap.LAYER_METADATA_SHORTLABEL
                             };
        return new LayerMetadataMap(supported);
    }

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// EdgeBasedDataSource member variables

	protected Edge 			m_colEdge 	= null;
	protected Edge 			m_rowEdge 	= null;
	protected PageEdge 	    m_pageEdge 	= null;		
	private Vector			m_listeners = new Vector();
}