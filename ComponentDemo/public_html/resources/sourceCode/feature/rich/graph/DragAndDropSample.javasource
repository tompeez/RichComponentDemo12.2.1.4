/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/DragAndDropSample.java /main/4 2010/03/24 07:15:48 hzhang Exp $ */

/* Copyright (c) 2009, 2010, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hzhang      06/16/09 - Creation
 */

package oracle.adfdemo.view.feature.rich.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import oracle.adf.view.faces.bi.component.graph.DataContext;
import oracle.adf.view.faces.bi.component.graph.DataSelection;
import oracle.adf.view.faces.bi.component.graph.GraphSelection;
import oracle.adf.view.faces.bi.component.graph.GraphSelectionSet;
import oracle.adf.view.faces.bi.dnd.GraphDragContext;
import oracle.adf.view.faces.bi.dnd.GraphDropSite;
import oracle.adf.view.faces.bi.model.DataModel;
import oracle.adf.view.faces.bi.model.GraphDataModel;
import oracle.adf.view.faces.bi.model.KeyMap;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import oracle.dss.dataView.LocalXMLDataSource;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.RowKeySet;


/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/DragAndDropSample.java /main/4 2010/03/24 07:15:48 hzhang Exp $
 *  @author  hzhang
 *  @since   release specific (what release of product did this appear in)
 */
public class DragAndDropSample {    
  // Fields and Methods for the "Drag Within Graph" Demo
  
  private Object[] m_columnLabels = {"Column 1", "Column 2", "Column 3", "Column 4", "Column 5", " Column 6"};
  private Object[] m_rowLabels = {"Series 1", "Series 2"};
  
  
  private DataModel m_bubbleDragAndDropModel;
  private Double[][] m_bubbleData = {{50.0, 70.0}, {32.0, 50.0}, {10.0, 20.0}, {15.0, 25.0}, {35.0, 60.0}, {40.0, 10.0}};
  public DataModel getBubbleDragAndDropModel() {
    if(m_bubbleDragAndDropModel == null) {
      // First set up the initial data source
      LocalXMLDataSource dataSource = new LocalXMLDataSource(m_columnLabels, m_rowLabels, m_bubbleData);
      m_bubbleDragAndDropModel = new GraphDataModel(dataSource);
    }
    return m_bubbleDragAndDropModel;
  }

  
  public DnDAction bubbleDropListener(DropEvent event) {
    StringBuilder eventInfo = new StringBuilder();
    
    // Get the SelectionSet from the transferable
    Transferable transferable = event.getTransferable();
    GraphSelectionSet selectionSet = transferable.getData(GraphSelectionSet.class);
    
    // Get the x,y values before the drop from the drag context object
    GraphDragContext dragContext = transferable.getData(GraphDragContext.class) ;
    double oldX = dragContext.getX().doubleValue();
    double oldY = dragContext.getY().doubleValue();
    
    // Get the x,y values after the drop from the event's dropSite
    Map dropSite = (Map) event.getDropSite();
    double newX = (Double) dropSite.get(GraphDropSite.X_VALUE_KEY);
    double newY = (Double) dropSite.get(GraphDropSite.Y_VALUE_KEY);
    
    // Markers will be moved by the difference in x and y values.
    // If a single selection, the newX and newY could be used directly.
    double dx = newX - oldX;
    double dy = newY - oldY;
    for(GraphSelection selection : selectionSet) {
      // Now move the marker in the data source
      DataContext dataContext = (DataContext) selection.getDataContext();
      int groupIndex = dataContext.getGroupIndex();
      int seriesIndex = dataContext.getSeriesIndex();
      m_bubbleData[3*groupIndex][seriesIndex] += dx;
      m_bubbleData[3*groupIndex+1][seriesIndex] += dy;  
    }
    
    // If only a single object, write out its details
    if(selectionSet.size() == 1) {
      GraphSelection selection = selectionSet.iterator().next();
      if(selection instanceof DataSelection) {
        DataSelection ds = (DataSelection) selection;
        Set seriesKeySet = ds.getSeriesKey().keySet();
        for(Object key : seriesKeySet) {
          eventInfo.append(key).append(": ").append(ds.getSeriesKey().get((String)key));
        }
        
        List<KeyMap> groupKeys = ds.getGroupKeys();
        for(KeyMap groupKey : groupKeys) {
          Set groupKeySet = groupKey.keySet();
          for(Object key : groupKeySet) {
            eventInfo.append("<br>").append(key).append(": ").append(groupKey.get((String)key));
          }
        }
      }
    }
    else { // Multiple objects
      eventInfo.append("Multiple objects were dragged.");
    }
    
    // Write the coordinates of the drop from the event
    eventInfo.append("<br>").append("Drop Coords: " + event.getDropX() + ", " + event.getDropY());
    
    // Write the old/new x and y values
    eventInfo.append("<br>").append("<b>Moved from (" + roundDouble(oldX) + ", " + roundDouble(oldY) + ") to (" + roundDouble(newX) + ", " + roundDouble(newY) + ")<b/>");
    
    // Save as the value of the outputText
    m_dropInfo = eventInfo.toString();
    RequestContext.getCurrentInstance().addPartialTarget(m_dropLabel);

    // Store the update to the model
    m_bubbleDragAndDropModel = new GraphDataModel(new LocalXMLDataSource(m_columnLabels, m_rowLabels, m_bubbleData));

    return DnDAction.MOVE;
  }
  
  
  private static double roundDouble(double value) {
    return Math.round(value*10)/10;
  }
  
