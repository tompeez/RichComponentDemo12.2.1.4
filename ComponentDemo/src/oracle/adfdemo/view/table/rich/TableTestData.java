/** Copyright (c) 2008, 2018, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandToolbarButton;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;

import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.context.RequestContext;

/**
 * Table data for various table demo pages.
 */
public class TableTestData extends ArrayList<TableTestData.FileData>
{
  /**
   * Default constructor using a default repetition of record content.
   */
  public TableTestData()
  {
    this(200, false);
  }

  /**
   * Creates a TableTestData instance with a specified number of records.
   * @param count the number of times to repeat the record set or the exact number of records
   * @param exactRecordCount true if counting exact number of records or false for set repetition
   */
  public TableTestData(int count, boolean exactRecordCount)
  {
    super(exactRecordCount?count:_DIR_DATA.length*count);
    int numberOfRecords = count;
    if (!exactRecordCount)
    {
      numberOfRecords *= _DIR_DATA.length;
    }
    for (int i=0; i<numberOfRecords; i++)
    {
      Object data[] = _DIR_DATA[i%_DIR_DATA.length];
      this.add(
        new FileData(
          String.valueOf(i),
          (String)data[3],
          (String)data[0],
          (String)data[2],
          ((Boolean)data[1]).booleanValue()));
    }  
  }

  public void selectAllValueChanged(ValueChangeEvent event)
  {
    boolean selectAll = Boolean.TRUE.equals(event.getNewValue());
    for (int i=0; i<size(); i++)
    {
      FileData data = this.get(i);
      data.setSelectBooleanValue2(selectAll);
    }
    RequestContext.getCurrentInstance().addPartialTarget(event.getComponent().getParent().getParent());
  }

  public void selectBooleanCheckboxValueChanged(ValueChangeEvent event)
  {
    boolean selected = Boolean.TRUE.equals(event.getNewValue());
    if(!selected)
    {
      UIComponent col = event.getComponent().getParent();
      EditableValueHolder evh =  (EditableValueHolder)col.getFacet("header");
      evh.setValue(selected);
      RequestContext.getCurrentInstance().addPartialTarget(col.getParent());
    }
  }
  
  public void toggleClickToEdit(ActionEvent event)
  {
    UIComponent component = event.getComponent().getParent();
    
    while(component != null)
    {
      if(!(component instanceof RichPanelCollection))
        component = component.getParent();
      else
      {
        UIComponent table = component.getChildren().get(0);
        String editingModeStr = RichTable.EDITING_MODE_KEY.getName();
        boolean isClickToEdit = RichTable.EDITING_MODE_CLICK_TO_EDIT.equals(table.getAttributes().get(editingModeStr));
          
        table.getAttributes().put(editingModeStr, isClickToEdit?
                                                  RichTable.EDITING_MODE_EDIT_ALL:
                                                  RichTable.EDITING_MODE_CLICK_TO_EDIT);
        RichCommandMenuItem cmi = (RichCommandMenuItem) component.findComponent("eami");
        cmi.setSelected(isClickToEdit);

        RichCommandToolbarButton ctb = (RichCommandToolbarButton) component.findComponent("eactb");
        ctb.setSelected(isClickToEdit);

        RequestContext.getCurrentInstance().addPartialTarget(table);
        RequestContext.getCurrentInstance().addPartialTarget(cmi);
        RequestContext.getCurrentInstance().addPartialTarget(ctb);
        break;
      }
    }
  }
  
