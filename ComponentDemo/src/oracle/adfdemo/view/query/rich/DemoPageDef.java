/** Copyright (c) 2008, 2015, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.query.rich;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.adf.view.rich.model.AttributeDescriptor.ComponentType;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.QueryDescriptor;


/**
 * A simple bean that manages the various definition objects that are used on a page and
 * specifically by the query component.
 * For e.g.,
 * <ul>
 *   <li>the PageDef which is a container of AttributeDef objects and SavedSearchDef objects. This
 *   is based on the simple notion that a jspx page displays attributes (represented by
 *   AttributeDef) objects and a page can also define various search criteria to be used (
 *   represented as SavedSearchDef).
 *   </li>
 *   <li>
 *     AttributeDef represents the definition of a single attribute - type, label, format etc.
 *   </li>
 *   <li>
 *     SavedSearchDef represents the definition for a saved search and contains a list of
 *     SearchFieldDef objects, each instance refering to an AttributeDef.
 *   </li>
 *   <li>
 *     SearchFieldDef is a kind of AttributeDef but describing a search field, such as the default
 *     operator to use etc.
 *   </li>
 *  </ul>
 */
public class DemoPageDef
{
  public DemoPageDef()
  {
  }

  /**
   * Adds an attribute definition. This method should be called once for a page as it represents an
   * attribute defintion. Also is an AttributeDef with the same name is found, it will be deleted
   * and a new AttributeDef created.
   * @param name
   * @param label
   * @param dataType
   * @param componentType
   * @param isLOV
   * @param lovValues an Object[] of values based on its type. IOW, if type is String, it's a
   * String[], date type is a Date[], number type is a Number[]
   * @param isMandatory
   * @param isUpdateable
   * @return
   */
  public AttributeDef addAttributeDef(String name, String label,
                                      Class dataType,
                                      ComponentType componentType,
                                      boolean isLOV, Object[] lovValues,
                                      boolean isMandatory,
                                      boolean isUpdateable)
  {
    return addAttributeDef(name, label, null, dataType, componentType,
                           isLOV, lovValues, isMandatory, isUpdateable,
                           false,null);
  }

    /**
     * Adds an attribute definition. This method should be called once for a page as it represents an
     * attribute defintion. Also is an AttributeDef with the same name is found, it will be deleted
     * and a new AttributeDef created.
     * @param name
     * @param label
     * @param description
     * @param dataType
     * @param componentType
     * @param isLOV
     * @param lovValues an Object[] of values based on its type. IOW, if type is String, it's a
     * String[], date type is a Date[], number type is a Number[]
     * @param isMandatory
     * @param isUpdateable
     * @param isIndexed if it is an indexed field
     * @return
     */
    public AttributeDef addAttributeDef(String name, String label,
                                        String description, Class dataType,
                                        ComponentType componentType,
                                        boolean isLOV, Object[] lovValues,
                                        boolean isMandatory,
                                        boolean isUpdateable,
                                        boolean isIndexed)
    {
        return addAttributeDef(name, label, description, dataType, componentType,
                               isLOV, lovValues, isMandatory, isUpdateable,
                               false,null);
    }

  /**
   * Adds an attribute definition. This method should be called once for a page as it represents an
   * attribute defintion. Also is an AttributeDef with the same name is found, it will be deleted
   * and a new AttributeDef created.
   * @param name
   * @param label
   * @param description
   * @param dataType
   * @param componentType
   * @param isLOV
   * @param lovValues an Object[] of values based on its type. IOW, if type is String, it's a
   * String[], date type is a Date[], number type is a Number[]
   * @param isMandatory
   * @param isUpdateable
   * @param isIndexed if it is an indexed field
   * @param groupName
   * @return
   */
  public AttributeDef addAttributeDef(String name, String label,
                                      String description, Class dataType,
                                      ComponentType componentType,
                                      boolean isLOV, Object[] lovValues,
                                      boolean isMandatory,
                                      boolean isUpdateable,
                                      boolean isIndexed,
                                      String groupName)
  {
    return addAttributeDef(name, label, description, dataType, componentType,
                           isLOV, lovValues, isMandatory, isUpdateable,
                           isIndexed,groupName,null);
  }
  /**
   * Adds an attribute definition. This method should be called once for a page as it represents an
   * attribute defintion. Also is an AttributeDef with the same name is found, it will be deleted
   * and a new AttributeDef created.
   * @param name
   * @param label
   * @param description
   * @param dataType
   * @param componentType
   * @param isLOV
   * @param lovValues an Object[] of values based on its type. IOW, if type is String, it's a
   * String[], date type is a Date[], number type is a Number[]
   * @param isMandatory
   * @param isUpdateable
   * @param isIndexed if it is an indexed field
   * @param groupName
   * @param placeholder
   * @return
   */
  public AttributeDef addAttributeDef(String name, String label,
                                      String description, Class dataType,
                                      ComponentType componentType,
                                      boolean isLOV, Object[] lovValues,
                                      boolean isMandatory,
                                      boolean isUpdateable,
                                      boolean isIndexed,
                                      String groupName,
                                      String placeholder)
  {
    assert (name != null);
    assert (dataType != null && dataType instanceof java.lang.Class);
    AttributeDef attrDef = null;
    if (_attributes != null)
    {
      // add the attribute but first check to see if it already exists, 'coz we don't want two attrs
      // of the same name
      AttributeDef foundAttr = _attributes.get(name);
      if (foundAttr != null)
      {
        _attributes.remove(name);
      }
      attrDef =
          new AttributeDef(name, label, description, dataType, componentType,
                           isLOV, lovValues, isMandatory, isUpdateable,
                           isIndexed, groupName, placeholder);
      _attributes.put(name, attrDef);
    }
    else
    {
      _attributes = new HashMap<String, AttributeDef>();
      attrDef =
          new AttributeDef(name, label, description, dataType, componentType,
                           isLOV, lovValues, isMandatory, isUpdateable,
                           isIndexed,groupName, placeholder);
      _attributes.put(name, attrDef);
    }
    return attrDef;
  }

