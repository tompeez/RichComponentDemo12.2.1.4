package oracle.adfdemo.view.listView.rich;


import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;

import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichListItem;
import oracle.adf.view.rich.component.rich.data.RichListView;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adfdemo.view.layout.DemoDashboardBean;
import oracle.adfdemo.view.layout.DemoDashboardBean.SideBarItem;

import org.apache.myfaces.trinidad.component.UIXGroup;
import org.apache.myfaces.trinidad.event.RowKeySetChangeEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeyPropertyModel;
import org.apache.myfaces.trinidad.model.RowKeyPropertyTreeModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;

public class ListViewBean
{
  public ListViewBean()
  {
  }

  public TreeModel getABTreeModel()
  {
    if(_addressBookTreeModel == null)
    {
      _createABTreeModel();
    }

    return _addressBookTreeModel;
  }

  public TreeModel getProjectTreeModel()
  {
    if(_projectTreeModel == null)
    {
      _createProjectModel();
    }

    return _projectTreeModel;
  }

  public TreeModel getDeptEmpTreeModel()
  {
    if(_deptEmpTreeModel == null)
    {
      _createDeptEmpTreeModel();
    }

    return _deptEmpTreeModel;
  }

  public void refreshProjectTreeModel()
  {
    // recreate the model with new parameters
    _createProjectModel();
  }

  public CollectionModel getTaskModel()
  {
    if(_taskModel == null)
    {
      _createTaskModel();
    }

    return _taskModel;
  }

  public CollectionModel getTabularModel()
  {
    if(_tabularModel == null)
    {
      _createTabularModel();
    }
    return _tabularModel;
  }

  public TreeModel getDashBoardTreeModel()
  {
    if(_dashBoardTreeModel == null)
    {
      _createDashBoardTreeModel();
    }

    return _dashBoardTreeModel;
  }


  public String refreshTaskModel()
  {
    _createTaskModel();
    return null;
  }

  public void setTaskCount(int taskCount)
  {
    this._taskCount = taskCount;
  }

  public int getTaskCount()
  {
    return _taskCount;
  }

  public void setTaskUnknownCount(boolean taskUnknownCount)
  {
    this._taskUnknownCount = taskUnknownCount;
  }

  public boolean isTaskUnknownCount()
  {
    return _taskUnknownCount;
  }

  public void setProjectTreeGroupCount(int projectTreeGroupCount)
  {
    _projectTreeGroupCount = projectTreeGroupCount;
  }

  public int getProjectTreeGroupCount()
  {
    return _projectTreeGroupCount;
  }

  public void setProjectTreeChildCount(int projectTreeChildCount)
  {
    _projectTreeChildCount = projectTreeChildCount;
  }

  public int getProjectTreeChildCount()
  {
    return _projectTreeChildCount;
  }

  public void setProjectTreeGroupUnknown(boolean projectTreeGroupUnknown)
  {
    this._projectTreeGroupUnknown = projectTreeGroupUnknown;
  }

  public boolean isProjectTreeGroupUnknown()
  {
    return _projectTreeGroupUnknown;
  }

  public void setProjectTreeChildUnknown(boolean projectTreeChildUnknown)
  {
    this._projectTreeChildUnknown = projectTreeChildUnknown;
  }

  public boolean isProjectTreeChildUnknown()
  {
    return _projectTreeChildUnknown;
  }

  /**
   * Selection event handler
   * @param event
   */
  public void selectionChange(SelectionEvent event)
  {
    logRowKeySets(event);
  }
  
  private void logRowKeySets(RowKeySetChangeEvent event)
  {
    RowKeySet sel = event.getAddedSet();
    RowKeySet unSel = event.getRemovedSet();

    _LOG.info("Added: " + sel + "\nRemoved: " + unSel);
  }
  
