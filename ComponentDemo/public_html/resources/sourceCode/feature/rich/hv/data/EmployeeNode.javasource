package oracle.adfdemo.view.feature.rich.hv.data;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmployeeNode {
  public final static String FirstName = "FirstName";
  public final static String LastName = "LastName";
  public final static String Title = "Title";
  public final static String WorkPhone = "WorkPhone";
  public final static String PersonId = "PersonId";
  public final static String Email = "Email";
  public final static String WorkAddr = "WorkAddr";
  public final static String WorkCity = "WorkCity";
  public final static String WorkState = "WorkState";
  public final static String YearsWorked = "YearsWorked";
  private final Map<String, String> attributes =
      new HashMap<String, String>();
  public static List<String> attribute_names = new ArrayList<String>();
  static {
    attribute_names.add(FirstName);
    attribute_names.add(LastName);
    attribute_names.add(Title);
    attribute_names.add(WorkPhone);
    attribute_names.add(Email);
    attribute_names.add(WorkAddr);
    attribute_names.add(WorkCity);
    attribute_names.add(WorkState);
    attribute_names.add(YearsWorked);
    attribute_names.add(PersonId);
  }
  
  private final EmployeeNode parent;

  private final List<EmployeeNode> children = new ArrayList<EmployeeNode>();
  
  public EmployeeNode(String FirstName, String LastName, String Title, String WorkPhone
                      , String PersonId, String Email, String WorkAddr1, String WorkCity
                      , String WorkState, String YearsWorked, EmployeeNode parent) {
    attributes.put(this.FirstName, FirstName);
    attributes.put(this.LastName, LastName);
    attributes.put(this.Title, Title);
    attributes.put(this.WorkPhone, WorkPhone);
    attributes.put(this.PersonId, PersonId);
    attributes.put(this.Email, Email);
    attributes.put(this.WorkAddr, WorkAddr1);
    attributes.put(this.WorkCity, WorkCity);
    attributes.put(this.WorkState, WorkState);
    attributes.put(this.YearsWorked, YearsWorked);
    this.parent = parent;
    if(parent != null){
      parent.addChild(this);
    }
    
  }

  
  public void addChild(EmployeeNode child) {
    children.add(child);
  }
  
  public void addChildren(List<EmployeeNode> children) {
    children.addAll(children);
  }

  public List<EmployeeNode> getChildren() {
    return children;
  }
  
  @Override
  public String toString() {
    return Title + ": " + FirstName + " " + LastName;
  }
  
  public String getFirstName() {
    return attributes.get(FirstName);
  }
  

  public String getLastName() {
    return attributes.get(LastName);
  }

  public String getTitle() {
    return attributes.get(Title);
  }

  public String getWorkPhone() {
    return attributes.get(WorkPhone);
  }

  public String getPersonId() {
    return attributes.get(PersonId);
  }

  public String getEmail() {
    return attributes.get(Email);
  }

  public String getWorkAddr1() {
    return attributes.get(WorkAddr);
  }

  public String getWorkCity() {
    return attributes.get(WorkCity);
  }

  public String getWorkState() {
    return attributes.get(WorkState);
  }

  public String getYearsWorked() {
    return attributes.get(YearsWorked);
  }

  public EmployeeNode getParent() {
    return parent;
  }
  
  public Boolean getHasChildren(){
    return (children.size()!=0);
  }
  public String getAttribute(String attr) {
    return attributes.get(attr);
  }
  public int getOrgSize(){            
    // If no children, return 1
    if(children == null)
      return 1;
            
    // Otherwise add up the reports by child
    int count = 1;
    for(EmployeeNode emp : children) {
      count += emp.getOrgSize();
    }
            
    return count;
  }
}