  /**
   * Creates and returns a new DemoSavedSearchDef object. A valid name is expected.
   * @param name
   * @param autoExecute
   * @param mode
   * @param saveResultsLayout
   * @param showInList
   * @param readOnly
   * @return
   */
  public SavedSearchDef addSavedSearchDef(String name, boolean autoExecute,
                                          QueryDescriptor.QueryMode mode,
                                          boolean saveResultsLayout,
                                          boolean showInList,
                                          boolean readOnly)
  {
    assert (name != null);
    return (new SavedSearchDef(name, autoExecute, mode, saveResultsLayout,
                               showInList, readOnly));
  }


  /**
   * Clones the savedSearchDef into a new definition. Adds all its search fields to the new one.
   * Its values become the clone's default values. Cloning a saved search always makes it editable.
   *
   * @param name
   * @param savedSearchDef
   * @return
   */
  public SavedSearchDef cloneSavedSearchDef(String name,
                                            SavedSearchDef savedSearchDef)
  {
    // TODO
    // Ensure the name of the new savedSearchDef is unique
    SavedSearchDef clone =
      new SavedSearchDef(name, savedSearchDef.isAutoExecute(),
                         savedSearchDef.getMode(),
                         savedSearchDef.isSaveResultsLayout(),
                         savedSearchDef.isShowInList(), false);
    for (SearchFieldDef fieldDef: savedSearchDef.getSearchFields())
    {
      // In the clone mark all fields as not removable.
      // The default value for the cloned search field is the entered value (of the first field)
      clone.addSearchFieldDef(fieldDef.getAttribute(),
                              fieldDef.getBeforeConjunction(),
                              fieldDef.getValues().get(0),
                              fieldDef.getOperator(),
                              fieldDef.isMultiSelectEnabled(), false,
                              fieldDef._hasDependentFields);
    }

    return clone;
  }

  /**
   * Gets the AttributeDescriptor object by its name.
   * @param attrName
   * @return
   */
  public AttributeDef getAttributeDescriptor(String attrName)
  {
    if (_attributes != null)
      return _attributes.get(attrName);

    return null;
  }

  /**
   * Gets the AttributeDescriptor objects by its name.
   * @return
   */
  public List<AttributeDef> getAttributeDescriptors()
  {
    if (_attributes != null)
    {
      List<AttributeDef> attrDescList =
        new ArrayList<AttributeDef>(_attributes.values());
      return attrDescList;
    }

    return Collections.emptyList();
  }

  public static Map<String, DemoPageDef.AttributeDef> getAttributes()
  {
    return _attributes;
  }

