package oracle.adfdemo.view.feature.rich.treemap.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.TreeModel;


public class UnbalancedTreeNodeCreator extends TreeNodeCreator {
  private static final int TOTAL_SIZE = 100000;
  
  public TreeModel newModel(int depth, int minChildren, int maxChildren, Random random) {
    TreeNode root = createNode(depth, minChildren, maxChildren, random, TOTAL_SIZE);
    return new ChildPropertyTreeModel(root, "children");
  }
  
  public TreeModel newMultiRootedModel(int depth, int minChildren, int maxChildren, Random random) {
    TreeNode root = createNode(depth + 1, minChildren, maxChildren, random, TOTAL_SIZE);
    List<TreeNode> roots = root.getChildren();
    return new ChildPropertyTreeModel(roots, "children");
  }
  
  private int m_id = 0;
      
  private TreeNode createNode(int depth, int minChildren, int maxChildren, Random random, int size) {
    String id = String.valueOf(m_id++);
    
    // Create the node
    TreeNode node = new TreeNode("Node " + id, size, _createColor(random));
    
    // Each node will recursively assign its children all of its size
    int numChildren = (maxChildren > minChildren) ? random.nextInt(maxChildren-minChildren) + minChildren : minChildren;
    if(numChildren > 0) {
      double sizes[] = new double[numChildren];
      double totalSize = 0;
      for(int i=0; i<numChildren; i++) {
        sizes[i] = Math.abs(random.nextGaussian());
        totalSize += sizes[i]; 
      }
      
      // Adjust the size proportions by the actual size
      for(int i=0; i<numChildren; i++) {
        sizes[i] = Math.round(size*sizes[i]/totalSize);
      }
      
      // The sum of the children must equal the sum of the parents.  Add the extra onto the first node
      int extra = size;
      for(int i=0; i<numChildren; i++) {
        extra -= sizes[i]; 
      }
      sizes[0] += extra;
      
      // Create the children
      List<TreeNode> children = new ArrayList<TreeNode>();  
      if(depth > 0) {
        for(int i=0; i<numChildren; i++) {
          TreeNode child = createNode(depth-1, minChildren, maxChildren, random, (int) sizes[i]);
          children.add(child); 
        } 
      }
      
      node.getChildren().addAll(children);
    }
    
    return node;
  }
}