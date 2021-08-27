/** Copyright (c) 2008, 2016, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.query.rich;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.AttributeDescriptor.ComponentType;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;
import oracle.adf.view.rich.model.ListOfValuesModel;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;

import org.apache.myfaces.trinidad.util.ComponentUtils;


/**
 * A simple bean implementation for the query and quickQuery components.
 */
public class DemoQuickQueryBean
{
  public DemoQuickQueryBean()
  {
    // Setup DemoPageDef containing a list of attribute and list of search field definitions.
    _createPageDef();
 
  }

  /**
   * Creates a DemoPageDef object, containing a list of attributes and a list of search field 
   * definitions.
   */
  private void _createPageDef()
  {
    _pageDef = new DemoPageDef();    
    _pageDef.addAttributeDef("Ename", 
                             "Employee Name",
                             null,
                             String.class, 
                             ComponentType.inputText, 
                             false, null, true, true, false, null,
                             "FirstName LastName");
    _pageDef.addAttributeDef("Empno", 
                             "Employee Number",
                             null,
                             Number.class, 
                             ComponentType.inputNumberSpinbox, 
                             false, null, false, true, false, null,
                             "e.g. 123");
    _pageDef.addAttributeDef("Deptno", 
                             "Department Number",
                             null,
                             Number.class, 
                             ComponentType.inputNumberSpinbox, 
                             false, null, false, true, false, null,
                             "Sample Text");
    String[] deptNames = new String[] {"R&D", "Marketing", "Sales", "HR", "Support"};
    _pageDef.addAttributeDef("Deptname", 
                             "Department Name",
                             String.class, 
                             ComponentType.selectOneChoice, 
                             true, deptNames,
                             false, true);
    _pageDef.addAttributeDef("Manager", 
                             "Manager",
                             String.class,  
                             ComponentType.inputText, 
                             false, null, false, true);
    _pageDef.addAttributeDef("Hiredate", 
                             "Hire Date",
                             null,
                             Date.class, 
                             ComponentType.inputDate, 
                             false, null, false, true, false, null,
                             "Example: 10/19/2008");
    _pageDef.addAttributeDef("Salary", 
                             "Salary",
                             Number.class,  
                             ComponentType.inputNumberSpinbox, 
                             false, null, false, true);
    _pageDef.addAttributeDef("Permanent", 
                             "Permanent", 
                             Boolean.class,
                             ComponentType.selectBooleanCheckbox, 
                             false, null, false, true);
    
    List<DemoPageDef.SearchFieldDef> searchFields = new ArrayList<DemoPageDef.SearchFieldDef> ();
    searchFields.add(_pageDef.new SearchFieldDef("Ename", null));
    searchFields.add(_pageDef.new SearchFieldDef("Empno", null));
    searchFields.add(_pageDef.new SearchFieldDef("Deptno", null));
    searchFields.add(_pageDef.new SearchFieldDef("Deptname", null));
    searchFields.add(_pageDef.new SearchFieldDef("Manager", null));
    searchFields.add(_pageDef.new SearchFieldDef("Hiredate", null));
    searchFields.add(_pageDef.new SearchFieldDef("Salary", null));
    searchFields.add(_pageDef.new SearchFieldDef("Permanent", null));
    _currentDescriptor = new DemoQueryDescriptor(searchFields);
    _queryModel = new DemoQueryModel();    
  }
  //==================== EL reachable APIs ===============================//
  // "value" attribute EL reachable
  public QueryDescriptor getQueryDescriptor()
  {
    return _currentDescriptor;
  }
  
  // "model" attribute EL reachable
  public QueryModel getQueryModel()
  {
    return _queryModel;
  }

  public void processQuery(QueryEvent event)
  {
    QueryDescriptor qd = event.getDescriptor();
    AttributeCriterion criterion = qd.getCurrentCriterion();
    String attrLabel = criterion.getAttribute().getLabel();
    setSqlString("Query based on field '" + attrLabel + 
                  "' with value '" + criterion.getValues().get(0) + "'");
  }
  
  public void setSqlString(String sqlString)
  {
    _sqlString = sqlString;
  }

  public String getSqlString()
  {
    return _sqlString;
  }
  
  public class DemoQueryDescriptor extends QueryDescriptor
  {
    
