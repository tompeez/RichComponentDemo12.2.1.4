 /** Copyright (c) 2006, 2009 Oracle and/or its affiliates. All rights reserved */
 package oracle.adfdemo.view.components.rich.tageditor;

 import java.awt.Color;

 import java.beans.PropertyDescriptor;

 import java.lang.reflect.Method;

 import java.util.ArrayList;
 import java.util.Date;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Set;

 import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.component.BIComplexAttributeBase;

import oracle.adf.view.faces.bi.component.geoMap.PointStyleItem;

import org.apache.myfaces.trinidad.bean.PropertyKey;
import org.apache.myfaces.trinidad.context.RequestContext;

public class PropertyOfComponent implements Comparable {

     public PropertyOfComponent(Object component, String mapname,
                                PropertyDescriptor descriptor)
     {
         
         this(descriptor.getName(), descriptor.getPropertyType());
         _mapname = mapname;
         
     }
       public PropertyOfComponent(Object component,
                                  PropertyDescriptor descriptor)
       {
           this(descriptor.getName(), descriptor.getPropertyType());
           if ( component instanceof UIComponent ){
                _component = (UIComponent)component;
                _componentType = TYPE_UICOMPONENT;
           }
           else if ( component instanceof BIComplexAttributeBase ){
               _childComponent = (BIComplexAttributeBase)component;
               _componentType = TYPE_BIPROPERTY;
           }
           _descriptor = descriptor;
           
       }
       // Sort by the name of the property
       public int compareTo(Object o)
       {
         PropertyOfComponent poc = (PropertyOfComponent) o;
         return getName().compareTo(poc.getName());
       }
       
      public void setPropertyKey(String name){
           _propertyMapKey = name;
      }
      public void setLabelName(String name){
           _labelName = name;
      }
      public void setComponent(Object component){
          if ( component instanceof UIComponent ){
               _component = (UIComponent)component;
               _componentType = TYPE_UICOMPONENT;
          }
          else if ( component instanceof BIComplexAttributeBase ){
              _childComponent = (BIComplexAttributeBase)component;
              _componentType = TYPE_BIPROPERTY;
          }
      }
       
       public String getName(){
           return _labelName;
       }
       public String getPropertyKey(){
           return _propertyMapKey;
       }

       public String getType(){
         return _type;
       }
       
       protected void updateValue(Object value){
           if (_componentType == TYPE_UICOMPONENT && _component != null ) {
           
              Map attributeMap = _component.getAttributes();
              if ( attributeMap != null )
                _component.getAttributes().put(getPropertyKey(), value);
           }
           else if (_componentType == TYPE_BIPROPERTY && _childComponent != null ) {
              PropertyKey key = _childComponent.getPropertyKey(getPropertyKey());
              if ( key != null )
                 _childComponent.setProperty(key, value);
           }

       }
       /*
       private static Boolean convertStringToBool(Object booleanObject){
           Boolean returnvalue = null;
           if ( booleanObject instanceof Boolean )
               returnvalue = ((Boolean)booleanObject).booleanValue();
           else if ( booleanObject instanceof String ) {
               if ( booleanObject.equals("true") )
                   returnvalue = Boolean.TRUE;
               else if ( booleanObject.equals("false") )
                   returnvalue = Boolean.FALSE;
           }
           return returnvalue;
       }*/
       public void flushToComponent(){
         if (_valueSet){             

            updateValue(_value);
         }
         
       }

       protected Object getBeanProperty()
       {
         Method method = _descriptor.getReadMethod();
         try
         {        
             if (_componentType == TYPE_UICOMPONENT && _component != null ) {
                 return method.invoke(_component);
             }
             else if (_componentType == TYPE_BIPROPERTY && _childComponent != null ) {
                 return method.invoke(_childComponent);
             }
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }

         return getProperty();
       }


       protected Object getProperty()
       {
           try{
           Object obj = null;
           if ( _componentType == TYPE_UICOMPONENT ){
               Map attributeMap = _component.getAttributes();
               if ( attributeMap != null ){
                   obj = attributeMap.get(getPropertyKey());
               }
           }
           else {
               PropertyKey key = _childComponent.getPropertyKey( getName() );
               if ( key != null ){
                    obj = _childComponent.getProperty(key);
               }
           }
           return obj;
           }
           catch(Exception e){;}
           return null;
       }

       protected void setProperty(Object value)
       {
         if ("".equals(value))
           value = null;

         _valueSet = true;
         _value = value;
       }
       
       public String getDefaultAttribValue() {
           return _attribValues[0];
       }
     public String getDescription() {
         return _description;
     }
     public void setDescription(String description) {
         _description = description;
     }
       public String[] getAttribValues() {
           return _attribValues;
       }

