/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.contentview;

import java.io.Serializable;

import java.util.Iterator;
import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;

import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adfdemo.view.explorer.rich.bean.FileExplorerBean;

import oracle.adfdemo.view.explorer.rich.data.FileExplorerDataFactory;
import oracle.adfdemo.view.explorer.rich.data.FileItem;

import org.apache.myfaces.trinidad.component.UIXShowDetail;
import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.SortableModel;
import org.apache.myfaces.trinidad.model.TreeModel;

public abstract class BaseContentView implements Serializable
{
  public BaseContentView()
  {
  }
  
  public BaseContentView(String name, String icon, String label)
  {
    initContentView(name, icon, label);
  }
  
  public void initContentView(String name, String icon, String label)
  {
    _name = name;
    _icon = icon;    
    _label = label;
  }
  
  /**
   * Refresh BaseNavigatorView
   */
  public void refresh()
  {
    _contentModel = null;
  }
  
  /**
   * Reset state of content tables
   * */
  public abstract void resetTableSelection();
  
  public void setFileExplorerBean(FileExplorerBean feBean)
  {
    this._feBean = feBean;
  }

  public FileExplorerBean getFileExplorerBean()
  {
    return _feBean;
  }
  
  public void setName(String name)
  {
    this._name = name;
  }

  public String getName()
  {
    return _name;
  }

  public void setIcon(String icon)
  {
    this._icon = icon;
  }

  public String getIcon()
  {
    return _icon;
  }
  
  public void setLabel(String label)
  {
    this._label = label;
  }
  
  public String getLabel()
  {
    if (_label == null)
    {
      _label = _feBean.getFileExplorerBundle().getString(getName());
    }
    
    return _label;
  }
  
  public void discloseContentView(DisclosureEvent disclosureEvent)
  {
    UIXShowDetail source = (UIXShowDetail)disclosureEvent.getSource();
    
    for (BaseContentView cv : _feBean.getContentViewManager().getContentViews())
    {
      if (cv.getName().equals(getName()))
      {
        cv.getCommandMenuItem().setSelected(source.isDisclosed());
      }
      else
      {
        cv.getCommandMenuItem().setSelected(!source.isDisclosed());
      }
    }
    
    _feBean.getHeaderManager().refresh();
  } 
  
  public void showContentView(ActionEvent actionEvent)
  {
    RichCommandMenuItem source = (RichCommandMenuItem)actionEvent.getSource();
    
    for (BaseContentView cv : _feBean.getContentViewManager().getContentViews())
    {
      if (cv.getName().equals(getName()))
      {
        cv.getShowDetailItem().setDisclosed(source.isSelected());
      }
      else
      {
        cv.getShowDetailItem().setDisclosed(!source.isSelected());
      }
    }
    
    _feBean.getContentViewManager().renderFolderTablesResponse();
  }
  
  /**
   * Handle drag start for table folder 
   * Since we refresh the content views tables we dont need to clean up the
   * row keys for the source components
   */
  public void onTableDragDropEnd(DropEvent dropEvent)
  {
    // log the drop info
    _feBean.logDropInfo(dropEvent);    
    
    _LOG.info("\nDrag Drop End on the table"+dropEvent);
  }

  /**
   * Handle drop start for table folder 
   */
  public DnDAction onTableDrop(DropEvent dropEvent)
  {
    // log the drop info
    _feBean.logDropInfo(dropEvent);

    try
    {
      // Get the dropped item
      DataFlavor<RowKeySet> rowKeySetFlavor = 
        DataFlavor.getDataFlavor(RowKeySet.class, "fileModel");
      RowKeySet tableDropRowKeySet = 
        dropEvent.getTransferable().getData(rowKeySetFlavor);
      
      // Get source component. We could safely cast it to UIXTable since for this
      // example we know only tables act as drag source with model of "fileModel"
      UIXTable tableSource = (UIXTable)dropEvent.getDragComponent();
      Object oldRK = tableSource.getRowKey();
      tableSource.setRowKey(getFirstSelectedPath(tableDropRowKeySet));
      FileItem sourceFileItem = (FileItem)tableSource.getRowData();
      tableSource.setRowKey(oldRK);
      
      if (sourceFileItem != null)
      {        
        // Get target node
        Object targetRowKey = dropEvent.getDropSite();
        if(targetRowKey == null)
        {
          // We do not support dropping onto the non data portion of the table
          return DnDAction.NONE;
        }
        UIXTable tableTarget = (UIXTable)dropEvent.getDropComponent();
        Object oldRK2 = tableTarget.getRowKey();
        tableTarget.setRowKey(targetRowKey);
        FileItem targetData = (FileItem)tableSource.getRowData();
        tableTarget.setRowKey(oldRK2);
      
        _feBean.getHeaderManager().moveFileItemTo(sourceFileItem, 
                                                targetData.getPathName());
      }
      else
      {
        return DnDAction.NONE;      
      }  
      
      // Refresh navigator via its manager
      _feBean.refreshAllManagers();
    }
    catch (Throwable t)
    {
      _LOG.severe(t);
      return DnDAction.NONE; 
    }
    
    return DnDAction.MOVE;
  }
  
