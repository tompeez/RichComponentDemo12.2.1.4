package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.faces.bi.component.thematicMap.mapProvider.LayerArea;
import oracle.adf.view.faces.bi.component.thematicMap.mapProvider.MapProvider;
import oracle.adf.view.faces.bi.component.thematicMap.mapProvider.utils.MapProviderUtils;

/**
 * Sample MapProvider instance that translates GeoJSON data from a zip file to create a custom Canada basemap.
 */
public class DemoMapProvider extends MapProvider {
  private static String zipFile = "/resources/thematicMap/elocation.zip";
  private static final ADFLogger _logger = ADFLogger.createADFLogger(DemoMapProvider.class);
  private List<String> hierarchy;

  public DemoMapProvider() {
    hierarchy = new ArrayList<String>();
    hierarchy.add("states");
  }

  @Override
  public Collection<LayerArea> getLayerAreas(String layer, Locale locale) {
    String text = getGeoJsonString();
    Map geoJSON = null;
    try {
      geoJSON = (Map) DemoMapProvider.parseJsonString(text);
    } catch (IOException e) {
      _logger.severe("Could not parse geometries.", e);
    }
    return DemoMapProvider.createLayerAreas(geoJSON);
  }

  @Override
  public Collection<LayerArea> getChildAreas(String layer, Locale locale, Collection<String> areas) {
    return new ArrayList<LayerArea>();
  }

  @Override
  public double getMaxZoomFactor() {
    return 5.0;
  }

  @Override
  public List<String> getHierarchy() {
    return hierarchy;
  }

  /**
   * Loads the geographic geometries stored in local file and returns as string
   * @return
   */
  private static String getGeoJsonString() {
    StringBuilder sb = new StringBuilder();
    BufferedReader br = null;
    ZipInputStream is = null;
    try {
      URL zipUrl = FacesContext.getCurrentInstance().getExternalContext().getResource(zipFile);
      is = new ZipInputStream(zipUrl.openStream());
      is.getNextEntry();
      br = new BufferedReader(new InputStreamReader(is, "UTF8"));
      String aux = br.readLine();
      while (aux != null) {
        sb.append(aux);
        aux = br.readLine();
      }
    } catch (Exception e) {
      _logger.severe("Could not load geometries from the file " + zipFile, e);
    } finally {
      if (br != null) {
        try {
          is.close();
          br.close();
        } catch (IOException e) { 
          // ignore
        }
      }
    }
    return sb.toString();
  }

  /**
   * Parses a JSON string and converts it to the correct Java object
   * @param str The JSON string to parse
   * @return
   * @throws IOException
   */
  private static Object parseJsonString(String str) throws IOException {
    if (str == null)
      return null;

    str = str.trim();

    char firstChar = str.charAt(0);
    if (firstChar == '[' || firstChar == '{') {
      // Array or Map
      // Count the number of open/close arrays or objects
      int numOpen = 0;

      // Quote handling: Count the number of open single/double quotes, except when there is an
      // open one already.  This handles nested single quotes in double quotes, and vice versa.
      int numSingleQuotes = 0;
      int numDoubleQuotes = 0;

      // Iterate through and split by pieces
      int prevIndex = 1;
      List<String> parts = new ArrayList<String>();
      for (int i = 1; i < str.length() - 1; i++) {
        char iChar = str.charAt(i);
        if (iChar == '[' || iChar == '{')
          numOpen++;
        else if (iChar == ']' || iChar == '}')
          numOpen--;
        else if (iChar == '\'' && numDoubleQuotes % 2 == 0)
          numSingleQuotes++;
        else if (iChar == '"' && numSingleQuotes % 2 == 0)
          numDoubleQuotes++;

        // If split index, store the substring
        if (numOpen == 0 && (numSingleQuotes % 2 == 0 && numDoubleQuotes % 2 == 0) && iChar == ',') {
          parts.add(str.substring(prevIndex, i));
          prevIndex = i + 1;
        }
      }

      // Grab the last part if present
      if (prevIndex < str.length() - 1) {
        parts.add(str.substring(prevIndex, str.length() - 1));
      }

      // Decode the parts into the result
      if (firstChar == '[') {
        List ret = new ArrayList();
        for (int arrayIndex = 0; arrayIndex < parts.size(); arrayIndex++)
          ret.add(parseJsonString(parts.get(arrayIndex)));
        return ret;
      } else if (firstChar == '{') {
        Map ret = new HashMap();
        for (String part : parts) {
          part = part.trim();
          int colonIndex = part.indexOf(':');
          String mapKey = part.substring(0, colonIndex);
          mapKey = mapKey.substring(1, mapKey.length() - 1); // 1 to -1 to avoid the quotes
          Object mapValue = parseJsonString(part.substring(colonIndex + 1, part.length()));
          ret.put(mapKey, mapValue);
        }
        return ret;
      }
      return null;
    } else if (firstChar == '"') // String
      return str.substring(1, str.length() - 1);
    else if ("true".equals(str))
      return true;
    else if ("false".equals(str))
      return false;
    else
      return Double.parseDouble(str);
  }

