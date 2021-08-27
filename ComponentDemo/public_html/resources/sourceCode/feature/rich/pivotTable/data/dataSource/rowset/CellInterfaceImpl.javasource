/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/CellInterfaceImpl.java /main/6 2010/02/17 16:23:15 bmoroze Exp $ */

/* Copyright (c) 2006, 2010, Oracle and/or its affiliates. 
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
    bmoroze     09/15/06 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/CellInterfaceImpl.java /main/6 2010/02/17 16:23:15 bmoroze Exp $
 *  @author  bmoroze 
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.rowset;

import oracle.adf.model.dvt.util.transform.DataCellInterface;
import oracle.adf.model.dvt.util.transform.DataType;

import oracle.adf.model.dvt.util.transform.TransformException;

import oracle.adf.model.dvt.util.transform.total.AggType;


public class CellInterfaceImpl implements DataCellInterface
    {
        protected Object m_value = null;
        protected String m_column = null;
        
        public CellInterfaceImpl(Object value, String column)
        {
            m_value = value;
            m_column = column;
        }
        
        // Return the data value for the given DataMap type
        public Object getData(String type)
        {    
            if (DataType.COLUMN.equals(type))
                return m_column;
            if (DataType.AGGTYPE.equals(type))
                return AggType.NONE;
            return m_value;
        }

        public boolean setData(Object value, String type) throws TransformException
        {
            return false;
        }        
    }
    
