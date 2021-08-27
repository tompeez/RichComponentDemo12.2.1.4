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

public class DemoTemplateMenuModel extends ViewIdPropertyMenuModel implements Serializable
{
  public DemoTemplateMenuModel()
  {
    super();
    setViewIdProperty("focusViewId");
    _pageTemplates = _initPageTemplates();
    setWrappedData(_pageTemplates);
  }

  private static DemoItemNode _getDemoItemNode(
    String demoName,
    String pageName,
    String shortDesc)
  {
    return new DemoItemNode(
      demoName,
      "/templates/" + pageName + ".jspx",
      "/adfdt/pageTemplate.png",
      "template." + pageName,
      shortDesc,
      null/*category*/,
      null/*componentName*/,
      null/*backingBean*/,
      "/images/icons-large/pageTemplate.png",
      null/*carouselIcon*/,
      false /*deprecated (currently templates aren't deprecated)*/ );
  }

  public static List<DemoItemNode> getPageTemplateNodes()
  {
    List<DemoItemNode> pageTemplatesList = new ArrayList<DemoItemNode>()
    {
      {
        add(_getDemoItemNode("File Explorer Template", "fileExplorerUsage", _FILE_EXPLORER_TEMPLATE_DESC));
        add(_getDemoItemNode("Panel Page Template", "panelPageUsage", _PANEL_PAGE_TEMPLATE_DESC));
        add(_getDemoItemNode("Tablet Template", "tabletUsage", _TABLET_PAGE_TEMPLATE_DESC));
        add(_getDemoItemNode("Tablet First Template", "tabletFirstTemplateUsage", _TABLET_FIRST_TEMPLATE_DESC));
        add(_getDemoItemNode("Media Behavior Template Demo", "behaviorDemoUsage", _MATCH_MEDIA_BEHAVIOR_TEMPLATE_DEMO_DESC));
      }
    };
    return pageTemplatesList;
  }

  private TreeModel _initPageTemplates()
  {
    List<DemoItemNode> pageTemplatesNodes = getPageTemplateNodes();

    DemoItemNode pageTemplatesGroup = new DemoItemNode("Page Templates", "/images/folder.png", pageTemplatesNodes);

    List pageTemplatesList = new ArrayList();
    pageTemplatesList.add(pageTemplatesGroup);

    TreeModel pageTemplates = new ChildPropertyTreeModel(pageTemplatesList, _CHILDREN);
    return pageTemplates;
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
      TreeModel model = this.getPageTemplates();
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

  public void setPageTemplates(TreeModel pageTemplates)
  {
    this._pageTemplates = pageTemplates;
  }

  public TreeModel getPageTemplates()
  {
    return _pageTemplates;
  }

  private static final String _FILE_EXPLORER_TEMPLATE_DESC = " - simple layout with small number of facets";
  private static final String _PANEL_PAGE_TEMPLATE_DESC = " - very complex layout with large number of facets";
  private static final String _TABLET_PAGE_TEMPLATE_DESC = " - slightly less complex layout with reduced number of facets";
  private static final String _TABLET_FIRST_TEMPLATE_DESC = " - new template for alta skin";
  private static final String _MATCH_MEDIA_BEHAVIOR_TEMPLATE_DEMO_DESC = " - Match Media Behavior usage in a temaplte";
  private static final String _CHILDREN = "children";
  private RowKeySet _foldersTreeState;
  private transient TreeModel _pageTemplates = null;

  @SuppressWarnings("compatibility:-3800908240921102246")
  private static final long serialVersionUID = 1L;
}
