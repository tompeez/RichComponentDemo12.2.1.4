/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/TimeIterator.java /main/1 2009/08/05 14:11:50 srkalyan Exp $ */

/* Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    Generates dates with a regular interval from a start date. Here's how it works:

    1.  In EL, start out with "time."
    2.  Next, specify configuration options, similar to GraphSharedDataSource's
        keyword arguments. All arguments are optional, and can be specified in
        any order:
        
        --  start. Expects a String that represents a Date. The date must be in
            one of two formats:
            --  Similar to "January 1, 2009" (month day, year)
            --  an ISO 8601-formatted date like "2009-1-1T00:00:00"
                (year-month-day, the letter "T", then hours:mins:secs. hours is
                zero-based (00 instead of 24))
            The default start date is "March 29, 2009"
            Aliases: "from", "begin"
        --  a time granularity. The options are:
            everySecond, everyMinute, everyHour, everyDay, everyWeek, everyMonth, everyYear
            The more common aliases hourly, daily, weekly, monthly, and yearly
            may also be used.
            The time granularity may be specified alone ("time.hourly") or with
            an optional integer parameter to specifiy the number of timesteps
            to skip ("time.hourly[12]" skips 12 hours at a time). 
            The default time granularity is daily and the default skip parameter
            is 1. 

    EXAMPLES:

        time
            Generates daily dates, starting from March 29, 2009 (default behavior)

        time.start['July 31, 1980'].hourly
            Generates hourly dates, starting from July 31, 1980. 

        time.start['2009-2-15T13:44:12'].everyDay[3]
            Generates dates, starting at ISO date "2009-2-15T13:44:12", every
            three days. 


   MODIFIED    (MM/DD/YY)
    carjacks    07/23/09 - Creation
 */

package oracle.adfdemo.view.feature.rich.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class TimeIterator implements Iterator, Map{
    
    private Date m_start;
    private Date m_next;
    private int m_delta = 1;
    private int m_granularity = Calendar.DATE; // a Calendar.CONST value
    
    private String m_paramBuffer;
    
    public TimeIterator(){
        super();
        try{
            // Default start date
            m_start = DateFormat.getDateInstance().parse("March 29, 2009");
        }catch(ParseException e){
            m_start = new Date(); // Fall back to today's date if that didn't work.
        }
        m_next = new Date(m_start.getTime());
    }
    public TimeIterator(Date start, int delta, int granularity){
        super();
        m_start = start;
        m_next = new Date(m_start.getTime());
        m_delta = delta;
        m_granularity = granularity;
    }
    
    public TimeIterator get(Object key) {
        if(key instanceof String){
            String s = (String) key;
            
            if(s.equals("start") || s.equals("from") || s.equals("begin")){
                m_paramBuffer = "start";
            }else if(m_paramBuffer == "start"){
                m_paramBuffer = null;
                try{
                    // ISO 8601 date
                    return new TimeIterator(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(s), m_delta, m_granularity);
                }catch(ParseException e){
                    try{
                        // A string that looks something like "March 29, 2009"
                        return new TimeIterator(DateFormat.getDateInstance().parse(s), m_delta, m_granularity);
                    }catch(ParseException g){
                        // Fail silently
                    }
                }
                
            }else if(s.equals("today") || s.equals("now")){ // 
                return new TimeIterator(new Date(), m_delta, m_granularity);
                
            // Set the time granularity
            }else if(s.equals("everySecond")){
                return new TimeIterator(m_start, m_delta, Calendar.SECOND);
            }else if(s.equals("everyMinute")){
                return new TimeIterator(m_start, m_delta, Calendar.MINUTE);
            }else if(s.equals("everyHour") || s.equals("hourly")){
                return new TimeIterator(m_start, m_delta, Calendar.HOUR);
            }else if(s.equals("everyDay") || s.equals("daily")){
                return new TimeIterator(m_start, m_delta, Calendar.DATE);
            }else if(s.equals("everyWeek") || s.equals("weekly")){
                return new TimeIterator(m_start, m_delta, Calendar.WEEK_OF_YEAR);
            }else if(s.equals("everyMonth") || s.equals("monthly")){
                return new TimeIterator(m_start, m_delta, Calendar.MONTH);
            }else if(s.equals("everyYear") || s.equals("yearly")){
                return new TimeIterator(m_start, m_delta, Calendar.YEAR);
            }
            
        }else if(key instanceof Long){
            // Set the time delta
            return new TimeIterator(m_start, ((Long) key).intValue(), m_granularity);
        }
        
        // If nothing recognised the parameter, fail silently and return this
        // to let the user continue to method chain
        return this;
    }

    public Date next(){
        Date next = m_next;
        Calendar c = Calendar.getInstance();
        c.setTime(m_next);
        c.add(m_granularity, m_delta);
        m_next = c.getTime();
        return next;
    }
    
    // This is required for GraphSharedDataSource caching to work properly. Also useful for debugging.
    public String toString(){
        return "[" + m_start.toString() + "] + " + m_delta + " Calendar.CONST[" + m_granularity + "]";
    }
    
    // Dummy interface implementation methods
    public boolean hasNext(){return true;} // TimeIterator can churn out dates forever
    public void remove(){}
    public int size(){return 0;}
    public boolean isEmpty(){return false;}
    public boolean containsKey(Object key){return true;} // Always returns true to catch all EL expressions
    public boolean containsValue(Object value){return false;}
    public Object put(Object key, Object value){return null;}
    public Object remove(Object key){return null;}
    public void putAll(Map m){}
    public void clear(){}
    public Set keySet(){return Collections.emptySet();}
    public Collection values(){return Collections.emptySet();}
    public Set entrySet(){return Collections.emptySet();}
}
