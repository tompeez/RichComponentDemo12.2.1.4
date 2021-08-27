/* Copyright (c) 2009, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.table.rich;

import java.util.List;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.adf.view.rich.component.rich.output.RichOutputText;


/**
 * Test bean for the varying column stretching use cases.
 */
public class TableColumnStretchingBean
{
  /**
   * Default constructor.
   */
  public TableColumnStretchingBean()
  {
  }

  public RichTable getTable()
  {
    return _table;
  }

  public void setTable(RichTable table)
  {
    _table = table;
  }

  public String getColumnHeaderTextNumber()
  {
    if (_columnHeaderPresent)
    {
      return "Number";
    }
    return null;
  }

  public String getColumnHeaderTextName()
  {
    if (_columnHeaderPresent)
    {
      return "Name";
    }
    return null;
  }

  public String getColumnHeaderTextSize()
  {
    if (_columnHeaderPresent)
    {
      return "Size";
    }
    return null;
  }

  public String getColumnHeaderTextDateModified()
  {
    if (_columnHeaderPresent)
    {
      return "Date Modified";
    }
    return null;
  }

  public String getColumnHeaderText5And6()
  {
    if (_columnHeaderPresent)
    {
      return "Cols 5 and 6";
    }
    return null;
  }

  public String getColumnHeaderText5()
  {
    if (_columnHeaderPresent)
    {
      return "Col 5";
    }
    return null;
  }

  public String getColumnHeaderText6()
  {
    if (_columnHeaderPresent)
    {
      return "Col 6";
    }
    return null;
  }

  public String getColumnHeaderText7()
  {
    if (_columnHeaderPresent)
    {
      return "Col 7";
    }
    return null;
  }

  public String getColumnHeaderText8()
  {
    if (_columnHeaderPresent)
    {
      return "Col 8";
    }
    return null;
  }

  public String getColumnHeaderText9And10()
  {
    if (_columnHeaderPresent)
    {
      return "Cols 9 and 10";
    }
    return null;
  }

  public String getColumnHeaderText9()
  {
    if (_columnHeaderPresent)
    {
      return "Col 9";
    }
    return null;
  }

  public String getColumnHeaderText10()
  {
    if (_columnHeaderPresent)
    {
      return "Col 10";
    }
    return null;
  }

  public String getColumnWidthRowHeader()
  {
    if ("all".equals(_columnStretching))
    {
      return "1*";
    }
    return "30";
  }

  public String getColumnWidthRowNumberPlain()
  {
    if ("all".equals(_columnStretching))
    {
      return "1*";
    }
    return "30";
  }

  public String getColumnWidthName()
  {
    if ("name".equals(_columnStretching) ||
        "nameAnd9".equals(_columnStretching) ||
        "nameAndSizeAnd9".equals(_columnStretching) ||
        "all".equals(_columnStretching))
    {
      return "3*";
    }
    return "110";
  }

  public String getColumnWidthSize()
  {
    if ("nameAndSizeAnd9".equals(_columnStretching) ||
        "all".equals(_columnStretching))
    {
      return "1*";
    }
    return "55";
  }

  public String getColumnWidthDateModified()
  {
    if ("all".equals(_columnStretching))
    {
      return "1*";
    }
    return "80";
  }

  public String getColumnWidth5()
  {
    if ("all".equals(_columnStretching))
    {
      return "1*";
    }
    return "90";
  }

  public String getColumnWidth6()
  {
    if ("all".equals(_columnStretching))
    {
      return "1*";
    }
    return "65";
  }

  public String getColumnWidth7()
  {
    if ("all".equals(_columnStretching))
    {
      return "1*";
    }
    return "55";
  }

  public String getColumnWidth8()
  {
    if ("all".equals(_columnStretching))
    {
      return "1*";
    }
    return "30";
  }

  public String getColumnWidth9()
  {
    if ("9".equals(_columnStretching) ||
        "nameAnd9".equals(_columnStretching) ||
        "nameAndSizeAnd9".equals(_columnStretching) ||
        "all".equals(_columnStretching))
    {
      return "1*";
    }
    return "90";
  }

  public String getColumnWidth10()
  {
    if ("all".equals(_columnStretching))
    {
      return "1*";
    }
    return "65";
  }

  public String getEmptyText()
  {
    if (_emptyTextPresent)
    {
      return "There is no data to show here.  Move along, nothing to see here.  Thank you.  Really, there is not any data to be displayed here since this table has no records that you can see.  Well the real reason why this empty text is super long is to make sure that it will wrap onto multiple lines if needed.";
    }
    return null;
  }

