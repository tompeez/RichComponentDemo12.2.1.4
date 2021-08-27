 /** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
  package oracle.adfdemo.view.components.rich;

 import java.beans.BeanInfo;
 import java.beans.IntrospectionException;
 import java.beans.Introspector;
 import java.beans.PropertyDescriptor;

 import java.util.Collection;
  import java.util.HashMap;
  import java.util.Iterator;
  import java.util.List;

  import javax.faces.component.UIComponent;
  import javax.faces.event.ValueChangeEvent;

  import org.apache.myfaces.trinidad.context.RequestContext;

  public class StyleChanger
  {
    public StyleChanger()
    {
      _components = new HashMap<String, UIComponent>();
      _parents    = new HashMap<String, UIComponent>();
    }

    public void setComponents(HashMap<String, UIComponent> _components)
    {
      this._components = _components;
    }

    public HashMap<String, UIComponent> getComponents()
    {
      return _components;
    }

    public void setParents (HashMap<String, UIComponent> _parents)
    {
      this._parents = _parents;
    }

    public HashMap<String, UIComponent> getParents()
    {
      return _parents;
    }
    
    /*
     * Does this component support the inlineStyle attribute.
     */
    public boolean isSupportsInlineStyle()
    {
      Collection<UIComponent> comps = _components.values();
      Iterator<UIComponent> it = comps.iterator();
      if (!it.hasNext())
      {
        return false;
      }
      UIComponent comp = it.next();
      Class clazz = comp.getClass();
      BeanInfo beanInfo;
     try
     {
       beanInfo = Introspector.getBeanInfo(clazz);
       PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
       for (PropertyDescriptor descriptor : descriptors)
       {
         if (descriptor.getName().equals("inlineStyle"))
           return true;
       }
     }
     catch (IntrospectionException e)
     {
       return false;
     }
      return false;
    }
    
    /*
     * Does this component support the contentStyle attribute.
     */
    public boolean isSupportsContentStyle()
    {
      Collection<UIComponent> comps = _components.values();
      Iterator<UIComponent> it = comps.iterator();
      if (!it.hasNext())
      {
        return false;
      }
      UIComponent comp = it.next();
      Class clazz = comp.getClass();
      BeanInfo beanInfo;
      try
      {
       beanInfo = Introspector.getBeanInfo(clazz);
       PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
       for (PropertyDescriptor descriptor : descriptors)
       {
         if (descriptor.getName().equals("contentStyle"))
           return true;
       }
      }
      catch (IntrospectionException e)
      {
       return false;
      }
      return false;
    }

    private void _handleStyleChanged (ValueChangeEvent event, String styleType)
    {
      List<String> value = (List<String>) event.getNewValue();
      String newStyle;

      if (value == null)
      {
        newStyle = "";
      }
      else
      {
        StringBuilder sb = new StringBuilder();
        if (value != null)
        {
          for (String styleClass: value)
          {
            sb.append(styleClass);
            sb.append(" ");
          }
        }
        newStyle = sb.toString();
      }

      // Redraw the component:
      RequestContext adfContext = RequestContext.getCurrentInstance();

      if (_components != null)
      {
        Collection<UIComponent> comps = _components.values();
        Iterator<UIComponent> i = comps.iterator();
        while (i.hasNext())
        {
          UIComponent c = i.next();
          c.getAttributes().put(styleType, newStyle);
          adfContext.addPartialTarget(c);
        }
      }

      // In some cases e.g. column, the parent component (e.g. table)
      // needs to be updated. The demo may have multiple tables
      // on the page, so use a map to store the references.
      if (_parents != null)
      {
        Collection<UIComponent> pars = _parents.values();
        Iterator<UIComponent> i = pars.iterator();
        while (i.hasNext())
        {
          UIComponent c = i.next();
          adfContext.addPartialTarget(c);
        }
      }
    }

    public void valueInlineStyleChanged(ValueChangeEvent event)
    {
      _handleStyleChanged (event, "inlineStyle");
    }

    public void valueContentStyleChanged(ValueChangeEvent event)
    {
      _handleStyleChanged (event, "contentStyle");
    }

    private HashMap<String, UIComponent> _components;
    private HashMap<String, UIComponent> _parents;
  }