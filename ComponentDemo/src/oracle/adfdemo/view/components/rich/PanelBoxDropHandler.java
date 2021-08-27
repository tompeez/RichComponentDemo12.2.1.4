/*
* Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved.
 * All rights reserved
 */
package oracle.adfdemo.view.components.rich;


import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.change.ChangeManager;
import org.apache.myfaces.trinidad.change.ComponentChange;
import org.apache.myfaces.trinidad.change.MoveChildComponentChange;
import org.apache.myfaces.trinidad.change.ReorderChildrenComponentChange;
import org.apache.myfaces.trinidad.context.RequestContext;


public class PanelBoxDropHandler
{
  /**
   * Move the component to the end of the list when dropped
   * @param dropEvent
   * @return The DnDAction performed or DnDAction.NONE, if drop is cancelled
   */
  public DnDAction handleComponentMove(DropEvent dropEvent)
  {
    Transferable dropTransferable = dropEvent.getTransferable();
    UIComponent movedComponent =  dropTransferable.getData(DataFlavor.UICOMPONENT_FLAVOR);
    
    FacesContext context = FacesContext.getCurrentInstance();

    if ((movedComponent != null) && DnDAction.MOVE.equals(dropEvent.getProposedAction()))
    {
      UIComponent dropComponent = dropEvent.getDropComponent();
      UIComponent dropParent = dropComponent.getParent();
      UIComponent movedParent = movedComponent.getParent();
      UIComponent rootParent;
      ComponentChange change;
      
      // If it is a DND within the same container (which is not usual in this particular demo)
      //  use the child reorder change
      if (dropComponent == movedParent)
      {
        // build the new list of ids, placing the moved componen after the dropped component
        String movedLayoutId = movedParent.getId();
        String dropLayoutId = dropComponent.getId();
        
        List<String> reorderedIdList = new ArrayList<String>(dropParent.getChildCount());
        
        for (UIComponent currChild : dropParent.getChildren())
        {
          String currId = currChild.getId();
          
          if (!currId.equals(movedLayoutId))
          {
            reorderedIdList.add(currId);

            if (currId.equals(dropLayoutId))
            {
              reorderedIdList.add(movedLayoutId);              
            }
          }
        }
        
        change = new ReorderChildrenComponentChange(reorderedIdList);
        rootParent = dropParent;
        
        ChangeManager cm = RequestContext.getCurrentInstance().getChangeManager();

        // add the change
        cm.addComponentChange(context, rootParent, change);
      }
      else
      {
        MoveChildComponentChange moveChange = new MoveChildComponentChange(movedComponent,
                                                                           dropComponent);
        
        ChangeManager cm = RequestContext.getCurrentInstance().getChangeManager();
        
        // add the change
        rootParent = moveChange.add(context, cm);
        
        change = moveChange;
      }

      // apply the change to the component tree immediately
      change.changeComponent(rootParent);
      
      // redraw the shared parent
      AdfFacesContext.getCurrentInstance().addPartialTarget(rootParent);
      
      return DnDAction.MOVE;
    }
    else
    {
      return DnDAction.NONE;      
    }
  }
}