  public List getTableData()
  {
    if ("none".equals(_dataPresent))
    {
      return _nonPresentData;
    }
    else if ("some".equals(_dataPresent))
    {
      return _someData;
    }
    return _lotsOfData;
  }

  public void setEmptyTextPresent(boolean emptyTextPresent)
  {
    this._emptyTextPresent = emptyTextPresent;
  }

  public boolean isEmptyTextPresent()
  {
    return _emptyTextPresent;
  }

  public void setColumnHeaderPresent(boolean columnHeaderPresent)
  {
    this._columnHeaderPresent = columnHeaderPresent;
  }

  public boolean isColumnHeaderPresent()
  {
    return _columnHeaderPresent;
  }

  public void setColumnFooterPresent(boolean _columnFooterPresent)
  {
    this._columnFooterPresent = _columnFooterPresent;
  }

  public boolean isColumnFooterPresent()
  {
    return _columnFooterPresent;
  }


  public void setNestedColumnHeaderPresent(boolean nestedColumnHeaderPresent)
  {
    this._nestedColumnHeaderPresent = nestedColumnHeaderPresent;
  }

  public boolean isNestedColumnHeaderPresent()
  {
    return _nestedColumnHeaderPresent;
  }

  public void setRowHeaderPresent(boolean rowHeaderPresent)
  {
    this._rowHeaderPresent = rowHeaderPresent;
  }

  public boolean isRowHeaderPresent()
  {
    return _rowHeaderPresent;
  }

  public void setDetailStampPresent(boolean detailStampPresent)
  {
    this._detailStampPresent = detailStampPresent;
  }

  public boolean isDetailStampPresent()
  {
    return _detailStampPresent;
  }

  public void detailStampChanged(ValueChangeEvent e)
  {
    boolean oldDetailStampPresent = ((Boolean)e.getOldValue()).booleanValue();
    boolean newDetailStampPresent = ((Boolean)e.getNewValue()).booleanValue();
    if (oldDetailStampPresent != newDetailStampPresent)
    {
      // The preference changed:
      if (newDetailStampPresent)
      {
        // Add the detailStamp facet:
        RichPanelGroupLayout layout = new RichPanelGroupLayout();
        layout.setLayout(RichPanelGroupLayout.LAYOUT_VERTICAL);
        RichOutputText text = new RichOutputText();
        text.setValue("Some detail text");
        text.setParent(layout);
        Map<String,UIComponent> facets = _table.getFacets();
        facets.put(RichTable.DETAIL_STAMP_FACET, layout);
      }
      else
      {
        // Remove the detailStamp facet:
        Map<String,UIComponent> facets = _table.getFacets();
        facets.remove(RichTable.DETAIL_STAMP_FACET);
      }
    }
  }

  public void setDataPresent(String dataPresent)
  {
    this._dataPresent = dataPresent;
  }

  public String getDataPresent()
  {
    if (_dataPresent == null)
      return "lots";
    return _dataPresent;
  }

  public void setContentDelivery(String contentDelivery)
  {
    this._contentDelivery = contentDelivery;
  }

  public String getContentDelivery()
  {
    if (_contentDelivery == null)
      return "lazy";
    return _contentDelivery;
  }

  public void setColumnStretching(String columnStretching)
  {
    this._columnStretching = columnStretching;
  }

  public String getColumnStretching()
  {
    if (_columnStretching == null)
      return "none";
    return _columnStretching;
  }

  public void setFrozenColumn(String frozenColumn)
  {
    this._frozenColumn = frozenColumn;
  }

  public String getFrozenColumn()
  {
    if (_frozenColumn == null)
      return "none";
    return _frozenColumn;
  }

  private RichTable _table = null;
  private List _lotsOfData = new TableTestData();
  private List _someData = new TableTestData(11, true);
  private List _nonPresentData = new TableTestData(0, true);
  private boolean _emptyTextPresent = true;
  private boolean _columnHeaderPresent = true;
  private boolean _columnFooterPresent = true;
  private boolean _nestedColumnHeaderPresent = true;
  private boolean _rowHeaderPresent = true;
  private boolean _detailStampPresent = false;
  private String _dataPresent;
  private String _contentDelivery;
  private String _columnStretching;
  private String _frozenColumn;

}