  private void _createABTreeModel()
  {
    List<EmpBean> empList = new ArrayList<EmpBean>();
    empList.add(new EmpBean(1, "Amy Bartlet", "Vice President", 1));
    empList.add(new EmpBean(2, "Andy Jones", "Director", 1));
    empList.add(new EmpBean(3, "Andrew Bugsy", "individual Contributer", 1));
    empList.add(new EmpBean(4, "Annett Barnes", "individual Contributer", 1));

    List<AlphaBeticalEmpHolderBean> empHolders = new ArrayList<AlphaBeticalEmpHolderBean>();
    empHolders.add(new AlphaBeticalEmpHolderBean("A", empList));

    empList = new ArrayList<EmpBean>();
    empList.add(new EmpBean(5, "Bob Jones", "Salesman", 2));
    empList.add(new EmpBean(6, "Bart Buckler", "Purchasing", 2));
    empList.add(new EmpBean(7, "Bobby Fisher", "individual Contributer", 2));
    empHolders.add(new AlphaBeticalEmpHolderBean("B", empList));

    empList = new ArrayList<EmpBean>();
    empList.add(new EmpBean(8, "Cathy Jones", "Director", 2));
    empList.add(new EmpBean(9, "Caroll Smith", "Purchasing", 2));
    empHolders.add(new AlphaBeticalEmpHolderBean("C", empList));

    _addressBookTreeModel = new ListViewTreeModel(empHolders, "emps", "alphabetHeading", "empno", false, false);
  }


  private void _createTaskModel()
  {
    List<TaskBean> taskList = new ArrayList<TaskBean>();
    int nameCount = _NAMES.length;

    for(int i = 0; i < _taskCount; i++)
    {
      int num = i+1;
      TaskBean taskBean = new TaskBean(num, "Task Name " +num, "Project name"+num+" > ...> T"+num+"." + "." +num,
                                       "Created By:" +_NAMES[Double.valueOf(Math.random()*1000).intValue()%nameCount]);
      taskList.add(taskBean);
    }

    _taskModel = _taskUnknownCount? new ListViewUnknownPropertyModel(taskList, "taskId") :
                                    new RowKeyPropertyModel(taskList, "taskId");
  }
  
  private void _updateTaskModel(Object rowKey)
  {
    CollectionModel model = _taskModel;
    
    TaskBean data = (TaskBean)model.getRowData(rowKey);
    Integer taskId = data.getTaskId();
    
    List<TaskBean> taskList = (List<TaskBean>)model.getWrappedData();
    Iterator itr = taskList.iterator();
    
    while(itr.hasNext())
    {
      TaskBean task = (TaskBean)itr.next();
      if (taskId == task.getTaskId())
      {
        itr.remove();
        break;
      }
    }
    
    _taskModel = new RowKeyPropertyModel(taskList, "taskId");
    
  }

  private void _createTabularModel()
  {
    List<CloudBean> cloudList = new ArrayList<CloudBean>();

    CloudBean cloudBean = new CloudBean(1, "My New Database(Oracle Database)", "Subscription: Enterprise", "153 Users", "Monthly Cast $340");
    cloudList.add(cloudBean);
    cloudBean = new CloudBean(2, "Collaboration(Oracle Social Network)", "Subscription: Trial Ends (01/10/2012", "168 Users", "Monthly Cast $340");
    cloudList.add(cloudBean);
    cloudBean = new CloudBean(3, "Platform Test(Oracle java)", "Subscription: Enterprise", "All Apps Oracle Base", "Monthly Cast $340");
    cloudList.add(cloudBean);
    cloudBean = new CloudBean(4, "New HCM(Oracle HCM)", "Subscription: Enterprise", "All Services", "Monthly Cast $340");
    cloudList.add(cloudBean);
    cloudBean = new CloudBean(5, "Phone Book(Custom Application)", "N/A", "319 Users", "Upgrading Results in $27 Credit on your Credit Card. You will be billed monthly and send a confirmation mail.");
    cloudList.add(cloudBean);

    for(int i = 5; i < _cloudCount; i++)
    {
      int num = i+1;
      cloudBean = new CloudBean(num, _ORAAPPS[i%_ORAAPPS.length], "Subscription: Enterprise", (Double.valueOf(Math.random()*1000).intValue()%200 +200)+" Users", "Monthly Cast $" +(Double.valueOf(Math.random()*1000).intValue()%400));
      cloudList.add(cloudBean);
    }

    _tabularModel = new RowKeyPropertyModel(cloudList, "id");
  }

