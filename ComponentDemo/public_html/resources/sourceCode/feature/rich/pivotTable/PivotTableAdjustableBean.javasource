/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableAdjustableBean.java /main/8 2012/07/09 13:46:38 jievans Exp $ */

/* Copyright (c) 2008, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jievans     05/31/11 - Improved version of Adjustable PT demo
    jievans     10/22/10 - call handleShapeChange on every shape change
    ccchow      03/15/10 - add active cell key support
    teclee      09/18/08 - 
    dahmed      08/27/08 - 
    ahadi       06/12/08 - new PT edit model
    ahadi       06/12/08 - Creation
 */

package oracle.adfdemo.view.feature.rich.pivotTable;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;
import oracle.adf.view.faces.bi.model.CellKey;
import oracle.adf.view.faces.bi.model.DataModel;

import oracle.dss.util.DataDirector;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableAdjustableModel;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.fake.FakeDataSource;

// Commented out for dvt-adf integration work
//import oracle.dvtdemo.utils.tageditor.ComponentEditorHandler;

/**
 * The adjustable data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableAdjustableBean.java /main/8 2012/07/09 13:46:38 jievans Exp $
 *  @author  ahadi   
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableAdjustableBean {
    public PivotTableAdjustableBean() {
        _dataModel = new PivotTableAdjustableModel();
    }
    
    public DataModel getDataModel() {
        return _dataModel;
    }

    /**
     * Resets component properties that can become invalid upon changing the datasource.
     * Resets the startRow and StartColumn properties to null, and clears the selection.  
     * This should be called by handlers that change the data source, change the shape of the data, etc.
     */
    private void resetPivotTableState() {
        UIPivotTable pivotTable = getPivotTable();
        if (pivotTable != null) {
            pivotTable.setStartRow(0);
            pivotTable.setStartColumn(0);
            pivotTable.getSelection().clear();
        }
    }

    public void handleShapeChange(ActionEvent event) {
        resetPivotTableState();
    }

    public String getRowEdgeMemberCounts() {
        FakeDataSource fds = (FakeDataSource)(_dataModel.getDataSource());
        return fds.getMemberCountString(DataDirector.ROW_EDGE);
    }

    public void setRowEdgeMemberCounts(String memberCounts) {
        handleShapeChange(null);
        FakeDataSource fds = (FakeDataSource)(_dataModel.getDataSource());
        fds.setMemberCountString(DataDirector.ROW_EDGE, memberCounts);
    }

    public String getColumnEdgeMemberCounts() {
        FakeDataSource fds = (FakeDataSource)(_dataModel.getDataSource());
        return fds.getMemberCountString(DataDirector.COLUMN_EDGE);
    }

    public void setColumnEdgeMemberCounts(String memberCounts) {
        handleShapeChange(null);
        FakeDataSource fds = (FakeDataSource)(_dataModel.getDataSource());
        fds.setMemberCountString(DataDirector.COLUMN_EDGE, memberCounts);
    }
    
    public boolean getCoordinateMode() {
        FakeDataSource fds = (FakeDataSource)(_dataModel.getDataSource());
        return fds.getCoordinateMode();
    }

    public void setCoordinateMode(boolean mode) {
        // not needed for coord mode:  handleShapeChange(null);
        FakeDataSource fds = (FakeDataSource)(_dataModel.getDataSource());
        fds.setCoordinateMode(mode);
    }
    
    public CellKey getActiveCellKey()
    {
        return _activeCellKey;
    }
    
    public String getCellActiveState()
    {
        CellKey _cellKey = _pivotTable.getCellKey();
        CellKey _activeCellKey = _pivotTable.getActiveCellKey();
        if (_cellKey != null && _cellKey.equals(_activeCellKey))
            return "active";
        else
            return "inactive";        
    }
    
    // In demos where we are bound to the editor rather than the PT, the setter never gets called, so that 
    // the ivar is null.  In these cases we get the component a different way, which requires that the 
    // component id be "demo:goodPT".
    public UIPivotTable getPivotTable()
    {
        return (_pivotTable == null) 
               ? (UIPivotTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("demo:goodPT")
               : _pivotTable;
    }
    
    public void setPivotTable(UIPivotTable pivotTable)
    {
        _pivotTable = pivotTable;
    }
    
    PivotTableAdjustableModel _dataModel;
    UIPivotTable _pivotTable;
    CellKey _activeCellKey;
}
