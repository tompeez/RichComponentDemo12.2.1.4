/* Copyright (c) 2010, 2014, Oracle and/or its affiliates. 
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

public class DemoStyleMenuModel extends ViewIdPropertyMenuModel implements Serializable
{
  public DemoStyleMenuModel()
  {
    super();
    setViewIdProperty("focusViewId");
    _styles = _initStyle();
    setWrappedData(_styles);
  }

  private static DemoItemNode _getDemoItemNode(String tagName)
  {
    return _getDemoItemNode(tagName, false);
  }

  private static DemoItemNode _getDemoItemNode(String tagName, boolean deprecated)
  {
    String titleCasedTagName = tagName.substring(0,1).toUpperCase() + tagName.substring(1);
    return _getDemoItemNode(titleCasedTagName, tagName, tagName, tagName, tagName, deprecated);
  }

  private static DemoItemNode _getDemoItemNode(
    String demoName,
    String pageName,
    String smallIconName,
    String largeIconName,
    String outcomeName)
  {
    return _getDemoItemNode(demoName, pageName, smallIconName, largeIconName, outcomeName, false);
  }

  private static DemoItemNode _getDemoItemNode(
    String demoName,
    String pageName,
    String smallIconName,
    String largeIconName,
    String outcomeName,
    boolean deprecated)
  {
    return new DemoItemNode(
      demoName,
      "/components/styles/" + pageName + ".jspx",
      "/adfdt/" + smallIconName + ".png",
      "styles." + outcomeName,
      " (deprecated)"/*shortDesc*/,
      null/*category*/,
      null/*componentName*/,
      null/*backingBean*/,
      "/images/icons-large/" + largeIconName + ".png",
      null/*carouselIcon*/,
      deprecated);
  }

  private TreeModel _initStyle()
  {
    // Styles - Styles Demo - Active Components
    List<DemoItemNode> activeComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("ActiveCommandToolbarButton", "activeCommandToolbarButton", "commandToolbarButton", "activeCommandToolbarButton", "activeCommandToolbarButton", true));
        add(_getDemoItemNode("ActiveImage", "activeImage", "image", "activeImage", "activeImage"));
        add(_getDemoItemNode("ActiveOutputText", "activeOutputText", "outputText", "activeOutputText", "activeOutputText"));
      }
    };

    // Styles - Styles Demo - Input Components
    List<DemoItemNode> inputComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("chooseColor"));
        add(_getDemoItemNode("chooseDate"));
        add(_getDemoItemNode("inputColor"));
        add(_getDemoItemNode("inputDate"));
        add(_getDemoItemNode("inputFile"));
        add(_getDemoItemNode("InputNumberSlider", "inputNumberSlider", "inputSlider", "inputNumberSlider", "inputNumberSlider"));
        add(_getDemoItemNode("inputNumberSpinbox"));
        add(_getDemoItemNode("InputRangeSlider", "inputRangeSlider", "inputSlider", "inputRangeSlider", "inputRangeSlider"));
        add(_getDemoItemNode("inputText"));
        add(_getDemoItemNode("richTextEditor"));
        add(_getDemoItemNode("selectBooleanCheckbox"));
        add(_getDemoItemNode("selectBooleanRadio"));
        add(_getDemoItemNode("selectItem"));
        add(_getDemoItemNode("selectManyCheckbox"));
        add(_getDemoItemNode("selectManyChoice"));
        add(_getDemoItemNode("selectManyListbox"));
        add(_getDemoItemNode("selectManyShuttle"));
        add(_getDemoItemNode("selectOneChoice"));
        add(_getDemoItemNode("selectOneListbox"));
        add(_getDemoItemNode("selectOneRadio"));
        add(_getDemoItemNode("selectOrderShuttle"));
      }
    };

    // Styles - Styles Demo - Layout Components
    List<DemoItemNode> layoutComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("decorativeBox"));
        add(_getDemoItemNode("deck"));
        add(_getDemoItemNode("Group", "group", "xml_group", "group", "group"));
        add(_getDemoItemNode("masonryLayout"));
        add(_getDemoItemNode("panelAccordion"));
        add(_getDemoItemNode("panelBorderLayout"));
        add(_getDemoItemNode("panelBox"));
        add(_getDemoItemNode("panelDashboard"));
        add(_getDemoItemNode("panelFormLayout"));
        add(_getDemoItemNode("panelGroupLayout"));
        add(_getDemoItemNode("panelHeader"));
        add(_getDemoItemNode("panelLabelAndMessage"));
        add(_getDemoItemNode("panelList"));
        add(_getDemoItemNode("panelSplitter"));
        add(_getDemoItemNode("panelStretchLayout"));
        add(_getDemoItemNode("panelTabbed"));
        add(_getDemoItemNode("showDetail"));
        add(_getDemoItemNode("showDetailHeader"));
        add(_getDemoItemNode("showDetailItem"));
        add(_getDemoItemNode("spacer"));
      }
    };

    // Styles - ComponentTags - List of Values Components
    List<DemoItemNode> lovComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("InputComboboxListOfValues", "inputComboboxListOfValues", "comboBox", "inputComboboxListOfValues", "inputComboboxListOfValues"));
        add(_getDemoItemNode("InputListOfValues", "inputListOfValues", "comboBox", "inputListOfValues", "inputListOfValues"));
      }
    };

    // Styles - ComponentTags - Menu and Toolbar Components
    List<DemoItemNode> menuAndToolbarComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("commandMenuItem"));
        add(_getDemoItemNode("commandToolbarButton", true));
        add(_getDemoItemNode("goMenuItem"));
        add(_getDemoItemNode("menu"));
        add(_getDemoItemNode("menuBar"));
        add(_getDemoItemNode("toolbar"));
        add(_getDemoItemNode("toolbox"));
      }
    };

    // Styles - ComponentTags - Miscellaneous Components
    List<DemoItemNode> miscComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Icon", "icon", "image", "icon", "icon"));
        add(_getDemoItemNode("image"));
        add(_getDemoItemNode("inlineFrame"));
        add(_getDemoItemNode("media"));
        add(_getDemoItemNode("poll"));
        add(_getDemoItemNode("progressIndicator"));
        add(_getDemoItemNode("statusIndicator"));
        add(_getDemoItemNode("separator"));
      }
    };

    // Styles - ComponentTags - Navigation Components
    List<DemoItemNode> navComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("breadCrumbs"));
        add(_getDemoItemNode("button"));
        add(_getDemoItemNode("commandButton", true));
        add(_getDemoItemNode("CommandImageLink", "commandImageLink", "image", "commandImageLink", "commandImageLink", true));
        add(_getDemoItemNode("commandLink", true));
        add(_getDemoItemNode("commandNavigationItem"));
        add(_getDemoItemNode("goButton", true));
        add(_getDemoItemNode("GoImageLink", "goImageLink", "image", "goImageLink", "goImageLink", true));
        add(_getDemoItemNode("goLink", true));
        add(_getDemoItemNode("link"));
        add(_getDemoItemNode("NavigationPane", "navigationPane", "navigationLevel", "navigationPane", "navigationPane"));;
        add(_getDemoItemNode("train"));
        add(_getDemoItemNode("trainButtonBar"));
      }
    };

    // Styles - ComponentTags - Output Components
    List<DemoItemNode> outputComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("message"));
        add(_getDemoItemNode("messages"));
        add(_getDemoItemNode("outputFormatted"));
        add(_getDemoItemNode("outputLabel"));
        add(_getDemoItemNode("outputText"));
      }
    };

    // Styles - ComponentTags - Popup Components
    List<DemoItemNode> popupComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("dialog"));
        add(_getDemoItemNode("NoteWindow", "noteWindow", "message", "noteWindow", "noteWindow"));
        add(_getDemoItemNode("panelWindow"));
        add(_getDemoItemNode("popup"));
      }
    };

    // Styles - ComponentTags - Query Components
    List<DemoItemNode> queryComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Query", "query", "find", "query", "query"));
        add(_getDemoItemNode("QuickQuery", "quickQuery", "find", "quickQuery", "quickQuery"));
      }
    };

    // Styles - ComponentTags - Table Components
    List<DemoItemNode> tableComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("carousel"));
        add(_getDemoItemNode("carouselItem"));
        add(_getDemoItemNode("column"));
        add(_getDemoItemNode("panelCollection"));
        add(_getDemoItemNode("table"));
        add(_getDemoItemNode("tree"));
        add(_getDemoItemNode("treeTable"));
      }
    };

    DemoItemNode activeComponentsGroup = new DemoItemNode ("Active", "/images/folder.png", activeComponentsList);
    DemoItemNode inputComponentsGroup = new DemoItemNode ("Input", "/images/folder.png", inputComponentsList);
    DemoItemNode layoutComponentsGroup = new DemoItemNode ("Layout", "/images/folder.png", layoutComponentsList);
    DemoItemNode lovComponentsGroup = new DemoItemNode ("List of Values", "/images/folder.png", lovComponentsList);
    DemoItemNode menuAndToolbarComponentsGroup = new DemoItemNode ("Menu and Toolbar", "/images/folder.png", menuAndToolbarComponentsList);
    DemoItemNode miscComponentsGroup = new DemoItemNode ("Miscellaneous", "/images/folder.png", miscComponentsList);
    DemoItemNode navComponentsGroup = new DemoItemNode ("Navigation", "/images/folder.png", navComponentsList);
    DemoItemNode outputComponentsGroup = new DemoItemNode ("Output", "/images/folder.png", outputComponentsList);
    DemoItemNode popupComponentsGroup = new DemoItemNode ("Popup", "/images/folder.png", popupComponentsList);
    DemoItemNode queryComponentsGroup = new DemoItemNode ("Query", "/images/folder.png", queryComponentsList);
    DemoItemNode tableComponentsGroup = new DemoItemNode ("Table", "/images/folder.png", tableComponentsList);

    List<DemoItemNode> componentsList = new ArrayList<DemoItemNode>();
    componentsList.add(activeComponentsGroup);
    componentsList.add(inputComponentsGroup);
    componentsList.add(layoutComponentsGroup);
    componentsList.add(lovComponentsGroup);
    componentsList.add(menuAndToolbarComponentsGroup);
    componentsList.add(miscComponentsGroup);
    componentsList.add(navComponentsGroup);
    componentsList.add(outputComponentsGroup);
    componentsList.add(popupComponentsGroup);
    componentsList.add(queryComponentsGroup);
    componentsList.add(tableComponentsGroup);

    DemoItemNode componentsGroup = new DemoItemNode("Styles", "/images/folder.png", componentsList);

    List stylesList = new ArrayList();
    stylesList.add(componentsGroup);
    //stylesList.addAll(componentsList);

    TreeModel styles = new ChildPropertyTreeModel(stylesList, _CHILDREN);
    return styles;
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
      TreeModel model = this.getStyles();
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

  public void setStyles(TreeModel styles)
  {
    this._styles = styles;
  }

  public TreeModel getStyles()
  {
    return _styles;
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
      TreeModel model = this.getStyles();
      selectionState.setCollectionModel(model);

      selectionState.setContained(true);

      _selectionState = selectionState;
    }

    return _selectionState;
  }

  private static final String _CHILDREN = "children";
  private RowKeySet _foldersTreeState;
  private RowKeySet _selectionState;
  private transient TreeModel _styles = null;

  @SuppressWarnings("compatibility:2727069751851108524")
  private static final long serialVersionUID = 1L;
}