  /**
   * Sets up the default list of saved search definitions and returns the list. This method should
   * only be called once per test run.
   * @return
   */
  public List<SavedSearchDef> setupSavedSearchDefList()
  {
    DemoPageDef.SavedSearchDef savedSearchDef = null;
    List<SavedSearchDef> searchDefList = new ArrayList<SavedSearchDef>();

    // System Search 1
    savedSearchDef =
        addSavedSearchDef("System Search 1", false, QueryDescriptor.QueryMode.ADVANCED,
                          false, true, true);
    savedSearchDef._addSearchFieldDef("Ename",
                                      ConjunctionCriterion.Conjunction.AND,
                                      "Joe", null);
    savedSearchDef._addSearchFieldDef("Deptno",
                                      ConjunctionCriterion.Conjunction.OR,
                                      529, DemoPageDef.OperatorDef.EQUALS);
    savedSearchDef._addSearchFieldDef("Hiredate",
                                      ConjunctionCriterion.Conjunction.AND,
                                      _getDate(2007, 5, 8),
                                      DemoPageDef.OperatorDef.BEFORE);
    searchDefList.add(savedSearchDef);

    // System Search 2
    savedSearchDef =
        addSavedSearchDef("System Search 2", true, QueryDescriptor.QueryMode.BASIC,
                          false, true, true);
    savedSearchDef._addSearchFieldDef("Manager",
                                      ConjunctionCriterion.Conjunction.AND,
                                      "Melissa",
                                      DemoPageDef.OperatorDef.EQUALS);
    savedSearchDef._addSearchFieldDef("Salary",
                                      ConjunctionCriterion.Conjunction.OR,
                                      100000.45d,
                                      DemoPageDef.OperatorDef.GREATER_THAN);
    savedSearchDef._addSearchFieldDef("Empno",
                                      ConjunctionCriterion.Conjunction.AND,
                                      100, DemoPageDef.OperatorDef.EQUALS,
                                      new boolean[] { true, true });
    savedSearchDef._addSearchFieldDef("Permanent",
                                      ConjunctionCriterion.Conjunction.AND,
                                      false, null);
    searchDefList.add(savedSearchDef);

    // System Search 3
    savedSearchDef =
        addSavedSearchDef("System Search 3", true, QueryDescriptor.QueryMode.ADVANCED,
                          false, true, true);
    savedSearchDef._addSearchFieldDef("Empno",
                                      ConjunctionCriterion.Conjunction.AND,
                                      100,
                                      DemoPageDef.OperatorDef.LESS_THAN);
    savedSearchDef._addSearchFieldDef("Ename",
                                      ConjunctionCriterion.Conjunction.AND,
                                      "Adam",
                                      DemoPageDef.OperatorDef.EQUALS);
    savedSearchDef._addSearchFieldDef("Deptno",
                                      ConjunctionCriterion.Conjunction.AND,
                                      531, DemoPageDef.OperatorDef.EQUALS);
    savedSearchDef.addSearchFieldDef("Deptname",
                                      ConjunctionCriterion.Conjunction.AND,
                                      null, DemoPageDef.OperatorDef.IN, true,false,new boolean[] { false, false });
    searchDefList.add(savedSearchDef);
    
    // System Search 4
    savedSearchDef =
        addSavedSearchDef("System Search 4", true, QueryDescriptor.QueryMode.BASIC,
                          false, true, true);
    savedSearchDef._addSearchFieldDef("Manager",
                                      ConjunctionCriterion.Conjunction.AND,
                                      "Melissa",
                                      DemoPageDef.OperatorDef.EQUALS);
    savedSearchDef._addSearchFieldDef("Empno",
                                      ConjunctionCriterion.Conjunction.AND,
                                      100, DemoPageDef.OperatorDef.EQUALS);
    savedSearchDef._addSearchFieldDef("Show",
                                      ConjunctionCriterion.Conjunction.AND,
                                      "Show",  DemoPageDef.OperatorDef.EQUALS,
                                      new boolean[] { true , true });
    savedSearchDef._addSearchFieldDef("Salary",
                                      ConjunctionCriterion.Conjunction.OR,
                                      100000,
                                      DemoPageDef.OperatorDef.GREATER_THAN);

    searchDefList.add(savedSearchDef);
    
    //Empty Saved search 
    savedSearchDef = addSavedSearchDef("System Search 5", true, QueryDescriptor.QueryMode.BASIC,
                                       false, true, true);
    savedSearchDef._searchFields = new ArrayList<SearchFieldDef>();
    searchDefList.add(savedSearchDef);
    
    return searchDefList;
  }

  private Date _getDate(int year, int month, int date)
  {
    Calendar cal = Calendar.getInstance();
    cal.set(year, month, date);
    return cal.getTime();
  }

  /**
   * Adds attributes to the pageDef
   */
  public void setupAttributes()
  {
    addAttributeDef("Ename", "Employee Name",
                    "Full name of the Employee",
                    String.class, ComponentType.inputText, false, null,
                    true, true, true, "General", "Last Name, First Name");
    addAttributeDef("Empno", "Employee Number",
                    "Identification Number of the Employee", Integer.class,
                    ComponentType.inputNumberSpinbox, false, null, false,
                    true, true, "General", "e.g. 34248");
    addAttributeDef("Deptno", "Department Number", null, Integer.class,
                    ComponentType.inputNumberSpinbox, false, null, false,
                    true, true, "General");
    String[] deptNames = new String[]
      { "R&D", "Marketing", "Sales", "HR", "Support" };
    addAttributeDef("Deptname", "Department Name",null, String.class,
                    ComponentType.selectOneChoice, true, deptNames, false,
                    true, false,"General");
    addAttributeDef("Manager", "Manager", null, String.class,
                    ComponentType.inputText, false, null, false, true, false, "General");
    addAttributeDef("Hiredate", "Hire Date", null, Date.class,
                    ComponentType.inputDate, false, null, false, true,false, "Confidential", "mm/dd/yyyy");
    AttributeDef attrDef = addAttributeDef("Salary", "Salary of Employee",null, Double.class,
                    ComponentType.inputText, false, null, false,
                    true,false, "Confidential");
    attrDef.setFormat("###,000.00");

    addAttributeDef("Permanent", "Permanent", null, Boolean.class,
                    ComponentType.selectBooleanCheckbox, false, null,
                    false, true, false, "Confidential");
    addAttributeDef("Show", "Show Salary", null, String.class,
                    ComponentType.selectOneChoice, true, new String[]{"Show", "Hide"},
                    false, true, false, "Confidential");
    addAttributeDef("Country", "Department Country", String.class,
                    ComponentType.selectOneChoice, true, new String[]{"India", "US", "UK", "Japan", "China"},
                    false, true);
    addAttributeDef("DOB", "Date of Birth", Date.class,
                    ComponentType.inputDate, false, null, false, true);
  }

