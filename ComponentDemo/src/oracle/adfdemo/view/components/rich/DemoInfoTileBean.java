package oracle.adfdemo.view.components.rich;


import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.el.ELContext;

import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.layout.RichDeck;

import oracle.adf.view.rich.render.ClientEvent;

import org.apache.myfaces.trinidad.context.Agent;
import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeyPropertyModel;

public class DemoInfoTileBean
{
  public DemoInfoTileBean()
  {
  }

  public CollectionModel getInfoTileProjectModel()
  {
    if(_infoTileProjectModel == null)
    {
      _createInfoTileProjectModel();
    }
    return _infoTileProjectModel;
  }
  
  public CollectionModel getInfoTileCloudItemModel()
  {
    if(_infoTileCloudItemModel == null)
    {
      _createInfoTileCloudItemModel();
    }
    return _infoTileCloudItemModel;
  }
  
  private void _createInfoTileProjectModel()
  {
    List<InfoTileProject> infoTileProjectList = new ArrayList<InfoTileProject>();

    InfoTileProject project = new InfoTileProject(1, "ADF Team", "Short ADF team project description",
                          oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileProject.Status.WARNING,
                          oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileProject.Status.OK, 2,2);
    infoTileProjectList.add(project);
    
    // make the first one selected by default
    _selectedInfoTileProject = project;
    _infoTileSelectedProjectIndex = 0;
    
    project = new InfoTileProject(2, "Mobile Team", "Short Mobile team project description",
                          oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileProject.Status.WARNING_GOING,
                          oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileProject.Status.OK, 2,3);
    infoTileProjectList.add(project);
    project = new InfoTileProject(3, "JET Team", "Short JET team project description",
                          oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileProject.Status.WARNING_GOING,
                          oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileProject.Status.ERROR, 4,1);
    infoTileProjectList.add(project);
    project = new InfoTileProject(4, "Hello Team", "Short Hello team project description",
                          oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileProject.Status.WARNING,
                          oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileProject.Status.ERROR, 1,2);
    infoTileProjectList.add(project);
    project = new InfoTileProject(5, "Train Team", "Short Train team project description",
                          oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileProject.Status.WARNING_GOING,
                          oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileProject.Status.OK, 3,1);
    infoTileProjectList.add(project);
    project = new InfoTileProject(6, "Portal Team", "Short Portal team project description",
                          oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileProject.Status.ERROR,
                          oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileProject.Status.WARNING, 1,3);
    infoTileProjectList.add(project);

    _infoTileProjectModel = new RowKeyPropertyModel(infoTileProjectList, "id");
  }
  
  public DemoInfoTileBean.InfoTileProject getSelectedInfoTileProject()
  {
    return _selectedInfoTileProject;
  }

  public int getInfoTileSelectedProjectIndex()
  {
    return _infoTileSelectedProjectIndex;
  }
  
  public void infoTileProjectSelected(ClientEvent event)
  {
    if("tileSelected".equals(event.getType()))
    {
      int index = ((Number) event.getParameters().get("index")).intValue();
      _infoTileSelectedProjectIndex = index;
      _selectedInfoTileProject = (InfoTileProject)_infoTileProjectModel.getRowData(index);
    }
  }

  private void _createInfoTileCloudItemModel()
  {
    List<InfoTileCloudItem> infoTileCloudItemList = new ArrayList<InfoTileCloudItem>();

    InfoTileCloudItem cloudItem = new InfoTileCloudItem(1, "Overview",
                            oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileCloudItem.ItemType.OVERVIEW);
    infoTileCloudItemList.add(cloudItem);
    
    // make the first one selected by default
    _selectedInfoTileCloudItem = cloudItem;
    _infoTileSelectedCloudItemIndex = 0;
    _cloudItemFirstChild = _CLOUDITEMDECKCHILDREN[0];
    
    cloudItem = new InfoTileCloudItem(2, "Administration",
                            oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileCloudItem.ItemType.ADMIN);
    infoTileCloudItemList.add(cloudItem);
    cloudItem = new InfoTileCloudItem(3, "Metrics",
                            oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileCloudItem.ItemType.METRICS);
    infoTileCloudItemList.add(cloudItem);
    cloudItem = new InfoTileCloudItem(4, "Associated Services",
                            oracle.adfdemo.view.components.rich.DemoInfoTileBean.InfoTileCloudItem.ItemType.SERVICES);
    infoTileCloudItemList.add(cloudItem);

    _infoTileCloudItemModel = new RowKeyPropertyModel(infoTileCloudItemList, "id");
  }
  
