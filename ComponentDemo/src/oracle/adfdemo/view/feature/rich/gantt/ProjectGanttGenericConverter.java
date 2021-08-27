/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttGenericConverter.java /main/1 2010/01/25 09:35:38 kiancu Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    kiancu      01/21/10 - .
    kiancu      01/21/10 - Add Generic Converter Demo
    kiancu      01/21/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttGenericConverter.java /main/1 2010/01/25 09:35:38 kiancu Exp $
 *  @author  kiancu  
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

public class ProjectGanttGenericConverter
{
    private TreeModel m_model;
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttModelCalendar();

        return m_model;
    }

}