  private void _createDeptEmpTreeModel()
  {
    List<EmpBean> empList1 = new ArrayList<EmpBean>();
    int i = 0;

    for(; i < 20; i++)
    {
      String job = (i== 0)? "President": ((i==1)?"Manager": (i== 2? "ProjectManager" : " Individual Contributer"));
      empList1.add(new EmpBean(i+1, _NAMES[i], job, 1));
    }


    List<EmpBean>  empList2 = new ArrayList<EmpBean>();
    empList2.add(new EmpBean(i+1, _NAMES[i], "Sales Person", 2));
    i++;
    empList2.add(new EmpBean(i+1, _NAMES[i], "Sales Person", 2));
    i++;
    empList2.add(new EmpBean(i+1, _NAMES[i], "Sales Support", 2));

    List<EmpBean>  empList3 = new ArrayList<EmpBean>();

    List<DeptBean> depts = new ArrayList<DeptBean>();
    depts.add(new DeptBean(1, "Development", "loc1", "", empList1));
    depts.add(new DeptBean(2, "Sales/Marketing", "loc2", null, empList2));
    depts.add(new DeptBean(3, "Quality Engineering", "loc3", "x", empList3));
    depts.add(new DeptBean(4, "Human Resources", "loc4", "x", null));

    _deptEmpTreeModel = new ListViewTreeModel(depts, "emps", "deptno", "empno", false, false);
  }

  private void _createProjectModel()
  {
    List<ProjectBean> projectList = new ArrayList<ProjectBean>();

    int nameCount = _NAMES.length;
    for(int i = 0; i < _projectTreeGroupCount; i++)
    {
      List<TaskBean> taskList = new ArrayList<TaskBean>();
      for(int j = 0; j < _projectTreeChildCount; j++)
      {
        int num = j+1;
        TaskBean taskBean = new TaskBean(num, "Task Name " +num, "Project name"+(i+1)+" >..> T"+num+"." + num,
                                         "Created By:" +_NAMES[Double.valueOf(Math.random()*1000).intValue()%nameCount]);
        taskList.add(taskBean);
      }

      ProjectBean projectBean = new ProjectBean(i, "Project Name " +(i+1), taskList);
      projectList.add(projectBean);
    }

    _projectTreeModel = new ListViewTreeModel(projectList, "tasks", "projectId", "taskId",
                                              _projectTreeGroupUnknown, _projectTreeChildUnknown);
  }

  private void _createDashBoardTreeModel()
  {
    List<NavbarGroup> groupList = new ArrayList<NavbarGroup>();

    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();

    List<SideBarItem> dashboardItems = (List<SideBarItem>)facesContext.getApplication()
      .getExpressionFactory().createValueExpression(
                                 elContext, "#{demoDashboard.sideBarDataItems}", List.class)
      .getValue(elContext);

    groupList.add(new NavbarGroup(1, "Dashboard","/images/pieGraph.png", null, null, dashboardItems));
    groupList.add(new NavbarGroup(2, "Requiring My Action","/images/action.png", "12", null, null));

    List<SideBarItem> serviceRequests = new ArrayList<SideBarItem>();
    serviceRequests.add(new NavbarGroupItem("My Action Requests", "9"));
    serviceRequests.add(new NavbarGroupItem("Team Active SRs", "1"));
    serviceRequests.add(new NavbarGroupItem("Critical HFM", null));
    serviceRequests.add(new NavbarGroupItem("Escalated SRs", null));

    groupList.add(new NavbarGroup(3, "Support Queues","/images/support.png", null, "add", serviceRequests));

    List<SideBarItem> knowledgeItems = new ArrayList<SideBarItem>();
    knowledgeItems.add(new NavbarGroupItem("Authoring Wizard", null));
    knowledgeItems.add(new NavbarGroupItem("My Documents", null));

    groupList.add(new NavbarGroup(4, "Knowledge","/images/knowledge.png", null, null, knowledgeItems));

    _dashBoardTreeModel = new ListViewBean.ListViewTreeModel(groupList, "sideBarItems", "id", "itemId", false, false);
  }
  
  public void deleteAction(ActionEvent event)
  {
    UIComponent actionItem = event.getComponent();
    UIComponent listItem = actionItem.getParent();

    if (listItem instanceof RichListItem)
    {
      RichListView parentListView = (RichListView) (listItem).getParent();

      RowKeySet selectedRows = parentListView.getSelectedRowKeys();
      Object currentRowKey = parentListView.getRowKey();

      Iterator itr = selectedRows.iterator();

      while (itr.hasNext())
      {
        Object rowKey = itr.next();

        if (rowKey.equals(currentRowKey))
        {
          parentListView.setRowKey(rowKey);
          this._updateTaskModel(rowKey);
        }

      }

      AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
      adfFacesContext.addPartialTarget(parentListView);
    }
  }
  
