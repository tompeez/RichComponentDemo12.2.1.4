/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/SampleProjectModel.java /bibeans_root/1 2012/04/20 14:35:16 imohamma Exp $ */

/* Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    imohamma    04/13/12 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/SampleProjectModel.java /bibeans_root/1 2012/04/20 14:35:16 imohamma Exp $
 *  @author  imohamma
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt.data;

import java.util.HashMap;
import java.util.List;

import javax.el.ELResolver;

import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.model.TaskKey;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;

class SampleProjectModel extends ChildPropertyTreeModel implements TaskKey 
{
    public SampleProjectModel() 
    {
        super();
    }
    
    public SampleProjectModel(Object instance, String childProperty) 
    {
        super(instance, childProperty);
    }
    
    public HashMap<String, Object> getKeys(List<String> ids) 
    {
        HashMap<String, Object> keys = new HashMap<String, Object>(ids.size());
        Object oldKey = getRowKey();
        try 
        {
            setRowKey(null);  // root node
            findKeys(ids, keys);
        }
        finally {
            setRowKey(oldKey);
        }
        
        return keys;
    }
    
    // recursive method
    private void findKeys(List<String> ids, HashMap<String, Object> keys) 
    {
        if (ids.size() == keys.size())
            return;
        
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver _resolver = context.getApplication().getELResolver();
        
        for (int i=0; i < getRowCount(); i++) 
        {
            setRowIndex(i);
            Object data = getRowData();
            String taskId = (String)_resolver.getValue(context.getELContext(), data, "taskId");
            if (ids.contains(taskId) && !keys.containsKey(taskId)) 
            {
                keys.put(taskId, getRowKey());
                
                if (ids.size() == keys.size())
                    return;  // we are done
            }
            
            if (isContainer()) 
            {
                enterContainer();
                findKeys(ids, keys);
                exitContainer();
            }
        }
    }
}
