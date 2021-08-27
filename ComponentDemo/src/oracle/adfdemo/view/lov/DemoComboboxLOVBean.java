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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.share.ADFConfig;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.UIXInputPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.LaunchPopupEvent;
import oracle.adf.view.rich.event.LaunchPopupListener;
import oracle.adf.view.rich.event.ReturnPopupEvent;
import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.ColumnDescriptor;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;
import oracle.adf.view.rich.model.ListOfValuesModel;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;
import oracle.adf.view.rich.model.TableModel;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.model.SortableModel;


public class DemoComboboxLOVBean
{
  public DemoComboboxLOVBean()
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
        String item7 = new Integer(i).toString();
        FileData _curRow =
          new FileData(new Integer(i), item1, (String) data[1], item2,
                       item3, item4, item5, item6, item7);
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

  public void setEname1(String ename1)
  {
    _ename1 = ename1;
  }

  public String getEname1()
  {
    return _ename1;
  }

  private Object returnPopupDataValue;

  public void setReturnPopupDataValue(Object returnPopupDataValue)
  {
      this.returnPopupDataValue = returnPopupDataValue;
  }

  public Object getReturnPopupDataValue()
  {
      return returnPopupDataValue;
  }

  private Object returnPopupDataValue1;

  public void selected(SelectionEvent event)
  {
    setReturnPopupDataValue(event.getAddedSet());
  }

