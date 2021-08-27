/*
** Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.graph.dataSource.metadata;

import oracle.dss.util.MetadataMap;

/**
 * This class holds the metadata for a layer member
 * within a <code>oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.Layer</code>.
 */
public class Member {

	/**
	 * Constructor for this class.
	 */
	public Member(){
	}
	
	/**
	 * Retrieves the member's metadata based on type specified by
	 * <code>oracle.dss.util.MetadataMap</code> constants.
   * The edge samples use this method to provide the metadata for a member
   * when one of the following methods are called on
   * <code>oracle.adfdemo.view.feature.rich.graph.dataSource.edge.Edge</code>:
   * <ul>
   * <li> <code>getMemberMetadata</code> method
   * </ul>
	 *
	 * @param type The type of metadata requested.
	 *
	 * @return The metadata specified.
	 *
	 * @see oracle.dss.util.MetadataMap
	 */
	public Object getMetadata(String type){
		if(type.equals(MetadataMap.METADATA_VALUE))
			return getValue();
		else if(type.equals(MetadataMap.METADATA_LONGLABEL))
			return getLongLabel();
		else if(type.equals(MetadataMap.METADATA_MEDIUMLABEL))
			return getMediumLabel();
		else if(type.equals(MetadataMap.METADATA_SHORTLABEL))
			return getShortLabel();
		else if(type.equals(MetadataMap.METADATA_INDENT))
			return getIndent();
		else if(type.equals(MetadataMap.METADATA_DRILLSTATE))
			return getDrillState();
		else 
			return null;
	}

	/**
	 * Retrieves the member's layer name.
	 *	
	 * @return The member's layer name.
	 */
	public String getLayer(){
		return m_layer;
	}
	
	/**
	 * Specifies the member's layer name.
	 *	
	 * @param layer The member's layer name.
	 */
	public void setLayer(String layer){
		m_layer = layer;
	}
	
	/**
	 * Retrieves the member's value.
	 *	
	 * @return The member's value.
	 */
	public String getValue(){
		return m_value;
	}
	
	/**
	 * Specifies the member's value.
	 *	
	 * @param value The member's value.
	 */
	public void setValue(String value){
		m_value = value;
	}

	/**
	 * Retrieves the member's long label.
	 *	
	 * @return The member's long label
	 */
	public String getLongLabel(){
		return m_longLabel;
	}

	/**
	 * Specifies the member's long label.
	 *	
	 * @param longLabel The member's long label.
	 */
	public void setLongLabel(String longLabel){
		m_longLabel = longLabel;
	}
			
	/**
	 * Retrieves the member's medium label.
	 *	
	 * @return The member's medium label.
	 */
	public String getMediumLabel(){
		return m_mediumLabel;
	}

	/**
	 * Specifies the member's medium label.
	 *	
	 * @param mediumLabel The member's medium label.
	 */
	public void setMediumLabel(String mediumLabel){
		m_mediumLabel = mediumLabel;
	}

	/**
	 * Retrieves the member's short label.
	 *	
	 * @return The member's short label.
	 */
	public String getShortLabel(){
		return m_shortLabel;
	}
	
	/**
	 * Specifies the member's short label.
	 *
	 * @param shortLabel The member's short label.
	 */
	public void setShortLabel(String shortLabel){
		m_shortLabel = shortLabel;
	}

	/**
	 * Retrieves the member's indentation level (0,1,2,...).
	 *	
	 * @return The member's indentation level.
	 */
	public Integer getIndent(){
		return m_indent;
	}
	
	/**
	 * Specifies the member's indendation level (0,1,2,...)
	 *	
	 * @param indent The member's indentation level.
	 */
	public void setIndent(Integer indent){
		m_indent = indent;
	}

	/**
	 * Retrieves the member's drill state.
	 *	
	 * @return The member's drill state.
	 */
	public Integer getDrillState(){
		return m_drillState;
	}
	
	/**
	 * Specifies the member's drill state.
	 *	
	 * @param drillState The member's drill state.
   *                   Valid drill state constants begin with the prefix
   *                   "Drillstate" and are defined in
   *                   <code>oracle.dss.util.DataDirector</code>.
	 *
	 * @see oracle.dss.util.DataDirector
	 */
	public void setDrillState(Integer drillState){
		m_drillState = drillState;
	}

	protected String 	m_layer 	=	null;
	protected String 	m_value				=	null;
	protected String 	m_longLabel		=	null;
	protected String 	m_mediumLabel	=	null;
	protected String 	m_shortLabel	=	null;
	protected Integer m_indent  		= null;
	protected Integer m_drillState	= null;
}