package oracle.adfdemo.view.feature.rich.graph;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.event.PollEvent;


public class TimeSeriesRTSample {
    public TimeSeriesRTSample() {
        super();
    }
    
    private List<Object[]> timeSeriesData = new ArrayList<Object[]>();

    private UIGraph graph;
    
    private Date lastAddedDate;

    public List getTimeSeriesData() {
        // Feed some dummy data if list is empty
        if (timeSeriesData.size() == 0) {
            for (int i = 0; i < 29; i++) {
                Random randGen = new Random();
                int num = randGen.nextInt(10) + 10;
                timeSeriesData.add(new Object[] {new Date(System.currentTimeMillis()
                                                             -60000+i*2000), "series1", new Double(num)});
            }
        } else {
            // Get the time lapsed between current system time and the time last data was added
            int interval = (int)(System.currentTimeMillis() - lastAddedDate.getTime())/2000;
            if (interval > 30) {
                interval = 30;
            }
            // Add data items according to the interval up to last 30
            for (int j=interval; j>1; j--) {
                Random randGen = new Random();
                int num = randGen.nextInt(10) + 10;
                if (timeSeriesData.size() > 29) {
                    timeSeriesData.remove(0);
                }
                timeSeriesData.add(new Object[] {new Date(System.currentTimeMillis()-j*2000), "series1", new Double(num)});
            }
        }
        return timeSeriesData;
    }

    public void updateModel(PollEvent pollEvent) {
        Random randGen = new Random();
        int num = randGen.nextInt(10) + 10;
        // Keep the size of the list to 30
        if (timeSeriesData.size() > 29) {
            timeSeriesData.remove(0);
        }
        lastAddedDate = new Date();
        timeSeriesData.add(new Object[] {lastAddedDate, "series1", new Double(num)});
        AdfFacesContext.getCurrentInstance().addPartialTarget(graph);
    }

    public void setGraph(UIGraph graph) {
        this.graph = graph;
    }

    public UIGraph getGraph() {
        return graph;
    }
}
