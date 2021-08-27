/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableTrendModel.java /main/2 2012/07/09 13:46:42 jievans Exp $ */

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
    hbroek      06/29/09 - Creation
 */

package oracle.adfdemo.view.feature.rich.pivotTable.data;

import java.util.ArrayList;

/**
 * This data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableTrendModel.java /main/2 2012/07/09 13:46:42 jievans Exp $
 *  @author  hbroek  
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableTrendModel extends BaseRowsetModel {
  
  public PivotTableTrendModel() {
  }

  protected ArrayList<String[]> getRowsetData() {
      return getRowsetDataFromString(PivotTableTrendModel.SAMPLE_ROWSET);
  }

  private static final String SAMPLE_ROWSET = 
        "Channel, Time, Product, Geography, Sales, Units\n" +
        "column, column, row, row, data, data\n" +
        "string, string, string, string, double, double\n" +
        "none, sum, none, none, none, none\n" +
        "All Channels, 2005, Tents, World, 5000, 50\n" +
        "All Channels, 2005, Tents, Boston, 125, 15\n" +
        "All Channels, 2005, Canoes, World, 3750, 20\n" +
        "All Channels, 2005, Canoes, Boston, 375, 2\n" +
        "All Channels, 2006, Tents, World, 10000, 100\n" +
        "All Channels, 2006, Tents, Boston, 250, 25\n" +
        "All Channels, 2006, Canoes, World, 7500, 40\n" +
        "All Channels, 2006, Canoes, Boston, 750, 4\n" +
        "All Channels, 2007, Tents, World, 20000, 200\n" +
        "All Channels, 2007, Tents, Boston, 500, 50\n" +
        "All Channels, 2007, Canoes, World, 15000, 75\n" +
        "All Channels, 2007, Canoes, Boston, 1500, 8\n" +
        "All Channels, 2008, Tents, World, 8000, 70\n" +
        "All Channels, 2008, Tents, Boston, 200, 22\n" +
        "All Channels, 2008, Canoes, World, 5000, 35\n" +
        "All Channels, 2008, Canoes, Boston, 450, 3\n" +
        "All Channels, 2009, Tents, World, 18000, 1900\n" +
        "All Channels, 2009, Tents, Boston, 400, 40\n" +
        "All Channels, 2009, Canoes, World, 12000, 70\n" +
        "All Channels, 2009, Canoes, Boston, 1100, 7\n" +
        "All Channels, 2010, Tents, World, 30000, 300\n" +
        "All Channels, 2010, Tents, Boston, 900, 110\n" +
        "All Channels, 2010, Canoes, World, 29000, 160\n" +
        "All Channels, 2010, Canoes, Boston, 3100, 16";
}
