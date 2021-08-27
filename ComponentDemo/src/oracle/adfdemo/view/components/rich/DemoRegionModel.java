/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.io.Serializable;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.faces.event.PhaseId;

import oracle.adf.view.rich.component.fragment.RegionSite;
import oracle.adf.view.rich.component.fragment.UIXRegion;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;
import oracle.adf.view.rich.model.RegionModel;

import oracle.adf.view.rich.model.RegionModel.RegionAction;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

/** 
 * A RegionModel instance that is also a managed bean.
 */
public class DemoRegionModel extends RegionModel implements ContextCallback
{
  /**
   * Default constructor.
   */
  public DemoRegionModel()
  {
    this("default", null, null, _ACTION_MAP);
  }

  /**
   * Setting constructor, specifying the default view.
   * @param defaultView the view to use if one isn't specified
   */
  private DemoRegionModel(
    String name,
    String defaultView,
    String sessionMapViewIdKey,
    Map<String, Map<String, DemoRegionAction>> actionMaps)
  {
    _name = name;
    
    if (defaultView == null)
      defaultView = "/components/pagefragments/sampleRegion.jspx";

    _defaultView = defaultView;
    _viewId = defaultView;
    _viewInstance = _getUnique();

    if (sessionMapViewIdKey == null)
      sessionMapViewIdKey = "regionModel.viewId";
        
    _sessionMapViewIdKey = sessionMapViewIdKey;
    
    if (actionMaps == null)
      actionMaps = Collections.emptyMap();

    //Property set in pageflow scope so that view can access how deep the recursion is in. 
    RequestContext requestContext = RequestContext.getCurrentInstance();
    Map pageflow = requestContext.getPageFlowScope();
    pageflow.put("recursionCount", _getRecursionCountByOutcome(null));
    
    _actionMaps = actionMaps;    
  }
  
  public TestTableData getTableData()
  {
    if (_tableModel == null)
    {
     _tableModel = new TestTableData();
    }
    return _tableModel;
  }
  
  public RegionModel getDragDropModel()
  {
    if (_dragNDropModel == null)
    {
      _dragNDropModel = new DemoRegionModel("dragDropModel",
                                             "/components/pagefragments/dragDropTable.jspx",
                                             "regionModel.dragDropViewId",
                                             null);
    }
    return _dragNDropModel;
   
  }
  
  public String getFullName()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    LinkedList<DemoRegionModel> regionModelStack =  _getRegionModelStack(context);
    String fullName = "";

    if (this._fullName == null)
    {
      boolean bFirst = true;
      for (DemoRegionModel rm : regionModelStack)
      {
        String sep = (bFirst) ? "" : ":" ;
        fullName = fullName + sep + rm._name;
        bFirst = false;
      }
    }  
    else
      fullName = _fullName;
    
