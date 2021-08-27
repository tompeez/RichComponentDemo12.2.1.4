/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttSelection.java /main/8 2012/12/01 11:15:16 ccchow Exp $ */

/* Copyright (c) 2008, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    imohamma    05/06/10 - NPE check
    imohamma    01/19/10 - new selection APIs
    hbroek      08/28/08 - Add Gantt samples
    kiancu      07/17/08 - clean up, remove gettime methods, add task info to
                           handle selected task method
    kiancu      06/26/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttSelection.java /main/8 2012/12/01 11:15:16 ccchow Exp $
 *  @author  kiancu  
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.component.gantt.UISchedulingGantt;
import oracle.adf.view.faces.bi.event.ResourceSelectionEvent;
import oracle.adf.view.faces.bi.event.TaskSelectionEvent;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;
import oracle.adfdemo.view.feature.rich.gantt.data.Task;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;


public class SchedulingGanttSelection extends SchedulingGanttBase
{    
    private TreeModel m_model;
    private UISchedulingGantt gantt1;
    private String select;

    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getSchedulingGanttModel();

        return m_model;
    }
    
    public void handleSelectedTask(TaskSelectionEvent evt)
    {
        Object _src = evt.getSource();
        if (_src instanceof UISchedulingGantt)
            gantt1 = (UISchedulingGantt)_src;
        String _message = "Received TaskSelectionEvent: ";
        Task _task = (Task)evt.getTask();
        _message += "Task Selected: " + ((_task != null)? _task.getTaskId() : "");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(_message));
    }
    
    public void handleSelectedResource(ResourceSelectionEvent evt) {
        String _message = "Received ResourceSelectionEvent: ";
        _message += "Resource Selected: " + evt.getResourceIds().get(0);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(_message));
    }
    
    public void setGantt1(UISchedulingGantt gantt1) {
        this.gantt1 = gantt1;
    }

    public UISchedulingGantt getGantt1() {
        return gantt1;
    }

    public void handleShowSelectionAction(ActionEvent evt) 
    {
        gantt1 = (UISchedulingGantt)((UIComponent)evt.getSource()).getParent().getParent().findComponent("schedulingGanttSelection");
        if (gantt1 != null) 
        {
            String _str = "Selected Task IDs=";
            FacesContext context = FacesContext.getCurrentInstance();
            RowKeySet _keys = gantt1.getSelectedTaskRowKeys();
            if (_keys != null) 
            {
                int _size = _keys.size();
                int _count = 0;
                for (Object _key : _keys)
                {
                    Object _task = gantt1.getTaskData(_key);
                    _str += (String)context.getApplication().getELResolver().getValue(context.getELContext(), _task, "taskId");
                    _count++;
                    if (_count < _size)
                        _str += ", ";
                }
            }
            context.addMessage(null, new FacesMessage(_str));
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
        gantt1 = (UISchedulingGantt)((UIComponent)evt.getSource()).getParent().getParent().findComponent("schedulingGanttSelection");
        RowKeySet _keys = new RowKeySetImpl();
        _keys.setCollectionModel((TreeModel)gantt1.getValue());
        if (_newVal.indexOf("t31") != -1)  // "t21,t31,t32"
        {
            _keys.add(getKey(1, "t21"));
            _keys.add(getKey(2, "t31"));
            _keys.add(getKey(2, "t32"));
        }
        else if (_newVal.indexOf("t41") != -1)  // "t41,t61"
        {
            _keys.add(getKey(3, "t41"));
            _keys.add(getKey(5, "t61"));
        }
        else if (_newVal.indexOf("t21") != -1) // "t21"
            _keys.add(getKey(1, "t21"));

        if (gantt1 != null) 
        {
            gantt1.setSelectedTaskRowKeys(_keys);
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

