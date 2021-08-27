package oracle.adfdemo.view.feature.rich.hv.data;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.TreeModel;


public class EmployeeData {
  final static EmployeeNode sKing = new EmployeeNode("Steven", "King", "Vice President", "(650) 555-0100", "10"
                                            , "sking@example.com", "200 Acme Parkway", "Redwood City"
                                            , "CA", "40", null);
  final static EmployeeNode nEvans = new EmployeeNode("Nina", "Evans", "Product Management Director", "(650) 555-0101", "1"
                                            , "nevans@example.com", "600 Acme Parkway", "Redwood City"
                                            , "CA", "35", sKing);
  final static EmployeeNode cDavies = new EmployeeNode("Curtis", "Davies", "Principle Product Manager", "(650) 555-0102", "12"
                                            , "cdavies@example.com", "300 Acme Parkway", "Redwood City"
                                            , "CA", "5", nEvans);
  final static EmployeeNode dLorentz = new EmployeeNode("Diana", "Lorentz", "Principle Product Manager", "(650) 555-0103", "2"
                                            , "dlorentz@example.com", "300 Acme Parkway", "Redwood City"
                                            , "CA", "8", nEvans);
  
  final static EmployeeNode jChen = new EmployeeNode("Julia", "Chen", "Principle Product Manager", "(650) 555-0104", "7"
                                            , "jchen@example.com", "300 Acme Parkway", "Redwood City"
                                            , "CA", "4", nEvans);
  
  final static EmployeeNode lBissot = new EmployeeNode("Laura", "Bissot", "Senior Product Manager", "(650) 555-0105", "4"
                                            , "lBissot@example.com", "200 Main Street", "Burlington"
                                            , "MA", "9", nEvans);
  
  final static EmployeeNode aHunold = new EmployeeNode("Alex", "Hunold", "Software Dev Senior Director", "(650) 555-0106", "11"
                                            , "ahunold@example.com", "100 Acme Parkway", "Redwood City"
                                            , "CA", "30", sKing);
  final static EmployeeNode dRaphaely = new EmployeeNode("Dan", "Raphaely", "Architect", "(650) 555-0108", "14"
                                            , "draphaely@example.com", "100 Acme Parkway", "Redwood City"
                                            , "CA", "8", aHunold);
  final static EmployeeNode sAustin = new EmployeeNode("Simon", "Austin", "Principal Software Engineer", "(650) 555-0107", "17"
                                            , "saustin@example.com", "200 Main Street", "Burlington"
                                            , "MA", "9", aHunold);
  
  final static EmployeeNode dYong = new EmployeeNode("David", "Yong", "Senior Software Engineer", "(650) 555-0109", "18"
                                            , "dyong@example.com", "200 Main Street", "Burlington"
                                            , "MA", "6", aHunold);
  
  final static EmployeeNode jMarlow = new EmployeeNode("James", "Marlow", "Marketing Director", "(650) 555-0110", "15"
                                            , "jmarlow@example.com", "100 Acme Parkway", "Redwood City"
                                            , "CA", "20", sKing);
  final static EmployeeNode lWhalen = new EmployeeNode("Lucy", "Whalen", "Principle Marketing Manager", "(650) 555-0112", "3"
                                            , "lwhalen@example.com", "300 Acme Parkway", "Redwood City"
                                            , "CA", "2", jMarlow);
  
  final static EmployeeNode kSullivan = new EmployeeNode("Kelly", "Sullivan", "Principle Marketing Manager", "(650) 555-0113", "6"
                                            , "ksullivan@example.com", "300 Acme Parkway", "Redwood City"
                                            , "CA", "4", jMarlow);
  
  final static EmployeeNode aWalsh = new EmployeeNode("Alaina", "Walsh", "Principle Marketing Manager", "(650) 555-0114", "8"
                                            , "awalsh@example.com", "300 Acme Parkway", "Redwood City"
                                            , "CA", "14", jMarlow);
  
  final static EmployeeNode jWu = new EmployeeNode("Jon", "Wu", "Senior Marketing Manager", "(650) 555-0115", "16"
                                            , "jwu@example.com", "300 Acme Parkway", "Redwood City"
                                            , "CA", "18", jMarlow);
                     
  final static EmployeeNode nGreen = new EmployeeNode("Nancy", "Green", "Senior Documents Manager", "(650) 555-0111", "9"
                                            , "ngreen@example.com", "300 Acme Parkway", "Redwood City"
                                            , "CA", "15", sKing);
  final static EmployeeNode sHiggins = new EmployeeNode("Susan", "Higgins", "Principle Technical Writer", "(650) 555-0114", "5"
                                            , "shiggins@example.com", "300 Acme Parkway", "Redwood City"
                                            , "CA", "8", nGreen);
  public static TreeModel newModel(){
    return new ChildPropertyTreeModel(createTree(), "children");
  }

  private static EmployeeNode createTree() {
    return sKing;                   
  }
  
  public static EmployeeNode getRoot(){
    return sKing;
  }  
} 
