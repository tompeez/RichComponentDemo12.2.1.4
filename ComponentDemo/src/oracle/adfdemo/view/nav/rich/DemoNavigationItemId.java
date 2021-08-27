/** Copyright (c) 2008, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import java.util.Map;

import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.context.RequestContext;

public class DemoNavigationItemId extends DemoNavigationItemBase implements Serializable
{
  public DemoNavigationItemId()
  {
    super();
  }


  public void setId(String id)
  {
    _id = id;
  }

  public String getId()
  {
    return _id;
  }

  public void setMessageType(String _messageType)
  {
    this._messageType = _messageType;
  }

  public String getMessageType()
  {
    if (getId().equals("trainId4"))
      return "confirmation";
    
    return _messageType;
  }

  public void setMessageTypeVertical(String _messageTypeVertical)
  {
    this._messageTypeVertical = _messageTypeVertical;
  }

  public String getMessageTypeVertical()
  {
    if (getId().equals("trainIdv3"))
      return "error";
    else if (getId().equals("trainIdv4"))
      return "info";
    
    return _messageTypeVertical;
  }


  public void doActionListener(ActionEvent action)
  {
    if (getId().equals("trainId2"))
    {
      // step 2, navigate to subtrain
      setCurrentId("trainId2a");
    }
    else if (getId().equals("trainIdv2"))
    {
      // step 2, navigate to subtrain
      setCurrentId("trainIdv2a");
    }
    else if (getId().equals("trainId2c"))
    {
      // move back to parent step
      setCurrentId("trainId3");
    }
    else if (getId().equals("trainIdv2c"))
    {
      // move back to parent step
      setCurrentId("trainIdv3");
    }
    else
    {
      setCurrentId(getId());
    }
    
  }

  /**
   * This method is used by adf-richclient-test project
   * @param action
   */
  public void testDoActionListener(ActionEvent action)
  {
    setCurrentId(getId());
  }

  public void setIdKey(String key)
  {
    if (key != null)
      _idKey = key;

  }

  public void setCurrentId(
    String id)
  {
    RequestContext rc = RequestContext.getCurrentInstance();
    Map scope = rc.getPageFlowScope();
    scope.put(_idKey, id);
  }

  @SuppressWarnings("compatibility:4084217066668723686")
  private static final long serialVersionUID = 1L;

  public static final String DEFAULT_ID_KEY = "oracle.adfdemo.view.nav.rich.DemoNavigationItemRegion.id";
  private String _idKey = DEFAULT_ID_KEY;
  private String _id = null;
  private String _messageType = "none";
  private String _messageTypeVertical = "none";
}