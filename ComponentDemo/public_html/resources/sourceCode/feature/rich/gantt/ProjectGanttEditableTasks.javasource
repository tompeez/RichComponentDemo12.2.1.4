
/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttEditableTasks.java /bibeans_root/1 2011/09/06 09:20:21 kiancu Exp $ */

/* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    kiancu      07/01/11 - Creation
 */

/**
 *  @version $Header: ProjectGanttEditableTasks.java 01-jul-2011.13:17:45 kiancu   Exp $
 *  @author  kiancu  
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.event.DataChangeEvent;
import oracle.adf.view.faces.bi.event.DoubleClickEvent;

import oracle.adf.view.faces.bi.event.GanttActionEvent;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ProjectGanttEditableTasks
{
    private TreeModel m_model;
      
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttModelEditableTasks();

        return m_model;
    }
    
    public void handleGanttAction(GanttActionEvent event)
    {
        // do nothing.. need this so that the features would be enabled in the toolbar and menu bar
    }
    
    public void handleDataChanged(DataChangeEvent event)
    {
        // do nothing.. need this so that the features would be enabled in the toolbar and menu bar        
    }
}