  /**
   * Represents the definition of a single attribute on a page - type, label, format etc.
   */
  public class AttributeDef
  {
    public AttributeDef(String name, String label, String description,
                          Class type, ComponentType componentType,
                          boolean isLOV, Object[] lovValues,
                          boolean isMandatory, boolean isUpdateable,
                          boolean isIndexed)
    {
        this(name,label,description,type,componentType,isLOV,lovValues,isMandatory,isUpdateable,isIndexed,null);
    }
    public AttributeDef(String name, String label, String description,
                        Class type, ComponentType componentType,
                        boolean isLOV, Object[] lovValues,
                        boolean isMandatory, boolean isUpdateable,
                        boolean isIndexed,
                        String groupName)
    {
      this(name,label,description,type,componentType,isLOV,lovValues,isMandatory,isUpdateable,isIndexed,groupName,null);
    }
    public AttributeDef(String name, String label, String description,
                        Class type, ComponentType componentType,
                        boolean isLOV, Object[] lovValues,
                        boolean isMandatory, boolean isUpdateable,
                        boolean isIndexed,
                        String groupName,
                        String placeholder)
    {
      super();
      this._name = name;
      this._label = label;
      this._description = description;
      this._type = type;
      this._isLOV = isLOV;
      if (isLOV && lovValues != null && lovValues.length > 0)
        _lovValues = lovValues;
      this._isMandatory = isMandatory;
      this._isUpdateable = isUpdateable;
      this._componentType = componentType;
      this._isIndexed = isIndexed;
      this._groupName = groupName;
      this._placeholder = placeholder;
    }

    public String getName()
    {
      return _name;
    }

    public String getLabel()
    {
      return _label;
    }

    public String getDescription()
    {
      return _description;
    }


    /**
     * Returns the list of supported operators for this attribute.
     * @return a List&lt;OperatorType&gt;
     */
    public final List<OperatorDef> getSupportedOperators()
    {
      //lets return no operator for checkbox "Permanant"
      String typeName = _type.getName();
      if (typeName.equals("java.lang.Number"))
        return _NUMBER_OPERATOR_LIST;
      else if (typeName.equals("java.util.Date"))
        return _DATE_OPERATOR_LIST;
      else if (typeName.equals("java.lang.Boolean"))
        return Collections.EMPTY_LIST;
      else
        return _STRING_OPERATOR_LIST;
    }

    public Class getType()
    {
      return _type;
    }

    public ComponentType getComponentType()
    {
      return _componentType;
    }

    public Object[] getListOfValues()
    {
      return _lovValues != null? _lovValues: null;
    }

    /**
     * By default returns a certain length.
     * @return
     */
    public int getLength()
    {
      if (_componentType.equals(ComponentType.inputDate) ||
          _componentType.equals(ComponentType.inputNumberSpinbox))
      {
        return 10;
      }
      else
        return 0;
    }

    public int getMaximumLength()
    {
      if (_componentType.equals(ComponentType.inputText))
        return 100;
      else
        return 0;
    }
    
    public String getFormat()
    {
      return _format;
    }    

    public boolean isLOV()
    {
      return _isLOV;
    }

    public boolean isIndexed()
    {
      return _isIndexed;
    }

    public boolean isMandatory()
    {
      return _isMandatory;
    }

    public boolean isUpdateable()
    {
      return _isUpdateable;
    }
    
    private void setFormat(String format)
    {
      _format = format;
    }    
    
    public String getGroupName() 
    {
        return _groupName;
    }

    public String getPlaceholder()
    {
      return _placeholder;
    }

    // the name of the attribute
    private String _name;
    // the label of the attribute
    private String _label;
    // the description of the attribute
    private String _description;
    // the type of the component to display the values of this attribute
    private ComponentType _componentType;
    // the type of the attribute
    private Class _type;
    // whether the attribute is a list of values
    private boolean _isLOV;
    
    // the format for the attribute to be used when converting values
    private String _format;

    // whether the attribute is a indexed field
    private boolean _isIndexed;
    // Whether the attribute is required. A true value means that null values for the attribute are
    // not allowed.
    private Object[] _lovValues;
    private boolean _isMandatory;
    // Whether the attribute is updateable. An updateable attribute allows its value to be changed.
    private boolean _isUpdateable;
    private String _groupName;
    private String _placeholder;
  }

  /**
   * Represents the definition for a saved search and contains a list of DemoSearchFieldDef objects,
   * each DemoSearchFieldDef being a kind of DemoAttributeDef.
   */
  public class SavedSearchDef
  {
    public SavedSearchDef(String name, boolean autoExecute,
                          QueryDescriptor.QueryMode mode,
                          boolean saveResultsLayout, boolean showInList,
                          boolean readOnly)
    {
      assert (name != null);
      _selectedConjunction = _defaultConjunction;
      _name = name;
      _uiHintAutoExecute = autoExecute;
      _uiHintMode =
          (mode != null)? mode: QueryDescriptor.QueryMode.ADVANCED;
      _uiHintSaveResultsLayout = saveResultsLayout;
      _uiHintShowInList = showInList;
      _uiHintResultsId = null;
      _uiHintDefault = false;
      _isReadOnly = readOnly;
    }


    /**
     * Adds a search field definition to the saved search def with defaults.
     * @param name the name of the attribute.
     * @param conjunction
     * @param defaultValue
     * @param defaultOperator the default OperatorType for this instance of the search field
     * criteria.
     */
    private void _addSearchFieldDef(String name,
                                    ConjunctionCriterion.Conjunction conjunction,
                                    Object defaultValue,
                                    OperatorDef defaultOperator)
    {
      boolean isMultiSelect = false;
      AttributeDef attrDef = DemoPageDef.getAttributes().get(name);

      // All search defs by default that of type LOV are multi-select enabled.
      /*if (attrDef != null && attrDef.isLOV())
        isMultiSelect = true;*/
      addSearchFieldDef(name, conjunction, defaultValue, defaultOperator,
                        isMultiSelect, false, new boolean[]
          { false, false });
    }


