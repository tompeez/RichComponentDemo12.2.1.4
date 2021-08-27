/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttTimeZone.java /main/3 2012/12/01 11:15:16 ccchow Exp $ */

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
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttTimeZone.java /main/3 2012/12/01 11:15:16 ccchow Exp $
 *  @author  jayturne
 *  @since   release specific (what release of product did this appear in)
 */
/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttTimeZone.java /main/3 2012/12/01 11:15:16 ccchow Exp $ */

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
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttTimeZone.java /main/3 2012/12/01 11:15:16 ccchow Exp $
 *  @author  jayturne
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class SchedulingGanttTimeZone extends SchedulingGanttBase
{    
    private TreeModel m_model;
    private TimeZone m_timeZone;
    private String m_currentTimeZone;
    
    public SchedulingGanttTimeZone()
    {
        m_timeZone = TimeZone.getTimeZone("America/New_York");
        m_currentTimeZone = "America/New_York";
    }
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getSchedulingGanttModel();

        return m_model;
    }
        
    public void setCurrentTimeZone(String timeZone)
    {
        m_timeZone = TimeZone.getTimeZone(timeZone);
        m_currentTimeZone = timeZone;
    }
    
    public String getCurrentTimeZone()
    {
        return m_currentTimeZone;
    }
            
    public TimeZone getTimeZone()
    {
        return m_timeZone;
    }
    
    public Date getStartTime()
    {
        return parseDate("12/21/2006 01:00");
    }
    
    public Date getEndTime()
    {
        return parseDate("12/22/2006 23:00");
    }
    
    private static Date parseDate(String date)
    {
        Date ret = null;
        try
        {
            if (date.indexOf(':') > -1)
                ret = HOUR_FORMAT.parse(date);
            else
                ret = DAY_FORMAT.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    private static DateFormat DAY_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private static DateFormat HOUR_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");
}
