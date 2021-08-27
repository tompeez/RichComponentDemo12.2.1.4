/* Copyright (c) 2007, 2009, Oracle and/or its affiliates. 
All rights reserved. */
package oracle.adfdemo.view.components.rich.tageditor;

import java.io.IOException;

import java.io.StringReader;

import java.util.Arrays;
import java.util.HashMap;

import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AttribParser extends DefaultHandler {
    private Map<String, AttribInfo> m_attribMap;
    private String m_attribKey;
    private String m_className;
    private String m_propertyName;
    private boolean m_componentClassTag;
    private boolean m_propertyNameTag;
    private boolean m_attribValueTag;
    private boolean m_attribValueMapTag;
    private boolean m_deprecatedTag;
    private boolean m_hiddenTag;
    private AttribInfo m_currentAttributeInfo;
    private boolean m_labelNameTag;
    private boolean m_defaultValueTag;
    private boolean m_multipleSelectionTag;
    private boolean m_propertyMapKeyTag;
    private boolean m_mapComponentClassTag;
    private boolean m_preferredTypeTag;
    private boolean m_descriptionTag;
    private boolean m_propertyTag;
    private boolean m_propertyCloseTag;

    public AttribParser() {
        m_attribMap = new HashMap<String, AttribInfo>();
        m_attribKey = "";
        m_className = "";
        m_componentClassTag = false;
        m_propertyNameTag = false;
        m_propertyMapKeyTag = false;
        m_attribValueTag = false;
        m_attribValueMapTag = false;
        m_labelNameTag = false;
        m_defaultValueTag = false;
        m_hiddenTag = false;
        m_deprecatedTag = false;
        m_multipleSelectionTag = false;
        m_mapComponentClassTag = false;
        m_preferredTypeTag = false;
        m_descriptionTag = false;
        m_propertyName = "";
        m_currentAttributeInfo = new AttribInfo();
    }

    public AttribInfo getAttribValues(String className, String propertyName) {
        return (AttribInfo)m_attribMap.get(className + "." + propertyName);
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        m_propertyTag = qName.equals("property");
        m_propertyCloseTag = qName.equals("/property");
        m_componentClassTag = qName.equals("component-class");
        m_propertyNameTag = qName.equals("property-name");
        m_attribValueTag = qName.equals("attribute-values") || qName.equals("fmd:property-values");
        m_attribValueMapTag = qName.equals("attribute-value-map");
        m_deprecatedTag = qName.equals("deprecated") || qName.equals("fmd:deprecated");
        m_hiddenTag = qName.equals("hidden");
        m_defaultValueTag = qName.equals("default-value");
        m_labelNameTag = qName.equals("label-name");
        m_propertyMapKeyTag = qName.equals("property-map-key");
        m_multipleSelectionTag = qName.equals("multiple-selection");
        m_mapComponentClassTag = qName.equals("map-component-class");
        m_preferredTypeTag = qName.equals("preferred-property-type");
        m_descriptionTag = qName.equals("description");
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        m_propertyCloseTag = qName.equals("property");
            
        if (m_propertyCloseTag) {            
            m_attribKey = m_className + "." + m_propertyName;
            AttribInfo existAttributeInfo = m_attribMap.get(m_attribKey);
            if ( existAttributeInfo != null ){
                existAttributeInfo.mergeAttributes(m_currentAttributeInfo);
                m_attribMap.put(m_attribKey, existAttributeInfo);
            }
            else {
                m_attribMap.put(m_attribKey, m_currentAttributeInfo);
            }
            m_propertyCloseTag = false;
        }
    }
    
    

    @Override
    public void characters(char[] ch, int start,
                           int length) throws SAXException {
        
        String value = String.copyValueOf(ch, start, length);
        
        if (m_componentClassTag) {
            m_className = value;            
            m_componentClassTag = false;
        }
        
        else if (m_propertyTag) {            
            m_currentAttributeInfo = new AttribInfo();
            m_propertyTag = false;
        }

        
        else if (m_propertyNameTag) {            
            m_propertyName = value;
            m_propertyNameTag = false;
        }
        
        else if (m_labelNameTag) {            
            m_currentAttributeInfo.setLabelName(value);
            m_labelNameTag = false;
        }
        
        else if (m_defaultValueTag) {            
            m_currentAttributeInfo.setDefaultValue(value);
            m_defaultValueTag = false;
        }
        
        else if (m_propertyMapKeyTag) {            
            m_currentAttributeInfo.setPropertyMapKeyName(value);
            m_propertyMapKeyTag = false;
        }
        
        else if (m_hiddenTag) {
            m_currentAttributeInfo.setHidden(true);
            m_hiddenTag = false;
        }
        
        else if (m_deprecatedTag) {
            m_currentAttributeInfo.setDeprecated(true);
            m_deprecatedTag = false;
        }
        
        else if (m_multipleSelectionTag) {
            m_currentAttributeInfo.setMultipleSelection(true);
            m_multipleSelectionTag = false;
        }
        
        else if (m_mapComponentClassTag) {
            m_currentAttributeInfo.setMapComponentClassName(value);
            m_mapComponentClassTag = false;
        }
        
        else if (m_preferredTypeTag) {
            m_currentAttributeInfo.setPreferredType(value);
            m_preferredTypeTag = false;
        }
        
        else if (m_descriptionTag) {
            m_currentAttributeInfo.setDescription(value);
            m_descriptionTag = false;
        }

        else if (m_attribValueTag) {
            String[] dataStr = value.split("\\s");
            if (dataStr.length > 0){
                for (int i = 0; i < dataStr.length; i++){
                    if ( dataStr[i].indexOf("+") != -1 )
                       dataStr[i] = dataStr[i].replace('+', ' ');
                }
                m_currentAttributeInfo.setAttributeValues(dataStr);
            }
            m_attribValueTag = false;
        }
        else if (m_attribValueMapTag) {
            String[] dataStr = value.split("\\s");
            if (dataStr.length > 0){
                Map<String, Integer> attributeMap = new HashMap<String, Integer>();
                for (String keyvalues:dataStr){
                    String[] keyValuePair = keyvalues.split("=");
                    if (keyValuePair != null && keyValuePair.length>=2 ){
                        String key = keyValuePair[0];
                        String keyvalue = keyValuePair[1];
                        if ( key != null && keyvalue != null ){
                            Integer keyvalueInt = Integer.parseInt(keyvalue);
                            if ( keyvalueInt != null ){
                                attributeMap.put(key, keyvalueInt);
                            }
                        }
                    }
                }
                m_currentAttributeInfo.setAttributeValueMap(attributeMap);
            }
            m_attribValueMapTag = false;
        }
    }
    


    public Map<String, AttribInfo> getAttribMap() {
        return m_attribMap;
    }

    @Override
    // Disable DTD validation to avoid resource not found error
    public InputSource resolveEntity(String publicId,
                                     String systemId) throws IOException,
                                                             SAXException {
        return new InputSource(new StringReader(""));        
    }
}
