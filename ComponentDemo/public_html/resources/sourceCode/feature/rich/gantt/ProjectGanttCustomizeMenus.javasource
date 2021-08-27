/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttCustomizeMenus.java /bibeans_root/2 2014/08/13 14:37:35 kiancu Exp $ */

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
    kiancu      11/07/11 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttCustomizeMenus.java /bibeans_root/2 2014/08/13 14:37:35 kiancu Exp $
 *  @author  kiancu  
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.gantt.UIProjectGantt;
import oracle.adf.view.faces.bi.event.DataChangeEvent;
import oracle.adf.view.faces.bi.event.GanttActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectManyCheckbox;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

import org.apache.myfaces.trinidad.context.RequestContext;

public class ProjectGanttCustomizeMenus
{
    private TreeModel m_model;
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttModel();

        return m_model;
    }

    public void updateFeaturesOff(ActionEvent event)
    {
        RichSelectManyCheckbox _selector = (RichSelectManyCheckbox)event.getComponent().getParent().findComponent("features");
        if (_selector != null)
        {
            List _selected = (List)_selector.getValue();
            String[] _featuresOff = null;
            if (_selected != null)
            {
                _featuresOff = new String[_selected.size()];
                _selected.toArray(_featuresOff);    
            }
                    
            UIProjectGantt _gantt = (UIProjectGantt)_selector.getParent().getParent().findComponent("projectGanttCustomizeMenus");
            if (_gantt != null)
                _gantt.setFeaturesOff(_featuresOff);
        }
    }
    
    public void updateLayout(ActionEvent event)
    {
        RichInputText _toolbarLayout = (RichInputText)event.getComponent().getParent().findComponent("inputTextToolbarLayout");
        RichInputText _menubarLayout = (RichInputText)event.getComponent().getParent().findComponent("inputTextMenubarLayout");
        RichInputText _tablePopupMenu = (RichInputText)event.getComponent().getParent().findComponent("inputTextTablePopupMenuLayout");
        RichInputText _chartPopupMenu = (RichInputText)event.getComponent().getParent().findComponent("inputTextChartPopupMenuLayout");
        
        if (_toolbarLayout != null)
        {
            String _selected = (String)_toolbarLayout.getValue();
            String[] _toolbarLayoutValue = null;
            if (_selected != null)
            {
                _toolbarLayoutValue = _selected.split(" ");
                //_selected.toArray(_toolbarLayoutValue);    
            }
                    
            UIProjectGantt _gantt = (UIProjectGantt)_toolbarLayout.getParent().getParent().findComponent("projectGanttCustomMenuToolbarLayout");
            if (_gantt != null && null != _toolbarLayoutValue){
                _gantt.setToolBarLayout(_toolbarLayoutValue);
                RequestContext.getCurrentInstance().addPartialTarget(_gantt);//.findComponent(_MENUBAR_ID));//                _gantt.
            }
        }
        
        if (_menubarLayout != null)
        {
            String _selected = (String)_menubarLayout.getValue();
            String[] _menubarLayoutValue = null;
            if (_selected != null)
            {
                _menubarLayoutValue = _selected.split(" ");
            }
                    
            UIProjectGantt _gantt = (UIProjectGantt)_menubarLayout.getParent().getParent().findComponent("projectGanttCustomMenuToolbarLayout");
            if (_gantt != null)
                _gantt.setMenuBarLayout(_menubarLayoutValue);
        }
        
        if (_tablePopupMenu != null)
        {
            String _selected = (String)_tablePopupMenu.getValue();
            String[] _tablePopupMenuValue = null;
            if (_selected != null)
            {
                _tablePopupMenuValue = _selected.split(" ");
            }
                    
            UIProjectGantt _gantt = (UIProjectGantt)_tablePopupMenu.getParent().getParent().findComponent("projectGanttCustomMenuToolbarLayout");
            if (_gantt != null)
                _gantt.setTablePopupMenuLayout(_tablePopupMenuValue);
        }
        
        if (_chartPopupMenu != null)
        {
            String _selected = (String)_chartPopupMenu.getValue();
            String[] _chartPopupMenuValue = null;
            if (_selected != null)
            {
                _chartPopupMenuValue = _selected.split(" ");
            }
                    
            UIProjectGantt _gantt = (UIProjectGantt)_chartPopupMenu.getParent().getParent().findComponent("projectGanttCustomMenuToolbarLayout");
            if (_gantt != null)
                _gantt.setChartPopupMenuLayout(_chartPopupMenuValue);
        }
    }
    
    public void handleGanttAction(GanttActionEvent event)
    {
        // do nothing.. need this so that the features would be enabled in the toolbar and menu bar
    }
    
    public void handleDataChanged(DataChangeEvent event)
    {
        // do nothing.. need this so that the features would be enabled in the toolbar and menu bar        
    }
}

