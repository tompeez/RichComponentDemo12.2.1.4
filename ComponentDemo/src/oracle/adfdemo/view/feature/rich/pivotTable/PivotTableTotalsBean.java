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

import java.util.Enumeration;

import oracle.adf.view.faces.bi.component.pivotTable.CellFormat;
import oracle.adf.view.faces.bi.component.pivotTable.DataCellContext;
import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;

import oracle.dss.util.QDRMember;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableSampleModel;


/**
 * The PivotTableSampleModel data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableTotalsBean.java /main/3 2012/07/09 13:46:42 jievans Exp $
 *  @author  hbroek
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableTotalsBean {

    public PivotTableTotalsBean() {
        m_dm = new PivotTableSampleModel();
        
        // Enable totals
        m_dm.getRowsetDataSource().setTotalsEnabled(true);
    }
        
    public PivotTableSampleModel getDataModel() {
        return m_dm;
    }
    
    public UIPivotTable getPivotTable() {
        return m_pivotTable;
    }

    private class TotalsCellFormat extends CellFormat {
         public TotalsCellFormat(DataCellContext dataCellContext){ 
             _dataCellContext = dataCellContext;
         }
         
         public String getTextStyle() {
             int totalMemberCounter = 0;
             for (Enumeration en = _dataCellContext.getQDR().getDimensions();en.hasMoreElements();) {
                 if (_dataCellContext.getQDR().getDimMember((String)en.nextElement()).getType() == QDRMember.TOTAL)
                    totalMemberCounter++;
             }
             String _textStyle = "";
             switch (totalMemberCounter) {
                case 0:
                  _textStyle = "";
                break;
                case 1:
                 _textStyle = "font-weight: bold;";
                break;
                default: // More than 1 Member Total
                 _textStyle = "font-weight: bold; font-style: italic;";
                break;
             }
             
             return _textStyle;
         }
         DataCellContext _dataCellContext;
    }

    public CellFormat getDataFormat(DataCellContext cxt) {
        CellFormat cellFormat = new TotalsCellFormat(cxt);
        return cellFormat;
    }

    UIPivotTable m_pivotTable = null;
    PivotTableSampleModel m_dm = null;
}
