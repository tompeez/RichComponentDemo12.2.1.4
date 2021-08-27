/** Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.mobile;

import java.io.Serializable;

import javax.el.ELContext;

import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.RichDocument;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adfdemo.view.components.rich.DemoComponentSkin;

import org.apache.myfaces.trinidad.component.visit.VisitTreeUtils;
import org.apache.myfaces.trinidad.context.Agent;
import org.apache.myfaces.trinidad.context.RequestContext;


public final class MobileSettingsBean
  implements Serializable
{
  public MobileSettingsBean()
  {
    super();
  }

  public void setUseDeviceWidth(boolean useDeviceWidth)
  {
    if (_useDeviceWidth == null || _useDeviceWidth != useDeviceWidth)
    {
      _useDeviceWidth = useDeviceWidth;
      DemoComponentSkin.reloadThePage();
    }
  }

  public boolean isUseDeviceWidth()
  {
    if (_useDeviceWidth == null)
    {
      _useDeviceWidth = isMobilePlatform();
      _dimensionsFromChildren = _useDeviceWidth;
    }

    return _useDeviceWidth;
  }

  public boolean isSimplifiedPresentation()
  {
    if (_useSimplifiedPresentation == null)
    {
      _useDeviceWidth = isMobilePlatform();
      _useSimplifiedPresentation = _useDeviceWidth;
    }

    return _useSimplifiedPresentation;
  }

  public void setDimensionsFromChildren(boolean dimensionsFromChildren)
  {
    if (_dimensionsFromChildren != dimensionsFromChildren)
    {
      _dimensionsFromChildren = dimensionsFromChildren;

      // Force the PPR of the document so the partial triggers do not need to be added across
      // a declarative component boundary.
      FacesContext facesContext = FacesContext.getCurrentInstance();
      facesContext.getViewRoot().visitTree(
        VisitContext.createVisitContext(facesContext, null,
          VisitTreeUtils.NON_TRANSIENT_RENDERED_HINTS),
        new PprDocumentVisitor());
    }
  }

  public boolean isDimensionsFromChildren()
  {
    return _dimensionsFromChildren;
  }

  public static MobileSettingsBean getInstance(FacesContext facesContext)
  {
    ELContext elContext = facesContext.getELContext();
    return (MobileSettingsBean)facesContext.getApplication()
      .getExpressionFactory().createValueExpression(
                                 elContext, "#{mobileSettings}", MobileSettingsBean.class)
      .getValue(elContext);
  }

  public boolean isMobileTablet()
  {
    // Currently only consider the iPad a tablet, will need to add more (like Android tablets
    // later)
    RequestContext context = RequestContext.getCurrentInstance();
    Agent agent = context.getAgent();

    if ("iphone".equalsIgnoreCase(agent.getPlatformName()))
    {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      String userAgent = facesContext.getExternalContext().getRequestHeaderMap().get("user-agent");
      return (userAgent != null && userAgent.contains("(iPad"));
    }

    return false;
  }

  public boolean isMobilePlatform()
  {
    RequestContext context = RequestContext.getCurrentInstance();
    Agent agent = context.getAgent();

    return
      Agent.TYPE_PDA.equals(agent.getType()) ||
      Agent.TYPE_PHONE.equals(agent.getType()) ||
      (
        Agent.AGENT_WEBKIT.equals(agent.getAgentName()) &&
        (
          "android".equalsIgnoreCase(agent.getPlatformName()) ||
          // iPad/iPhone/iPod touch will come in as a desktop type and an iphone platform:
          "iphone".equalsIgnoreCase(agent.getPlatformName())
        )
      );
  }

  private static class PprDocumentVisitor
    implements VisitCallback
  {
    @Override
    public VisitResult visit(
      VisitContext visitContext,
      UIComponent  target)
    {
      if (RichDocument.COMPONENT_FAMILY.equals(target.getFamily()))
      {
        AdfFacesContext.getCurrentInstance().addPartialTarget(target);
        return VisitResult.COMPLETE;
      }

      return VisitResult.ACCEPT;
    }
  }
  private Boolean _useDeviceWidth;
  private Boolean _useSimplifiedPresentation;
  private boolean _dimensionsFromChildren = false;
}
