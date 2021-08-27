/* Copyright (c) 2007, 2009, Oracle and/or its affiliates. 
All rights reserved. */
package oracle.adfdemo.view.components.rich.tageditor;

import java.util.Map;

/**
 * This class is to store the attribute information parsed from faces-config
 */
public class AttribInfo {
    
    private Boolean m_deprecated;
    private Boolean m_hidden;
    private Boolean m_multipleSelection;
    private String m_defaultValue;
    private String m_labelName;
    private String[] m_attributeValues;
    private Map m_attributeValueMap;
    private String m_propertyMapKeyName;
    private String m_mapComponentClassName;
    private String m_preferredType;
    private String m_description;
    
    
    /**
     * Constructs an attribute info object
     */
    public AttribInfo(){
        m_deprecated = false;
        m_hidden = false;
        m_multipleSelection = false;
        m_attributeValues = null;
        m_attributeValueMap = null;
        m_description = null;
        m_labelName = null;
        m_defaultValue = null;
        m_propertyMapKeyName = null;
        m_mapComponentClassName = null;
        m_preferredType = null;
    }
    
    /**
     * Gets the attribute values
     * @return the attribute values 
     */
    public String[] getAttributeValues() {
        return m_attributeValues;
    }
    /**
     * Sets the attribute values
     * @param attributeValues the attribute values
     */
    public void setAttributeValues(String[] attributeValues){
        m_attributeValues = attributeValues;
    }
    
    /**
     * Gets the attribute values map
     * @return the attribute values map
     */
    public Map getAttributeValueMap() {
        return m_attributeValueMap;
    }
    /**
     * Sets the attribute values map
     * @param attributeValueMap the attribute values map
     */
    public void setAttributeValueMap(Map attributeValueMap){
        m_attributeValueMap = attributeValueMap;
    }
    
    /**
     * Gets the description
     * @return the attribute description
     */
    public String getDescription() {
        return m_description;
    }
    /**
     * Sets the description
     * @param description the attribute description
     */
    public void setDescription(String description){
        m_description = description;
    }
    
    /**
     * Gets whether this attribute is deprecated
     * @return whether this attribute is deprecated
     */
    public Boolean isDeprecated() {
        return m_deprecated;
    }
    
    /**
     * Sets whether this attribute is deprecated
     * @param deprecated whether this attribute is deprecated
     */
    public void setDeprecated(Boolean deprecated){
        m_deprecated = deprecated;
    }
    
    /**
     * Gets whether this attribute is hidden
     * @return whether this attribute is hidden
     */
    public Boolean isHidden() {
        return m_hidden;
    }
    
    /**
     * Sets whether this attribute is hidden
     * @param hidden whether this attribute is hidden
     */
    public void setHidden(Boolean hidden){
        m_hidden = hidden;
    }
    
    /**
     * Gets whether this attribute can have multiple values
     * @return whether this attribute can have multiple values
     */
    public Boolean isMultipleSelection() {
        return m_multipleSelection;
    }
    
    /**
     * Sets whether this attribute can have multiple values
     * @param multipleSelection whether this attribute can have multiple values
     */
    public void setMultipleSelection(Boolean multipleSelection){
        m_multipleSelection = multipleSelection;
    }
    
    /**
     * Gets the name of the label of this attribute
     * @return the name of the label of this attribute
     */
    public String getLabelName() {
        return m_labelName;
    }
    
    /**
     * Sets the name of the label of this attribute
     * @param labelName the name of the label of this attribute
     */
    public void setLabelName(String labelName){
        m_labelName = labelName;
    }
    
    /**
     * Gets the name of the Property Map Key of this attribute
     * @return the name of the Property Map Key of this attribute
     */
    public String getPropertyMapKeyName() {
        return m_propertyMapKeyName;
    }
    
    /**
     * Sets the name of the Property Map Key of this attribute
     * @param propertyMapKeyName the Property Map Key of this attribute
     */
    public void setPropertyMapKeyName(String propertyMapKeyName){
        m_propertyMapKeyName = propertyMapKeyName;
    }
    
    /**
     * Gets the name of the Map Component Class of this attribute
     * @return the name of the Map Component Class of this attribute
     */
    public String getMapComponentClassName() {
        return m_mapComponentClassName;
    }
    
    /**
     * Sets the name of the Map Component Class of this attribute
     * @param mapComponentClassName the Map Component Class of this attribute
     */
    public void setMapComponentClassName(String mapComponentClassName){
        m_mapComponentClassName = mapComponentClassName;
    }
    
    /**
     * Gets the name of the preferred type this attribute
     * @return the name of the preferred type this attribute
     */
    public String getPreferredType() {
        return m_preferredType;
    }
    
    /**
     * Sets the name of the preferred type this attribute
     * @param preferredType the preferred type of this attribute
     */
    public void setPreferredType(String preferredType){
        m_preferredType = preferredType;
    }
    
    /**
     * Gets the name of the label of this attribute
     * @return the name of the label of this attribute
     */
    public String getDefaultValue() {
        return m_defaultValue;
    }
    
    /**
     * Sets the name of the label of this attribute
     * @param defaultValue the name of the label of this attribute
     */
    public void setDefaultValue(String defaultValue){
        m_defaultValue = defaultValue;
    }
    
    public void mergeAttributes(AttribInfo another){
        if (another.getDefaultValue() != null)
            setDefaultValue( another.getDefaultValue() );
        if (another.getAttributeValueMap() != null)
            setAttributeValueMap( another.getAttributeValueMap() );
        if (another.getAttributeValues() != null)
            setAttributeValues( another.getAttributeValues() );
        if (another.getDescription() != null)
            setDescription( another.getDescription() );
        if (another.getLabelName() != null)
            setLabelName( another.getLabelName() );
        if (another.getMapComponentClassName() != null)
            setMapComponentClassName( another.getMapComponentClassName() );
        if (another.getPreferredType() != null)
            setPreferredType( another.getMapComponentClassName() );
        if (another.getPropertyMapKeyName() != null)
            setPropertyMapKeyName( another.getPropertyMapKeyName() );
        if (another.isHidden() != null)
            setHidden( another.isHidden() );
        if (another.isDeprecated() != null)
            setDeprecated( another.isDeprecated() );
        if (another.isMultipleSelection() != null)
            setMultipleSelection( another.isMultipleSelection() );
    }
}
