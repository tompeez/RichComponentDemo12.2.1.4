package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Random;

import oracle.adfdemo.view.feature.rich.thematicMap.SampleContinentData.ContinentData;


public class SamplePointData extends ArrayList <SamplePointData.TestData>{
    public SamplePointData() {
        super();
        init (null);
    }
    
    public SamplePointData(String states[]) {
        super();
        init (states);
    }
    
    private void init (String states[]) {
        for(int i=0; i < pointData.length; i++)
        {
            Object data[] = pointData[i];
        
            boolean found=false;
            if (states != null)
            {
                for (int j=0; j < states.length; j++)
                {
                    if (((String) data[0]).indexOf(states[j]) == 0) 
                    {
                        found = true;
                        break;
                    }
                }
            } else {
                found = true;
            }
        
            if (found)
            {
                this.add(
                    new TestData(
                         (String)data[0],
                         (Double)data[1],
                         (Double)data[2],
                         (Color)data[3],
                         (String)data[4]));
            }
        }
        
    }
    
    public SamplePointData(long seed, int percentageFull) {
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
    
    public static class TestData{
        private String name;
        private Color color;
        private double longitude;
        private double latitude;
        private String category;
        
        TestData(String name, double latitude, double longitude, Color color, String category){
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.color = color;
            this.category = category;
        }

        public String getName(){
            return name;
        }
        
        public void setName(String name){
            this.name = name;
        }


        public String getFullName() {
            return name;
        }
        
        public void setFullName(String name) {
            this.name = name;
        }
        
        public Color getColor(){
            return color;
        }
        
        public void setColor(Color color){
            this.color = color;
        }
        
        public double getLongitude(){
            return longitude;
        }
        
        public void setLongitude(double longitude){
            this.longitude = longitude;
        }
        
        public double getLatitude(){
            return latitude;
        }
        
        public void setLatitude(double latitude){
            this.latitude = latitude;
        }
        
        public String getCategory(){
            return category;
        }
        
        public void setCategory(String category){
            this.category = category;
        }
        
        public Object getValue(){
            return null;
        }
        
        public void setValue(){
        }
        
    }
    
    private static Color color1 = new Color(180, 48, 48);
    private static Color color2 = new Color(64, 86, 149);
    private static Color color3 = new Color(170, 0, 255);
    private static Color color4 = new Color(122, 158, 198);
    private static Color color5 = new Color(66, 132, 255);
  
  private static final Object pointData[][] = 
      {{"NY_NEW_YORK_CITY", 40.77, -73.98, color1, "category1"},
       {"AK_JUNEAU", 58.37, -134.58, color2, "category1"},
       {"AK_ANCHORAGE", 61.22, -149.9, color3, "category2"},
       {"TX_HOUSTON", 29.97, -95.35, color4, "category2"},
       {"HI_HONOLULU", 21.29, -157.82, color5, "category2"},
       {"CA_SAN_FRANCISCO", 37.7749, -122.4194, color1,  "category2"},
       {"CO_DENVER", 39.7392, -104.9847, color2, "category1"},
       {"MI_DETROIT", 42.3314, -83.0458, color3, "category2"},
       {"ID_BOISE", 43.6126, -116.2111, color4, "category1"},
       {"IL_CHICAGO", 41.8500, -87.6501, color5, "category1"},
       {"GA_ATLANTA", 33.7490, -84.3880, color1, "category2"},
/*       
       {"Cairo", 30.058056, 31.228889, color1, "category1"},
       {"Cape Town", -33.925278, 18.423889, color2, "category2"},
       {"Beijing", 39.913889, 116.391607, color3, "category1"},
       {"Tokyo", 35.700556, 139.715, color4, "category2"},
       {"Sydney", -33.859972, 151.21111, color5, "category1"},       
       {"Melbourne", -37.81611, 144.963056, color1, "category2"},
       {"Paris", 48.85666, 2.350833, color2, "category1"},
       {"Rome", 41.9, 12.5, color3, "category2"},
       {"Sao Paulo", -23.55, -46.63333, color4, "category1"},
       {"Lima", -12.043333, -77.028333, color5, "category2"},       
*/
       };
}