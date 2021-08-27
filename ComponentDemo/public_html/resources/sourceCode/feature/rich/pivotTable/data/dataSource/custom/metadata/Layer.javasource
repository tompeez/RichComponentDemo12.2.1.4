/*
** Copyright (c) 2008, 2009, Oracle and/or its affiliates. 
** All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata;

import java.util.Vector;

import oracle.dss.util.LayerMetadataMap;

/**
 * This class holds the metadata for a layer on an edge as well as the
 * <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Member</code> objects
 * that the layer contains.
 */
public class Layer {
	
	/**
	 * Constructor for this class.
	 */
	public Layer(){
	}

	/**
	 * Retrieves the layer's metadata based on type specified by
	 * <code>oracle.dss.util.LayerMetadataMap</code> constants.
   * This edge samples use this method to provide the metadata for a layer
   * when the method <code>getLayerMetadata</code> is called on
   * <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge.Edge</code>.
	 *	
	 * @param type The type of metadata requested.
	 *
	 * @return The metadata specified
	 *
	 * @see oracle.dss.util.LayerMetadataMap
	 *
	 */
	public Object getMetadata(String type){
		if(type.equals(LayerMetadataMap.LAYER_METADATA_NAME))
			return getName();
		else if(type.equals(LayerMetadataMap.LAYER_METADATA_LONGLABEL))
			return getLongLabel();
		else if(type.equals(LayerMetadataMap.LAYER_METADATA_MEDIUMLABEL))
			return getMediumLabel();
		else if(type.equals(LayerMetadataMap.LAYER_METADATA_SHORTLABEL))
			return getShortLabel();
		else 
			return null;
	}
	
	/**
	 * Retrieves the name of the layer.
	 *	
	 * @return The layer's name.
	 */
	public String getName(){
		return m_name;
	}
	
	/**
	 * Specifies the name of the layer.
	 *	
	 * @param name The layer's name
	 */
	public void setName(String name){
		m_name = name;
	}

	/**
	 * Retrieves the layer's long label.
	 *	
	 * @return The layer's long label.
	 */
	public String getLongLabel(){
		return m_longLabel;
	}

	/**
	 * Specifies the layer's long label.
	 *	
	 * @param longLabel The layer's long label.
	 */
	public void setLongLabel(String longLabel){
		m_longLabel = longLabel;
	}
			
	/**
	 * Retrieves the layer's medium label.
	 *	
	 * @return The layer's medium label
	 */
	public String getMediumLabel(){
		return m_mediumLabel;
	}

	/**
	 * Specifies the layer's medium label.
	 *	
	 * @param mediumLabel The layer's medium label
	 */
	public void setMediumLabel(String mediumLabel){
		m_mediumLabel = mediumLabel;
	}

	/**
	 * Retrieves the layer's short label.
	 *	
	 * @return The layer's short label.
	 */
	public String getShortLabel(){
		return m_shortLabel;
	}
	
	/**
	 * Specifies the layer's short label.
	 *	
	 * @param shortLabel The layer's short label.
	 */
	public void setShortLabel(String shortLabel){
		m_shortLabel = shortLabel;
	}

	/**
	 * Retrieves the layer's members as a vector of
	 * <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Member</code> objects.
	 *	
	 * @return A vector of the layer's
   * <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Member</code> objects.
	 */
	public Vector getMembers(){
		return m_members;
	}

	/**
	 * Specifies the layer's members as a vector of
	 * <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Member</code> objects.
	 *	
	 * @param dimValues A vector of the layer's
   *                  <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Member</code>
   *                  objects
	 */
	public void setMembers(Vector members){
		m_members = members;
	}

	/**
	 * Retrieves a <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Member</code>
   * by its value.
	 *	
	 * @param value The value of the <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Member</code> that is being searched for
	 *
	 * @return A <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Member</code>,
   *         if a match is found.
	 */
	public Member getMember(String value){
		Member member = null;
		for(int i=0;i<m_members.size();i++){
			member = (Member)m_members.elementAt(i);
			if( value.equals(member.getValue()) )
				break;
		}
		return member;
	}
	
	protected String 	m_name				=	null;
	protected String 	m_longLabel		=	null;
	protected String 	m_mediumLabel	=	null;
	protected String 	m_shortLabel	=	null;
	protected Vector  m_members 		= null;
}