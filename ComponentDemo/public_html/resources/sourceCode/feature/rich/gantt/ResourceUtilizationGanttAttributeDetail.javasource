/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ResourceUtilizationGanttAttributeDetail.java /bibeans_root/1 2012/12/06 07:07:13 imohamma Exp $ */

/* Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    imohamma    11/29/12 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ResourceUtilizationGanttAttributeDetail.java /bibeans_root/1 2012/12/06 07:07:13 imohamma Exp $
 *  @author  imohamma
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import oracle.adf.view.faces.bi.component.gantt.TaskbarFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormatManager;
import oracle.adf.view.faces.bi.component.gantt.UIResourceUtilizationGantt;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ResourceUtilizationGanttAttributeDetail 
{
    private TreeModel m_model;
    
    public String[] getMetrics()
    {
        return new String[] {};
    }
    
    public TaskbarFormatManager getTaskbarFormatManager()
    {
        TaskbarFormatManager _manager = new TaskbarFormatManager();

        _manager.registerTaskbarFormat("Wilson", TaskbarFormat.getInstance("Wilson", UIResourceUtilizationGantt.MIDNIGHT_BLUE_FORMAT));
        _manager.registerTaskbarFormat("Umbro", TaskbarFormat.getInstance("Umbro", UIResourceUtilizationGantt.BRICK_RED_FORMAT));
        _manager.registerTaskbarFormat("Rbk", TaskbarFormat.getInstance("Rbk", UIResourceUtilizationGantt.LAVENDER_FORMAT));
        _manager.registerTaskbarFormat("Puma", TaskbarFormat.getInstance("Puma", UIResourceUtilizationGantt.TEAL_FORMAT));
        _manager.registerTaskbarFormat("Nike", TaskbarFormat.getInstance("Nike", UIResourceUtilizationGantt.ORANGE_FORMAT));
        _manager.registerTaskbarFormat("Mizuno", TaskbarFormat.getInstance("Mizuno", UIResourceUtilizationGantt.PLUM_FORMAT));
        _manager.registerTaskbarFormat("Mitre", TaskbarFormat.getInstance("Mitre", UIResourceUtilizationGantt.LIME_FORMAT));
        _manager.registerTaskbarFormat("Fila", TaskbarFormat.getInstance("Fila", UIResourceUtilizationGantt.INDIGO_FORMAT));
        _manager.registerTaskbarFormat("Ellesse", TaskbarFormat.getInstance("Ellesse", UIResourceUtilizationGantt.GREEN_FORMAT));
        _manager.registerTaskbarFormat("Asics", TaskbarFormat.getInstance("Asics", UIResourceUtilizationGantt.GOLD_FORMAT));
        _manager.registerTaskbarFormat("Diadora", TaskbarFormat.getInstance("Diadora", UIResourceUtilizationGantt.STEEL_BLUE_FORMAT));
        _manager.registerTaskbarFormat("Adidas", TaskbarFormat.getInstance("Adidas", UIResourceUtilizationGantt.LEMON_FORMAT));

        return _manager;      
    }

    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getResourceUtilizationGanttAttributeDetailModel();
        
        return m_model;
    }
}