  private RichOutputFormatted m_dropLabel = new RichOutputFormatted();
  public RichOutputFormatted getDropLabel() {
    return m_dropLabel;
  }
  public void setDropLabel(RichOutputFormatted label) {
    m_dropLabel = label;
  }
  
  private String m_dropInfo = "Drag and drop a marker to see information here.";
  public String getDropInfo() {
    return m_dropInfo;
  }
  
  // Fields and Methods for the "Drag Between Graphs" Demo
  
  // Use the same random number generator for consistency
  private Random m_random = new Random(23);

    /**
     * Simple class to represent employee data.
     */
  public static class Employee {
    public final String name;
    private int performance;
    private int potential;
    private double salary;
    private double bonus;
    private int experience;
    
    public Employee(String name, Random r) {
      this.name = name;
      this.performance = r.nextInt(100);
      this.potential = r.nextInt(100);
      this.salary = r.nextInt(100000);
      this.bonus = r.nextInt(50000);
      this.experience = r.nextInt(20);
    }
    
    /**
     * Returns a copy of the employee with a new name.
     * @param name
     * @param e
     */
    public Employee(String name, Employee e) {
      this.name = name;
      this.performance = e.performance;
      this.potential = e.potential;
      this.salary = e.salary;
      this.bonus = e.bonus;
      this.experience = e.experience;
    }
    
    public String getName() {
        return name;
    }

    public int getPerformance() {
        return performance;
    }

    public int getPotential() {
        return potential;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getBonus() {
        return bonus;
    }

    public int getExperience() {
        return experience;
    }
  }
  
  /**
   * Converts the list of employees to a scatter data model plotting potential and performance.
   * @param employees
   * @return
   */
  private static DataModel createPerfDataModel(List<Employee> employees) {
    Object[][] data = new Object[employees.size()*2][1];
    Object[] colLabels = new Object[employees.size()*2];    
    Object[] rowLabels = {"Employees"};    
    
    for(int i=0; i<employees.size(); i++) {
      Employee emp = employees.get(i);
      data[i*2][0] = emp.performance;
      data[i*2+1][0] = emp.potential;
      colLabels[i*2] = emp.name;
      colLabels[i*2+1] = emp.name + "1";
    }
    
    LocalXMLDataSource dataSource = new LocalXMLDataSource(colLabels, rowLabels, data);
    return new GraphDataModel(dataSource);
  }
  
