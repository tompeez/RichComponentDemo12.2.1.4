package oracle.adfdemo.view.table.rich;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;

public class ClickToEditBean
{
  public ClickToEditBean()
  {
    super();
  }
  
  public void makeReadOnly(ActionEvent event)
  {
    if (_table != null)
    {
      _table.setClickToEditRowReadOnly();
    }
  }
  
  /**
   * This method forces the table on the page to be put into readOnly mode
   * when the page is rendered.
   */
  public String getDummyText()
  {
    if (_table != null)
    {
      _table.setClickToEditRowReadOnly();
    }
    return "table readOnly on render";
  }

  public void registerClick(ActionEvent event)
  {
    _clickCount++;
  }
  
  public String getClickCount()
  {
    return String.valueOf(_clickCount);
  }
  

  public void setTable(RichTable table)
  {
    this._table = table;
  }

  public RichTable getTable()
  {
    return _table;
  }
  
  public void setPopup(RichPopup popup)
  {
    _popup = popup;
  }

  public RichPopup getPopup()
  {
    return _popup;
  } 
  
  public String getName()
  {
    return _name;
  }
  
  public void setName(String name)
  {
    _name = name;
  }
  
  public String getNumber()
  {
    return _number;
  }
  
  public void setNumber(String number)
  {
    _number = number;
  }
  
  public void showPopup(ActionEvent event)
  {
    UIComponent source = (UIComponent) event.getSource();

    RichPopup.PopupHints hints = new RichPopup.PopupHints();
    /*
    hints.add(RichPopup.PopupHints.HintTypes.HINT_ALIGN_ID, source)
          .add(RichPopup.PopupHints.HintTypes.HINT_LAUNCH_ID, source)
          .add(RichPopup.PopupHints.HintTypes.HINT_ALIGN, RichPopup.PopupHints.AlignTypes.ALIGN_AFTER_END);
*/
    getPopup().show(hints);
  }
  
  private RichPopup _popup = null;
  private RichTable _table = null;
  private String    _name  = "";
  private String    _number = "";
  private int       _clickCount = 0;
}
