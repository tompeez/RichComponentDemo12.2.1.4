package oracle.adfdemo.view.components.rich;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichSearchSection;
import oracle.adf.view.rich.component.rich.input.RichInputSearch;
import oracle.adf.view.rich.render.ClientEvent;
import oracle.adf.view.rich.render.RichRenderingContext;

import oracle.adfdemo.view.inputSearch.Service.EmpDeptRestService;
import oracle.adfdemo.view.inputSearch.model.DemoSearchModel;
import oracle.adfdemo.view.inputSearch.model.Employee;

import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.util.ArrayMap;


/**
 * Demo bean to hold the value and compute the JWT Token
 * This bean is SessionScoped
 */
public class DemoInputSearchBean implements Serializable
{
  public Set<Integer> getEmployeesSet()
  {
    RichRenderingContext rrc = (RichRenderingContext) RenderingContext.getCurrentInstance();
    if (rrc != null)
    {
      rrc.addFeature("AdfDemoRichInputSearch");
    }

    return Collections.unmodifiableSet(_employeesSet);
  }

  public Set<Integer> getEmployeesSet2()
  {
    return Collections.unmodifiableSet(_employeesSet2);
  }

  public Set<Integer> getEmployeesSet3()
  {
    return Collections.unmodifiableSet(_employeesSet3);
  }

  public void setEmployeesSet(Set<Integer> employees)
  {
    _employeesSet = new CopyOnWriteArraySet<Integer>(employees);
  }

  public void setEmployeesSet2(Set<Integer> employees)
  {
    _employeesSet2 = new CopyOnWriteArraySet<Integer>(employees);
  }

  public void setEmployeesSet3(Set<Integer> employees)
  {
    _employeesSet3 = new CopyOnWriteArraySet<Integer>(employees);
  }

  public List<Employee> getAllEmployees()
  {
    return DemoSearchModel.getInstance().getEmployees();
  }

  /**
   * Returns a mock JWT token that is required for the mock authenticated REST service
   */
  public String getJwtToken()
  {
    RichRenderingContext rrc = (RichRenderingContext) RenderingContext.getCurrentInstance();
    if (rrc != null)
    {
      rrc.addFeature("AdfDemoRichInputSearch");
    }

    return "{\"" + EmpDeptRestService.DEMO_MOCK_HEADER_TOKEN_KEY +
           "\" : \"" + EmpDeptRestService.DEMO_MOCK_HEADER_TOKEN_VALUE + "\"}";
  }

  /**
   * Converts the members of the selection object to appropriate type.
   * Specifically, it would convert the key of the Map, and "key", "index" and "data"
   * properties of the suggestion object
   */
  public <T> Map<Integer, Map<String, Object>> selectionConverter(
    Map<T, Map<String, Object>> selections)
  {
    Map<Integer, Map<String, Object>> convertedSelections =
      new HashMap<Integer, Map<String, Object>>();
    Set<Map.Entry<T, Map<String, Object>>> entrySet = selections.entrySet();
    for (Map.Entry<T, Map<String, Object>> entry : entrySet)
    {
      Map<String, Object> convertedSuggestionObj = new ArrayMap<String, Object>(3);

      Integer convertedKey = ((Number) entry.getKey()).intValue();
      convertedSuggestionObj.put("key", convertedKey);
      convertedSuggestionObj.put("index", entry.getValue().get("index"));

      Map<String, Object> rawData = (Map<String, Object>) entry.getValue().get("data");
      Map<String, Object> convertedRowData = _convertRawRowData(rawData);
      convertedSuggestionObj.put("data", convertedRowData);

      convertedSelections.put(convertedKey, Collections.unmodifiableMap(convertedSuggestionObj));
    }
    return Collections.unmodifiableMap(convertedSelections);
  }

  /**
   * Getter for dataUrl
   */
  public String getDataUrl()
  {
    return _dataUrl;
  }

  /**
   * Adds a new employee as part of Inline Create Feature Demo
   * @param event
   */
  public void createNewEmployee(ClientEvent event)
  {
    String submittedValue = (String) event.getParameters().get("submittedValue");
    String[] words = submittedValue.split(" ");

    // update the list
    Integer value = DemoSearchModel.getInstance().addEmployee(words[0],
                                                              words.length > 1 ? words[1] : "");

    // update the selected value on the bean and ppr the component
    setEmployeesSet(Collections.singleton(value));
    RequestContext.getCurrentInstance().addPartialTarget(event.getComponent());
  }

  /**
   * Updates the dataUrl of the searchSection component
   */
  public void updateDataUrl(ActionEvent event)
  {
    // Ideally the following three lines aren't required. But since I'm trying to show
    // both client and server data url changes on the same searchSection instance,
    // clear the value set by client via delta so that the value from this managed bean is picked up.
    RichInputSearch inputSearch = _getParentInputSearch(event.getComponent());
    RichSearchSection searchSection = (RichSearchSection) inputSearch.getChildren().get(0);
    searchSection.setDataUrl(null);

    _dataUrl = "/rest/employees?cache=etag&limit=" +
               ((int) Math.floor(Math.random() * DemoSearchModel.DEFAULT_ROW_LIMIT));
  }

  /**
   * Converts the each member of the raw suggestion data to appropriate type
   */
  private static Map<String, Object> _convertRawRowData(Map<String, Object> rowData)
  {
    Map<String, Object> convertedRowData = new HashMap<String, Object>();
    for (String key : rowData.keySet())
    {
      Object value = rowData.get(key);
      if ("id".equals(key) )
      {
        value = ((Number) value).intValue();
      }
      else if ("hireDate".equals(key) || "dateOfBirth".equals(key))
      {
        try
        {
          value = _DF.parse((String) value);
        }
        catch (ParseException e)
        {
          e.printStackTrace();
        }
      }
      else if ("genderCode".equals(key))
      {
        value = ((String) value).charAt(0);
      }
      convertedRowData.put(key, value);
    }
    return Collections.unmodifiableMap(convertedRowData);
  }

  private static RichInputSearch _getParentInputSearch(UIComponent component)
  {
    while (component != null && !(component instanceof RichInputSearch))
    {
      component = component.getParent();
    }
    return (RichInputSearch) component;
  }

  private Set<Integer> _employeesSet = Collections.emptySet();
  private Set<Integer> _employeesSet2 = Collections.emptySet();
  private Set<Integer> _employeesSet3 = Collections.emptySet();
  private String _dataUrl = "/rest/employees?cache=etag&limit=" + DemoSearchModel.DEFAULT_ROW_LIMIT;
  private static final DateFormat _DF = new SimpleDateFormat("MMM dd, yyyy");
  private static final long serialVersionUID = -779757308447336634L;
}
