/** Copyright (c) 2012, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.tags.foreachdata;

import java.io.Serializable;

public class Employee
  implements Serializable
{
  public Employee(
    int    id,
    String firstName,
    String lastName,
    String username,
    double salary)
  {
    _id = id;
    _firstName = firstName;
    _lastName = lastName;
    _salary = salary;
    _username = username;
  }

  public void setFirstName(String firstName)
  {
    _firstName = firstName;
  }

  public String getFirstName()
  {
    return _firstName;
  }

  public void setLastName(String lastName)
  {
    _lastName = lastName;
  }

  public String getLastName()
  {
    return _lastName;
  }

  public void setSalary(double salary)
  {
    _salary = salary;
  }

  public double getSalary()
  {
    return _salary;
  }

  public int getId()
  {
    return _id;
  }

  public void setUsername(String username)
  {
    _username = username;
  }

  public String getUsername()
  {
    return _username;
  }

  private final int _id;
  private String _username;
  private String _firstName;
  private String _lastName;
  private double _salary;

  @SuppressWarnings("compatibility:-660894831682208206")
  private static final long serialVersionUID = 1L;
}