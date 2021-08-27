/*
** Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.graph.dataSource.edge;

import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;


/**
 * A set of <code>oracle.dss.util.DataAccess</code>
 * methods that are necessary to render a non-page edge.
 * These methods are identical to methods on the
 * <code>oracle.dss.util.DataAccess</code> interface, except that they
 * do not contain an edge parameter.
 *
 * @hidden
 */
public interface Edge extends BaseEdge{		

	/**
	 * This method is identical to the <code>getSliceLabel</code> methods
   * of <code>oracle.dss.util.DataAccess</code>.
	 *
	 * @see oracle.dss.util.DataAccess#getSliceLabel
	 *
	 */
  public Object getSliceLabel(int slice) throws SliceOutOfRangeException;

	/**
	 * This method is identical to the <code>getSliceMemberCount</code> method of
   * <code>oracle.dss.util.DataAccess</code> except that it does not have the
   * edge parameter.
	 *
	 * @see oracle.dss.util.DataAccess#getSliceMemberCount
	 */
  public int getSliceMemberCount(int slice) throws SliceOutOfRangeException;

	/**
	 * This method is identical to the <code>getMemberDepth</code> method
   * of <code>oracle.dss.util.DataAccess</code> except that it does not have
   * the edge parameter.
	 *
	 * @see oracle.dss.util.DataAccess#getMemberDepth
	 */
  public int getMemberDepth(int layer, int slice) throws LayerOutOfRangeException, SliceOutOfRangeException;

	/**
	 * This method is identical to the <code>getMemberStartLayer</code>
   * method of <code>oracle.dss.util.DataAccess</code> except that it does not
   * have the edge parameter.
   * 	 *
	 * @see oracle.dss.util.DataAccess#getMemberStartLayer
	 *
	 */
  public int getMemberStartLayer(int layer, int slice) throws LayerOutOfRangeException, SliceOutOfRangeException;

	/**
	 * This method is identical to the <code>getMemberExtent</code> method of
   * <code>oracle.dss.util.DataAccess</code> except that it does not have the
   * edge parameter.
	 *
	 * @see oracle.dss.util.DataAccess#getMemberExtent
	 *
	 */
  public int getMemberExtent(int layer, int slice) throws SliceOutOfRangeException, LayerOutOfRangeException;

	/**
	 * This method is identical to the <code>getMemberStartSlice</code>
   * method of <code>oracle.dss.util.DataAccess</code> except that it does not
   * have the edge parameter.
	 *
	 * @see oracle.dss.util.DataAccess#getMemberStartSlice
	 *
	 */
  public int getMemberStartSlice(int layer, int slice) throws SliceOutOfRangeException, LayerOutOfRangeException;

	/**
	 * This method is identical to the <code>getMemberMetadata</code> of
   * <code>oracle.dss.util.DataAccess</code> except that it does not have the
   * edge parameter.
	 *
	 * @see oracle.dss.util.DataAccess#getMemberMetadata
	 *
	 */
  public Object getMemberMetadata(int layer, int slice, String type) throws SliceOutOfRangeException, LayerOutOfRangeException;

	/**
	 * This method is identical to the <code>getMemberHPos</code> method of
   * <code>oracle.dss.util.DataAccess</code> except that it does not have
   * the edge parameter.
	 *
	 * @see oracle.dss.util.DataDirector#getMemberHPos
	 *
	 */
	public int[] getMemberHPos(int layer, int slice) throws LayerOutOfRangeException, SliceOutOfRangeException;

}