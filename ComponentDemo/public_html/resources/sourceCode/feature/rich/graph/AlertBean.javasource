/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/AlertBean.java */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ytang      11/09/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/AlertBean.java
 *  @author  ytang  
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.graph;

import java.sql.Date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Random;


import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.component.graph.Alert;
import oracle.adf.view.rich.model.NumberRange;

public class AlertBean  {
    private List m_list;
    private List m_scatterList;
    private List m_lineList;
    private double[] m_values;
    private Double m_alertXValue;
    private Double m_alertYValue;
    private Double m_alertYValue2;
    private Date m_date;
    private NumberRange m_range;
    private Map m_alertMap;
    public AlertBean(){
        m_range = new NumberRange(40,60);
        m_values = new double[]{24,53,16,28,78,45,67,43,84,35};
        m_list = new ArrayList();
        m_alertMap = new HashMap();
        m_alertXValue = new Double(40);
        m_alertYValue = new Double(60);
        m_alertYValue2 = new Double(100);
        m_date = Date.valueOf("2010-01-01");
        for (int i = 0; i < m_values.length; i++ )
            m_list.add(new Object[]{"Group "+Integer.toString(i),"Series 1",new Double(m_values[i])});
        
        m_scatterList = new ArrayList();
        m_scatterList.add(new Object[]{"Group 1","Series 1", new Double(31)});
        m_scatterList.add(new Object[]{"Group 1'","Series 1", new Double(12)});
        m_scatterList.add(new Object[]{"Group 2","Series 1", new Double(54)});
        m_scatterList.add(new Object[]{"Group 2'","Series 1", new Double(23)});
        m_scatterList.add(new Object[]{"Group 1","Series 2", new Double(75)});
        m_scatterList.add(new Object[]{"Group 1'","Series 2", new Double(47)});
        m_scatterList.add(new Object[]{"Group 2","Series 2", new Double(67)});
        m_scatterList.add(new Object[]{"Group 2'","Series 2", new Double(89)});
        
        Random random = new Random();
        m_lineList = new ArrayList();
        Date newDate = Date.valueOf("2010-01-01");
        for (int i = 0; i < 100; i ++){
            double number = random.nextDouble() * 20+90;
            m_lineList.add(new Object[]{"Group "+i,"Series 1", newDate});
            m_lineList.add(new Object[]{"Group' "+i,"Series 1", new Double(number)});
            newDate = new Date(newDate.getTime() + 86400000);
        }
    }
    public NumberRange getValue(){
        return m_range;
    }
    public void setValue(NumberRange range){
        m_range = range;
    }
    public List getList(){
        return m_list;
    }
    public List getScatterList(){
        return m_scatterList;
    }
    public List getLineList(){
        return m_lineList;
    }
    public void valueListener(ValueChangeEvent event){

        
    }
    public Map getAlertMap(){
        m_alertMap = new HashMap();
        int index = 1;
        double max = m_range.getMaximum().doubleValue();
        double min = m_range.getMinimum().doubleValue();
        for (int i = 0; i < m_values.length; i++ ){
            if ( m_values[i] <= max && m_values[i] >= min ){
                Alert alert = new Alert();
                alert.setXValue("Group "+Integer.toString(i));
                alert.setYValue(m_values[i]);
                alert.setImageSource("/resources/images/graph/alert_icon.png");
                m_alertMap.put(new Integer(index), alert);
                index++;
            }
        }
        return m_alertMap;
    }
    
    public Date getAlertDate(){
        return m_date;
    }
    public void setAlertDate(Date date){
        m_date = date;
    }
    
    public Double getAlertXValue(){
        return m_alertXValue;
    }
    public void setAlertXValue(Double value){
        m_alertXValue = value;
    }
    public Double getAlertYValue(){
        return m_alertYValue;
    }
    public void setAlertYValue(Double value){
        m_alertYValue = value;
    }
    public Double getAlertYValue2(){
        return m_alertYValue2;
    }
    public void setAlertYValue2(Double value){
        m_alertYValue2 = value;
    }
}
