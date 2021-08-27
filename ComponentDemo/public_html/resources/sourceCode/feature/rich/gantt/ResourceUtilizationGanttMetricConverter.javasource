/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ResourceUtilizationGanttMetricConverter.java /main/2 2011/10/11 12:47:50 kiancu Exp $ */

/* Copyright (c) 2010, 2011, Oracle and/or its affiliates. 
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
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ResourceUtilizationGanttMetricConverter.java /main/1 2010/05/04 12:25:57 jayturne Exp $
 *  @author  jayturne
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class ResourceUtilizationGanttMetricConverter
{
    private TreeModel m_model;
    private Converter m_metricConverter;
    private boolean m_applyConverter;
    
    public ResourceUtilizationGanttMetricConverter()
    {
        m_applyConverter = false;
    }
    
    public String[] getMetrics()
    {
        return new String[]{"SETUP", "RUN", "AVAILABLE"};
    }
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getResourceUtilizationGanttModel();
        
        return m_model;
    }
    
    public void setApplyConverter(boolean applyConverter)
    {
        m_applyConverter = applyConverter;
    }

    public boolean getApplyConverter()
    {
        return m_applyConverter;
    }
    
    public Converter getMetricConverter()
    {
        if (m_applyConverter)
        {
            if (m_metricConverter == null)
                m_metricConverter = new MetricConverterImpl();
            return m_metricConverter;
        }
        return null;
    }
    
    public class MetricConverterImpl implements Converter{
        
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String string)
        {
            return string;
        }
        
        @Override
        public String getAsString(FacesContext context, UIComponent component, Object object)
        {
            if (object instanceof Double)
            {
                Double doub = (Double)object;
                if (doub.equals(16.0))
                    return "XVI";
                else if (doub.equals(10.0))
                    return "X";
                else if (doub.equals(8.0))
                    return "VIII";
                else
                    return "IV";
            }
            else
                return object.toString();
        }
    }
}
