/*
** Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.graph.dataSource.metadata;

import java.util.Vector;
import java.util.Hashtable;

import oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.Layer;
	
/**
 * AsymmetricLayer extends
 * <code>oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.Layer</code> and
 * holds extra metadata for an asymmetric layer.
 * This class is used by
 * <code>oracle.adfdemo.view.feature.rich.graph.dataSource.AsymmetricEdge</code>. 
 * 
 * An asymmetric layer contains information about its related parent and
 * child layers, as well as information about how its layer members
 * map to a related parent's layer member.  This information is used
 * to figure out how to draw asymmetric layers when two related 
 * layers are on the same edge.
 */
public class AsymmetricLayer extends Layer{
	
	/**
	 * Constructor for this class.
	 *
	 */
	public AsymmetricLayer(){
	}

	/**
	 * Retrieves the layer's related parent layers as
	 * a Vector of layer names as Strings.
   * For example, State is a related parent of City.
	 *	
	 * @return A Vector of layer names as Strings
	 *
	 * @status New
	 */
	public Vector getRelatedParentLayers(){
		return m_relatedParentLayers;
	}
	
	/**
	 * Specifies the layer's related parent layers as
	 * a Vector of layer names as Strings.
	 *	
	 * @param relatedParentlayers A Vector of layer names as Strings.
	 *
	 */
	public void setRelatedParentLayers(Vector relatedParentLayers){
		m_relatedParentLayers = relatedParentLayers;
	}

	/**
	 * Retrieves the layer's related child layers as
	 * a Vector of layer names as Strings.
   * For example, City is a related child of State.
	 *	
	 * @return A Vector of layer names as Strings
	 */
	public Vector getRelatedChildLayers(){
		return m_relatedChildLayers;
	}
	
	/**
	 * Specifies the layer's related child layers as
	 * a Vector of layer names as Strings.
	 *	
	 * @param relatedChildLayers A Vector of layer names as Strings
	 */
	public void setRelatedChildLayers(Vector relatedChildLayers){
		m_relatedChildLayers = relatedChildLayers;
	}
		
	/**
	 * Adds a relation between a specified layer member
	 * and a Vector of the layer members in a related child
   * layer.
   * For example, "Massachusetts" in the state layer is related to "Boston"
   * and "Worcester" in the city layer.
	 *	
	 * @param member                     The value of the Member in the
   *                                     current layer.
	 * @param relatedChildMembers  A Vector of child Members.
	 */
	public void addRelatedChildMembers(String member, Vector relatedChildMembers){
		if(m_relatedChildMembers==null)
			m_relatedChildMembers = new Hashtable();
			
		m_relatedChildMembers.put(member, relatedChildMembers);
	}

	/**
	 * Removes a relation between a specified layer member 
   * and a Vector of the layer members in a related child layer.
	 *	
	 * @param member The value of the Member in the current layer.
	 */
	public void removeRelatedChildMembers(String member){
		if(m_relatedChildMembers!=null)
			m_relatedChildMembers.remove(member);		
	}

	/**
	 * Retrieves a Vector of the layer members in a
   * related child layer that is based on the layer member that
	 * is passed in.
	 *	
	 * @param member The value of the Member in the current layer.
	 */
	public Vector getRelatedChildMembers(String member){
		if(m_relatedChildMembers!=null)
			return (Vector)m_relatedChildMembers.get(member);

		return null;				
	}

	/**
	 * Retrieves the Member in this layer that is related to a
   * specific child value in a child layer.
	 *	
	 * @param childLayer       The name of the child layer.
	 * @param relatedChild The value of the Member in the child
   *                             layer.
	 */
	public Member getMemberByRelatedChildMemberValue(String childLayer, String relatedChildMemberValue){
		Member member = null;
		Member relatedMember = null;
		Vector relatedMembers = null;
		for(int i=0;i<m_members.size();i++){
			member = (Member)m_members.elementAt(i);
			relatedMembers = getRelatedChildMembers(member.getValue());
			if(relatedMembers!=null){
				for(int j=0;j<relatedMembers.size();j++){
					relatedMember = (Member)relatedMembers.elementAt(j);
					if( (relatedMember.getLayer().equals(childLayer)==true) && relatedMember.getValue().equals(relatedChildMemberValue)==true)
						return member;
				}
			}
		}
		return null;
	}

	protected Vector  m_relatedParentLayers 			= null;
	protected Vector  m_relatedChildLayers 				= null;	
	protected Hashtable m_relatedChildMembers = null;
}