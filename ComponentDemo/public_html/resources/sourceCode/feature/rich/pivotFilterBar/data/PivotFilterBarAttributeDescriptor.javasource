/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/data/PivotFilterBarAttributeDescriptor.java /main/3 2011/12/23 08:32:06 jayturne Exp $ */

/* Copyright (c) 2009, 2011, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ccchow      05/01/09 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/data/PivotFilterBarAttributeDescriptor.java /main/2 2010/05/14 11:09:13 jayturne Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotFilterBar.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.ListOfValuesModel;

/**
 * This AttributeDescriptor implementation provides specific information regarding a single
 * filter of the PivotFilterBar and its values. This includes a description, label, length,
 * component type, model, and format of the values to be displayed in the given filter.
 */
public class PivotFilterBarAttributeDescriptor extends AttributeDescriptor
{
    private String m_label;
    private Object m_model;
    private int m_length;
    
    public PivotFilterBarAttributeDescriptor(String label, Object model)
    {
        m_label = label;
        m_model = model;
        
        int length = -1;
        List items = ((ListOfValuesModel)m_model).getItems();
        for (Object o : items)
        {
            HashMap map = (HashMap)o;
            Iterator iter = map.keySet().iterator();
            while (iter.hasNext())
            {
                Object key = iter.next();
                int curLength = map.get(key).toString().length();
                if (curLength > length)
                    length = curLength;
            }
        }
        m_length = length;
    }
    
    public AttributeDescriptor.ComponentType getComponentType()
    {
        return AttributeDescriptor.ComponentType.inputComboboxListOfValues;
    }

    public String getDescription()
    {
        return getLabel();
    }
    
    public String getFormat()
    {
        return null;
    }
    
    public String getLabel()
    {
        return m_label;
    }
    
    public int getLength()
    {
        return m_length;
    }
    
    public int getMaximumLength()
    {
        return -1;
    }
    
    public Object getModel()
    {
        return m_model;
    }
    
    public String getName()
    {
        return m_label;
    }
    
    public Class getType()
    {
        return Object.class;
    }
    
    public boolean isReadOnly()
    {
        return false;
    }
    
    public boolean isRequired()
    {
        return false;
    }
    
    public Set<AttributeDescriptor.Operator> getSupportedOperators()
    {
        return null;   
    }
}
