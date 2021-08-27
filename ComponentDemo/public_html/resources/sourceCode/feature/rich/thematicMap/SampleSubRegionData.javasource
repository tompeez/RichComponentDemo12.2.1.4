package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;


public class SampleSubRegionData extends ArrayList <SampleSubRegionData.ColorData> {
    public SampleSubRegionData() {
        for(int i=0; i < colorData.length; i++){
            Object data[] = colorData[i];
            this.add(
                new ColorData(
                    (String)data[0],
                    (Color)data[1],
                    (String)data[2]));
        }
        
    }

    public static class ColorData {
        private String name;
        private Color color;
        private String subRegions;
        
        ColorData(String name, Color color, String subRegions){
            this.name = name;
            this.color = color;
            this.subRegions = subRegions;
        }
        
        public String getName(){
            return name;
        }
        
        public void setName(String name){
            this.name = name;
        }
        
        public Color getColor(){
            return color;
        }
        
        public void setColor(Color color){
            this.color = color;
        }
        
        public String getSubRegions() {
            return this.subRegions;
        }
                
        public void setSubRegions(String subRegions) {
            this.subRegions = subRegions;
        }
        
        public String toString(){
            return "ColorData[name=" + name + "]";
        }
    }

      private static Color color1 = new Color(180, 48, 48);
      private static Color color2 = new Color(64, 86, 149);
      private static Color color3 = new Color(122, 158, 198);
      private static Color color4 = new Color(180, 48, 180);
      
      
    private static final Object colorData[][] = 
    {
    {"NorthEast", color1, "ME,NH,VT,MA,RI,CT,NY,PA,NJ"},
    {"MidWest",   color2, "WI,MI,IL,IN,OH,ND,SD,NE,KS,MN,IA,MO"},
    {"West",      color3, "ID,MT,WY,NV,UT,CO,AZ,NM,AK,WA,OR,CA,HI"},
    {"South",     color4, "DE,MD,VA,WV,NC,SC,GA,FL,KY,TN,MS,AL,OK,TX,AR,LA,DC"},
    };
}

