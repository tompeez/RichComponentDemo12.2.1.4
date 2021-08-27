/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttGoToDateConverter.java /main/2 2012/12/01 11:15:16 ccchow Exp $ */

/* Copyright (c) 2010, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jayturne    04/27/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttGoToDateConverter.java /main/2 2012/12/01 11:15:16 ccchow Exp $
 *  @author  jayturne
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import javax.faces.convert.DateTimeConverter;

import javax.faces.model.SelectItem;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class SchedulingGanttGoToDateConverter extends SchedulingGanttBase
{    
    private TreeModel m_model;
    private String m_currentPattern;
    private DateTimeConverter m_goToDateConverter;
    
    public SchedulingGanttGoToDateConverter()
    {
        m_currentPattern = "EEE, d MMM yyyy HH:mm:ss Z";
        
        m_goToDateConverter = (DateTimeConverter)FacesContext.getCurrentInstance().getApplication().createConverter(DateTimeConverter.CONVERTER_ID);
        m_goToDateConverter.setPattern(m_currentPattern);
    }
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getSchedulingGanttModel();

        return m_model;
    }
        
    public void setCurrentPattern(String pattern)
    {
        m_currentPattern = pattern;
        m_goToDateConverter.setPattern(pattern);
    }
    
    public String getCurrentPattern()
    {
        return m_currentPattern;
    }
    
    public Converter getGoToDateConverter()
    {
        return m_goToDateConverter;
    }
}