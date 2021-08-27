package oracle.adfdemo.view.feature.rich.tagCloud;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import oracle.adf.view.faces.bi.component.tagCloud.UITagCloud;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adfdemo.view.feature.rich.tagCloud.data.TagCloudData;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;

import org.apache.myfaces.trinidad.component.UIXCollection;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class DragAndDropSample {
  private UITagCloud _tagCloud;
  private CollectionModel _tagCloudItems;
  private CollectionModel tableModel;
  private RichTable table;
  private RichOutputFormatted _richOutputFormatted;
  private String _status;
  private TagCloudData _tagCloudData = TagCloudData.getInstance();
  
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
    createModel();
    return this._tagCloudItems;
  }
  
  private void createModel() {
    if(_tagCloudItems == null) {
      _tagCloudItems = _tagCloudData.getSocialNetworksData();
      _tagCloudItems = _tagCloudData.getSortedData(true);
    }
  }
  
  public CollectionModel getTableModel() {
    if (tableModel == null) {
      tableModel = ModelUtils.toCollectionModel(new ArrayList());
    }
    return tableModel;
  }

  public void setTable(RichTable table) {
    this.table = table;
  }

  public RichTable getTable() {
    return table;
  }
  
  public void setRichOutputText(RichOutputFormatted richOutputFormatted) {
    this._richOutputFormatted = richOutputFormatted;
  }

  public RichOutputFormatted getRichOutputText() {
    return _richOutputFormatted;
  }
  
  public String getStatus() {
    return _status;
  }
  
  public DnDAction tagCloudDropListener(DropEvent dropEvent) {
    Transferable transferable = dropEvent.getTransferable();
    DataFlavor<RowKeySet> dataFlavor = DataFlavor.getDataFlavor(RowKeySet.class);
    RowKeySet rowKeySet = transferable.getData(dataFlavor);
    if (rowKeySet == null || rowKeySet.getSize() <= 0)
      return DnDAction.NONE;

    UIXCollection dragComponent = (UIXCollection)dropEvent.getDragComponent();
    Object[] rowKeys = rowKeySet.toArray();
    Set<String> wordList = new HashSet<String>();
    _status = "<b>Drop Event</b> triggered on following Tag Cloud items: <b>";
    for (int i = 0; i < rowKeys.length; i++) {
      Map<String, Object> node = (Map<String, Object>)dragComponent.getRowData(rowKeys[i]);
      wordList.add((String)node.get("word"));
      _status += (String)node.get("word") + ", ";
    }
    _status = _status.substring(0, _status.length()-2);
    _status += "</b>";
    RequestContext.getCurrentInstance().addPartialTarget(this._tagCloud);
    if (table.equals(dragComponent)) {
      List tableData = (List)tableModel.getWrappedData();
      List<Map<String,Object>> tagCloudData = new ArrayList<Map<String,Object>>();
      for (int i = tableData.size() - 1; i >= 0; i--) {
        Map<String, Object> node = (Map<String, Object>)tableData.get(i);
        if (wordList.contains(node.get("word"))) {
          tableData.remove(i);
          tagCloudData.add(node);
        }
      }
      _tagCloudData.addData(tagCloudData);
      table.setSelectedRowKeys(new RowKeySetImpl());
      RequestContext.getCurrentInstance().addPartialTarget(table);
      RequestContext.getCurrentInstance().addPartialTarget(this._tagCloud);
      RequestContext.getCurrentInstance().addPartialTarget(_richOutputFormatted);
      
    }
    
    return dropEvent.getProposedAction();    
  }

  public DnDAction tableDropListener(DropEvent dropEvent) {
    if (table.equals(dropEvent.getDragComponent())) {
      return DnDAction.NONE;
    }
    Transferable transferable = dropEvent.getTransferable();
    DataFlavor<RowKeySet> dataFlavor = DataFlavor.getDataFlavor(RowKeySet.class);
    RowKeySet rowKeySet = transferable.getData(dataFlavor);
    if (rowKeySet == null || rowKeySet.getSize() <= 0)
      return DnDAction.NONE;

    Object[] rowKeys = rowKeySet.toArray();
    List<Map<String,Object>> tagCloudData = new ArrayList<Map<String,Object>>();
    _status = "<b>Drag Event</b> triggered on following Tag Cloud items: <b>";
    for (int i = 0; i < rowKeys.length; i++) {
      Map<String, Object> node = (Map<String, Object>)this._tagCloud.getRowData(rowKeys[i]);
      ((List)tableModel.getWrappedData()).add(node);
      tagCloudData.add(node);
      _status += (String)node.get("word") + ", ";
    }
    _status = _status.substring(0, _status.length()-2);
    _status += "</b>";
    _tagCloudData.removeData(tagCloudData);
    this._tagCloud.setSelectedRowKeys(new RowKeySetImpl());
    RequestContext.getCurrentInstance().addPartialTarget(this._tagCloud);
    RequestContext.getCurrentInstance().addPartialTarget(table);
    RequestContext.getCurrentInstance().addPartialTarget(_richOutputFormatted);
    return dropEvent.getProposedAction();    
  }
}
