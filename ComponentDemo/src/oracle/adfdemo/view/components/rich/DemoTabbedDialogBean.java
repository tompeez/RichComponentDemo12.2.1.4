package oracle.adfdemo.view.components.rich;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.bean.PropertyKey;
import org.apache.myfaces.trinidad.component.UIXComponent;
import org.apache.myfaces.trinidad.event.LaunchEvent;


public class DemoTabbedDialogBean implements Serializable
{
  private Person _person = null;
  private int currentRowid = -1;
  private String readOnly = Boolean.FALSE.toString();

  /**
   * @param person selected person from the mock memory datastore
   */
  public void setPerson(Person person)
  {
    _person = person;
  }

  /**
   * @return selected person from the mock memory datastore
   */
  public Person getPerson()
  {
    return _person;
  }

  /**
   * Persons loaded from the <code>_mockDatastore</code>.
   */
  private List<Person> _persons = new ArrayList<Person>();

  /**
   * @return subset of the person object used to populate the table.
   */
  public List<Person> getPersons()
  {
    if (_persons.size() == 0)
    {
      for (int i = 0; i < _mockDatastore.length; i++)
      {
        // loads a ghost person
        _persons.add(_createPerson(i, true));
      }
    }
    return _persons;
  }


  /**
   * Mock data that can be updated.
   */
  private String[][] _mockDatastore =
  {
    { "Shaggy", "William", "Rogers", "6743 Tiger Walk", "", "Martinsville", 
      "IL", "62442", "1-217-493-0312", "1-217-493-0311", 
      "shaggy@cartoon.network.com", "8675309", "A", "Detective" },
    { "Freddy", "Randolph", "Jones", "1215 E Briarwood Av", "", 
      "Englewood", "CO", "80111", "1-720-444-80123", "1-303-444-80123", 
      "freddy@cartoon.network.com", "8675310", "A", "Detective" },
    { "Daphne", "Ann", "Blake", "9579 S University Blvd St.", 
      "PO Box 1234", "Highlands Ranch", "CO", "80126", "1-720-493-1234",

      "1-303-543-7854", "daphne@cartoon.network.com", "8675311", "A", 
      "Detective" },
    { "Velma", "Sue", "Dinkley", "1215 E Briarwood Av", "", "Englewood", 
      "CO", "80111", "1-720-444-80123", "1-303-494-1234", 
      "velma@cartoon.network.com", "8675310", "A", "Detective" },
    { "Scooby", "Doo", "Rogers", "6743 Tiger Walk", "", "Martinsville", 
      "IL", "62442", "1-217-493-0312", "1-217-493-0311", 
      "scooby@cartoon.network.com", "8675309", "A", "Detective" }, };

  /**
   * Dialog listener callback that simulates handling data collected by the
   * dialog.
   *
   * @param event dialog event 
   */
  public void dialogListener(DialogEvent event)
  {
    _saveCurrentPerson();
  }


  /**
   * Callback invoked when a popup is opened.  Its purpose here is to populate
   * the selected person from the mock data store using a key identifier. The
   * key id is captured as an attribute of the launching source component.
   * 
   * @param event popup fetch event
   */
  public void launchListener(PopupFetchEvent event)
  {
    FacesContext context = FacesContext.getCurrentInstance();

    UIComponent source = 
      context.getViewRoot().findComponent(event.getLaunchSourceClientId());
    if (source == null)
    {
      return;
    }

    int rowid = 
      Integer.parseInt((String) source.getAttributes().get("rowid"));
    // select from the mock data store and popuplate the current person
    setCurrentRowid(rowid);
  }

  /**
   * Sets the row index and sets the corresponding person.
   * @param currentRowid selected row index
   */
  public void setCurrentRowid(int currentRowid)
  {
    this.currentRowid = currentRowid;
    setPerson(_createPerson(currentRowid, false));
  }

  /**
   * @return selected row index
   */
  public int getCurrentRowid()
  {
    return currentRowid;
  }


  /**
   * Saves current row by updating the memory datastore.
   */
  private void _saveCurrentPerson()
  {
    if (currentRowid > -1 && currentRowid < _persons.size())
    {
      Person person = getPerson();
      _mockDatastore[currentRowid][0] = person.getFirstName();
      _mockDatastore[currentRowid][1] = person.getMiddleName();
      _mockDatastore[currentRowid][2] = person.getLastName();
      _mockDatastore[currentRowid][3] = 
          person.getContactInfo().getAddress().getAddress1();
      _mockDatastore[currentRowid][4] = 
          person.getContactInfo().getAddress().getAddress2();
      _mockDatastore[currentRowid][5] = 
          person.getContactInfo().getAddress().getCity();
      _mockDatastore[currentRowid][6] = 
          person.getContactInfo().getAddress().getState();
      _mockDatastore[currentRowid][7] = 
          person.getContactInfo().getAddress().getZip();
      _mockDatastore[currentRowid][8] = 
          person.getContactInfo().getPhoneEmail().getCell();
      _mockDatastore[currentRowid][9] = 
          person.getContactInfo().getPhoneEmail().getHome();
      _mockDatastore[currentRowid][10] = 
          person.getContactInfo().getPhoneEmail().getEmail();
      _mockDatastore[currentRowid][11] = 
          person.getEmployeeInfo().getEmpId();
      _mockDatastore[currentRowid][12] = 
          person.getEmployeeInfo().getGrade();
      _mockDatastore[currentRowid][13] = 
          person.getEmployeeInfo().getCategory();

    }

    _persons.clear();
  }

