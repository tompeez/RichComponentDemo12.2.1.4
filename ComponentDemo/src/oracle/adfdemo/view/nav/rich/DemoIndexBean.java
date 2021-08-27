/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import oracle.adfdemo.view.mobile.MobileSettingsBean;

import org.apache.myfaces.trinidad.model.ViewIdPropertyMenuModel;


/**
 * Bean used with the tag guide menu page.
 */
public class DemoIndexBean implements Serializable
{
  /**
   * Generates the search index used by the DemoFindServlet (used by both the OpenSearch browser
   * utility that installs into the browser as well as by the find field in the demo page templates).
   * @return a String array of URL segments whose paths are relative to the components directory
   *         excluding the ".jspx" file extension that also contain slash characters or navigation
   *         outcome names that do not contain slash characters.  This might be an empty array in
   *         the event that the search index could not be performed (e.g. due to a MenuModel that
   *         has not yet been loaded)
   */
  public final static Map<String,String> getFindServletPaths()
  {
    try
    {
      DemoIndexBean indexBean = new DemoIndexBean();
      HashMap<String,String> findServletPaths = new HashMap<String,String>();

      // Add the hard-coded items:
      findServletPaths.put("Main - index", "../index");
      findServletPaths.put("Tag Guide - index", "guide");
      findServletPaths.put("Styles - index", "styles/index");
      findServletPaths.put("Feature Demos - index", "../feature/index");
      findServletPaths.put("Sample Page Templates - index", "../feature/templates");
      findServletPaths.put("Visual Designs - index", "../visualDesigns/index");
      findServletPaths.put("Commonly Confused Components - index", "../confusedComponents/index");

      // Add the dynamically-generated items:
      List<List<ItemSection>> tagGuideItems = indexBean.getTagGuideAlphaColumns();
      for (List<ItemSection> column : tagGuideItems)
      {
        for (ItemSection section : column)
        {
          List<ItemData> sectionData = section.getSectionData();
          _addDestinationsForSection(findServletPaths, "Tag Guide - ", "", sectionData);
        }
      }

      // Feature Demos:
      List<ItemSection> frameworkFeatureSections = indexBean.getFrameworkFeatureSections();
      for (ItemSection section : frameworkFeatureSections)
      {
        List<ItemData> sectionData = section.getSectionData();
        _addDestinationsForSection(findServletPaths, "Feature Demos - ", "../feature/", sectionData);
      }

      // Visual Designs:
      _addDestinationsForSection(findServletPaths, "Visual Designs - ", "../visualDesigns/", indexBean._getVisualDesignItems());

      // Styles:
      _addDestinationsForSection(findServletPaths, "Styles - ", "styles/", indexBean._getStyleItems());

      return findServletPaths;
    }
    catch (Exception e)
    {
      // The ViewIdPropertyMenuModel has a dependency on the FacesContext when initializing the
      // wrapped data of the menu model.  It is possible that it will throw a NullPointerException.
      // If this happens, we need to give up and try again later.
      return Collections.emptyMap();
    }
  }

  private static void _addDestinationsForSection(
    Map<String,String> findServletPaths,
    String             labelPrefix,
    String             destinationPrefix,
    List<ItemData>     sectionData)
  {
    for (ItemData itemData : sectionData)
    {
      // Skip items that are disabled:
      if (!itemData.isDisabled())
      {
        // Skip items that have no destination (can't do action outcomes from OpenSearch):
        String destination = itemData.getDestination();
        String outcome     = itemData.getOutcome();
        String text        = labelPrefix + itemData.getText();
        if (destination != null)
        {
          // Prepend the relative path prefix:
          findServletPaths.put(text, destinationPrefix + destination);
        }
        else if (outcome != null)
        {
          // Prepend the relative path prefix:
          findServletPaths.put(text, outcome);
        }
      }
    }
  }

  /**
   * Gets the data for the raw sections on the alphabetical view of the tag guide index page.
   * @return a list of ItemData objects used to build page links
   */
  private List<ItemSection> _getTagGuideAlphaSections()
  {
    if (_TAG_GUIDE_ALPHABETICAL_SECTIONS == null)
    {
      List<ItemSection> rawSectionsA = _getTagGuideComponentGroupSections();
      List<ItemSection> rawSectionsB = _getTagGuideOtherGroupSections();
      List<ItemSection> alphabetizedSections =
        _getAlphaSectionsFromGroupSections(rawSectionsA, rawSectionsB);

      _TAG_GUIDE_ALPHABETICAL_SECTIONS = alphabetizedSections;
    }
    return _TAG_GUIDE_ALPHABETICAL_SECTIONS;
  }

