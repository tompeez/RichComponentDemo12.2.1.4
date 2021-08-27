/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableCSVDemoModel.java /main/3 2012/07/09 13:46:40 jievans Exp $ */

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
    dahmed      08/07/08 - 
    jievans     01/22/08 - implement selection
    dahmed      06/14/07 - update demo and fix bug when data item is removed.
    dahmed      05/10/07 -
    jievans     03/26/07 - Creation
 */

package oracle.adfdemo.view.feature.rich.pivotTable.data;

import java.util.ArrayList;
import org.apache.myfaces.trinidad.model.UploadedFile;

/**
 * This data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableCSVDemoModel.java /main/3 2012/07/09 13:46:40 jievans Exp $
 *  @author  jievans 
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableCSVDemoModel extends BaseRowsetModel {
    public PivotTableCSVDemoModel() {
    }

    protected ArrayList<String[]> getRowsetData() {
        if(_file==null)
            return getRowsetDataFromString(PivotTableCSVDemoModel.SAMPLE_ROWSET);
        else
            return getRowsetDataFromFile(getCSVFile());
    }

    public UploadedFile getCSVFile() {
        return _file; 
    }

    public void setCSVFile(UploadedFile file) {
        _file = file; 
    }

    //////////////////////////////////////////////////////////////////////////
    // rowset datasource properties
    private UploadedFile _file;
    private static final String SAMPLE_ROWSET = 
        "Time, Product, Channel, Geography, Sales, Units\n" +
        "row, row, column, column, data, data\n" +
        "string, string, string, string, double, double\n" +
        "2007, Tents, All Channels, World, 20000, 200\n" +
        "2007, Tents, All Channels, Boston, 500, 50\n" +
        "2007, Canoes, All Channels, World, 15000, 75\n" +
        "2007, Canoes, All Channels, Boston, 1500, 8\n" +
        "2006, Tents, All Channels, World, 10000, 100\n" +
        "2006, Tents, All Channels, Boston, 250, 25\n" +
        "2006, Canoes, All Channels, World, 7500, 40\n" +
        "2006, Canoes, All Channels, Boston, 750, 4\n" +
        "2005, Tents, All Channels, World, 5000, 50\n" +
        "2005, Tents, All Channels, Boston, 125, 15\n" +
        "2005, Canoes, All Channels, World, 3750, 20\n" +
        "2005, Canoes, All Channels, Boston, 375, 2";
}
