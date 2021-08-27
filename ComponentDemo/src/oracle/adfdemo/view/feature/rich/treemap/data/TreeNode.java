package oracle.adfdemo.view.feature.rich.treemap.data;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;


public class TreeNode {
  private final String m_text;
  private final Number m_size;
  private final Color m_color;
  private final List<TreeNode> m_children = new ArrayList<TreeNode>();
  
  public TreeNode(String text, Number size, Color color) {
    m_text = text;
    m_size = size;
    m_color = color;
  }

  public String getText() {
    return m_text;
  }


  public Number getSize() {
    return m_size;
  }

  public Color getColor() {
    return m_color;
  }
  
  public void addChild(TreeNode child) {
    m_children.add(child);
  }
  
  public void addChildren(List<TreeNode> children) {
    m_children.addAll(children);
  }

  public List<TreeNode> getChildren() {
    return m_children;
  }
  
  @Override
  public String toString() {
    return m_text + ": " + m_color + " " + Math.round(m_size.doubleValue());
  }
}
