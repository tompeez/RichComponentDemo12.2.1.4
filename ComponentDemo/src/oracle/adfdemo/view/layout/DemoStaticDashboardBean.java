package oracle.adfdemo.view.layout;

import java.io.Serializable;

import java.util.ArrayList;

import java.util.List;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.layout.RichPanelDashboard;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.change.ComponentChange;
import org.apache.myfaces.trinidad.change.ReorderChildrenComponentChange;
import org.apache.myfaces.trinidad.context.RequestContext;

public class DemoStaticDashboardBean implements Serializable
{
  public DemoStaticDashboardBean()
  {
    // Set up the side bar items to match those in the _panelDashboard:
    int childCount = 10;
    _sideBarItems = new ArrayList<SideBarItem>(childCount);
    for (int i=1; i<=childCount; i++)
    {
      _sideBarItems.add(new SideBarItem("box" + i));
    }
  }

  /**
   * Returns the dashboard.
   * @return the dashboard
   */
  public RichPanelDashboard getDashboard()
  {
    return _dashboard;
  }

  /**
   * Specifies the dashboard.
   * @param dashboard the dashboard
   */
  public void setDashboard(RichPanelDashboard dashboard)
  {
    _dashboard = dashboard;
  }

  /**
   * Returns the side bar links.
   * @return the side bar links
   */
  public UIComponent getSideBar()
  {
    return _sideBar;
  }

  /**
   * Specifies the side bar links.
   * @param sideBar the side bar links
   */
  public void setSideBar(UIComponent sideBar)
  {
    _sideBar = sideBar;
  }

  /**
   * Retrieves the list of side bar items.
   * @return the list of side bar items
   */
  public List<SideBarItem> getSideBarItems()
  {
    return _sideBarItems;
  }

  /**
   * Handler for the re-ordering drop event on the panelDashboard.
   * Change the component order in the component tree and update the side bar with new insertion
   * indexes.
   * @param e the DropEvent for the dashboard child reordering
   * @return the DnDAction that determines whether to redraw the drop target
   */
  public DnDAction move(DropEvent e)
  {
    UIComponent dropComponent = e.getDropComponent();
    UIComponent movedComponent = e.getTransferable().getData(DataFlavor.UICOMPONENT_FLAVOR);
    UIComponent movedParent = movedComponent.getParent();

    // Ensure that we are handling the re-order of a direct child of the panelDashboard:
    if (movedParent.equals(dropComponent) && dropComponent.equals(_dashboard))
    {
      // Build the new list of IDs, placing the moved component at the drop index.
      int dropIndex = e.getDropSiteIndex();
      String movedId = movedComponent.getId();
      List<String> reorderedIdList = new ArrayList<String>(_dashboard.getChildCount());
      int index = 0;
      boolean added = false;

      for (UIComponent currChild : _dashboard.getChildren())
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
      ComponentChange change = new ReorderChildrenComponentChange(reorderedIdList);
      change.changeComponent(_dashboard);

      // Add the side bar as a partial target since we need to redraw the state of the side bar
      // items since their insert indexes are changed:
      RequestContext rc = RequestContext.getCurrentInstance();
      rc.addPartialTarget(_sideBar);
    }

    return DnDAction.NONE; // the client is already updated, so no need to redraw it again
  }

  /**
   * Action listener for the insert button.  Sets the rendered state of the panelBox to true and
   * lets the panelDashboard know about it so the visual change can be animated.
   * @param e the ActionEvent
   */
  public void handleInsert(ActionEvent e)
  {
    UIComponent eventComponent = e.getComponent();
    String panelBoxId = eventComponent.getAttributes().get("panelBoxId").toString();
    UIComponent panelBox = _dashboard.findComponent(panelBoxId);

    // Make this panelBox rendered:
    panelBox.setRendered(true);

    // Since the dashboard is already shown, let's perform an optimized render so the whole
    // dashboard doesn't have to be re-encoded:
    int insertIndex = 0;
    List<UIComponent> children = _dashboard.getChildren();
    for (UIComponent child : children)
    {
      if (child.equals(panelBox))
      {
        // Let the dashboard know that only the one child should be encoded during the render phase:
        _dashboard.prepareOptimizedEncodingOfInsertedChild(
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
    rc.addPartialTarget(_sideBar);
  }

  /**
   * Action listener for the delete button.  Sets the rendered state of the panelBox to false and
   * lets the panelDashboard know about it so the visual change can be animated.
   * @param e the ActionEvent
   */
  public void handleDelete(ActionEvent e)
  {
    UIComponent eventComponent = e.getComponent();
    String panelBoxId = eventComponent.getAttributes().get("panelBoxId").toString();
    UIComponent panelBox = _dashboard.findComponent(panelBoxId);

    // Make this panelBox non-rendered:
    panelBox.setRendered(false);

    // Since the dashboard is already shown, let's perform an optimized render so the whole
    // dashboard doesn't have to be re-encoded:
    int deleteIndex = 0;
    List<UIComponent> children = _dashboard.getChildren();
    for (UIComponent child : children)
    {
      if (child.equals(panelBox))
      {
        _dashboard.prepareOptimizedEncodingOfDeletedChild(
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

    // Add the side bar as a partial target since we need to redraw the state of the side bar item
    // that corresponds to the deleted item:
    RequestContext rc = RequestContext.getCurrentInstance();
    rc.addPartialTarget(_sideBar);
  }

  /**
   * Represents a single side bar item used for links to "insert" panelBoxes into the panelDashboard.
   */
  public class SideBarItem
  {
    public SideBarItem(String panelBoxId)
    {
      _panelBoxId = panelBoxId;
    }

    /**
     * Retrieves the ID of the panelBox associated with this side bar item.
     * @return the ID of the panelBox associated with this side bar item
     */
    public String getPanelBoxId()
    {
      return _panelBoxId;
    }

    /**
     * Retrieves whether this panelBox child is rendered in the dashboard.
     * @return true if rendered, false otherwise
     */
    public boolean isPanelBoxRendered()
    {
      return _dashboard.findComponent(_panelBoxId).isRendered();
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
      for (UIComponent component : _dashboard.getChildren())
      {
        if (component.getId().equals(_panelBoxId))
        {
          return index;
        }
        else if (component.isRendered())
        {
          index++;
        }
      }
      throw new RuntimeException("Unable to determine the index if rendered for " + _panelBoxId);
    }

    private String _panelBoxId;
  }

  private static final long serialVersionUID = 1L;    

  private RichPanelDashboard _dashboard;
  private UIComponent        _sideBar;
  private List<SideBarItem>  _sideBarItems;
}
