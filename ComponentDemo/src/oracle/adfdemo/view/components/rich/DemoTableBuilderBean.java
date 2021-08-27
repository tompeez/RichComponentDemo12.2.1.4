/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DemoTableBuilderBean
{
  public DemoTableBuilderBean()
  {
    // no arg contructor needed for usage as managed-bean
  }
  
  public void setBeanClass(String klass)
  {
    _beanClass = klass;
  }
  
  public void setBeanProperties(List props)
  {
    if (props == null)
      throw new NullPointerException("beanProperties");
    _beanProps = props;
  }
  
  public void setBeanData(List data)
  {
    if (data == null)
      throw new NullPointerException("beanData");
    _data = data;
  }
  
  public List getTableData()
    throws ClassNotFoundException, IntrospectionException, InstantiationException,
      IllegalAccessException, InvocationTargetException
  {
    if (_result == null)
    {
      _result = _getAsList();
    }
    return _result;
  }
  
  private List _getAsList() 
    throws ClassNotFoundException, IntrospectionException, InstantiationException,
      IllegalAccessException, InvocationTargetException
  {
    if (_beanClass == null)
      throw new NullPointerException("beanClass");
    
    Class beanClass = Class.forName(_beanClass);
    _setPropertySetters(beanClass, _beanProps);
    int sz = _beanProps.size();
    List result = new ArrayList(sz);

    Iterator cells = _data.iterator();
    while(cells.hasNext())
    {
      Object beanInstance = beanClass.newInstance();
      for(int i=0; i<sz; i++)
      {
        Object value = cells.next();
        Method setter = (Method) _beanProps.get(i);
        Class expectedType = setter.getParameterTypes()[0];
        if (!expectedType.isAssignableFrom(value.getClass()))
        {
          value = _convert(value, expectedType);
        }
        
        setter.invoke(beanInstance, new Object[] {value});
      }
      result.add(beanInstance);
    }
    return result;
  }
  
  private Object _convert(Object instance, Class expectedType)
  {
    if (Integer.TYPE == expectedType)
    {
      return new Integer(instance.toString());
    }
    throw new IllegalArgumentException("Could not convert instance:"+instance+
      " of class:"+instance.getClass()+" into "+expectedType);
  }
  
  private void _setPropertySetters(Class klass, List props)
    throws IntrospectionException
  {
    BeanInfo beanInfo = Introspector.getBeanInfo(klass);
    PropertyDescriptor[] descs = beanInfo.getPropertyDescriptors();
    for(int i=0, sz=props.size(); i<sz; i++)
    {
      String name = (String) props.get(i);
      PropertyDescriptor desc = _getDescriptor(descs, name);
      if (desc == null)
      {
        throw new IllegalArgumentException("property:"+name+" not found on:"
          +klass);
      }
      Method setter = desc.getWriteMethod();
      if (setter == null)
      {
        throw new IllegalArgumentException("No way to set property:"+name+" on:"
          +klass);
      }
      props.set(i, setter);
    }
  }
  
  private PropertyDescriptor _getDescriptor(
    PropertyDescriptor[] descs,
    String name)
  {
    for(int i=0; i<descs.length; i++)
    {
      PropertyDescriptor desc = descs[i];
      if (name.equals(desc.getName()))
        return desc;
    }
    return null;      
  }
  
  private String _beanClass = null;
  private List _beanProps = Collections.EMPTY_LIST, _data = Collections.EMPTY_LIST;
  private List _result = null;
}