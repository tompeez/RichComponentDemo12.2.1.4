/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableAdjustableModel.java /main/4 2012/07/09 13:46:38 jievans Exp $ */

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
    dahmed      08/27/08 - 
    ahadi       06/12/08 - new PT edit model
    ahadi       06/12/08 - Creation
 */

/**
 * The adjustable data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableAdjustableModel.java /main/4 2012/07/09 13:46:38 jievans Exp $
 *  @author  ahadi   
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.pivotTable.data;

import oracle.adf.view.faces.bi.model.PivotTableModel;

import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.fake.FakeDataSource;

public class PivotTableAdjustableModel extends PivotTableModel{
  public PivotTableAdjustableModel() {
      m_ds = new FakeDataSource();
      setDataSource(m_ds);
  }
  
  FakeDataSource m_ds = null;
}
