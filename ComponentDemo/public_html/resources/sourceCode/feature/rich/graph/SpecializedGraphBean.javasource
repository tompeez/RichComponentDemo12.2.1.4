package oracle.adfdemo.view.feature.rich.graph;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.view.faces.bi.component.graph.UIGraph;

import oracle.dss.graph.CommonGraph;
import oracle.dss.graph.GraphConstants;



import java.awt.Color;

import java.io.Serializable;

import java.util.Vector;

import oracle.adf.view.faces.bi.model.GraphDataModel;

import oracle.dss.dataView.ComponentHandle;
import oracle.dss.dataView.DataComponentHandle;
import oracle.dss.dataView.LocalXMLDataSource;
import oracle.dss.graph.CustomToolTipCallback;
import oracle.dss.graph.managers.GraphStyle;
import oracle.dss.rules.DiscriminatorRule;
import oracle.dss.rules.RuleBundle;
import oracle.dss.rules.discriminator.NumberValueDiscriminator;
import oracle.dss.rules.discriminator.PositionDiscriminator;
import oracle.dss.rules.discriminator.ValueDiscriminator;
import oracle.dss.util.ColumnComponentInfo;

public class SpecializedGraphBean {
    
    // Radar Section
    public List getRadarData(){
        List list = new ArrayList();
        
        list.add(new Object[]{"Wealth","Atlanta",new Double(60)});
        list.add(new Object[]{"Wealth","Boston",new Double(80)});
        list.add(new Object[]{"Wealth","Chicago",new Double(95)});
        
        list.add(new Object[]{"Education","Atlanta",new Double(65)});
        list.add(new Object[]{"Education","Boston",new Double(100)});
        list.add(new Object[]{"Education","Chicago",new Double(70)});
        
        list.add(new Object[]{"Transportation","Atlanta",new Double(80)});
        list.add(new Object[]{"Transportation","Boston",new Double(50)});
        list.add(new Object[]{"Transportation","Chicago",new Double(70)});
        
        list.add(new Object[]{"Population","Atlanta",new Double(76)});
        list.add(new Object[]{"Population","Boston",new Double(90)});
        list.add(new Object[]{"Population","Chicago",new Double(30)});
        
        list.add(new Object[]{"Climate","Atlanta",new Double(85)});
        list.add(new Object[]{"Climate","Boston",new Double(60)});
        list.add(new Object[]{"Climate","Chicago",new Double(50)});

        return list;
    }  
    


    // Waterfall Chart Demo
    
      
        
    private static final Color RED = new Color(255, 0, 0);
    private static final Color GREEN = new Color(64, 153, 51);
    private static final Color DARK_BLUE = new Color(0, 0, 187);
    private static final Color LIGHT_GRAY = Color.LIGHT_GRAY;
       
    
    public void setWaterFallGraph(UIGraph graph) 
    {
    }
    
    public UIGraph getWaterFallGraph() 
    {
        // Initialize the graph
        UIGraph graph = new UIGraph();
        
        // Set the series color for the series for the legend and tooltips
        graph.getSeriesSet().getSeries(1,true).setColor(LIGHT_GRAY);
        graph.getSeriesSet().getSeries(2,true).setColor(GREEN);
        graph.getSeriesSet().getSeries(3,true).setColor(RED);
        graph.getSeriesSet().getSeries(4,true).setColor(DARK_BLUE);
        
        // Update the tooltips
        graph.setCustomToolTipCallback(new CustomToolTipCallbackImpl());
        
        
        // Internal Code to hide legend series        
        CommonGraph cg = ((CommonGraph)graph.getImageView());
        // Make sure the legend hides the bar value series
        cg.setLegendSeriesCount(3);
        cg.setLegendSeriesStart(1);
        
        return graph;
    }
    
    
    /**
    * Source data, where the first and last values are the actual values, and the middle values are increments.
    */
    private static final double[] DATA = {100, -10, 20, -5, -1, -2, -3, 2, 7, 108};
    
    private GraphDataModel m_model;
    public GraphDataModel getGraphDataModel() {
        if (m_model == null)
            m_model = new GraphDataModel(getDataModel(DATA));
        return m_model;
    }
    
    /**
    * Helper function to create a DataAccess given a list of values and increments.
    */
    private static LocalXMLDataSource getDataModel(double[] sourceData) {
        String[] columnLabels = new String[sourceData.length];
        
        // Set up the column labels and row labels
        Object[] rowLabels = {"Float Value", "Bar Value", "Increase", "Decrease", "Total"};
        for(int i=0; i<columnLabels.length; i++) {
            columnLabels[i] = "Day " + i;
        }
        
        // Override the first and last labels
        columnLabels[0] = "Initial";
        columnLabels[columnLabels.length-1] = "Total";
        
        // Set up the data
        Double[][] data = new Double[columnLabels.length][rowLabels.length];
        double currValue = 0;
        for(int i=0; i<columnLabels.length; i++) {
            // Read the data from the data source:
            
            // First and last value: show entire bars
            // Middle values: show increments only
            if(i == 0 || i == sourceData.length-1) 
            data[i][0] = 0.0;
            else // Incremental value
            data[i][0] = currValue;
            
            // Set the bar value
            data[i][1] = sourceData[i];
            
            // Also set the placeholder values for the legend
            data[i][2] = 0.0;
            data[i][3] = 0.0;
            data[i][4] = 0.0;
            
            // Update the current value
            currValue += sourceData[i];
        }
        
        return new LocalXMLDataSource(columnLabels, rowLabels, data);
    }
    
