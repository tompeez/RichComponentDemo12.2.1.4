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

import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.SortableModel;

public class TableContentView extends BaseContentView
{
  public TableContentView()
  {
  }

  public TableContentView(String name, String icon, String label)
  {
    super(name, icon, label);
  }
  
  public void resetTableSelection()
  {
    if (_contentTable != null &&
       _contentTable.getSelectedRowKeys() != null)
    {     
      _contentTable.getSelectedRowKeys().clear();
      _contentTable.getDisclosedRowKeys().clear();
    }
  }
  
  public void tableSelectFileItem(SelectionEvent selectionEvent)
  {
    FileItem data = (FileItem)this.getContentTable().getSelectedRowData();
    setSelectedFileItem(data);
  }
  
  // Components accessors
  
  public void setContentTable(UIXTable contentTable)
  {
    _contentTable = contentTable;
  }

  public UIXTable getContentTable()
  {
    return _contentTable;
  }
  
  // Helper methods and member variables
  
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
    
    CollectionModel tableModel = new SortableModel(fileList);
    
    return tableModel;
  }
  
  private UIXTable _contentTable = null;
}