  /**
   * Gets the data for the columns on the alphabetical view of the tag guide index page.
   * @return a list of ItemData objects used to build page links
   */
  public List<List<ItemSection>> getTagGuideAlphaColumns()
  {
    int numColumns = _getNumberOfColumnsForAgent(4);

    if (_TAG_GUIDE_ALPHABETICAL_COLUMNS == null ||
      _TAG_GUIDE_ALPHABETICAL_COLUMNS.size() != numColumns)
    {
      List<ItemSection> rawSections = _getTagGuideAlphaSections();
      _TAG_GUIDE_ALPHABETICAL_COLUMNS = _rearrangeSectionsIntoColumns(numColumns, rawSections);
    }

    return _TAG_GUIDE_ALPHABETICAL_COLUMNS;
  }

  /**
   * Given one or more Lists of ItemSections, this method returns a List of ItemSections arranged
   * into alphabetical sections.
   */
  private List<ItemSection> _getAlphaSectionsFromGroupSections(
    List<ItemSection>... rawGroupSections)
  {
    ArrayList<ItemData> rawData = new ArrayList<ItemData>();

    for (int i=0; i<rawGroupSections.length; i++)
    {
      List<ItemSection> rawGroupSection = rawGroupSections[i];

      // Extract the item data:
      for (ItemSection section : rawGroupSection)
      {
        List<ItemData> sectionData = section.getSectionData();
        for (ItemData itemData : sectionData)
        {
          rawData.add(itemData);
        }
      }
    }

    // Sort the raw data:
    Collections.sort(rawData, new ItemDataComparator());

    // Create sections based on the first character of each item:
    ArrayList<ItemSection> alphabetizedSections = new ArrayList<ItemSection>();
    char currentLetter = '_';
    ItemSection section = null;
    ArrayList<ItemData> currentItems = new ArrayList<ItemData>();
    for (ItemData itemData : rawData)
    {
      char itemLetter = Character.toUpperCase(itemData.getText().charAt(0));
      if (section == null)
      {
        // Create the first section:
        currentLetter = itemLetter;
        currentItems.add(itemData);
        section = new ItemSection(""+currentLetter, currentItems);
        alphabetizedSections.add(section);
      }
      else if (itemLetter == currentLetter)
      {
        // Add to the existing section:
        currentItems.add(itemData);
      }
      else
      {
        // Start a new section:
        currentLetter = itemLetter;
        currentItems = new ArrayList<ItemData>();
        currentItems.add(itemData);
        section = new ItemSection((""+currentLetter).toUpperCase(), currentItems);
        alphabetizedSections.add(section);
      }
    }
    return alphabetizedSections;
  }

  /**
   * Gets the data for the columns on the component groupings view of the tag guide index page.
   * @return a list of ItemData objects used to build page links
   */
  public List<List<ItemSection>> getTagGuideComponentGroupColumns()
  {
    int numColumns = _getNumberOfColumnsForAgent(4);

    if (_TAG_GUIDE_COMPONENT_GROUP_COLUMNS == null ||
      _TAG_GUIDE_COMPONENT_GROUP_COLUMNS.size() != numColumns)
    {
      List<ItemSection> rawSections = _getTagGuideComponentGroupSections();
      _TAG_GUIDE_COMPONENT_GROUP_COLUMNS = _rearrangeSectionsIntoColumns(numColumns, rawSections);
    }
    return _TAG_GUIDE_COMPONENT_GROUP_COLUMNS;
  }

  /**
   * Gets the data for the raw sections on the component groupings view of the tag guide index page.
   * @return a list of ItemData objects used to build page links
   */
  private List<ItemSection> _getTagGuideComponentGroupSections()
  {
    if (_TAG_GUIDE_COMPONENT_GROUP_SECTIONS == null)
    {
      ArrayList<ItemSection> sections = new ArrayList<ItemSection>();

      DemoTGGroupMenuModel menuModel = new DemoTGGroupMenuModel();
      DemoItemNode rowData = (DemoItemNode)menuModel.getRowData(0);
      List<DemoItemNode> modelSections = rowData.getChildren();
      for (DemoItemNode modelSection : modelSections)
      {
        String sectionLabel = modelSection.getLabel();

        // this section belongs to the "components" group
        List<DemoItemNode> children = modelSection.getChildren();
        ArrayList<ItemData> items = new ArrayList<ItemData>();
        for (DemoItemNode node : children)
        {
          if (node.getChildren() == null)
          {
            items.add(
              new ItemData(
                node.getLabel(),
                null,
                node.getOutcome(),
                node.getDestination(),
                node.getShortDesc(),
                node.getIco(),
                node.getIconLarge(),
                node.getIconCarousel(),
                "Components - " + sectionLabel,
                node.isDeprecated()));
          }
          else
          {
            List<DemoItemNode> toExpand = new ArrayList<DemoItemNode>( node.getChildren());
            while (!toExpand.isEmpty())
            {
              DemoItemNode subNode = toExpand.remove(0);
              if (subNode.getChildren() == null)
                items.add(
                  new ItemData(
                    subNode.getLabel(),
                    null,
                    subNode.getOutcome(),
                    subNode.getDestination(),
                    subNode.getShortDesc(),
                    subNode.getIco(),
                    subNode.getIconLarge(),
                    subNode.getIconCarousel(),
                    "Components - " + sectionLabel,
                    subNode.isDeprecated()));
              else
                toExpand.addAll(subNode.getChildren());
            }
          }
        }
        sections.add(new ItemSection(sectionLabel, items));
      }

      _TAG_GUIDE_COMPONENT_GROUP_SECTIONS = sections;
    }
    return _TAG_GUIDE_COMPONENT_GROUP_SECTIONS;
  }

