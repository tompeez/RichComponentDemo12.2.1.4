/* Copyright (c) 2009, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
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

public class DemoCombinedMenuModel extends ViewIdPropertyMenuModel implements Serializable
{
  public DemoCombinedMenuModel()
  {
    super();
    setViewIdProperty("focusViewId");
    _feature = _initFeature();
    setWrappedData(_feature);
  }

  private static DemoItemNode _getDemoItemNode(
    String demoName,
    String pageDestination,
    String smallIconName,
    String largeIconName,
    String outcome,
    String shortDesc)
  {
    return new DemoItemNode(
      demoName,
      pageDestination,
      "/adfdt/" + smallIconName + ".png",
      outcome,
      shortDesc,
      null/*category*/,
      null/*componentName*/,
      null/*backingBean*/,
      "/images/icons-large/" + largeIconName + ".png",
      null/*carouselIcon*/,
      false /*deprecatred (feature demos aren't deprecated)*/ );
    
  }

  private TreeModel _initFeature()
  {
    // File Explorer
    List<DemoItemNode> fileExplorerList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("File Explorer", "/fileExplorer/index.jspx", "table", "table", "fileExplorer.index", _FILE_EXPLORER_DESC));
      }
    };

    // Offline
    List<DemoItemNode> offlineList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Offline Active Report", "/offline/index.jspx", "table", "table", "offline.index", _OFFLINE_DESC));
      }
    };
    
    //Query Basics
    List<DemoItemNode> queryBasicsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Query Basics", "/feature/queryBasics.jspx", "find", "query", "feature.queryBasics", _QUERY_BASICS_DESC));
      }
    };

    //LOV Basics
    List<DemoItemNode> LOVBasicsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("LOV Basics", "/feature/LOVBasics.jspx", "comboBox", "inputListOfValues", "feature.LOVBasics", _LOV_BASICS_DESC));
      }
    };

    //Table Basics
    List<DemoItemNode> tableBasicsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Table Basics", "/feature/tableBasics.jspx", "table", "table", "feature.tableBasics", _TABLE_BASICS_DESC));
        add(_getDemoItemNode("Tree Basics", "/feature/treeBasics.jspx", "tree", "tree", "feature.treeBasics", _TREE_BASICS_DESC));
      }
    };

    // Feature Demos - Layout Basics
    List<DemoItemNode> layoutBasicsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Layout Basics", "/feature/layoutBasics.jspx", "layout", "layout", "feature.layoutBasics", _LAYOUT_BASICS_DESC));
        add(_getDemoItemNode("Layout Simple Demo", "/feature/layoutBasicTest.jspx", "help", "help", "feature.layoutBasicsSimpleDemo", _LAYOUT_BASICS_SIMPLE_DEMO_DESC));
        add(_getDemoItemNode("Branding Bar", "/feature/layoutBrandingBar.jspx", "brandingBar", "brandingBar", "feature.layoutBrandingBar", _LAYOUT_BASICS_BRANDING_BAR_DESC));
        add(_getDemoItemNode("Form Layout", "/feature/layoutForm.jspx", "panelFormLayout", "panelFormLayout", "feature.layoutForm", _LAYOUT_BASICS_FORM_LAYOUT_DESC));
        add(_getDemoItemNode("Navigation-Master-Detail", "/feature/layoutNavigationMasterDetail.jspx", "table", "table", "feature.layoutNavigationMasterDetail", _LAYOUT_BASICS_NAV_MAST_DET_DESC));
        add(_getDemoItemNode("Stretched Header", "/feature/layoutStretchedHeader.jspx", "panelHeader", "panelHeader", "feature.layoutStretchedHeader", _LAYOUT_BASICS_STRETCHED_HEADER_DESC));
        add(_getDemoItemNode("Tiled Flowing", "/feature/layoutTiledFlowing.jspx", "tableLayout", "tableLayout", "feature.layoutTiledFlowing", _LAYOUT_BASICS_TILED_FLOW_DESC));
        add(_getDemoItemNode("Tiled Stretching", "/feature/layoutTiledStretching.jspx", "panelDashboard", "panelDashboard", "feature.layoutTiledStretching", _LAYOUT_BASICS_TILED_STRETCH_DESC));
      }
    };

    // Feature Demos - Page Templates
    List<DemoItemNode> pageTemplatesList = DemoTemplateMenuModel.getPageTemplateNodes();

    // Feature Demos - postBack
    List<DemoItemNode> postBackList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("PPR and Autosubmit", "/feature/formPpr.jspx", "poll", "poll", "feature.formPpr", _PPR_AUTOSUBMIT_DESC));
        add(_getDemoItemNode("Partial Postback", "/feature/richPostback.jspx", "poll", "poll", "feature.richPostback", _PARTIAL_POSTBACK_DESC));
        add(_getDemoItemNode("Full Postback", "/feature/richFullPostback.jspx", "poll", "poll", "feature.richFullPostback", _FULL_POSTBACK_DESC));
      }
    };

    // Feature Demos - Converters and Validators
    List<DemoItemNode> convertersAndValidatorsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Client converters and validators", "/feature/clientConvert.jspx", "validator", "validateDoubleRange", "feature.clientConvert", _CLIENT_CONV_AND_VALID_DESC));
        add(_getDemoItemNode("Custom client converter and validator", "/feature/customConvert.jspx", "validator", "validateDoubleRange", "feature.customConvert",_CUSTOM_CLIENT_CONV_AND_VALID_DESC));
        add(_getDemoItemNode("Interfield validation", "/feature/interfieldValidation.jspx", "validator", "validateDoubleRange", "feature.interfieldValidation", _INTERFIELD_VALIDATION_DESC));
      }
    };
      // Feature Demos - Dynamic Faces
    List<DemoItemNode> dynamicFacesList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Dynamic Form Flatten", "/feature/dynamicFaces/dynFormFlatten.jspx", "dynamicFaces", "dynamicFaces", "feature.dynFormFlatten", _DYNAMIC_FACES_FORM_FLATTEN_DESC));
        add(_getDemoItemNode("Dynamic Form Hierarchical", "/feature/dynamicFaces/dynFormHierarchical.jspx", "dynamicFaces", "dynamicFaces", "feature.dynFormHierarchical",_DYNAMIC_FACES_FORM_HIERARCHICAL_DESC));
        add(_getDemoItemNode("Dynamic Table Flatten", "/feature/dynamicFaces/dynTableFlatten.jspx", "dynamicFaces", "dynamicFaces", "feature.dynTableFlatten", _DYNAMIC_FACES_TABLE_FLATTEN_DESC));
        add(_getDemoItemNode("Dynamic Table With Panel Collection", "/feature/dynamicFaces/dynTableFlattenWithPC.jspx", "dynamicFaces", "dynamicFaces", "feature.dynTableFlattenWithPC", _DYNAMIC_FACES_TABLE_FLATTEN_WITH_PC_DESC));
        add(_getDemoItemNode("Dynamic Table Hierarchical", "/feature/dynamicFaces/dynTableHierarchical.jspx", "dynamicFaces", "dynamicFaces", "feature.dynTableHierarchical", _DYNAMIC_FACES_TABLE_HIERARCHICAL_DESC));
      }
    };
    // Feature Demos - Optimized Lifecycle
    List<DemoItemNode> optimizedLifecycleList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("AutoSubmit and validation", "/feature/autoSubmit.jspx", "validator", "validateDoubleRange", "feature.autoSubmit", _AUTOSUBMIT_VALIDATION_DESC));
      }
    };

    // Feature Demos - Help and Hints
    List<DemoItemNode> helpsAndHintsList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Help Demo", "/feature/dynamicHelp.jspx", "help", "help", "feature.dynamicHelp", _DYNAMIC_HELP_DEMO_DESC));
        add(_getDemoItemNode("Help on header components", "/feature/headerDynamicHelp.jspx", "panelHeader", "panelHeader", "feature.headerDynamicHelp", _DYNAMIC_HELP_HEADER_DESC));
        add(_getDemoItemNode("Help on form components", "/feature/formDynamicHelp.jspx", "form", "form", "feature.formDynamicHelp", _DYNAMIC_HELP_FORM_DESC));
        add(_getDemoItemNode("Help on dialog components", "/feature/dialogDynamicHelp.jspx", "dialog", "dialog", "feature.dialogDynamicHelp", _DYNAMIC_HELP_DIALOG_DESC));
        add(_getDemoItemNode("Help on miscellaneous components", "/feature/miscDynamicHelp.jspx", "help", "help", "feature.miscDynamicHelp", _DYNAMIC_HELP_MISC_DESC));
        add(_getDemoItemNode("Help on accordion", "/feature/panelAccordionHelp.jspx", "panelAccordion", "panelAccordion", "feature.panelAccordionHelp", _ACCORDION_DESC));
        add(_getDemoItemNode("Converter and validator hints", "/feature/formCvtrVldr.jspx", "validator", "validateDoubleRange", "feature.formCvtrVldr", _CONV_VALID_HINTS_FORM_DESC));
      }
    };

    // Feature Demos - Changed Indicator
    List<DemoItemNode> changedIndicatorList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Changed Indicators", "/feature/changedIndicator.jspx", "inputText", "inputText", "feature.changedIndicator", _CHANGED_INDICATOR_DESC));
      }
    };

    // Feature Demos - Drag and Drop
    List<DemoItemNode> dragAndDropList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Drag and Drop", "/feature/dragAndDrop.jspx", "export", "dragSource", "feature.dragAndDrop", _DRAG_AND_DROP_DESC));
      }
    };

    // Feature Demos - Emailable Page
    List<DemoItemNode> emailablePageList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Emailable Page", "/feature/emailablePage.jspx", "email", "email", "feature.emailablePage", _EMAILABLE_PAGE_DESC));
      }
    };

    // Feature Demos - Active Data Service
    List<DemoItemNode> activeDataServiceList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Active Data Samples", "/visualDesigns/activeData.jspx", "poll", "poll", "feature.activeData", _ACTIVE_DATA_SAMPLES_DESC));
        add(_getDemoItemNode("Automatic PPR", "/feature/autoPPR.jspx", "poll", "poll", "feature.autoPPR", _AUTOMATIC_PPR_DESC));
      }
    };

    // Feature Demos - Client Behavior
    List<DemoItemNode> clientBehaviorList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Client Behavior Holder", "/feature/clientBehaviorHolder.jspx", "listener", "clientListener", "feature.clientBehaviorHolder", _CLIENT_BEHAVIR_HOLDER_DESC));
      }
    };

    // Feature Demos - Menu Model Usage
    List<DemoItemNode> menuModelUsageList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("Using XML MenuModel", "/components/pagehierarchy_xmlmenumodel/index.jspx", "breadCrumbs", "breadCrumbs", "guide.pageHierarchy.xmlMenuModel", _XML_MENUMODEL_DESC));
        add(_getDemoItemNode("Without XML MenuModel", "/components/pagehierarchy/index.jspx", "breadCrumbs", "breadCrumbs", "guide.pageHierarchy", _WITHOUT_XML_MENUMODEL_DESC));
        add(_getDemoItemNode("MenuBar Using MenuModel", "/components/pagehierarchy_xmlmenumodel/index.jspx", "menuBar", "menuBar", "menuBarDemo.mainPage", _MENUBAR_USING_MENUMODEL_DESC));
      }
    };

   //PanelSpringboard App
	List<DemoItemNode> panelSpringboardList = new ArrayList<DemoItemNode>()
	{
	  {
		add(_getDemoItemNode("panelSpringboard", "/feature/panelSpringboardApp.jspx", "panelSpringboard", "panelSpringboard", "feature.panelSpringboardApp", _PANEL_SPRINGBOARD_APP_DESC));
	  }
    };


    // Feature Demos
    DemoItemNode fileExplorerGroup = new DemoItemNode("File Explorer", "/images/folder.png", fileExplorerList);
    // Hiding  the Offline Report feature demo as it is not supported in 12.1.2.0.0
    /* DemoItemNode offlineGroup = new DemoItemNode("Offline Report", "/images/folder.png", offlineList);*/
    DemoItemNode layoutBasicsGroup = new DemoItemNode("Layout Basics", "/images/folder.png", layoutBasicsList);
    DemoItemNode queryBasicsGroup = new DemoItemNode("Query Basics", "/images/folder.png", queryBasicsList);
    DemoItemNode LOVBasicsGroup = new DemoItemNode("LOV Basics", "/images/folder.png", LOVBasicsList);
    DemoItemNode tableBasicsGroup = new DemoItemNode("Table Components Basics", "/images/folder.png", tableBasicsList);
    DemoItemNode pageTemplatesGroup = new DemoItemNode("Sample Page Templates", "/images/folder.png", pageTemplatesList);
    DemoItemNode postBackGroup = new DemoItemNode("PostBack", "/images/folder.png", postBackList);
    DemoItemNode convertersAndValidatorsGroup = new DemoItemNode("Converters and Validators", "/images/folder.png", convertersAndValidatorsList);
    DemoItemNode optimizedLifecycleGroup = new DemoItemNode("Optimized Lifecycle", "/images/folder.png", optimizedLifecycleList);
    DemoItemNode helpsAndHintsGroup = new DemoItemNode("Help and Hints", "/images/folder.png", helpsAndHintsList);
    DemoItemNode changedIndicatorGroup = new DemoItemNode("Changed Indicator", "/images/folder.png", changedIndicatorList);
    DemoItemNode dragAndDropGroup = new DemoItemNode("Drag and Drop", "/images/folder.png", dragAndDropList);
    DemoItemNode emailablePageGroup = new DemoItemNode("Emailable Page", "/images/folder.png", emailablePageList);
    DemoItemNode activeDataServiceGroup = new DemoItemNode("Active Data Service", "/images/folder.png", activeDataServiceList);
    DemoItemNode clientBehaviorGroup = new DemoItemNode("Client Behaviors", "/images/folder.png", clientBehaviorList);
    DemoItemNode menuModelUsageGroup = new DemoItemNode("Menu Model Usages", "/images/folder.png", menuModelUsageList);
    DemoItemNode dvtGroup = new DemoItemNode("Data Visualization Tools", "/images/folder.png", DemoDVTMenuModel.getFeatureDemoList());
    DemoItemNode panelSpringboardGroup = new DemoItemNode("Panel Springboard App", "/images/folder.png", panelSpringboardList);
    DemoItemNode dynamicFacesGroup = new DemoItemNode("Dynamic Faces", "/images/folder.png", dynamicFacesList);


    List<DemoItemNode> featureList = new ArrayList<DemoItemNode>();

    // Removing these, they are confusing when used with Alta
    //featureList.add(layoutBasicsGroup);
    featureList.add(queryBasicsGroup);
    featureList.add(LOVBasicsGroup);
    featureList.add(tableBasicsGroup);
    featureList.add(pageTemplatesGroup);
    featureList.add(fileExplorerGroup);
    //featureList.add(offlineGroup);
    featureList.add(postBackGroup);
    featureList.add(convertersAndValidatorsGroup);
    featureList.add(optimizedLifecycleGroup);
    featureList.add(helpsAndHintsGroup);
    featureList.add(changedIndicatorGroup);
    featureList.add(dragAndDropGroup);
    featureList.add(emailablePageGroup);
    featureList.add(activeDataServiceGroup);
    featureList.add(clientBehaviorGroup);
    featureList.add(menuModelUsageGroup);
    featureList.add(dvtGroup);
    featureList.add(panelSpringboardGroup);
    featureList.add(dynamicFacesGroup);
      
    Collections.sort(featureList);

    TreeModel feature = new ChildPropertyTreeModel(featureList, _CHILDREN);
    return feature;
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
      TreeModel model = this.getFeature();
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

  public void setFeature(TreeModel feature)
  {
    this._feature = feature;
  }

  public TreeModel getFeature()
  {
    return _feature;
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
      TreeModel model = this.getFeature();
      selectionState.setCollectionModel(model);

      selectionState.setContained(true);

      _selectionState = selectionState;
    }

    return _selectionState;
  }

  private static final String _CHILDREN = "children";
  private RowKeySet _foldersTreeState;
  private RowKeySet _selectionState;
  private transient TreeModel _feature = null;

  private static final String _FILE_EXPLORER_DESC = " - shows a sample file browser linked to a live server model";
  private static final String _OFFLINE_DESC =       " - shows the online and offline versions of an active report";
  private static final String _LAYOUT_BASICS_DESC = " - A must-read for avoiding browser pitfalls. Recommends best practices and a process for creating layouts.";
  private static final String _LAYOUT_BASICS_SIMPLE_DEMO_DESC = " - a very simple demo to help understand how various attributes affect layout";
  private static final String _QUERY_BASICS_DESC  = " - Examples of query with different configurations and use-cases";
  private static final String _LOV_BASICS_DESC    = " - Examples of LOV with different configurations and use-cases";
  private static final String _TABLE_BASICS_DESC  = " - Examples of table features and its architecture";
  private static final String _TREE_BASICS_DESC   = " - Examples of tree features and its architecture";
  private static final String _LAYOUT_BASICS_BRANDING_BAR_DESC = " - logo, title, and global navigation links";
  private static final String _LAYOUT_BASICS_FORM_LAYOUT_DESC = " - organized labels and fields";
  private static final String _LAYOUT_BASICS_NAV_MAST_DET_DESC = " - tree on the left, table above, detail below";
  private static final String _LAYOUT_BASICS_STRETCHED_HEADER_DESC = " - construct a header with a vertically stretchable body";
  private static final String _LAYOUT_BASICS_TILED_FLOW_DESC = " - spaced out tiles that flow vertically";
  private static final String _LAYOUT_BASICS_TILED_STRETCH_DESC = " - spaced out tiles that stretch vertically";
  private static final String _PPR_AUTOSUBMIT_DESC = " - how to use autosubmit and partialTriggers to do things like hide and disable content.";
  private static final String _PARTIAL_POSTBACK_DESC = " - an example of command buttons which trigger (server-side) actions with a partial-page refresh.";
  private static final String _FULL_POSTBACK_DESC = " - an example of command buttons which trigger (server-side) actions with a full-page refresh";
  private static final String _CLIENT_CONV_AND_VALID_DESC = " - shows some of the client converters and validators that are supported.";
  private static final String _CUSTOM_CLIENT_CONV_AND_VALID_DESC = " - shows an example of a 3rd party client converter and validator.";
  private static final String _INTERFIELD_VALIDATION_DESC = " - shows an example of validation involving multiple fields.";
  private static final String _AUTOSUBMIT_VALIDATION_DESC = " - shows improvements and limitations of autoSubmit and validation.";
  private static final String _DYNAMIC_HELP_DEMO_DESC = " - demo of dynamic help";
  private static final String _DYNAMIC_HELP_HEADER_DESC = " - demo of dynamic help on header components";
  private static final String _DYNAMIC_HELP_FORM_DESC = " - demo of dynamic help on form components";
  private static final String _DYNAMIC_HELP_DIALOG_DESC = " - demo of dynamic help on dialog/panelWindow components";
  private static final String _DYNAMIC_HELP_MISC_DESC = " - demo of dynamic help on miscellaneous components";
  private static final String _ACCORDION_DESC = " - demo of help on a panelAccordion component";
  private static final String _CONV_VALID_HINTS_FORM_DESC = " - demo of converter/validator hint text on form components";
  private static final String _CHANGED_INDICATOR_DESC = " - shows the changed indicator functionality.";
  private static final String _DRAG_AND_DROP_DESC = " - shows drag and drop functionality";
  private static final String _EMAILABLE_PAGE_DESC = " - shows a page in email mode; it is rendered so that the source will render fine in an email client which has limited css capabilities compared to a browser.";
  private static final String _ACTIVE_DATA_SAMPLES_DESC = " - shows ADS in table, tree and treeTable";
  private static final String _AUTOMATIC_PPR_DESC = " - shows auto-PPR functionality";
  private static final String _CLIENT_BEHAVIR_HOLDER_DESC = " - shows client behavior holder support";
  private static final String _XML_MENUMODEL_DESC = " - shows xml menuModel usage";
  private static final String _WITHOUT_XML_MENUMODEL_DESC = " - shows menuModel usage without XMLMenuModel";
  private static final String _MENUBAR_USING_MENUMODEL_DESC = " - shows menuBar using menuModel";
  private static final String _PANEL_SPRINGBOARD_APP_DESC  = " - Example of a panelSpringboard in a panelStretchLayout with sliding News";
  private static final String _DYNAMIC_FACES_FORM_FLATTEN_DESC = " - Demo of dynamic faces form flatten";
  private static final String _DYNAMIC_FACES_FORM_HIERARCHICAL_DESC = " - Demo of dynamic faces form hierarchial";
  private static final String _DYNAMIC_FACES_TABLE_FLATTEN_DESC = " - Demo of dynamic faces table flatten";
  private static final String _DYNAMIC_FACES_TABLE_FLATTEN_WITH_PC_DESC = " - Demo of dynamic faces table flatten with PC";
  private static final String _DYNAMIC_FACES_TABLE_HIERARCHICAL_DESC = " - Demo of dynamic faces table hierarchial";
 

  @SuppressWarnings("compatibility:5914821950940510162")
  private static final long serialVersionUID = 1L;
}