    /**
     * Adds a search field definition with dependent search fields
     * @param name
     * @param conjunction
     * @param defaultValue
     * @param defaultOperator
     * @param hasDependentFields
     */
    private void _addSearchFieldDef(String name,
                                    ConjunctionCriterion.Conjunction conjunction,
                                    Object defaultValue,
                                    OperatorDef defaultOperator,
                                    boolean[] hasDependentFields)
    {
      assert (hasDependentFields != null && hasDependentFields.length > 0);
      boolean isMultiSelect = false;
      AttributeDef attrDef = DemoPageDef.getAttributes().get(name);

      // All search defs by default that of type LOV are multi-select enabled.
      /*if (attrDef != null && attrDef.isLOV())
        isMultiSelect = true;*/
      addSearchFieldDef(name, conjunction, defaultValue, defaultOperator,
                        isMultiSelect, false, hasDependentFields);
    }


      /**
       * Adds a search field definition to the saved search def. Multiple instances of a search field
       * can exist for the same attribute. For e.g., a saved search could have search fields like
       * "Job = 'Foo'" and "Job <> 'Bar'". <br/>
       * There is also an order to the search fields that are part of the saved search criteria. And
       * the conjunction is applied between 2 search fields. For e.g, to represent a criteria like
       * "Job = 'Foo' OR Salary > '10000' AND Dept = 'Sales', the conjunction to use between Job and
       * Salary needs to be provided on the Salary search field def.
       * @param name the name of the attribute.
       * @param conjunction
       * @param defaultValue
       * @param defaultOperator the default OperatorType for this instance of the search field
       * criteria.
       * @param hasDependentFields a boolean array indicating whether the search field has dependent
       * criterion. Each index representing each operand (value part of the search field).
       */
      public void addSearchFieldDef(String name,
                                    ConjunctionCriterion.Conjunction conjunction,
                                    Object defaultValue,
                                    OperatorDef defaultOperator,
                                    boolean isMultiSelect,
                                    boolean isRemovable,
                                    boolean[] hasDependentFields)
      {
          if (_searchFields == null)
            _searchFields = new ArrayList<SearchFieldDef>();

          _searchFields.add(new SearchFieldDef(name, conjunction, defaultValue,
                                               defaultOperator, isMultiSelect,
                                               isRemovable,
                                               hasDependentFields));
      }


    /**
     * Adds a search field definition
     * @param searchFieldDef
     */
    public void addSearchFieldDef(DemoPageDef.SearchFieldDef searchFieldDef)
    {
      if (_searchFields == null)
        return;
    
       _searchFields.add(searchFieldDef);
    }

    /**
     * Removes a search field definition
     * @param searchFieldDef
     */
    public void removeSearchFieldDef(DemoPageDef.SearchFieldDef searchFieldDef)
    {
      if (_searchFields == null)
        return;

      _searchFields.remove(searchFieldDef);
    }


    /**
     * Returns the default conjunction to use between all search fields. If a default is not set,
     * this method loops through all its searchFieldDefs to see if they all use the same conjunction.
     * If they do we use that otherwise this method returns ConjunctionType.NONE.
     * @return
     */
    public ConjunctionCriterion.Conjunction getDefaultConjunction()
    {
      boolean hasSameConj = true;
      boolean bFirst = true;

      ConjunctionCriterion.Conjunction prevConj =
        ConjunctionCriterion.Conjunction.NONE;
      for (SearchFieldDef field: _searchFields)
      {
        ConjunctionCriterion.Conjunction currConj =
          field.getBeforeConjunction();
        if (!bFirst)
        {
          // If the conjunction differs between search fields return NONE
          if (!prevConj.equals(currConj))
          {
            hasSameConj = false;
            break;
          }
        }
        prevConj = currConj;
        bFirst = false;
      }

      if (!hasSameConj)
      {
        return ConjunctionCriterion.Conjunction.NONE;
      }
      else
      {
        return prevConj;
      }
    }

    public void setDefaultConjunction(ConjunctionCriterion.Conjunction conjunction)
    {
      _defaultConjunction = conjunction;
    }

    public void setName(String name)
    {
      _name = name;
    }

    public String getName()
    {
      return _name;
    }

    public String getResultsId()
    {
      return _uiHintResultsId;
    }

    public List<SearchFieldDef> getSearchFields()
    {
      if(_searchFields == null)
        _searchFields = new ArrayList<SearchFieldDef>();
      
      return _searchFields;
    }

    /**
     * Gets the conjunction selected by the user.
     * @return
     */
    public ConjunctionCriterion.Conjunction getSelectedConjunction()
    {
      return _selectedConjunction;
    }

    public boolean isReadOnly()
    {
      return _isReadOnly;
    }

    public boolean isAutoExecute()
    {
      return _uiHintAutoExecute;
    }

    public QueryDescriptor.QueryMode getMode()
    {
      return _uiHintMode;
    }

