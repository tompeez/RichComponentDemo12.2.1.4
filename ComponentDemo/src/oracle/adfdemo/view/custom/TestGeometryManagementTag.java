/** Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.custom;

import javax.el.ValueExpression;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.webapp.UIXComponentELTag;

/**
 * Tag class for the TestGeometryManagement component.
 */
public class TestGeometryManagementTag extends UIXComponentELTag
{
  /**
   * Construct an instance of the TestGeometryManagementTag.
   */
  public TestGeometryManagementTag()
  {
  }

  public String getComponentType()
  {
    return "oracle.adf.TestGeometryManagement";
  }

  public String getRendererType()
  {
    return "oracle.adf.rich.test.TestGeometryManagement";
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

  private ValueExpression _disabledNotes;
  public void setDisabledNotes(ValueExpression disabledNotes)
  {
    _disabledNotes = disabledNotes;
  }

  private ValueExpression _fakeStretchedStyle;
  public void setFakeStretchedStyle(ValueExpression fakeStretchedStyle)
  {
    _fakeStretchedStyle = fakeStretchedStyle;
  }

  private ValueExpression _flowNotes;
  public void setFlowNotes(ValueExpression flowNotes)
  {
    _flowNotes = flowNotes;
  }

  private ValueExpression _flowWithZeroWidthNotes;
  public void setFlowWithZeroWidthNotes(ValueExpression flowWithZeroWidthNotes)
  {
    _flowWithZeroWidthNotes = flowWithZeroWidthNotes;
  }

  private ValueExpression _stretchNotes;
  public void setStretchNotes(ValueExpression stretchNotes)
  {
    _stretchNotes = stretchNotes;
  }

  private ValueExpression _chrome;
  public void setChrome(ValueExpression chrome)
  {
    _chrome = chrome;
  }

  private ValueExpression _stretchChildren;
  public void setStretchChildren(ValueExpression stretchChildren)
  {
    _stretchChildren = stretchChildren;
  }

  protected void setProperties(
    FacesBean bean)
  {
    super.setProperties(bean);
    bean.setValueExpression(TestGeometryManagement.INLINE_STYLE_KEY, _inlineStyle);
    bean.setValueExpression(TestGeometryManagement.STYLE_CLASS_KEY, _styleClass);
    bean.setValueExpression(TestGeometryManagement.VISIBLE_KEY, _visible);
    bean.setValueExpression(TestGeometryManagement.CLIENT_COMPONENT_KEY, _clientComponent);
    setStringArrayProperty(bean, TestGeometryManagement.PARTIAL_TRIGGERS_KEY, _partialTriggers);
    bean.setValueExpression(TestGeometryManagement.DISABLED_KEY, _disabled);
    bean.setValueExpression(TestGeometryManagement.DISABLED_NOTES_KEY, _disabledNotes);
    bean.setValueExpression(TestGeometryManagement.FAKE_STRETCHED_STYLE_KEY, _fakeStretchedStyle);
    bean.setValueExpression(TestGeometryManagement.FLOW_NOTES_KEY, _flowNotes);
    bean.setValueExpression(TestGeometryManagement.FLOW_WITH_ZERO_WIDTH_NOTES_KEY, _flowWithZeroWidthNotes);
    bean.setValueExpression(TestGeometryManagement.STRETCH_NOTES_KEY, _stretchNotes);
    bean.setValueExpression(TestGeometryManagement.CHROME_KEY, _chrome);
    bean.setValueExpression(TestGeometryManagement.STRETCH_CHILDREN_KEY, _stretchChildren);
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
    _disabledNotes = null;
    _fakeStretchedStyle = null;
    _flowNotes = null;
    _flowWithZeroWidthNotes = null;
    _stretchNotes = null;
    _chrome = null;
    _stretchChildren = null;
  }
}
