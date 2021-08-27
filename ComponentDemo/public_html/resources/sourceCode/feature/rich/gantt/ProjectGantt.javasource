/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGantt.java /main/4 2010/06/18 10:31:56 kiancu Exp $ */

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
    kiancu      06/17/10 - Show message when percent complete changed
    ccchow      04/08/10 - add client behavior related test method
    hbroek      08/28/08 - Add Gantt samples
    imohamma    07/10/08 - backing bean for projectGantt.jspx
    imohamma    07/10/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGantt.java /main/4 2010/06/18 10:31:56 kiancu Exp $
 *  @author  imohamma
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.event.DataChangeEvent;

import oracle.adf.view.faces.bi.event.GanttActionEvent;
import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ProjectGantt
{
    private TreeModel m_model;

    public TreeModel getModel()
    {
        if (m_model != null)
            return m_model;
    
        m_model = SampleModelFactory.getProjectGanttModel();
        return m_model;
    }

    public void handleAction(GanttActionEvent evt)
    {
        StringBuffer _message = new StringBuffer("Received GanttActionEvent: action=");
        int _type = evt.getActionType();
        if (_type == GanttActionEvent.SHOW_AS_HIER)
            _message.append("SHOW AS HIER");
        else if (_type == GanttActionEvent.SHOW_AS_LIST)
            _message.append("SHOW AS LIST");
        else if (_type == GanttActionEvent.PRINT)
            _message.append("PRINT");
        
        if (_type == GanttActionEvent.SHOW_AS_HIER
            || _type == GanttActionEvent.SHOW_AS_LIST
            || _type == GanttActionEvent.PRINT)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(_message.toString()));
    }

    public void handleDataChanged(DataChangeEvent evt)
    {
        StringBuffer _message = new StringBuffer("Received DataChangeEvent: type=");
        int _type = evt.getActionType();
        if (_type == DataChangeEvent.INDENT)
            _message.append("INDENT");
        else if (_type == DataChangeEvent.OUTDENT)
            _message.append("OUTDENT");
        else if (_type == DataChangeEvent.TIME_CHANGED) 
            _message.append("TIME_CHANGED");
        else if (_type == DataChangeEvent.DURATION_CHANGED) 
            _message.append("DURATION_CHANGED");
        else if (_type == DataChangeEvent.LINK)
            _message.append("LINK");
        else if (_type == DataChangeEvent.UNLINK)
            _message.append("UNLINK");
        else if (_type == DataChangeEvent.DELETE)
            _message.append("DELETE");
        else if (_type == DataChangeEvent.CREATE)
            _message.append("CREATE");
        else if (_type == DataChangeEvent.UPDATE)
            _message.append("UPDATE");
        else if (_type == DataChangeEvent.CUT)
            _message.append("CUT");
        else if (_type == DataChangeEvent.COPY)
            _message.append("COPY");
        else if (_type == DataChangeEvent.PASTE)
            _message.append("PASTE");
        else if (_type == DataChangeEvent.TASK_SPLITTED)
            _message.append("TASK_SPLITTED");
        else if (_type == DataChangeEvent.TASK_MERGED)
            _message.append("TASK_MERGED");
        else if (_type == DataChangeEvent.PROGRESS_CHANGED)
            _message.append("PROGRESS_CHANGED");

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(_message.toString()));
    }

    public String[] getTooltipKeys()
    {
        return new String[]{"taskName", "resourceName", "startTime", "endTime"};
    }
    
    public String[] getTooltipLabels()
    {
        return new String[]{"Task", "Resource", "Start Date", "End Date"};
    }
    
    public String[] getLegendKeys()
    {
        return new String[]{"taskName", "resourceName", "startTime", "endTime", "%timezone%"};
    }
    
    public String[] getLegendLabels()
    {
        return new String[]{"Task", "Resource", "Start", "End", "Time Zone"};
    }

    public String getCurrentTime() {
	return (new java.util.Date(System.currentTimeMillis())).toString();
    }
}
