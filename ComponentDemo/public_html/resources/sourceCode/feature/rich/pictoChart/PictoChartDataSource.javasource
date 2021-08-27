package oracle.adfdemo.view.feature.rich.pictoChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oracle.adf.view.faces.bi.component.pictoChart.UIPictoChart;
import oracle.adf.view.faces.bi.event.chart.ChartDrillEvent;
import oracle.adf.view.faces.bi.event.pictoChart.PictoChartDrillEvent;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;

// TODO implement the flexible data model system
public class PictoChartDataSource {
  /**
   * Object representing the data for a single row of the model.
   */
  public static class PictoChartItem extends HashMap<String, Object> {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    
    public PictoChartItem(String shape, String color, Number count, String shortDesc) {
      put("shape", shape);
      put("color", color);
      put("count", count);
      put("shortDesc", shortDesc);
    }    
    
  }
    private String m_selection = "No Nodes Selected";
    private String drilling = "on";
    private String dataItemDrilling = "inherit";
    private String m_drillState = "No Nodes drilled";

    public String getDrillState() {
      return m_drillState;
    }

    public void setDrilling(String drilling) {
      this.drilling = drilling;
    }

    public String getDrilling() {
      return drilling;
    }

    public void setDataItemDrilling(String drilling) {
      this.dataItemDrilling = drilling;
    }

    public String getDataItemDrilling() {
      return dataItemDrilling;
    }
    public void selectionListener(SelectionEvent event) {
      UIPictoChart chart = (UIPictoChart) event.getComponent();
      RowKeySet rowKeySet = chart.getSelectedRowKeys();
      if (rowKeySet != null && rowKeySet.size() > 0) {
        StringBuilder sb = new StringBuilder("Selection: ");
        for (Object rowKey : rowKeySet) {
          sb.append(rowKey).append(", ");
        }

        // Remove the trailing comma and set the selection string
        sb.setLength(sb.length() - 2);
        m_selection = sb.toString();
      } else
        m_selection = "No Nodes Selected";
    }
    public void drillListener(PictoChartDrillEvent event) {
      if (event.getRowKey() != null)
        m_drillState = "Drill on Data Item: " + event.getRowKey().toString();
    }

    public String getSelectionState() {
      return m_selection;
    }
  public CollectionModel getDefaultPictoData() {
    List<PictoChartItem> dataItems = new ArrayList<PictoChartItem>();
    dataItems.add(new PictoChartItem("rectangle","red", 54, "Republican"));
    dataItems.add(new PictoChartItem("rectangle","blue", 44, "Democrat"));
    dataItems.add(new PictoChartItem("rectangle","gray", 2, "Independent"));

    return ModelUtils.toCollectionModel(dataItems);
  }  
 
  public CollectionModel getDefaultPictoDataWithShape() {
    List<PictoChartItem> dataItems = new ArrayList<PictoChartItem>();
    dataItems.add(new PictoChartItem("human", "red", 54, "Republican"));
    dataItems.add(new PictoChartItem("human", "blue", 44, "Democrat"));
    dataItems.add(new PictoChartItem("human", "gray", 2, "Independent"));

    return ModelUtils.toCollectionModel(dataItems);
  }  
 
}