  public void showPopup(ActionEvent event)
  {
    UIComponent actionItem = event.getComponent();
    UIComponent listItem = actionItem.getParent();
    
    if (listItem instanceof UIXGroup)
    {
      listItem = listItem.getParent();
      
    }
    RichListView parentListView = (RichListView)(listItem).getParent();
    
    RichPopup popup = (RichPopup)parentListView.findComponent("popupMarkWaiting");
    popup.show(new RichPopup.PopupHints());//modify these hint parameters if you do not want the default behavior.
  } 
  
  public void handleDialog(DialogEvent event)
  {
    // jsut re-render the listView
    this.refreshTaskModel();
  }

  /**
   * Extends RowKeyPropertyTreeModel
   */
  public static class ListViewTreeModel<T extends List> extends RowKeyPropertyTreeModel
  {
    public ListViewTreeModel(
      T       model,
      String  childProperty,
      String  rowKeyProperty,
      String  childRowKeyProperty,
      boolean isUnknownRowCount,
      boolean isChildUnknownRowCount)
    {
      super();
      setWrappedData(model);
      setRowKeyProperty(rowKeyProperty);
      setChildProperty(childProperty);
      _childRowKeyProperty = childRowKeyProperty;
      _isUnknownRowCount = isUnknownRowCount;
      _isChildUnknownRowCount = isChildUnknownRowCount;
    }

    @Override
    protected CollectionModel createChildModel(Object childData)
    {
      CollectionModel model = _isChildUnknownRowCount ?
        new ListViewUnknownPropertyModel(childData, _childRowKeyProperty) :
        new RowKeyPropertyModel(childData, _childRowKeyProperty);
      model.setRowIndex(-1);
      return model;
    }

    @Override
    public int getRowCount()
    {
      if(_isUnknownRowCount && this.getDepth() ==0)
        return -1;
      else if(_isChildUnknownRowCount && this.getDepth() > 0)
        return -1;
      else
        return super.getRowCount();
    }

    private String _childRowKeyProperty;
    private final boolean _isUnknownRowCount;
    private final boolean _isChildUnknownRowCount;
  }

  public static class ListViewUnknownPropertyModel extends RowKeyPropertyModel
  {
    public ListViewUnknownPropertyModel(Object model, String rowKeyProperty)
    {
      super(model, rowKeyProperty);
    }

    @Override
    public int getRowCount()
    {
      return -1;
    }

    @Override
    public void setRowKey(Object key)
    {
      // simply convert into an integer
      if(key != null)
      {
        int index = Integer.valueOf(key.toString()) -1;
        if(index >=0)
          setRowIndex(index);
      }
      else
      {
        super.setRowKey(key);
      }
    }
  }

  public static class AlphaBeticalEmpHolderBean
  {

    public AlphaBeticalEmpHolderBean(String alphabet, List<EmpBean> emps)
    {
      _alphabet = alphabet;
      _emps     = emps;
    }

    public String getAlphabetHeading()
    {
      return _alphabet;
    }

    public List<EmpBean> getEmps()
    {
      return _emps;
    }

    List<EmpBean> _emps;
    String        _alphabet;
  }


  public static class ProjectBean
  {

    public ProjectBean(int projectId, String projectName, List<TaskBean> tasks)
    {
      _projectId   = Integer.toString(projectId);
      _projectName = projectName;
      _tasks     = tasks;
    }

    public String getProjectId()
    {
      return _projectId;
    }

    public String getProjectName()
    {
      return _projectName;
    }

    public List<TaskBean> getTasks()
    {
      return _tasks;
    }

    private final String   _projectId;
    private List<TaskBean> _tasks;
    private String         _projectName;
  }

  public static class TaskBean extends java.util.HashMap
  {

    @SuppressWarnings("compatibility:1378584936344827653")
    private static final long serialVersionUID = 1L;

    public TaskBean()
    {
    }