  /**
   * Converts the list of employees to a scatter data model plotting potential and performance.
   * @param employees
   * @return
   */
  private static DataModel createSalaryDataModel(List<Employee> employees) {
    Object[][] data = new Object[employees.size()*2][1];
    Object[] colLabels = new Object[employees.size()*2];    
    Object[] rowLabels = {"Employees"};    
    
    for(int i=0; i<employees.size(); i++) {
      Employee emp = employees.get(i);
      data[i*2][0] = emp.salary;
      data[i*2+1][0] = emp.bonus;
      colLabels[i*2] = emp.name;
      colLabels[i*2+1] = emp.name + "1";
    }
    
    LocalXMLDataSource dataSource = new LocalXMLDataSource(colLabels, rowLabels, data);
    return new GraphDataModel(dataSource);
  }
  
  private List<Employee> m_performanceList;
  private List<Employee> m_salaryList;
  
  public DataModel getPerformanceModel() {
    if(m_performanceList == null) {
      m_performanceList = new ArrayList<Employee>();
      m_performanceList.add(new Employee("John", m_random));
      m_performanceList.add(new Employee("Brian", m_random));
      m_performanceList.add(new Employee("Jonas", m_random));
      m_performanceList.add(new Employee("Bob", m_random));
      m_performanceList.add(new Employee("Matt", m_random));
      m_performanceList.add(new Employee("Amy", m_random));
      m_performanceList.add(new Employee("Gary", m_random));
      m_performanceList.add(new Employee("Michelle", m_random));
      m_performanceList.add(new Employee("David", m_random));
      m_performanceList.add(new Employee("Beverly", m_random));
      m_performanceList.add(new Employee("Will", m_random));
      m_performanceList.add(new Employee("Kevin", m_random));
    }
    return createPerfDataModel(m_performanceList);
  }
  
  public DataModel getSalaryModel() {
      if(m_salaryList == null) {
        m_salaryList = new ArrayList<Employee>();
        m_salaryList.add(new Employee("Peter", m_random));
        m_salaryList.add(new Employee("Clark", m_random));
        m_salaryList.add(new Employee("Bruce", m_random));
      }
      return createSalaryDataModel(m_salaryList);
  }
  
  public DnDAction betweenGraphDropListener(DropEvent event) {
    // Get the ComponentHandle from the transferable
    Transferable transferable = event.getTransferable();
    
    // Find the marker that was dragged.  Selection is disabled in this graph, so just get the first and only one.
    GraphSelectionSet selectionSet = transferable.getData(GraphSelectionSet.class);
    GraphSelection selection = selectionSet.iterator().next();
    
    Employee emp = findEmployee(m_performanceList, selection);
    if(emp == null)
      return DnDAction.NONE;
    
    Map<Object, Object> dropSite = (Map<Object, Object>) event.getDropSite();
    Number salary = (Number) dropSite.get(GraphDropSite.X_VALUE_KEY);
    Number bonus = (Number) dropSite.get(GraphDropSite.Y_VALUE_KEY);
    emp.setSalary(salary.doubleValue());
    emp.setBonus(bonus.doubleValue());
    
    // If the salary list doesn't already include the employee, add it
    if(!m_salaryList.contains(emp))
        m_salaryList.add(emp);

    return DnDAction.COPY;
  }
  
  /**
   * Returns the employee represented by the selection.
   * @param list the List of employees to search
   * @param selection
   * @return
   */
  private Employee findEmployee(List<Employee> list, GraphSelection selection) {
    if(!(selection instanceof DataSelection))
      return null;
    
    List<KeyMap> groupKeys = ((DataSelection)selection).getGroupKeys();
    for(KeyMap groupKey : groupKeys) {
      Set groupKeySet = groupKey.keySet();
      for(Object key : groupKeySet) {
        // Find the employee
        String name = groupKey.get((String)key);
        Employee emp = findEmployee(list, name);
        if(emp != null) {
          return emp;
        }
      }
    }
    return null;
  }
  
  private Employee findEmployee(List<Employee> list, String name) {
    for(Employee emp : list) {
      if(name.equals(emp.name))
        return emp;
    }
    return null;
  }
  
  // Fields and Methods for the "Drag Between Graph and Table" Demo
  
