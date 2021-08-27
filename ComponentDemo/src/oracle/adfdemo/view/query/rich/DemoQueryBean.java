/** Copyright (c) 2008, 2018, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.query.rich;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.NumberConverter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.RichQuery;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.event.QueryOperationEvent;
import oracle.adf.view.rich.event.QueryOperationListener;
import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.AttributeDescriptor.ComponentType;
import oracle.adf.view.rich.model.AutoSuggestBehaviorConfig;
import oracle.adf.view.rich.model.AutoSuggestUIHints;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;
import oracle.adf.view.rich.model.Descriptor;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;
import oracle.adf.view.rich.model.GroupAttributeDescriptor;
import oracle.adf.view.rich.model.ListOfValuesModel;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;

import org.apache.myfaces.trinidad.util.ComponentUtils;


/**
 * A simple bean implementation for the query and quickQuery components.
 */
public class DemoQueryBean
{
  public DemoQueryBean()
  {
    // Setup DemoPageDef containing a list of attribute, list of saved searches and list of search 
    // field definitions.
    _createPageDef();
 
  }

  /**
   * Creates a DemoPageDef object, containing a list of attributes, saved searches and each saved 
   * search definintion object containing a list of search field definitions.
   */
  private void _createPageDef()
  {
    _pageDef = new DemoPageDef();
    _pageDef.setupAttributes();
    
    List<DemoPageDef.SavedSearchDef> searchDefList = _pageDef.setupSavedSearchDefList();
    
    // Mark the first sabved Search definition as the current
    _currentDescriptor = new DemoQueryDescriptor(searchDefList.get(0));
    for (DemoPageDef.SavedSearchDef searchDef: searchDefList)
    {
      DemoQueryDescriptor qd = new DemoQueryDescriptor(searchDef);
      _QDMap.put(searchDef.getName(), qd); 
      if (_currentDescriptor == null)
        _currentDescriptor = qd;
    }

    _queryModel = new DemoQueryModel(_QDMap);
    
  }
  
  //==================== EL reachable APIs ===============================//
  // "value" attribute EL reachable
  public QueryDescriptor getQueryDescriptor()
  {
    return _currentDescriptor;
  }
  // "queryOperationListener" attribute EL reachable
  public QueryOperationListener getQueryOperationListener()
  {
    return new DemoQueryOperationListener();
  }
  
  // "model" attribute EL reachable
  public QueryModel getQueryModel()
  {
    return _queryModel;
  }

  public DemoPageDef getPageDef()
  {
    return _pageDef;
  }

  public void addSearchField(ActionEvent event)
  {
    UIComponent src = event.getComponent();

    String newValue = (String) src.getAttributes().get("selectedText");
    if (newValue != null)
    {
      _currentDescriptor.addCriterion(newValue);
      
    }      
  }

  public void processQuery(QueryEvent event)
  {
    DemoQueryDescriptor descriptor = (DemoQueryDescriptor) event.getDescriptor();
    String sqlString = descriptor.getSavedSearchDef().toString();
    setSqlString(sqlString);
  }
  
  public void setSqlString(String sqlString)
  {
    _sqlString = sqlString;
  }

  public String getSqlString()
  {
    return _sqlString;
  }
  /**
   * Returns true if there exists at least one criterion in the criterion list, that is 'indexed'.
   * 
   * @return true if single indexed criterion exist else false
   */
  private boolean _isSingleIndexedCriterion()
  {
    QueryDescriptor qd = getQueryDescriptor();
    List<Criterion> criterionList = qd.getConjunctionCriterion().getCriterionList();
    int indexedFieldCount = 0;
    for(Criterion criterion: criterionList)
    {
      AttributeCriterion attrCtr = (AttributeCriterion)criterion;
      AttributeDescriptor attrDesc =  attrCtr.getAttribute();
      if(attrDesc.isIndexed())
      {
        indexedFieldCount++;
        if(indexedFieldCount > 1)
          return false;
      }
    }
    return (indexedFieldCount == 1);
  }
  
  public class DemoQueryDescriptor extends FilterableQueryDescriptor
  {
    