  /** 
   * @param rowId index into the <code>_mockDatastore</code>
   * @param ghost if <code>true</code> only populate a subset of person
   * @return return Person corresponding to the rowId
   */
  private Person _createPerson(int rowId, boolean ghost)
  {
    Person person = new Person();
    person.setRowId(rowId);
    person.setFirstName(_mockDatastore[rowId][0]);
    person.setMiddleName(_mockDatastore[rowId][1]);
    person.setLastName(_mockDatastore[rowId][2]);
    person.getContactInfo().getPhoneEmail().setEmail(_mockDatastore[rowId][10]);
    person.getContactInfo().getAddress().setAddress1(_mockDatastore[rowId][3]);

    if (!ghost)
    {
      person.getContactInfo().getAddress().setAddress2(_mockDatastore[rowId][4]);
      person.getContactInfo().getAddress().setCity(_mockDatastore[rowId][5]);
      person.getContactInfo().getAddress().setState(_mockDatastore[rowId][6]);
      person.getContactInfo().getAddress().setZip(_mockDatastore[rowId][7]);
      person.getContactInfo().getPhoneEmail().setCell(_mockDatastore[rowId][8]);
      person.getContactInfo().getPhoneEmail().setHome(_mockDatastore[rowId][9]);
      person.getEmployeeInfo().setEmpId(_mockDatastore[rowId][11]);
      person.getEmployeeInfo().setGrade(_mockDatastore[rowId][12]);
      person.getEmployeeInfo().setCategory(_mockDatastore[rowId][13]);
    }
    return person;
  }

  /**
   * @param readOnly used to determine if the input components on the dialog can
   * be updated
   */
  public void setReadOnly(String readOnly)
  {
    this.readOnly = readOnly;
  }

  /**
   * @return returns the literal "true" or "false" used to determine if the input 
   * components on the dialog can be updated
   */
  public String getReadOnly()
  {
    return readOnly;
  }


  /**
   * Mock person value object.
   */
  public static final class Person
  {

    private String firstName = null;
    private String middleName = null;
    private String lastName = null;
    private int rowId = -1;

    private Employee employeeInfo = null;
    private Contact contactInfo = null;

    public void setFirstName(String firstName)
    {
      this.firstName = firstName;
    }

    public String getFirstName()
    {
      return firstName;
    }

    public void setMiddleName(String middleName)
    {
      this.middleName = middleName;
    }

    public String getMiddleName()
    {
      return middleName;
    }

    public void setLastName(String lastName)
    {
      this.lastName = lastName;
    }

    public String getLastName()
    {
      return lastName;
    }

    public String getFullName()
    {
      StringBuilder buff = new StringBuilder();
      buff.append(lastName).append(", ").append(firstName).append(" ").append(middleName);
      return buff.toString();
    }

    public void setRowId(int rowId)
    {
      this.rowId = rowId;
    }

    public int getRowId()
    {
      return rowId;
    }

    public void setEmployeeInfo(Employee employeeInfo)
    {
      this.employeeInfo = employeeInfo;
    }

    public Employee getEmployeeInfo()
    {
      if (employeeInfo == null)
      {
        employeeInfo = new Employee();
      }
      return employeeInfo;
    }

    public void setContactInfo(Contact contactInfo)
    {
      this.contactInfo = contactInfo;
    }

    public Contact getContactInfo()
    {
      if (contactInfo == null)
      {
        contactInfo = new Contact();
      }
      return contactInfo;
    }


  }

  /**
   * Mock contact value object.
   */
  public static final class Contact
  {
    private Address address = null;
    private PhoneEmail phoneEmail = null;

    public void setAddress(Address address)
    {
      this.address = address;
    }

    public Address getAddress()
    {
      if (address == null)
      {
        address = new Address();
      }
      return address;
    }

    public void setPhoneEmail(PhoneEmail phoneEmail)
    {
      this.phoneEmail = phoneEmail;
    }

    public PhoneEmail getPhoneEmail()
    {
      if (phoneEmail == null)
      {
        phoneEmail = new PhoneEmail();
      }
      return phoneEmail;
    }
  }

  /**
   * Mock address value object.
   */
  public static final class Address
  {
    private String address1 = null;
    private String address2 = null;
    private String city = null;
    private String state = null;
    private String zip = null;

    public void setAddress1(String address1)
    {
      this.address1 = address1;
    }

    public String getAddress1()
    {
      return address1;
    }

    public void setAddress2(String address2)
    {
      this.address2 = address2;
    }

    public String getAddress2()
    {
      return address2;
    }

    public void setCity(String city)
    {
      this.city = city;
    }

    public String getCity()
    {
      return city;
    }

    public void setState(String state)
    {
      this.state = state;
    }

    public String getState()
    {
      return state;
    }

    public void setZip(String zip)
    {
      this.zip = zip;
    }

    public String getZip()
    {
      return zip;
    }
  }

  /**
   * Mock Phone and Email value object.
   */
  public static final class PhoneEmail
  {
    private String home = null;
    private String cell = null;
    private String email = null;

    public void setHome(String home)
    {
      this.home = home;
    }

    public String getHome()
    {
      return home;
    }

    public void setCell(String cell)
    {
      this.cell = cell;
    }

    public String getCell()
    {
      return cell;
    }

    public void setEmail(String email)
    {
      this.email = email;
    }

    public String getEmail()
    {
      return email;
    }
  }

  /**
   * Mock employee value object.
   */
  public static final class Employee
  {
    private String empId = null;
    private String category = null;
    private String grade = null;

    public void setEmpId(String empId)
    {
      this.empId = empId;
    }

    public String getEmpId()
    {
      return empId;
    }

    public void setCategory(String category)
    {
      this.category = category;
    }

    public String getCategory()
    {
      return category;
    }

    public void setGrade(String grade)
    {
      this.grade = grade;
    }

    public String getGrade()
    {
      return grade;
    }
  }
  
  private static final long serialVersionUID = 1L;    
}
