/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/FilterSpec.java /main/2 2009/03/19 21:37:43 teclee Exp $ */

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
      dahmed    03/13/08 - move from SamplePivotTableModel
      dahmed    01/21/08 - 
      bmoroze   11/16/07 - 
 */
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.rowset;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/FilterSpec.java /main/2 2009/03/19 21:37:43 teclee Exp $
 *  @author  dahmed  
 *  @since   release specific (what release of product did this appear in)
 */
public class FilterSpec {
    public FilterSpec(String label, int columnIndex, double minValue, double maxValue, Number rangeMinValue, Number rangeMaxValue) 
    {
        _label = label;
        _columnIndex = columnIndex;
        _minValue = (long)minValue;
        _maxValue = (long)maxValue;
        _rangeMinValue = rangeMinValue.longValue();
        _rangeMaxValue = rangeMaxValue.longValue();
        _dataEnabled=true;
    }
    
    public boolean includeRow(Object[] row) {
        Object _value = row[_columnIndex];
        if(_value instanceof Number)
        {
            double value = ((Number)_value).doubleValue();
            if(value < _rangeMinValue.doubleValue() ||
               value > _rangeMaxValue.doubleValue())
                return false;
        }
        return true;
    }

    public void setRangeMax(Number rangeMaxValue) {
        _rangeMaxValue=rangeMaxValue;
    }

    public void setRangeMin(Number rangeMinValue) {
        _rangeMinValue=rangeMinValue;
    }

    public void setMax(Number maxValue) {
        _maxValue=maxValue;
    }

    public void setMin(Number minValue) {
        _minValue=minValue;
    }

    public Number getRangeMax() {
        return _rangeMaxValue;
    }

    public Number getRangeMin() {
        return _rangeMinValue;
    }
    
    public String getLabel() {
        return _label;
    }
    public Number getMin() {
        return _minValue;
    }

    public Number getMax() {
        return _maxValue;
    }
    
    public void setDataEnabled(Boolean dataEnabled) {
        _dataEnabled = dataEnabled;
    }
    public Boolean isDataEnabled() {
        return _dataEnabled;
    }
    
    String _label;
    int _columnIndex;
    Number _minValue;
    Number _maxValue;
    Number _rangeMinValue;
    Number _rangeMaxValue;
    Boolean _dataEnabled;
}
