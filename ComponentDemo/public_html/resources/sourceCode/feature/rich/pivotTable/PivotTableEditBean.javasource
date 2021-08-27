/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableEditBean.java /main/7 2014/10/16 10:23:35 jayturne Exp $ */

/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jievans     04/01/13 - Fix bug 16568977 - NPE when pivot Measure Dim off of column edge
    dahmed      08/27/08 - 
    ahadi       06/12/08 - new PT edit model
    ahadi       06/12/08 - Creation
 */

package oracle.adfdemo.view.feature.rich.pivotTable;

import java.util.ArrayList;

import oracle.adf.view.faces.bi.component.pivotTable.SizingManager;
import oracle.adf.view.faces.bi.component.pivotTable.SizingManagerImpl;
import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;
import oracle.adf.view.faces.bi.model.DataCellIndex;
import oracle.adf.view.faces.bi.model.DataModel;

import oracle.adf.view.rich.event.ReturnPopupEvent;
import oracle.adf.view.rich.model.ListOfValuesModel;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableComboboxLOVModel;
import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableEditModel;

import oracle.dss.util.DataMap;
import oracle.dss.util.QDR;
import oracle.dss.util.QDRMember;

/**
 * PivotTableEditModel and PivotTableComboboxLOVModel are intended for demo, non-production code only.  They were created solely to facilitate the UI demos, 
 * and are not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableEditBean.java /main/7 2014/10/16 10:23:35 jayturne Exp $
 *  @author  ahadi   
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableEditBean {
    public PivotTableEditBean() {
        _dataModel = new PivotTableEditModel();
        _lovModel = new PivotTableComboboxLOVModel();
        _sizingManager = new CustomSizingManager();
    }
    
    public ListOfValuesModel getListOfValuesModel(){
        return _lovModel.getListOfValuesModel();
    }
    
    public DataModel getDataModel() {
        return _dataModel;
    }
    
    public SizingManager getSizingManager() {
        return _sizingManager;
    }

    public void handlePopupReturn(ReturnPopupEvent event)
    {
        ArrayList _values = (ArrayList)event.getReturnValue();
        PivotTableComboboxLOVModel.FileData _value = (PivotTableComboboxLOVModel.FileData)_values.get(0);
        UIPivotTable _pt = (UIPivotTable)event.getComponent().getParent().getParent().getParent();
        DataCellIndex _index = (DataCellIndex)_pt.getCellIndex();
        try
        {
            _dataModel.getDataAccess().setValue(_value.getCode(), _index.getRow(), _index.getColumn(), DataMap.DATA_VALUE);
        }
        catch (Exception ex){}
    }

    //////////////////////////////////////////////////////////////////////////
    //PivotTableEditBean class attributes    
    PivotTableEditModel _dataModel; // data model
    PivotTableComboboxLOVModel _lovModel; // combobox lov model        
    CustomSizingManager _sizingManager;

    private static class CustomSizingManager extends SizingManagerImpl 
    {
        @Override
        public int computeColumnWidth(QDR sliceQDR) {                
            // make the size column wider
            QDRMember _member = sliceQDR.getDimMember("MeasDim");
            if (_member != null && "Size".equals(_member.getData()))
                return 80;
            else if (_member != null && "Sales".equals(_member.getData()))
                return 75;
            else if (_member != null && "Units".equals(_member.getData()))
                return 60;
            else if (_member != null && "Available".equals(_member.getData()))
                return 115;
            else if (_member != null && "Color".equals(_member.getData()))
                return 80;
            else if (_member != null && "Supply Date".equals(_member.getData()))
                return 110;
            else
                return super.computeColumnWidth(sliceQDR);
        }
    }
}