    public TaskBean(int n, String tn, String pd, String cd)
    {
      put("taskId", new Integer(n));
      put("taskName", tn);
      put("projectDesc", pd);
      put("created", cd);
    }

    public Integer getTaskId()
    {
      return (Integer) get("taskId");
    }

    public String getTaskName()
    {
      return (String) get("taskName");
    }

    public String getProjectDesc()
    {
      return (String) get("projectDesc");
    }

    public String getCreated()
    {
      return (String) get("created");
    }
  }

  public static class CloudBean extends java.util.HashMap
  {

    @SuppressWarnings("compatibility:5085837192891001510")
    private static final long serialVersionUID = 1L;

    public CloudBean()
    {
    }

    public CloudBean(int id, String desc, String subsc, String userCount, String cost)
    {
      put("id", new Integer(id));
      put("description", desc);
      put("subscription", subsc);
      put("userCount", userCount);
      put("cost", cost);
    }

    public Integer getId()
    {
      return (Integer) get("id");
    }

    public String getDescription()
    {
      return (String) get("description");
    }

    public String getSubscription()
    {
      return (String) get("subscription");
    }

    public String getUserCount()
    {
      return (String) get("userCount");
    }

    public String getCost()
    {
      return (String) get("cost");
    }

    public void setChecked(boolean _checked)
    {
      put("checked", _checked);
    }

    public boolean isChecked()
    {
      return Boolean.TRUE.equals(get("checked"));
    }
  }

  public static class EmpBean extends java.util.HashMap
  {

    @SuppressWarnings("compatibility:-5191488758978998433")
    private static final long serialVersionUID = 1L;

    public EmpBean()
    {
    }

    public EmpBean(int n, String e, String l, int d)
    {
      put("empno", new Integer(n));
      put("ename", e);
      put("job", l);
      put("deptno", new Integer(d));
    }

    public int getEmpno()
    {
      return ((Integer) get("empno")).intValue();
    }

    public String getEname()
    {
      return (String) get("ename");
    }

    public String getJob()
    {
      return (String) get("job");
    }

    public int getDeptno()
    {
      return ((Integer) get("deptno")).intValue();
    }
  }

  public static class DeptBean extends java.util.HashMap
  {

    @SuppressWarnings("compatibility:6512002194204247018")
    private static final long serialVersionUID = 1L;

    public DeptBean(int n, String d, String l, String h, List<EmpBean> al)
    {
      put("deptno", new Integer(n));
      put("dname", d);
      put("loc", l);
      put("hasmanager", h);
      put("emps", al);
    }

    public int getDeptno()
    {
      return ((Integer) get("deptno")).intValue();
    }

    public String getDname()
    {
      return (String) get("dname");
    }

    public String getLoc()
    {
      return (String) get("loc");
    }

    public String getHasmanager()
    {
      return (String) get("hasmanager");
    }

    public List<EmpBean> getEmps()
    {
      return (List<EmpBean>) get("emps");
    }
  }

  public static class NavbarGroup implements Serializable
  {

    @SuppressWarnings("compatibility:8597860999993697664")
    private static final long serialVersionUID = 1L;

    public NavbarGroup(int id, String title, String imgSrc, String status, String action, List<SideBarItem> sideBarItems)
    {
      _title = title;
      _imgSrc = imgSrc;
      _id = id;
      _status = status;
      _action = action;
      _sideBarItems = sideBarItems;
    }

    /**
     * Retrieves the title of the group.
     * @return the title of the group.
     */
    public String getTitle()
    {
      return _title;
    }

    /**
     * Retrieves the source for the icon on the group
     */
    public String getImgSrc()
    {
      return _imgSrc;
    }

    /**
     * Retrieves the ID of the Group.
     */
    public int getId()
    {
      return _id;
    }

    public String getAction()
    {
      return _action;
    }

    public String getStatus()
    {
      return _status;
    }

    public List<DemoDashboardBean.SideBarItem> getSideBarItems()
    {
      return _sideBarItems;
    }

    private String    _title;
    private int       _id;
    private String    _imgSrc;
    private String    _action;
    private String    _status;
    List<SideBarItem> _sideBarItems;
  }

  public static class NavbarGroupItem extends SideBarItem implements Serializable
  {
    @SuppressWarnings("compatibility:1417783313244427785")
    private static final long serialVersionUID = 1L;

