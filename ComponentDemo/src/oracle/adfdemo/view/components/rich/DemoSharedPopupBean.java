package oracle.adfdemo.view.components.rich;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.event.PopupFetchEvent;

import org.apache.myfaces.trinidad.component.UIXEditableValue;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.SortableModel;


public class DemoSharedPopupBean implements Serializable
{

  public void handle(ActionEvent ae)
  {
    System.out.println("actionListener: text of component clicked = " + ae.getComponent().getAttributes().get("text"));
    System.out.println("actionListener: row index = "  + _myCollectionModel.getRowIndex());
  }
  
  public void handleFeedbackStamped(ValueChangeEvent vce)
  {
    Object feedback = vce.getNewValue();
    ((UIXEditableValue)vce.getComponent()).resetValue();
        
    if (feedback != null)
    {
      ((DemoEmp)_myCollectionModel.getRowData()).setPeerFeedback(feedback.toString());
    }
                                                                            
  }
  

  public void handleFeedback(ValueChangeEvent vce)
  {
    Object feedback = vce.getNewValue();
    ((UIXEditableValue)vce.getComponent()).resetValue();
    
    Map requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
    UIComponent launchComp = (UIComponent)requestMap.get("launcher");
    String currId = launchComp.getAttributes().get("empId").toString();
    setCurrId(currId);    
    
    if (feedback != null)
    {
      getCurrEmp().setPeerFeedback(feedback.toString());
    }
                                                                            
  }
  
  public void popupFetchListener2(PopupFetchEvent pfe)
  {
    System.out.println("popupFetchListener2: " + pfe);
  }
  
  public void popupFetchListener(PopupFetchEvent pfe)
  {
    System.out.println("popuFetchListener: " + pfe);
    _currProp = (DemoEmp)_myCollectionModel.getRowData();
  }  
  

  public DemoSharedPopupBean()
  {
    _initializeView();
  }

  private void _initializeView()
  {
    _myCollectionModel = _getMyCollectionModel();
    _myCollectionModel.setRowIndex(0);
    _currProp = (DemoEmp)_myCollectionModel.getRowData();
  }

  private CollectionModel _getMyCollectionModel()
  {     
     
    ArrayList<DemoEmp> propsList = new ArrayList<DemoEmp>();
    DemoEmp prop = new DemoEmp();
  
    prop.setName("Jane Jones");  
    prop.setId("4");
    prop.setGrade("Grade 1");
    prop.setLocation("New Jersey");
    prop.setCategory("Human Resources");
    propsList.add(prop);
  
    prop = new DemoEmp(); 
    prop.setId("34");
    prop.setName("Meghan Marks");
    prop.setGrade("Grade 2");
    prop.setLocation("California");
    prop.setCategory("Engineering");
    propsList.add(prop);
  
    prop = new DemoEmp(); 
    prop.setId("73");
    prop.setName("Sam Star");
    prop.setGrade("Grade 3");
    prop.setLocation("Missouri");
    prop.setCategory("Sales");
    propsList.add(prop);
    
    return new SortableModel(propsList);
    
  }

  public CollectionModel getMyCollectionModel()
  {
      return _myCollectionModel;
  }

  public List getMyList()
  {
      return (List)_myCollectionModel.getWrappedData();
  }
  
  public DemoEmp getCurrEmp()
  {
    return _currProp;
  }  
  
  public void setCurrEmp(DemoEmp currProp)
  {
    _currProp = currProp;
  }
    
  public void setCurrId(String id)
  {
    if (id != null)
    {
      CollectionModel model = getMyCollectionModel();
      
      for (int i = 0; i < model.getRowCount(); i++)
      {
        DemoEmp prop = (DemoEmp)model.getRowData(i);
        if (id.equals(prop.getId()))
        {
          this.setCurrEmp(prop);
          return;
        }
      }
    }
  }

  private static final long serialVersionUID = 1L;    

  private DemoEmp _currProp;
  CollectionModel _myCollectionModel;

}
