/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttAttributeConverterFactory.java /main/2 2010/07/21 12:57:45 jayturne Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

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
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttAttributeConverterFactory.java /main/2 2010/07/21 12:57:45 jayturne Exp $
 *  @author  jayturne
 *  @since   release specific (what release of product did this appear in)
 */
/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttAttributeConverterFactory.java /main/2 2010/07/21 12:57:45 jayturne Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

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
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttAttributeConverterFactory.java /main/2 2010/07/21 12:57:45 jayturne Exp $
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

import oracle.adf.view.faces.bi.component.gantt.AttributeConverterFactory;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ProjectGanttAttributeConverterFactory{
    
    private TreeModel m_model;
    private String m_currentPattern;
    private String m_currentAttribute;
    private AttributeConverterFactoryImpl m_attributeConverterFactory;
    
    public ProjectGanttAttributeConverterFactory()
    {
        m_currentAttribute = "All Dates";
        m_currentPattern = "yyyy.MMMMM.dd GGG hh:mm aaa";
        m_attributeConverterFactory = new AttributeConverterFactoryImpl(m_currentPattern, m_currentAttribute);
    }
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttModel();
  
        return m_model;
    }
    
    public String[] getTooltipKeys()
    {
        return new String[]{"taskName", "resourceName", "startTime", "endTime"};
    }
    
    public String[] getTooltipLabels()
    {
        return new String[]{"Task", "Resource", "Start Date", "End Date"};
    }
    
    public String[] getLegendKeys()
    {
        return new String[]{"taskName", "resourceName", "startTime", "endTime", "%timezone%"};
    }
    
    public String[] getLegendLabels()
    {
        return new String[]{"Task", "Resource", "Start", "End", "Time Zone"};
    }
    
    public void setCurrentPattern(String pattern)
    {
        m_currentPattern = pattern;
        m_attributeConverterFactory.setNewPattern(pattern);
    }
    
    public String getCurrentPattern()
    {
        return m_currentPattern;
    }
    
    public void setCurrentAttribute(String attribute)
    {
        m_currentAttribute = attribute;
        m_attributeConverterFactory.setNewAttribute(attribute);
    }
    
    public String getCurrentAttribute()
    {
        return m_currentAttribute;
    }
    
    public AttributeConverterFactory getAttributeConverterFactory()
    {
        return m_attributeConverterFactory;
    }
    
    public class AttributeConverterFactoryImpl extends AttributeConverterFactory
    {
        private DateTimeConverter m_dateConverter;
        private String m_attribute;
        
        public AttributeConverterFactoryImpl(String currentPattern, String currentAttribute)
        {
            m_dateConverter = (DateTimeConverter)FacesContext.getCurrentInstance().getApplication().createConverter(DateTimeConverter.CONVERTER_ID);
            m_dateConverter.setPattern(currentPattern);
            m_attribute = currentAttribute;
        }
        
        public Converter getAttributeConverter(String attribute)
        {
            if (attribute != null)
            {
                if (attribute.equals(m_attribute))
                    return m_dateConverter;
                if (m_attribute.equals("All Dates") && (attribute.equals("startTime") || attribute.equals("endTime") ||
                    attribute.equals("completedThrough") || attribute.equals("actualStartTime") || attribute.equals("actualEndTime")))
                    return m_dateConverter;
            }
            return null;
        }
        
        public void setNewPattern(String newPattern)
        {
            m_dateConverter.setPattern(newPattern);
        }
        
        public void setNewAttribute(String newAttribute)
        {
            m_attribute = newAttribute;
        }
    }
}