  public DemoInfoTileBean.InfoTileCloudItem getSelectedInfoTileCloudItem()
  {
    return _selectedInfoTileCloudItem;
  }

  public int getInfoTileSelectedCloudItemIndex()
  {
    return _infoTileSelectedCloudItemIndex;
  }
  
  public void infoTileCloudItemSelected(ClientEvent event)
  {
    if("tileSelected".equals(event.getType()))
    {
      RichDeck deck =  (RichDeck)event.getComponent().findComponent("pglDet");
      int index = ((Number) event.getParameters().get("index")).intValue();
      
      _cloudItemFirstChild = _CLOUDITEMDECKCHILDREN[index];
      _infoTileSelectedCloudItemIndex = index;
      _selectedInfoTileCloudItem = (InfoTileCloudItem)_infoTileCloudItemModel.getRowData(index);
    }
  }
  
  public String getCloudItemFirstChild()
  {
    return _cloudItemFirstChild;
  }


  public String getSelectedInfoTileCloudItemFacet()
  {
    return _selectedInfoTileCloudItem.getItemType().toString();  
  }
  
  public List<Map> getInfoTileCloudItemListSS()
  {
    if(_infoTileCloudItemListSS == null)
    {
      _infoTileCloudItemListSS = new ArrayList<Map>(1);
      //single row of bogus data
      _infoTileCloudItemListSS.add(new HashMap());
    }
    return _infoTileCloudItemListSS;
  }
  
  public List<Object> getInfoTileCloudItemColumns()
  {
    if(_infoTileCloudItemColumns == null)
    {
      _infoTileCloudItemColumns = new ArrayList<Object>(30);
      
      for(int i = 0; i < 30; i++)
      {
        //single row of bogus data
        _infoTileCloudItemColumns.add(new Integer(i));
      }
    }
    return _infoTileCloudItemColumns;
  }
  
  public List<Object> getInfoTileCloudListData()
  {
    if(_infoTileCloudListData == null)
    {
      // fill in some bogus data
      _infoTileCloudListData = new ArrayList<Object>(4);
      
      for(int i = 0; i < 4; i++)
      {
        _infoTileCloudListData.add(new Object());
      }
    }
    return _infoTileCloudListData;
  }

  public boolean isMediaQuerySupported()
  {
    //IE 8 and below does not support media queries
    Agent agent = RequestContext.getCurrentInstance().getAgent();
    

    return !(Agent.AGENT_IE.equals(agent.getAgentName()) && 
             (agent.getAgentVersion().startsWith("7.") || agent.getAgentVersion().startsWith("8.")));
  }
      
  public static class InfoTileProject extends java.util.HashMap
  {

    @SuppressWarnings("compatibility:5085837192891001510")
    private static final long serialVersionUID = 1L;

    public InfoTileProject()
    {
    }

