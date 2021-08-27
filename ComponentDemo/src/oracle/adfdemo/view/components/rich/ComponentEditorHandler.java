/** Copyright (c) 2008, 2016, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.awt.Color;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.component.FlattenedComponent;
import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.component.UIXTree;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;


public class ComponentEditorHandler
{
  public String update()
  {
    UIComponent editedComponent = getComponent();
    boolean rendered = editedComponent.isRendered();
    List list = _list;
    if (list != null)
    {
      Iterator iter = list.iterator();
      while (iter.hasNext())
      {
        ((PropertyOfComponent) (iter.next())).flushToComponent();
      }
    }

    RequestContext rc = RequestContext.getCurrentInstance();

    // If we toggled rendered, we'd better toggle the parent.  Also,
    // to work around cases where components might not render their own
    // id, we check for the special "targetMe" parent.  When present
    // we target the parent instead of the component itself.
    UIComponent parent = editedComponent.getParent();
    UIComponent partialTarget =
      (rendered != editedComponent.isRendered() ||
         _isPartialTargetParent(parent)) ?
           parent : editedComponent;

    rc.addPartialTarget(partialTarget);

    return null;
  }

  public void reinitialize()
  {
    _cachedComponent = null;
    _javascriptShown = true;
    _alternateComponentsChecked = false;
    _list = null;
  }

  /**
   * If your tag demo page has a picker component that needs to be a partial trigger for the center
   * or demoTab facets, you'll need to pass in this list as the centerPartialTriggers or
   * demoTabPartialTriggers attributes of your tag demo template so that the parent of those facets
   * can be updated whenver your picker queues its event to the server.
   * For an example, see the af:switcher tag demo.
   * @return a String array with one item containing the absolute expression of ":dmoTpl:picker"
   */
  public String[] getPickerPartialTriggersList()
  {
    return _PICKER_PARTIAL_TRIGGERS;
  }
  static private final String[] _PICKER_PARTIAL_TRIGGERS = { ":dmoTpl:picker" };

  public void setComponent(UIComponent component)
  {
    _cachedComponent = component;
  }

  public UIComponent getComponent()
  {
    if (_cachedComponent == null && !_alternateComponentsChecked)
    {
      // check the special use case if the document or the form should be used
      // when the component has not been set
      FacesContext facesContext = FacesContext.getCurrentInstance();

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
      _alternateComponentsChecked = true;
    }
    return _cachedComponent;
  }

  public boolean isJavascriptShown()
  {
    return _javascriptShown;
  }

  public void setJavascriptShown(boolean javascriptShown)
  {
    _javascriptShown = javascriptShown;
  }

  @SuppressWarnings({ "oracle.jdeveloper.java.null-collection-return",
                      "oracle.jdeveloper.java.insufficient-catch-block", "unchecked"
    })
  public List getAttributes()
  {
    if (_list != null)
      return _list;

    UIComponent comp = getComponent();
    if (comp == null)
      return null;

    List<PropertyOfComponent> list = new ArrayList<PropertyOfComponent>();
    try
    {
      Class clazz = comp.getClass();
      @SuppressWarnings("unchecked")
      BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
      PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
      for (PropertyDescriptor descriptor: descriptors)
      {
        // "Write-only" properties - no go
        if (descriptor.getReadMethod() == null)
          continue;

        PropertyOfComponent poc = null;

        boolean readOnly = descriptor.getWriteMethod() == null;
        if (readOnly)
          continue;

        boolean isPrivateAttribute = _isPrivateAttribute(comp, descriptor);
        if (isPrivateAttribute)
          continue;

        String name = descriptor.getName();

        /* Commenting this out since it appears to work just fine and allowing editing of this will
         * let the document component demo have an edit field for maximized and for title.
         * If you need to suppress something else in particular, perhaps a special check for that
         * should be used instead?
        // For now, skip any attributes with ValueBindings
        if (skipAttributeForValueBinding(comp, name))
          continue;
        */

        Class type = descriptor.getPropertyType();

        if (type == String.class)
        {
          if (!isJavascriptShown() && name.startsWith("on"))
            continue;

          poc = new StringProperty(comp, descriptor);
        }
        else if ((type == Integer.class) || (type == Integer.TYPE))
        {
          poc = new IntegerProperty(comp, descriptor);
        }
        else if (type == Number.class)
        {
          poc = new NumberProperty(comp, descriptor);
        }
        else if ((type == Boolean.class) || (type == Boolean.TYPE))
        {
          poc = new BooleanProperty(comp, descriptor);
        }
        else if (type == Date.class)
        {
          poc = new DateProperty(comp, descriptor);
        }
        else if (type == Color.class)
        {
          poc = new ColorProperty(comp, descriptor);
        }
        else if (type == List.class)
        {
          poc = new ListProperty(comp, descriptor);
        }
        else if (type == Set.class)
        {
          poc = new SetProperty(comp, descriptor);
        }
        else if ((type == Character.class) || (type == Character.TYPE))
        {
          poc = new CharacterProperty(comp, descriptor);
        }
        else if (type == Object.class)
        {
          if (!isJavascriptShown() && name.startsWith("on"))
            continue;

          poc = new StringProperty(comp, descriptor);
        }
        else if (type.isEnum())
        {
          poc = new EnumProperty(comp, descriptor);
        }

        if (poc != null)
        {
          /* See if this is deprecated - for the component attribute editor */
          StringBuilder attributeGetter = new StringBuilder();
          if (poc instanceof BooleanProperty)
            attributeGetter.append("is");
          else
            attributeGetter.append("get");
          attributeGetter.append(name.substring(0, 1).toUpperCase());
          attributeGetter.append(name.substring(1));
          boolean deprecated = false;
          try
          {
            @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
            Method method = clazz.getDeclaredMethod(attributeGetter.toString());
            if (method.getAnnotation(Deprecated.class) != null)
              deprecated = true;
          }
          // getDeclaredMethod can throw 'method not found', so can't be deprecated then.
          catch (NoSuchMethodException e)
          {
          }

          poc.setDeprecated(deprecated);
          list.add(poc);
        }
      }

      // Sort the list by property name
      Collections.sort(list);

      _list = list;

      return list;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return null;
  }

  static public class PropertyOfComponent implements Comparable
  {
    public PropertyOfComponent(UIComponent component,
                               PropertyDescriptor descriptor)
    {
      _component = component;
      _descriptor = descriptor;
    }

    // Sort by the name of the property
    public int compareTo(Object o)
    {
      PropertyOfComponent poc = (PropertyOfComponent) o;
      return getName().compareTo(poc.getName());
    }

    public String getName()
    {
      return _descriptor.getName();
    }

    public String getType()
    {
      return null;
    }

    public void flushToComponent()
    {
      if (_valueSet)
        _component.getAttributes().put(getName(), _value);
    }

    protected Object getBeanProperty()
    {
      Method method = _descriptor.getReadMethod();
      try
      {
        return method.invoke(_component);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }

      return getProperty();
    }

    protected Object getProperty()
    {
      return _component.getAttributes().get(getName());
    }

    protected void setProperty(Object value)
    {
      if ("".equals(value))
        value = null;

      _valueSet = true;
      _value = value;
    }

    public boolean getDeprecated()
    {
      return _deprecated;
    }

    public void setDeprecated(boolean deprecated)
    {
      _deprecated = deprecated;
    }

    private boolean _valueSet = false;
    private Object  _value    = null;
    private final PropertyDescriptor _descriptor;
    private final UIComponent        _component;
    private boolean _deprecated;
  }

  static public class IntegerProperty extends PropertyOfComponent
  {
    public IntegerProperty(UIComponent component, PropertyDescriptor descriptor)
    {
      super(component, descriptor);
    }

    public Integer getValue()
    {
      return (Integer) getProperty();
    }

    public void setValue(Integer i)
    {
      setProperty(i);
    }

    public String getType()
    {
      return "integer";
    }
  }

  static public class NumberProperty extends PropertyOfComponent
  {
    public NumberProperty(UIComponent component, PropertyDescriptor descriptor)
    {
      super(component, descriptor);
    }

    public Number getValue()
    {
      return (Number) getProperty();
    }

    public void setValue(Number n)
    {
      setProperty(n);
    }

    public String getType()
    {
      return "number";
    }
  }

  static public class CharacterProperty extends PropertyOfComponent
  {
    public CharacterProperty(UIComponent component, PropertyDescriptor descriptor)
    {
      super(component, descriptor);
    }

    public Character getValue()
    {
      return (Character) getProperty();
    }

    public void setValue(Character i)
    {
      setProperty(i);
    }

    public String getType()
    {
      return "character";
    }
  }

  static public class DateProperty extends PropertyOfComponent
  {
    public DateProperty(UIComponent component, PropertyDescriptor descriptor)
    {
      super(component, descriptor);
    }

    public Date getValue()
    {
      return (Date) getProperty();
    }

    public void setValue(Date i)
    {
      setProperty(i);
    }

    public String getType()
    {
      return "date";
    }
  }

  static public class ColorProperty extends PropertyOfComponent
  {
    public ColorProperty(UIComponent component, PropertyDescriptor descriptor)
    {
      super(component, descriptor);
    }

    public Color getValue()
    {
      return (Color) getProperty();
    }

    public void setValue(Color i)
    {
      setProperty(i);
    }

    public String getType()
    {
      return "color";
    }
  }

  static public class ListProperty extends PropertyOfComponent
  {
    public ListProperty(UIComponent component, PropertyDescriptor descriptor)
    {
      super(component, descriptor);
    }

    public List getValue()
    {
      return (List) getProperty();
    }

    public void setValue(List i)
    {
      setProperty(i);
    }

    public String getType()
    {
      return "list";
    }
  }

  static public class SetProperty extends PropertyOfComponent
  {
    public SetProperty(UIComponent component, PropertyDescriptor descriptor)
    {
      super(component, descriptor);
    }

    public Set getValue()
    {
      return (Set) getProperty();
    }

    public void setValue(Set i)
    {
      setProperty(i);
    }

    public String getType()
    {
      return "set";
    }
  }

  static public class StringProperty extends PropertyOfComponent
  {
    public StringProperty(UIComponent component, PropertyDescriptor descriptor)
    {
      super(component, descriptor);
    }

    public String getValue()
    {
      Object o = getProperty();
      if (o == null)
        return null;
      return o.toString();
    }

    public void setValue(String s)
    {
      setProperty(s);
    }

    public String getType()
    {
      return "string";
    }
  }

  static public class BooleanProperty extends PropertyOfComponent
  {
    public BooleanProperty(UIComponent component, PropertyDescriptor descriptor)
    {
      super(component, descriptor);
    }

    public Boolean getValue()
    {
      return (Boolean) getBeanProperty();
    }

    public void setValue(Boolean b)
    {
      setProperty(b);
    }

    public String getType()
    {
      return "boolean";
    }
  }
  static public class EnumProperty extends PropertyOfComponent
  {
    public EnumProperty(UIComponent component, PropertyDescriptor descriptor)
    {
      super(component, descriptor);
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public String getValue()
    {
      Enum value = (Enum)getProperty();
      try
      {
        @SuppressWarnings("unchecked")
        Method method = value.getDeclaringClass().getDeclaredMethod("displayName");
        String str = (String)method.invoke(value);
        return str;
      }
      // getDeclaredMethod can throw 'method not found', and if it does we return null.
      catch (NoSuchMethodException e)
      {
      }
      // Invoke can throw and IllegalAccess exception. If it does we return null.
      catch (IllegalAccessException e)
      {
      }
      // Invoke can thorw and InvocationTarget exception. If it does we return null;
      catch (InvocationTargetException e)
      {
      }
      return null;
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void setValue(String b)
    {
      try
      {
        Enum value = (Enum)getProperty();
        Class[] cArg = new Class[1];
        cArg[0] = String.class;
        @SuppressWarnings("unchecked")
        Method method = value.getDeclaringClass().getDeclaredMethod("valueOfDisplayName", cArg);
        Enum vl = (Enum)method.invoke(value, b);
        setProperty(vl);
      }
      // getDeclaredMethod can throw 'method not found', so can't if it does we are not setting any value.
      catch (NoSuchMethodException e)
      {
      }
      // Invoke can throw and IllegalAccess exception. If it does no value will be set.
      catch (IllegalAccessException e)
      {
      }
      // Invoke can thorw and InvocationTarget exception. If it does no value will be set.
      catch (InvocationTargetException e)
      {
      }
    }

    public String getType()
    {
      return "enum";
    }

  }

  /**
   *
   * @param comp the component that provides attribute for testing
   * @param name attribute name
   * @return true if the attribute should be skipped due to value binding, false
   * otherwise.
   */
  protected boolean skipAttributeForValueBinding(UIComponent comp, String name)
  {
    return comp.getValueExpression(name) != null;
  }

  /**
   * Check if the given property is private and should be skipped.
   * @param component the component that provides attribute for testing
   * @param descriptor component porperty descriptor
   * @return true if the attribute is private and should be skipped
   */
  private boolean _isPrivateAttribute(UIComponent component, PropertyDescriptor descriptor)
  {
    // We do not want to display CollectionModel or Collection attributes because cases like
    // the table demo will insert the entire data records in the input field, making the page
    // huge and slow.
    Class type = descriptor.getPropertyType();
    if (type == Object.class)
    {
      Object value = component.getAttributes().get(descriptor.getName());
      if ((value instanceof CollectionModel) || (value instanceof Collection))
        return true;
    }

    // Check to see if this is an attribute that we should be skipping for every single component:
    String name = descriptor.getName();
    if (_attributesToSkip.contains(name))
    {
      return true;
    }

    if (component instanceof UIXTable || component instanceof UIXTree)
    {
      if (_tableTreeAttributesToSkip.contains(name))
        return true;
    }

    return false;
  }

  // Tests whether the specified component is a partial target parent - ie.
  // whether the specified parent component should be used as the partial
  // target in place of the edited component.
  private boolean _isPartialTargetParent(UIComponent component)
  {
    // We use the bonus "partialTargetParent" attribute to mark partial
    // target parents.
    UIComponent partialTargetComponent = null;

    if (component instanceof FlattenedComponent)
    {
      //This means that the component is wrapped so we need to get the
      //parent to check for the partialTarget flag.
      partialTargetComponent = component.getParent();
    }
    else
    {
      partialTargetComponent = component;
    }
    return "true".equals(partialTargetComponent.getAttributes().get("partialTargetParent"));
  }

  // Attributes that we don't want to show up in the attributes list for the editor:
  private static final List<String> _attributesToSkip = new ArrayList<String>();
  // table/tree/treeTable attributes to skip
  private static final List<String> _tableTreeAttributesToSkip = new ArrayList<String>();

  static
  {
    _attributesToSkip.add("inView");
    _attributesToSkip.add("rendererType");
    _attributesToSkip.add("transient");
    _attributesToSkip.add("componentVar");

    _tableTreeAttributesToSkip.add("scrollTop");
    _tableTreeAttributesToSkip.add("scrollLeft");
    _tableTreeAttributesToSkip.add("scrollTopRowKey");
    _tableTreeAttributesToSkip.add("activeRowKey");
  }

  private UIComponent _cachedComponent;
  private boolean     _javascriptShown = true;
  private boolean     _alternateComponentsChecked = false;
  private List        _list;

}
