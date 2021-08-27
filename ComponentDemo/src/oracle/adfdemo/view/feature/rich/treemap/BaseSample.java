package oracle.adfdemo.view.feature.rich.treemap;

import java.util.Random;

import oracle.adfdemo.view.feature.rich.dvt.data.CensusData;
import oracle.adfdemo.view.feature.rich.dvt.data.CensusData.CensusTreeNode;
import oracle.adfdemo.view.feature.rich.treemap.data.SparseTreeNodeCreator;
import oracle.adfdemo.view.feature.rich.treemap.data.TreeNode;
import oracle.adfdemo.view.feature.rich.treemap.data.TreeNodeCreator;
import oracle.adfdemo.view.feature.rich.treemap.data.UnbalancedTreeNodeCreator;

import org.apache.myfaces.trinidad.component.UIXHierarchy;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;


public class BaseSample {
  // Generic Faces Attrs
  private String contentDelivery = "immediate";

  // Data Model Attrs
  private TreeModel currentModel;
  private String dataType = "Random";
  private int depth = 2;
  private int breadth = 5;
  private int seed = 23;
  private int displayLevelsChildren = 2;  
  private final CensusData censusData = new CensusData();
  private String censusRoot = "United States";

  // Component Attrs
  private String sorting = "off";
  private int animationDuration = 500;
  private String animationOnDisplay = "alphaFade";
  private String animationOnDataChange = "auto";
  private String emptyText = "Empty Treemap";
  private String labelStyle;
  private String selectionMode = "multiple";
  protected String labelDisplay = "node";
  private double otherThreshold = 0.10;
  private String otherThresholdBasis ="parentPercentage";
  
  private String noteWindowMessage = null;
  
  // Data Models: Instance needs to be consistent across requests
  private final TreeModel DATA_UNEVEN_HIERARCHY = (new SparseTreeNodeCreator()).newModel(4, 5, new Random(36));
  private final TreeModel DATA_PATTERN = (new TreeNodeCreator()).newModel(2, 6, new Random(23));
  private final TreeModel DATA_DRILL_INSERT = (new TreeNodeCreator()).newModel(4, 3, new Random(23));
  private final TreeModel DATA_MULTI_ROOTED = (new UnbalancedTreeNodeCreator()).newMultiRootedModel(5, 2, 4, new Random(46));
  private final TreeModel DATA_UNIFORM = (new TreeNodeCreator()).newModel(2, 3, new Random(23));
  private final TreeModel DATA_EMPTY_DEPTH = (new TreeNodeCreator()).newModel(0, 2, new Random(23));
  private final TreeModel DATA_EMPTY_BREADTH = (new TreeNodeCreator()).newModel(2, 0, new Random(23));

  public TreeModel getNullData() {
    return null;
  }

  public TreeModel getEmptyDataDepth() {
    return DATA_EMPTY_DEPTH;
  }

  public TreeModel getEmptyDataBreadth() {
    return DATA_EMPTY_BREADTH;
  }

  public TreeModel getUniformData() {
    return DATA_UNIFORM;
  }

  public TreeModel getUnevenHierarchyData() {
    return DATA_UNEVEN_HIERARCHY;
  }

  public TreeModel getPatternData() {
    return DATA_PATTERN;
  }
  
  public TreeModel getDrillInsertData() {
    return DATA_DRILL_INSERT;
  }
  
  public TreeModel getMultiRootedData() {
    return DATA_MULTI_ROOTED;
  }

  public TreeModel getCensusRootData() {
    return censusData.getUnitedStatesData();
  }

  public TreeModel getCensusData() {
    if ("West Region".equals(censusRoot))
      return censusData.getRegionWestData();
    else if ("South Region".equals(censusRoot))
      return censusData.getRegionSouthData();
    else if ("Midwest Region".equals(censusRoot))
      return censusData.getRegionMidwestData();
    else if ("Northeast Region".equals(censusRoot))
      return censusData.getRegionNortheastData();
    else if ("Pacific Division".equals(censusRoot))
      return censusData.getDivisionPacificData();
    else
      return censusData.getUnitedStatesData();
  }

  public void setDisplayLevelsChildren(int displayLevelsChildren) {
    this.displayLevelsChildren = displayLevelsChildren;
  }

  public int getDisplayLevelsChildren() {
    return displayLevelsChildren;
  }

  public TreeModel getData() {
    // Return cached data model if available
    if(currentModel != null)
      return currentModel;
    
    // Otherwise generate, cache, and return the model    
    if ("Census Data".equals(dataType))
      currentModel = getCensusData();
    else {
      // Reset the display levels children
      this.displayLevelsChildren = 3;

      if ("Uniform".equals(dataType))
        currentModel = (new TreeNodeCreator()).newModel(depth, breadth, new Random(seed));
      else if ("Empty Data".equals(dataType))
        return null;
      else
        currentModel = (new UnbalancedTreeNodeCreator()).newModel(depth, breadth, breadth, new Random(seed));
    }
    
    return currentModel;
  }