    public DemoQueryDescriptor (DemoPageDef.SavedSearchDef savedSearchDef)
    {
      _savedSearchDef = savedSearchDef;
      _conjunctionCriterion = new DemoConjunctionCriterion(savedSearchDef);
      _hiddenCriterions = new ArrayList<Criterion>();
      _uiHintMap = new AbstractMap()
      {
        public Object get(Object key)
        {
          if (key.equals(UIHINT_AUTO_EXECUTE))
            return _savedSearchDef.isAutoExecute();
          else if (key.equals(UIHINT_MODE))
            return _savedSearchDef.getMode();
          else if (key.equals(UIHINT_NAME))
            return _savedSearchDef.getName();
          else if (key.equals(UIHINT_SAVE_RESULTS_LAYOUT))
            return _savedSearchDef.isSaveResultsLayout();
          else if (key.equals(UIHINT_SHOW_IN_LIST))
            return _savedSearchDef.isShowInList();
          else if (key.equals(UIHINT_IMMUTABLE))
            return _savedSearchDef.isReadOnly();
          else if (key.equals(UIHINT_RESULTS_COMPONENT_ID))
            return _savedSearchDef.getResultsId();
          else if (key.equals(UIHINT_DEFAULT))
            return _savedSearchDef.isDefault();
          else 
            return null;
        }
      
        public Object put(Object key, Object value)
        {
          if (value == null && containsKey(key))
            return get(key);
        
          if (key.equals(UIHINT_NAME))
          {
            _savedSearchDef.setName((String) value);
            return _savedSearchDef.getName();
          }
          else if (key.equals(UIHINT_MODE) && value instanceof QueryDescriptor.QueryMode)
          {
            _savedSearchDef.setMode((QueryDescriptor.QueryMode)value);
            return _savedSearchDef.getMode();
          }
          else if (key.equals(UIHINT_RESULTS_COMPONENT_ID))
          {
            _savedSearchDef.setResultsId((String) value);
            return _savedSearchDef.getResultsId();
          }
          else
          {
            // Rest are boolean entries
            boolean b = (value instanceof String) ? Boolean.parseBoolean((String) value) :
                                                    Boolean.TRUE.equals(value);
            if (key.equals(UIHINT_AUTO_EXECUTE))
            {
              _savedSearchDef.setAutoExecute(b);
              return _savedSearchDef.isAutoExecute();
            }
            else if (key.equals(UIHINT_SAVE_RESULTS_LAYOUT))
            {
              _savedSearchDef.setSaveResultsLayout(b);
              return _savedSearchDef.isSaveResultsLayout();
            }
            else if (key.equals(UIHINT_SHOW_IN_LIST))
            {
              _savedSearchDef.setShowInList(b);
              return _savedSearchDef.isShowInList();
            }
            else if (key.equals(UIHINT_IMMUTABLE))
            {
              _savedSearchDef.setReadOnly(b);
              return _savedSearchDef.isReadOnly();
            }
            else if (key.equals(UIHINT_DEFAULT))
            {
              _savedSearchDef.setDefault(b);
              return _savedSearchDef.isDefault();
            }            
          }
          
          return null;
        }

        public Set entrySet()
        {
          return Collections.EMPTY_SET;
        }
        
      };
      
    }
    
    public void addCriterion(String name)
    {
      // searchField is marked as removable when user adds new criterion. But once a QueryDescriptor 
      // is saved (Save.../ or QOP.CREATE), the added Criterion(s) are removed from the original 
      // QueryDescriptor and in the new QueryDescriptor, the added Criterions can no longer be 
      // removed, as saving it makes it part of a new savedSearch definition.
      
      // by default, added criterion is marked removable, multiSelect is false and has no dependent 
      // criterion
      _savedSearchDef.addSearchFieldDef(name, null, null, null, 
                                        false /* isMultiSelect*/, 
                                        true /* isRemovable*/, 
                                        new boolean[]{false, false});
      _conjunctionCriterion = new DemoConjunctionCriterion(_savedSearchDef);
    }
    
    public void changeMode(QueryDescriptor.QueryMode mode)
    {
      // Do nothing
      getUIHints().put(QueryDescriptor.UIHINT_MODE, mode);
    }

    public DemoPageDef.SavedSearchDef getSavedSearchDef()
    {
      return _savedSearchDef;
    }
    
    public boolean isAdvancedMode()
    {
      return (_savedSearchDef.getMode() == QueryDescriptor.QueryMode.ADVANCED);
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
      return _savedSearchDef.getName();
    }

    public void removeCriterion(Criterion criterion)
    {
      if (criterion!= null && criterion  instanceof AttributeCriterion)
      {
        DemoAttributeCriterion attrCriterion = (DemoAttributeCriterion) criterion;
        DemoConjunctionCriterion demoConj= (DemoConjunctionCriterion)_conjunctionCriterion;
        demoConj.removeCriterion(attrCriterion);
      }
      else
      {
        throw new UnsupportedOperationException();
      }
    }

    @Override
    public void removeCriteria(List<Criterion> criteria)
    {
      for(Criterion criterion : criteria)
      {
        removeCriterion(criterion);
      }
    }
    
    public Map<String, Object> getUIHints()
    {
      return _uiHintMap;
    }

    public AttributeCriterion getCurrentCriterion()
    {
      return null;
    }

    public void setCurrentCriterion(AttributeCriterion attrCriterion)
    {
    }

    @Override
    public Map<String, Object> getFilterCriteria()
    {
      return _filterCriteria;
    }

    @Override
    public void setFilterCriteria(Map<String, Object> filterCriteria)
    {
      _filterCriteria = filterCriteria;
    }
    
    public void addHiddenCriterion(DemoAttributeCriterion criterion)
    {
      _hiddenCriterions.add(criterion);
    }

    public void removeHiddenCriterion(DemoAttributeCriterion criterion)
    {
      _hiddenCriterions.remove(criterion);
    }

    Map<String, Object> _filterCriteria;
    private DemoPageDef.SavedSearchDef _savedSearchDef;
    private Map<String, Object> _uiHintMap;
    private ConjunctionCriterion _conjunctionCriterion;
    //List for hidden criterion so that they can be added back unchanged.
    private List<Criterion> _hiddenCriterions;
  }

