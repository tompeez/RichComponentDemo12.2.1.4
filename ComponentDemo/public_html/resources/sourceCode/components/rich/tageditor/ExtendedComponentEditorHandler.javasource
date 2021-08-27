/** Copyright (c) 2008, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich.tageditor;

import java.awt.Color;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.BIComplexAttributeBase;
import oracle.adf.view.faces.bi.component.gantt.UIGantt;
import oracle.adf.view.faces.bi.component.graph.CoreGraph;
import oracle.adf.view.faces.bi.component.gauge.CoreGauge;
import oracle.adf.view.faces.bi.component.geoMap.BarChartThemeFOI;
import oracle.adf.view.faces.bi.component.geoMap.ColorThemeFOI;
import oracle.adf.view.faces.bi.component.geoMap.PieChartThemeFOI;
import oracle.adf.view.faces.bi.component.geoMap.PointFOI;
import oracle.adf.view.faces.bi.component.geoMap.ThemeFOI;
import oracle.adf.view.faces.bi.component.geoMap.UIGeoMap;
import oracle.adf.view.faces.bi.component.graph.GraphTitle;
import oracle.adf.view.faces.bi.component.graph.UISparkChart;
import oracle.adf.view.faces.bi.component.pivotFilterBar.UIPivotFilterBar;
import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.model.ListOfValuesModel;

import oracle.adfdemo.view.components.rich.DemoTemplateBindings;

import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.component.UIXTree;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;


public class ExtendedComponentEditorHandler
{

    
  public String update()
  {
    UIComponent editedComponent = _cachedComponent;
    boolean rendered = editedComponent.isRendered();
    List list = getCachedAttributeList();
    
    if (list != null)
    {
      Iterator iter = list.iterator();
      while (iter.hasNext())
      {
          Object object = iter.next();
          ((PropertyOfComponent)object).flushToComponent();
      }
    }
      RequestContext rc = RequestContext.getCurrentInstance();
      UIComponent parent = editedComponent.getParent();
      UIComponent partialTarget =
        (rendered != editedComponent.isRendered() ||
           _isPartialTargetParent(parent)) ?
             parent : editedComponent;

      rc.addPartialTarget(partialTarget);

      return null;
  }
  public boolean isChildTagListShow(){
      return ( _cachedComponent instanceof CoreGraph || 
               _cachedComponent instanceof CoreGauge ||
               _cachedComponent instanceof UIGeoMap ||
               _cachedComponent instanceof UISparkChart
          );
  }
  private RichPanelBox m_panelBox = null;
  public RichPanelBox getPanelBox(){
      if (m_panelBox == null)
          m_panelBox = new RichPanelBox();
      return m_panelBox;
  }
  public void setPanelBox(RichPanelBox panelBox){
      m_panelBox = panelBox;
  }

  public void reinitialize()
  {
      _cachedComponent = null;
      _alternateComponentsChecked = false;
      _list = null;
      if (m_panelBox != null){
          m_panelBox.setDisclosed(false);
          RequestContext rc = RequestContext.getCurrentInstance();
          rc.addPartialTarget(m_panelBox);
      }
  }
    public boolean isJavascriptShown()
    {
      return _javascriptShown;
    }

    public void setJavascriptShown(boolean javascriptShown)
    {
      _javascriptShown = javascriptShown;
    }

  public void setComponent(UIComponent component)
  {
    _cachedComponent = component;
  }

  public UIComponent getComponent()
  {      
      FacesContext facesContext = FacesContext.getCurrentInstance();
      String viewID = facesContext.getViewRoot().getViewId();
      Map requestMap = facesContext.getExternalContext().getRequestMap();
      String oldViewID = (String)requestMap.get(_CACHED_VIEW_ID);
      if ( oldViewID == null || !oldViewID.equals(viewID) )
          reinitialize();
      requestMap.put(_CACHED_VIEW_ID, viewID);
      
      if (_cachedComponent == null && !_alternateComponentsChecked)
      {
          
          
          
        // check the special use case if the document or the form should be used
        // when the component has not been set

        DemoTemplateBindings templateBindingsBean = (DemoTemplateBindings)
          facesContext.getApplication().getELResolver().getValue(
            facesContext.getELContext(), null, "templateBindings");

        if (templateBindingsBean != null)
        {
          if (templateBindingsBean.isEditingDocument())
          {
            _cachedComponent = templateBindingsBean.getDocumentComponent();
          }
          else if (templateBindingsBean.isEditingForm())
          {
            _cachedComponent = templateBindingsBean.getFormComponent();
          }
        }
        if ( _childEditor == null )
              _childEditor = (ChildComponentEditorHandler)facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "childEditor");
        _childEditor.clearListOfChildTag();
        _childEditor.initialize();
        _alternateComponentsChecked = true;
      }
      return _cachedComponent;
  }
  
  public PropertyOfComponent additionalAttributeInfo(Object comp, String compName, PropertyOfComponent poc, AttribInfo attributeInfo, PropertyDescriptor descriptor){
      if ( attributeInfo != null ){
          String preferredType = attributeInfo.getPreferredType();
          if (preferredType != null)
              poc.setType(preferredType);
          if ( attributeInfo.isHidden() != null && attributeInfo.isHidden() )
              return null;
          if ( attributeInfo.isDeprecated() != null && attributeInfo.isDeprecated() )
              return null;
          String[] attributeValues = attributeInfo.getAttributeValues();
          if ( attributeValues != null ){
              if ( attributeInfo.isMultipleSelection() != null && attributeInfo.isMultipleSelection() )
                  poc = new PropertyOfComponent.EnumSetProperty(comp, descriptor);
              else
                  poc = new PropertyOfComponent.EnumProperty(comp, descriptor);
              poc.setAttribValues(attributeValues);
              
              Map attributeValueMap = attributeInfo.getAttributeValueMap();
              if ( attributeValueMap != null ){
                 ((PropertyOfComponent.EnumProperty)poc).setActualValuesMap(attributeValueMap);
              }
          }
          String description = attributeInfo.getDescription();
          if ( description != null ){
              poc.setDescription(description);
          }
          else {
              poc.setDescription("To change "+poc.getName());
          }
      }
      return poc;
  }
  protected ChildComponentEditorHandler getChildEditor(){
      return _childEditor;
  }
  
    /**
     * Constructs the child of child tag
     * @param comp          Current BI Component
     * @param compName      Name of the Current BI Component
     * @param poc           Current Attribute
     * @param attributeInfo Additional Info for this attribute
     * @param descriptor    Description of this attribute
     */
    protected PropertyOfComponent getBIPoc(Object comp, String compName, PropertyOfComponent poc, AttribInfo attributeInfo, PropertyDescriptor descriptor){
      
      
      // Firstly, try to construct the attribute and see whether it is a BI property
      Class valueClass = descriptor.getPropertyType();
      try {
          Object instance = valueClass.newInstance();
          if ( instance instanceof BIComplexAttributeBase ) {
              
              BIComplexAttributeBase bipropertyNew = (BIComplexAttributeBase)instance;
              poc = new PropertyOfComponent.BIProperty(comp, descriptor);
              
              // If property key/ label name are different, overrides them
              if ( attributeInfo != null ){
                  String propertyKey = attributeInfo.getPropertyMapKeyName();
                  String newLabelName = attributeInfo.getLabelName();
                  if ( newLabelName != null )
                      poc.setLabelName(newLabelName);
                  if ( propertyKey != null )
                      poc.setPropertyKey(propertyKey);
              }
              
              // Invokes the "get" method to get the BI property
              Method m = descriptor.getReadMethod();
              BIComplexAttributeBase biproperty = (BIComplexAttributeBase)m.invoke(comp, new Object[0]);
              if ( biproperty == null )
                  biproperty = bipropertyNew;
              poc.setPropertyKey(Character.toUpperCase(poc.getPropertyKey().charAt(0))+poc.getPropertyKey().substring(1) );
              ((PropertyOfComponent.BIProperty)poc).setBIProperty(biproperty);
              FacesContext facesContext = FacesContext.getCurrentInstance();
              if ( _childEditor == null )
                    _childEditor = (ChildComponentEditorHandler)facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "childEditor");
              // Adds this component to the overall child tag list
              if (compName == null){
                  
                  _childEditor.addComponent(biproperty, poc.getPropertyKey(), poc.getPropertyKey() );
                  RequestContext rc = RequestContext.getCurrentInstance();                     
                  rc.addPartialTarget(_childEditor.getRichSelectOneChoice());
              }
              else
              {
                  
                  ((PropertyOfComponent.BIProperty)poc).setListName(compName + "." + poc.getPropertyKey());
                  /*
                  _childEditor.addComponent(biproperty, poc.getPropertyKey(), compName + "." + poc.getPropertyKey() );
                  RequestContext rc = RequestContext.getCurrentInstance();                     
                  rc.addPartialTarget(_childEditor.getRichSelectOneChoice());*/
              }
              
          }
      }
      catch(InstantiationException e){;}
      catch(IllegalAccessException e){;}
      catch(IllegalArgumentException e){;}
      catch(InvocationTargetException e){;}
      return poc;
    }
    protected PropertyOfComponent getAdditionalPoc(Object comp, String compName, PropertyOfComponent poc, AttribInfo attributeInfo, PropertyDescriptor descriptor){
        return poc;
    }
    public void overridesPropertyName(PropertyOfComponent poc, AttribInfo attributeInfo){
        if ( attributeInfo != null ){
            String labelName = attributeInfo.getLabelName();
            if ( labelName != null && labelName.length()>0 )
                poc.setLabelName(labelName);
            String propertyMapKey = attributeInfo.getPropertyMapKeyName();
            if ( propertyMapKey != null && propertyMapKey.length()>0 )
                poc.setPropertyKey(propertyMapKey);        
            String preferredType = attributeInfo.getPreferredType();
            if ( preferredType != null && preferredType.length()>0 )
                poc.setType(preferredType);      
        }  
    }
  public List getCachedAttributeList(){
      return _list;
  }
  public void setCachedAttributeList(List list){
      _list = list;
  }
  public AttribParser getAttribParser(){
      AttribParser attribParser = null;
      FacesContext facesContext = FacesContext.getCurrentInstance();
      if ( _editorCache == null )
          _editorCache = (EditorCache)facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "editorCache");
          if ( _editorCache != null )
              _editorCache.init();
      if ( _editorCache != null ){
          attribParser = _editorCache.getAttribParser();  
      }
      return attribParser;
  }
  public AttribInfo getAttributeInfo(AttribParser attribParser, Object comp, PropertyDescriptor descriptor){
      AttribInfo attributeInfo = null;       
      String className = comp.getClass().getName();
      String name = descriptor.getName();
      String renderType = ((UIComponent)comp).getRendererType();
      // TODO if more components want the combo box, put more components here
      //if ( comp instanceof CoreGraph || comp instanceof CoreGauge ){
      
        // Try to get the attribute info using render type
        if ( attribParser != null ){
          attributeInfo = attribParser.getAttribValues(renderType, name);
          // Try again with class type
          if ( attributeInfo == null ){
              attributeInfo = attribParser.getAttribValues(className, name);
          }
        }
      //}
      return attributeInfo;
  }
  public Object getAttributeListComponent(){
      return _cachedComponent;
  }
  public String getClassName(){
      return _cachedComponent.getClass().getName();
  }
  public String getChildName(){
      return null;
  }

  public List getAttributes()
  {
    if (getCachedAttributeList() != null)
      return getCachedAttributeList();

    Object comp = getAttributeListComponent();
    if (comp == null)
      return null;

    List list = new ArrayList();
    try
    {
        BeanInfo beanInfo = Introspector.getBeanInfo(comp.getClass());
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        AttribParser attribParser = getAttribParser();
        PropertyDescriptor themeListDescriptor = null;
            
      for (int i = 0; i < descriptors.length; i++)
      {
        PropertyDescriptor descriptor = descriptors[i];
          
          
         // Hard coded GeoMap theme code
        if ( comp instanceof UIGeoMap && descriptor.getName().equals("themeList") ){
            themeListDescriptor = descriptor;
        }
          
          
          
        // "Write-only" properties - no go
        boolean noWrite = descriptor.getWriteMethod() == null;
        boolean noRead = descriptor.getReadMethod() == null;
        if ( comp instanceof ThemeFOI ){
            if ( descriptor.getName().equals("sliceSet") ||
                 descriptor.getName().equals("pointStyleSet") ||
                 descriptor.getName().equals("barThemeInfoSet")) {
                
            }
            else if ( noWrite || noRead ){     
                continue;
            }
        }
        else {
            if ( noWrite || noRead ){     
                continue;
            }
        }
        

        PropertyOfComponent poc = null;

        
          

        AttribInfo attributeInfo = getAttributeInfo(attribParser, comp, descriptor);        
        
        String className = getClassName();
        String childName = getChildName();
          
        poc = new PropertyOfComponent(comp, descriptor);
        poc = additionalAttributeInfo(comp, childName, poc, attributeInfo, descriptor);
        if ( poc == null ) 
            continue;
        poc = getBIPoc(comp, childName, poc, attributeInfo, descriptor);
        poc = getAdditionalPoc(comp, childName, poc, attributeInfo, descriptor);
        
        if (poc.getType() != null){
          overridesPropertyName(poc, attributeInfo);
          list.add(poc);
        }
      }
        
      // Hard coded GeoMap theme code
        if ( comp instanceof UIGeoMap ){
            UIGeoMap map = (UIGeoMap)comp;
            
            PropertyDescriptor descriptor = themeListDescriptor;
            
            ArrayList themelist = map.getThemeList();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            if ( _childEditor == null )
                  _childEditor = (ChildComponentEditorHandler)facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "childEditor");
            
            int i = 0;
            if (themelist != null){
                for (Object foi: themelist){
                    if (foi instanceof ThemeFOI){
                        
                        ThemeFOI themefoi = (ThemeFOI)foi;
                        PropertyOfComponent poc = new PropertyOfComponent.BIProperty(comp, descriptor);
                        ((PropertyOfComponent.BIProperty)poc).setBIProperty(themefoi);
                        poc.setUIComponent((UIComponent)comp);
                        if (themefoi instanceof ColorThemeFOI)
                            _childEditor.addComponent(themefoi, "ColorThemeFOI", Integer.toString(i)+" ColorThemeFOI" );
                        else if (themefoi instanceof PointFOI)
                            _childEditor.addComponent(themefoi, "PointFOI", Integer.toString(i)+" PointFOI" );
                        else if (themefoi instanceof PieChartThemeFOI)
                            _childEditor.addComponent(themefoi, "PieChartThemeFOI", Integer.toString(i)+" PieChartThemeFOI" );
                        else if (themefoi instanceof BarChartThemeFOI)
                            _childEditor.addComponent(themefoi, "BarChartThemeFOI", Integer.toString(i)+" BarChartThemeFOI" );
                        i++;
                    }
                }
                RequestContext rc = RequestContext.getCurrentInstance();                     
                rc.addPartialTarget(_childEditor.getRichSelectOneChoice());
            }
        }

      // Sort the list by property name
      Collections.sort(list);
        
      setCachedAttributeList(list);
      return list;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return null;
  }
    // Tests whether the specified component is a partial target parent - ie.
    // whether the specified parent component should be used as the partial
    // target in place of the edited component.
    private boolean _isPartialTargetParent(UIComponent component)
    {
      // We use the bonus "partialTargetParent" attribute to mark partial
      // target parents.
      return "true".equals(component.getAttributes().get("partialTargetParent"));
    }
    public static class ChildTagHandle{
        public ChildTagHandle(BIComplexAttributeBase biproperty, String propertymapname, String name){
            m_biproperty = biproperty;
            m_name = name;
            m_propertyMapName = propertymapname;
        }
        public String getName(){
            return m_name;
        }
        public String getPropertyMapName(){
            return m_propertyMapName;
        }
        public BIComplexAttributeBase getComponent(){
            return m_biproperty;
        }
        protected BIComplexAttributeBase m_biproperty;
        protected String m_name;
        protected String m_propertyMapName;
    }
  protected UIComponent _cachedComponent;
  protected boolean     _alternateComponentsChecked = false;
  protected List        _list;
  protected EditorCache _editorCache = null;
  protected ChildTagHandle _currentChildTagHandle;
  protected ChildComponentEditorHandler _childEditor = null;
  protected AttribParser _attribParser = null;
  protected boolean _javascriptShown = true;
  protected static final String _CACHED_VIEW_ID = 
      "oracle.adfdemo.view.components.rich.tageditor.ExtendedComponentEditorHandler.CACHED_VIEW_ID";
}
