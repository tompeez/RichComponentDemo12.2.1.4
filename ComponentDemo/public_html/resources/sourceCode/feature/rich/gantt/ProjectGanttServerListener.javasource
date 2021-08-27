/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttServerListener.java /bibeans_root/1 2012/12/14 13:07:15 ccchow Exp $ */

/* Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ccchow      12/07/12 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttServerListener.java /bibeans_root/1 2012/12/14 13:07:15 ccchow Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.render.ClientEvent;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ProjectGanttServerListener
{
    private TreeModel m_model;
    
    public void handleCustomEvent(ClientEvent event)
    {
        String _key = "skyIsFalling";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Received client event with parameter: "+_key+" value: "+event.getParameters().get(_key)));        
    }
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttModel();

        return m_model;
    }
}