  // A QueryModel is the model holding all available saved searches -- represented by QueryDescriptor.
  public class DemoQueryModel extends QueryModel
  {
    public DemoQueryModel(Map<String, QueryDescriptor> QDs)
    {
      _qdSet = QDs;
       
      //Create the GroupAttributeDescriptor and AttributeDescriptor tree here.
      Map<String, GroupAttributeDescriptor> attribGroupMap = new LinkedHashMap<String, GroupAttributeDescriptor>();
      List<DemoPageDef.AttributeDef> attributeList = _pageDef.getAttributeDescriptors();
      for(DemoPageDef.AttributeDef demoAttrDesc : attributeList)
      {
        String groupName = demoAttrDesc.getGroupName();
        if(groupName == null || groupName.length() == 0)
        {
          AttributeDescriptor attrDesc = new DemoAttributeDescriptor(demoAttrDesc);
          _attributes.add(attrDesc);
        }
        else
        {
          GroupAttributeDescriptor groupAttrib = attribGroupMap.get(groupName);
          if(groupAttrib == null)
          {
            groupAttrib = new DemoGroupAttributeDescriptor(groupName, groupName, null);
            attribGroupMap.put(groupName, groupAttrib);
            _attributeGroups.add(groupAttrib);
            _attributes.add(groupAttrib);
          }
          AttributeDescriptor attrDesc = new DemoAttributeDescriptor(demoAttrDesc, groupName);
          ((DemoGroupAttributeDescriptor)groupAttrib).addAttribute(attrDesc);
        }
      }
       
    }
    
    @Override
    public List<AttributeDescriptor> getAttributes()
    {
      return Collections.emptyList();
    }
    
    @Override
    public List<Descriptor> getDescriptors()
    {
      return _attributes;
    }
    
    public List<GroupAttributeDescriptor> getCriteriaGroups()
    {
      return _attributeGroups;
    }


    //Create a QueryDescriptor.
    public QueryDescriptor create(String name, QueryDescriptor qdBase)
    {
      if (name == null)
        return null;

      // If the queryDescriptor already exists, then just return that
      if (_qdSet.get(name) != null)
      {
        return _qdSet.get(name);
      }
      
      // We need a create a new QueryDescriptor based on the existing one
      if (qdBase != null)
      {
        DemoQueryDescriptor qdImpl = (DemoQueryDescriptor)qdBase;
        // 1. Clone the saved search Def
        DemoPageDef.SavedSearchDef savedSearchDef = qdImpl.getSavedSearchDef();
        DemoPageDef.SavedSearchDef newSavedSearchDef = _pageDef.cloneSavedSearchDef(
                                                             name, 
                                                             savedSearchDef);
        DemoQueryDescriptor newQueryDesc = new DemoQueryDescriptor(newSavedSearchDef);

        // 2: Mark this newly created QD as a user saved search
        newQueryDesc.getUIHints().put(QueryDescriptor.UIHINT_IMMUTABLE, Boolean.FALSE);
        _QDMap.put(name, newQueryDesc);
  
        // 3: Mark all criterion in the new QueryDescriptor as immutable
        for(DemoPageDef.SearchFieldDef fieldDef : newSavedSearchDef.getSearchFields())
        {
          if (fieldDef.isRemovable())
          {
            fieldDef.setRemovable(false);
          }
        }
        
        // 4. Reset the base queryDescriptor. Also remove any criterion that was added by the user
        reset(qdBase);
  
        return newQueryDesc;
        
      }
      return null;
      
    }

    public void delete(QueryDescriptor queryDescriptor)
    {
      // we can't delete systemQueries
      if(null != queryDescriptor)
      {
        Object immutable = queryDescriptor.getUIHints().get(QueryDescriptor.UIHINT_IMMUTABLE);
        if (immutable == null || immutable.equals(Boolean.TRUE))
        {
          return;
        }
      
        // Remove the QueryDescriptor from the QueryModel at this point.
        _qdSet.remove(queryDescriptor.getName());
      }
    }

    public void update(QueryDescriptor queryDescriptor, Map<String, Object> uiHints)
    {
      // Update the UIHints. If the name changes the update the _qdSet 
      if (uiHints != null && !uiHints.isEmpty())
      {
        // If the current name of the QD changes, delete the old entry from the _qdSet and add a 
        // new entry. 
        String newName = (String) uiHints.get(QueryDescriptor.UIHINT_NAME);
        String currentName = queryDescriptor.getName();
        
        if (currentName != null && 
           (newName != null && newName.trim() != "") && 
           !currentName.equals(newName))
        {
          getQueries().remove(currentName);
          queryDescriptor.getUIHints().putAll(uiHints);
          getQueries().put(queryDescriptor.getName(), queryDescriptor);
        }
        else
        {
          queryDescriptor.getUIHints().putAll(uiHints);
        }
      }
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
      DemoPageDef.SavedSearchDef savedSearchDef = 
        ((DemoQueryDescriptor)queryDescriptor).getSavedSearchDef();
      List<DemoPageDef.SearchFieldDef> origSearchFieldList = savedSearchDef.getSearchFields();
      List<DemoPageDef.SearchFieldDef> removableFields = 
        new ArrayList<DemoPageDef.SearchFieldDef>();
      for(DemoPageDef.SearchFieldDef searchField : savedSearchDef.getSearchFields())
      {
        if (searchField.isRemovable())
        {
          removableFields.add(searchField);
        }
        else
        {
          searchField.resetValue();
          searchField.setOperator(searchField.getDefaultOperator()); 
        }
      }
      origSearchFieldList.removeAll(removableFields);
      
      // Conjunction
      savedSearchDef.setSelectedConjunction(savedSearchDef.getDefaultConjunction());
      DemoQueryDescriptor demoDescriptor = (DemoQueryDescriptor)queryDescriptor;
      demoDescriptor.setConjunctionCriterion(new DemoConjunctionCriterion(savedSearchDef));
    }