  /**
   * Gets the data for the columns on the other groupings view of the tag guide index page.
   * @return a list of ItemData objects used to build page links
   */
  public List<List<ItemSection>> getTagGuideOtherGroupColumns()
  {
    int numColumns = _getNumberOfColumnsForAgent(3);

    if (_TAG_GUIDE_OTHER_GROUP_COLUMNS == null ||
      _TAG_GUIDE_OTHER_GROUP_COLUMNS.size() != numColumns)
    {
      List<ItemSection> rawSections = _getTagGuideOtherGroupSections();
      _TAG_GUIDE_OTHER_GROUP_COLUMNS = _rearrangeSectionsIntoColumns(numColumns, rawSections);
    }

    return _TAG_GUIDE_OTHER_GROUP_COLUMNS;
  }

  /**
   * Gets the data for the raw sections on the other groupings view of the tag guide index page.
   * @return a list of ItemData objects used to build page links
   */
  private List<ItemSection> _getTagGuideOtherGroupSections()
  {
    if (_TAG_GUIDE_OTHER_GROUP_SECTIONS == null)
    {
      ArrayList<ItemSection> sections = new ArrayList<ItemSection>();

      DemoTGGroupMenuModel menuModel = new DemoTGGroupMenuModel();
      DemoItemNode rowData = (DemoItemNode)menuModel.getRowData(1);
      List<DemoItemNode> modelSections = rowData.getChildren();
      for (DemoItemNode modelSection : modelSections)
      {
        String sectionLabel = modelSection.getLabel();

        // this section belongs to the "others" group
        List<DemoItemNode> children = modelSection.getChildren();
        ArrayList<ItemData> items = new ArrayList<ItemData>();
        for (DemoItemNode node : children)
        {
          items.add(
            new ItemData(
              node.getLabel(),
              null,
              node.getOutcome(),
              node.getDestination(),
              node.getShortDesc(),
              node.getIco(),
              node.getIconLarge(),
              node.getIconCarousel(),
              "Other Tags - " + sectionLabel,
              node.isDeprecated()));
        }
        sections.add(new ItemSection(sectionLabel, items));
      }

      _TAG_GUIDE_OTHER_GROUP_SECTIONS = sections;
    }
    return _TAG_GUIDE_OTHER_GROUP_SECTIONS;
  }

  public DemoIndexListBean getTagGuideListBean()
  {
    if (_TAG_GUIDE_LIST_BEAN == null)
    {
      List<ItemSection> sections = _getTagGuideAlphaSections();
      ArrayList<ItemData> list = new ArrayList<ItemData>();
      for (ItemSection section : sections)
      {
        list.addAll(section.getSectionData());
      }
      _TAG_GUIDE_LIST_BEAN = new DemoIndexListBean(list);
    }

    return _TAG_GUIDE_LIST_BEAN;
  }

