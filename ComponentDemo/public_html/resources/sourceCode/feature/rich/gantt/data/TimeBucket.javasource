/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/TimeBucket.java /main/4 2012/11/30 23:36:11 imohamma Exp $ */

/* Copyright (c) 2008, 2012, Oracle and/or its affiliates. 
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
    ccchow      06/17/08 - Represents a time bucket
    ccchow      06/17/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/TimeBucket.java /main/4 2012/11/30 23:36:11 imohamma Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt.data;

import java.util.Date;

public class TimeBucket
{
    private Date m_time;
    private double m_val1;
    private double m_val2;
    private double m_val3;
    private double m_val4;
    
    public TimeBucket(Date time, double value1, double value2, double value3)
    {
        m_time = time;
        m_val1 = value1;
        m_val2 = value2;
        m_val3 = value3;
    }

    public TimeBucket(Date time, double value1, double value2, double value3, double value4)
    {
        m_time = time;
        m_val1 = value1;
        m_val2 = value2;
        m_val3 = value3;
        m_val4 = value4;
    }
    
    public Date getTime()
    {
        return m_time;
    }

    public double getValue1()
    {
        return m_val1;
    }

    public double getValue2()
    {
        return m_val2;
    }

    public double getValue3()
    {
        return m_val3;
    }

    public double getValue4()
    {
        return m_val4;
    }

    public double[] getValues()
    {
        if (m_val4 > 0)
            return new double[]{m_val1, m_val2, m_val3, m_val4};
        else
            return new double[]{m_val1, m_val2, m_val3};
    }
}

