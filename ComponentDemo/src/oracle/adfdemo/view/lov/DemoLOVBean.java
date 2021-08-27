/** Copyright (c) 2008, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.lov;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.UIXInputPopup;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.LaunchPopupEvent;
import oracle.adf.view.rich.event.ReturnPopupEvent;
import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.AutoSuggestBehaviorConfig;
import oracle.adf.view.rich.model.AutoSuggestUIHints;
import oracle.adf.view.rich.model.ColumnDescriptor;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;
import oracle.adf.view.rich.model.ListOfValuesModel;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;
import oracle.adf.view.rich.model.TableModel;

import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.model.SortableModel;


public class DemoLOVBean
{
  public DemoLOVBean()
  {
    for (int i = 0; i < _DIR_DATA.length * 50; i++)
    {
      try
      {
        Object data[] = _DIR_DATA[i % _DIR_DATA.length];
        String item1 = (String) data[0] + new Integer(i).toString();
        Integer item2 = new Integer(data[2].toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date item3 = sdf.parse(data[3].toString());
        Integer item4 = new Integer(data[4].toString());
        Integer item5 = new Integer(data[5].toString());
        Integer item6 = new Integer(data[6].toString());
        Integer item7 = new Integer(i);
        FileData _curRow =
          new FileData(i, item1, (String) data[1], item2, item3, item4,
                       item5, item6, item7);
        _values.add(_curRow);
      }
      catch (ParseException e)
      {
        e.printStackTrace();
      }
    }
    _filteredList.addAll(_values);
  }

  //------------------public API for binding --------------------------------//

  UIXTable _table;

  public void setTable(UIXTable table)
  {
    _table = table;
  }

  public UIXTable getTable()
  {
    return _table;
  }

  //---Binding attributes for fields in the base page ----//
  private Integer _empno;
  public void setEmpno(Integer empno)
  {
    _empno = empno;
  }

  public Integer getEmpno()
  {
    return _empno;
  }

  private String _ename;
  private String _ename1;

  public void setEname(String ename)
  {
    _ename = ename;
  }

  public String getEname()
  {
    return _ename;
  }

  private Integer _deptno;
  public void setDeptno(Integer deptno)
  {
    _deptno = deptno;
  }

  public Integer getDeptno()
  {
    return _deptno;
  }

  private Date _hireDate;
  public void setHireDate(Date date)
  {
    _hireDate = date;
  }

  public Date getHireDate()
  {
    return _hireDate;
  }

  private Integer _mgr;
  public void setMgr(Integer mgr)
  {
    _mgr = mgr;
  }

  public Integer getMgr()
  {
    return _mgr;
  }

  private Integer _sal;
  public void setSal(Integer sal)
  {
    _sal = sal;
  }

  public Integer getSal()
  {
    return _sal;
  }

  private Integer _comm;
  public void setComm(Integer comm)
  {
    _comm = comm;
  }

  public Integer getComm()
  {
    return _comm;
  }

  Object _returnData;

  public void setReturnData(Object value)
  {
    _returnData = value;
  }

  public Object getReturnData()
  {
    return "return value: " + getLovValue();
  }

  Object _lovValue;

  public void setLovValue(Object value)
  {
    _lovValue = value;
  }

  public Object getLovValue()
  {
    return _lovValue;
  }

  private Object returnPopupDataValue;
  private Object returnPopupDataValue1;

  public void setReturnPopupDataValue(Object returnPopupDataValue)
  {
      this.returnPopupDataValue = returnPopupDataValue;
  }

  public Object getReturnPopupDataValue()
  {
      return returnPopupDataValue;
  }

  public void selected(SelectionEvent event)
  {
    setReturnPopupDataValue(event.getAddedSet());
  }

  public void returnPopupListener(ReturnPopupEvent returnPopupEvent)
  {
    Object value = returnPopupEvent.getReturnValue();
    if (value != null)
    {
      UIXInputPopup comp = (UIXInputPopup)returnPopupEvent.getComponent();
      if (comp != null)
      {
        comp.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(comp);
      }
      FileData rowData = _getRowData(value);
      if(rowData != null)
      {
        this.setValues(rowData);
      }
      else
        this.setEname(value.toString());
    }
  }

  public void launchPopupListener(LaunchPopupEvent event)
  {
    Object submittedValue = event.getSubmittedValue();
    _filterList((String)submittedValue, false);
    if (_filteredList.size() == 1)
    {
      FileData rowData = _filteredList.get(0);
      if(rowData != null)
      {
        this.setValues(rowData);
      }
      else
        this.setEname(submittedValue.toString());

      event.setLaunchPopup(false);
      UIXInputPopup comp = (UIXInputPopup)event.getComponent();
      if (comp != null)
      {
        comp.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(comp);
      }
    }
  }

  public void inputReturnPopupListener(ReturnPopupEvent returnPopupEvent)
  {
    Object value = returnPopupEvent.getReturnValue();
    if (value != null)
    {
      UIXInputPopup comp = (UIXInputPopup)returnPopupEvent.getComponent();
      if (comp != null)
      {
        comp.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(comp);
      }
      this.setEname1(value.toString());
    }
  }

  private void setValues(FileData rowData)
  {
    if ( rowData != null)
    {
      this.setEmpno(rowData.getEmpno());
      this.setEname(rowData.getEname());
      this.setDeptno(rowData.getDeptno());
      this.setHireDate(rowData.getHireDate());
      this.setMgr(rowData.getMgr());
      this.setSal(rowData.getSal());
      this.setComm(rowData.getComm());
    }
  }

  private void _filterList(String eName, boolean caseInsensitive)
  {
    _filteredList.clear();
    if (eName != null)
    {
      for (FileData data : _values)
      {
        if (caseInsensitive)
        {
          if(data.getEname().toUpperCase().startsWith(eName.toUpperCase()))
          {
            _filteredList.add(data);
          }
        }
        else if (data.getEname().startsWith(eName) )
        {
          _filteredList.add(data);
        }
      }
    }
    else
    {
      _filteredList = new ArrayList(_values);
    }
    listModel = getListLovCollection();
  }

  public CollectionModel getListModel()
  {
    return listModel;
  }

  public ListOfValuesModel getListOfValuesModel()
  {
    if(_listOfValuesModel == null)
      _listOfValuesModel = new ListOfValuesModelImpl(this);
    return _listOfValuesModel;
  }

  /**
   * Get the Model object. Instanitiate if not exists and return.
   * @return - DynamicLOVModelImpl instance.
   */
  public ListOfValuesModel getDynamicLOVModel()
  {
    if(_dynamicLOVModel == null)
      _dynamicLOVModel = new DynamicLOVModelImpl(this);
    return _dynamicLOVModel;
  }

  private ListOfValuesModel _dynamicLOVModel;
  private ListOfValuesModel _listOfValuesModel;

  public void validate(FacesContext facesContext, UIComponent uIComponent,
                       Object object)
  {
    if (object == null || "".equals(object.toString().trim()))
      return;

    for (Object data : _values)
    {
      if (((FileData)data).getEname().equals(object))
      {
        return;
      }
    }
    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "Not a Valid Value",
                                            "Not a Valid Value");
    throw new ValidatorException(message);
  }

  /**
   * ValueChangeListener for testing the efficacy of immediate value submissions
   * on an LOV component, in order to avoid validation triggered on other ppr
   * updated fields. For an LOV component, the VCE is broadcast only when a
   * valid value is entered/picked in an LOV field. Moreover jumping to Render
   * Response is not the right solution because the LaunchPopupEvent and
   * ReturnPopupEvent are both queued for the InvokeApplication phase.
   *
   * @param vce ValueChangeEvent
   */
  public void immediateValueChange(ValueChangeEvent vce)
  {
    FacesContext.getCurrentInstance().renderResponse();
  }

  public FileData _getRowData(Object selectedRowKey)
  {
    if (selectedRowKey != null && selectedRowKey instanceof RowKeySet)
    {
      Iterator selection = ((RowKeySet) selectedRowKey).iterator();
      while (selection.hasNext())
      {
        Object rowKey = selection.next();
        Object oldRowKey = listModel.getRowKey();
        listModel.setRowKey(rowKey);
        FileData rowData = (FileData)listModel.getRowData();
        listModel.setRowKey(oldRowKey);
        return rowData;
      }
    }
    return null;
  }

  public void setEname1(String _ename1)
  {
    this._ename1 = _ename1;
  }

  public String getEname1()
  {
    return _ename1;
  }

  public void setReturnPopupDataValue1(Object returnPopupDataValue1)
  {
    this.returnPopupDataValue1 = returnPopupDataValue1;
  }

  public Object getReturnPopupDataValue1()
  {
    return returnPopupDataValue1;
  }

  public static class ListOfValuesModelImpl extends ListOfValuesModel
  {
    public ListOfValuesModelImpl(DemoLOVBean bean)
    {
      _bean = bean;
    }

    /**
     * Not applicable as items are only supported in comboLOV
     * @return
     */
    @Override
    public List<? extends Object> getItems()
    {
      return null;
    }

    /**
     * Returns null for now.
     * @return
     */
    @Override
    public QueryModel getQueryModel()
    {
      return new QueryModelImpl();
    }

    /**
     * Not applicable as items are only supported in comboLOV
     * @return
     */
    @Override
    public List<? extends Object> getRecentItems()
    {
      return null;
    }

    @Override
    public TableModel getTableModel()
    {
      return new TableModelImpl(_bean.getListModel());
    }

    @Override
    public boolean isAutoCompleteEnabled()
    {
      return false;
    }

    public void performQuery(QueryDescriptor qd)
    {
      AttributeCriterion criterion = (AttributeCriterion) qd.getConjunctionCriterion().getCriterionList().get(0);
      String ename = (String) criterion.getValues().get(0);
      _bean._filterList(ename, false);
    }

    public List<Object> autoCompleteValue(Object value)
    {
      // wierd way of filtering and accessing _filteredList but for now its ok
      _bean._filterList((String)value, false);
      int size = _bean._filteredList.size();
      if (size == 1)
      {
        List<Object> returnList = new ArrayList<Object>();
        RowKeySet rowKeySet = new RowKeySetImpl();
        rowKeySet.add(new Integer(0));
        returnList.add(rowKeySet);
        return returnList;
      }
      else if(size>0) // multiple match case
      {
        AttributeCriterion searchField =
          (AttributeCriterion) getQueryDescriptor().getConjunctionCriterion().getCriterion(0);
        searchField.setValue(value);
      }

      // sad that we need to cast, return type should have been List<?> instead
      return (List) _bean._filteredList;
    }

    public void setCriterionValue(Object value)
    {
      AttributeCriterion searchField =
        (AttributeCriterion) getQueryDescriptor().getConjunctionCriterion().getCriterion(0);
      searchField.setValue(value);
    }

    public void valueSelected(Object value)
    {
      FileData rowData = _getRowData(value);
      if(rowData != null)
      {
        _bean.setValues(rowData);
      }

      // reset the value
      AttributeCriterion criterion = (AttributeCriterion) getQueryDescriptor().getConjunctionCriterion().getCriterion(0);
      criterion.setValue("");
    }

    @Override
    public void searchCancelled()
    {
      // reset the value
      AttributeCriterion criterion = (AttributeCriterion) getQueryDescriptor().getConjunctionCriterion().getCriterion(0);
      criterion.setValue("");
    }

    @Override
    public Object getValueFromSelection(Object selectedRowKey)
    {
        FileData rowData = _getRowData(selectedRowKey);
        if(rowData != null)
        {
          return rowData.getEname();
        }
        return null;
    }

    private FileData _getRowData(Object selectedRowKey)
    {
      if (selectedRowKey != null && selectedRowKey instanceof RowKeySet)
      {
        Iterator selection = ((RowKeySet) selectedRowKey).iterator();
        while (selection.hasNext())
        {
          Object rowKey = selection.next();
          Object oldRowKey = _bean.listModel.getRowKey();
          _bean.listModel.setRowKey(rowKey);
          FileData rowData = (FileData)_bean.listModel.getRowData();
          _bean.listModel.setRowKey(oldRowKey);
          return rowData;
        }
      }
      return null;
    }

    public QueryDescriptor getQueryDescriptor()
    {
      if(_queryDescriptor == null)
        _queryDescriptor = new QueryDescriptorImpl();
      return _queryDescriptor;
    }

    private QueryDescriptor _queryDescriptor;
    private DemoLOVBean _bean;

  }

  // Simple implementation of the QueryDescriptor classs to display one inputText
  // field to filter the data in the table inside dialog based on the Ename
  // For now return a void implementation for the querymodel to show a simple query component
  // such that the Search... link will also be displayed in the dropdown
  public static class QueryModelImpl
    extends QueryModel
  {

    public QueryDescriptor create(String name, QueryDescriptor qdBase)
    {
      return null;
    }

    public void delete(QueryDescriptor qd)
    {
    }

    public List<AttributeDescriptor> getAttributes()
    {
      return null;
    }

    public List<QueryDescriptor> getSystemQueries()
    {
      return null;
    }

    public List<QueryDescriptor> getUserQueries()
    {
      return null;
    }

    public void reset(QueryDescriptor qd)
    {
    }

    public void setCurrentDescriptor(QueryDescriptor qd)
    {
    }

    public void update(QueryDescriptor qd, Map<String, Object> uiHints)
    {
    }
  }

  public static class QueryDescriptorImpl
    extends QueryDescriptor
  {
    public QueryDescriptorImpl()
    {
      _conjCriterion = new ConjunctionCriterionImpl();
    }

    public void addCriterion(String name)
    {
    }

    public void changeMode(QueryDescriptor.QueryMode mode)
    {
    }

    public ConjunctionCriterion getConjunctionCriterion()
    {
      return _conjCriterion;
    }

    public void setConjunctionCriterion(ConjunctionCriterion criterion)
    {
      _conjCriterion = criterion;
    }

    public String getName()
    {
      return null;
    }

    public Map<String, Object> getUIHints()
    {
      return new HashMap<String, Object>();
    }

    public void removeCriterion(oracle.adf.view.rich.model.Criterion object)
    {
    }

    public AttributeCriterion getCurrentCriterion()
    {
      return null;
    }

    public void setCurrentCriterion(AttributeCriterion attrCriterion)
    {
    }

    ConjunctionCriterion _conjCriterion;
  }

  public static class AttributeDescriptorImpl
    extends AttributeDescriptor
  {

    public AttributeDescriptor.ComponentType getComponentType()
    {
      return AttributeDescriptor.ComponentType.inputText;
    }

    public String getDescription()
    {
      return null;
    }

    public String getFormat()
    {
      return null;
    }

    public String getLabel()
    {
      return "Ename";
    }

    public int getLength()
    {
      return 0;
    }

    public int getMaximumLength()
    {
      return 0;
    }

    public Object getModel()
    {
      return null;
    }

    public String getName()
    {
      return null;
    }

    public Set<AttributeDescriptor.Operator> getSupportedOperators()
    {
      return null;
    }

    public Class getType()
    {
      return null;
    }

    public boolean isReadOnly()
    {
      return false;
    }

    public boolean isRequired()
    {
      return false;
    }
  }

  public static class ConjunctionCriterionImpl
    extends ConjunctionCriterion
  {
    public ConjunctionCriterionImpl()
    {
      _criterionList = new ArrayList<Criterion>();
      _criterionList.add(new AttributeCriterionImpl());
    }

    public ConjunctionCriterion.Conjunction getConjunction()
    {
      return ConjunctionCriterion.Conjunction.NONE;
    }

    public List<oracle.adf.view.rich.model.Criterion> getCriterionList()
    {
      return _criterionList;
    }

    public Object getKey(oracle.adf.view.rich.model.Criterion criterion)
    {
      return Integer.toString(0);
    }

    public Criterion getCriterion(Object key)
    {
      assert(_criterionList != null);
      return _criterionList.get(0);
    }

    public void setConjunction(ConjunctionCriterion.Conjunction conjunction)
    {
    }
    List<Criterion> _criterionList;
  }

  public static class AttributeCriterionImpl extends AttributeCriterion
  {
    public AttributeCriterionImpl()
    {
      if(_values == null)
      {
        _values = new ArrayList<Object>();
        _values.add("A");
      }
    }

    public AttributeDescriptor getAttribute()
    {
      return new AttributeDescriptorImpl();
    }

    public AttributeDescriptor.Operator getOperator()
    {
      return null;
    }

    public Map<String, AttributeDescriptor.Operator> getOperators()
    {
      return null;
    }

    public List<? extends Object> getValues()
    {
      return _values;
    }

    public boolean isRemovable()
    {
      return false;
    }

    public void setOperator(AttributeDescriptor.Operator operator)
    {
    }

    List<Object> _values;
  }

  public static class TableModelImpl extends TableModel
  {
    public TableModelImpl(CollectionModel collectionModel)
    {
      assert(collectionModel != null);
      _collectionModel = collectionModel;
    }
    @Override
    public CollectionModel getCollectionModel()
    {
      return _collectionModel;
    }

    @Override
    public List<ColumnDescriptor> getColumnDescriptors()
    {
      if (_descriptors == null)
      {
        _descriptors = new ArrayList<ColumnDescriptor>(_attributes.size());
        for (String attr : _attributes)
        {
          _descriptors.add (new ColumnDescriptorImpl (attr));
        }
      }
      return _descriptors;
    }

    public static class ColumnDescriptorImpl extends ColumnDescriptor
    {
      public ColumnDescriptorImpl(String name)
      {
        _name = name;
      }

      @Override
      public String getFormat()
      {
        return null;
      }

      @Override
      public String getLabel()
      {
        return _name.toUpperCase();
      }

      @Override
      public String getName()
      {
        return _name;
      }

      @Override
      public Class getType()
      {
        return String.class;
      }

      @Override
      public String getAlign()
      {
        return null;
      }

      @Override
      public AttributeDescriptor.ComponentType getComponentType()
      {
        return AttributeDescriptor.ComponentType.inputText;
      }

      @Override
      public String getDescription()
      {
        return null;
      }

      @Override
      public String getModel()
      {
        return null;
      }

      @Override
      public Set<AttributeDescriptor.Operator> getSupportedOperators()
      {
        return Collections.emptySet();
      }

      @Override
      public int getLength()
      {
        return 0;
      }

      @Override
      public int getMaximumLength()
      {
        return 0;
      }

      @Override
      public boolean isRequired()
      {
        return false;
      }

      @Override
      public int getWidth()
      {
        return 0;
      }

      @Override
      public boolean isReadOnly()
      {
        return true;
      }

      private String _name;


    }


    private CollectionModel _collectionModel;
    private static List<ColumnDescriptor> _descriptors;
  }

  public class FileData
  {
    private int empno;
    private String ename;
    private String job;
    private Integer mgr;
    private Date hireDate;
    private Integer sal;
    private Integer comm;
    private Integer deptno;
    private Integer rowId;

    FileData(int empno, String ename, String job, Integer mgr,
             Date hireDate, Integer sal, Integer comm, Integer deptno,
             Integer rowID)
    {
      this.empno = empno;
      this.ename = ename;
      this.job = job;
      this.mgr = mgr;
      this.hireDate = hireDate;
      this.sal = sal;
      this.comm = comm;
      this.deptno = deptno;
      this.rowId = rowID;
    }

    public int getEmpno()
    {
      return empno;
    }

    public String getEname()
    {
      return ename;
    }

    public String getJob()
    {
      return job;
    }

    public Integer getMgr()
    {
      return mgr;
    }

    public Date getHireDate()
    {
      return hireDate;
    }

    public Integer getSal()
    {
      return sal;
    }

    public Integer getComm()
    {
      return comm;
    }

    public Integer getDeptno()
    {
      return deptno;
    }

    public Integer getRowId()
    {
      return rowId;
    }
  }

  // return a pre defined list as the smart list
  public List<SelectItem> smartList()
  {
    List<SelectItem> items = new ArrayList<SelectItem>();
    for (int i=0; i < _DIR_DATA.length; i++)
    {
      SelectItem selectItem = new SelectItem();
      FileData data = _values.get(i);
      String eName = data.getEname();
      selectItem.setLabel(String.format("%-15s%s", eName, data.getJob()));
      selectItem.setValue(eName);
      items.add(selectItem);
    }
    return items;
  }

  public List<SelectItem> suggestedItems(String searchString)
  {
    List<FileData> values;

    if (searchString == null || searchString.length() == 0)
    {
      // This code should never get executed as we don't queue the event on the client
      // when searchString length is 0
      return null;
    }
    else
    {
      _filterList(searchString, true);
      values = _filteredList;
    }

    int size = values.size();
    int maxItems = Math.min(10, size);

    List<SelectItem> items = new ArrayList<SelectItem>();
    for (int i = 0; i < maxItems; i++)
    {
      SelectItem selectItem = new SelectItem();
      FileData data = values.get(i);
      String eName = data.getEname();
      selectItem.setLabel(String.format("%-15s%s", eName, data.getJob()));
      selectItem.setValue(eName);
      items.add(selectItem);
    }
    return items;
  }

  public List<SelectItem> suggestItems(FacesContext context, AutoSuggestUIHints hints)
  {
    String searchString = hints.getSubmittedValue();
    List<FileData> values;
    int maxSuggestedItems = hints.getMaxSuggestedItems();

    if (searchString == null || searchString.length() == 0)
    {
      // This code should never get executed as we don't queue the event on the client
      // when searchString length is 0
      return null;
    }
    else
    {
      _filterList(searchString, true);
      values = _filteredList;
    }

    int size = values.size();
    if(maxSuggestedItems == -1)
      maxSuggestedItems = size;
    else
      maxSuggestedItems = Math.min(maxSuggestedItems, size);

//    int maxItems = Math.min(10, maxSuggestedItems);

   List<SelectItem> items = new ArrayList<SelectItem>();
    for (int i = 0; i < maxSuggestedItems; i++)
    {
      SelectItem selectItem = new SelectItem();
      FileData data = values.get(i);
      String eName = data.getEname();
      selectItem.setLabel(String.format("%-15s%s", eName, data.getJob()));
      selectItem.setValue(eName);
      items.add(selectItem);
    }
    return items;
  }

  //Single datasource
  static Object _DIR_DATA[][] =
  {
    { "Adam", "Engineer", 1, "1998-01-19", 23232, 66767, 10 },
    { "Avance", "Manager", 1, "2002-04-15", 24232, 32211, 20 },
    { "Abdul", "Analyst", 1, "2001-01-19", 25232, 444, 10 },
    { "Blake", "Technician", 1, "1998-01-14", 53232, 8787, 30 },
    { "Bob", "Engineer", 1, "1998-06-19", 23432, 5454, 40 },
    { "Brenta", "Manager", 1, "1998-04-19", 6454, 39343, 30 },
    { "Bejond", "Analyst", 1, "2001-01-14", 5455, 23122, 10 },
    { "Calvin", "Analyst", 1, "2002-04-19", 54465, 39343, 10 },
    { "Carl", "Engineer", 1, "1998-01-12", 54345, 54544, 40 },
    { "Chris", "Technician", 1, "2002-01-23", 3212, 6565, 20 },
    { "Claire", "Manager", 1, "2001-06-19", 43234, 5555, 20 },
    { "Dave", "Operator", 1, "2002-04-11", 76456, 434343, 40 },
    { "David", "Manager", 1, "2001-01-22", 78687, 2222, 10 },
    { "Derek", "Analyst", 1, "1998-04-19", 4323, 21231, 30 },
    { "Eric", "Technician", 1, "2002-01-15", 45354, 2112, 10 },
    { "Eilane", "Engineer", 1, "2001-06-24", 9879, 4534, 40 },
    { "Frank", "Analyst", 1, "1998-06-25", 65454, 43543, 50 },
    { "Fonda", "Technician", 1, "1998-01-26", 43543, 564654, 30 },
    { "Ford", "Manager", 1, "2001-04-09", 56465, 76658, 30 },
    { "Gary", "Analyst", 1, "2002-01-21", 23232, 7676, 20 },
    { "Good", "Engineer", 1, "2002-01-08", 65465, 54565, 60 },
    { "Goodon", "Analyst", 1, "2001-04-07", 23232, 4343, 60 },
    { "T.J", "Technician", 1, "2002-01-05", 45345, 56565, 50 },
    { "James", "Engineer", 1, "2002-01-44", 43453, 5454, 10 },
    { "Henry", "Operator", 1, "2001-01-09", 7665, 7657, 20 },
    { "Howard", "DBA", 1, "2002-01-17", 4444, 21113, 20 },
    { "Ivory", "Operator", 1, "1999-12-19", 43653, 5454, 50 },
    { "Jidd", "DBA", 1, "1998-11-20", 4343, 54564, 50 },
    { "J.R.", "Consultant", 1, "2001-10-21", 47343, 5454, 70 },
    { "Jordon", "Operator", 1, "2000-09-22", 49843, 5454, 50 },
    { "Karl", "DBA", 1, "1998-08-29", 4343, 98054, 50 },
    { "Kerry", "Consultant", 1, "1996-07-23", 6553, 5454, 70 },
    { "King", "DBA", 1, "1998-06-24", 4343, 7854, 50 },
    { "Larry", "Consultant", 1, "1995-06-25", 47893, 5454, 50 },
    { "Lelsie", "Consultant", 1, "1994-05-26", 6743, 5454, 70 },
    { "Linda", "DBA", 1, "1993-04-11", 4343, 9854, 50 },
    { "Merk", "DBA", 1, "1992-03-12", 4343, 5476, 50 },
    { "Mandona", "Consultant", 1, "1991-02-13", 6643, 5454, 50 },
    { "Nomarn", "Operator", 1, "1990-01-14", 43343, 5454, 50 }
  };

  public CollectionModel getListLovCollection()
  {
    return new SortableModel(_filteredList);
  }

  /**
   * LOV Model for dynamic auto suggest behavior added components.
   */
  public static class DynamicLOVModelImpl extends ListOfValuesModelImpl
  {
    public DynamicLOVModelImpl(DemoLOVBean bean)
    {
      super(bean);
    }

    /**
     * Get AutoSuggestBehaviorConfigImpl instance.
     */
    public AutoSuggestBehaviorConfig getAutoSuggestBehaviorConfig()
    {
      if (_autoSuggestConfig == null)
      {
        _autoSuggestConfig = new AutoSuggestBehaviorConfigImpl();
      }
      return _autoSuggestConfig;
    }

    private AutoSuggestBehaviorConfig _autoSuggestConfig;
  }

  private static class AutoSuggestBehaviorConfigImpl extends AutoSuggestBehaviorConfig
  {
    @Override
    public ValueExpression getMinChars()
    {
      FacesContext context = FacesContext.getCurrentInstance();
      Application app = context.getApplication();
      return app.getExpressionFactory().createValueExpression(context.getELContext(), "2", Integer.class);
    }

    @Override
    public ValueExpression getMaxSuggestedItems()
    {
      FacesContext context = FacesContext.getCurrentInstance();
      Application app = context.getApplication();
      return app.getExpressionFactory().createValueExpression(context.getELContext(), "6", Integer.class);
    }

    @Override
    public MethodExpression getSuggestItems()
    {
      FacesContext context = FacesContext.getCurrentInstance();
      Application app = context.getApplication();
      return app.getExpressionFactory().createMethodExpression(context.getELContext(),
                                                               "#{demoLOV.suggestItems}",
                                                               List.class,
                                                               new Class[]{FacesContext.class,
                                                                                       AutoSuggestUIHints.class});
    }

    @Override
    public ValueExpression getSmartList()
    {
      FacesContext context = FacesContext.getCurrentInstance();
      Application app = context.getApplication();
      return app.getExpressionFactory().createValueExpression(context.getELContext(),
                                                              "#{demoLOV.smartList}",
                                                              List.class);
    }
  }

  private CollectionModel listModel = getListLovCollection();
  private List<FileData> _values = new ArrayList<FileData>();
  private List<FileData> _filteredList = new ArrayList<FileData>();
  private static List<String> _attributes = new ArrayList<String>();

  static
  {
    _attributes.add("empno");
    _attributes.add("ename");
    _attributes.add("job");
    _attributes.add("mgr");
    _attributes.add("hireDate");
    _attributes.add("sal");
    _attributes.add("comm");
    _attributes.add("deptno");
  }
}
