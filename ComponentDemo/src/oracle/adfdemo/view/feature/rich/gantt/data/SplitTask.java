/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/SplitTask.java /main/2 2009/03/19 21:37:42 teclee Exp $ */

/* Copyright (c) 2007, 2009, Oracle and/or its affiliates. 
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
    ccchow      03/23/07 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/SplitTask.java /main/2 2009/03/19 21:37:42 teclee Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt.data;

import java.util.Date;

public class SplitTask
{
    private String m_id;  
    private String m_name;
    private String m_resource;
    private Date m_startDate;
    private Date m_endDate;
    
    public SplitTask(String id, String name, String resource, Date startDate, Date endDate)
    {
        m_id = id;
        m_name = name;
        m_resource = resource;
        m_startDate = startDate;
        m_endDate = endDate;
    }
    
    public String getSplitId()
    {
        return m_id;
    }
    
    public String getTaskName()
    {
        return m_name;
    }

    public String getResourceName()
    {
        return m_resource;
    }
    
    public Date getStartTime()
    {
        return m_startDate;
    }
    
    public Date getEndTime()
    {
        return m_endDate;
    }
        
    void setStartTime(Date startDate)
    {
        m_startDate = startDate;
    }
    
    void setEndTime(Date endDate)
    {
        m_endDate = endDate;
    }    
}
