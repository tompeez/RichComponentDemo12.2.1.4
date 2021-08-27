/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.render.ClientEvent;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;


public class DemoRepeaterBean
{

  public DemoRepeaterBean()
  {
    List repeaterRows = new ArrayList();
    repeaterRows.add(_getDefaultRepeaterRow());
    _listCollectionModel = new ListCollectionModel(repeaterRows);
  }

  public CollectionModel getCollectionModel()
  {
    return _listCollectionModel;
  }

  public List getList()
  {
    return (List) _listCollectionModel.getWrappedData();
  }

  public String getAddRowIconSourceLocation()
  {
    if (isAddAllowed())
    {
      return "/images/field_group_plus_ena.png"; //enabled button
    }
    else
    {
      return "/images/field_group_plus_dis.png"; //disabled button
    }
  }

  public String getRemoveRowIconSourceLocation()
  {
    if (isRemoveAllowed())
    {
      return "/images/field_group_minus_ena.png"; //enabled button
    }
    else
    {
      return "/images/field_group_minus_dis.png"; //disabled button
    }
  }

  public boolean isAddAllowed()
  {

    return _listCollectionModel.getRowCount() < _MAX_ALLOWED_ROWS;
  }

  public boolean isRemoveAllowed()
  {
    return _listCollectionModel.getRowCount() > _MIN_ALLOWED_ROWS;
  }

  public void resetData(ActionEvent actionEvent)
  {
    List repeaterRows = new ArrayList();
    repeaterRows.add(_getDefaultRepeaterRow());
    _listCollectionModel = new ListCollectionModel(repeaterRows);

    FacesContext context = FacesContext.getCurrentInstance();
    UIViewRoot viewRoot = context.getViewRoot();
    UIComponent queryLabelAndMessage = viewRoot.findComponent("pt:QueryLabelAndMessage");
    RequestContext requestContext = RequestContext.getCurrentInstance();
    requestContext.addPartialTarget(queryLabelAndMessage);
  }

  public void addRowEvent(ClientEvent event)
  {
    Double rowIndexDouble = (Double) event.getParameters().get("rowIndex");
    int rowIndex = rowIndexDouble.intValue();
    _listCollectionModel.add(rowIndex+1, _getDefaultRepeaterRow());
  }

  public void removeRowEvent(ClientEvent event)
  {
    Double rowIndexDouble = (Double) event.getParameters().get("rowIndex");
    int rowIndex = rowIndexDouble.intValue();
    _listCollectionModel.remove(rowIndex);
  }

  private DemoRepeaterQueryRowBean _getDefaultRepeaterRow()
  {
    return new DemoRepeaterQueryRowBean("From");
  }

  private ListCollectionModel _listCollectionModel;

  private final static int _MAX_ALLOWED_ROWS = 5;
  private final static int _MIN_ALLOWED_ROWS = 1;
}
