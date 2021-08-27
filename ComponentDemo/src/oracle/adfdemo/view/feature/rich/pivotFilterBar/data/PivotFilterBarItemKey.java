/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/data/PivotFilterBarItemKey.java /main/2 2011/12/23 08:32:06 jayturne Exp $ */

/* Copyright (c) 2010, 2011, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jayturne    05/19/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/data/PivotFilterBarItemKey.java /main/1 2010/05/21 12:49:31 jayturne Exp $
 *  @author  jayturne
 *  @since   release specific (what release of product did this appear in)
 */
 package oracle.adfdemo.view.feature.rich.pivotFilterBar.data;

import java.io.Serializable;

/**
 * This ItemKey implementation is used to keep track of the currently selected value of
 * a filter of the PivotFilterBar. It is used by the custom ListOfValuesModel class.
 */
public class PivotFilterBarItemKey implements Serializable
{
    static final long serialVersionUID = 1L;
    private int index;
    private String label;
    
    public String getLabel()
    {
        return this.label;
    }
    
    public void setLabel(String label)
    {
        this.label = label;
    }

    public int getIndex()
    {
        return this.index;
    }
    
    public void setIndex(int index)
    {
        this.index = index;
    }
    
    public String toString()
    {
        return this.label;
    }
    
    @Override
    public boolean equals(Object value)
    {
        if (value instanceof PivotFilterBarItemKey)
        {
            if (this.index == ((PivotFilterBarItemKey)value).index)
                return true;
        }
        else if (value instanceof String)
        {
            if (this.label.equals(value.toString()))
                return true;
        }
        return false;
    }
}