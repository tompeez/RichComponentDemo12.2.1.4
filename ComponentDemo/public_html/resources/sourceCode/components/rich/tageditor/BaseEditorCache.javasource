/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/components/rich/tageditor/BaseEditorCache.java /main/2 2009/10/05 13:09:46 glook Exp $ */

/* Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ytang       09/16/09 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/components/rich/tageditor/BaseEditorCache.java /main/2 2009/10/05 13:09:46 glook Exp $
 *  @author  ytang   
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.components.rich.tageditor;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/*
 * This class caches the
 * 1. XML metadata
 * 2. property descriptors from bean introspection
 */


public class BaseEditorCache {
    private AttribParser _attribParser;
    private HashMap _descMap;
    private SAXParser _saxParser = null;
    
    /**
     * Initiates this class
     */
    public BaseEditorCache(){
        _attribParser = new AttribParser();
        _descMap = new HashMap();
        _saxParser = null;
    }

    /**
     * Initiates the sax parser
     * @throws ParserConfigurationException
     * @throws SAXException
    */
    public void init() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        _saxParser = factory.newSAXParser();
        
    }
    /**
     * Parses a faces config file
     * @param path the path of the file
     * @throws SAXException
     * @throws IOException
     */
    public void parseConfigFile(String path) throws SAXException, IOException {
        if ( _saxParser != null ){
            InputStream stream;
            stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            _saxParser.parse(stream, _attribParser);            
        }
    }


    /**
     * Parses a faces config file
     * @param configFile the name of the config file to parse, relative to the 
     * location of the editor cache class
     * @throws SAXException
     * @throws IOException
     */
    public void parseConfigFileClassRelative(String configFile) throws SAXException, IOException {
        if ( _saxParser != null ){
            String configFullPath;
            configFullPath = getClass().getResource(configFile).getFile();
            InputStream stream = new FileInputStream(configFullPath);

            //InputStream stream;
            //stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            _saxParser.parse(stream, _attribParser);            
        }
    }

    /**
     * Returns Attribute parser
     * @return the Attribute parser
     */
    public AttribParser getAttribParser() {
        return _attribParser;
    }
    
    /**
     * Returns cached property descriptors
     * @param uiComp UIComponent to introspec
     */
    public PropertyDescriptor[] getDescriptors(Class uiComp) {
        if (_descMap.containsKey(uiComp))
            return (PropertyDescriptor[])_descMap.get(uiComp);
        
        try {
            PropertyDescriptor[] desc = Introspector.getBeanInfo(uiComp).getPropertyDescriptors();
            _descMap.put(uiComp, desc);
            return desc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

