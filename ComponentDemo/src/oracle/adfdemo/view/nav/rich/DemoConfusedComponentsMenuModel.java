/* Copyright (c) 2010, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetTreeImpl;
import org.apache.myfaces.trinidad.model.TreeModel;
import org.apache.myfaces.trinidad.model.ViewIdPropertyMenuModel;

public class DemoConfusedComponentsMenuModel extends ViewIdPropertyMenuModel implements Serializable
{
  public DemoConfusedComponentsMenuModel()
  {
    super();
    setViewIdProperty("focusViewId");
    _confusedComponents = _initConfusedComponents();
    setWrappedData(_confusedComponents);
  }

  private TreeModel _initConfusedComponents()
    {
      // Confused Components
      List<DemoItemNode> confusedComponentsNodes = new ArrayList<DemoItemNode>()
      {
        {
          add(new DemoItemNode("Checkboxes", "/confusedComponents/checkboxes.jspx","/adfdt/selectManyCheckbox.png","confused.checkboxes"));
          add(new DemoItemNode("Choice", "/confusedComponents/choice.jspx","/adfdt/selectManyChoice.png","confused.choice"));
          add(new DemoItemNode("Command Buttons", "/confusedComponents/commandButton.jspx","/adfdt/commandButton.png","confused.commandButton"));
          add(new DemoItemNode("Radio Buttons", "/confusedComponents/radioButtons.jspx","/adfdt/selectBooleanRadio.png","confused.radioButtons"));
          add(new DemoItemNode("Secondary Windows", "/confusedComponents/secondaryWindows.jspx","/adfdt/dialog.png","confused.secondaryWindows"));
          add(new DemoItemNode("Tabs", "/confusedComponents/tabs.jspx","/adfdt/panelTabbed.png","confused.tabs"));
          add(new DemoItemNode("Iterators", "/confusedComponents/iterators.jspx","/adfdt/iterator.png","confused.iterators"));
        }
      };

      DemoItemNode confusedComponentsGroup = new DemoItemNode("Commonly Confused", "/images/folder.png", confusedComponentsNodes);

      List confusedComponentsList = new ArrayList();
      confusedComponentsList.add(confusedComponentsGroup);

      TreeModel confusedComponents = new ChildPropertyTreeModel(confusedComponentsList, _CHILDREN);
      return confusedComponents;
    }

  public void setFoldersTreeState(RowKeySet _foldersTreeState)
  {
    this._foldersTreeState = _foldersTreeState;
  }

  /**
   * @return
   */
  public RowKeySet getFoldersTreeState()
  {
    if (_foldersTreeState == null)
    {
      // Create the PathSet that we'll use to store the initial
      // expansion state for the tree
      RowKeySet treeState = new RowKeySetTreeImpl();

      // RowKeySet requires access to the TreeModel for currency.
      TreeModel model = this.getConfusedComponents();
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

  public void setConfusedComponents(TreeModel confusedComponents)
  {
    this._confusedComponents = confusedComponents;
  }

  public TreeModel getConfusedComponents()
  {
    return _confusedComponents;
  }

  public void setSelectionState(RowKeySet _selectionState)
  {
    this._selectionState = _selectionState;
  }

  public RowKeySet getSelectionState()
  {
    if (_selectionState == null)
    {
      RowKeySet selectionState = new RowKeySetTreeImpl();

      // RowKeySet requires access to the TreeModel for currency.
      TreeModel model = this.getConfusedComponents();
      selectionState.setCollectionModel(model);

      selectionState.setContained(true);

      _selectionState = selectionState;
    }

    return _selectionState;
  }

  private static final String _CHILDREN = "children";
  private RowKeySet _foldersTreeState;
  private RowKeySet _selectionState;
  private transient TreeModel _confusedComponents = null;

  @SuppressWarnings("compatibility:-4609678521312157785")
  private static final long serialVersionUID = 1L;
}
