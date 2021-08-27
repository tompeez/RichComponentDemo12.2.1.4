/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.beans.IntrospectionException;

import java.util.ArrayList;

import org.apache.myfaces.trinidad.model.MenuModel;
import org.apache.myfaces.trinidad.model.ProcessMenuModel;

/**
 * This class facilitates the construction of a ProcessMenuModel instance
 * via managed-beans. ProcessMenuModel does not have a no-arg constructor.
 * This class does, and so can be instantiated as a managed-bean.
 * Two properties need to be set: "viewIdProperty" and "instance"
 */
public class ProcessMenuModelAdapter implements java.io.Serializable
{
  public ProcessMenuModelAdapter()
  {
  }

  public MenuModel getModel() throws IntrospectionException
  {
    if (_model == null)
    {
      _model = new ProcessMenuModel(getInstance(),
                                    getViewIdProperty(),
                                    getMaxPathKey());
    }
    return _model;
  }

  public String getViewIdProperty()
  {
    return _propertyName;
  }

  /**
   * Sets the property to use to get at view id
   * @param propertyName
   */
  public void setViewIdProperty(String propertyName)
  {
    _propertyName = propertyName;
    _model = null;
  }

  public ArrayList getWrappedData()
  {
    return _wrappedData;
  }

  public void setWrappedData(ArrayList wrappedData)
  {
    _wrappedData = wrappedData;
  }

  public Object getInstance()
  {
    return _instance;
  }

  /**
   * Sets the treeModel
   * @param instance must be something that can be converted into a TreeModel
   */
  public void setInstance(Object instance)
  {
    _instance = instance;
    _model = null;
  }

  public String getMaxPathKey()
  {
    return _maxPathKey;
  }

  public void setMaxPathKey(String maxPathKey)
  {
    _maxPathKey = maxPathKey;
  }

  private String _propertyName = null;
  private Object _instance = null;
  private String _maxPathKey = null;
  private ArrayList _wrappedData = null;
  private transient MenuModel _model = null;
}
