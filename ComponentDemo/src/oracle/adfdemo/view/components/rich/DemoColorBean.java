/*
** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved.
**
**345678901234567890123456789012345678901234567890123456789012345678901234567890
*/
package oracle.adfdemo.view.components.rich;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Managed bean for color component demos.
 * @version $Name:  $ ($Revision: /main/5 $) $Date: 2014/02/03 08:09:39 $
 */
public class DemoColorBean implements java.io.Serializable
{
  public Color getColorValue1()
  {
    return _colors[0];
  }

  public void setColorValue1(Color colorValue)
  {
    _colors[0] = colorValue;
  }

  public Color getColorValue2()
  {
    return _colors[1];
  }

  public void setColorValue2(Color colorValue)
  {
    _colors[1] = colorValue;
  }

  public Color getColorValue3()
  {
    return _colors[2];
  }

  public void setColorValue3(Color colorValue)
  {
    _colors[2] = colorValue;
  }

  public Color getColorValue4()
  {
    return _colors[3];
  }

  public void setColorValue4(Color colorValue)
  {
    _colors[3] = colorValue;
  }
  
  public Color getColorValue5()
  {
    return _colors[4];
  }

  public void setColorValue5(Color colorValue)
  {
    _colors[4] = colorValue;
  }

  public Color getColorValue6()
  {
    return _colors[5];
  }
  
  public void setColorValue6(Color colorValue)
  {
    _colors[5] = colorValue;
  }    

  public List getCustomColorList()
  {    
    // bug 6111483 : customColorList used to contain colors that are not in the Resource Bundle
    // Changed this to contain colors from the last 12 of the 121 colors in the Resource Bundle,
    // i.e. colors 109-121 in the 121-color palette.
    // Hence, this can be used with demos or tests with width <= 10 without duplication 
    List colorList = new ArrayList(12);
    for(int i = 0; i<_customColorsStr11.length; i++)
    {
      try
      {
        colorList.add (new Color(Integer.parseInt(_customColorsStr11[i], 16)));
      } 
      catch(Exception e)
      {
        System.out.println("ParseCustomError: "+i+" "+_customColorsStr11[i]);
      }
    }

   return colorList;
  }

  public List getColorList()
  {
    List colorList = new ArrayList();
    colorList.add(null);
    
    // bug 6111483 : colorList used to contain colors that are not in the Resource Bundle because it
    // had 144 colors, and our resource bundle only provides names for 121 and some extra colors for 49, 64. 
    // Changed this to contain the colors from the resource Bundle (121 + 1st color from 49/64 palette), 
    // but in reverse order from the color table. This should be used for demos/tests with width <= 11.
    // 
    // If customColorList is also displayed, the demo/test component should have width=10 because the 
    // customColorList contains colors 109-121 in the 121-color palette, and there would be duplication 
    // of colors shown between the colorList and customColorList.
    for(int i = 1; i<_colorsStr121.length; i++)
    {
    try{
      colorList.add(new Color(Integer.parseInt(_colorsStr121[i], 16)));
    }catch(Exception e){System.out.println("ParseError: "+i+_colorsStr121[i]);}
    }
    return colorList;
  }
  
  private Color[] _colors = { new Color(255, 0, 0),
                              new Color(0, 255, 0),
                              new Color(0, 0, 255),
                              new Color(255, 255, 0),
                              new Color(153, 255, 153),
                              new Color(255, 0, 255),
                              new Color(0, 255, 255),
                              new Color(255, 255, 255),
                              new Color(128, 0, 0),
                              new Color(0, 128, 0),
                              new Color(0, 0, 128),
                              new Color(128, 128, 0),
                              new Color(128, 0, 128),
                              new Color(0, 128, 128),
                              new Color(64, 64, 0),
                              new Color(64, 0, 64),
                              new Color(0, 64, 64)
                            };
  
  private String[] _colorsStr121 = {
    "800080", "C71585", "800000", "FF0000", "CD853F", "556B2F", "008080", "191970", "000080", "000000", "FFFAF0", 
    "8B008B", "FF1493", "8B0000", "DC143C", "B8860B", "006400", "5F9EA0", "483D8B", "00008B", "2F4F4F", "FDF5E6",
    "9400D3", "FF00FF", "A52A2A", "CD5C5C", "DAA520", "008000", "008B8B", "4682B4", "4B0082", "696969", "F5F5DC",
    "8A2BE2", "DB7093", "B22222", "FF6347", "F4A460", "6B8E23", "228B22", "00CED1", "0000CD", "808080", "FFF5EE",
    "9932CC", "FF69B4", "8B4513", "FF7F50", "BC8F8F", "808000", "2E8B57", "48D1CC", "0000FF", "708090", "F8F8FF",
    "BA55D3", "DA70D6", "A0522D", "FA8072", "D2B48C", "32CD32", "3CB371", "40E0D0", "6A5ACD", "778899", "F0F8FF",
    "9370DB", "EE82EE", "D2691E", "F08080", "DEB887", "9ACD32", "20B2AA", "7FFFD4", "4169E1", "A9A9A9", "F0FFFF",
    "7B68EE", "DDA0DD", "FF4500", "E9967A", "F5DEB3", "00FF00", "8FBC8F", "00FFFF", "1E90FF", "C0C0C0", "F5FFFA",
    "6495ED", "FFB6C1", "FF8C00", "FFA07A", "FFE4C4", "7CFC00", "66CDAA", "B0E0E6", "00BFFF", "D3D3D3", "F0FFF0",
    "B0C4DE", "FFC0CB", "FFA500", "FFDEAD", "FFEBCD", "7FFF00", "00FA9A", "AFEEEE", "87CEFA", "DCDCDC", "FFFFFF",
    "D8BFD8", "FFE4E1", "FFFF00", "FAF0E6", "FFF8DC", "ADFF2F", "90EE90", "E0FFFF", "87CEEB", "F5F5F5", "FFDAB9"
  };
  
  private String[] _customColorsStr11 = {
      "FFFAF0", "000000", "000080", "191970", "008080", "556B2F", 
      "CD853F", "FF0000", "800000", "C71585", "800080", "8B008B"
  };
  
}
