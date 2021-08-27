/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/thematicMap/SampleRegionData.java /main/1 2010/11/11 11:22:04 ytang Exp $ */

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
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/thematicMap/SampleRegionData.java /main/1 2010/11/11 11:22:04 ytang Exp $
 *  @author  kchow   
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Random;

import oracle.adfdemo.view.feature.rich.thematicMap.SampleRegionData.RegionData;


public class SampleRegionData extends ArrayList <RegionData> {

    public SampleRegionData(long seed, int percentageFull) {
        Random random = new Random(seed);
        
        for(int i=0; i < colorData.length; i++){
            if ( random.nextInt(100) <= percentageFull ){
                Object data[] = colorData[i];
                
                int colorType = random.nextInt(6);
                Color color = SampleContinentData.colorSet[colorType];
                String category = SampleContinentData.categorySet[colorType];
                Integer value = random.nextInt(100);
                this.add(
                    new RegionData(
                        (String)data[0],
                        (String)data[1],
                        color,
                        category,
                        value));
            }
        }
    }
    
    public class RegionData{
        private String m_regionCode;
        private String m_regionName;
        private String m_category;
        private Color m_color;
        private int m_value;
        
        public RegionData(String regionCode, String regionName, Color color, String category, int value){
            m_regionCode = regionCode;
            m_regionName = regionName;
            m_category = category;
            m_color = color;
            m_value = value;
        }
        
        public String getContinentCode(){
            return m_regionCode;
        }
        
        public String getFullName(){
            return m_regionName.replaceAll("_", " ");
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
    
    
    private static final Object colorData[][] = 
    {
        {"APAC","Asia Pacific"},
        {"EMEA","Europe, the Middle East, and Africa"},
        {"LAT","Latin America"},
        {"NA","North America"}
    };


}
