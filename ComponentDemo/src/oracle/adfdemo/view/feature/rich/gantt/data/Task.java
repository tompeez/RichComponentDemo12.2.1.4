/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/Task.java /main/6 2011/09/06 09:20:21 kiancu Exp $ */

/* Copyright (c) 2007, 2011, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    kiancu      01/21/10 - Add Generic Converter Demo
    hbroek      07/22/09 - Remove label alignment from task and add set seom
                           additional labels on the Gantt test model
    hbroek      08/28/08 - Add Gantt samples
    imohamma    12/27/07 - 
    ccchow      05/16/07 - add duration to task
    imohamma    04/12/07 - 
    ccchow      04/05/07 - add setter for align methods
    ccchow      03/23/07 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/Task.java /main/5 2010/06/22 11:44:09 ytang Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adfdemo.view.feature.rich.gantt.GanttConstants;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.context.RenderingContext;

public class Task 
{
    private static final String ON_TIME = "ontime";
    private static final String LATE = "late";

    private String m_id;
    private String m_name;
    private String m_resourceId;
    private String m_resourceName;
    private Date m_startDate;
    private Date m_endDate;
    private Date m_actualStartDate;
    private Date m_actualEndDate;
    private String m_type;
    private int m_complete;
    private String m_label;
    private int m_labelAlign = GanttConstants.LEFT;
    private String m_icon1;
    private String m_icon2;
    private String m_icon3;
    private int m_iconAlign = GanttConstants.RIGHT;
    private String m_status = ON_TIME;
    private String m_priority = "p0";
    private int m_startupTime;
    private boolean m_filtered = false;

    private List<Task> m_tasks;
    private List<TaskDependency> m_dependencies;

    private List m_splits;
    private Task m_parent;

    private int m_counter = 1;
    private String m_editsAllowed;
    
    public Task(String id, String name, String resourceId, String resourceName, Date startDate, Date endDate, String type)
    {
        m_id = id;
        m_name = name;
        m_resourceId = resourceId;
        m_resourceName = resourceName;
        m_startDate = startDate;
        m_endDate = endDate;
        
        m_type = type;
        m_tasks = new ArrayList<Task>(10);        
    }
    
    public Task(String id, String name, String resourceId, String resourceName, Date startDate, Date endDate, String type, String editsAllowed)
    {
        m_id = id;
        m_name = name;
        m_resourceId = resourceId;
        m_resourceName = resourceName;
        m_startDate = startDate;
        m_endDate = endDate;
        
        m_type = type;
        m_editsAllowed = editsAllowed;
        m_tasks = new ArrayList<Task>(10);        
    }
    
    void setParent(Task parent)
    {
        m_parent = parent;
    }
    
    Task getParent()
    {
        return m_parent;
    }

    public List getSplitTasks()
    {
        return m_splits;
    }

    SplitTask getSplit(String sid)
    {
        if (m_splits != null)
        {
            for (int i=0; i<m_splits.size(); i++)
            {
                SplitTask _split = (SplitTask)m_splits.get(i);
                if (_split.getSplitId().equals(sid))
                    return _split;
            }
        }
        return null;
    }

    public void addSplits(Date startDate, Date endDate)
    {
        if (m_splits == null)        
            m_splits = new ArrayList(10);

        String _sid = "s"+m_counter;            
        m_splits.add(new SplitTask(_sid, getTaskName(), getResourceName(), startDate, endDate));
        m_counter++;
    }

    public void addDependency(TaskDependency dependency)
    {
        if (m_dependencies == null)        
            m_dependencies = new ArrayList<TaskDependency>(10);

        m_dependencies.add(dependency);
    }
    
    public void removeDependency(Task task)
    {
        if (m_dependencies != null)
        {
            TaskDependency _found = null;
            for (int i=0; i<m_dependencies.size(); i++)
            {
                TaskDependency _dep = m_dependencies.get(i);
                if (_dep.getToId().equals(task.getTaskId()))
                {
                    _found = _dep;
                    break;
                }
            }
            
            if (_found != null)
                m_dependencies.remove(_found);
        }
    }
    
    public List getDependencies()
    {
        return m_dependencies;
    }

    public void addSubtask(Task task)
    {
        m_tasks.add(task);
        task.setParent(this);
    }

    void removeSubtask(Task task)
    {
        m_tasks.remove(task);
    }

    public String getTaskId()
    {
        return m_id;
    }

    public String getTaskName()
    {
        return m_name;
    }
    
    public void setTaskName(String taskName)
    {
        m_name = taskName;
    }

    public String getTaskType()
    {
        //Map _map = RenderingContext.getCurrentInstance().getProperties();
        //if (_map.get("isScheduling") != null)
        //    return m_priority;
        //else
            return m_type;
    }

    public void setTaskType(String type)
    {
        m_type = type;
    }

    public String getResourceId()
    {
        return m_resourceId;
    }

    public void setResourceId(String resourceId)
    {
        m_resourceId = resourceId;
    }
    
    public String getResourceName()
    {
        return m_resourceName;
    }
    
    public void setResourceName(String resourceName)
    {
        m_resourceName = resourceName;
    }

    public String getIcon1()
    {
        return m_icon1;
    }

    public void setIcon1(String icon)
    {
        m_icon1 = icon;
    }

    public String getIcon2()
    {
        return m_icon2;
    }

    public void setIcon2(String icon)
    {
        m_icon2 = icon;
    }
    
    public String getIcon3()
    {
        return m_icon3;
    }

    public void setIcon3(String icon)
    {
        m_icon3 = icon;
    }
    
    public String getEditsAllowed(){
        return m_editsAllowed;
    }
    
    public void setEditsAllowed(String editsAllowed){
        m_editsAllowed = editsAllowed;
    }
    
/*    public int getLabelAlign()
    {
        return m_labelAlign;
    }

    public void setLabelAlign(int align)
    {
        m_labelAlign = align;
    }
*/    
    public int getIconAlign()
    {
        return m_iconAlign;
    }

    public void setIconAlign(int align)
    {
        m_iconAlign = align;
    }

    public Date getActualStartTime()
    {
        return m_actualStartDate;
    }
    
    public void setActualStartTime(Date actualStartDate)
    {
        m_actualStartDate = actualStartDate;
    }
    
    public Date getActualEndTime()
    {
        return m_actualEndDate;
    }
    
    public void setActualEndTime(Date actualEndDate)
    {
        m_actualEndDate = actualEndDate;
    }

    public String getDuration()
    {
        if (m_endDate != null)
            return Math.round((m_endDate.getTime() - m_startDate.getTime())/(1000*60*60*24)) + " days";
        else
            return "0 days";
    }

    public String getLabel()
    {
      return m_label;
    }

    public void setLabel(String label)
    {
        m_label = label;
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
    
    public int getPercentComplete()
    {
        return m_complete;
    }

    public void setPercentComplete(int complete)
    {
        m_complete = complete;
    }

    public List getSubTasks()
    {
        ArrayList _subTasks = new ArrayList(m_tasks.size());
        for (int i=0; i<m_tasks.size(); i++)
        {
            if (!m_tasks.get(i).isFiltered())
                _subTasks.add(m_tasks.get(i));
        }
        return _subTasks;
    }
    
    public boolean isFiltered()
    {
        return m_filtered;
    }

    public void setFiltered(boolean filtered)
    {
        m_filtered = filtered;
    }
    
    // Scheduling task specific methods
    public int getStartupTime()
    {
        return m_startupTime;
    }
    
    public void setStartupTime(int startupTime)
    {
        m_startupTime = startupTime;
    }
    
    public String getPriority()
    {
        return m_priority;
    }

    public void setPriority(String priority)
    {
        m_priority = priority;
    }
    
    public String getStatus()
    {
        return m_status;
    }

    public void setStatus(String status)
    {
        m_status = status;
    }
    
    // update callback methods
    public void startTimeChanged(ValueChangeEvent evt)
    {
        Date _newStartTime = (Date)evt.getNewValue();
        setStartTime(_newStartTime);
        refreshGantt(); 
    }

    public void endTimeChanged(ValueChangeEvent evt)
    {
        Date _newEndTime = (Date)evt.getNewValue();
        setEndTime(_newEndTime);
        refreshGantt();   
    }

    public List getRecurringTasks() 
    {
        return null;
    }

    public void setRecurringTasks(List recurringTasks) 
    {
    }

    private void refreshGantt()
    {
        UIComponent _gantt = FacesContext.getCurrentInstance().getViewRoot().findComponent("mygantt");
        if (_gantt != null)
            AdfFacesContext.getCurrentInstance().addPartialTarget(_gantt);                                            
    }
}
