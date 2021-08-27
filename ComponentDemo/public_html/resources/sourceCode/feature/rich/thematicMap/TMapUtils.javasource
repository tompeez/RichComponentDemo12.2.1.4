package oracle.adfdemo.view.feature.rich.thematicMap;

import java.util.Random;

import javax.faces.component.UIComponent;

import oracle.adf.view.faces.bi.component.thematicMap.UIDataLayer;
import oracle.adf.view.faces.bi.component.thematicMap.UIThematicMap;

public class TMapUtils {
    public TMapUtils() {
        super();
    }
    
  public static UIThematicMap getThematicMap(UIComponent comp) 
  {
      UIComponent curComp = comp;
      while (curComp != null && !(curComp instanceof UIThematicMap)) {
          curComp = curComp.getParent();
      }
      
      if (curComp != null && curComp instanceof UIThematicMap)
          return (UIThematicMap) curComp;
      else
          return null;
  }
  
  public static UIDataLayer getDataLayer(UIComponent comp) 
  {
      UIComponent curComp = comp;
      while (curComp != null && !(curComp instanceof UIDataLayer)) {
          curComp = curComp.getParent();
      }
      
      if (curComp != null && curComp instanceof UIDataLayer)
          return (UIDataLayer) curComp;
      else
          return null;
  }
  
    public static Integer[] getRandomInts(int size, int max){
        Integer[] data = new Integer[size];
        Random rand = new Random((long)Math.floor(Long.MAX_VALUE * Math.random()));
        for(int i=0; i<size; i++){
            data[i] = rand.nextInt(max);
        }
        
        return data;
    }
    
    public static Float[] getGaussian(int size){
        Float[] data = new Float[size];
        Random rand = new Random((long)Math.floor(Long.MAX_VALUE * Math.random()));
        for(int i=0; i<size; i++){
            float mean = rand.nextFloat() * 100;
            float stddev = 30 * (float) (rand.nextFloat() + .5);
            data[i] = mean + (float)rand.nextGaussian() * stddev;
        }
        
        return data;
    }
      
    protected static String[] stateAbbrevs = new String[] {
    "AL",
    "AK",
    "AZ",
    "AR",
    "CA",
    "CO",
    "CT",
    "DE",
    "FL",
    "GA",
    "HI",
    "ID",
    "IL",
    "IN",
    "IA",
    "KS",
    "KY",
    "LA",
    "ME",
    "MD",
    "MA",
    "MI",
    "MN",
    "MS",
    "MO",
    "MT",
    "NE",
    "NV",
    "NH",
    "NJ",
    "NM",
    "NY",
    "NC",
    "ND",
    "OH",
    "OK",
    "OR",
    "PA",
    "RI",
    "SC",
    "SD",
    "TN",
    "TX",
    "UT",
    "VT",
    "VA",
    "WA",
    "WV",
    "WI",
    "WY"};
  
    protected static String[] stateNames = new String[] {
    "Alabama",
    "Alaska",
    "Arizona",
    "Arkansas",
    "California",
    "Colorado",
    "Connecticut",
    "Delaware",
    "Florida",
    "Hawaii",
    "Georgia",
    "Idaho",
    "Illinois",
    "Indiana",
    "Iowa",
    "Kansas",
    "Kentucky",
    "Louisiana",
    "Maine",
    "Maryland",
    "Massachusetts",
    "Michigan",
    "Minnesota",
    "Mississippi",
    "Missouri",
    "Montana",
    "Nebraska",
    "Nevada",
    "New Hampshire",
    "New Jersey",
    "New Mexico",
    "New York",
    "North Carolina",
    "North Dakota",
    "Ohio",
    "Oklahoma",
    "Oregon",
    "Pennsylvania",
    "Rhode Island",
    "South Carolina",
    "South Dakota",
    "Tennessee",
    "Texas",
    "Utah",
    "Vermont",
    "Virginia",
    "Washington",
    "West Virginia",
    "Wisconsin",
    "Wyoming"};
    
    protected static String[] countyIds = new String[] {
        "MA_BARNSTABLE",
        "MA_BERKSHIRE",
        "MA_LOWELL",
        "MA_MIDDLESEX"
        
    };
                                             
}
