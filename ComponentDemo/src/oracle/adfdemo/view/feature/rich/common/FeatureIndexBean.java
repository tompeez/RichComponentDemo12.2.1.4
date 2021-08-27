/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/common/FeatureIndexBean.java /main/13 2016/02/05 09:27:40 elinkov Exp $ */

/* Copyright (c) 2010, 2016, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.feature.rich.common;

import java.util.List;

import oracle.adfdemo.view.nav.rich.DemoDVTMenuModel;
import oracle.adfdemo.view.nav.rich.DemoItemNode;

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    psarram     09/29/10 - Creation
 */

/**
 *  Bean used with the feature demo index pages.
 *  
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/common/FeatureIndexBean.java /main/13 2016/02/05 09:27:40 elinkov Exp $
 *  @author  psarram 
 *  @since   release specific (what release of product did this appear in)
 */
public class FeatureIndexBean {
    
    public FeatureIndexBean() {
        
    }
    
    public String getViewType() {
        return _viewType;
    }
    
    public void setViewType(String type) {
        _viewType = type;
    }
    
    public List<DemoItemNode> getGaugeDemoData() {
        return DemoDVTMenuModel.getGaugeDemoList();
    }
    
    public List<DemoItemNode> getLegacyGaugeDemoData() {
        return DemoDVTMenuModel.getLegacyGaugeDemoList();
    }
    
    public List<DemoItemNode> getChartDemoData() {
        return DemoDVTMenuModel.getChartDemoList();
    }
    
    public List<DemoItemNode> getPictoChartDemoData() {
        return DemoDVTMenuModel.getPictoChartDemoList();
    }
  
    public List<DemoItemNode> getGraphDemoData() {
        return DemoDVTMenuModel.getGraphDemoList();
    }
    
    public List<DemoItemNode> getSunburstDemoData() {
        return DemoDVTMenuModel.getSunburstDemoList();
    }
    
    public List<DemoItemNode> getTreemapDemoData() {
        return DemoDVTMenuModel.getTreemapDemoList();
    }
    
    public List<DemoItemNode> getHvDemoData() {
        return DemoDVTMenuModel.getHvDemoList();
    }
    
    public List<DemoItemNode> getGanttDemoData() {
        return DemoDVTMenuModel.getGanttDemoList();
    }
    
    public List<DemoItemNode> getTmapDemoData() {
        return DemoDVTMenuModel.getTmapDemoList();
    }
    
    public List<DemoItemNode> getGeoMapDemoData() {
        return DemoDVTMenuModel.getGeoMapDemoList();
    }
    
    public List<DemoItemNode> getPivotFilterBarDemoData() {
        return DemoDVTMenuModel.getPivotFilterBarDemoList();
    }
    
    public List<DemoItemNode> getPivotTableDemoData() {
        return DemoDVTMenuModel.getPivotTableDemoList();
    }

    public List<DemoItemNode> getTimelineDemoData() {
        return DemoDVTMenuModel.getTimelineDemoList();
    }
    
    public List<DemoItemNode> getDiagramDemoData() {
        return DemoDVTMenuModel.getDiagramDemoList();
    }
    
    public List<DemoItemNode> getNBoxDemoData() {
        return DemoDVTMenuModel.getNBoxDemoList();
    }
    
    public List<DemoItemNode> getTagCloudDemoData() {
        return DemoDVTMenuModel.getTagCloudDemoList();
    }
    
    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getGaugeCategoryData() {
        return DemoDVTMenuModel.getGaugeCategoryList();
    }
    
    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getLegacyGaugeCategoryData() {
        return DemoDVTMenuModel.getLegacyGaugeCategoryList();
    }
  
    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getChartCategoryData() {
        return DemoDVTMenuModel.getChartCategoryList();
    }
  
    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getGraphCategoryData() {
        return DemoDVTMenuModel.getGraphCategoryList();
    }
    
    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getPictoChartCategoryData() {
        return DemoDVTMenuModel.getPictoChartCategoryList();
    }
    
    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getSunburstCategoryData() {
        return DemoDVTMenuModel.getSunburstCategoryList();
    }
    
    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getTreemapCategoryData() {
        return DemoDVTMenuModel.getTreemapCategoryList();
    }
    
    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getHvCategoryData() {
        return DemoDVTMenuModel.getHvCategoryList();
    }
    
    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getTmapCategoryData() {
        return DemoDVTMenuModel.getTmapCategoryList();
    }
    
    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getGeoMapCategoryData() {
        return DemoDVTMenuModel.getGeoMapCategoryList();
    }
    
    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getPivotFilterBarCategoryData() {
        return DemoDVTMenuModel.getPivotFilterBarCategoryList();
    }
    
    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getPivotTableCategoryData() {
        return DemoDVTMenuModel.getPivotTableCategoryList();
    }

    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getGanttCategoryData() {
        return DemoDVTMenuModel.getGanttCategoryList();
    }

    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getTimelineCategoryData() {
        return DemoDVTMenuModel.getTimelineCategoryList();
    }

    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getDiagramCategoryData() {
        return DemoDVTMenuModel.getDiagramCategoryList();
    }
    
    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getNBoxCategoryData() {
        return DemoDVTMenuModel.getNBoxCategoryList();
    }

    public List<DemoDVTMenuModel.DemoDVTCategoryItem> getTagCloudCategoryData() {
        return DemoDVTMenuModel.getTagCloudCategoryList();
    }
    
    private String _viewType = "list";
}

