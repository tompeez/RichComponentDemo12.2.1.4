package oracle.adfdemo.view.feature.rich.treemap.data;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.TreeModel;


public class SparseTreeNodeCreator {
  public TreeModel newModel(int depth, int numChildren, Random random) {
    SparseTreeNodeCreator creator = new SparseTreeNodeCreator();
    TreeNode root = creator.createNode(depth, numChildren, random);
    return new ChildPropertyTreeModel(root, "children");
  }
  
  private int m_id = 0;
    
  private TreeNode createNode(int depth, int numChildren, Random random) {
    String id = String.valueOf(m_id++);
    double total = 100;
    
    // Each node will recursively assign its children all of its size
    List<TreeNode> children = new ArrayList<TreeNode>();  
    if(numChildren > 0) {
      if(depth > 0) {
        total = 0;
        for(int i=0; i<numChildren; i++) {
          TreeNode child = createNode(depth-1, random.nextInt(numChildren), random);
          total += child.getSize().doubleValue();
          children.add(child); 
        } 
      }
      else {
        total = 100;
      }
    }
    
    TreeNode node = new TreeNode("Node " + id, (int) total, _createColor(random));   
    node.getChildren().addAll(children);
    return node;
  }

  protected Color _createColor(Random random) {
    double modifier = random.nextDouble();
    if(modifier < 0.25)
      return new Color(38,125,179);
    else if(modifier < 0.5)
      return new Color(104,193,130);
    else if(modifier < 0.75)
      return new Color(250,213,92);
    else
      return new Color(237,102,71);
  }
}