    public boolean isDefault()
    {
      return _uiHintDefault;
    }

    public boolean isShowInList()
    {
      return _uiHintShowInList;
    }

    public boolean isSaveResultsLayout()
    {
      return _uiHintSaveResultsLayout;
    }

    public void setAutoExecute(boolean autoExecute)
    {
      _uiHintAutoExecute = autoExecute;
    }

    /**
     * Determines if the definition is readOnly and hence cannot be changed.
     * @param readOnly
     */
    public void setReadOnly(boolean readOnly)
    {
      _isReadOnly = readOnly;
    }

    public void setShowInList(boolean showInList)
    {
      _uiHintShowInList = showInList;
    }

    public void setDefault(boolean isDefault)
    {
      _uiHintDefault = isDefault;
    }

    public void setResultsId(String resultsId)
    {
      _uiHintResultsId = resultsId;
    }

    public void setSaveResultsLayout(boolean saveLayout)
    {
      _uiHintSaveResultsLayout = saveLayout;
    }

    public void setMode(QueryDescriptor.QueryMode mode)
    {
      _uiHintMode = mode;
    }

    /**
     * Sets the conjunction selected by the user for this saved search. This overrides the search
     * field specific conjunction.
     * @param selectedConjunction
     */
    public void setSelectedConjunction(ConjunctionCriterion.Conjunction selectedConjunction)
    {
      this._selectedConjunction = selectedConjunction;
    }

    /**
     * Returns the SQL search criteria based on the search fields and the values entered.
     * @return
     */
    public String toString()
    {
      boolean ignoreConjunction = true;
      StringBuilder searchCriteria = new StringBuilder();
      for (SearchFieldDef field: _searchFields)
      {
        OperatorDef oper = field.getOperator();

        // If the operator is null then skip the criterion.
        if (oper == null)
          continue;

        if (!ignoreConjunction)
        {
          // if the conjuction was selected by user use that always. Otherwise use the default.
          ConjunctionCriterion.Conjunction useConj =
            (getSelectedConjunction() != null &&
             getSelectedConjunction() !=
             ConjunctionCriterion.Conjunction.NONE)?
            getSelectedConjunction(): field.getBeforeConjunction();
          searchCriteria.append(" ").append(useConj).append(" ");
        }

        searchCriteria.append(field.getAttribute());
        searchCriteria.append(" ").append(oper.getSymbol()).append(" ");

        int count = -1;
        for (Object value: field.getValues())
        {
          count++;
          if (oper.getOperandCount() > 0 || oper.getOperandCount() == -1)
          {
            if (count > 0)
            {
              if (oper.getOperandCount() > 1) // second value or higher
                searchCriteria.append(" - ");
              else
                break;
            }
            String dummyValue = (value == null)? "": value.toString();
            searchCriteria.append("'").append(dummyValue).append("'");
          }
        }

        ignoreConjunction = false;
      }
      return searchCriteria.toString();
    }

    // The default conjunction to use between all search fields. This is used in place of specific
    // conjunction if one is not available
    private ConjunctionCriterion.Conjunction _defaultConjunction;
    // The name of the saved search. This also happens to be the display name.
    private String _name;
    // Whether the saved search is readOnly. A true value implies that the definition cannot be
    // changed once its created.
    private boolean _isReadOnly;
    // A list of search fields associated to this SavedSearchDef
    private List<SearchFieldDef> _searchFields;
    // the conjunction selected by the user. This overrides the search field specific ones.
    private ConjunctionCriterion.Conjunction _selectedConjunction;
    // AutoExecute
    private boolean _uiHintAutoExecute;
    // Default
    private boolean _uiHintDefault;
    // ResultsId
    private String _uiHintResultsId;
    // Mode
    private QueryDescriptor.QueryMode _uiHintMode;
    // Show In List
    private boolean _uiHintShowInList;
    // Save Results Layout
    private boolean _uiHintSaveResultsLayout;

  }


  /**
   * DemoSearchFieldDef represents a search field that embellishes an attributeDef by providing
   * info such as the default operator to use for the attribute when it is used is a search
   * criteria etc.
   */
  public class SearchFieldDef
  {

    /**
     * Called from quickQuery bean
     * @param attrName
     * @param defaultValue
     */
    public SearchFieldDef(String attrName, Object defaultValue)
    {
      this(attrName, defaultValue, new boolean[]
          { false, false });
    }

    public SearchFieldDef(String attrName,
                          ConjunctionCriterion.Conjunction conjunction,
                          Object defaultValue, OperatorDef operator,
                          boolean isMultiSelect, boolean isRemovable)
    {
      this(attrName, conjunction, defaultValue, operator, isMultiSelect,
           isRemovable, new boolean[]
          { false, false });
    }

    public SearchFieldDef(String attrName, Object defaultValue,
                          boolean[] hasDependentFields)
    {
      _attrName = attrName;
      _defaultValue = defaultValue;
      _defaultOperator = null;
      _selectedOperator = _defaultOperator;      
      _values = new ArrayList<Object>();

      // We are harcoding the number of values to be 2 for now.
      _values.add(_defaultValue);
      _values.add(null);
      _hasDependentFields = hasDependentFields;
    }

