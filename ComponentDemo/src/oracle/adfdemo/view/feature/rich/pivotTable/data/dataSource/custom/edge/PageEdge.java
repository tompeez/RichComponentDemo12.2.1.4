/*
** Copyright (c) 2008, 2009, Oracle and/or its affiliates. 
** All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge;

import oracle.dss.util.DataDirectorException;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;


/**
 * The required methods from <code>oracle.dss.util.DataAccess</code>
 * and <code>oracle.dss.util.DataDirector</code>
 * to render a page edge.
 * All of the methods in this interface use hierarchical addressing.
 *
 * 
 * @hidden
 */
public interface PageEdge extends BaseEdge{

	/**
	 * This method is identical to the <code>getEdgeCurrentHPos</code> method of
   * <code>oracle.dss.util.DataAccess</code> except that it does not have
   * the edge parameter.
	 *
	 * @see oracle.dss.util.DataDirector#getEdgeCurrentHPos
	 *
	 */
  public int[] getEdgeCurrentHPos();

	/**
	 * This method is identical to the <code>getFirstHPos</code> method of
   * <code>oracle.dss.util.DataAccess</code> except that it does not have
   * the edge parameter.
	 *
	 * @see oracle.dss.util.DataDirector#getFirstHPos
	 *
	 */
  public int[] getFirstHPos();

	/**
	 * This method is identical to the <code>getLastHPos</code> method of
   * <code>oracle.dss.util.DataAccess</code> except that it does not have
   * the edge parameter.
	 *
	 * @see oracle.dss.util.DataDirector#getLastHPos
	 *
	 */
  public int[] getLastHPos();

	/**
	 * This method is identical to the <code>getNextHPos</code> method of
   * <code>oracle.dss.util.DataAccess</code> except that it does not have
   * the edge parameter.
	 *
	 * @see oracle.dss.util.DataDirector#getNextHPos
	 *
	 */
  public int[] getNextHPos(int[] hPos) throws LayerOutOfRangeException, SliceOutOfRangeException;

	/**
	 * This method is identical to the <code>getPrevHPos</code> method of
   * <code>oracle.dss.util.DataAccess</code> except that it does not have
   * the edge parameter.
	 *
	 * @see oracle.dss.util.DataDirector#getPrevHPos
	 *
	 */
  public int[] getPrevHPos(int[] hPos)throws LayerOutOfRangeException, SliceOutOfRangeException;

	/**
	 * This method is identical to the <code>changeEdgeCurrentHPos</code> method of
         *    * <code>oracle.dss.util.DataDirector</code> except that it does not have
   * the edge parameter.
	 *
	 * @see oracle.dss.util.DataDirector#changeEdgeCurrentHPos
	 *
	 */
  public boolean changeEdgeCurrentHPos(int[] hPos, int maxLayerSpecified) throws LayerOutOfRangeException, SliceOutOfRangeException, DataDirectorException;

}