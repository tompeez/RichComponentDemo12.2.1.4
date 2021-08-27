/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/data/PivotFilterBarAttributeCriterion.java /main/2 2011/12/23 08:32:06 jayturne Exp $ */

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
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/data/PivotFilterBarAttributeCriterion.java /main/1 2009/05/05 15:56:19 ccchow Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotFilterBar.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.ListOfValuesModel;

/**
 * This AttributeCriterion implementation represents a single filter of the PivotFilterBar. The
 * 'getValues()' method returns the currently selected value of the given filter. Each instance
 * of an AttributeCriterion also provides access to an instance of an AttributeDescriptor which
 * provides additional information corresponding to the given filter and its values.
 */
public class PivotFilterBarAttributeCriterion extends AttributeCriterion
{
    private AttributeDescriptor m_descriptor;
    
    public PivotFilterBarAttributeCriterion(String label, ListOfValuesModel model)
    {
        m_descriptor = new PivotFilterBarAttributeDescriptor(label, model);
    }
    
    public AttributeDescriptor getAttribute()
    {
        return m_descriptor;
    }
    
    public AttributeDescriptor.ComponentType getComponentType()
    {
        return getAttribute().getComponentType();
    }
    
    public List<? extends Object> getModelList()
    {
        return null;
    }
    
    public List<? extends java.lang.Object> getValues()
    {
        Object _value = ((PivotFilterBarListOfValuesModel)getAttribute().getModel()).getSelected();
        ArrayList _list = new ItemsList(1);
        _list.add(_value);
        return _list;
    }
    
    public AttributeDescriptor.Operator getOperator()
    {
        return null;
    }

    public void setOperator(AttributeDescriptor.Operator operator)
    {
    }
    
    public Map<String, AttributeDescriptor.Operator> getOperators()
    {
        return null;
    }
    
    public boolean hasDependentCriterion(int index)
    {
        return false;
    }

    public boolean isRemovable() 
    {
        return false;
    }    

    class ItemsList extends ArrayList
    {
        ItemsList(int capacity)
        {
            super(capacity);
        }
    
        public Object set(int index, Object value)
        {
            ((PivotFilterBarListOfValuesModel)getAttribute().getModel()).setSelected(value);    
            return super.set(index, value);
        } 
    }
}

