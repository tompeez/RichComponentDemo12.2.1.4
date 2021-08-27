/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import oracle.adfdemo.view.explorer.rich.bean.FileExplorerBean;
import oracle.adfdemo.view.explorer.rich.data.FileItem;

import oracle.adfdemo.view.explorer.rich.navigator.BaseNavigatorView;
import oracle.adfdemo.view.explorer.rich.navigator.FoldersNavigatorView;
import oracle.adfdemo.view.explorer.rich.navigator.SearchNavigatorView;

import org.apache.myfaces.trinidad.component.UIXPanel;
import org.apache.myfaces.trinidad.context.RequestContext;


public class NavigatorManager
{
  public static String removeRootFileName(String path)
  {
    if (path == null)
    {
      return path;
    }
    
    ResourceBundle fileExplorerBundle = 
      ResourceBundle.getBundle(FileExplorerBean.FILE_EXPLORER_BUNDLE_NAME, 
                               FacesContext.getCurrentInstance().getViewRoot().getLocale());
    String myFiles = 
      fileExplorerBundle.getString("navigator.myfiles") + FileItem.PATH_SEPARATOR;

    if (path.equals(fileExplorerBundle.getString("navigator.myfiles")) == true)
    {
      return "";
    }
    else
    {
      if (path.contains(myFiles) == false)
      {
        return path;
      }
      else
      {
        return path.substring(myFiles.length());
      }
    }
  }
  
  public NavigatorManager(FileExplorerBean feBean)
  {
    _feBean = feBean;
  }
  
  // Refresh the navigators' models and components
  public void refresh()
  {
    for (BaseNavigatorView nav: getNavigators())
    {
      nav.refresh();
    }
    
    RequestContext adfContext = RequestContext.getCurrentInstance();
      adfContext.addPartialTarget(_navigatorAccordion);
  }
  
  /**
   * @return FoldersNavigatorView
   */
  public FoldersNavigatorView getFoldersNavigator()
  {
    return (FoldersNavigatorView)this.getNavigators().get(0);
  }

  /**
   * @return SearchNavigatorView
   */
  public SearchNavigatorView getSearchNavigator()
  {
    return (SearchNavigatorView)this.getNavigators().get(1);
  }
  
  /**
   * Returns List of navigators
   * @return a List of navigators
   */
  public List<BaseNavigatorView> getNavigators()
  {
    if (_navigators == null)
    {
      // set navigator ui
      _navigators = _createNavigators();
    }
    
    return _navigators;
  }

  /**
   * Set the List of navigators
   * @param navigators
   */
  public void setNavigators(List<BaseNavigatorView> navigators)
  {
    this._navigators = navigators;
  }
  
  // Components accessors
  
  public void setNavigatorAccordion(UIXPanel navigatorAccordion)
  {
    _navigatorAccordion = navigatorAccordion;
  }

  public UIXPanel getNavigatorAccordion()
  {
    return _navigatorAccordion;
  }
  
  // Helper methods and member variables  
  
  private List<BaseNavigatorView> _createNavigators()
  { 
    ResourceBundle fileExplorerBundle = 
      ResourceBundle.getBundle(FileExplorerBean.FILE_EXPLORER_BUNDLE_NAME, 
                               FacesContext.getCurrentInstance().getViewRoot().getLocale());

    // Folders Navigator
    BaseNavigatorView foldersNavigator = 
      new FoldersNavigatorView("navigator.folders", 
                            "/fileExplorer/images/directorytreeview.png", 
                            fileExplorerBundle.getString("navigator.folders"), 
                            null);
    foldersNavigator.setFileExplorerBean(_feBean);
    foldersNavigator.setSelected(true);
    foldersNavigator.setDisclosed(true);

    // Search Navigator
    BaseNavigatorView searchNavigator = 
      new SearchNavigatorView("navigator.search", 
                            "/fileExplorer/images/find_ena.png", 
                            fileExplorerBundle.getString("navigator.search"), 
                            null);
    searchNavigator.setFileExplorerBean(_feBean);
    searchNavigator.setSelected(false);
    searchNavigator.setDisclosed(false);
    searchNavigator.setFlex(2);    
    
    List<BaseNavigatorView> navList = new ArrayList<BaseNavigatorView>();
    navList.add(foldersNavigator);
    navList.add(searchNavigator);
    
    return navList;
  }
  
  private FileExplorerBean _feBean;
  private UIXPanel _navigatorAccordion;
  
  private List<BaseNavigatorView> _navigators;
}
