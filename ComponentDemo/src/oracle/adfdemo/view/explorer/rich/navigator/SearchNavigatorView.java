/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.navigator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.adfdemo.view.explorer.rich.data.CriteriaFileItemFilter;
import oracle.adfdemo.view.explorer.rich.data.FileItem;

import oracle.adfdemo.view.explorer.rich.data.FileItemProperty;

import org.apache.myfaces.trinidad.component.UIXInput;
import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.SortableModel;

public class SearchNavigatorView extends BaseNavigatorView
{
  public SearchNavigatorView()
  {
  }
  
  public SearchNavigatorView(String type, String icon, String label, Map data)
  {
    super(type, icon, label, data);
  }
  
  /**
   * Implement abstarct method from parent
   * Refresh the navigators' models and components
   */
  public void refresh()
  {
    if (getShowDetailItem() != null)
    {
      // Add show detail item as parital targets
      RequestContext adfContext = RequestContext.getCurrentInstance();
      adfContext.addPartialTarget(getShowDetailItem());
    }
  }
  
  /**
   * Handle the search action event
   * @param actionEvent
   * */
  public void searchForFileItem(ActionEvent actionEvent)
  {
     doRealSearchForFileItem();
  }
  
  /**
   * Execute real search operation
   */
  public void doRealSearchForFileItem()
  {
    // Ask the data manager for file name map index
    Map<String, List<FileItem>> nameToFileItems = 
      _feBean.getDataFactory().getNameToFileItems();
    
    // Check for empty file name search
    if ((_searchCriteriaName == null) ||
        ("".equalsIgnoreCase(_searchCriteriaName) == true))
    {
      return;
    }
    
    _searchResultFileItemList = new ArrayList<FileItem>();

    // create criteria filter for FileItem
    CriteriaFileItemFilter criteriaFilter = 
      new CriteriaFileItemFilter(_searchCriteriaLastModified, 
                                 _searchCriteriaSize, 
                                 _searchCriteriaType,
                                 _searchCriteriaLastModifiedDate);

    Iterator it = nameToFileItems.entrySet().iterator();
    while (it.hasNext())
    {
      Map.Entry pair = (Map.Entry)it.next();
      String fileName = (String)pair.getKey();

      if (fileName.contains(_searchCriteriaName.toLowerCase()))
      {
        // Pick all match the other search criteria
        List<FileItem> fileItems = (List<FileItem>)pair.getValue();        
        if (fileItems == null)
        {
          continue;
        }
        
        // Iterates through all matching FileItem items
        for (FileItem fi: fileItems)
        {
          if (criteriaFilter.accept(fi) == true)
          {
            // Add it to result search list
            _searchResultFileItemList.add(fi);
          }
        }
      }
    }
    // Create search results table model
    _searchResultsTableModel = new SortableModel(_searchResultFileItemList);
  }
  
  /**
   * Handle selection on the search result table
   * @param selectionEvent
   */
  public void searchTableSelectFileItem(SelectionEvent selectionEvent)
  {
    // Get the selected FileItem
    FileItem data = 
      (FileItem)((UIXTable)selectionEvent.getSource()).getSelectedRowData();

    if (data != null)
    {       
      // Check if the selcted item is a folder
      if (data.isDirectory())
      {
        // Set selected folder
        _feBean.setSelectedDirectory(data.getDisplayPathName());
        
        // Save current selected FileItem state
        _feBean.setLastSelectedFileItem(data);
      
        // Ask Folders Navigator to auto sync disclosed and selected nodes
        _feBean.getNavigatorManager().getFoldersNavigator().autoSyncFoldersTree(data.getDisplayPathName());
      }
      else
      {
        // Set selected folder
        _feBean.setSelectedDirectory(data.getParentDisplayPathName());
        
        // Save current selected FileItem state
        _feBean.setLastSelectedFileItem(data.getParentFile());
        
        // Ask Folders Navigator to auto sync disclosed and selected nodes
        // parent of the selected item
        _feBean.getNavigatorManager().getFoldersNavigator().autoSyncFoldersTree(data.getParentDisplayPathName());
      }
      
      // Ask folders tree to be refreshed
      _feBean.getNavigatorManager().getFoldersNavigator().renderFoldersTree();
    }    
  }
  
