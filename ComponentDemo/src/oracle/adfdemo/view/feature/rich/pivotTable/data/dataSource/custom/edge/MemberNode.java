/*
** Copyright (c) 2008, 2009, Oracle and/or its affiliates. 
** All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge;

import java.util.Vector;

import oracle.dss.util.MetadataMap;

import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Layer;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Member;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;

/**
 * This class extends Vector to provide the implementation of a node in a tree
 * of members on an edge. This class and the related classes that are
 * mentioned in this description reside in the data source samples package
 * <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom</code>.
 * <p>
 * The tree provides a data model for the following edge classes:
 * <ul>
 * <li> <code>edge.AsymmetricEdge</code>
 * <li> <code>edge.AsymmetricPageEdge</code>
 * </ul>
 * These edge classes delegate many of the methods from the edge interfaces
 * <code>edge.Edge</code> and <code>edge.PageEdge</code> to
 * <code>MemberNode</code>.
 *
 * @hidden
 */
public class MemberNode extends Vector {
		
	/**
	 * Constructor for this class.
	 *
	 * @param parent    The parent node in the tree that contains this node.
	 * @param member  	The member that this node represents.
	 * @param layer 		The layer for the member.
	 * @param depth 		The depth for the member.
	 * 
	 * @see oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Layer
	 * @see oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Member
	 *
	 */
	public MemberNode(MemberNode parent, Member member, Layer layer, int depth){
		m_parent	= parent;
		m_member	= member;
		m_layer 	= layer;
		m_depth		= depth;
	}

	/**
	 * Indicates whether this node is the root of a tree.
	 *	
	 * @return <code>true</code> if the node is the root; <code>false</code>
   *                           if the node is not the root.
	 *
	 */	
	public boolean isRoot(){
		return (m_parent==null);		
	}		
	
	/**
	 * Retrieves the depth for a node.
	 *	
	 * @return The node's depth, which determines the depth passed back
   *         from the <code>getMemberDepth</code> methods of the
   *         <code>edge.Edge</code> interface and the
   *         <code>oracle.dss.util.DataAccess</code> interface.
	 *
	 * @see oracle.adfdemo.view.feature.rich.graph.dataSource.edge.Edge#getMemberDepth
	 * @see oracle.dss.util.DataAccess#getMemberDepth
	 *
	 */
	public int getDepth(){			
		return m_depth;
	}

	/**
	 * Specifies the depth for a node.
	 *	
	 * @param depth     The node's depth, which determines the depth
   *                  passed back from the <code>getMemberDepth</code>
   *                  method of the <code>edge.Edge</code> interface and the
   *                  <code>oracle.dss.util.DataAccess</code> interface.
   *
   * @see oracle.adfdemo.view.feature.rich.graph.dataSource.edge.Edge
	 * @see oracle.dss.util.DataAccess
	 *
	 */	
	public void setDepth(int depth){			
		m_depth = depth;
	}

	/**
	 * Retrieves the member of the node.
	 *
	 * @return The node's member.
	 *
	 * @see oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.Member
	 *
	 */
	public Member getMember(){
		return m_member;
	}

	/**
	 * Retrieves the layer for a node's member
	 *	
	 * @return The layer for a node's member
	 *
	 * @see oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.Layer
	 *
	 */
	public Layer getLayer(){
		return m_layer;
	}

	/**
	 * Retrieves the parent for a member
   * on a node.
	 *	
	 * @return The node's parent MemberNode.
	 *
	 * @status New
	 */	
	public MemberNode getParent(){
		return m_parent;
	}

	/**
	 * Specifies a new parent for a member on a node.
	 *
	 * @param parent The node's new parent MemberNode.
	 *
	 */	
	public void setParent(MemberNode parent){
		m_parent = parent;
	}

	/**
	 * Retrieves the number of leaves in a tree under a specific node.
	 *
	 * @return The number of leaves in the tree under this node.
	 *
	 */	
  protected int getLeafCount(){	  	
  	int total = 0;	  		 	  	
  	if(size()==0)
  		total = 1;
  	else {
	  	total = 0;	  		 	  	
	  	for(int i=0;i<size();i++){	  		
  			MemberNode node = (MemberNode)elementAt(i);
 				total = total + node.getLeafCount();
  		}
  	}
  	if(m_bLogging)
  		log("GetLeafCount() = " + total);
  	return total;
 	}

