package oracle.adfdemo.view.feature.rich.dvt.data;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import oracle.adfdemo.view.feature.rich.treemap.data.TreeNode;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.TreeModel;

public class CensusData {
  
  public TreeModel getUnitedStatesData() {
    return DATA_UNITED_STATES;
  }

  public TreeModel getRegionWestData() {
    return DATA_WEST;
  }

  public TreeModel getRegionNortheastData() {
    return DATA_NORTHEAST;
  }

  public TreeModel getRegionMidwestData() {
    return DATA_MIDWEST;
  }

  public TreeModel getRegionSouthData() {
    return DATA_SOUTH;
  }

  public TreeModel getDivisionPacificData() {
    return DATA_PACIFIC;
  }
  
  public static List<CensusData.DataItem> getAllStateData() {
    List<CensusData.DataItem> allStateData = new ArrayList<CensusData.DataItem>();
    for(CensusData.DataItem region : ROOT.getChildren()) {
      for(CensusData.DataItem division : region.getChildren()) {
        allStateData.addAll(division.getChildren());
      }
    }
    return allStateData;
  }

  private static TreeModel getModel(DataItem rootItem) {
    TreeNode root = getTreeNode(rootItem);
    return new ChildPropertyTreeModel(root, "children");
  }

  private static TreeNode getTreeNode(DataItem dataItem)
  {
    // Create the node itself
    TreeNode node = new CensusTreeNode(dataItem.getName(), dataItem.getPopulation(),
                                       getColor(dataItem.getIncome(), MIN_INCOME, MAX_INCOME),
                                       dataItem.getIncome());

    // Create its children
    List<TreeNode> children = new ArrayList<TreeNode>();
    for(DataItem childItem : dataItem.children) {
      children.add(getTreeNode(childItem));
    }

    // Add the children and return
    node.addChildren(children);
    return node;
  }

  private static Color getColor(double value, double min, double max) {
    double percent = Math.max((value - min) / max, 0);
    if(percent < 0.25)
      return new Color(38,125,179);
    else if(percent < 0.5)
      return new Color(104,193,130);
    else if(percent < 0.75)
      return new Color(250,213,92);
    else
      return new Color(237,102,71);
  }

  public static class DataItem {
    private final String name;
    private final int population;
    private final int income;
    private final List<DataItem> children;

    public DataItem(String name, int population, int income) {
      this.name = name;
      this.population = population;
      this.income = income;
      this.children = new ArrayList<DataItem>();
    }

    public void addChild(DataItem child) {
      this.children.add(child);
    }

    public String getName() {
      return name;
    }

    public int getPopulation() {
      return population;
    }

    public int getIncome() {
      return income;
    }

    public List<CensusData.DataItem> getChildren() {
      return children;
    }
  }

  private static final int MIN_INCOME = 0;
  private static final int MAX_INCOME = 70000;

  private final TreeModel DATA_UNITED_STATES = getModel(ROOT);
  private final TreeModel DATA_WEST = getModel(REGION_W);
  private final TreeModel DATA_NORTHEAST = getModel(REGION_NE);
  private final TreeModel DATA_MIDWEST = getModel(REGION_MW);
  private final TreeModel DATA_SOUTH = getModel(REGION_S);
  private final TreeModel DATA_PACIFIC = getModel(DIVISION_P);

  private static final DataItem ROOT = new DataItem("United States", 301461533, 51425);

  private static final DataItem REGION_NE = new DataItem("Northeast Region", 54906297, 57208);
  private static final DataItem REGION_MW = new DataItem("Midwest Region", 66336038, 49932);
  private static final DataItem REGION_S = new DataItem("South Region", 110450832, 47204);
  private static final DataItem REGION_W = new DataItem("West Region", 69768366, 56171);

  private static final DataItem DIVISION_NE = new DataItem("New England", 14315257, 61511);
  private static final DataItem DIVISION_MA = new DataItem("Middle Atlantic", 40591040, 55726);
  private static final DataItem DIVISION_ENC = new DataItem("East North Central", 46277998, 50156);
  private static final DataItem DIVISION_WNC = new DataItem("West North Central", 20058040, 49443);
  private static final DataItem DIVISION_SA = new DataItem("South Atlantic", 57805475, 50188);
  private static final DataItem DIVISION_ESC = new DataItem("East South Central", 17966553, 41130);
  private static final DataItem DIVISION_WSC = new DataItem("West South Central", 34678804, 45608);
  private static final DataItem DIVISION_M = new DataItem("Mountain", 21303294, 51504);
  private static final DataItem DIVISION_P = new DataItem("Pacific", 48465072, 58735);

