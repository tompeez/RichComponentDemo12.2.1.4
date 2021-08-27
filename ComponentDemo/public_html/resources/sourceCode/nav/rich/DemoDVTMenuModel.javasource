package oracle.adfdemo.view.nav.rich;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/nav/rich/DemoDVTMenuModel.java /main/212 2016/08/16 11:53:11 breliu Exp $ */

/* Copyright (c) 2009, 2016, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    amdai       10/23/13 - XbranchMerge amdai_131021_tmap_mapprovider from main
    ckadima     07/31/13 - HV Tilt Panning Demo is removed as tilt panning feature is deprecated.
    bglazer     12/12/12 - fix for bug 14532388
    bglazer     10/12/12 - added Diagram stacking demo
    jievans     07/09/12 - Add pivot table layer label demo
    bglazer     05/30/12 - fixed typo
    kobradov    05/29/12 - Added a new Database Schema Diagram demo
    lmolesky    05/23/12 - add demo pages for diagram node parts
    bglazer     03/30/12 - added demo pages for bus layout and container
                           padding
    lmolesky    03/07/12 - fix diagram editor reference
    lmolesky    03/05/12 - Add diagrammer demos
    bglazer     10/14/11 - merge after refresh
    bglazer     10/12/11 - added hvDragAndDrop.jspx
    amdai       04/07/11 - added large icons to tag guide demos
    bglazer     11/05/10 - demo page for HV panel card transition effects
    mguirgui    10/19/10 - to sort Tag Guide List
    bglazer     09/08/10 - added hvTiltPanning.jspx
    imohamma    04/08/10 - projectGantt export to excel
    ccchow      04/05/10 - add pt client behavior
    ccchow      03/17/10 - add active cell key page
    bglazer     02/22/10 - HV BiDi node content demo page
    kiancu      01/06/10 - added gant autoppr
    kiancu      12/09/09 - add gantt whenAvailable
    jievans     11/17/09 - add PT whenAvailable demo
    asghosh     10/12/09 - add gauge demos
    hbroek      07/01/09 - More Shepherd sample updates
    jayturne    06/22/09 - Adding pivotFilterBar Samples.
    mytang      06/01/09 - move geoMapMultipleThemes.jspx to dvtTagGuide
    teclee      03/20/09 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/nav/rich/DemoDVTMenuModel.java /main/212 2016/08/16 11:53:11 breliu Exp $
 *  @author  teclee
 *  @since   release specific (what release of product did this appear in)
 */
public class DemoDVTMenuModel {
    
    /**
     * New inner class DemoDVTCategoryItem for categories in feature index pages
     */
    public static class DemoDVTCategoryItem implements Comparable
    {
        private List<DemoItemNode> _list;
        private String _name;
        private Integer _priority;
        
        public DemoDVTCategoryItem(String name, Integer priority){
            _name = name;
            _list = new ArrayList<DemoItemNode>();
            _priority = priority;
        }
        
        /**
         * Add demo page to the category
         */
        public void add(DemoItemNode item){
            _list.add(item);
        }
        
        /**
         * Retrieves the list of demo pages
         */
        public List<DemoItemNode> getList(){
            return _list;
        }
        
        /**
         * Retrieves the name of this category
         */
        public String getName(){
            return _name;
        }
        
         /**
          * Retrieves the priority of the category, the smaller number, the higher priority
          */
        public Integer getPriority(){
            return _priority;
        }
         
        /**
         * Compares the priority, and then compares the name
         */
        @Override
        public int compareTo(Object o) {
            int comparePriority = ((DemoDVTCategoryItem)o).getPriority();
            if ( comparePriority == getPriority() ){
                String compareName = ((DemoDVTCategoryItem)o).getName();
                return getName().compareTo(compareName);
            }
            return getPriority().compareTo(((DemoDVTCategoryItem)o).getPriority()) ;
        }
        
        /**
         * Creates a list of categories by feeding the raw demo list
         */
        public static List<DemoDVTCategoryItem> createCategories(List<DemoItemNode> demos){
            
            // Un-sorted Map
            Map<String, DemoDVTCategoryItem> map = new HashMap<String, DemoDVTCategoryItem>();        
            for (DemoItemNode demo:demos){
                String categoryName = demo.getCategory();
                DemoDVTCategoryItem category = map.get(categoryName);
                
                // Find if there is a present priority
                Integer priority = categoryOrderMap.get(categoryName);
                if ( priority == null ){
                    priority = 0;
                }
                
                // Create new category
                if ( category == null ){
                    category = new DemoDVTCategoryItem(categoryName, priority);
                }
                category.add(demo);
                map.put(categoryName, category);
            }
            
            // List
            Iterator<String> itMap = map.keySet().iterator();
            List<DemoDVTCategoryItem> list = new ArrayList<DemoDVTCategoryItem>(20);
            while (itMap.hasNext()){
                list.add(map.get(itMap.next()));
            }
            
            Collections.sort(list);
            return list;
        }
        
        /**
         * The smaller the number, the higher the priority
         */
        private static Map<String, Integer> categoryOrderMap = new HashMap<String, Integer>(){
            {
                // A list of pre-defined categories
                put(DemoDVTMenuModel.GENERAL_FEATURES,-3);
                put(DemoDVTMenuModel.INTERACTIVITY_FEATURES,-2);
                put(DemoDVTMenuModel.ADVANCED_FEATURES,-1);
                put(DemoDVTMenuModel.STAMPING_FEATURES,0);
            }
        };
    }
    
    
    /*
     * The contents of tagGuideList and featureDemoList will be picked up by
     * the ADF demos
     */