    public Map<String, QueryDescriptor> getQueries()
    {
      return _qdSet;
    }

    // "systemQueries" attribute EL reachable
    public List<QueryDescriptor> getSystemQueries()
    {
      List<QueryDescriptor> queries = new ArrayList<QueryDescriptor>();
      Map<String, QueryDescriptor> Queries = ((DemoQueryModel) _queryModel).getQueries();
      Iterator keys = Queries.keySet().iterator();
      while (keys != null && keys.hasNext())
      {
        Object key = keys.next();
        QueryDescriptor qd = Queries.get(key);
        Object queryType = qd.getUIHints().get(QueryDescriptor.UIHINT_IMMUTABLE);

        // For SystemQueries, immutable is null or true
        if (queryType == null || queryType.equals(Boolean.TRUE))
        {
          queries.add(qd);
        }
      }

      return queries;
    }

    // "userQueries" attribute EL reachable
    public List<QueryDescriptor> getUserQueries()
    {
      List<QueryDescriptor> queries = new ArrayList<QueryDescriptor>();
      Map<String, QueryDescriptor> Queries = ((DemoQueryModel) _queryModel).getQueries();
      Iterator keys = Queries.keySet().iterator();
      while (keys != null && keys.hasNext())
      {
        Object key = keys.next();
        QueryDescriptor qd = Queries.get(key);
        Object queryType = qd.getUIHints().get(QueryDescriptor.UIHINT_IMMUTABLE);

        // For userQueries, immutable is false
        if (queryType != null && queryType.equals(Boolean.FALSE))
        {
          queries.add(qd);
        }
      }

      return queries;
    }

    public void setCurrentDescriptor(QueryDescriptor qd)
    {
      if (qd != null)
        _currentDescriptor = qd;
    }

    Map<String, QueryDescriptor> _qdSet;
    List<Descriptor> _attributes = new ArrayList<Descriptor>();
    List<GroupAttributeDescriptor> _attributeGroups = new ArrayList<GroupAttributeDescriptor>();
  }

  private class DemoConjunctionCriterion extends ConjunctionCriterion
  {
    public DemoConjunctionCriterion(DemoPageDef.SavedSearchDef savedSearchDef)
    {
      List<DemoPageDef.SearchFieldDef> searchFieldDefList =  savedSearchDef.getSearchFields();
      _savedSearchDef = savedSearchDef;
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
          return new Integer(index);// Return an Object 
      }
      return null;
    }
    
    public Criterion getCriterion(Object key)
    {
      int index = -1;

      // Always expect Integer object from framework as Integer is passed in getKey() method
      if (key != null && key instanceof Integer)
      {
        index = ((Integer)key).intValue();
      }

      if(index > -1 && index < _criterionList.size())
        return _criterionList.get(index);
      
      return null;
    }

    // Utility method for searching a criterion by it 'Name'. This only returns criterion which
    // are not added by Add Fields button.
    public Criterion getCriterionByName(String name)
    {
      for (Criterion criterion : _criterionList)
      {
        DemoAttributeCriterion attr = (DemoAttributeCriterion)criterion;
        if(name.equals(attr.getAttribute().getName()) &&
           !attr.getSearchField().isRemovable())
          return criterion;
      }
      
      return null;
    }

    public ConjunctionCriterion.Conjunction getConjunction()
    {
      ConjunctionCriterion.Conjunction conjType = _savedSearchDef.getSelectedConjunction();
      if (conjType == null)
      {
        return _savedSearchDef.getDefaultConjunction();
      }
      return conjType;
    }
    
    private List<Criterion> _sortCriterionList()
    {
      //first sort the list based on groupNames before sorting based on field order
      Collections.sort(_criterionList, _groupNameComparator);
      Collections.sort(_criterionList, _fieldOrderComparator);
      for(int count = 0 ; count < _criterionList.size() ; count++)
      {
        ((DemoAttributeCriterion)_criterionList.get(count)).setOrderDirty(false);
      }
      return _criterionList;
    }
    
    private boolean isCriterionOrderDirty()
    {
      for(Criterion criterion : _criterionList)
      {
        if(((DemoAttributeCriterion)criterion).isOrderDirty())
        {
          return true;
        }
      }
      
      return false;
    }
    
    public List<Criterion> getCriterionList()
    {
      if (_criterionList != null && !_criterionList.isEmpty())
      {
        if(isCriterionOrderDirty())
        {
          return _sortCriterionList();
        }
        else
        {
          return _criterionList;
        }
      }
      else
      {
        return _criterionList;
      }
    }

