/*
** Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.graph.dataSource.edge;

import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;


/**
 * The common methods from <code>oracle.dss.util.DataAccess</code>
 * and <code>oracle.dss.util.DataDirector</code>
 * to render an edge or page edge.
 * The <code>Edge</code> and <code>PageEdge</code> interfaces, which extend this
 * interface, are used as the basis of all edges in the DataSource samples.
 *
 * @hidden
 */
public interface BaseEdge {

	/**
	 * This method is identical to the <code>getEdgeExtent</code> method of
     * <code>oracle.dss.util.DataAccess</code>.
	 *
	 * @see oracle.dss.util.DataAccess#getEdgeExtent
	 */
    public int getExtent();

	/**
	 * This method is identical to the <code>getMemberSiblingCount</code> method of

     * <code>oracle.dss.util.DataAccess</code> except that it does not have
     * the edge parameter.
	 *
	 * @see oracle.dss.util.DataDirector#getMemberSiblingCount
	 *
	 */
	public int getMemberSiblingCount(int[] hPos, int memberLayer) throws LayerOutOfRangeException, SliceOutOfRangeException;
		
	/**
	 * This method is identical to the <code>getMemberMetadata</code> method of
     * <code>oracle.dss.util.DataAccess</code> except that it does not have
     * the edge parameter.
	 *
	 * @see oracle.dss.util.DataDirector#getMemberMetadata
	 *
	 */
	public Object getMemberMetadata(int[] hPos, int memberLayer, int hIndex, String type) throws LayerOutOfRangeException, SliceOutOfRangeException;

	/**
	 * This method is identical to the <code>getLayerMetadata</code> method
     * of <code>oracle.dss.util.DataAccess</code> except that it does not have
     * the edge parameter.
	 *
	 * @see oracle.dss.util.DataAccess#getLayerMetadata
	 */
  public Object getLayerMetadata(int layer, String type) throws LayerOutOfRangeException;

	/**
	 * This method is identical to the <code>getLayerCount</code> method of
     * <code>oracle.dss.util.DataAccess</code> except that it does not have the
     * edge parameter.
	 *
	 * @see oracle.dss.util.DataAccess#getLayerCount
	 */
 	public int getLayerCount();


}
