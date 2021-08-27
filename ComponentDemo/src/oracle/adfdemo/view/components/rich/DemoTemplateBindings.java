/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.util.Map;
import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.fragment.UIXPageTemplate;
import oracle.adf.view.rich.component.rich.RichDocument;
import oracle.adf.view.rich.component.rich.RichForm;


public class DemoTemplateBindings
{
  public void setDocumentComponent(RichDocument documentComponent)
  {
    _documentComponent = documentComponent;
    _checkViewRoot();
  }

  public RichDocument getDocumentComponent()
  {
    _checkViewRoot();
    return _documentComponent;
  }

  public void setFormComponent(RichForm formComponent)
  {
    _formComponent = formComponent;
    _checkViewRoot();
  }

  public RichForm getFormComponent()
  {
    _checkViewRoot();
    return _formComponent;
  }

  public boolean isEditingDocument()
  {
    return _isEditing(_documentComponent, null, "editingDocument");
  }

  public boolean isEditingForm()
  {
    return _isEditing(_formComponent, _documentComponent, "editingForm");
  }

  private boolean _isEditing(UIComponent comp, UIComponent altComp, String attrName)
  {
    if (comp == null)
    {
      return false;
    }

    // this is not ideal, but we can use this for the demo.
    // The "attrs" variable in not available during component
    // binding time and during the firing of events, so instead
    // we get the attribute value directly from the parent
    // UIXPageTemplate component.
    //
    // 03/30/2009 mstarets - The "attrs" is actually available during tag execution.
    // We cannot rely on finding a parent with Facelets, since components are connected later.
    // Swicth to EL evaluation instead of looking up page template
    Map<String, Object> attrs = comp.getAttributes();
    Boolean val = (Boolean)attrs.get(attrName);

    if (val == null && altComp != null)
    {
      // Try the alternate component first (needed for the testCaseTemplate of the test project):
      attrs = altComp.getAttributes();
      val = (Boolean)attrs.get(attrName);
    }

    if (val == null)
    {
      /*UIXPageTemplate template = null;
      for (UIComponent parent = comp.getParent();
        parent != null; parent = parent.getParent())
      {
        if (parent instanceof UIXPageTemplate)
        {
          template = (UIXPageTemplate)parent;
          break;
        }
      }

      if (template == null)
      {
        // bug #7500581 - use findComponent to find the template.
        // The document is no longer a child of the template after byg #7450273
        // was fixed
        template = (UIXPageTemplate)FacesContext.getCurrentInstance().getViewRoot().findComponent("dmoTpl");
      }

      if (template != null)
      {
        val = (Boolean)template.getAttributes().get(attrName);
        if (val != null)
        {
          // store the value so we don't have to do this again
          attrs.put(attrName, val);
        }
      }*/

      FacesContext facesContext = FacesContext.getCurrentInstance();
      val = (Boolean)facesContext.getApplication().
        evaluateExpressionGet(facesContext, "#{attrs." + attrName + "}", Boolean.class);
      if (val != null)
      {
        // store the value so we don't have to do this again
        attrs.put(attrName, val);
      }
    }

    return Boolean.TRUE.equals(val);
  }

  private UUID _getViewRootUniqueId()
  {
    UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
    UUID uuid  = (UUID)root.getAttributes().get(_UUID_ATTRIBUTE_KEY);
    if (uuid == null)
    {
      uuid = UUID.randomUUID();
      root.getAttributes().put(_UUID_ATTRIBUTE_KEY, uuid);
    }
    return uuid;
  }

  private void _checkViewRoot()
  {
    UUID uuid = _getViewRootUniqueId();
    if (_documentComponent != null)
    {
      UUID docId = (UUID)_documentComponent.getAttributes().get(_UUID_ATTRIBUTE_KEY);
      if (docId == null)
      {
        _documentComponent.getAttributes().put(_UUID_ATTRIBUTE_KEY, uuid);
      }
      else if (!uuid.equals(docId))
      {
        _documentComponent = null;
      }
    }
    if (_formComponent != null)
    {
      UUID formId = (UUID)_formComponent.getAttributes().get(_UUID_ATTRIBUTE_KEY);
      if (formId == null)
      {
        _formComponent.getAttributes().put(_UUID_ATTRIBUTE_KEY, uuid);
      }
      else if (!uuid.equals(formId))
      {
        _formComponent = null;
      }
    }
  }

  private RichDocument _documentComponent;
  private RichForm _formComponent;

  private final static String _UUID_ATTRIBUTE_KEY = DemoTemplateBindings.class.getName() + "_UUID";
}
