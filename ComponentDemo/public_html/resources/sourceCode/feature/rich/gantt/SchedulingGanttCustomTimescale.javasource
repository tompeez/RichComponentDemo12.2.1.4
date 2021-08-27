/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttCustomTimescale.java /main/2 2012/12/01 11:15:16 ccchow Exp $ */

/* Copyright (c) 2009, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
      hbroek    06/30/09 - Additional Shepherd samples
      hbroek    06/25/09 - Additional Shepherd samples
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/SchedulingGanttCustomTimescale.java /main/2 2012/12/01 11:15:16 ccchow Exp $
 *  @author  hbroek  
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.gantt.CustomTimeScale;
import oracle.adf.view.faces.bi.event.DataChangeEvent;

import oracle.adf.view.faces.bi.event.GanttActionEvent;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;

public class SchedulingGanttCustomTimescale extends SchedulingGanttBase
{    
    private TreeModel m_model;
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getSchedulingGanttModel();

        return m_model;
    }
        
    public Map<String, CustomTimeScale> getCustomTimescales() {
        Map<String, CustomTimeScale> customScales = new HashMap<String, CustomTimeScale>();

        // Specify the key values threeyears and fiveminutes in your timeAxis zoomorder attribute.
        customScales.put("threeyears", new ThreeYearTimescale());
        customScales.put("fiveminutes", new FiveMinuteTimescale());

        return customScales;
    }
    
    class ThreeYearTimescale implements CustomTimeScale
    {
        private DateFormat format = new SimpleDateFormat("yyyy");
        private Calendar calendar = Calendar.getInstance();

        public String getScaleName() 
        {
            return "Three Years";
        }

        public Date getNextDate(Date date) 
        {
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 3);
            return calendar.getTime();
        }
        
        public String getLabel(Date date)
        {
            return format.format(date);
        }
        
        public Date getPreviousDate(Date date) 
        {
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
            int roll_amt = (calendar.get(Calendar.MONTH) - Calendar.JANUARY + 12) % 36;
            if (roll_amt > 0)
                calendar.add(Calendar.MONTH, -roll_amt);
            return calendar.getTime();
        }
    }
    
    class FiveMinuteTimescale implements CustomTimeScale
    {
        private DateFormat format = new SimpleDateFormat("h:mm");
        private Calendar calendar = Calendar.getInstance();

        public String getScaleName() 
        {
            return "Five Minutes";
        }

        public Date getNextDate(Date date) 
        {
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, 5);
            return calendar.getTime();
        }
        
        public String getLabel(Date date)
        {
            return format.format(date);
        }
        
        public Date getPreviousDate(Date ganttStartDate) 
        {
            calendar.setTime(ganttStartDate);
            calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
            calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
            calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
            return calendar.getTime();
        }
    }
}