/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/LayerInterfaceImpl.java /main/3 2009/12/08 13:46:53 bmoroze Exp $ */

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
    bmoroze     09/15/06 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/LayerInterfaceImpl.java /main/3 2009/12/08 13:46:53 bmoroze Exp $
 *  @author  bmoroze 
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.rowset;

import oracle.adf.model.dvt.util.transform.LayerInterface;

import oracle.adf.model.dvt.util.transform.LayerMetadata;
import oracle.adf.model.dvt.util.transform.TransformException;

import oracle.dss.util.DataMap;
import oracle.dss.util.LayerMetadataMap;
import oracle.dss.util.MetadataMap;

public class LayerInterfaceImpl implements LayerInterface
{
    protected String m_value = null;
    
    public LayerInterfaceImpl(String value)
    {
        super();
        m_value = value;
    }
    
    // Compare two layers based on the getValue()
    public boolean equals(LayerInterface layer)
    {
        try
        {
            return m_value.equals(layer.getValue());
        }
        catch (TransformException e)
        {
            return false;
        }
    }
    
    // Generate a hash code for this layer based on the getValue()
    public int hashCode()
    {
        return m_value.hashCode();
    }
    
    // Represent this layer as its getValue() String
    public String toString()
    {
        return m_value;
    }
    
    // Return the basic value used as the unique key for this value (i.e., LayerMetadataMap.LAYER_METADATA_NAME)
    public String getValue() throws TransformException
    {
        return m_value;
    }
    
    // Return the appropriate layer metadata for the given LayerMetadataMap type
    public Object getMetadata(String type) throws TransformException
    {
        if (type.equals(LayerMetadata.MEASURE))
            return Boolean.valueOf(m_value.equals(BaseProjectionImpl.MEASDIM));
        else if(type.equals(LayerMetadata.LABEL) && m_value.equals(BaseProjectionImpl.MEASDIM))
            return "Measure";            
        return m_value;
    }
    
    public LayerMetadataMap getSupportedLayerMetadataMap()
    {
        return null;
    }
    
    public MetadataMap getSupportedMetadataMap()
    {
        return null;
    }

    public DataMap getSupportedDataMap()
    {
        return null;
    }        
}