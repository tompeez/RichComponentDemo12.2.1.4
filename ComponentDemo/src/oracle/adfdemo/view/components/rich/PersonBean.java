/** Copyright (c) 2008, 2015, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import oracle.adf.view.rich.render.ClientEvent;

import org.apache.myfaces.trinidad.component.UIXOutput;
import org.apache.myfaces.trinidad.component.UIXPanel;

public class PersonBean implements Serializable
{

  public PersonBean()
  {
    _persons = new SelectItem[3];

    Person p1 = new Person(0);
    p1.setFirstname("Homer");
    p1.setSecondname("Meyers");
    p1.setAge(53);
    _allPersons.put(p1.getId(), p1);
    
    Person p2 = new Person(1);
    p2.setFirstname("Jim");
    p2.setSecondname("Johnson");
    p2.setAge(33);
    _allPersons.put(p2.getId(), p2);

    Person p3 = new Person(2);
    p3.setFirstname("Osacar");
    p3.setSecondname("Balmer");
    p3.setAge(65);
    _allPersons.put(p3.getId(), p3);

    _persons[0] = new SelectItem(p1.getId(), p1.getSecondname());
    _persons[1] = new SelectItem(p2.getId(), p2.getSecondname());
    _persons[2] = new SelectItem(p3.getId(), p3.getSecondname());
    
  }
  
  public void doCustomEvent(ClientEvent event)
  {
    String personId = (String) event.getParameters().get("value");
    if(personId != null)
    {
      _selectedPerson = _allPersons.get(Integer.parseInt(personId));
    }
    FacesContext context = FacesContext.getCurrentInstance();

    ExtendedRenderKitService erks =
      Service.getRenderKitService(context,
                                  ExtendedRenderKitService.class);
    erks.addScript(context,
                   "showPopup();");

  }
  
  public List<Person> getManyListValue()
  {
    return _manylistvalue;
  }

  public void setManyListValue(List<Person> manylistvalue)
  {
    _manylistvalue = manylistvalue;
  }

  public Person getSelectedPerson()
  {
    return _selectedPerson;
  }
  
  public SelectItem[] getPersons()
  {
    return _persons;
  }
  
  private Map<Integer, Person> _allPersons = new HashMap<Integer, Person>();
  private List<Person> _manylistvalue;
  private SelectItem[] _persons;
  private Person _selectedPerson;

  public void checkboxValueChanged(ValueChangeEvent valueChangeEvent)
  {
    List<Integer> newValue = (List<Integer>)valueChangeEvent.getNewValue();
    List<Integer> oldValue = (List<Integer>)valueChangeEvent.getOldValue();
    
    // figure out what has changed, and set that to the selectedPerson
    FacesContext context = FacesContext.getCurrentInstance();
    int personId = personChanged(newValue, oldValue);
    _selectedPerson = _allPersons.get(personId);

    ExtendedRenderKitService erks =
      Service.getRenderKitService(context,
                                  ExtendedRenderKitService.class);
    erks.addScript(context,
                   "showMyPopup();");
  }
  
  private int personChanged(List<Integer> newValue, List<Integer> oldValue)
  {
    // figure out what person has changed.
    if (newValue != null && newValue.size() > 0 && oldValue == null)
      return newValue.get(0);
    if (newValue == null && oldValue != null)
      return oldValue.get(0);
    
    if (newValue != null && oldValue != null)
    {

      if (newValue.size() > oldValue.size())
      {
        // find the newValue that isn't in the oldValue
        for (Integer i : newValue)
        {
          if (!(oldValue.contains(i)))
            return i;
        }

      }
      else if (oldValue.size() > newValue.size())
      {
        // find the oldValue that isn't in the newValue
        for (Integer i : oldValue)
        {
          if (!(newValue.contains(i)))
            return i;
        }
      }
    }
    return 0;
    
  }
  
  public void popupClosedEvent(ClientEvent event)
  {
    System.out.println("serverListener invoked from " + event.getComponent() + " for event type " + event.getType());
    if (Boolean.TRUE.equals(event.getParameters().get("skyIsBlue")))
    {
      System.out.println("the sky is blue");
    }
    _counter++;
    _outputTextValue = "Some server side calculation on popup close goes here. Counter is: " + _counter;
    AdfFacesContext afc = AdfFacesContext.getCurrentInstance();
    afc.addPartialTarget(getOutputText());
    
  }
  
  /**
   * Returns the outputText component.
   * @return the outputText component
   */
  public UIXOutput getOutputText()
  {
    return _outputText;
  }

  /**
   * Specifies the outputText component.
   * @param outputText
   */
  public void setOutputText(UIXOutput outputText)
  {
    _outputText = outputText;
  }
  
  public String getOutputTextValue()
  {
    return _outputTextValue;
  }
  
  private static final long serialVersionUID = 1L;    

  private UIXOutput _outputText;
  private String _outputTextValue;
  private static int _counter = 0;
}