       public void setAttribValues(String[] attribValues) {
           _attribValues = attribValues;
       }
       public void setUIComponent(UIComponent comp){
           _component = comp;
       }

       public PropertyOfComponent(  String name,
                                    Class type) {
             _name = name;
             _propertyMapKey = name;
             _labelName = name;
             
             if (type == String.class) {
                 _type = "string";
             } else if ((type == Integer.class) || (type == Integer.TYPE)) {
                 _type = "integer";
             } else if (type == Number.class) {
                 _type = "number";
             } else if (( type == Double.class ) || ( type == Double.TYPE ) ) {
                _type = "double";
             } else if ((type == Boolean.class) || (type == Boolean.TYPE)) {
                 _type = "boolean";
             } else if (type == Date.class) {
                 _type = "date";
             } else if (type == Color.class) {
                 _type = "color";
             } else if (type == List.class) {
                 _type = "list";
             } else if (type == Set.class) {
                 _type = "set";
             } else if (type == Map.class) {
                 _type = "map";
             } else if ((type == Character.class) || (type == Character.TYPE)) {
                 _type = "character";
             }  
             else {
                 _type = null;
             }
       }



     @Override
     public boolean equals(Object o) {
         PropertyOfComponent poc = (PropertyOfComponent)o;
         return _name.equals(poc.getName());
     }

     public void setDefaultValue(String defaultValue) {
         try{
             if (getType().equals("boolean"))
                 if ( defaultValue.equals("true") )
                     setValue(Boolean.TRUE);
             else if (getType().equals("string"))
                 setValue( defaultValue );
             else if (getType().equals("integer"))
                 setValue( Integer.parseInt(defaultValue) );
             else if (getType().equals("number"))
                 setValue( Double.parseDouble(defaultValue) );
             flushToComponent();
         }
         catch (Exception e){;}
     }
     public void setType(String type){
         _type = type;
     }
     
     public Object getValue() {
         if (getType().equals("boolean")) {
            return getBeanProperty();
         }
         else {
            return getProperty();
         }
     }

     public void setValue(Object value) {
         if ("".equals(value))
             value = null;

         _valueSet = true;
         _value = value;
     }
     
       protected String _name;
       protected String _type;
       protected String _mapname;

       protected String[] _attribValues;
       protected boolean _valueSet = false;
       protected Object  _value    = null;
       protected String _labelName = null;
       protected String _propertyMapKey = null;
       protected PropertyDescriptor _descriptor = null;
       protected String _description = null;
       protected UIComponent        _component = null;
       protected BIComplexAttributeBase _childComponent = null;
       protected int _componentType = TYPE_UICOMPONENT;

           
       protected static final int TYPE_UICOMPONENT = 1;
       protected static final int TYPE_BIPROPERTY = 2;


       public static class EnumSetProperty extends EnumProperty
       {
           public EnumSetProperty(Object component, PropertyDescriptor descriptor)
           {
               super(component, descriptor);
           }
           
           public Object getValue() {
               ArrayList list = new ArrayList();
               String[] processString = null;
               if (_descriptor.getPropertyType() == String.class ){
                   String initialString = (String)getProperty();
                   if ( initialString != null )
                      processString = initialString.split(" ");
               }
               else if (_descriptor.getPropertyType() == String[].class ){
                   processString = (String[])getProperty();
               }
                   
               if (processString != null){
                   for (String i:processString){
                       list.add(i);
                   }
               }

               return list;
           }
         
           public void flushToComponent()
           {
               // Converts an arraylist of string to a string split by spaces
               if (_valueSet){
                   
                   if (_descriptor.getPropertyType() == String[].class ){
                       ArrayList list = (ArrayList)_value;
                       
                       String[] finalString = new String[list.size()];
                       for (int i = 0; i < list.size(); i++){
                           finalString[i] = (String)list.get(i);
                       }
                       updateValue(finalString);
                   }
                   else if (_descriptor.getPropertyType() == String.class ){
                       ArrayList list = (ArrayList)_value;
                       String finalString = "";
                       if (list!=null){
                           for (int i = 0; i < list.size(); i++){
                               String item = (String)list.get(i);
                               if (i==0)
                                   finalString = item;
                               else
                                   finalString = finalString + " " + item;
                           }
                       }
                       updateValue(finalString);
                   }
               }
           }

