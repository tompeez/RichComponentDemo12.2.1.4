/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import java.beans.IntrospectionException;
import java.util.List;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.TreeModel;

/**
 * This class facilitates the construction of a ChildPropertyTreeModel instance
 * via managed-beans. ChildPropertyTreeModel does not have a no-arg constructor.
 * This class does, and so can be instantiated as a managed-bean.
 * Two properties need to be set: "childProperty" and "instance"
 */
public class TreeModelAdapter implements java.io.Serializable
{
  public TreeModelAdapter()
  {
  }

  private String _propertyName = null;
  private Object _instance = null;
  private transient TreeModel _model = null;

  public TreeModel getModel() throws IntrospectionException
  {
    if (_model == null)
    {
      _model = new ChildPropertyTreeModel(getInstance(), getChildProperty());
    }
    return _model;
  }

  public String getChildProperty()
  {
    return _propertyName;
  }

  /**
   * Sets the property to use to get at child lists
   * @param propertyName
   */
  public void setChildProperty(String propertyName)
  {
    _propertyName = propertyName;
    _model = null;
  }

  public Object getInstance()
  {
    return _instance;
  }

  /**
   * Sets the root list for this tree.
   * @param instance must be something that can be converted into a List
   */
  public void setInstance(Object instance)
  {
    _instance = instance;
    _model = null;
  }

  /**
   * Sets the root list for this tree.
   * This is needed for passing a List when using the managed bean list
   * creation facility, which requires the parameter type is List.
   * @param instance the list of root nodes
   */
  public void setListInstance(List instance)
  {
    setInstance(instance);
  }
}
