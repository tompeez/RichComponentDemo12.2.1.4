package oracle.adfdemo.view.feature.rich.dvt.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import oracle.adfdemo.view.feature.rich.chart.ChartDataSource.ChartDataItem;

public class WorldPopulation {
  
  /**
   * Object representing the data for a single row of the model.
   */
  public static class ChartReferenceItem extends HashMap<String, Object> {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    
    public ChartReferenceItem(Object group, Number min, Number max) {
      put("group", group);
      put("min", min);
      put("max", max);
    }  
  }

  private static ArrayList<ChartDataItem> worldPop;
  private static ArrayList<ChartReferenceItem> worldRange;
  
  private static int[] actualYear = {1950, 1955, 1960, 1965, 1970, 1975, 1980, 1985, 1990, 1995, 2000, 2005, 2010};
  private static int[] projectedYear = {2010, 2015, 2020, 2025, 2030, 2035, 2040, 2045, 2050, 2055, 2060, 2065, 2070, 2075, 2080, 2085, 2090, 2095, 2100};
  
  // World Population
  private static int[] actualPop = {2526, 2762, 3026, 3329, 3691, 4071, 4449, 4863, 5320, 5742, 6128, 6514, 6916};
  private static int[] projectedPopHigh = {6916, 7392, 7893, 8398, 8882, 9359, 9848, 10352, 10868, 11389, 11911, 12443, 12989, 13556, 14142, 14748, 15371, 16004, 16641};
  private static int[] projectedPopMedium = {6916, 7325, 7717, 8083, 8425, 8743, 9039, 9308, 9551, 9766, 9957, 10127, 10277, 10409, 10524, 10626, 10717, 10794, 10854};
  private static int[] projectedPopLow = {6916, 7257, 7539, 7768, 7969, 8135, 8255, 8324, 8342, 8315, 8249, 8149, 8017, 7852, 7661, 7450, 7225, 6992, 6750};
  
  private static int[] actualAfricaPop = {
    2526, 2762, 3026, 3329, 3691, 4071, 4449, 4863, 5320, 5742, 6128, 6514, 6916
  };
  
  private static int[] actualAsiaPop = {
    2526, 2762, 3026, 3329, 3691, 4071, 4449, 4863, 5320, 5742, 6128, 6514, 6916
  };
  
  private static int[] actualEuropePop = {
    2526, 2762, 3026, 3329, 3691, 4071, 4449, 4863, 5320, 5742, 6128, 6514, 6916
  };
  
  private static int[] actualLAmericaPop = {
    2526, 2762, 3026, 3329, 3691, 4071, 4449, 4863, 5320, 5742, 6128, 6514, 6916
  };
  
  private static int[] actualNAmericaPop = {
    2526, 2762, 3026, 3329, 3691, 4071, 4449, 4863, 5320, 5742, 6128, 6514, 6916
  };
  
  private static int[] actualOceaniaPop = {
    2526, 2762, 3026, 3329, 3691, 4071, 4449, 4863, 5320, 5742, 6128, 6514, 6916
  };
  
  
  
  
  private static int[] projectedAsiaPopHigh = {
    6916, 7392, 7893, 8398, 8882, 9359, 9848, 10352, 10868, 11389, 11911, 12443, 12989, 13556, 14142, 14748, 15371, 16004, 16641 
  };
  private static int[] projectedAsiaPopMedium = {
    6916, 7325, 7717, 8083, 8425, 8743, 9039, 9308, 9551, 9766, 9957, 10127, 10277, 10409, 10524, 10626, 10717, 10794, 10854
  };
  private static int[] projectedAsiaPopLow = {
    6916, 7257, 7539, 7768, 7969, 8135, 8255, 8324, 8342, 8315, 8249, 8149, 8017, 7852, 7661, 7450, 7225, 6992, 6750
  };
  
  private static int[] projectedAfricaPopHigh = {
    6916, 7392, 7893, 8398, 8882, 9359, 9848, 10352, 10868, 11389, 11911, 12443, 12989, 13556, 14142, 14748, 15371, 16004, 16641 
  };
  private static int[] projectedAfricaPopMedium = {
    6916, 7325, 7717, 8083, 8425, 8743, 9039, 9308, 9551, 9766, 9957, 10127, 10277, 10409, 10524, 10626, 10717, 10794, 10854
  };
  private static int[] projectedAfricaPopLow = {
    6916, 7257, 7539, 7768, 7969, 8135, 8255, 8324, 8342, 8315, 8249, 8149, 8017, 7852, 7661, 7450, 7225, 6992, 6750
  };
  
