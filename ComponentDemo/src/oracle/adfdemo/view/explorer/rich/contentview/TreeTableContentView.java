/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.contentview;

import java.util.List;

import oracle.adfdemo.view.explorer.rich.data.FileItem;

import org.apache.myfaces.trinidad.component.UIXTreeTable;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.TreeModel;

public class TreeTableContentView extends BaseContentView
{
  public TreeTableContentView()
  {
  }
  
  public TreeTableContentView(String name, String icon, String label)
  {
    super(name, icon, label);
  }

  public void resetTableSelection()
  {
    if (_contentTreeTable != null && 
        _contentTreeTable.getSelectedRowKeys() != null)
    {
      _contentTreeTable.getSelectedRowKeys().clear();
      _contentTreeTable.getDisclosedRowKeys().clear();
    }
  }
  
  public void treeTableSelectFileItem(SelectionEvent selectionEvent)
  {
    UIXTreeTable source = (UIXTreeTable)selectionEvent.getSource();
    FileItem data = (FileItem)source.getRowData();
    setSelectedFileItem(data);
  }
  
  // Components accessors
  
  public void setContentTreeTable(UIXTreeTable contentTreeTable)
  {
    _contentTreeTable = contentTreeTable;
  }

  public UIXTreeTable getContentTreeTable()
  {
    return _contentTreeTable;
  }
  
  // Helper methods and members
  
  protected CollectionModel createContentModel(String selectedDirectory, 
                                               boolean fromFake)
  {
    List fileList = null;
    if (fromFake)
    {
      fileList = getFileList();
    }
    else
    {
      fileList = getChildFileList(selectedDirectory);
    }
    
    // create the TreeModel using ChildPropertyTreeModel
    TreeModel treeModel = new ChildPropertyTreeModel(fileList, 
                            FileItem.getChildPropertyName());     
    
    return treeModel;
  }
  
  private UIXTreeTable  _contentTreeTable = null;
}
