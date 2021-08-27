/*
** Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.graph.dataSource.edge;

import java.util.Vector;


import oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.Layer;
import oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.Member;
import oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.AsymmetricLayer;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;

/**
 * Implements the <code>edge.Edge</code> interface and
 * provides a row or column edge that contains multiple symmetric or asymmetric
 * layers for a data source sample that is derived from
 * <code>EdgeBasedDataSource</code>.
 * This class and the supporting classes that are mentioned in this description
 * reside in the package <code>oracle.adfdemo.view.feature.rich.graph.dataSource</code>.
 * <p>
 * An asymmetric edge can contain many different types of asymmetry, such as 
 * varying layers (layers with depth>1) or related layers where
 * members vary based on related layers that are closer to the top
 * of the edge. 
 * <p>
 * Internally, this class is based on a tree data structure that uses
 * <code>edge.MemberNode</code> for the nodes in the tree.
 * Each node has a parent member node, layer, member, and depth.
 * Each node is logically a single member.
 * This class contains a reference to the root node and uses methods on
 * <code>edge.MemberNode</code> for many of the <code>Edge</code> methods.
 * <p>
 * Many methods use the <code>log</code> method to capture method invocations
 * and results.
 * To enable logging, set the <code>m_logging</code> variable to true.
 *
 * @hidden
 */
public class AsymmetricEdge implements Edge {

	/**
	 * Constructor for this class.
	 *
	 * @param layers    An array of symmetric and/or asymmetric layers that will be rendered by the edge
	 * 
	 * @see oracle.dss.samples.metadata.Layer
	 * @see oracle.dss.samples.metadata.AsymmetricLayer
	 *
	 *
	 */
	public AsymmetricEdge(Layer[] layers){
		setLayers(layers);
	}


	////////////////////////////////////////////////////////////////////////////////////
	// AsymmetricEdge methods
	
	/**
	 * Retrieves the edge's Layers
	 *	
	 * @return The edge's Layers
	 *
	 * @see oracle.dss.samples.metadata.Layer
	 * 
	 *
	 */
	public Layer[] getLayers(){
		return m_layers;
	}

	/**
	 * Specifies the edge's Layers.
	 *
	 * @param dim    The edge's new layers
	 *
	 * @see oracle.dss.samples.metadata.Layer
	 * 
	 *
	 */
	public void setLayers(Layer[] layers){
		m_layers = layers;
		// when the layers are set on the edge, reconstruct the edge tree model
		m_memberRoot = createEdgeTree(m_layers);
	}
			
  public int getExtent() {
  	// the count is the number of leaves in the layer value tree	  	
  	int count = m_memberRoot.getLeafCount();
	  log("AsymmetricEdge:getExtent() = " + count);
  	return count;
	}
	  
 	public Object getSliceLabel(int slice) throws SliceOutOfRangeException{
  	// delegate to the root MemberNode
  	String label = m_memberRoot.getSliceLabel(slice);
  	log("AsymmetricEdge:getSliceLabel(" + slice + ") = " + label);
  	return label;
	}

  public int getLayerCount() {
  	// The Layer count is always constant, even on an asymmetric edge. 
		int count = m_layers.length;
  	log("AsymmetricEdge:getLayerCount() = " + count);
  	return count;
  }

  public int getSliceMemberCount(int slice) throws SliceOutOfRangeException {
  	// delegate to the root MemberNode
		int depth = m_memberRoot.getSliceMemberCount(slice);
  	log("AsymmetricEdge:getSliceMemberCount(" + slice + ") = " + depth);
  	return depth;
 	}

  public int getMemberDepth(int layer, int slice) throws LayerOutOfRangeException, SliceOutOfRangeException {
  	// have the root MemberNode find the MemberNode by layer and index
		MemberNode node = m_memberRoot.getMemberNode(layer,slice);		
		// get the depth from the MemberNode
		int depth = node.getDepth();		
		log("AsymmetricEdge:getMemberDepth(" + layer + "," + slice + ") = " + depth);
	  return depth;
  }