    return fullName;
  }
  
  public void setFullName(String fullName)
  {
    _fullName = fullName;
  }

  public RegionModel getRegion1()
  {
    if (_region1 == null)
      _region1 = new DemoRegionModel("region1", null, null, _ACTION_MAP);

    return _region1;
  }

  public RegionModel getRegion2()
  {
    if (_region2 == null)
      _region2 = new DemoRegionModel("region2", null, null, _ACTION_MAP);
    
    return _region2;
  }

  /**
   * Clears out any cached content held by this model.
   * The next time the region component renders, this model will
   * recompute everything and possibly return new viewIds.
   * <P>
   * This method should not be called directly.
   * Instead {@link oracle.adf.view.rich.component.fragment.UIXRegion#refresh}
   * may be called.
   */
  @Override
  public void refresh(FacesContext context)
  {
    // reset to the default view
    _setViewId(context, _defaultView);
  }

  /**
   * Retrieves a region model using a default view of simple content.
   * @return the RegionModel instance where the default view is simple content
   */
  public RegionModel getValidationModel()
  {
    if (_validationModel == null)
    {
      _validationModel = new DemoRegionModel("validationModel",
                                             "/components/pagefragments/validationRegion.jspx",
                                             null,
                                             null);
    }
    return _validationModel;
  }


  /**
   * Retrieves a region model using a default view of simple content.
   * @return the RegionModel instance where the default view is simple content
   */
  public RegionModel getSimpleContentRegionModel()
  {
    if (_simpleContentRegionModel == null)
    {
      _simpleContentRegionModel = new DemoRegionModel("simpleContent",
                                                      "regionSimpleContent.jspx",
                                                      null,
                                                      null);
    }
    return _simpleContentRegionModel;
  }

  /**
  /**
   * Retrieves a region model which navigates recursively. The scenario would be 
   * a region of employee and upon manager click action, recurse to the the same view with
   * manager employee id. 
   * @return a Recursive Region Model. 
   */
  public RegionModel getRecursiveRegionModel()
  {
    if(_recursiveRegionModel == null)
    {
      Map<String, Map<String, DemoRegionAction>> actionMap = 
        new HashMap<String, Map<String, DemoRegionAction>>();
      Map<String, DemoRegionAction> recursiveActions = new HashMap<String, DemoRegionAction>();
      recursiveActions.put("CALL", new DemoRegionAction("CALL",
                                                             "/components/pagefragments/recursiveView.jsff",
                                                             "Recursive Page"));
      recursiveActions.put("BACK", new DemoRegionAction("BACK",
                                                             "/components/pagefragments/recursiveView.jsff",
                                                             "Return Recursive Page")); 
      
      actionMap.put("/components/pagefragments/recursiveView.jsff", recursiveActions);
      _recursiveRegionModel = new DemoRegionModel("recursive", "/components/pagefragments/recursiveView.jsff", null, actionMap);
    }
    return _recursiveRegionModel;
  }
  /**
   * Retrieves a region model using view IDs that are up one level from the current folder.
   * This is used in the component tests so that we only need a single copy of the region jspx
   * pages.
   * @return the RegionModel instance where the default views are up on level from the current path
   */
  public RegionModel getUpOneLevelRegionModel()
  {
    if (_upOneLevelRegionModel == null)
    {
      _upOneLevelRegionModel = new DemoRegionModel("upOneLevel",
                                                   "../pagefragments/sampleRegion.jspx",
                                                   null,
                                                   null);
    }
    return _upOneLevelRegionModel;
  }

  /**
   * Retrieves a region model for the "master" part of the master-detail test.
   * @return the RegionModel instance for the "master" part of the master-detail test case
   */
  public RegionModel getMasterRegionModel()
  {
    if (_masterRegionModel == null)
    {
      _masterRegionModel = new DemoRegionModel("master",
                                               "/components/pagefragments/regionMasterContent.jspx",
                                               "regionModel.masterViewId",
                                               null);
    }
    return _masterRegionModel;
  }

  /**
   * Retrieves a region model for the "detail" part of the master-detail test.
   * @return the RegionModel instance for the "detail" part of the master-detail test case
   */
  public RegionModel getDetailRegionModel()
  {
    if (_detailRegionModel == null)
    {
      _detailRegionModel =
        new DemoRegionModel("detail",
                            "/components/pagefragments/regionDetailContent.jspx",
                            "regionModel.detailViewId",
                            null);
    }
    return _detailRegionModel;
  }
  
  public RegionModel getChildRegionModel()
  {
    if (_childRegionModel == null)
    {
      _childRegionModel = 
        new DemoRegionModel("child",
                            "/components/pagefragments/childRegion.jspx",
                            "regionModel.childViewId",
                            null);
    }
    return _childRegionModel;
  }
  
  /**
   * Specifically setup to test nested regions and communiation from child to parent 
   */
  public RegionModel getParentRegionModel()
  {
    if (_parentRegionModel == null)
    {
      Map<String, Map<String, DemoRegionAction>> actionMap = 
        new HashMap<String, Map<String, DemoRegionAction>>();
      Map<String, DemoRegionAction> parentMap = Collections.singletonMap(
                                        "NEXT1",
                                        new DemoRegionAction("NEXT1",
                                                             "/components/pagefragments/parent1Region.jspx",
                                                             "Next Page"));                            
      Map<String, DemoRegionAction> parent1Map = Collections.singletonMap(
                                        "PREV1",
                                        new DemoRegionAction("PREV1",
                                                             "/components/pagefragments/parentRegion.jspx",
                                                             "Previous Page"));
      
      actionMap.put("/components/pagefragments/parentRegion.jspx", parentMap);
      actionMap.put("/components/pagefragments/parent1Region.jspx", parent1Map);
      
      _parentRegionModel = 
        new DemoRegionModel("parent",
                            "/components/pagefragments/parentRegion.jspx",
                            "regionModel.parentViewId",
                            actionMap);
    }
    return _parentRegionModel;
  }

  /**
   * Retrieves a region model using view IDs that are keyed by a geometry-managment-specific session
   * key.
   * This is used in the component tests and is needed so errors are not encountered when running
   * other pages that don't have the numerous region jspx pages that this test requires.
   * @return the RegionModel instance for the geometry management test case
   */
  public RegionModel getGeometryManagmentRegionModel()
  {
    if (_geometryManagmentRegionModel == null)
    {
      _geometryManagmentRegionModel =new DemoRegionModel("geometryManagement",
                                                         null,
                                                         "regionModel.geometryManagementViewId",
                                                         null);
    }
    return _geometryManagmentRegionModel;
  }

  public String getViewId(FacesContext context)
  {
    if (_viewId != null)
    {
      // check for a new view ID:
      Map session = context.getExternalContext().getSessionMap();
      String viewId = (String)session.get(_sessionMapViewIdKey);

      if (viewId != null)
      {
        // use the new value:
        _viewId = viewId;
      }
    }
    
    return _viewId;
  }
  @Override
  public Serializable getViewHandle(FacesContext context)
  {
    return _viewInstance;
  }

  @Override
  public Set<String> getCapabilities()
  {
    Map<String, DemoRegionAction> actionToRegionActionMap = 
                                    _getActionToRegionActionMap(FacesContext.getCurrentInstance());
                                                                                         
    if (actionToRegionActionMap != null)
    {
      return actionToRegionActionMap.keySet();      
    }
    else
    {
      return Collections.emptySet();
    }
  }

  @Override
  public List<? extends RegionAction> getActions()
  {
    Map<String, DemoRegionAction> actionToRegionActionMap = 
                                    _getActionToRegionActionMap(FacesContext.getCurrentInstance());
                                                                                         
    if (actionToRegionActionMap != null)
    {
      return new ArrayList<DemoRegionAction>(actionToRegionActionMap.values());
    }
    else
    {
      return Collections.emptyList();
    }
  }

  public void processBeginRegion(FacesContext context, RegionSite rc)
  {
    LinkedList<DemoRegionModel> regionModelStack =  _getRegionModelStack(context);
    
    if (regionModelStack.contains(this))
      throw new IllegalStateException("Recursive RegionModel invocation");
    
    // skankily make sure that our NavigationHandler is installed
    Application app =  context.getApplication();
    NavigationHandler currHandler = app.getNavigationHandler();
    
    // the first time that we are called, hook our NavigationHandler in
    if (!(currHandler instanceof RegionNavigationHandler))
      app.setNavigationHandler(new RegionNavigationHandler(currHandler));
    
    regionModelStack.add(this);

    _oldval = _setupEL(context, _VAR, new Demo());
    _viewId = getRegionViewId(context);
    this._regionSite = rc;
  }

  public void processEndRegion(FacesContext context, RegionSite rc)
  {
    _setupEL(context, _VAR, _oldval);
    _viewId = null;
    // pop the RegionModel stack
    _getRegionModelStack(context).removeLast();
    this._regionSite = null;
  }

  protected String getRegionViewId(FacesContext fc)
  {
    Map session = fc.getExternalContext().getSessionMap();
    String viewId = (String)session.get(_sessionMapViewIdKey);

    return (viewId != null) ? viewId : _defaultView;
  }

  protected boolean navigateRegion(
    FacesContext context,
    String fromAction,
    String outcome)
  {
    Map<String, DemoRegionAction> actionToRegionActionMap = _getActionToRegionActionMap(context);

    if (actionToRegionActionMap != null)
    {
      DemoRegionAction regionAction =  actionToRegionActionMap.get(fromAction);
      if (regionAction == null)
      {
        // Try locating the RegionAction using the outcome
        if (outcome != null)
          regionAction =  actionToRegionActionMap.get(outcome);
      }
      
      if (regionAction != null)
      {
        //recursive region navigation case. 
        if(getViewId(context).equals(regionAction.getDestinationViewId()))
        {
          RequestContext requestContext = RequestContext.getCurrentInstance();
          Map pageflow = requestContext.getPageFlowScope();
          pageflow.put("recursionCount", _getRecursionCountByOutcome(outcome));
        }

        _setViewId(context, regionAction.getDestinationViewId());
        _viewInstance = _getUnique();
        return true;
      }
    }
    
    return false;
  }

  /**
   * ActionEvent handler for handling command that will call invokeOnComponent with the
   * component so that the region is invoked recursively, since the action event will be
   * delivered when the component is in scope.
   * @param actionEvent
   */
  public void invokeRecursively(ActionEvent actionEvent)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    
    String actionClientId = actionEvent.getComponent().getClientId(context);
    
    context.getViewRoot().invokeOnComponent(context, actionClientId, this);
  }

  /**
   * An ActionListener that navigates away on the enclosing view 
   * @param event the ActionEvent
   */
  public void goGuide(ActionEvent event)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    LinkedList<DemoRegionModel> regionContextStack = _getRegionModelStack(context);
    ListIterator<DemoRegionModel> lIter =  regionContextStack.listIterator(regionContextStack.size());

    DemoRegionModel currModel = null;
    if (lIter.hasPrevious())
      currModel = lIter.previous();

    RegionSite rc = currModel._regionSite;
    if (rc != null)    
    {
      rc.queueParentOutcome(context, _OUTCOME_GUIDE);
    }
  }
  
  /**
   * An ActionListener that navigates to the next viewId on the parent region
   * @param event the ActionEvent
   */
  public void goParentNext(ActionEvent event)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    if (this._regionSite != null)    
    {
      this._regionSite.queueParentOutcome(context, "NEXT1");
    }
  }  
  
  /**
   * An ActionListener that navigates on the viewRoot of the page regardless of
   * how nested the region that calls this is.
   */
  public void goRootOutcome(ActionEvent event)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    if (this._regionSite != null)    
    {
      this._regionSite.queueRootOutcome(context, _OUTCOME_GUIDE);
    }
  }    
  
  /*
   * Called from a command on the parent (base) region, this gets a handle to the child region and then calls 
   * queueActionEventInRegion() on it. For the MethodExpression provided to this method the first action from the 
   * list of actions returned by RegionModel.getActions() is retrieved and the outcome used to construct the 
   * MethodExpression.
   */
  public void queueActionEvent(ActionEvent event)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    
    DemoRegionModel parentRegionModel = (DemoRegionModel)this.getParentRegionModel();
    RichRegion childRegion = parentRegionModel.getThisRegion();
    if (childRegion != null)
    {
      ExpressionFactory expressionFactory = context.getApplication().getExpressionFactory();
      ELContext elContext = context.getELContext();
      Class<String> stringClass = String.class;
      Class[] emptyArray = new Class[0];
      
      List<? extends RegionAction> actions = parentRegionModel.getActions();
      String currOutcome = null;
      if (!actions.isEmpty())
      {
        for (RegionAction currAction:actions)
        {
          currOutcome = currAction.getOutcome();
          if (currOutcome != null)
            break;
        }
      }
      MethodExpression actionExpression = expressionFactory.createMethodExpression(elContext,
                                                                                   currOutcome,
                                                                                   stringClass,
                                                                                   emptyArray);
      
      childRegion.queueActionEventInRegion(actionExpression, null, 
                                           null, false, -1, -1, PhaseId.ANY_PHASE);
    }
  }
  
  public void resetForm(ActionEvent event)
  {
    FacesMessage parentMessage = new FacesMessage(
      FacesMessage.SEVERITY_INFO,
      "This is a faces message.",
      "This is the restListener for the action event type. " +
      "The editable values in the parent form have been reset to: " + getFullName());
    FacesContext facesContext = FacesContext.getCurrentInstance();
    facesContext.addMessage(null, parentMessage);
  }
  
  public RichRegion getThisRegion()
  {
    return _thisRegion;  
  }
  
  public void setThisRegion(RichRegion thisRegion)
  {
    _thisRegion = thisRegion;
  }

  /**
   * ActionEvent handler for handling command to refresh the model
   * @param actionEvent
   */
  public void refreshRegion(ActionEvent actionEvent)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    
    UIXRegion region = null;
    UIComponent currComponent = actionEvent.getComponent();
    
    while (currComponent != null)
    {
      currComponent = currComponent.getParent();
      
      if (currComponent instanceof UIXRegion)
      {
        region = (UIXRegion)currComponent;
        break;
      }
    }
    
    if (region != null)    
      region.refresh(context);
  }

  /**
   * Context callback for testing recursive invocation.
   * @param context
   * @param target
   */
  public void invokeContextCallback(FacesContext context, UIComponent target)
  {
    target.getAttributes().put("text", "Recursively invoked");
    AdfFacesContext.getCurrentInstance().addPartialTarget(target);
  }

  private Map<String, DemoRegionAction> _getActionToRegionActionMap(FacesContext context)
  {
    String viewId = getRegionViewId(context);
    
    // get the Map of actions to viewIds for this viewId
    return _actionMaps.get(viewId);
  }


  private void _setViewId(FacesContext context, String newViewId)
  {
    _viewId = newViewId;
    
    Map session = context.getExternalContext().getSessionMap();
    session.put(_sessionMapViewIdKey, newViewId);
  }

  private Object _setupEL(FacesContext fc, String name, Object value)
  {
    Map request = fc.getExternalContext().getRequestMap();
    return request.put(name, value);
  }
  
  private static LinkedList<DemoRegionModel> _getRegionModelStack(FacesContext context)
  {
    Map<String, Object> requestMap = context.getExternalContext().getRequestMap();

    LinkedList<DemoRegionModel> regionStack = (LinkedList<DemoRegionModel>)
                                               requestMap.get(_CURR_REGION_MODEL_KEY);

    if (regionStack == null)
    {
      regionStack = new LinkedList<DemoRegionModel>();
      requestMap.put(_CURR_REGION_MODEL_KEY, regionStack);
    }

    return regionStack;
  }

  private Long _getUnique()
  {
    return new Random().nextLong();
  }

  private Integer _getRecursionCountByOutcome(String outcome)
  {
    if(_CALL_ACTION.equals(outcome))
    {
      _recursionCount++;
    }
    else if(_BACK_ACTION.equals(outcome))
    {
      _recursionCount--;
    }
    return _recursionCount;
  }

  private final class Demo extends AbstractMap
  {
    public Set entrySet()
    {
      return Collections.emptySet();
    }

    public Object get(Object key)
    {
      if ("viewId".equals(key))
        return _viewId;
      else if ("regionParentViewId".equals(key))
      {
        FacesContext fc = FacesContext.getCurrentInstance();
        return fc.getViewRoot().getViewId();
      }
      else
        return null;
    }
  }

  private static class RegionNavigationHandler extends NavigationHandler
  {
    RegionNavigationHandler(NavigationHandler oldHandler)
    {
      _oldHandler = oldHandler;
    }
    
    public void handleNavigation(FacesContext context, String fromAction, String outcome)
    {
      LinkedList<DemoRegionModel> regionContextStack = _getRegionModelStack(context);
      ListIterator<DemoRegionModel> lIter =  regionContextStack.listIterator(regionContextStack.size());
      
      DemoRegionModel currRegion = null;
      if (lIter.hasPrevious())
        currRegion = lIter.previous();
      
      boolean handledNavigation = false;
      
      if (currRegion != null)
        handledNavigation =  currRegion.navigateRegion(context, fromAction, outcome);
      
      if (!handledNavigation)
        _oldHandler.handleNavigation(context, fromAction, outcome);
    }    
    
    private final NavigationHandler _oldHandler;
  }

  private static final String _CURR_REGION_MODEL_KEY = 
                    "oracle.adfdemo.view.components.rich.DemoRegionModel.CURR_REGION";
  
  private static final String _VAR = "demo";
  private static final String _OUTCOME_GUIDE = "guide";
  private static final String _OUTCOME_REGION_HOME = "guide.region";

  private static final Map<String, Map<String, DemoRegionAction>> _ACTION_MAP;
  
  private static class DemoRegionAction extends RegionModel.RegionAction
  {
    public DemoRegionAction(String outcome, String destinationViewId, String displayName)
    {
      if (outcome == null)
        throw new IllegalArgumentException();

      if (destinationViewId == null)
        throw new IllegalArgumentException();
      
      _outcome = outcome;
      _destinationViewId = destinationViewId;
      _displayName = displayName;
    }
    
    public String getOutcome()
    {
      return _outcome;
    }

    public String getDestinationViewId()
    {
      return _destinationViewId;
    }

    public String getDisplayName()
    {
      return _displayName;
    }

    private final String _outcome;
    private final String _destinationViewId;
    private final String _displayName;
  }
 
  public static class TestTableData 
  {
      List dataLeft;
      List dataRight;
      
      public TestTableData() {
          initData(); 
      }

      private void initData() {
          Map row = null;
              
          dataLeft = new ArrayList();
          row = new HashMap();
          row.put("col1", "One");
          dataLeft.add(row);
          row = new HashMap();
          row.put("col1", "Two");
          dataLeft.add(row);
          row = new HashMap();
          row.put("col1", "Three");
          dataLeft.add(row);
          row = new HashMap();
          row.put("col1", "Four");
          dataLeft.add(row);
          
          dataRight = new ArrayList();
          
      }

      public void setDataLeft(List dataLeft) {
          this.dataLeft = dataLeft;
      }

      public List getDataLeft() {
          return dataLeft;
      }

      public void setDataRight(List dataRight) {
          this.dataRight = dataRight;
      }

      public List getDataRight() {
          return dataRight;
      }
      
      public DnDAction addRole(DropEvent dropEvent) {
          
          RowKeySet selectedIndex = (RowKeySetImpl)dropEvent.getTransferable().getData(RowKeySet.class);
         List moveData = new ArrayList();
         
          Iterator iter = selectedIndex.iterator();
          while(iter.hasNext()) {
              Integer index = (Integer)iter.next();
              moveData.add(getDataLeft().get(index.intValue()));
          }

          getDataLeft().removeAll(moveData);
          getDataRight().addAll(moveData);
          
          refreshTables();
          
          return DnDAction.MOVE;
      }    

      private void refreshTables() {
          UIComponent component = FacesContext.getCurrentInstance().getViewRoot().findComponent("dmoTpl:myRegion:tableLeft");
          AdfFacesContext.getCurrentInstance().addPartialTarget(component);

          component = FacesContext.getCurrentInstance().getViewRoot().findComponent("dmoTpl:myRegion:tableRight");
          AdfFacesContext.getCurrentInstance().addPartialTarget(component);
      }

      public DnDAction removeRole(DropEvent dropEvent) {
          return DnDAction.MOVE;
      }

      public void resetDataButton(ActionEvent actionEvent) {
          initData();
          refreshTables();
      }
  }
  
  static
  {
    Map<String, Map<String, DemoRegionAction>> actionMap = new HashMap<String, Map<String, DemoRegionAction>>();
    Map<String, DemoRegionAction> sampleMap = Collections.singletonMap(
                                      "NEXT",
                                      new DemoRegionAction("NEXT",
                                                           "/components/pagefragments/sample2Region.jspx",
                                                           "Next Page"));                            
    Map<String, DemoRegionAction> sample2Map = Collections.singletonMap(
                                      "PREV",
                                      new DemoRegionAction("PREV",
                                                           "/components/pagefragments/sampleRegion.jspx",
                                                           "Previous Page"));                            
   
    actionMap.put("/components/pagefragments/sampleRegion.jspx", sampleMap);
    actionMap.put("/components/pagefragments/sample2Region.jspx", sample2Map);
    _ACTION_MAP = actionMap;
  }
  
  private final String _name;
  private final String _defaultView;
  private final String _sessionMapViewIdKey;
  private final Map<String, Map<String, DemoRegionAction>> _actionMaps;
  private static final String _CALL_ACTION = "CALL";
  private static final String _BACK_ACTION = "BACK";
  
  private String _viewId;
  private Serializable _viewInstance;
  private Object _oldval;
  private RegionSite _regionSite;
  private TestTableData _tableModel;
  private String _fullName;
  
  private RichRegion _thisRegion;
  
  private int _recursionCount = 0;
  private DemoRegionModel _region1;
  private DemoRegionModel _region2;
  private DemoRegionModel _childRegionModel;
  private DemoRegionModel _parentRegionModel;
  private DemoRegionModel _recursiveRegionModel;
  private DemoRegionModel _simpleContentRegionModel;
  private DemoRegionModel _upOneLevelRegionModel;
  private DemoRegionModel _masterRegionModel;
  private DemoRegionModel _detailRegionModel;
  private DemoRegionModel _geometryManagmentRegionModel;
  private DemoRegionModel _validationModel;
  private DemoRegionModel _dragNDropModel;
}
