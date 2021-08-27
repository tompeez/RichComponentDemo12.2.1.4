/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/PivotFilterBarBean.java /main/4 2010/05/10 12:04:34 jayturne Exp $ */

/* Copyright (c) 2009, 2010, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ccchow      04/30/09 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/PivotFilterBarBean.java /main/4 2010/05/10 12:04:34 jayturne Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotFilterBar;

import oracle.adf.view.faces.bi.component.pivotTable.CellFormat;
import oracle.adf.view.faces.bi.component.pivotTable.DataCellContext;

import oracle.adf.view.faces.bi.model.PivotTableModel;
import oracle.adf.view.faces.bi.model.PivotableQueryDescriptor;

import oracle.adfdemo.view.feature.rich.pivotFilterBar.data.PivotFilterBarSampleQueryDescriptor;
import oracle.adfdemo.view.feature.rich.pivotTable.PivotTableDrillableBean;

public class PivotFilterBarBean extends PivotTableDrillableBean {
    
    private PivotFilterBarSampleQueryDescriptor _queryDescriptor;
    private boolean _dataAvailable;

    public PivotFilterBarBean() {
        super();
        PivotTableModel _model = getDataModel();
        this._dataAvailable = true;
        _queryDescriptor = new PivotFilterBarSampleQueryDescriptor(_model.getDataAccess(), _model.getDataDirector(), _dataAvailable);
    }
    
    public PivotableQueryDescriptor getQueryDescriptor() {
        return _queryDescriptor;
    }
    
    public void setDataAvailable(boolean dataAvailable) {
        this._dataAvailable = dataAvailable;
        this._queryDescriptor.setDataAvailable(this._dataAvailable);
    }
    
    public boolean getDataAvailable() {
        return this._dataAvailable;
    }

    public CellFormat getDataFormat(DataCellContext context) {
        if ("-".equals(context.getValue()))
        {
            CellFormat _format = new CellFormat();
            _format.setStyle("text-align:center");
            return _format;
        }
        else
            return null;
    }
    
    public String getCurrentTime() {
      return (new java.util.Date(System.currentTimeMillis())).toString();
    }
}
