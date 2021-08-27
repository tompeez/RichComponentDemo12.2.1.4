/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/data/CircleData.java /bibeans_root/5 2014/04/28 23:24:22 jramanat Exp $ */

/* Copyright (c) 2012, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jramanat    02/07/12 - Creation
 */

package oracle.adfdemo.view.feature.rich.diagram.data;

import java.awt.Color;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/data/CircleData.java /bibeans_root/5 2014/04/28 23:24:22 jramanat Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class CircleData {
  public enum Coloring {RAINBOW, TWO_COLOR_GRADIENT, CIRCULAR_GRADIENT};
  
  private static final float SATURATION = .9f;
  private static final float BRIGHTNESS = .9f;
  private static final Color color1 = new Color(0x0572ce);
  private static final Color color2 = new Color(0xe4e8ea);
  public static CollectionModel getNodes(int size, Coloring coloring) {
    List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < size; i++) {
      Map<String, Object> node = new HashMap<String, Object>();
      node.put("id", "N" + i);
      node.put("index", i);
      node.put("color", getColor(i*1.0f/size, coloring));
      nodes.add(node);
    }
    return ModelUtils.toCollectionModel(nodes);
  }
  
  public static CollectionModel getLinks(int size, Coloring coloring) {
    List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < size; i++) {
      Map<String, Object> link = new HashMap<String, Object>();
      link.put("id", "L" + i);
      link.put("index", i);
      link.put("start", "N" + i);
      link.put("end", "N" + ((2*i+1)%size));
      link.put("color", getColor(i*1.0f/size, coloring));
      links.add(link);
    }
    return ModelUtils.toCollectionModel(links);
  }
  
  public static CollectionModel getRingLinks(int size, Coloring coloring) {
    String[] linkStyles = {"solid", "dash", "dot", "dashDot"};
    List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < size; i++) {
      Map<String, Object> link = new HashMap<String, Object>();
      link.put("id", "L" + i);
      link.put("index", i);
      link.put("start", "N" + i);
      link.put("end", "N" + ((i+1)%size));
      link.put("color", getColor(i*1.0f/size, coloring));
      link.put("style", linkStyles[i % linkStyles.length]);
      links.add(link);
    }
    return ModelUtils.toCollectionModel(links);
  }

  public static CollectionModel getLatticeLinks(int size, Coloring coloring) {
    // Creates the links for a lattice with Math.floor(Math.sqrt(size)) columns
    int columns = (int)Math.floor(Math.sqrt(size));
    List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
    int linkid = 0;
    for (int i = 0; i < size; i++) {
      int row = i / columns;
      int col = i % columns;
      if (i + columns < size) {
        // Vertical Link
        Map<String, Object> link = new HashMap<String, Object>();
        link.put("id", "L" + linkid);
        link.put("index", linkid);
        link.put("start", "N" + i);
        link.put("end", "N" + (i+columns));
        link.put("color", getColor(i*1.0f/size, coloring));
        links.add(link);
        linkid++;          
      }
      if (col + 1 < columns && (i+1 < size)) {
        // Horizontal Link
        Map<String, Object> link = new HashMap<String, Object>();
        link.put("id", "L" + linkid);
        link.put("index", linkid);
        link.put("start", "N" + i);
        link.put("end", "N" + (i+1));
        link.put("color", getColor(i*1.0f/size, coloring));
        links.add(link);
        linkid++;                  
      }
    }
    return ModelUtils.toCollectionModel(links);
  }
  
  private static String getColor(float value, Coloring coloring) {
    switch (coloring) {
      case RAINBOW:
        return getRainbowColor(value);
      case TWO_COLOR_GRADIENT:
        return getTwoColorGradientColor(value, color1, color2);
      case CIRCULAR_GRADIENT:
        return getCircularGradientColor(value, color1, color2);
    }
    return null;
  }
  
  private static String getColorString(int value) {
    String strValue = Integer.toHexString(value);
    while (strValue.length() < 6) {
      strValue = "0" + strValue;
    }
    return "#" + strValue;
  }
  
  private static float[] toHSL(Color color) {
    float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
    float hsb_h = hsb[0];
    float hsb_s = hsb[1];
    float hsb_b = hsb[2];
    float hsl_h = hsb_h;
    float hsl_l = .5f * hsb_b * (2 - hsb_s);
    float hsl_s = hsb_b * hsb_s / (1 - Math.abs(2 * hsl_l - 1));
    return new float[] {hsl_h, hsl_s, hsl_l};
  }
  
  private static Color toColor(float[] hsl) {
    float hsl_h = hsl[0];
    float hsl_s = hsl[1];
    float hsl_l = hsl[2];
    float hsb_h = hsl_h;
    float hsb_b = (2 * hsl_l + hsl_s * (1 - Math.abs(2 * hsl_l - 1)))/2;
    float hsb_s = 2 * (hsb_b - hsl_l)/hsb_b;
    return Color.getHSBColor(hsb_h, hsb_s, hsb_b);
  }
  private static String getRainbowColor(float value) {
    return getColorString(Color.HSBtoRGB(value, SATURATION, BRIGHTNESS) & 0xFFFFFF);
  }
  
  private static String getTwoColorGradientColor(float value, Color color1, Color color2) {
    float[] hsl1 = toHSL(color1);
    float[] hsl2 = toHSL(color2);
    float[] hsl = {hsl1[0] + (hsl2[0] - hsl1[0]) * value,
                   hsl1[1] + (hsl2[1] - hsl1[1]) * value,
                   hsl1[2] + (hsl2[2] - hsl1[2]) * value};
    Color color = toColor(hsl);
    return getColorString(color.getRGB() & 0xFFFFFF);
  }
  
  private static String getCircularGradientColor(float value, Color color1, Color color2) {
    return getTwoColorGradientColor(2 * (value > 0.5 ? (1 - value) : value), color2, color1);
  }
  
}