    public SearchFieldDef(String attrName,
                          ConjunctionCriterion.Conjunction conjunction,
                          Object defaultValue, OperatorDef operator,
                          boolean isMultiSelect, boolean isRemovable,
                          boolean[] hasDependentFields)
    {
      _attrName = attrName;
      // defaults to AND if NONE or null is speclified
      _beforeConjunction =
          (conjunction == null || conjunction == ConjunctionCriterion.Conjunction.NONE)?
          ConjunctionCriterion.Conjunction.AND: conjunction;
      _defaultValue = defaultValue;

      // value starts out being the selectedValue
      _isRemovable = isRemovable;
      _isMultiSelect = isMultiSelect;
      // whether search field has denedent fields
      _hasDependentFields = hasDependentFields;

      // set the default operator after checking against the supported list
      List<OperatorDef> operList = getOperators();
      if (operList.contains(operator))
        _defaultOperator = operator;

      _selectedOperator = _defaultOperator;
      _values = new ArrayList<Object>();

      // We are harcoding the number of values to be 2 for now.
      _values.add(_defaultValue);
      _values.add(null);
    }

    public AttributeDef getAttributeDef()
    {
      return DemoPageDef.this.getAttributeDescriptor(_attrName);
    }

    public boolean equals(String attrName)
    {
      if (attrName != null)
        return (this.getAttribute().equals(attrName));

      return false;
    }

    /**
     * Gets the name of the attribute
     * @return
     */
    public String getAttribute()
    {
      return _attrName;
    }

    /**
     * Gets the conjunction to use before this search field.
     * @return
     */
    public ConjunctionCriterion.Conjunction getBeforeConjunction()
    {
      return _beforeConjunction;
    }

    /**
     * Gets the selected (if changed by user) or the default operator to use with this search field
     * @return
     */
    public DemoPageDef.OperatorDef getOperator()
    {
      return _selectedOperator;
    }

    /**
     * Returns the list of operators for this search field. Basically it loops through the supported
     * list of operators for the attribute and replace Equals and Not Equals with In and Not In, for
     * LOV attribute types
     * @return a List&lt;OperatorDef&gt;
     */
    public final List<OperatorDef> getOperators()
    {
      List<OperatorDef> operList = getAttributeDef().getSupportedOperators();
      
      if (getAttributeDef().isLOV() && isMultiSelectEnabled())
      {
        List<OperatorDef> updatedOperList = new ArrayList<OperatorDef>();
        for (DemoPageDef.OperatorDef operator: operList)
        {
          if (operator.equals(OperatorDef.EQUALS))
            updatedOperList.add(OperatorDef.IN);
          else if (operator.equals(OperatorDef.NOT_EQUALS))
            updatedOperList.add(OperatorDef.NOT_IN);
          else
            updatedOperList.add(operator);
        }

        return updatedOperList;
      }

      return operList;
    }

    public void setOperator(DemoPageDef.OperatorDef operator)
    {
      this._selectedOperator = operator;
    }

    /**
     * Gets the default value. it could be a String literal or a Number
     * @return
     */
    public Object getDefaultValue()
    {
      return _defaultValue;
    }

    public OperatorDef getDefaultOperator()
    {
      return _defaultOperator;
    }

    public boolean isRemovable()
    {
      return _isRemovable;
    }

    /**
     * Gets the last saved values.
     * @return
     */
    public List<Object> getValues()
    {
      return _values;
    }

    /**
     * Resets the value of the search field to its default value.
     */
    public void resetValue()
    {
      _values.set(0, _defaultValue);
      _values.set(1, null);
    }

    public void setRemovable(boolean removable)
    {
      _isRemovable = removable;
    }

    /**
     * Returns true, if this criterion has dependent field for given value map index.
     * @param index the value map index of the search value field
     * @return boolean
     */
    public boolean hasDependentField(int index)
    {
      return _hasDependentFields[index];
    }

    public boolean isMultiSelectEnabled()
    {
      return _isMultiSelect;
    }

    // the name of the attribute that this search field references
    private String _attrName;
    // The conjunction to use before this search field.
    private ConjunctionCriterion.Conjunction _beforeConjunction;
    // default operator to use with this search field
    private OperatorDef _defaultOperator;

    // if the search field has dependent fields
    private boolean[] _hasDependentFields;

    private OperatorDef _selectedOperator;
    // the default value. it could be a String literal or a Number
    private Object _defaultValue;
    // the entered values. it could be a String literal or a Number
    private List<Object> _values;

    private boolean _isRemovable = false;
    private boolean _isMultiSelect = false;

  }

  /**
   * List of all available operators.
   */
  public enum OperatorDef
  {
    NO_OPERATOR(" ", ""),
    EQUALS("Equals", "="),
    GREATER_THAN("Greater Than", ">"),
    GREATER_THAN_EQUALS("Greater Than Equals", ">="),
    LESS_THAN("Less Than", "<"),
    LESS_THAN_EQUALS("Less Than Equals", "<="),
    NOT_EQUALS("Not Equals", "<>"),
    LIKE("Like", "LIKE"),
    CONTAINS("Contains", "CONTAINS"),
    DOES_NOT_CONTAIN("Does not Contain", "DOESNOTCONTAIN"),
    STARTS_WITH("Starts With", "STARTSWITH"),
    ENDS_WITH("Ends With", "ENDSWITH"),
    IS_BLANK("Is Blank", "ISBLANK"),
    IS_NOT_BLANK("Is not Blank", "ISNOTBLANK"),
    BETWEEN("Between", "BETWEEN"),
    NOT_BETWEEN("Not Between", "NOTBETWEEN"),
    EXISTS("Exists", "EXISTS"),
    IN("In", "IN"),
    NOT_IN("Not In", "NOT IN"),
    BEFORE("Before", "BEFORE"),
    AFTER("After", "AFTER"),
    ON_OR_BEFORE("On or Before", "ONORBEFORE"),
    ON_OR_AFTER("On or After", "ONORAFTER");

