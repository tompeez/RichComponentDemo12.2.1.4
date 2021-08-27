package oracle.adfdemo.view.components.rich;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.UUID;

import javax.faces.component.UIComponent;

import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adf.view.rich.event.MasonryDeleteEvent;
import oracle.adf.view.rich.event.MasonryInsertEvent;
import oracle.adf.view.rich.event.MasonryReorderEvent;
import oracle.adf.view.rich.event.MasonryResizeEvent;

import org.apache.myfaces.trinidad.component.UIXIterator;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeyPropertyModel;

public class DemoMasonryLayoutBean
{
  public DemoMasonryLayoutBean()
  {
  }
  
  public CollectionModel getBasicData()
  {
    if (_basicData == null)
      _basicData = _createData();
    if (_basicDataModel == null)
      _basicDataModel = new RowKeyPropertyModel(_basicData, "key");
    return _basicDataModel;
  }
  
  public CollectionModel getData()
  {
    if (_data == null)
      _data = _createData();
    if (_dataModel == null)
      _dataModel = new RowKeyPropertyModel(_data, "key");
    return _dataModel;
  }
  
  public CollectionModel getInsertData()
  {
    if (_insertData == null)
    {
      _insertData = _createData();
      //keep a separate list of all the data, both rendered and not rendered, 
      //in order to maintain the ordering for inserts
      _orderData = new ArrayList<Map<String, Object>>();
      for (int i = 0; i < _insertData.size(); i++)
      {
        _orderData.add(_insertData.get(i));
      }
    }
    if (_insertDataModel == null)
        _insertDataModel = new RowKeyPropertyModel(_insertData, "key");
    return _insertDataModel;
  }
  
  public UIXIterator getMasonryIterator()
  {
    return _masonryIterator;
  }
  
  public void setMasonryIterator(UIXIterator iterator)
  {
    _masonryIterator = iterator;
  }
  
  public UIXIterator getInsertIterator()
  {
    return _insertIterator;
  }
  
  public void setInsertIterator(UIXIterator iterator)
  {
    _insertIterator = iterator;
  }
  
  public RichPanelGroupLayout getInsertPgl()
  {
    return _insertPgl;
  }
  
  public void setInsertPgl(RichPanelGroupLayout pgl)
  {
    _insertPgl = pgl;
  }
  
  public int getInsertIndex()
  {
    Map<String, Object> insertItem = (Map<String, Object>)_insertIterator.getRowData();
    if (insertItem == null)
      return -1;
    String symbol = (String)insertItem.get("symbol");
    int index = 0;
    //figure out the insert index from the ordered list of all data items
    for (int i = 0; i < _orderData.size(); i++)
    {
      Map<String, Object> item = _orderData.get(i);
      String currSymbol = (String)item.get("symbol");
      if (currSymbol.equals(symbol))
        return index;
      Boolean renderedBool = (Boolean)item.get("rendered");
      if (renderedBool.booleanValue() == true)
        index++;
    }
    return -1;
  }
  
  public void handleDelete(MasonryDeleteEvent event)
  {
    String deleteId = event.getChildClientId();
    UIComponent source = (UIComponent)event.getSource();
    
    FacesContext facesContext = FacesContext.getCurrentInstance();
    VisitContext visitContext = VisitContext.createVisitContext(
      facesContext,
      Collections.singleton(deleteId), 
      EnumSet.of(VisitHint.SKIP_UNRENDERED));
    GetDataByClientIdCallback callback = new GetDataByClientIdCallback();
    source.visitTree(visitContext, callback);

    Map<String, Object> data = callback.getData();
    String symbol = (String)data.get("symbol");
    //remove the data item from the masonryLayout and store it in the list of 
    //deleted items
    _data.remove(data);
    if (_deletedItems == null)
      _deletedItems = new ArrayList<Map<String, Object>>();
    _deletedItems.add(data);
    
    //mark the data item for the insert link as not rendered
    Map<String, Object> insertItem = _getInsertItemForSymbol(symbol);
    insertItem.put("rendered", false);
    
    RequestContext rc = RequestContext.getCurrentInstance();
    rc.addPartialTarget(_insertPgl);
  }
  