  /**
   * Handle custom event from client when user press enter
   * @param clientEvent
   */
  public void searchOnEnter(ClientEvent clientEvent)
  {
    doRealSearchForFileItem();
    
    // refresh search navigator
    this.refresh();
  }
  
  /**
   * Return number of matches
   * @return return number of search results
   */
  public String getSearchMatchesCountMessage()
  {
    if ((_searchResultFileItemList == null) ||
        (_searchResultFileItemList.size() == 0))
    {
      return _feBean.getFileExplorerBundle().getString("navigator.nofilesfound");
    }
    else
    {
      StringBuilder builder = new StringBuilder();
      builder.append(_feBean.getFileExplorerBundle().getString("navigator.filesfound"));
      builder.append(_searchResultFileItemList.size());
      return builder.toString();
    }
  }
  
  // accessors
  
  public void setSearchCriteriaName(String searchCriteriaName)
  {
    this._searchCriteriaName = searchCriteriaName;
  }

  public String getSearchCriteriaName()
  {
    return _searchCriteriaName;
  }
  
  public void setSearchCriteriaType(String searchCriteriaType)
  {
    this._searchCriteriaType = searchCriteriaType;
  }

  public String getSearchCriteriaType()
  {
    return _searchCriteriaType;
  }

  public List getSearchFileItemTypes()
  {
    if (_searchFileItemTypes == null)
    {
      FileItemProperty.FileItemType[] types = 
        FileItemProperty.FileItemType.values();
      
      _searchFileItemTypes = new ArrayList<SelectItem>(types.length);
      
      for (FileItemProperty.FileItemType type : types)
      {
        String typeString = FileItemProperty.getTypeEnumToStringMap().get(type);
        _searchFileItemTypes.add(new SelectItem(type.toString(), typeString));
      }
    }
    return _searchFileItemTypes;
  }

  public CollectionModel getSearchResultsTableModel()
  {
    return _searchResultsTableModel;
  }
  
  public void setSearchCriteriaSize(String searchCriteriaSize)
  {
    this._searchCriteriaSize = searchCriteriaSize;
  }

  public String getSearchCriteriaSize()
  {
    return _searchCriteriaSize;
  }

  public void setSearchCriteriaLastModified(String searchCriteriaLastModified)
  {
    this._searchCriteriaLastModified = searchCriteriaLastModified;
  }

  public String getSearchCriteriaLastModified()
  {
    return _searchCriteriaLastModified;
  }
  
  public void setDisabledLastModifiedDate(boolean disabledLastModifiedDate)
  {
    this._disabledLastModifiedDate = disabledLastModifiedDate;
  }

  public boolean isDisabledLastModifiedDate()
  {
    if ("specify".equals(_searchCriteriaLastModified))
    {
      return _disabledLastModifiedDate = false;
    }
    else
    {
      _disabledLastModifiedDate = true;
    }
      
    return _disabledLastModifiedDate;
  }

  public void setSearchCriteriaLastModifiedDate(Date searchCriteriaLastModifiedDate)
  {
    this._searchCriteriaLastModifiedDate = searchCriteriaLastModifiedDate;
  }

  public Date getSearchCriteriaLastModifiedDate()
  {
    return _searchCriteriaLastModifiedDate;
  }
  
  // Helper Methods and members
  
  // For Search Navigator
  private String _searchCriteriaName;
  private String _searchCriteriaType;
  private List _searchFileItemTypes;
  private String _searchCriteriaSize = "any";
  private String _searchCriteriaLastModified = "any";
  private CollectionModel _searchResultsTableModel;
  private List<FileItem> _searchResultFileItemList;
  private boolean _disabledLastModifiedDate=true;
  private Date _searchCriteriaLastModifiedDate = new Date();
}
