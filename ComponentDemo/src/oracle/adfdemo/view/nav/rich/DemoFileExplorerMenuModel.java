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

public class DemoFileExplorerMenuModel extends ViewIdPropertyMenuModel implements Serializable
{
  public DemoFileExplorerMenuModel()
  {
    super();
    setViewIdProperty("focusViewId");
    _fileExplorer = _initFileExplorer();
    setWrappedData(_fileExplorer);
  }

  private TreeModel _initFileExplorer()
  {
    // File Explorer
    DemoItemNode fileExplorerNode =
      new DemoItemNode("File Explorer Demo", "/fileExplorer/index.jspx",
                       "/images/folder.png", "fileExplorer.index");

    List fileExplorerList = new ArrayList();
    fileExplorerList.add(fileExplorerNode);

    TreeModel fileExplorer =
      new ChildPropertyTreeModel(fileExplorerList, _CHILDREN);
    return fileExplorer;
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
      TreeModel model = this.getFileExplorer();
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

  public void setFileExplorer(TreeModel fileExplorer)
  {
    this._fileExplorer = fileExplorer;
  }

  public TreeModel getFileExplorer()
  {
    return _fileExplorer;
  }

  private static final String _CHILDREN = "children";
  private RowKeySet _foldersTreeState;
  private transient TreeModel _fileExplorer = null;

  @SuppressWarnings("compatibility:1407192970830036828")
  private static final long serialVersionUID = 1L;
}
