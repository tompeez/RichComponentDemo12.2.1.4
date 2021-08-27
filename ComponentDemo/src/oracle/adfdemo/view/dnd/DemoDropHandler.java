/*
* Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved.
 * All rights reserved
 */
package oracle.adfdemo.view.dnd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.change.ChangeManager;
import org.apache.myfaces.trinidad.change.ComponentChange;
import org.apache.myfaces.trinidad.change.ReorderChildrenComponentChange;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;


public class DemoDropHandler
{
  /**
   * Returns Object[]  drag content
   * @return
   */
  public Object[] getEmpNames()
  {
    return new Object[]{"Blake", "Adam", "Andy"};
  }

  /**
   * Returns List drag content
   * @return
   */
  public List<Object> getEmpNamesList()
  {
    return Arrays.asList(getEmpNames());
  }
  
  public DnDAction handleOuterFireDrop(DropEvent dropEvent)
  {
    return _handleFireDrop(dropEvent, "label");
  }

  public DnDAction handleFireDrop(DropEvent dropEvent)
  {
    return _handleFireDrop(dropEvent, "value");
  }
  
  /**
   * Check that DataFlavors serialize correctly
   * @param flavor
   */
  private void _checkSerializationOfDataFlavor(DataFlavor flavor)
  {
    try
    {
      ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
      ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
      objectOutput.writeObject(flavor);
      
      ObjectInputStream objectInput = 
                        new ObjectInputStream(new ByteArrayInputStream(byteOutput.toByteArray()));
      DataFlavor newFlavor = (DataFlavor)objectInput.readObject();
      
      if (!flavor.equals(newFlavor))
      {
        System.out.println("No match to serialized flavor");        
      }
    }
    catch (IOException e)
    {
      System.out.println("failed DataFlavor serialization");
    }
    catch (ClassNotFoundException e)
    {
      System.out.println("failed DataFlavor deserialization");
    }
  }
  
  private DnDAction _handleFireDrop(DropEvent dropEvent, String propertyName)
  {
    // dump information about the drop
    _dumpDropEventInfo(dropEvent);
    
    Transferable dropTransferable = dropEvent.getTransferable();
    
    Object[] firedEmployees = dropTransferable.getData(DataFlavor.OBJECT_ARRAY_FLAVOR);
    _checkSerializationOfDataFlavor(DataFlavor.OBJECT_ARRAY_FLAVOR);
    
    if (firedEmployees != null)
    {
      UIComponent dropComponent = dropEvent.getDropComponent();
 
      //dropComponent.getAttributes().put("foo", new Object());
     
      // test non-Serializable Objects in Session Map
      //javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bar", new Object());
      
      // test non-Serialiable Object in Application Map
      //javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("bar", new Object());
      
         
      // update the specified property of the drop component with the Object[] dropped
      dropComponent.getAttributes().put(propertyName, Arrays.toString(firedEmployees));
      
      return DnDAction.COPY;
    }
    else
    {
      return DnDAction.NONE;      
    }    
  }

  public DnDAction handleCollectionFireDrop(DropEvent dropEvent)
  {
    // dump information about the drop
    _dumpDropEventInfo(dropEvent);
    
    Transferable dropTransferable = dropEvent.getTransferable();
    
    Collection firedEmployees = dropTransferable.getData(_COLLECTION_DATAFLAVOR);
    
    if (firedEmployees != null)
    {
      UIComponent dropComponent = dropEvent.getDropComponent();
      
      // update the value of the drop component with the Collection dropped
      ((ValueHolder)dropComponent).setValue(firedEmployees.toString());
      
      return DnDAction.COPY;
    }
    else
    {
      return DnDAction.NONE;      
    }
  }

