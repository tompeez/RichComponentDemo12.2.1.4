/** Copyright (c) 2008, 2018, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import java.text.DateFormat;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Locale;

import java.util.Map;
import java.util.UUID;

import javax.el.ELContext;
import javax.el.ELResolver;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;

import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.component.UIXTable;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;

import oracle.adfdemo.view.components.rich.DemoComponentSkin;

import org.apache.myfaces.trinidad.component.UIXCollection;
import org.apache.myfaces.trinidad.component.UIXComponent;
import org.apache.myfaces.trinidad.component.UIXTree;
import org.apache.myfaces.trinidad.component.UIXTreeTable;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.FocusEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetTreeImpl;
import org.apache.myfaces.trinidad.model.SortCriterion;
import org.apache.myfaces.trinidad.model.SortableModel;
import org.apache.myfaces.trinidad.model.TreeModel;

public class TreeTableTestData
{

  public TreeTableTestData()
  {
  }

  /**
   * unknownCount and estimatedCount are mutually exclusive if both are true, unknown
   * wins
   * @param unknownRowCount
   * @param estimatedCount
   */
  public TreeTableTestData(boolean unknownRowCount, boolean estimatedRowCount)
  {
    _unknownRowCount = unknownRowCount;
    _estimatedRowCount = estimatedRowCount;
  }
    
  public Object getFocusRowKey()
  {
    List frk = new ArrayList<String>();
    frk.add("9");
    return frk;
  }
  

  public void setFocusRowKey(ActionEvent event)
  {
    if (_component != null)
    {
      UIXTree tree = (UIXTree)_component;
      tree.setFocusRowKey(tree.getRowKey());
      RequestContext.getCurrentInstance().addPartialTarget(tree);
    }    
  }

  public void onFocus(FocusEvent event)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    
    Object newKey = event.getNewKey();
    Object oldKey = event.getOldKey();
    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "Info", 
              "Old Focus RowKey: " + (oldKey != null ? oldKey.toString() : "null") +
              " New Focus RowKey: " + (newKey != null ? newKey.toString() : "null"));
    context.addMessage(null, message);
  }
  
    
  public void setComponent(UIComponent component)
  {
    _component = component;
  }
  public UIComponent getComponent()
  {
    return _component;
  }
  
  public TreeModel getTreeModel()
  {
    if(_model != null)
      return _model;
    _model = createTreeModel(createTestData(_DEFAULT_FIRST_LEVEL_SIZE, false), _unknownRowCount, _estimatedRowCount);
    return _model;
  }
    
  public static TreeModel createTreeModel(List<TreeNode> root,
                                   final boolean unknownRowCount,
                                   final boolean estimatedRowCount)
  {
    return new ChildPropertyTreeModel(root, _CONTAINER_KEY)
    {
      @Override
      public boolean isContainer()
      {
        TreeNode currNode = (TreeNode) getRowData();
        return null != currNode.getChildren() &&
          currNode.getChildren().size() > 0;
      }

      @Override
      public int getRowCount()
      {
        if (unknownRowCount)
        {
          return -1;
        }
        else if (estimatedRowCount)
        {
          return _DEFAULT_FIRST_LEVEL_SIZE * 2;
        }
        else
        {
          return super.getRowCount();
        }
      }

      @Override
      protected CollectionModel createChildModel(Object childData)
      {

        CollectionModel childModel = new SortableModel(childData)
        {
          @Override
          public int getRowCount()
          {
            if (unknownRowCount)
            {
              return -1;
            }
            else if (estimatedRowCount)
            {
              return _DEFAULT_FIRST_LEVEL_SIZE * 2;
            }
            else
            {
              return super.getRowCount();
            }
          }
        };
        childModel.setRowIndex(-1);
        return childModel;
      }

      @Override
      public void setSortCriteria(List<SortCriterion> criteria)
      {
        super.setSortCriteria(criteria);
        if (null == getRowKey())
        {
          _rootCriteria = criteria;
        }
      }


      private List<SortCriterion> _rootCriteria;

    };
  }

  public static List<TreeNode> createTestData(int firstLevelSize,
    boolean isSingleRoot)
  {
    List<TreeNode> root = new ArrayList<TreeNode>();
    for(int i = 0; i < firstLevelSize; i++)
    {
      List<TreeNode> level1 = new ArrayList<TreeNode>();
      for(int j = 0; j < i; j++)
      {
        List<TreeNode> level2 = new ArrayList<TreeNode>();
        for(int k=0; k<j; k++)
        {
          TreeNode z = new TreeNode(null, _nodeVal(i,j,k));  
          level2.add(z);
        }
        TreeNode c = new TreeNode(level2, _nodeVal(i,j));
        level1.add(c);
      }
      TreeNode n = new TreeNode(level1, 
                                    _nodeVal(i));
      root.add(n);
    }
    
    if (isSingleRoot)
    {
      List<TreeNode> singleRoot = new ArrayList<TreeNode>();
      singleRoot.add(new TreeNode(root, _nodeVal(0)));      
      return singleRoot;
    }
    else
      return root;
  }

  public void PPRCell(ActionEvent event)
  {
    // clear out the displayRow first
    UIComponent parentComponent = event.getComponent().getParent();
    UIXCollection pprTable =  (UIXCollection)parentComponent.findComponent("tableCellPPR");
    // get the rowkey
    EditableValueHolder edit = (EditableValueHolder) parentComponent.findComponent("rowKeyValue");
    Object rowKey = edit.getValue();
    if(rowKey != null)
    {
      EditableValueHolder select = (EditableValueHolder) parentComponent.findComponent("columnName");
      Object selectValue = select.getValue();
      if(selectValue != null)
      {
        EditableValueHolder cellEdit = (EditableValueHolder) parentComponent.findComponent("cellValue");
        Object cellVal = cellEdit.getValue();
        if(cellVal != null)
        {
          Object oldRowKey = pprTable.getRowKey();
          try
          {
            if(pprTable instanceof UIXTree)
            {
              String[] keys = rowKey.toString().split(",");
              List<Integer> treeKeys = new ArrayList<Integer>(keys.length);
              for(String key: keys)
              {
                treeKeys.add(new Integer(key));
              }
              pprTable.setRowKey(treeKeys);
            }
            else
              pprTable.setRowKey(new Integer(rowKey.toString()));
            
            Object rowObject = pprTable.getRowData();
            FacesContext context = FacesContext.getCurrentInstance();
            ELResolver resolver = context.getApplication().getELResolver();
            ELContext elContext = context.getELContext();
            cellVal = _convertColumnaDataType(
                        resolver.getType(elContext, rowObject, selectValue.toString()), cellVal);
            resolver.setValue(elContext, rowObject, selectValue.toString(), cellVal);
            RequestContext.getCurrentInstance().addPartialTarget(
              pprTable.findComponent(selectValue.toString()));
          }
          finally
          {
            pprTable.setRowKey(oldRowKey);
          }
        }
      }
    }
  }
  

  public void showFirstRow(ActionEvent event)
  {
    _setDisplayRow("first", event.getComponent().getParent().findComponent("displayRowTable"));
  }
  
  public void showLastRow(ActionEvent event)
  {
    _setDisplayRow("last", event.getComponent().getParent().findComponent("displayRowTable"));
  }
  
  public void showSelectedRow(ActionEvent event)
  {
    _setDisplayRow("selected", event.getComponent().getParent().findComponent("displayRowTable"));
  }
  
  private void _setDisplayRow(String displayRow, UIComponent displayRowTable)
  {
    if(displayRowTable instanceof UIXTable)
    {
      displayRowTable.getAttributes().put("displayRowKey", null);
      displayRowTable.getAttributes().put("displayRow", displayRow);
    }
    else if(displayRowTable instanceof RichTreeTable)
    {
      ((RichTreeTable)displayRowTable).setDisplayRowKey(null);
      ((RichTreeTable)displayRowTable).setDisplayRow(displayRow);
    }
    else if(displayRowTable instanceof RichTree)
    {
      ((RichTree)displayRowTable).setDisplayRowKey(null);
      ((RichTree)displayRowTable).setDisplayRow(displayRow);
    }
  }
    
  public void showRowKey(ActionEvent event)
  {
    UIComponent parentComponent = event.getComponent().getParent();
    UIComponent displayRowTable =  parentComponent.findComponent("displayRowTable");
    // clear out the displayRow first
    _setDisplayRow(null, displayRowTable);
    EditableValueHolder edit = (EditableValueHolder)
                  parentComponent.findComponent("rowKeyValue");
    Object rowKey = edit.getValue();
    if(rowKey != null)
    {
      if(displayRowTable instanceof UIXTable)
      {
        displayRowTable.getAttributes().put("displayRowKey",new Integer(rowKey.toString()));
      }
      else
      {
        String[] keys = rowKey.toString().split(",");
        List<Integer> treeKeys = new ArrayList<Integer>(keys.length);
        for(String key: keys)
        {
          treeKeys.add(new Integer(key));
        }
        if(displayRowTable instanceof RichTreeTable)
          ((RichTreeTable)displayRowTable).setDisplayRowKey(treeKeys);
        else if(displayRowTable instanceof RichTree)
          ((RichTree)displayRowTable).setDisplayRowKey(treeKeys);
      }
    }
  }
    
  private Object _convertColumnaDataType(Class clazz, Object value)
  {
    if(clazz == Date.class)
    {
      try
      {
       value = DateFormat.getDateInstance(
                    DateFormat.SHORT, Locale.US).parse(value.toString());
      }
      catch(ParseException e)
      {
        System.out.println("error parsing date string");
      }
    }
    else if(clazz == Integer.class || clazz == int.class)
    {
      value = new Integer(value.toString());
    }
    else if(clazz == Boolean.class || clazz == boolean.class)
    {
      value = new Boolean(value.toString());
    }
    return value;
  }

  public RowKeySet getDisclosedRowKeys()
  {
    if (disclosedRowKeys == null)
    {
      // Create the PathSet that we'll use to store the initial
      // expansion state for the tree
      RowKeySet treeState = new RowKeySetTreeImpl();

      // RowKeySet requires access to the TreeModel for currency.
      TreeModel model = getTreeModel();
      treeState.setCollectionModel(model);

      // Make the model point at the root node
      int oldIndex = model.getRowIndex();

      model.setRowKey(null);
      for(int i = 1; i<=19; ++i)
      {
        model.setRowIndex(i);
        treeState.setContained(true);
      }
      
      model.setRowIndex(oldIndex);

      disclosedRowKeys = treeState;
    }

    return disclosedRowKeys;
  }
  
  private static String _nodeVal(Integer... args)
  {
    StringBuilder s = new StringBuilder();
    for(Integer i : args)
      s.append(i);
    return s.toString();
  }

  public static class TreeNode
  {
    public TreeNode() {}
    public TreeNode(List<TreeNode> children, String value)
    {
      _children = children;
      _value = value;
      _name = "Name" + _value;
    }
    
    public void rowKeyTest(javax.faces.event.ActionEvent event)
    {
      FacesContext context = FacesContext.getCurrentInstance();
      if (_tree != null)
      {
        TreeNode n = (TreeNode)_tree.getRowData();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "Info", "Clicked on " + _tree.getRowKey() +
                                                " src: " + n.getSrc() +
                                                " target: " + n.getTarget());
        context.addMessage(null, message);
      }
    }
    
    public void setTree(UIComponent tree)
    {
      _tree = (UIXTree) tree;
    }
    public boolean getDisabled()
    {
      return _timestamp.length() > 5;
    }
    
    public String getSelectOneValue()
    {
      return (_selectOneVal != null)?_selectOneVal:"Mouse";
    }

    public void setSelectOneValue(String value)
    {
      _selectOneVal = value;
    }

    public String getSelectOneValue2()
    {
      return (_selectOneVal != null)?_selectOneVal:"Mouse";
    }

    public void setSelectOneValue2(String value)
    {
      _selectOneVal = value;
    }
    
    public String getSelectOneChoiceValue()
    {
      return (_selectOneChoiceVal != null)?_selectOneChoiceVal:"Mouse";
    }

    public void setSelectOneChoiceValue(String value)
    {
      _selectOneChoiceVal = value;
    }
    
    public void setChecked(boolean checked)
    {
      _checked = checked;
    }
    public boolean getChecked()
    {
      return _checked;
    }
    
    public boolean getIconVisible()
    {
      return Math.random() > 0.5;
    }
    
        
    public String resetFocusRowKey()
    {
      FacesContext context = FacesContext.getCurrentInstance();
      UIViewRoot viewRoot = context.getViewRoot();
      UIXTree tree = (UIXTree)viewRoot.findComponent("dmoTpl:tree");
      
      Object oldKey = tree.getRowKey();
      tree.setRowKey(null);
      tree.setRowIndex(0);
      Object root = tree.getRowKey();
      tree.setRowKey(oldKey);
      tree.setFocusRowKey(root);
      RequestContext.getCurrentInstance().addPartialTarget(tree);
      
      return null;
    }
    
    public void selectOneValueChanged(ValueChangeEvent event)
    {
      FacesContext context = FacesContext.getCurrentInstance();
      FacesMessage message = new FacesMessage("New Value: " + event.getNewValue());
      context.addMessage(null, message);
    }
    
    
    public List<TreeNode> getChildren()
    {
      return _children;
    }
    public void setChildren(List<TreeNode> children)
    {
      _children = children;
    }
    
    
    public String getValue()
    {
      return _value;
    }
    public void setValue(String value)
    {
      _value = value;
    }
    public String getTimestamp()
    {
      return _timestamp;
    }
    public void setTimestamp(String t)
    {
      _timestamp = t;
    }
    public String getFunctionalIcon()
    {
      return _FUNCTIONAL_ICON_URI;    
    }
    
    public void setFunctionalIcon(String icon)
    {
    }
    
    public String getStatusIcon()
    {
      return _checked? _CHECKED_STATUS_ICON_URI: _UNCHECKED_STATUS_ICON_URI;    
    }
    
    public void setStatusIcon()
    {
          
    }
    
    boolean iconVisible()
    {
      return Math.random() * 100.0 > 50.0;
    }
    public String getIcon()
    {
      if (getDemoSkin().isAltaBased())
        return _ALTA_FOLDER_IMAGE_URI;
      else
        return _FOLDER_IMAGE_URI;
    }
    public String getFileIcon()
    {
      return _FILE_IMAGE_URI;
    }
    public void setIcon(String icon)
    {
      
    }
    public void setTarget(String t)
    {
      _target = t;
    }
    public String getTarget()
    {
      return _target;
    }
    public String getSrc()
    {
      return "src:" + _value;
    }
    public void setSize(int param)
    {
      this.size = param;
    }

    public int getSize()
    {
      return size;
    }

    public String getRequiredField()
    {
      return _requiredField;
    }

    public void setRequiredField(String val)
    {
      _requiredField = val;
    }
    
    public Date getInputDate()
    {
      Date inputDate = null;
      try
      {
        inputDate = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse(_date);
      }
      catch(ParseException e)
      {
        System.out.println("error parsing date string in table backing bean");
      }
      return inputDate;
    }
    
    public void setInputDate(Date date)
    {
      DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
      
      _date = format.format(date);
    }    
    
    public void setName(String _name)
    {
      this._name = _name;
    }

    public String getName()
    {
      return _name;
    }
    
    public String getKey()
    {
      return _key;
    }
    
    public DemoComponentSkin getDemoSkin()
    {
      if (_demoComponentSkin == null)
      {
        FacesContext facesCtx = FacesContext.getCurrentInstance(); 
        ExternalContext ectx = facesCtx.getExternalContext(); 
        Map<String, Object> sessionParamsVar = ectx.getSessionMap();
        _demoComponentSkin = (DemoComponentSkin)sessionParamsVar.get("demoSkin");
      }
      
      return _demoComponentSkin;
    }    
    
    private DemoComponentSkin _demoComponentSkin = null;
    private List<TreeNode> _children;
    private String _value;
    private String _timestamp = Long.toHexString(System.nanoTime());
    private String _target;
    private boolean _checked;
    private String _selectOneVal;
    private String _selectOneChoiceVal;
    private int size = (int)(Math.random() * 100) ;
    private String _requiredField = String.valueOf(size);
    private String _date = "07/12/2004";   
    private String _name;
    private String _key = UUID.randomUUID().toString(); 
    private UIXTree _tree;
  }
  
  protected static final String _CONTAINER_KEY = "children";

  private static final String _FOLDER_IMAGE_URI = "/images/folder.gif";
  private static final String _FILE_IMAGE_URI = "/images/file.gif";
  private static final String _UNCHECKED_STATUS_ICON_URI = "/images/field_group_minus_dis.png";
  private static final String _CHECKED_STATUS_ICON_URI = "/images/confirmation.png";
  private static final String _FUNCTIONAL_ICON_URI = "/images/find_ena.png";
  private static int _DEFAULT_FIRST_LEVEL_SIZE = 20;

  /* for alta */
  private static final String _ALTA_FOLDER_IMAGE_URI = "/images/alta_v1/folder_ena.png";
  
  protected boolean _unknownRowCount;
  protected boolean _estimatedRowCount;

  private UIComponent _component;
  private TreeModel _model;
  private RowKeySet disclosedRowKeys;
  private static final ADFLogger _LOG =
    ADFLogger.createADFLogger(TreeTableTestData.class);  
}
