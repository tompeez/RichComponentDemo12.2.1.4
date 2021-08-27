/* Copyright (c) 2007, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hbroek      11/20/08 - New Pivottable samples
    hbroek      11/20/08 - First version
    hbroek      08/10/23 - 
 */
package oracle.adfdemo.view.feature.rich.pivotTable;

import javax.faces.convert.NumberConverter;

import oracle.adf.view.faces.bi.component.pivotTable.CellFormat;
import oracle.adf.view.faces.bi.component.pivotTable.DataCellContext;
import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;

import oracle.dss.util.DataException;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableSampleModel;

/**
 * The PivotTableSampleModel data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableDataBarsBean.java /main/3 2012/07/09 13:46:40 jievans Exp $
 *  @author  hbroek
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableDataBarsBean {

    public PivotTableDataBarsBean() {
        m_dm = new PivotTableSampleModel();
    }
        
    public PivotTableSampleModel getDataModel() {
        return m_dm;
    }
    
    public UIPivotTable getPivotTable() {
        return m_pivotTable;
    }
    
    public CellFormat getDataFormat(DataCellContext cxt) {
        CellFormat cellFormat = new SimpleCellFormat(cxt);
        return cellFormat;
    }

    private class SimpleCellFormat extends CellFormat {
         public SimpleCellFormat(DataCellContext dataCellContext){ 
             m_dataCellContext = dataCellContext;
         }
         
         public String getStyle() {
             return "text-align:left;vertical-align:middle;";
         }
         
         public String getTextStyle() {

             // get the min/max threshold based on the data being displayed by the cells
             double minValue = 0;
             double maxValue = 0;
             
             String _textStyle = "width:0px;height:0px;";

             try {
                 minValue = ((Number)m_dataCellContext.getValue("dataAttributeMinimum")).doubleValue();
                 maxValue = ((Number)m_dataCellContext.getValue("dataAttributeMaximum")).doubleValue();
             } catch (DataException e) {
                 e.printStackTrace();
             }
             Object _value = m_dataCellContext.getValue();
             if(_value instanceof Number)
             {
                 Number value = (Number)_value;                         
                 if (value != null) {
                     double range = maxValue - minValue;
                     double diffValue = value.doubleValue() - minValue;
                     double percentage = diffValue / range;
                     int width = new Double(percentage * CELL_WIDTH).intValue();
                     int margin = CELL_WIDTH - width;
                     _textStyle = "width:" + width + "px;height:12px;margin-right:" + margin + "px;";
                 }
             }
             return _textStyle;
         }
         private DataCellContext m_dataCellContext;
    }

    public static int CELL_WIDTH = 50;
    UIPivotTable m_pivotTable = null;
    PivotTableSampleModel m_dm = null;
    private NumberConverter m_converter = null;
}