    public DemoQueryDescriptor (List<DemoPageDef.SearchFieldDef> searchFields)
    {
      _conjunctionCriterion = new DemoConjunctionCriterion(searchFields);
      _currentAttrCriterion = (AttributeCriterion)(_conjunctionCriterion.getCriterionList().get(0));      
    }
    
    public void addCriterion(String name)
    {
      
    }
    
    public void changeMode(QueryDescriptor.QueryMode mode)
    {
      // Do nothing
    }

    public ConjunctionCriterion getConjunctionCriterion()
    {
      return _conjunctionCriterion;
    }

    public void setConjunctionCriterion(ConjunctionCriterion criterion)
    {
      _conjunctionCriterion = criterion;
    }

    public String getName()
    {
      return null;
    }

    public void removeCriterion(Criterion criterion)
    {
      
    }

    public AttributeCriterion getCurrentCriterion()
    {
      return _currentAttrCriterion;
    }

    public void setCurrentCriterion(AttributeCriterion attrCriterion)
    {
      _currentAttrCriterion = attrCriterion;
      _currentAttrCriterion.getValues().add(0, null);
    }

    public Map<String, Object> getUIHints()
    {
      return Collections.emptyMap();
    }

    private ConjunctionCriterion _conjunctionCriterion;
    private AttributeCriterion _currentAttrCriterion;
  }

  // A QueryModel is the model holding all available saved searches -- represented by QueryDescriptor.
  public class DemoQueryModel extends QueryModel
  {
    public DemoQueryModel()
    {
    }
    
    public List<AttributeDescriptor> getAttributes()
    {
      List<DemoPageDef.AttributeDef> attributeList = _pageDef.getAttributeDescriptors();
      List<AttributeDescriptor> attrDescList = new ArrayList<AttributeDescriptor>();
      for(DemoPageDef.AttributeDef demoAttrDesc : attributeList)
      {
        AttributeDescriptor attrDesc = new DemoAttributeDescriptor(demoAttrDesc);
        attrDescList.add(attrDesc);
      }
      return attrDescList;
    }

    //Create a QueryDescriptor.

    public QueryDescriptor create(String name, QueryDescriptor qdBase)
    {
      return null;      
    }

    public void delete(QueryDescriptor queryDescriptor)
    {
    }

    public void update(QueryDescriptor queryDescriptor, Map<String, Object> uiHints)
    {
      
    }

    /**
     * Resets the QueryDescriptor back to its original state. Removes all added criterion objects 
     * and resets the values of all the fields to its default. Also resets the conjunction to its 
     * default
     * 
     * @param queryDescriptor
     */
    public void reset(QueryDescriptor queryDescriptor)
    {
      
    }

    // "systemQueries" attribute EL reachable
    public List<QueryDescriptor> getSystemQueries()
    {
      return Collections.emptyList();
    }

    // "userQueries" attribute EL reachable
    public List<QueryDescriptor> getUserQueries()
    {
      return Collections.emptyList();
    }

    public void setCurrentDescriptor(QueryDescriptor qd)
    {
      _currentDescriptor = qd;
    }

    
  }

  private class DemoConjunctionCriterion extends ConjunctionCriterion
  {
    public DemoConjunctionCriterion(List<DemoPageDef.SearchFieldDef> searchFields)
    {
      List<DemoPageDef.SearchFieldDef> searchFieldDefList =  searchFields;
      _criterionList = new ArrayList<Criterion>();
      _criterionSearchFieldMap = new HashMap<Criterion, DemoPageDef.SearchFieldDef>();

      int index = 0;
      for (DemoPageDef.SearchFieldDef searchFieldDef : searchFieldDefList)
      {
        Criterion criterion = new DemoAttributeCriterion(searchFieldDef);
        _criterionList.add(criterion);
        _criterionSearchFieldMap.put(criterion, searchFieldDef);
        index++;
      }
      
    }    
    
    public DemoPageDef.SearchFieldDef getDemoSearchField(Object criterion)
    {
      if(criterion != null)
        return _criterionSearchFieldMap.get(criterion);
      
      return null;
    }    
    
    public ConjunctionCriterion getRoot()
    {
      return this;
    }
    
