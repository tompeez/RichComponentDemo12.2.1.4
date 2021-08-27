/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.custom;

import javax.el.ValueExpression;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.webapp.UIXComponentELTag;

/**

 * Auto-generated tag class.
 */
public class DemoTestPanelResizeNotifyTag extends UIXComponentELTag
{

  /**
   * Construct an instance of the UnifiedPanelResizeNotifyTag.
   */
  public DemoTestPanelResizeNotifyTag()
  {
  }

  public String getComponentType()
  {
    return "oracle.adf.DemoTestPanelResizeNotify";
  }

  public String getRendererType()
  {
    return "oracle.adf.rich.test.ResizeNotify";
  }

  private ValueExpression _inlineStyle;
  public void setInlineStyle(ValueExpression inlineStyle)
  {
    _inlineStyle = inlineStyle;
  }

  private ValueExpression _styleClass;
  public void setStyleClass(ValueExpression styleClass)
  {
    _styleClass = styleClass;
  }

  private ValueExpression _visible;
  public void setVisible(ValueExpression visible)
  {
    _visible = visible;
  }

  private ValueExpression _clientComponent;
  public void setClientComponent(ValueExpression clientComponent)
  {
    _clientComponent = clientComponent;
  }

  private ValueExpression _shortDesc;
  public void setShortDesc(ValueExpression shortDesc)
  {
    _shortDesc = shortDesc;
  }

  private ValueExpression _partialTriggers;
  public void setPartialTriggers(ValueExpression partialTriggers)
  {
    _partialTriggers = partialTriggers;
  }

  private ValueExpression _disabled;
  public void setDisabled(ValueExpression disabled)
  {
    _disabled = disabled;
  }

  protected void setProperties(
    FacesBean bean)
  {
    super.setProperties(bean);
    bean.setValueExpression(DemoTestPanelResizeNotify.INLINE_STYLE_KEY, _inlineStyle);
    bean.setValueExpression(DemoTestPanelResizeNotify.STYLE_CLASS_KEY, _styleClass);
    bean.setValueExpression(DemoTestPanelResizeNotify.VISIBLE_KEY, _visible);
    bean.setValueExpression(DemoTestPanelResizeNotify.CLIENT_COMPONENT_KEY, _clientComponent);
    setStringArrayProperty(bean, DemoTestPanelResizeNotify.PARTIAL_TRIGGERS_KEY, _partialTriggers);
    bean.setValueExpression(DemoTestPanelResizeNotify.DISABLED_KEY, _disabled);
  }

  public void release()
  {
    super.release();
    _inlineStyle = null;
    _styleClass = null;
    _visible = null;
    _clientComponent = null;
    _shortDesc = null;
    _partialTriggers = null;
    _disabled = null;
  }
}
