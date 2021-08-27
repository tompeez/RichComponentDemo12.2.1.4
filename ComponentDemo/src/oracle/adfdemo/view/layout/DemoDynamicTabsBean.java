/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.layout;

import java.io.File;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.UIXTable;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.nav.RichNavigationPane;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.ItemEvent;

import oracle.adfdemo.view.table.rich.FileSystem;

import org.apache.myfaces.trinidad.component.UIXPanel;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyMenuModel;
import org.apache.myfaces.trinidad.model.MenuModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

/**
 * Managed bean for handling dynamic tabs as seen in the Navigation-Master-Detail example.
 */
public class DemoDynamicTabsBean implements Serializable
{
  /**
   * Default constructor.
   */
  public DemoDynamicTabsBean()
  {
    _tabsInfo = new ArrayList<TabData>(5);
    _fs = new FileSystem();
    _rootPath = _fs.getRootDirectory().toString();
    TabData initialData = new TabData(_rootPath);
    initialData.setName(_ROOT_NAME); // override the name of the root folder
    _tabsInfo.add(initialData);
    _setSelectedTab(0);
  }

  /**
   * Returns the dynamic tab bar panel.
   * @return the dynamic tab bar panel
   */
  public RichNavigationPane getTabBarPanel()
  {
    return _tabBarPanel;
  }

  /**
   * Specifies the dynamic tab bar panel.
   * @param tabBarPanel the dynamic tab bar panel
   */
  public void setTabBarPanel(RichNavigationPane tabBarPanel)
  {
    _tabBarPanel = tabBarPanel;
  }

  /**
   * Returns the dynamic tab body panel.
   * @return the dynamic tab body panel
   */
  public UIXPanel getTabBodyPanel()
  {
    return _tabBodyPanel;
  }

  /**
   * Specifies the master detail panelTabbed.
   * @param panelTabbedMasterDetail the master detail panelTabbed
   */
  public void setPanelTabbedMasterDetail(RichPanelTabbed panelTabbedMasterDetail)
  {
    _panelTabbedMasterDetail = panelTabbedMasterDetail;
  }

  /**
   * Returns the master detail panelTabbed.
   * @return the master detail panelTabbed
   */
  public RichPanelTabbed getPanelTabbedMasterDetail()
  {
    return _panelTabbedMasterDetail;
  }

  /**
   * Specifies the dynamic tab body panel.
   * @param tabBodyPanel the dynamic tab body panel
   */
  public void setTabBodyPanel(UIXPanel tabBodyPanel)
  {
    _tabBodyPanel = tabBodyPanel;
  }

  /**
   * Returns the dynamic tab body table.
   * @return the dynamic tab body table
   */
  public UIXTable getSelectedTabContentsTable()
  {
    return _selectedTabContentsTable;
  }

  /**
   * Specifies the row detail panel.
   * @param rowDetailPanel the row detail panel
   */
  public void setRowDetailPanel(UIXPanel rowDetailPanel)
  {
    _rowDetailPanel = rowDetailPanel;
  }

  /**
   * Returns the row detail panel.
   * @return the row detail panel
   */
  public UIXPanel getRowDetailPanel()
  {
    return _rowDetailPanel;
  }

  /**
   * Specifies the dynamic tab body table.
   * @param selectedTabContentsTable the dynamic tab body table
   */
  public void setSelectedTabContentsTable(UIXTable selectedTabContentsTable)
  {
    _selectedTabContentsTable = selectedTabContentsTable;
  }

  /**
   * Retrieves the MenuModel for the folder tabs.
   * @return the MenuModel for the folder tabs
   */
  public MenuModel getTabsMenuModel()
  {
    ArrayList<Integer> focusRowKey = new ArrayList<Integer>(1);
    focusRowKey.add(_selectedTabIndex);
    MenuModel menuModel = new ChildPropertyMenuModel(_tabsInfo, "kids", focusRowKey);
    return menuModel;
  }

  /**
   * Handle tab item remove event for the navPane component
   */
  public void handleNavPaneTabItemRemove(ItemEvent e)
  {
    if (!ItemEvent.Type.remove.equals(e.getType()))
    {
      return;
    }

    UIComponent source = (UIComponent) e.getSource();
    Map attributes = source.getAttributes();
    Integer index = (Integer) attributes.get("index");
    int indexToRemove = index.intValue();
    
    //remove the tab
    _tabsInfo.remove(indexToRemove);
    
    if (_selectedTabIndex > indexToRemove)
    {
      // if the selected tab is after the removed tab, move selection index down one
      _selectedTabIndex--;
    }
    else if (_selectedTabIndex == indexToRemove) //case: selected tab was removed
    {
      // if the selected tab is the removed tab, select next tab if available
      int tabCount = _tabsInfo.size();
      boolean lastSelectedTabRemoved = indexToRemove >= tabCount;
        
      int newSelectedTabIndex = (lastSelectedTabRemoved) ? --_selectedTabIndex : _selectedTabIndex;
      _setSelectedTab(newSelectedTabIndex);
    }
    else
    {
      //if the selected tab is before the removed tab, nothing else needs to be done
    }

    AdfFacesContext afc = AdfFacesContext.getCurrentInstance();
    afc.addPartialTarget(_tabBarPanel);
    afc.addPartialTarget(_tabBodyPanel);
  }

