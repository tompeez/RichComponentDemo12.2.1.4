/** Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.custom;

import java.io.IOException;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.context.RenderingContext;

import oracle.adf.view.rich.render.ClientComponent;
import oracle.adf.view.rich.render.RichRenderer;

import org.apache.myfaces.trinidad.component.ComponentProcessingContext;
import org.apache.myfaces.trinidad.component.ComponentProcessor;
import org.apache.myfaces.trinidad.component.UIXComponent;

public class TestPanelStackedLayoutRenderer extends RichRenderer
{
  public TestPanelStackedLayoutRenderer()
  {
    super(TestPanelStackedLayout.TYPE);
  }

  @Override
  protected ClientComponent.Type getClientComponentType(
    FacesContext context,
    UIComponent  component,
    FacesBean    bean)
  {
    return ClientComponent.Type.SKIP_ALWAYS;
  }

  protected String getClientConstructor()
  {
    return "TestPanelStackedLayout";
  }

  @Override
  protected String getDefaultStyleClass(
    FacesContext context,
    RenderingContext rc,
    FacesBean bean)
  {
    return "af|panelStackedLayout";
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
      return "position:relative;min-width:1px;min-height:1px;";// TODO put this in a skin for the default style class and return null here instead
    }
    else
    {
      // Clean up after ourselves since we don't need this value anymore.
      requestMap.remove(_VISUAL_ROOT_STYLES_KEY);
      return visualRootStylesObject.toString();
    }
  }

/* TODO for future releases:
  @Override
  protected boolean visitChildrenForEncodingImpl(
    UIXComponent  component,
    VisitContext  visitContext,
    VisitCallback visitCallback)
  {
    FacesContext     fc   = FacesContext.getCurrentInstance();
    RenderingContext rc   = RenderingContext.getCurrentInstance();
    FacesBean        bean = component.getFacesBean();
    try
    {
      return _encodeAllOrVisitChildrenForEncodingImpl(fc, rc, component, null, bean, visitContext, visitCallback);
    }
    catch (IOException ioe)
    {
      throw new FacesException(ioe);
    }
  }
*/

  @Override
  protected void encodeAll(
    FacesContext     fc,
    RenderingContext rc,
    UIComponent      component,
    ClientComponent  client,
    FacesBean        bean)
    throws IOException
  {
    _encodeAllOrVisitChildrenForEncodingImpl(fc, rc, (UIXComponent)component, client, bean, null, null);
  }

  /**
   * If visitContext is null, encodes the panelGridLayout, otherwise just visits each child of the given component in proper
   * context (e.g. for stretching).
   * @param fc the FacesContext
   * @param rc the RenderingContext
   * @param component the component to render or visit the children of
   * @param client null or the client component
   * @param bean the FacesBean of the component to render
   * @param visitContext null or the visit context
   * @param visitCallback null or the visit callback
   * @return <code>true</code> if the visit is complete
   * @throws IOException if there is a problem encoding
   */
  private boolean _encodeAllOrVisitChildrenForEncodingImpl(
    FacesContext     fc,
    RenderingContext rc,
    UIXComponent     component,
    ClientComponent  client,
    FacesBean        bean,
    Object/*TODO for future releases: VisitContext*/     visitContext,
    Object/*TODO for future releases: VisitCallback*/    visitCallback)
    throws IOException
  {
    ResponseWriter rw = null;
    if (client != null) // then this is an encodeAll processing, not a visitChildrenForEncodingImpl processing
    {
      rw = fc.getResponseWriter();
    }

    // The following visual root code must occur before renderRootStyleAttributes() is executed
    // since getPrependedInlineStyle() will consume it from the request map.
    StringBuilder visualRootStylesSB = null;
    boolean nextElementToBeStretched = isNextElementToBeStretched(fc);

    String visualRootStyles = null;// TODO for future releases: getVisualRootStretchingStyles(fc, rc, component, client, bean);
    if (visualRootStyles != null)
      visualRootStylesSB = new StringBuilder(visualRootStyles);

    if (visualRootStylesSB != null)
    {
      // We need to store this somewhere so that encodeStretchedChild can be applied when needed!
      Map<String, Object> requestMap =
        fc.getExternalContext().getRequestMap();
      requestMap.put(_VISUAL_ROOT_STYLES_KEY, visualRootStylesSB);
    }

    boolean stretchedByAncestor = (nextElementToBeStretched || visualRootStylesSB != null);

    startElement(rw, "div", component);

    if (client != null) // then this is an encodeAll processing, not a visitChildrenForEncodingImpl processing
    {
      renderId(fc, component);
      renderAllRootAttributes(fc, rc, client, bean);
    }

    EncodingCallbackState encodingCallbackState =
      new EncodingCallbackState(
        rc,
        rw,
        visitContext,
        visitCallback,
        stretchedByAncestor);
    UIXComponent.encodeFlattenedChildren(
      fc,
      _childEncoderCallback,
      component.getChildren(),
      encodingCallbackState);

    endElement(rw, "div");

    if (visitContext != null) // came here from a visitChildrenForEncodingImpl call
    {
      return false;// TODO for future releases: encodingCallbackState.isVisitChildrenForEncodingImplComplete();
    }

    return false;
  }

  private static void startElement(
    ResponseWriter rw,
    String         tagName,
    UIComponent    component)
    throws IOException
  {
    if (rw != null) // this should only be null if during a visitChildrenForEncodingImpl call, not for encodeAll
    {
      rw.startElement(tagName, component);
    }
  }

  private static void endElement(
    ResponseWriter rw,
    String         tagName)
    throws IOException
  {
    if (rw != null) // this should only be null if during a visitChildrenForEncodingImpl call, not for encodeAll
    {
      rw.endElement(tagName);
    }
  }

  public static void writeAttribute(
    ResponseWriter rw,
    String         attributeName,
    Object         attributeValue,
    String         relatedProperty)
    throws IOException
  {
    if (rw != null) // this should only be null if during a visitChildrenForEncodingImpl call, not for encodeAll
    {
      rw.writeAttribute(attributeName, attributeValue, relatedProperty);
    }
  }

  public final class EncodingCallbackState
  {
    public EncodingCallbackState(
      RenderingContext  rc,
      ResponseWriter    rw,
      Object/*TODO for future releases: VisitContext*/      visitContext,
      Object/*TODO for future releases: VisitCallback*/     visitCallback,
      boolean           stretchedByAncestor)
    {
      _rc                  = rc;
      _rw                  = rw;
      _visitContext        = visitContext;
      _visitCallback       = visitCallback;
      _stretchedByAncestor = stretchedByAncestor;
    }

    public RenderingContext getRenderingContext()
    {
      return _rc;
    }

    public ResponseWriter getResponseWriter()
    {
      return _rw;
    }

    public Object/*TODO for future releases: VisitContext*/ getVisitContext()
    {
      return _visitContext;
    }

    public Object/*TODO for future releases: VisitCallback*/ getVisitCallback()
    {
      return _visitCallback;
    }

    public boolean isStretchedByAncestor()
    {
      return _stretchedByAncestor;
    }

    public boolean isVisitIsComplete()
    {
      return _visitChildrenForEncodingImplComplete;
    }

    public void markVisitChildrenForEncodingImplComplete()
    {
      _visitChildrenForEncodingImplComplete = true;
    }

    private final RenderingContext         _rc;
    private final ResponseWriter           _rw;
    private final Object/*TODO for future releases: VisitContext*/             _visitContext;
    private final Object/*TODO for future releases: VisitCallback*/            _visitCallback;
    private final boolean                  _stretchedByAncestor;
    private boolean                        _visitChildrenForEncodingImplComplete;
  }

  public final class ChildEncoderCallback implements ComponentProcessor<EncodingCallbackState>
  {
    public void processComponent(
      FacesContext               fc,
      ComponentProcessingContext processContext,
      UIComponent                child,
      EncodingCallbackState      encodingState)
      throws IOException
    {
      if (encodingState.isVisitIsComplete())
        return;

      ResponseWriter rw = encodingState.getResponseWriter();

      if (encodingState.isStretchedByAncestor())
      {
        // Stretch the child to the boundaries of the root element of the panelStackedLayout:
        encodeStretchedChild(fc, encodingState.getRenderingContext(), child, "0px");
      }
      else
      {
        // Flow the child but anchor it to the top of the root element of the panelStackedLayout:
        startElement(rw, "div", null);
        writeAttribute(rw, "style", "top:0px", null);
        encodeChild(fc, child);
        endElement(rw, "div");
      }
    }
  }

  private final ChildEncoderCallback  _childEncoderCallback  = new ChildEncoderCallback();

  private static final String _VISUAL_ROOT_STYLES_KEY  = "_afrStackRootStyles";
}