  public int getMemberStartLayer(int layer, int slice) throws LayerOutOfRangeException, SliceOutOfRangeException {
  	// delegate to the root MemberNode
 	 	int startLayer = m_memberRoot.getMemberStartLayer(layer, slice, 0);
		log("AsymmetricEdge:getMemberStartLayer(" + layer + "," + slice + ") = " + startLayer);
  	return startLayer;
  }

  public Object getLayerMetadata(int layer, String type) throws LayerOutOfRangeException {		
		// This call is used with getLayerCount() to find metadata for all the layers
		// on the edge. This call ignores the location on the edge and thus varying member depths. 
		Object value = null;
		value = m_layers[layer].getMetadata(type);
	 	log("AsymmetricEdge:getLayerMetadata(" + layer + "," + type + ") = " + value);				
  	return value;
  }

  public int getMemberExtent(int layer, int slice) throws SliceOutOfRangeException, LayerOutOfRangeException {
  	// have the root MemberNode find the MemberNode by layer and slice
		MemberNode node = m_memberRoot.getMemberNode(layer, slice);
		
		// the extent is the number of leaves under the node
		int extent = node.getLeafCount();
		if(extent==0) // leaf nodes by definition have an extent of 1
			extent=1;		
	 	log("AsymmetricEdge:getMemberExtent(" + layer + "," + slice + ") = " + extent);
		return extent;
  }

  public int getMemberStartSlice(int layer, int slice) throws SliceOutOfRangeException, LayerOutOfRangeException {
  	// delegate to the root MemberNode
		int memberStartSlice = m_memberRoot.getMemberStartSlice(layer, slice);
	 	log("AsymmetricEdge:getMemberStartSlice(" + layer + "," + slice + ") = " + memberStartSlice);
		return memberStartSlice;
 	}    
  
  public Object getMemberMetadata(int layer, int slice, String type) throws SliceOutOfRangeException, LayerOutOfRangeException {
  	// have the root MemberNode find the MemberNode by layer and slice
		MemberNode node = m_memberRoot.getMemberNode(layer, slice);
		Object value = node.getMember().getMetadata(type);
	 	log("AsymmetricEdge:getMemberMetadata(" + layer + "," + slice + "," + type + ") = " + value);
		return value;
	}

	public int getMemberSiblingCount(int[] hPos, int memberLayer) throws LayerOutOfRangeException, SliceOutOfRangeException{	
  	// have the root MemberNode find the MemberNode by hPos and memberLayer
		MemberNode node = m_memberRoot.getMemberNode(hPos, memberLayer-1);
		int count = node.size();
	 	log("AsymmetricEdge:getMemberSiblingCount(");									
		if(hPos!=null){
			for(int i=0;i<hPos.length;i++){
		 		log("---->hPos[" + i + "] = " + hPos[i]);									
			}
		} 
	 	log("," + memberLayer + ") = " + count);									
		return count;
	}

	public Object getMemberMetadata(int[] hPos, int memberLayer, int hIndex, String type) throws LayerOutOfRangeException, SliceOutOfRangeException{
  	// have the root MemberNode find the MemberNode by hPos and memberLayer
		MemberNode node = m_memberRoot.getMemberNode(hPos, memberLayer-1);					
		Object value = ((MemberNode)node.elementAt(hIndex)).getMember().getMetadata(type);
	 	log("AsymmetricEdge:getMemberMetadata(");									
		if(hPos!=null){
			for(int i=0;i<hPos.length;i++){
		 		log("---->hPos[" + i + "] = " + hPos[i]);									
			}
		} 
	 	log("," + memberLayer + "," + hIndex + "," + type + ") = " + value);									
		return value;
 	}		

	public int[] getMemberHPos(int layer, int slice) throws LayerOutOfRangeException, SliceOutOfRangeException{
		int[] hPos = m_memberRoot.getMemberHPos(layer, slice);		
	 	log("AsymmetricEdge:getMemberHPos(" + slice + ") = ");									
		if(hPos!=null){
			for(int i=0;i<hPos.length;i++){
		 		log("---->hPos[" + i + "] = " + hPos[i]);									
			}
		} 		
		return hPos;
	}

