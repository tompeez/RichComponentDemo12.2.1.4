/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttExpandAll.java /main/1 2009/07/14 18:59:08 srkalyan Exp $ */

/* Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hbroek      06/30/09 - Additional Shepherd samples
    hbroek      06/25/09 - Additional Shepherd samples
    hbroek      06/25/09 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttExpandAll.java /main/1 2009/07/14 18:59:08 srkalyan Exp $
 *  @author  hbroek  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt;

import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.gantt.UIProjectGantt;
import oracle.adf.view.faces.bi.event.DataChangeEvent;
import oracle.adf.view.faces.bi.event.GanttActionEvent;

import oracle.adf.view.rich.component.rich.input.RichSelectManyCheckbox;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ProjectGanttExpandAll
{
    private TreeModel m_model;
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttModel();

        return m_model;
    }

}