  public String getStatus() {
    return ((int)(Math.pow(breadth, depth))) + " Nodes";
  }

  public void setDepth(int depth) {    
    this.depth = depth;
    
    // Clear the cached data model because it has changed
    currentModel = null;
  }

  public int getDepth() {
    return depth;
  }

  public void setBreadth(int breadth) {
    this.breadth = breadth;
    
    // Clear the cached data model because it has changed
    currentModel = null;
  }

  public int getBreadth() {
    return breadth;
  }

  public void setSeed(int seed) {
    this.seed = seed;
    
    // Clear the cached data model because it has changed
    currentModel = null;
  }

  public int getSeed() {
    return seed;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
    
    // Clear the cached data model because it has changed
    currentModel = null;
  }

  public String getDataType() {
    return dataType;
  }

  public void setCensusRoot(String censusRoot) {
    this.censusRoot = censusRoot;
    
    // Clear the cached data model because it has changed
    currentModel = null;
  }

  public String getCensusRoot() {
    return censusRoot;
  }

  public void setContentDelivery(String contentDelivery) {
    this.contentDelivery = contentDelivery;
  }

  public String getContentDelivery() {
    return contentDelivery;
  }

  public String getDataFacet() {
    if (dataType.equals("Random") || dataType.equals("Uniform"))
      return "Custom";
    else
      return dataType;
  }

  public void setSelectionMode(String selectionMode) {
    this.selectionMode = selectionMode;
  }

  public String getSelectionMode() {
    return selectionMode;
  }
  
  public void selectionListener(SelectionEvent event) {
    
  }

  public void setLabelDisplay(String labelDisplay) {
    this.labelDisplay = labelDisplay;
  }

  public String getLabelDisplay() {
    return labelDisplay;
  }

  public void setEmptyText(String emptyText) {
    this.emptyText = emptyText;
  }

  public String getEmptyText() {
    return emptyText;
  }

  public void setLabelStyle(String labelStyle) {
    this.labelStyle = labelStyle;
  }

  public String getLabelStyle() {
    return labelStyle;
  }
  
  public void setSorting(String sorting) {
    this.sorting = sorting;
  }
  
  public String getSorting() {
    return sorting;
  }

  public void setAnimationDuration(int animationDuration) {
    this.animationDuration = animationDuration;
  }

  public int getAnimationDuration() {
    return animationDuration;
  }
  
  public void setAnimationOnDisplay(String animationOnDisplay) {
    this.animationOnDisplay = animationOnDisplay;
  }

  public String getAnimationOnDisplay() {
    return animationOnDisplay;
  }

  public void setAnimationOnDataChange(String animationOnDataChange) {
    this.animationOnDataChange = animationOnDataChange;
  }

  public String getAnimationOnDataChange() {
    return animationOnDataChange;
  }

  /**
   * Converts the rowKeySet into a string of node text labels.
   * @param rowKeySet
   * @param hierarchy
   * @return
   */
  public static String convertToString(RowKeySet rowKeySet, UIXHierarchy hierarchy) {
    StringBuilder s = new StringBuilder();
    if (rowKeySet != null) {
      for (Object rowKey : rowKeySet) {
        TreeNode rowData = (TreeNode)hierarchy.getRowData(rowKey);
        s.append(rowData.getText()).append(", ");
      }

      // Remove the trailing comma
      if (s.length() > 0)
        s.setLength(s.length() - 2);
    }
    return s.toString();
  }
  
  public void setNoteWindowMessage(String noteWindowMessage) {
    this.noteWindowMessage = noteWindowMessage;
  }

  public String getNoteWindowMessage() {
    return noteWindowMessage;
  }

  public void setOtherThreshold(double otherThreshold) {
    this.otherThreshold = otherThreshold;
  }
    
  public double getOtherThreshold() {
    return otherThreshold;
  }
  
  public void setOtherThresholdBasis(String otherThresholdBasis) {
    this.otherThresholdBasis = otherThresholdBasis;
  }

  public String getOtherThresholdBasis() {
    return otherThresholdBasis;
  }
  
  public String otherColor(RowKeySet set) {
    // The color should be the mean income of the contained regions.  Note that it should actually
    // be the median, but we can't calculate that with the available information.
    TreeModel tree = getCensusRootData();
    
    // Loop through and get the population + average income
    double population = 0;
    double average = 0;
    for(Object rowKey : set) {
      CensusData.CensusTreeNode item = (CensusData.CensusTreeNode) tree.getRowData(rowKey);
      population += item.getSize().doubleValue();
      average += item.getSize().doubleValue() * item.getIncome();
    }
    
    // Calculate the average
    average = average / population;
    
    // Match the attr groups used by the demos
    return average > 50000 ? "#68C182" : "#ED6647";
  }
  }