  private static int[] projectedEuropePopHigh = {
    6916, 7392, 7893, 8398, 8882, 9359, 9848, 10352, 10868, 11389, 11911, 12443, 12989, 13556, 14142, 14748, 15371, 16004, 16641 
  };
  private static int[] projectedEuropePopMedium = {
    6916, 7325, 7717, 8083, 8425, 8743, 9039, 9308, 9551, 9766, 9957, 10127, 10277, 10409, 10524, 10626, 10717, 10794, 10854
  };
  private static int[] projectedEuropePopLow = {
    6916, 7257, 7539, 7768, 7969, 8135, 8255, 8324, 8342, 8315, 8249, 8149, 8017, 7852, 7661, 7450, 7225, 6992, 6750
  };
  
  private static int[] projectedLAmericaPopHigh = {
    6916, 7392, 7893, 8398, 8882, 9359, 9848, 10352, 10868, 11389, 11911, 12443, 12989, 13556, 14142, 14748, 15371, 16004, 16641 
  };
  private static int[] projectedLAmericaPopMedium = {
    6916, 7325, 7717, 8083, 8425, 8743, 9039, 9308, 9551, 9766, 9957, 10127, 10277, 10409, 10524, 10626, 10717, 10794, 10854
  };
  private static int[] projectedLAmericaPopLow = {
    6916, 7257, 7539, 7768, 7969, 8135, 8255, 8324, 8342, 8315, 8249, 8149, 8017, 7852, 7661, 7450, 7225, 6992, 6750
  };
  
  private static int[] projectedNAmericaPopHigh = {
    6916, 7392, 7893, 8398, 8882, 9359, 9848, 10352, 10868, 11389, 11911, 12443, 12989, 13556, 14142, 14748, 15371, 16004, 16641 
  };
  private static int[] projectedNAmericaPopMedium = {
    6916, 7325, 7717, 8083, 8425, 8743, 9039, 9308, 9551, 9766, 9957, 10127, 10277, 10409, 10524, 10626, 10717, 10794, 10854
  };
  private static int[] projectedNAmericaPopLow = {
    6916, 7257, 7539, 7768, 7969, 8135, 8255, 8324, 8342, 8315, 8249, 8149, 8017, 7852, 7661, 7450, 7225, 6992, 6750
  };
  
  private static int[] projectedOceaniaPopHigh = {
    6916, 7392, 7893, 8398, 8882, 9359, 9848, 10352, 10868, 11389, 11911, 12443, 12989, 13556, 14142, 14748, 15371, 16004, 16641 
  };
  private static int[] projectedOceaniaPopMedium = {
    6916, 7325, 7717, 8083, 8425, 8743, 9039, 9308, 9551, 9766, 9957, 10127, 10277, 10409, 10524, 10626, 10717, 10794, 10854
  };
  private static int[] projectedOceaniaPopLow = {
    6916, 7257, 7539, 7768, 7969, 8135, 8255, 8324, 8342, 8315, 8249, 8149, 8017, 7852, 7661, 7450, 7225, 6992, 6750
  };

 

  public WorldPopulation() {
    worldPop = new ArrayList<ChartDataItem>();
    worldRange = new ArrayList<ChartReferenceItem>();
    int i;
    
    for (i=0; i<actualYear.length; i++) {
      worldPop.add(new ChartDataItem("World", new GregorianCalendar(actualYear[i], 0, 1).getTime(), actualPop[i]));
    }
    
    Date date;
    for (i=0; i<projectedYear.length; i++) {
      date = new GregorianCalendar(projectedYear[i], 0, 1).getTime();
      worldPop.add(new ChartDataItem("WorldH", date, projectedPopHigh[i]));
      worldPop.add(new ChartDataItem("WorldM", date, projectedPopMedium[i]));
      worldPop.add(new ChartDataItem("WorldL", date, projectedPopLow[i]));
      worldRange.add(new ChartReferenceItem(date, projectedPopLow[i], projectedPopHigh[i]));
    }
  }

  public List<ChartDataItem> getWorldPop() {
    return worldPop;
  }
  
  public List<ChartReferenceItem> getWorldRange() {
    return worldRange;
  }
}
