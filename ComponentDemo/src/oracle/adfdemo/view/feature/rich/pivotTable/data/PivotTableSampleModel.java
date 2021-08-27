/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableSampleModel.java /main/5 2012/07/09 13:46:39 jievans Exp $ */

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
    jievans     11/30/09 - use new API for whenAvailable check
    jievans     11/17/09 - implement AsyncFetch
    dahmed      08/27/08 - 
    ahadi       06/12/08 - new PT edit model
    ahadi       06/12/08 - Creation
 */

package oracle.adfdemo.view.feature.rich.pivotTable.data;

import java.util.ArrayList;

import oracle.adf.share.ADFContext;
import oracle.adf.view.faces.bi.model.DataCellIndex;
import oracle.adf.view.rich.model.AsyncFetch;
import oracle.adf.view.rich.model.AsyncFetcher;

/**
 * This data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableSampleModel.java /main/5 2012/07/09 13:46:39 jievans Exp $
 *  @author  ahadi   
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableSampleModel extends BaseRowsetModel implements AsyncFetch {
  
  public PivotTableSampleModel() {
  }

  protected ArrayList<String[]> getRowsetData() {
      return getRowsetDataFromString(PivotTableSampleModel.SAMPLE_ROWSET);
  }

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

    public boolean isRangeLocallyAvailable(DataCellIndex cellIndex, int rowCount, int columnCount) {
        return fetched;
    }
    
    public AsyncFetcher getAsyncFetcher(Object fetchConstraint) {
        return asyncFetcher;
    }

    public boolean isSupportedFetchConstraint(Class<?> fetchConstraintsClass) {
        return true;
    }
    
    // Init fetched to false since initially the data is not fetched.  getDataAccess() sets it to true, 
    // so that subsequent calls to isFetched() return true, just like a real dataSource.
    // Note: after changing to the da.isRangeLocallyAvailable() API for whenAvailable, the "override getDA()" approach 
    // isn't effective anymore, since with the new API we have to call getDA() before the wA check.  We wanted to have 
    // a checkbox in the page anyway, so just use that.
//    @Override
//    public DataAccess getDataAccess() {
//        fetched = true; // Note: it doesn't work to set this ivar in fetch(), since that method isn't called if there is only one streaming component on the page.  So we set it here.
//        return super.getDataAccess();
//    }
    
    private boolean fetched = false; 

    public boolean isFetched() {
        return fetched;
    }
    
    public void setFetched(boolean fetched) {
        this.fetched = fetched;
    }

    private AsyncFetcher asyncFetcher = new AsyncFetcher() {
        public boolean isFetched(AsyncFetch model, Object fetchConstraint) {
            return fetched; 
        }
    
        public Object getFetchContext(AsyncFetch model, Object fetchConstraint) {
            return ADFContext.getCurrent();
        }

        public Object fetch(AsyncFetch model, Object fetchConstraint, Object fetchContext) {
            ADFContext adfContext = (ADFContext)fetchContext;
            adfContext.setAsCurrent();

            try {
                // Nothing to do in this fake impl
            } finally {
                adfContext.removeAsCurrent();
            }

            return null;
        }
    };
}