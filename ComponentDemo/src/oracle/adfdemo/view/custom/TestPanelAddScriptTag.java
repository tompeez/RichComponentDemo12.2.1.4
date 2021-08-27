/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.custom;

import javax.el.ValueExpression;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.webapp.UIXComponentELTag;

/**

 * Auto-generated tag class.
 */
public class TestPanelAddScriptTag extends UIXComponentELTag

{

  /**
   * Construct an instance of the UnifiedPanelAddScriptTag.
   */
  public TestPanelAddScriptTag()
  {
  }

  public String getComponentType()
  {
    return "oracle.adf.TestPanelAddScript";
  }

  public String getRendererType()
  {
    return "oracle.adf.rich.test.AddScript";
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
    bean.setValueExpression(TestPanelAddScript.INLINE_STYLE_KEY, _inlineStyle);
    bean.setValueExpression(TestPanelAddScript.STYLE_CLASS_KEY, _styleClass);
    bean.setValueExpression(TestPanelAddScript.VISIBLE_KEY, _visible);
    bean.setValueExpression(TestPanelAddScript.CLIENT_COMPONENT_KEY, _clientComponent);
    bean.setValueExpression(TestPanelAddScript.PARTIAL_TRIGGERS_KEY, _partialTriggers);
    bean.setValueExpression(TestPanelAddScript.DISABLED_KEY, _disabled);
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
