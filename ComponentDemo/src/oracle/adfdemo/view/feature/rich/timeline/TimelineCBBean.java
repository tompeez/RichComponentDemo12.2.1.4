/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/timeline/TimelineCBBean.java /bibeans_root/2 2011/12/09 08:38:56 ccchow Exp $ */

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
 *  @version $Header: TimelineCBBean.java 20-jul-2011.16:23:56 ccchow   Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.timeline;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;

import java.util.Iterator;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;

import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHint;
import javax.faces.event.AjaxBehaviorEvent;

import oracle.adf.view.faces.bi.component.timeline.UITimelineSeries;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;

@ManagedBean(name="cb")
@RequestScoped
public class TimelineCBBean
{
    private CollectionModel m_model;

    public TimelineCBBean()
    {
        super();
    }
    
    public CollectionModel getModel()
    {
        if (m_model != null)
            return m_model;

        ArrayList _list = new ArrayList(10);

        _list.add(new EmpEvent("0", parseDate("01.13.2010"), "Oracle Application Express", "se198AyXcsk", null));
        _list.add(new EmpEvent("1", parseDate("01.27.2010"), "Larry Ellison on the Sun-Oracle Close", "ylNgcD2Ay6M", null));
        _list.add(new EmpEvent("2", parseDate("01.29.2010"), "Oracle Exadata: Consolidating Database Applications", "cAYw2zcSIPw", null));
        _list.add(new EmpEvent("3", parseDate("02.04.2010"), "HRMall and PeopleSoft: Human Capital Delivered to the Boardroom ", "akzfduL0MZ0", null));
        _list.add(new EmpEvent("4", parseDate("02.05.2010"), "Code Ninja Chronicles Episode 404", "qeAk0TQCMZ4", null));
        _list.add(new EmpEvent("5", parseDate("02.16.2010"), "Code Ninja Chronicles Episode 127.0.0.1", "oAM0fDsDFSQ", null));

        // group
        _list.add(new EmpEvent("7", parseDate("03.20.2010"), "Larry Ellison, CEO, Oracle: OpenWorld", "tWB0fR-buJ4", "g1"));
        _list.add(new EmpEvent("8", parseDate("03.20.2010"), "Safra Catz, President, Oracle: OpenWorld", "oAPsLPA83M0", "g1"));
        // end group        

        _list.add(new EmpEvent("11", parseDate("04.21.2010"), "Up Close: Interview with NZOUGs Lynne O'Donoghue", "1-SYw-fjVHc", null));
        _list.add(new EmpEvent("12", parseDate("06.15.2010"), "Oracle OpenWorld 2009 Keynote Highlights", "pEkwXgUOzxU", null));
        _list.add(new EmpEvent("13", parseDate("07.15.2010"), "Data Warehousing Best Practices Star Schemas", "LfehTEyglrQ", null));
        _list.add(new EmpEvent("14", parseDate("07.26.2010"), "The Golden Parachute", "DrZXtkAoJKM", null));
        _list.add(new EmpEvent("15", parseDate("08.10.2010"), "Stronger Together: Up Close Interview with Ecuador's Paola Pullas", "MG0P68X9tDo", null));
        _list.add(new EmpEvent("16", parseDate("08.13.2010"), "Lady Java", "Mk3qkQROb_k", null));
        _list.add(new EmpEvent("17", parseDate("08.27.2010"), "Eliminate Idle Redundancy in Your Data Center", "msip5B6jTTo", null));

        _list.add(new EmpEvent("18", parseDate("09.21.2010"), "Larry Ellison, CEO, Oracle: OpenWorld", "tWB0fR-buJ4", null));
        _list.add(new EmpEvent("19", parseDate("12.07.2010"), "Eliminate Idle Redundancy in Your Data Center", "msip5B6jTTo", null));
        _list.add(new EmpEvent("20", parseDate("02.26.2011"), "Second block event", "msip5B6jTTo", null));
        _list.add(new EmpEvent("21", parseDate("12.26.2011"), "Far far away event", "msip5B6jTTo", null));

        m_model = ModelUtils.toCollectionModel(_list);

        return m_model;
    }        

    public void handleKey(AjaxBehaviorEvent event)
    {
        ClientBehavior _behavior = (ClientBehavior)event.getBehavior();
        Set<ClientBehaviorHint> _hints = _behavior.getHints();
        
        UITimelineSeries _series = (UITimelineSeries)event.getComponent().findComponent("ts1");
        if (_series == null)
            return;

        RowKeySet _rowKeySet = _series.getSelectedRowKeys();
        Iterator _iterator = _rowKeySet.iterator();

        ArrayList _list = (ArrayList)m_model.getWrappedData();
        while (_iterator.hasNext())
        {
            Object _rowKey = _iterator.next();
            Object _event = m_model.getRowData(_rowKey);            

            _list.remove(_event);
        }
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