/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/BackgroundBar.java /bibeans_root/1 2013/07/18 22:22:46 jayturne Exp $ */

/* Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jayturne    07/10/13 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/BackgroundBar.java /bibeans_root/1 2013/07/18 22:22:46 jayturne Exp $
 *  @author  jayturne
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt.data;

import java.util.Date;

public class BackgroundBar
{
    private Date m_startDate;
    private Date m_endDate;
    private String m_type;
   
    public BackgroundBar(Date startDate, Date endDate, String type)
    {
        m_startDate = startDate;
        m_endDate = endDate;
        m_type = type;
    }
    
    public String getType()
    {
        return m_type;
    }

    public void setType(String type)
    {
        m_type = type;
    }

    public void setStartTime(Date startTime)
    {
        m_startDate = startTime;
    }
    
    public Object getStartTime()
    {
        return m_startDate;
    }

    public void setEndTime(Date endTime)
    {
        m_endDate = endTime;
    }

    public Object getEndTime()
    {
        return m_endDate;
    }
}
