package oracle.adfdemo.view.table.rich;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.myfaces.trinidad.model.TreeModel;


@ManagedBean(name="treeAutoHeightData")
@SessionScoped
public class TreeAutoHeightData implements Serializable
{

  public TreeAutoHeightData()
  {
    super();
  }
  
  public TreeModel getTreeModel()
  {
    if(_model == null)
      _model = TreeTableTestData.createTreeModel(TreeTableTestData.createTestData(10, true), false, false);    
    return _model;
  }

  public int getAutoHeightRows()
  {
    return _autoHeightRows;
  }

  transient private TreeModel _model;
  private int _autoHeightRows = 20;
  private static final long serialVersionUID = 1L;

}
