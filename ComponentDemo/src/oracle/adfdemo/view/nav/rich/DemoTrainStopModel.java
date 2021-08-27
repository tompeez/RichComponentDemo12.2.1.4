/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import java.util.List;

import javax.el.ELContext;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.model.ProcessMenuModel;


/**
 * Represents the model for a train stop. Usually MenuModel implementation will only have to 
 * override the getFocusRowKey() and contain a collection of nodes, each node represented by an 
 * object (that extends TrainStopModel or implement a class with similar methods). This class 
 * contains all the methods expected from a model that represents a train stop but does not 
 * necessarily extend the TrainStopModel.
 */
public class DemoTrainStopModel implements Serializable
{
  public DemoTrainStopModel()
  {
    // defaults
    _messageType = "none";
    _shortDesc = null;
    _showRequired = false;
    _model = null;   
  }
  
  /**
   * Returns the logical outcome associated with clicking on a train stop (which
   *  may be null). The outcome will be used to navigate the user to the next 
   *  stop within the train.
   */  
  public String action()
  {
    return _outcome;
  }

  public List getChildren()
  {
    return _children;
  }

  public String getMessageType()
  {
    return _messageType;
  }

  private ProcessMenuModel _getModel()
  {
    
    // Get the train managed bean
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    if (_model == null && _modelName != null)
    {
      // _model = (ProcessMenuModel) elContext.getELResolver().getValue(elContext, null, _modelName);
      
      String modelExpr = "#{" + _modelName + "}";
      ValueExpression ve = facesContext.getApplication().
        getExpressionFactory().createValueExpression(elContext,
                                                     modelExpr,
                                                     ProcessMenuModel.class);
      
      _model = (ProcessMenuModel) ve.getValue(elContext);
    }
      
    return _model;
      
  }
  /**
   * Return a short description for the stop. This text is commonly used by 
   * to display tooltip help text.
   */
  public String getShortDescription ()
  {
    return _shortDesc;
  }
  /**
   * Return the label of the stop alongwith with the accessKey character to use.
   * For example, if the stop label is "Step One" and the accessKey is 'O', the 
   * return value like "Step &One" will set the text to "Step One" and the 
   * access key to 'O'. If no accessKey is required, the label alone can be 
   * returned.
   */ 
  public String getTextAndAccessKey()
  {
    return _label;
  }
  /**
   * Return the view id associated with this stop. 
   */
  public String getViewId()
  {
    return _viewId;
  }
  /**
   * Return a flag indicating whether the stop needs to be disabled. A true 
   * value renders the stop read-only and not clickable. 
   */  
  public boolean isDisabled()
  {
    if (_model == null) 
    {
      _getModel();
    }
    return _model.isReadOnly();
  }
  /**
   * Return a flag to inidicate whether or not data validations need to occur. 
   * When true, the default ActionListener provided by the JavaServer Faces 
   * implementation will be executed immediately (that is, during Apply Request 
   * Values phase of the request processing lifecycle), rather than waiting 
   * until the Invoke Application phase. Default value is false.
   */  
  public boolean isImmediate()
  {
    if (_model == null) 
    {
      _getModel();
    }
    return _model.isImmediate();
  }
  /**
   * Return a true if the stop should show a required indicator (usually used 
   * when there are one more required fields). Default value is false.
   */  
  public boolean isShowRequired()
  {
    return _showRequired;
  }

  /**
   * Return a flag to indicate whether the stop has been visited before. 
   */
  public boolean isVisited()
  {
    if (_model == null)
    {
      _getModel();
    }
    return _model.isVisited();
  }

  // Setters
  public void setChildren(List children)
  {
    _children = children;
  }

  public void setMessageType(String messageType)
  {
    _messageType = messageType;
  }

  /**
   * Usually the MenuModel implementation will only have to override the getFocusRowKey(), but for 
   * the DemoTrainModel (that extends ProcessMenuModel) has the logic to determine whether a stop
   * is visited, disabled etc. We delegate calls to determine the visited, disabled and immediate 
   * atribute to the DemoTrainModel. 
   * We get the managed bean name and retrieve the managed bean.
   * 
   * @param name name of the menuModel managed bean defined in the faces-config.
   */
  public void setModel (String name)
  {
    _modelName = name;
  }

  /**
   * The outcome that will navigate the user to the view represented by this stop.
   * @param outcome
   */
  public void setOutcome(String outcome)
  {
    _outcome = outcome;
  }
  public void setShortDescription(String shortDesc)
  {
    _shortDesc = shortDesc;
  }
  public void setShowRequired(boolean required)
  {
    _showRequired = required; 
  }
  /*
   * Usually the text or the textAndAccessKey to render this stop.
   */
  public void setLabel(String label)
  {
    _label = label;
  }
  public void setViewId(String viewId)
  {
    _viewId = viewId;
  }

  
  private List              _children;
  private String            _label;
  
  private String            _messageType;
  private ProcessMenuModel  _model;
  private String            _modelName;
  private String            _shortDesc;
  private boolean           _showRequired;
  
  private String            _outcome;
  private String            _viewId ;
  
}
