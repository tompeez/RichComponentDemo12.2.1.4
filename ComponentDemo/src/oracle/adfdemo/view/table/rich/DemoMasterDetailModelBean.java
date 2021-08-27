package oracle.adfdemo.view.table.rich;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeyPropertyModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class DemoMasterDetailModelBean
{
  public DemoMasterDetailModelBean()
  {
    _selectedMasterRows = new RowKeySetImpl();
  }
  
  public CollectionModel getMasterModel()
  {
    if (_deptModel == null)
    {
      List<EmpBean> emp1 = new ArrayList<EmpBean>();
      List<EmpBean> emp2 = new ArrayList<EmpBean>();
      List<EmpBean> emp3 = new ArrayList<EmpBean>();
      List<EmpBean> emp4 = new ArrayList<EmpBean>();
      List<EmpBean> emp5 = new ArrayList<EmpBean>();
      List<DeptBean> depts = new ArrayList<DeptBean>();


      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

      try
      {
        emp1.add(new EmpBean("E101", "Adam", "Engineer", "E701", sdf.parse("1998-01-19"), 
                             Integer.valueOf(23232), Integer.valueOf(6676), Integer.toString(10), 
                             "6505062101", "adam.engineer@oracle.com"));
        emp1.add(new EmpBean("E102", "Bob", "Engineer", "E701", sdf.parse("1998-06-19"), 
                             Integer.valueOf(23432), Integer.valueOf(5454), Integer.toString(10), 
                             "6505062102", "bob.engineer@oracle.com"));
        emp1.add(new EmpBean("E103", "Carl", "Engineer", "E701", sdf.parse("1998-01-12"), 
                             Integer.valueOf(54345), Integer.valueOf(54544), Integer.toString(10), 
                             "6505062103", "carl.engineer@oracle.com"));
        emp1.add(new EmpBean("E104", "Eilane", "Engineer", "E701", sdf.parse("2001-06-24"), 
                             Integer.valueOf(9879), Integer.valueOf(4534), Integer.toString(10), 
                             "6505062104", "eilane.engineer@oracle.com"));
        emp1.add(new EmpBean("E105", "Good", "Engineer", "E701", sdf.parse("2002-01-08"), 
                             Integer.valueOf(65465), Integer.valueOf(54565), Integer.toString(10), 
                             "6505062105", "good.engineer@oracle.com"));
        emp1.add(new EmpBean("E106", "James", "Engineer", "E701", sdf.parse("2002-01-44"), 
                             Integer.valueOf(43453), Integer.valueOf(5454), Integer.toString(10), 
                             "6505062106", "james.engineer@oracle.com"));
        emp1.add(new EmpBean("E501", "Karl", "DBA", "E701", sdf.parse("1998-08-29"), 
                             Integer.valueOf(4343), Integer.valueOf(98054), Integer.toString(10), 
                             "6505062501", "karl.dba@oracle.com"));
        emp1.add(new EmpBean("E803", "Norman", "Product Manager", "E701", sdf.parse("1990-01-14"), 
                             Integer.valueOf(4343), Integer.valueOf(5454), Integer.toString(10), 
                             "6505062803", "norman.prodmanager@oracle.com"));
        emp1.add(new EmpBean("E701", "Claire", "Manager - R&D", "E702", sdf.parse("2001-06-19"), 
                              Integer.valueOf(43234), Integer.valueOf(5555), Integer.toString(10), 
                              "6505062701", "claire.manager@oracle.com"));


        emp2.add(new EmpBean("E201", "Abdul", "Analyst", "E703", sdf.parse("2001-01-19"), 
                             Integer.valueOf(25232), Integer.valueOf(444), Integer.toString(20), 
                             "6505062201", "abdul.analyst@oracle.com"));
        emp2.add(new EmpBean("E202", "Bejond", "Analyst", "E703", sdf.parse("2001-01-14"), 
                             Integer.valueOf(5455), Integer.valueOf(23122), Integer.toString(20), 
                             "6505062202", "bejond.analyst@oracle.com"));
        emp2.add(new EmpBean("E203", "Calvin", "Analyst", "E703", sdf.parse("2002-04-19"), 
                             Integer.valueOf(54465), Integer.valueOf(39343), Integer.toString(20), 
                             "6505062203", "calvin.analyst@oracle.com"));
        emp2.add(new EmpBean("E204", "Derek", "Analyst", "E703", sdf.parse("1998-04-19"), 
                            Integer.valueOf(4323), Integer.valueOf(21231), Integer.toString(20), 
                             "6505062204", "derek.analyst@oracle.com"));
        emp2.add(new EmpBean("E205", "Frank", "Analyst", "E703", sdf.parse("1998-06-25"), 
                            Integer.valueOf(65454), Integer.valueOf(43543), Integer.toString(20), 
                            "6505062205", "frank.analyst@oracle.com"));
        emp2.add(new EmpBean("E206", "Gary", "Analyst", "E703", sdf.parse("2002-01-21"), 
                             Integer.valueOf(23232), Integer.valueOf(7676), Integer.toString(20), 
                             "6505062206", "gary.analyst@oracle.com"));
        emp2.add(new EmpBean("E207", "Goodon", "Analyst", "E703", sdf.parse("2001-04-07"), 
                             Integer.valueOf(23232), Integer.valueOf(4343), Integer.toString(20), 
                             "6505062207", "gordon.analyst@oracle.com"));
        emp2.add(new EmpBean("E801", "Ivory", "Product Manager", "E703", sdf.parse("1999-12-19"), 
                             Integer.valueOf(43653), Integer.valueOf(5454), Integer.toString(20), 
                             "6505062801", "ivory.prodmanager@oracle.com"));
        emp2.add(new EmpBean("E802", "Jordon", "Product Manager", "E703", sdf.parse("2000-09-22"), 
                             Integer.valueOf(49843), Integer.valueOf(5454), Integer.toString(20), 
                             "6505062802", "jordon.prodmanager@oracle.com"));
        emp2.add(new EmpBean("E703", "David", "Manager - TechSupport", "E702", sdf.parse("2001-01-22"), 
                             Integer.valueOf(78687), Integer.valueOf(2222), Integer.toString(20), 
                             "6505062703", "david.manager@oracle.com"));

        
        emp3.add(new EmpBean("E301", "Chris", "Technician", "E704", sdf.parse("2002-01-23"), 
                             Integer.valueOf(3212), Integer.valueOf(6565), Integer.toString(30), 
                             "6505062301", "chris.technician@oracle.com"));
        emp3.add(new EmpBean("E302", "Eric", "Technician", "E704", sdf.parse("2002-01-15"), 
                             Integer.valueOf(45354), Integer.valueOf(2112), Integer.toString(30), 
                             "6505062302", "eric.technician@oracle.com"));
        emp3.add(new EmpBean("E303", "Fonda", "Technician", "E704", sdf.parse("1998-01-26"), 
                             Integer.valueOf(43543), Integer.valueOf(564654), Integer.toString(30), 
                             "6505062303", "fonda.technician@oracle.com"));
        emp3.add(new EmpBean("E304", "T.J", "Technician", "E704", sdf.parse("2002-01-05"), 
                             Integer.valueOf(45345), Integer.valueOf(56565), Integer.toString(30), 
                             "6505062304", "t.j.technician@oracle.com"));
        emp3.add(new EmpBean("E704", "Ford", "Manager - Operations", "E702", sdf.parse("2001-04-09"), 
                             Integer.valueOf(56465), Integer.valueOf(76658), Integer.toString(30), 
                             "6505062704", "ford.manager@oracle.com"));

        emp4.add(new EmpBean("E401", "J.R.", "Consultant", "E705", sdf.parse("2001-10-21"), 
                             Integer.valueOf(47343), Integer.valueOf(5454), Integer.toString(40), 
                             "6505062401", "j.r.consultant@oracle.com"));
        emp4.add(new EmpBean("E402", "Mandona", "Consultant", "E705", sdf.parse("1991-02-13"), 
                             Integer.valueOf(6643), Integer.valueOf(5454), Integer.toString(40), 
                             "6505062402", "madonna.consultant@oracle.com"));
        emp4.add(new EmpBean("E403", "Kerry", "Consultant", "E705", sdf.parse("1996-07-23"), 
                             Integer.valueOf(6553), Integer.valueOf(5454), Integer.toString(40), 
                             "6505062403", "kerry.consultant@oracle.com"));
        emp4.add(new EmpBean("E404", "Larry", "Consultant", "E705", sdf.parse("1995-06-25"), 
                             Integer.valueOf(47893), Integer.valueOf(5454),Integer.toString(40), 
                             "6505062404", "larry.consultant@oracle.com"));
        emp4.add(new EmpBean("E405", "Lelsie", "Consultant", "E705", sdf.parse("1994-05-26"), 
                             Integer.valueOf(6743), Integer.valueOf(5454), Integer.toString(40), 
                             "6505062405", "leslie.consultant@oracle.com"));
        emp4.add(new EmpBean("E502", "Linda", "DBA", "E705", sdf.parse("1993-04-11"), 
                             Integer.valueOf(4343), Integer.valueOf(9854), Integer.toString(40), 
                             "6505062502", "linda.dba@oracle.com"));
        emp4.add(new EmpBean("E503", "Merk", "DBA", "E705", sdf.parse("1992-03-12"), 
                             Integer.valueOf(4343), Integer.valueOf(5476), Integer.toString(40), 
                             "6505062503", "merk.dba@oracle.com"));
        emp4.add(new EmpBean("E504", "King", "DBA", "E705", sdf.parse("1998-06-24"), 
                             Integer.valueOf(4343), Integer.valueOf(7854), Integer.toString(40), 
                             "6505062504", "king.dba@oracle.com"));
        emp4.add(new EmpBean("E505", "Howard", "DBA", "E705", sdf.parse("2002-01-17"), 
                             Integer.valueOf(4444), Integer.valueOf(21113), Integer.toString(40), 
                             "6505062505", "howard.dba@oracle.com"));
        emp4.add(new EmpBean("E506", "Jidd", "DBA", "E705", sdf.parse("1998-11-20"), 
                             Integer.valueOf(4343), Integer.valueOf(54564), Integer.toString(40), 
                             "6505062506", "jidd.dba@oracle.com"));
        emp4.add(new EmpBean("E602", "Henry", "Assistant", "E705", sdf.parse("2001-01-09"), 
                             Integer.valueOf(7665), Integer.valueOf(7657), Integer.toString(40), 
                             "6505062602", "henry.assistant@oracle.com"));
        emp4.add(new EmpBean("E705", "Avance", "Manager - Infrastructure", "E702", sdf.parse("2002-04-15"), 
                             Integer.valueOf(24232), Integer.valueOf(32211), Integer.toString(40), 
                             "6505062705", "avance.manager@oracle.com" ));
        
        emp5.add(new EmpBean("E702", "Brenta", "COO - HQ", "", sdf.parse("1998-04-19"), 
                             Integer.valueOf(6454), Integer.valueOf(39343), Integer.toString(50), 
                             "6505062702", "brenta.manager@oracle.com"));

        depts.add(new DeptBean("10", "Research and Development", "R&D", "E701", "Claire", "Manager - R&D", 
                              "6505062701", "claire.manager@oracle.com", 
                               new RowKeyPropertyModel(emp1, "empno")));
        depts.add(new DeptBean("20", "Technical Support", "TSU", "E703", "David", 
                               "Manager - TechSupport", "6505062703", "david.manager@oracle.com", 
                               new RowKeyPropertyModel(emp2, "empno")));
        depts.add(new DeptBean("30", "Operational Facilities", "FLT", "E704", "Ford", 
                               "Manager - Operations", "6505062704", "ford.manager@oracle.com", 
                               new RowKeyPropertyModel(emp3, "empno")));
        depts.add(new DeptBean("40", "IT Infrastructure", "ITI", "E705", "Avance", 
                               "Manager - Infrastructure", "6505062705", 
                               "avance.manager@oracle.com", 
                               new RowKeyPropertyModel(emp4, "empno")));
        depts.add(new DeptBean("50", "Headquarters Division", "HQD", "E702", "Brenta", "COO - HQ", 
                              "6505062702", "brenta.manager@oracle.com", 
                               new RowKeyPropertyModel(emp5, "empno")));
      }
      catch (ParseException e)
      {
        e.printStackTrace();
      }

      _deptModel = new RowKeyPropertyModel(depts, "deptno");

      // Set the rowkey to be the first row
      DeptBean rowData = (DeptBean) depts.get(0);
      Object rowKey = rowData.getDeptNo();
      _selectedMasterRows.add(rowKey);
    }
    
    
    
    return _deptModel;
  }
  
  public CollectionModel getDetailModel()
  {
    CollectionModel detailModel = null;
    
    if(_selectedMasterRows.getSize()>0 && _deptModel != null)
    {
      Object oldRowKey = _deptModel.getRowKey();
      Object selRowKey = _selectedMasterRows.iterator().next();
      try
      {
        _deptModel.setRowKey(selRowKey);
        DeptBean dept = (DeptBean)_deptModel.getRowData();
        detailModel = dept.getEmps();
      }
      finally
      {
        _deptModel.setRowKey(oldRowKey);
      }
    }
    
    return detailModel;
  }
  
  public DeptBean getDeptDetailForSelectedRow()
  {
    DeptBean dept = null;
    
    if(_selectedMasterRows.getSize()>0 && _deptModel != null)
    {
      Object oldRowKey = _deptModel.getRowKey();
      Object selRowKey = _selectedMasterRows.iterator().next();
      try
      {
        _deptModel.setRowKey(selRowKey);
        dept = (DeptBean)_deptModel.getRowData();
      }
      finally
      {
        _deptModel.setRowKey(oldRowKey);
      }
    }
    
    return dept;
  }  
  
  public String getSelectedRowKey()
  {
    Object selRowKey = "";
    if (_selectedMasterRows.getSize() > 0 && _deptModel != null)
    {
      selRowKey = _selectedMasterRows.iterator().next();
    }
    
    return (String) selRowKey;
  }
  
  public void selectionChange(SelectionEvent event)
  {
    // just so that the selection request goes to the server and the detail is ppred
    this._selectedMasterRows = event.getAddedSet();
  }

  public void setSelectedRows(RowKeySet selectedRows)
  {
    this._selectedMasterRows = selectedRows;
  }

  public RowKeySet getSelectedRows()
  {
    return _selectedMasterRows;
  }

  public static class DeptBean extends java.util.HashMap
  {

    public DeptBean()
    {
    }

    public DeptBean(
      String deptno, 
      String deptname, 
      String code, 
      String headno,
      String headname,
      String jobtitle,
      String phone,
      String email,
      CollectionModel emps)
    {
      put("deptno", deptno);
      put("deptname", deptname);
      put("code", code);
      put("empno", headno);
      put("empname", headname);
      put("jobtitle", jobtitle);
      put("phone", phone);
      put("email", email);
      put("emps", emps);
    }

    public String getDeptNo()
    {
      return get("deptno").toString();
    }

    public String getDeptName()
    {
      return (String) get("deptname");
    }

    public String getCode()
    {
      return (String) get("code");
    }

    public String getEmpNo()
    {
      return (String) get("empno");
    }
    public String getEmpName()
    {
      return (String) get("empname");
    }
    public String getPhone()
    {
      return (String) get("phone");
    }
    public String getEmail()
    {
      return (String) get("email");
    }
    public String getJobTitle()
    {
      return (String) get("jobtitle");
    }

    public CollectionModel getEmps()
    {
      return (CollectionModel) get("emps");
    }

  }
  
  public static class EmpBean extends java.util.HashMap
  {

    public EmpBean()
    {
    }

    public EmpBean(
      String empno, 
      String ename, 
      String job, 
      String mgr,
      Date hireDate, 
      Integer sal, 
      Integer comm, 
      String deptno,
      String phone,
      String email)
    {
      put("empno", empno);
      put("empname", ename);
      put("jobtitle", job);
      put("manager", mgr);
      put("hiredate", hireDate);
      put("salary", sal);
      put("commission", comm);
      put("deptno", deptno);
      put("phone", phone);
      put("email", email);
    }

    public String getEmpNo()
    {
      return get("empno").toString();
    }

    public String getEmpName()
    {
      return (String) get("ename");
    }

    public String getManager()
    {
      return (String) get("manager");
    }
    public Date getHireDate()
    {
      return (Date) get("hiredate");
    }
    public Integer getSalary()
    {
      return (Integer) get("salary");
    }

    public Integer getCommission()
    {
      return (Integer) get("commission");
    }
    
    public String getJobTitle()
    {
      return (String) get("jobtitle");
    }

    public String getPhone()
    {
      return (String) get("phone");
    }
    public String getEmail()
    {
      return (String) get("email");
    }

  }
  
  private CollectionModel _deptModel;
  private RowKeySet _selectedMasterRows;
}
