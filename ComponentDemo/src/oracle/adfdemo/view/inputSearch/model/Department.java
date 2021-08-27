package oracle.adfdemo.view.inputSearch.model;

import java.io.Serializable;

import java.util.List;

public class Department 
  extends SearchModelBase
  implements Comparable<Department>, Serializable
{
  public Department(
    Integer     id, 
    String      name, 
    String      location, 
    Integer     mgrId, 
    List<SearchModelChangeListener> listeners) 
  {
    super(listeners);
    
    setId(id);
    setName(name);
    setLocation(location);
    setMgrId(mgrId);
  }

  public void setId(Integer id) 
  {
    setProperty("id", id);
  }

  public Integer getId() 
  {
    return (Integer)getProperty("id");
  }

  public void setName(String name) 
  {
    setProperty("name", name);
  }

  public String getName() 
  {
    return (String)getProperty("name");
  }

  public void setLocation(String location) 
  {
    setProperty("location", location);
  }

  public String getLocation() 
  {
    return (String)getProperty("location");
  }

  public void setMgrId(Integer mgrId) 
  {
    setProperty("mgrId", mgrId);
  }

  public Integer getMgrId() 
  {
    return (Integer)getProperty("mgrId");
  }

  @Override
  public int compareTo(Department other) 
  {
    return getName().compareTo(other.getName());
  }

  private static final long serialVersionUID = 6018360264703263119L;
}
