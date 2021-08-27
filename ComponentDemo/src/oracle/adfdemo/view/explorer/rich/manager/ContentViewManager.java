/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    This class is used as backingbean for the File Explorer demo that provides model for 
    navigating through a directory folder hierarchy, and a List to represent the files in any 
    given directory.
    This class will delegate works to other classes if necessary

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.manager;

import java.util.ArrayList;
import java.util.List;

import oracle.adfdemo.view.explorer.rich.contentview.BaseContentView;
import oracle.adfdemo.view.explorer.rich.bean.FileExplorerBean;

import oracle.adfdemo.view.explorer.rich.contentview.TableContentView;
import oracle.adfdemo.view.explorer.rich.contentview.TreeTableContentView;

import org.apache.myfaces.trinidad.component.UIXComponent;
import org.apache.myfaces.trinidad.context.RequestContext;

public class ContentViewManager
{
  public ContentViewManager(FileExplorerBean feBean)
  {
    _feBean = feBean;
  }
  
  // Public methods 

  /**
   * Refresh the content views' models and components
   */
  public void refresh()
  {
    for (BaseContentView cv : getContentViews())
    {
      cv.refresh();  
    }
    
    // refresh content tables
    renderFolderTablesResponse();
  }
  
  /**
   * Reset state of content tables
   */
  public void resetTableSelection()
  {
    for (BaseContentView cv : getContentViews())
    {
      cv.resetTableSelection();  
    }
  }

  /**
   * Re-render the folder table
   */
  public void renderFolderTablesResponse()
  {
    // Add panelTabbed component as partial target 
    RequestContext adfContext = RequestContext.getCurrentInstance();
    adfContext.addPartialTarget(_contentViewPanelTabbed);
  }
  
  /**
   * Return all the list of available content views
   * @return
   */
  public List<BaseContentView> getContentViews()
  {
    if (_baseContentViews == null)
    {
      // set content view ui
      _baseContentViews = _createContentViews();
    }
    
    return _baseContentViews;
  }
  
  /**
   * Set list of content views
   * @param baseContentViews
   */
  public void setContentViews(List<BaseContentView> baseContentViews)
  {
    this._baseContentViews = baseContentViews;
  }
  
  /**
   * Return the Table content view 
   * @return TableContentView
   */
  public TableContentView getTableContentView()
  {
    return (TableContentView)getContentViews().get(0);
  }
  
  /**
   * Return the Tree Table content view 
   * @return TreeTableContentView
   */
  public TreeTableContentView getTreeTableContentView()
  {
    return (TreeTableContentView)getContentViews().get(1);
  }
  
  /**
   * Return the List Table content view 
   * @return TableContentView
   */
  public TableContentView getListTableContentView()
  {
    return (TableContentView)getContentViews().get(2);
  }
  
  // Components accessor methods
  
  public UIXComponent getContentViewTab()
  {
    return _contentViewPanelTabbed;
  }
  
  public void setContentViewTab(UIXComponent contentViewPanelTabbed)
  {
    _contentViewPanelTabbed = contentViewPanelTabbed;
  }
  
  // Helper methods and member variables
  
  private List<BaseContentView> _createContentViews()
  { 
    // Create table content view
    BaseContentView tableCV = new TableContentView("contentView.table", 
                              "/fileExplorer/images/view_as_table_ena.png", 
                                        null);
    tableCV.setFileExplorerBean(_feBean);
    tableCV.setSelected(true);
    tableCV.setDisclosed(true);  
  
    // Create tree table content view
    BaseContentView treeTableCV = new TreeTableContentView("contentView.treetable", 
                              "/fileExplorer/images/view_as_hierarchy_ena.png", 
                                        null);
    treeTableCV.setFileExplorerBean(_feBean);
    treeTableCV.setSelected(false);
    treeTableCV.setDisclosed(false);  
    
    // Create list table content view
    BaseContentView listTableCV = new TableContentView("contentView.list", 
                              "/fileExplorer/images/listview_ena.png", 
                                        null);
    listTableCV.setFileExplorerBean(_feBean);
    listTableCV.setSelected(false);
    listTableCV.setDisclosed(false);  
    
    // Add the newly created content views to the array list
    List<BaseContentView> contentViewList = new ArrayList<BaseContentView>();
    contentViewList.add(tableCV);
    contentViewList.add(treeTableCV);
    contentViewList.add(listTableCV);
    
    return contentViewList;
  }
  
  private FileExplorerBean _feBean = null;
  private List<BaseContentView> _baseContentViews = null;
  private UIXComponent  _contentViewPanelTabbed = null;
}
