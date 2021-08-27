/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/MemberInterfaceImpl.java /main/7 2010/02/17 16:23:15 bmoroze Exp $ */

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
    bmoroze     11/14/07 - 
    dahmed      05/07/07 - .\
    bmoroze     09/15/06 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/MemberInterfaceImpl.java /main/7 2010/02/17 16:23:15 bmoroze Exp $
 *  @author  bmoroze 
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.rowset;

import oracle.dss.util.DataDirector;
import oracle.adf.model.dvt.util.transform.MemberInterfaceAbstractImpl;
import oracle.adf.model.dvt.util.transform.MemberMetadata;
import oracle.adf.model.dvt.util.transform.TransformException;

import oracle.adfinternal.model.dvt.util.transform.total.AggMethod;
import oracle.adf.model.dvt.util.transform.total.AggType;

public class MemberInterfaceImpl extends MemberInterfaceAbstractImpl
    {
        protected String m_value = null;
        protected String m_column = null;
        
        public MemberInterfaceImpl(String value, String column)
        {
            super();
            m_value = value;
            m_column = column;
        }
        
        // Represent this member as its getValue() String
        public String toString()
        {
            return m_value.toString();
        }
        
        // Return the basic value used as the unique key for this member (i.e., MetadataMap.METADATA_VALUE)
        public String getValue() throws TransformException
        {
            return m_value;
        }
        
        // Return the appropriate member metadata for the given MetadataMap type
        public Object getMetadata(String type) throws TransformException
        {
            if (type.equals(MemberMetadata.COLUMN))
                return m_column;
            if (type.equals(MemberMetadata.COLLAPSED))
                return Boolean.valueOf(false);
            if (type.equals(MemberMetadata.AGGREGATE_POSITION))
                return AggregatePosition.NONE;
            if(type.equals(MemberMetadata.DRILLSTATE)) {
                return DataDirector.DRILLSTATE_NOT_DRILLABLE;
            }
            if (type.equals(MemberMetadata.AGGTYPE)) {
                return new AggMethod[] {new AggMethod(AggType.NONE)};
            }
            return m_value;
        }

    }
    
