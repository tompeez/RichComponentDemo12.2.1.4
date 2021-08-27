/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/timeline/Employee.java /bibeans_root/3 2011/07/13 15:39:02 ccchow Exp $ */

/* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ccchow      07/10/11 - Creation
 */

/**
 *  @version $Header: Employee.java 10-jul-2011.09:41:30 ccchow   Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.timeline;

public class Employee
{
    private String m_id;
    private String m_name;
    
    public Employee(String id, String name)
    {
        m_id = id;
        m_name = name;
    }
    
    public String getId()
    {
        return m_id;
    }
    
    public String getName()
    {
        return m_name;
    }
}