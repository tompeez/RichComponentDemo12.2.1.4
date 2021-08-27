/* Copyright (c) 2007, 2013, Oracle and/or its affiliates. 
All rights reserved. */
package oracle.adfdemo.view.feature.rich.graph;


import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.rich.component.rich.RichDocument;
import oracle.adf.view.rich.component.rich.RichForm;
import oracle.adf.view.faces.bi.event.ClickEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.dss.dataView.Attributes;
import oracle.dss.dataView.ComponentHandle;
import oracle.dss.dataView.DataComponentHandle;
import oracle.dss.dataView.GroupComponentHandle;
import oracle.dss.dataView.SeriesComponentHandle;

import java.util.ArrayList;

import java.util.Date;
import java.util.Hashtable;


import javax.faces.component.UIComponent;

import oracle.adf.view.rich.component.UIXTable;

import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.dss.dataView.AnnotationComponentHandle;
import oracle.dss.dataView.BubbleDataComponentHandle;
import oracle.dss.dataView.NonDataComponentHandle;
import oracle.dss.dataView.StockDataComponentHandle;
import oracle.dss.graph.Annotation;
import oracle.dss.graph.Graph;

import oracle.dss.graph.UIGraphType;

import org.apache.myfaces.trinidad.component.UIXComponent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class ClickListener implements oracle.dss.graph.GraphConstants{
    private RichForm form1;
    private RichDocument document1;
    private String m_text = "Most recent click event listed here";
    private ArrayList<ClickEventBean> m_eventQueue = new ArrayList( 10);
    private Hashtable<Integer,String> m_componentIDHash = new Hashtable<Integer,String>();
    private int MAX_QUEUE_SIZE = 10;
    private RichTable m_table;
    private UIGraph m_barGraph;
    private boolean m_barVisible;
    private boolean m_lineVisible;
    private boolean m_pieVisible;
    private boolean m_bubbleVisible;
    private boolean m_stockVisible;
    private UIGraph m_lineGraph;
    private int m_graphType;
    private UIGraph m_pieGraph;
    private UIGraph m_bubbleGraph;
    private UIGraph m_stockGraph;
    private boolean m_expanded;
    
    public ClickListener() {
        m_componentIDHash.put(Integer.valueOf(X1TITLE), "X1 Title");
        m_componentIDHash.put(Integer.valueOf(X1TICKLABEL), "X1 Tick Label");
        m_componentIDHash.put(Integer.valueOf(Y1TITLE), "Y1 Title");
        m_componentIDHash.put(Integer.valueOf(Y1TICKLABEL), "Y1 Tick Label");
        m_componentIDHash.put(Integer.valueOf(O1TITLE), "O1 Title");
        m_componentIDHash.put(Integer.valueOf(O1TICKLABEL), "O1 Tick Label");
        m_componentIDHash.put(Integer.valueOf(LEGENDMARKER), "Legend Marker");
        m_componentIDHash.put(Integer.valueOf(LEGENDTEXT), "Legend Text");
        m_componentIDHash.put(Integer.valueOf(TWODMARKER), "2-D Data Marker");
        m_componentIDHash.put(Integer.valueOf(DATALINE), "Dataline");
        m_componentIDHash.put(Integer.valueOf(PIELABEL), "Pie Graph Label");
        m_componentIDHash.put(Integer.valueOf(SLICE), "Pie Slice");
        m_componentIDHash.put(Integer.valueOf(SLICELABEL), "Pie Slice Label");
        m_componentIDHash.put(Integer.valueOf(DATAMARKER), "DataMarker");
        m_componentIDHash.put(Integer.valueOf(STOCKMARKER), "Stock Marker");
        m_componentIDHash.put(Integer.valueOf(VOLUMEMARKER), "Volume Marker");
        m_componentIDHash.put(Integer.valueOf(ANNOTATION), "Annotation");
        m_componentIDHash.put(Integer.valueOf(MARKERTEXT), "Marker Text");

        m_barVisible = true;
        m_lineVisible = false;
        m_pieVisible = false;
        m_bubbleVisible = false;
        m_stockVisible = false;
        m_expanded = true;
        setGraphType(BAR_VERT_CLUST);
    }

   
    public void setForm1(RichForm form1) {
        this.form1 = form1;
    }

    public RichForm getForm1() {
        return form1;
    }

    public void setDocument1(RichDocument document1) {
        this.document1 = document1;
    }

    public RichDocument getDocument1() {
        return document1;
    }
    
    public String getText() {
        return m_text;
    }
    
    public void setText(String txt) {
        m_text = txt;
    }
    
    public String processDblClick(ComponentHandle handle) 
    {   
        return "document.getElementById('mouseEventTextArea').innerHTML = 'Double click on " +
            handle.getName() + "';";
    }
 
    public String processMouseOver(ComponentHandle handle) 
    {
        return "document.getElementById('mouseEventTextArea').innerHTML = 'Mouse over event from " +
            handle.getName() + "';";
    }
    
    public void processClick(ClickEvent event)
    {   
        ComponentHandle handle = event.getComponentHandle();
        String fullDetail = "";
        int handleID = handle.getID();
        System.out.println("Handle type:" + handle.getName() + "--" + handle.getID());
        setText("You last clicked on a " + m_componentIDHash.get(Integer.valueOf(handle.getID())));
     
        ClickEventBean bean = new ClickEventBean(m_componentIDHash.get(Integer.valueOf(handle.getID())));
        if(m_eventQueue.size()==MAX_QUEUE_SIZE+1) {
            m_eventQueue.remove(0);
        }

        m_eventQueue.add(bean);
                 
        if (handle instanceof NonDataComponentHandle) {
            System.out.println("Got NonDataComponentHandle");
            bean.setValue("Non-Data Component Handle");                 
        } else if (handle instanceof AnnotationComponentHandle) {
            System.out.println("Got AnnotationComponentHandle");
            fullDetail += ((AnnotationComponentHandle)handle).getText() + "<br>";
            fullDetail += "Series value: " + ((AnnotationComponentHandle)handle).getSeries() + "<br>";
            fullDetail += "Group value: " + ((AnnotationComponentHandle)handle).getGroup();
            bean.setValue(fullDetail);
        } else if (handle instanceof GroupComponentHandle) {
            System.out.println("Got GroupComponentHandle");
            Attributes [] groupInfo = ((GroupComponentHandle) handle).getGroupAttributes();
            fullDetail += produceGroupInfo(groupInfo);   
            bean.setValue(fullDetail);
        } else if (handle instanceof SeriesComponentHandle) {
            System.out.println("Got SeriesComponentHandle");
            // Get the series attributes
            Attributes [] seriesInfo = ((SeriesComponentHandle)handle).getSeriesAttributes();
            fullDetail += produceSeriesInfo(seriesInfo);
            bean.setValue(fullDetail);
        } else if (handle instanceof BubbleDataComponentHandle) {
            BubbleDataComponentHandle bdhandle = (BubbleDataComponentHandle)handle;

            String type = m_componentIDHash.get(Integer.valueOf(handle.getID()));
            Double xData, yData, zData;
            xData = (Double)bdhandle.getXValue(DataComponentHandle.UNFORMATTED_VALUE);
            yData = (Double)bdhandle.getYValue(DataComponentHandle.UNFORMATTED_VALUE);
            zData = (Double)bdhandle.getZValue(DataComponentHandle.UNFORMATTED_VALUE);

            System.out.println("You last clicked on a " + type + ". Value: " + xData +
                               ","+ yData +","+ zData);
            setText("You last clicked on a " + type + ". Value: " + xData +
                               ","+ yData +","+ zData);
            fullDetail = "Data values: x=" + xData + ", y=" + yData + ", z=" + zData + "<br>";
            
            Attributes [] xAttr = bdhandle.getXAttributes();
            Attributes [] yAttr = bdhandle.getYAttributes();
            Attributes [] zAttr = bdhandle.getZAttributes();
            
            Attributes [] xSeriesAttr = bdhandle.getSeriesAttributes();
            Attributes [] xGroupAttr = bdhandle.getGroupAttributes();
            fullDetail += produceSeriesInfo(xSeriesAttr);

            // getGroupAttributes on the BubbleDataComponentHandle doesn't work 
            // as it should, so I'm first getting the X attributes, via getXAttributes
            // and then printing out the group info from that
            
            //fullDetail += "getGroupAttributes <br>";
            //fullDetail += produceGroupInfo(xGroupAttr);
            //fullDetail += "getXAttributes, GroupInfo <br>";

            fullDetail += produceGroupInfo(xAttr);
            
            //fullDetail += produceSeriesInfo(xAttr);
            //fullDetail += produceGroupInfo(xAttr);
            //fullDetail += produceSeriesInfo(yAttr);
            //fullDetail += produceGroupInfo(yAttr);
            //fullDetail += produceSeriesInfo(zAttr);
            //fullDetail += produceGroupInfo(zAttr);
            bean.setValue(fullDetail);
            
        } else if (handle instanceof StockDataComponentHandle) {
            StockDataComponentHandle dhandle = (StockDataComponentHandle)handle;
            // Get the value displayed in the series
            String type = m_componentIDHash.get(Integer.valueOf(handle.getID()));
            System.out.println("You last clicked on a " + type + ". Value: " + dhandle.getValue(DataComponentHandle.UNFORMATTED_VALUE));
            System.out.println("String value: "+ dhandle.toString());
            setText("You last clicked on a " + type + ". Value: " + dhandle.getValue(DataComponentHandle.UNFORMATTED_VALUE).toString() );
            fullDetail = "Open value: " + dhandle.getOpenValue(DataComponentHandle.UNFORMATTED_VALUE).toString() + "<br>";
            fullDetail += "High value: " + dhandle.getHighValue(DataComponentHandle.UNFORMATTED_VALUE).toString() + "<br>";
            fullDetail += "Low value: " + dhandle.getLowValue(DataComponentHandle.UNFORMATTED_VALUE).toString() + "<br>";
            fullDetail += "Close value: " + dhandle.getCloseValue(DataComponentHandle.UNFORMATTED_VALUE).toString() + "<br>";
            fullDetail += "Volume value: " + dhandle.getVolumeValue(DataComponentHandle.UNFORMATTED_VALUE).toString() + "<br>";
            
            // Get the series attributes
            Attributes [] seriesInfo = dhandle.getSeriesAttributes();
            fullDetail += produceSeriesInfo(seriesInfo);
             
             // Get the group attributes
             Attributes [] groupInfo = dhandle.getGroupAttributes();
             fullDetail += produceGroupInfo(groupInfo);
         
             bean.setValue(fullDetail);
         }
           else if (handle instanceof DataComponentHandle) {
            DataComponentHandle dhandle = (DataComponentHandle)handle;
            // Get the value displayed in the series
            String type = m_componentIDHash.get(Integer.valueOf(handle.getID()));
            System.out.println("You last clicked on a " + type + ". Value: " + dhandle.getValue(DataComponentHandle.UNFORMATTED_VALUE));
            System.out.println("String value: "+ dhandle.toString());
            setText("You last clicked on a " + type + ". Value: " + dhandle.getValue(DataComponentHandle.UNFORMATTED_VALUE).toString() );
            fullDetail = "Data value: " + dhandle.getValue(DataComponentHandle.UNFORMATTED_VALUE).toString() + "<br>";
            
            // Get the series attributes
            Attributes [] seriesInfo = dhandle.getSeriesAttributes();
            fullDetail += produceSeriesInfo(seriesInfo);
             
             // Get the group attributes
             Attributes [] groupInfo = dhandle.getGroupAttributes();
             fullDetail += produceGroupInfo(groupInfo);
         
             bean.setValue(fullDetail);
         }
         AdfFacesContext _context = AdfFacesContext.getCurrentInstance();
         _context.addPartialTarget(m_table);
         
        
         //refreshTable();
         //expandLastRow();
     }


    private void expandLastRow() {
        RowKeySet rks = new RowKeySetImpl();
        Object key;
        m_table.setRowIndex((m_eventQueue.size()-1));
        key = m_table.getRowKey();
        rks.add(key);
        m_table.setDisclosedRowKeys(rks);
    }
    
    private String produceSeriesInfo(Attributes[] seriesInfo) {
        String data = "";
        if(seriesInfo != null) {
            for(Attributes attrs: seriesInfo) {
                data += "Series value: " + attrs.getValue(Attributes.LABEL_VALUE) + "<br>";
                data += "Series name: " + attrs.getValue(Attributes.LABEL_ATTRIBUTE) + "<br>";
                data += "Series value id: " + attrs.getValue(Attributes.ID_VALUE) + "<br>";
                data += "Series name id: " + attrs.getValue(Attributes.ID_ATTRIBUTE) + "<br>";
            }
        }
        return data;
    }
    
    private String produceGroupInfo(Attributes[] groupInfo) {
        String data = "";
        if(groupInfo != null)
        {
            for(Attributes attrs: groupInfo)
            {
                data += "Group value: " + attrs.getValue(Attributes.LABEL_VALUE) + "<br>";
                data += "Group name: " + attrs.getValue(Attributes.LABEL_ATTRIBUTE) + "<br>";
            }        
        } 
        return data;
    }
    
    public ArrayList<ClickEventBean> getEventQueue() {
        return m_eventQueue;
    }
    
    
    public void clearTable(ActionEvent actionEvent) {
        m_eventQueue.clear();
        setText("Most recent click event listed here");
    }

    public void setTable(RichTable table) {
        m_table = table;
    }

    public RichTable getTable() {
        return m_table;
    }

    public void setBarGraph(UIGraph barGraph1) {
        m_barGraph = barGraph1;
    }

    public UIGraph getBarGraph() {
        return m_barGraph;
    }

    public boolean getBarVisible() {
        return m_barVisible;
    }
    
    public void setBarVisible(boolean isVisible) {
        m_barVisible = isVisible;
    }
    
    public void setStockGraph(UIGraph graph) {
        m_stockGraph = graph;
    }

    public UIGraph getStockGraph() {
        return m_stockGraph;
    }

    public boolean getStockVisible() {
        return m_stockVisible;
    }
    
    public void setStockVisible(boolean isVisible) {
        m_stockVisible = isVisible;
    }
    
    public boolean getLineVisible() {
        return m_lineVisible;
    }
    
    public void setLineVisible(boolean isVisible) {
        m_lineVisible = isVisible;
    }

    public void setLineGraph(UIGraph lineGraph) {
        m_lineGraph = lineGraph;
    }

    public UIGraph getLineGraph() {
        return m_lineGraph;
    }

    public int getGraphType() {
        return m_graphType;
    }
    
    public void setGraphType(int type) {
        m_graphType = type;
        makeAllGraphsInvisible();
        if(m_graphType == BAR_VERT_CLUST) {
            setBarVisible(true);
        } else if (m_graphType == LINE_VERT_ABS) {
            setLineVisible(true);
        } else if (m_graphType == PIE) {
            setPieVisible(true);
        } else if (m_graphType == BUBBLE) {
            setBubbleVisible(true);
        } else if (m_graphType == STOCK_OHLC_CANDLE_VOLUME) {
            setStockVisible(true);
        } else {
        System.out.println("Unrecognized graph type");
        }
    }


    public void setPieVisible(boolean b) {
        m_pieVisible=b;
    }

    public boolean getPieVisible() {
        return m_pieVisible;
    }
    private void makeAllGraphsInvisible() {
        setBarVisible(false);
        setLineVisible(false);
        setPieVisible(false);
        setBubbleVisible(false);
        setStockVisible(false);
    }


    public void setPieGraph(UIGraph pieGraph) {
        m_pieGraph = pieGraph;
    }

    public UIGraph getPieGraph() {
        return m_pieGraph;
    }

    public void setBubbleGraph(UIGraph bubbleGraph) {
        m_bubbleGraph = bubbleGraph;
    }
    
    public UIGraph getBubbleGraph() {
        return m_bubbleGraph;
    }

    public void setBubbleVisible(boolean isVisible){
        m_bubbleVisible = isVisible;
    }
    
    public boolean getBubbleVisible(){
        return m_bubbleVisible;
    }

    public void setExpanded(boolean expanded) {
        m_expanded = expanded;
    }

    public boolean getExpanded() {
        boolean b = m_expanded;
        if(m_table.getRowIndex()==m_eventQueue.size()-1)
            return true;
        else
            return false;
    }

    public class ClickEventBean {
        private String m_id;
        private String m_value;
        private Date m_date;

        public ClickEventBean(String id) {
            this(id, "");
        }
                
        public ClickEventBean(String id, String value) {
            m_id = id;
            m_value = value;
            m_date = new java.util.Date();
        }
        
        public String getID () {
            return m_id;
        }
        
        public void setID( String id) {
            m_id = id;
        }
        
        public String getValue() {
            return m_value;
        }
        
        public void setValue( String value) {
            m_value = value;
        }
        
        public String getDate() {
            return m_date.toString();
        }
        
        public void setDate(String s) {
            return;
        }
    
    }



       /*
        private void refreshTable() {
            System.out.println("number of items in table: " + m_table.getRowCount());    
      
            AdfFacesContext _context = AdfFacesContext.getCurrentInstance();
                            
            if(m_table.getRowCount() <= MAX_QUEUE_SIZE) {
                m_table.setRowIndex(m_table.getRowCount()-1);
                System.out.println("current rowKey index is " + m_table.getRowIndex());
                UIComponent c = m_table.findComponent("timestamp_column");
                if(c != null) {
                    System.out.println("found timestamp_column");
                    System.out.println(c.getChildren());
                    c = c.findComponent("timestamp_text");
                    ((RichOutputText) c).setValue("Test");
                    _context.addPartialTarget(c);
                } else {
                    System.out.println("failed to find timestamp_column");
                }

                c = m_table.findComponent("text_column");
                if(c != null) {
                    System.out.println("found text_column");
                    System.out.println(c.getChildren());
                    c = c.findComponent("text");
                    _context.addPartialTarget(c);
                } else {
                    System.out.println("failed to find text_column");
                }
                return;
            }
            
            // iterate over all the rows and update the text in each cell
            for(int i=1; i<= m_table.getRowCount(); i++ ) {

                //m_table.setRowKey(m_table.getRowData(i));
                UIComponent d = m_table.findComponent("timestamp_text");
                if (d != null){
                    System.out.println("found timestamp_text right away!");
                } else {
                    System.out.println("FAIL");
                }
                
                UIComponent c = m_table.findComponent("timestamp_column");
                if(c != null) {
                    System.out.println("found timestamp_column");
                    System.out.println(c.getChildren());
                    c = c.findComponent("timestamp_text");
                    _context.addPartialTarget(c);
                } else {
                    System.out.println("failed to find timestamp_column");
                }

                c = m_table.findComponent("text_column");
                if(c != null) {
                    System.out.println("found text_column");
                    System.out.println(c.getChildren());
                    c = c.findComponent("text");
                    _context.addPartialTarget(c);
                } else {
                    System.out.println("failed to find text_column");
                }

                //_context.addPartialTarget(m_table.findComponent("clickType"));
                //_context.addPartialTarget(m_table.findComponent("timeStamp"));
            }

                                
        }
      */
        
    

}
