/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableBean.java /main/6 2012/07/09 13:46:42 jievans Exp $ */

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
    ccchow      04/06/10 - add client behavior test methods
    jievans     11/30/09 - add checkbox to whenAvailable demo
    dahmed      08/27/08 - 
    ahadi       06/12/08 - new PT edit model
    ahadi       06/12/08 - Creation
 */

package oracle.adfdemo.view.feature.rich.pivotTable;

import oracle.adf.view.faces.bi.model.DataModel;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableSampleModel;

/**
 * The PivotTableSampleModel data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableBean.java /main/6 2012/07/09 13:46:42 jievans Exp $
 *  @author  ahadi   
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableBean {
    public PivotTableBean() {
        _dataModel = new PivotTableSampleModel();
    }
    
    public DataModel getDataModel() {
        return _dataModel;
    }
    
    public boolean isDataFetched() {
        return _dataModel.isFetched();
    }
    
    public void setDataFetched(boolean fetched) {
        _dataModel.setFetched(fetched);
    }

    PivotTableSampleModel _dataModel; // data model
}


