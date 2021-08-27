/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/PivotFilterBarFeatureLinksBean.java /main/1 2010/06/24 08:52:58 jayturne Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jayturne    06/23/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/PivotFilterBarFeatureLinksBean.java /main/1 2010/06/24 08:52:58 jayturne Exp $
 *  @author  jayturne
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotFilterBar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.RichDocument;

import oracle.adfdemo.view.nav.rich.DemoDVTMenuModel;

public class PivotFilterBarFeatureLinksBean
{
    public String getPivotFilterBarTitle()
    {
        return DemoDVTMenuModel.PIVOT_FILTER_BAR_TAG_LABEL;
    }
    
    public boolean isPivotFilterBarVisible()
    {
        return isVisible(getPivotFilterBarTitle());
    }
    
    public String getClientBehaviorPivotFilterBarTitle()
    {
        return DemoDVTMenuModel.CLIENT_BEHAVIOR_PIVOT_FILTER_BAR_LABEL;
    }
    
    public boolean isClientBehaviorPivotFilterBarVisible()
    {
        return isVisible(getClientBehaviorPivotFilterBarTitle());
    }
    
    public String getContentDeliveryPivotFilterBarTitle()
    {
        return DemoDVTMenuModel.CONTENT_DELIVERY_PIVOT_FILTER_BAR_LABEL;
    }
    
    public boolean isContentDeliveryPivotFilterBarVisible()
    {
        return isVisible(getContentDeliveryPivotFilterBarTitle());
    }
    
    public String getGraphAndPivotTablePivotFilterBarTitle()
    {
        return DemoDVTMenuModel.GRAPH_AND_PIVOT_TABLE_PIVOT_FILTER_BAR_LABEL;
    }
    
    public boolean isGraphAndPivotTablePivotFilterBarVisible()
    {
        return isVisible(getGraphAndPivotTablePivotFilterBarTitle());
    }
    
    public String getModelNamePivotFilterBarTitle()
    {
        return DemoDVTMenuModel.MODEL_NAME_PIVOT_FILTER_BAR_LABEL;
    }
    
    public boolean isModelNamePivotFilterBarVisible()
    {
        return isVisible(getModelNamePivotFilterBarTitle());
    }
    
    private boolean isVisible(String title)
    {
        UIComponent viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        if (viewRoot != null && viewRoot.getChildCount() == 1)
        {
            UIComponent child = viewRoot.getChildren().get(0);
            if (child instanceof RichDocument)
            {
                String value = ((RichDocument)child).getTitle();
                if(value != null && value.equals(title))
                    return false;
            }
        }
        return true;
    }
}