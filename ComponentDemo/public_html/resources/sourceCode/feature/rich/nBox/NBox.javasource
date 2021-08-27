/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/nBox/NBox.java /bibeans_root/5 2016/07/29 11:46:10 breliu Exp $ */

/* Copyright (c) 2014, 2016, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jramanat    04/04/14 - Creation
 */

package oracle.adfdemo.view.feature.rich.nBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import oracle.adf.view.faces.bi.component.nBox.UINBox;

import oracle.adfdemo.view.feature.rich.nBox.data.TriangleData;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/nBox/NBox.java /bibeans_root/5 2016/07/29 11:46:10 breliu Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class NBox {
  private UINBox nBox;
  private CollectionModel model;
  private CollectionModel rowModel;
  private CollectionModel columnModel;
  private CollectionModel cellModel;
  private int nodes = 50;
  private String dataChange = "none";
  private String rowsTitle = null;
  private String columnsTitle = null;
  private boolean rowLabels = false;
  private boolean columnLabels = false;
  private boolean cellLabels = false;
  private String cellShowCount = "auto";
  private boolean customCountLabels = false;
  private String cellBackground = "default";
  private String nodeLabel = "dual";
  private String nodeIcon = "photo";
  private String selection = "multiple";
  private String cellContent = "auto";
  private String cellMaximize = "on";
  private String labelTruncation = "on";

  public void setNBox(UINBox nBox) {
    this.nBox = nBox;
  }

  public UINBox getNBox() {
    return nBox;
  }

  public void setNodes(int nodes) {
    this.nodes = nodes;
    model = null;
    cellModel = null;
  }

  public int getNodes() {
    return nodes;
  }

  public void setRows(int rows) {
    this.rows = Math.max(1, rows);
    model = null;
    rowModel = null;
    cellModel = null;
  }

  public int getRows() {
    return rows;
  }

  public void setColumns(int columns) {
    this.columns = Math.max(1, columns);
    model = null;
    columnModel = null;
    cellModel = null;
  }

  public int getColumns() {
    return columns;
  }
  private int rows = 3;
  private int columns = 3;
  
  public CollectionModel getModel() {
    createModels();
    return model;
  }
  
  public CollectionModel getRowModel() {
    createModels();
    return rowModel;
  }

  public CollectionModel getColumnModel() {
    createModels();
    return columnModel;
  }

  public CollectionModel getCellModel() {
    createModels();
    return cellModel;
  }

  public void setDataChange(String dataChange) {
    this.dataChange = dataChange;
  }

  public String getDataChange() {
    return dataChange;
  }

  public void setRowsTitle(String rowsTitle) {
    this.rowsTitle = rowsTitle;
  }

  public String getRowsTitle() {
    return rowsTitle;
  }

  public void setColumnsTitle(String columnsTitle) {
    this.columnsTitle = columnsTitle;
  }

  public String getColumnsTitle() {
    return columnsTitle;
  }

  public void setRowLabels(boolean rowLabels) {
    this.rowLabels = rowLabels;
  }

  public boolean isRowLabels() {
    return rowLabels;
  }

  public void setColumnLabels(boolean columnLabels) {
    this.columnLabels = columnLabels;
  }

  public boolean isColumnLabels() {
    return columnLabels;
  }

  public void setCellLabels(boolean cellLabels) {
    this.cellLabels = cellLabels;
  }

  public boolean isCellLabels() {
    return cellLabels;
  }
  
  public void setCellShowCount(String cellShowCount) {
    this.cellShowCount = cellShowCount;
  }

  public String getCellShowCount() {
    return cellShowCount;
  }
  
  public void setCustomCountLabels(boolean customCountLabels) {
    this.customCountLabels = customCountLabels;
  }

  public boolean isCustomCountLabels() {
    return customCountLabels;
  }

  public void setCellBackground(String cellBackground) {
    this.cellBackground = cellBackground;
  }

  public String getCellBackground() {
    return cellBackground;
  }

  public void setNodeLabel(String nodeLabel) {
    this.nodeLabel = nodeLabel;
  }

  public String getNodeLabel() {
    return nodeLabel;
  }

  public void setNodeIcon(String nodeIcon) {
    this.nodeIcon = nodeIcon;
  }

  public String getNodeIcon() {
    return nodeIcon;
  }

  public void setSelection(String selection) {
    this.selection = selection;
    nBox.setSelectedRowKeys(null);
  }

  public String getSelection() {
    return selection;
  }
  
  public void setCellContent(String cellContent) {
    this.cellContent = cellContent;
  }

  public String getCellContent() {
    return cellContent;
  }
  
  public void setCellMaximize(String cellMaximize) {
    this.cellMaximize = cellMaximize;
  }

  public String getCellMaximize() {
    return cellMaximize;
  }
  
  public void setLabelTruncation(String labelTruncation) {
    this.labelTruncation = labelTruncation;
  }

  public String getLabelTruncation() {
    return labelTruncation;
  }

  public String getSelectionState() {
    RowKeySet rowKeySet = nBox.getSelectedRowKeys();
    StringBuilder s = new StringBuilder();
    if (rowKeySet != null && !selection.equals("none")) {
      for (Object rowKey : rowKeySet) {
        s.append(((Map<String, Object>)nBox.getRowData(rowKey)).get("name")).append(' ');
      }
    }
    if (s.length() > 0) {
      s.setLength(s.length() - 1);
    }
    return s.toString();
  }

  private void createModels() {
    if (rowModel == null) {
      List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
      for (int i = 0; i < rows; i++) {
        rowList.add(createMap("value", i, "label", "Row " + i));
      }
      rowModel = ModelUtils.toCollectionModel(rowList);
    }
    if (columnModel == null) {
      List<Map<String, Object>> columnList = new ArrayList<Map<String, Object>>();
      for (int i = 0; i < columns; i++) {
        columnList.add(createMap("value", i, "label", "Column " + i));
      }
      columnModel = ModelUtils.toCollectionModel(columnList);
    }
    List<TriangleData.Node> nodeData = TriangleData.getData(nodes, rows, columns);
    if (model == null) {
      model = ModelUtils.toCollectionModel(nodeData);
    }    
    if (cellModel == null) {
      List<Map<String, Object>> cellList = new ArrayList<Map<String, Object>>();
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < columns; c++) {
          String background1;
          String background2;
          int[] ired = {234, 153, 153};
          int[] igreen = {147, 196, 125};
          int[] iyellow = {255, 229, 153};
          int[] iblue = {159, 197, 248};
          float w1 = 2;
          float w2 = .75f;
          String red1 = averageColor(ired, 221, w1);
          String green1 = averageColor(igreen, 221, w1);
          String yellow1 = averageColor(iyellow, 221, w1);
          String blue1 = averageColor(iblue, 221, w1);
          String red2 = averageColor(ired, 221, w2);
          String green2 = averageColor(igreen, 221, w2);
          String yellow2 = averageColor(iyellow, 221, w2);
          String blue2 = averageColor(iblue, 221, w2);
          double oneThird = 1.0 / 3;
          double twoThirds = 2.0 / 3;
          if (c < oneThird * columns) {
            background1 = r < twoThirds * rows ? red1 : blue1;
            background2 = r < twoThirds * rows ? red2 : blue2;
          } 
          else if (c < twoThirds * columns) {
            background1 = r < twoThirds * rows ? yellow1 : green1;
            background2 = r < twoThirds * rows ? yellow2 : green2;
          } 
          else {
            background1 = r < oneThird * rows ? yellow1 : green1;
            background2 = r < oneThird * rows ? yellow2 : green2;
          }     
          
          int nodeCount = 0;
          for (TriangleData.Node node : nodeData) {
            if (node.get("row").equals(r) && node.get("column").equals(c)) {
              nodeCount += 1;
            }
          }
          String countLabel = nodeCount + " (" + Math.round(100.0 * nodeCount / nodes) + "%)";
          cellList.add(createMap("row", r, "column", c, "label", "Cell (" + r + "," + c + ")", "countLabel", countLabel, "color1", background1, "color2", background2, "gradient", "linear-gradient(90deg, " + background1 + " 10%, " + background2 + " 100%)"));
        }
      }
      cellModel = ModelUtils.toCollectionModel(cellList);
    }
  }

  private String averageColor(int[] color, int base, float weight) {
    int[] newColor = new int[color.length];
    for (int i = 0; i < color.length; i++) {
      newColor[i] = (int)((color[i] + base*weight)/(weight + 1));
    }
    return "rgb(" + newColor[0] + "," + newColor[1] + "," + newColor[2] + ")";
  }

  private Map<String, Object> createMap(Object... values) {
    Map<String, Object> map = new HashMap<String, Object>();
    for (int i = 0; i < values.length; i+= 2) {
      map.put(values[i].toString(), values[i+1]);
    }
    return map;
  }
}