  /**
   * Return the model for the content view
   * @return
   */
  public CollectionModel getContentModel()
  {
    if (_contentModel == null ||
      (_showModelForSelectedDirectory.equals(_feBean.getSelectedDirectory()) 
        == false))
    {
      resetTableSelection();
      
      if (_feBean.getSelectedDirectory() == null)
      {
        _contentModel = createContentModel(null, false);
      }
      else
      {
        FileItem selectedFileItem = 
          (FileItem) _feBean.getNavigatorManager().getFoldersNavigator().getSelectedFileItems();
        
        if (selectedFileItem == null)
        {
          selectedFileItem= _feBean.getLastSelectedFileItem();
        }
        
        if (selectedFileItem == null)
        {
          _contentModel = createContentModel(null, false);
        }
        else if (selectedFileItem.isFake())
        {
          _contentModel = createContentModel(null, true);
        }
        else
        {
          _contentModel = 
              createContentModel(selectedFileItem.getPathName(), false);
        }
      }
      
      if (_feBean.getSelectedDirectory() != null)
      {
        _showModelForSelectedDirectory = _feBean.getSelectedDirectory();
      }
    }
    return _contentModel;
  }
  
  // Base properties accessors
  
  public void setSelected(boolean selected)
  {
    this._selected = selected;
  }

  public boolean isSelected()
  {
    return _selected;
  }

  public void setDisclosed(boolean disclosed)
  {
    this._disclosed = disclosed;
  }

  public boolean isDisclosed()
  {
    return _disclosed;
  }

  // Components accessors

  public void setShowDetailItem(UIXShowDetail showDetailItem)
  {
    this._showDetailItem = showDetailItem;
  }

  public UIXShowDetail getShowDetailItem()
  {
    return _showDetailItem;
  }

  public void setCommandMenuItem(RichCommandMenuItem commandMenuItem)
  {
    this._commandMenuItem = commandMenuItem;
  }

  public RichCommandMenuItem getCommandMenuItem()
  {
    return _commandMenuItem;
  }
  
  // Helper methods and member
  
  protected abstract CollectionModel createContentModel(String selectedDirectory,
                                                   boolean fromFake);
  
  // Return List of children FileItem of selected Directory 
  protected List getChildFileList(String selectedDirectory)
  {
    FileExplorerDataFactory factory = _feBean.getDataFactory();
    
    List<FileItem> data = factory.getChildFileList(selectedDirectory);
    
    return data;
  }
  
  // Return List of all FileItem
  protected List getFileList()
  {
    FileExplorerDataFactory factory = _feBean.getDataFactory();
    
    List<FileItem> data = factory.getFileItemList();
    
    return data;
  }
  
  protected void setSelectedFileItem(FileItem data)
  {
    if (data != null)
    {
      _feBean.setLastSelectedFileItem(data);
    }
  }
  
  protected Object getFirstSelectedPath(RowKeySet selectedPaths)
  {
    if (selectedPaths == null)
    {
      return null;
    }
    
    Iterator iter = selectedPaths.iterator();
    if (!iter.hasNext())
    {
      return null;
    }
    
    return iter.next();
  }
  
  private FileExplorerBean _feBean;
  
  private String _showModelForSelectedDirectory = "";
  private CollectionModel _contentModel = null;  
  
  private boolean _selected = false;
  private boolean _disclosed = false;
  private UIXShowDetail _showDetailItem = null;
  private RichCommandMenuItem _commandMenuItem = null;
  
  private String _name;
  private String _icon;
  private String _label;
  
  private static final ADFLogger _LOG =
    ADFLogger.createADFLogger(BaseContentView.class);
}