  public void makeReadOnly(ActionEvent event)
  {
    UIComponent component = event.getComponent().getParent();
    
    while(component != null)
    {
      if(!(component instanceof RichPanelGroupLayout))
        component = component.getParent();
      else
      {
        UIComponent table = component.getChildren().get(4);
        ((RichTable)table).setClickToEditRowReadOnly();
        break;
      }
    }
  }

  
  public static class FileData
  {
    private String name;
    private String date;
    private String size;
    private String _requiredField;
    private String _serverOnlyValidator = "server";
    private Integer _testSpinbox = 1979;
    private Integer _bigNumber = 1234567890;
    private String number;
    private boolean isDir;
    private String _selectOneVal = null;
    private String _selectOneVal2 = null;
    private List _selectManyVal1;
    private boolean _selectBooleanVal1 = false;
    private boolean _selectBooleanVal2 = false;
    private List _selectManyList1;
    private List _selectManyChoice1;
    private String reqField;
    
    FileData(String number, String name, String date, String size, boolean isDir)
    {
      this.name = name;
      this.date = date;
      this.isDir = isDir;
      this.size = size;
      this._requiredField = size;
      this.number = number;
    }

    public void buttonAction(ActionEvent event)
    {
      FacesContext context = FacesContext.getCurrentInstance();
      UIXTable table = (UIXTable)(((UIComponent)event.getSource()).getParent().getParent());
      FacesMessage message = new FacesMessage("Clicked on " + table.getRowKey());
      context.addMessage(null, message);
    }

    private static final String _CLICKED_ROW_KEY = "DEMO_RowKeyClicked";
    private static final String _CLICKED_TABLE_KEY = "DEMO_TableClicked";
    
    public void linkAction(ActionEvent event)
    {
      FacesContext context = FacesContext.getCurrentInstance();
      UIXTable table = (UIXTable)(((UIComponent)event.getSource()).getParent().getParent());
      
      Map<String, Object> requestMap = context.getExternalContext().getRequestMap();
      Object rowKey = table.getRowKey();
     
      requestMap.put(_CLICKED_ROW_KEY, rowKey);
      requestMap.put(_CLICKED_TABLE_KEY, table);
    }
    
    public String getMessage()
    {
      FacesContext context = FacesContext.getCurrentInstance();
      Map<String, Object> requestMap = context.getExternalContext().getRequestMap();

      UIXTable table = (UIXTable)requestMap.get(_CLICKED_TABLE_KEY);

      Object rowKey = requestMap.get(_CLICKED_ROW_KEY);
            
      if ((table != null) && table.getRowKey().equals(rowKey))
      {
        Date date = new java.util.Date();
        return "Clicked on " + rowKey + " - " + (new Timestamp(date.getTime()));
      }
      else
      {
        return "";
      }
    }

    public String getName()
    {
      return name;
    }
    
    public String getLongStringName()
    {
      return isDir ?
        (name + " is a directory. It starts with the letter " + name.charAt(0))
      : (name + " is a File. It starts with the letter " + name.charAt(0));
    }
    
    public boolean isDirectory()
    {
      return this.isDir;
    }

    public boolean getIsDirectory()
    {
      return isDir;    
    }
    
    public void setIsDirectory(boolean b)
    {
      isDir = b;    
    }
    
    public String getDate()
    {
      return date;
    }
    
    public String getLongStringDate()
    {
      return "This file was modified on "+ date + " which was a long while back";
    }
    
    public Integer getTestSpinbox()
    {
      return _testSpinbox;
    }
    
    public void setTestSpinbox(Integer testSpinbox)
    {
      _testSpinbox = testSpinbox;
    }
    
    public Date getInputDate()
    {
      Date inputDate = null;
      try
      {
        if (date == null)
          return null;
        
        inputDate = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse(date);
      }
      catch(ParseException e)
      {
        System.out.println("error parsing date string in table backing bean");
      }
      return inputDate;
    }
    
    public void setInputDate(Date date)
    {
      DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
      
      this.date = format.format(date);
    }    
    
    public String getSize()
    {
      return size;
    }

    public void setSize(String size)
    {
      this.size = size;
    }

    public String getRequiredField()
    {
      return _requiredField;
    }

    public void setRequiredField(String val)
    {
      _requiredField = val;
    }
    
