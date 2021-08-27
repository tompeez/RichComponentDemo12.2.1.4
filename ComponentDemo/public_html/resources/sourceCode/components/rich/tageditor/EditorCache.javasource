/** Copyright (c) 2006, 2009 Oracle and/or its affiliates. All rights reserved */

package oracle.adfdemo.view.components.rich.tageditor;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

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

public class EditorCache extends BaseEditorCache {
   
    private static final String ADF_FACES_DEMO_PATH = "/oracle/adfdemo/view/components/rich/tageditor/adf-faces-config.xml";
    private static final String DVT_FACES_DEMO_PATH = "/oracle/adfdemo/view/components/rich/tageditor/dvt-faces-config.xml";
    private static final String ADDON_FACES_DEMO_PATH = "/oracle/adfdemo/view/components/rich/tageditor/dvt-addon-faces-config.xml";
    private boolean _init = false;
    
    public void init() {
        if (_init)
            return;
        // DVT component faces config
        try {
            super.init();        
            parseConfigFile(DVT_FACES_DEMO_PATH);
            parseConfigFile(ADDON_FACES_DEMO_PATH);            
            _init = true;
        } catch (ParserConfigurationException e) {
        } catch (SAXException e) {
        } catch (IOException e) {
        }

    }
}
