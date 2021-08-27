/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttLabelIconPlacement.java /main/1 2010/11/20 06:45:44 imohamma Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    imohamma    11/19/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttLabelIconPlacement.java /main/1 2010/11/20 06:45:44 imohamma Exp $
 *  @author  imohamma
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ProjectGanttLabelIconPlacement
{
    private TreeModel m_model;
    private String m_labelPlacement = "left";
    private String m_iconPlacement = "left";

    public void setIconPlacement(String iconPlacement) {
        m_iconPlacement = iconPlacement;
    }

    public void setLabelPlacement(String labelPlacement) {
        m_labelPlacement = labelPlacement;
    }

    public String getLabelPlacement() {
        return m_labelPlacement;
    }

    public String getIconPlacement() {
        return m_iconPlacement;
    }

    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttLabelIconModel();
  
        return m_model;
    }
}