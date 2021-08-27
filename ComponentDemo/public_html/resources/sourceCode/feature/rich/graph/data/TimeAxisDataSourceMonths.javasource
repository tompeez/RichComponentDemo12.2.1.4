/*
** Copyright (c) 2008, 2009, Oracle and/or its affiliates. 
** All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.graph.data;

import oracle.dss.util.DataDirector;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.SliceOutOfRangeException;

public class TimeAxisDataSourceMonths extends BaseLocalDataSource 
    {                           

    // DATA
    private static final double defaultData[][] =
        {
        {42, 50, 38, 46, 46, 54, 42, 50},
        {26, 34, 22, 30, 30, 38, 26, 34},
        {10, 18,  6, 14,  0, 22, 10, 18},
        {58, 66, 54, 62, 62, 70, 58, 66},
        {76, 84, 72, 80, 80, 88, 76, 88}
        };

    public TimeAxisDataSourceMonths()
        {                       // default constructor
        // Construct a simple default data set with one page, four rows, and eight columns

        super( defaultData, 4, 8, 1 );
        }  
        
    /**
     * Retrieves the label for the specified column.
     *
     * @param   edge Edge of interest.
     * @param slice The slice whose label is desired.
     * @param type A constant that specifies the kind of metadata that you want.
     *             Valid <code>type</code> values are defined in the MetadataMap.
     *
     * @return The label (or metadata) for the location on the specified edge.
     *
     * @throws EdgeOutOfRangeException If <code>edge</code> is out of range.
     * @throws SliceOutOfRangeException If <code>slice</code> is negative or
     *         too large.
     *
     * @status Needs change API change
     */
    public Object getSliceLabel(int edge, int slice, String type) throws EdgeOutOfRangeException, SliceOutOfRangeException {
        switch (edge) {
            case DataDirector.COLUMN_EDGE:
                if (slice < 0 || slice >= 8) 
                    throw new SliceOutOfRangeException(slice, 8);
                if(slice == 0)
                    return java.sql.Date.valueOf("2005-03-01");
                if(slice == 1)
                    return java.sql.Date.valueOf("2005-05-01"); 
                if(slice == 2)
                    return java.sql.Date.valueOf("2005-07-01"); 
                if(slice == 3)
                    return java.sql.Date.valueOf("2005-09-01");
                if(slice == 4)
                    return java.sql.Date.valueOf("2005-11-01");
                if(slice == 5)
                    return java.sql.Date.valueOf("2006-01-01");
                if(slice == 6)
                    return java.sql.Date.valueOf("2006-03-01");
                if(slice == 7)
                    return java.sql.Date.valueOf("2006-05-01");
            default:
                return super.getSliceLabel(edge, slice, type);
        }            
    }
    
    public Object getMemberMetadata(int edge, int layer, int slice, String type) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
         if (edge == DataDirector.COLUMN_EDGE && type == MetadataMap.METADATA_DATE)
        {
            return getSliceLabel(edge, slice, type);
        }
        else
            return super.getMemberMetadata(edge, layer, slice, type);
    }
                             
}                           
