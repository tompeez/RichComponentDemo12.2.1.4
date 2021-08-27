/** Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.custom;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.bean.PropertyKey;
import org.apache.myfaces.trinidad.component.UIXPanel;
import org.apache.myfaces.trinidad.util.ComponentUtils;

/**
 * Component that displays multiple components overlaid on top of each other.
 *
 * <h4>Events:</h4>
 * <table border="1" width="100%" cellpadding="3" summary="">
 * <tr bgcolor="#CCCCFF" class="TableHeadingColor">
 * <th align="left">Type</th>
 * <th align="left">Phases</th>
 * <th align="left">Description</th>
 * </tr>
 * <tr class="TableRowColor">
 * <td valign="top"><code>oracle.adf.view.rich.event.AttributeChangeEvent</code></td>
 * <td valign="top" nowrap>Apply Request Values</td>
 * <td valign="top">Event delivered to describe an attribute change.</td>
 * </tr>
 * </table>
 */
public class TestPanelStackedLayout extends UIXPanel
{
  static public final FacesBean.Type TYPE = new FacesBean.Type(
    UIXPanel.TYPE);
  static public final PropertyKey INLINE_STYLE_KEY =
    TYPE.registerKey("inlineStyle", String.class);
  static public final PropertyKey STYLE_CLASS_KEY =
    TYPE.registerKey("styleClass", String.class);
  static public final PropertyKey VISIBLE_KEY =
    TYPE.registerKey("visible", Boolean.class, Boolean.TRUE);
  static public final PropertyKey SHORT_DESC_KEY =
    TYPE.registerKey("shortDesc", String.class);
  static public final PropertyKey PARTIAL_TRIGGERS_KEY =
    TYPE.registerKey("partialTriggers", String[].class);

  static public final String COMPONENT_FAMILY =
    "org.apache.myfaces.trinidad.Panel";
  static public final String COMPONENT_TYPE =
    "oracle.adf.TestPanelStackedLayout";

  /**
   * Construct an instance of the TestPanelStackedLayout.
   */
  public TestPanelStackedLayout()
  {
    super("oracle.adf.rich.test.StackedLayout");
  }

  /**
   * Gets Specifies CSS styles to use for this component.
   */
  final public String getInlineStyle()
  {
    return ComponentUtils.resolveString(getProperty(INLINE_STYLE_KEY));
  }

  /**
   * Sets Specifies CSS styles to use for this component.
   */
  final public void setInlineStyle(String inlineStyle)
  {
    setProperty(INLINE_STYLE_KEY, (inlineStyle));
  }

  /**
   * Gets a CSS style class to use for this component.
   */
  final public String getStyleClass()
  {
    return ComponentUtils.resolveString(getProperty(STYLE_CLASS_KEY));
  }

  /**
   * Sets a CSS style class to use for this component.
   */
  final public void setStyleClass(String styleClass)
  {
    setProperty(STYLE_CLASS_KEY, (styleClass));
  }

  /**
   * Gets the visibility of the component.  If it is "false", the component will be hidden on the client.  Unlike "rendered", this does not affect the lifecycle on the server - the component may have its bindings executed, etc. - and the visibility of the component can be toggled on and off on the client, or toggled with PPR.  When "rendered" is false, the component will not in any way be rendered, and cannot be made visible on the client.
   */
  final public boolean isVisible()
  {
    return ComponentUtils.resolveBoolean(getProperty(VISIBLE_KEY), true);
  }

  /**
   * Sets the visibility of the component.  If it is "false", the component will be hidden on the client.  Unlike "rendered", this does not affect the lifecycle on the server - the component may have its bindings executed, etc. - and the visibility of the component can be toggled on and off on the client, or toggled with PPR.  When "rendered" is false, the component will not in any way be rendered, and cannot be made visible on the client.
   */
  final public void setVisible(boolean visible)
  {
    setProperty(VISIBLE_KEY, visible? Boolean.TRUE : Boolean.FALSE);
  }

  /**
   * Gets the IDs of the components that should trigger a partial update.
   *         This component will listen on the trigger components. If one of the
   *         trigger components receives an event that will cause it to update
   *         in some way, this component will request to be updated too.
   */
  final public String[] getPartialTriggers()
  {
    return (String[])(getProperty(PARTIAL_TRIGGERS_KEY));
  }

  /**
   * Sets the IDs of the components that should trigger a partial update.
   *         This component will listen on the trigger components. If one of the
   *         trigger components receives an event that will cause it to update
   *         in some way, this component will request to be updated too.
   */
  final public void setPartialTriggers(String[] partialTriggers)
  {
    setProperty(PARTIAL_TRIGGERS_KEY, (partialTriggers));
  }

  public String getFamily()
  {
    return COMPONENT_FAMILY;
  }

  protected FacesBean.Type getBeanType()
  {
    return TYPE;
  }

  /**
   * Construct an instance of the TestPanelStackedLayout.
   */
  protected TestPanelStackedLayout(
    String rendererType
    )
  {
    super(rendererType);
  }

  static
  {
    TYPE.lockAndRegister("oracle.adf.Panel","oracle.adf.rich.test.StackedLayout");
  }
}
