/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttResourceAction.java /bibeans_root/1 2016/03/23 12:11:56 kiancu Exp $ */

/* Copyright (c) 2013, 2016, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jayturne    07/10/13 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttResourceAction.java /bibeans_root/1 2016/03/23 12:11:56 kiancu Exp $
 *  @author  jayturne
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.gantt.BackgroundBarFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormatManager;
import oracle.adf.view.faces.bi.component.gantt.TimeAxis;
import oracle.adf.view.faces.bi.component.gantt.UISchedulingGantt;

import oracle.adf.view.faces.bi.event.ResourceActionEvent;

import oracle.adf.view.faces.bi.event.ResourceSelectionEvent;

import oracle.adfdemo.view.feature.rich.gantt.data.BackgroundBar;
import oracle.adfdemo.view.feature.rich.gantt.data.Employee;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

import org.apache.myfaces.trinidad.context.RequestContext;

public class SchedulingGanttResourceAction extends SchedulingGanttBase
{    
    private TreeModel m_model;
    
    private BackgroundBar _timeBucketBackgroundBar = new BackgroundBar(new Date(), new Date(), "yellow");
    private Employee _timeBucketResource = null;
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getSchedulingGanttResourceActionModel();

        return m_model;
    }
    
    @Override
    public TaskbarFormatManager getTaskbarFormatManager()
    {
        TaskbarFormatManager _manager = new TaskbarFormatManager();
        
        _manager.registerBackgroundBarFormat("yellow", new BackgroundBarFormat("yellow", "#ffff00", null));
        
        _manager.registerTaskbarFormat("blue", TaskbarFormat.getInstance("Steel Blue", UISchedulingGantt.STEEL_BLUE_FORMAT));
        
        return _manager;
    }
    
    public void handleSelectedResource(ResourceSelectionEvent evt) {
        String _message = "Received ResourceSelectionEvent: ";
        _message += "Resource Selected: " + evt.getResourceIds().get(0);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(_message));
    }
    
    private Employee findResource(String resourceId)
    {
        ArrayList _resources = (ArrayList)m_model.getWrappedData();
        for (int i=0; i<_resources.size(); i++)
        {
            Employee _resource = (Employee)_resources.get(i);
            if (resourceId.equals(_resource.getResourceId()))
                return _resource;
        }
        return null;
    }
    
    public void handleResourceAction(ResourceActionEvent evt) {
        String _resourceId = evt.getResourceId();
        Date _date = evt.getDate();
        
        Employee _resource = new Employee(_resourceId, _resourceId, _resourceId);
        
        if (_resourceId != null)
            _resource = findResource(_resourceId);
        
        UISchedulingGantt _gantt = null;
        Object _src = evt.getSource();
        if (_src instanceof UISchedulingGantt)
            _gantt = (UISchedulingGantt)_src;
        
        UIComponent _axis = _gantt.getMinorAxis();
        String _scale = ((TimeAxis) _axis).getScale();
        
        Date _backgroundStartDate = new Date();
        Date _backgroundEndDate = new Date();
        if (_scale.equals("hours")) {
            _backgroundStartDate = new Date(_date.toString());
            _backgroundStartDate.setMinutes(0);
            _backgroundStartDate.setSeconds(0);
            _backgroundEndDate = new Date(_date.toString());
            _backgroundEndDate.setMinutes(0);
            _backgroundEndDate.setSeconds(0);
            _backgroundEndDate.setHours(_backgroundStartDate.getHours() + 1);
        }else if (_scale.equals("days")){
            _backgroundStartDate = new Date(_date.toString());
            _backgroundStartDate.setMinutes(0);
            _backgroundStartDate.setSeconds(0);
            _backgroundStartDate.setHours(0);
            _backgroundEndDate = new Date(_backgroundStartDate.toString());
            _backgroundEndDate.setDate(_backgroundStartDate.getDate() + 1);
        } else if (_scale.equals("months")){
            _backgroundStartDate = new Date(_date.toString());
            _backgroundStartDate.setMinutes(0);
            _backgroundStartDate.setSeconds(0);
            _backgroundStartDate.setHours(0);
            _backgroundStartDate.setDate(1);
            _backgroundEndDate = new Date(_backgroundStartDate.toString());
            _backgroundEndDate.setMonth(_backgroundStartDate.getMonth() + 1);
        }
        
        if (_timeBucketResource != null)
            _timeBucketResource.removeBackgroundBar(_timeBucketBackgroundBar);
        _timeBucketBackgroundBar.setStartTime(_backgroundStartDate);
        _timeBucketBackgroundBar.setEndTime(_backgroundEndDate);
        _resource.addBackgroundBar(_timeBucketBackgroundBar);
        _timeBucketResource = _resource; 
        
        RequestContext.getCurrentInstance().addPartialTarget(_gantt);
    }
}