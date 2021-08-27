/** Copyright (c) 2008, 2018, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.myfaces.trinidad.component.UIXInput;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;

public class TableTestUtils
{

  private String addedSet = "---";
  private String removedSet = "---";
  private String label = "Selected Row: ";

  public TableTestUtils()
  {
  }

  public void rowSelected(SelectionEvent selectionEvent)
  {
    FacesContext context = FacesContext.getCurrentInstance();

    setAddedSet(selectionEvent.getAddedSet().toString());
    setRemovedSet(selectionEvent.getRemovedSet().toString());
    UIXInput addedSetComponent = (UIXInput) context.getViewRoot().findComponent("tmplt:addedSet");
    UIXInput removedSetComponent = (UIXInput) context.getViewRoot().findComponent("tmplt:removedSet");
    addedSetComponent.setValue(getAddedSet());
    removedSetComponent.setValue(getRemovedSet());

    RequestContext adfContext = RequestContext.getCurrentInstance();
    adfContext.addPartialTarget(addedSetComponent);
    adfContext.addPartialTarget(removedSetComponent);
  }

  public void setAddedSet(String message)
  {
    this.addedSet = message;
  }

  public String getAddedSet()
  {
    return addedSet;
  }

  public void setRemovedSet(String removedSet)
  {
    this.removedSet = removedSet;
  }

  public String getRemovedSet()
  {
    return removedSet;
  }

  public void setEditingMode(ActionEvent event)
  {
    String buttonId = event.getComponent().getId();
    String modeName = RichTable.EDITING_MODE_READ_ONLY;
    if (buttonId.contains("c2eButton"))
    {
      modeName = RichTable.EDITING_MODE_CLICK_TO_EDIT;
    }
    else if (buttonId.contains("editAllButton"))
    {
      modeName = RichTable.EDITING_MODE_EDIT_ALL;
    }
    FacesContext context = FacesContext.getCurrentInstance();
    RichTable table = (RichTable) context.getViewRoot().findComponent("tmplt:table1");

    table.getAttributes().put(RichTable.EDITING_MODE_KEY.getName(), modeName);
    RequestContext.getCurrentInstance().addPartialTarget(table);
  }

  public void setRowSelectionMode(ActionEvent event)
  {
    String buttonId = event.getComponent().getId();
    String modeName = RichTable.ROW_SELECTION_NONE;
    if (buttonId.contains("Single"))
    {
      modeName = RichTable.ROW_SELECTION_SINGLE;
    }
    else if (buttonId.contains("Multiple"))
    {
      modeName = RichTable.ROW_SELECTION_MULTIPLE;
    }
    FacesContext context = FacesContext.getCurrentInstance();
    RichTable table = (RichTable) context.getViewRoot().findComponent("tmplt:table1");

    table.getAttributes().put(RichTable.ROW_SELECTION_KEY.getName(), modeName);
    RequestContext.getCurrentInstance().addPartialTarget(table);
  }


}