  public void returnPopupListener(ReturnPopupEvent returnPopupEvent)
  {
    Object value = returnPopupEvent.getReturnValue();
    if (value != null)
    {
      FileData rowData = _getRowData(value);
      if(rowData != null)
      {
        this.setEname(rowData.getEname());
        //this._addToRecentValuesList(rowData, this._recentValues);
      }
      else
        this.setEname(value.toString());
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

  private void _addToRecentValuesList(FileData rowData, List recentValues)
  {
    if (!recentValues.contains(rowData))
      recentValues.add(0, rowData);

    int size = recentValues.size();
    if (size > 3)
      recentValues.remove(3);
  }

  private FileData _getRowData(Object selectedRow)
  {
    if (selectedRow != null && selectedRow instanceof List)
    {
      List listvalue = (List) selectedRow;
      for (int i = 0; i < listvalue.size(); i++)
      {
        Object rowData = listvalue.get(i);
        if (rowData instanceof FileData)
        {
          return ((FileData) rowData);
        }
      }
    }
    else if (selectedRow != null && selectedRow instanceof RowKeySet)
    {
      Iterator selection = ((RowKeySet) selectedRow).iterator();
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

  public void validate(FacesContext facesContext, UIComponent uIComponent,
                       Object object)
  {
    if (object == null || "".equals(object.toString().trim()))
      return;

    for (Object data: _values)
    {
      if (((FileData) data).getEname().equals(object))
      {
        return;
      }
    }

    FacesMessage message;
    if (_isShowSearchDialogOnError())
    {
      message =
       new FacesMessage(FacesMessage.SEVERITY_ERROR, "_afrlovshowsearchdialogonerror", "_afrlovshowsearchdialogonerror");
    }
    else
    {
      message =
      new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not a Valid Value",
                       "Not a Valid Value");
    }
    throw new ValidatorException(message);
  }

  /**
   * Returns true if search dialog needs to be displayed on input field losing focus
   */
  private static Boolean _isShowSearchDialogOnError;
  private static Boolean _isShowSearchDialogOnError()
  {
    if (_isShowSearchDialogOnError == null)
    {
      ADFConfig config = ADFContext.getCurrent().getADFConfig();
      Map<?,?> adfFacesConfig = config.getConfigObject("http://xmlns.oracle.com/adf/faces/config");
      String propertyValue = adfFacesConfig == null ?
                             null :
                             (String) adfFacesConfig.get("lov-show-searchdialog-onerror");
      _isShowSearchDialogOnError = Boolean.valueOf(propertyValue);
    }
    return _isShowSearchDialogOnError;
  }

  public List getValues()
  {
    return _values;
  }

  public List getRecentValues()
  {
    List recentValues = new ArrayList();
    recentValues.addAll(_recentValues);

    if (recentValues.size() > 0)
      recentValues.add(new FileData(null, null, null, null, null, null,
                                    null, null, null));

    return recentValues;
  }

  public List getRecentValues1()
  {
    List recentValues = new ArrayList();
    recentValues.addAll(_recentValues1);

    if (recentValues.size() > 0)
      recentValues.add(new FileData(null, null, null, null, null, null,
                                    null, null, null));

    return recentValues;
  }

  // "LaunchPopupListener" attribute EL reachable
  public LaunchPopupListener getLaunchPopupListener()
  {
    return new DemoLOVPopupListener();
  }

  private RichTable _table;

  public CollectionModel getListModel()
  {
    return listModel;
  }

  public String[] getAttrs()
  {
    return _attrs;
  }

  public ListOfValuesModel getListOfValuesModel()
  {
    if(_listOfValuesModel == null)
      _listOfValuesModel = new ListOfValuesModelImpl(this);
    return _listOfValuesModel;
  }

  public ListOfValuesModel getListOfValuesModel1()
  {
    if(_listOfValuesModel1 == null)
      _listOfValuesModel1 = new ListOfValuesModelImpl1(this);
    return _listOfValuesModel1;
  }

  private ListOfValuesModel _listOfValuesModel;
  private ListOfValuesModel _listOfValuesModel1;

  private void filterList(String eName, List filtered)
  {
    filtered.clear();
    if (eName != null)
    {
      for (Object data: _values)
      {
        if (((FileData) data).getEname().startsWith(eName))
        {
          filtered.add(data);
        }
      }
    }
    else
    {
      filtered.addAll(_values);
    }
    listModel = getListLovCollection();
//    if (filtered.size() == 0)
//    {
//      filtered.addAll(_values);
//    }
  }

  public void setReturnPopupDataValue1(Object returnPopupDataValue1)
  {
    this.returnPopupDataValue1 = returnPopupDataValue1;
  }

  public Object getReturnPopupDataValue1()
  {
    return returnPopupDataValue1;
  }

  public void setTable(RichTable _table)
  {
    this._table = _table;
  }

  public RichTable getTable()
  {
    return _table;
  }

  public void clearRecentItems(ActionEvent event)
  {
    _recentValues.clear();
  }

  public static class ListOfValuesModelImpl
    extends ListOfValuesModel
  {
    public ListOfValuesModelImpl(DemoComboboxLOVBean bean)
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
      return _bean.getValues();
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
     * @return
     */
    @Override
    public List<? extends Object> getRecentItems()
    {
      return _bean.getRecentValues();
    }

    @Override
    public TableModel getTableModel()
    {
      return new TableModelImpl(_bean.getListModel());
    }

    @Override
    public List<ColumnDescriptor> getItemDescriptors()
    {
      List<ColumnDescriptor> descriptors = getTableModel().getColumnDescriptors();
      if (descriptors != null && descriptors.size() > 3)
      {
        return descriptors.subList(0,3);
      }
      return descriptors;
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
      _bean.filterList(ename, _bean._filteredList);
    }

    public List<Object> autoCompleteValue(Object value)
    {
      // wierd way of filtering and accessing _filteredList but for now its ok
      _bean.filterList((String) value, _bean._filteredList);
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
      return _bean._filteredList;
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
        _bean.setEname(rowData.getEname());
        _bean._addToRecentValuesList(rowData, _bean._recentValues);
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
    public Object getValueFromSelection(Object selectedRow)
    {
      FileData rowData = _getRowData(selectedRow);
      if(rowData != null)
      {
        return rowData.getEname();
      }
      return null;
    }

    private FileData _getRowData(Object selectedRow)
    {
      if (selectedRow != null && selectedRow instanceof List)
      {
        List listvalue = (List) selectedRow;
        for (int i = 0; i < listvalue.size(); i++)
        {
          Object rowData = listvalue.get(i);
          if (rowData instanceof FileData)
          {
            return ((FileData) rowData);
          }
        }
      }
      else if (selectedRow != null && selectedRow instanceof RowKeySet)
      {
        Iterator selection = ((RowKeySet) selectedRow).iterator();
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
    private DemoComboboxLOVBean _bean;

  }

  /* LOVModel for comboLOV with LaunchPopupListener */
  public static class ListOfValuesModelImpl1
    extends ListOfValuesModel
  {
    public ListOfValuesModelImpl1(DemoComboboxLOVBean bean)
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
      return _bean._listenersFilteredList;
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
     * @return
     */
    @Override
    public List<? extends Object> getRecentItems()
    {
      return _bean.getRecentValues1();
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
      _bean.filterList(ename, _bean._filteredList);
    }

    public List<Object> autoCompleteValue(Object value)
    {
      // wierd way of filtering and accessing _filteredList but for now its ok
      _bean.filterList((String) value, _bean._filteredList);
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
      return _bean._filteredList;
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
        _bean.setEname1(rowData.getEname());
        _bean._addToRecentValuesList(rowData, _bean._recentValues1);
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
    public Object getValueFromSelection(Object selectedRow)
    {
      FileData rowData = _getRowData(selectedRow);
      if(rowData != null)
      {
        return rowData.getEname();
      }
      return null;
    }

    private FileData _getRowData(Object selectedRow)
    {
      if (selectedRow != null && selectedRow instanceof List)
      {
        List listvalue = (List) selectedRow;
        for (int i = 0; i < listvalue.size(); i++)
        {
          Object rowData = listvalue.get(i);
          if (rowData instanceof FileData)
          {
            return ((FileData) rowData);
          }
        }
      }
      else if (selectedRow != null && selectedRow instanceof RowKeySet)
      {
        Iterator selection = ((RowKeySet) selectedRow).iterator();
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
    private DemoComboboxLOVBean _bean;

  }


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

  // Simple implementation of the QueryDescriptor classs to display one inputText
  // field to filter the data in the table inside dialog based on the Ename
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

  public static class TableModelImpl
    extends TableModel
  {
    public TableModelImpl(CollectionModel collectionModel)
    {
      assert (collectionModel != null);
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
        for (String attr: _attributes)
        {
          _descriptors.add(new ColumnDescriptorImpl(attr));
        }
      }
      return _descriptors;
    }

    public static class ColumnDescriptorImpl
      extends ColumnDescriptor
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
      public Set<AttributeDescriptor.Operator> getSupportedOperators()
      {
        return Collections.emptySet();
      }

      @Override
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
      @Override
      public int getWidth()
      {
        if(_name.equalsIgnoreCase("ename"))
          return 12*7 + 3;
        else if(_name.equalsIgnoreCase("empno"))
          return 3*7 + 3;
        else if(_name.equalsIgnoreCase("job"))
          return 10*7 + 3;
        else if(_name.equals("mgr"))
          return 2*7 + 3;
        return 0;
      }

      /**
       * The column attributes are all readOnly.
       *
       * @return
       */
      @Override
      public boolean isReadOnly()
      {
        return true;
      }

      @Override
      public boolean isRequired()
      {
        return false;
      }

      private String _name;

    }


    private CollectionModel _collectionModel;
    private static List<ColumnDescriptor> _descriptors;
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
    { "Nomarn", "Operator", 1, "1990-01-14", 43343, 5454, 50 } };

  public class FileData
  {
    private Integer empno;
    private String ename;
    private String job;
    private Integer mgr;
    private Date hireDate;
    private Integer sal;
    private Integer comm;
    private Integer deptno;
    private String rowId;

    FileData(Integer empno, String ename, String job, Integer mgr,
             Date hireDate, Integer sal, Integer comm, Integer deptno,
             String rowID)
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

    public Integer getEmpno()
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

    public String getRowId()
    {
      return rowId;
    }
  }

  public class DemoLOVPopupListener implements LaunchPopupListener
  {
    public void processLaunch(LaunchPopupEvent event)
    {
      if(LaunchPopupEvent.PopupType.DROPDOWN_LIST.equals(event.getPopupType()))
      {
        //DropDown list is launched
        RichInputComboboxListOfValues comboLOV = (RichInputComboboxListOfValues) event.getComponent();
        ListOfValuesModelImpl1 lovModel = (ListOfValuesModelImpl1)comboLOV.getModel();
        String value= (String) event.getSubmittedValue();

        //Here we filter the values in in dropdownlist acording to the value typed in
        lovModel._bean.filterList(value, lovModel._bean._listenersFilteredList);
      }
    }
  }

  List _values = new ArrayList();
  List _recentValues = new ArrayList();
  List _recentValues1 = new ArrayList();
  List _filteredList = new ArrayList();
  List _listenersFilteredList = new ArrayList();

  public CollectionModel getListLovCollection()
  {
    return new SortableModel(_filteredList);
  }

  //listModel is for the table binding for table in LOV popup dialog
  private CollectionModel listModel = getListLovCollection();

  //display attributes.
  private String[] _attrs = new String[]
    { "ename", "job", "sal" };

  private static List<String> _attributes = new ArrayList<String>();
  static {
    _attributes.add("ename");
    _attributes.add("empno");
     /*Bug 6909956 - inputcombolistofvalues demo should only show two columns*/
    _attributes.add("job");
    _attributes.add("mgr");
    _attributes.add("hireDate");
    _attributes.add("sal");
    _attributes.add("comm");
    _attributes.add("deptno");
  }
}
