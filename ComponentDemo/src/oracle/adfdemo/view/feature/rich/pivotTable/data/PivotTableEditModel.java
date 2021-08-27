/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableEditModel.java /main/4 2012/07/09 13:46:39 jievans Exp $ */

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

package oracle.adfdemo.view.feature.rich.pivotTable.data;

import java.util.ArrayList;

/**
 * This data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableEditModel.java /main/4 2012/07/09 13:46:39 jievans Exp $
 *  @author  ahadi   
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableEditModel extends BaseRowsetModel {
  
  public PivotTableEditModel() {
  }

  protected ArrayList<String[]> getRowsetData() {
      return getRowsetDataFromString(PivotTableEditModel.SAMPLE_ROWSET);
  }

  private static final String SAMPLE_ROWSET = 
        "Time, Product, Channel, Geography, Sales, Units, Available, Price, Color, Weight, Link, Size, Supply Date\n" +
        "row, row, row, row, data, data, data, data, data, data, data, data, data\n" +
        "string, string, string, string, double, double, string, double, string, double, string, string, date\n" +
        "2007, Tents, All Channels, World, 20000, 200, true, 33, red, 33, Main-link, L, 01/01/2000\n" +
        "2007, Tents, All Channels, Boston, 500, 50, false, 66, coffee, 66, Main-link, S, 04/04/2004\n" +
        "2007, Jacket, All Channels, World, 40000, 4000, true, 11, red, 44, Main-link, L, 01/01/2000\n" +
        "2007, Jacket, All Channels, Boston, 700, 70, false, 13, coffee, 42, Main-link, S, 04/04/2004\n" +
        "2008, Tents, All Channels, World, 30000, 300, true, 36, red, 33, Main-link, L, 01/01/2000\n" +
        "2008, Tents, All Channels, Boston, 3000, 30, false, 69, coffee, 66, Main-link, S, 04/04/2004\n" +
        "2008, Jacket, All Channels, World, 10000, 1000, true, 12, red, 44, Sub-link, M, 03/03/2003\n" +
        "2008, Jacket, All Channels, Boston, 600, 60, false, 15, coffee, 42, Main-link, S, 04/04/2004";
}
