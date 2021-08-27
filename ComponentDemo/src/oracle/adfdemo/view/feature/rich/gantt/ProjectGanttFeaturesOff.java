/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttFeaturesOff.java /main/2 2009/03/19 21:37:42 teclee Exp $ */

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
    ccchow      06/19/08 - fix empty selection causes NPE
    ccchow      06/17/08 - Backing bean for features off
    ccchow      06/17/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttFeaturesOff.java /main/2 2009/03/19 21:37:42 teclee Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.gantt.UIProjectGantt;
import oracle.adf.view.faces.bi.event.DataChangeEvent;
import oracle.adf.view.faces.bi.event.GanttActionEvent;

import oracle.adf.view.rich.component.rich.input.RichSelectManyCheckbox;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ProjectGanttFeaturesOff
{
    private TreeModel m_model;
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttModel();

        return m_model;
    }

    public void updateFeaturesOff(ActionEvent event)
    {
        RichSelectManyCheckbox _selector = (RichSelectManyCheckbox)event.getComponent().getParent().findComponent("features");
        if (_selector != null)
        {
            List _selected = (List)_selector.getValue();
            String[] _featuresOff = null;
            if (_selected != null)
            {
                _featuresOff = new String[_selected.size()];
                _selected.toArray(_featuresOff);    
            }
                    
            UIProjectGantt _gantt = (UIProjectGantt)_selector.getParent().getParent().findComponent("projectGanttFeaturesOff");
            if (_gantt != null)
                _gantt.setFeaturesOff(_featuresOff);
        }
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