  /**
   * Converts a GeoJSON object to a list of LayerArea objects
   * @param geoJSON The GeoJSON object containing this basemap layer's area geometry data
   * @return
   */
  private static List<LayerArea> createLayerAreas(Map geoJSON) {
    List territories = Arrays.asList(MapProviderBean.territoryNames);
    HashMap<String, DemoLayerArea> areaMap = new HashMap<String, DemoLayerArea>();
    if (geoJSON != null) {
      List features = (List) geoJSON.get("features");
      int numFeatures = features.size();
      for (int j = 0; j < numFeatures; j++) {
        Map feature = (Map) features.get(j);
        Map properties = (Map) feature.get("properties");
        String label = (String) properties.get("POLYGON_NAME");
        // We just want to render canada
        if (!territories.contains(label))
          continue;

        Map geometry = (Map) feature.get("geometry");

        Rectangle2D labelBox = null;
        List<Double> labelBoxList = (List<Double>) feature.get("label_box");
        if (labelBoxList != null) {
          int minX = (int) (labelBoxList.get(0) / 2000);
          int minY = (int) (labelBoxList.get(1) / 2000);
          int maxX = (int) (labelBoxList.get(2) / 2000);
          int maxY = (int) (labelBoxList.get(3) / 2000);
          labelBox = new Rectangle(minX, -minY, maxX - minX, maxY - minY);
        }
        DemoLayerArea area = areaMap.get(label);
        if (area != null)
          area.setPath(area.getPath() + DemoMapProvider.simplifyGeometries(geometry));
        else
          areaMap.put(label,
                      new DemoLayerArea(label, null, label, labelBox, DemoMapProvider.simplifyGeometries(geometry),
                                        null));
      }
    }

    List<LayerArea> layerAreas = new ArrayList<LayerArea>();
    for (Map.Entry<String, DemoLayerArea> entry : areaMap.entrySet())
      layerAreas.add(entry.getValue());
    return layerAreas;
  }

  /**
   * Converts and simplifies area geometries to relative path commands
   * @param geometry The map containing an area's coordinates
   * @return
   */
  private static String simplifyGeometries(Map geometry) {
    StringBuilder sb = new StringBuilder();
    String type = (String) geometry.get("type");
    List coords = (List) geometry.get("coordinates");
    int len = coords.size();
    if ("Polygon".equals(type)) {
      for (int i = 0; i < len; i++)
        sb.append(DemoMapProvider.simplifyGeometriesHelper((List) coords.get(i)));
    } else if ("MultiPolygon".equals(type)) {
      for (int i = 0; i < len; i++) {
        List nestCoords = (List) coords.get(i);
        int nestCoordsLen = nestCoords.size();
        for (int j = 0; j < nestCoordsLen; j++)
          sb.append(DemoMapProvider.simplifyGeometriesHelper((List) coords.get(j)));
      }
    }
    return sb.toString();
  }

  /**
   * Helper method for parsing a GeoJSON geometry object
   * @param coords The list of coordinates to simplify and convert to a relative path command
   * @return
   */
  private static String simplifyGeometriesHelper(List coords) {
    List<Point2D> points = new ArrayList<Point2D>();
    int len = coords.size();
    // Convert coordinates to Point2D objects so we can use MapProviderUtils to simplify area gemoetries
    // Also reduce data precision by dividing by 2000
    for (int i = 0; i < len; i += 2)
      points.add(new Point2D.Double(Math.floor((Double) coords.get(i) / 2000),
                                    Math.floor((Double) coords.get(i + 1) / 2000)));
    return MapProviderUtils.convertToPath(points);
  }
}
