package oracle.adfdemo.view.components.rich;

public class DemoEmp
{
  
  public void setName(String name)
  {
    _name = name;
  }
  
  public String getName()
  {
    return _name;
  }
  
  public void setGrade(String grade)
  {
    _grade = grade;
  }  
  
  public void setLocation(String loc)
  {
    _location = loc;
  }
  
  public String getGrade()
  {
    return _grade;
  }
  
  public String getLocation()
  {
    return _location;
  }
  
  
  public String getCategory()
  {
     return _category;
  }
  
  public void setCategory(String categ)
  {
     _category = categ;
  }

  public void setId(String id)
  {
    _id = id;
  }

  public String getId()
  {
    return _id;
  }

  public void setPeerFeedback(String peerFeedback)
  {
    _peerFeedback = peerFeedback;
  }

  public String getPeerFeedback()
  {
    return _peerFeedback;
  }
  
  private String _id;
  private String _name;
  private String _grade;
  private String _location;
  private String _category;
  private String _peerFeedback;

} 
