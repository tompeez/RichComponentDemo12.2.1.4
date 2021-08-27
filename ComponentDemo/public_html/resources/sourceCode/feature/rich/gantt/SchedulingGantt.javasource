/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGantt.java /main/3 2012/12/01 11:15:16 ccchow Exp $ */

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
    hbroek      08/28/08 - Add Gantt samples
    kiancu      07/17/08 - clean up, remove gettime methods, add tooltips
    kiancu      06/19/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGantt.java /main/3 2012/12/01 11:15:16 ccchow Exp $
 *  @author  kiancu  
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.event.DataChangeEvent;

import oracle.adf.view.faces.bi.event.GanttActionEvent;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class SchedulingGantt extends SchedulingGanttBase 
{    
    private TreeModel m_model;
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getSchedulingGanttModel();

        return m_model;
    }
        
    public void handleDataChanged(DataChangeEvent evt)
    {
        String _message = "Received DataChangeEvent: action=";
        int _type = evt.getActionType();
        
        if (_type == DataChangeEvent.CREATE)
        {
            _message += "CREATE";
        }
        else if (_type == DataChangeEvent.UPDATE)
        {
            _message += "UPDATE";
        }
        else if (_type == DataChangeEvent.CUT)
        {
            _message += "CUT";
        }
        else if (_type == DataChangeEvent.COPY)
        {
            _message += "COPY";
        }
        else if (_type == DataChangeEvent.PASTE)
        {
            _message += "PASTE";
        }
        else if (_type == DataChangeEvent.DELETE)
        {
            _message += "DELETE";
        }
        else if (_type == DataChangeEvent.INDENT)
        {
            _message += "INDENT";
        }
        else if (_type == DataChangeEvent.OUTDENT)
        {
            _message += "OUTDENT";
        }
        else if (_type == DataChangeEvent.TASK_SPLITTED)
        {
            _message += "TASK SPLITTED";
        }
        else if (_type == DataChangeEvent.TASK_MERGED)
        {
            _message += "TASK MERGE";
        }
        else if (_type == DataChangeEvent.TIME_CHANGED)
        {
            _message += "Move task: "+evt.getTaskId() + " from resource=" + evt.getFromResourceId() + " to resource=" + evt.getToResourceId() + " newStartTime=" + evt.getNewStartTime();
        }
        else if (_type == DataChangeEvent.DURATION_CHANGED)
        {
            _message += "RESIZE taskid=" + evt.getTaskId() + " newEndTime=" + evt.getNewEndTime();
        }
        else if (_type == DataChangeEvent.CREATE_RESOURCE)
        {
            _message += "CREATE RESOURCE";
        }
        else if (_type == DataChangeEvent.LINK)
        {
            _message += "LINK";
        }
        else if (_type == DataChangeEvent.UNLINK)
        {
            _message += "UNLINK";
        }
        else if (_type == DataChangeEvent.UPDATE_RESOURCE)
        {
            _message += "UPDATE RESOURCE";
        }
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(_message));
    }
    
    public void handleAction(GanttActionEvent evt)
    {
        String _message = "Received DataChangeEvent: action=";
        int _type = evt.getActionType();
        
        if (_type == GanttActionEvent.PRINT)
            _message += "PRINT";
        else if (_type == GanttActionEvent.UNDO)
            _message += "UNDO";
        else if (_type == GanttActionEvent.REDO)
            _message += "REDO";
        else if (_type == GanttActionEvent.SHOW_AS_LIST)
            _message += "SHOW_AS_LIST";
        else if (_type == GanttActionEvent.SHOW_AS_HIER)
            _message += "SHOW_AS_HIER";
        else if (_type == GanttActionEvent.ZOOM)
            _message += "ZOOM";
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(_message));
    }
}