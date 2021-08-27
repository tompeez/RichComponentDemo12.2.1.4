/*
** Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.graph.dataSource.edge;

import java.util.Vector;

import oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.Layer;
import oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.AsymmetricLayer;
import oracle.dss.util.DataDirectorException;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;

/**
 * Implements <code>edge.PageEdge</code> and
 * provides a row, column, or page edge that contains multiple symmetric or
 * asymmetric layers for a data source sample that is derived from
 * <code>EdgeBasedDataSource</code> in
 * <code>oracle.adfdemo.view.feature.rich.graph.dataSource</code>.
 * The page edge keeps track of its current page.
 * 
 * @hidden
 */
public class AsymmetricPageEdge extends AsymmetricEdge implements PageEdge {	
	
	/**
	 * Constructor for this class.
	 *
	 * @param dims    An array of symmetric and/or asymmetric Layers that will
   *                be rendered by the edge.
	 * 
	 * @see oracle.dss.samples.metadata.AsymmetricLayer
   * @see oracle.dss.samples.metadata.Layer
	 *
	 */
	public AsymmetricPageEdge(Layer[] layers){
	 	super(layers);		 	
	}
	
	public void setLayers(Layer[] layers){
		super.setLayers(layers);
		// initialize the hierarchical position to the first index
		m_hPos = getFirstHPos();
	}

  public int[] getEdgeCurrentHPos(){
		// initialize the hierarchical position to the first index
		int[] hPos = new int[m_hPos.length];
		for(int i=0;i<m_hPos.length;i++)
			hPos[i] = m_hPos[i];		
  	return hPos;
  }

  public boolean changeEdgeCurrentHPos(int[] hPos, int maxLayerSpecified) throws LayerOutOfRangeException, SliceOutOfRangeException, DataDirectorException {
		boolean bDone=false;
		for(int i=0;i<=maxLayerSpecified;i++){
			if(m_hPos[i] != hPos[i]){
				m_hPos[i] = hPos[i];
				
				// if the layer being changed is an asymmetric layer, 
				// then change all asymmetric layers after it to point to their
				// first value 
				if(!bDone && (m_layers[i] instanceof AsymmetricLayer)){
					for(int j=i+1;j<hPos.length;j++){
						if(m_layers[j] instanceof AsymmetricLayer)
							m_hPos[j]=0;
					}
					bDone=true;
				}							
			} 
		}

		fillHPosHoles(m_hPos);
        
        
		return true;
  }

  public int[] getFirstHPos(){
		MemberNode node = m_memberRoot;
		try {							
			int[] hPos = new int[getLayerCount()];
			for(int i=0;i<hPos.length;i++){
				if(node.size()!=0){
					node = (MemberNode)node.elementAt(0);
					if(node.getDepth()>0){
						for(int j=i;j<i+node.getDepth();j++){
							if(j==i)																	
								hPos[j]=0;
							else
								hPos[j]=-1;
						}
					} else
						hPos[i] = 0;				
				} else
					hPos[i]=0;
			}
			return hPos;
		} catch(Throwable t){
			t.printStackTrace();
		}
		return null;
  }

  public int[] getLastHPos(){
		MemberNode node = m_memberRoot;
		try {							
			int[] hPos = new int[getLayerCount()];
			for(int i=0;i<hPos.length;i++){
				if(node.size()!=0){
					int hIndex = node.size()-1;							
					node = (MemberNode)node.elementAt(hIndex);
					int endLayer = i+node.getDepth();
					int startLayer = i;
					while(true){
						if(i==startLayer)																	
							hPos[i]=hIndex;
						else
							hPos[i]=-1;
						
						if(i+1<endLayer)
							i++;
						else
							break;
					}
				} else
					hPos[i] = 0;
			}
			return hPos;
		} catch(Throwable t){
			t.printStackTrace();
		}
		return null;
  }

