/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/Employee.java /main/4 2016/03/23 12:11:56 kiancu Exp $ */

/* Copyright (c) 2007, 2016, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hbroek      08/28/08 - Add Gantt samples
    ccchow      06/17/08 - add time bucket accessors
    ccchow      03/23/07 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/Employee.java /main/4 2016/03/23 12:11:56 kiancu Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Employee
{
    private String m_id;
    private String m_name;
    private String m_dept;
    private String m_workingStartTime;
    private String m_workingEndTime;
    private int[] m_workingDaysOfTheWeek = new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY};

    private List<Task> m_tasks;
    private List<TimeBucket> m_buckets;
    private List<BackgroundBar> m_backgroundBars;
    
    public Employee(String id, String name, String dept)
    {
        m_id = id;    
        m_name = name;
        m_dept = dept;        
    }

    public Employee(String id, String name, String dept, String workingStartTime, String workingEndTime, int[] workingDaysOfTheWeek)
    {
        this(id, name, dept);
        m_workingStartTime = workingStartTime;
        m_workingEndTime = workingEndTime;
        
        if (workingDaysOfTheWeek != null)
            m_workingDaysOfTheWeek = workingDaysOfTheWeek;
    }
    
    public String getResourceId()
    {
        return m_id;
    }
    
    public String getResourceName()
    {
        return m_name;
    }

    public String getDepartment()
    {
        return m_dept;
    }
    
    public List getTasks()
    {
        return m_tasks;
    }
    
    public void addTask(Task task)
    {
        if (m_tasks == null)
            m_tasks = new ArrayList<Task>(10);
            
        m_tasks.add(task);        
    }
    
    public List getBackgroundBars()
    {
        return m_backgroundBars;
    }
    
    public void addBackgroundBar(BackgroundBar backgroundBar)
    {
        if (m_backgroundBars == null)
            m_backgroundBars = new ArrayList<BackgroundBar>(10);
            
        m_backgroundBars.add(backgroundBar);        
    }
    
    public void removeBackgroundBar(BackgroundBar backgroundBar)
    { 
        if (m_backgroundBars == null)
            return;
            
        m_backgroundBars.remove(backgroundBar);
    }
    
    public void removeTask(Task task)
    {
        m_tasks.remove(task);
    }
    
    public List getSubResources()
    {
        return null;
    }
    
    public String getWorkingStartTime()
    {
        return m_workingStartTime;
    }
    
    public String getWorkingEndTime()
    {
        return m_workingEndTime;
    }
    
    public int[] getWorkingDaysOfTheWeek()
    {
        return m_workingDaysOfTheWeek;
    }

    // used by the Resource Utilization Gantt samples
    public List<TimeBucket> getTimeBuckets()
    {
        return m_buckets;
    }
    
    public void addBucket(TimeBucket bucket)
    {
        if (m_buckets == null)
            m_buckets = new ArrayList<TimeBucket>(10);

        m_buckets.add(bucket);        
    }
}