  /**
   * Divide up the sections into the specified number of columns.
   * @param columnCount the desired number of columns
   * @param rawSections the raw sections which might get split up
   * @return a list of lists of sections for each column
   */
  private List<List<ItemSection>> _rearrangeSectionsIntoColumns(
    int               columnCount,
    List<ItemSection> rawSections)
  {
    List<List<ItemSection>> columns = new ArrayList<List<ItemSection>>();
    int itemCount = 0;

    for (ItemSection section : rawSections)
    {
      itemCount += section.getSize();
    }
    int rawSectionCount = rawSections.size();
    itemCount += rawSectionCount; // count the sections too
    itemCount += columnCount - 1; // count each column except the last for possibly split sections

    // Add more items per column in case of lots of splitting:
    int itemsPerColumn = (int)Math.ceil((double)itemCount / (double)columnCount);

    itemCount = 0;
    ArrayList<ItemSection> column = new ArrayList<ItemSection>();
    for (ItemSection section : rawSections)
    {
      int sectionSize = section.getSize() + 1;

      if (itemCount + sectionSize <= itemsPerColumn)
      {
        // If we can fit all of the section's items into the column, just add the column as is:
        column.add(section);
        itemCount += sectionSize;
      }
      else
      {
        // The section's items could not fit as one chunk so we need to start trying to fit it in:
        int spaceAvailable = itemsPerColumn - itemCount;

        if (spaceAvailable == 0)
        {
          // Create a new column:
          columns.add(column); // add the previous column to the list
          column         = new ArrayList<ItemSection>();
          itemCount      = 0;
          spaceAvailable = itemsPerColumn;
        }

        if (spaceAvailable >= sectionSize)
        {
          // If we can fit all of the section's items into the column, just add the column as is:
          column.add(section);
          itemCount += sectionSize;
        }
        else
        {
          // We need to split the section up in to at least 2 separate sections:
          String         sectionText   = section.getText();
          String         continuedText = sectionText + " (continued)";
          List<ItemData> itemsToUse    = section.getSectionData();
          int            startingIndex = 0;
          while (startingIndex < sectionSize - 1)
          {
            ArrayList<ItemData> splitData = new ArrayList<ItemData>();
            int lastIndex = Math.min(startingIndex + spaceAvailable + 1, sectionSize - 2);
            for (int i=startingIndex; i<=lastIndex; i++)
            {
              splitData.add(itemsToUse.get(i));
            }
            startingIndex = lastIndex + 1;
            ItemSection splitSection = new ItemSection(sectionText, splitData);
            column.add(splitSection);
            itemCount += splitSection.getSize() + 1;

            if (itemCount >= itemsPerColumn)
            {
              // The column is full, start another column:
              columns.add(column); // add the previous column to the list
              column         = new ArrayList<ItemSection>();
              itemCount      = 0;
              spaceAvailable = itemsPerColumn;
              sectionText    = continuedText;
            }
          }
        }
      }
    }

    // Add the last column to the list:
    columns.add(column);

    return columns;
  }

  /**
   * Gets the data for the columns on the alphabetical view of the feature index page.
   * @return a list of ItemData objects used to build page links
   */
  public List<List<ItemSection>> getFeatureAlphaColumns()
  {
    int numColumns = _getNumberOfColumnsForAgent(4);

    if (_FRAMEWORK_FEATURE_ALPHABETICAL_COLUMNS == null ||
      _FRAMEWORK_FEATURE_ALPHABETICAL_COLUMNS.size() != numColumns)
    {
      List<ItemSection> rawSections           = _getFrameworkFeatureAlphaSections();
      _FRAMEWORK_FEATURE_ALPHABETICAL_COLUMNS = _rearrangeSectionsIntoColumns(
                                                  numColumns, rawSections);
    }
    return _FRAMEWORK_FEATURE_ALPHABETICAL_COLUMNS;
  }

  /**
   * Gets the data for the columns on the groupings view of the feature index page.
   * @return a list of ItemData objects used to build page links
   */
  public List<List<ItemSection>> getFeatureGroupColumns()
  {
    int numColumns = _getNumberOfColumnsForAgent(4);

    if (_FRAMEWORK_FEATURE_GROUP_COLUMNS == null ||
      _FRAMEWORK_FEATURE_GROUP_COLUMNS.size() != numColumns)
    {
      List<ItemSection> rawSections    = getFrameworkFeatureSections();
      _FRAMEWORK_FEATURE_GROUP_COLUMNS = _rearrangeSectionsIntoColumns(numColumns, rawSections);
    }

    return _FRAMEWORK_FEATURE_GROUP_COLUMNS;
  }

  private List<ItemSection> _getFrameworkFeatureAlphaSections()
  {
    if (_FRAMEWORK_FEATURE_ALPHABETICAL_SECTIONS == null)
    {
      List<ItemSection> rawSectionsA         = getFrameworkFeatureSections();
      List<ItemSection> alphabetizedSections = _getAlphaSectionsFromGroupSections(rawSectionsA);

      _FRAMEWORK_FEATURE_ALPHABETICAL_SECTIONS = alphabetizedSections;
    }
    return _FRAMEWORK_FEATURE_ALPHABETICAL_SECTIONS;
  }

