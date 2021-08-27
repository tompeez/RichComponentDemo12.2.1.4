package oracle.adfdemo.view.feature.rich.hv;

import java.util.ArrayList;

import java.util.List;

import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import oracle.adf.view.faces.bi.component.hierarchyViewer.SearchResults;
import oracle.adf.view.faces.bi.component.hierarchyViewer.UIHierarchyViewer;

import oracle.adf.view.faces.bi.event.hierarchyViewer.ResultEvent;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adfdemo.view.feature.rich.hv.data.EmployeeData;

import oracle.adfdemo.view.feature.rich.hv.data.EmployeeNode;
import oracle.adfdemo.view.feature.rich.hv.data.EmployeeResult;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.TreeModel;


public class SearchSample {
  
  protected UIHierarchyViewer _hvComponent;
  
  // Data Models: Instance needs to be consistent across requests
  private TreeModel DATA_SIMPLE = EmployeeData.newModel();
  
  protected String _searchText = null;
  protected List<EmployeeResult> _results = new ArrayList<EmployeeResult>();
  protected CollectionModel _resultModel = ModelUtils.toCollectionModel(_results);
  protected List<EmployeeNode> _flatList = null;
  protected List<String> _filterOptions = new ArrayList<String>(defaultFilterOptions);
  protected List<String> _tempFilterOptions = _filterOptions;
  protected static List<String> defaultFilterOptions = new ArrayList<String>();
  protected List<SelectItem> _searchableFields = null;
  protected String _selectedId = null;
  
  static {
      defaultFilterOptions.add(EmployeeNode.FirstName);
      defaultFilterOptions.add(EmployeeNode.LastName);
  }
  
  public TreeModel getHvModel() {
    List<EmployeeNode> result = new ArrayList<EmployeeNode>();

    //save old RowKey
    Object oldRowKey = DATA_SIMPLE.getRowKey();

    // point the tree to the root
    DATA_SIMPLE.setRowKey(null);
    DATA_SIMPLE.setRowIndex(0);
    addContainer(result); 
        

    //restore old RowKey
    DATA_SIMPLE.setRowKey(oldRowKey);


    _flatList = result;
    return DATA_SIMPLE;
  }
  protected void addChild(List<EmployeeNode> result) {
      if (DATA_SIMPLE.isRowAvailable()) {
          result.add((EmployeeNode)(DATA_SIMPLE.getRowData()));
      }
  }
  protected void addContainer(List<EmployeeNode> result) {

      //if (DATA_SIMPLE.isContainer() && !DATA_SIMPLE.isContainerEmpty()) {
          // add self
          addChild(result);

          // add children
          DATA_SIMPLE.enterContainer();
        int nRowCount = DATA_SIMPLE.getRowCount();

        for (int i = 0; i < nRowCount; i++) {
            DATA_SIMPLE.setRowIndex(i);
            addContainer(result);
        }
          DATA_SIMPLE.exitContainer();

         // return true;
      //}
      //return false;
  }

  public void setHvComponent(UIHierarchyViewer hvComponent) {
    this._hvComponent = hvComponent;
  }


  public UIHierarchyViewer getHvComponent() {
      return _hvComponent;
    }

  public void setSearchText(String _searchText) {
    this._searchText = _searchText;
  }

  public String getSearchText() {
    return _searchText;
  }
  
  public void doSearch(ActionEvent event) {

      String[] segments = getSearchText().toLowerCase().split(" ");

      ArrayList<String> searchTerms = new ArrayList<String>();
      for (int i = 0; i < segments.length; i++) {
          String s = segments[i];
          if (!s.equals("")) {
              String pattern = s.replaceAll("_", ".").replaceAll("%", ".*");
              pattern = ".*" + pattern + ".*";
              searchTerms.add(pattern);
          }
      }
      _results.clear();

      for (int i = 0; i < _flatList.size(); i++) {
          EmployeeNode employee = _flatList.get(i);
          for (String attr : _filterOptions) {
              boolean found = false;
              String val = employee.getAttribute(attr);
              if (val != null) {
                  for (String pattern : searchTerms) {
                      if (val.toLowerCase().matches(pattern)) {
                          _results.add(new EmployeeResult(employee, attr, val, pattern));
                          found = true;
                          break;
                      }
                  }
              }
              if (found) {
                  break;
              }

          }

      }
  }
  public CollectionModel getResults() {
      return _resultModel;
  }
  public void doResultAction(ActionEvent event) {
      SearchResults results =
          (SearchResults)((ResultEvent)event).getSearchResults();

      CollectionModel model = (CollectionModel)results.getValue();
      EmployeeResult emResult = (EmployeeResult)model.getRowData();

      EmployeeNode em = emResult.getEmployee();
      DATA_SIMPLE = new ChildPropertyTreeModel(em, "children");
  }
  public void handleAdvanced(DialogEvent event) {
      if (event.getOutcome().equals(DialogEvent.Outcome.ok)) {
          System.out.println("Yes triggered");
          _filterOptions = _tempFilterOptions;
      }

  }
  public void setFilterOptions(List<String> items) {
     // System.out.println("Setting temp filter options");
      _tempFilterOptions = items;
  }
  public List<String> getFilterOptions() {
      return _filterOptions;
  }
  public List<SelectItem> getSearchableOptions() {
      if (_searchableFields == null) {
          List<SelectItem> options = new ArrayList<SelectItem>();
          for (String key : EmployeeNode.attribute_names) {
              SelectItem item = new SelectItem();
              item.setLabel(key);
              item.setValue(key);
              options.add(item);
          }

          _searchableFields = options;
      }
      _tempFilterOptions = _filterOptions;
      return _searchableFields;

  }
  public void doSetAnchor(ActionEvent actionEvent) {
      // Add event code here...

      UIHierarchyViewer hv = (UIHierarchyViewer)actionEvent.getSource();
      TreeModel model = (TreeModel)hv.getValue();
      EmployeeNode em = (EmployeeNode)model.getRowData();

      DATA_SIMPLE = new ChildPropertyTreeModel(em, "children");
  }
  public void doNavigateUp(ActionEvent actionEvent) {
      UIHierarchyViewer hv = (UIHierarchyViewer)actionEvent.getSource();
      TreeModel model = (TreeModel)hv.getValue();
      EmployeeNode em = (EmployeeNode) model.getRowData();

      if (em.getParent() != null)
        DATA_SIMPLE = new ChildPropertyTreeModel(em.getParent(), "children");
  }

  public void setSelectedId(String _selectedId) {
    this._selectedId = _selectedId;
  }

  public String getSelectedId() {
    return _selectedId;
  }
}