    public NavbarGroupItem(String title, String status)
    {
      super(title, status);
    }

    public String getItemType()
    {
      return "NAVBARGROUPITEM";
    }
  }

  public DnDAction handleEmpMove(DropEvent dropEvent)
  {
    Object dropRowKey = dropEvent.getDropSite();

    Transferable transferable = dropEvent.getTransferable();

    // The data in the transferrable is the row key for the dragged component.
    DataFlavor<RowKeySet> rowKeySetFlavor = DataFlavor.getDataFlavor(RowKeySet.class, "empModel");
    RowKeySet rowKeySet = transferable.getData(rowKeySetFlavor);
    if (dropRowKey!= null && rowKeySet != null)
    {
      // Get the model for the dragged component.
      CollectionModel dragModel = transferable.getData(CollectionModel.class);
      Object oldRowKey = dragModel.getRowKey();
      try
      {
        if (dragModel != null && _deptEmpTreeModel == dragModel)
        {
          // Set the row key for this model using the row key from the transferrable.
          Object currKey = rowKeySet.iterator().next();

          Object groupParentRowKey = _deptEmpTreeModel.getContainerRowKey(currKey);
          Object dropGroupParentRowKey = _deptEmpTreeModel.getDepth(dropRowKey) == 0 ? dropRowKey:
                                          _deptEmpTreeModel.getContainerRowKey(dropRowKey);

          if(dropGroupParentRowKey.equals(groupParentRowKey))
          {
            FacesMessage message = new FacesMessage("DnD Rejected! The Source and the Target are in the same group");

            FacesContext.getCurrentInstance().addMessage(null, message);
            return DnDAction.NONE;
          }

          DeptBean dragDept = (DeptBean)_deptEmpTreeModel.getRowData(groupParentRowKey);
          DeptBean dropDept = (DeptBean)_deptEmpTreeModel.getRowData(dropGroupParentRowKey);
          EmpBean  dragEmp = (EmpBean)_deptEmpTreeModel.getRowData(currKey);

          // add it to the new group
          List<EmpBean> emps =  dropDept.getEmps();

          if(emps == null)
          {
            emps = new ArrayList<EmpBean>();
          }

          emps.add(dragEmp);

          // remove from the old group
          dragDept.getEmps().remove(dragEmp);
        }
      }
      finally
      {
        dragModel.setRowKey(oldRowKey);
      }
      return dropEvent.getProposedAction();
    }
    else
    {
      return DnDAction.NONE;
    }
  }


  private TreeModel _addressBookTreeModel;
  private TreeModel _projectTreeModel;
  private TreeModel _deptEmpTreeModel;
  private TreeModel _dashBoardTreeModel;
  private CollectionModel _taskModel;
  private CollectionModel _tabularModel;

  private int       _taskCount   = 100;
  private int       _cloudCount   = 50;

  private boolean   _taskUnknownCount = false;

  private int       _projectTreeGroupCount   = 5;
  private int       _projectTreeChildCount   = 100;
  private boolean   _projectTreeGroupUnknown = false;
  private boolean   _projectTreeChildUnknown = false;

  private static final String [] _NAMES = {"Amy Bartlet", "Andy Jones", "Bart Buckler", "Caroll Smith", "Annett Barnes",
                                     "Bobby Fisher", "Liam Baker", "Olivia Johnson", "Emma Williams", "Mason Brown",
                                     "Noah James", "Jacob Miller", "Ava Davis", "Jack Wilson", "Ella Garcia",
                                     "Aiden Rodriguez", "Mia Taylor", "Logan Thomas", "Jackson Moore", "Lucas Martin",
                                     "Emily Thompson", "Chole White", "Lily Lopez", "Benjamin Lee", "Madison Clark",
                                     "Alexander Lewis", "Ryan Robinson", "James Walker","Avery Perez","Abigail Young",
                                     "Ameila Sanchez"};
  private static final String [] _ORAAPPS = {"New SCM(Oracle SCM)",
                     "New Transportation(Oracle TPM)",
                     "New CRM(Oracle CRM)",
                     "New Manufacturing(Oracle Manufacturing)",
                     "New LCM(Oracle LCM)"};
  
  private static final ADFLogger _LOG =
    ADFLogger.createADFLogger(ListViewBean.class);
}
