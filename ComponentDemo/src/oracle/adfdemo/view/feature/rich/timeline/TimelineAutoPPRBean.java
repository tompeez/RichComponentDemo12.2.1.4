/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/timeline/TimelineAutoPPRBean.java /bibeans_root/5 2011/12/09 08:38:56 ccchow Exp $ */

/* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ccchow      07/08/11 - Creation
 */

/**
 *  @version $Header: TimelineAutoPPRBean.java 08-jul-2011.15:00:57 ccchow   Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.timeline;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;
import java.util.Iterator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.component.timeline.UITimeline;
import oracle.adf.view.faces.bi.component.timeline.UITimelineSeries;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;

import org.apache.myfaces.trinidad.model.RowKeySet;

import oracle.adfdemo.view.feature.rich.timeline.model.UpdateableCollectionModel;

@ManagedBean(name="autoppr")
@RequestScoped
public class TimelineAutoPPRBean
{
    private UpdateableCollectionModel m_model;
    private EmpEvent m_newEvent;
    private Object m_currentRowKey;

    private int m_current = 1;

    public TimelineAutoPPRBean()
    {
    }

    public EmpEvent getCurrentEvent()
    {
        if (m_currentRowKey == null)
            return null;
        else
            return (EmpEvent)m_model.getRowData(m_currentRowKey);
    }

    public EmpEvent getNewEvent()
    {
        if (m_newEvent ==  null)
            m_newEvent = new EmpEvent(Integer.toString(m_current++));

        return m_newEvent;
    }

    // filtered active data testing
    public CollectionModel getUpdateableModel()
    {
        if (m_model != null)
            return m_model;

        ArrayList _list = new ArrayList(10);
        _list.add(new EmpEvent("0", parseDate("04/12/2011"), "New Hire", EmpEvent.EMPLOYMENT, null));

        m_model = new UpdateableCollectionModel(_list, "id");
        return m_model;
    }
    
    public void handleRefresh(ActionEvent event)
    {
        ArrayList _list = new ArrayList(10);
        _list.add(new EmpEvent("0", parseDate("04/12/2011"), "New Hire", EmpEvent.EMPLOYMENT, null));

        m_model.setWrappedData(_list);

        m_model.refresh();
    }
    
    public void handleUpdate(ActionEvent event)
    {
        if (m_currentRowKey != null)
        {
            EmpEvent _current = getCurrentEvent();

            // put the event in the right place
            boolean _needToMove = false;
            ArrayList _list = (ArrayList)m_model.getWrappedData();
            if (_list.size() > 1)
            {
                int _index = _list.indexOf(_current);
                if (_index < _list.size()-1 && _current.getDate().after(((EmpEvent)_list.get(_index+1)).getDate()))
                    _needToMove = true;
                else if (_index > 0 && _current.getDate().before(((EmpEvent)_list.get(_index-1)).getDate()))
                    _needToMove = true;

                if (_needToMove)
                {
                    boolean flag = _list.remove(_current);
                    if (flag)
                    {
                        int _newIndex = -1;
                        for (int i=0; i<_list.size(); i++)
                        {
                            EmpEvent _event = (EmpEvent)_list.get(i);
                            if (_current.getDate().before(_event.getDate()))
                                _newIndex = i;
                        }

                        if (_newIndex != -1)
                            _list.add(_newIndex, _current);
                    }
                }
            }
            m_model.updateRow(m_currentRowKey, _current);
        }
    }
    
    public void handleInsert(ActionEvent event)
    {
        Date _new = m_newEvent.getDate();

        int _count = m_model.getRowCount();
        EmpEvent _last = (EmpEvent)m_model.getRowData(_count-1);
        if (_new.after(_last.getDate()))
            m_model.insertRow(m_newEvent);
        else
        {
            Object _rowKey = null;
            for (int i=0; i<_count; i++)
            {
                m_model.setRowIndex(i);
                EmpEvent _current = (EmpEvent)m_model.getRowData();
                if (_new.before(_current.getDate()))
                {
                    _rowKey = m_model.getRowKey();
                    break;
                }
            }

            m_model.insertRow(_rowKey, m_newEvent);
        }

        // clear it
        m_newEvent = null;
    }
    
    public void handleDelete(ActionEvent event)
    {        
        UITimelineSeries _timelineSeries = (UITimelineSeries)event.getComponent().getParent().getParent().getParent().findComponent("tl1:ts1");
        RowKeySet _selectedRowKeys = _timelineSeries.getSelectedRowKeys();
        if (_selectedRowKeys != null && _selectedRowKeys.size() > 0)
        {
            Iterator<Object> _iterator = _selectedRowKeys.iterator();
            while (_iterator.hasNext())
            {
                Object _rowKey = _iterator.next();
                m_model.deleteRow(_rowKey);
            }
        }
        
        m_currentRowKey = null;
    }
    
    public void handleSelect(SelectionEvent event)
    {
        RowKeySet _added = event.getAddedSet();
        if (_added != null && _added.size() > 0)
            m_currentRowKey = _added.iterator().next();
        else
            m_currentRowKey = null;

        UIComponent _panel = event.getComponent().getParent().getParent().findComponent("updatePanel");
        if (_panel != null)
            RequestContext.getCurrentInstance().addPartialTarget(_panel.getParent());
    }

    private static Date parseDate(String date)
    {
        Date ret = null;
        try
        {
            ret = s_format.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return ret;
    }
    static DateFormat s_format = new SimpleDateFormat("MM/dd/yyyy");
}