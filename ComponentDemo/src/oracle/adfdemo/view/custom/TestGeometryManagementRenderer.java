/** Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.custom;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import oracle.adf.view.rich.render.ClientComponent;
import oracle.adf.view.rich.render.ClientMetadata;
import oracle.adf.view.rich.render.RichRenderer;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.bean.PropertyKey;
import org.apache.myfaces.trinidad.component.ComponentProcessingContext;
import org.apache.myfaces.trinidad.component.ComponentProcessor;
import org.apache.myfaces.trinidad.component.UIXComponent;
import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidad.render.CoreRenderer;


public class TestGeometryManagementRenderer extends RichRenderer
{
  public TestGeometryManagementRenderer()
  {
    super(TestGeometryManagement.TYPE);
  }

  @Override
  protected void findTypeConstants(
    FacesBean.Type type,
    ClientMetadata metadata)
  {
    super.findTypeConstants(type, metadata);

    _disabledNotesKey          = type.findKey("disabledNotes");
    _fakeStretchedStyleKey     = type.findKey("fakeStretchedStyle");
    _flowNotesKey              = type.findKey("flowNotes");
    _flowWithZeroWidthNotesKey = type.findKey("flowWithZeroWidthNotes");
    _stretchNotesKey           = type.findKey("stretchNotes");
    _chromeKey                 = type.findKey("chrome");

    _disabledKey = type.findKey("disabled");
    metadata.addRequiredProperty(_disabledKey);

    _stretchChildrenKey = type.findKey("stretchChildren");
    metadata.addRequiredProperty(_stretchChildrenKey);
  }

  @Override
  protected String getClientConstructor()
  {
    return "TestGeometryManagement";
  }

  @Override
  protected String getDefaultStyleClass(
    FacesContext     context,
    RenderingContext rc,
    FacesBean        bean)
  {
    return "af|testGeometryManagement";
  }

  @Override
  protected ClientComponent.Type getClientComponentType(
    FacesContext context,
    UIComponent  component,
    FacesBean    bean)
  {
    return ClientComponent.Type.CREATE_WITH_REQUIRED_ATTRS;
  }

  /**
   * Returns null or extra styles to prepend to the inlineStyle.
   * @return null or extra styles to prepend to the inlineStyle (terminated by a semicolon)
   */
  @Override
  protected String getPrependedInlineStyle(
    FacesContext     context,
    RenderingContext rc,
    UIComponent      component,
    ClientComponent  client,
    FacesBean        bean)
  {
    Map<String, Object> requestMap = context.getExternalContext().getRequestMap();
    Object visualRootStylesObject = requestMap.get(_VISUAL_ROOT_STYLES_KEY);
    if (visualRootStylesObject == null)
    {
      return null;
    }
    else
    {
      // Clean up after ourselves since we don't need this value anymore.
      requestMap.remove(_VISUAL_ROOT_STYLES_KEY);
      return visualRootStylesObject.toString();
    }
  }

  @Override
  protected void encodeAll(
    FacesContext     context,
    RenderingContext rc,
    UIComponent      component,
    ClientComponent  client,
    FacesBean        bean)
    throws IOException
  {
    ResponseWriter rw = context.getResponseWriter();

    // Start the root element:
    rw.startElement("div", component);

    String visualRootStyles = getVisualRootStretchingStyles(context, rc, component, client, bean);
    if (visualRootStyles != null)
    {
      // We need to store this somewhere so that encodeStretchedChild can be applied when needed!
      Map<String, Object> requestMap =
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
      requestMap.put(_VISUAL_ROOT_STYLES_KEY, visualRootStyles);
    }

    // Now that we've started our root element, it is guaranteed safe to ask if we are being
    // stretched:
    boolean stretchedByAncestor = isNextElementToBeStretched(context) || visualRootStyles != null;
    boolean emailOrPrintMode    = false;
    if (_isEmailablePage(rc) || _isPrintablePage(rc))
    {
      emailOrPrintMode = true;
      stretchedByAncestor = false; // disable stretching in print and email modes
    }

    // Render all of the standard root element attributes.
    // Note that we are passing in false to renderAllRootAttributes() and calling
    // renderRootStyleAttributes() explicitly with a non-null component so that
    // our implementation of getPrependedInlineStyle() will be invoked.
    renderId(context, component);
    renderAllRootAttributes(context, rc, client, bean, false);
    renderRootStyleAttributes(context, rc, component, client, bean);

    boolean disabled = _isDisabled(bean);
    client.addProperty(_disabledKey, disabled);

    String stretchChildren = _getStretchChildren(bean);
    client.addProperty(_stretchChildrenKey, stretchChildren);

    // Gather information for the flattened children:
    ChildVisitorCallbackState childVisitorCallbackState = new ChildVisitorCallbackState();
    List<UIComponent> children = component.getChildren();
    UIXComponent.processFlattenedChildren(
      context,
      _childVisitorCallback,
      children,
      childVisitorCallbackState);
    int flattenedChildCount = childVisitorCallbackState.childCount;

    boolean fakedStretchedByAncestor = false;
    if (!emailOrPrintMode && !stretchedByAncestor)
    {
      // The page developer failed to place this component in a container that will be stretching
      // it so rather than just complaining, we will wrap it in a div that has hard-coded dimensions
      // so you can still see the component being stretched--you just won't be able to see the
      // component resize when you resize the browser.
      fakedStretchedByAncestor = true;
      rw.startElement("div", null);
      String clientId = getClientId(context, component, client);
      rw.writeAttribute("id", createSubId(clientId, "fake"), null);

      if ("first".equals(stretchChildren))
      {
        // Force it to be true:
        stretchedByAncestor = true;

        // Child needs to stretch so we must style this pseudo-stretched div so that the child can
        // be stretched inside of it:
        String fakeStretchedStyle = _getFakeStretchedStyle(bean);
        rw.writeAttribute("style", "position:relative;width:98%;height:500px;overflow:hidden;border:1px solid orange;" + fakeStretchedStyle, null);
      }
    }

    // Encode the children (wrapped in a div with styles based on whether this component is being
    // stretched by its ancestor):
    rw.startElement("div", null);
    String clientId = getClientId(context, component, client);
    rw.writeAttribute("id", createSubId(clientId, "body"), null);
    if (stretchedByAncestor)
    {
      String overflow = "hidden";
      if (!"first".equals(stretchChildren))
        overflow = "auto";
      rw.writeAttribute(
        "style",
        "position:absolute;overflow:" + overflow + ";left:0;right:0;top:0;bottom:" + _initialControlsHeight,
         null);
    }
    boolean useZeroWidthContainer = "noneWithZeroWidth".equals(stretchChildren);
    if (useZeroWidthContainer)
    {
      rw.startElement("table", null);
      rw.writeAttribute("cellpadding", "0", null);
      rw.writeAttribute("cellspacing", "0", null);
      rw.writeAttribute("border", "0", null);
      rw.writeAttribute("role", "presentation", null);
      rw.writeAttribute(
        "summary",
        "Layout table for generating an intrinsically zero-width container.",
        null);
      rw.startElement("tbody", null);
      rw.startElement("tr", null);
    }
    UIXComponent.encodeFlattenedChildren(
      context,
      _childEncoderCallback,
      children,
      new EncoderCallbackState(rc, flattenedChildCount, stretchedByAncestor, stretchChildren));
    if (useZeroWidthContainer)
    {
      rw.endElement("tr");
      rw.endElement("tbody");
      rw.endElement("table");
    }
    rw.endElement("div");
    if (fakedStretchedByAncestor)
    {
      rw.endElement("div");
    }

    // Encode the control area:
    rw.startElement("div", null); // start controls outer div
    rw.writeAttribute("id", createSubId(clientId, "controls"), null);
    if ("hidden".equals(_getChrome(bean)))
    {
      // Make sure that this div cannot be seen:
      rw.writeAttribute(
        "style",
        "position:absolute;overflow:hidden;left:0;right:0;bottom:0;display:none",
         null);
    }
    else if (stretchedByAncestor)
    {
      rw.writeAttribute(
        "style",
        "position:absolute;overflow:hidden;left:0;right:0;bottom:0;",
         null);
    }

    rw.startElement("div", null); // start padded controls div
    rw.writeAttribute("style", "padding:4px", null);
    rw.startElement("span", null);
    rw.writeAttribute("style", "font-weight:bold", null);
    rw.writeText("Geometry Management", null);
    rw.endElement("span");
    rw.startElement("ul", null);
    rw.writeAttribute("style", "margin:0;padding:0 2em", null);
    if (flattenedChildCount > 1)
    {
      rw.startElement("li", null);
      rw.writeText("The above " + flattenedChildCount + " components are only shown in a non-stretched (flowing) manner rather than being stretched by their ancestor because there needs to only be 1 component for stretching to work.",
        null);
      rw.endElement("li");

      rw.startElement("li", null);
      rw.writeText(
        "  Please ask the author of this page to correct this hierarchy or to utilize the \"nonVisual\" facet for non-visual components like popups.",
        null);
      rw.endElement("li");
    }
    else
    {
      if (fakedStretchedByAncestor)
      {
        rw.startElement("li", null);
        rw.writeText("The above component is currently shown with an artificial fixed size because its grandparent component is not stretching its child.  This means that resizing your browser will have zero effect on resizing the component.",
          null);
        rw.endElement("li");
      }

      if (emailOrPrintMode)
      {
        rw.startElement("li", null);
        rw.writeText("The above component is only shown in a non-stretched (flowing) manner because you are viewing this page in either email or print mode where stretching must be disabled.",
          null);
        rw.endElement("li");
      }
      else if (stretchedByAncestor && "first".equals(stretchChildren))
      {
        String stretchNotes = _getStretchNotes(bean);
        if (stretchNotes != null)
        {
          rw.startElement("li", null);
          rw.writeText(stretchNotes, null);
          rw.endElement("li");
        }

        if (disabled)
        {
          String disabledNotes = _getDisabledNotes(bean);
          if (disabledNotes != null)
          {
            rw.startElement("li", null);
            rw.writeText(disabledNotes, null);
            rw.endElement("li");
          }

          rw.startElement("li", null);
          rw.writeText("The above component is currently shown in a stretched-by-its-ancestor manner.", null);
          rw.endElement("li");
        }
        else // not disabled
        {
          rw.startElement("li", null);
          _writeModeSwitches(context, rw, rc, clientId, SwitchType.STRETCH_FIRST);
          rw.endElement("li");
        }
      }
      else if (useZeroWidthContainer)
      {
        String flowWithZeroWidthNotes = _getFlowWithZeroWidthNotes(bean);
        if (flowWithZeroWidthNotes != null)
        {
          rw.startElement("li", null);
          rw.writeText(flowWithZeroWidthNotes, null);
          rw.endElement("li");
        }

        if (disabled)
        {
          String disabledNotes = _getDisabledNotes(bean);
          if (disabledNotes != null)
          {
            rw.startElement("li", null);
            rw.writeText(disabledNotes, null);
            rw.endElement("li");
          }

          rw.startElement("li", null);
          rw.writeText("The above component is currently shown in a non-stretched (flowing) manner and it exists in a container that does not have any intrinsic width.", null);
          rw.endElement("li");
        }
        else // not disabled
        {
          rw.startElement("li", null);
          _writeModeSwitches(context, rw, rc, clientId, SwitchType.STRETCH_NONE_WITH_NO_INTRINSIC_WIDTH);
          rw.endElement("li");
        }
      }
      else
      {
        String flowNotes = _getFlowNotes(bean);
        if (flowNotes != null)
        {
          rw.startElement("li", null);
          rw.writeText(flowNotes, null);
          rw.endElement("li");
        }

        if (disabled)
        {
          String disabledNotes = _getDisabledNotes(bean);
          if (disabledNotes != null)
          {
            rw.startElement("li", null);
            rw.writeText(disabledNotes, null);
            rw.endElement("li");
          }

          rw.startElement("li", null);
          rw.writeText("The above component is currently shown in a non-stretched (flowing) manner and it exists in a container that has an intrinsic width.", null);
          rw.endElement("li");
        }
        else // not disabled
        {
          rw.startElement("li", null);
          _writeModeSwitches(context, rw, rc, clientId, SwitchType.STRETCH_NONE_WITH_INTRINSIC_WIDTH);
          rw.endElement("li");
        }
      }
    }
    rw.endElement("ul");
    rw.endElement("div"); // end padded controls div

    rw.endElement("div"); // end controls outer div

    // If someone needs some extra components (e.g. supporting popups, they can put them into the
    // "nonVisual" facet of this component and we'll encode them in a separate DIV so that they do
    // not interfere with stretching:
    UIComponent nonVisualFacet = component.getFacet("nonVisual");
    if (nonVisualFacet != null)
    {
      rw.startElement("div", null);
      rw.writeAttribute("id", createSubId(clientId, "nonVisual"), null);
      encodeChild(context, nonVisualFacet);
      rw.endElement("div");
    }

    // Close the root element:
    rw.endElement("div");
  }

  @Override
  protected boolean visitChildrenForEncodingImpl(
    UIXComponent  component,
    VisitContext  visitContext,
    VisitCallback callback)
  {
    FacesContext     fc = FacesContext.getCurrentInstance();

    boolean stretchedByAncestor = isNextElementToBeStretched(fc);
    String  stretchChildren     = _getStretchChildren(component.getFacesBean());

    // Gather information for the flattened children:
    ChildVisitorCallbackState childVisitorCallbackState = new ChildVisitorCallbackState();
    List<UIComponent> children = component.getChildren();
    try
    {
      UIXComponent.processFlattenedChildren(
        fc,
        _childVisitorCallback,
        children,
        childVisitorCallbackState);
    }
    catch (IOException e)
    {
      throw new FacesException(e);
    }
    int flattenedChildCount = childVisitorCallbackState.childCount;

    if (stretchedByAncestor && flattenedChildCount <= 1 && "first".equals(stretchChildren))
    {
      return _visitFlattenedChildrenForEncodingAsStretched(component, visitContext, callback);
    }
    else
    {
      return super.visitChildrenForEncodingImpl(component, visitContext, callback);
    }
  }

  private void _writeModeSwitches(
    FacesContext     context,
    ResponseWriter   rw,
    RenderingContext rc,
    String           clientId,
    SwitchType       selectedSwitchType)
    throws IOException
  {
    rw.startElement("div", null);
    rw.writeText("Component container display mode:", null);
    rw.endElement("div");

    _writeLink(
      context,
      rw,
      rc,
      createSubId(clientId, "swstretch"), // this subid is used in the peer
      "Stretched",
      "Displays the component as being stretched by its ancestor",
      SwitchType.STRETCH_FIRST.equals(selectedSwitchType));

    rw.writeText(" | ", null);

    _writeLink(
      context,
      rw,
      rc,
      createSubId(clientId, "swflowyw"), // this subid is used in the peer
      "Flow with Width",
      "Displays the component as flowing (not stretched by its ancestor) where the ancestor does have an intrinsic width",
      SwitchType.STRETCH_NONE_WITH_INTRINSIC_WIDTH.equals(selectedSwitchType));

    rw.writeText(" | ", null);

    _writeLink(
      context,
      rw,
      rc,
      createSubId(clientId, "swflownw"), // this subid is used in the peer
      "Flow without Width",
      "Displays the component as flowing (not stretched by its ancestor) where the ancestor does not have an intrinsic width",
      SwitchType.STRETCH_NONE_WITH_NO_INTRINSIC_WIDTH.equals(selectedSwitchType));

  }

  private static void _writeLink(
    FacesContext     context,
    ResponseWriter   rw,
    RenderingContext rc,
    String           id,
    String           text,
    String           shortDesc,
    boolean          selected)
    throws IOException
  {
    rw.startElement("a", null);
    rw.writeAttribute("id", id, null);
    renderStyleClass(context, rc, "af|commandLink"); // TODO this should be some kind of "AFLink"-like selector but no such thing exists
    rw.writeURIAttribute("href", "#", null);
    if (CoreRenderer.isWebKit(rc))
    {
      rw.writeAttribute("onclick", "this.focus();return false", null);
    }
    else
    {
      rw.writeAttribute("onclick", "return false", null);
    }


    if (shortDesc != null)
      rw.writeAttribute("title", shortDesc, null);

    if (selected)
      rw.writeAttribute("style", "font-weight:bold;cursor:default", null);

    rw.writeText(text, null);

    rw.endElement("a");
  }

  /**
   * Returns true if this is a printable page
   */
  private static boolean _isPrintablePage(RenderingContext rc)
  {
    return "printable".equals(rc.getOutputMode());
  }

  /**
   * Returns true if this is a email page
   */
  private static boolean _isEmailablePage(RenderingContext rc)
  {
    return "email".equals(rc.getOutputMode());
  }

  /**
   * Returns a boolean representing whether this component is disabled.
   * @param bean the FacesBean for this component
   * @return boolean value for whether this component is disabled
   */
  private boolean _isDisabled(FacesBean bean)
  {
    Object o = bean.getProperty(_disabledKey);
    if (o == null)
      o = _disabledKey.getDefault();

    return Boolean.TRUE.equals(o);
  }

  private String _getChrome(FacesBean bean)
  {
    Object o = bean.getProperty(_chromeKey);
    if (o == null)
      o = _chromeKey.getDefault();

    return toString(o);
  }

  /**
   * Returns "first", "noneWithZeroWidth", or "none" for the desired stretching or flowing container
   * characteristics.
   * @param bean the FacesBean for this component
   * @return the desired behavior for stretching of the children
   */
  private String _getStretchChildren(FacesBean bean)
  {
    Object o = bean.getProperty(_stretchChildrenKey);
    if (o == null)
      o = _stretchChildrenKey.getDefault();

    return toString(o);
  }

  private String _getDisabledNotes(FacesBean bean)
  {
    Object o = bean.getProperty(_disabledNotesKey);
    if (o == null)
      o = _disabledNotesKey.getDefault();

    return toString(o);
  }

  private String _getFakeStretchedStyle(FacesBean bean)
  {
    Object o = bean.getProperty(_fakeStretchedStyleKey);
    if (o == null)
      o = _fakeStretchedStyleKey.getDefault();

    if (o == null)
      return "";

    return toString(o);
  }

  private String _getFlowNotes(FacesBean bean)
  {
    Object o = bean.getProperty(_flowNotesKey);
    if (o == null)
      o = _flowNotesKey.getDefault();

    return toString(o);
  }

  private String _getFlowWithZeroWidthNotes(FacesBean bean)
  {
    Object o = bean.getProperty(_flowWithZeroWidthNotesKey);
    if (o == null)
      o = _flowWithZeroWidthNotesKey.getDefault();

    return toString(o);
  }

  private String _getStretchNotes(FacesBean bean)
  {
    Object o = bean.getProperty(_stretchNotesKey);
    if (o == null)
      o = _stretchNotesKey.getDefault();

    return toString(o);
  }

  /**
   * Flattens the children of the given component and then visits them as if they were flattened but
   * also manages the stretching of each flattened child.
   * @param component The component to visit the children of
   * @param visitContext The visit context
   * @param callback the visit callback
   * @return <code>true</code> if the visit is complete.
   */
  private boolean _visitFlattenedChildrenForEncodingAsStretched(
    UIXComponent  component,
    VisitContext  visitContext,
    VisitCallback callback)
  {
    FacesContext      fc       = visitContext.getFacesContext();
    RenderingContext  rc       = RenderingContext.getCurrentInstance();
    List<UIComponent> children = component.getChildren();

    try
    {
      EncodingVisitAsStretchedCallbackState callbackState =
        new EncodingVisitAsStretchedCallbackState(rc, visitContext, callback);

      UIXComponent.encodeFlattenedChildren(
        fc,
        _encodingVisitAsStretchedCallback,
        children,
        callbackState);

      return callbackState.visitIsComplete;
    }
    catch (IOException ioe)
    {
      throw new FacesException(ioe);
    }
  }

  /**
   * Context object used to collect information about the children to be rendered.
   */
  private static class ChildVisitorCallbackState
  {
    public ChildVisitorCallbackState()
    {
    }

    private int childCount;
  }

  /**
   * Visitor for each flattened child, using the information in the RenderedItemCallbackState.
   */
  private class ChildVisitorCallback implements ComponentProcessor<ChildVisitorCallbackState>
  {
    public void processComponent(
      FacesContext               context,
      ComponentProcessingContext cpContext,
      UIComponent                currChild,
      ChildVisitorCallbackState  childVisitorCallbackState)
      throws IOException
    {
      childVisitorCallbackState.childCount++;
    }
  }

  /**
   * Context object used to encode the children.
   */
  private static class EncoderCallbackState
  {
    public EncoderCallbackState(
      RenderingContext rc,
      int              childCount,
      boolean          stretchedByAncestor,
      String           stretchChildren)
    {
      this.rc                  = rc;
      this.childCount          = childCount;
      this.stretchedByAncestor = stretchedByAncestor;
      this.stretchChildren     = stretchChildren;
    }

    protected final RenderingContext rc;
    protected final int              childCount;
    protected final boolean          stretchedByAncestor;
    protected final String           stretchChildren;
  }

  /**
   * Visitor for each flattened child, using the information in the RenderedItemCallbackState.
   */
  private class ChildEncoderCallback implements ComponentProcessor<EncoderCallbackState>
  {
    public void processComponent(
      FacesContext               context,
      ComponentProcessingContext cpContext,
      UIComponent                currChild,
      EncoderCallbackState       encodingState)
      throws IOException
    {
      RenderingContext rc                  = encodingState.rc;
      int              childCount          = encodingState.childCount;
      boolean          stretchedByAncestor = encodingState.stretchedByAncestor;
      String           stretchChildren     = encodingState.stretchChildren;

      if (stretchedByAncestor && childCount <= 1 && "first".equals(stretchChildren))
      {
        // There is only one child so that means we can stretch it:
        encodeStretchedChild(context, rc, currChild, "0px");
      }
      else if ("noneWithZeroWidth".equals(stretchChildren))
      {
        ResponseWriter rw = context.getResponseWriter();
        rw.startElement("td", null);
        encodeChild(context, currChild);
        rw.endElement("td");
      }
      else
      {
        // There is more than one child and since we don't have any special mechanism for
        // distributing space among multiple children, we won't stretch the children:
        encodeChild(context, currChild);
      }
    }
  }

  private static class EncodingVisitAsStretchedCallbackState
  {
    public EncodingVisitAsStretchedCallbackState(
      RenderingContext rc,
      VisitContext     visitContext,
      VisitCallback    callback)
    {
      this.rc           = rc;
      this.visitContext = visitContext;
      this.callback     = callback;
    }

    private RenderingContext rc;
    private VisitContext     visitContext;
    private VisitCallback    callback;
    private boolean          visitIsComplete;
  }

  /**
   * Visitor for each flattened child, using the information in the RenderedItemCallbackState.
   */
  private class EncodingVisitAsStretchedCallback
    implements ComponentProcessor<EncodingVisitAsStretchedCallbackState>
  {
    public void processComponent(
      FacesContext                          fc,
      ComponentProcessingContext            cpc,
      UIComponent                           currChild,
      EncodingVisitAsStretchedCallbackState callbackState)
      throws IOException
    {
      if (!callbackState.visitIsComplete)
      {
        ResponseWriter rw = encodeStretchedChildBegin(fc, callbackState.rc, "0px");
        try
        {
          if (currChild.visitTree(callbackState.visitContext, callbackState.callback))
          {
            callbackState.visitIsComplete = true;
          }
        }
        finally
        {
          encodeStretchedChildEnd(fc, rw);
        }
      }
    }
  }

  /**
   * Enumeration for stretchChildren modes.
   */
  private enum SwitchType {
    STRETCH_FIRST,
    STRETCH_NONE_WITH_NO_INTRINSIC_WIDTH,
    STRETCH_NONE_WITH_INTRINSIC_WIDTH
  };

  private String      _initialControlsHeight = "2em";
  private PropertyKey _disabledKey;
  private PropertyKey _disabledNotesKey;
  private PropertyKey _fakeStretchedStyleKey;
  private PropertyKey _flowNotesKey;
  private PropertyKey _flowWithZeroWidthNotesKey;
  private PropertyKey _stretchNotesKey;
  private PropertyKey _chromeKey;
  private PropertyKey _stretchChildrenKey;

  static private final String _VISUAL_ROOT_STYLES_KEY = "_testGmRootStyles";

  private final ChildVisitorCallback _childVisitorCallback = new ChildVisitorCallback();
  private final ChildEncoderCallback _childEncoderCallback = new ChildEncoderCallback();
  private final EncodingVisitAsStretchedCallback _encodingVisitAsStretchedCallback =
    new EncodingVisitAsStretchedCallback();
}
