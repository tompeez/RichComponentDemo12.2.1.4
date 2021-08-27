/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttBase.java /bibeans_root/2 2014/04/30 16:31:07 jayturne Exp $ */

/* Copyright (c) 2012, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ccchow      11/30/12 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttBase.java /bibeans_root/2 2014/04/30 16:31:07 jayturne Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt;

import oracle.adf.view.faces.bi.component.gantt.TaskbarFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormatManager;
import oracle.adf.view.faces.bi.component.gantt.UISchedulingGantt;

class SchedulingGanttBase
{        
    public String[] getLegendKeys()
    {
        return new String[]{"taskName", "taskType", "startTime", "endTime", "%timezone%"};
    }
    
    public String[] getLegendLabels()
    {
        return new String[]{"Task Name", "Task Type", "Start Time", "End Time", "Time Zone"};
    }
    
    public String[] getTooltipKeys()
    {
        return new String[]{"taskName", "resourceName", "startTime", "endTime"};
    }
    
    public String[] getTooltipLabels()
    {
        return new String[]{"Task", "Resource", "Start Date", "End Date"};
    }

    public TaskbarFormatManager getTaskbarFormatManager()
    {
        TaskbarFormatManager _manager = new TaskbarFormatManager();
        _manager.registerTaskbarFormat("blue", TaskbarFormat.getInstance("Steel Blue", UISchedulingGantt.STEEL_BLUE_FORMAT));
        _manager.registerTaskbarFormat("gold", TaskbarFormat.getInstance("Gold", UISchedulingGantt.GOLD_FORMAT));
        _manager.registerTaskbarFormat("red", TaskbarFormat.getInstance("Red", UISchedulingGantt.BRICK_RED_FORMAT));
        _manager.registerTaskbarFormat("lemon", TaskbarFormat.getInstance("Lemon", UISchedulingGantt.LEMON_FORMAT));
        _manager.registerTaskbarFormat("orange", TaskbarFormat.getInstance("Orange", UISchedulingGantt.ORANGE_FORMAT));
        _manager.registerTaskbarFormat("indigo", TaskbarFormat.getInstance("Indigo", UISchedulingGantt.INDIGO_FORMAT));
        _manager.registerTaskbarFormat("teal", TaskbarFormat.getInstance("Teal", UISchedulingGantt.TEAL_FORMAT));
        _manager.registerTaskbarFormat("green", TaskbarFormat.getInstance("Green", UISchedulingGantt.GREEN_FORMAT));
        _manager.registerTaskbarFormat("lavender", TaskbarFormat.getInstance("Lavender", UISchedulingGantt.LAVENDER_FORMAT));
        _manager.registerTaskbarFormat("lime", TaskbarFormat.getInstance("Lime", UISchedulingGantt.LIME_FORMAT));
        _manager.registerTaskbarFormat("plum", TaskbarFormat.getInstance("Plum", UISchedulingGantt.PLUM_FORMAT));
        _manager.registerTaskbarFormat("mb", TaskbarFormat.getInstance("Midnight Blue", UISchedulingGantt.MIDNIGHT_BLUE_FORMAT));        
        
        return _manager;
    }    
}