	/**
	 * Internal method called by setLayers(). It regenerates
	 * the tree of MemberNodes that represents the edge.
	 *	
	 * @param layers An array of Layers on the edge.
	 *
	 * @return The root MemberNode for the new tree.
	 *
	 *
	 */
	protected MemberNode createEdgeTree(Layer[] layers){
		// create a "blank" root node
		MemberNode root = null;
		Member member = new Member();
		member.setValue(new String(""));
		root = new MemberNode(null, member,null,-1);
		
		// call createMemberNodes to create the tree
		createMemberNodes(root, layers , 0, new Vector(), new Vector());
		return root;
	}
	
	/**
	 * An internal recursive method called by <code>createEdgeTree</code>.
	 *	
	 * @param parent               The parent MemberNode that this routine will
   *                             add children to.
	 * @param layers               The array of Layers on the edge.
	 * @param layer                The current layer in the layers array that
   *                             is being created.
	 * @param relatedParentMembers When there are multiple related layers on the
   *                             edge, a vector of RelatedValues that identify
   *                             parent members (that is, MemberNodes above the
   *                             current nodes that are being created) is
   *                             passed down this recursive function.
   *                             The members of a child layer below a parent
   *                             layer are based on the related parent member.
	 * @param relatedChildMembers  When there are multiple related layers on the
   *                             edge, a vector of RelatedValues that identify
   *                             child members (that is, MemberNodes above the
   *                             current nodes that are being created)
	 *														 is passed down this recursive function.
   *                             The member of a parent layer below a child
   *                             layer is based on the related child member.
	 *
	 */
	protected void createMemberNodes(MemberNode parent, Layer[] layers, int iLayer, Vector parentRelatedMembers, Vector childRelatedMembers){		
		// if we have processed all of the layers, then return 
		if(iLayer>=layers.length)
			return;
		
		Layer layer = layers[iLayer];
		Vector members = layer.getMembers();
			
		boolean bRelatedParentBelow=false;
		boolean bRelatedChildBelow=false;
						
		if(layer instanceof AsymmetricLayer){
			// check if a related parent layer is in the layers after the current layer
			bRelatedParentBelow = findMatch(layers, iLayer, layers.length-1, ((AsymmetricLayer)layer).getRelatedParentLayers());
			if(bRelatedParentBelow==false)
				bRelatedChildBelow = findMatch(layers, iLayer, layers.length-1, ((AsymmetricLayer)layer).getRelatedChildLayers());
			
			// check if any of the parent related members are related to the current layer
			RelatedMember parentRelatedMember = findMatch( parentRelatedMembers, ((AsymmetricLayer)layer).getRelatedParentLayers() ); 						
			// if there is a parent, then find out what the members for this level should be
			if(parentRelatedMember!=null)
				members = ((AsymmetricLayer)parentRelatedMember.getLayer()).getRelatedChildMembers(parentRelatedMember.getMember().getValue());

			// check if any of the child related members are related to the current layer
			RelatedMember childRelatedMember = findMatch( childRelatedMembers, ((AsymmetricLayer)layer).getRelatedChildLayers() ); 						
			// if there is a child, then find out what the members for this level should be
			if(childRelatedMember!=null){
				// check the related members for each member in the current layer
				// to see which member contains the child 
				Member m = ((AsymmetricLayer)layer).getMemberByRelatedChildMemberValue(childRelatedMember.getLayer().getName(),childRelatedMember.getMember().getValue());
				if(m==null)
					members=null;
				else {
					// there is a single member
					members = new Vector();
					members.addElement(m);
				}
			}
		} 
		
		if( (members==null) || (members.size()==0) ){
			// we have a parent that does not contain a member at this level, so expand the
			// parent node's depth and move onto the next level
			parent.setDepth(parent.getDepth() + 1);
			createMemberNodes(parent,layers,iLayer+1,parentRelatedMembers, childRelatedMembers);			
		} else {						
			Vector newParentRelatedMembers = null;
			Vector newChildRelatedMembers = null;
			for(int i=0;i<members.size();i++){
				newParentRelatedMembers = parentRelatedMembers;
				newChildRelatedMembers = childRelatedMembers;
				
				// create the current member node
				Member member = (Member)members.elementAt(i);
				MemberNode newNode = new MemberNode(parent,member,layer,1);
				parent.addElement(newNode);
				if(bRelatedParentBelow==true) {
					// create a copy of the vector that was passed in, so that we don't
					// send the wrong information back up to our parents
					newChildRelatedMembers = new Vector();
					for(int index=0;index<childRelatedMembers.size();index++)
						newChildRelatedMembers.addElement(childRelatedMembers.elementAt(index));
					newChildRelatedMembers.addElement(new RelatedMember(layer,member));													
				} else if(bRelatedChildBelow==true){
					// Asymmetric child nodes need to understand which related member node is above it, 
					// so that it can only create the related members, so pass a relation with 
					// the current member down the chain!				
					
					// create a copy of the vector that was passed in, so that we don't
					// send the wrong information back up to our parents
					newParentRelatedMembers = new Vector();
					for(int index=0;index<parentRelatedMembers.size();index++)
						newParentRelatedMembers.addElement(parentRelatedMembers.elementAt(index));
					newParentRelatedMembers.addElement(new RelatedMember(layer,member));								
				}
				// recursively call createMemberNodes, until iLayer is equal to the number of layers
				createMemberNodes(newNode,layers,iLayer+1,newParentRelatedMembers, newChildRelatedMembers);
			}
		}		
	}