	/**
	 * Finds the child node of the node that contains the leaf node that is
	 * specified by <code>leafIndex</code>.
   * This is a relative method that processes a subtree,
	 * so leafIndex is not the same as the absolute index on the edge,
   * except when this method is called on the root node of the tree.
	 *	
	 * @param leafIndex   The index of the leaf node.
	 *
	 * @return A FindResults instance that contains the node, the number of leaves
   *         under the children before the node, and the index of the node.
	 *
	 */
  public FindResults findNode(int leafIndex){
  	int offset = 0;	  		 
  	int index = 0;
  	MemberNode node=null;
		
  	// check to see which node the index is located in
  	for(index=0;index<size();index++){
  		node = (MemberNode)elementAt((int)index);
 			if( (offset + node.getLeafCount()) > leafIndex )
 				break;	  			
 			offset = offset + node.getLeafCount();
			// if we did not find the node, then set it to null and continue
			node = null;
  	}
  	if(node!=null){
	  	if(m_bLogging)
  			log("FindNode(" + leafIndex + ") = [" + node.getMember().getValue() + "," + offset + "," + index + "]");	  	
		} else if( (size()==0) && (leafIndex==0))
			offset=-1;
			
  	return new FindResults(node,offset,index);
  }
		
	/**
	 * Searches recursively for the node at a specific layer that contains
	 * the leaf node that is identified by <code>leafIndex</code>.
	 *	
	 * @param layer      The layer of the node to return.
	 * @param leafIndex  The index of the leaf node under the node to return.
	 *
	 * @return  The MemberNode at the layer that contains the leaf node.
         * 	 *
	 */	
  public MemberNode getMemberNode(int layer, int slice) throws SliceOutOfRangeException, LayerOutOfRangeException {
		MemberNode node=null;

		// check if this is a leaf node and that the layer and slice is 0, which means that the corrent bottom node has been found
		if(size()==0 && slice==0 && layer<=0 )
			node = this;
		else {
			// find the child of this node that contains the leaf node
			FindResults fResults = findNode(slice);
 			if(fResults.m_node!=null){
 				// if we are not at the correct depth yet, then adjust the layer and slice
 				// and recursively call this method again. Otherwise, we've found the node.
				if(layer>0)
					node = fResults.m_node.getMemberNode(layer-fResults.m_node.getDepth(),slice - fResults.m_offset);
 				else
					node = fResults.m_node;
			} else {							
				if( (layer!=0) && (fResults.m_node.size()==0) )
					throw new LayerOutOfRangeException("layer out of range");
				else
					throw new SliceOutOfRangeException("slice out of range");			
			}
		}

  	if(m_bLogging)
			log("getMemberNode(" + layer + "," + slice + ") = " + node);		
		return node;
	}
		
	/**
	 * Creates a label for a slice by appending the long labels for all
	 * MemberNodes that are parents of the leaf node.
   * This method provides the implemetation for the <code>getSliceLabel</code> method
   * of the <code>oracle.adfdemo.view.feature.rich.graph.dataSource.Edge</code> interface and
   * the <code>getSliceLabel</code> method of the
   * <code>oracle.dss.util.DataAccess</code> interface.
	 *	
	 * @param slice The index of the slice under the node to return.
	 *
	 * @return The label for the slice.
	 *
	 * @see oracle.adfdemo.view.feature.rich.graph.dataSource.edge.Edge#getLabel
	 * @see oracle.dss.util.DataAccess#getColumnLabel
   * @see oracle.dss.util.DataAccess#getPageLabel
   * @see oracle.dss.util.DataAccess#getRowLabel
   *
	 */
	public String getSliceLabel(int slice){
		String label = null;		

		// get the label for this node		
		if(isRoot()==false)
			label =	(String)getMember().getMetadata(MetadataMap.METADATA_LONGLABEL);	  	
		
		// find the node that contains the leaf node	
  	FindResults fResults = findNode(slice);	  	 					
  	MemberNode node = fResults.m_node;
 		if(node != null){
 			if(isRoot())
				label = node.getSliceLabel(slice - fResults.m_offset);
 			else	// recursively call getLabel on the node containing the leaf node
				label = label + "-" + node.getSliceLabel(slice - fResults.m_offset);
 		}  		
  	if(m_bLogging)
	  	log("GetSliceLabel(" + slice + ") = " + label);
  	return label;
	}
	
	/**
	 * Retrieves the number of <code>MemberNode</code> objects above the leaf
   * node.
   * This method provides the implemetation for the
   * <code>getSliceMemberCount</code> method on the Edge and DataAccess
   * interfaces.
	 *	
	 * @param leafIndex The index of the leaf node.
	 *
	 * @return The number of <code>MemberNode</code> objects above the leaf
   *         node.
	 *
	 * @see oracle.adfdemo.view.feature.rich.graph.dataSource.edge.Edge#getSliceMemberCount
	 * @see oracle.dss.util.DataAccess#getSliceMemberCount
	 *
	 */
	public int getSliceMemberCount(int slice) throws SliceOutOfRangeException {
		int count = 1;

		// find the node that contains the leaf node	
  	FindResults fResults = findNode(slice);	  	 					
 		if(fResults.m_node != null){
 			if(isRoot())
				count = fResults.m_node.getSliceMemberCount(slice - fResults.m_offset);
			else // add one to the depth for each node that is found
				count = fResults.m_node.getSliceMemberCount(slice - fResults.m_offset) + 1;
		}	else if(isRoot()){			
			// return a default if the edge is empty
			if(size()==0)
				count=0;
			else
 				throw new SliceOutOfRangeException("slice = " + slice + " is not valid");
		}
  	if(m_bLogging)
	  	log("getSliceMemberCount(" + slice + ") = " + count);
  	return count;
	}