  private Map<String, Object> _getInsertItemForSymbol(String symbol)
  {
    for (int i = 0; i < _insertData.size(); i++)
    {
      Map<String, Object> insertItem = _insertData.get(i);
      if (symbol.equals(insertItem.get("symbol")))
        return insertItem;
    }
    return null;
  }
  
  public void handleInsert(MasonryInsertEvent event)
  {
    //FIX BUG 19996416: process the first queued insert
    String symbol = null;
    if (_pendingInserts != null && _pendingInserts.size() > 0)
    {
      symbol = _pendingInserts.remove(0);
    }
    
    for (int i = 0; i < _deletedItems.size(); i++)
    {
      //find the data for the deleted item to be inserted again
      Map<String, Object> item = _deletedItems.get(i);
      String itemSymbol = (String)item.get("symbol");
      if (itemSymbol.equals(symbol))
      {
        //mark the data for the insert link as rendered
        Map<String, Object> insertItem = _getInsertItemForSymbol(symbol);
        insertItem.put("rendered", true);
        
        //remove the data item from the deleted list
        _deletedItems.remove(i);
        //create a new copy of the deleted item, and give it a unique key
        //so that it will get a unique clientId in the iterator
        Map<String, Object> newItem = new HashMap<String, Object>(item);
        newItem.put("key", UUID.randomUUID().toString());
        //add the new item to the masonry data collection
        int insertIndex = event.getIndex();
        if (insertIndex > -1)
          _data.add(insertIndex, newItem);
        else
          _data.add(newItem);
        
        break;
      }
    }

    RequestContext rc = RequestContext.getCurrentInstance();
    rc.addPartialTarget(_insertPgl);
  }
  
  public void handleInsertAction(ActionEvent event)
  {
    //FIX BUG 19996416: queue the item to be inserted before the 
    //masonryInsert event is received
    UIComponent source = (UIComponent)event.getSource();
    String symbol = (String)source.getAttributes().get("symbol");
    
    if (_pendingInserts == null)
      _pendingInserts = new ArrayList<String>();
    _pendingInserts.add(symbol);
  }
  
  public void handleBasicReorder(MasonryReorderEvent event)
  {
    _handleReorder(event, _basicData);
  }
  
  public void handleReorder(MasonryReorderEvent event)
  {
    _handleReorder(event, _data);
    //keep the ordered list of all data in sync with the rendered data
    _handleReorder(event, _orderData);
    
    RequestContext rc = RequestContext.getCurrentInstance();
    if (_insertPgl != null)
      rc.addPartialTarget(_insertPgl);
    //refresh the masonryLayout so that any setPropertyListeners referring to 
    //iterator var EL will be updated
    UIComponent source = event.getComponent();
    rc.addPartialTarget(source);
  }
  
  private void _handleReorder(MasonryReorderEvent event, List<Map<String, Object>> data)
  {
    int fromIndex = event.getFromIndex();
    int toIndex = event.getToIndex();
    
    //remove the item from the fromIndex
    int renderedCount = 0;
    Map<String, Object> reorderedItem = null;
    for (int i = 0; i < data.size(); i++)
    {
      Map<String, Object> item = data.get(i);
      Boolean boolObj = (Boolean)item.get("rendered");
      boolean bRendered = boolObj.booleanValue();
      if (bRendered)
      {
        if (renderedCount == fromIndex)
        {
          reorderedItem = item;
          data.remove(i);
          break;
        }
        else
        {
          renderedCount++;
        }
      }
    }
    
    //insert the item at the toIndex
    renderedCount = 0;
    for (int i = 0; i < data.size(); i++)
    {
      Map<String, Object> item = data.get(i);
      Boolean boolObj = (Boolean)item.get("rendered");
      boolean bRendered = boolObj.booleanValue();
      if (bRendered)
      {
        if (renderedCount == toIndex)
        {
          data.add(i, reorderedItem);
          break;
        }
        else
        {
          renderedCount++;
        }
      }
    }
  }
  