  /**
   * Gets the data for the sections on the Feature Demos index page.
   * @return a list of ItemData objects used to build page links
   */
  public List<ItemSection> getFrameworkFeatureSections()
  {
    if (_FRAMEWORK_FEATURE_GROUP_SECTIONS == null)
    {
      ArrayList<ItemSection> sections = new ArrayList<ItemSection>();

      DemoCombinedMenuModel menuModel = new DemoCombinedMenuModel();
      int sectionCount = menuModel.getRowCount();
      for (int i=0; i<sectionCount; i++)
      {
        DemoItemNode modelSection = (DemoItemNode)menuModel.getRowData(i);
        String sectionLabel = modelSection.getLabel();
        List<DemoItemNode> children = modelSection.getChildren();
        ArrayList<ItemData> items = new ArrayList<ItemData>();
        for (DemoItemNode node : children)
        {
          if(node.getChildren() == null)
          {
            items.add(
              new ItemData(
                node.getLabel(),
                null,
                node.getOutcome(),
                node.getDestination(),
                node.getShortDesc(),
                node.getIco(),
                node.getIconLarge(),
                node.getIconCarousel(),
                sectionLabel,
                node.isDeprecated()));
          }
          else
          {
            List<DemoItemNode> toExpand = new ArrayList<DemoItemNode>(node.getChildren());
            while (!toExpand.isEmpty())
            {
              DemoItemNode subNode = toExpand.remove(0);
              if (subNode.getChildren() == null)
              {
                if (!(sectionLabel.equals("Data Visualization Tools")) || (subNode.getLabel().indexOf(" Index") > 0))
                  items.add(
                    new ItemData(
                      subNode.getLabel(),
                      null,
                      subNode.getOutcome(),
                      subNode.getDestination(),
                      subNode.getShortDesc(),
                      subNode.getIco(),
                      subNode.getIconLarge(),
                      subNode.getIconCarousel(),
                      subNode.getLabel(),
                      subNode.isDeprecated()));
              }
              else
              {
                toExpand.addAll(subNode.getChildren());
              }
            }
          }
        }
        sections.add(new ItemSection(sectionLabel, items));
      }

      _FRAMEWORK_FEATURE_GROUP_SECTIONS = sections;
    }
    return _FRAMEWORK_FEATURE_GROUP_SECTIONS;
  }

  public DemoIndexListBean getFeatureListBean()
  {
    if (_FRAMEWORK_FEATURE_LIST_BEAN == null)
    {
      List<ItemSection> sections = _getFrameworkFeatureAlphaSections();
      ArrayList<ItemData> list = new ArrayList<ItemData>();
      for (ItemSection section : sections)
      {
        list.addAll(section.getSectionData());
      }
      _FRAMEWORK_FEATURE_LIST_BEAN = new DemoIndexListBean(list);
    }

    return _FRAMEWORK_FEATURE_LIST_BEAN;
  }

  /**
   * Gets the data for the links on the page templates index page.
   * @return a list of ItemData objects used to build page links
   */
  public List<ItemData> getPageTemplateItems()
  {
    if (_PAGE_TEMPLATE_ITEMS == null)
    {
      ArrayList<ItemData> items = new ArrayList<ItemData>();

      DemoTemplateMenuModel menuModel = new DemoTemplateMenuModel();
      DemoItemNode rowData = (DemoItemNode)menuModel.getRowData(0);
      List<DemoItemNode> children = rowData.getChildren();
      for (DemoItemNode node : children)
      {
        items.add(
          new ItemData(
            node.getLabel(),
            null,
            node.getOutcome(),
            node.getDestination(),
            node.getShortDesc(),
            node.getIco(),
            node.getIconLarge(),
            node.getIconCarousel(),
            "Sample Page Templates",
            node.isDeprecated()));
      }

      _PAGE_TEMPLATE_ITEMS = items;
    }

    return _PAGE_TEMPLATE_ITEMS;
  }

  /**
   * Gets the data for the columns on the alphabetical view of the visual design index page.
   * @return a list of ItemData objects used to build page links
   */
  public List<List<ItemSection>> getVisualDesignAlphaColumns()
  {
    int numColumns = _getNumberOfColumnsForAgent(4);

    if (_VISUAL_DESIGN_ALPHABETICAL_COLUMNS == null ||
      _VISUAL_DESIGN_ALPHABETICAL_COLUMNS.size() != numColumns)
    {
      List<ItemSection> rawSections       = _getVisualDesignAlphaSections();
      _VISUAL_DESIGN_ALPHABETICAL_COLUMNS = _rearrangeSectionsIntoColumns(numColumns, rawSections);
    }
    return _VISUAL_DESIGN_ALPHABETICAL_COLUMNS;
  }

