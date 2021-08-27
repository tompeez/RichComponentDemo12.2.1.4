/** Copyright (c) 2012, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.tags;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adfdemo.view.tags.foreachdata.Employee;
import oracle.adfdemo.view.tags.foreachdata.Genus;
import oracle.adfdemo.view.tags.foreachdata.Species;
import oracle.adfdemo.view.tags.foreachdata.Tribe;

import org.apache.myfaces.trinidad.component.UIXComponent;
import org.apache.myfaces.trinidad.component.visit.VisitTreeUtils;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.RowKeyPropertyModel;
import org.apache.myfaces.trinidad.model.SortCriterion;
import org.apache.myfaces.trinidad.util.ComponentUtils;

/**
 * Demo bean to show af:forEach functionality
 */
public class ForEachBean
  implements Serializable
{
  public ForEachBean()
  {
    // Set the default state
    _initializeData("simple.jsff", false);
  }

  public RowKeyPropertyModel getEmployeeModel()
  {
    // Since the model is not serializable, ensure that it is created for the data
    _initializeData(_selectedSubViewId, false);
    return _employeeModel;
  }

  public final void setNewFirstName(String newFirstName)
  {
    _newFirstName = newFirstName;
  }

  public final String getNewFirstName()
  {
    return _newFirstName;
  }

  public final void setNewLastName(String newLastName)
  {
    _newLastName = newLastName;
  }

  public final String getNewLastName()
  {
    return _newLastName;
  }

  public final void setNewEmployeeId(Integer newEmployeeId)
  {
    _newEmployeeId = newEmployeeId;
  }

  public final Integer getNewEmployeeId()
  {
    if (_newEmployeeId == null)
    {
      // Be nice and figure out the next one
      Integer largest = null;
      for (Integer id : getEmployeeMap().keySet())
      {
        if (largest == null)
        {
          largest = id;
        }
        else if (largest < id)
        {
          largest = id;
        }
      }

      _newEmployeeId = largest == null ? 1 : largest + 1;
    }

    return _newEmployeeId;
  }

  public final void setNewSalary(Double newSalary)
  {
    _newSalary = newSalary;
  }

  public final Double getNewSalary()
  {
    return _newSalary;
  }

  public Map<Integer, Employee> getEmployeeMap()
  {
    return _employeeMap;
  }

  public List<Employee> getEmployeeList()
  {
    return _employeeList;
  }

  public Tribe[] getCanidaeTribes()
  {
    return _canidaeTribes;
  }

  public void setSelectedSubViewId(String selectedSubViewId)
  {
    // Code to set the currently view example
    if (_selectedSubViewId == selectedSubViewId ||
      (_selectedSubViewId != null && _selectedSubViewId.equals(selectedSubViewId)))
    {
      return;
    }

    // Reset the data, erasing any changes for a clean demo
    _initializeData(selectedSubViewId, true);
  }

  public String getSelectedSubViewId()
  {
    return _selectedSubViewId;
  }

  public void setSortProperty(String sortProperty)
  {
    _sortProperty = sortProperty;
  }

  public String getSortProperty()
  {
    return _sortProperty;
  }

  public void setSortAscending(boolean sortAscending)
  {
    _sortAscending = sortAscending;
  }

  public boolean getSortAscending()
  {
    return _sortAscending;
  }

  /**
   * Example of how to generate partial triggers for a component triggered by all components
   * of a for each tag.
   */
  public String[] getSalaryPartialTriggers()
  {
    final String idFormat = "salary_%d";

    Map<Integer, Employee> emps = getEmployeeMap();

    String[] triggers = new String[emps.size()];
    int i = 0;

    for (Employee emp : emps.values())
    {
      triggers[i++] = String.format(idFormat, emp.getId());
    }

    return triggers;
  }

  public int getPprDemoPanelFormLayoutRowsCount()
  {
    return (int)Math.round(getEmployeeMap().size() / 2d);
  }

  public double getTotalEmployeeMapSalary()
  {
    double salary = 0;

    for (Employee emp : getEmployeeMap().values())
    {
      salary += emp.getSalary();
    }

    return salary;
  }

  /**
   * Ensure a new employee does not duplicate the ID
   */
  public void validateNewEmployeeId(
    @SuppressWarnings("unused")
    FacesContext facesContext,
    @SuppressWarnings("unused")
    UIComponent comp,
    Object       object)
  {
    if (object instanceof Number)
    {
      int value = ((Number)object).intValue();

      if (getEmployeeMap().containsKey(value) == false)
      {
        return;
      }

      throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
          "ID already in use",
          "Choose another ID, this one is already in use by another employee"));
    }
  }

  /**
   * Resets the data without having to change the view
   */
  public void resetData(
    @SuppressWarnings("unused")
    ActionEvent evt)
  {
    _initializeData(_selectedSubViewId, true);
  }

  public void sortModel(
    ActionEvent evt)
  {
    final RowKeyPropertyModel model = getEmployeeModel();

    if (_sortProperty == null || _sortProperty.length() == 0)
    {
      model.setSortCriteria(null);
    }
    else
    {
      model.setSortCriteria(Collections.singletonList(new SortCriterion(_sortProperty,
              _sortAscending)));
    }

    _sortApplied = true;

    // Now fire a component event to re-order the components
    FacesContext facesContext = FacesContext.getCurrentInstance();
    UIComponent forEachParent = ComponentUtils.findRelativeComponent(evt.getComponent(),
                                  "forEachParent");
    Object oldRowKey = model.getRowKey();
    Map<Integer, Integer> idToIndexMap = new HashMap<Integer, Integer>(model.getRowCount());

    try
    {
      int index = 0;
      for (Object data : model)
      {
        Employee emp = (Employee)data;
        idToIndexMap.put(emp.getId(), index++);
      }
    }
    finally
    {
      model.setRowKey(oldRowKey);
    }

    _sortChildren(facesContext, forEachParent, "rowKey", idToIndexMap);
  }

  public void handleAddEmployeeDialogEvent(DialogEvent dialogEvent)
  {
    if (dialogEvent.getOutcome() != DialogEvent.Outcome.ok)
    {
      return;
    }

    Employee emp = new Employee(_newEmployeeId, _newFirstName, _newLastName, null, _newSalary);
    Map<Integer, Employee> map = getEmployeeMap();
    map.put(emp.getId(), emp);
  }

  public void handleDelete(ActionEvent evt)
  {
    UIComponent target = evt.getComponent();
    Integer empId = (Integer)target.getAttributes().get("employeeId");
    getEmployeeMap().remove(empId);

    RequestContext.getCurrentInstance().addPartialTargets(target, "::forEachParent");
  }

  public DnDAction employeeIdDrop(DropEvent dropEvent)
  {
    Number employeeId = dropEvent.getTransferable().getData(Number.class);
    Integer dragEmployeeId = new Integer(employeeId.intValue());
    final Map<Integer, Employee> emps = getEmployeeMap();
    Employee dragEmployee = emps.remove(dragEmployeeId);

    Integer dropEmployeeId = (Integer) dropEvent.getDropComponent().getAttributes().get("empid");
    UIComponent forEachParent;

    if (dropEmployeeId == null)
    {
      // Move the employee to the end (the component is not in a naming container)
      emps.put(dragEmployeeId, dragEmployee);
      forEachParent = ComponentUtils.findRelativeComponent(
        dropEvent.getDropComponent(), "forEachParent");
    }
    else
    {
      forEachParent = ComponentUtils.findRelativeComponent(
        dropEvent.getDropComponent(), "::forEachParent");
    }

    // Need to rebuild in the desired order
    Map<Integer, Employee> map = new LinkedHashMap<Integer, Employee>(emps);
    emps.clear();
    final Map<Integer, Integer> orderMap = new HashMap<Integer, Integer>();

    int index = 0;
    for (Map.Entry<Integer, Employee> entry : map.entrySet())
    {
      Integer empId = entry.getKey();
      if (empId.equals(dropEmployeeId))
      {
        emps.put(dragEmployeeId, dragEmployee);
        orderMap.put(dragEmployeeId, index++);
      }

      emps.put(empId, entry.getValue());
      orderMap.put(empId, index++);
    }

    _sortChildren(FacesContext.getCurrentInstance(), forEachParent, "employeeId", orderMap);

    return DnDAction.MOVE;
  }

  private void _initializeData(
    String  selectedSubViewId,
    boolean clean)
  {
    if (clean)
    {
      _canidaeTribes = null;
      _employeeMap = null;
      _employeeModel = null;
      _employeeList = null;
      _sortApplied = false;
    }

    if ("simple.jsff".equals(selectedSubViewId))
    {
      _buildEmployeeList();
      _buildEmployeeModel();
      _buildEmployeeMap();
    }
    else if ("sort.jsff".equals(selectedSubViewId))
    {
      _buildEmployeeModel();
    }
    else if ("updates.jsff".equals(selectedSubViewId))
    {
      _buildEmployeeMap();
    }
    else if ("ppr.jsff".equals(selectedSubViewId))
    {
      _buildEmployeeMap();
    }
    else if ("nested.jsff".equals(selectedSubViewId))
    {
      _buildCanidaeTaxonomy();
    }
    else
    {
      throw new IllegalArgumentException();
    }

    _selectedSubViewId = selectedSubViewId;
  }

  private void _buildEmployeeModel()
  {
    _buildEmployeeList();
    if (_employeeModel == null)
    {
      _employeeModel = new RowKeyPropertyModel(_employeeList, "id");

      if (_sortApplied)
      {
        if (_sortProperty == null || _sortProperty.length() == 0)
        {
          _employeeModel.setSortCriteria(null);
        }
        else
        {
          _employeeModel.setSortCriteria(Collections.singletonList(new SortCriterion(_sortProperty,
                  _sortAscending)));
        }
      }
    }
  }

  private void _buildEmployeeList()
  {
    if (_employeeList == null)
    {
      _employeeList = Arrays.asList(_buildEmployeeData());
    }
  }

  private void _buildEmployeeMap()
  {
    if (_employeeMap == null)
    {
      Employee[] emps = _buildEmployeeData();
      _employeeMap = new LinkedHashMap<Integer, Employee>(emps.length);
      for (Employee e : emps)
      {
        _employeeMap.put(e.getId(), e);
      }
    }
  }

  private Employee[] _buildEmployeeData()
  {
    return new Employee[]
      {
        new Employee(100, "Steven", "King", "SKING", 24000),
        new Employee(101, "Neena", "Kochhar", "NKOCHHAR", 17000),
        new Employee(102, "Lex", "De Haan", "LDEHAAN", 17000),
        new Employee(103, "Alexander", "Hunold", "AHUNOLD", 9000),
        new Employee(104, "Bruce", "Ernst", "BERNST", 6000),
        new Employee(105, "David", "Austin", "DAUSTIN", 4800),
        new Employee(106, "Valli", "Pataballa", "VPATABAL", 4800),
        new Employee(107, "Diana", "Lorentz", "DLORENTZ", 4200),
        new Employee(108, "Nancy", "Greenberg", "NGREENBE", 12000),
        new Employee(109, "Daniel", "Faviet", "DFAVIET", 9000),
        new Employee(110, "John", "Chen", "JCHEN", 8200),
        new Employee(111, "Ismael", "Sciarra", "ISCIARRA", 7700),
        new Employee(112, "Jose Manuel", "Urman", "JMURMAN", 7800),
        new Employee(113, "Luis", "Popp", "LPOPP", 6900),
        new Employee(114, "Den", "Raphaely", "DRAPHEAL", 11000),
        new Employee(115, "Alexander", "Khoo", "AKHOO", 3100),
        new Employee(116, "Shelli", "Baida", "SBAIDA", 2900),
        new Employee(117, "Sigal", "Tobias", "STOBIAS", 2800),
        new Employee(118, "Guy", "Himuro", "GHIMURO", 2600),
        new Employee(119, "Karen", "Colmenares", "KCOLMENA", 2500),
        new Employee(120, "Matthew", "Weiss", "MWEISS", 8000),
        new Employee(121, "Adam", "Fripp", "AFRIPP", 8200),
        new Employee(122, "Payam", "Kaufling", "PKAUFLIN", 7900),
        new Employee(123, "Shanta", "Vollman", "SVOLLMAN", 6500),
        new Employee(124, "Kevin", "Mourgos", "KMOURGOS", 5800),
        new Employee(125, "Julia", "Nayer", "JNAYER", 3200),
        new Employee(126, "Irene", "Mikkilineni", "IMIKKILI", 2700),
        new Employee(127, "James", "Landry", "JLANDRY", 2400),
        new Employee(128, "Steven", "Markle", "SMARKLE", 2200),
        new Employee(129, "Laura", "Bissot", "LBISSOT", 3300),
        new Employee(130, "Mozhe", "Atkinson", "MATKINSO", 2800),
        new Employee(131, "James", "Marlow", "JAMRLOW", 2500),
        new Employee(132, "TJ", "Olson", "TJOLSON", 2100),
        new Employee(133, "Jason", "Mallin", "JMALLIN", 3300),
        new Employee(134, "Michael", "Rogers", "MROGERS", 2900)
      };
  }

  private void _buildCanidaeTaxonomy()
  {
    if (_canidaeTribes != null)
    {
      return;
    }

    _canidaeTribes = new Tribe[]
      {
        new Tribe("True dogs", "Tribe Canini",
          new Genus("Canis",
            new Species("Gray wolf", "Canis lupus "),
            new Species("Domestic dog", "Canis lupus familiaris"),
            new Species("Dingo", "Canis lupus dingo "),
            new Species("Coyote", "Canis latrans "),
            new Species("Ethiopian wolf", "Canis simensis "),
            new Species("Golden jackal", "Canis aureus"),
            new Species("Side-striped jackal", "Canis adustus"),
            new Species("Black-backed jackal", "Canis mesomelas")),
          new Genus("Cuon",
            new Species("Dhole", "Cuon alpinus or Canis alpinus ")),
          new Genus("Lycaon",
            new Species("African wild dog", "Lycaon pictus ")),
          new Genus("Atelocynus",
            new Species("Short-eared dog", "Atelocynus microtis")),
          new Genus("Cerdocyon",
            new Species("Crab-eating fox", "Cerdocyon thous")),
          new Genus("Dusicyon ",
            new Species("Falklands wolf", "Dusicyon australis ")),
          new Genus("Lycalopex ",
            new Species("Culpeo", "Lycalopex culpaeus"),
            new Species("Darwin's fox", "Lycalopex fulvipes"),
            new Species("South American gray fox", "Lycalopex griseus"),
            new Species("Pampas fox", "Lycalopex gymnocercus"),
            new Species("Sechura fox", "Lycalopex sechurae"),
            new Species("Hoary fox", "Lycalopex vetulus")),
          new Genus("Chrysocyon",
            new Species("Maned wolf", "Chrysocyon brachyurus")),
          new Genus("Speothos",
            new Species("Bush dog", "Speothos venaticus"))),
        new Tribe("True foxes", "Tribe Vulpini",
          new Genus("Vulpes",
            new Species("Arctic fox", "Vulpes lagopus"),
            new Species("Red fox", "Vulpes vulpes "),
            new Species("Swift fox", "Vulpes velox"),
            new Species("Kit fox", "Vulpes macrotis"),
            new Species("Corsac fox", "Vulpes corsac"),
            new Species("Cape fox", "Vulpes chama"),
            new Species("Pale fox", "Vulpes pallida"),
            new Species("Bengal fox", "Vulpes bengalensis"),
            new Species("Tibetan sand fox", "Vulpes ferrilata"),
            new Species("Blanford's fox", "Vulpes cana"),
            new Species("Rappell's fox", "Vulpes rueppelli"),
            new Species("Fennec fox", "Vulpes zerda")),
          new Genus("Urocyon ",
            new Species("Gray fox", "Urocyon cinereoargenteus"),
            new Species("Island fox", "Urocyon littoralis"),
            new Species("Cozumel fox", "Urocyon sp."))),
        new Tribe(null, "Basal Caninae",
          new Genus("Otocyon ",
            new Species("Bat-eared fox", "Otocyon megalotis")),
          new Genus("Nyctereutes",
            new Species("Raccoon dog", "Nyctereutes procyonoides")))
      };
  }

  private void _sortChildren(
    FacesContext                facesContext,
    UIComponent                 parentComponent,
    final String                sortAttributeName,
    final Map<Integer, Integer> employeeIdToIndexMap)
  {
    String clientId = parentComponent.getClientId(facesContext);
    VisitTreeUtils.visitSingleComponent(facesContext,
      clientId,
      new VisitCallback()
      {
        @Override
        public VisitResult visit(
          VisitContext visitContext,
          UIComponent  target)
        {
          FacesContext facesContext = visitContext.getFacesContext();

          VisitContext childVisitContext = VisitContext.createVisitContext(facesContext);
          GetEmployeeIdsVisitCallback callback = new GetEmployeeIdsVisitCallback(sortAttributeName);
          UIXComponent.visitChildren(childVisitContext, target, callback);

          Map<String, Integer> compIdToEmpId = callback.getComponentIdToEmployeeIdMap();
          Map<String, Integer> compIdToIndex = new HashMap<String, Integer>(compIdToEmpId.size());
          for (Map.Entry<String, Integer> entry : compIdToEmpId.entrySet())
          {
            compIdToIndex.put(entry.getKey(), employeeIdToIndexMap.get(entry.getValue()));
          }
          ComponentSorter sorter = new ComponentSorter(facesContext, compIdToIndex);
          List<UIComponent> children = target.getChildren();
          ArrayList<UIComponent> childrenCopy = new ArrayList<UIComponent>(children);
          childrenCopy.removeAll(callback.getComponentsBeforeForEach());
          childrenCopy.removeAll(callback.getComponentsAfterForEach());
          Collections.sort(childrenCopy, sorter);

          // Add back the non-for each components
          childrenCopy.addAll(0, callback.getComponentsBeforeForEach());
          childrenCopy.addAll(callback.getComponentsAfterForEach());

          children.clear();
          children.addAll(childrenCopy);
          return VisitResult.COMPLETE;
        }
      });

    RequestContext.getCurrentInstance().addPartialTarget(parentComponent);
  }

  private static class ComponentSorter
    implements Comparator<UIComponent>
  {
    private ComponentSorter(
      FacesContext         facesContext,
      Map<String, Integer> componentIdToIndexMap)
    {
      _idToIndexMap = componentIdToIndexMap;
      _facesContext = facesContext;
    }

    @Override
    public int compare(
      UIComponent comp1,
      UIComponent comp2)
    {
      Integer index1 = _idToIndexMap.get(comp1.getId());
      Integer index2 = _idToIndexMap.get(comp2.getId());

      return index1 == null ? -1 :
        index2 == null ? 1 :
        index1 - index2;
    }

    private final Map<String, Integer> _idToIndexMap;
    private final FacesContext _facesContext;
  }

  private static class GetEmployeeIdsVisitCallback
    implements VisitCallback
  {
    private GetEmployeeIdsVisitCallback(String attributeName)
    {
      _attributeName = attributeName;
    }

    @Override
    public VisitResult visit(
      VisitContext visitContext,
      UIComponent  target)
    {
      Integer employeeId = (Integer)target.getAttributes().get(_attributeName);
      if (employeeId != null)
      {
        _employeeIds.put(target.getId(), employeeId);
      }
      else
      {
        (_employeeIds.isEmpty() ? _componentsBeforeForEach : _componentsAfterForEach)
          .add(target);
      }

      // Only process the children
      return VisitResult.REJECT;
    }

    public Map<String, Integer> getComponentIdToEmployeeIdMap()
    {
      return _employeeIds;
    }

    public List<UIComponent> getComponentsBeforeForEach()
    {
      return _componentsBeforeForEach;
    }

    public List<UIComponent> getComponentsAfterForEach()
    {
      return _componentsAfterForEach;
    }

    private final List<UIComponent> _componentsBeforeForEach = new ArrayList<UIComponent>();
    private final List<UIComponent> _componentsAfterForEach = new ArrayList<UIComponent>();
    private final Map<String, Integer> _employeeIds = new HashMap<String, Integer>();
    private final String _attributeName;
  }

  private transient RowKeyPropertyModel _employeeModel;
  private Map<Integer, Employee> _employeeMap;
  private List<Employee> _employeeList;
  private Tribe[] _canidaeTribes;
  private String _selectedSubViewId = "simple.jsff";
  private String _sortProperty;
  private boolean _sortAscending = true;
  private boolean _sortApplied = false;
  private transient String _newFirstName;
  private transient String _newLastName;
  private transient Integer _newEmployeeId;
  private transient Double _newSalary;

  @SuppressWarnings("compatibility:6316051515141351007")
  private static final long serialVersionUID = 2L;
}

