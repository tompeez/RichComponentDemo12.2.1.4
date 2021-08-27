/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/timeline/TimelineDnDBean.java /bibeans_root/2 2011/12/09 08:38:56 ccchow Exp $ */

/* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ccchow      07/20/11 - Creation
 */

/**
 *  @version $Header: TimelineDnDBean.java 20-jul-2011.14:01:42 ccchow   Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.timeline;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;

import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;

@ManagedBean(name="dnd")
@RequestScoped
public class TimelineDnDBean
{
    private CollectionModel m_timelineModel;
    private CollectionModel m_tableModel;

    public TimelineDnDBean()
    {
        super();
    }

    public CollectionModel getTimelineModel()
    {
        if (m_timelineModel == null)
        {
            ArrayList _list = new ArrayList(50);
            m_timelineModel = ModelUtils.toCollectionModel(_list);                        
        }
        return m_timelineModel;
    }
    
    public CollectionModel getTableModel()
    {
        if (m_tableModel == null)
        {
            ArrayList _list = new ArrayList(50);
    
            _list.add(new EmpEvent("0", null, "Oracle Application Express", "se198AyXcsk", null));
            _list.add(new EmpEvent("1", null, "Larry Ellison on the Sun-Oracle Close", "ylNgcD2Ay6M", null));
            _list.add(new EmpEvent("2", null, "Oracle Exadata: Consolidating Database Applications", "cAYw2zcSIPw", null));
            _list.add(new EmpEvent("3", null, "HRMall and PeopleSoft: Human Capital Delivered to the Boardroom ", "akzfduL0MZ0", null));
            _list.add(new EmpEvent("4", null, "Code Ninja Chronicles Episode 404", "qeAk0TQCMZ4", null));
            _list.add(new EmpEvent("5", null, "Code Ninja Chronicles Episode 127.0.0.1", "oAM0fDsDFSQ", null));
    
            _list.add(new EmpEvent("6", null, "Oracle Open World 2010", "tWB0fR-buJ4", null));
            _list.add(new EmpEvent("7", null, "Larry Ellison, CEO, Oracle: OpenWorld", "tWB0fR-buJ4", null));
            _list.add(new EmpEvent("8", null, "Safra Catz, President, Oracle: OpenWorld", "oAPsLPA83M0", null));
            _list.add(new EmpEvent("9", null, "Mark Hurd, President, Oracle: OpenWorld", "kSJvK7f3pQ8", null));
            _list.add(new EmpEvent("10", null, "Judy Sim, CMO, Oracle: OpenWorld", "KEmlU08z6i8", null));
    
            _list.add(new EmpEvent("11", null, "Up Close: Interview with NZOUGs Lynne O'Donoghue", "1-SYw-fjVHc", null));
            _list.add(new EmpEvent("12", null, "Oracle OpenWorld 2009 Keynote Highlights", "pEkwXgUOzxU", null));
            _list.add(new EmpEvent("13", null, "Data Warehousing Best Practices Star Schemas", "LfehTEyglrQ", null));
            _list.add(new EmpEvent("14", null, "The Golden Parachute", "DrZXtkAoJKM", null));
            _list.add(new EmpEvent("15", null, "Stronger Together: Up Close Interview with Ecuador's Paola Pullas", "MG0P68X9tDo", null));
            _list.add(new EmpEvent("16", null, "Lady Java", "Mk3qkQROb_k", null));
            _list.add(new EmpEvent("17", null, "Eliminate Idle Redundancy in Your Data Center", "msip5B6jTTo", null));
    
            _list.add(new EmpEvent("18", null, "Larry Ellison, CEO, Oracle: OpenWorld", "tWB0fR-buJ4", null));
            _list.add(new EmpEvent("19", null, "Eliminate Idle Redundancy in Your Data Center", "msip5B6jTTo", null));
            _list.add(new EmpEvent("20", null, "Second block event", "msip5B6jTTo", null));
            _list.add(new EmpEvent("21", null, "Far far away event", "msip5B6jTTo", null));
    
            m_tableModel = ModelUtils.toCollectionModel(_list);
        }
        
        return m_tableModel;
    }

    public DnDAction handleDropOnTimeline(DropEvent event)
    {
        Date _date = (Date)event.getDropSite();       
        Transferable _transferable = event.getTransferable();
        RowKeySet _rowKeySet = _transferable.getData(DataFlavor.ROW_KEY_SET_FLAVOR);
        Object _rowKey = _rowKeySet.iterator().next();
        
        EmpEvent _event = (EmpEvent)m_tableModel.getRowData(_rowKey);
        _event.setDate(_date);

        orderInsert(_event);

        RequestContext.getCurrentInstance().addPartialTarget(event.getDragComponent());
        return DnDAction.COPY;
    }

    private void orderInsert(EmpEvent event)
    {
        int _index = -1;
        ArrayList _list = (ArrayList)m_timelineModel.getWrappedData();
        for (int i=0; i<_list.size(); i++)
        {
            EmpEvent _current = (EmpEvent)_list.get(i);
            if (event.getDate().before(_current.getDate()))
            {
                _index = i;
                break;
            }
        }
        
        if (_index == -1)
            _list.add(event);
        else
            _list.add(_index, event);

        ArrayList _list2 = (ArrayList)m_tableModel.getWrappedData();
        _list2.remove(event);                
    }

    public DnDAction handleDropOnTable(DropEvent event)
    {
        Integer _dropSite = (Integer)event.getDropSite();
        Transferable _transferable = event.getTransferable();
        RowKeySet _rowKeySet = _transferable.getData(DataFlavor.ROW_KEY_SET_FLAVOR);
        Object _rowKey = _rowKeySet.iterator().next();
        
        EmpEvent _event = (EmpEvent)m_timelineModel.getRowData(_rowKey);
        ArrayList _list = (ArrayList)m_tableModel.getWrappedData();
        _list.add(_dropSite.intValue(), _event);

        ArrayList _list2 = (ArrayList)m_timelineModel.getWrappedData();
        _list2.remove(_event);                
        
        RequestContext.getCurrentInstance().addPartialTarget(event.getDragComponent());
        return DnDAction.COPY;
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

    static DateFormat s_format = new SimpleDateFormat("MM.dd.yyyy");
}