  static {
    // Set up the regions
    ROOT.addChild(REGION_NE);
    ROOT.addChild(REGION_MW);
    ROOT.addChild(REGION_S);
    ROOT.addChild(REGION_W);

    // Set up the divisions
    REGION_NE.addChild(DIVISION_NE);
    REGION_NE.addChild(DIVISION_MA);
    REGION_MW.addChild(DIVISION_ENC);
    REGION_MW.addChild(DIVISION_WNC);
    REGION_S.addChild(DIVISION_SA);
    REGION_S.addChild(DIVISION_ESC);
    REGION_S.addChild(DIVISION_WSC);
    REGION_W.addChild(DIVISION_M);
    REGION_W.addChild(DIVISION_P);

    // Set up the states
    DIVISION_NE.addChild(new DataItem("Connecticut", 3494487, 67721));
    DIVISION_NE.addChild(new DataItem("Maine", 1316380, 46541));
    DIVISION_NE.addChild(new DataItem("Massachusetts", 6511176, 64496));
    DIVISION_NE.addChild(new DataItem("New Hampshire", 1315419, 63033));
    DIVISION_NE.addChild(new DataItem("Rhode Island", 1057381, 55569));
    DIVISION_NE.addChild(new DataItem("Vermont", 620414, 51284));

    DIVISION_MA.addChild(new DataItem("New Jersey", 8650548, 68981));
    DIVISION_MA.addChild(new DataItem("New York", 19423896, 55233));
    DIVISION_MA.addChild(new DataItem("Pennsylvania", 12516596, 49737));

    DIVISION_ENC.addChild(new DataItem("Indiana", 6342469, 47465));
    DIVISION_ENC.addChild(new DataItem("Illinois", 12785043, 55222));
    DIVISION_ENC.addChild(new DataItem("Michigan", 10039208, 48700));
    DIVISION_ENC.addChild(new DataItem("Ohio", 11511858, 47144));
    DIVISION_ENC.addChild(new DataItem("Wisconsin", 5599420, 51569));

    DIVISION_WNC.addChild(new DataItem("Iowa", 2978880, 48052));
    DIVISION_WNC.addChild(new DataItem("Kansas", 2777835, 48394));
    DIVISION_WNC.addChild(new DataItem("Minnesota", 5188581, 57007));
    DIVISION_WNC.addChild(new DataItem("Missouri", 5904382, 46005));
    DIVISION_WNC.addChild(new DataItem("Nebraska", 1772124, 47995));
    DIVISION_WNC.addChild(new DataItem("North Dakota", 639725, 45140));
    DIVISION_WNC.addChild(new DataItem("South Dakota", 796513, 44828));

    DIVISION_SA.addChild(new DataItem("Delaware", 863832, 57618));
    DIVISION_SA.addChild(new DataItem("District of Columbia", 588433, 56519));
    DIVISION_SA.addChild(new DataItem("Florida", 18222420, 47450));
    DIVISION_SA.addChild(new DataItem("Georgia", 9497667, 49466));
    DIVISION_SA.addChild(new DataItem("Maryland", 5637418, 69475));
    DIVISION_SA.addChild(new DataItem("North Carolina", 9045705, 45069));
    DIVISION_SA.addChild(new DataItem("South Carolina", 4416867, 43572));
    DIVISION_SA.addChild(new DataItem("Virginia", 7721730, 60316));
    DIVISION_SA.addChild(new DataItem("West Virginia", 1811403, 37356));

    DIVISION_ESC.addChild(new DataItem("Alabama", 4633360, 41216));
    DIVISION_ESC.addChild(new DataItem("Kentucky", 4252000, 41197));
    DIVISION_ESC.addChild(new DataItem("Mississippi", 2922240, 36796));
    DIVISION_ESC.addChild(new DataItem("Tennessee", 6158953, 42943));

    DIVISION_WSC.addChild(new DataItem("Arkansas", 2838143, 38542));
    DIVISION_WSC.addChild(new DataItem("Louisiana", 4411546, 42167));
    DIVISION_WSC.addChild(new DataItem("Oklahoma", 3610073, 41861));
    DIVISION_WSC.addChild(new DataItem("Texas", 23819042, 48199));

    DIVISION_M.addChild(new DataItem("Arizona", 6324865, 50296));
    DIVISION_M.addChild(new DataItem("Colorado", 4843211, 56222));
    DIVISION_M.addChild(new DataItem("Idaho", 1492573, 46183));
    DIVISION_M.addChild(new DataItem("Montana", 956257, 43089));
    DIVISION_M.addChild(new DataItem("Nevada", 2545763, 55585));
    DIVISION_M.addChild(new DataItem("New Mexico", 1964860, 42742));
    DIVISION_M.addChild(new DataItem("Utah", 2651816, 55642));
    DIVISION_M.addChild(new DataItem("Wyoming", 523949, 51990));

    DIVISION_P.addChild(new DataItem("Alaska", 683142, 64635));
    DIVISION_P.addChild(new DataItem("California", 36308527, 60392));
    DIVISION_P.addChild(new DataItem("Hawaii", 1280241, 64661));
    DIVISION_P.addChild(new DataItem("Oregon", 3727407, 49033));
    DIVISION_P.addChild(new DataItem("Washington", 6465755, 56384));
  }

  public static class CensusTreeNode extends TreeNode {
    private int income;

    public CensusTreeNode(String text, Number size, Color color, int income) {
      super(text, size, color);
      this.income = income;
    }

    public int getIncome() {
      return income;
    }
  }
} 
