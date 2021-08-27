/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/TaskDependency.java /main/2 2009/03/19 21:37:42 teclee Exp $ */

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
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/TaskDependency.java /main/2 2009/03/19 21:37:42 teclee Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt.data;

public class TaskDependency
{
    private Task m_from;
    private Task m_to;
    private String m_type;
    
    public TaskDependency(Task from, Task to, String type)
    {
        m_from = from;
        m_to = to;
        m_type = type;
    }
    
    public String getFromId()
    {
        return m_from.getTaskId();
    }
    
    public String getToId()
    {
        return m_to.getTaskId();
    }
    
    public String getType()
    {
        return m_type;
    }
}
