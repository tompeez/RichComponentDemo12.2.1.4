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

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class TestPanelAddScriptRenderer extends RichRenderer
{
  public TestPanelAddScriptRenderer()
  {
    super(TestPanelAddScript.TYPE);
  }

  protected String getClientConstructor()
  {
    return "TestPanelAddScript";
  }

  protected String getDefaultStyleClass(FacesContext context,
                                        RenderingContext arc,
                                        FacesBean bean)
  {
    return "af|panelAddScript";
  }

  protected void encodeAll(
    FacesContext        context,
    RenderingContext arc,
    UIComponent         component,
    ClientComponent     client,
    FacesBean           bean) throws IOException
  {
    ResponseWriter rw = context.getResponseWriter();

    rw.startElement("div", component);
    renderId(context, component);
    renderAllRootAttributes(context, arc, client, bean);

    String clientId = client.getClientId();
    if (clientId == null)
      clientId = getClientId(context, component);

    // Encode a div with the resize message    
    rw.startElement("div", null);
    rw.writeAttribute("id", createSubId(clientId, "message"), null);
    rw.writeText("AddScript id=", null);
    rw.writeText(clientId, null);
    rw.endElement("div");
    
    rw.endElement("div");

    // Tack on a script using the ExtendedRenderKitService.  The script
    // simply finds the client-side component and displays an alert.
    String script = "var id = '" + clientId + "';"                 +
                    "var comp = AdfPage.PAGE.findComponent(id);" +
                    "alert('findComponent(' + id + '): ' + comp);";

    ExtendedRenderKitService erks = Service.getService(context.getRenderKit(),
                                                       ExtendedRenderKitService.class);  
    erks.addScript(context, script);
  }
}
