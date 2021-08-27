/** Copyright (c) 2008, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich.tageditor;

import java.beans.PropertyDescriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.component.BIComplexAttributeBase;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import org.apache.myfaces.trinidad.bean.PropertyKey;


public class ChildComponentEditorHandler extends ExtendedComponentEditorHandler
{
  protected RichSelectOneChoice _richSelectOneChoice = null;
  public void setRichSelectOneChoice(RichSelectOneChoice richSelectOneChoice){
      _richSelectOneChoice = richSelectOneChoice;
  }
  public RichSelectOneChoice getRichSelectOneChoice(){
      return _richSelectOneChoice;
  }
  protected RichPanelHeader _richPanelHeader = null;
  public void  setRichPanelHeader(RichPanelHeader richPanelHeader){
      _richPanelHeader = richPanelHeader;
  }
  public RichPanelHeader getRichPanelHeader(){
      return _richPanelHeader;
  }

    public ChildTagHandle getChildValue(){
        return _currentChildTagHandle;
        
    }
  public void setChildValue(ChildTagHandle childTagHandle){
      _currentChildTagHandle = childTagHandle;
  }

  public void clearListOfChildTag(){
      _mapOfListOfAttributes = new HashMap<String,List>();
      _component = null;
      _name = null;
      _listOfChildTag = new ArrayList();
      if (_richSelectOneChoice!=null)
          _richSelectOneChoice.setValue("");
  }

    protected ChildComponentEditorHandler getChildEditor(){
        return this;
    }
    /**
     * Whenever the overall child tag list is changed, change the list of attributes
     * @param valueChangeEvent
     */
    public void valueChangeListener(ValueChangeEvent valueChangeEvent){
        String value = (String)valueChangeEvent.getNewValue();
        ChildTagHandle handle = null;
        for ( int i = 0; i < _listOfChildTag.size(); i++ ){
            handle = (ChildTagHandle)_listOfChildTag.get(i);
            if ( handle!=null && handle.getName().equals(value) ){
                _component = handle.getComponent();
                _name = handle.getName();
                continue;
            }
        }
        getAttributes();
    }
    public void mapNumberChangeListener(ValueChangeEvent valueChangeEvent){
    }
    public String getValue(){
        return _name;
    }
    public void setValue(String name){
        _name = name;
    }

    public Object getAttributeListComponent(){
        return _component;
    }
    
    private boolean _childTagRendered = false;
    
    public boolean getDisplayChildTagHandler(){
        return _childTagRendered;
    }
    public void setDisplayChildTagHandler(boolean childTagRendered){
        _childTagRendered = childTagRendered;
    }
    
    public List getCachedAttributeList(){
        if ( _mapOfListOfAttributes == null )
            _mapOfListOfAttributes = new HashMap<String,List>();
        List list = _mapOfListOfAttributes.get(_name);
        if (list != null)
            return list;
        return list;
    }
    public void setCachedAttributeList(List list){
        if ( _mapOfListOfAttributes == null )
            _mapOfListOfAttributes = new HashMap<String,List>();
        _mapOfListOfAttributes.put(_name, list);
    }
    public String getClassName(){
        return _name;
    }
    public String getChildName(){
        return _name;
    }
    
    public AttribInfo getAttributeInfo(AttribParser attribParser, Object comp, PropertyDescriptor descriptor){
        AttribInfo attributeInfo = null;        
        String className = comp.getClass().getName();
        String name = descriptor.getName();
        // Loads our addon faces config for additional information for this attribute
        if ( attribParser != null ){
            attributeInfo = attribParser.getAttribValues(className, name);
            // Try again with bi base type
            if ( attributeInfo == null ){
                attributeInfo = attribParser.getAttribValues(BICOMPLEXATTRIBUTEBASE_CLASS, name);
            }
        }
        return attributeInfo;
    }
    
    public List getChildTagList(){
        return _listOfChildTag;
    }
    public void initialize(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExtendedComponentEditorHandler editor = (ExtendedComponentEditorHandler)facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "extEditor");
        _cachedComponent = editor._cachedComponent;
        if ( _cachedComponent !=null ){
            setDisplayChildTagHandler(true);
        }
        else{
            setDisplayChildTagHandler(false);
        }
    }
    public void addComponent(BIComplexAttributeBase biproperty, String propertymapname, String name)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExtendedComponentEditorHandler editor = (ExtendedComponentEditorHandler)facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "extEditor");
        _cachedComponent = editor._cachedComponent;
        if ( _listOfChildTag == null )
            _listOfChildTag = new ArrayList();
        _listOfChildTag.add( new ChildTagHandle( biproperty, propertymapname, name ) );
    }
    
    protected PropertyOfComponent getAdditionalPoc(Object comp, String compName, PropertyOfComponent poc, AttribInfo attributeInfo, PropertyDescriptor descriptor){
        return getMapPoc((BIComplexAttributeBase)comp, compName, poc, attributeInfo, descriptor);
    }
    /**
     * Constructs the child of child tag
     * @param comp          Current BI Component
     * @param compName      Name of the Current BI Component
     * @param poc           Current Attribute
     * @param attributeInfo Additional Info for this attribute
     * @param descriptor    Description of this attribute
     * @return the new poc
     */
     private PropertyOfComponent getMapPoc(BIComplexAttributeBase comp, String compName, PropertyOfComponent poc, AttribInfo attributeInfo, PropertyDescriptor descriptor){
        
        
      if ( attributeInfo != null && descriptor.getPropertyType() == Map.class ){
          try{
              // Firstly, try to construct the attribute and see whether it is a BI property
              String labelName = attributeInfo.getMapComponentClassName();
              if ( labelName == null )
                  return poc;
              
              poc = new PropertyOfComponent.MapComponent(comp, descriptor);
              String[] attributeValues = attributeInfo.getAttributeValues();
              if ( attributeValues != null ){                
                  poc.setType("mapEnum");
                  poc.setAttribValues(attributeValues);
              }
              
              Class mapComponentClass = Class.forName(labelName);
              Object instance = mapComponentClass.newInstance();
              if ( instance instanceof BIComplexAttributeBase ) {
                  BIComplexAttributeBase biproperty = (BIComplexAttributeBase)instance;       
                  
                  // If property key/ label name are different, overrides them
                  if ( attributeInfo != null ){
                      String propertyKey = attributeInfo.getPropertyMapKeyName();
                      String newLabelName = attributeInfo.getLabelName();
                      if ( newLabelName != null )
                          poc.setLabelName(newLabelName);
                      if ( propertyKey != null )
                          poc.setPropertyKey(propertyKey);
                  }
                  
                  poc.setPropertyKey(Character.toUpperCase(poc.getPropertyKey().charAt(0))+poc.getPropertyKey().substring(1) );
                  
                  // Puts a new map / uses an existing map for this attribute
                  PropertyKey key = comp.getPropertyKey(poc.getPropertyKey());
                  Map propertyMap =  (Map)comp.getProperty(key);
                  if ( propertyMap == null ){
                      propertyMap = new HashMap();
                      comp.setProperty(key, propertyMap);
                  }
                  
                  // Sets up the map poc for later use for adding this component to the overall child tag list
                  ((PropertyOfComponent.MapComponent)poc).setUIComponent(_cachedComponent);
                  ((PropertyOfComponent.MapComponent)poc).setHigherLevelComponentName(_name);
                  ((PropertyOfComponent.MapComponent)poc).setMapComponentClass(mapComponentClass);
                  ((PropertyOfComponent.MapComponent)poc).setMap(propertyMap);
              }
          }
          catch(InstantiationException e){;}
          catch(IllegalAccessException e){;}      
          catch(ClassNotFoundException e){;}
      }
      return poc;
  }
  
  protected BIComplexAttributeBase _component;
  protected boolean     _alternateComponentsChecked = false;
  protected String      _name;
  protected List        _listOfChildTag;

  protected Map<String,List> _mapOfListOfAttributes;
  protected static final String BICOMPLEXATTRIBUTEBASE_CLASS =
      "oracle.adf.view.faces.bi.component.BIComplexAttributeBase";
}
