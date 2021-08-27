package oracle.adfdemo.view.table.rich;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

import org.apache.myfaces.trinidad.component.UIXTree;
import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetTreeImpl;
import org.apache.myfaces.trinidad.model.TreeModel;


public class TreeIteratorTestData
{
  public TreeIteratorTestData()
  {
    _items1 = new ArrayList<IterItem>(3);
    _items1.add(_createItem("tree1"));
    _items1.add(_createItem("tree2"));
    _items1.add(_createItem("tree3"));

    _items2 = new ArrayList<IterItem>(3);
    _items2.add(_createItem("treeTable1"));
    _items2.add(_createItem("treeTable2"));
    _items2.add(_createItem("treeTable3"));

    _items3 = new ArrayList<IterItem>(3);
    _items3.add(_createItem("table1"));
    _items3.add(_createItem("table2"));
    _items3.add(_createItem("table3"));

  }

  public void onTreeSelect(SelectionEvent event)
  {
    UIXTree tree = (UIXTree) event.getSource();
    TreeModel compModel = (TreeModel) tree.getValue();
    RowKeySet selSet = tree.getSelectedRowKeys();
    RowKeySet added = event.getAddedSet();
    RowKeySet removed = event.getRemovedSet();
    System.out.println(added + " " + removed);
  }

  public void onTreeDisclose(RowDisclosureEvent event)
  {
    UIXTree tree = (UIXTree) event.getSource();
    TreeModel compModel = (TreeModel) tree.getValue();
    RowKeySet selSet = tree.getDisclosedRowKeys();
    RowKeySet added = event.getAddedSet();
    RowKeySet removed = event.getRemovedSet();
    System.out.println(added + " " + removed);
  }

  public List<IterItem> getItems1()
  {
    return _items1;
  }

  public List<IterItem> getItems2()
  {
    return _items2;
  }

  public List<IterItem> getItems3()
  {
    return _items3;
  }

  private IterItem _createItem(String name)
  {
    TreeModel model = _createTreeModel(name);
    IterItem item = new IterItem(name, model);
    return item;
  }

  private TreeModel _createTreeModel(String name)
  {
    Random rand = new Random(Math.round(Math.random()*10));
    List<TreeNode> model = new ArrayList<TreeNode>();
    int roots = rand.nextInt(4);
    for (int n = 0; n < roots; n++)
    {
      TreeNode node = new TreeNode(name + n);
      int count = rand.nextInt(2) + 1;
      List<TreeNode> kids = new ArrayList<TreeNode>(count);

      for (int i = 0; i < count; i++)
      {
        kids.add(new TreeNode(name + n + i));
      }
      node.setChildren(kids);
      model.add(node);
    }

    return new ChildPropertyTreeModel(model, "children");

  }


  public class TreeNode
  {
    public TreeNode(String name)
    {
      _name = name;
    }

    public String getIcon()
    {
      return _FOLDER_IMAGE_URI;
    }

    public void setName(String _name)
    {
      this._name = _name;
    }

    public String getName()
    {
      return _name;
    }

    private String _name;
    private static final String _FOLDER_IMAGE_URI = "/images/folder.gif";
    private List<TreeNode> _children = new ArrayList<TreeNode>();

    public void setChildren(List<TreeNode> _children)
    {
      this._children = _children;
    }

    public List<TreeNode> getChildren()
    {
      return _children;
    }
  }

  public class IterItem
  {
    public IterItem(String name, TreeModel model)
    {
      _name = name;
      _model = model;
      _selSet.setCollectionModel(_model);
      _discSet.setCollectionModel(_model);
    }

    public TreeModel getModel()
    {
      return _model;
    }

    public RowKeySet getSelectedRowKeys()
    {
      return _selSet;
    }

    public RowKeySet getDisclosedRowKeys()
    {
      return _discSet;
    }

    public void setName(String _name)
    {
      this._name = _name;
    }

    public String getName()
    {
      return _name;
    }
    
    private TreeModel _model;
    private RowKeySet _selSet = new RowKeySetTreeImpl();
    private RowKeySet _discSet = new RowKeySetTreeImpl();
    private String _name;

  }


  private List<IterItem> _items1;
  private List<IterItem> _items2;
  private List<IterItem> _items3;
}