    public InfoTileProject(int id, String title, String desc, Status buildStatus, Status depStatus, int reviewCount, int taskCount)
    {
      put("id", new Integer(id));
      put("title", title);
      put("description", desc);
      put("buildStatus", buildStatus);
      put("buildStatusImage", _statusIcons[buildStatus.ordinal()]);
      put("deploymentStatus", depStatus);
      put("deploymentStatusImage", _statusIcons[depStatus.ordinal()]);
      put("reviewCount", reviewCount);
      put("taskCount", taskCount);
      
      Status[] statuses = DemoInfoTileBean.InfoTileProject.Status.values();
      int count = statuses.length;
      int ord = buildStatus.ordinal();
      Status [] buildJobs = {statuses[ord], statuses[(ord+1)%count], statuses[(ord+2)%count]};
      put("buildJobStatuses", buildJobs);
      
      String [] buildJobStatusImages = {_statusIcons[buildJobs[0].ordinal()], _statusIcons[buildJobs[1].ordinal()], _statusIcons[buildJobs[2].ordinal()]};
      put("buildJobStatusImages", buildJobStatusImages);
      
      ord = depStatus.ordinal();
      Status [] depTargets = {statuses[ord], statuses[(ord+1)%count], statuses[(ord+2)%count]};
      put("deploymentTargetStatuses", depTargets);
      
      String [] depTargetStatusImages = {_statusIcons[depTargets[0].ordinal()], _statusIcons[depTargets[1].ordinal()], _statusIcons[depTargets[2].ordinal()]};
      put("deploymentTargetStatusImages", depTargetStatusImages);
      
      List<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>(2);
      
      Map<String, Object> task = new HashMap<String, Object>();
      task.put("taskId", new Integer(NEXT_TASK_ID++));
      task.put("summary", title + " task 1 Summary");
      taskList.add(task);
      
      task = new HashMap<String, Object>();
      task.put("taskId", new Integer(NEXT_TASK_ID++));
      task.put("summary", title + " task 2 Summary");
      taskList.add(task);
      
      put("taskList", taskList);        
    }

    public Integer getId()
    {
      return (Integer) get("id");
    }

    public String getTitle()
    {
      return (String) get("title");
    }
    
    public String getDescription()
    {
      return (String) get("description");
    }

    public Status getBuildStatus()
    {
      return (Status) get("buildStatus");
    }

    public Status getDeploymentStatus()
    {
      return (Status) get("deploymentStatus");
    }

    public Integer getReviewCount()
    {
      return (Integer) get("reviewCount");
    }

    public Integer getTaskCount()
    {
      return (Integer) get("taskCount");
    }

    /**
     * Possible status of activity
     */
    public enum Status
    {
      OK,
      WARNING_GOING,
      WARNING,      
      ERROR
    }
    
    private static String [] _statusIcons = {"/images/alta_v1/status_ok.png", "/images/alta_v1/status_warn_going.png",
                                             "/images/alta_v1/status_warn.png","/images/alta_v1/status_error.png"};
    private static int NEXT_TASK_ID = 1598;
  }
  
  public static class InfoTileCloudItem extends java.util.HashMap
  {

    @SuppressWarnings("oracle.jdeveloper.java.serialversionuid-stale")
    private static final long serialVersionUID = 1L;

    public InfoTileCloudItem()
    {
    }

    public InfoTileCloudItem(int id, String title, ItemType itemType)
    {
      put("id", new Integer(id));
      put("title", title);
      put("itemType", itemType);
    }

    public Integer getId()
    {
      return (Integer) get("id");
    }

    public String getTitle()
    {
      return (String) get("title");
    }
    
    public ItemType getItemType()
    {
      return (ItemType) get("itemType");
    }

    /**
     * Possible type of this cloud item
     */
    public enum ItemType
    {
      OVERVIEW,
      ADMIN,
      METRICS,      
      SERVICES
    }
  }

  private CollectionModel _infoTileProjectModel;
  private InfoTileProject _selectedInfoTileProject;
  private int  _infoTileSelectedProjectIndex;

  private CollectionModel _infoTileCloudItemModel;
  private InfoTileCloudItem _selectedInfoTileCloudItem;
  private int  _infoTileSelectedCloudItemIndex;
  private List<Map> _infoTileCloudItemListSS;
  private List<Object> _infoTileCloudItemColumns;
  private List<Object> _infoTileCloudListData;
  private String _cloudItemFirstChild;

  
  private final String [] _CLOUDITEMDECKCHILDREN = {"dbovw", "dboadmn", "dbomet", "dbosvc"};
  
  private static final ADFLogger _LOG =
    ADFLogger.createADFLogger(DemoInfoTileBean.class);
}
