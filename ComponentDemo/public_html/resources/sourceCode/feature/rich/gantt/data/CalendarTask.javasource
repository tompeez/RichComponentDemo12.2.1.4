/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/CalendarTask.java /main/1 2010/01/25 09:35:38 kiancu Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    kiancu      01/22/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/CalendarTask.java /main/1 2010/01/25 09:35:38 kiancu Exp $
 *  @author  kiancu  
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt.data;

import java.util.Calendar;
import java.util.Date;

public class CalendarTask extends Task
{
    private Calendar m_startDateCal;
    private Calendar m_endDateCal;
   
    public CalendarTask(String id, String name, String resourceId, String resourceName, Calendar startDate, Calendar endDate, String type)
    {
        super(id, name, resourceId, resourceName, null, null, type);
        m_startDateCal = startDate;
        m_endDateCal = endDate;
    }

    public void setStartTime(Calendar startTime)
    {
        m_startDateCal = startTime;
    }

    public Object getStartTime()
    {
        return m_startDateCal;
    }

    public void setEndTime(Calendar endTime)
    {
        m_endDateCal = endTime;
    }

    public Object getEndTime()
    {
        return m_endDateCal;
    }
    
}