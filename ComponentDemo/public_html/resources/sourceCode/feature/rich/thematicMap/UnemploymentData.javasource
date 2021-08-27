package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import oracle.adf.view.faces.bi.component.thematicMap.UIThematicMap;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class UnemploymentData {

  private ArrayList<SamplePointData.TestData> m_pointData;
  private static final Object colorData[][] = {
    { "ALABAMA", "AL", 6.5, 11, 4.5 }, { "ALASKA", "AK", 6.8, 8.8, 2 }, { "ARIZONA", "AZ", 6.6, 9.1, 2.5 },
    { "ARKANSAS", "AR", 5.7, 7.7, 2 }, { "CALIFORNIA", "CA", 8.7, 12.4, 3.7 }, { "COLORADO", "CO", 5.8, 7.5, 1.7 },
    { "CONNECTICUT", "CT", 6.6, 8.9, 2.3 }, { "DELAWARE", "DE", 5.7, 9, 3.3 },
    { "District of Columbia", "DC", 8.2, 12.1, 3.9 }, { "FLORIDA", "FL", 7.6, 11.8, 4.2 },
    { "GEORGIA", "GA", 7.5, 10.3, 2.8 }, { "HAWAII", "HI", 5.1, 6.9, 1.8 }, { "IDAHO", "ID", 6.1, 9.1, 3 },
    { "ILLINOIS", "IL", 7.2, 11.1, 3.9 }, { "INDIANA", "IN", 7.8, 9.9, 2.1 }, { "IOWA", "IA", 4.4, 6.6, 2.2 },
    { "KANSAS", "KS", 5, 6.6, 1.6 }, { "KENTUCKY", "KY", 7.6, 10.7, 3.1 }, { "LOUISIANA", "LA", 5.5, 7.5, 2 },
    { "MAINE", "ME", 6.5, 8.3, 1.8 }, { "MARYLAND", "MD", 5.4, 7.5, 2.1 }, { "MASSACHUSETTS", "MA", 6.4, 9.4, 3 },
    { "MICHIGAN", "MI", 10.2, 14.6, 4.4 }, { "MINNESOTA", "MN", 6.6, 7.4, 0.8 },
    { "MISSISSIPPI", "MS", 7.8, 10.6, 2.8 }, { "MISSOURI", "MO", 7.1, 9.6, 2.5 }, { "MONTANA", "MT", 5, 6.7, 1.7 },
    { "NEBRASKA", "NE", 3.9, 4.7, 0.8 }, { "NEVADA", "NV", 8.4, 13, 4.6 }, { "NEW HAMPSHIRE", "NH", 4.3, 7, 2.7 },
    { "NEW JERSEY", "NJ", 6.8, 10.1, 3.3 }, { "NEW MEXICO", "NM", 4.7, 8.3, 3.6 }, { "NEW YORK", "NY", 6.6, 9, 2.4 },
    { "NORTH CAROLINA", "NC", 8.1, 11.2, 3.1 }, { "NORTH DAKOTA", "ND", 3.3, 4.4, 1.1 },
    { "OHIO", "OH", 7.4, 10.9, 3.5 }, { "OKLAHOMA", "OK", 4.6, 6.6, 2 }, { "OREGON", "OR", 8.3, 11, 2.7 },
    { "PENNSYLVANIA", "PA", 6.4, 8.9, 2.5 }, { "RHODE ISLAND", "RI", 9.4, 12.9, 3.5 },
    { "SOUTH CAROLINA", "SC", 8.8, 12.6, 3.8 }, { "SOUTH DAKOTA", "SD", 3.7, 4.7, 1 },
    { "TENNESSEE", "TN", 7.6, 10.9, 3.3 }, { "TEXAS", "TX", 5.6, 8.3, 2.7 }, { "UTAH", "UT", 4.1, 6.7, 2.6 },
    { "VERMONT", "VT", 5.9, 6.9, 1 }, { "VIRGINIA", "VA", 5, 6.9, 1.9 }, { "WASHINGTON", "WA", 6.5, 9.5, 3 },
    { "WEST VIRGINIA", "WV", 4.5, 9.1, 4.6 }, { "WISCONSIN", "WI", 5.9, 8.7, 2.8 }, { "WYOMING", "WY", 3.2, 7.5, 4.3 }
  };

  private String m_year = "2008";
  private Color m_topColor = Color.RED;
  private Color m_bottomColor = Color.YELLOW;
  private double m_markerOpacity;
  private boolean m_disablePan;
  private String m_areaLocationChild = "area";

  public boolean getDisablePan() {
    return m_disablePan;
  }

  public void setDisablePan(boolean disablePan) {
    m_disablePan = disablePan;
  }

  public void setAreaLocationChild(String child) {
    m_areaLocationChild = child;
  }

  public String getAreaLocationChild() {
    return m_areaLocationChild;
  }

  public Set<String> getFeaturesOff() {
    Set<String> featuresOff = new HashSet<String>();
    if (m_disablePan)
      featuresOff.add(UIThematicMap.PAN);
    return featuresOff;
  }


  private static int convertHexDecimal(String hexName) {
    int result = 0;

    if (hexName != null) {
      for (int i = 0; i < hexName.length(); i++) {
        result = result * 16;

        char c = hexName.charAt(i);
        if (c == '0')
          result += 0;
        else if (c == '1')
          result += 1;
        else if (c == '2')
          result += 2;
        else if (c == '3')
          result += 3;
        else if (c == '4')
          result += 4;
        else if (c == '5')
          result += 5;
        else if (c == '6')
          result += 6;
        else if (c == '7')
          result += 7;
        else if (c == '8')
          result += 8;
        else if (c == '9')
          result += 9;
        else if (c == 'A' || c == 'a')
          result += 10;
        else if (c == 'B' || c == 'b')
          result += 11;
        else if (c == 'C' || c == 'c')
          result += 12;
        else if (c == 'D' || c == 'd')
          result += 13;
        else if (c == 'E' || c == 'e')
          result += 14;
        else if (c == 'F' || c == 'f')
          result += 15;
        else
          return -1;
      }
    }

    return result;
  }

  public static Color convertToColor(String colorName) {
    if (colorName != null && (colorName.length() == 7 || colorName.length() == 9) && colorName.charAt(0) == '#') {
      String rColor = colorName.substring(1, 3);
      String gColor = colorName.substring(3, 5);
      String bColor = colorName.substring(5, 7);

      int rVal = convertHexDecimal(rColor);
      int gVal = convertHexDecimal(gColor);
      int bVal = convertHexDecimal(bColor);

      if (rVal >= 0 && gVal >= 0 && bVal >= 0) {
        if (colorName.length() == 9) {
          String strTransparency = colorName.substring(7, 9);
          int alpha = convertHexDecimal(strTransparency);
          if (alpha >= 0)
            return new Color(rVal, gVal, bVal, alpha);
        }

        return new Color(rVal, gVal, bVal);
      }
    }

    return null;
  }

  public static String convertToHex(int number) {
    String result = "";

    while (number >= 0) {
      int curNum = number % 16;
      char c = 0;

      if (curNum < 10)
        c = String.valueOf(curNum).charAt(c);
      else if (curNum == 10)
        c = 'A';
      else if (curNum == 11)
        c = 'B';
      else if (curNum == 12)
        c = 'C';
      else if (curNum == 13)
        c = 'D';
      else if (curNum == 14)
        c = 'E';
      else if (curNum == 15)
        c = 'F';
      else
        return null;

      result = c + result;

      if (number < 16)
        break;

      number = number / 16;
    }

    if (result.length() == 1)
      result = "0" + result;

    return result;
  }

  public static String convertColorToString(Color color) {
    String result = null;

    if (color != null) {
      int red = color.getRed();
      int green = color.getGreen();
      int blue = color.getBlue();

      result = "#" + convertToHex(red) + convertToHex(green) + convertToHex(blue);
    }

    return result;
  }

  public UnemploymentData() {
    super();
    m_pointData = new SamplePointData();
    m_disablePan = true;
  }

  public void setTopColor(Color c) {
    m_topColor = c;
  }

  public Color getTopColor() {
    return m_topColor;
  }

  public void setBottomColor(Color c) {
    m_bottomColor = c;
  }

  public Color getBottomColor() {
    return m_bottomColor;
  }

  public void setYear(String year) {
    m_year = year;
  }

  public String getYear() {
    return m_year;
  }

  private int calculateNumber(int top, int bottom, int numBuckets, int bucket) {
    int min = bottom; //(top > bottom) ? top : bottom;
    int range = (top - bottom) / (numBuckets - 1);
    int value = min + range * bucket;
    if (value > 255)
      value = 255;
    if (value < 0)
      value = 0;
    return value;
  }

  public Color getColor(double data) {
    int bucket = (int) Math.floor(data / 2.0);
    if (bucket >= 4)
      bucket = 4;

    int red = calculateNumber(m_topColor.getRed(), m_bottomColor.getRed(), 5, bucket);
    int green = calculateNumber(m_topColor.getGreen(), m_bottomColor.getGreen(), 5, bucket);
    int blue = calculateNumber(m_topColor.getBlue(), m_bottomColor.getBlue(), 5, bucket);

    Color resultColor = new Color(red, green, blue);
    return resultColor;
  }

  public ArrayList<ColorData> getColorModel() {
    ArrayList<ColorData> colorModel = new ArrayList<ColorData>();
    int column = 2;
    if (m_year.equalsIgnoreCase("2009")) {
      column = 3;
    }
    /*
        long t0 = System.currentTimeMillis();
        long t1 = t0;

        do {
            t1 = System.currentTimeMillis();
        } while ((t1-t0) < 1000.0);
*/
    categoryMap.clear();
    for (int bucket = 0; bucket < 5; bucket++) {
      Color resultColor = getColor(bucket * 2 + 1.0);
      categoryMap.put(resultColor, "Category " + bucket);
    }

    for (int i = 0; i < colorData.length; i++) {
      String fullName = (String) colorData[i][0];
      String region = (String) colorData[i][1];
      Number value = (Number) colorData[i][column];
      int size = (int) (Math.floor(value.doubleValue()));
      String symColor = "#cc3333";
      Color color = getColor(value.doubleValue());
      String category = getCategory(color);
      ColorData data = new ColorData(fullName, region, color, size, symColor, value.doubleValue(), category);
      colorModel.add(data);
    }
    Collections.sort(colorModel, new Comparator<ColorData>() {
      @Override
      public int compare(UnemploymentData.ColorData o1, UnemploymentData.ColorData o2) {
        return o1.category.compareTo(o2.category);
      }
    });
    return colorModel;
  }

  public ArrayList<SamplePointData.TestData> getPointModel() {
    return m_pointData;
  }

  public CollectionModel getEmploymentData() {
    return ModelUtils.toCollectionModel(getColorModel());
  }

  public CollectionModel getDescription() {
    return null;
  }

  public RowKeySet getDisclosedSet() {
    // Disclose "TX"
    RowKeySet set = new RowKeySetImpl();
    set.add(new Integer(43));
    return set;
  }

  private Map<Color, String> categoryMap = new HashMap<Color, String>();

  public String getCategory(Color color) {
    //        if (!categoryMap.containsKey(color)) {
    //          categoryMap.put(color, "Category "+(categoryMap.size()+1));
    //        }
    return categoryMap.get(color);
  }

  public double getMarkerOpacity() {
    return m_markerOpacity;
  }

  public void setMarkerOpacity(double markerOpacity) {
    m_markerOpacity = markerOpacity;
  }

  public static class ColorData {
    private String fullName;
    private int size;
    private String name;
    private Color color;
    private String symColor;
    private double data;
    private String category;

    ColorData(String fullName, String name, Color color, int size, String symColor, double data, String category) {
      this.fullName = fullName;
      this.name = name;
      this.color = color;
      this.size = size;
      this.symColor = symColor;
      this.data = data;
      this.category = category;
    }


    public String getFullName() {
      return this.fullName;
    }

    public void setFullName(String fullName) {
      this.fullName = fullName;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Color getColor() {
      return color;
    }

    public void setColor(Color color) {
      this.color = color;
    }

    public String toString() {
      return "ColorData[name=" + name + "]";
    }

    public int getSize() {
      return size;
    }

    public void setSize(int size) {
      this.size = size;
    }

    public String getSymColor() {
      return symColor;
    }

    public void setSymColor(String symColor) {
      this.symColor = symColor;
    }

    public double getData() {
      return data;
    }

    public void setData(double data) {
      this.data = data;
    }

    public void setCategory(String category) {
      this.category = category;
    }

    public String getCategory() {
      return category;
    }

    public void setLegendLabel() {

    }

    public String getLegendLabel() {
      if (category.equalsIgnoreCase("Category 0")) {
        return "0.0 - 2.0";
      } else if (category.equalsIgnoreCase("Category 1")) {
        return "2.0 - 4.0";
      } else if (category.equalsIgnoreCase("Category 2")) {
        return "4.0 - 6.0";
      } else if (category.equalsIgnoreCase("Category 3")) {
        return "6.0 - 8.0";
      } else if (category.equalsIgnoreCase("Category 4")) {
        return ">8.0";
      }

      return "";
    }
  }

  class SamplePointData extends ArrayList<SamplePointData.TestData> {
    public SamplePointData() {
      super();
      init(null);
    }

    public SamplePointData(String states[]) {
      super();
      init(states);
    }

    private void init(String states[]) {
      for (int i = 0; i < pointData.length; i++) {
        Object data[] = pointData[i];

        boolean found = false;
        if (states != null) {
          for (int j = 0; j < states.length; j++) {
            if (((String) data[0]).indexOf(states[j]) == 0) {
              found = true;
              break;
            }
          }
        } else {
          found = true;
        }

        if (found) {
          this.add(new TestData((String) data[0], (Double) data[1], (Double) data[2], (Color) data[3], (String) data[4],
                                (Double) data[5]));
        }
      }

    }

    public class TestData {
      private String name;
      private Color color;
      private double longitude;
      private double latitude;
      private double value;
      private String category;

      TestData(String name, double latitude, double longitude, Color color, String category, double data) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.color = color;
        this.category = category;
        this.value = data;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }


      public String getFullName() {
        return name;
      }

      public void setFullName(String name) {
        this.name = name;
      }

      public Color getColor() {
        return color;
      }

      public void setColor(Color color) {
        this.color = color;
      }

      public double getValue() {
        return value;
      }

      public double getLongitude() {
        return longitude;
      }

      public void setLongitude(double longitude) {
        this.longitude = longitude;
      }

      public double getLatitude() {
        return latitude;
      }

      public void setLatitude(double latitude) {
        this.latitude = latitude;
      }

      public String getCategory() {
        return category;
      }

      public void setCategory(String category) {
        this.category = category;
      }

      public void setValue() {
      }

    }

    private Color color1 = new Color(180, 48, 48);
    private Color color2 = new Color(64, 86, 149);
    private Color color3 = new Color(170, 0, 255);
    private Color color4 = new Color(122, 158, 198);
    private Color color5 = new Color(66, 132, 255);

    private final Object pointData[][] = {
      { "NY_NEW_YORK_CITY", 40.77, -73.98, color1, "category1", 23.5 },
      { "AK_JUNEAU", 58.37, -134.58, color2, "category1", 31.6 },
      { "AK_ANCHORAGE", 61.22, -149.9, color3, "category2", 12.5 },
      { "TX_HOUSTON", 29.97, -95.35, color4, "category2", 32.8 },
      { "HI_HONOLULU", 26.29, -157.82, color5, "category2", 25.7 },
      { "CA_SAN_FRANCISCO", 40.7749, -122.4194, color1, "category2", 36.1 },
      { "CO_DENVER", 35.7392, -104.9847, color2, "category1", 38.2 },
      { "MI_DETROIT", 42.3314, -83.0458, color3, "category2", 26.4 },
      { "ID_BOISE", 42.6126, -116.2111, color4, "category1", 43.6 },
      { "IL_CHICAGO", 41.8500, -87.6501, color5, "category1", 41.3 },
      { "GA_ATLANTA", 36.7490, -82.3880, color1, "category2", 48.3 },

    };
  }
}