    OperatorDef(String label, String operator)
    {
      _symbol = operator;
      _label = label;
    }

    public String getSymbol()
    {
      return _symbol;
    }

    public String getLabel()
    {
      return _label;
    }

    /**
     * Returns the number of operands required by an OperatorType instance. This may be useful in
     * determining the number of input components to display for the operator and attribute.
     * @return an int
     */
    public int getOperandCount()
    {
      switch (this)
      {
        case IS_BLANK:
        case IS_NOT_BLANK:
          return 0;
        case BETWEEN:
        case NOT_BETWEEN:
          return 2;
        case IN:
        case NOT_IN:
          // Indicating operator supports unlimited number of operands
          return -1;
        default:
          return 1;
      }
    }

    public boolean hasVariableOperands()
    {
      return (getOperandCount() == -1);
    }
    
    private String _symbol;
    private String _label;
  }

  // Operators for the Number dataType
  private static List<OperatorDef> _NUMBER_OPERATOR_LIST =
    new ArrayList<OperatorDef>();
  static {
    _NUMBER_OPERATOR_LIST.add(DemoPageDef.OperatorDef.EQUALS);
    _NUMBER_OPERATOR_LIST.add(DemoPageDef.OperatorDef.NOT_EQUALS);
    _NUMBER_OPERATOR_LIST.add(DemoPageDef.OperatorDef.GREATER_THAN);
    _NUMBER_OPERATOR_LIST.add(DemoPageDef.OperatorDef.LESS_THAN);
    _NUMBER_OPERATOR_LIST.add(DemoPageDef.OperatorDef.GREATER_THAN_EQUALS);
    _NUMBER_OPERATOR_LIST.add(DemoPageDef.OperatorDef.LESS_THAN_EQUALS);
    _NUMBER_OPERATOR_LIST.add(DemoPageDef.OperatorDef.BETWEEN);
    _NUMBER_OPERATOR_LIST.add(DemoPageDef.OperatorDef.NOT_BETWEEN);
  }

  // Operators for the Date dataType
  private static List<OperatorDef> _DATE_OPERATOR_LIST =
    new ArrayList<OperatorDef>();
  static {
    _DATE_OPERATOR_LIST.add(DemoPageDef.OperatorDef.BETWEEN);
    _DATE_OPERATOR_LIST.add(DemoPageDef.OperatorDef.NOT_BETWEEN);
    _DATE_OPERATOR_LIST.add(DemoPageDef.OperatorDef.EQUALS);
    _DATE_OPERATOR_LIST.add(DemoPageDef.OperatorDef.NOT_EQUALS);
    _DATE_OPERATOR_LIST.add(DemoPageDef.OperatorDef.AFTER);
    _DATE_OPERATOR_LIST.add(DemoPageDef.OperatorDef.BEFORE);
    _DATE_OPERATOR_LIST.add(DemoPageDef.OperatorDef.ON_OR_AFTER);
    _DATE_OPERATOR_LIST.add(DemoPageDef.OperatorDef.ON_OR_BEFORE);
  }

  // Operators for the String dataType
  private static List<OperatorDef> _STRING_OPERATOR_LIST =
    new ArrayList<OperatorDef>();
  static {
    _STRING_OPERATOR_LIST.add(DemoPageDef.OperatorDef.EQUALS);
    _STRING_OPERATOR_LIST.add(DemoPageDef.OperatorDef.NOT_EQUALS);
    _STRING_OPERATOR_LIST.add(DemoPageDef.OperatorDef.IS_BLANK);
    _STRING_OPERATOR_LIST.add(DemoPageDef.OperatorDef.IS_NOT_BLANK);
    _STRING_OPERATOR_LIST.add(DemoPageDef.OperatorDef.STARTS_WITH);
    _STRING_OPERATOR_LIST.add(DemoPageDef.OperatorDef.ENDS_WITH);
    _STRING_OPERATOR_LIST.add(DemoPageDef.OperatorDef.CONTAINS);
    _STRING_OPERATOR_LIST.add(DemoPageDef.OperatorDef.DOES_NOT_CONTAIN);
    _STRING_OPERATOR_LIST.add(DemoPageDef.OperatorDef.LIKE);
  }

  // Operators for the Boolean dataType
  private static List<OperatorDef> _BOOLEAN_OPERATOR_LIST =
    new ArrayList<OperatorDef>();
  static {
    _BOOLEAN_OPERATOR_LIST.add(DemoPageDef.OperatorDef.EQUALS);
    _BOOLEAN_OPERATOR_LIST.add(DemoPageDef.OperatorDef.NOT_EQUALS);
  }

  // Map of attributes for the page
  private static Map<String, AttributeDef> _attributes;

}
