/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.navigator;

import java.io.Serializable;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;

import oracle.adfdemo.view.explorer.rich.bean.FileExplorerBean;

import org.apache.myfaces.trinidad.component.UIXShowDetail;
import org.apache.myfaces.trinidad.event.DisclosureEvent;

public abstract class BaseNavigatorView implements Serializable
{
  public BaseNavigatorView()
  {
  }
  
  public BaseNavigatorView(String type, String icon, String label, Map data)
  {
    initNavigator(type, icon, label, data);
  }
  
  public void initNavigator(String type,
                            String icon,
                            String label, 
                            Map data)
  {
    setData(data);
    setIcon (icon);
    setLabel(label);
    setType(type);
  }
  
  /**
   * Refresh BaseNavigatorView
   */
  public abstract void refresh();
  
  public void setFileExplorerBean(FileExplorerBean feBean)
  {
    this._feBean = feBean;
  }

  public FileExplorerBean getFileExplorerBean()
  {
    return _feBean;
  }

  public void setData(Map data)
  {
    this._data = data;
  }

  public Map getData()
  {
    return _data;
  }
  
  public void setSelected(boolean selected)
  {
    this._selected = selected;
  }

  public boolean isSelected()
  {
    return _selected;
  }

  public void setDisclosed(boolean disclosed)
  {
    this._disclosed = disclosed;
  }

  public boolean isDisclosed()
  {
    return _disclosed;
  }
  
  public String getLabel()
  {
    if (_label == null)
    {
      _label =  _feBean.getFileExplorerBundle().getString(getType());
    }
    
    return _label;
  }
  
  public void setLabel(String label)
  {
    _label = label;
  }

  public void setIcon(String icon)
  {
    _icon = icon;
  }
  
  public String getIcon()
  {
    if (_icon == null)
    {
      return "/fileExplorer/images/arrange.png";
    }
    else
    {
      return _icon;
    }
  }

  public void setType(String type)
  {
    this._type = type;
  }

  public String getType()
  {
    return _type;
  }

  public void discloseNavigator(DisclosureEvent disclosureEvent)
  {
    UIXShowDetail source = (UIXShowDetail)disclosureEvent.getSource();
    
    for (BaseNavigatorView nav : _feBean.getNavigatorManager().getNavigators())
    {
      if (nav.getType().equals(getType()))
      {
        nav.getCommandMenuItem().setSelected(source.isDisclosed());
        break;
      }
    }
    
    _feBean.getHeaderManager().refresh();
  } 
  
  public void showNavigator(ActionEvent actionEvent)
  {
    RichCommandMenuItem source = (RichCommandMenuItem)actionEvent.getSource();
    
    for (BaseNavigatorView nav :  _feBean.getNavigatorManager().getNavigators())
    { 
      if (nav.getType().equals(getType())) 
      {
        nav.getShowDetailItem().setDisclosed(source.isSelected());
        break;
      }
    }
    
    _feBean.getNavigatorManager().refresh();
  }

  public void setFlex(int flex)
  {
    this._flex = flex;
  }

  public int getFlex()
  {
    return _flex;
  }
  
  public void setShowDetailItem(UIXShowDetail showDetailItem)
  {
    this._showDetailItem = showDetailItem;
  }

  public UIXShowDetail getShowDetailItem()
  {
    return _showDetailItem;
  }

  public void setCommandMenuItem(RichCommandMenuItem commandMenuItem)
  {
    this._commandMenuItem = commandMenuItem;
  }

  public RichCommandMenuItem getCommandMenuItem()
  {
    return _commandMenuItem;
  }
  
  protected FileExplorerBean _feBean = null;
  
  private boolean _selected = false;
  private boolean _disclosed = false;
  private UIXShowDetail _showDetailItem = null;
  private RichCommandMenuItem _commandMenuItem = null;
  private int _flex = 1;
  
  private String _type = null;
  private String  _icon = null;
  private String  _label = null;
  private Map     _data = null;
}
