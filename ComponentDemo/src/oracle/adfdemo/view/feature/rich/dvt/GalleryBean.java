package oracle.adfdemo.view.feature.rich.dvt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Managed bean for the data visualization gallery page.
 */
public class GalleryBean {
  
  private static final List<ThumbGalleryItem> thumbGalleryList = new ArrayList<ThumbGalleryItem>();
  static {
    thumbGalleryList.add(new ThumbGalleryItem("chartZoomAndScroll.png", "feature.chartZoomAndScroll", "Chart Zoom and Scroll"));
    thumbGalleryList.add(new ThumbGalleryItem("diagramArcLayout.png", "feature.diagramArcLayout", "Les Miserables Arc Diagram"));
    thumbGalleryList.add(new ThumbGalleryItem("mapBurlington.png", "guide.geoMapMultipleThemes", "Geographic Map"));
    thumbGalleryList.add(new ThumbGalleryItem("mapRedwoodShores.png", "guide.geoMapMultipleThemes", "Geographic Map"));
    thumbGalleryList.add(new ThumbGalleryItem("sunburstRootContent.png", "feature.sunburstRootContent", "Sunburst Root Content"));
    thumbGalleryList.add(new ThumbGalleryItem("tmapElectionFlorida.png", "feature.tmapElection", "2012 Florida Election Results"));
    thumbGalleryList.add(new ThumbGalleryItem("tmapFlightTracker.png", "feature.tmapFlightTracker", "Flight Tracker"));
    thumbGalleryList.add(new ThumbGalleryItem("tmapGDP.png", "feature.tmapGlobalGDP", "2012 Global GDP"));
    thumbGalleryList.add(new ThumbGalleryItem("tmapTerritories.png", "feature.tmapTerritories", "Sales Territories"));
  }
  
  private List<ThumbGalleryItem> myThumbGallery;
  
  public GalleryBean() {
    // Shuffle the thumb gallery for each session. Can't shuffle for each request because that breaks action support.
    myThumbGallery = new ArrayList<ThumbGalleryItem>(thumbGalleryList);
    Collections.shuffle(myThumbGallery);
  }
  
  /**
   * Returns the thumbnail gallery items in randomized order.
   */
  public List<ThumbGalleryItem> getThumbGallery() {
    return myThumbGallery;
  }
  
  /**
   * Returns a helper that enables declarative EL for action attributes.
   */
  public ActionHelper getActionHelper() {
    return new ActionHelper();
  }
  
  /**
   * Helper class enabling declarative EL for action attributes.  Should be used like: #{actionHelper['myAction'].action
   */
  @SuppressWarnings("oracle.jdeveloper.java.serialversionuid-field-missing")
  public static class ActionHelper extends HashMap {
    private String action = null;
    
    @Override
    public Object get(Object action) {
      this.action = String.valueOf(action);
      return this;
    }
    
    public String action() {
      return this.action;
    }
  }
  
  /**
   * Class representing a single entry in the thumbnail gallery.
   */
  public static class ThumbGalleryItem {
    private static final String THUMB_URL = "/resources/images/dataVisualization/overviewThumbs/";
    
    private final String action;
    private final String shortDesc;
    private final String source;
    private final int height;
    private final int leftOffset;
    private final int topOffset;
    
    public ThumbGalleryItem(String source, String action, String shortDesc) {
      this(source, action, shortDesc, 125, 0, 0);
    }
    
    public ThumbGalleryItem(String source, String action, String shortDesc, int height) {
      this(source, action, shortDesc, height, 0, 0);
    }
    
    public ThumbGalleryItem(String source, String action, String shortDesc, int height, int topOffset, int leftOffset) {
      this.source = THUMB_URL + source;
      this.action = action;
      this.shortDesc = shortDesc;
      this.height = height;
      this.topOffset = topOffset;
      this.leftOffset = leftOffset;
    }
    
    public String action() {
      return this.action;
    }
    
    public String getShortDesc() {
      return this.shortDesc;
    }
    
    public String getSource() {
      return this.source;
    }
    
    public String getInlineStyle() {
      StringBuilder ret = new StringBuilder();
      ret.append("height:").append(height).append("px;");
      
      if(topOffset != 0)
        ret.append("top:").append(topOffset).append("px;");
      
      if(leftOffset != 0)
        ret.append("left:").append(leftOffset).append("px;");
      
      return ret.toString();
    }
  }
}