  public DnDAction handleDrop(DropEvent dropEvent)
  {
    // dump information about the drop
    _dumpDropEventInfo(dropEvent);
    
    Transferable dropTransferable = dropEvent.getTransferable();
    
    DataFlavor<RowKeySet> rowKeySetFlavor = DataFlavor.getDataFlavor(RowKeySet.class, "fileModel");
    
    RowKeySet tableDrop = dropTransferable.getData(rowKeySetFlavor);
    
    if (tableDrop != null)
    {
      UIComponent dropComponent = dropEvent.getDropComponent();
      
      // get the data for the dropped rows
      CollectionModel dragModel = dropTransferable.getData(CollectionModel.class);
      
      StringBuilder rowOutput = new StringBuilder();
      
      if (dragModel != null)
      {
        for (Object currKey: tableDrop)
        {
          dragModel.setRowKey(currKey);
          
          Object rowValue = dragModel.getRowData();
          rowOutput.append(rowValue);
          rowOutput.append(',');
        }
      }
      
      rowOutput.append(tableDrop.getSize());
      rowOutput.append(" rows");
      
      // update the value of the drop component with the String dropped
      ((ValueHolder)dropComponent).setValue(rowOutput.toString());
      
      return DnDAction.COPY;
    }
    else
    {
      return DnDAction.NONE;      
    }
  }
  
  private void _dumpDropEventInfo(DropEvent dropEvent)
  {    
    System.out.println("Drop location: Drop x:" + dropEvent.getDropX() + "," +
                       "Drop y:" + dropEvent.getDropY());    
  }
  
  private static final DataFlavor<Collection> _COLLECTION_DATAFLAVOR =
                                                 DataFlavor.getDataFlavor(Collection.class);
  
  /**
   * Returns Object[]  drag content
   * @return
   */
  public Object[] getDrinks()
  {
    return new Object[]{"Milk", "Wine", "Soda"};
  }

  /**
   * Returns List drag content
   * @return
   */
  public List<Object> getDrinksList()
  {
    return Arrays.asList(getDrinks());
  }

  public DnDAction handleTableDrop(DropEvent dropEvent)
  {
    // Add event code here...
    System.out.println("Drop me table:" + dropEvent);
    return DnDAction.NONE;
  }

  /**
   * Move the component to the end of the list when dropped
   * @param dropEvent
   * @return The DnDAction performed or DnDAction.NONE, if drop is cancelled
   */
  public DnDAction handleComponentMove(DropEvent dropEvent)
  {
    Transferable dropTransferable = dropEvent.getTransferable();
    UIComponent movedComponent =  dropTransferable.getData(DataFlavor.UICOMPONENT_FLAVOR);

    if ((movedComponent != null) && DnDAction.MOVE.equals(dropEvent.getProposedAction()))
    {
      UIComponent dropComponent = dropEvent.getDropComponent();
      UIComponent dropParent = dropComponent.getParent();
      UIComponent movedParent = movedComponent.getParent();
      UIComponent rootParent;
      ComponentChange change;
      
      // build the new list of ids, placing the moved component after the dropped component
      String movedLayoutId = movedParent.getId();
      String dropLayoutId = dropComponent.getId();
        
      List<String> reorderedIdList = new ArrayList<String>(dropParent.getChildCount());
      
      // flag indicating if we are moving up or down
      boolean movedLayoutIdFound = false;
      
      for (UIComponent currChild : dropParent.getChildren())
      {
        String currId = currChild.getId();
          
        if (!currId.equals(movedLayoutId))
        {
          if(!movedLayoutIdFound && currId.equals(dropLayoutId))
            reorderedIdList.add(movedLayoutId);
          
          reorderedIdList.add(currId);

          if(movedLayoutIdFound && currId.equals(dropLayoutId))
            reorderedIdList.add(movedLayoutId);              
        }
        else
          movedLayoutIdFound = true;
      }
        
      change = new ReorderChildrenComponentChange(reorderedIdList);
      rootParent = dropParent;
      
      
      ChangeManager cm = RequestContext.getCurrentInstance().getChangeManager();

      // add the change
      cm.addComponentChange(FacesContext.getCurrentInstance(), rootParent, change);
      
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
