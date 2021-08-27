/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/timeline/TimelineContextMenuBean.java /bibeans_root/1 2016/02/19 21:50:06 jayturne Exp $ */

/* Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jayturne    02/18/16 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/timeline/TimelineContextMenuBean.java /bibeans_root/1 2016/02/19 21:50:06 jayturne Exp $
 *  @author  jayturne
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.timeline;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.component.timeline.UITimeSeries;

import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;

import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;

@ManagedBean(name="timeline")
@RequestScoped
public class TimelineContextMenuBean
{
    private UITimeSeries _timeSeries;

    private String status;
    private String selectedItemMenu;
    private RichOutputFormatted outputFormatted;

    private CollectionModel m_sampleModel;
    
    public TimelineContextMenuBean()
    {
        
    }
    
    public void setTimeSeries(UITimeSeries timeSeries)
    {
        this._timeSeries = timeSeries;
    }

    public UITimeSeries getTimeSeries()
    {
        return this._timeSeries;
    }
    
    public void setSelectedItemMenu(String _selectedItemMenu)
    {
        //Get the current data from the model to access the JSON map
        EmpEvent rowData = (EmpEvent)_timeSeries.getRowData();
        if (rowData != null)
            this.selectedItemMenu = rowData.getDescription();
    }

    public String getSelectedItemMenu()
    {
        //return selected menu item
        return this.selectedItemMenu;
    }
    
    public String getStatus()
    {
        return this.status;
    }
    
    public void setOutputFormatted(RichOutputFormatted outputFormatted)
    {
        this.outputFormatted = outputFormatted;
    }

    public RichOutputFormatted getOutputFormatted()
    {
        return outputFormatted;
    }
    
    /**
     * Called when a commandMenuItem is clicked.  Updates the outputText with information about the menu item clicked.
     * @param actionEvent
     */
    public void menuItemListener(ActionEvent actionEvent)
    {
        UIComponent component = actionEvent.getComponent();
        if (component instanceof RichCommandMenuItem)
        {
            RichCommandMenuItem cmi = (RichCommandMenuItem) component;

            // Add the text of the item into the status message
            StringBuilder s = new StringBuilder();
            s.append("You clicked on <b> ").append(cmi.getText()).append(" </b>.  <br><br>");
            this.status = s.toString();

            // Update the status text component
            RequestContext.getCurrentInstance().addPartialTarget(this.outputFormatted);
        }
    }
    
    public CollectionModel getModel()
    {
        if (m_sampleModel != null)
            return m_sampleModel;

        ArrayList _list = new ArrayList(10);

        _list.add(new EmpEvent("0", parseDate("09.21.2013 09:00"), parseDate("09.21.2013 10:00"), "The State of the Dolphin", "Mk3qkQROb_k", null));
        _list.add(new EmpEvent("1", parseDate("09.21.2013 10:00"), parseDate("09.21.2013 10:30"), "MySQL at Facebook", "DrZXtkAoJKM", null));
        _list.add(new EmpEvent("2", parseDate("09.21.2013 10:30"), parseDate("09.21.2013 11:00"), "Current MySQL Usage Models and Future Developments", "ylNgcD2Ay6M", null));
        _list.add(new EmpEvent("3", parseDate("09.22.2013 12:00"), parseDate("09.22.2013 15:00"), "Java Strategy and Technical Keynotes", "1-SYw-fjVHc", null));
        _list.add(new EmpEvent("4", parseDate("09.22.2013 13:00"), parseDate("09.22.2013 15:15"), "Oracle PartnerNetwork Exchange @ OpenWorld Keynote", "MG0P68X9tDo", null));
        _list.add(new EmpEvent("5", parseDate("09.22.2013 17:00"), parseDate("09.22.2013 19:00"), "Welcome Keynote", "msip5B6jTTo", null));
        _list.add(new EmpEvent("6", parseDate("09.23.2013 08:00"), parseDate("09.23.2013 09:45"), "Transforming Businesses with Big Data and Analytics", "msip5B6jTTo", null));
        _list.add(new EmpEvent("7", parseDate("09.23.2013 10:15"), parseDate("09.23.2013 11:45"), "Empowering Modern Human Resources and Talent Management in the Cloud", "cAYw2zcSIPw", null));
        _list.add(new EmpEvent("8", parseDate("09.23.2013 10:15"), parseDate("09.23.2013 11:45"), "Vision for Enabling Relevant Customer Experience at Scale", "msip5B6jTTo", null));
        _list.add(new EmpEvent("9", parseDate("09.24.2013 08:00"), parseDate("09.24.2013 09:30"), "Mobile and Social: Capitalizing on a Massive Shift of User Behavior", "pEkwXgUOzxU", null));
        
        _list.add(new EmpEvent("10", parseDate("09.24.2013 08:00"), parseDate("09.24.2013 10:00"), "Hardware and Software, Engineered to Work Together", "qeAk0TQCMZ4", null));
        _list.add(new EmpEvent("11", parseDate("09.24.2013 13:30"), parseDate("09.24.2013 15:15"), "Cloud Keynote", "se198AyXcsk", null));
        _list.add(new EmpEvent("12", parseDate("09.25.2013 08:00"), parseDate("09.25.2013 09:30"), "From Efficiency to Resilience: The New HR Function in the Age of Disruption", "akzfduL0MZ0", null));
        _list.add(new EmpEvent("13", parseDate("09.25.2013 08:00"), parseDate("09.25.2013 09:45"), "Modern Customer Experience: Welcome to the Age of the Customer", "LfehTEyglrQ", null));
        _list.add(new EmpEvent("14", parseDate("09.26.2013 08:45"), parseDate("09.26.2013 10:30"), "Unlocking Innovation and the Value of Embedded Intelligence on Devices", "tWB0fR-buJ4", null));
        _list.add(new EmpEvent("15", parseDate("09.26.2013 09:00"), parseDate("09.26.2013 10:30"), "Java Community Keynote", "oAM0fDsDFSQ", null));
        
        m_sampleModel = ModelUtils.toCollectionModel(_list);

        return m_sampleModel;
    }
    
    private static Date parseDate(String date)
    {
        Date ret = null;
        try
        {
            if (date.indexOf(':') > -1)
                ret = t_format.parse(date);
            else
                ret = s_format.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return ret;
    }
    
    static DateFormat s_format = new SimpleDateFormat("MM.dd.yyyy");
    static DateFormat t_format = new SimpleDateFormat("MM.dd.yyyy HH:mm");
}
