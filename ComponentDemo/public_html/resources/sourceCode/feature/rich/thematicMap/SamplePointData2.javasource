/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/thematicMap/SamplePointData2.java /main/2 2010/12/14 14:48:18 ytang Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    kchow       04/20/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/thematicMap/SamplePointData2.java /main/2 2010/12/14 14:48:18 ytang Exp $
 *  @author  kchow   
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Random;

import oracle.adfdemo.view.feature.rich.thematicMap.SamplePointData.TestData;


public class SamplePointData2 extends ArrayList <SamplePointData.TestData>{
    public SamplePointData2() {
        for(int i=0; i < pointData.length; i++){
            Object data[] = pointData[i];
            this.add(
                new SamplePointData.TestData(
                    (String)data[0],
                    (Double)data[1],
                    (Double)data[2],
                    (Color)data[3],
                    (String)data[4]));
        }
    }
    public SamplePointData2(long seed, int percentageFull) {
        Random random = new Random(seed);
        
        for(int i=0; i < pointData.length; i++){
            if ( random.nextInt(100) <= percentageFull ){
                Object data[] = pointData[i];
                int colorType = random.nextInt(5);
                Color color;
    
                switch (colorType){
                    case 0: color = color1;break;
                    case 1: color = color2;break;
                    case 2: color = color3;break;
                    case 3: color = color4;break;
                    default: color = color5;break;
                }
                String category = "Category "+colorType;
                Integer value = random.nextInt(100);
                this.add(
                    new TestData(
                        (String)data[0],
                        (Double)data[1],
                        (Double)data[2],
                        color,
                        category));
            }
        }
    }
        
    private static Color color1 = new Color(80, 148, 48);
    private static Color color2 = new Color(164, 68, 49);
    private static Color color3 = new Color(70, 10, 55);
    private static Color color4 = new Color(22, 58, 19);
    private static Color color5 = new Color(166, 32, 25);
  
    private static final Object pointData[][] = {
        {"Cairo", 30.058056, 31.228889, color1, "category1"},
        {"Cape Town", -33.925278, 18.423889, color2, "category2"},
        {"Beijing", 39.913889, 116.391607, color3, "category1"},
        {"Tokyo", 35.700556, 139.715, color4, "category2"},
        {"Sydney", -33.859972, 151.21111, color5, "category1"},       
        {"Melbourne", -37.81611, 144.963056, color1, "category2"},
        {"Paris", 48.85666, 2.350833, color2, "category1"},
        {"Rome", 41.9, 12.5, color3, "category2"},
        {"Sao Paulo", -23.55, -46.63333, color4, "category1"},
        {"Lima", -12.043333, -77.028333, color5, "category2"}};
}