  /** 
   * Handle tab selection from the navPane component
   */
  public void handleNavPaneTabSelection(ActionEvent e)
  {
    // The ActionEvent should be from a command component that passes file path information onto the
    // pageFlowScope:
    AdfFacesContext afc = AdfFacesContext.getCurrentInstance();
    String path = (String)afc.getPageFlowScope().get("path");

    //set the new selected tab and update the page
    int newSelectedTabIndex = _getTabDataIndex(path);
    _setSelectedTab(newSelectedTabIndex);
    afc.addPartialTarget(_tabBarPanel);
    afc.addPartialTarget(_tabBodyPanel);
  }

  /**
   * Handles the navigation tree link actions in order to display the correct corresponding master
   * and detail area; creates a tab (if applicable) and makes it the currently-shown tab.
   * @param e the ActionEvent
   */
  public void handleNavTreeLink(ActionEvent e)
  {
    // The ActionEvent should be from a command component that passes file path information onto the
    // pageFlowScope:
    AdfFacesContext afc = AdfFacesContext.getCurrentInstance();
    String path = (String)afc.getPageFlowScope().get("path");
    boolean needsUpdate = false;

    // If we already have a tab entry for this path, let's make it the active one.
    // Otherwise, let's add an entry for it and then make it the active one.
    TabData tabData = _getTabData(path);
    if (tabData == null)
    {
      // Add and select the tab:
      TabData newTabData = new TabData(path);
      if (path.equals(_rootPath))
      {
        newTabData.setName(_ROOT_NAME); // override the name of the root folder
      }
      _tabsInfo.add(newTabData);
      _setSelectedTab(_tabsInfo.size() - 1);
      needsUpdate = true;
    }
    else
    {
      // Select the tab (if not already selected):
      int newSelectedTabIndex = _getTabDataIndex(path);
      if (newSelectedTabIndex != _selectedTabIndex)
      {
        _setSelectedTab(newSelectedTabIndex);
        needsUpdate = true;
      }
    }

    if (needsUpdate)
    {
      afc.addPartialTarget(_tabBarPanel);
      afc.addPartialTarget(_tabBodyPanel);
    }
  }

  /**
   * Handles the master table selections in order to display the correct corresponding detail.
   * @param e the SelectionEvent
   */
  public void handleTableRowSelection(SelectionEvent e)
  {
    RowKeySet selectedRowKeySet = e.getAddedSet();
    Iterator<Object> selectedRowKeySetIterator = selectedRowKeySet.iterator();
    TabData selectedTableRowData = null;

    if (selectedRowKeySetIterator.hasNext())
    {
      Object selectedRowKey = selectedRowKeySetIterator.next();

      // In order to translate from row key to data index, we need to use the UIXTable object to
      // convert between row key and row index:

      // 1. Get the current row key so that we can restore it when done:
      Object rowKeyToRestore = _selectedTabContentsTable.getRowKey();

      // 2. Set the row key to be the selected row key from the SelectionEvent:
      _selectedTabContentsTable.setRowKey(selectedRowKey);

      // 3. Get the selected row data:
      selectedTableRowData = (TabData)_selectedTabContentsTable.getRowData();

      // 4. Clean up after ourselves by restoring the existing row key:
      _selectedTabContentsTable.setRowKey(rowKeyToRestore);
    }

    boolean needsUpdate = false;
    if (selectedTableRowData == null && _selectedTableRowData != null)
    {
      _selectedTableRowData = null;
      needsUpdate = true;
    }
    else if (!selectedTableRowData.equals(_selectedTableRowData))
    {
      _selectedTableRowData = selectedTableRowData;
      needsUpdate = true;
    }

    if (needsUpdate)
    {
      AdfFacesContext afc = AdfFacesContext.getCurrentInstance();
      afc.addPartialTarget(_rowDetailPanel);
    }
  }

  /**
   * Fetches the data for the selected tab.
   * @return the data for the selected tab
   */
  public TabData getSelectedTabData()
  {
    return _selectedTabData;
  }