    public Object getKey(Criterion criterion)
    {
      for (int index = 0; index < _criterionList.size(); index++)
      {
        if (_criterionList.get(index).equals(criterion))
          return Integer.toString(index);
      }
      return null;
    }
    
    public Criterion getCriterion(Object key)
    {
      int index = -1;
      if (key != null && key instanceof Integer)
      {
        index = ((Integer)key).intValue();
      }
      else if (key instanceof String)
      {
        index = Integer.parseInt(ComponentUtils.resolveString(key));
      }
      List<Criterion> criterionList = getCriterionList();
      if(index > -1 && index < criterionList.size())
        return criterionList.get(index);
      
      return null;
    }

    public ConjunctionCriterion.Conjunction getConjunction()
    {
      return null;
    }

    public List<Criterion> getCriterionList()
    {
      return _criterionList;
    }

    public void setConjunction(ConjunctionCriterion.Conjunction conjunction)
    {
      
    }

    Map<Criterion, DemoPageDef.SearchFieldDef> _criterionSearchFieldMap;
    List<Criterion> _criterionList;
  }  
  
  public class DemoAttributeCriterion extends AttributeCriterion
  {
    public DemoAttributeCriterion(
      DemoPageDef.SearchFieldDef  searchFieldDef, 
      AttributeDescriptor         attrDesc)
    {
      _searchFieldDef = searchFieldDef;
      _attrDesc = attrDesc;
    }
    
    public DemoAttributeCriterion(DemoPageDef.SearchFieldDef searchFieldDef)
    {
      _searchFieldDef = searchFieldDef;
      _attrDesc = new DemoAttributeDescriptor(searchFieldDef.getAttributeDef());
    }
    
    public AttributeDescriptor getAttribute()
    {
      return _attrDesc;
    }

    public AttributeDescriptor.Operator getOperator()
    {
      return ((DemoAttributeDescriptor)_attrDesc).getOperator(_searchFieldDef.getOperator());
    }

    public Map<String, AttributeDescriptor.Operator> getOperators()
    {
      return ((DemoAttributeDescriptor)_attrDesc).getOperators();
    }

    public List<? extends Object> getValues()
    {
      return _searchFieldDef.getValues();
    }

    public void setValues(List<? extends Object> values)
    {
      int index = 0;
      for (Object value: values)
      {
        _searchFieldDef.getValues().set(index, value);
        index++;
      }
    }

    public boolean isRemovable()
    {
      return _searchFieldDef.isRemovable();
    }

    public void setOperator(AttributeDescriptor.Operator operator)
    {
      _searchFieldDef.setOperator(getDemoOperator(operator));
    }
    
    public boolean equals(Object obj)
    {
      if(obj != null && obj instanceof AttributeCriterion)
      {
        AttributeCriterion criterion = (AttributeCriterion) obj;
        AttributeDescriptor attrDesc = criterion.getAttribute();
        if(attrDesc != null && _attrDesc != null)
        {
          return (attrDesc.getName().equals(_attrDesc.getName()));
        }
      }
      return false;
    }
    
    public String toString()
    {
      if(_attrDesc != null)
      {
        return _attrDesc.getName();
      }
      
      return "";
    }
      
    private DemoPageDef.OperatorDef getDemoOperator(AttributeDescriptor.Operator selectedOper)
    {
      for(DemoPageDef.OperatorDef optr: DemoPageDef.OperatorDef.values())
      {
         if (selectedOper != null && selectedOper.getValue().equals(optr.getSymbol()))
         {
           return optr;
         }
       }
      return null;
    }
    
    private DemoPageDef.SearchFieldDef _searchFieldDef = null;
    private AttributeDescriptor _attrDesc = null;
  }

  public final class DemoAttributeDescriptor extends AttributeDescriptor
  {
    public DemoAttributeDescriptor(DemoPageDef.AttributeDef attrDef)
    {
      _attrDef = attrDef;
    }
    public String getDescription()
    {
      return _attrDef.getName();
    }

    public String getFormat()
    {
      return null;
    }

    public String getLabel()
    {
      return _attrDef.getLabel();
    }

    @Override
    public boolean equals(Object obj)
    {
      if (obj != null && obj instanceof AttributeDescriptor)
      {
        AttributeDescriptor attrDesc = (AttributeDescriptor) obj;
        
        return (attrDesc.getName().equals(_attrDef.getName()) && 
                attrDesc.getLabel().equals(_attrDef.getLabel()));
      }
      return false;
    }