  /**
   * Converts the list of employees to a scatter data model plotting potential and performance.
   * @param employees
   * @return
   */
  private static DataModel createBubbleDataModel(List<Employee> employees) {
    Object[][] data = new Object[employees.size()*3][1];
    Object[] colLabels = new Object[employees.size()*3];    
    Object[] rowLabels = {"Employees"};    
    
    for(int i=0; i<employees.size(); i++) {
      Employee emp = employees.get(i);
      data[i*3][0] = emp.performance;
      data[i*3+1][0] = emp.salary;
      data[i*3+2][0] = emp.experience;
      colLabels[i*3] = emp.name;
      colLabels[i*3+1] = emp.name + "1";
      colLabels[i*3+2] = emp.name + "2";
    }
    
    LocalXMLDataSource dataSource = new LocalXMLDataSource(colLabels, rowLabels, data);
    return new GraphDataModel(dataSource);
  }
  
  private List<Employee> m_graphList;
  
  public DataModel getGraphModel() {
    if(m_graphList == null) {
      m_graphList = new ArrayList<Employee>();
      m_graphList.add(new Employee("Dan", m_random));
      m_graphList.add(new Employee("Ben", m_random));
      m_graphList.add(new Employee("Dave", m_random));
      m_graphList.add(new Employee("Chris", m_random));
      m_graphList.add(new Employee("Frank", m_random));
      m_graphList.add(new Employee("Jill", m_random));
      m_graphList.add(new Employee("Ray", m_random));
    }
    return createBubbleDataModel(m_graphList);
  }
  
  private List<Employee> m_tableModel;
  public List getTableModel() {
    if(m_tableModel == null) {
      m_tableModel = new ArrayList<Employee>();
      m_tableModel.add(new Employee("Brad", m_random));
      m_tableModel.add(new Employee("Derrick", m_random));
      m_tableModel.add(new Employee("Matt", m_random));
    }
    return m_tableModel;
  }
  
  public DnDAction fromGraphDropListener(DropEvent event) {
    // Get the ComponentHandle from the transferable
    Transferable transferable = event.getTransferable();
    GraphSelectionSet selectionSet = transferable.getData(GraphSelectionSet.class);
    
    // Now change each marker based on the DropEvent's proposed action
    DnDAction proposedAction = event.getProposedAction();
    for(GraphSelection selection : selectionSet) {
      Employee emp = findEmployee(m_graphList, selection);
      if(emp == null)
        return DnDAction.NONE;

      if(proposedAction == DnDAction.COPY) {
        m_tableModel.add(new Employee("Copy of " + emp.getName(), emp));  
      }
      else if(proposedAction == DnDAction.LINK) {
        m_tableModel.add(new Employee("Link to " + emp.getName(), emp));  
      }
      else if(proposedAction == DnDAction.MOVE) {
        m_graphList.remove(emp);
        m_tableModel.add(emp);
      }
    }
    
    RequestContext.getCurrentInstance().addPartialTarget(event.getDragComponent());
    
    return proposedAction;
  }
  
  public DnDAction fromTableDropListener(DropEvent event) {
    Transferable transferable = event.getTransferable();
    DataFlavor<RowKeySet> dataFlavor = DataFlavor.getDataFlavor(RowKeySet.class, "fromTable");
    RowKeySet set = transferable.getData(dataFlavor);
    Employee emp = null;
    if(set != null && !set.isEmpty()) {
      int index = (Integer) set.iterator().next();
      emp = m_tableModel.get(index);
    }
    
    if(emp == null)
        return DnDAction.NONE;
        
    DnDAction proposedAction = event.getProposedAction();
    if(proposedAction == DnDAction.COPY) {
      m_graphList.add(emp);  
    }
    else if(proposedAction == DnDAction.LINK) {
      m_graphList.add(emp);
    }
    else if(proposedAction == DnDAction.MOVE) {
      m_graphList.add(emp);
      m_tableModel.remove(emp);
    }
    
    RequestContext.getCurrentInstance().addPartialTarget(event.getDragComponent());
    
    return event.getProposedAction();
  }
}
