/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ResourceUtilizationGanttStacking.java /main/2 2009/03/19 21:37:41 teclee Exp $ */

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
    imohamma    07/10/08 - backing bean for
                           resourceUtilizationGanttStacking.jspx
    imohamma    07/10/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ResourceUtilizationGanttStacking.java /main/2 2009/03/19 21:37:41 teclee Exp $
 *  @author  imohamma
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

public class ResourceUtilizationGanttStacking
{
    private TreeModel m_model;
    
    public String[] getMetrics()
    {
        return new String[]{"SETUP", "RUN", "AVAILABLE"};
    }
    
    public TaskbarFormatManager getTaskbarFormatManager()
    {
        TaskbarFormatManager _manager = new TaskbarFormatManager();

        TaskbarFormat _runFormat = TaskbarFormat.getInstance("Run Hours", UIResourceUtilizationGantt.MIDNIGHT_BLUE_FORMAT);
        _runFormat.setStacked(true);
        TaskbarFormat _setupFormat = TaskbarFormat.getInstance("Setup Hours", UIResourceUtilizationGantt.BRICK_RED_FORMAT);
        _setupFormat.setStacked(true);
        TaskbarFormat _availableFormat = TaskbarFormat.getInstance("Available Hours", UIResourceUtilizationGantt.TEAL_FORMAT);
        _availableFormat.setStacked(true);

        _manager.registerTaskbarFormat("SETUP", _setupFormat);
        _manager.registerTaskbarFormat("RUN", _runFormat);
        _manager.registerTaskbarFormat("AVAILABLE", _availableFormat);

        return _manager;      
    }

    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getResourceUtilizationGanttModel();
        
        return m_model;
    }
}