    static List<DemoItemNode> mapList = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Map", "/components/map.jspx", "/adf/geographic_map.png",
                                 "guide.geoMapMultipleThemes", null, null, null, null, "/images/icons-large/map.png",
                                 null, false));
            add(new DemoDVTItemNode("MapBarGraphTheme", "/components/mapBarGraphTheme.jspx", "/adf/geographic_map.png",
                                 "guide.geoMapBarTheme", null, null, null, null,
                                 "/images/icons-large/mapBarGraphTheme.png", null, false));
            add(new DemoDVTItemNode("MapColorTheme", "/components/mapColorTheme.jspx", "/adf/geographic_map.png",
                                 "guide.geoMapColorTheme", null, null, null, null,
                                 "/images/icons-large/mapColorTheme.png", null, false));
            add(new DemoDVTItemNode("MapPieGraphTheme", "/components/mapPieGraphTheme.jspx", "/adf/geographic_map.png",
                                 "guide.geoMapPieTheme", null, null, null, null,
                                 "/images/icons-large/mapPieGraphTheme.png", null, false));
            add(new DemoDVTItemNode("MapPointTheme", "/components/mapPointTheme.jspx", "/adf/geographic_map.png",
                                 "guide.geoMapPointTheme", null, null, null, null,
                                 "/images/icons-large/mapPointTheme.png", null, false));
        }
    };

    static List<DemoItemNode> tagGuideLegacyList = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("AreaGraph", "/components/legacy/areaGraph.jspx", "/adf/area_graph.png", "guide.areaGraph",
                                 null, null, null, null, "/images/icons-large/areaGraph.png", null, false));
            add(new DemoDVTItemNode("BarGraph", "/components/legacy/barGraph.jspx", "/adf/bar_graph.png", "guide.barGraph", null,
                                 null, null, null, "/images/icons-large/barGraph.png", null, false));
            add(new DemoDVTItemNode("BubbleGraph", "/components/legacy/bubbleGraph.jspx", "/adf/bubble_graph.png",
                                 "guide.bubbleGraph", null, null, null, null, "/images/icons-large/bubbleGraph.png",
                                 null, false));
            add(new DemoDVTItemNode("ComboGraph", "/components/legacy/comboGraph.jspx", "/adf/combo_graph.png",
                                 "guide.comboGraph", null, null, null, null, "/images/icons-large/comboGraph.png",
                                 null, false));
            add(new DemoDVTItemNode("FunnelGraph", "/components/funnelGraph.jspx", "/adf/funnel_graph.png",
                                 "guide.funnelGraph", null, null, null, null, "/images/icons-large/funnelGraph.png",
                                 null, false));
            add(new DemoDVTItemNode("HorizontalBarGraph", "/components/legacy/horizontalBarGraph.jspx",
                                 "/adf/horizontal_bar_graph.png", "guide.horizontalBarGraph", null, null, null, null,
                                 "/images/icons-large/horizontalBarGraph.png", null, false));
            add(new DemoDVTItemNode("LineGraph", "/components/legacy/lineGraph.jspx", "/adf/line_graph.png", "guide.lineGraph",
                                 null, null, null, null, "/images/icons-large/lineGraph.png", null, false));
            add(new DemoDVTItemNode("ParetoGraph", "/components/legacy/paretoGraph.jspx", "/adf/pareto_graph.png",
                                 "guide.paretoGraph", null, null, null, null, "/images/icons-large/paretoGraph.png",
                                 null, false));
            add(new DemoDVTItemNode("PieGraph", "/components/legacy/pieGraph.jspx", "/adf/pie_graph.png", "guide.pieGraph", null,
                                 null, null, null, "/images/icons-large/pieGraph.png", null, false));
            add(new DemoDVTItemNode("RadarGraph", "/components/legacy/radarGraph.jspx", "/adf/radar_graph.png",
                                 "guide.radarGraph", null, null, null, null, "/images/icons-large/radarGraph.png",
                                 null, false));
            add(new DemoDVTItemNode("StockGraph", "/components/legacy/stockGraph.jspx", "/adf/stock_graph.png",
                                 "guide.stockGraph", null, null, null, null, "/images/icons-large/stockGraph.png",
                                 null, false));
            add(new DemoDVTItemNode("Time Selector", "/feature/graph/timeSelector.jspx", "/adf/advanced_graph.png",
                                 "feature.graphTimeSelector", _TIME_SELECTOR_DESC, null, null, null,
                                 "/images/icons-large/timeSelector.png", null, false));
            add(new DemoDVTItemNode("Gauge", "/components/legacy/gauge.jspx", "/adf/gauge.png", "guide.gauge", null,
                                 null, null, null, "/images/icons-large/gauge.png", null, false));
        }
    };

    static List<DemoItemNode> tagGuideList = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("AreaChart", "/components/areaChart.jspx", "/adf/area_graph.png", "guide.areaChart",
                                 null, null, null, null, "/images/icons-large/areaGraph.png", null, false));
            add(new DemoDVTItemNode("BarChart", "/components/barChart.jspx", "/adf/bar_graph.png", "guide.barChart", null,
                                 null, null, null, "/images/icons-large/barGraph.png", null, false));
            add(new DemoDVTItemNode("BubbleChart", "/components/bubbleChart.jspx", "/adf/bubble_graph.png",
                                 "guide.bubbleChart", null, null, null, null, "/images/icons-large/bubbleGraph.png",
                                 null, false));
            add(new DemoDVTItemNode("ComboChart", "/components/comboChart.jspx", "/adf/combo_graph.png",
                                 "guide.comboChart", null, null, null, null, "/images/icons-large/comboGraph.png",
                                 null, false));
            add(new DemoDVTItemNode("FunnelChart", "/components/funnelChart.jspx", "/adf/funnel_graph.png",
                                 "guide.funnelChart", null, null, null, null, "/images/icons-large/funnelChart.png",
                                 null, false));
            add(new DemoDVTItemNode("HorizontalBarChart", "/components/horizontalBarChart.jspx",
                                 "/adf/horizontal_bar_graph.png", "guide.horizontalBarChart", null, null, null, null,
                                 "/images/icons-large/horizontalBarGraph.png", null, false));
            add(new DemoDVTItemNode("LineChart", "/components/lineChart.jspx", "/adf/line_graph.png", "guide.lineChart",
                                 null, null, null, null, "/images/icons-large/lineGraph.png", null, false));
            add(new DemoDVTItemNode("PieChart", "/components/pieChart.jspx", "/adf/pie_graph.png", "guide.pieChart", null,
                                 null, null, null, "/images/icons-large/pieGraph.png", null, false));
            add(new DemoDVTItemNode("PictoChart", "/components/pictoChart.jspx", "/adf/pictochart.png", "guide.pictoChart", null,
                               null, null, null, "/images/icons-large/pictoChart.png", null, false));
            add(new DemoDVTItemNode("ScatterChart", "/components/scatterChart.jspx", "/adf/scatter_graph.png",
                                 "guide.scatterChart", null, null, null, null, "/images/icons-large/scatterGraph.png",
                                 null, false));
            add(new DemoDVTItemNode("SparkChart", "/components/sparkChart.jspx", "/adf/line_graph.png",
                                 "guide.sparkChart", null, null, null, null, "/images/icons-large/sparkChart.png",
                                 null, false));
            add(new DemoDVTItemNode("StockChart", "/components/stockChart.jspx", "/adf/stock_graph.png",
                                 "guide.stockChart", null, null, null, null, "/images/icons-large/stockGraph.png",
                                 null, false));
            add(new DemoDVTItemNode("DialGauge", "/components/dialGauge.jspx", "/adf/gauge.png", "guide.dialGauge", null,
                                 null, null, null, "/images/icons-large/gauge.png", null, false));
            add(new DemoDVTItemNode("LEDGauge", "/components/ledGauge.jspx", "/adf/gauge.png", "guide.ledGauge", null,
                                 null, null, null, "/images/icons-large/gauge.png", null, false));
            add(new DemoDVTItemNode("RatingGauge", "/components/ratingGauge.jspx", "/adf/gauge.png", "guide.ratingGauge", null,
                                 null, null, null, "/images/icons-large/gauge.png", null, false));
            add(new DemoDVTItemNode("StatusMeterGauge (Circular)", "/components/circularStatusMeterGauge.jspx", "/adf/gauge.png", "guide.circularStatusMeterGauge", null,
                                 null, null, null, "/images/icons-large/gauge.png", null, false));
            add(new DemoDVTItemNode("StatusMeterGauge (Horizontal)", "/components/horizontalStatusMeterGauge.jspx", "/adf/gauge.png", "guide.horizontalStatusMeterGauge", null,
                                 null, null, null, "/images/icons-large/gauge.png", null, false));
            add(new DemoDVTItemNode("StatusMeterGauge (Vertical)", "/components/verticalStatusMeterGauge.jspx", "/adf/gauge.png", "guide.verticalStatusMeterGauge", null,
                                 null, null, null, "/images/icons-large/gauge.png", null, false));
            add(new DemoDVTItemNode("Sunburst", "/components/sunburst.jspx", "/resources/images/icons/sunburst.png",
                                 "guide.sunburst", null, null, null, null, "/images/icons-large/gauge.png",
                                 null, false));
            add(new DemoDVTItemNode("Treemap", "/components/treemap.jspx", "/resources/images/icons/treemap.png",
                                 "guide.treemap", null, null, null, null, "/images/icons-large/gauge.png",
                                 null, false));
            add(new DemoDVTItemNode(PIVOT_FILTER_BAR_TAG_LABEL, "/components/pivotFilterBar.jspx",
                                 "/adf/pivotfilterbar.png", "guide.pivotFilterBar", null, null, null, null,
                                 "/images/icons-large/pivotFilterBar.png", null, false));
            add(new DemoDVTItemNode(PIVOT_TABLE_TAG_LABEL, "/components/pivotTable.jspx", "/adf/pivot_table.png",
                                 "guide.pivotTable", null, null, null, null, "/images/icons-large/pivotTable.png",
                                 null, false));
            add(new DemoDVTItemNode("Map", "/images/folder.png", mapList));
            add(new DemoDVTItemNode(PROJECT_GANTT_TAG_LABEL, "/components/projectGantt.jspx", "/adf/gantt.png",
                                 "guide.projectGantt", null, null, null, null, "/images/icons-large/projectGantt.png",
                                 null, false));
            add(new DemoDVTItemNode(RESOURCE_GANTT_TAG_LABEL, "/components/resourceUtilizationGantt.jspx",
                                 "/adf/gantt.png", "guide.resourceUtilizationGantt", null, null, null, null,
                                 "/images/icons-large/projectGantt.png", null, false));
            add(new DemoDVTItemNode(SCHEDULING_GANTT_TAG_LABEL, "/components/schedulingGantt.jspx", "/adf/gantt.png",
                                 "guide.schedulingGantt", null, null, null, null,
                                 "/images/icons-large/schedulingGantt.png", null, false));
            add(new DemoDVTItemNode("HierarchyViewer", "/components/hierarchyViewer.jspx", "/adf/xmlSchema.png",
                                 "guide.hierarchyViewer", null, null, null, null,
                                 "/images/icons-large/hierarchyViewer.png", null, false));
            add(new DemoDVTItemNode("ThematicMap", "/components/thematicMap.jspx", "/adf/thematicmap.png",
                                 "guide.thematicMap", null, null, null, null, "/images/icons-large/thematicMap.png",
                                 null, false));
            add(new DemoDVTItemNode("Timeline", "/components/timeline.jspx", "/adf/timeline.png",
                                 "guide.timeline", null, null, null, null, null, null, false));
            add(new DemoDVTItemNode("TagCloud", "/components/tagCloud.jspx", "/resources/images/icons/tagCloud.png",
                                 "guide.tagCloud", null, null, null, null, null, null, false));
            add(new DemoDVTItemNode("Diagram", "/components/diagram.jspx", "/adf/xmlSchema.png",
                                 "guide.diagram", null, null, null, null, "/images/icons-large/hierarchyViewer.png",
                                 null, false));
            add(new DemoDVTItemNode("NBox", "/components/nBox.jspx", "/resources/images/icons/nBox.png",
                                 "guide.nBox", null, null, null, null, null,
                                 null, false));
            add(new DemoDVTItemNode("Legacy", "/images/folder.png", tagGuideLegacyList));
        }
    };

    // Chart feature demo list
    static List<DemoItemNode> chartDemoList = new ArrayList<DemoItemNode>() {
    {
        // Top of the list
        add(new DemoDVTItemNode("Chart Gallery", "/feature/chart/gallery.jspx", "/adf/advanced_graph.png",
                             "feature.chartGallery", _CHART_GALLERY_DESC, GENERAL_FEATURES, "Chart", ""));
        
        // Overview of new features
        add(new DemoDVTItemNode("Chart vs Graph",  "/feature/chart/chartEnhancements.jspx",
                           "/adf/advanced_graph.png", "feature.chartEnhancements", _CHART_VS_GRAPH_DESC,
                           GENERAL_FEATURES, "Chart", ""));
        
        // Others are alphabetical;
        add(new DemoDVTItemNode("Layout", "/feature/chart/layout.jspx", "/adf/advanced_graph.png",
                             "feature.chartLayout", _CHART_SMALL_DESC, GENERAL_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Action Events", "/feature/chart/actionEvents.jspx", "/adf/advanced_graph.png",
                             "feature.chartActionEvents", _CHART_ACTION_DESC, INTERACTIVITY_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Categorical Axis Styling", "/feature/chart/categoricalAxisStyling.jspx",
                             "/adf/advanced_graph.png", "feature.chartCategoricalAxisStyling",
                             _CHART_CATEGORICAL_AXIS_STYLING_DESC, GENERAL_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Data Cursor", "/feature/chart/dataCursor.jspx",
                             "/adf/advanced_graph.png", "feature.chartDataCursor",
                             _CHART_DATA_CURSOR_DESC, INTERACTIVITY_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Data Labels", "/feature/chart/dataLabels.jspx",
                             "/adf/advanced_graph.png", "feature.chartDataLabels", _CHART_DATA_LABELS_DESC,
                             GENERAL_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Drilling", "/feature/chart/drilling.jspx", "/adf/advanced_graph.png",
                             "feature.chartDrilling", _CHART_DRILLING_DESC, GENERAL_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Hide And Show Series", "/feature/chart/hideAndShow.jspx",
                             "/adf/advanced_graph.png", "feature.chartHideAndShow", _CHART_HIDE_SHOW_SERIES_DESC,
                             INTERACTIVITY_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Hierarchical Labeling", "/feature/chart/hierarchicalLabeling.jspx",
                             "/adf/advanced_graph.png", "feature.chartHierarchicalLabeling",
                             _CHART_HIERARCHICAL_LABELING_DESC, GENERAL_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Logarithmic Scale", "/feature/chart/logScale.jspx", "/adf/advanced_graph.png",
                             "feature.chartLogScale", _CHART_LOG_SCALE_DESC, GENERAL_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Number Formatting", "/feature/chart/numberFormat.jspx", "/adf/advanced_graph.png",
                             "feature.chartNumberFormat", _CHART_NUMBER_FORMAT_DESC, GENERAL_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Pie Charts", "/feature/chart/pieCharts.jspx",
                             "/adf/advanced_graph.png", "feature.chartPieCharts",
                             _PIE_CHARTS_DESC, GENERAL_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Reference Objects", "/feature/chart/referenceObjects.jspx",
                             "/adf/advanced_graph.png", "feature.chartReferenceObjects",
                             _CHART_REFERENCE_OBJECTS_DESC, GENERAL_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Scrollable Legend", "/feature/chart/scrollableLegend.jspx",
                             "/adf/advanced_graph.png", "feature.chartScrollableLegend", _CHART_SCROLLABLE_LEGEND_DESC,
                             INTERACTIVITY_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Selection", "/feature/chart/selection.jspx", "/adf/advanced_graph.png",
                             "feature.chartSelection", _CHART_SELECTION_DESC, INTERACTIVITY_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Series Effect", "/feature/chart/seriesEffect.jspx",
                             "/adf/advanced_graph.png", "feature.chartSeriesEffect",
                             _CHART_SERIES_EFFECT_DESC, GENERAL_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Spark Charts", "/feature/chart/sparkCharts.jspx", "/adf/advanced_graph.png",
                             "feature.graphSparkcharts", _SPARK_CHARTS_DESC, GENERAL_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Style Customization", "/feature/chart/styleCustomization.jspx",
                             "/adf/advanced_graph.png", "feature.chartStyleCustomization",
                             _CHART_STYLE_CUSTOMIZATION_DESC, GENERAL_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Time Axis", "/feature/chart/timeAxis.jspx", "/adf/advanced_graph.png",
                             "feature.chartTimeAxis", _CHART_TIME_AXIS_DESC, GENERAL_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Tooltip Formatting", "/feature/chart/tooltipFormat.jspx", "/adf/advanced_graph.png",
                             "feature.chartTooltipFormat", _CHART_TOOLTIP_FORMATTING_DESC, GENERAL_FEATURES, "Chart", ""));
        add(new DemoDVTItemNode("Zoom and Scroll", "/feature/chart/zoomAndScroll.jspx", "/adf/advanced_graph.png",
                             "feature.chartZoomAndScroll", _CHART_ZOOM_AND_SCROLL_DESC, INTERACTIVITY_FEATURES, "Chart", ""));
    }
  };
    
  // Chart feature demo list
  static List<DemoItemNode> pictoChartDemoList = new ArrayList<DemoItemNode>() {
    {
        // Top of the list
        add(new DemoDVTItemNode("Picto Chart Gallery", "/feature/pictoChart/pictoChart.jspx", "/adf/pictochart.png",
                             "feature.pictoChartGallery", "Picto Chart Gallery", GENERAL_FEATURES, "Picto Chart", ""));
        
        add(new DemoDVTItemNode("Picto Chart Drill and Selection Listeners", "/feature/pictoChart/drill.jspx", "/adf/picto-chart.png",
                             "feature.pictoChartDrill", "Picto Chart Drill and Selection Listeners", "", "Picto Chart", ""));
      
    }
  };
  
    // Graph feature demo list
    static List<DemoItemNode> graphDemoList = new ArrayList<DemoItemNode>() {
        {
            
            // Top of the list
            add(new DemoDVTItemNode("Graph Index", "/feature/graph/index.jspx", "/adf/advanced_graph.png",
                                 "feature.graphIndex", null, null, null, null, "/images/icons-large/barGraph.png",
                                 null, false));
            
            add(new DemoDVTItemNode("Graph Gallery", "/feature/graph/graphGallery.jspx", "/adf/advanced_graph.png",
                                 "feature.graphGallery", _GRAPH_GALLERY_DESC, GENERAL_FEATURES, "Graph", ""));
            
            
            // Others are alphabetical
            add(new DemoDVTItemNode("Alert", "/feature/graph/alert.jspx", "/adf/advanced_graph.png", "feature.graphAlert",
                                 _GRAPH_ALERT_DESC, ADVANCED_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/AlertBean.javasource"));
            add(new DemoDVTItemNode("Animation for Data Objects", "/feature/graph/animation.jspx",
                                 "/adf/advanced_graph.png", "feature.graphAnimation", _GRAPH_ANIMATION_DESC,
                                 GENERAL_FEATURES, "Graph", ""));
            add(new DemoDVTItemNode("Animations", "/feature/graph/animation.jspx", "/adf/advanced_graph.png",
                                 "feature.graphAnimation2", _GRAPH_ANIMATION2_DESC, GENERAL_FEATURES, "Graph", ""));
            add(new DemoDVTItemNode("Annotations", "/feature/graph/annotations.jspx", "/adf/advanced_graph.png",
                                 "feature.graphAnnotations", _ANNOTATIONS_DESC, ADVANCED_FEATURES, "Graph", ""));
            add(new DemoDVTItemNode("BIDI", "/feature/graph/graphBIDI.jspx", "/adf/advanced_graph.png",
                                 "feature.graphBIDI", _BIDI_DESC, ADVANCED_FEATURES, "Graph", ""));
            add(new DemoDVTItemNode("Click Listener and Shape Attributes", "/feature/graph/clickListener.jspx",
                                 "/adf/advanced_graph.png", "feature.graphClickListener", _GRAPH_CLICK_DESC,
                                 INTERACTIVITY_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/ClickListener.javasource"));
            add(new DemoDVTItemNode("Conditional Formatting", "/feature/graph/conditionalFormatting.jspx",
                                 "/adf/advanced_graph.png", "feature.graphConditionalFormatting",
                                 _CONDITIONAL_FORMATTING_DESC, ADVANCED_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/ConditionalFormattingSample.javasource"));
            add(new DemoDVTItemNode("Context Menus", "/feature/graph/contextMenu.jspx", "/adf/advanced_graph.png",
                                 "feature.graphContextMenu", _CONTEXT_MENU_DESC, INTERACTIVITY_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/ContextMenuSample.javasource"));
            add(new DemoDVTItemNode("Custom Tooltip Formatting", "/feature/graph/customToolTipCallback.jspx",
                                 "/adf/advanced_graph.png", "feature.graphCustomToolTipCallback",
                                 _CUSTOM_TOOLTIP_CALLBACK_DESC, ADVANCED_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/CustomToolTipCallbackSample.javasource"));
            add(new DemoDVTItemNode("Drag and Drop", "/feature/graph/dragAndDrop.jspx", "/adf/advanced_graph.png",
                                 "feature.graphDragAndDrop", _DRAG_AND_DROP_DESC, INTERACTIVITY_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/DragAndDropSample.javasource"));
            add(new DemoDVTItemNode("Drilling", "/feature/graph/drilling.jspx", "/adf/advanced_graph.png",
                                 "feature.graphDrilling", _DRILLING_DESC, INTERACTIVITY_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/DrillingSample.javasource"));
            add(new DemoDVTItemNode("Dynamic Resize", "/feature/graph/dynamicResize.jspx", "/adf/advanced_graph.png",
                                 "feature.graphDynamicResize", _DYNAMIC_RESIZE_DESC, ADVANCED_FEATURES, "Graph", ""));
            add(new DemoDVTItemNode("Empty Graph Feature", "/feature/graph/emptyText.jspx", "/adf/advanced_graph.png",
                                 "feature.graphEmpty", _EMPTY_TEXT_DESC, ADVANCED_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/EmptyGraph.javasource"));    
            add(new DemoDVTItemNode("Graph Types", "/feature/graph/graphTypesSmall.jspx", "/adf/advanced_graph.png",
                                 "feature.graphTypes", _GRAPH_TYPES_DESC, GENERAL_FEATURES, "Graph", ""));
            add(new DemoDVTItemNode("Hide And Show Series", "/feature/graph/hideAndShowSeries.jspx",
                                 "/adf/advanced_graph.png", "feature.graphHideAndShowSeries", _HIDE_SHOW_SERIES_DESC,
                                 INTERACTIVITY_FEATURES, "Graph", ""));
            add(new DemoDVTItemNode("Interactive Slice Behavior", "/feature/graph/interactiveSliceBehavior.jspx",
                                 "/adf/advanced_graph.png", "feature.graphInteractiveSliceBehavior",
                                 _INTERACTIVE_PIE_SLICE_DESC, INTERACTIVITY_FEATURES, "Graph", ""));
            add(new DemoDVTItemNode("Line Data Cursor", "/feature/graph/lineDataCursor.jspx",
                                 "/adf/advanced_graph.png", "feature.graphLineDataCursor",
                                 _LINE_DATA_CURSOR_DESC, INTERACTIVITY_FEATURES, "Graph", ""));
            add(new DemoDVTItemNode("Marker Shapes and Colors", "/feature/graph/markerShapeColor.jspx",
                                 "/adf/advanced_graph.png", "feature.graphMarkerShapeColor",
                                 _MARKER_SHAPES_COLORS_DESC, ADVANCED_FEATURES, "Graph", 
                                 "markerShapeColor:/resources/sourceCode/feature/rich/graph/MarkerShapeColor.javasource"));
            add(new DemoDVTItemNode("Number Format", "/feature/graph/graphNumberFormat.jspx",
                                 "/adf/advanced_graph.png", "feature.graphNumberFormat", _GRAPH_NUMBER_FORMAT_DESC,
                                 ADVANCED_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/GraphNumberFormatBean.javasource"));
            add(new DemoDVTItemNode("Popup Support", "/feature/graph/popupSupport.jspx", "/adf/advanced_graph.png",
                                 "feature.graphPopupSupport", _POPUP_SUPPORT_DESC, INTERACTIVITY_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/PopupSample.javasource"));
            add(new DemoDVTItemNode("Reference Objects", "/feature/graph/referenceObjects.jspx",
                                 "/adf/advanced_graph.png", "feature.graphReferenceObjects", _REF_OBJECTS_DESC,
                                 ADVANCED_FEATURES, "Graph", ""));
            add(new DemoDVTItemNode("Scrollable Legend", "/feature/graph/scrollableLegend.jspx",
                                 "/adf/advanced_graph.png", "feature.graphScrollableLegend", _SCROLLABLE_LEGEND_DESC,
                                 INTERACTIVITY_FEATURES, "Graph", ""));
            add(new DemoDVTItemNode("Selection", "/feature/graph/selection.jspx", "/adf/advanced_graph.png",
                                 "feature.graphSelection", _SELECTION_DESC, INTERACTIVITY_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/SelectionSample.javasource"));
            add(new DemoDVTItemNode("Series Customization", "/feature/graph/seriesCustomization.jspx",
                                 "/adf/advanced_graph.png", "feature.graphSeriesCustomization",
                                 _SERIES_CUSTOMIZATION_DESC, ADVANCED_FEATURES, "Graph", ""));
            add(new DemoDVTItemNode("Series Rollover", "/feature/graph/seriesRollover.jspx",
                                 "/adf/advanced_graph.png", "feature.graphSeriesRollover",
                                 _SERIES_ROLLOVER_DESC, INTERACTIVITY_FEATURES, "Graph", ""));
            add(new DemoDVTItemNode("Spark Charts", "/feature/chart/sparkCharts.jspx", "/adf/advanced_graph.png",
                                 "feature.graphSparkcharts", _SPARK_CHARTS_DESC, GENERAL_FEATURES, "Graph", ""));
            add(new DemoDVTItemNode("Specialized Graphs", "/feature/graph/specializedGraphs.jspx", "/adf/advanced_graph.png",
                                 "feature.graphSpecialized", _SPECIALIZED_GRAPHS_DESC, GENERAL_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/SpecializedGraphBean.javasource"));
            add(new DemoDVTItemNode("Tick Label and Tick Mark", "/feature/graph/tickLabelCallback.jspx",
                                 "/adf/advanced_graph.png", "feature.graphTickLabelAndTickMark",
                                 _TICK_LABEL_CALLBACK_DESC, ADVANCED_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/TickLabelCallbackSample.javasource"));
            add(new DemoDVTItemNode("Time Axis", "/feature/graph/timeAxis.jspx", "/adf/advanced_graph.png",
                                 "feature.graphTimeAxis", _TIME_AXIS_DESC, ADVANCED_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/TimeBlackoutSample.javasource"));
            add(new DemoDVTItemNode("Time Axis - Real Time", "/feature/graph/timeAxisRT.jspx", "/adf/advanced_graph.png",
                                 "feature.graphTimeAxisRT", _TIME_AXIS_RT_DESC, ADVANCED_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/TimeSeriesRTSample.javasource"));
            add(new DemoDVTItemNode("Time Selector", "/feature/graph/timeSelector.jspx", "/adf/advanced_graph.png",
                                 "feature.graphTimeSelector", _TIME_SELECTOR_DESC, ADVANCED_FEATURES, "Graph", 
                                 "/resources/sourceCode/feature/rich/graph/TimeSelectorSample.javasource"));
            add(new DemoDVTItemNode("Zoom and Scroll", "/feature/graph/zoomAndScroll.jspx", "/adf/advanced_graph.png",
                                 "feature.graphZoomAndScroll", _ZOOM_SCROLL_DESC, INTERACTIVITY_FEATURES, "Graph", ""));
        }
    };
    
  // Gauge feature demo list
  static List<DemoItemNode> gaugeDemoList = new ArrayList<DemoItemNode>() {
      {
          // Gallery
          add(new DemoDVTItemNode(GAUGE_GALLERY_LABEL, "/feature/gauge/gallery.jspx", "/adf/gauge.png",
                               "feature.gaugeGallery", _GAUGE_GALLERY_DESC, GENERAL_FEATURES, "Gauge", ""));
      }
  };
  
    // Gauge feature demo list
    static List<DemoItemNode> legacyGaugeDemoList = new ArrayList<DemoItemNode>() {
        {
            // Gallery

            add(new DemoDVTItemNode(GAUGE_GALLERY_LABEL, "/feature/gauge/legacy/gallery.jspx", "/adf/gauge.png",
                                 "feature.legacyGaugeGallery", _GAUGE_GALLERY_DESC, GENERAL_FEATURES, "Gauge", ""));

            // Other Demo
            add(new DemoDVTItemNode(GAUGE_DIAL_STATUSMETER_LABEL, "/feature/gauge/legacy/animationGauge.jspx", "/adf/gauge.png",
                                 "feature.animationGauge", _DIAL_STATUS_METER_DESC, GENERAL_FEATURES, "Gauge", ""));
            add(new DemoDVTItemNode(GAUGE_CUSTOM_SHAPES_LABEL, "/feature/gauge/legacy/customShapes.jspx", "/adf/gauge.png",
                                 "feature.gaugeCustomShapes", _CUSTOM_SHAPES_DESC, ADVANCED_FEATURES, "Gauge", ""));
            add(new DemoDVTItemNode(GAUGE_IN_TABLE_LABEL, "/feature/gauge/legacy/gaugeInTable.jspx", "/adf/gauge.png",
                                 "feature.gaugeInTable", _GAUGE_IN_TABLE_DESC, GENERAL_FEATURES, "Gauge", ""));
            add(new DemoDVTItemNode(GAUGE_SET_LABEL, "/feature/gauge/legacy/gaugeSet.jspx", "/adf/gauge.png", "feature.gaugeSet",
                                 _GAUGE_SET_DESC, GENERAL_FEATURES, "Gauge", "/resources/sourceCode/feature/rich/gauge/GaugeData.javasource"));
            
            add(new DemoDVTItemNode(GAUGE_MULTIPLE_INDICATORS_LABEL, "/feature/gauge/legacy/multipleIndicators.jspx",
                                 "/adf/gauge.png", "feature.gaugeMultipleIndicators", _MULTIPLE_INDICATORS_DESC,
                                 ADVANCED_FEATURES, "Gauge", "/resources/sourceCode/feature/rich/gauge/MultipleIndicators.javasource"));
            add(new DemoDVTItemNode(GAUGE_TICK_MARKS_LABEL, "/feature/gauge/legacy/tickMarks.jspx", "/adf/gauge.png",
                                 "feature.gaugeTickMarks", _TICK_MARKS_DESC, ADVANCED_FEATURES, "Gauge", ""));
            
        }
    };
    
    // Sunburst feature demo list
    static List<DemoItemNode> sunburstDemoList = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Build Your Own", "/feature/sunburst/sunburst.jspx", "/resources/images/icons/sunburst.png",
                                 "feature.sunburstFeatures", _SUNBURST_FEATURES_DESC, GENERAL_FEATURES, "Sunburst", ""));
            
            add(new DemoDVTItemNode("Attribute Groups", "/feature/sunburst/attrGroups.jspx", "/resources/images/icons/sunburst.png",
                                 "feature.sunburstAttrGroups", _SUNBURST_ATTRIBUTE_GROUPS_DESC, ADVANCED_FEATURES, "Sunburst", ""));
            add(new DemoDVTItemNode("Context Menus", "/feature/sunburst/contextMenu.jspx", "/resources/images/icons/sunburst.png",
                                 "feature.sunburstContextMenu", _SUNBURST_CONTEXT_MENU_DESC, INTERACTIVITY_FEATURES, "Sunburst", ""));
            add(new DemoDVTItemNode("Drag and Drop", "/feature/sunburst/dragAndDrop.jspx", "/resources/images/icons/sunburst.png",
                                 "feature.sunburstDragAndDrop", _SUNBURST_DRAG_AND_DROP_DESC, INTERACTIVITY_FEATURES, "Sunburst", ""));
            add(new DemoDVTItemNode("Drilling", "/feature/sunburst/drilling.jspx", "/resources/images/icons/sunburst.png",
                                 "feature.sunburstDrilling", _SUNBURST_DRILLING_DESC, INTERACTIVITY_FEATURES, "Sunburst", ""));
            add(new DemoDVTItemNode("Multiple Roots", "/feature/sunburst/multiRoot.jspx", "/resources/images/icons/sunburst.png",
                                 "feature.sunburstMultiRoot", _SUNBURST_MULTI_ROOT_DESC, ADVANCED_FEATURES, "Sunburst", ""));
            add(new DemoDVTItemNode("Other Node", "/feature/sunburst/otherNode.jspx", "/resources/images/icons/sunburst.png",
                                 "feature.sunburstOtherNode", _SUNBURST_OTHER_NODE_DESC, ADVANCED_FEATURES, "Sunburst", ""));
            add(new DemoDVTItemNode("Pattern Fill", "/feature/treemap/patternFill.jspx", "/resources/images/icons/sunburst.png",
                                 "feature.treemapPatternFill", _SUNBURST_PATTERN_FILL_DESC, ADVANCED_FEATURES, "Sunburst", ""));
            add(new DemoDVTItemNode("Popups", "/feature/treemap/popups.jspx", "/resources/images/icons/sunburst.png",
                                 "feature.treemapPopups", _SUNBURST_POPUPS_DESC, INTERACTIVITY_FEATURES, "Sunburst", ""));
            add(new DemoDVTItemNode("Root Content", "/feature/sunburst/rootContent.jspx", "/resources/images/icons/sunburst.png",
                                 "feature.sunburstRootContent", _SUNBURST_ROOT_CONTENT_DESC, ADVANCED_FEATURES, "Sunburst", ""));
            add(new DemoDVTItemNode("Rotation", "/feature/sunburst/rotation.jspx", "/resources/images/icons/sunburst.png",
                                 "feature.sunburstRotation", _SUNBURST_ROTATION_DESC, INTERACTIVITY_FEATURES, "Sunburst", ""));
            add(new DemoDVTItemNode("Small Form Factor", "/feature/treemap/smallFormFactor.jspx", "/resources/images/icons/sunburst.png",
                                 "feature.treemapSmallFormFactor", _SUNBURST_SMALL_FORM_FACTOR_DESC, ADVANCED_FEATURES, "Sunburst", ""));
            add(new DemoDVTItemNode("Uneven Hierarchy", "/feature/sunburst/unevenHierarchy.jspx", "/resources/images/icons/sunburst.png",
                                 "feature.sunburstUnevenHierarchy", _SUNBURST_UNEVEN_HIERARCHY_DESC, ADVANCED_FEATURES, "Sunburst", ""));
        }
    };
    
    // Treemap feature demo list
    static List<DemoItemNode> treemapDemoList = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Build Your Own", "/feature/treemap/treemap.jspx", "/resources/images/icons/treemap.png",
                                 "feature.treemapFeatures", _TREEMAP_FEATURES_DESC, GENERAL_FEATURES, "Treemap", ""));
            add(new DemoDVTItemNode("Attribute Groups", "/feature/treemap/attrGroups.jspx", "/resources/images/icons/treemap.png",
                                 "feature.treemapAttrGroups", _TREEMAP_ATTRIBUTE_GROUPS_DESC, ADVANCED_FEATURES, "Treemap", ""));
            add(new DemoDVTItemNode("Context Menus", "/feature/treemap/contextMenu.jspx", "/resources/images/icons/treemap.png",
                                 "feature.treemapContextMenu", _TREEMAP_CONTEXT_MENU_DESC, INTERACTIVITY_FEATURES, "Treemap", ""));
            add(new DemoDVTItemNode("Drag and Drop", "/feature/treemap/dragAndDrop.jspx", "/resources/images/icons/treemap.png",
                                 "feature.treemapDragAndDrop", _TREEMAP_DRAG_AND_DROP_DESC, INTERACTIVITY_FEATURES, "Treemap", ""));
            add(new DemoDVTItemNode("Drilling", "/feature/treemap/drilling.jspx", "/resources/images/icons/treemap.png",
                                 "feature.treemapDrilling", _TREEMAP_DRILLING_DESC, INTERACTIVITY_FEATURES, "Treemap", ""));
            add(new DemoDVTItemNode("Isolate", "/feature/treemap/isolate.jspx", "/resources/images/icons/treemap.png",
                                 "feature.treemapIsolate", _TREEMAP_ISOLATE_DESC, INTERACTIVITY_FEATURES, "Treemap", ""));
            add(new DemoDVTItemNode("Layout", "/feature/treemap/layout.jspx", "/resources/images/icons/treemap.png",
                                 "feature.treemapLayout", _TREEMAP_LAYOUT_DESC, ADVANCED_FEATURES, "Treemap", ""));
            add(new DemoDVTItemNode("Multiple Roots", "/feature/treemap/multiRoot.jspx", "/resources/images/icons/treemap.png",
                                 "feature.treemapMultiRoot", _TREEMAP_MULTI_ROOT_DESC, ADVANCED_FEATURES, "Treemap", ""));
            add(new DemoDVTItemNode("Node Content Facet", "/feature/treemap/nodeContent.jspx", "/resources/images/icons/treemap.png",
                                 "feature.treemapNodeContent", _TREEMAP_NODE_CONTENT_DESC, ADVANCED_FEATURES, "Treemap", ""));
            add(new DemoDVTItemNode("Other Node", "/feature/treemap/otherNode.jspx", "/resources/images/icons/treemap.png",
                                 "feature.treemapOtherNode", _TREEMAP_OTHER_NODE_DESC, ADVANCED_FEATURES, "Treemap", ""));
            add(new DemoDVTItemNode("Pattern Fill", "/feature/treemap/patternFill.jspx", "/resources/images/icons/treemap.png",
                                 "feature.treemapPatternFill", _TREEMAP_PATTERN_FILL_DESC, ADVANCED_FEATURES, "Treemap", ""));
            add(new DemoDVTItemNode("Popups", "/feature/treemap/popups.jspx", "/resources/images/icons/treemap.png",
                                 "feature.treemapPopups", _TREEMAP_POPUPS_DESC, INTERACTIVITY_FEATURES, "Treemap", ""));
            add(new DemoDVTItemNode("Small Form Factor", "/feature/treemap/smallFormFactor.jspx", "/resources/images/icons/treemap.png",
                                 "feature.treemapSmallFormFactor", _TREEMAP_SMALL_FORM_FACTOR_DESC, ADVANCED_FEATURES, "Treemap", ""));
        }
    };

    // GeoMap feature demo list
    static List<DemoItemNode> geoMapDemoList = new ArrayList<DemoItemNode>() {
        {
            
            add(new DemoDVTItemNode("Base Maps Demo", "/feature/geoMap/geoMapBaseMaps.jspx", "/adf/geographic_map.png",
                                 "feature.geoMapBaseMaps", _GEOMAP_BASE_MAPS_DESC, GENERAL_FEATURES, "Geographic Map", ""));
            add(new DemoDVTItemNode("Custom Colors", "/feature/geoMap/geoMapColorListTheme.jspx", "/adf/geographic_map.png",
                                 "feature.geoMapColorListTheme", _GEOMAP_COLOR_LIST_DESC, GENERAL_FEATURES, "Geographic Map",
                                 ""));
            add(new DemoDVTItemNode("Bar Theme: Custom Chart", "/feature/geoMap/geoMapCustomBarStyle.jspx",
                                 "/adf/geographic_map.png", "feature.geoMapCustomBarStyle", _GEOMAP_BAR_STYLES_DESC,
                                 ADVANCED_FEATURES, "Geographic Map", ""));
            add(new DemoDVTItemNode("Point Theme: Custom Images", "/feature/geoMap/geoMapCustomDefaultPoint.jspx",
                                 "/adf/geographic_map.png", "feature.geoMapCustomDefaultPoint", _GEOMAP_POINT_DESC,
                                 ADVANCED_FEATURES, "Geographic Map", ""));
            add(new DemoDVTItemNode("Info Window", "/feature/geoMap/geoMapCustomInfoWindow.jspx",
                                 "/adf/geographic_map.png", "feature.geoMapCustomInfoWindow", _GEOMAP_INFO_WINDOW_DESC,
                                 ADVANCED_FEATURES, "Geographic Map", ""));
            add(new DemoDVTItemNode("Pie Theme: Custom Chart", "/feature/geoMap/geoMapCustomPieStyle.jspx",
                                 "/adf/geographic_map.png", "feature.geoMapCustomPieStyle", _GEOMAP_PIE_STYLE_DESC,
                                 ADVANCED_FEATURES, "Geographic Map", ""));
            add(new DemoDVTItemNode("Point Theme: Custom Callback", "/feature/geoMap/geoMapCustomPointCallback.jspx",
                                 "/adf/geographic_map.png", "feature.geoMapCustomPointCallback",
                                 _GEOMAP_POINT_CALLBACK_DESC, INTERACTIVITY_FEATURES, "Geographic Map", ""));
            add(new DemoDVTItemNode("Popup Support", "/feature/geoMap/geoMapPopupDialog.jspx",
                                 "/adf/geographic_map.png", "feature.geoMapPopupDialog", _GEOMAP_POPUP_DESC,
                                 INTERACTIVITY_FEATURES, "Geographic Map", ""));
            add(new DemoDVTItemNode("Geocoding", "/feature/geoMap/geoMapGeocoding.jspx", "/adf/geographic_map.png",
                                 "feature.geoMapGeocoding", _GEOMAP_GEOCODING_DESC, GENERAL_FEATURES, "Geographic Map", ""));
            add(new DemoDVTItemNode("Legend", "/feature/geoMap/geoMapLegend.jspx", "/adf/geographic_map.png",
                                 "feature.geoMapLegend", _GEOMAP_LEGEND_DESC, GENERAL_FEATURES, "Geographic Map", ""));

            add(new DemoDVTItemNode("Selection", "/feature/geoMap/geoMapSelectionEvent.jspx",
                                 "/adf/geographic_map.png", "feature.geoMapSelectionEvent", _GEOMAP_SELECTION_DESC,
                                 INTERACTIVITY_FEATURES, "Geographic Map", ""));
            add(new DemoDVTItemNode("Zoom Behavior", "/feature/geoMap/geoMapThemeZoomLevels.jspx",
                                 "/adf/geographic_map.png", "feature.geoMapThemeZoomLevels", _GEOMAP_ZOOM_LEVELS_DESC,
                                 ADVANCED_FEATURES, "Geographic Map", ""));
            add(new DemoDVTItemNode("Point Theme: Category Bucketing", "/feature/geoMap/geoMapPointCategoryBucketing.jspx",
                                 "/adf/geographic_map.png", "feature.geoMapPointCategoryBucketing",
                                 _GEOMAP_POINT_CAT_BUCKET_DESC, ADVANCED_FEATURES, "Geographic Map", ""));
            add(new DemoDVTItemNode("Point Theme: Customization", "/feature/geoMap/geoMapPointThemeDemo.jspx", "/adf/geographic_map.png",
                                 "feature.geoMapPointThemeDemo", _GEOMAP_POINT_THEME_DESC, GENERAL_FEATURES,
                                 "Geographic Map", ""));
            add(new DemoDVTItemNode("Point Theme: Value Bucketing", "/feature/geoMap/geoMapPointValueBucketing.jspx",
                                 "/adf/geographic_map.png", "feature.geoMapPointValueBucketing",
                                 _GEOMAP_POINT_VAL_BUCKET_DESC, ADVANCED_FEATURES, "Geographic Map", ""));
            
        }
    };

    // tmap feature demo list
    static List<DemoItemNode> tmapDemoList = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Action Events", "/feature/thematicMap/actionEvents.jspx",
                                 "/adf/thematicmap.png", "feature.tmapActionEvents", _TMAP_ACTION_EVENTS_DESC,
                                 INTERACTIVITY_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Attribute Groups", "/feature/thematicMap/attributeGroups.jspx",
                                 "/adf/thematicmap.png", "feature.tmapAttributeGroups",
                                 _TMAP_ATTRIBUTE_GROUPS_DESC, ADVANCED_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Build Your Own", "/feature/thematicMap/tmap.jspx",
                                 "/adf/thematicmap.png", "feature.tmapFeatures",
                                 _THEMATIC_MAP_FEATURES_DESC, GENERAL_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Color Coded Regions", "/feature/thematicMap/colorCodedRegions.jspx",
                                 "/adf/thematicmap.png", "feature.tmapColorCodedRegions",
                                 _TMAP_COLOR_CODED_REGIONS_DESC, GENERAL_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Custom Regions", "/feature/thematicMap/customRegions.jspx",
                                 "/adf/thematicmap.png", "feature.tmapCustomRegions", _TMAP_CUSTOM_REGIONS_DESC,
                                 ADVANCED_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Drag And Drop", "/feature/thematicMap/dragAndDrop.jspx",
                                 "/adf/thematicmap.png", "feature.tmapDragAndDrop", _TMAP_DND_DESC,
                                 INTERACTIVITY_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Drilling", "/feature/thematicMap/drilling.jspx", "/adf/thematicmap.png",
                                 "feature.tmapDrilling", _TMAP_DRILLING_DESC, INTERACTIVITY_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Election Results", "/feature/thematicMap/election.jspx",
                                 "/adf/thematicmap.png", "feature.tmapElection", _TMAP_ELECTION_DESC,
                                 ADVANCED_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Flight Tracker", "/feature/thematicMap/flightTracker.jspx",
                                 "/adf/thematicmap.png", "feature.tmapFlightTracker",
                                 _TMAP_FLIGHT_TRACKER, GENERAL_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Global GDP", "/feature/thematicMap/globalGDP.jspx",
                                 "/adf/thematicmap.png", "feature.tmapGlobalGDP",
                                 _TMAP_GLOBAL_GDP, GENERAL_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Keyboard Model", "/feature/thematicMap/keyboard.jspx", "/adf/thematicmap.png",
                                 "feature.tmapKeyboard", _TMAP_KEYBOARD_DESC, INTERACTIVITY_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Legend", "/feature/thematicMap/legend.jspx", "/adf/thematicmap.png",
                                 "feature.tmapLegend", _TMAP_LEGEND_DESC, GENERAL_FEATURES, "Thematic Map", ""));
            add (new DemoDVTItemNode("Map Provider", "/feature/thematicMap/mapProvider.jspx", "/adf/thematicmap.png",
                                  "feature.tmapMapProvider", _TMAP_MAP_PROVIDER, ADVANCED_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Office Locator", "/feature/thematicMap/offices.jspx",
                                 "/adf/thematicmap.png", "feature.tmapOffices", _TMAP_CUSTOM_POINTS_DESC,
                                 ADVANCED_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Olympic Hosts", "/feature/thematicMap/olympicHosts.jspx",
                                 "/adf/thematicmap.png", "feature.tmapOlympicHosts", _TMAP_OLYMPIC_HOSTS_DESC,
                                 ADVANCED_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Popups", "/feature/thematicMap/popups.jspx", "/adf/thematicmap.png",
                                 "feature.tmapPopups", _TMAP_POPUPS_DESC, INTERACTIVITY_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Styling", "/feature/thematicMap/styles.jspx",
                                 "/adf/thematicmap.png", "feature.tmapStyles", _TMAP_STYLES_DESC,
                                 GENERAL_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Territories", "/feature/thematicMap/territories.jspx",
                                 "/adf/thematicmap.png", "feature.tmapTerritories",
                                 _TMAP_TERRITORIES, GENERAL_FEATURES, "Thematic Map", ""));
            add(new DemoDVTItemNode("Context Menus", "/feature/thematicMap/contextMenus.jspx",
                                 "/adf/thematicmap.png", "feature.tmapContextMenus",
                                 _TMAP_CONTEXT_MENUS, GENERAL_FEATURES, "Thematic Map", ""));
        }
    };

    // Pivot Filter Bar feature demo list
    static List<DemoItemNode> pivotFilterBarDemoList = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode(GRAPH_AND_PIVOT_TABLE_PIVOT_FILTER_BAR_LABEL,
                                 "/feature/pivotFilterBar/pivotFilterBarWithPivotTableAndGraph.jspx",
                                 "/adf/pivotfilterbar.png", "feature.pivotFilterBarWithPivotTableAndGraph",
                                 _PIVOT_FILTER_BAR_GRAPH_AND_TABLE_DESC, ADVANCED_FEATURES, "Pivot Filter Bar",
                                 ""));
            add(new DemoDVTItemNode(MODEL_NAME_PIVOT_FILTER_BAR_LABEL,
                                 "/feature/pivotFilterBar/pivotFilterBarWithTwoPivotTables.jspx",
                                 "/adf/pivotfilterbar.png", "feature.pivotFilterBarWithTwoPivotTables",
                                 _PIVOT_FILTER_BAR_MULTIPLE_TABLES_DESC, ADVANCED_FEATURES, "Pivot Filter Bar",
                                 ""));
            add(new DemoDVTItemNode(CONTENT_DELIVERY_PIVOT_FILTER_BAR_LABEL,
                                 "/feature/pivotFilterBar/pivotFilterBarWhenAvailable.jspx", "/adf/pivotfilterbar.png",
                                 "feature.pivotFilterBarWhenAvailable", _PIVOT_FILTER_BAR_WHEN_AVAILABLE_DESC,
                                 GENERAL_FEATURES, "Pivot Filter Bar", ""));
            add(new DemoDVTItemNode(CLIENT_BEHAVIOR_PIVOT_FILTER_BAR_LABEL,
                                 "/feature/pivotFilterBar/pivotFilterBarClientBehavior.xhtml",
                                 "/adf/pivotfilterbar.png", "feature.pivotFilterBarClientBehavior",
                                 _PIVOT_FILTER_BAR_CLIENT_BEHAVIOR_DESC, GENERAL_FEATURES, "Pivot Filter Bar", ""));
        }
    };

    // Pivot Table feature demo list
    static List<DemoItemNode> pivotTableDemoList = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode(CSV_ROWSET_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableCSVDemo.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableCSVDemo", _PIVOT_TABLE_CSV_ROWSET_DESC,
                                 GENERAL_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(HEADER_CELL_STAMPING_PIVOT_TABLE_LABEL,
                                 "/feature/pivotTable/pivotTableHeaderCellStamping.jspx", "/adf/pivot_table.png",
                                 "feature.pivotTableHeaderCellStamping", _PIVOT_TABLE_HEADER_CELL_STAMPING_DESC,
                                 STAMPING_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(ADJUSTABLE_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableAdjustable.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableAdjustable", _PIVOT_TABLE_ADJUSTABLE_DESC,
                                 GENERAL_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(LAYER_LABEL_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableLayerLabels.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableLayerLabels", _PIVOT_TABLE_LAYER_LABEL_DESC,
                                 GENERAL_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(DATA_BARS_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableDataBars.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableDataBars", _PIVOT_TABLE_DATA_BARS_DESC,
                                 GENERAL_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(DRILLABLE_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableDrillable.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableDrillable", _PIVOT_TABLE_DRILLABLE_DESC,
                                 INTERACTIVITY_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(EDITABLE_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableEdit.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableEdit", _PIVOT_TABLE_EDIT_DESC, INTERACTIVITY_FEATURES,
                                 "Pivot Table", ""));
            add(new DemoDVTItemNode(COMPLEX_STAMPS_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableComplexStamps.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableComplexStamps", _PIVOT_TABLE_COMPLEX_STAMPS_DESC, INTERACTIVITY_FEATURES,
                                 "Pivot Table", ""));
            add(new DemoDVTItemNode(HEAT_MAP_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableHeatMap.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableHeatMap", _PIVOT_TABLE_HEAT_MAP_DESC,
                                 ADVANCED_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(SELECTION_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableSelection.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableSelection", _PIVOT_TABLE_SELECTION_DESC,
                                 INTERACTIVITY_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(TOTALS_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableTotals.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableTotals", _PIVOT_TABLE_TOTALS_DESC,
                                 GENERAL_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(EXPORT_TO_EXCEL_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableExport.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableExport", _PIVOT_TABLE_EXPORT_DESC,
                                 GENERAL_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(GAUGE_STAMPED_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableGauge.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableGauge", _PIVOT_TABLE_GAUGE_DESC,
                                 STAMPING_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(GRAPH_STAMPED_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableGraph.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableGraph", _PIVOT_TABLE_GRAPH_DESC,
                                 STAMPING_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(CELL_FORMATTING_PIVOT_TABLE_LABEL,
                                 "/feature/pivotTable/pivotTableCellFormatting.jspx", "/adf/pivot_table.png",
                                 "feature.pivotTableCellFormatting", _PIVOT_TABLE_CELL_FORMATTING_DESC,
                                 ADVANCED_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(MEMBER_FORMATTING_PIVOT_TABLE_LABEL,
                                 "/feature/pivotTable/pivotTableMemberFormatting.jspx", "/adf/pivot_table.png",
                                 "feature.pivotTableMemberFormatting", _PIVOT_TABLE_MEMBER_FORMATTING_DESC,
                                 ADVANCED_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(SPARK_CHART_STAMPED_PIVOT_TABLE_LABEL,
                                 "/feature/pivotTable/pivotTableSparkChart.jspx", "/adf/pivot_table.png",
                                 "feature.pivotTableSparkChart", _PIVOT_TABLE_SPARKCHART_DESC, STAMPING_FEATURES,
                                 "Pivot Table", ""));
            /*            add(new DemoDVTItemNode("Pivot Table Rowset Drilling",
                                 "/feature/pivotTable/pivotTableInsertDrill.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableInsertDrill", _PIVOT_TABLE_ROWSET_DRILL_DESC));*/
            add(new DemoDVTItemNode(WHEN_AVAILABLE_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableWhenAvailable.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableWhenAvailable",
                                 _PIVOT_TABLE_WHEN_AVAILABLE_DESC, GENERAL_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(ACTIVE_CELL_STAMPING_PIVOT_TABLE_LABEL,
                                 "/feature/pivotTable/pivotTableActiveCellStamping.jspx", "/adf/pivot_table.png",
                                 "feature.pivotTableActiveCellKey", _PIVOT_TABLE_ACTIVE_CELL_KEY_DESC, STAMPING_FEATURES,
                                 "Pivot Table", ""));
            add(new DemoDVTItemNode(CLIENT_BEHAVIOR_PIVOT_TABLE_LABEL,
                                 "/feature/pivotTable/pivotTableClientBehavior.xhtml", "/adf/pivot_table.png",
                                 "feature.pivotTableClientBehavior", _PIVOT_TABLE_CLIENT_BEHAVIOR_DESC,
                                 GENERAL_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(HEADER_CELL_WRAPPING_PIVOT_TABLE_LABEL,
                                 "/feature/pivotTable/pivotTableHeaderCellWrapping.jspx", "/adf/pivot_table.png",
                                 "feature.pivotTableHeaderCellWrapping", _PIVOT_TABLE_HEADER_CELL_WRAPPING_DESC,
                                 GENERAL_FEATURES, "Pivot Table", ""));
            add(new DemoDVTItemNode(CLIENT_LISTENER_PIVOT_TABLE_LABEL, "/feature/pivotTable/pivotTableClientListener.jspx",
                                 "/adf/pivot_table.png", "feature.pivotTableClientListener", _PIVOT_TABLE_CLIENT_LISTENER_DESC,
                                 INTERACTIVITY_FEATURES, "Pivot Table", ""));
        }
    };

    // Hierarchy Viewer feature demo list
    static List<DemoItemNode> hvDemoList = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Build Your Own", "/feature/hv/hv.jspx", "/adf/xmlSchema.png",
                               "feature.hvFeatures", _HV_FEATURES_DESC, GENERAL_FEATURES, "Hierarchy Viewer",
                               ""));
            add(new DemoDVTItemNode("Anchor Listener", "/feature/hv/anchorListener.jspx", "/adf/xmlSchema.png",
                             "feature.hvAnchor", _HV_ANCHOR_DESC, INTERACTIVITY_FEATURES, "Hierarchy Viewer",
                             ""));
            add(new DemoDVTItemNode("Drag and Drop", "/feature/hv/dragAndDrop.jspx", "/adf/xmlSchema.png",
                               "feature.hvDragAndDrop", _HV_DRAG_AND_DROP_DESC, INTERACTIVITY_FEATURES, "Hierarchy Viewer", 
                                 ""));
            add(new DemoDVTItemNode("Lateral navigation", "/feature/hv/lateralNavigation.jspx", "/adf/xmlSchema.png",
                               "feature.hvLateralNavigation", _HV_LATERAL_NAV_DESC, INTERACTIVITY_FEATURES, "Hierarchy Viewer",
                               ""));
            add(new DemoDVTItemNode("Search", "/feature/hv/search.jspx", "/adf/xmlSchema.png", "feature.hvSearch",
                               _HV_SEARCH_DESC, INTERACTIVITY_FEATURES, "Hierarchy Viewer", 
                               ""));
            add(new DemoDVTItemNode("Panel Card", "/feature/hv/panelCard.jspx", "/adf/xmlSchema.png",
                               "feature.hvPanelCard", _HV_PANEL_CARD_DESC, GENERAL_FEATURES, "Hierarchy Viewer", ""));
            add(new DemoDVTItemNode("Popups", "/feature/hv/popup.jspx", "/adf/xmlSchema.png", "feature.hvPopup",
                               _HV_MODAL_POPUP_DESC, INTERACTIVITY_FEATURES, "Hierarchy Viewer", 
                               ""));
            add(new DemoDVTItemNode("Zooming", "/feature/hv/zooming.jspx", "/adf/xmlSchema.png", "feature.hvZooming",
                               _HV_ZOOMING_DESC, GENERAL_FEATURES, "Hierarchy Viewer", 
                               ""));
        }
    };

    // Gantt feature demo list
    static List<DemoItemNode> schedulingGanttDemoList = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode(SCHEDULING_GANTT_BACKGROUND_BARS_LABEL,
                                 "/feature/gantt/schedulingGanttBackgroundBars.jspx", "/adf/gantt.png",
                                 "feature.schedulingGanttBackgroundBars", _GANTT_BACKGROUND_BARS_DESC, ADVANCED_FEATURES,
                                 "Scheduling Gantt", ""));
            add(new DemoDVTItemNode(SCHEDULING_GANTT_RESOURCE_ACTION_LABEL,
                                 "/feature/gantt/schedulingGanttResourceAction.jspx", "/adf/gantt.png",
                                 "feature.schedulingGanttResourceAction", _GANTT_RESOURCE_ACTION_DESC, ADVANCED_FEATURES,
                                 "Scheduling Gantt", ""));
            add(new DemoDVTItemNode(SCHEDULING_GANTT_CUSTOM_CONTEXT_MENU_LABEL,
                                 "/feature/gantt/schedulingGanttCustomContextMenus.jspx", "/adf/gantt.png",
                                 "feature.schedulingGanttCustomContextMenus", _GANTT_CONTEXT_MENUS_DESC,
                                 ADVANCED_FEATURES, "Scheduling Gantt", ""));
            add(new DemoDVTItemNode(SCHEDULING_GANTT_DROP_TARGET_LABEL, "/feature/gantt/schedulingGanttDropTarget.jspx",
                                 "/adf/gantt.png", "feature.schedulingGanttDropTarget", _GANTT_DROP_TARGET_DESC,
                                 INTERACTIVITY_FEATURES, "Scheduling Gantt", ""));
            add(new DemoDVTItemNode(SCHEDULING_GANTT_SELECTION_LABEL, "/feature/gantt/schedulingGanttSelection.jspx",
                                 "/adf/gantt.png", "feature.schedulingGanttSelection", _GANTT_SELECTION_DESC,
                                 INTERACTIVITY_FEATURES, "Scheduling Gantt", ""));
            add(new DemoDVTItemNode(SCHEDULING_GANTT_TASKBAR_FORMAT_LABEL,
                                 "/feature/gantt/schedulingGanttTaskbarFormat.jspx", "/adf/gantt.png",
                                 "feature.schedulingGanttTaskbarFormat", _GANTT_TASKBAR_FORMAT_DESC, ADVANCED_FEATURES,
                                 "Scheduling Gantt", ""));
            add(new DemoDVTItemNode(SCHEDULING_GANTT_CUSTOM_TIME_SCALE_LABEL,
                                 "/feature/gantt/schedulingGanttCustomTimescale.jspx", "/adf/gantt.png",
                                 "feature.schedulingGanttCustomTimescale", _GANTT_CUSTOM_TIMESCALE_DESC,
                                 ADVANCED_FEATURES, "Scheduling Gantt", ""));
            add(new DemoDVTItemNode(SCHEDULING_GANTT_INITIAL_DATE_FOCUS_LABEL,
                                 "/feature/gantt/schedulingGanttInitialDateFocus.jspx", "/adf/gantt.png",
                                 "feature.schedulingGanttInitialDateFocus", _GANTT_INITIAL_DATE_FOCUS_DESC, GENERAL_FEATURES,
                                 "Scheduling Gantt", ""));
            add(new DemoDVTItemNode(SCHEDULING_GANTT_GO_TO_DATE_CONVERTER_LABEL,
                                 "/feature/gantt/schedulingGanttGoToDateConverter.jspx", "/adf/gantt.png",
                                 "feature.schedulingGanttGoToDateConverter", _GANTT_GO_TO_DATE_CONVERTER_DESC,
                                 ADVANCED_FEATURES, "Scheduling Gantt", ""));
            add(new DemoDVTItemNode(SCHEDULING_GANTT_TIME_ZONE_LABEL, "/feature/gantt/schedulingGanttTimeZone.jspx",
                                 "/adf/gantt.png", "feature.schedulingGanttTimeZone", _GANTT_TIME_ZONE_DESC, GENERAL_FEATURES,
                                 "Scheduling Gantt", ""));
            add(new DemoDVTItemNode(SCHEDULING_GANTT_SORT,
                                 "/feature/gantt/schedulingGanttSort.jspx", "/adf/gantt.png",
                                 "feature.schedulingGanttSort", _GANTT_SORT_DESC, GENERAL_FEATURES,
                                 "Scheduling Gantt", ""));
        }
    };
    static List<DemoItemNode> projectGanttDemoList = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode(PROJECT_GANTT_CLICK_TO_EDIT_LABEL, "/feature/gantt/projectGanttClickToEdit.jspx",
                                 "/adf/gantt.png", "feature.projectGanttClickToEdit", _GANTT_CLICK_TO_EDIT_DESC,
                                 INTERACTIVITY_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_TABLE_ACTIVE_ROW_KEY, "/feature/gantt/projectGanttTableActiveRowKey.jspx",
                                 "/adf/gantt.png", "feature.projectGanttTableActiveRowKey", _GANTT_TABLE_ACTIVE_ROW_KEY,
                                 ADVANCED_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_CUSTOM_MENU_AND_TOOLBAR_LABEL,
                                 "/feature/gantt/projectGanttCustomMenubarToolbar.jspx", "/adf/gantt.png",
                                 "feature.projectGanttCustomMenubarToolbar", _GANTT_MENU_TOOLBAR_DESC,
                                 ADVANCED_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_DOUBLE_CLICK_LABEL, "/feature/gantt/projectGanttDoubleClick.jspx",
                                 "/adf/gantt.png", "feature.projectGanttDoubleClick", _GANTT_DOUBLE_CLICK_DESC,
                                 INTERACTIVITY_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_EDITABLE_TASKS_LABEL, "/feature/gantt/projectGanttEditableTasks.jspx",
                                 "/adf/gantt.png", "feature.projectGanttEditableTasks", _GANTT_EDITABLE_TASKS_DESC,
                                 GENERAL_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_SCROLL_SYNC_LABEL, "/feature/gantt/projectGanttScrollSync.jspx",
                                 "/adf/gantt.png", "feature.projectGanttScrollSync", _GANTT_SCROLL_SYNC_DESC,
                                 GENERAL_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_ZOOM_TO_FIT_LABEL, "/feature/gantt/projectGanttZoomToFit.jspx",
                                 "/adf/gantt.png", "feature.projectGanttZoomToFit", _GANTT_ZOOM_TO_FIT_DESC,
                                 GENERAL_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_DRAG_SOURCE_LABEL, "/feature/gantt/projectGanttDragSource.jspx",
                                 "/adf/gantt.png", "feature.projectGanttDragSource", _GANTT_DRAG_SOURCE_DESC,
                                 INTERACTIVITY_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_FEATURES_OFF_LABEL, "/feature/gantt/projectGanttFeaturesOff.jspx",
                                 "/adf/gantt.png", "feature.projectGanttFeaturesOff", _GANTT_FEATURES_OFF_DESC,
                                 GENERAL_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_CUSTOMIZE_MENUS_LABEL, "/feature/gantt/projectGanttCustomizeMenus.jspx",
                                 "/adf/gantt.png", "feature.projectGanttCustomizeMenus", _GANTT_CUSTOMIZE_MENUS_DESC,
                                 GENERAL_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_CUSTOM_MENU_TOOLBAR_LAYOUT_LABEL, "/feature/gantt/projectGanttCustomMenuToolbarLayout.jspx",
                                 "/adf/gantt.png", "feature.projectGanttCustomMenuToolbarLayout", _GANTT_CUSTOMIZE_MENUS_DESC,
                                 GENERAL_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_CUSTOM_PRINTING_LABEL, "/feature/gantt/projectGanttPrinting.jspx",
                                 "/adf/gantt.png", "feature.projectGanttPrinting", _GANTT_PRINTING_DESC,
                                 ADVANCED_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_TASKBAR_FORMAT_LABEL, "/feature/gantt/projectGanttTaskbarFormat.jspx",
                                 "/adf/gantt.png", "feature.projectGanttTaskbarFormat", _GANTT_TASKBAR_FORMAT_DESC,
                                 ADVANCED_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_INITIAL_EXPAND_ALL_LABEL, "/feature/gantt/projectGanttExpandAll.jspx",
                                 "/adf/gantt.png", "feature.projectGanttExpandAll", _GANTT_EXPAND_ALL_DESC, GENERAL_FEATURES,
                                 "Project Gantt", ""));
            /*            add(new DemoDVTItemNode("Project Gantt whenAvailable Content Delivery",
                                 "/feature/gantt/projectGanttWhenAvailable.jspx",
                                 "/adf/gantt.png", "feature.projectGanttWhenAvailable", _GANTT_WHEN_AVAILABLE_DESC));
            add(new DemoDVTItemNode("Project Gantt AutoPPR",
                                 "/feature/gantt/projectGanttAutoPPR.jspx",
                                 "/adf/gantt.png", "feature.projectGanttAutoPPR", _GANTT_AUTO_PPR));*/
            add(new DemoDVTItemNode(PROJECT_GANTT_GENERIC_CONVERTER_LABEL,
                                 "/feature/gantt/projectGanttGenericConverter.jspx", "/adf/gantt.png",
                                 "feature.projectGanttGenericConverter", _GANTT_GENERIC_CONVERTER_DESC, GENERAL_FEATURES,
                                 "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_EXPORT_TO_EXCEL_LABEL, "/feature/gantt/projectGanttExportToExcel.jspx",
                                 "/adf/gantt.png", "feature.projectGanttExportToExcel", _GANTT_EXPORT_TO_EXCEL_DESC,
                                 GENERAL_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_CLIENT_BEHAVIOR_LABEL,
                                 "/feature/gantt/projectGanttClientBehavior.xhtml", "/adf/gantt.png",
                                 "feature.projectGanttClientBehavior", _GANTT_CLIENT_BEHAVIOR_DESC, GENERAL_FEATURES,
                                 "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_ATTRIBUTE_CONVERTER_FACTORY_LABEL,
                                 "/feature/gantt/projectGanttAttributeConverterFactory.jspx", "/adf/gantt.png",
                                 "feature.projectGanttAttributeConverterFactory",
                                 _GANTT_ATTRIBUTE_CONVERTER_FACTORY_DESC, GENERAL_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_LABEL_ICON_PLACEMENT_LABEL,
                                 "/feature/gantt/projectGanttLabelIconPlacement.jspx", "/adf/gantt.png",
                                 "feature.projectGanttLabelIconPlacement", _GANTT_LABEL_ICON_PLACEMENT_DESC,
                                 GENERAL_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_CLIENT_LISTENER_LABEL, "/feature/gantt/projectGanttClientListener.jspx",
                                 "/adf/gantt.png", "feature.projectGanttClientListener", _GANTT_CLIENT_LISTENER_DESC,
                                 GENERAL_FEATURES, "Project Gantt", ""));
            add(new DemoDVTItemNode(PROJECT_GANTT_SERVER_LISTENER_LABEL, "/feature/gantt/projectGanttServerListener.jspx",
                                 "/adf/gantt.png", "feature.projectGanttServerListener", _GANTT_SERVER_LISTENER_DESC,
                                 GENERAL_FEATURES, "Project Gantt", ""));
        }
    };

    static List<DemoItemNode> resourceGanttDemoList = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode(RESOURCE_GANTT_DOUBLE_CLICK_LABEL,
                                 "/feature/gantt/resourceUtilizationGanttDoubleClick.jspx", "/adf/gantt.png",
                                 "feature.resourceUtilizationGanttDoubleClick", _RU_GANTT_DBL_CLICK_DESC,
                                 INTERACTIVITY_FEATURES, "Resource Utilization Gantt", ""));
            add(new DemoDVTItemNode(RESOURCE_GANTT_METRIC_CONVERTER_LABEL,
                                 "/feature/gantt/resourceUtilizationGanttMetricConverter.jspx", "/adf/gantt.png",
                                 "feature.resourceUtilizationGanttMetricConverter", _RU_GANTT_METRIC_CONVERTER_DESC,
                                 GENERAL_FEATURES, "Resource Utilization Gantt", ""));
            add(new DemoDVTItemNode(RESOURCE_GANTT_SELECTION_LABEL,
                                 "/feature/gantt/resourceUtilizationGanttSelection.jspx", "/adf/gantt.png",
                                 "feature.resourceUtilizationGanttSelection", _RU_GANTT_SELECTION_DESC, INTERACTIVITY_FEATURES,
                                 "Resource Utilization Gantt", ""));
            add(new DemoDVTItemNode(RESOURCE_GANTT_STACKING_LABEL, "/feature/gantt/resourceUtilizationGanttStacking.jspx",
                                 "/adf/gantt.png", "feature.resourceUtilizationGanttStacking", _RU_GANTT_STACKING_DESC,
                                 GENERAL_FEATURES, "Resource Utilization Gantt", ""));
            add(new DemoDVTItemNode(RESOURCE_GANTT_STEPPED_LINE_LABEL, "/feature/gantt/resourceUtilizationGanttSteppedLine.jspx",
                                 "/adf/gantt.png", "feature.resourceUtilizationGanttSteppedLine", _RU_GANTT_STEPPED_LINE_DESC,
                                 GENERAL_FEATURES, "Resource Utilization Gantt", ""));
            //Comment out until we have Design Time for this feature.
            /*add(new DemoDVTItemNode(RESOURCE_GANTT_ATTRIBUTE_DETAIL_LABEL, "/feature/gantt/resourceUtilizationGanttAttributeDetail.jspx",
                                 "/adf/gantt.png", "feature.resourceUtilizationGanttAttributeDetail", _RU_GANTT_ATTRIBUTE_DETAIL_DESC,
                                 GENERAL_FEATURES, "Resource Utilization Gantt", ""));*/
        }
    };

    static List<DemoItemNode> ganttDemoList = new ArrayList<DemoItemNode>() {
        {
            Collections.sort(projectGanttDemoList);
            Collections.sort(resourceGanttDemoList);
            Collections.sort(schedulingGanttDemoList);
            addAll(projectGanttDemoList);
            addAll(resourceGanttDemoList);
            addAll(schedulingGanttDemoList);
        }
    };

    // Timeline feature demo list
    static List<DemoItemNode> timelineDemoList = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Dual Timeline", "/feature/timeline/dualTimeline.jspx", "/adf/timeline.png",
                                 "feature.timelineDual", _TIMELINE_DUAL_DESC, GENERAL_FEATURES, "Timeline", ""));
            add(new DemoDVTItemNode("Drag and Drop", "/feature/timeline/timelineDnD.jspx", "/adf/gantt.png",
                                 "feature.timelineDnD", _TIMELINE_DND_DESC, INTERACTIVITY_FEATURES, "Timeline", ""));
            add(new DemoDVTItemNode("Durations", "/feature/timeline/timelineDurations.jspx", "/adf/gantt.png",
                                 "feature.timelineDurations", _TIMELINE_DURATIONS_DESC, INTERACTIVITY_FEATURES, "Timeline", ""));
            add(new DemoDVTItemNode("Auto PPR", "/feature/timeline/timelineAutoPPR.jspx", "/adf/timeline.png",
                                 "feature.timelineAutoPPR", _TIMELINE_AUTOPPR_DESC, ADVANCED_FEATURES, "Timeline", ""));            
            add(new DemoDVTItemNode("Client Behavior", "/feature/timeline/timelineClientBehavior.xhtml", "/adf/timeline.png",
                                 "feature.timelineClientBehavior", _TIMELINE_CLIENT_BEHAVIOR_DESC, GENERAL_FEATURES, "Timeline", ""));
            add(new DemoDVTItemNode("Context Menu", "/feature/timeline/timelineContextMenu.jspx", "/adf/timeline.png",
                                 "feature.timelineContextMenu", _TIMELINE_CONTEXT_MENU_DESC, GENERAL_FEATURES, "Timeline", ""));
        }
    };

  // Diagram feature demo list
  static List<DemoItemNode> diagramDemoList = new ArrayList<DemoItemNode>() {
      {
        add(new DemoDVTItemNode("Build Your Own", "/feature/diagram/diagram.jspx", "/adf/xmlSchema.png",
                             "feature.diagramFeatures", _DIAGRAM_FEATURES_DESC, GENERAL_FEATURES, "Diagram", ""));
        add(new DemoDVTItemNode("Layout Tutorial", "/feature/diagram/tutorial/layoutTutorial.jspx", "/adf/xmlSchema.png",
                             "feature.diagramLayoutTutorial", _DIAGRAM_LAYOUTS_DESC, GENERAL_FEATURES, "Diagram", ""));        
        add(new DemoDVTItemNode("Link Styles", "/feature/diagram/linkStyles.jspx", "/adf/xmlSchema.png",
                             "feature.diagramLinkStyles", _DIAGRAM_LINK_STYLES_DESC, GENERAL_FEATURES, "Diagram", ""));
          add(new DemoDVTItemNode("Context Menus", "/feature/diagram/contextMenu.jspx", "/adf/xmlSchema.png",
                               "feature.diagramContextMenu", _DIAGRAM_CONTEXT_MENU_DESC, INTERACTIVITY_FEATURES, "Diagram", ""));
          add(new DemoDVTItemNode("Zooming", "/feature/diagram/zooming.jspx", "/adf/xmlSchema.png",
                               "feature.diagramZooming", _DIAGRAM_ZOOMING_DESC, INTERACTIVITY_FEATURES, "Diagram", ""));
          add(new DemoDVTItemNode("Highlighting", "/feature/diagram/highlighting.jspx", "/adf/xmlSchema.png",
                               "feature.diagramHighlighting", _DIAGRAM_HIGHLIGHTING_DESC, INTERACTIVITY_FEATURES, "Diagram", ""));
          add(new DemoDVTItemNode("Popups", "/feature/diagram/popup.jspx", "/adf/xmlSchema.png",
                               "feature.diagramPopup", _DIAGRAM_POPUP_DESC, INTERACTIVITY_FEATURES, "Diagram", ""));
          add(new DemoDVTItemNode("Diagram Editor", "/feature/diagram/editor.jspx", "/adf/xmlSchema.png",
                               "feature.diagramEditor", _DIAGRAM_EDIT_DESC, INTERACTIVITY_FEATURES, "Diagram", ""));
          add(new DemoDVTItemNode("Diagram Simple Rules Editor", "/feature/diagram/rulesEditor.jspx", "/adf/xmlSchema.png",
                             "feature.diagramRulesEditor", _DIAGRAM_RULES_EDITOR_DESC, INTERACTIVITY_FEATURES, "Diagram", ""));        
          add(new DemoDVTItemNode("Employee Tree", "/feature/diagram/employeeTree.jspx", "/adf/xmlSchema.png",
                               "feature.diagramEmployee", _DIAGRAM_EMPLOYEE_DESC, ADVANCED_FEATURES, "Diagram", ""));
          add(new DemoDVTItemNode("Arc Diagram", "/feature/diagram/arcLayout.jspx", "/adf/xmlSchema.png",
                           "feature.diagramArcLayout", _DIAGRAM_ARC_LAYOUT_DESC, ADVANCED_FEATURES, "Diagram", ""));        
          add(new DemoDVTItemNode("Container Padding", "/feature/diagram/containerPadding.jspx", "/adf/xmlSchema.png",
                               "feature.diagramContainerPadding", _DIAGRAM_CONTAINER_PADDING_DESC, ADVANCED_FEATURES, "Diagram", ""));
          add(new DemoDVTItemNode("US State-to-State Migration", "/feature/diagram/stateMigration.jspx", "/adf/xmlSchema.png",
                               "feature.diagramStateMigration", _DIAGRAM_STATE_MIGRATION_DESC, ADVANCED_FEATURES, "Diagram", ""));
          add(new DemoDVTItemNode("Database Schema Layout", "/feature/diagram/databaseSchemaDiagram.jspx", "/adf/xmlSchema.png",
                               "feature.diagramDbSchema", _DIAGRAM_DB_SCHEMA_DESC, ADVANCED_FEATURES, "Diagram", ""));
          add(new DemoDVTItemNode("Stacking", "/feature/diagram/stacking.jspx", "/adf/xmlSchema.png",
                               "feature.diagramStacking", _DIAGRAM_STACKING_DESC, ADVANCED_FEATURES, "Diagram", ""));
          add(new DemoDVTItemNode("PanelCard Content", "/feature/diagram/panelCardContent.jspx", "/adf/xmlSchema.png",
                               "feature.diagramPanelCard", _DIAGRAM_PANELCARD_DESC, ADVANCED_FEATURES, "Diagram", ""));
          add(new DemoDVTItemNode("DataFlavors", "/feature/diagram/flavors.jspx", "/adf/xmlSchema.png",
                             "feature.diagramFlavors", _DIAGRAM_FLAVORS_DESC, ADVANCED_FEATURES, "Diagram", ""));
      }
  };

  // NBox feature demo list
  static List<DemoItemNode> nBoxDemoList = new ArrayList<DemoItemNode>() {
      {
        add(new DemoDVTItemNode("Build Your Own", "/feature/nBox/nBox.jspx", "/resources/images/icons/nBox.png",
                             "feature.nBoxFeatures", _NBOX_FEATURES_DESC, GENERAL_FEATURES, "NBox", ""));
        add(new DemoDVTItemNode("Highlighting", "/feature/nBox/highlighting.jspx", "/resources/images/icons/nBox.png",
                             "feature.nBoxHighlighting", _NBOX_HIGHLIGHTING_DESC, GENERAL_FEATURES, "NBox", ""));
        add(new DemoDVTItemNode("Drag and Drop", "/feature/nBox/dragAndDrop.jspx", "/resources/images/icons/nBox.png",
                             "feature.nBoxDnD", _NBOX_DND_DESC, INTERACTIVITY_FEATURES, "NBox", ""));
        add(new DemoDVTItemNode("Attribute Groups", "/feature/nBox/attributeGroups.jspx", "/resources/images/icons/nBox.png",
                             "feature.nBoxAttributeGroups", _NBOX_ATTRIBUTE_GROUPS_DESC, INTERACTIVITY_FEATURES, "NBox", ""));
        add(new DemoDVTItemNode("Popups", "/feature/nBox/popups.jspx", "/resources/images/icons/nBox.png",
                             "feature.nBoxPopups", _NBOX_POPUPS_DESC, GENERAL_FEATURES, "NBox", ""));
      }
  };
  
  // TagCloud feature demo list
  static List<DemoItemNode> tagCloudDemoList = new ArrayList<DemoItemNode>() {
    {
      add(new DemoDVTItemNode("Build Your Own", "/feature/tagCloud/tagCloud.jspx", "/resources/images/icons/tagCloud.png",
                         "feature.tagCloudFeatures", _TAG_CLOUD_FEATURES_DESC, GENERAL_FEATURES, "TagCloud", ""));
      add(new DemoDVTItemNode("Action Events", "/feature/tagCloud/actionEvents.jspx", "/resources/images/icons/tagCloud.png",
                         "feature.tagCloudActionEvent", _TAG_CLOUD_ACTION_EVENT_DESC, GENERAL_FEATURES, "TagCloud", ""));
      add(new DemoDVTItemNode("Destination", "/feature/tagCloud/destination.jspx", "/resources/images/icons/tagCloud.png",
                         "feature.tagCloudDestination", _TAG_CLOUD_DESTINATION_DESC, GENERAL_FEATURES, "TagCloud", ""));
      add(new DemoDVTItemNode("Context Menu", "/feature/tagCloud/contextMenu.jspx", "/resources/images/icons/tagCloud.png",
                         "feature.tagCloudContextMenu", _TAG_CLOUD_CONTEXT_MENU_DESC, GENERAL_FEATURES, "TagCloud", ""));
      add(new DemoDVTItemNode("Attribute Groups", "/feature/tagCloud/attributeGroups.jspx", "/resources/images/icons/tagCloud.png",
                         "feature.tagCloudAttributeGroups", _TAG_CLOUD_ATTRIBUTE_GROUPS_DESC, GENERAL_FEATURES, "TagCloud", ""));
      add(new DemoDVTItemNode("Highlight and Filter", "/feature/tagCloud/highlightAndFilter.jspx", "/resources/images/icons/tagCloud.png",
                         "feature.tagCloudHighlightAndFilter", _TAG_CLOUD_HIGHLIGHT_AND_FILTER_DESC, GENERAL_FEATURES, "TagCloud", ""));
      add(new DemoDVTItemNode("Drag and Drop", "/feature/tagCloud/dragAndDrop.jspx", "/resources/images/icons/tagCloud.png",
                         "feature.tagCloudDragAndDrop", _TAG_CLOUD_DND_DESC, GENERAL_FEATURES, "TagCloud", ""));
      add(new DemoDVTItemNode("Popups", "/feature/tagCloud/popups.jspx", "/resources/images/icons/tagCloud.png",
                         "feature.tagCloudPopups", _TAG_CLOUD_POPUPS_DESC, GENERAL_FEATURES, "TagCloud", ""));
    }
  };

    //
    // Lists of feature demos plus the index page for each component
    //
    static List<DemoItemNode> legacyDemoPlusIndex = new ArrayList<DemoItemNode>() {
        {
          add(new DemoDVTItemNode("Gauge", "/images/folder.png", legacyGaugeDemoList));
          add(new DemoDVTItemNode("Graph", "/images/folder.png", graphDemoList));
        }
    };
    
    static List<DemoItemNode> chartDemoPlusIndex = new ArrayList<DemoItemNode>() {
      {
          add(new DemoDVTItemNode("Chart Index", "/feature/chart/index.jspx", "/adf/advanced_graph.png",
                               "feature.chartIndex", null, null, null, null, "/images/icons-large/barGraph.png",
                               null, false));
          addAll(chartDemoList);
      }
  };

    static List<DemoItemNode> pictoChartDemoPlusIndex = new ArrayList<DemoItemNode>() {
      {
          add(new DemoDVTItemNode("Picto Chart Index", "/feature/pictoChart/index.jspx", "/adf/pictochart.png",
                               "feature.pictoChartIndex", null, null, null, null, "/images/icons-large/pictoChart.png",
                               null, false));
          addAll(pictoChartDemoList);
      }
    };
  
    static List<DemoItemNode> gaugeDemoPlusIndex = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Gauge Index", "/feature/gauge/index.jspx", "/adf/gauge.png", "feature.gaugeIndex",
                                 null, null, null, null, "/images/icons-large/gauge.png", null, false));
            addAll(gaugeDemoList);
        }
    };
    
    static List<DemoItemNode> legacyGaugeDemoPlusIndex = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Legacy Gauge Index", "/feature/gauge/legacy/index.jspx", "/adf/gauge.png", "feature.legacyGaugeIndex",
                                 null, null, null, null, "/images/icons-large/gauge.png", null, false));
            addAll(legacyGaugeDemoList);
        }
    };
    
    static List<DemoItemNode> treemapDemoPlusIndex = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Treemap Index", "/feature/treemap/index.jspx", "/resources/images/icons/treemap.png",
                                 "feature.treemapIndex", null, null, null, null, "/images/icons-large/gauge.png",
                                 null, false));
            addAll(treemapDemoList);
        }
    };

    static List<DemoItemNode> sunburstDemoPlusIndex = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Sunburst Index", "/feature/sunburst/index.jspx", "/resources/images/icons/sunburst.png", "feature.sunburstIndex",
                                 null, null, null, null, "/images/icons-large/gauge.png", null, false));
            addAll(sunburstDemoList);
        }
    };

    static List<DemoItemNode> ganttDemoPlusIndex = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Gantt Index", "/feature/gantt/index.jspx", "/adf/gantt.png", "feature.ganttIndex",
                                 null, null, null, null, "/images/icons-large/projectGantt.png", null, false));
            add(new DemoDVTItemNode("Project Gantt", "/images/folder.png", projectGanttDemoList));
            add(new DemoDVTItemNode("Resource Utilization", "/images/folder.png", resourceGanttDemoList));
            add(new DemoDVTItemNode("Scheduling", "/images/folder.png", schedulingGanttDemoList));
        }
    };

    static List<DemoItemNode> hvDemoPlusIndex = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Hierarchy Viewer Index", "/feature/hv/index.jspx", "/adf/xmlSchema.png",
                                 "feature.hvIndex", null, null, null, null, "/images/icons-large/hierarchyViewer.png",
                                 null, false));
            addAll(hvDemoList);
        }
    };

    static List<DemoItemNode> pivotFilterBarDemoPlusIndex = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Pivot Filter Bar Index", "/feature/pivotFilterBar/index.jspx",
                                 "/adf/pivotfilterbar.png", "feature.pivotFilterBarIndex", null, null, null, null,
                                 "/images/icons-large/pivotFilterBar.png", null, false));
            Collections.sort(pivotFilterBarDemoList);
            addAll(pivotFilterBarDemoList);
        }
    };

    static List<DemoItemNode> pivotTableDemoPlusIndex = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Pivot Table Index", "/feature/pivotTable/index.jspx", "/adf/pivot_table.png",
                                 "feature.pivotTableIndex", null, null, null, null,
                                 "/images/icons-large/pivotTable.png", null, false));
            Collections.sort(pivotTableDemoList);
            addAll(pivotTableDemoList);
        }
    };

    static List<DemoItemNode> tmapDemoPlusIndex = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Thematic Map Index", "/feature/thematicMap/index.jspx", "/adf/thematicmap.png",
                                 "feature.tmapIndex", null, null, null, null, "/images/icons-large/thematicMap.png",
                                 null, false));
            addAll(tmapDemoList);
        }
    };

    static List<DemoItemNode> geoMapDemoPlusIndex = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Geographic Map Index", "/feature/geoMap/index.jspx", "/adf/geographic_map.png",
                                 "feature.geoMapIndex", null, null, null, null, "/images/icons-large/map.png", null, false));
            addAll(geoMapDemoList);
        }
    };

    static List<DemoItemNode> timelineDemoPlusIndex = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Timeline Index", "/feature/timeline/index.jspx", "/adf/timeline.png",
                                 "feature.timelineIndex", null, null, null, null,
                                 "/images/icons-large/timeline.png", null, false));
            Collections.sort(timelineDemoList);
            addAll(timelineDemoList);
        }
    };

    static List<DemoItemNode> diagramDemoPlusIndex = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Diagram Index", "/feature/diagram/index.jspx", "/adf/xmlSchema.png",
                                 "feature.diagramIndex", null, null, null, null, "/images/icons-large/hierarchyViewer.png",
                                 null, false));
            addAll(diagramDemoList);
        }
    };
    
  static List<DemoItemNode> nBoxDemoPlusIndex = new ArrayList<DemoItemNode>() {
      {
          add(new DemoDVTItemNode("NBox Index", "/feature/nBox/index.jspx", "/resources/images/icons/nBox.png",
                               "feature.nBoxIndex", null, null, null, null, null,
                               null, false));
          addAll(nBoxDemoList);
      }
  };
  
    static List<DemoItemNode> tagCloudDemoPlusIndex = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Tag Cloud Index", "/feature/tagCloud/index.jspx", "/resources/images/icons/tagCloud.png",
                                 "feature.tagCloudIndex", null, null, null, null, null,
                                 null, false));
            addAll(tagCloudDemoList);
        }
    };

    static List<DemoDVTCategoryItem> graphCategoryList = DemoDVTCategoryItem.createCategories(graphDemoList);
    
    static List<DemoDVTCategoryItem> legacyCategoryList = DemoDVTCategoryItem.createCategories(legacyDemoPlusIndex);
    
    static List<DemoDVTCategoryItem> chartCategoryList = DemoDVTCategoryItem.createCategories(chartDemoList);
    
    static List<DemoDVTCategoryItem> gaugeCategoryList = DemoDVTCategoryItem.createCategories(gaugeDemoList);

    static List<DemoDVTCategoryItem> legacyGaugeCategoryList = DemoDVTCategoryItem.createCategories(legacyGaugeDemoList); 
    
    static List<DemoDVTCategoryItem> pictoChartCategoryList = DemoDVTCategoryItem.createCategories(pictoChartDemoList);

    
    static List<DemoDVTCategoryItem> treemapCategoryList = DemoDVTCategoryItem.createCategories(treemapDemoList);
    
    static List<DemoDVTCategoryItem> sunburstCategoryList = DemoDVTCategoryItem.createCategories(sunburstDemoList);
    
    static List<DemoDVTCategoryItem> hvCategoryList = DemoDVTCategoryItem.createCategories(hvDemoList);
    
    static List<DemoDVTCategoryItem> tmapCategoryList = DemoDVTCategoryItem.createCategories(tmapDemoList);
    
    static List<DemoDVTCategoryItem> geoMapCategoryList = DemoDVTCategoryItem.createCategories(geoMapDemoList);
    
    static List<DemoDVTCategoryItem> pivotFilterBarCategoryList = DemoDVTCategoryItem.createCategories(pivotFilterBarDemoList);
    
    static List<DemoDVTCategoryItem> pivotTableCategoryList = DemoDVTCategoryItem.createCategories(pivotTableDemoList);

    static List<DemoDVTCategoryItem> ganttCategoryList = DemoDVTCategoryItem.createCategories(ganttDemoList);
    
    static List<DemoDVTCategoryItem> timelineCategoryList = DemoDVTCategoryItem.createCategories(timelineDemoList);

    static List<DemoDVTCategoryItem> diagramCategoryList = DemoDVTCategoryItem.createCategories(diagramDemoList);

    static List<DemoDVTCategoryItem> nBoxCategoryList = DemoDVTCategoryItem.createCategories(nBoxDemoList);
    
    static List<DemoDVTCategoryItem> tagCloudCategoryList = DemoDVTCategoryItem.createCategories(tagCloudDemoList);
    
    //
    // Feature Demo list
    //
    static List<DemoItemNode> featureDemoList = new ArrayList<DemoItemNode>() {
        {
            add(new DemoDVTItemNode("Geographic Map", "/images/folder.png", geoMapDemoPlusIndex));
            add(new DemoDVTItemNode("Chart", "/images/folder.png", chartDemoPlusIndex));
            add(new DemoDVTItemNode("Picto Chart", "/images/folder.png", pictoChartDemoPlusIndex));
            add(new DemoDVTItemNode("Gauge", "/images/folder.png", gaugeDemoPlusIndex));
            add(new DemoDVTItemNode("Sunburst", "/images/folder.png", sunburstDemoPlusIndex));
            add(new DemoDVTItemNode("Treemap", "/images/folder.png", treemapDemoPlusIndex));
            add(new DemoDVTItemNode("Pivot Filter Bar", "/images/folder.png", pivotFilterBarDemoPlusIndex));
            add(new DemoDVTItemNode("Pivot Table", "/images/folder.png", pivotTableDemoPlusIndex));
            add(new DemoDVTItemNode("Hierarchy Viewer", "/images/folder.png", hvDemoPlusIndex));
            add(new DemoDVTItemNode("Gantt", "/images/folder.png", ganttDemoPlusIndex));
            add(new DemoDVTItemNode("Thematic Map", "/images/folder.png", tmapDemoPlusIndex));
            add(new DemoDVTItemNode("Timeline", "/images/folder.png", timelineDemoPlusIndex));
            add(new DemoDVTItemNode("Diagram", "/images/folder.png", diagramDemoPlusIndex));
            add(new DemoDVTItemNode("NBox", "/images/folder.png", nBoxDemoPlusIndex));
            add(new DemoDVTItemNode("Tag Cloud", "/images/folder.png", tagCloudDemoPlusIndex));
            add(new DemoDVTItemNode("Legacy", "/images/folder.png", legacyDemoPlusIndex));
        }
    };

    static List<DemoItemNode> getTagGuideList() {
        Collections.sort(tagGuideList);
        return tagGuideList;
    }

    static List<DemoItemNode> getFeatureDemoList() {
        List<DemoItemNode> list = new ArrayList<DemoItemNode>(featureDemoList);  
        Collections.sort(list);
        list.add(0, new DemoDVTItemNode("Retirement Calculator", "/feature/dataVisualization/retirement.jspx", "/adf/advanced_graph.png",
                               "feature.retirementCalculator"));
        list.add(0, new DemoDVTItemNode("Election Across Time", "/feature/dataVisualization/electionAcrossTime.jspx", "/adf/advanced_graph.png",
                               "feature.electionAcrossTime"));
        list.add(0, new DemoDVTItemNode("Election Across Components", "/feature/dataVisualization/electionAcrossComponents.jspx", "/adf/advanced_graph.png",
                               "feature.electionAcrossComponents"));
        list.add(0, new DemoDVTItemNode("Data Visualization Featured Demos", "/feature/dataVisualization/featuredDemos.jspx", "/adf/advanced_graph.png",
                                     "feature.dataVisualizationFeaturedDemos", _DATA_VISUALIZATION_GALLERY_DESC, GENERAL_FEATURES, "Data Visualization", ""));
        list.add(0, new DemoDVTItemNode("Data Visualization Components", "/feature/dataVisualization/components.jspx", "/adf/advanced_graph.png",
                                     "feature.dataVisualizationComponents", _DATA_VISUALIZATION_GALLERY_DESC, GENERAL_FEATURES, "Data Visualization", ""));
        list.add(0, new DemoDVTItemNode("Data Visualization Overview", "/feature/dataVisualization/overview.jspx", "/adf/advanced_graph.png",
                                     "feature.dataVisualizationOverview", _DATA_VISUALIZATION_GALLERY_DESC, GENERAL_FEATURES, "Data Visualization", ""));
        return list;
    }


    /**
     * Returns a list of feature demos for the Chart component
     */
    public static List<DemoItemNode> getChartDemoList() {
        return chartDemoList;
    }
    
    /**
     * Returns a list of feature demos for the Chart component
     */
    public static List<DemoItemNode> getPictoChartDemoList() {
        return pictoChartDemoList;
    }
  
    /**
     * Returns a list of feature demos for the Graph component
     */
    public static List<DemoItemNode> getGraphDemoList() {
        return graphDemoList;
    }

    /**
     * Returns a list of feature demos for the Gauge component
     */
    public static List<DemoItemNode> getGaugeDemoList() {
        return gaugeDemoList;
    }
    
    /**
     * Returns a list of feature demos for the legacy Gauge component
     */
    public static List<DemoItemNode> getLegacyGaugeDemoList() {
        return legacyGaugeDemoList;
    }
    
    /**
     * Returns a list of feature demos for the Treemap component
     */
    public static List<DemoItemNode> getSunburstDemoList() {
        return sunburstDemoList;
    }

    /**
     * Returns a list of feature demos for the Treemap component
     */
    public static List<DemoItemNode> getTreemapDemoList() {
        return treemapDemoList;
    } 
    
    /**
     * Returns a list of feature demos for the Gantt component
     */
    public static List<DemoItemNode> getGanttDemoList() {
        return ganttDemoList;
    }

    /**
     * Returns a list of feature demos for the hv component
     */
    public static List<DemoItemNode> getHvDemoList() {
        return hvDemoList;
    }

    /**
     * Returns a list of feature demos for the tmap component
     */
    public static List<DemoItemNode> getTmapDemoList() {
        return tmapDemoList;
    }

    /**
     * Returns a list of feature demos for the geomap component
     */
    public static List<DemoItemNode> getGeoMapDemoList() {
        return geoMapDemoList;
    }

    /**
     * Returns a list of feature demos for the Pivot Table component
     */
    public static List<DemoItemNode> getPivotTableDemoList() {
        return pivotTableDemoList;
    }

    /**
     * Returns a list of feature demos for the Pivot Filter Bar component
     */
    public static List<DemoItemNode> getPivotFilterBarDemoList() {
        return pivotFilterBarDemoList;
    }

    /**
     * Returns a list of feature demos for the Timeline component
     */
    public static List<DemoItemNode> getTimelineDemoList() {
        return timelineDemoList;
    }
    
    /**
     * Returns a list of feature demos for the Diagram component
     */
    public static List<DemoItemNode> getDiagramDemoList() {
        return diagramDemoList;
    } 
  
    /**
     * Returns a list of feature demos for the NBox component
     */
    public static List<DemoItemNode> getNBoxDemoList() {
        return nBoxDemoList;
    } 
    
    /**
     * Returns a list of feature demos for the TagCloud component
     */
    public static List<DemoItemNode> getTagCloudDemoList() {
      return tagCloudDemoList;
    }

    /**
     * Returns a list of feature demos for the Chart component
     */
    public static List<DemoDVTCategoryItem> getChartCategoryList() {
        return chartCategoryList;
    }
  
    /**
     * Returns a list of feature demos for the Graph component
     */
    public static List<DemoDVTCategoryItem> getGraphCategoryList() {
        return graphCategoryList;
    }

    /**
     * Returns a list of feature demos for the Gauge component
     */
    public static List<DemoDVTCategoryItem> getGaugeCategoryList() {
        return gaugeCategoryList;
    }
    
    /**
     * Returns a list of feature demos for the Gauge component
     */
    public static List<DemoDVTCategoryItem> getLegacyGaugeCategoryList() {
        return legacyGaugeCategoryList;
    }
    
    /**
     * Returns a list of feature demos for the Picto Chart component
     */
    public static List<DemoDVTCategoryItem> getPictoChartCategoryList() {
        return pictoChartCategoryList;
    }
    
    /**
     * Returns a list of feature demos for the Treemap component
     */
    public static List<DemoDVTCategoryItem> getSunburstCategoryList() {
        return sunburstCategoryList;
    }

    /**
     * Returns a list of feature demos for the Treemap component
     */
    public static List<DemoDVTCategoryItem> getTreemapCategoryList() {
        return treemapCategoryList;
    } 

    /**
     * Returns a list of feature demos for the hv component
     */
    public static List<DemoDVTCategoryItem> getHvCategoryList() {
        return hvCategoryList;
    }

    /**
     * Returns a list of feature demos for the tmap component
     */
    public static List<DemoDVTCategoryItem> getTmapCategoryList() {
        return tmapCategoryList;
    }

    /**
     * Returns a list of feature demos for the geomap component
     */
    public static List<DemoDVTCategoryItem> getGeoMapCategoryList() {
        return geoMapCategoryList;
    }

    /**
     * Returns a list of feature demos for the Pivot Table component
     */
    public static List<DemoDVTCategoryItem> getPivotTableCategoryList() {
        return pivotTableCategoryList;
    }

    /**
     * Returns a list of feature demos for the Pivot Filter Bar component
     */
    public static List<DemoDVTCategoryItem> getPivotFilterBarCategoryList() {
        return pivotFilterBarCategoryList;
    }

    /**
     * Returns a list of feature demos for the Gantt component
     */
    public static List<DemoDVTCategoryItem> getGanttCategoryList() {
        return ganttCategoryList;
    }

     /**
     * Returns a list of feature demos for the Timeline component
     */
    public static List<DemoDVTCategoryItem> getTimelineCategoryList() {
        return timelineCategoryList;
    }

    /**
     * Returns a list of feature demos for the Diagram component
     */
    public static List<DemoDVTCategoryItem> getDiagramCategoryList() {
        return diagramCategoryList;
    } 
  
    /**
     * Returns a list of feature demos for the NBox component
     */
    public static List<DemoDVTCategoryItem> getNBoxCategoryList() {
        return nBoxCategoryList;
    }
    
    /**
     * Returns a list of feature demos for the TagCloud component
     */
    public static List<DemoDVTCategoryItem> getTagCloudCategoryList() {
        return tagCloudCategoryList;
    }
    
    public static final String GENERAL_FEATURES = "General Features";  // Top Priority
    public static final String INTERACTIVITY_FEATURES = "Interactivity Features"; // Second Priority
    public static final String ADVANCED_FEATURES = "Advanced Features"; // Third Priority
    public static final String STAMPING_FEATURES = "Stamping Features"; // Fourth Priority
    
    private static final String _DATA_VISUALIZATION_GALLERY_DESC = "Shows a gallery of available data visualizations and demos";

    //Pivot Filter Bar Demo Node Labels
    public static final String PIVOT_FILTER_BAR_TAG_LABEL = "PivotFilterBar";
    public static final String CLIENT_BEHAVIOR_PIVOT_FILTER_BAR_LABEL = "Client Behavior Demo";
    public static final String CONTENT_DELIVERY_PIVOT_FILTER_BAR_LABEL = "Content Delivery Demo";
    public static final String GRAPH_AND_PIVOT_TABLE_PIVOT_FILTER_BAR_LABEL =
        "Pivot Filter Bar With Pivot Table And Graph Demo";
    public static final String MODEL_NAME_PIVOT_FILTER_BAR_LABEL = "Pivot Filter Bar With Multiple Pivot Tables Demo";

    //Pivot Table Demo Node Labels
    public static final String PIVOT_TABLE_TAG_LABEL = "PivotTable";
    public static final String ACTIVE_CELL_STAMPING_PIVOT_TABLE_LABEL = "Active Cell Key";
    public static final String ADJUSTABLE_PIVOT_TABLE_LABEL = "Adjustable Pivot Table";
    public static final String LAYER_LABEL_PIVOT_TABLE_LABEL = "Layer Labels";
    public static final String CELL_FORMATTING_PIVOT_TABLE_LABEL = "Cell Formatting";
    public static final String CLIENT_BEHAVIOR_PIVOT_TABLE_LABEL = "Client Behavior";
    public static final String CSV_ROWSET_PIVOT_TABLE_LABEL = "CSV Rowset Demo";
    public static final String DATA_BARS_PIVOT_TABLE_LABEL = "Data Bars";
    public static final String DRILLABLE_PIVOT_TABLE_LABEL = "Drillable Pivot Table";
    public static final String EDITABLE_PIVOT_TABLE_LABEL = "Editable Pivot Table";
    public static final String COMPLEX_STAMPS_PIVOT_TABLE_LABEL = "Pivot Table with multiple stamp components";
    public static final String EXPORT_TO_EXCEL_PIVOT_TABLE_LABEL = "Export to Excel";
    public static final String GAUGE_STAMPED_PIVOT_TABLE_LABEL = "Pivot Table stamped with Gauges";
    public static final String GRAPH_STAMPED_PIVOT_TABLE_LABEL = "Pivot Table stamped with Graphs";
    public static final String HEADER_CELL_STAMPING_PIVOT_TABLE_LABEL = "Header Cell Stamping Demo";
    public static final String HEAT_MAP_PIVOT_TABLE_LABEL = "Heat Map";
    public static final String MEMBER_FORMATTING_PIVOT_TABLE_LABEL = "Member based formatting";
    public static final String SELECTION_PIVOT_TABLE_LABEL = "Selection";
    public static final String SPARK_CHART_STAMPED_PIVOT_TABLE_LABEL = "Member stamped with SparkCharts";
    public static final String TOTALS_PIVOT_TABLE_LABEL = "Pivot Table Totals";
    public static final String WHEN_AVAILABLE_PIVOT_TABLE_LABEL = "whenAvailable Content Delivery";
    public static final String HEADER_CELL_WRAPPING_PIVOT_TABLE_LABEL = "Header Cell Wrapping";
    public static final String CLIENT_LISTENER_PIVOT_TABLE_LABEL = "Client Listener And Server Listener";

    //Gantt Demo Node Labels
    public static final String PROJECT_GANTT_TAG_LABEL = "ProjectGantt";
    public static final String RESOURCE_GANTT_TAG_LABEL = "ResourceUtilizationGantt";
    public static final String SCHEDULING_GANTT_TAG_LABEL = "SchedulingGantt";
    public static final String PROJECT_GANTT_CLICK_TO_EDIT_LABEL = "Click to Edit";
    public static final String PROJECT_GANTT_TABLE_ACTIVE_ROW_KEY = "Table Active Row Key";
    public static final String PROJECT_GANTT_CUSTOM_MENU_AND_TOOLBAR_LABEL = "Custom Menu and Toolbar";
    public static final String PROJECT_GANTT_DOUBLE_CLICK_LABEL = "Double Click";
    public static final String PROJECT_GANTT_EDITABLE_TASKS_LABEL = "Editable Tasks";
    public static final String PROJECT_GANTT_SCROLL_SYNC_LABEL = "Synced Scrolling";
    public static final String PROJECT_GANTT_ZOOM_TO_FIT_LABEL = "Zoom To Fit";
    public static final String PROJECT_GANTT_DRAG_SOURCE_LABEL = "Drag Source";
    public static final String PROJECT_GANTT_FEATURES_OFF_LABEL = "Features Off";
    public static final String PROJECT_GANTT_CUSTOMIZE_MENUS_LABEL = "Customize Menus";
    public static final String PROJECT_GANTT_CUSTOM_MENU_TOOLBAR_LAYOUT_LABEL = "Custom Menu Toolbar";
    public static final String PROJECT_GANTT_CUSTOM_PRINTING_LABEL = "Custom Printing";
    public static final String PROJECT_GANTT_TASKBAR_FORMAT_LABEL = "Taskbar Format";
    public static final String PROJECT_GANTT_INITIAL_EXPAND_ALL_LABEL = "Initially Expand All";
    public static final String PROJECT_GANTT_GENERIC_CONVERTER_LABEL = "Generic Converter";
    public static final String PROJECT_GANTT_EXPORT_TO_EXCEL_LABEL = "Export to Excel";
    public static final String PROJECT_GANTT_CLIENT_BEHAVIOR_LABEL = "Client Behavior";
    public static final String PROJECT_GANTT_ATTRIBUTE_CONVERTER_FACTORY_LABEL = "Attribute Converter Factory";
    public static final String PROJECT_GANTT_LABEL_ICON_PLACEMENT_LABEL = "Label and Icon Placement";
    public static final String PROJECT_GANTT_CLIENT_LISTENER_LABEL = "Client Listener";
    public static final String PROJECT_GANTT_SERVER_LISTENER_LABEL = "Server Listener";
    public static final String SCHEDULING_GANTT_BACKGROUND_BARS_LABEL = "Background Bars";
    public static final String SCHEDULING_GANTT_CUSTOM_CONTEXT_MENU_LABEL = "Custom Context Menus";
    public static final String SCHEDULING_GANTT_DROP_TARGET_LABEL = "As a Drop Target";
    public static final String SCHEDULING_GANTT_SELECTION_LABEL = "Selection";
    public static final String SCHEDULING_GANTT_RESOURCE_ACTION_LABEL = "Resource Action";
    public static final String SCHEDULING_GANTT_TASKBAR_FORMAT_LABEL = "Taskbar Format";
    public static final String SCHEDULING_GANTT_CUSTOM_TIME_SCALE_LABEL = "Custom Timescales";
    public static final String SCHEDULING_GANTT_INITIAL_DATE_FOCUS_LABEL = "Initial Date Focus";
    public static final String SCHEDULING_GANTT_GO_TO_DATE_CONVERTER_LABEL = "'Go To Date' Converter";
    public static final String SCHEDULING_GANTT_TIME_ZONE_LABEL = "Time Zone";
    public static final String SCHEDULING_GANTT_SORT = "Scheduling Gantt Sort";

    public static final String RESOURCE_GANTT_DOUBLE_CLICK_LABEL = "Double Click";
    public static final String RESOURCE_GANTT_METRIC_CONVERTER_LABEL = "Metric Converter";
    public static final String RESOURCE_GANTT_SELECTION_LABEL = "Selection";
    public static final String RESOURCE_GANTT_STACKING_LABEL = "Stacking";
    public static final String RESOURCE_GANTT_STEPPED_LINE_LABEL = "Stepped Line";
    public static final String RESOURCE_GANTT_ATTRIBUTE_DETAIL_LABEL = "Attribute Detail";

    // Gauge feature demo node labels
    public static final String GAUGE_GALLERY_LABEL = "Gallery";
    public static final String GAUGE_DIAL_STATUSMETER_LABEL = "Animation for Dial and Statusmeter";
    public static final String GAUGE_CUSTOM_SHAPES_LABEL = "Custom Shapes";
    public static final String GAUGE_MULTIPLE_INDICATORS_LABEL = "Multiple Indicators";
    public static final String GAUGE_TICK_MARKS_LABEL = "Tick Marks";
    public static final String GAUGE_IN_TABLE_LABEL = "Gauge in Table";
    public static final String GAUGE_SET_LABEL = "Gauge Set";

    //Demo Node Descriptions
    private static final String _GRAPH_ALERT_DESC = "Shows alert objects in different graph types.";
    private static final String _GRAPH_ANIMATION_DESC = "Shows animation effects for graph data objects.";
    private static final String _GRAPH_ANIMATION2_DESC = "Shows animation effects for graphs.";
    private static final String _ANNOTATIONS_DESC =
        "Shows annotations, which are used to highlight data points in the graph";
    private static final String _BIDI_DESC = "Shows graphs current BIDI support";
    private static final String _GRAPH_CLICK_DESC =
        "Shows click events (handled server-side) and mouse over events (handled via javascript)";
    private static final String _CONDITIONAL_FORMATTING_DESC =
        "Shows a graph that is formatted based on its data values";
    private static final String _CONTEXT_MENU_DESC = "Shows a graph with context menus";
    private static final String _CUSTOM_TOOLTIP_CALLBACK_DESC = "Shows a graph with user specified tooltips";
    private static final String _DRAG_AND_DROP_DESC = "Shows graph support for drag and drop";
    private static final String _DRILLING_DESC = "Shows graph drilling and Demonstrates drill listeners";
    private static final String _DYNAMIC_RESIZE_DESC = "Shows graphs that resize to their containers";
    private static final String _HIDE_SHOW_SERIES_DESC = "Shows hide and show series interactivity";
    private static final String _LINE_DATA_CURSOR_DESC = "Shows line data cursor feature";
    private static final String _SERIES_ROLLOVER_DESC = "Shows series rollover interactivity";
    private static final String _EMPTY_TEXT_DESC = "Shows the empty text feature";
    private static final String _GRAPH_GALLERY_DESC = "Shows different graph types and configurations";
    private static final String _GRAPH_TYPES_DESC = "Shows the available graph types";
    private static final String _INTERACTIVE_PIE_SLICE_DESC = "Shows pie slice interactivity";
    private static final String _MARKER_SHAPES_COLORS_DESC =
        "Uses custom data-driven shapes and colors for scatter graph markers";
    private static final String _POPUP_SUPPORT_DESC = "Shows graph and gauge support for showing popups";
    private static final String _PIE_CHARTS_DESC = "Shows features of pie charts and different customizations";
    private static final String _REF_OBJECTS_DESC = "Shows a graph with interactive reference objects";
    private static final String _SPECIALIZED_GRAPHS_DESC = "Shows different specialized graphs";
    private static final String _SCROLLABLE_LEGEND_DESC = "Shows a graph with a scrolling legend";
    private static final String _GRAPH_NUMBER_FORMAT_DESC = "Customizes a graph with convert number";
    private static final String _SELECTION_DESC = "Shows graph support for data selection";
    private static final String _SERIES_CUSTOMIZATION_DESC =
        "Shows how to customize the series properties, such as line and marker styles";
    private static final String _SPARK_CHARTS_DESC = "Shows different spark chart types and configurations";
    private static final String _TICK_LABEL_CALLBACK_DESC = "Shows a graph with custom tick labels and tick marks";
    private static final String _TIME_SELECTOR_DESC =
        "Shows a master detail graph relationship using the time selector";
    private static final String _TIME_AXIS_DESC =
        "Illustrates different types of time axis types and how they can be used";
    private static final String _TIME_AXIS_RT_DESC = "Shows a real time time axis chart";
    private static final String _ZOOM_SCROLL_DESC = "Shows the zoom and scroll interactivity of the graph";
    private static final String _GAUGE_GALLERY_DESC = "Shows different gauge types and configurations";
    private static final String _DIAL_STATUS_METER_DESC = "Shows several gauges that animate on changes in data";
    private static final String _CUSTOM_SHAPES_DESC =
        "Shows several custom gauges built using the custom shapes feature";
    private static final String _MULTIPLE_INDICATORS_DESC = "Shows a dial gauge set with multiple indicators";
    private static final String _TICK_MARKS_DESC = "Shows several gauges with custom tick marks";
    private static final String _GAUGE_IN_TABLE_DESC = "Shows gauge in table";
    private static final String _GAUGE_SET_DESC = "Shows gauge set";
    private static final String _PIVOT_FILTER_BAR_GRAPH_AND_TABLE_DESC =
        "Shows a Pivot Filter Bar interacting with a Pivot Table and Bar Graph";
    private static final String _PIVOT_FILTER_BAR_MULTIPLE_TABLES_DESC =
        "Shows a Pivot Filter Bar with multiple Pivot Tables";
    private static final String _PIVOT_FILTER_BAR_WHEN_AVAILABLE_DESC =
        "Shows a Pivot Filter Bar with contentDelivery set to 'whenAvailable'";
    private static final String _PIVOT_FILTER_BAR_CLIENT_BEHAVIOR_DESC =
        "Shows a Pivot Filter Bar with a client behavior tag";
    private static final String _PIVOT_TABLE_CSV_ROWSET_DESC =
        "Shows how to load a data from a csv file into the Pivot Table";
    private static final String _PIVOT_TABLE_HEADER_CELL_STAMPING_DESC =
        "Shows how to use header cell stamping in the Pivot Table";
    private static final String _PIVOT_TABLE_ADJUSTABLE_DESC = "Shows a pivot table whose number of rows, columns, and layers can be configured by the user";
    private static final String _PIVOT_TABLE_LAYER_LABEL_DESC = "Shows a pivot table with persistent layer labels";
    private static final String _PIVOT_TABLE_DATA_BARS_DESC = "Shows a Pivot Table with data bars in cells";
    private static final String _PIVOT_TABLE_DRILLABLE_DESC = "Shows a Pivot Table with drilling";
    private static final String _PIVOT_TABLE_EDIT_DESC = "Shows a Pivot Table with editable cells";
    private static final String _PIVOT_TABLE_COMPLEX_STAMPS_DESC = "Shows a Pivot Table with cells containing multiple components";
    private static final String _PIVOT_TABLE_HEAT_MAP_DESC =
        "Shows a Pivot Table with cells color-coded as a heat map";
    private static final String _PIVOT_TABLE_SELECTION_DESC = "Shows how to use Pivot Table selection";
    private static final String _PIVOT_TABLE_TOTALS_DESC = "Shows a Pivot Table with totals and formatting";
    private static final String _PIVOT_TABLE_EXPORT_DESC =
        "Shows how to Export all or a selected set of cells from the Pivot Table";
    private static final String _PIVOT_TABLE_GAUGE_DESC = "Shows a Pivot Table stamped with gauges in each cell";
    private static final String _PIVOT_TABLE_GRAPH_DESC = "Shows a Pivot Table stamped with Graphs in the Total cells";
    private static final String _PIVOT_TABLE_CELL_FORMATTING_DESC =
        "Shows a Pivot Table where data cells and headers cells are declaratively formatted via their inlineStyle and styleClass attributes";
    private static final String _PIVOT_TABLE_MEMBER_FORMATTING_DESC =
        "Shows a Pivot Table where data is declaratively formatted based on member values";
    private static final String _PIVOT_TABLE_SPARKCHART_DESC = "Shows a Pivot Table stamped with Trend Sparkcarts";
    //    private static final String _PIVOT_TABLE_ROWSET_DRILL_DESC = "Shows a Pivot Table with drilling via a rowset data control";
    private static final String _PIVOT_TABLE_WHEN_AVAILABLE_DESC =
        "Shows a Pivot Table with contentDelivery set to whenAvailable";
    private static final String _PIVOT_TABLE_ACTIVE_CELL_KEY_DESC =
        "Shows a Pivot Table using active cell key to determine stamped component";
    private static final String _PIVOT_TABLE_CLIENT_BEHAVIOR_DESC = "Shows a Pivot Table with client behavior tag";
    private static final String _PIVOT_TABLE_HEADER_CELL_WRAPPING_DESC = "Shows a Pivot Table with header cell wrapping enabled";
    private static final String _PIVOT_TABLE_CLIENT_LISTENER_DESC = "Shows a Pivot Table with client listener and server listener tags";
    
    // Chart descriptions
    private static final String _CHART_VS_GRAPH_DESC = "Shows chart functionality compared to the functionality of the legacy graph components";
    private static final String _CHART_DATA_LABELS_DESC = "Shows data labeling for charts";
    private static final String _CHART_GALLERY_DESC = "Shows different chart types and configurations"; 
    private static final String _CHART_SMALL_DESC = "Shows chart resize and small form";
    private static final String _CHART_HIDE_SHOW_SERIES_DESC = "Shows chart hide and show series feature";
    private static final String _CHART_REFERENCE_OBJECTS_DESC = "Shows chart reference object feature";
    private static final String _CHART_STYLE_CUSTOMIZATION_DESC = "Shows different style customization options for chart series and data items";
    private static final String _CHART_SERIES_EFFECT_DESC = "Shows different chart series effects";
    private static final String _CHART_SCROLLABLE_LEGEND_DESC = "Shows chart scrollable legend feature";
    private static final String _CHART_SELECTION_DESC = "Shows chart selection capabilities";
    private static final String _CHART_TIME_AXIS_DESC = "Shows chart time axis";
    private static final String _CHART_ZOOM_AND_SCROLL_DESC = "Shows chart zoom and scroll";
    private static final String _CHART_DATA_CURSOR_DESC = "Shows data cursor feature";
    private static final String _CHART_NUMBER_FORMAT_DESC = "Shows chart number formatting";
    private static final String _CHART_CATEGORICAL_AXIS_STYLING_DESC = "Shows different style customization options for chart categorical axis groups";
    private static final String _CHART_HIERARCHICAL_LABELING_DESC = "Shows how to create multiple levels of labels for the categorical axis"; 
    private static final String _CHART_LOG_SCALE_DESC = "Shows how to use the log scale on the chart axes"; 
    
    private static final String _CHART_DRILLING_DESC = "Shows chart drilling capabilities";
    private static final String _CHART_TOOLTIP_FORMATTING_DESC = "Shows chart tooltip formatting capabilities";    
    private static final String _CHART_ACTION_DESC = "Shows chart action handling";
   
    
    private static final String _GANTT_CLICK_TO_EDIT_DESC = "Enable click to edit on the table side of the Gantt";
    private static final String _GANTT_TABLE_ACTIVE_ROW_KEY = "Shows how to use the tableActiveRowKey property";
    private static final String _GANTT_MENU_TOOLBAR_DESC =
        "Add menus and buttons on the Project Gantt Menu and Toolbar";
    private static final String _GANTT_DOUBLE_CLICK_DESC = "Add a double-click listener on the Gantt";
    private static final String _GANTT_EDITABLE_TASKS_DESC = "Show a gantt with some tasks editable and some not";
    private static final String _GANTT_SCROLL_SYNC_DESC = "Shows how multiple Gantt components can have their chart scrolling synced";
    private static final String _GANTT_ZOOM_TO_FIT_DESC = "Add a zoom-to-fit listener on the Gantt";
    private static final String _GANTT_DRAG_SOURCE_DESC =
        "Configure the Project Gantt to allow task being dragged from the Gantt to a Table.";
    private static final String _GANTT_FEATURES_OFF_DESC =
        "Shows how features such as linking can be switched on and off";
    private static final String _GANTT_BACKGROUND_BARS_DESC = "Shows how to add background bars to the Scheduling Gantt";
    private static final String _GANTT_RESOURCE_ACTION_DESC = "Shows how to use the ResourceActionEvent to add a background bar to an empty space, corresponding to a unit of the minor time axis, in the Scheduling Gantt";
    private static final String _GANTT_CUSTOMIZE_MENUS_DESC = "Shows how menu items can be switched on and off";
    private static final String _GANTT_PRINTING_DESC = "Show how to print a Project Gantt using XML Publisher";
    private static final String _GANTT_EXPAND_ALL_DESC = "Shows how to declaratively show all Tasks expanded";
    private static final String _GANTT_TASKBAR_FORMAT_DESC = "Shows how to change the Taskbar formats of the bars";
    private static final String _GANTT_CONTEXT_MENUS_DESC = "Add custom context menus to the Scheduling Gantt";
    private static final String _GANTT_DROP_TARGET_DESC =
        "Configure the Scheduling Gantt so Table rows can be dropped on it.";
    private static final String _GANTT_SELECTION_DESC = "Add selection listener to the Scheduling Gantt";
    private static final String _GANTT_CUSTOM_TIMESCALE_DESC = "Shows a Gantt with Custom timescales";
    private static final String _GANTT_INITIAL_DATE_FOCUS_DESC =
        "Shows how to set the initial time focus of the Gantt";
    private static final String _GANTT_SORT_DESC =
        "Shows how to sort columns in Scheduling Gantt";
    private static final String _RU_GANTT_DBL_CLICK_DESC =
        "Add a double-click listener on the Resource Utilization Gantt";
    private static final String _RU_GANTT_METRIC_CONVERTER_DESC =
        "Shows a Resource Utilization Gantt with the metricConverter tag";
    private static final String _RU_GANTT_SELECTION_DESC = "Add selection listener to the Resource Utilization Gantt";
    private static final String _RU_GANTT_STACKING_DESC = "Shows how to stack the utilization bars";
    private static final String _RU_GANTT_STEPPED_LINE_DESC = "Shows how to display stepped line in the Resource Utilization Gantt";
    private static final String _RU_GANTT_ATTRIBUTE_DETAIL_DESC = "Shows how to display attribute details in the Resource Utilization Gantt";
    private static final String _GANTT_WHEN_AVAILABLE_DESC =
        "Shows a Project Gantt with contentDelivery set to whenAvailable";
    //    private static final String _GANTT_AUTO_PPR = "Shows AutoPPR on Project Gantt";
    private static final String _GANTT_GENERIC_CONVERTER_DESC =
        "Project Gantt with non-java dates, being converted using GenericConverter";
    private static final String _GANTT_EXPORT_TO_EXCEL_DESC = "Project Gantt Export to Excel";
    private static final String _GANTT_CLIENT_BEHAVIOR_DESC = "Shows a Project Gantt with client behavior tag";
    private static final String _GANTT_ATTRIBUTE_CONVERTER_FACTORY_DESC =
        "Shows a Project Gantt with the attributeConverterFactory tag";
    private static final String _GANTT_LABEL_ICON_PLACEMENT_DESC =
        "Shows a Project Gantt with the labelPlacement and the iconPlacement property";
    private static final String _GANTT_GO_TO_DATE_CONVERTER_DESC =
        "Shows a Scheduling Gantt with the goToDateConverter tag";
    private static final String _GANTT_TIME_ZONE_DESC = "Shows a Scheduling Gantt with the timeZone tag";
    private static final String _GANTT_CLIENT_LISTENER_DESC = "Add a client listener on the Gantt";
    private static final String _GANTT_SERVER_LISTENER_DESC = "Add a server listener on the Gantt";
    
    private static final String _HV_ANCHOR_DESC = "Enable Anchor Listener to enable drilling";
    private static final String _HV_LATERAL_NAV_DESC =
        "Enable lateral navigation by setting the 'levelDisplaySize' property";
    private static final String _HV_MODAL_POPUP_DESC = "Display modal and non-modal popup dialogs";
    private static final String _HV_SEARCH_DESC = "Demonstrates search capabilities within an HV";
    private static final String _HV_FEATURES_DESC = "Configure an HV with commonly used features";
    private static final String _HV_PANEL_CARD_DESC = "Demonstrates panel card";
    private static final String _HV_DRAG_AND_DROP_DESC = "Demonstrates node drag and drop";
    private static final String _HV_ZOOMING_DESC = "Demonstrates Zoom facets";

    private static final String _GEOMAP_POINT_THEME_DESC =
        "Demonstrates programmatic control of a GeoMap's point theme, via a backing bean. ";
    private static final String _GEOMAP_BASE_MAPS_DESC =
        "Demonstration of using different base maps: color theme based on states, countries, and continents. ";
    private static final String _GEOMAP_COLOR_LIST_DESC = "Shows a color theme with discrete color list. ";
    private static final String _GEOMAP_BAR_STYLES_DESC = "Shows a bar graph theme with customized bar colors. ";
    private static final String _GEOMAP_POINT_DESC = "Shows a point theme with custom images for each point. ";
    private static final String _GEOMAP_INFO_WINDOW_DESC = "Shows how to create a custom info window. ";
    private static final String _GEOMAP_PIE_STYLE_DESC = "Shows a pie graph theme with customized pie slice colors.";
    private static final String _GEOMAP_POINT_CALLBACK_DESC =
        "Shows a point theme with custom HTML displayed at each point.";
    private static final String _GEOMAP_GEOCODING_DESC = "Shows a point theme made up of geocoded addresses.";
    private static final String _GEOMAP_LEGEND_DESC = "Shows how to customize the legend labels.";
    private static final String _GEOMAP_POINT_CAT_BUCKET_DESC =
        "Shows point theme bucketing by category; images displayed for each point based on what category the point falls under";
    private static final String _GEOMAP_POINT_VAL_BUCKET_DESC =
        "Shows point theme bucketing by value; images displayed for each point based on what range its value falls under";
    private static final String _GEOMAP_POPUP_DESC =
        "Shows map with custom click behaviors; left click brings up custom popup, right click brings up custom context menu, hover brings up info window";
    private static final String _GEOMAP_SELECTION_DESC =
        "Shows Map theme selection events; use the selection tools in the toolbar to select items of the theme and test the selection event listener";
    private static final String _GEOMAP_ZOOM_LEVELS_DESC =
        "Shows a map with multiple themes (color, pie graph, and point) set to different zoom levels";
    private static final String _THEMATIC_MAP_FEATURES_DESC =
        "Configure a thematic map with commonly used features and built-in base maps.";
    private static final String _TMAP_COLOR_CODED_REGIONS_DESC =
        "Demonstrates coloring regions of a Thematic Map based on data values. ";
    private static final String _TMAP_CUSTOM_REGIONS_DESC =
        "Demonstrates the use of creating custom regions from built-in base map regions in a Thematic Map. ";
    private static final String _TMAP_ACTION_EVENTS_DESC = "Demonstrates click events on markers in a Thematic Map. ";
    private static final String _TMAP_POPUPS_DESC =
        "Demonstrates display of popups using af:showPopupBehavior on areas and markers. ";
    private static final String _TMAP_DND_DESC =
        "Demonstrates dragging an area or marker from the area data layer in Thematic Map to another ADF component. ";
    private static final String _TMAP_STYLES_DESC =
        "Demonstrates the use of styling attributes to override the default skin styling in a Thematic Map.";
    private static final String _TMAP_DRILLING_DESC = "Demonstrates drilling in thematic Map";
    private static final String _TMAP_KEYBOARD_DESC = "Demonstrates the Thematic Map's keyboard model.";
    private static final String _TMAP_ATTRIBUTE_GROUPS_DESC =
        "Demonstrates simple examples of using Attribute Groups with the Thematic Map";
    private static final String _TMAP_CUSTOM_POINTS_DESC =  "Demonstrates the use of a custom basemap with points";
    private static final String _TMAP_ELECTION_DESC =  "Demonstrates the use of isolatedRowKey and disclosedRowKeys";
    private static final String _TMAP_GLOBAL_GDP =  "Demonstrates the use of area layer styling to hide regions and  display only area layer markers";
    private static final String _TMAP_FLIGHT_TRACKER =  "Demonstrates the use of marker rotation and marker images using the marker source attributes";
    private static final String _TMAP_TERRITORIES =  "Demonstrates how to display sales territories using drilling and drilledRowKeys";
    private static final String _TMAP_LEGEND_DESC = "Demonstrates the different ways to configure the Legend within a Thematic Map";
    private static final String _TMAP_MAP_PROVIDER = "Demonstrates how to use the MapProvider APIs to generate a custom basemap";
    private static final String _TMAP_OLYMPIC_HOSTS_DESC = "Demonstrates small form factor Thematic Maps and how to use Thematic Map in associated views";
    private static final String _TMAP_CONTEXT_MENUS =  "Demonstrates how to use context menus in Thematic Map";
    
    private static final String _SUNBURST_FEATURES_DESC = "Configure a sunburst with commonly used features";
    private static final String _SUNBURST_ATTRIBUTE_GROUPS_DESC = "Demonstrates a sunburst with attribute groups";
    private static final String _SUNBURST_CONTEXT_MENU_DESC = "Demonstrates a sunburst with context menus";
    private static final String _SUNBURST_DRAG_AND_DROP_DESC = "Demonstrates a sunburst with drag and drop support";
    private static final String _SUNBURST_DRILLING_DESC = "Demonstrates a sunburst with drilling";
    private static final String _SUNBURST_MULTI_ROOT_DESC = "Demonstrates a sunburst with multiple rows in the root collection";
    private static final String _SUNBURST_OTHER_NODE_DESC = "Demonstrates a sunburst with aggregated other nodes";
    private static final String _SUNBURST_PATTERN_FILL_DESC = "Demonstrates a sunburst with pattern fill";
    private static final String _SUNBURST_POPUPS_DESC = "Demonstrates a sunburst with popups";
    private static final String _SUNBURST_ROOT_CONTENT_DESC = "Demonstrates sunburst with advanced root node content";
    private static final String _SUNBURST_ROTATION_DESC = "Demonstrates sunburst rotation interactivity";
    private static final String _SUNBURST_SMALL_FORM_FACTOR_DESC = "Demonstrates small form factor sunburst layout";
    private static final String _SUNBURST_UNEVEN_HIERARCHY_DESC = "Demonstrates a sunburst with uneven hierarchical data";
    
    private static final String _TREEMAP_FEATURES_DESC = "Configure a treemap with commonly used features";
    private static final String _TREEMAP_ATTRIBUTE_GROUPS_DESC = "Demonstrates a treemap with attribute groups";
    private static final String _TREEMAP_CONTEXT_MENU_DESC = "Demonstrates a treemap with context menus";
    private static final String _TREEMAP_DRAG_AND_DROP_DESC = "Demonstrates a treemap with drag and drop support";
    private static final String _TREEMAP_DRILLING_DESC = "Demonstrates a treemap with drilling";
    private static final String _TREEMAP_ISOLATE_DESC = "Demonstrates a treemap with group isolate support";
    private static final String _TREEMAP_LAYOUT_DESC = "Demonstrates treemap layout options";
    private static final String _TREEMAP_MULTI_ROOT_DESC = "Demonstrates a treemap with multiple rows in the root collection";
    private static final String _TREEMAP_NODE_CONTENT_DESC = "Demonstrates advanced node content";
    private static final String _TREEMAP_OTHER_NODE_DESC = "Demonstrates a treemap with aggregated other nodes";
    private static final String _TREEMAP_PATTERN_FILL_DESC = "Demonstrates a treemap with pattern fill";
    private static final String _TREEMAP_POPUPS_DESC = "Demonstrates a treemap with popups";
    private static final String _TREEMAP_SMALL_FORM_FACTOR_DESC = "Demonstrates small form factor treemap layout";

    private static final String _TIMELINE_DUAL_DESC = "Demonstrates dual timeline";
    private static final String _TIMELINE_DND_DESC = "Demonstrates timeline as a drag source and drop target";
    private static final String _TIMELINE_DURATIONS_DESC = "Demonstrates timeline with durations";
    private static final String _TIMELINE_AUTOPPR_DESC = "Demonstrates timeline auto PPR";
    private static final String _TIMELINE_CLIENT_BEHAVIOR_DESC = "Demonstrates timeline client behavior";
    private static final String _TIMELINE_CONTEXT_MENU_DESC = "Demonstrates a timeline with context menus";

    private static final String _DIAGRAM_FEATURES_DESC = "Configure a diagram with commonly used features";
    private static final String _DIAGRAM_LAYOUTS_DESC = "Basic layout functionality tutorial";
    private static final String _DIAGRAM_LINK_STYLES_DESC = "Demonstrates the various link styles";
    private static final String _DIAGRAM_CONTEXT_MENU_DESC = "Demonstrates a diagram with context menus";
    private static final String _DIAGRAM_EDIT_DESC = "Demonstrates a diagram with edit features enabled";
    private static final String _DIAGRAM_EMPLOYEE_DESC = "Demonstrates a Diagram representing an employee tree";
    private static final String _DIAGRAM_FLAVORS_DESC = "Demonstrates a diagram with dataFlavors";
    private static final String _DIAGRAM_PANELCARD_DESC = "Demonstrates a diagram with panelCard and content";
    private static final String _DIAGRAM_ZOOMING_DESC = "Demonstrates the zooming capabilities of a diagram";
    private static final String _DIAGRAM_HIGHLIGHTING_DESC = "Demonstrates the highlighting capabilities of a diagram";
    private static final String _DIAGRAM_POPUP_DESC = "Demonstrates popups on Diagram nodes and links";
    private static final String _DIAGRAM_ARC_LAYOUT_DESC = "Demonstrates an arc diagram";
    private static final String _DIAGRAM_CONTAINER_PADDING_DESC = "Demonstrates a layout that applies container padding";
    private static final String _DIAGRAM_STATE_MIGRATION_DESC = "Demonstrates curved links and rotated labels against state-to-state migration data";
    private static final String _DIAGRAM_DB_SCHEMA_DESC = "Shows a Diagram representing a database schema. Demonstrates nested containers and curved links";
    private static final String _DIAGRAM_STACKING_DESC = "Demonstrates stacking nodes in Diagram";
    private static final String _DIAGRAM_RULES_EDITOR_DESC = "Demonstrates rules editor, support for external drag sources and various types of drop zones";

    private static final String _NBOX_FEATURES_DESC = "Configure an NBox with commonly used features";
    private static final String _NBOX_HIGHLIGHTING_DESC = "Demonstrates the ability to highlight nodes";
    private static final String _NBOX_DND_DESC = "Demonstrates an NBox with drag and drop support";
    private static final String _NBOX_ATTRIBUTE_GROUPS_DESC = "Demonstrates the use of attribute groups in an NBox";
    private static final String _NBOX_POPUPS_DESC = "Demonstrates the use of popups and context menus in an NBox";

    private static final String _TAG_CLOUD_FEATURES_DESC = "Configure a Tag Cloud with commonly used features";
    private static final String _TAG_CLOUD_ACTION_EVENT_DESC = "Demonstrates a Tag Cloud action event and action listener";
    private static final String _TAG_CLOUD_DESTINATION_DESC = "Demonstrates Tag Cloud destination feature";
    private static final String _TAG_CLOUD_CONTEXT_MENU_DESC = "Demonstrates a Tag Cloud with context menus";
    private static final String _TAG_CLOUD_ATTRIBUTE_GROUPS_DESC = "Demonstrates the use of attribute groups in a Tag Cloud";
    private static final String _TAG_CLOUD_HIGHLIGHT_AND_FILTER_DESC = "Demonstrates the ability to Highlight and Filter Tag Cloud items";
    private static final String _TAG_CLOUD_DND_DESC = "Demonstrates drag and drop feature in Tag Cloud";
    private static final String _TAG_CLOUD_POPUPS_DESC = "Demonstrates a Tag Cloud with popups";

}