    /**
     * Based on the component type of the attribute, it returns an appropriate model object expected 
     * of that component
     * @return
     */
    public Object getModel()
    {
      AttributeDescriptor.ComponentType compType = _attrDef.getComponentType();
      if (compType.equals(AttributeDescriptor.ComponentType.selectOneChoice) ||
          compType.equals(AttributeDescriptor.ComponentType.selectOneListbox)||
          compType.equals(AttributeDescriptor.ComponentType.selectOneRadio))
      {
        if (_attrDef.isLOV())
        {
          List<SelectItem> selectItems = new ArrayList<SelectItem>();
          for (int i = 0; i < _attrDef.getListOfValues().length; i++)
          {
            Object item = _attrDef.getListOfValues()[i];
            SelectItem selItem = new SelectItem();
            selItem.setLabel(item.toString());
            selItem.setValue(item);
            selectItems.add(selItem);
          }
          
          return selectItems;
        }
      }
      else if (compType.equals(AttributeDescriptor.ComponentType.inputListOfValues) || 
               compType.equals(AttributeDescriptor.ComponentType.inputComboboxListOfValues))
      {
        // TODO
        ListOfValuesModel lovModel = null;
        return lovModel;
      }
      
      return null;
    }

    public String getName()
    {
      return _attrDef.getName();
    }

    public Set<AttributeDescriptor.Operator> getSupportedOperators()
    {
      List<DemoPageDef.OperatorDef> operatorList = _attrDef.getSupportedOperators();
      Set<AttributeDescriptor.Operator> optrSet = new HashSet<AttributeDescriptor.Operator>();
      for(DemoPageDef.OperatorDef operator : operatorList)
      {
        AttributeDescriptor.Operator optr = new OperatorImpl(operator);
        optrSet.add(optr);
      }
      return optrSet;
    }

    public Map<String, AttributeDescriptor.Operator> getOperators()
    {
      List<DemoPageDef.OperatorDef> operatorList = _attrDef.getSupportedOperators();
      Map<String, AttributeDescriptor.Operator> optrMap = 
        new HashMap<String, AttributeDescriptor.Operator>();
      for(DemoPageDef.OperatorDef operator : operatorList)
      {
        AttributeDescriptor.Operator optr = new OperatorImpl(operator);
        optrMap.put(optr.getLabel(), optr);
      }
      return optrMap;
    }

    public Class getType()
    {
      return _attrDef.getType();
    }

    public AttributeDescriptor.ComponentType getComponentType()
    {
      return _attrDef.getComponentType();
    }

    public boolean isReadOnly()
    {
      return false;
    }

    public boolean isRequired()
    {
      return _attrDef.isMandatory();
    }

    public AttributeDescriptor.Operator getOperator(DemoPageDef.OperatorDef operator)
    {
      return new OperatorImpl(operator);
    }

    public int getLength()
    {
      return 0;
    }

    public int getMaximumLength()
    {
      return 0;
    }

    @Override
    public String getPlaceholder()
    {
      return _attrDef.getPlaceholder();
    }

    private class OperatorImpl extends AttributeDescriptor.Operator
    {
      public OperatorImpl(DemoPageDef.OperatorDef operator)
      {
        if(operator != null)
          _operator = operator;
        else 
          _operator = DemoPageDef.OperatorDef.NO_OPERATOR;
      }
      public String getLabel()
      {
        return _operator.getLabel();
      }

      public Object getValue()
      {
        return _operator.getSymbol();
      }

      public int getOperandCount()
      {
        return (_operator != null) ? _operator.getOperandCount() : 1;
      }
      
      @Override
      public boolean equals(Object operator)
      {
        if(operator != null)
        {
          return (_operator.getSymbol().equals(operator.toString()));
        }
        return false;
      }      

      @Override
      public String toString()
      {
        return _operator.getSymbol();
      }


      DemoPageDef.OperatorDef _operator = null;

    }

    private DemoPageDef.AttributeDef _attrDef;
  }

  private static DemoPageDef _pageDef;
  private QueryDescriptor _currentDescriptor = null;
  private QueryModel _queryModel = null;
  private String _sqlString = null;
}