package oracle.adfdemo.view.feature.rich.thematicMap.data;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import oracle.adfdemo.view.feature.rich.hv.util.HVUtils;

public class GroupNode extends AbstractNode {
  public GroupNode(String groupBy, String groupName) {
    setGroupBy(groupBy);
    setGroupName(groupName);
  }
  
  protected List<MajorNode> groupElements = null;

  public void setGroupElements(List<MajorNode> l) {
  
    if (l != null) {
      for (AbstractNode n : l) {
        n.clearChildElements();
      }
    }
    
  this.groupElements = l;
  this.groupCount = l.size();
  }

  private String groupName;

  private int groupCount;
  
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getGroupName() {
    return groupName;
  }

  @Override
  public boolean isTopNode() {
    return false;
  }
  
  @Override
  public String getType() {
    return TYPE_GROUP_NODE;
  }

  public void setGroupCount(int groupCount) {
    this.groupCount = groupCount;
  }

  public int getGroupCount() {
    return groupCount;
  }

  public void generateChildren() {
    childElements = new ArrayList<AbstractNode>();
      for (MajorNode n : groupElements) {
            boolean add = childElements.add(n);
        }
  }
  
  public String getHexColor() {
      Color c = this.getColorFor(groupName);
      String color = HVUtils.toHex(c);
      return color;
  }

    public AbstractNode fetchParent() {
        return null;
    }

    public boolean isHighlighted() {
        return false;
    }

    public int compareTo(Object o) {
        return 0;
    }
}
