/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.layout;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.layout.RichPanelDashboard;

import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.change.ComponentChange;
import org.apache.myfaces.trinidad.change.ReorderChildrenComponentChange;
import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.component.visit.VisitTreeUtils;
import org.apache.myfaces.trinidad.context.RequestContext;

/**
 * Managed bean for handling dynamic tabs as seen in the Navigation-Master-Detail example.
 */
public class DemoDashboardBean implements Serializable
{
  /**
   * Default constructor.
   */
  public DemoDashboardBean()
  {
    _sideBarItems = new ArrayList<SideBarItem>(9);
    _sideBarItems.add(_ACTIVITY_CENTER);
    _sideBarItems.add(_CONTACTS);
    _sideBarItems.add(_CUSTOMER_INFO);
    _sideBarItems.add(_ORG_CHART);
    _sideBarItems.add(_DISCUSSIONS);
    _sideBarItems.add(_NOTES);
    _sideBarItems.add(_OPPORTUNITY_TEAM);
    _sideBarItems.add(_RECOMMENDATIONS);
    _sideBarItems.add(_REVENUE);
  }

  public List<String> getContactsList()
  {
    List<String> contactData = new ArrayList<String>();
    contactData.add("Aquarium Guards LLC");
    contactData.add("Everyday Moats and Company");
    contactData.add("Exotic Seafood and Movie Studio");
    contactData.add("Luxury Goods GmbH");
    contactData.add("Neighborhood Robotic Emporium");
    contactData.add("Police Alternatives, Inc.");
    contactData.add("Satellite Photography Tree Artists LLC");
    contactData.add("Seafarers of the Reef and Sons");
    contactData.add("Secret Agent Supply Warehouse, Inc.");
    contactData.add("Undersea Living Monthly");
    contactData.add("Warehouse Guard and Protection Agency");
    return contactData;
  }

  /**
   * Fetches the list of panel data objects.
   * @return a list of PanelData objects
   */
  public List<SideBarItem> getSideBarDataItems()
  {
    return _sideBarItems;
  }

  public String getMaximizedTitle()
  {
    Iterator<SideBarItem> i = _sideBarItems.iterator();
    while (i.hasNext())
    {
      SideBarItem data = i.next();
      if (_maximizedPanelKey.equals(data.getItemId()))
      {
        return data.getTitle();
      }
    }
    return null;
  }

  public String getMaximizedKey()
  {
    if (_maximizedPanelKey == null)
    {
      return "empty"; // to avoid a FileNotFoundException
    }

    return _maximizedPanelKey.toString();
  }

  /**
   * Changes the shown dashboard items to match the desired state.
   * (_maximizedPanelKey must be null)
   * @param e the ActionEvent associated with the action
   */
  public void showPresetItems(ActionEvent e)
  {
    UIComponent eventComponent = e.getComponent();
    Object desiredMode = _getAssociatedModeKey(eventComponent);
    SideBarItem[] desiredItems = null;
    if ("opportunity".equals(desiredMode))
    {
      desiredItems = _KNOW_YOUR_OPPORTUNITY_ITEMS;
    }
    else if ("working".equals(desiredMode))
    {
      desiredItems = _WORKING_THE_DEAL_ITEMS;
    }
    else if ("closing".equals(desiredMode))
    {
      desiredItems = _CLOSING_THE_DEAL_ITEMS;
    }
    else if ("all".equals(desiredMode))
    {
      desiredItems = _ALL_ITEMS;
    }
    else if ("none".equals(desiredMode))
    {
      desiredItems = _NO_ITEMS;
    }
    else
    {
      throw new IllegalStateException("Unknown preset items mode: " + desiredMode);
    }

    FacesContext context = FacesContext.getCurrentInstance();

    // Remove any undesired items:
    int deleteIndex = 0;
    List<UIComponent> children = _getDashboard().getChildren();
    for (UIComponent child : children)
    {
      boolean childIsDesired = false;
      for (int i=0; i<desiredItems.length; i++)
      {
        if (desiredItems[i].getItemId().equals(child.getId()))
        {
          childIsDesired = true;
          break;
        }
      }

      if (!childIsDesired && child.isRendered())
      {
        // Set rendered to false and increment since we want to count the ones we are switching
        // too as part of the index (so the correct one will be deleted in the browser):
        child.setRendered(false);
        _getDashboard().prepareOptimizedEncodingOfDeletedChild(context, deleteIndex++);
      }

      if (child.isRendered())
      {
        // Only count rendered children since that's all that the panelDashboard can see:
        deleteIndex++;
      }
    }

    // Add any missing items:
    int insertIndex = 0;
    for (UIComponent child : children)
    {
      for (int i=0; i<desiredItems.length; i++)
      {
        if (desiredItems[i].getItemId().equals(child.getId()))
        {
          if (!child.isRendered())
          {
            child.setRendered(true);
            _getDashboard().prepareOptimizedEncodingOfInsertedChild(context, insertIndex);
          }
          break;
        }
      }

      if (child.isRendered())
      {
        // Only count rendered children since that's all that the panelDashboard can see:
        insertIndex++;
      }
    }

    // Redraw the side bar so that we can draw update the colors of the opened items and the
    // insertion indexes of the links:
    RequestContext rc = RequestContext.getCurrentInstance();
    rc.addPartialTarget(_getSideBarContainer());
  }

