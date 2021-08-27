/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.util.List;

import org.apache.myfaces.trinidad.model.ViewIdPropertyMenuModel;


/**
 * This class facilitates calling ViewIdPropertyMenuModel.addViewId via
 * an "aliasList".
 */
public class MenuModelAdapter implements java.io.Serializable
{
  public MenuModelAdapter()
  {
  }

  private ViewIdPropertyMenuModel _model = null;
  private List _aliasList = null;
  private boolean _aliasListAdded = false;

  /**
   *
   * @param model an instance of ViewIdPropertyMenuModel
   */
  public void setModel(ViewIdPropertyMenuModel model)
  {
    _model = model;
    _aliasListAdded = false;

  }

  public ViewIdPropertyMenuModel getModel()
  {
    if (_model != null && !_aliasListAdded)
    {
      _aliasListAdded = true;
      if(_aliasList != null && !_aliasList.isEmpty())
      {
        int size = _aliasList.size();
        if (size % 2 == 1)
          size = size - 1;

        for ( int i = 0; i < size; i=i+2)
        {
          _model.addViewId(_aliasList.get(i).toString(),
                         _aliasList.get(i+1).toString());
        }
      }
    }
    return _model;
  }

  public List getAliasList()
  {
    return _aliasList;
  }

  /**
   * aliasList is just a list of viewId strings grouped into pairs.
   * We iterate over the list like so:
   * ViewIdPropertyMenuModel.addViewId(aliasList.get(i), aliasList.get(i+1))
   */
  public void setAliasList(List aliasList)
  {
    _aliasList = aliasList;
  }
}
