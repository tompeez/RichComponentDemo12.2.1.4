/* Copyright (c) 2009, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.table.rich;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.adfdemo.view.table.rich.model.UpdateableModel;
import oracle.adfdemo.view.table.rich.model.UpdateableTreeModel;

import org.apache.myfaces.trinidad.component.UIXComponent;
import org.apache.myfaces.trinidad.component.UIXTree;
import org.apache.myfaces.trinidad.component.visit.VisitTreeUtils;
import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;


public class UpdateableTreeBean
  extends UpdateableTableBean
{
  public UpdateableTreeBean()
  {
  }

  @Override
  public CollectionModel getModel()
  {
    if (_model == null)
    {
      _model = _createTreeModel();
    }

    return _model;
  }

  public void moveRowUp(ActionEvent actionEvent)
  {
    Object rowKey = null;
    RowKeySet selection = getSelectedRowKeys();
    if (selection.getSize() > 0)
    {
      rowKey = selection.iterator().next();
    }

    if (rowKey == null)
      return;

    TreeModel model = (TreeModel) getModel();

    try
    {

      Object parentKey = model.getContainerRowKey(rowKey);
      if (parentKey == null)
        return;

      model.setRowKey(rowKey);
      TreeTableTestData.TreeNode row = (TreeTableTestData.TreeNode) model.getRowData(rowKey);
      ((UpdateableModel) model).deleteRow();

      model.setRowKey(parentKey);
      UpdateableModel childModel = ((UpdateableTreeModel) model).getChildModel();
      if (childModel != null)
      {
        childModel.insertRow(null, row);
      }
    }
    finally
    {
      model.setRowKey(rowKey);
    }
  }

  public void moveRowDown(ActionEvent actionEvent)
  {

    // find selected rowkey
    RowKeySet selection = getSelectedRowKeys();
    Object rowKey = selection.iterator().next();

    if (rowKey == null)
      return;

    TreeModel model = (TreeModel) getModel();

    try
    {
      // set selected row key
      model.setRowKey(rowKey);

      int rowIndex = model.getRowIndex();

      // grab reference to relocate to sibling
      TreeTableTestData.TreeNode row = (TreeTableTestData.TreeNode) model.getRowData(rowKey);
      ((UpdateableModel) model).deleteRow();

      // set to new sibling
      model.setRowIndex(rowIndex);

      TreeTableTestData.TreeNode nextSibling = (TreeTableTestData.TreeNode) model.getRowData();

      // if sibling has no children, create a children list
      if (!model.isContainer())
      {
        if (nextSibling.getChildren() == null)
          nextSibling.setChildren(new ArrayList<TreeTableTestData.TreeNode>());

        ((UpdateableModel) model).updateRow(nextSibling);
      }

      // enter into the sibling
      model.enterContainer();
      UpdateableModel childModel = ((UpdateableTreeModel) model).getChildModel();
      childModel.insertRow(null, row);

    }
    finally
    {
      model.setRowKey(rowKey);
    }
  }

  public void selectionListener(SelectionEvent event)
  {
    FacesContext context = FacesContext.getCurrentInstance();

    Collection<String> clientIds = new ArrayList<String>();
    clientIds.add("demoTemplate:pc1:cmimu"); //move up menu item
    clientIds.add("demoTemplate:pc1:cmimd"); //move down menu item
    clientIds.add("demoTemplate:pc1:cmiii"); //increase indent menu item
    clientIds.add("demoTemplate:pc1:cmidi"); //decrease indent menu item
    clientIds.add("demoTemplate:pc1:ctbii"); //increase indent toobar item
    clientIds.add("demoTemplate:pc1:ctbdi"); //decrease indent toobar item

    VisitCallback visitCallback = new VisitCallback()
    {

      @Override
      public VisitResult visit(VisitContext visitContext, UIComponent uIComponent)
      {
        RequestContext.getCurrentInstance().addPartialTarget(uIComponent);
        return VisitResult.ACCEPT;
      }
    };

    // target specific menu and toolbar actions to be PPR'd on table selection
    VisitContext visitContext = VisitTreeUtils.createVisitContext(context, clientIds, null);
    UIXComponent.visitTree(visitContext, component.getParent(), visitCallback);
    
    TreeModel model = (TreeModel) getModel();
    Object oldKey = model.getRowKey();

    try
    {
      _canMoveRowDown = false;
      _canMoveRowUp = false;

      RowKeySet selection = event.getAddedSet();
      if (selection == null)
        return;

      Object rowKey = selection.iterator().next();

      if (rowKey == null)
        return;

      model.setRowKey(rowKey);

      int rowIndex = model.getRowIndex();
      _canMoveRowDown = model.isRowAvailable(rowIndex + 1);

      Object parentKey = model.getContainerRowKey(rowKey);
      _canMoveRowUp = (parentKey != null);

    }
    finally
    {
      model.setRowKey(oldKey);

    }
  }

  private boolean _canMoveRowDown = false;

  public boolean getCanMoveRowDown()
  {
    return _canMoveRowDown;
  }

  private boolean _canMoveRowUp = false;

  public boolean getCanMoveRowUp()
  {
    return _canMoveRowUp;
  }

  public void popupFetchListener(PopupFetchEvent event)
  {
    CollectionModel model = getModel();
    Object oldKey = model.getRowKey();
    try
    {
      Object rowKey = null;
      RowKeySet selection = getSelectedRowKeys();
      if (selection.getSize() > 0)
        rowKey = selection.iterator().next();
      TreeTableTestData.TreeNode row = (TreeTableTestData.TreeNode) model.getRowData(rowKey);

      AdfFacesContext adfContext = AdfFacesContext.getCurrentInstance();
      adfContext.getPageFlowScope().put("row", row);
      adfContext.getPageFlowScope().put("selectedRowkey", rowKey);
    }
    finally
    {
      model.setRowKey(oldKey);
    }

  }

  public void dialogListener(DialogEvent event)
  {
    CollectionModel model = getModel();
    Object oldKey = model.getRowKey();
    try
    {
      AdfFacesContext adfContext = AdfFacesContext.getCurrentInstance();
      TreeTableTestData.TreeNode row = (TreeTableTestData.TreeNode) adfContext.getPageFlowScope().get("row");
      Object rowKey = adfContext.getPageFlowScope().get("selectedRowkey");

      model.setRowKey(rowKey);
      ((UpdateableModel) model).updateRow(row);

      adfContext.getPageFlowScope().remove("row");
      adfContext.getPageFlowScope().remove("selectedRowkey");
    }
    finally
    {
      model.setRowKey(oldKey);
    }
  }

  public void popupCanceledListener(PopupCanceledEvent event)
  {
    AdfFacesContext adfContext = AdfFacesContext.getCurrentInstance();
    adfContext.getPageFlowScope().remove("row");
    adfContext.getPageFlowScope().remove("selectedRowkey");
  }


  public void appendRow(ActionEvent actionEvent)
  {
    CollectionModel model = getModel();
    Object oldKey = model.getRowKey();
    try
    {
      Object rowKey = null;
      RowKeySet selection = getSelectedRowKeys();
      if (selection.getSize() > 0)
        rowKey = selection.iterator().next();
      model.setRowKey(rowKey);
      UpdateableModel childModel = ((UpdateableTreeModel) model).getChildModel();
      if (childModel != null)
      {
        TreeTableTestData.TreeNode newRow = (TreeTableTestData.TreeNode) createRow();
        childModel.insertRow(null, newRow);
      }
    }
    finally
    {
      model.setRowKey(oldKey);
    }
  }


  @Override
  public void insertStart(ActionEvent actionEvent)
  {
    CollectionModel model = getModel();
    Object oldKey = model.getRowKey();
    try
    {
      Object rowKey = null;
      RowKeySet selection = getSelectedRowKeys();
      if (selection.getSize() > 0)
        rowKey = selection.iterator().next();
      model.setRowKey(rowKey);
      model.setRowIndex(0);
      insertAt(model.getRowKey());
    }
    finally
    {
      model.setRowKey(oldKey);
    }
  }

  public void insertExpandedRow(ActionEvent event)
  {
    CollectionModel model = getModel();
    Object oldKey = model.getRowKey();
    try
    {
      RowKeySet selection = getSelectedRowKeys();
      if (selection.getSize() > 0)
      {
        Object rowKey = selection.iterator().next();
        TreeTableTestData.TreeNode newRow = (TreeTableTestData.TreeNode) createRow();
        List<TreeTableTestData.TreeNode> childNodes = new ArrayList<TreeTableTestData.TreeNode>(3);
        childNodes.add((TreeTableTestData.TreeNode) createRow());
        childNodes.add((TreeTableTestData.TreeNode) createRow());
        childNodes.add((TreeTableTestData.TreeNode) createRow());
        newRow.setChildren(childNodes);
        Object newRowKey = newRow.getKey();

        List<String> newRowPath = new ArrayList<String>((List<String>) rowKey);
        newRowPath.set(newRowPath.size() - 1, (String) newRowKey);
        RowKeySet expandedSet = ((UIXTree) component).getDisclosedRowKeys();
        expandedSet.add(newRowPath);
        model.setRowKey(rowKey);
        ((UpdateableModel) model).insertRow(newRow);
      }
    }
    finally
    {
      model.setRowKey(oldKey);
    }
  }

  @Override
  protected RowKeySet getSelectedRowKeys()
  {
    return ((UIXTree) component).getSelectedRowKeys();
  }

  @Override
  protected Object createRow()
  {
    TreeTableTestData.TreeNode row =
      new TreeTableTestData.TreeNode(null, Long.toString(Math.round(Math.random() * 100)));
    return row;
  }

  public void duplicateRow(ActionEvent event)
  {
    CollectionModel model = getModel();
    Object oldKey = model.getRowKey();
    try
    {
      Object rowKey = null;
      RowKeySet selection = getSelectedRowKeys();
      if (selection.getSize() > 0)
        rowKey = selection.iterator().next();
      model.setRowKey(rowKey);

      UpdateableModel childModel = ((UpdateableTreeModel) model).getChildModel();
      TreeTableTestData.TreeNode row = (TreeTableTestData.TreeNode) model.getRowData(rowKey);

      TreeTableTestData.TreeNode newRow = new TreeTableTestData.TreeNode(null, "Copy of " + row.getValue());
      childModel.insertRow(null, newRow);

    }
    finally
    {
      model.setRowKey(oldKey);
    }
  }

  @Override
  protected Object updateRow(Object rowKey)
  {
    CollectionModel model = getModel();
    model.setRowKey(rowKey);
    TreeTableTestData.TreeNode row = (TreeTableTestData.TreeNode) model.getRowData();
    row.setName("name" + Long.toString(Math.round(Math.random() * 100)));
    row.setSize((int) Math.round(Math.random() * 10000));
    row.setValue(Long.toString(Math.round(Math.random() * 100)));
    return row;
  }

  @Override
  protected Object getRowKeyForRow(Object row)
  {
    Object rowKey = null;
    if (row instanceof TreeTableTestData.TreeNode)
      rowKey = ((TreeTableTestData.TreeNode) row).getKey();

    return rowKey;
  }

  private UpdateableTreeModel _createTreeModel()
  {
    List<TreeTableTestData.TreeNode> nodes = TreeTableTestData.createTestData(_DEFAULT_FIRST_LEVEL_SIZE, false);
    List<TreeTableTestData.TreeNode> childNodes =
      TreeTableTestData.createTestData(_DEFAULT_FIRST_LEVEL_SIZE, false);
    TreeTableTestData.TreeNode node = nodes.get(0);
    node.setChildren(childNodes);
    return new UpdateableTreeModel<List>(nodes, _CONTAINER_KEY, _ROW_KEY);
  }

  private UpdateableTreeModel _model;
  private static int _DEFAULT_FIRST_LEVEL_SIZE = 10;
  private static final String _CONTAINER_KEY = "children";
  private static final String _ROW_KEY = "key";


}
