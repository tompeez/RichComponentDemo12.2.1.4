package oracle.adfdemo.view.feature.rich.tagCloud;

import java.util.Iterator;
import java.util.Map;

import java.util.Random;

import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.tagCloud.UITagCloud;
import oracle.adfdemo.view.feature.rich.tagCloud.data.TagCloudData;
import oracle.adf.view.rich.component.rich.nav.RichButton;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class TagCloudSample {
  private UITagCloud _tagCloud;
  private CollectionModel _tagCloudItems;
  private String _dataSource = "Census";
  private String _emptyData = "No data to display";
  private boolean _sortData = false;
  private boolean _randomColor = false;
  private String _layout = "rectangular";
  private String _selectionMode = "none";
  private String _displayTransition = "none";
  private String _dataChangeTransition = "none";
  private String _contentDelivery = "immediate";
  private TagCloudData _tagCloudData = TagCloudData.getInstance();
  private RowKeySet _selectedItems = new RowKeySetImpl();
  private RichButton _updateColorButton;
  
  public void setTagCloud(UITagCloud tagCloud) {
    this._tagCloud = tagCloud;
  }

  public UITagCloud getTagCloud() {
    return this._tagCloud;
  }

  public void setItems(CollectionModel tagCloudItems) {
    this._tagCloudItems = tagCloudItems;
  }

  public CollectionModel getModel() {
    return this.getItems();
  }

  public CollectionModel getItems() {
    if(_tagCloudItems != null)
      return this._tagCloudItems;
    createModel();
    return this._tagCloudItems;
  }

  private void createModel() {
    if(_dataSource.equals("Social"))
      _tagCloudItems = _tagCloudData.getSocialNetworksData();
    else if(_dataSource.equals("GDP"))
      _tagCloudItems = _tagCloudData.getGDPData();
    else if(_dataSource.equals("Census"))
      _tagCloudItems = _tagCloudData.getCensusData();
    else
      _tagCloudItems = null;
    if(this._sortData)
      setSortData(this._sortData);
    if(this._randomColor)
      setRandomColor(this._randomColor);
    _selectedItems.clear();
    _initializeSelectedRows();
  }
  
  public String getDataSource() {
    return this._dataSource;
  }
  
  public void setDataSource(String dataSource) {
    this._dataSource = dataSource;
    _tagCloudItems = null;
    _selectedItems.clear();
    _initializeSelectedRows();
  }
  
  public String getEmptyData() {
    return this._emptyData;
  }
  
  public void setEmptyData(String emptyData) {
    this._emptyData = emptyData;
  }
  
  public boolean getSortData() {
    return this._sortData;
  }
  
  public void setSortData(boolean sortData) {
    if("none".equals(_dataSource))
      return;

    this._sortData = sortData;
    if("none".equals(_dataSource))
      return;
    _tagCloudItems = _tagCloudData.getSortedData(sortData);
  }
  
  public boolean getRandomColor() {
    return this._randomColor;
  }
  
  public void setRandomColor(boolean randomColor) {
    if("none".equals(_dataSource))
      return;

    this._randomColor = randomColor;
    if(randomColor)
      changeColor(null);
    else
     _tagCloudData.setDefaultColor();
    
    _updateColorButton.setDisabled(!randomColor);
    RequestContext.getCurrentInstance().addPartialTarget(_updateColorButton);
  }
  
  public RichButton getUpdateColorButton() {
    return _updateColorButton;
  }
  
  public void setUpdateColorButton(RichButton button) {
    _updateColorButton = button;
  }

  public String getLayout() {
    return this._layout;
  }

  public void setLayout(String layout) {
    this._layout = layout;
  }
  
  public String getDisplayTransition() {
    return _displayTransition;
  }
  
  public void setDisplayTransition(String displayTransition) {
    _displayTransition = displayTransition;
  }
  
  public String getContentDelivery() {
    return _contentDelivery;
  }
  
  public void setContentDelivery(String contentDelivery) {
    _contentDelivery = contentDelivery;
  }
  
  public String getDataChangeTransition() {
    return _dataChangeTransition;
  }
  
  public void setDataChangeTransition(String dataTransition) {
    _dataChangeTransition = dataTransition;
  }

  public String getSelectionMode() {
    return _selectionMode;
  }

  public void setSelectionMode(String selectionMode) {
    _selectionMode = selectionMode;
    if(_selectionMode.equals("none"))
      _selectedItems.removeAll();
    
    if(_selectionMode.equals("single") && _selectedItems.getSize() > 1) {
      while(_selectedItems.getSize() > 1)
        _selectedItems.remove(_selectedItems.toArray()[_selectedItems.getSize() - 1]);
    }
    
    _initializeSelectedRows();
  }

  public void actionListener(ActionEvent actionEvent) {

  }

  public void selectionListener(SelectionEvent selectionEvent) {
    _selectedItems.removeAll(selectionEvent.getRemovedSet());
    _selectedItems.addAll(selectionEvent.getAddedSet());
  }
  
  private void _initializeSelectedRows() {
    if(_selectionMode.equals("none") ||  _tagCloudItems == null)
      return;
    
    if(_selectionMode.equals("single")) {
      if(_selectedItems.getSize() > 0)
        return;
      else {
        //initial selected items
        _tagCloudItems.setRowIndex(0);
        _selectedItems.add(_tagCloudItems.getRowKey());
      }
    }
    
    if(_selectionMode.equals("multiple")) {
      if(_selectedItems.getSize() > 1)
        return;
      else {
        int rowIndex = 0;
        while(_selectedItems.getSize() < 2) {
          //initial selected items
          _tagCloudItems.setRowIndex(rowIndex);
          _selectedItems.add(_tagCloudItems.getRowKey());
          rowIndex++;
        }
      }
    }
    //reset row keys
    _tagCloudItems.setRowKey(null);
  }

  public void setSelectedRows(RowKeySet items) {
    _selectedItems = items;
  }
  
  public RowKeySet getSelectedRows() {
    return _selectedItems;
  }

  public String getSelectedItems() {
    String _selections = "";
    try {
      _selectedItems = _tagCloud.getSelectedRowKeys();
      if (_selectedItems != null && _tagCloudItems != null) {
        Iterator iterator = _selectedItems.iterator();
        while (iterator.hasNext()) {
          Map data = (Map) _tagCloudItems.getRowData(iterator.next());
          _selections += "<br> " + (data.get("word") + ": " + String.format("%.2f",data.get("frequency")));
        }
      }
    } catch(Exception e) {
      
    }
    return _selections;
  }
  
  public boolean isMultipleSelection() {
    if (this._selectedItems != null && this._selectedItems.getSize() > 1) {
      return true;
    }
    return false;
  }

  public void changeValue(ActionEvent actionEvent) {
    if("none".equals(_dataSource))
      return;
    _tagCloudData.setRandomValue();
  }

  public void changeColor(ActionEvent actionEvent) {
    if("none".equals(_dataSource))
      return;
    this._randomColor = true;
    _tagCloudData.setRandomColor();
  }
}