  /**
   * Fetches the data for the selected tab's table row data.
   * @return the data for the selected tab's table row data
   */
  public TabData getSelectedTableRowData()
  {
    return _selectedTableRowData;
  }

  /**
   * Fetches the list of file data for the children of the item represented by the selected tab.
   * @return a list of TabData objects for the contents of the selected tab
   */
  public List<TabData> getSelectedTabContents()
  {
    if (_selectedTabContents == null)
    {
      File parent = new File(_selectedTabData.getPath());

      if (_selectedTabData.isDirectory())
      {
        ArrayList<TabData> contents = new ArrayList<TabData>();
        File[] children = parent.listFiles();

        for (int i=0; i<children.length; i++)
        {
          contents.add(new TabData(children[i].getPath()));
        }

        _selectedTabContents = contents;
      }
      else
      {
        _selectedTabContents = Collections.emptyList();
      }
    }
    return _selectedTabContents;
  }

  private TabData _getTabData(String path)
  {
    for (TabData tabData : _tabsInfo)
    {
      if (tabData.getPath().equals(path))
      {
        return tabData;
      }
    }
    return null;
  }

  private TabData _getTabData(int index)
  {
    return _tabsInfo.get(index);
  }

  private int _getTabDataIndex(String path)
  {
    int i = 0;
    for (TabData tabData : _tabsInfo)
    {
      if (tabData.getPath().equals(path))
      {
        break;
      }
      i++;
    }
    return i;
  }

  /**
   * Set the selected tab to the indicated index.
   */
  private void _setSelectedTab(int index)
  {
    //unselect the currently selected panelTabbed item
    if (_selectedTabData != null)
    {
      _selectedTabData.setSelected(false);
    }
    
    // Reset all tab and table selection information and corresponding cached data:
    _selectedTabIndex = index;
    _selectedTabData = _getTabData(index);
    _selectedTabContents = null;
    if (_selectedTabContentsTable != null)
    {
      _selectedTabContentsTable.setSelectedRowKeys(null);
    }
    _selectedTableRowData = null;
    
    //select the newly selected panelTabbed item
    _selectedTabData.setSelected(true);
  }

  public class TabData
  {
    public TabData(String path)
    {
      this._path = path;
    }

    public String getPath()
    {
      return _path;
    }

    public String getDisplayPath()
    {
      return _ROOT_NAME + _path.substring(_rootPath.length());
    }

    public String getName()
    {
      _initializeTabData();
      return _name;
    }

    public void setName(String name)
    {
      _initializeTabData();
      _name = name;
    }

    public boolean getSelected()
    {
      return _selected;
    }

    public void setSelected(boolean selected)
    {
      _selected = selected;
    }

    public long getLastModifiedSortable()
    {
      return _lastModified;
    }

    public String getLastModified()
    {
      _initializeTabData();
      DateFormat df =
        DateFormat.getDateTimeInstance(
          DateFormat.FULL,
          DateFormat.FULL,
          FacesContext.getCurrentInstance().getExternalContext().getRequestLocale());
      return df.format(new Date(_lastModified));
    }

    public long getSizeSortable()
    {
      return _size;
    }

    public String getSize()
    {
      _initializeTabData();
      NumberFormat nf =
        NumberFormat.getInstance(
          FacesContext.getCurrentInstance().getExternalContext().getRequestLocale());
      return nf.format(_size);
    }

    public boolean isDirectory()
    {
      _initializeTabData();
      return _isDirectory;
    }

    public List getKids()
    {
      // MenuModel can be flat so let's leave this null:
      return Collections.emptyList();
    }

    private void _initializeTabData()
    {
      if (!_dataLoaded)
      {
        // Lazily-load the tab data:
        File file = new File(_path);
        _name = file.getName();
        _lastModified = file.lastModified();
        _size = file.length();
        _isDirectory = file.isDirectory();
        _dataLoaded = true;
      }
    }

    boolean _dataLoaded;
    String _path;
    String _name;
    long _lastModified;
    long _size;
    boolean _isDirectory;
    boolean _selected = false;
  }

  private final static String _ROOT_NAME = "My Files";
  private static final long serialVersionUID = 1L;    

  private List<TabData> _tabsInfo;
  private FileSystem _fs;
  private String _rootPath;
  private TabData _selectedTabData;
  private int _selectedTabIndex;
  private List<TabData> _selectedTabContents;
  private TabData _selectedTableRowData;
  private RichNavigationPane _tabBarPanel;
  private UIXPanel _tabBodyPanel;
  private UIXTable _selectedTabContentsTable;
  private UIXPanel _rowDetailPanel;
  private RichPanelTabbed _panelTabbedMasterDetail;
}
