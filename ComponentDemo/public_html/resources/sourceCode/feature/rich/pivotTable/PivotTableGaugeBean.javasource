/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableGaugeBean.java /main/2 2012/07/09 13:46:41 jievans Exp $ */

/* Copyright (c) 2009, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hbroek      06/30/09 - Additional Shepherd samples
    hbroek      06/26/09 - Added PivotTable samples for Export and stamped with
                           Gauges and SparkCharts
    hbroek      06/26/09 - Creation
 */

package oracle.adfdemo.view.feature.rich.pivotTable;

import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableSampleModel;

/**
 * The PivotTableSampleModel data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableGaugeBean.java /main/2 2012/07/09 13:46:41 jievans Exp $
 *  @author  hbroek  
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableGaugeBean {

    public PivotTableGaugeBean() {
        m_dm = new PivotTableSampleModel();
    }
        
    public PivotTableSampleModel getDataModel() {
        return m_dm;
    }
    
    public UIPivotTable getPivotTable() {
        return m_pivotTable;
    }
    
    UIPivotTable m_pivotTable = null;
    PivotTableSampleModel m_dm = null;
}


