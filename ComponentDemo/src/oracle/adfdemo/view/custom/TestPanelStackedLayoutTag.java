/** Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.custom;

import javax.el.ValueExpression;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.webapp.UIXComponentELTag;

public class TestPanelStackedLayoutTag extends UIXComponentELTag
{
  /**
   * Construct an instance of the UnifiedPanelStackedLayoutTag.
   */
  public TestPanelStackedLayoutTag()
  {
  }

  public String getComponentType()
  {
    return "oracle.adf.TestPanelStackedLayout";
  }

  public String getRendererType()
  {
    return "oracle.adf.rich.test.StackedLayout";
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

  protected void setProperties(
    FacesBean bean)
  {
    super.setProperties(bean);
    bean.setValueExpression(TestPanelStackedLayout.INLINE_STYLE_KEY, _inlineStyle);
    bean.setValueExpression(TestPanelStackedLayout.STYLE_CLASS_KEY, _styleClass);
    bean.setValueExpression(TestPanelStackedLayout.VISIBLE_KEY, _visible);
    setStringArrayProperty(bean, TestPanelStackedLayout.PARTIAL_TRIGGERS_KEY, _partialTriggers);
  }

  public void release()
  {
    super.release();
    _inlineStyle = null;
    _styleClass = null;
    _visible = null;
    _shortDesc = null;
    _partialTriggers = null;
  }
}
