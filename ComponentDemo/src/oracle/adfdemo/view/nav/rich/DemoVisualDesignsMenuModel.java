/* Copyright (c) 2010, 2017, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetTreeImpl;
import org.apache.myfaces.trinidad.model.TreeModel;
import org.apache.myfaces.trinidad.model.ViewIdPropertyMenuModel;

public class DemoVisualDesignsMenuModel extends ViewIdPropertyMenuModel implements Serializable
{
  public DemoVisualDesignsMenuModel()
  {
    super();
    setViewIdProperty("focusViewId");
    _visualDesigns = _initVisualDesigns();
    setWrappedData(_visualDesigns);
  }

  private static DemoItemNode _getDemoItemNode(
    String demoName,
    String pageName,
    String iconName)
  {
    return _getDemoItemNode(demoName, pageName, iconName, iconName);
  }

  private static DemoItemNode _getDemoItemNode(
    String demoName,
    String pageName,
    String smallIconName,
    String largeIconName)
  {
    return new DemoItemNode(
      demoName,
      "/visualDesigns/" + pageName + ".jspx",
      "/adfdt/" + smallIconName + ".png",
      "visualDesigns." + pageName,
      null/*shortDesc*/,
      null/*category*/,
      null/*componentName*/,
      null/*backingBean*/,
      "/images/icons-large/" + largeIconName + ".png",
      null/*carouselIcon*/,
      false /*deprecated (visual designs aren't deprecated)*/ );
  }

  private TreeModel _initVisualDesigns()
  {
    // Visual Designs - Active
    List<DemoItemNode> activeList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Active Data", "activeData", "poll"));
      }
    };

    // Visual Designs - Input
    List<DemoItemNode> inputList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Rich Text Editing", "richTextEditor", "richTextEditor"));
        add(_getDemoItemNode("Switchers", "switchers", "switcher"));
      }
    };

    // Visual Designs - Layout
    List<DemoItemNode> layoutList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Accordion", "accordion", "panelAccordion"));
        add(_getDemoItemNode("Branding", "branding", "brandingBar"));
        add(_getDemoItemNode("Content Container", "contentContainer", "panelBox"));
        add(_getDemoItemNode("Context Region", "contextRegion", "selectOneChoice"));
        add(_getDemoItemNode("Dashboard", "dashboard", "panelDashboard"));
        add(_getDemoItemNode("Deck", "deck", "deck"));
        add(_getDemoItemNode("Form Layout", "formLayout", "panelFormLayout"));
        add(_getDemoItemNode("Headers", "headers", "panelHeader"));
        add(_getDemoItemNode("Panel List", "panelList", "panelList"));
        add(_getDemoItemNode("Splitter Bar", "splitterBar", "panelSplitter"));
        add(_getDemoItemNode("Tabs", "tabs", "panelTabbed"));
        add(_getDemoItemNode("Vertical PanelTabbed", "verticalPaneltabbed", "panelTabbed"));
        add(_getDemoItemNode("Info Tile", "infoTile", "panelGroupLayout"));
        add(_getDemoItemNode("Info Tile Horizontal Layout", "infoTileHorizontal", "panelGroupLayout"));
        add(_getDemoItemNode("Info Tile Discreet Tile Content", "infoTileDiscreetContent", "panelGroupLayout"));
      }
    };

    /* TODO add this when there are lov designs:
    // Visual Designs - List of Values
    List<DemoItemNode> lovList = new ArrayList<DemoItemNode>()
    {
      {
      }
    };*/

    // Visual Designs - Menu and Toolbar
    List<DemoItemNode> menuAndToolbarComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Menu", "menu", "menuBar"));
        add(_getDemoItemNode("Tabbed Toolbar", "tabbedToolbar", "toolbar"));
        add(_getDemoItemNode("Toolbar", "toolbar", "toolbar"));
      }
    };

    // Visual Designs - Miscellaneous
    List<DemoItemNode> miscellaneousList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Separator", "separator", "separator"));
      }
    };

    // Visual Designs - Navigation
    List<DemoItemNode> navigationList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("BreadCrumbs", "breadCrumbs", "breadCrumbs"));
        add(_getDemoItemNode("Button", "button", "button"));
        add(_getDemoItemNode("Data Links", "dataLinks", "link"));
        add(_getDemoItemNode("Global Links", "globalLinks", "navigationLevel", "navigationPane"));
        add(_getDemoItemNode("Train", "train", "train"));
      }
    };

    // Visual Designs - Output
    List<DemoItemNode> outputList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Key Notation", "keyNotation", "outputLabel"));
        add(_getDemoItemNode("Messaging", "messaging", "messages"));
        add(_getDemoItemNode("Page Stamp", "pageStamp", "document"));
      }
    };

    /* TODO add this when there are popup designs:
    // Visual Designs - Popups
    List<DemoItemNode> popupsList = new ArrayList<DemoItemNode>()
    {
      {
      }
    };*/

    // Visual Designs - Query
    List<DemoItemNode> queryList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Query", "query", "find", "query"));
      }
    };

    // Visual Designs - Table
    List<DemoItemNode> tableList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Carousel", "carousel", "carousel"));
        add(_getDemoItemNode("Hierarchical Selector", "hierarchicalSelector", "treeTable"));
        add(_getDemoItemNode("Table", "table", "table"));
        add(_getDemoItemNode("Tree", "tree", "tree"));
        add(_getDemoItemNode("Tree Table", "treeTable", "treeTable"));
      }
    };

    DemoItemNode activeComponentsGroup = new DemoItemNode ("Active", "/images/folder.png", activeList);
    DemoItemNode inputComponentsGroup = new DemoItemNode ("Input", "/images/folder.png", inputList);
    DemoItemNode layoutComponentsGroup = new DemoItemNode ("Layout", "/images/folder.png", layoutList);
    // TODO add this when there are lov designs: DemoItemNode popupComponentsGroup = new DemoItemNode ("List of Values Designs", "/images/folder.png", lovList);
    DemoItemNode menuAndToolbarComponentsGroup = new DemoItemNode ("Menu and Toolbar", "/images/folder.png", menuAndToolbarComponentsList);
    DemoItemNode miscComponentsGroup = new DemoItemNode ("Miscellaneous", "/images/folder.png", miscellaneousList);
    DemoItemNode navComponentsGroup = new DemoItemNode ("Navigation", "/images/folder.png", navigationList);
    DemoItemNode outputComponentsGroup = new DemoItemNode ("Output", "/images/folder.png", outputList);
    // TODO add this when there are popup designs: DemoItemNode popupComponentsGroup = new DemoItemNode ("Popup Designs", "/images/folder.png", popupsList);
    DemoItemNode queryComponentsGroup = new DemoItemNode ("Query", "/images/folder.png", queryList);
    DemoItemNode tableComponentsGroup = new DemoItemNode ("Table", "/images/folder.png", tableList);

    List<DemoItemNode> componentsList = new ArrayList<DemoItemNode>();
    componentsList.add(activeComponentsGroup);
    componentsList.add(inputComponentsGroup);
    componentsList.add(layoutComponentsGroup);
    // TODO add this when there are lov designs: componentsList.add(lovComponentsGroup);
    componentsList.add(menuAndToolbarComponentsGroup);
    componentsList.add(miscComponentsGroup);
    componentsList.add(navComponentsGroup);
    componentsList.add(outputComponentsGroup);
    // TODO add this when there are popup designs: componentsList.add(popupComponentsGroup);
    componentsList.add(queryComponentsGroup);
    componentsList.add(tableComponentsGroup);

    DemoItemNode visualDesignsGroup = new DemoItemNode("Visual Designs", "/images/folder.png", componentsList);

    List visualDesignsList = new ArrayList();
    visualDesignsList.add(visualDesignsGroup);

    TreeModel visualDesigns = new ChildPropertyTreeModel(visualDesignsList, _CHILDREN);
    return visualDesigns;
  }

  public void setFoldersTreeState(RowKeySet _foldersTreeState)
  {
    this._foldersTreeState = _foldersTreeState;
  }

  /**
   * @return
   */
  public RowKeySet getFoldersTreeState()
  {
    if (_foldersTreeState == null)
    {
      // Create the PathSet that we'll use to store the initial
      // expansion state for the tree
      RowKeySet treeState = new RowKeySetTreeImpl();

      // RowKeySet requires access to the TreeModel for currency.
      TreeModel model = this.getVisualDesigns();
      treeState.setCollectionModel(model);

      // Make the model point at the root node
      Object oldRowKey = model.getRowKey();
      int oldIndex = model.getRowIndex();

      model.setRowKey(null);
      model.setRowIndex(0);

      treeState.setContained(true);

      // Restore keys
      model.setRowKey(oldRowKey);
      model.setRowIndex(oldIndex);

      _foldersTreeState = treeState;
    }

    return _foldersTreeState;
  }

  public void setVisualDesigns(TreeModel visualDesigns)
  {
    this._visualDesigns = visualDesigns;
  }

  public TreeModel getVisualDesigns()
  {
    return _visualDesigns;
  }

  public void setSelectionState(RowKeySet _selectionState)
  {
    this._selectionState = _selectionState;
  }

  public RowKeySet getSelectionState()
  {
    if (_selectionState == null)
    {
      RowKeySet selectionState = new RowKeySetTreeImpl();

      // RowKeySet requires access to the TreeModel for currency.
      TreeModel model = this.getVisualDesigns();
      selectionState.setCollectionModel(model);

      selectionState.setContained(true);

      _selectionState = selectionState;
    }

    return _selectionState;
  }

  private static final String _CHILDREN = "children";
  private RowKeySet _foldersTreeState;
  private RowKeySet _selectionState;
  private transient TreeModel _visualDesigns = null;

  @SuppressWarnings("compatibility:2386218335013319267")
  private static final long serialVersionUID = 1L;
}
