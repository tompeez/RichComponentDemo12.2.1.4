/*
** Copyright (c) 2008, 2009, Oracle and/or its affiliates. 
** All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge;

import oracle.dss.util.MetadataMap;

import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Layer;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Member;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;

/**
 * Implements <code>edge.Edge</code>
 * and provides a single layer on the row or column edge for a data source
 * sample that is derived from <code>EdgeBasedDataSource</code> in
 * <code>oracle.adfdemo.view.feature.rich.graph.dataSource</code>.
 * 
 * @hidden
 */
public class SingleLayerEdge implements Edge {    

	/**
	 * Constructor for this class.
	 *
	 * @param layer    The layer to display on the edge.
	 * 
	 * @see oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.Layer
	 *
	 */
	public SingleLayerEdge(Layer layer){
		setLayer(layer);
	}

	/**
	 * Retrieves the layer on an edge.
	 *	
	 * @return The edge's layer.
	 *
	 * @see oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.Layer
	 */
	public Layer getLayer(){
		return m_layer;
	}

	/**
	 * Specifies the layer on an edge.
	 *
	 * @param dim    The edge's new layer.
	 *
	 * @see oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.Layer
	 * 
	 */
	public void setLayer(Layer layer){
		m_layer = layer;
	}
	
  public int getExtent() {
		// returns the number of values in the edge's layer (This could be number or rows, columns, or pages)
 		return getLayer().getMembers().size();
 	}
  
 	public Object getSliceLabel(int slice) throws SliceOutOfRangeException{
 		try {
			// returns the member's long label
			return getMemberMetadata(0, slice, MetadataMap.METADATA_LONGLABEL);
 		} 
 		catch(LayerOutOfRangeException e){
 			e.printStackTrace();
 		}
 		catch(SliceOutOfRangeException e){
 			e.printStackTrace();
 		}
 		return null;
	}

  public int getLayerCount() {
		// this edge only has a single layer
		return 1;
  }

  public int getSliceMemberCount(int slice) throws SliceOutOfRangeException {
		// this edge only has a single layer
		return 1;
 	}

  public int getMemberDepth(int layer, int slice) throws LayerOutOfRangeException, SliceOutOfRangeException {
		// since the edge has only one layer, the depth is always 1
 	 	return 1;
 	}

  public int getMemberStartLayer(int layer, int slice) throws LayerOutOfRangeException, SliceOutOfRangeException {
		// since the edge has only one layer, the member always starts at index 0
  	return 0;
  }

  public int getMemberLogicalLayer(int layer, int slice) throws LayerOutOfRangeException, SliceOutOfRangeException {
		// since the edge has only one layer, the member always is on layer 0
  	return 0;
  }

  public Object getLayerMetadata(int layer, String type) throws LayerOutOfRangeException {
  	// return the layer's metadata.  The Layer class contains its metadata. 
		return m_layer.getMetadata(type);
  }
   
  public int getMemberExtent(int layer, int slice) throws SliceOutOfRangeException, LayerOutOfRangeException {
		// since the edge has only one layer, the member's extent is always 1
		return 1;
  }
 
  public int getMemberStartSlice(int layer, int slice) throws SliceOutOfRangeException, LayerOutOfRangeException {
		// since the edge has only one layer, the member's always starts at its index
 		return slice;
 	}
 
  public Object getMemberMetadata(int layer, int slice, String type) throws SliceOutOfRangeException, LayerOutOfRangeException {
		// The Layer class contains its members.  The Member class contains member metadata. 
  	Member member = (Member)getLayer().getMembers().elementAt(slice);
		return member.getMetadata(type);
	}
  
	public int getMemberSiblingCount(int[] hPos, int memberLayer) throws LayerOutOfRangeException, SliceOutOfRangeException{	
		if(memberLayer==0)
			return getExtent();
		else
			return 0;
	}
		
	public Object getMemberMetadata(int[] hPos, int memberLayer, int hIndex, String type) throws LayerOutOfRangeException, SliceOutOfRangeException{
		if( memberLayer==0 )
			return getMemberMetadata(0,hIndex,type);
		else
			return null;	
	}

	public int[] getMemberHPos(int layer, int slice) throws LayerOutOfRangeException, SliceOutOfRangeException{
		int[] hPos = new int[1];
		hPos[0] = slice;
		return hPos;		  	
  }

  
	// the edge's layer
 	protected Layer m_layer				= null;
}