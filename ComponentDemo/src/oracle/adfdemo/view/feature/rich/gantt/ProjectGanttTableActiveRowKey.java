/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttTableActiveRowKey.java /bibeans_root/2 2013/08/29 15:39:06 ppark Exp $ */

/* Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    kiancu      07/12/13 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttTableActiveRowKey.java /bibeans_root/2 2013/08/29 15:39:06 ppark Exp $
 *  @author  kiancu  
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import java.util.Date;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.component.gantt.UIGantt;

import oracle.adf.view.faces.bi.component.gantt.UIProjectGantt;

import oracle.adf.view.faces.bi.component.gantt.UISchedulingGantt;
import oracle.adf.view.faces.bi.event.TaskSelectionEvent;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;
import oracle.adfdemo.view.feature.rich.gantt.data.Task;

public class ProjectGanttTableActiveRowKey {
    private TreeModel m_model;
    private UIGantt m_gantt;
    
    Task m_task;
    Object m_rowkey;
    
    public TreeModel getModel() {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttModel();

        return m_model;
    }
    
    public void handleActionSetActiveData(ActionEvent event) {
        UIGantt _gantt = getUIGantt(event);
        String _message = "Active Row Key is: ";
        if (_gantt != null){
            Object _currRowKey = null;
            int i = 0;
            do{
                _gantt.setRowIndex(i);
                _currRowKey = _gantt.getRowKey();
                i++;
            }while(i==1);
            _gantt.setTableActiveRowKey(_currRowKey);
            _message += (_currRowKey.toString());
        }
        RequestContext.getCurrentInstance().addPartialTarget(_gantt);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(_message));
    }
    
    private UIGantt getUIGantt(ActionEvent event){
        UIComponent _component = event.getComponent();
        UIComponent _compparent = _component.getParent();
        UIComponent _compparentparent = _compparent.getParent();
        int childCount = _compparentparent.getChildCount();
        List l = _compparentparent.getChildren();
        UIGantt _gantt = (UIGantt)l.get(childCount - 1);
        return _gantt;
    }
    
    public void handleActionGetActiveData(ActionEvent event) {
        UIGantt _gantt = getUIGantt(event);
        String _message = "Active Row Key is: ";
        if (_gantt != null){
            Object o = _gantt.getTableActiveRowKey();
            _message += (o.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(_message));
        }
    }
    
    public void handleStartDateChanged(ValueChangeEvent event) {
        UIComponent _inputDate = event.getComponent();
        UIProjectGantt _gantt =
            (UIProjectGantt)_inputDate.getParent().getParent();
        
        Date _newDate = (Date)event.getNewValue();
        Task _task = (Task)m_model.getRowData();
        if (m_task == null)
            m_task = _task;
        _task.setStartTime(_newDate);

        Object _rowKey = m_model.getRowKey();
        _gantt.setRowKey(_rowKey);
        if (m_rowkey == null)
            m_rowkey = _rowKey;
        RequestContext.getCurrentInstance().addPartialTarget(_inputDate);
    }

    public void handleEndDateChanged(ValueChangeEvent event) {
        UIComponent _inputDate = event.getComponent();
        UIProjectGantt _gantt =
            (UIProjectGantt)_inputDate.getParent().getParent();
     
        Date _newDate = (Date)event.getNewValue();
        Task _task = (Task)m_model.getRowData();
        _task.setEndTime(_newDate);

        Object _rowKey = m_model.getRowKey();
        _gantt.setRowKey(_rowKey);
        RequestContext.getCurrentInstance().addPartialTarget(_inputDate);
    }

    /*
     * add set/get method, otherwise inputDate renders in readonly mode
     */
    public void setTaskStartTime(Date taskStartTime) 
    {
    }

    public Date getTaskStartTime() 
    {
        Task _task = (Task)m_model.getRowData();
        return (Date)_task.getStartTime();
    }

    /*
     * add set/get method, otherwise inputDate renders in readonly mode
     */
    public void setTaskEndTime(Date taskEndTime) 
    {
    }

    public Date getTaskEndTime() 
    {
        Task _task = (Task)m_model.getRowData();
        return (Date)_task.getEndTime();
    }
    
    public void setGantt(UIComponent gantt)
    {
       m_gantt = (UIGantt)gantt;
    }
    
    public UIComponent getGantt()
    {
        return m_gantt;
    }
}
