/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.custom;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.context.RenderingContext;

import oracle.adf.view.rich.render.ClientComponent;
import oracle.adf.view.rich.render.RichRenderer;

public class TestPanelResizeNotifyRenderer extends RichRenderer
{
  public TestPanelResizeNotifyRenderer()
  {
    super(TestPanelResizeNotify.TYPE);
  }

  protected String getClientConstructor()
  {
    return "TestPanelResizeNotify";
  }

  protected String getDefaultStyleClass(FacesContext context,
                                        RenderingContext arc,
                                        FacesBean bean)
  {
    return "af|panelResizeNotify";
  }

  protected void encodeAll(
    FacesContext     context,
    RenderingContext rc,
    UIComponent      component,
    ClientComponent  client,
    FacesBean        bean)
    throws IOException
  {
    ResponseWriter rw = context.getResponseWriter();

    rw.startElement("div", component);
    renderId(context, component);
    renderAllRootAttributes(context, rc, client, bean);

    String clientId = client.getClientId();
    if (clientId == null)
      clientId = getClientId(context, component);

    // Encode a div with the resize message    
    rw.startElement("div", null);
    rw.writeAttribute("id", createSubId(clientId, "message"), null);

    rw.writeText("Count: 0", null);
    rw.startElement("br", null);
    rw.endElement("br");
    rw.writeText("Old Width: n/a", null);
    rw.startElement("br", null);
    rw.endElement("br");
    rw.writeText("Old Height: n/a", null);
    rw.startElement("br", null);
    rw.endElement("br");
    rw.writeText("New Width: n/a", null);
    rw.startElement("br", null);
    rw.endElement("br");
    rw.writeText("New Height: n/a", null);

    rw.endElement("div");

    // Encode the children
    rw.startElement("div", null);
    encodeAllChildren(context, component);
    UIComponent centerFacet = component.getFacet("center");
    if (centerFacet != null)
    {
      encodeChild(context, centerFacet);
    }
    rw.endElement("div");
    
    rw.endElement("div");
  }
}
