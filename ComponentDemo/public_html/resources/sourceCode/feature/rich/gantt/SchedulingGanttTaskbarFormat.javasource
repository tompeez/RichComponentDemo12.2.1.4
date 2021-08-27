/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttTaskbarFormat.java /main/4 2014/05/05 09:51:12 jayturne Exp $ */

/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hbroek      08/28/08 - Add Gantt samples
    kiancu      07/09/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttTaskbarFormat.java /main/4 2014/05/05 09:51:12 jayturne Exp $
 *  @author  kiancu  
 *  @since   release specific (what release of product did this appear in)
 */

/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttTaskbarFormat.java /main/4 2014/05/05 09:51:12 jayturne Exp $ */

/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    kiancu      06/26/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttTaskbarFormat.java /main/4 2014/05/05 09:51:12 jayturne Exp $
 *  @author  kiancu  
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import oracle.adf.view.faces.bi.component.gantt.TaskbarFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormatManager;
import oracle.adf.view.faces.bi.component.gantt.UISchedulingGantt;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class SchedulingGanttTaskbarFormat extends SchedulingGanttBase
{    
    private TreeModel m_model;
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getSchedulingGanttTaskbarFormatModel();

        return m_model;
    }
    
    @Override    
    public TaskbarFormatManager getTaskbarFormatManager()
    {
        TaskbarFormatManager _manager = new TaskbarFormatManager();
        
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
        

        /*_manager.registerTaskbarFormat("blue", new TaskbarFormat("Blue", "#0000FF", null, "#76EEC6", 13));
        _manager.registerTaskbarFormat("purple", new TaskbarFormat("Purple", "#5518AB", null, "#76EEC6", 13));
        _manager.registerTaskbarFormat("aqua", new TaskbarFormat("Aqua", "#76EEC6", null, "#76EEC6", 13));
        _manager.registerTaskbarFormat("gray", new TaskbarFormat("Gray", "#BEBEBE", null, "#76EEC6", 13));
        _manager.registerTaskbarFormat("tan", new TaskbarFormat("Tan", "#D2B48C", null, "#76EEC6", 13));*/

        //this doesn't have the issue with the border
        /*_manager.registerTaskbarFormat("blue", new TaskbarFormat("Blue", "#0000FF", null, null, 13));
        _manager.registerTaskbarFormat("purple", new TaskbarFormat("Purple", "#5518AB", null, null, 13));
        _manager.registerTaskbarFormat("aqua", new TaskbarFormat("Aqua", "#76EEC6", null, null, 13));
        _manager.registerTaskbarFormat("gray", new TaskbarFormat("Gray", "#BEBEBE", null, null, 13));
        _manager.registerTaskbarFormat("tan", new TaskbarFormat("Tan", "#D2B48C", null, null, 13));*/
        
        return _manager;
    }
    
}


