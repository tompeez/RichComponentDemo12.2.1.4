/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.dnd;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class CollectionDnD
{
  private static ArrayList<DnDDemoData> source1Values;
  private static ArrayList<DnDDemoData> source2Values;
  private static ArrayList<DnDDemoData> source3Values;
  private static ArrayList<DnDDemoData> targetValues;
  private static ArrayList<DnDDemoData> target3Values;

  public CollectionDnD()
  {
  }

  public ArrayList<DnDDemoData> getSource1Values()
  {
    if (source1Values == null)
    {
      source1Values = new ArrayList<DnDDemoData>(3);
      source1Values.add(new DnDDemoData("hammer", 1));
      source1Values.add(new DnDDemoData("saw", 5));
      source1Values.add(new DnDDemoData("nails", 2));
      source1Values.add(new DnDDemoData("wood", 7));
    }
    return source1Values;
  }

  public ArrayList<DnDDemoData> getSource2Values()
  {
    if (source2Values == null)
    {
      source2Values = new ArrayList<DnDDemoData>(3);
      source2Values.add(new DnDDemoData("coffee", 3));
      source2Values.add(new DnDDemoData("tea", 2));
      source2Values.add(new DnDDemoData("energy drink", 5));
      source2Values.add(new DnDDemoData("water", 1));
    }
    return source2Values;
  }
  
  public ArrayList<DnDDemoData> getSource3Values()
  {
    if (source3Values == null)
    {
      source3Values = new ArrayList<DnDDemoData>(3);
      source3Values.add(new DnDDemoData("lettuce", 2));
      source3Values.add(new DnDDemoData("kale", 2));
      source3Values.add(new DnDDemoData("spinach", 1));
      source3Values.add(new DnDDemoData("chard", 4));
    }
    return source3Values;
  }  

  public ArrayList<DnDDemoData> getTargetValues()
  {
    if (targetValues == null)
    {
      targetValues = new ArrayList<DnDDemoData>(3);
      targetValues.add(new DnDDemoData("milk", 2));
      targetValues.add(new DnDDemoData("eggs", 3));
      targetValues.add(new DnDDemoData("chips", 1));
    }
    return targetValues;
  }
  
  public ArrayList<DnDDemoData> getTarget3Values()
  {
    if (target3Values == null)
    {
      target3Values = new ArrayList<DnDDemoData>(3);
      target3Values.add(new DnDDemoData("mint", 2));
      target3Values.add(new DnDDemoData("sage", 3));
      target3Values.add(new DnDDemoData("dill", 1));
    }
    return target3Values;
  }  

  public DnDAction handleDrop(DropEvent dropEvent)
  {
    return _handleDrop(dropEvent, getTargetValues(), "DnDDemoModel");
  }
  
  public DnDAction handleCopyMove(DropEvent dropEvent)
  {
    return _handleDrop(dropEvent, getTarget3Values(), "DnDDemoModel2");
  }

  private DnDAction _handleDrop(DropEvent dropEvent, 
                                ArrayList<DnDDemoData> targetValues,
                                String discriminator)
  {
    Transferable transferable = dropEvent.getTransferable();
    
    // The data in the transferrable is the row key for the dragged component.
    DataFlavor<RowKeySet> rowKeySetFlavor = DataFlavor.getDataFlavor(RowKeySet.class, discriminator);
    RowKeySet rowKeySet = transferable.getData(rowKeySetFlavor);
    if (rowKeySet != null)
    {
      // Get the model for the dragged component.
      CollectionModel dragModel = transferable.getData(CollectionModel.class);
      if (dragModel != null)
      {
        // Set the row key for this model using the row key from the transferrable.
        Object currKey = rowKeySet.iterator().next();
        dragModel.setRowKey(currKey);
          
        // And now get the actual data from the dragged model.
        // Note this won't work in a region.  Need to change this to use collectionModel data flavor.
        DnDDemoData dnDDemoData = (DnDDemoData)dragModel.getRowData();
        
        // Put the dragged data into the target model directly.  Note that if you wanted
        // validation/business rules on the drop this would be different.
        if (dropEvent.getProposedAction() == DnDAction.LINK)
          dnDDemoData = DnDDemoData.addALink(dnDDemoData);
        targetValues.add(dnDDemoData);
      }
      return dropEvent.getProposedAction();
    }
    else
    {
      return DnDAction.NONE;      
    }
  }  

  /**
   * Demos the use of providing extra properties on the component that has a dragSource.
   * @return a Map&lt;String, String&gt; properties
   */
  public Map<String, String> getExtraProps()
  {
    Map<String, String> extraProps = new HashMap<String, String>(2);
    extraProps.put("COPY", "Copy Operation");
    extraProps.put("MOVE", "Move Operation");
    return extraProps;
  }
  
  public void endListener(DropEvent dropEvent)
  {
    _updateSource(dropEvent, getSource2Values(), "DnDDemoModel");
  }
  
  public void endListenerMove(DropEvent dropEvent)
  {
    // On;y remove the source value when the operation is 
    if (dropEvent.getDropAction() != DnDAction.MOVE)
      return;
    
    _updateSource(dropEvent, getSource3Values(), "DnDDemoModel2");
  }

  private void _updateSource(DropEvent dropEvent, 
                             ArrayList<DnDDemoData> sourceValues, 
                             String discriminator)
  {
    Transferable transferable = dropEvent.getTransferable();
    
    // The data in the transferrable is the row key for the dragged component.
    DataFlavor<RowKeySet> rowKeySetFlavor = DataFlavor.getDataFlavor(RowKeySet.class, discriminator);
    RowKeySet rowKeySet = transferable.getData(rowKeySetFlavor);
    if (rowKeySet != null)
    {
      Integer currKey = (Integer)rowKeySet.iterator().next();
      Object removed = sourceValues.remove(currKey.intValue());
    }
    // Need to add the drag source table so it gets redrawn.
    AdfFacesContext.getCurrentInstance().addPartialTarget(dropEvent.getDragComponent());
  }  

  public static class DnDDemoData
  {
    private String product;
    private Integer cost;
    
    DnDDemoData(String product, Integer cost)
    {
      this.product = product;
      this.cost = cost;
    }
    
    public String getProduct()
    {
      return product;
    }
    
    public Integer getCost()
    {
      return cost;
    }
    
    public static DnDDemoData addALink(DnDDemoData source)
    {
      if (source == null)
        return null;
      
      String linkProduct = "Link to " + source.product;
      DnDDemoData clone = new DnDDemoData(linkProduct, source.cost);
      
      return clone;
    }
  }
}
