/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/timeline/TimelineBean.java /bibeans_root/5 2014/08/15 11:59:10 jayturne Exp $ */

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
    ccchow      07/08/11 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/timeline/TimelineBean.java /bibeans_root/5 2014/08/15 11:59:10 jayturne Exp $
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
import java.util.HashMap;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import javax.faces.component.UIComponent;
import javax.faces.event.ValueChangeEvent;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

@ManagedBean(name="timeline")
@RequestScoped
public class TimelineBean
{
    private HashMap<String, CollectionModel> m_models;

    private String m_firstEmp;
    private String m_secondEmp;

    private List<Employee> m_employees;

    private CollectionModel m_sampleModel;
    private CollectionModel m_sampleDurationModel;
    
    public TimelineBean()
    {
        buildModels();
    }

    public List getEmployees()
    {
        return m_employees;
    }
    
    public String getFirstEmp()
    {
        return m_firstEmp;
    }

    public void setFirstEmp(String emp)
    {
        m_firstEmp = emp;
    }

    public String getSecondEmp()
    {
        return m_secondEmp;
    }

    public void setSecondEmp(String emp)
    {
        m_secondEmp = emp;
    }

    public CollectionModel getModel()
    {
        if (m_sampleModel != null)
            return m_sampleModel;

        ArrayList _list = new ArrayList(10);

        _list.add(new EmpEvent("0", parseDate("01.13.2010"), "Oracle Application Express", "se198AyXcsk", null));
        _list.add(new EmpEvent("1", parseDate("01.27.2010"), "Larry Ellison on the Sun-Oracle Close", "ylNgcD2Ay6M", null));
        _list.add(new EmpEvent("2", parseDate("01.29.2010"), "Oracle Exadata: Consolidating Database Applications", "cAYw2zcSIPw", null));
        _list.add(new EmpEvent("3", parseDate("02.04.2010"), "HRMall and PeopleSoft: Human Capital Delivered to the Boardroom ", "akzfduL0MZ0", null));
        _list.add(new EmpEvent("4", parseDate("02.05.2010"), "Code Ninja Chronicles Episode 404", "qeAk0TQCMZ4", null));
        _list.add(new EmpEvent("5", parseDate("02.16.2010"), "Code Ninja Chronicles Episode 127.0.0.1", "oAM0fDsDFSQ", null));

        // group
        _list.add(new EmpEvent("6", parseDate("03.20.2010"), "Oracle Open World 2010", "tWB0fR-buJ4", "g1"));
        _list.add(new EmpEvent("7", parseDate("03.20.2010"), "Larry Ellison, CEO, Oracle: OpenWorld", "tWB0fR-buJ4", "g1"));
        _list.add(new EmpEvent("8", parseDate("03.20.2010"), "Safra Catz, President, Oracle: OpenWorld", "oAPsLPA83M0", "g1"));
        _list.add(new EmpEvent("9", parseDate("03.20.2010"), "Mark Hurd, President, Oracle: OpenWorld", "kSJvK7f3pQ8", "g1"));
        _list.add(new EmpEvent("10", parseDate("03.20.2010"), "Judy Sim, CMO, Oracle: OpenWorld", "KEmlU08z6i8", "g1"));
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

        m_sampleModel = ModelUtils.toCollectionModel(_list);

        return m_sampleModel;
    }
    
    public CollectionModel getDurationModel()
    {
        if (m_sampleDurationModel != null)
            return m_sampleDurationModel;

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
        
        m_sampleDurationModel = ModelUtils.toCollectionModel(_list);

        return m_sampleDurationModel;
    }
    
    public CollectionModel getFirstModel()
    {
        return m_models.get(m_firstEmp);
    }

    public CollectionModel getSecondModel()
    {
        return m_models.get(m_secondEmp);
    }

