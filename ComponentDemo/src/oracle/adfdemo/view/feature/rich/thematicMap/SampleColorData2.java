/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/thematicMap/SampleColorData2.java /main/3 2010/12/14 14:48:18 ytang Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    kchow       04/19/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/thematicMap/SampleColorData2.java /main/3 2010/12/14 14:48:18 ytang Exp $
 *  @author  kchow   
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;



public class SampleColorData2 extends ArrayList <SampleColorData.ColorData> {
    public SampleColorData2() {
        for(int i=0; i < colorData.length; i++){
            Object data[] = colorData[i];
            this.add(
                new SampleColorData.ColorData(
                    (String)data[0],
                    (String)data[1],
                    (Color)data[2],
                    (String)data[3],
                    (Integer)data[4]));
        }
        
    }
    
    public static Color[] colorSet = new Color[]{new Color(122, 60, 30),
                                              new Color(94, 16, 19),
                                              new Color(22, 158, 18),
                                              new Color(158, 22, 18),
                                              new Color(198, 148, 18),
                                              new Color(52, 68, 157)};
    
    public static String[] categorySet = new String[]{"Gucci","Chanel","Fendi","Burberry","Louis Vuitton","Dior"};
    
    private static final Object colorData[][] = 
    {{"AL", "Alabama", colorSet[0], "category1", 24},
    {"AK", "Alaska", colorSet[1], "category1", 63},
    {"AZ", "Arizona", colorSet[2], "category1", 15},
    {"AR", "Arkansas", colorSet[0], "category1", 76},
    {"CA", "California", colorSet[1], "category1", 3},
    {"CO", "Colorado", colorSet[2], "category1", 44},
    {"CT", "Connecticut", colorSet[0], "category1", 82},
    {"DE", "Delaware", colorSet[1], "category1", 55},
    {"MA", "Massachusetts", colorSet[2], "category1", 13},
    {"MI", "Michigan", colorSet[0], "category1", 77},
    {"MN", "Minnesota", colorSet[1], "category2", 90},
    {"MS", "Mississippi", colorSet[2], "category2", 17},
    {"MO", "Missouri", colorSet[0], "category2", 8},
    {"ND", "North Dakota", colorSet[0], "category2", 87},
    {"OH", "Ohio", colorSet[1], "category2", 52},
    {"OK", "Oklahoma", colorSet[2], "category2", 11},
    {"OR", "Oregon", colorSet[0], "category2", 4},
    {"TN", "Tennessee", colorSet[2], "category2", 55},
    {"TX", "Texas", colorSet[0], "category1", 51},
    {"UT", "Utah", colorSet[1], "category2", 61},
    {"WV", "West Virginia", colorSet[2], "category2", 58},
    {"WI", "Wisconsin", colorSet[0], "category2", 18},
    {"WY", "Wyoming", colorSet[1], "category2", 12}};            
}
