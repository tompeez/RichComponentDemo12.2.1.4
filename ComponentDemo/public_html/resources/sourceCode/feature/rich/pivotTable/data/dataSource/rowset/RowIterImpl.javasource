/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/RowIterImpl.java /main/4 2009/12/08 13:46:53 bmoroze Exp $ */

/* Copyright (c) 2006, 2009, Oracle and/or its affiliates. 
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
    bmoroze     11/08/07 - 
    dahmed      06/14/07 - fix bug when a data item isn't part of the layout or
                           data items
    jramanat    05/31/07 - 
    bmoroze     11/01/06 - Move files back to oracle.dss.util
    bmoroze     09/15/06 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/RowIterImpl.java /main/4 2009/12/08 13:46:53 bmoroze Exp $
 *  @author  bmoroze 
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.rowset;

import java.util.Hashtable;

import oracle.adf.model.dvt.util.transform.DataCellInterface;

import oracle.adf.model.dvt.util.transform.MemberInterface;

import oracle.dss.util.QDR;
import oracle.dss.util.QDRMember;
import oracle.adf.model.dvt.util.transform.RowIterator;
import oracle.adf.model.dvt.util.transform.TransformException;

public class RowIterImpl implements RowIterator
    {
        protected int m_row = -1;
        protected int m_rowLimit = -1;
        protected String[] m_columns = null;
        protected Object[][] m_data = null;
        protected Hashtable<QDR, DataCellInterface> m_dataCache = null;
        protected MemberInterface[] m_dataItems = null;
        protected String[][] m_layout = null;        
        
        public RowIterImpl(String[] columns, Object[][] data, Hashtable<QDR, DataCellInterface> dataCache, MemberInterface[] dataItems, String[][] layout)
        {
            super();
            m_columns = columns;
            m_data = data;
            if (m_data != null)
                m_rowLimit = m_data.length;
            m_dataCache = dataCache;
            m_dataItems = dataItems;
            m_layout = layout;
        }
        

	public Object clone() {
	    return new RowIterImpl(m_columns, m_data, m_dataCache, m_dataItems, m_layout);
	}

        // Indicates whether the iterator has more rows
        public boolean hasMoreRows() throws TransformException
        {
            return m_row+1 < m_rowLimit;
        }
        
        public String[] getColumns()
        {
            return m_columns;
        }
        
        // Iterates to the next row, returns true if the iteration was successful
        public boolean nextRow() throws TransformException
        {
            boolean hasMore = hasMoreRows();
            m_row++;
            if (hasMore)
                storeData();
            return hasMore;
        }
        
        // Frees any resources associated with this iterator
        public void close() throws TransformException
        {
        }
        
        protected int getColumnPosition(String column)
        {
            for (int i = 0; i < m_columns.length; i++)
            {
                if (m_columns[i].equals(column))
                    return i;
            }
            return -1;
        }
        
        // Retrieves a MemberInterface implentation representing the member ID in column
        public MemberInterface getMember(String column) throws TransformException
        {
            if (column.equals(BaseProjectionImpl.DATA_ONLY))
            {
                if (m_data[m_row].length > m_columns.length)
                    return new MemberInterfaceImpl(m_data[m_row][m_data[m_row].length-1] == null ? null : m_data[m_row][m_data[m_row].length-1].toString(), column);
                else
                    return null;
            }
            return new MemberInterfaceImpl(m_data[m_row][getColumnPosition(column)] == null ? null : m_data[m_row][getColumnPosition(column)].toString(), column);
        }
        
        // Build a QDR key and store the data in the cache
         protected void storeData() throws TransformException
        {
            QDR qdr = null;
            DataCellInterface cellValue = null;
            if (m_dataItems == null)
                return;
            
            for (int m = 0; m < m_dataItems.length; m++)
            {
                qdr = new QDR(BaseProjectionImpl.MEASDIM);
                cellValue = null;
                // For each column, build the QDR for each data item
                for (int i = 0; i < m_columns.length; i++)
                {
                    if (m_columns[i].equals(m_dataItems[m].getValue()))
                    {
                        // This is the data value
                        cellValue = new CellInterfaceImpl(m_data[m_row][i], m_columns[i]);
                        qdr.addDimMemberPair(BaseProjectionImpl.MEASDIM, m_columns[i]);
                        
                        // Now, is it in layout as well?  special case for gauges - put it in QDR, it
                        // will be part of the edge
                        if (isInLayout(m_columns[i]))
                        {
                            if (m_data[m_row][i] != null)
                                qdr.addDimMemberPair(m_columns[i], new QDRMember(QDRMember.FIXED, m_data[m_row][i] == null ? null : m_data[m_row][i].toString()));
                        }
                    }
                    else if (!isDataItem(m_columns[i], m_dataItems) && isInLayout(m_columns[i]))
                    {
                        // Add it to the QDR
                        if (m_data[m_row][i] != null)
                            qdr.addDimMemberPair(m_columns[i], new QDRMember(QDRMember.FIXED, m_data[m_row][i] == null ? null : m_data[m_row][i].toString()));
                    }
                }
                m_dataCache.put(qdr, cellValue);
            }            
        }
        
        private boolean isInLayout(String column)
        {
            if (m_layout == null)
                return false;
            
            for (int e = 0; e < m_layout.length; e++)
                if (m_layout[e] != null)
                    for (int l = 0; l < m_layout[e].length; l++)
                        if (column.equals(m_layout[e][l]))
                            return true;
            return false;
        }
        
        private boolean isDataItem(String column, MemberInterface[] dataItems) throws TransformException
        {
            for (int i = 0; i < dataItems.length; i++)
            {
                if (column.equals(dataItems[i].getValue()))
                    return true;
            }
            return false;
        }
        
        public Object getValue(String column) throws TransformException
        {
            return m_data[m_row][getColumnPosition(column)];
        }
        
        // Retrieves a DataCellInterface implentation for the data layer id in column
        public DataCellInterface getCell(String column) throws TransformException
        {                
            return new CellInterfaceImpl(m_data[m_row][getColumnPosition(column)], column);
        }

    

}