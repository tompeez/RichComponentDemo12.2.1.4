/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/timeline/EmpEvent.java /bibeans_root/4 2014/08/12 21:40:08 jayturne Exp $ */

/* Copyright (c) 2011, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ccchow      07/09/11 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/timeline/EmpEvent.java /bibeans_root/4 2014/08/12 21:40:08 jayturne Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.timeline;

import java.util.Date;

public class EmpEvent
{
    public static final String PERSON = "person";
    public static final String EMPLOYMENT = "employment";
    public static final String COMPENSATION = "compensation";
    public static final String STOCK = "stock";
    public static final String TRAINING = "training";

    private String m_id;
    private Date m_date;
    private Date m_endDate;
    private String m_description;
    private String m_type;
    private String m_group;
    
    public EmpEvent(String id)
    {
        m_id = id;
        m_date = new Date();
        m_description = "Fill in a description";
        m_type = PERSON;
    }

    public EmpEvent(String id, Date date, String description, String type, String group)
    {
        super();
        m_id = id;
        m_date = date;
        m_description = description;
        m_type = type;
        m_group = group;
    }
    
    public EmpEvent(String id, Date date, Date endDate, String description, String type, String group)
    {
        super();
        m_id = id;
        m_date = date;
        m_endDate = endDate;
        m_description = description;
        m_type = type;
        m_group = group;
    }

    public String getId()
    {
        return m_id;
    }

    public Date getDate()
    {
        return m_date;
    }

    public void setDate(Date date)
    {
        m_date = date;
    }
    
    public Date getEndDate()
    {
        return m_endDate;
    }

    public void setEndDate(Date endDate)
    {
        m_endDate = endDate;
    }
    
    public String getDescription()
    {
        return m_description;
    }

    public void setDescription(String description)
    {
        m_description = description;
    }

    public String getType()
    {
        return m_type;
    }
    
    public void setType(String type)
    {
        m_type = type;
    }
    
    public String getGroup()
    {
        return m_group;
    }    

    public void setGroup(String group)
    {
        m_group = group;
    }    
}