           public String getType()
           {
             return "enumSet";
           }
       }
       public static class  EnumProperty extends PropertyOfComponent
       {
           public EnumProperty(Object component, PropertyDescriptor descriptor)
           {
             super(component, descriptor);
           }
           public String getType()
           {
             return "enum";
           }
           public void setActualValuesMap(Map listOfValuesMap) {
               m_listOfValuesMap = listOfValuesMap;
           }
           public Map getActualValuesMap() {
               return m_listOfValuesMap;
           }
           public void flushToComponent(){
             if (_valueSet){
                Object value = _value; 
                Map actualValuesMap = getActualValuesMap();
                if ( actualValuesMap != null ){
                   value = actualValuesMap.get(_value);
                }
                updateValue(value);
             }             
           }
           private Map m_listOfValuesMap = null;
       } 
     public static class  BIProperty extends PropertyOfComponent
     {
         protected ChildComponentEditorHandler _childEditor = null;
         protected BIComplexAttributeBase _biproperty;
         protected String _listName;
         public BIProperty(Object component, PropertyDescriptor descriptor)
         {
             super(component, descriptor);
         }
         public String getType()
         {
           return "biproperty";
         }
         public void setBIProperty(BIComplexAttributeBase biproperty){
             _biproperty = biproperty;
         }
         public void setListName(String listName){
             _listName = listName;
         }
         public void flushToComponent(){
             
             FacesContext facesContext = FacesContext.getCurrentInstance();
             if ( _childEditor == null ) {
                 _childEditor = (ChildComponentEditorHandler)facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "childEditor");
             }
             if ( _listName != null ){
                 _childEditor.addComponent(_biproperty, getPropertyKey(), _listName);
                 RequestContext rc = RequestContext.getCurrentInstance();                     
                 rc.addPartialTarget(_childEditor.getRichSelectOneChoice());
                 _listName = null;
             }
         }

     } 
     public static class MapComponent extends PropertyOfComponent {
     
         protected Class _mapComponentClass = null;
         protected Map _map = null;
         protected String _listname;
         protected ChildComponentEditorHandler _childEditor = null;
         
         public MapComponent(Object component, PropertyDescriptor descriptor)
         {
             super(component, descriptor);
         }
         public void flushToComponent(){
             FacesContext facesContext = FacesContext.getCurrentInstance();
             if ( _childEditor == null )
                 _childEditor = (ChildComponentEditorHandler)facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "childEditor");
             
             try{
                 PropertyKey key = _childComponent.getPropertyKey(getPropertyKey());
                 Map propertyMap =  (Map) _childComponent.getProperty(key);
                 if ( propertyMap == null ){
                     propertyMap = new HashMap();
                     _childComponent.setProperty(key, propertyMap);
                 }
                 
                 if ( _type.equals("map") ){
                     
                     Integer keyValue = new Integer(0);
                     Integer keyValueEnd = new Integer(1);                 
                     if (_value!=null){
                         keyValueEnd = (Integer)_value;
                     }
                     
                     for ( keyValue = 0; keyValue < keyValueEnd; keyValue++){  
                         Object realKeyValue = null;
                         if (_mapComponentClass == PointStyleItem.class ){
                             realKeyValue = keyValue.toString();
                         }
                         else {
                             realKeyValue = keyValue;
                         }
                         BIComplexAttributeBase biproperty = (BIComplexAttributeBase)propertyMap.get(realKeyValue);
                         if ( biproperty == null ){
                             biproperty = (BIComplexAttributeBase)_mapComponentClass.newInstance();
                             biproperty.setParent(_childComponent);
                             _childEditor.addComponent(biproperty, getPropertyKey(), _listname + "." + getPropertyKey() + ":" + keyValue );
                         }
                         propertyMap.put(realKeyValue, biproperty);
                     }
                 }
                 else if ( _type.equals("mapEnum") ){
                     if ( _value != null ){
                         BIComplexAttributeBase biproperty = (BIComplexAttributeBase)propertyMap.get(_value);
                         if ( biproperty == null ){
                             biproperty = (BIComplexAttributeBase)_mapComponentClass.newInstance();
                             biproperty.setParent(_childComponent);
                             _childEditor.addComponent(biproperty, getPropertyKey(), _listname + "." + getPropertyKey() + ":" + _value );
                         }
                         propertyMap.put(_value, biproperty);
                     }
                 }
                     
                 
                 
                 RequestContext rc = RequestContext.getCurrentInstance();                     
                 rc.addPartialTarget(_childEditor.getRichSelectOneChoice());
             }
             catch(InstantiationException e){;}
             catch(IllegalAccessException e){;}   
         }
         public void setMap(Map map){
             _map = map;
         }
         public void setMapComponentClass(Class mapComponentClass){
             _mapComponentClass = mapComponentClass;
         }
         public void setHigherLevelComponentName(String name){
             _listname = name;
         }
     }
 }
