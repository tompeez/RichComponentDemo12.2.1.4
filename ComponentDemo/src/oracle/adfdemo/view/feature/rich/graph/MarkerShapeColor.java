package oracle.adfdemo.view.feature.rich.graph;

import javax.faces.component.UIComponent;

import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.faces.bi.model.GraphDataModel;

import oracle.dss.graph.CommonGraph;

import oracle.dss.util.ColumnOutOfRangeException;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.RowOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;
import oracle.dss.util.QDR;

import oracle.adfdemo.view.feature.rich.graph.data.BaseLocalDataSource;

import oracle.dss.util.DataDirector;
/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/MarkerShapeColor.java /main/6 2010/01/18 17:32:03 teclee Exp $ */

/* Copyright (c) 2009, 2010, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    teclee      03/16/09 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/MarkerShapeColor.java /main/6 2010/01/18 17:32:03 teclee Exp $
 *  @author  teclee
 *  @since   release specific (what release of product did this appear in)
 */

public class MarkerShapeColor {
     public GraphDataModel getData() {
        return new GraphDataModel(new ScatterData1());
    }
}

class ScatterData1 extends BaseLocalDataSource { // CombinedScatterDataSource

    // DATA
    private static final int row = 6;
    private static final int col = 4;
    private static final double defaultData[][] =

        { { 10, 12, 36, 4}, {7, 40, 53, 14},{32, 34, 10, 25} , {23, 24, 47, 10}, /*{20, 5, 12, 7},*/ {60, 25, /*4, 1*/20,5 }, { 4, 4, 64, 36}};//, },{ 36, 4 , 53, 14}, {10, 25, 47, 10 }};

    public ScatterData1() { // default constructor
        // Construct a simple default data set with one page, row rows, and col columns
        super(defaultData, row, col, 1);
    }
    
    String PRODUCT[] = {"Coke", "Pepsi"};
    String BRAND[] = {"Regular", "Diet", "Cherry"};
    @Override
    public Object getLayerMetadata(int edge, int layer,
                                   String type) throws EdgeOutOfRangeException,
                                                       LayerOutOfRangeException {
        if (edge == DataDirector.COLUMN_EDGE) {
            if (layer == 0)
                return "Packages";
            else if (layer == 1)
                return "Metrics";
            else
                return null;
            
        }
        
        if (layer == 0)
            return "Product";
        else if (layer == 1)
            return "Brand";
        else
            return null;
    }

    @Override
    public Object getMemberMetadata(int edge, int layer, int slice,
                                    String type) throws EdgeOutOfRangeException,
                                                        LayerOutOfRangeException,
                                                        SliceOutOfRangeException {       
        
        if (edge == DataDirector.COLUMN_EDGE) {
            if (layer == 0) {
                return ((slice>1)?"Bottles":"Cans");
            }
            if (layer == 1) {
                return ((slice%2==0)?"Sales":"Cost");
            }
            else {
                return null;
            }
        }
        else {
            if (layer == 0) {
                return PRODUCT[(slice/3)%2];
            }
            else if (layer == 1) {
                return BRAND[slice%3];
            }
            else {
                return null;
            }
        }
    }

    @Override
    public int getLayerCount(int edge) throws EdgeOutOfRangeException {
        return 2;
    }


    @Override
    public QDR getSliceQDR(int edge, int slice,
                           int flags) throws EdgeOutOfRangeException,
                                          SliceOutOfRangeException {
        
        if (edge == DataDirector.COLUMN_EDGE) {
            return new QDR("", "Packages");
        }
        
        
        QDR qdr = new QDR();
        qdr.addDimMemberPair("Brand", "Brand");
        qdr.addDimMemberPair("Product", "Product");
        return qdr;
    }
    
    String rowLabels2[] = {"Coke Regular", "Coke Diet", "Coke Cherry", "Pepsi Regular", "Pepsi Diet", "Pepsi Cherry"};
    
    @Override
    public Object getSliceLabel(int edge, int slice, String type) throws EdgeOutOfRangeException, SliceOutOfRangeException {
        switch (edge) {
            case DataDirector.COLUMN_EDGE:
                if (slice < 0 || slice >= columnCount) 
                    throw new SliceOutOfRangeException(slice, columnCount);
            switch (slice) {
            case 0:
                return "Bottles Sales";
                case 1:
                return "Bottles Cost";
                case 2:
                return "Cans Sales";
                case 3:
                return "Cans Cost";
            }
            case DataDirector.ROW_EDGE:
                if (slice < 0 || slice >= rowCount)
                    throw new SliceOutOfRangeException(slice, rowCount);
                return rowLabels2[slice];
            default:
                return super.getSliceLabel(edge, slice, type);
        }            
    }
}