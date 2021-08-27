/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttDragSource.java /main/2 2009/03/19 21:37:41 teclee Exp $ */

/* Copyright (c) 2008, 2009, Oracle and/or its affiliates. 
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
    imohamma    07/10/08 - check-in src
    ccchow      06/17/08 - Backing bean for double click
    ccchow      06/17/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttDragSource.java /main/2 2009/03/19 21:37:41 teclee Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt;

import java.util.ArrayList;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.gantt.TaskDragInfo;
import oracle.adf.view.faces.bi.event.DoubleClickEvent;

import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;

import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;
import oracle.adfdemo.view.feature.rich.gantt.data.Task;

public class ProjectGanttDragSource
{
    private TreeModel m_model;
    private TreeModel m_tableModel;

    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttModel();

        return m_model;
    }
    
    public TreeModel getTreeTableModel()
    {
        if (m_tableModel == null) {
            m_tableModel = new ChildPropertyTreeModel(new ArrayList<Task>(), "subTasks");
        }

        return m_tableModel;
    }
    
    public DnDAction onTableDrop(DropEvent evt)
    {   
        DataFlavor<TaskDragInfo> _flv = DataFlavor.getDataFlavor(TaskDragInfo.class, null);
        Transferable _transferable = evt.getTransferable();

        TaskDragInfo _info = _transferable.getData(_flv);
        if (_info == null)
            return DnDAction.COPY;
        
        Task _draggedTask = findTask(_info.getTaskId(), (ArrayList<Task>)m_model.getWrappedData());
        
        if (_draggedTask != null) {
            List<Task> _taskList = (List<Task>)m_tableModel.getWrappedData();
            _taskList.add(_draggedTask);
            m_tableModel.setWrappedData(_taskList);
            RequestContext.getCurrentInstance().addPartialTarget(evt.getDragComponent());
        }
        return DnDAction.COPY;
    }
    
    
    private Task findTask(String taskId, List<Task> tasks)
    {
        for (Task task: tasks)
        {
            System.out.println(task.getTaskName());
            if (task.getTaskId().equals(taskId))
                return task;
            else if (task.getSubTasks() != null)
            {
                Task _subTask = findTask(taskId, task.getSubTasks());
                if (_subTask != null)
                    return _subTask;
            }        
        }
        return null;
    }
    
}