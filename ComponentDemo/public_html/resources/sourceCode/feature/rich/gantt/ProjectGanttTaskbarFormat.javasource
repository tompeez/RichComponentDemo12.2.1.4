/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttTaskbarFormat.java /main/2 2009/03/19 21:37:41 teclee Exp $ */

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
    kiancu      07/22/08 - .
    kiancu      07/09/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttTaskbarFormat.java /main/2 2009/03/19 21:37:41 teclee Exp $
 *  @author  kiancu  
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import java.util.ArrayList;

import javax.faces.component.UIComponent;

import oracle.adf.view.faces.bi.component.gantt.Filter;
import oracle.adf.view.faces.bi.component.gantt.FilterManager;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormatManager;

import oracle.adf.view.faces.bi.component.gantt.UIGantt;

import org.apache.myfaces.trinidad.model.RowKeySetTreeImpl;
import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ProjectGanttTaskbarFormat
{
    private TreeModel m_model;
    private UIGantt m_gantt;
    private TaskbarFormatManager m_taskbarFormatManager;
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGantTaskbarFormattModel();

        return m_model;
    }
    
    public void setGantt(UIComponent gantt)
    {
       m_gantt = (UIGantt)gantt;
       
       if (m_taskbarFormatManager == null){
            //cannot create new TaskbarFormatManager because Project Gantt has predefined TaskbarFormats and creating a new
            //manager would override them, so must get existing TaskbarFormatManager
            m_taskbarFormatManager = m_gantt.getTaskbarFormatManager();
            TaskbarFormat _format = new TaskbarFormat("Blue", "#76EEC6", null, "#76EEC6", 13);
            m_taskbarFormatManager.registerTaskbarFormat("custom", _format);
        }
    }
    
    public UIComponent getGantt()
    {
        return m_gantt;
    }
    
}
