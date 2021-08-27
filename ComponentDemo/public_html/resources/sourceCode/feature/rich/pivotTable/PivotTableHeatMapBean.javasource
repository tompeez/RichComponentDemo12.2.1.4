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

import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;
import oracle.adf.view.faces.bi.component.pivotTable.CellFormat;
import oracle.adf.view.faces.bi.component.pivotTable.DataCellContext;

import oracle.dss.util.DataException;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableSampleModel;

/**
 * The PivotTableSampleModel data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableHeatMapBean.java /main/4 2012/07/09 13:46:41 jievans Exp $
 *  @author  hbroek
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableHeatMapBean {
             
    public PivotTableHeatMapBean() {
        m_dm = new PivotTableSampleModel();
    }
        
    public PivotTableSampleModel getDataModel() {
        return m_dm;
    }

    public UIPivotTable getPivotTable() {
        return m_pivotTable;
    }

    public void setPivotTable(UIPivotTable pivotTable) {
        m_pivotTable = pivotTable;
    }

    private class HeatMapCellFormat extends CellFormat {
         public HeatMapCellFormat(DataCellContext dataCellContext){ 
             _dataCellContext = dataCellContext;
         }
         
         public String getStyle() {
             return "background-color:" + getColor()+";";
         }

        /* Uncomment the method if you don't want the value to show 
         public String getTextStyle() {
             return "color:" + getColor() + ";";
         }*/

         private String getColor() {
             
             double minValue = 0;
             double maxValue = 0;

             try {
                 minValue = ((Number)_dataCellContext.getValue("dataAttributeMinimum")).doubleValue();
                 maxValue = ((Number)_dataCellContext.getValue("dataAttributeMaximum")).doubleValue();
             } catch (DataException e) {
                 e.printStackTrace();
             }
             String _color = "#EEEEEE";

             Object _value = _dataCellContext.getValue();
             if(_value instanceof Number) {
                 Number value = (Number)_value;                         
                 if (value != null) {
                     double range = maxValue - minValue;
                     double diffValue = value.doubleValue() - minValue;
                     double percentage = diffValue / range;
                     
                     int numColors = GREEN_YELLOW_RED_COLOR_RAMP.length;
                     double colorIndex = numColors*percentage;
                     int _colorIndex = (int)Math.round(colorIndex)-1;
                     if(_colorIndex<0)
                         _colorIndex=0;
                     else if(_colorIndex>numColors-1)
                         _colorIndex = numColors-1;
    
                     _color = GREEN_YELLOW_RED_COLOR_RAMP[_colorIndex];
                 }
             }
             return _color;
         }
         private DataCellContext _dataCellContext;
         protected final String[] GREEN_YELLOW_RED_COLOR_RAMP = new String[]{
                        "#f8706c",
                        "#f8776d",
                        "#f97f6f",
                        "#f98670",
                        "#fa8e72",
                        "#fa9573",
                        "#fa9d75",
                        "#fba476",
                        "#fbab77",
                        "#fcb379",
                        "#fcba7a",
                        "#fcc27c",
                        "#fdc97d",
                        "#fdd17f",
                        "#fed880",
                        "#fedf81",
                        "#fee783",
                        "#fbea84",
                        "#f2e884",
                        "#e9e583",
                        "#e9e583",
                        "#d7e082",
                        "#cedd82",
                        "#c6db81",
                        "#bdd881",
                        "#b4d680",
                        "#abd380",
                        "#a2d07f",
                        "#99ce7f",
                        "#90cb7e",
                        "#87c97e",
                        "#7ec67d",
                        "#75c47d",
                        "#6cc17c",
                        "#6cc17c",
             };
    }
    
    public CellFormat getDataFormat(DataCellContext cxt) {
        CellFormat cellFormat = new HeatMapCellFormat(cxt);
        return cellFormat;
    }

    UIPivotTable m_pivotTable = null;
    PivotTableSampleModel m_dm = null;
}
