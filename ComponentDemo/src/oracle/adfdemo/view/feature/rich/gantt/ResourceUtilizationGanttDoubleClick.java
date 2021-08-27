/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ResourceUtilizationGanttDoubleClick.java /main/2 2009/03/19 21:37:42 teclee Exp $ */

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
    ccchow      06/17/08 - Backing bean for double click
    ccchow      06/17/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ResourceUtilizationGanttDoubleClick.java /main/2 2009/03/19 21:37:42 teclee Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt;

import java.text.DateFormat;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.gantt.TaskbarFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormatManager;
import oracle.adf.view.faces.bi.component.gantt.UIResourceUtilizationGantt;
import oracle.adf.view.faces.bi.event.TimeBucketDoubleClickEvent;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ResourceUtilizationGanttDoubleClick
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

    public void handleDoubleClick(TimeBucketDoubleClickEvent event)
    {
        String _resourceId = event.getResourceId();
        Date _date = event.getDate();
        String _formattedDate = DateFormat.getDateInstance(DateFormat.SHORT).format(_date);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Double click on time bucket: "+_formattedDate+" resource id: "+_resourceId));        
    }
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getResourceUtilizationGanttModel();
        
        return m_model;
    }
}

