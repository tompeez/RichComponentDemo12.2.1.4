package oracle.adfdemo.view.inputSearch.model;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employee 
  extends SearchModelBase
  implements Comparable<Employee>, Serializable
{
  public Employee(
    Integer     id, 
    String      fName, 
    String      lName, 
    String      jobTitle, 
    String      email,
    char        genderCode,
    Date        hireDate, 
    Date        dateOfBirth, 
    String      deptName, 
    String      location, 
    String      profileKey,
    String      tags,
    List<SearchModelChangeListener> listeners) 
  {
    super(listeners);
    
    this.setId(id);
    this.setFirstName(fName);
    this.setLastName(lName);
    this.setJobTitle(jobTitle);
    this.setEmail(email);
    this.setGenderCode(genderCode);
    this.setHireDate(hireDate);
    this.setDateOfBirth(dateOfBirth);
    this.setDeptName(deptName);
    this.setDeptLocation(location);
    this.setProfileKey(profileKey);
    this.setTags(tags);
  }

  public void setId(Integer id) 
  {
    setProperty("id", id);
  }

  public Integer getId() 
  {
    return (Integer)getProperty("id");
  }

  public void setFirstName(String fName) 
  {
    setProperty("fName", fName);
  }

  public String getFirstName() 
  {
    return (String)getProperty("fName");
  }

  public void setLastName(String lName) 
  {
    setProperty("lName", lName);
  }

  public String getLastName() 
  {
    return (String)getProperty("lName");
  }

  public void setJobTitle(String jobTitle) 
  {
    setProperty("jobTitle", jobTitle);
  }

  public String getJobTitle() 
  {
    return (String)getProperty("jobTitle");
  }

  public void setEmail(String email) 
  {
    setProperty("email", email);
  }

  public String getEmail() 
  {
    return (String)getProperty("email");
  }

  public void setGenderCode(Character genderCode) 
  {
    setProperty("genderCode", genderCode);
  }

  public Character getGenderCode() 
  {
    return (Character)getProperty("genderCode");
  }

  public void setHireDate(Date hireDate) 
  {
    setProperty("hireDate", hireDate);
  }

  public Date getHireDate() 
  {
    return (Date)getProperty("hireDate");
  }

  public void setDateOfBirth(Date dateOfBirth) 
  {
    setProperty("dateOfBirth", dateOfBirth);
  }

  public Date getDateOfBirth() 
  {
    return (Date)getProperty("dateOfBirth");
  }

  public void setDeptName(String deptName) 
  {
    setProperty("deptName", deptName);
  }

  public String getDeptName() 
  {
    return (String)getProperty("deptName");
  }

  public void setDeptLocation(String deptLocation) 
  {
    setProperty("deptLocation", deptLocation);
  }

  public String getDeptLocation() 
  {
    return (String)getProperty("deptLocation");
  }

  public void setProfileKey(String profileKey) 
  {
     setProperty("profileKey", profileKey);
  }

  public String getProfileKey() 
  {
    return (String)getProperty("profileKey");
  }

  public void setTags(String tags) 
  {
     setProperty("tags", tags);
  }

  public String getTags() 
  {
    return (String)getProperty("tags");
  }

  @Override
  public int compareTo(Employee other) 
  {
    String otherName = other.getFirstName() + other.getLastName(); 
    String selfName = this.getFirstName() + this.getLastName(); 
    
    return selfName.compareTo(otherName);
  }

  private static final long serialVersionUID = 564203398438289509L;
}
