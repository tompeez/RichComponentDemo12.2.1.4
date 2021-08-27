/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttDoubleClick.java /main/3 2014/10/17 11:42:32 ccchow Exp $ */

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
    ccchow      06/17/08 - Backing bean for double click
    ccchow      06/17/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttDoubleClick.java /main/3 2014/10/17 11:42:32 ccchow Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.event.DoubleClickEvent;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ProjectGanttDoubleClick
{
    private TreeModel m_model;
    private static DateFormat s_format = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
    
    public void handleDoubleClick(DoubleClickEvent event)
    {
        String _taskId = event.getTaskId();
        Date _time = event.getTime();
        if (_time != null)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Double click on time: "+s_format.format(_time)));       
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Double click on task: "+_taskId));        
    }
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttModel();

        return m_model;
    }
}
