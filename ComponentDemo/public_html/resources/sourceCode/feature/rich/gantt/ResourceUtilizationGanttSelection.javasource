/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ResourceUtilizationGanttSelection.java /main/3 2010/12/23 09:07:04 imohamma Exp $ */

/* Copyright (c) 2008, 2010, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hbroek      08/28/08 - Add Gantt samples
    kiancu      07/17/08 - clean up, add time bucket info to handle selected
                           time bucket method
    kiancu      07/07/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ResourceUtilizationGanttSelection.java /main/2 2009/03/19 21:37:41 teclee Exp $
 *  @author  kiancu  
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.component.gantt.UIResourceUtilizationGantt;
import oracle.adf.view.faces.bi.event.ResourceSelectionEvent;
import oracle.adf.view.faces.bi.event.TimeBucketSelectionEvent;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;
import oracle.adfdemo.view.feature.rich.gantt.data.TimeBucket;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class ResourceUtilizationGanttSelection
{
    private TreeModel m_model;
    private String select;
    
    public String[] getMetrics()
    {
        return new String[]{"SETUP", "RUN", "AVAILABLE"};
    }
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getResourceUtilizationGanttModel();
        
        return m_model;
    }
    
    public void handleSelectedResource(ResourceSelectionEvent evt)
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Resource Selected: " + evt.getResourceIds().get(0)));
    }
    
    public void handleSelectedTimeBucket(TimeBucketSelectionEvent evt)
    {
        String _message = "Received TimeBucketSelectionEvent: ";
        
        if (evt != null){
            TimeBucket _timeBucket = (TimeBucket)evt.getTimeBucket();
            if (_timeBucket != null){
                Date _timeBucketDate = _timeBucket.getTime();
                _message += "Time Bucket date is: " + _timeBucketDate;
            }
        }
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(_message));
    }
    
    public void handleShowSelectionAction(ActionEvent evt) 
    {
        UIResourceUtilizationGantt gantt1 = (UIResourceUtilizationGantt)((UIComponent)evt.getSource()).getParent().getParent().findComponent("resourceUtilizationGanttSelection");
        if (gantt1 != null) 
        {
            String _str = "Selected Time Bucket date is: ";
            RowKeySet _keys = gantt1.getSelectedTimeBucketRowKeys();
            if (_keys != null && _keys.getSize() > 0) 
            {
                Object _key = _keys.iterator().next();
                TimeBucket _timeBucket = (TimeBucket)gantt1.getTimeBucketData(_key);
                if (_timeBucket != null)
                {
                    Date _date = _timeBucket.getTime();
                    _str += _date;
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(_str));
        }
    }

    public void setSelect(String select) 
    {
        this.select = select;
    }

    public String getSelect() 
    {
        return select;
    }
    
    public void handleValueChange(ValueChangeEvent evt) 
    {
        String _newVal = (String)evt.getNewValue();
        UIResourceUtilizationGantt gantt1 = (UIResourceUtilizationGantt)((UIComponent)evt.getSource()).getParent().getParent().findComponent("resourceUtilizationGanttSelection");
        RowKeySet _keys = new RowKeySetImpl();
        _keys.setCollectionModel((TreeModel)gantt1.getValue());

        if (_newVal.indexOf("David") != -1) // Jan. 10
            _keys.add(getKey(0, "01102008000000"));
        else if (_newVal.indexOf("Glen") != -1) // Jan. 10
            _keys.add(getKey(1, "01102008000000"));

        if (gantt1 != null) 
        {
            gantt1.setSelectedTimeBucketRowKeys(_keys);
            RequestContext context = RequestContext.getCurrentInstance();
            context.addPartialTarget(gantt1);
        }
    }

    // The following code to get key is specific to the POJO TreeModel used from this demo.
    // Key generation code for other POJO TreeModel, or TreeModel retrieved from the binding, can be different.
    private Object getKey(Object row, Object id) 
    {
        ArrayList _key = new ArrayList(2);
        ArrayList _row = new ArrayList(1);
        _row.add(row);
        _key.add(_row);
        _key.add(id);
        
        return _key;
    }
}
