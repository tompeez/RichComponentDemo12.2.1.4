/*
** Copyright (c) 2008, 2009, Oracle and/or its affiliates. 
** All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge;


import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Layer;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Member;
import oracle.dss.util.DataDirectorException;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;

/**
 * Implements <code>edge.PageEdge</code>.
 * This class provides a page edge that can contain multiple symmetric
 * dimensions for a data source sample that is derived from
 * <code>EdgeBasedDataSource</code>
 * in the package <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom</code>.
 * The page edge keeps track of its current page.
 * 
 * @hidden
 */
public class SymmetricPageEdge implements PageEdge {	
	
	/**
	 * Constructor for this class.
	 *
	 * @param layers    An array of Layers that will be rendered by the edge.
	 * 
	 * @see oracle.dss.samples.metadata.Layer
	 *
	 */
	public SymmetricPageEdge(Layer[] layers) {
	 	setLayers(layers);		 	
	}

	/**
	 * This method is identical to the <code>getEdgeExtent</code> method of
  	 * <code>oracle.dss.util.DataAccess</code>.
	 *
	 * @see oracle.dss.util.DataAccess#getEdgeExtent
	 */
	public int getExtent()
	{
        int extent = 1;
        for(int i=0;i<m_layers.length;i++)
        {
            extent = extent * m_layers[i].getMembers().size();
        }
        System.out.println("Extent = " + extent);
        return extent;
	}


	/**
	 * Retrieves the edge's layers.
	 *	
	 * @return The edge's layers.
	 *
	 * @see oracle.dss.samples.metadata.Layer
	 *
	 */
	public Layer[] getLayers(){
		return m_layers;
	}

	/**
	 * Specifies the edge's layers.
	 *
	 * @param layers    The edge's new layers.
	 *
	 * @see oracle.dss.samples.metadata.Layer
	 *
   */
	public void setLayers(Layer[] layers){
		m_layers = layers;
		
		// initialize the hierarchical position to the first index
		m_hPos = new int[layers.length];
		for(int i=0;i<layers.length;i++)
			m_hPos[i] = 0;
	}
	
 	public int getLayerCount(){
 		return m_layers.length;
 	}

  public Object getLayerMetadata(int layer, String type) throws LayerOutOfRangeException{
		if(layer>=m_layers.length)
			throw new LayerOutOfRangeException(layer, m_layers.length-1);

  	return m_layers[layer].getMetadata(type);	
  }

	public int getMemberSiblingCount(int[] hPos, int memberLayer) throws LayerOutOfRangeException, SliceOutOfRangeException{
		if(memberLayer>=m_layers.length)
			throw new LayerOutOfRangeException(memberLayer, m_layers.length-1);

		return m_layers[memberLayer].getMembers().size();
	}
		
	public Object getMemberMetadata(int[] hPos, int memberLayer, int hIndex, String type) throws LayerOutOfRangeException, SliceOutOfRangeException{
		if(memberLayer>=m_layers.length)
			throw new LayerOutOfRangeException(memberLayer, m_layers.length-1);

		return ((Member)(m_layers[memberLayer].getMembers().elementAt(hIndex))).getMetadata(type);
	}

  public int[] getEdgeCurrentHPos(){
  	return m_hPos;
  }

  public int[] getFirstHPos(){				
		int[] hPos = new int[m_layers.length];
		for(int i=0;i<m_layers.length;i++)
			hPos[i] = 0;
		return hPos;
  }

  public int[] getLastHPos(){
		int[] hPos = new int[m_layers.length];
		for(int i=0;i<m_layers.length;i++)
			hPos[i] = m_layers[i].getMembers().size()-1;
		return hPos;
  }

  public int[] getNextHPos(int[] hPos)throws LayerOutOfRangeException, SliceOutOfRangeException{
		int[] tempHPos = new int[m_layers.length];
		boolean bDone=false;
		for(int i=(m_layers.length-1);i>=0;i--){
			if(!bDone && hPos[i]<m_layers[i].getMembers().size()-1){
				tempHPos[i]=hPos[i]+1;
				// set all of the layers below back to zero!
				for(int j=i+1;j<m_layers.length;j++)
					tempHPos[j]=0;
				
				bDone=true;
			} else
				tempHPos[i] = hPos[i];
		}
		if(bDone==false)
            return null;
		return tempHPos;
  }

  public int[] getPrevHPos(int[] hPos)throws LayerOutOfRangeException, SliceOutOfRangeException{
		int[] tempHPos = new int[m_layers.length];
		boolean bDone=false;
		for(int i=(m_layers.length-1);i>=0;i--){
			if(!bDone && hPos[i]>0){								
				tempHPos[i]=hPos[i]-1;				
				for(int j=i+1;j<m_layers.length;j++)
					tempHPos[j]=m_layers[j].getMembers().size()-1;				
				bDone = true;				
			} else
				tempHPos[i] = hPos[i];
		}
		if(bDone==false)
            return null;
		return tempHPos;
  }

  public boolean changeEdgeCurrentHPos(int[] hPos, int maxLayerSpecified) throws LayerOutOfRangeException, SliceOutOfRangeException, DataDirectorException{
		for(int i=0; (i<m_layers.length) && (i<=maxLayerSpecified);i++)
			m_hPos[i] = hPos[i];
		return true;
  }

	protected Layer[] m_layers = null;
	protected int[] m_hPos = null;
}