    private void buildModels()
    {
        if (m_employees == null)
            m_employees = new ArrayList<Employee>(10);
        else
            m_employees.clear();
        
        m_employees.add(new Employee("1", "Peter J. Smith"));
        m_employees.add(new Employee("2", "Darryl G. Brown"));
        m_employees.add(new Employee("3", "Gilbert H. Lawson"));
        m_employees.add(new Employee("4", "Lisa A. Cully"));
        m_employees.add(new Employee("5", "Jason P. Mcmillan"));
        m_employees.add(new Employee("6", "Gerard K. Morris"));
        m_employees.add(new Employee("7", "Ellen J. Rodrigues"));
        m_employees.add(new Employee("8", "Cindy M. Bohn"));
        m_employees.add(new Employee("9", "Rebecca J. Mohr"));
        m_employees.add(new Employee("10", "Timothy E. Munson"));
        
        m_firstEmp = m_employees.get(0).getId();
        m_secondEmp = m_employees.get(1).getId();

        if (m_models == null)
            m_models = new HashMap<String, CollectionModel>(10);
        else
            m_models.clear();

        // Peter J. Smith
        ArrayList _list = new ArrayList(19);
        _list.add(new EmpEvent("0", parseDate("02.01.2000"), "Hire", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("1", parseDate("02.05.2000"), "Direct Deposit Form", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("2", parseDate("02.05.2000"), "Stock Option Grant", EmpEvent.STOCK, null));
        _list.add(new EmpEvent("3", parseDate("04.02.2000"), "Select Benefits Package", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("4", parseDate("02.11.2002"), "Promotion", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("5", parseDate("02.20.2002"), "Promotion", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("6", parseDate("10.01.2004"), "Retroactive Title Change", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("7", parseDate("10.01.2004"), "Benefactor Change", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("8", parseDate("06.05.2005"), "Change of Marital Status", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("9", parseDate("08.03.2006"), "Change of Address", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("10", parseDate("05.02.2007"), "Base Salary Increase", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("11", parseDate("11.10.2008"), "Future Title Change", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("12", parseDate("12.15.2008"), "Promotion", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("13", parseDate("10.14.2009"), "Stock Option Grant", EmpEvent.STOCK, null));
        _list.add(new EmpEvent("14", parseDate("10.14.2009"), "Marital Status Change", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("15", parseDate("12.01.2009"), "Benefactor Change", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("16", parseDate("04.14.2010"), "Base Salary Increase", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("17", parseDate("04.14.2010"), "Executive Management Course", EmpEvent.TRAINING, null));
        _list.add(new EmpEvent("18", parseDate("03.15.2011"), "Promotion", EmpEvent.EMPLOYMENT, null));
        m_models.put("1", ModelUtils.toCollectionModel(_list));

        // Darryl G. Brown
        _list = new ArrayList(18);
        _list.add(new EmpEvent("0", parseDate("03.03.2000"), "Hire", EmpEvent.EMPLOYMENT, null));   
        // event removed (02.01.200)
        _list.add(new EmpEvent("1", parseDate("09.28.2000"), "Internal Product Training", EmpEvent.TRAINING, null));
        _list.add(new EmpEvent("2", parseDate("12.25.2000"), "Full Year Performance Review", EmpEvent.EMPLOYMENT, null)); // corrected
        _list.add(new EmpEvent("3", parseDate("01.28.2001"), "Internal Product Training", EmpEvent.TRAINING, null));
        _list.add(new EmpEvent("4", parseDate("05.17.2001"), "Mid Year Performance Review", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("5", parseDate("09.28.2001"), "Database Course", EmpEvent.TRAINING, null)); // corrected
        _list.add(new EmpEvent("6", parseDate("12.15.2001"), "Full Year Performance Review", EmpEvent.EMPLOYMENT, null)); // corrected
        _list.add(new EmpEvent("7", parseDate("01.28.2002"), "Internal Product Training", EmpEvent.TRAINING, null));
        _list.add(new EmpEvent("8", parseDate("05.17.2002"), "Mid Year Performance Review", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("9", parseDate("11.01.2005"), "Promotion", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("10", parseDate("04.17.2003"), "Change of Location", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("11", parseDate("08.05.2005"), "Change of Marital Status", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("12", parseDate("10.01.2005"), "Benefactor Change", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("13", parseDate("07.13.2007"), "Enroll in Commuter Benefits Program", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("14", parseDate("04.14.2008"), "Base Salary Increase", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("15", parseDate("05.13.2009"), "Promotion", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("16", parseDate("04.14.2010"), "Base Salary Increase", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("17", parseDate("03.27.2011"), "Change of Address", EmpEvent.PERSON, null));
        m_models.put("2", ModelUtils.toCollectionModel(_list));

        // Gilbert H. Lawson
        _list = new ArrayList(19);
        _list.add(new EmpEvent("0", parseDate("03.03.2000"), "Hire", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("1", parseDate("03.07.2000"), "Roll-Over 401K", EmpEvent.COMPENSATION, null));   // corrected
        _list.add(new EmpEvent("2", parseDate("06.02.2000"), "Direct Deposit Form", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("3", parseDate("10.28.2000"), "Stock Option Grant", EmpEvent.STOCK, null));
        _list.add(new EmpEvent("4", parseDate("08.03.2001"), "Change of Address", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("5", parseDate("06.04.2002"), "Base Salary Increase", EmpEvent.COMPENSATION, "g1"));
        _list.add(new EmpEvent("6", parseDate("06.04.2002"), "Marital Status Change", EmpEvent.PERSON, "g1"));
        _list.add(new EmpEvent("7", parseDate("12.20.2002"), "Future Title Change", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("8", parseDate("02.11.2003"), "Promotion", EmpEvent.EMPLOYMENT, null)); // corrected
        _list.add(new EmpEvent("9", parseDate("09.09.2002"), "Change of Manager", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("10", parseDate("11.14.2003"), "Change of Location", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("11", parseDate("04.14.2004"), "Base Salary Increase", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("12", parseDate("10.11.2004"), "Stock Option Grant", EmpEvent.STOCK, null));
        _list.add(new EmpEvent("13", parseDate("11.09.2005"), "Benefactor Change", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("14", parseDate("04.17.2006"), "Technology Conference", EmpEvent.TRAINING, null));
        _list.add(new EmpEvent("15", parseDate("06.02.2007"), "Change Benefits Package", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("16", parseDate("10.24.2008"), "Change of Address", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("17", parseDate("02.11.2010"), "Promotion", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("18", parseDate("04.24.2011"), "Base Salary Increase", EmpEvent.COMPENSATION, null));
        m_models.put("3", ModelUtils.toCollectionModel(_list));

        // Lisa A. Cully
        _list = new ArrayList(17);
        _list.add(new EmpEvent("0", parseDate("10.17.2000"), "Hire", EmpEvent.EMPLOYMENT, null));
        _list.add(new EmpEvent("1", parseDate("10.28.2000"), "Stock Option Grant", EmpEvent.STOCK, null));
        _list.add(new EmpEvent("2", parseDate("11.03.2000"), "Direct Deposit Form", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("3", parseDate("02.07.2001"), "Roll-Over 401K", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("4", parseDate("05.02.2002"), "Base Salary Increase", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("5", parseDate("02.19.2003"), "Change Marital Status", EmpEvent.PERSON, "g1"));
        _list.add(new EmpEvent("6", parseDate("02.19.2003"), "Change of Name", EmpEvent.PERSON, "g1"));
        _list.add(new EmpEvent("7", parseDate("04.01.2003"), "Change of Address", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("8", parseDate("02.11.2004"), "Promotion", EmpEvent.EMPLOYMENT, null));  // corrected
        _list.add(new EmpEvent("9", parseDate("06.05.2005"), "Maternity Leave", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("10", parseDate("12.02.2007"), "College Savings Program", EmpEvent.PERSON, null)); 
        _list.add(new EmpEvent("11", parseDate("08.09.2008"), "Benefactor Change", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("12", parseDate("08.09.2008"), "Grant Stock Options", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("13", parseDate("03.09.2009"), "Leave of Absence", EmpEvent.PERSON, null)); // corrected
        _list.add(new EmpEvent("14", parseDate("02.10.2010"), "Promotion", EmpEvent.PERSON, null)); // corrected
        _list.add(new EmpEvent("15", parseDate("06.02.2010"), "Select Benefits Package", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("16", parseDate("04.14.2011"), "Promotion", EmpEvent.PERSON, null));
        m_models.put("4", ModelUtils.toCollectionModel(_list));

        // Jason P. Mcmillan
        _list = new ArrayList(17);
        _list.add(new EmpEvent("0", parseDate("02.04.2001"), "Hire", EmpEvent.EMPLOYMENT, null)); // corrected
        _list.add(new EmpEvent("1", parseDate("02.07.2001"), "Roll-Over 401K", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("2", parseDate("06.18.2001"), "Direct Deposit Form", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("3", parseDate("10.28.2001"), "Stock Option Grant", EmpEvent.STOCK, null));
        _list.add(new EmpEvent("4", parseDate("05.01.2002"), "Base Salary Increase", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("5", parseDate("04.02.2003"), "Select Benefits Package", EmpEvent.COMPENSATION, "g1"));
        _list.add(new EmpEvent("6", parseDate("12.15.2003"), "General Management Training", EmpEvent.TRAINING, "g1"));
        _list.add(new EmpEvent("7", parseDate("01.28.2004"), "Internal Product Training", EmpEvent.TRAINING, null)); // corrected
        _list.add(new EmpEvent("8", parseDate("02.11.2004"), "Promotion", EmpEvent.EMPLOYMENT, null));  
        _list.add(new EmpEvent("9", parseDate("03.05.2006"), "Change of Address", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("10", parseDate("08.05.2007"), "Change of Marital Status", EmpEvent.PERSON, null)); 
        _list.add(new EmpEvent("11", parseDate("09.14.2007"), "Base Salary Increase", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("12", parseDate("11.01.2007"), "Benefactor Change", EmpEvent.PERSON, null));
        _list.add(new EmpEvent("13", parseDate("06.02.2008"), "Risk Assessment", EmpEvent.EMPLOYMENT, null)); // corrected
        _list.add(new EmpEvent("14", parseDate("04.17.2009"), "Technology Conference", EmpEvent.TRAINING, null)); // corrected
        _list.add(new EmpEvent("15", parseDate("06.06.2009"), "Bonus", EmpEvent.COMPENSATION, null));
        _list.add(new EmpEvent("16", parseDate("08.06.2009"), "Termination of Employment", EmpEvent.EMPLOYMENT, null));
        m_models.put("5", ModelUtils.toCollectionModel(_list));

        // Gerard K. Morris
        _list = new ArrayList(11);
        _list.add(new EmpEvent("0", parseDate("05.15.2003"), "Hire", EmpEvent.EMPLOYMENT, null)); 
        _list.add(new EmpEvent("1", parseDate("06.02.2003"), "Select Benefits Package", EmpEvent.EMPLOYMENT, null)); 
        _list.add(new EmpEvent("2", parseDate("07.07.2003"), "Roll-Over 401K", EmpEvent.COMPENSATION, null)); 
        _list.add(new EmpEvent("3", parseDate("05.21.2004"), "Stock Option Grant", EmpEvent.STOCK, null)); 
        _list.add(new EmpEvent("4", parseDate("08.05.2007"), "Change of Marital Status", EmpEvent.PERSON, null)); 
        _list.add(new EmpEvent("5", parseDate("11.01.2008"), "Promotion", EmpEvent.EMPLOYMENT, null)); 
        _list.add(new EmpEvent("6", parseDate("11.10.2008"), "Retroactive Title Change", EmpEvent.EMPLOYMENT, null)); 
        _list.add(new EmpEvent("7", parseDate("05.01.2009"), "Base Salary Increase", EmpEvent.COMPENSATION, null)); // corrected
        _list.add(new EmpEvent("8", parseDate("03.11.2010"), "Change of Manager", EmpEvent.EMPLOYMENT, null)); // corrected
        _list.add(new EmpEvent("9", parseDate("08.03.2010"), "Change of Location", EmpEvent.EMPLOYMENT, null)); // corrected
        _list.add(new EmpEvent("10", parseDate("05.17.2011"), "Mid Year Performance Review", EmpEvent.EMPLOYMENT, null)); // corrected
        m_models.put("6", ModelUtils.toCollectionModel(_list));

        // Ellen J. Rodrigues
        _list = new ArrayList(9);
        _list.add(new EmpEvent("0", parseDate("06.01.2005"), "Hire", EmpEvent.EMPLOYMENT, null)); 
        _list.add(new EmpEvent("1", parseDate("06.02.2005"), "Direct Deposit Form", EmpEvent.COMPENSATION, null)); 
        _list.add(new EmpEvent("2", parseDate("05.05.2006"), "Base Salary Increase", EmpEvent.COMPENSATION, null)); 
        _list.add(new EmpEvent("3", parseDate("08.14.2007"), "Change Marital Status", EmpEvent.PERSON, "g1")); 
        _list.add(new EmpEvent("3", parseDate("08.14.2007"), "Change of Name", EmpEvent.PERSON, "g1")); 
        _list.add(new EmpEvent("4", parseDate("06.05.2009"), "Maternity Leave", EmpEvent.PERSON, null)); 
        _list.add(new EmpEvent("5", parseDate("07.09.2009"), "Benefactor Change", EmpEvent.PERSON, "g2")); 
        _list.add(new EmpEvent("5", parseDate("07.09.2009"), "Change Benefits Package", EmpEvent.PERSON, "g2")); 
        _list.add(new EmpEvent("6", parseDate("12.02.2010"), "College Savings Program", EmpEvent.PERSON, null)); 
        _list.add(new EmpEvent("7", parseDate("04.13.2011"), "Technology Conference", EmpEvent.TRAINING, null)); 
        _list.add(new EmpEvent("8", parseDate("05.15.2011"), "General Management Training", EmpEvent.TRAINING, null)); 
        m_models.put("7", ModelUtils.toCollectionModel(_list));

        // Cindy M. Bohn
        _list = new ArrayList(9);
        _list.add(new EmpEvent("0", parseDate("09.21.2000"), "Hire", EmpEvent.EMPLOYMENT, null)); // corrected
        _list.add(new EmpEvent("1", parseDate("04.15.2001"), "Relocation Expense Form", EmpEvent.EMPLOYMENT, null)); 
        _list.add(new EmpEvent("1", parseDate("04.17.2001"), "College Savings Program", EmpEvent.EMPLOYMENT, null)); // added
        _list.add(new EmpEvent("1", parseDate("05.02.2001"), "Technology Conference", EmpEvent.TRAINING, null)); 
        _list.add(new EmpEvent("2", parseDate("05.10.2001"), "Enroll in Commuter Benefits Program", EmpEvent.EMPLOYMENT, null)); 
        _list.add(new EmpEvent("1", parseDate("05.15.2001"), "General Management Training", EmpEvent.TRAINING, null)); 
        _list.add(new EmpEvent("1", parseDate("05.20.2001"), "Direct Deposit Form", EmpEvent.EMPLOYMENT, null)); 
        _list.add(new EmpEvent("3", parseDate("03.09.2002"), "Leave of Absence", EmpEvent.EMPLOYMENT, null)); 
        _list.add(new EmpEvent("4", parseDate("06.15.2002"), "Risk Assessment", EmpEvent.EMPLOYMENT, null)); 
        _list.add(new EmpEvent("5", parseDate("08.09.2002"), "Benefactor Change", EmpEvent.COMPENSATION, "g1")); 
        _list.add(new EmpEvent("6", parseDate("08.09.2002"), "Change Benefits Package", EmpEvent.COMPENSATION, "g1")); 
        _list.add(new EmpEvent("7", parseDate("02.10.2003"), "Change 401K Option", EmpEvent.COMPENSATION, null)); 
        _list.add(new EmpEvent("8", parseDate("02.13.2004"), "Base Salary Increase", EmpEvent.COMPENSATION, null)); 
        m_models.put("8", ModelUtils.toCollectionModel(_list));

        // Rebecca J. Mohr
        _list = new ArrayList(3);
        _list.add(new EmpEvent("0", parseDate("02.11.2010"), "Hire", EmpEvent.EMPLOYMENT, null)); 
        _list.add(new EmpEvent("1", parseDate("03.01.2010"), "Direct Deposit Form", EmpEvent.EMPLOYMENT, null)); 
        _list.add(new EmpEvent("2", parseDate("02.15.2011"), "Change of Location", EmpEvent.EMPLOYMENT, null)); 
        m_models.put("9", ModelUtils.toCollectionModel(_list));

        // Timothy E. Munson
        _list = new ArrayList(5);
        _list.add(new EmpEvent("0", parseDate("12.01.2010"), "Hire", EmpEvent.EMPLOYMENT, null)); 
        _list.add(new EmpEvent("1", parseDate("01.05.2011"), "Direct Deposit Form", EmpEvent.EMPLOYMENT, "g1")); 
        _list.add(new EmpEvent("1", parseDate("01.05.2011"), "Stock Option Grant", EmpEvent.STOCK, "g1")); 
        _list.add(new EmpEvent("2", parseDate("01.05.2011"), "Direct Deposit Form", EmpEvent.COMPENSATION, "g1")); 
        _list.add(new EmpEvent("3", parseDate("01.05.2011"), "Select Benefits Package", EmpEvent.COMPENSATION, "g1")); 
        _list.add(new EmpEvent("4", parseDate("05.31.2011"), "Base Salary Increase", EmpEvent.COMPENSATION, null)); 
        m_models.put("10", ModelUtils.toCollectionModel(_list));
    }

    public void handleValueChange(ValueChangeEvent event)
    {
        UIComponent _timeline = event.getComponent().getParent().findComponent("tl1");
        RequestContext.getCurrentInstance().addPartialTarget(_timeline);
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
