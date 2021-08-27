/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableComboboxLOVModel.java /main/4 2012/07/09 13:46:40 jievans Exp $ */
/* Copyright (c) 2008, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ccchow      10/05/09 - populate filtered list
    dahmed      08/27/08 - 
    ahadi       06/27/08 - model for comboboxLOV
    ahadi       06/27/08 - Creation
 */

 package oracle.adfdemo.view.feature.rich.pivotTable.data;

 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;

 import javax.faces.application.FacesMessage;
 import javax.faces.component.UIComponent;

 import javax.faces.context.FacesContext;
 import javax.faces.validator.ValidatorException;

 import oracle.adf.view.rich.model.AttributeCriterion;
 import oracle.adf.view.rich.model.AttributeDescriptor;
 import oracle.adf.view.rich.model.ColumnDescriptor;
 import oracle.adf.view.rich.model.ConjunctionCriterion;
 import oracle.adf.view.rich.model.Criterion;
 import oracle.adf.view.rich.model.ListOfValuesModel;
 import oracle.adf.view.rich.model.QueryDescriptor;
 import oracle.adf.view.rich.model.QueryModel;
 import oracle.adf.view.rich.model.TableModel;

 import org.apache.myfaces.trinidad.model.CollectionModel;
 import org.apache.myfaces.trinidad.model.RowKeySet;
 import org.apache.myfaces.trinidad.model.RowKeySetImpl;