  /**
   * Handler for component movements into the side bar from the dashboard.
   * @param e the DropEvent for the movement
   * @return the DnDAction that determines whether to redraw the drop target
   */
  public DnDAction handleSideBarDrop(DropEvent e)
  {
    UIComponent dragComponent = e.getDragComponent();
    UIComponent dragParent    = dragComponent.getParent();

    // Ensure that the drag source is one of the items from the dashboard:
    if (dragParent.equals(_getDashboard()))
    {
      _minimize(dragComponent);
    }

    return DnDAction.NONE; // the client is already updated, so no need to redraw it again
  }

  /**
   * Handler for the re-ordering drop event on the panelDashboard.
   * Change the component order in the component tree and update the side bar with new insertion
   * indexes.
   * This is also the handler for dropping in a minimized side bar item into the dashboard.
   * In that case, the associated panelDashboard child should then be restored.
   * @param e the DropEvent for the dashboard child reordering
   * @return the DnDAction that determines whether to redraw the drop target
   */
  public DnDAction move(DropEvent e)
  {
    UIComponent dropComponent    = e.getDropComponent();
    String      dragClientId     = e.getDragClientId();
    UIComponent dragComponent    = e.getDragComponent();
    UIComponent dragParent       = dragComponent.getParent();
    RichPanelDashboard dashboard = _getDashboard();

    // Ensure that we are handling the re-order of a direct child of the panelDashboard:
    if (dragParent.equals(dropComponent) && dropComponent.equals(dashboard))
    {
      // Move the already rendered child and redraw the side bar since the insert indexes have
      // changed:
      _moveDashboardChild(e.getDropSiteIndex(), dragComponent.getId());
    }
    else
    {
      // This isn't a re-order but rather the user dropped a minimized side bar item into the
      // dashboard, in which case that item should be restored at the specified drop location.
      String panelKey = _getAssociatedPanelKey(dragClientId);

      if (panelKey != null)
      {
        UIComponent panelBoxToShow = dashboard.findComponent(panelKey);

        // Make this panelBox rendered:
        panelBoxToShow.setRendered(true);

        int insertIndex = e.getDropSiteIndex();

        // Move the already rendered child and redraw the side bar since the insert indexes have
        // changed and because the side bar minimized states are out of date:
        _moveDashboardChild(insertIndex, panelKey);

        // Let the dashboard know that only the one child should be encoded during the render phase:
        dashboard.prepareOptimizedEncodingOfInsertedChild(
          FacesContext.getCurrentInstance(),
          insertIndex);
      }
      else
      {
        System.err.println("Could not find an associatedPanelKey attribute for moved component - " + dragClientId);
      }
    }

    return DnDAction.NONE; // the client is already updated, so no need to redraw it again
  }

  public String getSideBarDragIdentifier()
  {
    return _sideBarDragIdentifier;
  }

  public void setSideBarDragIdentifier(String sideBarDragIdentifier)
  {
    _sideBarDragIdentifier = sideBarDragIdentifier;
  }

