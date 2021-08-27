/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttDropTarget.java /main/6 2012/12/01 11:15:16 ccchow Exp $ */

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
    kiancu      01/21/10 - Add changes for Generic Converter test
    hbroek      08/28/08 - Add Gantt samples
    imohamma    07/10/08 - check-in src
    ccchow      06/17/08 - Backing bean for double click
    ccchow      06/17/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttDropTarget.java /main/6 2012/12/01 11:15:16 ccchow Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.gantt.TaskDragInfo;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormatManager;
import oracle.adf.view.faces.bi.component.gantt.UISchedulingGantt;
import oracle.adf.view.faces.bi.event.DoubleClickEvent;

import oracle.adf.view.faces.bi.model.Resource;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.Employee;
import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;
import oracle.adfdemo.view.feature.rich.gantt.data.Task;


public class SchedulingGanttDropTarget extends SchedulingGanttBase
{
    private TreeModel m_model;
    private CollectionModel m_treemodel;
    private UISchedulingGantt m_gantt;
    

    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getSchedulingGanttModel();

        return m_model;
    }

    public CollectionModel getTreeTableModel()
    {
        if (m_treemodel == null)
            m_treemodel = ModelUtils.toCollectionModel(this.getTodoList());

        return m_treemodel;
    }

    
    public DnDAction onGanttDrop(DropEvent evt)
    {   
        Transferable _transferable = evt.getTransferable();

        // get the drag source, which is a row key to identify which row has been dragged
        RowKeySetImpl _rowKey = (RowKeySetImpl)_transferable.getTransferData(DataFlavor.ROW_KEY_SET_FLAVOR).getData();

        // set the row index on the table model (source) so I can get the data
        // m_tableModel is the model for the Table (the drag source)
        Integer _row = (Integer)_rowKey.iterator().next();
        m_treemodel.setRowIndex(_row.intValue());

        // see which resource this is dropped on (specific for Scheduling Gantt)
        Object _dropRowKey = evt.getDropSite();
        // not drop on a row in Gantt
        if (_dropRowKey == null)
            return DnDAction.NONE;

        m_model.setRowKey(_dropRowKey);

        Employee _resource = (Employee)m_model.getRowData();

        // see what time slot did this dropped on
        Date _date = _transferable.getData(Date.class);

        // get the Task from the drag source data model
        Task _newTask = (Task)m_treemodel.getRowData();
        long _duration = ((Date)_newTask.getEndTime()).getTime() - ((Date)_newTask.getStartTime()).getTime();
        Date _newEndDate = new Date(_date.getTime() + _duration);
        _newTask.setStartTime(_date);
        _newTask.setEndTime(_newEndDate);

        // update my gantt model by adding a new task (this would probably be different than yours... )
        _resource.addTask(_newTask);

        // remove the task from table
        ((List)m_treemodel.getWrappedData()).remove(_newTask);

        // refresh the table and the gantt
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.addPartialTarget(evt.getDragComponent());
        rc.addPartialTarget(m_gantt);

        // indicate drop is successful
        return DnDAction.COPY;
    }
    
    public Employee findEmployeeById(String id)
    {
        List<Employee> employees = (List<Employee>)m_model.getWrappedData();
        for (Employee employee: employees)
            if (employee.getResourceId().equals(id))
                return employee;
        
        return null;
    }
    
    public List<Task> getTodoList()
    {
        Calendar _cal = Calendar.getInstance();
        _cal.set(Calendar.HOUR_OF_DAY, 9);
        _cal.set(Calendar.MINUTE, 0);
        _cal.set(Calendar.SECOND, 0);
        _cal.add(Calendar.DAY_OF_YEAR, -1);
        
        List<Task> _tasks = new ArrayList<Task>();
        for (int j=0; j<10; j++)
        {            
            if (j%8==0)
            {
                _cal.add(Calendar.DAY_OF_YEAR, 1);
                _cal.set(Calendar.HOUR_OF_DAY, 9);
                _cal.set(Calendar.MINUTE, 0);
                _cal.set(Calendar.SECOND, 0);
            }
            Date _start = _cal.getTime();
            _cal.add(Calendar.HOUR_OF_DAY, 1);
            _cal.add(Calendar.MINUTE, -1);
            Date _end   = _cal.getTime();
            _cal.add(Calendar.MINUTE, 1);

            Task _t = new Task("TaskId-"+j, "TaskName-"+j, null, null, _start, _end, "gold");
            _tasks.add(_t);
        }
        return _tasks;
    }
    public void setGantt(UISchedulingGantt gantt) 
    {
        m_gantt = gantt;        
    }
    
    public UISchedulingGantt getGantt() 
    {
        return m_gantt;
    }

}

