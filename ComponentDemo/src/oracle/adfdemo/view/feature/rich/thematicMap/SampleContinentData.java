/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/thematicMap/SampleContinentData.java /main/1 2010/11/11 11:22:04 ytang Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    kchow       04/30/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/thematicMap/SampleContinentData.java /main/1 2010/11/11 11:22:04 ytang Exp $
 *  @author  kchow   
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Random;

import oracle.adfdemo.view.feature.rich.thematicMap.SampleContinentData.ContinentData;


public class SampleContinentData extends ArrayList <ContinentData> {

    public SampleContinentData(long seed, int percentageFull) {
        Random random = new Random(seed);
        
        for(int i=0; i < colorData.length; i++){
            if ( random.nextInt(100) <= percentageFull ){
                Object data[] = colorData[i];
                
                int colorType = random.nextInt(6);
                Color color = SampleContinentData.colorSet[colorType];
                String category = SampleContinentData.categorySet[colorType];
                Integer value = random.nextInt(100);
                this.add(
                    new ContinentData(
                        (String)data[0],
                        (String)data[1],
                        color,
                        category,
                        value));
            }
        }
    }
    
    public class ContinentData{
        private String m_continentCode;
        private String m_countinentName;
        private String m_category;
        private Color m_color;
        private int m_value;
        
        public ContinentData(String continentCode, String countinentName, Color color, String category, int value){
            m_continentCode = continentCode;
            m_countinentName = countinentName;
            m_category = category;
            m_color = color;
            m_value = value;
        }
        
        public String getContinentCode(){
            return m_continentCode;
        }
        
        public String getFullName(){
            return m_countinentName.replaceAll("_", " ");
        }
        
        public Color getColor(){
            return m_color;
        }
        
        public String getCategory(){
            return m_category;
        }
        
        public String getName(){
            return getContinentCode();
        }
        
        public int getValue(){
            return m_value;
        }
        
        public String toString(){
            return getFullName();
        }
    }
        
        
    
    public static Color[] colorSet = new Color[]{new Color(200, 100, 100),
                                              new Color(100, 100, 200),
                                              new Color(100, 200, 100),
                                              new Color(200, 200, 100),
                                              new Color(100, 200, 200),
                                              new Color(200, 100, 200)};
    
    public static String[] categorySet = new String[]{"Coke","Pepsi","7Up","Mountain Dew","Dr Pepper","Sunkist"};
    
    
    private static final Object colorData[][] = 
    {
        {"AS","Asia"},
        {"SA","South America"},
        {"NA","North America"},
        {"AF","Africa"},
        {"EU","Europe"},
        {"AN","Antarctica"},
        {"AU","Australia"},
    };


}
