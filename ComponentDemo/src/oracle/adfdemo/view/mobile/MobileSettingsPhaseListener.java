/** Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.mobile;

import java.util.Map;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import oracle.adf.view.rich.component.rich.RichDocument;

import org.apache.myfaces.trinidad.component.UIXComponent;
import org.apache.myfaces.trinidad.component.UIXGroup;
import org.apache.myfaces.trinidad.component.html.HtmlMeta;
import org.apache.myfaces.trinidad.component.visit.VisitTreeUtils;


public final class MobileSettingsPhaseListener
  implements PhaseListener
{
  @Override
  public void afterPhase(PhaseEvent event)
  {
  }

  @Override
  public void beforePhase(PhaseEvent event)
  {
    UIViewRoot viewRoot = event.getFacesContext().getViewRoot();
    Map<String, Object> viewRootAttrs = viewRoot.getAttributes();

    if (!viewRootAttrs.containsKey(_VIEW_ROOT_ATTR_KEY))
    {
      viewRoot.addPhaseListener(new AfterViewPopulatedPhaseListener());
      viewRootAttrs.put(_VIEW_ROOT_ATTR_KEY, Boolean.TRUE);
    }
  }

  @Override
  public PhaseId getPhaseId()
  {
    return PhaseId.RENDER_RESPONSE;
  }

  private static final class AfterViewPopulatedPhaseListener
    implements PhaseListener
  {
    @Override
    public void afterPhase(PhaseEvent event)
    {
    }

    @Override
    public void beforePhase(PhaseEvent event)
    {
      FacesContext facesContext = event.getFacesContext();
      MobileSettingsBean mobileSettings = MobileSettingsBean.getInstance(facesContext);

      Map<String, Object> viewAttrs = facesContext.getViewRoot().getAttributes();
      Boolean viewValue = (Boolean)viewAttrs.get(_USE_DEVICE_WIDTH);

      boolean useDeviceWidth = mobileSettings.isUseDeviceWidth();
      if (viewValue == null || viewValue.booleanValue() != useDeviceWidth)
      {
        viewAttrs.put(_USE_DEVICE_WIDTH, useDeviceWidth);

        // Add or remove the meta-component from the document metaContainer facet:
        facesContext.getViewRoot().visitTree(
          VisitContext.createVisitContext(facesContext, null,
            VisitTreeUtils.NON_TRANSIENT_RENDERED_HINTS),
          new MetaContainerVisitor(useDeviceWidth));
      }
    }

    @Override
    public PhaseId getPhaseId()
    {
      return PhaseId.RENDER_RESPONSE;
    }

    private static final long serialVersionUID = 0L;
    private static final String _USE_DEVICE_WIDTH =
      AfterViewPopulatedPhaseListener.class.getName() + ".USE_DEVICE_WIDTH";
  }

  private static class MetaContainerVisitor
    implements VisitCallback
  {
    private MetaContainerVisitor(boolean useDeviceWidth)
    {
      _useDeviceWidth = useDeviceWidth;
    }

    @Override
    public VisitResult visit(
      VisitContext visitContext,
      UIComponent  target)
    {
      if (RichDocument.COMPONENT_FAMILY.equals(target.getFamily()))
      {
        FacesContext facesContext = visitContext.getFacesContext();
        UIComponent metaContainer = target.getFacet(RichDocument.META_CONTAINER_FACET);

        if (metaContainer == null && _useDeviceWidth)
        {
          Application app = facesContext.getApplication();
          _metaFacet = app.createComponent(UIXGroup.COMPONENT_TYPE);

          target.getFacets().put(RichDocument.META_CONTAINER_FACET, _metaFacet);

          _addMetaComponent(facesContext, _metaFacet);

          return VisitResult.COMPLETE;
        }
        else if (_useDeviceWidth && !UIXGroup.COMPONENT_TYPE.equals(metaContainer.getFamily()))
        {
          Application app = facesContext.getApplication();
          _metaFacet = app.createComponent(UIXGroup.COMPONENT_TYPE);

          _metaFacet.getChildren().add(metaContainer);
          target.getFacets().put(RichDocument.META_CONTAINER_FACET, _metaFacet);

          _addMetaComponent(facesContext, _metaFacet);

          return VisitResult.COMPLETE;
        }
        else if (_useDeviceWidth)
        {
          _metaFacet = metaContainer;
        }
        else if (!_useDeviceWidth)
        {
          // Nothing to do, there is no meta component to update
          return VisitResult.COMPLETE;
        }
      }
      else if (target == _metaFacet)
      {
        if (!UIXComponent.visitChildren(visitContext, target, new MetaVisitor(_useDeviceWidth)))
        {
          _addMetaComponent(visitContext.getFacesContext(), target);
        }

        return VisitResult.COMPLETE;
      }

      return VisitResult.ACCEPT;
    }

    private void _addMetaComponent(
      FacesContext facesContext,
      UIComponent metaContainer)
    {
      Application app = facesContext.getApplication();
      HtmlMeta meta = (HtmlMeta)app.createComponent(HtmlMeta.COMPONENT_TYPE);

      meta.setName(_VIEWPORT_META_NAME);
      meta.setContent(_VIEWPORT_DEVICE_WIDTH);

      metaContainer.getChildren().add(meta);
    }

    private UIComponent _metaFacet;
    private final boolean _useDeviceWidth;
  }

  private static class MetaVisitor
    implements VisitCallback
  {
    private MetaVisitor(boolean useDeviceWidth)
    {
      _useDeviceWidth = useDeviceWidth;
    }

    @Override
    public VisitResult visit(
      VisitContext visitContext,
      UIComponent  target)
    {
      if (target instanceof HtmlMeta)
      {
        HtmlMeta meta = (HtmlMeta)target;

        if (_VIEWPORT_META_NAME.equals(meta.getName()))
        {
          String value = meta.getContent();

          if (_useDeviceWidth)
          {
            meta.setContent(_VIEWPORT_DEVICE_WIDTH);
          }
          else if (value != null)
          {
            meta.getParent().getChildren().remove(meta);
          }

          return VisitResult.COMPLETE;
        }
      }

      return VisitResult.ACCEPT;
    }

    private final boolean _useDeviceWidth;
  }

  private static final String _VIEWPORT_META_NAME = "viewport";
  private static final String _VIEWPORT_DEVICE_WIDTH = "initial-scale=1.0";
  private static final String _VIEW_ROOT_ATTR_KEY = MobileSettingsPhaseListener.class.getName();
}