	// RelatedMember is an inner class that is used to pass back results from 
	// findMatch()
	protected class RelatedMember {
		protected RelatedMember(Layer layer, Member member){
			m_member = member;
			m_layer = layer;
		}
		
		protected Layer getLayer(){
			return m_layer;
		}
		
		protected Member getMember(){
			return m_member;
		}
		
		private Member m_member=null;
		private Layer m_layer=null;
	}
	
	// internal helper method that searches for the first RelatedMember that has a layer that
	// matches a layer in the layer array. 
	protected RelatedMember findMatch(Vector relatedMembers, Vector layers){
		if( (relatedMembers==null) || (layers==null))
			return null;

		RelatedMember relatedMember = null;
		Layer layer = null;
		for(int i=0;(i<relatedMembers.size())&&(relatedMember==null);i++){
			for(int j=0;(j<layers.size())&&(relatedMember==null);j++){
				layer = ((RelatedMember)relatedMembers.elementAt(i)).getLayer();
				if( ((String)layers.elementAt(j)).equals(layer.getName()) )
					relatedMember = (RelatedMember)relatedMembers.elementAt(i);
			}
		}	
		return relatedMember;		
	}

	// internal helper method that checks if there is a layer in the layer
	// array between specific layers that is the same as one of the layers in a
	// Vector of layers.
	public boolean findMatch(Layer[] layerArray, int startLayer, int endLayer, Vector layers){
		if( (layerArray==null) || (layers==null))
			return false;
			
		boolean bFoundOne = false;
		for(int i=startLayer;(i<=endLayer) && (bFoundOne==false);i++){
			for(int j=0;(j<layers.size())&&(bFoundOne==false);j++){
				if( ((String)layers.elementAt(j)).equals(layerArray[i].getName()) )
					bFoundOne = true;
			}
		}	
		return bFoundOne;		
	}
	
	protected MemberNode getRootNode(){
		return m_memberRoot;
	}
 
 	// internal helper method that allows logging to be turned on/off easily
	protected void log(String s){
		if(m_bLogging) 
			System.out.println(s);
	}

	// the layers on the edge
	protected Layer[]		m_layers	=null;
	
	// the root of the tree based representation of the edge
	protected MemberNode 	m_memberRoot=null;
	
	// are we logging or not
	protected 	boolean 			m_bLogging=false;

}