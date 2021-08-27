/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/TimeBucketAttributeDetail.java /bibeans_root/1 2012/12/06 07:07:12 imohamma Exp $ */

/* Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    imohamma    11/29/12 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/TimeBucketAttributeDetail.java /bibeans_root/1 2012/12/06 07:07:12 imohamma Exp $
 *  @author  imohamma
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt.data;

import java.util.Date;

public class TimeBucketAttributeDetail extends TimeBucket 
{
    private double[] m_attributeValues;
    private String[] m_attributeFormatLabels;
    
    public TimeBucketAttributeDetail(Date time, double[] attrValues, String[] attrFormatLabels)
    {
        super(time, 0, 0, 0);
        m_attributeValues = attrValues;
        m_attributeFormatLabels = attrFormatLabels;
    }

    public TimeBucketAttributeDetail(Date time, double value1, double value2, double value3, double[] attrValues, String[] attrFormatLabels)
    {
        super(time, value1, value2, value3);
        m_attributeValues = attrValues;
        m_attributeFormatLabels = attrFormatLabels;
    }

    public TimeBucketAttributeDetail(Date time, double value1, double value2, double value3, double value4, double[] attrValues, String[] attrFormatLabels)
    {
        super(time, value1, value2, value3, value4);
        m_attributeValues = attrValues;
        m_attributeFormatLabels = attrFormatLabels;
    }

    public double[] getValues()
    {
        return new double[] {};
    }

    public double[] getAttributeValues() 
    {
        return m_attributeValues;
    }
    
    public String[] getAttributeFormatLabels()
    {
        return m_attributeFormatLabels;
    }
}