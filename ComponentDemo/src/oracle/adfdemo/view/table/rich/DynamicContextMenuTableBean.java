package oracle.adfdemo.view.table.rich;


import java.util.Map;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.SortableModel;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class DynamicContextMenuTableBean
{
  public DynamicContextMenuTableBean()
  {
  }
  
  public CollectionModel getData()
  {
    return _data;
  }

  public void setCurrentRowData(TableTestData.FileData currentRowData)
  {
    this._currentRowData = currentRowData;
  }

  public TableTestData.FileData getCurrentRowData()
  {
    return _currentRowData;
  }

  public void setCurrentTreeRowData(Map currentTreeRowData)
  {
    _currentTreeRowData = currentTreeRowData;
  }

  public Map getCurrentTreeRowData()
  {
    return _currentTreeRowData;
  }
  
  public void alertTreeRowData(ActionEvent event)
  {
    FacesContext context =  FacesContext.getCurrentInstance();
    ExtendedRenderKitService erks =
      Service.getRenderKitService(context,
                                  ExtendedRenderKitService.class);
  
    StringBuilder builder = new StringBuilder();
    builder.append("alert(' Path is: ");
    String path = _currentTreeRowData.get("path").toString();
    builder.append(path.replace("\\","\\\\"));
    builder.append("')");
    erks.addScript(context, builder.toString());
  }
  
  public void alertRowData(ActionEvent event)
  {
    FacesContext context =  FacesContext.getCurrentInstance();
    ExtendedRenderKitService erks =
      Service.getRenderKitService(context,
                                  ExtendedRenderKitService.class);
  
    StringBuilder builder = new StringBuilder();
    builder.append("alert(' Row Number is: ");
    builder.append(_currentRowData.getNo());
    builder.append("')");
    erks.addScript(context, builder.toString());
  }
  
  private CollectionModel _data = new SortableModel(new TableTestData());
  private TableTestData.FileData _currentRowData;
  private Map _currentTreeRowData;
}
