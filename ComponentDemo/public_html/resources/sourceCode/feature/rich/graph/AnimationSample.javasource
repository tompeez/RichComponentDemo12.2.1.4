package oracle.adfdemo.view.feature.rich.graph;

import java.util.Random;

import oracle.adf.view.faces.bi.model.DataModel;
import oracle.adf.view.faces.bi.model.GraphDataModel;

import oracle.dss.dataView.LocalXMLDataSource;

public class AnimationSample {
    private String m_animationOnDataChange = "auto";
    private String m_animationOnDisplay = "auto";
    private int m_year = 2008; // year for subtitle and slider
    private String m_region = "All"; // region for footnote

    public String getDataChange() {
      return m_animationOnDataChange;
    }

    public void setDataChange(String type) {
      m_animationOnDataChange = type;
    }

    public String getDisplay() {
      return m_animationOnDisplay;
    }

    public void setDisplay(String type) {
      m_animationOnDisplay = type;
    }
    
    public int getYear() {
      return m_year;
    }
    
    public void setYear(int year) {
      // Choose the right animation
      if(year < m_year)
        setDataChange("slideToRight");
      else
        setDataChange("slideToLeft");
      
      m_year = year;
    }
    
    public String getRegion() {
      return m_region;
    }
    
    public void setRegion(String region) {
      // Choose the right animation
      setDataChange("transitionToLeft");
      
      m_region = region;
    }
    
    public String getSubtitle() {
      return String.valueOf(m_year);
    }
    
    public String getFootnote() {
      return "Region: " + m_region;
    }

    /**
     * Returns a randomly generated data source for the graph.
     * @return
     */
    public DataModel getRandomGraphData() {
      String[] columnLabels = {"Q1", "Q2", "Q3", "Q4"};
      String[] rowLabels = {"Database", "Middleware", "Applications", "Hardware"};
      
      Random r = new Random();
      Object[][] data = new Object[columnLabels.length][rowLabels.length];
      for (int i = 0; i < columnLabels.length; i++) {
        for (int j = 0; j < rowLabels.length; j++)
          data[i][j] = r.nextInt(90);
      }

      return new GraphDataModel(new LocalXMLDataSource(columnLabels, rowLabels, data));
    }

}
