package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;

import java.util.Hashtable;
import java.util.Random;



public class SampleColorData extends ArrayList <SampleColorData.ColorData> {
    public SampleColorData() {
        init(null);
    }
    
    public SampleColorData(String filter []) {
        super();
        init(filter);
    }
    
    private void init (String filter[]) {
        Hashtable lookupTable = null;
        if (filter != null)
        {
            lookupTable = new Hashtable();
            for(int j=0; j < filter.length; j++) {
                lookupTable.put(filter[j], true);
            }
        } 
        
        for(int i=0; i < colorData.length; i++){
            Object data[] = colorData[i];
            if (lookupTable == null || lookupTable.get(data[0]) != null)
            {
                this.add(
                    new ColorData(
                        (String)data[0],
                        (String)data[1],
                        (Color)data[2],
                        (String)data[3],
                        (Integer)data[4]));
            }
        }
        
    }
    
    public SampleColorData(long seed, int percentageFull) {
        Random random = new Random(seed);
        
        for(int i=0; i < colorData.length; i++){
            if ( random.nextInt(100) <= percentageFull ){
                Object data[] = colorData[i];
                
                int colorType = random.nextInt(6);
                Color color;
                String category;
                if ( seed % 3 == 0 ){
                    color = SampleContinentData.colorSet[colorType];
                    category = SampleContinentData.categorySet[colorType];
                }
                else if ( seed % 3 == 1 ){
                    color = SampleColorData.colorSet[colorType];
                    category = SampleColorData.categorySet[colorType];
                }
                else {
                    color = SampleColorData2.colorSet[colorType];
                    category = SampleColorData2.categorySet[colorType];
                }
                Integer value = random.nextInt(100);
                this.add(
                    new ColorData(
                        (String)data[0],
                        (String)data[1],
                        color,
                        category,
                        value));
            }
        }
    }
    
    public static Color[] colorSet = new Color[]{new Color(180, 48, 48),
                                              new Color(128, 128, 128),
                                              new Color(122, 158, 198),
                                              new Color(234, 254, 38),
                                              new Color(42, 150, 68),
                                              new Color(192, 58, 188)};
    public static String[] categorySet = new String[]{"Toyota","BMW","Honda","Nissan","Ford","GM"};
    
    private static final Object colorData[][] = 
    {{"AL", "Alabama", colorSet[0], "category1", 24},
    {"AK", "Alaska", colorSet[1], "category1", 63},
    {"AZ", "Arizona", colorSet[2], "category1", 15},
    {"AR", "Arkansas", colorSet[0], "category1", 76},
    {"CA", "California", colorSet[1], "category1", 3},
    {"CO", "Colorado", colorSet[2], "category1", 44},
    {"CT", "Connecticut", colorSet[0], "category1", 82},
    {"DC", "District of Columbia", colorSet[0], "category1", 81},
    {"DE", "Delaware", colorSet[1], "category1", 55},
    {"FL", "Florida", colorSet[2], "category1", 61},
    {"GA", "Georgia", colorSet[0], "category1", 13},
    {"HI", "Hawaii", colorSet[1], "category1", 71},
    {"ID", "Idaho", colorSet[2], "category2", 9},
    {"IL", "Illinois", colorSet[0], "category1", 34},
    {"IN", "Indiana", colorSet[1], "category1", 17},
    {"IA", "Iowa", colorSet[2], "category1", 76}, 
    {"KS", "Kansas", colorSet[0], "category1", 52},
    {"KY", "Kentucky", colorSet[1], "category1", 42},
    {"LA", "Louisiana", colorSet[2], "category1", 12},
    {"ME", "Maine", colorSet[0], "category1", 43},
    {"MD", "Maryland", colorSet[1], "category1", 23},
    {"MA", "Massachusetts", colorSet[2], "category1", 13},
    {"MI", "Michigan", colorSet[0], "category1", 77},
    {"MN", "Minnesota", colorSet[1], "category2", 90},
    {"MS", "Mississippi", colorSet[2], "category2", 17},
    {"MO", "Missouri", colorSet[0], "category2", 8},
    {"MT", "Montana", colorSet[1], "category2", 65}, 
    {"NE", "Nebraska", colorSet[2], "category2", 54},
    {"NV", "Nevada", colorSet[0], "category2", 57},
    {"NH", "New Hampshire", colorSet[1], "category2", 43},
    {"NJ", "New Jersey", colorSet[2], "category2", 21},
    {"NM", "New Mexico", colorSet[0], "category2", 29},
    {"NY", "New York", colorSet[1], "category2", 43},
    {"NC", "North Carolina", colorSet[2], "category2", 39},
    {"ND", "North Dakota", colorSet[0], "category2", 87},
    {"OH", "Ohio", colorSet[1], "category2", 52},
    {"OK", "Oklahoma", colorSet[2], "category2", 11},
    {"OR", "Oregon", colorSet[0], "category2", 4},
    {"PA", "Pennsylvania", colorSet[1], "category2", 84},
    {"RI", "Rhode Island", colorSet[2], "category2", 63},
    {"SC", "South Carolina", colorSet[0], "category2", 23},
    {"SD", "South Dakota", colorSet[1], "category1", 73},
    {"TN", "Tennessee", colorSet[2], "category2", 55},
    {"TX", "Texas", colorSet[0], "category1", 51},
    {"UT", "Utah", colorSet[1], "category2", 61},
    {"VT", "Vermont", colorSet[2], "category2", 45},
    {"VA", "Virginia", colorSet[0], "category2", 37}, 
    {"WA", "Washington", colorSet[1], "category2", 56},
    {"WV", "West Virginia", colorSet[2], "category2", 58},
    {"WI", "Wisconsin", colorSet[0], "category2", 18},
    {"WY", "Wyoming", colorSet[1], "category2", 12}};
    
    
    
    public static class ColorData {
        private String name;
        private Color color;
        private String category;
        private int value;
        private String fullName;
        
        public ColorData(String name, String fullName, Color color, String category, int value){
            this.name = name;
            this.color = color;
            this.category = category;
            this.value = value;
            this.fullName = fullName;
        }
        
        public String getName(){
            return name;
        }
        
        public void setName(String name){
            this.name = name;
        }
        
        public String getFullName(){
            return fullName;
        }
        
        public void setFullName(String fullName){
            this.fullName = fullName;
        }
      
        public Color getColor(){
            return color;
        }

        public void setColor(Color color){
            this.color = color;
        }

        public String getCategory(){
            return category;
        }
        
        public void setCategory(String category){
            this.category = category;
        }
        
        public int getValue(){
            return value;
        }
        
        public void setValue(int value){
            this.value = value;
        }
        public String toString(){
            return "ColorData[name=" + name + "]";
        }
    }
}