  /**
   * Gets the data for the columns on the "tag" groupings view of the visual design index page.
   * @return a list of ItemData objects used to build page links
   */
  public List<List<ItemSection>> getVisualDesignGroupColumns()
  {
    int numColumns = _getNumberOfColumnsForAgent(4);

    if (_VISUAL_DESIGN_COLUMNS == null ||
      _VISUAL_DESIGN_COLUMNS.size() != numColumns)
    {
      List<ItemSection> rawSections = _getVisualDesignGroupSections();
      _VISUAL_DESIGN_COLUMNS        = _rearrangeSectionsIntoColumns(numColumns, rawSections);
    }

    return _VISUAL_DESIGN_COLUMNS;
  }

  private List<ItemSection> _getVisualDesignAlphaSections()
  {
    if (_VISUAL_DESIGN_ALPHABETICAL_SECTIONS == null)
    {
      List<ItemSection> rawSectionsA         = _getVisualDesignGroupSections();
      List<ItemSection> alphabetizedSections =
        _getAlphaSectionsFromGroupSections(rawSectionsA);

      _VISUAL_DESIGN_ALPHABETICAL_SECTIONS = alphabetizedSections;
    }
    return _VISUAL_DESIGN_ALPHABETICAL_SECTIONS;
  }

  private List<ItemSection> _getVisualDesignGroupSections()
  {
    if (_VISUAL_DESIGN_SECTIONS == null)
    {
      ArrayList<ItemSection> overallSections = new ArrayList<ItemSection>();

      DemoVisualDesignsMenuModel menuModel = new DemoVisualDesignsMenuModel();
      DemoItemNode rowData = (DemoItemNode)menuModel.getRowData(0);
      List<DemoItemNode> sections = rowData.getChildren();
      for (DemoItemNode section : sections)
      {
        List<DemoItemNode> children = section.getChildren();
        ArrayList<ItemData> items = new ArrayList<ItemData>();

        for (DemoItemNode node : children)
        {
          items.add(
            new ItemData(
              node.getLabel(),
              null,
              node.getOutcome(),
              node.getDestination(),
              node.getShortDesc(),
              node.getIco(),
              node.getIconLarge(),
              node.getIconCarousel(),
              section.getLabel(),
              node.isDeprecated()));
        }

        // Sort the raw data:
        Collections.sort(items, new ItemDataComparator());

        ItemSection vdTagSection = new ItemSection(section.getLabel(), items);
        overallSections.add(vdTagSection);
      }

      _VISUAL_DESIGN_SECTIONS = overallSections;
    }
    return _VISUAL_DESIGN_SECTIONS;
  }

  /**
   * Gets the data for the links on the visual designs index page.
   * @return a list of ItemData objects used to build page links
   */
  private List<ItemData> _getVisualDesignItems()
  {
    if (_VISUAL_DESIGN_ITEMS == null)
    {
      _VISUAL_DESIGN_ITEMS = _getItemDataListForFindServlet(new DemoVisualDesignsMenuModel());
    }

    return _VISUAL_DESIGN_ITEMS;
  }

  public DemoIndexListBean getVisualDesignListBean()
  {
    if (_VISUAL_DESIGN_LIST_BEAN == null)
    {
      List<ItemSection> sections = _getVisualDesignAlphaSections();
      ArrayList<ItemData> list = new ArrayList<ItemData>();
      for (ItemSection section : sections)
      {
        list.addAll(section.getSectionData());
      }
      _VISUAL_DESIGN_LIST_BEAN = new DemoIndexListBean(list);
    }

    return _VISUAL_DESIGN_LIST_BEAN;
  }

  /**
   * Gets the data for the columns on the alphabetical view of the style index page.
   * @return a list of ItemData objects used to build page links
   */
  public List<List<ItemSection>> getStyleAlphaColumns()
  {
    int numColumns = _getNumberOfColumnsForAgent(4);

    if (_STYLE_ALPHABETICAL_COLUMNS == null ||
      _STYLE_ALPHABETICAL_COLUMNS.size() != numColumns)
    {
      List<ItemSection> rawSections = _getStyleAlphaSections();
      _STYLE_ALPHABETICAL_COLUMNS   = _rearrangeSectionsIntoColumns(numColumns, rawSections);
    }
    return _STYLE_ALPHABETICAL_COLUMNS;
  }

  /**
   * Gets the data for the columns on the "tag" groupings view of the style index page.
   * @return a list of ItemData objects used to build page links
   */
  public List<List<ItemSection>> getStyleGroupColumns()
  {
    int numColumns = _getNumberOfColumnsForAgent(4);

    if (_STYLE_COLUMNS == null ||
      _STYLE_COLUMNS.size() != numColumns)
    {
      List<ItemSection> rawSections = _getStyleGroupSections();
      _STYLE_COLUMNS                = _rearrangeSectionsIntoColumns(numColumns, rawSections);
    }

    return _STYLE_COLUMNS;
  }

