/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttBackgroundBars.java /bibeans_root/2 2014/04/30 16:31:07 jayturne Exp $ */

/* Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jayturne    07/10/13 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttBackgroundBars.java /bibeans_root/2 2014/04/30 16:31:07 jayturne Exp $
 *  @author  jayturne
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import oracle.adf.view.faces.bi.component.gantt.BackgroundBarFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormatManager;
import oracle.adf.view.faces.bi.component.gantt.UISchedulingGantt;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class SchedulingGanttBackgroundBars extends SchedulingGanttBase
{    
    private TreeModel m_model;
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getSchedulingGanttBackgroundBarsModel();

        return m_model;
    }
    
    public String[] getLegendKeys()
    {
        return new String[]{"taskName", "taskType", "startTime", "endTime", "%timezone%"};
    }
    
    public String[] getLegendLabels()
    {
        return new String[]{"Task Name", "Task Type", "Start Time", "End Time", "Time Zone"};
    }
    
    @Override    
    public TaskbarFormatManager getTaskbarFormatManager()
    {
        TaskbarFormatManager _manager = new TaskbarFormatManager();
        
        //create and register new colors
        _manager.registerBackgroundBarFormat("fillColor", new BackgroundBarFormat("fillColor", "#f6f7c3", null));
        _manager.registerBackgroundBarFormat("fillColor2", new BackgroundBarFormat("fillColor2", "#d9f4fa", null));
        
        //register predefined colors
        _manager.registerTaskbarFormat("gold", TaskbarFormat.getInstance("Gold", UISchedulingGantt.GOLD_FORMAT));
        _manager.registerTaskbarFormat("green", TaskbarFormat.getInstance("Green", UISchedulingGantt.GREEN_FORMAT));
        _manager.registerTaskbarFormat("orange", TaskbarFormat.getInstance("Orange", UISchedulingGantt.ORANGE_FORMAT));
        _manager.registerTaskbarFormat("lavender", TaskbarFormat.getInstance("Lavender", UISchedulingGantt.LAVENDER_FORMAT));
        _manager.registerTaskbarFormat("lime", TaskbarFormat.getInstance("Lime", UISchedulingGantt.LIME_FORMAT));
        
        //create and register new colors
        _manager.registerTaskbarFormat("blue", new TaskbarFormat("Blue", "#0000FF", null, "#0000FF", 13));
        _manager.registerTaskbarFormat("purple", new TaskbarFormat("Purple", "#5518AB", null, "#5518AB", 13));
        _manager.registerTaskbarFormat("aqua", new TaskbarFormat("Aqua", "#76EEC6", null, "#76EEC6", 13));
        _manager.registerTaskbarFormat("gray", new TaskbarFormat("Gray", "#BEBEBE", null, "#BEBEBE", 13));
        _manager.registerTaskbarFormat("tan", new TaskbarFormat("Tan", "#D2B48C", null, "#D2B48C", 13));
        
        return _manager;
    }
    
}