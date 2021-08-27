/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttCustomMenubarToolbar.java /main/2 2009/03/19 21:37:41 teclee Exp $ */

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
                           projectGanttCustomMenubarToolbar.jspx
    imohamma    07/10/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttCustomMenubarToolbar.java /main/2 2009/03/19 21:37:41 teclee Exp $
 *  @author  imohamma
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletResponse;

import oracle.adf.view.faces.bi.component.gantt.GanttPrinter;
import oracle.adf.view.faces.bi.component.gantt.UIProjectGantt;
import oracle.adf.view.faces.bi.event.DataChangeEvent;
import oracle.adf.view.faces.bi.event.GanttActionEvent;

import oracle.xdo.template.FOProcessor;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ProjectGanttCustomMenubarToolbar
{
    private TreeModel m_model;

    public void handleCustomCommand1()
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Custom Command 1"));
    }
    
    public void handleCustomCommand2()
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Custom Command 2"));
    }
    public void handleCustomCommand3()
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Custom Command 3"));
    }

    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttModel();

        return m_model;
    }
}
