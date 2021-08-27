/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;

import oracle.adfdemo.view.query.rich.DemoPageDef;
import oracle.adfdemo.view.query.rich.DemoQueryBean;

public class TableFilterBean
{
  public TableFilterBean()
  {
  }

  public List<TableFilterBean.EmpRow> getData()
  {
    return _data;
  }

  public QueryDescriptor getQueryDescriptor()
  {
    // delegate
    return _queryBean.getQueryDescriptor();
  }

  public QueryModel getQueryModel()
  {
    // delegate
    return _queryBean.getQueryModel();
  }

  public void processQuery(QueryEvent event)
  {
    // delegate
    _queryBean.processQuery(event);
  }

  public void processTableFilter(QueryEvent event)
  {
    // delegate
    _queryBean.processTableFilter(event);
  }

  public void setSqlString(String sqlString)
  {
    // delegate
    _queryBean.setSqlString(sqlString);
  }

  public String getSqlString()
  {
    // delegate
    return _queryBean.getSqlString();
  }

  public List getDeptNumbers()
  {
    return _deptNums;
  }

  private class TableDemoQueryBean
    extends DemoQueryBean
  {
    @Override
    public void processQuery(QueryEvent event)
    {
      DemoQueryDescriptor descriptor =
        (DemoQueryDescriptor) event.getDescriptor();
      String sqlString = descriptor.getSavedSearchDef().toString();

      // QBE if any
      sqlString = _addFilterCriteria(descriptor, sqlString);

      setSqlString(sqlString);
    }

    public void processTableFilter(QueryEvent event)
    {
      FilterableQueryDescriptor descriptor =
        (FilterableQueryDescriptor) event.getDescriptor();
      String sqlString = _addFilterCriteria(descriptor, "");
      setSqlString(sqlString);
    }

    // QBE related methods

    private String _addFilterCriteria(FilterableQueryDescriptor descriptor,
                                      String sql)
    {
      // QBE if any
      Map<String, Object> filterCriteria = descriptor.getFilterCriteria();
      StringWriter sw = new StringWriter();
      sw.append(sql);
      if (filterCriteria != null && !filterCriteria.isEmpty())
      {
        sw.append("\nQBE: ");
        Set<String> keySet = filterCriteria.keySet();
        for (String key: keySet)
        {
          Object filterValue = filterCriteria.get(key);
          if (filterValue != null)
          {
            String strValue = filterValue.toString();
            List<DemoPageDef.SearchFieldDef> items =
              _parseViewCriteriaItems(key, strValue);
            if (items != null)
            {
              for (int i = 0; i < items.size(); i++)
              {
                DemoPageDef.SearchFieldDef vcItem = items.get(i);
                sw.append(' ').append(vcItem.getBeforeConjunction().toString()).append(' ');
                DemoPageDef.OperatorDef operator = vcItem.getOperator();
                String operStr =
                  (operator != null)? operator.getSymbol(): "";
                String sqlItem =
                  vcItem.getAttribute() + " " + operStr + " " +
                  vcItem.getDefaultValue() + " ";

                sw.append(sqlItem);
              }
            }
          }
        }
      }
      return sw.toString();
    }

    // QBE related methods

    private List<DemoPageDef.SearchFieldDef> _parseViewCriteriaItems(String key,
                                                                     String value)
    {
      List<DemoPageDef.SearchFieldDef> criteriaItems = null;
      value = value.trim();
      int charCount = value.length();
      if (charCount > 0)
      {
        criteriaItems = new ArrayList<DemoPageDef.SearchFieldDef>();
        Pattern pattern1 = Pattern.compile("(?i)\\band\\b"), pattern2;
        Matcher m = pattern1.matcher(value);
        boolean andMatches = m.find(), orMatches = false;

        if (!andMatches)
        {
          pattern2 = Pattern.compile("(?i)\\bor\\b");
          m = pattern2.matcher(value);
          orMatches = m.find();
        }
        if (andMatches || orMatches)
        {
          String[] tokenValues = value.split(" ");
          List<String> bufferedValues = new ArrayList<String>();
          ConjunctionCriterion.Conjunction lastConjunction =
            ConjunctionCriterion.Conjunction.AND;
          for (int i = 0; i < tokenValues.length; i++)
          {
            String tokenValue = tokenValues[i];
            if (tokenValue.equalsIgnoreCase("AND"))
            {
              DemoPageDef.SearchFieldDef vcItem =
                _parseViewCriteriaItem(key,
                                       _joinStrings(' ', bufferedValues),
                                       lastConjunction);
              if (vcItem != null)
                criteriaItems.add(vcItem);
              lastConjunction = ConjunctionCriterion.Conjunction.AND;
              bufferedValues.clear();
            }
            else if (tokenValue.equalsIgnoreCase("OR"))
            {
              DemoPageDef.SearchFieldDef vcItem =
                _parseViewCriteriaItem(key,
                                       _joinStrings(' ', bufferedValues),
                                       lastConjunction);
              if (vcItem != null)
                criteriaItems.add(vcItem);
              lastConjunction = ConjunctionCriterion.Conjunction.OR;
              bufferedValues.clear();
            }
            else
            {
              bufferedValues.add(tokenValue);
            }
          }
          if (bufferedValues.size() > 0)
          {
            DemoPageDef.SearchFieldDef vcItem =
              _parseViewCriteriaItem(key, _joinStrings(' ',
                                                       bufferedValues),
                                     lastConjunction);
            if (vcItem != null)
              criteriaItems.add(vcItem);
          }
        }
        else
        {
          DemoPageDef.SearchFieldDef vcItem =
            _parseViewCriteriaItem(key, value,
                                   ConjunctionCriterion.Conjunction.AND);
          if (vcItem != null)
            criteriaItems.add(vcItem);

        }
      }
      return criteriaItems;
    }

    // QBE related methods

    private String _joinStrings(char token, List<String> bufferedValues)
    {
      StringWriter sb = new StringWriter();
      int i = 0, count = bufferedValues.size();
      for (String string: bufferedValues)
      {
        sb.append(string);
        if (i < count - 1)
          sb.append(token);
        i++;
      }

      return (sb.toString());
    }

    // QBE related methods

    private DemoPageDef.SearchFieldDef _parseViewCriteriaItem(String key,
                                                              String value,
                                                              ConjunctionCriterion.Conjunction conjunction)
    {
      value = value.trim();
      int charCount = value.length();
      DemoPageDef.OperatorDef operator = null;
      if (charCount > 0)
      {
        char firstChar = value.charAt(0);
        if (firstChar == '>')
        {
          char secondChar = charCount > 1? value.charAt(1): '\0';
          if (secondChar == '=')
          {
            if (charCount > 2)
            {
              operator = DemoPageDef.OperatorDef.GREATER_THAN_EQUALS;
              value = value.substring(2);
            }
          }
          else if (charCount > 1)
          {
            operator = DemoPageDef.OperatorDef.GREATER_THAN;
            value = value.substring(1);
          }
        }
        else if (firstChar == '<')
        {
          char secondChar = charCount > 1? value.charAt(1): '\0';
          if (secondChar == '=')
          {
            if (charCount > 2)
            {
              operator = DemoPageDef.OperatorDef.LESS_THAN_EQUALS;
              value = value.substring(2);
            }
          }
          else if (charCount > 1)
          {
            operator = DemoPageDef.OperatorDef.LESS_THAN;
            value = value.substring(1);
          }
        }
        else if (firstChar == '*')
        {
          if (charCount > 1)
          {
            operator = DemoPageDef.OperatorDef.ENDS_WITH;
            value = value.substring(1);
          }
        }
        else if (firstChar == '=')
        {
          operator = DemoPageDef.OperatorDef.EQUALS;
          if (charCount > 1)
          {
            value = value.substring(1);
          }
        }
        else
        {
          char lastChar = value.charAt(charCount - 1);
          if (lastChar == '*')
          {
            if (charCount > 1)
            {
              // TODO: Cannot assume operator to be STARTS_WITH as it's only supported on String
              // type attributes and if user enter * on a Number attribute, a NPE is thrown in
              // getSqlString() as the operator never gets stored
              operator = DemoPageDef.OperatorDef.STARTS_WITH;
              value = value.substring(0, charCount - 1);
            }
          }
          else
          {
            operator = DemoPageDef.OperatorDef.EQUALS;
          }
        }
      }

      if (operator != null)
      {
        DemoPageDef.SearchFieldDef searchField =
          getPageDef().new SearchFieldDef(key, conjunction, value,
                                          operator, false, false);
        searchField.setOperator(operator);

        return searchField;
      }
      else
      {
        return null;
      }
    }

  }

