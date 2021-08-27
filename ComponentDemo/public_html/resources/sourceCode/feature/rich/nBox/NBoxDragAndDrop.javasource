/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/nBox/NBoxDragAndDrop.java /bibeans_root/2 2014/04/28 23:24:23 jramanat Exp $ */

/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jramanat    04/04/14 - Creation
 */

package oracle.adfdemo.view.feature.rich.nBox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;

import oracle.adf.view.faces.bi.component.nBox.UINBox;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adfdemo.view.feature.rich.nBox.data.TriangleData;

import org.apache.myfaces.trinidad.component.UIXCollection;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/nBox/NBoxDragAndDrop.java /bibeans_root/2 2014/04/28 23:24:23 jramanat Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class NBoxDragAndDrop {
  private CollectionModel model;
  private CollectionModel tableModel;
  private UINBox nBox;
  private RichTable table;
  
  public CollectionModel getModel() {
    if (model == null) {
      model = ModelUtils.toCollectionModel(TriangleData.getData(75, 3, 3));
    }
    return model;
  }

  public CollectionModel getTableModel() {
    if (tableModel == null) {
      tableModel = ModelUtils.toCollectionModel(new ArrayList());
    }
    return tableModel;
  }


  public void setNBox(UINBox nBox) {
    this.nBox = nBox;
  }

  public UINBox getNBox() {
    return nBox;
  }

  public void setTable(RichTable table) {
    this.table = table;
  }

  public RichTable getTable() {
    return table;
  }
  
  public DnDAction nBoxDropListener(DropEvent dropEvent) {
    Transferable transferable = dropEvent.getTransferable();
    DataFlavor<RowKeySet> dataFlavor = DataFlavor.getDataFlavor(RowKeySet.class);
    RowKeySet rowKeySet = transferable.getData(dataFlavor);
    if (rowKeySet == null || rowKeySet.getSize() <= 0)
      return DnDAction.NONE;

    String row = null;
    String column = null;
    Object dropSite = dropEvent.getDropSite();
    if (dropSite instanceof Map) {
      row = (String) ((Map) dropSite).get("row");
      column = (String) ((Map) dropSite).get("column");
    }
    
    if (row == null || column == null)
      return DnDAction.NONE;

    UIXCollection dragComponent = (UIXCollection)dropEvent.getDragComponent();
    Object[] rowKeys = rowKeySet.toArray();
    Set<Integer> indices = new HashSet<Integer>();
    for (int i = 0; i < rowKeys.length; i++) {
      Map<String, Object> node = (Map<String, Object>)dragComponent.getRowData(rowKeys[i]);
      node.put("row", row);
      node.put("column", column);
      indices.add((Integer)node.get("index"));
    }
    RequestContext.getCurrentInstance().addPartialTarget(nBox);
    if (table.equals(dragComponent)) {
      List tableData = (List)tableModel.getWrappedData();
      for (int i = tableData.size() - 1; i >= 0; i--) {
        if (indices.contains(((Map<String, Object>)tableData.get(i)).get("index"))) {
          tableData.remove(i);
        }
      }
      table.setSelectedRowKeys(new RowKeySetImpl());
      RequestContext.getCurrentInstance().addPartialTarget(table);
    }
    
    return dropEvent.getProposedAction();    
  }

  public DnDAction tableDropListener(DropEvent dropEvent) {
    if (table.equals(dropEvent.getDragComponent())) {
      return DnDAction.NONE;
    }
    Transferable transferable = dropEvent.getTransferable();
    DataFlavor<RowKeySet> dataFlavor = DataFlavor.getDataFlavor(RowKeySet.class);
    RowKeySet rowKeySet = transferable.getData(dataFlavor);
    if (rowKeySet == null || rowKeySet.getSize() <= 0)
      return DnDAction.NONE;

    Object[] rowKeys = rowKeySet.toArray();
    for (int i = 0; i < rowKeys.length; i++) {
      Map<String, Object> node = (Map<String, Object>)nBox.getRowData(rowKeys[i]);
      node.put("row", null);
      node.put("column", null);
      ((List)tableModel.getWrappedData()).add(node);
    }
    nBox.setSelectedRowKeys(null);
    RequestContext.getCurrentInstance().addPartialTarget(nBox);
    RequestContext.getCurrentInstance().addPartialTarget(table);
    
    return dropEvent.getProposedAction();    
  }
}