    public String getNo()
    {
      return number;
    }
    
    public String getIcon()
    {
      if (isDir)
        return _FOLDER_IMAGE_URI;
      else
        return _FILE_IMAGE_URI;
    }

    public String getIconShortDesc()
    {
      if (isDir)
        return "directory";
      else
        return "file";
    }

    public String getNodeType()
    {
      if (isDir)
        return "directory";
      else
        return "file";
    }

    public String getSelectOneValue()
    {
      return (_selectOneVal != null)?_selectOneVal:"Mouse";
    }

    public String getSelectOneValue2()
    {
      return (_selectOneVal2 != null)?_selectOneVal2:"Mouse";
    }
    
    public void setSelectOneValue(String value)
    {
      _selectOneVal = value;
    }
    
    public void setSelectOneValue2(String value)
    {
      _selectOneVal2 = value;
    }
    
    public void selectOneValueChanged(ValueChangeEvent event)
    {
      FacesContext context = FacesContext.getCurrentInstance();
      FacesMessage message = new FacesMessage("New Value (Row " + this.number + "): " + event.getNewValue());
      context.addMessage(null, message);
    }
    
    public void selectManyValueChanged(ValueChangeEvent event)
    {
      FacesContext context = FacesContext.getCurrentInstance();
      FacesMessage message = new FacesMessage("New Value (Row " + this.number + "; Col9): " + event.getNewValue());
      context.addMessage(null, message);
    }
    
    public void setName(String name)
    {
      this.name = name;
    }
    
    public List getSelectManyValue1()
    {
      return _selectManyVal1;
    }

    public void setSelectManyValue1(List value)
    {
      _selectManyVal1 = value;
    }

    public boolean getSelectBooleanValue1()
    {
      return _selectBooleanVal1;
    }

    public void setSelectBooleanValue1(boolean value)
    {
      _selectBooleanVal1 = value;
    }

    public boolean getSelectBooleanValue2()
    {
      return _selectBooleanVal2;
    }

    public void setSelectBooleanValue2(boolean value)
    {
      _selectBooleanVal2 = value;
    }

    public String getCol5()
    {
      return name;
    }

    public void setCol5(String name)
    {
      this.name = name;
    }
  
    public String getCol6()
    {
      return date;
    }
    
    public void setCol6(String date)
    {
      this.date = date;
    }

    public String getCol7()
    {
      return size;
    }

    public String getCol8()
    {
      return number;
    }
    
    public String getCol9()
    {
      return name;
    }
  
    public String getCol10()
    {
      return date;
    }
    
    public String getCol11()
    {
      return size;
    }

    public String getCol12()
    {
      return number;
    }
    
    public String getCol13()
    {
      return name;
    }
  
    public String getCol14()
    {
      return date;
    }
    
    public String getCol15()
    {
      return size;
    }

    public String getCol16()
    {
      return number;
    }
    
    public String getCol17()
    {
      return name;
    }
  
    public String getCol18()
    {
      return date;
    }
    
    public String getCol19()
    {
      return size;
    }

    public String getCol20()
    {
      return number;
    }
    
    public String getCol21()
    {
      return name;
    }
  
    public String getCol22()
    {
      return date;
    }
    
    public String getCol23()
    {
      return size;
    }

    public String getCol24()
    {
      return number;
    }
    
    public String getCol25()
    {
      return name;
    }
  
    public String getCol26()
    {
      return date;
    }
    
    public String getCol27()
    {
      return size;
    }

    public String getCol28()
    {
      return number;
    }
    
    public String getCol29()
    {
      return name;
    }
  
    public String getCol30()
    {
      return date;
    }
    
    public String getCol31()
    {
      return size;
    }

    public String getCol32()
    {
      return number;
    }
    
    public String getCol33()
    {
      return name;
    }
  
    public String getCol34()
    {
      return date;
    }
    
    public String getCol35()
    {
      return size;
    }