  private void _moveDashboardChild(int dropIndex, String movedId)
  {
    // Build the new list of IDs, placing the moved component at the drop index.
    RichPanelDashboard dashboard = _getDashboard();
    List<String> reorderedIdList = new ArrayList<String>(dashboard.getChildCount());
    int index = 0;
    boolean added = false;

    for (UIComponent currChild : dashboard.getChildren())
    {
      if (currChild.isRendered())
      {
        if (index == dropIndex)
        {
          reorderedIdList.add(movedId);
          added = true;
        }

        String currId = currChild.getId();
        if (currId.equals(movedId) && index < dropIndex)
        {
          // component is moved later, need to shift the index by 1
          dropIndex++;
        }

        if (!currId.equals(movedId))
        {
          reorderedIdList.add(currId);
        }
        index++;
      }
    }

    if (!added)
    {
      // Added to the very end:
      reorderedIdList.add(movedId);
    }

    // Apply the change to the component tree immediately:
    // Note that the ChangeManager.addComponentChange() is required here if using Facelets because the Facelets view
    // handler will discard the change in order when the next render phase is started.
    ComponentChange change = new ReorderChildrenComponentChange(reorderedIdList);
    RequestContext rc = RequestContext.getCurrentInstance();
    rc.getChangeManager().addComponentChange(FacesContext.getCurrentInstance(), dashboard, change);
    change.changeComponent(dashboard);

    // Add the side bar as a partial target since we need to redraw the state of the side bar items
    // since their insert indexes are changed and possibly because the side bar minimized states are
    // out of date:
    rc.addPartialTarget(_getSideBarContainer());
  }

  public void sideBarShow(ActionEvent e)
  {
    if (_maximizedPanelKey == null)
    {
      // Show non-maximized:
      UIComponent eventComponent = e.getComponent();
      String panelKey = _getAssociatedPanelKey(eventComponent);
      RichPanelDashboard dashboard = _getDashboard();
      UIComponent panelBoxToShow = dashboard.findComponent(panelKey);

      // Make this panelBox rendered:
      panelBoxToShow.setRendered(true);

      // Since the dashboard is already shown, let's perform an optimized render so the whole
      // dashboard doesn't have to be re-encoded:
      int insertIndex = 0;
      List<UIComponent> children = dashboard.getChildren();
      for (UIComponent child : children)
      {
        if (child.equals(panelBoxToShow))
        {
          // Let the dashboard know that only the one child should be encoded during the render phase:
          dashboard.prepareOptimizedEncodingOfInsertedChild(
            FacesContext.getCurrentInstance(),
            insertIndex);
          break;
        }

        if (child.isRendered())
        {
          // Only count rendered children since that's all that the panelDashboard can see:
          insertIndex++;
        }
      }

      // Add the side bar as a partial target since we need to redraw the state of the side bar item
      // that corresponds to the inserted item:
      RequestContext rc = RequestContext.getCurrentInstance();
      rc.addPartialTarget(_getSideBarContainer());
    }
    else
    {
      // Show maximized:
      sideBarMaximize(e);
    }
  }

  public void minimize(ActionEvent e)
  {
    UIComponent eventComponent = e.getComponent();
    String panelKey = _getAssociatedPanelKey(eventComponent);
    UIComponent panelBoxToMinimize = _getDashboard().findComponent(panelKey);
    _minimize(panelBoxToMinimize);
  }

  private void _minimize(UIComponent panelBoxToMinimize)
  {
    // Make this panelBox non-rendered:
    panelBoxToMinimize.setRendered(false);

    // If the dashboard is showing, let's perform an optimized render so the whole dashboard doesn't
    // have to be re-encoded.
    // If the dashboard is hidden (because the panelBox is maximized), we will not do an optimized
    // encode since we need to draw the whole thing.
    if (_maximizedPanelKey == null)
    {
      int deleteIndex = 0;
      RichPanelDashboard dashboard = _getDashboard();
      List<UIComponent> children = dashboard.getChildren();
      for (UIComponent child : children)
      {
        if (child.equals(panelBoxToMinimize))
        {
          dashboard.prepareOptimizedEncodingOfDeletedChild(
            FacesContext.getCurrentInstance(),
            deleteIndex);
          break;
        }

        if (child.isRendered())
        {
          // Only count rendered children since that's all that the panelDashboard can see:
          deleteIndex++;
        }
      }
    }

    RequestContext rc = RequestContext.getCurrentInstance();
    if (_maximizedPanelKey != null)
    {
      // Exit maximized mode:
      _maximizedPanelKey = null;

      UIXSwitcher switcher = _getSwitcher();
      switcher.setFacetName("restored");
      rc.addPartialTarget(switcher);
    }

    // Redraw the side bar so that we can update the colors of the opened items:
    rc.addPartialTarget(_getSideBarContainer());
  }

