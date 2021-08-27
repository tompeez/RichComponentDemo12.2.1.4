/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ResourceUtilizationGanttSteppedLine.java /bibeans_root/1 2012/11/27 13:37:11 imohamma Exp $ */

/* Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    imohamma    11/02/12 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ResourceUtilizationGanttSteppedLine.java /bibeans_root/1 2012/11/27 13:37:11 imohamma Exp $
 *  @author  imohamma
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.gantt.MetricFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormatManager;
import oracle.adf.view.faces.bi.component.gantt.UIResourceUtilizationGantt;
import oracle.adf.view.faces.bi.event.DataChangeEvent;
import oracle.adf.view.faces.bi.event.GanttActionEvent;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ResourceUtilizationGanttSteppedLine 
{
    private TreeModel m_model;
    
    public String[] getMetrics()
    {
        return new String[]{"SETUP", "RUN", "AVAILABLE", "THRESHOLD"};
    }
    
    public TaskbarFormatManager getTaskbarFormatManager()
    {
        TaskbarFormatManager _manager = new TaskbarFormatManager();

        TaskbarFormat _format = TaskbarFormat.getInstance("Run Hours", UIResourceUtilizationGantt.MIDNIGHT_BLUE_FORMAT);
        _format.setStacked(true);

        _manager.registerTaskbarFormat("SETUP", TaskbarFormat.getInstance("Setup Hours", UIResourceUtilizationGantt.BRICK_RED_FORMAT));
        _manager.registerTaskbarFormat("RUN", _format);
        _manager.registerTaskbarFormat("AVAILABLE", TaskbarFormat.getInstance("Available Hours", UIResourceUtilizationGantt.TEAL_FORMAT));

        MetricFormat _threshold = new MetricFormat("threshold", "#FF0000", null, "#FF0000", MetricFormat.Display.STEPPED_LINE);
        _manager.registerTaskbarFormat("THRESHOLD", _threshold);

        return _manager;      
    }

    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getResourceUtilizationGanttSteppedLineModel();
        
        return m_model;
    }
}