/**
 * This data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableComboboxLOVModel.java /main/4 2012/07/09 13:46:40 jievans Exp $
 *  @author  ahadi   
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableComboboxLOVModel
{
  
  public PivotTableComboboxLOVModel()
  {
    for (int i = 0; i < _DIR_DATA.length ; i++)
    {
      //try
      //{
        Object data[] = _DIR_DATA[i % _DIR_DATA.length];
        String item1 = (String) data[0];
        String item2 = (String) data[1];
        Integer item3 = new Integer(data[2].toString());
        String item4 = new Integer(i).toString();
        FileData _curRow = 
          new FileData(item1, item2, item3, item4);
        _values.add(_curRow);
      //}
      //catch (ParseException e)
      //{
      //  e.printStackTrace();
      //}
    }

    // gets the list populated
    filterList(null);
  }

  //------------------public API for binding --------------------------------//    
  private String _code;

  public void setCode(String code)
  {
    _code = code;
  }

  public String getCode()
  {
    return _code;
  }

  private void _addToRecentValuesList(FileData rowData)
  {
    if (!_recentValues.contains(rowData))
      _recentValues.add(0, rowData);

    int size = _recentValues.size();
    if (size > 3)
      _recentValues.remove(3);
  }

  public void validate(FacesContext facesContext, UIComponent uIComponent, 
                       Object object)
  {
    for (Object data: _values)
    {
      if (((FileData) data).getCode().equals(object))
      {
        return;
      }
    }
    FacesMessage message = 
      new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not a Valid Value", 
                       "Not a Valid Value");
    throw new ValidatorException(message);
  }

  public List getValues()
  {
    return _filteredList;
  }

  public List getRecentValues()
  {
    List recentValues = new ArrayList();
    recentValues.addAll(_recentValues);

    if (recentValues.size() > 0)
      recentValues.add(new FileData(null, null, null,null));

    return recentValues;
  }

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
  
  private ListOfValuesModel _listOfValuesModel;

  private void filterList(String code)
  {

    _filteredList.clear();
// we don't want to filter anything
//    if (code != null)
//    {
//      for (Object data: _values)
//      {
//        if (((FileData) data).getCode().startsWith(code))
//        {
//          _filteredList.add(data);
//        }
//      }
//    }
    if (_filteredList.size() == 0)
    {
      _filteredList.addAll(_values);
    }
  }
  
  // In the real case, LOV is using ListBinding, and the MRU support is inside
  // the datasource in the listBinding.
  class BaseLovCollection
    extends CollectionModel
  {
    public Object getRowKey()
    {
      if (_row != null)
      {
        return _row.getRowId();
      }
      return null;
    }

    /**
     * Finds the row with the matching key and makes it current
     * @param rowKey the rowKey, previously obtained from {@link #getRowKey}.
     */
    public void setRowKey(Object rowKey)
    {
      if (rowKey == null)
      {
        _row = null;
        return;
      }

      int index = -1;
      for (int i = 0; i < _filteredList.size(); i++)
      {
        String rowId = ((FileData) _filteredList.get(i)).getRowId();
        if (rowId.equals(rowKey))
        {
          index = i;
          break;
        }
      }

      setRowIndex(index);
    }

    public void setRowIndex(int rowIndex)
    {
      int size = _filteredList.size();
      if (rowIndex < 0 || rowIndex > size || size == 0)
      {
        _row = null;
        _rowIndex = -1;
      }
      else
      {
        _row = (FileData) _filteredList.get(rowIndex);
        _rowIndex = rowIndex;
      }
    }

    public int getRowIndex()
    {
      return _rowIndex;
    }

    public Object getRowData()
    {
      return _row;
    }

    public int getRowCount()
    {
      return _filteredList.size();
    }

    public boolean isRowAvailable()
    {
      return (_row != null);
    }

    public Object getRowData(int rowIndex)
    {
      int oldIndex = getRowIndex();
      try
      {
        setRowIndex(rowIndex);
        return getRowData();
      }
      finally
      {
        setRowIndex(oldIndex);
      }
    }

    public boolean isSortable(String property)
    {
      return false;
    }

    public List getSortCriteria()
    {
      return Collections.EMPTY_LIST;
    }

    public Object getWrappedData()
    {
      return BaseLovCollection.this;
    }

    public void setWrappedData(Object data)
    {
      throw new UnsupportedOperationException();
    }

    public BaseLovCollection()
    {
    }

    FileData _row = null;
    int _rowIndex = -1;
  }

  class ListLovCollection
    extends CollectionModel
  {
    public Object getRowKey()
    {
      if (_row != null)
      {
        return _row.getRowId();
      }
      return null;
    }

    /**
     * Finds the row with the matching key and makes it current
     * @param rowKey the rowKey, previously obtained from {@link #getRowKey}.
     */
    public void setRowKey(Object rowKey)
    {
      if (rowKey == null)
      {
        _row = null;
        return;
      }

      int index = -1;
      for (int i = 0; i < _filteredList.size(); i++)
      {
        String rowId = ((FileData) _filteredList.get(i)).getRowId();
        if (rowId.equals(rowKey))
        {
          index = i;
          break;
        }
      }

      setRowIndex(index);
    }

    public void setRowIndex(int rowIndex)
    {
      int size = _filteredList.size();
      if (rowIndex < 0 || rowIndex > size || size == 0)
      {
        _row = null;
        _rowIndex = -1;
      }
      else
      {
        _row = (FileData) _filteredList.get(rowIndex);
        _rowIndex = rowIndex;
      }
    }

    public int getRowIndex()
    {
      return _rowIndex;
    }

    public Object getRowData()
    {
      return _row;
    }

    public int getRowCount()
    {
      return _filteredList.size();
    }

    public boolean isRowAvailable()
    {
      return (_row != null);
    }

    public Object getRowData(int rowIndex)
    {
      int oldIndex = getRowIndex();
      try
      {
        setRowIndex(rowIndex);
        return getRowData();
      }
      finally
      {
        setRowIndex(oldIndex);
      }
    }

    public boolean isSortable(String property)
    {
      return false;
    }

    public List getSortCriteria()
    {
      return Collections.EMPTY_LIST;
    }

    public Object getWrappedData()
    {
      return ListLovCollection.this;
    }

    public void setWrappedData(Object data)
    {
      throw new UnsupportedOperationException();
    }

    FileData _row = null;
    int _rowIndex = -1;
  }

  public static class ListOfValuesModelImpl
    extends ListOfValuesModel
  {
    public ListOfValuesModelImpl(PivotTableComboboxLOVModel bean)
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
    public boolean isAutoCompleteEnabled()
    {
      return false;
    }

    public void performQuery(QueryDescriptor qd)
    {
      AttributeCriterion criterion = (AttributeCriterion) qd.getConjunctionCriterion().getCriterionList().get(0);
      String code = (String) criterion.getValues().get(0);
      _bean.filterList(code);
    }

    public List<Object> autoCompleteValue(Object value)
    {
      // wierd way of filtering and accessing _filteredList but for now its ok
      _bean.filterList((String) value);
      if (_bean._filteredList.size() == 1)
      {
        List<Object> returnList = new ArrayList<Object>();
        FileData rowData = (FileData) _bean._filteredList.get(0);
        Object rowKey = rowData.getRowId();
        RowKeySet rowKeySet = new RowKeySetImpl();
        rowKeySet.add(rowKey);
        returnList.add(rowKeySet);
        return returnList;
      }
      return null;
    }

    public void valueSelected(Object value)
    {
      FileData rowData = _getRowData(value);
      if(rowData != null)
      {
        _bean.setCode(rowData.getCode());
        _bean._addToRecentValuesList(rowData);
      }
    }
    
    @Override
    public Object getValueFromSelection(Object selectedRow)
    {
      FileData rowData = _getRowData(selectedRow);
      if(rowData != null)
      {
        return rowData.getCode();
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
    private PivotTableComboboxLOVModel _bean;
    
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
    { "X-S", "Extra Small", 10 },
    { "S", "Small", 20 },
    { "M", "Medium", 30 }, 
    { "L", "Large", 40 },
    { "X-L", "Extra Large", 50 }
  };

  public class FileData
  {    
    private String code;
    private String description;
    private Integer size;
    private String rowId;
    

    FileData(String code, String desc, Integer size, String rowId)
    {
      this.code = code;
      this.description = desc;
      this.size = size;
      this.rowId = rowId;
    }

    public String getRowId()
    {
       return rowId;
    }

    public String getCode()
    {
      return code;
    }

    public String getDescription()
    {
      return description;
    }


    public Integer getSize()
    {
      return size;
    }

  }


  List _values = new ArrayList();
  List _recentValues = new ArrayList();
  List _filteredList = new ArrayList();

  //listModel is for the table binding for table in LOV popup dialog
  private CollectionModel listModel = new ListLovCollection();
  //baseModel is for defined for the base component.
  private CollectionModel baseModel = new BaseLovCollection();
  //display attributes.
  private String[] _attrs = new String[]
    { "ename", "job", "sal" };

  private static List<String> _attributes = new ArrayList<String>();
  static {
    _attributes.add("code");
    _attributes.add("description");
     /*Bug 6909956 - inputcombolistofvalues demo should only show two columns
    _attributes.add("job");
    _attributes.add("mgr");
    _attributes.add("hireDate");
    _attributes.add("sal");
    _attributes.add("comm");
    _attributes.add("deptno");*/
  }
}
