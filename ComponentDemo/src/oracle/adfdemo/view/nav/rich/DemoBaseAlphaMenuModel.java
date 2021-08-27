/** Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetTreeImpl;
import org.apache.myfaces.trinidad.model.TreeModel;
import org.apache.myfaces.trinidad.model.ViewIdPropertyMenuModel;

public abstract class DemoBaseAlphaMenuModel extends ViewIdPropertyMenuModel implements Serializable
{
  public DemoBaseAlphaMenuModel()
  {
    super();
    setViewIdProperty("focusViewId");
    _treeModel = _initTreeModel();
    setWrappedData(_treeModel);
  }

  public abstract String getRootNodeText();

  public abstract ViewIdPropertyMenuModel getGroupedMenuModel();

  private TreeModel _initTreeModel()
  {
    // Reuse the data from the grouped MenuModel so people don't have to define the links more than once:
    List<DemoItemNode> alphabeticalNodes = new ArrayList<DemoItemNode>();
    ViewIdPropertyMenuModel groupMenuModel = getGroupedMenuModel();
    int groupCount = groupMenuModel.getRowCount();
    for (int i=0; i<groupCount; i++)
    {
      DemoItemNode rowData = (DemoItemNode)groupMenuModel.getRowData(i);
      _addLeafNodes(alphabeticalNodes, rowData.getChildren());
    }

    Collections.sort(alphabeticalNodes);
    DemoItemNode componentsGroup =
      new DemoItemNode(
        getRootNodeText(),
        "/images/folder.png",
        alphabeticalNodes);

    List componentsList = new ArrayList();
    componentsList.add(componentsGroup);

    TreeModel alphabeticalComponents = new ChildPropertyTreeModel(componentsList, _CHILDREN);
    return alphabeticalComponents;
  }

  private void _addLeafNodes(List<DemoItemNode> alphabeticalNodes, List<DemoItemNode> groupChildren)
  {
    if (groupChildren != null || groupChildren.size() == 0)
    {
      for (DemoItemNode child : groupChildren)
      {
        List<DemoItemNode> grandChildren = child.getChildren();
        if (grandChildren == null || grandChildren.size() == 0)
        {
          DemoItemNode itemNode =
            new DemoItemNode(
              child.getLabel(),
              child.getFocusViewId(),
              child.getIco(),
              child.getOutcome(),
              child.getShortDesc(),
              child.getCategory(),
              child.getComponentName(),
              child.getBackingBean(),
              child.getIconLarge(),
              child.getIconCarousel(),
              child.isDeprecated());
          alphabeticalNodes.add(itemNode);
        }
        else
        {
          _addLeafNodes(alphabeticalNodes, grandChildren);
        }
      }
    }
  }

  public void setFoldersTreeState(RowKeySet foldersTreeState)
  {
    _foldersTreeState = foldersTreeState;
  }

  public RowKeySet getFoldersTreeState()
  {
    if (_foldersTreeState == null)
    {
      // Create the PathSet that we'll use to store the initial
      // expansion state for the tree
      RowKeySet treeState = new RowKeySetTreeImpl();

      // RowKeySet requires access to the TreeModel for currency.
      TreeModel model = getTreeModel();
      treeState.setCollectionModel(model);

      // Make the model point at the root node
      Object oldRowKey = model.getRowKey();
      int oldIndex = model.getRowIndex();

      model.setRowKey(null);
      model.setRowIndex(0);

      treeState.setContained(true);

      // Restore keys
      model.setRowKey(oldRowKey);
      model.setRowIndex(oldIndex);

      _foldersTreeState = treeState;
    }

    return _foldersTreeState;
  }

  public void setTreeModel(TreeModel treeModel)
  {
    _treeModel = treeModel;
  }

  public TreeModel getTreeModel()
  {
    if (_treeModel == null)
    {
      _treeModel = _initTreeModel();
    }
    return _treeModel;
  }

  public void setSelectionState(RowKeySet selectionState)
  {
    _selectionState = selectionState;
  }

  public RowKeySet getSelectionState()
  {
    if (_selectionState == null)
    {
      RowKeySet selectionState = new RowKeySetTreeImpl();

      // RowKeySet requires access to the TreeModel for currency.
      TreeModel model = getTreeModel();
      selectionState.setCollectionModel(model);

      selectionState.setContained(true);

      _selectionState = selectionState;
    }

    return _selectionState;
  }

  private static final String _CHILDREN = "children";
  private RowKeySet _foldersTreeState;
  private RowKeySet _selectionState;
  private transient TreeModel _treeModel = null;

  @SuppressWarnings("compatibility:-7412236995369681298")
  private static final long serialVersionUID = 1L;
}