    /**
    * Set bar colors with conditional formatting
    */
    public Vector getConditionalFormattingVector() {
        // Color rising and falling markers differently.  More options are available via conditional formatting
        RuleBundle bundle = new RuleBundle();
        DiscriminatorRule dr = new DiscriminatorRule();
        GraphStyle gs = new GraphStyle();
        //create rule for fill color red if value is negative
        dr.setDiscriminator(new NumberValueDiscriminator(0.0, ValueDiscriminator.LT));
        gs.setFillColor(RED); 
        dr.setFixedMergeable(gs);
        bundle.addRule(dr);
        //create rule for fill color green if value is positive
        dr = new DiscriminatorRule();
        dr.setDiscriminator(new NumberValueDiscriminator(0.0, ValueDiscriminator.GT));
        gs = new GraphStyle();
        gs.setFillColor(GREEN);
        dr.setFixedMergeable(gs);
        bundle.addRule(dr);
        //create rule for fill color dark blue for starting bar
        dr = new DiscriminatorRule();
        ColumnComponentInfo cci_start = new ColumnComponentInfo(0);
        dr.setDiscriminator(new PositionDiscriminator(cci_start));
        gs.setFillColor(DARK_BLUE); 
        dr.setFixedMergeable(gs);
        bundle.addRule(dr);  
        //create rule for fill color dark blue for ending bar
        dr = new DiscriminatorRule();
        ColumnComponentInfo cci_end = new ColumnComponentInfo(DATA.length-1);
        dr.setDiscriminator(new PositionDiscriminator(cci_end));
        gs.setFillColor(DARK_BLUE); 
        dr.setFixedMergeable(gs);
        bundle.addRule(dr);
        
        //put bundle in vector and set on graph
        Vector styleVector = new Vector();
        styleVector.addElement(bundle);
        return styleVector;
    }
    
    /**
    * Updates the tooltips to be more appropriate for a waterfall chart.
    */
    private static class CustomToolTipCallbackImpl implements CustomToolTipCallback, Serializable {
        @SuppressWarnings("compatibility:892675340220429727")
        private static final long serialVersionUID = 1L;

        public String getToolTipText(String string, ComponentHandle componentHandle) {
            if(componentHandle instanceof DataComponentHandle) {
                DataComponentHandle dh = (DataComponentHandle) componentHandle;
                
                // If a placeholder series, return nothing
                int series = dh.getSeries();
                if(series != 1)
                return null;
                
                int group = dh.getGroup();
                if(group == 0) 
                return "Start Value: " + DATA[group];
                else if(group == DATA.length-1)
                return "End Value: " + DATA[group];
                else
                return "Change: " + DATA[group];
            }
            // Otherwise return default
            return string;
        }
    }
    

    
    // Box Plot Section
    private UIGraph fiveGraph, sevenGraph;
    private String m_text;
    
    public String getText(){
        m_text = "Outlier \n Value: ";
        return m_text;
    }
    
    public List getBoxPlotData(){
        List list = new ArrayList();
        list.add(new Object[]{"Group A", "Series 1", new Double(10)});
        list.add(new Object[]{"Group A1", "Series 1", new Double(20)});
        list.add(new Object[]{"Group A2", "Series 1", new Double(40)});
        list.add(new Object[]{"Group A3", "Series 1", new Double(60)});
        list.add(new Object[]{"Group A4", "Series 1", new Double(100)});
        list.add(new Object[]{"Group B", "Series 1", new Double(-5)});
        list.add(new Object[]{"Group B1", "Series 1", new Double(10)});
        list.add(new Object[]{"Group B2", "Series 1", new Double(20)});
        list.add(new Object[]{"Group B3", "Series 1", new Double(40)});
        list.add(new Object[]{"Group B4", "Series 1", new Double(70)});
        return list;
    }
    
    
    public void setFiveValueGraph(UIGraph graph){
        fiveGraph = graph;
    }
    
    public void setSevenValueGraph(UIGraph graph){
        sevenGraph = graph;
    }
    
    public UIGraph getFiveValueGraph(){
        if (fiveGraph == null)
            fiveGraph = new UIGraph();
        CommonGraph cg = ((CommonGraph)fiveGraph.getImageView());
        cg.setGraphType(GraphConstants.BOXPLOT_FIVE_VALUE);

        return fiveGraph;
    }
    
    public UIGraph getSevenValueGraph(){
        if (sevenGraph == null)
            sevenGraph = new UIGraph();
        CommonGraph cg = ((CommonGraph)sevenGraph.getImageView());
        cg.setGraphType(GraphConstants.BOXPLOT_SEVEN_VALUE);

        return sevenGraph;
    }
}
