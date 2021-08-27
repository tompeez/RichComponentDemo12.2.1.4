package oracle.adfdemo.view.components.rich;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

public class DemoDdcBean
{
  private String[] _items = null;
  private String _parameter = "Hello World";
  private transient int _nameType = 0;
  private static final String[][] _NAMES = new String[][] { 
    { "One", "Two", "Three" },
    { "A", "B", "C" },
    { "I", "II", "III" }
  };
  private SelectItem[] _selectItems;
  
  public DemoDdcBean()
  {
    _selectItems = new SelectItem[] {
      new SelectItem(0, "Ordinal"),
      new SelectItem(1, "Letters"),
      new SelectItem(2, "Roman numerals")
    };
    
    _updateItems();
  }
  
  public void updateItemNames(ActionEvent event)
  {
    _updateItems();
  }

  public String[] getItems()
  {
    return _items;
  }

  public void setParameter(String _parameter)
  {
    this._parameter = _parameter;
  }

  public String getParameter()
  {
    return _parameter;
  }

  public void setNameType(int nameType)
  {
    if (nameType < 0 || nameType >= _NAMES.length)
    {
      throw new IllegalArgumentException();
    }
    this._nameType = nameType;
  }

  public int getNameType()
  {
    return _nameType;
  }

  public SelectItem[] getSelectItems()
  {
    return _selectItems;
  }
  
  private void _updateItems()
  {
    String[] names = _NAMES[_nameType];
    if (_items == null || _items.length != names.length)
    {
      _items = new String[names.length];
    }
    for (int i = 0; i < names.length; ++i)
    {
      _items[i] = String.format("Item %s", names[i]);
    }
  }
}