  public void sideBarMaximize(ActionEvent e)
  {
    String panelKey = _getAssociatedPanelKey(e.getComponent());
    _maximizedPanelKey = panelKey;
    UIComponent panelBoxToMaximize = _getDashboard().findComponent(panelKey);

    // Make this panelBox rendered:
    panelBoxToMaximize.setRendered(true);

    UIXSwitcher switcher = _getSwitcher();
    switcher.setFacetName("maximized");
    RequestContext rc = RequestContext.getCurrentInstance();
    rc.addPartialTarget(switcher);

    // Redraw the side bar so that we can draw update the maximized icons:
    rc.addPartialTarget(_getSideBarContainer());
  }

  public void maximize(ActionEvent e)
  {
    String panelKey = _getAssociatedPanelKey(e.getComponent());
    _maximizedPanelKey = panelKey;

    UIXSwitcher switcher = _getSwitcher();
    switcher.setFacetName("maximized");
    RequestContext rc = RequestContext.getCurrentInstance();
    rc.addPartialTarget(switcher);

    // Redraw the side bar so that we can draw update the maximized icons:
    rc.addPartialTarget(_getSideBarContainer());
  }

  public void restore(ActionEvent e)
  {
    _maximizedPanelKey = null;

    UIXSwitcher switcher = _getSwitcher();
    switcher.setFacetName("restored");
    RequestContext rc = RequestContext.getCurrentInstance();
    rc.addPartialTarget(switcher);

    // Redraw the side bar so that we can draw update the maximized icons:
    rc.addPartialTarget(_getSideBarContainer());
  }

  private String _getAssociatedPanelKey(String clientId)
  {
    AssociatedPanelKeyVisitResult callback = new AssociatedPanelKeyVisitResult();
    VisitTreeUtils.visitSingleComponent(
      FacesContext.getCurrentInstance(),
      clientId,
      callback);
    String panelKey = callback.getAssociatedPanelKey(); // added to the component by the f:attribute tag
    return panelKey;
  }

  private String _getAssociatedPanelKey(UIComponent component)
  {
    AssociatedPanelKeyVisitResult callback = new AssociatedPanelKeyVisitResult();
    VisitTreeUtils.visitSingleComponent(
      FacesContext.getCurrentInstance(),
      component.getClientId(),
      callback);
    String panelKey = callback.getAssociatedPanelKey(); // added to the component by the f:attribute tag
    return panelKey;
  }

  private class AssociatedPanelKeyVisitResult implements VisitCallback
  {
    public String getAssociatedPanelKey()
    {
      return _associatedPanelKey;
    }

    public VisitResult visit(VisitContext context, UIComponent target)
    {
      Map<String,Object> attrs = target.getAttributes();
      _associatedPanelKey = (String)attrs.get("associatedPanelKey");
      return VisitResult.COMPLETE;
    }

    private String _associatedPanelKey;
  }

  private Object _getAssociatedModeKey(UIComponent component)
  {
    Map<String,Object> attrs = component.getAttributes();
    return attrs.get("mode"); // added to the component by the f:attribute tag
  }

  private RichPanelDashboard _getDashboard()
  {
    return (RichPanelDashboard)FacesContext.getCurrentInstance().getViewRoot().findComponent(
             "dmoTpl:dashboard");
  }

  private UIComponent _getSideBarContainer()
  {
    return FacesContext.getCurrentInstance().getViewRoot().findComponent(
             "dmoTpl:sideBar");
  }

  private UIXSwitcher _getSwitcher()
  {
    return (UIXSwitcher)FacesContext.getCurrentInstance().getViewRoot().findComponent(
             "dmoTpl:switcher");
  }

  /**
   * Represents a single side bar item used for links to panelBoxes into the panelDashboard.
   */
  public static class SideBarItem implements Serializable
  {
    public SideBarItem(String title, String status)
    {
      _title = title;
      _status = status;

      // Generate the ID from the title.
      // The ID uniquely identifies which component in the dashboard that will be rendered or not.
      _itemId = title.replaceAll("[ .]", ""); // strip spaces and dots
      _itemId = _itemId.substring(0, 1).toLowerCase().charAt(0) + _itemId.substring(1);
    }

    /**
     * Retrieves the title of the side bar item.
     * @return the title of the side bar item
     */
    public String getTitle()
    {
      return _title;
    }

