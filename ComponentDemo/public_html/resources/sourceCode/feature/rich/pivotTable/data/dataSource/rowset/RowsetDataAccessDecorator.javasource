/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/RowsetDataAccessDecorator.java /main/6 2010/12/23 05:57:07 jievans Exp $ */

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
      jievans   12/23/10 - bug 8577899: sorting broken for edited columns
      ccchow    01/19/10 - put row col info in updatedValue cache
      ccchow    12/10/09 - provide tabular data for graph
      dahmed    01/21/08 - 
      bmoroze   11/16/07 - 
      dahmed    07/09/07 - initial version
 */
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.rowset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oracle.adfinternal.view.faces.bi.renderkit.pivotTable.DataAccessDecorator;

import oracle.dss.util.ColumnOutOfRangeException;
import oracle.dss.util.DataDirector;
import oracle.dss.util.DataAccess;
import oracle.dss.util.DataMap;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.QDR;
import oracle.dss.util.QDRMember;
import oracle.dss.util.RowOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;


/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/RowsetDataAccessDecorator.java /main/5 2010/08/06 07:59:04 jayturne Exp $
 *  @author  dahmed  
 *  @since   release specific (what release of product did this appear in)
 */
public class RowsetDataAccessDecorator extends DataAccessDecorator {
    public RowsetDataAccessDecorator(DataAccess dataAccess, HashMap<String,FilterSpec> filterSpecs, Map<Set<String>,Map<String,Object>> updatedValues) {
        super(dataAccess);
        __filterSpecs = filterSpecs; 
        __updatedValues = updatedValues;
    }


    public Object getValue(int row, int column, String type) throws RowOutOfRangeException, ColumnOutOfRangeException
    {
        try {
            if(type.equals("dataItem")) // used by graph in pt demo!
                return getDataAttribute(row,column);
            if(type.equals("dataAttributeMinimum"))                
                return getDataAttributeMinimum(getDataAttribute(row,column));
            else if(type.equals("dataAttributeMaximum"))                
                return getDataAttributeMaximum(getDataAttribute(row,column));
            else if(type.equals(DataMap.DATA_VALUE))
            {
              QDR valueQDR = getValueQDR(row,column,DataAccess.QDR_WITHOUT_PAGE);              
              Set<String> key = this.createQDRKey(valueQDR);
              if(__updatedValues.containsKey(key))
                 return __updatedValues.get(key).get("value");
            }
            else if(type.equals("tabularData")) {
                ArrayList _list = new ArrayList(1);
                _list.add(new Object[]{super.getValue(row,column,MetadataMap.METADATA_VALUE), getDataAttribute(row,column), super.getValue(row,column,"formattedDataValue")});
                return _list;
            }
            return super.getValue(row, column,type);
        }catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
    
    private Set<String> createQDRKey(QDR qdr)
    {
        Set<String> _set = new HashSet<String>();
        String pairs = qdr.getDimMemberPairs();
        int index = pairs.indexOf(";");
        while (index != -1)
        {
            _set.add(pairs.substring(0, index));
            pairs = pairs.substring(index + 1);
            index = pairs.indexOf(";");
        }
        _set.add(pairs);
        return _set;
    }

    public boolean setValue(Object data, int row, int col, String type) throws RowOutOfRangeException, ColumnOutOfRangeException {
        if(DataMap.DATA_VALUE.equals(type)) {
            if(data instanceof Number)
                data = new Double(((Number)data).doubleValue());
            else if (data instanceof String) {
                try
                {
                    Object _oldValue = super.getValue(row, col, DataMap.DATA_UNFORMATTED);
                    if (_oldValue instanceof Number)
                        data = new Double((String)data);
                }
                catch (Exception ex){}                                
            }
            
            QDR valueQDR = getValueQDR(row,col,DataAccess.QDR_WITHOUT_PAGE);
            Set<String> key = this.createQDRKey(valueQDR);
            
            Map<String,Object> map = new HashMap<String,Object>(2);
            map.put("qdr", valueQDR);
            map.put("value", data);
            __updatedValues.put(key, map);

            return true;
        }
        return false;
    }
    
    public Object getMemberMetadata(int edge, int layer, int slice, String type) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException
    {
        if(type.equals("dataItem")) // for the moment, this is only used in the graph in pt demo to determine if the measure dim is on the edge ( via dataItem!=null )              
            return getDataAttribute(edge,layer,slice);
        if(type.equals("dataAttributeMinimum"))                
            return getDataAttributeMinimum(getDataAttribute(edge,layer,slice));
        else if(type.equals("dataAttributeMaximum"))                
            return getDataAttributeMaximum(getDataAttribute(edge,layer,slice));
        else
            return super.getMemberMetadata(edge,layer,slice,type);
    }

    public String getDataAttribute(int row, int column)  throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException 
    {
      String dataAttribute = getDataAttribute(DataDirector.ROW_EDGE,0,row);
      if(dataAttribute==null)
          dataAttribute = getDataAttribute(DataDirector.COLUMN_EDGE,0,column);
      return dataAttribute;
    }
    
    public String getDataAttribute(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException
    {
      QDR sliceQDR = getSliceQDR(edge,slice,DataAccess.QDR_WITH_PAGE);
      QDRMember dimMember = sliceQDR.getDimMember(sliceQDR.getMeasureDim()); 
      if(dimMember!=null)
          return dimMember.toString();          
      else
          return null;
    }
    
    public Number getDataAttributeMinimum(String dataAttribute) {
        FilterSpec filter = __filterSpecs.get(dataAttribute);
        return filter.getMin();
    }
    public Number getDataAttributeMaximum(String dataAttribute) {
        FilterSpec filter = __filterSpecs.get(dataAttribute);
        return filter.getMax();
    }
    
    HashMap<String,FilterSpec> __filterSpecs = null;
    Map<Set<String>,Map<String,Object>> __updatedValues = null;
}