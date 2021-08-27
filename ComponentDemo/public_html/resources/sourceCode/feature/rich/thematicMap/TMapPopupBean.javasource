package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import oracle.adfdemo.view.feature.rich.chart.ChartDataSource.ChartDataItem;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;


public class TMapPopupBean{
    private CollectionModel m_colorModel;
    private CollectionModel m_pointModel;
    private SampleColorData m_mapData;
    private SamplePointData m_pointData;
    private static String color1str = "#666699";
    private static String color2str = "#006666";
    private static Color color1 = new Color(102,102,153);
    private static Color color2 = new Color(0,102,102);
    private Object _dialogSource;
    private Object _noteSource;
    private GraphData graphData;
    
    public TMapPopupBean(){
        m_mapData = new SampleColorData();
        m_pointData = new SamplePointData();
        graphData = new GraphData();
    }
    public ArrayList getGraphModel(){
        SampleColorData.ColorData rowData = (SampleColorData.ColorData)getColorModel().getRowData();
        ArrayList data = new ArrayList();
        data.add(new Object[]{"Group1", "Candidate 2", rowData.getValue()});
        data.add(new Object[]{"Group1", "Candidate 1", 100-rowData.getValue()});
        return data;
    }
    
    
    public GraphData getGraphData(){
        return graphData;
    }
    
    public CollectionModel getColorModel(){
        if (m_colorModel == null){
            m_colorModel = ModelUtils.toCollectionModel(m_mapData);
        }
        return m_colorModel;
    }
    
    public CollectionModel getPointModel(){
        if (m_pointModel == null){
            m_pointModel = ModelUtils.toCollectionModel(m_pointData);
        }
        return m_pointModel;
    }
    
    public Object getSource() {
        return _dialogSource;
    }
  
    public void setSource(Object o) {
        _dialogSource = o;
    }
    
    public Object getNoteSource(){
        return _noteSource;
    }
    
    public void setNoteSource(Object o){
        _noteSource = o;
    }
    
    public String getStrColor1(){
      return color1str;
    }
    
    public String getStrColor2(){
      return color2str;
    }
    
    public Color getColor1(){
      return color1;
    }
    
    public Color getColor2(){
      return color2;
    }
}

class GraphData implements Map{
    ///// Dummy Map implementation methods /////
    public int size(){return 0;}
    public boolean isEmpty(){return false;}
    public boolean containsKey(Object key){return true;} // This one is special--must return true in order for EL hack to work
    public boolean containsValue(Object value){return false;}
    public Object put(Object key, Object value){return null;}
    public Object remove(Object key) {return null;}
    public void putAll(Map m){}
    public void clear(){}
    public Set keySet(){return Collections.emptySet();}
    public Collection values(){return Collections.emptySet();}
    public Set entrySet() {return Collections.emptySet();}

    public List createTabularData(SampleColorData.ColorData rowData){
        List<ChartDataItem> data = new ArrayList<ChartDataItem>();
        data.add(new ChartDataItem("Series1", "Group1", rowData.getValue()));
        data.add(new ChartDataItem("Series2", "Group1", 100-rowData.getValue()));
        return data;
    }
    public Object get(Object key) {
        return createTabularData((SampleColorData.ColorData)key);
    }
}
