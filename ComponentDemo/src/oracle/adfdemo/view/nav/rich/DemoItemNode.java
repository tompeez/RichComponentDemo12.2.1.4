/** Copyright (c) 2009, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import java.util.List;

/**
 * Represents an item (or node) in a parent MenuModel. It holds the focusViewId for the node which
 * determines the destination view to goto on navigation.
 */
public class DemoItemNode
  extends DemoNavigationItemBase
  implements Serializable,Comparable
{
  public DemoItemNode()
  {
    super();
  }

  /**
   * Constructor to create an leaf item node that has no children but provides a destination
   * @param label the label for the node
   * @param focusViewId the relative path to the destination page or the outcome as needed
   * @param icon the icon to use to represent this node
   */
  public DemoItemNode(String label, String focusViewId, String icon)
  {
    super(label, icon);
    setFocusViewId(focusViewId);
  }

  public DemoItemNode(String label, String focusViewId, String icon,
                      String outcome)
  {
    super(label, icon);
    setFocusViewId(focusViewId);
    setOutcome(outcome);
  }

  public DemoItemNode(String label, String focusViewId, String icon,
                      String outcome, String shortDesc)
  {
    super(label, icon);
    setFocusViewId(focusViewId);
    setOutcome(outcome);
    setShortDesc(shortDesc);
  }

    /**
     * Constructs a DemoItemNode representing a leaf node that provides categoty and backing bean
     * the node belongs to.
     *
     * @param label the label for the node
     * @param focusViewId the relative path to the destination page or the outcome as needed
     * @param icon the icon to use to represent this node
     * @param outcome
     * @param shortDesc short desctiption of the node
     * @param category the category the node belongs to
     * @param componentName the component name
     * @param backingBean the relative path to the backing bean used with this node
     */
    public DemoItemNode(String label, String focusViewId, String icon,
                        String outcome, String shortDesc, String category,
                        String componentName, String backingBean)
    {
      super(label, icon);
      setFocusViewId(focusViewId);
      setOutcome(outcome);
      setShortDesc(shortDesc);
      setCategory(category);
      setComponentName(componentName);
      setBackingBean(backingBean);
    }

  /**
   * Constructs a DemoItemNode representing a leaf node that provides categoty and backing bean
   * the node belongs to.
   * @param label the label for the node
   * @param focusViewId the relative path to the destination page or the outcome as needed
   * @param icon the icon to use to represent this node
   * @param outcome
   * @param shortDesc short desctiption of the node
   * @param category the category the node belongs to
   * @param componentName the component name
   * @param backingBean the relative path to the backing bean used with this node
   * @param largeIcon the large icon
   * @param carouselIcon the largest icon for the carousel
   * @param deprecated is item deprecated
   */
  public DemoItemNode(String label, String focusViewId, String icon,
                      String outcome, String shortDesc, String category,
                      String componentName, String backingBean, String largeIcon, String carouselIcon,
                      boolean deprecated)
  {
    super(label, icon);
    setFocusViewId(focusViewId);
    setOutcome(outcome);
    setShortDesc(shortDesc);
    setCategory(category);
    setComponentName(componentName);
    setBackingBean(backingBean);
    setIconLarge(largeIcon);
    setIconCarousel(carouselIcon);
    setDeprecated(deprecated);
  }

  /**
   * Constructs a DemoItemNode representing a group of other group nodes or leaf nodes or both
   * @param label
   * @param icon
   * @param children
   */
  public DemoItemNode(String label, String icon, List<DemoItemNode> children)
  {
    super(label, icon, children);
    setIconLarge("/images/icons-large/folder.png");
  }

  /**
   * Compares the labels of 2 DemoItemNodes for ordering
   * @param demoItemNode another node to compare against.
   */
  public int compareTo(Object demoItemNode)
  {
    String label          = getLabel().toLowerCase();
    String compareToLabel = ((DemoItemNode)demoItemNode).getLabel().toLowerCase();

    return label.compareTo(compareToLabel);
  }

  public void setFocusViewId(String focusViewId)
  {
    _focusViewId = focusViewId;
  }

  public String getFocusViewId()
  {
    return _focusViewId;
  }

  public void setOutcome(String outcome)
  {
    _outcome = outcome;
  }

  public String getOutcome()
  {
    return _outcome;
  }

  public String actionOutcome()
  {
    return getOutcome();
  }

  public void setShortDesc(String shortDesc)
  {
    _shortDesc = shortDesc;
  }

  public String getShortDesc()
  {
    return _shortDesc;
  }

  public boolean isDeprecated()
  {
    return _deprecated;
  }

  public void setDeprecated(boolean deprecated)
  {
    _deprecated = deprecated;
  }

  public void setCategory(String category)
  {
    _category = category;
  }

  public String getCategory()
  {
    return _category;
  }

  public void setComponentName(String componentName)
  {
    _componentName = componentName;
  }

  public String getComponentName()
  {
    return _componentName;
  }

  public void setBackingBean(String backingBean)
  {
    _backingBean = backingBean;
  }

  public String getBackingBean()
  {
    return _backingBean;
  }

  public String getBeanFileName()
  {
      String bean = getBackingBean();
      if (bean != null && bean.trim().length() > 0 ) {
          bean = bean.substring(bean.lastIndexOf("/") + 1);
      }

      return bean;
  }

  public String getSourceFileName()
  {
      String source = getFocusViewId();
      if (source != null && source.trim().length() > 0) {
          source = source.substring(source.lastIndexOf("/") + 1);
      }

      return source;
  }

  public String getSource()
  {
    return getFocusViewId() + ".source";
  }

  @SuppressWarnings("compatibility:-9214375935744911706")
  private static final long serialVersionUID = 1L;

  private String _focusViewId = null;
  private String _outcome = null;
  private String _shortDesc = null;
  private boolean _deprecated = false;
  private String _category = null;
  private String _componentName = null;
  private String _backingBean = null;
}
