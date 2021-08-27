/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttZoomToFit.java /bibeans_root/1 2011/09/23 10:48:25 kiancu Exp $ */

/* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    kiancu      06/15/11 - Creation
 */

/**
 *  @version $Header: ProjectGanttZoomToFit.java 15-jun-2011.14:22:28 kiancu   Exp $
 *  @author  kiancu  
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.event.GanttActionEvent;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ProjectGanttZoomToFit
{
    private TreeModel m_model;
    
    public void handleAction(GanttActionEvent evt)
    {
        StringBuffer _message = new StringBuffer("Received GanttActionEvent: action=");
        int _type = evt.getActionType();
        
        if (_type == GanttActionEvent.ZOOM_TO_FIT)
        {
            String zoomto = evt.getZoomTo();
            _message.append("ZOOM TO FIT: " + zoomto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(_message.toString()));
        }
    }
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttModel();

        return m_model;
    }
}