  private List<ItemSection> _getStyleAlphaSections()
  {
    if (_STYLE_ALPHABETICAL_SECTIONS == null)
    {
      List<ItemSection> rawSectionsA         = _getStyleGroupSections();
      List<ItemSection> alphabetizedSections =
        _getAlphaSectionsFromGroupSections(rawSectionsA);

      _STYLE_ALPHABETICAL_SECTIONS = alphabetizedSections;
    }
    return _STYLE_ALPHABETICAL_SECTIONS;
  }

  private List<ItemSection> _getStyleGroupSections()
  {
    if (_STYLE_SECTIONS == null)
    {
      ArrayList<ItemSection> overallSections = new ArrayList<ItemSection>();

      DemoStyleMenuModel menuModel = new DemoStyleMenuModel();
      DemoItemNode rowData         = (DemoItemNode)menuModel.getRowData(0);
      List<DemoItemNode> sections  = rowData.getChildren();
      for (DemoItemNode section : sections)
      {
        List<DemoItemNode> children = section.getChildren();
        ArrayList<ItemData> items   = new ArrayList<ItemData>();

        for (DemoItemNode node : children)
        {
          items.add(
            new ItemData(
              node.getLabel(),
              null,
              node.getOutcome(),
              node.getDestination(),
              node.getShortDesc(),
              node.getIco(),
              node.getIconLarge(),
              node.getIconCarousel(),
              section.getLabel(),
              node.isDeprecated()));
        }

        // Sort the raw data:
        Collections.sort(items, new ItemDataComparator());

        ItemSection styleTagSection = new ItemSection(section.getLabel(), items);
        overallSections.add(styleTagSection);
      }

      _STYLE_SECTIONS = overallSections;
    }
    return _STYLE_SECTIONS;
  }

  /**
   * Gets the data for the links on the visual designs index page.
   * @return a list of ItemData objects used to build page links
   */
  private List<ItemData> _getStyleItems()
  {
    if (_STYLE_ITEMS == null)
    {
      _STYLE_ITEMS = _getItemDataListForFindServlet(new DemoStyleMenuModel());
    }

    return _STYLE_ITEMS;
  }

  public DemoIndexListBean getStyleListBean()
  {
    if (_STYLE_LIST_BEAN == null)
    {
      List<ItemSection> sections = _getStyleAlphaSections();
      ArrayList<ItemData> list = new ArrayList<ItemData>();
      for (ItemSection section : sections)
      {
        list.addAll(section.getSectionData());
      }
      _STYLE_LIST_BEAN = new DemoIndexListBean(list);
    }

    return _STYLE_LIST_BEAN;
  }

  private static List<ItemData> _getItemDataListForFindServlet(ViewIdPropertyMenuModel menuModel)
  {
    ArrayList<ItemData> items = new ArrayList<ItemData>();

    DemoItemNode rowData = (DemoItemNode)menuModel.getRowData(0);
    List<DemoItemNode> sections = rowData.getChildren();
    for (DemoItemNode section : sections)
    {
      List<DemoItemNode> children = section.getChildren();
      for (DemoItemNode node : children)
      {
        items.add(
          new ItemData(
            node.getLabel(),
            null,
            node.getOutcome(),
            node.getDestination(),
            node.getShortDesc(),
            node.getIco(),
            node.getIconLarge(),
            node.getIconCarousel(),
            section.getLabel(),
            node.isDeprecated()));
      }
    }

    // Sort the raw data:
    Collections.sort(items, new ItemDataComparator());

    return items;
  }

  private int _getNumberOfColumnsForAgent(int defaultValue)
  {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    MobileSettingsBean mobileSettings = MobileSettingsBean.getInstance(facesContext);

    if (mobileSettings.isMobilePlatform())
    {
      return mobileSettings.isMobileTablet() ? 2 : 1;
    }
    else
    {
      return defaultValue;
    }
  }

  public static class ItemData implements Serializable
  {
    public ItemData(
      String text,
      String description,
      String outcome,
      String destination,
      String shortDesc,
      String smallIcon,
      String largeIcon,
      String carouselIcon,
      String path,
      boolean deprecated)
    {
      _text         = text;
      _description  = description;
      _outcome      = outcome;
      _destination  = destination;
      _disabled     = false;
      _linkNote     = shortDesc;
      _smallIcon    = smallIcon;
      _largeIcon    = largeIcon;
      _carouselIcon = carouselIcon;
      _path         = path;
      _deprecated   = deprecated;
    }

    public String getSmallIcon()
    {
      return _smallIcon;
    }

    public String getLargeIcon()
    {
      return _largeIcon;
    }

    public String getCarouselIcon()
    {
      return _carouselIcon;
    }

    public String getText()
    {
      return _text;
    }

    public String getDescription()
    {
      return _description;
    }

