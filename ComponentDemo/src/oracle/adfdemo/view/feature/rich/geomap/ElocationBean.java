package oracle.adfdemo.view.feature.rich.geomap;

import java.net.MalformedURLException;
import java.net.URL;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.geoMap.MapConfiguration;
import oracle.adf.view.faces.bi.component.geoMap.UIGeoMap;

import oracle.adfdemo.view.components.rich.ComponentEditorHandler;
import oracle.adfdemo.view.components.rich.tageditor.ExtendedComponentEditorHandler;

/**
 * Backing bean to return a UIGeoMap that has been configured with the correct
 * URL settings
 */
public class ElocationBean {
    private UIComponent _component;
    
    private UIComponent m_countryMap;
    private UIComponent m_continentMap;
    
    public void setCountryMap(UIComponent map){
        m_countryMap = map;
        setGeoMapConnections(m_countryMap);
    }
    
    public void setContinentMap(UIComponent map){
        m_continentMap = map;
        setGeoMapConnections(m_continentMap);
        
    }
    public UIComponent getCountryMap(){
        return m_countryMap;
    }
    
    public UIComponent getContinentMap(){
        return m_continentMap;
    }
    
    // Try to reuse the tag inspector backing bean first so that tag guide pages
    // will continue to work
    public void setComponent(UIComponent component) {
        _component = component;
        setGeoMapConnections(component);
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExtendedComponentEditorHandler extEditor = (ExtendedComponentEditorHandler)facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "extEditor");
        ComponentEditorHandler editor = (ComponentEditorHandler)facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "editor");
        
        if (extEditor != null) {
            extEditor.setComponent(component);
        }
        else if (editor != null) {
            editor.setComponent(component);
        }
    }
    
    public UIComponent getComponent() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExtendedComponentEditorHandler extEditor = (ExtendedComponentEditorHandler)facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "extEditor");
        ComponentEditorHandler editor = (ComponentEditorHandler)facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "editor");
        
        if (extEditor != null) {
            return extEditor.getComponent();
        }
        else if (editor != null) {
            return editor.getComponent();
        }
        
        return _component;
    }
    
    /**
     * Utility method to configure a UIGeoMap with the correct URL settings
     * @param component
     */
    public static void setGeoMapConnections(UIComponent component) {
        if (component instanceof UIGeoMap)   
        {
            UIGeoMap map = (UIGeoMap) component;

            try {
                MapConfiguration mapConfig = new MapConfiguration();                
                mapConfig.setMapViewerURL(new URL("http://elocation.oracle.com/mapviewer"));
                mapConfig.setGeocoderURL(new URL("http://elocation.oracle.com/geocoder/gcserver"));
                map.setMapConfig(mapConfig);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
