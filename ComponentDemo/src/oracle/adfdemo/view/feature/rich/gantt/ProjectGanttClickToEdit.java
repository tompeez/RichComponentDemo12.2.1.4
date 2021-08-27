/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttClickToEdit.java /main/3 2010/10/15 12:04:37 imohamma Exp $ */

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
    ccchow      06/23/08 - add the guts
    ccchow      06/17/08 - Backing bean for click to edit
    ccchow      06/17/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttClickToEdit.java /main/3 2010/10/15 12:04:37 imohamma Exp $
 *  @author  ccchow
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.component.gantt.UIGantt;

import oracle.adf.view.faces.bi.component.gantt.UIProjectGantt;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;
import oracle.adfdemo.view.feature.rich.gantt.data.Task;

public class ProjectGanttClickToEdit {
    private TreeModel m_model;

    public TreeModel getModel() {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttModel();

        return m_model;
    }

    public void handleStartDateChanged(ValueChangeEvent event) {
        UIComponent _inputDate = event.getComponent();
        UIProjectGantt _gantt =
            (UIProjectGantt)_inputDate.getParent().getParent();

        Date _newDate = (Date)event.getNewValue();
        Task _task = (Task)m_model.getRowData();
        _task.setStartTime(_newDate);

        Object _rowKey = m_model.getRowKey();
        _gantt.setRowKey(_rowKey);
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
}