  public void handleResize(MasonryResizeEvent event)
  {
    String resizeId = event.getChildClientId();
    UIComponent source = (UIComponent)event.getSource();
    
    FacesContext facesContext = FacesContext.getCurrentInstance();
    VisitContext visitContext = VisitContext.createVisitContext(
      facesContext,
      Collections.singleton(resizeId), 
      EnumSet.of(VisitHint.SKIP_UNRENDERED));
    GetDataByClientIdCallback callback = new GetDataByClientIdCallback();
    source.visitTree(visitContext, callback);

    Map<String, Object> data = callback.getData();
    boolean bExpanded = ((Boolean)data.get("expanded")).booleanValue();
    data.put("expanded", !bExpanded);
    
    //keep the insert item data in sync with resizes
    String symbol = (String)data.get("symbol");
    Map<String, Object> insertItem = _getInsertItemForSymbol(symbol);
    insertItem.put("expanded", !bExpanded);
  }
  
  /**
   * Handle a drop on the masonryLayout.
   * @param dropEvent
   * @return The DnDAction performed or DnDAction.NONE, if drop is cancelled
   */
  public DnDAction handleDrop(DropEvent dropEvent)
  {
    Transferable dropTransferable = dropEvent.getTransferable();
    UIComponent movedComponent =  dropTransferable.getData(DataFlavor.UICOMPONENT_FLAVOR);

    if ((movedComponent != null) && DnDAction.MOVE.equals(dropEvent.getProposedAction()))
    {
      UIComponent dropComponent = dropEvent.getDropComponent();
      UIComponent movedParent = movedComponent.getParent();
      
      //only allow the drop if it's a reorder within the masonryLayout
      if (dropComponent == movedParent)
      {
        return DnDAction.MOVE;
      }
    }
    
    return DnDAction.NONE; 
  }
  
  private List<Map<String, Object>> _createData()
  {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    
    list.add(_createItem("H", "Hydrogen", "AFMasonryTileSize1x1", "AFMasonryTileSize3x1"));    
    list.add(_createItem("C", "Carbon", "AFMasonryTileSize1x1", "AFMasonryTileSize2x2"));    
    list.add(_createItem("N", "Nitrogen", "AFMasonryTileSize1x1", "AFMasonryTileSize2x1"));    
    list.add(_createItem("O", "Oxygen", "AFMasonryTileSize1x1", "AFMasonryTileSize2x2"));    
    list.add(_createItem("P", "Phosphorus", "AFMasonryTileSize1x1", "AFMasonryTileSize1x2"));    
    list.add(_createItem("S", "Sulphur", "AFMasonryTileSize1x1", "AFMasonryTileSize2x2"));    
    list.add(_createItem("He", "Helium", "AFMasonryTileSize1x1", "AFMasonryTileSize2x1"));    
    list.add(_createItem("Ne", "Neon", "AFMasonryTileSize1x1", "AFMasonryTileSize2x2"));    
    list.add(_createItem("Ar", "Argon", "AFMasonryTileSize1x1", "AFMasonryTileSize1x2"));    
    list.add(_createItem("Kr", "Krypton", "AFMasonryTileSize1x1", "AFMasonryTileSize2x2"));
    
    return list;
  }
  
  private Map<String, Object> _createItem(
    String symbol,
    String name,
    String sizeStyleClass,
    String expandedSizeStyleClass)
  {
    Map<String, Object> item = new HashMap<String, Object>();
    item.put("symbol", symbol);
    item.put("name", name);
    item.put("sizeStyleClass", sizeStyleClass);
    item.put("expandedSizeStyleClass", expandedSizeStyleClass);
    item.put("expanded", false);
    item.put("rendered", true);
    //assign a unique key so that each item will get a unique clientId in the iterator
    item.put("key", UUID.randomUUID().toString());
    return item;
  }
  
  private List<Map<String, Object>> _basicData;
  private List<Map<String, Object>> _data;
  private List<Map<String, Object>> _insertData;
  private List<Map<String, Object>> _orderData;
  private CollectionModel _basicDataModel;
  private CollectionModel _dataModel;
  private CollectionModel _insertDataModel;
  private RichPanelGroupLayout _insertPgl;
  private UIXIterator _insertIterator;
  private UIXIterator _masonryIterator;
  private List<String> _pendingInserts;
  private List<Map<String, Object>> _deletedItems;
  
  private class GetDataByClientIdCallback
    implements VisitCallback
  {
    private Map<String, Object> _childData;
    
    public Map<String, Object> getData()
    {
      return _childData;
    }

    public VisitResult visit(
      VisitContext visitContext,
      UIComponent  target)
    {
      _childData = (Map<String, Object>)_masonryIterator.getRowData();
      return VisitResult.COMPLETE;
    }
  }
}