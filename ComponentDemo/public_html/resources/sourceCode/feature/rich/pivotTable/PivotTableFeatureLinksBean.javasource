/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableFeatureLinksBean.java /main/5 2012/07/09 13:46:38 jievans Exp $ */

/* Copyright (c) 2010, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jievans     07/09/12 - Add layer label demo
    jayturne    06/21/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableFeatureLinksBean.java /main/5 2012/07/09 13:46:38 jievans Exp $
 *  @author  jayturne
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotTable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.RichDocument;

import oracle.adfdemo.view.nav.rich.DemoDVTMenuModel;

public class PivotTableFeatureLinksBean
{
    public String getPivotTableTitle()
    {
        return DemoDVTMenuModel.PIVOT_TABLE_TAG_LABEL;
    }
    
    public boolean isPivotTableVisible()
    {
        return isVisible(getPivotTableTitle());
    }
    
    public String getActiveCellStampingPivotTableTitle()
    {
        return DemoDVTMenuModel.ACTIVE_CELL_STAMPING_PIVOT_TABLE_LABEL;
    }
    
    public boolean isActiveCellStampingPivotTableVisible()
    {
        return isVisible(getActiveCellStampingPivotTableTitle());
    }
    
    public String getAdjustablePivotTableTitle()
    {
        return DemoDVTMenuModel.ADJUSTABLE_PIVOT_TABLE_LABEL;
    }
    
    public boolean isAdjustablePivotTableVisible()
    {
        return isVisible(getAdjustablePivotTableTitle());
    }
    
    public String getLayerLabelPivotTableTitle()
    {
        return DemoDVTMenuModel.LAYER_LABEL_PIVOT_TABLE_LABEL;
    }
    
    public boolean isLayerLabelPivotTableVisible()
    {
        return isVisible(getLayerLabelPivotTableTitle());
    }
    
    public String getClientBehaviorPivotTableTitle()
    {
        return DemoDVTMenuModel.CLIENT_BEHAVIOR_PIVOT_TABLE_LABEL;
    }
    
    public boolean isClientBehaviorPivotTableVisible()
    {
        return isVisible(getClientBehaviorPivotTableTitle());
    }
    
    public String getCsvRowsetPivotTableTitle()
    {
        return DemoDVTMenuModel.CSV_ROWSET_PIVOT_TABLE_LABEL;
    }
    
    public boolean isCsvRowsetPivotTableVisible()
    {
        return isVisible(getCsvRowsetPivotTableTitle());
    }
    
    public String getDataBarsPivotTableTitle()
    {
        return DemoDVTMenuModel.DATA_BARS_PIVOT_TABLE_LABEL;
    }
    
    public boolean isDataBarsPivotTableVisible()
    {
        return isVisible(getDataBarsPivotTableTitle());
    }
    
    public String getDrillablePivotTableTitle()
    {
        return DemoDVTMenuModel.DRILLABLE_PIVOT_TABLE_LABEL;
    }
    
    public boolean isDrillablePivotTableVisible()
    {
        return isVisible(getDrillablePivotTableTitle());
    }
    
    public String getEditablePivotTableTitle()
    {
        return DemoDVTMenuModel.EDITABLE_PIVOT_TABLE_LABEL;
    }
    
    public boolean isEditablePivotTableVisible()
    {
        return isVisible(getEditablePivotTableTitle());
    }
    
    public String getExportToExcelPivotTableTitle()
    {
        return DemoDVTMenuModel.EXPORT_TO_EXCEL_PIVOT_TABLE_LABEL;
    }
    
    public boolean isExportToExcelPivotTableVisible()
    {
        return isVisible(getExportToExcelPivotTableTitle());
    }
    
    public String getGaugeStampedPivotTableTitle()
    {
        return DemoDVTMenuModel.GAUGE_STAMPED_PIVOT_TABLE_LABEL;
    }
    
    public boolean isGaugeStampedPivotTableVisible()
    {
        return isVisible(getGaugeStampedPivotTableTitle());
    }
    
    public String getGraphStampedPivotTableTitle()
    {
        return DemoDVTMenuModel.GRAPH_STAMPED_PIVOT_TABLE_LABEL;
    }
    
    public boolean isGraphStampedPivotTableVisible()
    {
        return isVisible(getGraphStampedPivotTableTitle());
    }
    
    public String getHeaderCellStampingPivotTableTitle()
    {
        return DemoDVTMenuModel.HEADER_CELL_STAMPING_PIVOT_TABLE_LABEL;
    }
    
    public boolean isHeaderCellStampingPivotTableVisible()
    {
        return isVisible(getHeaderCellStampingPivotTableTitle());
    }
    
    public String getHeatMapPivotTableTitle()
    {
        return DemoDVTMenuModel.HEAT_MAP_PIVOT_TABLE_LABEL;
    }
    
    public boolean isHeatMapPivotTableVisible()
    {
        return isVisible(getHeatMapPivotTableTitle());
    }
    
    public String getCellFormattingPivotTableTitle()
    {
        return DemoDVTMenuModel.CELL_FORMATTING_PIVOT_TABLE_LABEL;
    }
    
    public boolean isCellFormattingPivotTableVisible()
    {
        return isVisible(getCellFormattingPivotTableTitle());
    }
    
    public String getMemberFormattingPivotTableTitle()
    {
        return DemoDVTMenuModel.MEMBER_FORMATTING_PIVOT_TABLE_LABEL;
    }
    
    public boolean isMemberFormattingPivotTableVisible()
    {
        return isVisible(getMemberFormattingPivotTableTitle());
    }
    
    public String getSelectionPivotTableTitle()
    {
        return DemoDVTMenuModel.SELECTION_PIVOT_TABLE_LABEL;
    }
    
    public boolean isSelectionPivotTableVisible()
    {
        return isVisible(getSelectionPivotTableTitle());
    }
    
    public String getSparkChartStampedPivotTableTitle()
    {
        return DemoDVTMenuModel.SPARK_CHART_STAMPED_PIVOT_TABLE_LABEL;
    }
    
    public boolean isSparkChartStampedPivotTableVisible()
    {
        return isVisible(getSparkChartStampedPivotTableTitle());
    }
    
    public String getTotalsPivotTableTitle()
    {
        return DemoDVTMenuModel.TOTALS_PIVOT_TABLE_LABEL;
    }
    
    public boolean isTotalsPivotTableVisible()
    {
        return isVisible(getTotalsPivotTableTitle());
    }
    
    public String getWhenAvailablePivotTableTitle()
    {
        return DemoDVTMenuModel.WHEN_AVAILABLE_PIVOT_TABLE_LABEL;
    }
    
    public boolean isWhenAvailablePivotTableVisible()
    {
        return isVisible(getWhenAvailablePivotTableTitle());
    }
    
    public String getHeaderCellWrappingPivotTableTitle()
    {
        return DemoDVTMenuModel.HEADER_CELL_WRAPPING_PIVOT_TABLE_LABEL;
    }
    
    public boolean isHeaderCellWrappingPivotTableVisible()
    {
        return isVisible(getHeaderCellWrappingPivotTableTitle());
    }

    public String getClientListenerPivotTableTitle()
    {
        return DemoDVTMenuModel.CLIENT_LISTENER_PIVOT_TABLE_LABEL;
    }
    
    public boolean isClientListenerPivotTableVisible()
    {
        return isVisible(getClientListenerPivotTableTitle());
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