	/**
	 * Retrieves the layer at the start of a node that is identified by
	 * <codelayer</code> and <code>slice</code>.
   * This method provides the implemetation for the
	 * <code>getMemberStartLayer</code> method on the <code>Edge</code> and
   * <code>DataAccess</code> interfaces.
	 *	
	 * @param layer              The layer of the node.
	 * @param slice  		         The index of the leaf under the node.
	 * @param layerCount         Internal state variable; 0 when the method is
   *                           called on the root.
	 * @param logicalLayerOffset If this argument is not null, then the logical
	 * 										       layer offset is placed into the arg.
	 *
	 * @return   The layer at the start of node that is identified by
   *           <code>layer</code> and <code>slice</code>.
	 *
	 * @see oracle.adfdemo.view.feature.rich.graph.dataSource.edge.Edge#getMemberStartLayer
	 * @see oracle.dss.util.DataAccess#getMemberStartLayer
	 *
	 */	
	public int getMemberStartLayer(int layer, int slice, int layerCount)throws LayerOutOfRangeException, SliceOutOfRangeException{
		// if we are at a leaf node, then return the current layer
		if(size()==0 && slice==0 && layer<=0)
			return layerCount;

		// if the layer requested is 0 and we are at the root, then return 0
		if((isRoot()==true)&&(layer==0))
			return 0;
		
		// find the node that contains the leaf node
		FindResults fResults = findNode(slice);
 		if(fResults.m_node!=null){
 			int startLayer=0;
			if((layer-fResults.m_node.getDepth())<0){
	 			// if the node that has been found is the target
				startLayer = layerCount;
			} else {
				// the node is not the target, so decrement the layer, increase the layerCount and continue
				layer = layer - fResults.m_node.getDepth();
				layerCount = layerCount + fResults.m_node.getDepth();
				startLayer = fResults.m_node.getMemberStartLayer(layer,slice - fResults.m_offset,layerCount);
			}

		  if(m_bLogging)
				log("getMemberStartLayer(" + layer + "," + slice + "," + layerCount + ") = " + startLayer);
			return startLayer;
		} 				
			
		if( (layer!=0) && (fResults.m_node.size()==0) )
			throw new LayerOutOfRangeException("Layer out of range");
		else
 			throw new SliceOutOfRangeException("Slice out of range"); 			
	}


	/**
	 * Retrieves the index at the start of node identified by layer and leafIndex.
   * This method provides the implemetation for the
	 * <code>getMemberStartSlice</code> method on the Edge and DataAccess
   * interfaces.
	 *	
	 * @param layer     The layer of the node.
	 * @param slice		 	The index of the leaf under the node.
	 *
	 * @return The slice at the start of the node that is identified by
	 *         <code>layer</code> and <code>slice</code>.
	 *
	 * @see oracle.adfdemo.view.feature.rich.graph.dataSource.edge.Edge#getMemberStartSlice
	 * @see oracle.dss.util.DataAccess#getMemberStartSlice
	 *
	 */
  public int getMemberStartSlice(int layer, int slice) throws SliceOutOfRangeException, LayerOutOfRangeException {			
			// if we are at the leaf node, then return 0
			if( (layer<=0) && (isRoot()==false) )
				return 0;
			
		// find the node that contains the leaf node
			FindResults fResults = findNode(slice);
  		if(fResults.m_node!=null){
  			int startSlice=0;
				if(isRoot())
 					startSlice = fResults.m_offset + fResults.m_node.getMemberStartSlice(layer,slice - fResults.m_offset);
				else if(layer>0)
 					startSlice = fResults.m_offset + fResults.m_node.getMemberStartSlice(layer-fResults.m_node.getDepth(),slice - fResults.m_offset);

		  	if(m_bLogging)
					log("getDimensionSpanStartIndex(" + layer + "," + slice + ") = " + startSlice);
				
				return startSlice;
 			} else if((layer==1) && ((layer-getDepth())<=0) )
 				return 0;
 			
 			if( (layer!=0) && (fResults.m_node.size()==0) )
 				throw new LayerOutOfRangeException("layer out of range");
 			else
  			throw new SliceOutOfRangeException("slice out of range"); 					
  }			


