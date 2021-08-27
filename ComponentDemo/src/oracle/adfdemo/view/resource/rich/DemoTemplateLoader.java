/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.resource.rich;

import java.beans.Beans;

import javax.el.ELContext;
import javax.el.ELResolver;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.context.AdfFacesContext;

public class DemoTemplateLoader
{
  public DemoTemplateLoader()
  {
  }
  
  public String getComponentTemplate()
  {
    //if we're using the portlet output mode then we need to use the portal template
    // Fix for bug #8784462 adds the isDesignTime check here.
    if(!Beans.isDesignTime() &&
       (AdfFacesContext.OutputMode.PORTLET.equals(AdfFacesContext.getCurrentInstance().getOutputModeEnum())))
    {
      return "/templates/pageTemplateDefs/tagDemoPortletTemplate.jspx";
    } 
    
    FacesContext context = FacesContext.getCurrentInstance();
    Application app = context.getApplication();
    ELContext elContext = context.getELContext();    
    String exprStr = "#{demoSkin.altaBased}";
    ValueExpression ve = app.getExpressionFactory().createValueExpression(elContext,
                                                   exprStr, Boolean.class);
                                                   
    Boolean isAtlaBasedSkin =  (Boolean)ve.getValue(elContext);
    
    return isAtlaBasedSkin ? "/templates/pageTemplateDefs/tagDemoTemplateAlta.jspx" : 
                             "/templates/pageTemplateDefs/tagDemoTemplate.jspx";
  }

  public String getVisualDesignTemplate()
  {    
    FacesContext context = FacesContext.getCurrentInstance();
    Application app = context.getApplication();
    ELContext elContext = context.getELContext();    
    String exprStr = "#{demoSkin.altaBased}";
    ValueExpression ve = app.getExpressionFactory().createValueExpression(elContext,
                                                   exprStr, Boolean.class);
                                                   
    Boolean isAtlaBasedSkin =  (Boolean)ve.getValue(elContext);
    
    return isAtlaBasedSkin ? "/templates/pageTemplateDefs/panelPageTemplateAlta.jspx" : 
                             "/templates/pageTemplateDefs/panelPageTemplate.jspx";
  }
  
}
