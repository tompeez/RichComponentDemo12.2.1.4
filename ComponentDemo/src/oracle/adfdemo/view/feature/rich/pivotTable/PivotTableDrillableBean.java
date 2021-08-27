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

import oracle.adf.view.faces.bi.model.PivotTableModel;

import oracle.dss.util.DataSource;

import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.AsymmetricDrillablePagingDataSource;

/**
 * The AsymmetricDrillablePagingDataSource data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableDrillableBean.java /main/4 2012/07/09 13:46:40 jievans Exp $
 *  @author  hbroek
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableDrillableBean {

    public PivotTableDrillableBean() {
        m_dm = new PivotTableDrillableModel();
    }
        
    public PivotTableModel getDataModel() {
        return m_dm;
    }
    
    /**
     * The AsymmetricDrillablePagingDataSource data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
     * and is not necessarily scalable or performant.  
     */
    private class PivotTableDrillableModel extends PivotTableModel {
          public PivotTableDrillableModel() {
//              m_ds = new DrillablePagingDataSource();
              m_ds = new AsymmetricDrillablePagingDataSource();
              setDataSource(m_ds);
          }
          
          DataSource m_ds = null;
    }
    
    PivotTableModel m_dm = null;
}