	/**
	 * Find the node at a specific layer that contains the leaf node that is
   * identified by <code>hPos</code> and <code>memberLayer</code>.
	 *	
	 * @param hPos         An array of indexes that provides the location of a
   *                     node.
	 * @param memberLayer  The layer of the member.
	 *
	 * @return The MemberNode at the layer that contains the leaf node.
	 *
	 */	
  public MemberNode getMemberNode(int[] hPos, int memberLayer) throws SliceOutOfRangeException, LayerOutOfRangeException {		
		MemberNode node=null;
		if(memberLayer==-1)
			node = this;
		else {
			node = (MemberNode)elementAt(hPos[0]);				
			for(int i=1;i<=memberLayer;i++){
				if(hPos[i]!=-1)
					node = (MemberNode)node.elementAt(hPos[i]);
			}
		}
		return node;
  }

	/**
	 * Retrieves all of the member indexes for all layers above
	 * the leaf node identified by slice.
   * This method provides the implemetation for the
	 * <code>getMemberHPos</code> method on the Edge and DataAccess
   * interfaces.
	 *	
	 * @param slice The index that identifies the leaf node.
	 *
	 * @return    An array of member indexes.
	 *
	 * @see oracle.adfdemo.view.feature.rich.graph.dataSource.edge.Edge#getMemberHPos
	 * @see oracle.dss.util.DataAccess#getMemberHPos
	 *
	 */
	public int[] getMemberHPos(int layer, int slice) throws SliceOutOfRangeException {
		// create the context array and call fillContextFromAbsolute
		int [] hPos = new int[layer +1];			
		fillMemberHPos(0,slice, hPos, layer);
  	    if(m_bLogging){
			log("getMemberHPos(" + slice +") = ");		
			for(int i=0;i<hPos.length;i++)
				log("----> = [" + i + "] = " + hPos[i]);							
  	    }
		return hPos;
  }

	// internal method used to populate hPos for each layer up to max layer
  private void fillMemberHPos(int layer, int slice, int[] hPos, int maxLayer) {
		FindResults fResults = findNode(slice);
		hPos[(int)layer] = fResults.m_index;			
		layer = layer + 1;
		if( (fResults.m_node!=null) && (layer<maxLayer))
			fResults.m_node.fillMemberHPos(layer,slice - fResults.m_offset,hPos,maxLayer);
  }

	/**
	 * Retrieves a new leaf index given an <code>hPos</code> that identifies the
   * current leaf index and the description of a layer index change.
   * This method is called by <code>AsymmetricPageEdge</code> to calculate
   * the new page index after a page change has occurred.
	 *	
	 * @hPos 		     An array of layer indexes that identifies a leaf node.
	 * @changeLayer  The layer where an index change occurred.
	 * @changeIndex  The new index at the changeLayer.
	 * @currentLayer Internal method variable that should be set to 0 when
   *               calling the root node
	 *
	 * @return The new leaf index.
	 *
	 */	
  public int getSpecificLocation(int[] hPos, int changeLayer, int changeIndex, int currentLayer){
		int location=0;
		if(currentLayer==changeLayer){
			if(size()!=0){
				// get the total number of cells underneath the dimensions before the dimension in context
				for(int i=0;i<changeIndex;i++)
					location = location + ((MemberNode)elementAt(i)).getLeafCount();
			} else
				location = changeIndex;
		}
		else if(currentLayer<changeLayer){
			location = ((MemberNode)elementAt((int)hPos[currentLayer])).getSpecificLocation(hPos, changeLayer, changeIndex, currentLayer+1);
			// get the total number of cells underneath the layers before the layer in context
			for(int i=0;i<hPos[currentLayer];i++)
				location = location + ((MemberNode)elementAt(i)).getLeafCount();				
		}
			
  	if(m_bLogging)
			log("getSpecificLocation([?]," + changeLayer +  "," + changeIndex + "," + currentLayer + ") = " + location);		
		return location;
  }			

 	// internal helper method that allows logging to be turned on/off easily
	protected void log(String s){
		if(m_bLogging&& getMember()!=null)
			System.out.println("MemberNode[" + getMember().getValue() + "]:" + s);
		else
			System.out.println("MemberNode[null]:" + s);			
	}
	
 	// inner class that stores the results of a findNode() call
	protected class FindResults {
		public MemberNode m_node;
		public int 	m_offset;
		public int 	m_index;
		
		public FindResults(MemberNode node, int offset, int index){
			m_node = node;
			m_offset = offset;
			m_index = index;
		}
	}		

	private MemberNode			m_parent		= null;
	private Member					m_member 		= null;
	private Layer			 			m_layer			= null;
	private int 						m_depth			=	1;
	private boolean 				m_bIsRoot 	= false;
	private boolean 				m_bLogging	= false;
}