  public int[] getNextHPos(int[] hPos)throws LayerOutOfRangeException, SliceOutOfRangeException{
		// find the current leaf node
		MemberNode node = getRootNode();
		Vector nodeIndexes = new Vector();
		int index = 0;
		int layer=0;
		for(int i=0;i<hPos.length;i++){
			if( (node.size()>0) && (hPos[i]!=-1) ){
				layer = i;
				index = hPos[i];
				node = (MemberNode)node.elementAt(index);
			}
		}			
		
		// working up from the bottom construct a vector with the nodes in the new hPos		
		Vector nodes=null;						
		while((node!=null) && (node!=getRootNode())){							
			// try to go to the next node via the parent
			if(nodes==null){
				if(node.getParent().size() > index+1){
					MemberNode newNode = (MemberNode)node.getParent().elementAt(index+1);
					nodes = new Vector();
					while(newNode!=null){
						nodes.insertElementAt(newNode,0);
						if(newNode.size()>0)
							newNode = (MemberNode)newNode.elementAt(0);
						else 
							newNode = null;
					}
				}
			} else 
				nodes.addElement(node);
			
			// get the next node index
			layer--;
			for(;layer>=0;layer--){				
				if(hPos[layer]!=-1){
					index = hPos[layer];								
					break;
				}
			}

			node = node.getParent();
		}
			
		return buildHPosFromNodes(nodes);		
  }

  public int[] getPrevHPos(int[] hPos)throws LayerOutOfRangeException, SliceOutOfRangeException{
		// find the current leaf node
		MemberNode node = getRootNode();
		Vector nodeIndexes = new Vector();
		int index = 0;
		int layer=0;
		for(int i=0;i<hPos.length;i++){
			if( (node.size()>0) && (hPos[i]!=-1) ){
				layer = i;
				index = hPos[i];
				node = (MemberNode)node.elementAt(index);
			}
		}			

		// working up from the bottom construct a vector with the nodes in the new hPos		
		Vector nodes=null;						
		while((node!=null) && (node!=getRootNode())){							
			// try to go to the next node via the parent
			if(nodes==null){
				if(index>0){
					MemberNode newNode = (MemberNode)node.getParent().elementAt(index-1);
					nodes = new Vector();
					while(newNode!=null){
						nodes.insertElementAt(newNode,0);
						if(newNode.size()>0)
							newNode = (MemberNode)newNode.elementAt(newNode.size()-1);
						else 
							newNode = null;
					}
				}
			} else 
				nodes.addElement(node);
			
			// get the next node index
			layer--;
			for(;layer>=0;layer--){				
				if(hPos[layer]!=-1){
					index = hPos[layer];								
					break;
				}
			}

			node = node.getParent();
		}

		return buildHPosFromNodes(nodes);
  }

	private int[] buildHPosFromNodes(Vector nodes){
		int[] newHPos = new int[getLayerCount()];
		int layer = 0;
		MemberNode currentNode = null;
		for(int i=nodes.size()-1;i>=0;i--){
			currentNode = (MemberNode)nodes.elementAt(i);
			int hIndex = currentNode.getParent().indexOf(currentNode);
			int endLayer = layer + currentNode.getDepth();
			int startLayer = layer;
			for(;layer<endLayer;layer++){
				if(layer==startLayer)
					newHPos[layer]=hIndex;
				else
					newHPos[layer]=-1;				
			}			
		}				
		return newHPos;
	}

	protected int[] fillHPosHoles(int[] hPos){		
	    
        // set all -1 values to 0
        for(int i=0;i<m_layers.length;i++)
        {
            if(hPos[i]==-1)
                hPos[i]=0;
        }
        
		// change invalid values to -1
        MemberNode node = m_memberRoot;		
		for(int i=0;i<m_layers.length;){			
			node = (MemberNode)node.elementAt(hPos[i]);				
        	for(int j=i+1;j<i+node.getDepth();j++)
    			hPos[j] = -1;            
            i=i+node.getDepth();    
		}

        
		return hPos;
	}

	// the edge's current page
	private int[] m_hPos;
}