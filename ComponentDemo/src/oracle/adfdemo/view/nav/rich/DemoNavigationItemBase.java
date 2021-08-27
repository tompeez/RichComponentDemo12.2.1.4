/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import java.util.List;

public class DemoNavigationItemBase
  implements Serializable
{
  public DemoNavigationItemBase()
  {
  }

  public DemoNavigationItemBase(String label, String icon)
  {
    setIco(icon);
    setLabel(label);
  }

  public DemoNavigationItemBase(String label, String icon, List<DemoItemNode> children)
  {
    setIco(icon);
    setLabel(label);
    setChildren(children);
  }

  public List<DemoItemNode> getChildren()
  {
    return _children;
  }

  public String getDestination()
  {
    return _destination;
  }
  // calling this 'ico' instead of 'icon' due to tree bug

  public String getIco()
  {
    return _icon;
  }

  public String getIconLarge()
  {
    if (_iconLarge == null)
      return "/images/icons-large/element.png"; // fall back on the generic element icon if no large icon was specified
    return _iconLarge;
  }

  public void setIconLarge(String iconLarge)
  {
    _iconLarge = iconLarge;
  }

  public String getIconCarousel()
  {
    if (_iconCarousel == null)
      return getIconLarge(); // fall back on the large icon if no carousel icon was specified
    return _iconCarousel;
  }

  public void setIconCarousel(String iconCarousel)
  {
    _iconCarousel = iconCarousel;
  }

  public String getLabel()
  {
    return _label;
  }

  public boolean getShowRequired()
  {
    return _showRequired;
  }

  public String getMessageType()
  {
    return _messageType;
  }

  public void setChildren(List<DemoItemNode> children)
  {
    _children = children;
  }

  public void setDestination(String destination)
  {
    _destination = destination;
  }

  // calling this 'ico' instead of 'icon' due to tree bug

  public void setIco(String icon)
  {
    _icon = icon;
  }

  public void setLabel(String label)
  {
    _label = label;
  }

  public void setMessageType(String messageType)
  {
    _messageType = messageType;
  }

  public void setShowRequired(boolean required)
  {
    _showRequired = required; // Boolean.getBoolean(required);
  }

  @SuppressWarnings("compatibility:3179263556189907678")
  private static final long serialVersionUID = 1L;

  private String _label = null;
  private String _destination = null;
  private String _icon = null;
  private String _iconLarge = null;
  private String _iconCarousel = null;
  private List<DemoItemNode> _children = null;
  private String _messageType;
  private boolean _showRequired;
}