    /**
     * Retrieves the status for the side bar item.
     * @return the status for the side bar item
     */
    public String getStatus()
    {
      return _status;
    }

    /**
     * Retrieves the ID of the panelDashboard child associated with this side bar item.
     * @return the ID of the panelDashboard child associated with this side bar item
     */
    public String getItemId()
    {
      return _itemId;
    }

    public String getItemType()
    {
      return "SIDEBAR";
    }

    /**
     * Retrieves whether this panelBox child is rendered in the dashboard.
     * @return true if rendered, false otherwise
     */
    public boolean isPanelBoxRendered()
    {
      RichPanelDashboard dashboard = _getDashboard();
      if (dashboard == null)
      {
        throw new RuntimeException("Unexpected null dashboard.");
      }
      UIComponent panelBox = dashboard.findComponent(_itemId);
      if (panelBox == null)
      {
        throw new RuntimeException("Unexpected null panelBox id=" + _itemId);
      }
      return panelBox.isRendered();
    }

    /**
     * Retrieves the index within the list of rendered dashboard children at which the insert
     * will occur.  Since the insertion placeholder gets added before the insertion occurs on the
     * server, you must specify the location where you are planning to insert the child so that if
     * the user reloads the page, the children will continue to remain displayed in the same order.
     * @return the client rendered index at which the item needs to appear
     */
    public int getIndexIfRendered()
    {
      int index = 0;
      for (UIComponent child : _getDashboard().getChildren())
      {
        if (_itemId.equals(child.getId()))
        {
          return index;
        }
        else if (child.isRendered())
        {
          index++;
        }
      }
      throw new RuntimeException("Unable to determine the index if rendered for panelBox id=" + _itemId);
    }

    private RichPanelDashboard _getDashboard()
    {
      return (RichPanelDashboard)FacesContext.getCurrentInstance().getViewRoot().findComponent(
               "dmoTpl:dashboard");
    }

    private String _title;
    private String _itemId;
    private String _status;

    @SuppressWarnings("compatibility:1543061195598069037")
    private static final long serialVersionUID = 1L;
  }

  private SideBarItem _ACTIVITY_CENTER  = new SideBarItem("Activity Center",  "7 Total");
  private SideBarItem _CONTACTS         = new SideBarItem("Contacts",         "4 Total");
  private SideBarItem _CUSTOMER_INFO    = new SideBarItem("Customer Info",    "");
  private SideBarItem _ORG_CHART        = new SideBarItem("Org. Chart",       "7 Total");
  private SideBarItem _DISCUSSIONS      = new SideBarItem("Discussions",      "5 New, 2 Threads");
  private SideBarItem _NOTES            = new SideBarItem("Notes",            "7 New, 25 Total");
  private SideBarItem _OPPORTUNITY_TEAM = new SideBarItem("Opportunity Team", "4 Total");
  private SideBarItem _RECOMMENDATIONS  = new SideBarItem("Recommendations",  "4 New");
  private SideBarItem _REVENUE          = new SideBarItem("Revenue",          "$1,000,000, 2 Products");

  private SideBarItem[] _KNOW_YOUR_OPPORTUNITY_ITEMS = {
    _ACTIVITY_CENTER,
    _CONTACTS,
    _CUSTOMER_INFO,
    _OPPORTUNITY_TEAM
  };
  private SideBarItem[] _WORKING_THE_DEAL_ITEMS = {
    _CONTACTS,
    _CUSTOMER_INFO,
    _DISCUSSIONS,
    _NOTES,
    _RECOMMENDATIONS
  };
  private SideBarItem[] _CLOSING_THE_DEAL_ITEMS = {
    _ACTIVITY_CENTER,
    _CONTACTS,
    _CUSTOMER_INFO,
    _ORG_CHART,
    _REVENUE
  };
  private SideBarItem[] _ALL_ITEMS = {
    _ACTIVITY_CENTER,
    _CONTACTS,
    _CUSTOMER_INFO,
    _ORG_CHART,
    _DISCUSSIONS,
    _NOTES,
    _OPPORTUNITY_TEAM,
    _RECOMMENDATIONS,
    _REVENUE
  };
  private SideBarItem[] _NO_ITEMS = {};

  private List<SideBarItem> _sideBarItems;
  private String            _maximizedPanelKey;
  private String            _sideBarDragIdentifier;

  @SuppressWarnings("compatibility:1044903493103015777")
  private static final long serialVersionUID = 1L;
}