  public static class EmpRow
  {

    public EmpRow(String empno, String ename, String manager,
                  String hireDate, String salary, String deptno)
    {
      this._ename = ename;
      this._empno = empno;
      this._deptno = deptno;
      this._manager = manager;
      this._hireDate = hireDate;
      this._salary = salary;
    }

    public String getEname()
    {
      return _ename;
    }

    public String getEmpno()
    {
      return _empno;
    }

    public String getDeptno()
    {
      return _deptno;
    }

    public String getManager()
    {
      return _manager;
    }

    public String getHireDate()
    {
      return _hireDate;
    }

    public String getSalary()
    {
      return _salary;
    }

    private String _ename;
    private String _empno;
    private String _deptno;
    private String _manager;
    private String _hireDate;
    private String _salary;
  }

  private final List<TableFilterBean.EmpRow> _data =
    Arrays.asList(new TableFilterBean.EmpRow[]
      { new EmpRow("7369", "SMITH     ", "7902", "12/17/1980", "800",
                   "20"),
        new EmpRow("7499", "ALLEN     ", "7698", "2/20/1981", "1600",
                   "30"),
        new EmpRow("7521", "WARD      ", "7698", "2/22/1981", "1250",
                   "30"),
        new EmpRow("7566", "JONES     ", "7839", "4/2/1981", "2975", "20"),
        new EmpRow("7654", "MARTIN    ", "7698", "9/28/1981", "1250",
                   "30"),
        new EmpRow("7698", "BLAKE     ", "7839", "5/1/1981", "2850", "30"),
        new EmpRow("7782", "CLARK     ", "7839", "6/9/1981", "2450", "10"),
        new EmpRow("7788", "SCOTT     ", "7566", "12/9/1982", "3000",
                   "20"),
        new EmpRow("7839", "KING      ", "", "11/17/1981", "5000", "10"),
        new EmpRow("7844", "TURNER    ", "7698", "9/8/1981", "1500", "30"),
        new EmpRow("7876", "ADAMS     ", "7788", "1/12/1983", "1100",
                   "20"),
        new EmpRow("7900", "JAMES     ", "7698", "12/3/1981", "950", "30"),
        new EmpRow("7902", "FORD      ", "7566", "12/3/1981", "3000",
                   "20"),
        new EmpRow("7934", "MILLER    ", "7782", "1/23/1982", "1300",
                   "10"),
        new EmpRow("7469", "SMITHY    ", "7902", "12/17/1980", "900",
                   "20"),
        new EmpRow("7599", "FALLEN    ", "7698", "2/20/1981", "1700",
                   "30"),
        new EmpRow("7621", "WARDY     ", "7698", "2/22/1981", "1350",
                   "30"),
        new EmpRow("7766", "BONES     ", "7839", "4/2/1981", "2775", "20"),
        new EmpRow("7754", "JUSTIN    ", "7698", "9/28/1981", "1350",
                   "30"),
        new EmpRow("7798", "DRAKE     ", "7839", "5/1/1981", "2650", "30"),
        new EmpRow("7882", "MARK      ", "7839", "6/9/1981", "2350", "10"),
        new EmpRow("7888", "SCOTTY    ", "7566", "12/9/1982", "3100",
                   "20") });

  private final TableDemoQueryBean _queryBean = new TableDemoQueryBean();

  private final List<SelectItem> _deptNums =
    Arrays.asList(new SelectItem("10"), new SelectItem("20"),
                  new SelectItem("30"));

}
