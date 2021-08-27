package oracle.adfdemo.view.feature.rich.graph;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import oracle.adf.view.faces.bi.component.graph.UIGraph;

import oracle.dss.graph.CommonGraph;
import oracle.dss.graph.GraphConstants;


public class BoxPlotBean extends JPanel {
    private UIGraph fiveGraph, sevenGraph;
    private String m_text;

    public BoxPlotBean() {
    }
    
    public String getText(){
        m_text = "Outlier \n Value: ";
        return m_text;
    }
    
    public List getData(){
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