    public String getOutcome()
    {
      return _outcome;
    }

    public String getDestination()
    {
      return _destination;
    }

    public boolean isDisabled()
    {
      return _disabled;
    }

    public String getLinkNote()
    {
      return _linkNote;
    }

    public String getPath()
    {
      return _path;
    }

    public boolean isDeprecated()
    {
      return _deprecated;
    }

    public String getInlineStyle()
    {
      if (_disabled)
      {
        return "text-decoration:line-through;color:black";
      }
      if (_deprecated)
      {
        return "font-style:italic;color:gray";
      }
      return "";
    }

    @SuppressWarnings("compatibility:-1902566628833107196")
    private static final long serialVersionUID = 1L;

    private String  _smallIcon;
    private String  _largeIcon;
    private String  _carouselIcon;
    private String  _text;
    private String  _description;
    private String  _outcome;
    private String  _destination;
    private boolean _disabled;
    private String  _linkNote;
    private String  _path;
    private boolean _deprecated;
  }

  public static class ItemSection extends ItemData implements Serializable
  {
    public ItemSection(String text, List<ItemData> sectionData)
    {
      super(text, null/*description*/, null/*outcome*/, null/*destination*/, null/*shortDesc*/, null/*smallIcon*/, null/*largeIcon*/, null/*carouselIcon*/, null/*path*/, false /*deprecated*/);
      _sectionData = sectionData;
    }

    public List<ItemData> getSectionData()
    {
      return _sectionData;
    }

    public int getSize()
    {
      if (_sectionData == null)
      {
        return 0;
      }
      return _sectionData.size();
    }

    @SuppressWarnings("compatibility:-3130270509464173082")
    private static final long serialVersionUID = 1L;

    private List<ItemData> _sectionData;
  }

  public static class ItemDataComparator implements Comparator<ItemData>, Serializable
  {
    public int compare(ItemData itemData1, ItemData itemData2)
    {
      // Compare by names first:
      String text1 = itemData1.getText();
      String text2 = itemData2.getText();
      int comparison = text1.compareToIgnoreCase(text2);
      return comparison;
    }

    @SuppressWarnings("compatibility:1143746316725861282")
    private static final long serialVersionUID = 1L;
  }

  @SuppressWarnings("compatibility:-4496334439737500618")
  private static final long serialVersionUID = 1L;

  private static List<List<ItemSection>> _TAG_GUIDE_ALPHABETICAL_COLUMNS;
  private static List<ItemSection>       _TAG_GUIDE_ALPHABETICAL_SECTIONS;
  private static List<List<ItemSection>> _TAG_GUIDE_COMPONENT_GROUP_COLUMNS;
  private static List<ItemSection>       _TAG_GUIDE_COMPONENT_GROUP_SECTIONS;
  private static List<List<ItemSection>> _TAG_GUIDE_OTHER_GROUP_COLUMNS;
  private static List<ItemSection>       _TAG_GUIDE_OTHER_GROUP_SECTIONS;
  private static DemoIndexListBean       _TAG_GUIDE_LIST_BEAN;
  private static List<List<ItemSection>> _FRAMEWORK_FEATURE_ALPHABETICAL_COLUMNS;
  private static List<ItemSection>       _FRAMEWORK_FEATURE_ALPHABETICAL_SECTIONS;
  private static List<List<ItemSection>> _FRAMEWORK_FEATURE_GROUP_COLUMNS;
  private static List<ItemSection>       _FRAMEWORK_FEATURE_GROUP_SECTIONS;
  private static DemoIndexListBean       _FRAMEWORK_FEATURE_LIST_BEAN;
  private static List<ItemData>          _PAGE_TEMPLATE_ITEMS;
  private static List<List<ItemSection>> _VISUAL_DESIGN_ALPHABETICAL_COLUMNS;
  private static List<ItemSection>       _VISUAL_DESIGN_ALPHABETICAL_SECTIONS;
  private static List<List<ItemSection>> _VISUAL_DESIGN_COLUMNS;
  private static List<ItemSection>       _VISUAL_DESIGN_SECTIONS;
  private static List<ItemData>          _VISUAL_DESIGN_ITEMS;
  private static DemoIndexListBean       _VISUAL_DESIGN_LIST_BEAN;
  private static List<List<ItemSection>> _STYLE_ALPHABETICAL_COLUMNS;
  private static List<ItemSection>       _STYLE_ALPHABETICAL_SECTIONS;
  private static List<List<ItemSection>> _STYLE_COLUMNS;
  private static List<ItemSection>       _STYLE_SECTIONS;
  private static List<ItemData>          _STYLE_ITEMS;
  private static DemoIndexListBean       _STYLE_LIST_BEAN;
}