    public void setConjunction(ConjunctionCriterion.Conjunction conjunction)
    {
      _savedSearchDef.setSelectedConjunction(conjunction);
    }

    private void removeCriterion(DemoAttributeCriterion criterion)
    {
      _savedSearchDef.removeSearchFieldDef(getDemoSearchField(criterion)); 
      _criterionList.remove(criterion);
      _criterionSearchFieldMap.remove(criterion);
    }

    private void addCriterion(DemoAttributeCriterion criterion, DemoPageDef.SearchFieldDef searchField)
    { 
      _criterionList.add(criterion);
      _criterionSearchFieldMap.put(criterion, searchField);
      _savedSearchDef.addSearchFieldDef(searchField); 
    }

    DemoPageDef.SavedSearchDef _savedSearchDef;
    Map<Criterion, DemoPageDef.SearchFieldDef> _criterionSearchFieldMap;
    List<Criterion> _criterionList;
    FieldOrderComparator _fieldOrderComparator = new FieldOrderComparator();
    GroupNameComparator _groupNameComparator = new GroupNameComparator();
  }  
  
  private class AutoSuggestBehaviorConfigImpl extends AutoSuggestBehaviorConfig 
  {
    @Override
    public ValueExpression getMinChars() {
      FacesContext context = FacesContext.getCurrentInstance();
      Application app = context.getApplication();
      return app.getExpressionFactory().createValueExpression(context.getELContext(), "2", Integer.class);
    }
  
    @Override
    public ValueExpression getMaxSuggestedItems() {
      FacesContext context = FacesContext.getCurrentInstance();
      Application app = context.getApplication();
      return app.getExpressionFactory().createValueExpression(context.getELContext(), "5", Integer.class);
    }
  
    @Override
    public MethodExpression getSuggestItems() {
      FacesContext context = FacesContext.getCurrentInstance();
      Application app = context.getApplication();
      return app.getExpressionFactory().createMethodExpression(context.getELContext(), "#{demoLOV.suggestItems}", List.class, new Class[]{FacesContext.class, AutoSuggestUIHints.class});
    }
  
    @Override
    public ValueExpression getSmartList() {
      FacesContext context = FacesContext.getCurrentInstance();
      Application app = context.getApplication();
      return app.getExpressionFactory().createValueExpression(context.getELContext(), "#{demoLOV.smartList}", List.class);
    }
  }
  
  private static class FieldOrderComparator implements Comparator
  {
    @Override
    public int compare(Object o1, Object o2) 
    {
      DemoAttributeCriterion criterion1 = (DemoAttributeCriterion)o1;
      DemoAttributeCriterion criterion2 = (DemoAttributeCriterion)o2;
      String groupName1 = criterion1.getGroupName();
      String groupName2 = criterion2.getGroupName();
      
      if((groupName1 == null && groupName2 == null) || (groupName1 != null && groupName2 != null && groupName1.equals(groupName2)))
      {
        String fieldOrder1 = criterion1.getFieldOrder();
        String fieldOrder2 = criterion2.getFieldOrder();
        if(fieldOrder1 != null && fieldOrder2 != null)
        {
          return fieldOrder1.compareTo(fieldOrder2);
        }
        else
        {
          return 0;
        }
      }
      else
      {
        return 0;
      }
    }
  }

  private static class GroupNameComparator implements Comparator
  {
    @Override
    public int compare(Object o1, Object o2) 
    {
      DemoAttributeCriterion criterion1 = (DemoAttributeCriterion)o1;
      DemoAttributeCriterion criterion2 = (DemoAttributeCriterion)o2;
      String groupName1 = criterion1.getGroupName();
      String groupName2 = criterion2.getGroupName();
      
      if(groupName1 != null && groupName2 != null)
      {
        return groupName1.compareTo(groupName2);
      }
      else if(groupName1 == null && groupName2 == null)
      {
        return 0;
      }
      else if(groupName1 == null && groupName2 != null)
      {
        return -1;
      }
      else if(groupName1 != null && groupName2 == null)
      {
        return 1;
      }
      else
      {
        return 0;
      }
    }
  }

  public class DemoAttributeCriterion extends AttributeCriterion
  {
    
    public DemoAttributeCriterion(DemoPageDef.SearchFieldDef searchFieldDef)
    {
      _searchFieldDef = searchFieldDef;
      _attrDesc = new DemoAttributeDescriptor(searchFieldDef.getAttributeDef(), searchFieldDef.getAttributeDef().getGroupName());
    }
    public AttributeDescriptor getAttribute()
    {
      return _attrDesc;
    }

    public AttributeDescriptor.Operator getOperator()
    {
      return ((DemoAttributeDescriptor)_attrDesc).getOperator(_searchFieldDef.getOperator());
    }


    /**
     * The list of operators returned for an AttributeCriterion could be different from those 
     * returned for an AttributeDescriptor. For e.g., for an LOV attribute that supports multiple 
     * selection it makes more sense to support "In" and "Not In" operators rather than "Equals / 
     * Not Equals". 
     * @return
     */
    public Map<String, AttributeDescriptor.Operator> getOperators()
    {
      
      Map<String, AttributeDescriptor.Operator> optrMap = 
        new LinkedHashMap<String, AttributeDescriptor.Operator>();
        
      List<DemoPageDef.OperatorDef> operatorList = _searchFieldDef.getOperators();

      for(DemoPageDef.OperatorDef operator : operatorList)
      {
        AttributeDescriptor.Operator optr = 
          ((DemoAttributeDescriptor) _attrDesc).getOperator(operator);
        optrMap.put(optr.getLabel(), optr);
      }
      return optrMap;
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
  
    /**
     * Returns true, if this criterion has dependent criterion. The method will be called to 
     * determine whether af:query component should be refreshed in case the value of this criterion 
     * changes.
     * @param index value indicating the position of the criterion value field 
     * @retrun boolean returns true if the criterion has dependent criterion
     */
    public boolean hasDependentCriterion(int index)
    { 
      return _searchFieldDef.hasDependentField(index); 
    } 

    public DemoPageDef.SearchFieldDef getSearchField(){
        return _searchFieldDef;
    }

    public boolean isRemovable()
    {
      return _searchFieldDef.isRemovable();
    }

    public void setOperator(AttributeDescriptor.Operator operator)
    {
      _searchFieldDef.setOperator(getDemoOperator(operator));
    }

    /**
     * Returns the ComponentType specific to an AttributeCriterion. This may be different from the 
     * ComponentType of the AttributeDescriptor, based on the operator chosen or whether 
     * multi-select is enabled or not. For e.g., for LOV attributes, <br/>
     * - when multi-select is enabled, <br/> 
     *   - for operator 'In / Not In', the component type will always be selectManyChoice regardless 
     *     of the default type or dataType. <br/>
     *   - For all other operators it's an inputText (inputDate or inputNumberSpinbox for Date and 
     *     Number datatypes respectively.)
     * - when multi-select is disabled, <br/>
     *   - for operator Equals/Not Equals, it will be the default type (of the attribute)<br/>
     *   - for all other operators, an inputText (or inputDate or inputNumberSpinbox)
     * 
     * @param operator the operator for which the component type needs to be determined
     * @return a ComponentType
     */
    @Override
    public AttributeDescriptor.ComponentType getComponentType(AttributeDescriptor.Operator operator)
    {
      DemoAttributeDescriptor demoAttrDesc = (DemoAttributeDescriptor) getAttribute();
      boolean isMultiSelectOper = demoAttrDesc.hasVariableOperands(operator);
      if (demoAttrDesc.isLOV())
      {
        if (isMultiSelectOper)
        {
          // always return selectOneChoice for multiSelect enabled operators
          return ComponentType.selectManyChoice;
        }
        else
        {
          // For certain operators the default component type is used, otherwise the base component
          // type is used (based on the datatype)
          if (demoAttrDesc.useDefaultComponentType(operator))
          {
            AttributeDescriptor.ComponentType compType = demoAttrDesc.getComponentType();
            
            //try to simulate the current model driven query's behaviour and return 
            //inputText for all operators except equals and non equals
            if(compType.equals(AttributeDescriptor.ComponentType.selectOneChoice))
            {
              if(operator != null && (DemoPageDef.OperatorDef.EQUALS.getLabel().equals(operator.getLabel()) || 
                                      DemoPageDef.OperatorDef.NOT_EQUALS.getLabel().equals(operator.getLabel())))
              {
                return compType;
              }
              else
              {
                return ComponentType.inputText;
              }
            }
          }
          else
          {
            // based on the type of the attribute 
            String typeName = demoAttrDesc.getType().getName();
            if (typeName.equals("java.lang.Number"))
              return ComponentType.inputNumberSpinbox;
            else if (typeName.equals("java.util.Date"))
              return ComponentType.inputDate;
            else
              return ComponentType.inputText;
          }
        }
      }
      
      // Return default componentType
      return demoAttrDesc.getComponentType();
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

    /**
     * For the componentType used by the criterion based on the current operator, return the list of 
     * model objects to use.
     * @return
     */
    @Override
    public List<? extends Object> getModelList()
    {
      AttributeDescriptor.ComponentType compType = getComponentType(getOperator());
      DemoAttributeDescriptor attrDesc = (DemoAttributeDescriptor ) getAttribute();
      
      if (attrDesc == null)
      {
        return Collections.emptyList();
      }
      else
      {
        Object attrModel = attrDesc.getModel(compType);
        if (attrModel != null)
        {
          List<Object> modelList = new ArrayList<Object>();
          AttributeDescriptor.Operator operator = getOperator();
          int operandCount = (operator != null) ? operator.getOperandCount() : 1;
          // Add a model item to the list for each operand, of the current operator.
          for (int i = 0; i < operandCount; i++)
          {
            modelList.add(attrModel);
          }
          return modelList;
        }
        else 
          return Collections.emptyList();
      }
    }

    
    public void setFieldOrder(String _fieldOrder)
    {
      this._fieldOrder = _fieldOrder;
      _orderDirty = true;
    }

    public String getFieldOrder()
    {
      return _fieldOrder;
    }
    
    public boolean isOrderDirty()
    {
      return _orderDirty;
    }
    
    public void setOrderDirty(boolean dirty)
    {
      _orderDirty = dirty;
    }
    
    public void setGroupName(String groupName)
    {
      if(groupName != null)
      {
        _groupName = groupName;
      }
      else
      {
        _groupName = ""; // if the field is moved to uncategorized category, mark it empty
      }
    }

    public String getGroupName()
    {
      if(_groupName != null && _groupName.equals(""))
      {
        return null;
      }
      else
      {
        return (_groupName != null) ? _groupName : super.getGroupName();
      }
    }

    private DemoPageDef.SearchFieldDef _searchFieldDef = null;
    private AttributeDescriptor _attrDesc = null;
    private String _fieldOrder = null;
    private boolean _orderDirty = false;
    private String _groupName;

  }
  
  public final class DemoGroupAttributeDescriptor extends GroupAttributeDescriptor
  {
    public DemoGroupAttributeDescriptor(String groupName, String label, String description)
    {
      _groupName = groupName;
      _label = label;
      _description = description;
    }

    @Override
    public String getName()
    {
      return _groupName;
    }

    @Override
    public String getLabel()
    {
      return _label;
    }

    @Override
    public String getDescription()
    {
      return _description;
    }
    
    @Override
    public List<AttributeDescriptor> getAttributes()
    {
      return _attributes;
    }
    
    public void addAttribute(AttributeDescriptor attrib)
    {
      if(_attributes == null)
      {
        _attributes = new ArrayList<AttributeDescriptor>();
      }
      
      _attributes.add(attrib);
    }
    
    private String _groupName;
    private String _label;
    private String _description;
    private List<AttributeDescriptor> _attributes;
  }
  
  public final class DemoAttributeDescriptor extends AttributeDescriptor
  {
    public DemoAttributeDescriptor(DemoPageDef.AttributeDef attrDef)
    {
      this(attrDef, null);
    }
    
    public DemoAttributeDescriptor(DemoPageDef.AttributeDef attrDef, String groupName)
    {
      _attrDef = attrDef;
      _groupName = groupName;
    }
    
    public String getGroupName()
    {
      return _groupName;
    }
    
    /**
     * Returns the NumberConverter for Salary attribute else returns null.
     * @return Converter returns the NumberConverter for Salary attribute else returns null
     * @see AttributeDescriptor#getConverter
     */
    public Converter getConverter()
    {
      Application application = FacesContext.getCurrentInstance().getApplication();
      String name = getName();
      if (name.equals("Salary"))
      {
        Converter converter = application.createConverter(Number.class);
        if (converter != null && converter instanceof NumberConverter)
        {
          NumberConverter numberConv = (NumberConverter) converter;
          numberConv.setGroupingUsed(false);
          numberConv.setPattern("$ ###0,000.00");
          numberConv.setType("currency");
          numberConv.setCurrencySymbol("$");        
          return numberConv;
        }
      }
      return null;
    }
    public String getDescription()
    {
      return _attrDef.getDescription();
    }

    public String getFormat()
    {
      return _attrDef.getFormat();
    }

    public String getLabel()
    {
      return _attrDef.getLabel();
    }
    
    /**
     * Returns true if criterion is indexed and not required.
     * 
     * @return true if criterion is indexed and not required else false.
     */
    public boolean isIndexed()
    {
      //only when criterion is indexed but not required it will be considered as indexed
      return (_attrDef.isIndexed() && !_attrDef.isMandatory());
    }
    
    public boolean isLOV()
    {
      return _attrDef.isLOV();
    }

    /**
     * Based on the component type of the attribute, it returns an appropriate model object expected 
     * of that component
     * @return
     */
    public Object getModel()
    {
      AttributeDescriptor.ComponentType compType = _attrDef.getComponentType();
      return getModel(compType);
    }

    public Object getModel(AttributeDescriptor.ComponentType compType)
    {
      if (compType.equals(AttributeDescriptor.ComponentType.selectOneChoice) ||
         compType.equals(AttributeDescriptor.ComponentType.selectManyChoice))
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
      for (DemoPageDef.OperatorDef operator : operatorList)
      {
        AttributeDescriptor.Operator optr = new OperatorImpl(operator);
        optrSet.add(optr);
      }
      return optrSet;
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
    
    /**
     * Returns true if this criterion is a required field or is the only indexed field in the 
     * criterion list.
     * 
     * @return true if single indexed criterion exist else false
     */
    public boolean isRequired()
    {
      if(_attrDef.isIndexed() && _isSingleIndexedCriterion())
      {
        return true;
      }
      
      return _attrDef.isMandatory();
    }

    @Override
    public String getPlaceholder()
    {
      return _attrDef.getPlaceholder();
    }

    public AttributeDescriptor.Operator getOperator(DemoPageDef.OperatorDef operator)
    {
      if(operator != null)
        return new OperatorImpl(operator);
      else
        return null;
    }

    public int getLength()
    {
      return _attrDef.getLength();
    }

    public int getMaximumLength()
    {
      return _attrDef.getMaximumLength();
    }

    /**
     * Determines if the passed in operator can be used with multiple values, like the IN, Not IN
     * @param operator
     * @return boolean
     */
    public boolean hasVariableOperands(AttributeDescriptor.Operator operator)
    {
      if (operator == null)
        return false;
      OperatorImpl operImpl = (OperatorImpl) operator;
      return operImpl.getOperatorDef().hasVariableOperands();
    }

    /**
     * Determines if the operator requires showing the default component type. The default component 
     * type of the attribute is used for operator Equals / Not Equals. 
     * @param operator
     * @return
     */
    public boolean useDefaultComponentType(AttributeDescriptor.Operator operator)
    {
      if(operator != null)
      {
         OperatorImpl operImpl = (OperatorImpl) operator;
         return (operImpl.getOperatorDef() == null ||
                operImpl.getOperatorDef().equals(DemoPageDef.OperatorDef.EQUALS) ||
                operImpl.getOperatorDef().equals(DemoPageDef.OperatorDef.NOT_EQUALS));
      }
      return true;
    }
    
    public AutoSuggestBehaviorConfig getAutoSuggestBehaviorConfig()
    {
      if (_autoSuggestConfig == null)
      {
        _autoSuggestConfig = new AutoSuggestBehaviorConfigImpl();
      }      
      return _autoSuggestConfig;
    } 

    public class OperatorImpl extends AttributeDescriptor.Operator
    {
      public OperatorImpl(DemoPageDef.OperatorDef operator)
      {
        _operator = operator;
      }
      public String getLabel()
      {
        return (_operator != null) ? _operator.getLabel() : null;
      }

      public Object getValue()
      {
        return (_operator != null) ?_operator.getSymbol() : null;
      }

      // Returns the default operandCount for the operator. Except when the operator is null it 
      // returns 1 and if operandCount is -1, indicating unlimited operands, it returns 1, as from 
      // the UI standpoint a selectmanyChoice will be rendered.
      public int getOperandCount()
      {
        if (_operator != null) 
        {
          int operandCount = _operator.getOperandCount();
          if (operandCount != -1)
           return operandCount;
        }
        return 1;
      }
      
      @Override
      public boolean equals(Object operator)
      {
        if(operator != null && _operator != null)
        {
          return (_operator.getSymbol().equals(operator.toString()));
        }
        return false;
      }      

      @Override
      public String toString()
      {
        return (_operator != null) ?_operator.getSymbol() : null;
      }

      private DemoPageDef.OperatorDef getOperatorDef()
      {
        return _operator;
      }

      DemoPageDef.OperatorDef _operator;
    }

    private DemoPageDef.AttributeDef _attrDef;
    private String _groupName;
    private AutoSuggestBehaviorConfig _autoSuggestConfig;
  }
  
  /* A simple queryOperationListener which processes CRITERION_UPDATE event.*/
  public final class DemoQueryOperationListener implements QueryOperationListener
  {
    public void processQueryOperation(QueryOperationEvent event) throws AbortProcessingException
    {
      DemoQueryDescriptor qd = (DemoQueryDescriptor)event.getDescriptor();
      if (event.getOperation()==QueryOperationEvent.Operation.CRITERION_UPDATE &&
         "Show".equals(event.getAttributeCriterion().getAttribute().getName()))
      {
        String value = (String) event.getAttributeCriterion().getValues().get(0);
        RichQuery query =(RichQuery) event.getComponent();
        DemoConjunctionCriterion qc = (DemoConjunctionCriterion)qd.getConjunctionCriterion();
        
        if ("Hide".equals(value))
        {
          // Search the criterion named Salary; add it to hidden criterion list and
          // remove from conjunctionCriterion
          DemoAttributeCriterion attr = (DemoAttributeCriterion) qc.getCriterionByName("Salary");
          if (attr != null)
          {
            qd.addHiddenCriterion(attr);
            qc.removeCriterion(attr);
          }
        }
        if ("Show".equals(value))
        {
          if(!qd._hiddenCriterions.isEmpty())
          {
            //get the criterion from hiddenCriterions and add it to conjunctionCriterion
            DemoAttributeCriterion attr = (DemoAttributeCriterion) qd._hiddenCriterions.get(0);
            DemoPageDef.SearchFieldDef searchField = attr.getSearchField();
            qd.removeHiddenCriterion(attr);
            qc.addCriterion(attr, searchField);
          }
        }
        
        //refresh the query
        query.refresh(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(query);
      }

      else if (event.getOperation()==QueryOperationEvent.Operation.OVERRIDE)
      {
        // How do we retrieve the name of the current saved search
        Map<String, Object> updatedHints = event.getUpdatedHints();
        String newName = (String) updatedHints.get(QueryDescriptor.UIHINT_NAME);
        if(qd.getName().equals(newName))
        {
          DemoPageDef.SavedSearchDef savedSearchDef = qd.getSavedSearchDef();

          // Mark all criterion in the new QueryDescriptor as immutable
          for(DemoPageDef.SearchFieldDef fieldDef : savedSearchDef.getSearchFields())
          {
            if (fieldDef.isRemovable())
            {
              fieldDef.setRemovable(false);
            }
          }
        }
      }
    }
  }

  private DemoPageDef _pageDef;
  
  //This is the static defined part.
  Map<String, QueryDescriptor> _QDMap = new TreeMap<String, QueryDescriptor>();
  private QueryDescriptor _currentDescriptor = null;
  private QueryModel _queryModel = null;
  private String _sqlString = null;
}
