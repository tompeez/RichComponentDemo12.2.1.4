package oracle.adfdemo.view.table.rich;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.SortableModel;

public class TableClickToEditSwitcherBean
{
  public TableClickToEditSwitcherBean()
  {
    super();
  }
  
  public void setTable(UIXTable table)
  {
    _table = table;
  }

  public UIXTable getTable()
  {
    return _table;
  }


  public static class TableRow
  {

    public TableRow(int number)
    {
      this._number = number;
    }

    public String getSelectOneValue()
    {
      return _selectOneVal;
    }

    public void setSelectOneValue(String value)
    {
      _selectOneVal = value;
    }

    public int getNumber()
    {
      return _number;
    }

    public String getName()
    {
      return getSelectOneValue();
    }    

    public String getIcon()
    {
      return _FOLDER_IMAGE_URI;
    }    
    
    public String getFileIcon()
    {
      return _FILE_IMAGE_URI;
    }    
    
    public String getGuyIcon()
    {
      return _GUY_IMAGE_URI;
    }    
    
    private String _selectOneVal = "Mouse";
    private int _number;
    private static final String _FOLDER_IMAGE_URI = "/images/folder.gif";
    private static final String _FILE_IMAGE_URI = "/images/file.gif";
    private static final String _GUY_IMAGE_URI = "/images/guy.gif";

  }


  public CollectionModel getModel()
  {
    if (_model == null)
    {
      List<TableRow> data = new ArrayList<TableRow> (20);
      
      for (int r = 0; r < 10; r++)
      {
        data.add(new TableRow(r));
      }
      
      _model = new SortableModel(data);
    }
    
    return _model;
  }
  private UIXTable _table;
  private CollectionModel _model;
}
