package oracle.adfdemo.view.feature.rich.thematicMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.thematicMap.UIAreaDataLayer;

import oracle.adf.view.faces.bi.component.thematicMap.UIDataLayer;
import oracle.adf.view.faces.bi.component.common.UIMarker;
import oracle.adf.view.faces.bi.component.thematicMap.UIThematicMap;
import oracle.adf.view.faces.bi.event.ClickEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class TMapEventBean {
    private String m_clickString = "Action event detail shown here";
    private CollectionModel m_tableModel = null;
    private RichTable table;

    public TMapEventBean () {
        m_tableModel = ModelUtils.toCollectionModel(new ArrayList());
    }
    public void processClick(ActionEvent event){
        UIMarker marker = (UIMarker)event.getSource();
        UIDataLayer dataLayer = TMapUtils.getDataLayer(marker);
        CollectionModel model = (CollectionModel)dataLayer.getValue();
        SampleColorData.ColorData data = (SampleColorData.ColorData)model.getRowData();
        
        m_clickString = "The last human marker you clicked was on: " + data.getFullName();
    }
    
    public String getClickString(){
       return m_clickString;
    }
    
    public void setClickString(String text){
        m_clickString = text;
    }
    
    private String m_selection = " ";
    
    public String getSelectionString(){
        return "Selected Regions: " + m_selection;
    }
    
    public CollectionModel getTableModel() {
        return m_tableModel;        
    }
    
    public void processSelection(SelectionEvent selectionEvent){
        StringBuilder infoString = new StringBuilder();
        ArrayList selectList = new ArrayList();
        
        UIAreaDataLayer dataLayer = (UIAreaDataLayer)selectionEvent.getSource();
  
        CollectionModel model = (CollectionModel)dataLayer.getValue();
        RowKeySet selectedSet = dataLayer.getSelectedRowKeys();
        
        Iterator i = selectedSet.iterator();
        while(i.hasNext()){
            Object rowKey = i.next();
            model.setRowKey(rowKey);
            if (model.getRowData() instanceof SampleColorData.ColorData)
            {
                SampleColorData.ColorData selected = (SampleColorData.ColorData)model.getRowData();
                infoString.append(selected.getFullName()).append("; ");
            } else if (model.getRowData() instanceof UnemploymentData.ColorData) {
                UnemploymentData.ColorData selected = (UnemploymentData.ColorData)model.getRowData();
                selectList.add(selected);
                Collection collection =
                    (Collection)m_tableModel.getWrappedData();
                collection.add(selected);

            }
        }

        m_tableModel = ModelUtils.toCollectionModel(selectList);
        RequestContext currentInstance =
            RequestContext.getCurrentInstance();
        currentInstance.addPartialTarget(table);                
      
        m_selection = infoString.toString();
    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }
}