    public String getCol36()
    {
      return number;
    }
    
    public String getCol37()
    {
      return name;
    }
  
    public String getCol38()
    {
      return date;
    }
    
    public String getCol39()
    {
      return size;
    }

    public String getCol40()
    {
      return number;
    }


    public void setSelectManyList1(List value)
    {
      _selectManyList1 = value;
    }

    public List getSelectManyList1()
    {
      return _selectManyList1;
    }

    public void setSelectManyChoice1(List value)
    {
      _selectManyChoice1 = value;
    }

    public List getSelectManyChoice1()
    {
      return _selectManyChoice1;
    }

    public void setReqField(String newreqField)
    {
      this.reqField = newreqField;
    }

    public String getReqField()
    {
      return reqField;
    }
    
    public String toString()
    {
      return "FileData [name=" + name + "]";
    }

    public void setServerOnlyValidator(String serverOnlyValidator)
    {
      this._serverOnlyValidator = serverOnlyValidator;
    }

    public String getServerOnlyValidator()
    {
      return _serverOnlyValidator;
    }

    public void setBigNumber(Integer bigNumber)
    {
      this._bigNumber = bigNumber;
    }

    public Integer getBigNumber()
    {
      return _bigNumber;
    }
  }

  private static final Object _DIR_DATA[][] = 
  {{"07/12/2004", Boolean.TRUE, "0 B", "."},
  {"07/12/2004", Boolean.TRUE, "0 B", ".."},
  {"05/11/2004", Boolean.FALSE, "1 KB", "admin.jar"},
  {"07/12/2004", Boolean.TRUE, "0 B", "applib"},
  {"07/12/2004", Boolean.TRUE, "0 B", "applications"},
  {"07/12/2004", Boolean.TRUE, "0 B", "config"},
  {"07/12/2004", Boolean.TRUE, "0 B", "connectors"},
  {"07/12/2004", Boolean.TRUE, "0 B", "database"},
  {"07/12/2004", Boolean.TRUE, "0 B", "default-web-app"},
  {"05/11/2004", Boolean.FALSE, "1,290 KB",  "iiop.jar"},
  {"05/11/2004", Boolean.FALSE, "37 KB", "iiop_gen_bin.jar"},
  {"05/11/2004", Boolean.FALSE, "144 KB", "iiop_rmic.jar"},
  {"07/12/2004", Boolean.TRUE, "0 B", "jazn"},
  {"05/11/2004", Boolean.FALSE, "266 KB", "jazn.jar"},
  {"05/11/2004", Boolean.FALSE, "553 KB", "jazncore.jar"},
  {"05/11/2004", Boolean.FALSE, "12 KB", "jaznplugin.jar"},
  {"07/12/2004", Boolean.TRUE, "0 B", "jsp"},
  {"07/12/2004", Boolean.TRUE, "0 B", "lib"},
  {"05/11/2004", Boolean.FALSE, "1 KB", "loadbalancer.jar"},
  {"07/12/2004", Boolean.TRUE, "0 B", "log"},
  {"05/11/2004", Boolean.FALSE, "5,696 KB", "oc4j.jar"},
  {"05/11/2004", Boolean.FALSE, "1,202 KB", "oc4jclient.jar"},
  {"05/11/2004", Boolean.FALSE, "4 KB", "oc4j_interop.jar"},
  {"05/11/2004", Boolean.FALSE, "1 KB", "ojspc.jar"},
  {"07/12/2004", Boolean.TRUE, "0 B", "persistence"},
  {"05/11/2004", Boolean.FALSE, "1 KB", "rmic.jar"},
  {"07/12/2004", Boolean.TRUE, "0 B", "sql"}};
  
  private static final Object _EMPTY_DIR_DATA[][] = {};

  private static final String _FILE_IMAGE_URI = "/images/file.gif";
  private static final String _FOLDER_IMAGE_URI = "/images/folder.gif";

}
