/** Copyright (c) 2009, 2016, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetTreeImpl;
import org.apache.myfaces.trinidad.model.TreeModel;
import org.apache.myfaces.trinidad.model.ViewIdPropertyMenuModel;


public class DemoTGGroupMenuModel extends ViewIdPropertyMenuModel implements Serializable
{
  public DemoTGGroupMenuModel()
  {
    super();
    setViewIdProperty("focusViewId");
    _tagGuide = _initTagGuide();
    setWrappedData(_tagGuide);
  }

  private static DemoItemNode _getDemoItemNode(String tagName)
  {
    return _getDemoItemNode(tagName, tagName);
  }

  private static DemoItemNode _getDemoItemNode(String tagName, boolean deprecated)
  {
    return _getDemoItemNode(tagName, tagName, deprecated);
  }

  private static DemoItemNode _getDemoItemNode(String tagName, String smallIconName)
  {
    return _getDemoItemNode(tagName, smallIconName, tagName);
  }

  private static DemoItemNode _getDemoItemNode(String tagName, String smallIconName, boolean deprecated)
  {
    return _getDemoItemNode(tagName, smallIconName, tagName, deprecated);
  }

  private static DemoItemNode _getDemoItemNode(String tagName, String smallIconName, String outcomeName)
  {
    return _getDemoItemNode(tagName, smallIconName, outcomeName, false);
  }

  private static DemoItemNode _getDemoItemNode(String tagName, String smallIconName, String outcomeName, boolean deprecated)
  {
    String titleCasedTagName = tagName.substring(0,1).toUpperCase() + tagName.substring(1);
    String shortDesc = (deprecated) ? " (deprecated)" : "";
    return new DemoItemNode(
      titleCasedTagName,
      "/components/" + tagName + ".jspx",
      "/adfdt/" + smallIconName + ".png",
      "guide." + outcomeName,
      shortDesc/*shortDesc*/,
      null/*category*/,
      null/*componentName*/,
      null/*backingBean*/,
      "/images/icons-large/" + tagName + ".png",
      null/*"/images/carousel/" + tagName + ".jpg"*/,
      deprecated);
  }

  private TreeModel _initTagGuide()
  {
    // Tag Guide - ComponentTags - Active Components
    List<DemoItemNode> activeComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("activeCommandToolbarButton", "commandToolbarButton", true));
        add(_getDemoItemNode("activeImage", "image"));
        add(_getDemoItemNode("activeOutputText", "outputText"));
      }
    };

    // Tag Guide - ComponentTags - Input Components
    List<DemoItemNode> inputComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("chooseColor"));
        add(_getDemoItemNode("chooseDate", "chooseDate", "componentChooseDate"));
        add(_getDemoItemNode("codeEditor"));
        add(_getDemoItemNode("form"));
        add(_getDemoItemNode("inputColor"));
        add(_getDemoItemNode("inputDate", "inputDate", "componentInputDate"));
        add(_getDemoItemNode("inputFile"));
        add(_getDemoItemNode("inputNumberSlider", "inputSlider"));
        add(_getDemoItemNode("inputNumberSpinbox"));
        add(_getDemoItemNode("inputRangeSlider", "inputSlider"));
        add(_getDemoItemNode("inputText", "inputText", "componentInputText"));
        add(_getDemoItemNode("resetButton", true));
        add(_getDemoItemNode("richTextEditor", "richTextEditor", "componentRichTextEditor"));
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
        add(_getDemoItemNode("subform"));
      }
    };

    // Tag Guide - ComponentTags - Layout Components
    List<DemoItemNode> layoutComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("decorativeBox"));
        add(_getDemoItemNode("deck"));
        add(_getDemoItemNode("gridCell"));
        add(_getDemoItemNode("gridRow"));
        add(_getDemoItemNode("group", "xml_group"));
        add(_getDemoItemNode("masonryLayout"));
        add(_getDemoItemNode("panelAccordion"));
        add(_getDemoItemNode("panelBorderLayout"));
        add(_getDemoItemNode("panelBox"));
        add(_getDemoItemNode("panelDashboard"));
        add(_getDemoItemNode("panelDrawer"));
        add(_getDemoItemNode("panelFormLayout"));
        add(_getDemoItemNode("panelGridLayout", "layoutGrid"));
        add(_getDemoItemNode("panelGroupLayout"));
        add(_getDemoItemNode("panelHeader"));
        add(_getDemoItemNode("panelLabelAndMessage"));
        add(_getDemoItemNode("panelList"));
        add(_getDemoItemNode("panelSplitter"));
        add(_getDemoItemNode("panelSpringboard"));
        add(_getDemoItemNode("panelStretchLayout"));
        add(_getDemoItemNode("panelTabbed"));
        add(_getDemoItemNode("showDetail"));
        add(_getDemoItemNode("showDetailHeader"));
        add(_getDemoItemNode("showDetailItem"));
        add(_getDemoItemNode("spacer"));
      }
    };

    // Tag Guide - ComponentTags - List of Values Components
    List<DemoItemNode> lovComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("inputComboboxListOfValues", "comboBox"));
        add(_getDemoItemNode("inputListOfValues", "comboBox"));
        add(_getDemoItemNode("inputSearch", "comboBox"));
      }
    };

    // Tag Guide - ComponentTags - Menu and Toolbar Components
    List<DemoItemNode> menuAndToolbarComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("commandToolbarButton", true));
        add(_getDemoItemNode("commandMenuItem"));
        add(_getDemoItemNode("goMenuItem"));
        add(_getDemoItemNode("menu"));
        add(_getDemoItemNode("menuBar"));
        add(_getDemoItemNode("toolbar"));
        add(_getDemoItemNode("toolbox"));
      }
    };

    // Tag Guide - ComponentTags - Miscellaneous Components
    List<DemoItemNode> miscComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("calendar"));
        add(_getDemoItemNode("contextInfo"));
        add(_getDemoItemNode("declarativeComponent", "dialog"));
        add(_getDemoItemNode("document"));
        add(_getDemoItemNode("icon", "image"));
        add(_getDemoItemNode("image"));
        add(_getDemoItemNode("inlineFrame"));
        add(_getDemoItemNode("iterator"));
        add(_getDemoItemNode("media"));
        add(_getDemoItemNode("pageTemplate"));
        add(_getDemoItemNode("poll"));
        add(_getDemoItemNode("progressIndicator"));
        add(_getDemoItemNode("region", "dialog"));
        add(_getDemoItemNode("separator"));
        add(_getDemoItemNode("statusIndicator"));
        add(_getDemoItemNode("streaming"));
        add(_getDemoItemNode("switcher"));
      }
    };

    // Tag Guide - ComponentTags - Navigation Components
    List<DemoItemNode> navComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("breadCrumbs", "breadCrumbs", "componentBreadCrumbs"));
        add(_getDemoItemNode("button"));
        add(_getDemoItemNode("commandButton", true));
        add(_getDemoItemNode("commandImageLink", "image", true));
        add(_getDemoItemNode("commandLink", "commandLink", "componentCommandLink", true));
        add(_getDemoItemNode("commandNavigationItem", "commandNavigationItem", "componentCommandNavigationItem"));
        add(_getDemoItemNode("goButton", true));
        add(_getDemoItemNode("goImageLink", "image", true));
        add(_getDemoItemNode("goLink", true));
        add(_getDemoItemNode("link"));
        add(_getDemoItemNode("navigationPane", "navigationLevel", "componentNavigationPane"));
        add(_getDemoItemNode("train", "train", "componentTrain"));
        add(_getDemoItemNode("trainButtonBar", "trainButtonBar", "componentTrainButtonBar"));
      }
    };

    // Tag Guide - ComponentTags - Output Components
    List<DemoItemNode> outputComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("message"));
        add(_getDemoItemNode("messages"));
        add(_getDemoItemNode("outputFormatted"));
        add(_getDemoItemNode("outputLabel"));
        add(_getDemoItemNode("outputText"));
        add(_getDemoItemNode("sanitized"));
      }
    };

    // Tag Guide - ComponentTags - Popup Components
    List<DemoItemNode> popupComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("dialog"));
        add(_getDemoItemNode("noteWindow", "message"));
        add(_getDemoItemNode("panelWindow"));
        add(_getDemoItemNode("popup"));
      }
    };

    // Tag Guide - ComponentTags - Query Components
    List<DemoItemNode> queryComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("query", "find"));
        add(_getDemoItemNode("quickQuery", "find"));
      }
    };

    // Tag Guide - ComponentTags - Table Components
    List<DemoItemNode> tableComponentsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("carousel"));
        add(_getDemoItemNode("carouselItem"));
        add(_getDemoItemNode("column"));
        add(_getDemoItemNode("listItem"));
        add(_getDemoItemNode("listView"));
        add(_getDemoItemNode("panelCollection"));
        add(_getDemoItemNode("table", "table", "componentTable"));
        add(_getDemoItemNode("tree"));
        add(_getDemoItemNode("treeTable"));
        add(_getDemoItemNode("dynamicComponent"));
      }
    };

    // Tag Guide - OtherTags - Behavior Tags
    List<DemoItemNode> behaviorTagsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("autoSuggestBehavior"));
        add(_getDemoItemNode("checkUncommittedDataBehavior", "document", "document"));
        add(_getDemoItemNode("insertTextBehavior", "inputText"));
        add(_getDemoItemNode("masonryLayoutBehavior", "masonryLayout"));
        add(_getDemoItemNode("matchMediaBehavior"));
        add(_getDemoItemNode("panelDashboardBehavior", "panelDashboard"));
        add(_getDemoItemNode("richTextEditorInsertBehavior"));
        add(_getDemoItemNode("scrollComponentIntoViewBehavior"));
        add(_getDemoItemNode("showPopupBehavior", "popup"));
        add(_getDemoItemNode("showPrintablePageBehavior", "print"));
        add(_getDemoItemNode("transition"));
      }
    };

    // Tag Guide - OtherTags - Converter Tags
    List<DemoItemNode> converterTagsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("clientConverter"));
        add(_getDemoItemNode("convertColor"));
        add(_getDemoItemNode("convertDateTime"));
        add(_getDemoItemNode("convertNumber"));
      }
    };

    // Tag Guide - OtherTags - Drag and Drop Tags
    List<DemoItemNode> dragAndDropTagsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("attributeDragSource", "export"));
        add(_getDemoItemNode("attributeDropTarget", "importIcon"));
        add(_getDemoItemNode("calendarDropTarget", "importIcon"));
        add(_getDemoItemNode("collectionDragSource", "export"));
        add(_getDemoItemNode("collectionDropTarget", "importIcon"));
        add(_getDemoItemNode("componentDragSource", "export"));
        add(_getDemoItemNode("dataFlavor"));
        add(_getDemoItemNode("dragSource", "export"));
        add(_getDemoItemNode("dropTarget", "importIcon"));
      }
    };

    // Tag Guide - OtherTags - Listener Tags
    List<DemoItemNode> listenerTagsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("clientListener", "listener"));
        add(_getDemoItemNode("exportCollectionActionListener", "listener"));
        add(_getDemoItemNode("fileDownloadActionListener", "listener"));
        add(_getDemoItemNode("resetActionListener"));
        add(_getDemoItemNode("returnActionListener", "listener"));
        add(_getDemoItemNode("resetListener", "listener"));
        add(_getDemoItemNode("serverListener", "listener"));
        add(_getDemoItemNode("setPropertyListener", "listener"));
      }
    };

    // Tag Guide - OtherTags - Miscellaneous Tags
    List<DemoItemNode> miscTagsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("clientAttribute", "attribute"));
        add(_getDemoItemNode("forEach"));
        add(_getDemoItemNode("resource"));
        add(_getDemoItemNode("skipLinkTarget"));
        add(_getDemoItemNode("target"));
      }
    };

    // Tag Guide - OtherTags - Page Templates and Declarative Components
    List<DemoItemNode> PgtAndDecTagsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("componentDef", "element"));
        add(_getDemoItemNode("facetRef", "facet"));
        add(_getDemoItemNode("pageTemplateDef", "element", "pageTemplate"));
        add(_getDemoItemNode("xmlContent", "xml"));
      }
    };

    // Tag Guide - OtherTags - Validator Tags
    List<DemoItemNode> validatorTagsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("validateByteLength"));
        add(_getDemoItemNode("validateDateRestriction", "validateDateTimeRange"));
        add(_getDemoItemNode("validateDateTimeRange"));
        add(_getDemoItemNode("validateDoubleRange", "validator"));
        add(_getDemoItemNode("validateLength", "validateByteLength"));
        add(_getDemoItemNode("validateLongRange", "validator"));
        add(_getDemoItemNode("validateRegExp"));
      }
    };

    DemoItemNode activeComponentsGroup = new DemoItemNode ("Active", "/images/folder.png", activeComponentsList);
    DemoItemNode dvtTagsGroup = new DemoItemNode ("Data Visualization Tools", "/images/folder.png", DemoDVTMenuModel.getTagGuideList());
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

    DemoItemNode behaviorTagsGroup = new DemoItemNode ("Behavior", "/images/folder.png", behaviorTagsList);
    DemoItemNode converterTagsGroup = new DemoItemNode ("Converter", "/images/folder.png", converterTagsList);
    DemoItemNode dragAndDropTagsGroup = new DemoItemNode ("Drag and Drop", "/images/folder.png", dragAndDropTagsList);
    DemoItemNode listenerTagsGroup = new DemoItemNode ("Listener", "/images/folder.png", listenerTagsList);
    DemoItemNode miscTagsGroup = new DemoItemNode ("Miscellaneous", "/images/folder.png", miscTagsList);
    DemoItemNode PgtAndDecTagsGroup = new DemoItemNode ("Page Templates and Declarative", "/images/folder.png", PgtAndDecTagsList);
    DemoItemNode validatorTagsGroup = new DemoItemNode ("Validator", "/images/folder.png", validatorTagsList);

    List<DemoItemNode> componentsList = new ArrayList<DemoItemNode>();
    componentsList.add(activeComponentsGroup);

    //componentsList.addAll(DemoDVTMenuModel.getTagGuideList());

    componentsList.add(dvtTagsGroup);

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

    Collections.sort(componentsList);

    List<DemoItemNode> otherTagsList = new ArrayList<DemoItemNode>();
    otherTagsList.add(behaviorTagsGroup);
    otherTagsList.add(converterTagsGroup);
    otherTagsList.add(dragAndDropTagsGroup);
    otherTagsList.add(listenerTagsGroup);
    otherTagsList.add(miscTagsGroup);
    otherTagsList.add(PgtAndDecTagsGroup);
    otherTagsList.add(validatorTagsGroup);


    DemoItemNode componentsGroup = new DemoItemNode("Components", "/images/folder.png", componentsList);
    DemoItemNode otherTagsGroup = new DemoItemNode("Other Tags", "/images/folder.png", otherTagsList);

    List tagGuideList = new ArrayList();
    tagGuideList.add(componentsGroup);
    tagGuideList.add(otherTagsGroup);

    TreeModel tagGuide = new ChildPropertyTreeModel(tagGuideList, _CHILDREN);
    return tagGuide;
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
      TreeModel model = this.getTagGuide();
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

  public void setTagGuide(TreeModel tagGuide)
  {
    this._tagGuide = tagGuide;
  }

  public TreeModel getTagGuide()
  {
    return _tagGuide;
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
      TreeModel model = this.getTagGuide();
      selectionState.setCollectionModel(model);

      selectionState.setContained(true);

      _selectionState = selectionState;
    }

    return _selectionState;
  }

  private static final String _CHILDREN = "children";
  private RowKeySet _foldersTreeState;
  private RowKeySet _selectionState;
  private transient TreeModel _tagGuide = null;

  @SuppressWarnings("compatibility:2447198514566622131")
  private static final long serialVersionUID = 1L;
}
