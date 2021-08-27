/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

public class Person
{
  public Person(int id)
  {
    _id = id; 
  }
  public Integer getId()
  {
    return _id;
  }
  public Integer getAge()
  {
    return _age;
  }
  public void setAge(Integer age)
  {
    _age = age;
  }
  public String getFirstname()
  {
    return _firstname;
  }
  public void setFirstname(String firstname)
  {
    _firstname = firstname;
  }
  public String getSecondname()
  {
    return _secondname;
  }
  
  public void setSecondname(String secondname)
  {
    _secondname = secondname;
  }

  private String _firstname;
  private String _secondname;
  private Integer _age;
  private Integer _id;

}