/** Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.custom;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.bean.PropertyKey;
import org.apache.myfaces.trinidad.component.UIXPanel;
import org.apache.myfaces.trinidad.util.ComponentUtils;

import oracle.adf.view.rich.event.ClientListenerSet;

/**
 * Use this to test geometry management of components.
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
public class TestGeometryManagement extends UIXPanel
{
  static public final FacesBean.Type TYPE = new FacesBean.Type(
    UIXPanel.TYPE);
  static public final PropertyKey INLINE_STYLE_KEY =
    TYPE.registerKey("inlineStyle", String.class);
  static public final PropertyKey STYLE_CLASS_KEY =
    TYPE.registerKey("styleClass", String.class);
  static public final PropertyKey VISIBLE_KEY =
    TYPE.registerKey("visible", Boolean.class, Boolean.TRUE);
  static public final PropertyKey CLIENT_COMPONENT_KEY =
    TYPE.registerKey("clientComponent", Boolean.class);
  static public final PropertyKey SHORT_DESC_KEY =
    TYPE.registerKey("shortDesc", String.class);
  static public final PropertyKey CLIENT_LISTENERS_KEY =
    TYPE.registerKey("clientListeners", ClientListenerSet.class, PropertyKey.CAP_NOT_BOUND);
  static public final PropertyKey PARTIAL_TRIGGERS_KEY =
    TYPE.registerKey("partialTriggers", String[].class);
  static public final PropertyKey DISABLED_KEY =
    TYPE.registerKey("disabled", Boolean.class);
  static public final PropertyKey DISABLED_NOTES_KEY =
    TYPE.registerKey("disabledNotes", String.class);
  static public final PropertyKey FAKE_STRETCHED_STYLE_KEY =
    TYPE.registerKey("fakeStretchedStyle", String.class);
  static public final PropertyKey FLOW_NOTES_KEY =
    TYPE.registerKey("flowNotes", String.class);
  static public final PropertyKey FLOW_WITH_ZERO_WIDTH_NOTES_KEY =
    TYPE.registerKey("flowWithZeroWidthNotes", String.class);
  static public final PropertyKey STRETCH_NOTES_KEY =
    TYPE.registerKey("stretchNotes", String.class);
  static public final PropertyKey CHROME_KEY =
    TYPE.registerKey("chrome", String.class, "shown");
  static public final PropertyKey STRETCH_CHILDREN_KEY =
    TYPE.registerKey("stretchChildren", String.class, "none");

  static public final String COMPONENT_FAMILY =
    "org.apache.myfaces.trinidad.Panel";
  static public final String COMPONENT_TYPE =
    "oracle.adf.TestGeometryManagement";

  /**
   * Construct an instance of the TestPanelResizeNotify.
   */
  public TestGeometryManagement()
  {
    super("oracle.adf.rich.test.TestGeometryManagement");
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
   * Gets whether a client-side component will be generated.  A component may be generated whether or not this flag is set, but if client Javascript requires the component object, this must be set to true to guarantee the component's presence.  Client component objects that are generated today by default may not be present in the future;  setting this flag is the only way to guarantee a component's presence, and clients cannot rely on implicit behavior.  However, there is a performance cost to setting this flag, so clients should avoid turning on client components unless absolutely necessary.)
   */
  final public boolean isClientComponent()
  {
    return ComponentUtils.resolveBoolean(getProperty(CLIENT_COMPONENT_KEY));
  }

  /**
   * Sets whether a client-side component will be generated.  A component may be generated whether or not this flag is set, but if client Javascript requires the component object, this must be set to true to guarantee the component's presence.  Client component objects that are generated today by default may not be present in the future;  setting this flag is the only way to guarantee a component's presence, and clients cannot rely on implicit behavior.  However, there is a performance cost to setting this flag, so clients should avoid turning on client components unless absolutely necessary.)
   */
  final public void setClientComponent(boolean clientComponent)
  {
    setProperty(CLIENT_COMPONENT_KEY, clientComponent? Boolean.TRUE : Boolean.FALSE);
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

  /**
   * Gets If value is "true", the component becomes non-interactive.  Otherwise,the default value is "false" and component assumes its expected behavior.
   */
  final public boolean isDisabled()
  {
    return ComponentUtils.resolveBoolean(getProperty(DISABLED_KEY));
  }

  /**
   * Sets If value is "true", the component becomes non-interactive.  Otherwise,the default value is "false" and component assumes its expected behavior.
   */
  final public void setDisabled(boolean disabled)
  {
    setProperty(DISABLED_KEY, disabled? Boolean.TRUE : Boolean.FALSE);
  }

  /**
   * Gets the disabledNotes property.
   */
  final public String getDisabledNotes()
  {
    return ComponentUtils.resolveString(getProperty(DISABLED_NOTES_KEY));
  }

  /**
   * Sets the disabledNotes property.
   */
  final public void setDisabledNotes(String disabledNotes)
  {
    setProperty(DISABLED_NOTES_KEY, disabledNotes);
  }

  /**
   * Gets the fakeStretchedStyle property.
   */
  final public String getFakeStretchedStyle()
  {
    return ComponentUtils.resolveString(getProperty(FAKE_STRETCHED_STYLE_KEY));
  }

  /**
   * Sets the fakeStretchedStyle property.
   */
  final public void setFakeStretchedStyle(String fakeStretchedStyle)
  {
    setProperty(FAKE_STRETCHED_STYLE_KEY, fakeStretchedStyle);
  }

  /**
   * Gets the flowNotes property.
   */
  final public String getFlowNotes()
  {
    return ComponentUtils.resolveString(getProperty(FLOW_NOTES_KEY));
  }

  /**
   * Sets the flowNotes property.
   */
  final public void setFlowNotes(String flowNotes)
  {
    setProperty(FLOW_NOTES_KEY, flowNotes);
  }

  /**
   * Gets the flowWithZeroWidthNotes property.
   */
  final public String getFlowWithZeroWidthNotes()
  {
    return ComponentUtils.resolveString(getProperty(FLOW_WITH_ZERO_WIDTH_NOTES_KEY));
  }

  /**
   * Sets the flowWithZeroWidthNotes property.
   */
  final public void setFlowWithZeroWidthNotes(String flowWithZeroWidthNotes)
  {
    setProperty(FLOW_WITH_ZERO_WIDTH_NOTES_KEY, flowWithZeroWidthNotes);
  }

  /**
   * Gets the stretchNotes property.
   */
  final public String getStretchNotes()
  {
    return ComponentUtils.resolveString(getProperty(STRETCH_NOTES_KEY));
  }

  /**
   * Sets the stretchNotes property.
   */
  final public void setStretchNotes(String stretchNotes)
  {
    setProperty(STRETCH_NOTES_KEY, stretchNotes);
  }

  /**
   * Gets the chrome property.
   */
  final public String getChrome()
  {
    return ComponentUtils.resolveString(getProperty(CHROME_KEY));
  }

  /**
   * Sets the chrome property
   */
  final public void setChrome(String chrome)
  {
    setProperty(CHROME_KEY, chrome);
  }

  /**
   * Gets the stretchChildren property.
   */
  final public String getStretchChildren()
  {
    return ComponentUtils.resolveString(getProperty(STRETCH_CHILDREN_KEY));
  }

  /**
   * Sets the stretchChildren property.
   */
  final public void setStretchChildren(String stretchChildren)
  {
    setProperty(STRETCH_CHILDREN_KEY, stretchChildren);
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
   * Construct an instance of the TestGeometryManagement.
   */
  protected TestGeometryManagement(
    String rendererType
    )
  {
    super(rendererType);
  }

  static
  {
    TYPE.lockAndRegister("oracle.adf.Panel","oracle.adf.rich.test.TestGeometryManagement");
  }
}

