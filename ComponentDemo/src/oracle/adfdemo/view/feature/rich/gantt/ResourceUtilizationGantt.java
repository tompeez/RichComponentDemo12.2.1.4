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
    imohamma    07/10/08 - check in src
    hbroek      06/17/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ResourceUtilizationGantt.java /main/3 2010/10/15 12:04:37 imohamma Exp $
 *  @author  hbroek
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt;

import java.text.DateFormat;

import java.util.Date;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.component.gantt.TaskbarFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormatManager;
import oracle.adf.view.faces.bi.component.gantt.UIProjectGantt;
import oracle.adf.view.faces.bi.component.gantt.UIResourceUtilizationGantt;
import oracle.adf.view.faces.bi.event.DataChangeEvent;
import oracle.adf.view.faces.bi.event.GanttActionEvent;
import oracle.adf.view.faces.bi.event.TimeBucketDoubleClickEvent;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ResourceUtilizationGantt
{
    private TreeModel m_model;
    
    public String[] getMetrics()
    {
        return new String[]{"SETUP", "RUN", "AVAILABLE"};
    }
    
    public TaskbarFormatManager getTaskbarFormatManager()
    {
        TaskbarFormatManager _manager = new TaskbarFormatManager();

        TaskbarFormat _format = TaskbarFormat.getInstance("Run Hours", UIResourceUtilizationGantt.MIDNIGHT_BLUE_FORMAT);
        _format.setStacked(true);

        _manager.registerTaskbarFormat("SETUP", TaskbarFormat.getInstance("Setup Hours", UIResourceUtilizationGantt.BRICK_RED_FORMAT));
        _manager.registerTaskbarFormat("RUN", _format);
        _manager.registerTaskbarFormat("AVAILABLE", TaskbarFormat.getInstance("Available Hours", UIResourceUtilizationGantt.TEAL_FORMAT));

        return _manager;      
    }

    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getResourceUtilizationGanttModel();
        
        return m_model;
    }
    
    public void handleDataChanged(DataChangeEvent event)
    {
        List<String> _resources = event.getResourceIds();
        
        String _resourceString = "";
        if (_resources != null)
        {
            for (String _resource: _resources)
                if (_resourceString == null)
                    _resourceString = _resource;
                else
                    _resourceString += ", " + _resource;
        }
        
        String eventName = "Unknown";
        switch (event.getActionType())
        {
            case DataChangeEvent.INDENT:
                eventName = "INDENT";
            break;
            case DataChangeEvent.OUTDENT:
                eventName = "OUTDENT";
            break;
            case DataChangeEvent.CREATE_RESOURCE:
                eventName = "CREATE_RESOURCE";
            break;
        }
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Data event " + eventName + " for resource(s): " + _resourceString));
    }

    public void handleGanttAction(GanttActionEvent event)
    {
        System.out.println("Event action type:" + event.getActionType());
        
        String eventName = "Unknown";
        switch (event.getActionType()) {
            case GanttActionEvent.PRINT:
                eventName = "PRINT";
            break;
            case GanttActionEvent.REDO:
                eventName = "REDO";
            break;
            case GanttActionEvent.SHOW_AS_HIER:
                eventName = "SHOW AS HIER";
            break;
            case GanttActionEvent.SHOW_AS_LIST:
                eventName = "SHOW AS LIST";
            break;
            case GanttActionEvent.UNDO:
                eventName = "UNDO";
            break;
            case GanttActionEvent.ZOOM:
                eventName = "ZOOM";
            break;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Action event: " + eventName));
    }
}

