/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.validator.ValidatorException;

import javax.servlet.http.HttpServletResponse;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.model.SelectItemSeparator;
import oracle.adf.view.rich.render.ClientEvent;

import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.LaunchEvent;
import org.apache.myfaces.trinidad.event.PollEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.ExternalContextUtils;
import org.apache.myfaces.trinidad.util.Service;

/**
 * Bean class used by feature/richPostback.jspx..
 */
public class TestPostbackBean
{
  public TestPostbackBean()
  {
    _value = _MESSAGE;
  }

  public void testCustomEvent(ClientEvent event)
  {
    System.out.println("Received custom event, " + event);
    doIncrementCounter();
  }


  /**
   * Test method for read-only boolean values.
   */
  public boolean isAlwaysTrue()
  {
    return true;
  }

  /**
   * An action method that always throws an exception.
   */
  public String doThrow()
  {
    throw new IllegalStateException("This action method never works!");
  }

  public void testActionListener(ActionEvent event)
  {
    System.out.println("Action event on " + event.getSource());
  }

  public void testPollListener(PollEvent event)
  {
    System.out.println("Poll event on " + event.getSource());
  }

  public void clearSession()
  {
    // This nukes the session without generating a response that adds back
    // the session
    FacesContext context = FacesContext.getCurrentInstance();
    context.getExternalContext().getSessionMap().clear();
    context.responseComplete();
  }

  public void alwaysError(ValueChangeEvent event)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage(event.getComponent().getClientId(context),
                       new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "<html>That <b>ain't</b> good!</html>",
                                        null));
  }
  
  /**
   * Tests a custom event that contains the UIComponents to setRendered(false) on
   * @param event
   */
  public void testCustomHideComponentEvent(ClientEvent event)
  {
    List<UIComponent> componentsToHide = (List<UIComponent>)event.getParameters().get("hide");
    
    // hide all of the components in the lsit
    if (componentsToHide != null)
    {
      RequestContext trinContext = RequestContext.getCurrentInstance();
      
      for (UIComponent hideComponent : componentsToHide)
      {
        hideComponent.setRendered(false);
        
        // parent as ppr target so that area including child is re-rendered
        trinContext.addPartialTarget(hideComponent.getParent());
      }
    }
  }
    

  /**
   * Increment the counter or reset it if it is too big.
   */
  public void doResetCounter()
  {
    _counter = 0;
  }

  /**
   * Increment the counter or reset it if it is too big.
   */
  public void doIncrementCounter()
  {
    _counter++;
  }

  /**
   * Returns the counter value that is manipulated by our actions.
   */
  public int getCounter()
  {
    return _counter;
  }

  /**
   * Returns the value that is manipulated by our actions.
   */
  public String getValue()
  {
    return _value;
  }

  /**
   * Flip the value.
   */
  public void doFlip()
  {
    StringBuffer buffer = new StringBuffer(_value);
    buffer.reverse();
    _value = buffer.toString();

   /** FacesContext context = FacesContext.getCurrentInstance();
    ExtendedRenderKitService service =
      Service.getRenderKitService(context, ExtendedRenderKitService.class);
    service.addScript(context, "alert('flip!');");*/
  }

  /**
   * Grow the value.
   */
  public void doGrow()
  {
    _value = (_value + _MESSAGE);
  }

  /**
   * Shrink the value.
   */
  public void doShrink()
  {
    String value = _value;
    int length = value.length();

    // Only shrink the value if value is longer than
    // default message length.
    if (length > _MESSAGE.length())
      _value = value.substring(0, length - _MESSAGE.length());
  }

  public void forceFullSubmitListener(ActionEvent ae)
  {
    
  }
  
  public String doDownload()
    throws IOException
  {
    // NOTE (10/14/09): When pprNavigation is set to "onWithForcePPR", components in our demos that
    // have action="#{testPostback.doDownload}" fail. This will be fixed with ER bug 8993936. See 
    // also bug 8812125.
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext ec = context.getExternalContext();
    
    if (ExternalContextUtils.isPortlet(ec)) 
    {
      // redirect to another servlet in the portlet case
      StringBuffer url = new StringBuffer();

      url.append(ec.getRequestContextPath());
      url.append("/portletdownload");
      url.append("?javax.portlet.faces.InProtocolResourceLink=true");
      

      // encode the URL
      String encodedUrl = ec.encodeResourceURL(url.toString());
       // redirect
      ec.redirect(encodedUrl);
    }
    else 
    {
      boolean isGecko = true;
      Map<String, String> headers = context.getExternalContext().getRequestHeaderMap();
      String agentName = headers.get("User-Agent").toLowerCase();
      if (agentName.contains("msie") || agentName.contains("applewebkit") || agentName.contains("safari"))
        isGecko = false;
      // boolean isIE = CoreRenderer.isIE(RenderingContext.getCurrentInstance());
      
      HttpServletResponse response = (HttpServletResponse)
        ec.getResponse();
      response.setContentType("text/plain");
      response.setHeader("Content-Disposition", "attachment; filename=\"test.txt\"");
  
      Writer out = response.getWriter();
      out.write("Test successful");
      out.close();
    }
    context.responseComplete();
    return null;
  }

  public void setInputField(RichInputText inputField)
  {
    _inputField = inputField;
  }

  public RichInputText getInputField()
  {
    return _inputField;
  }

  public void addMessageToInputField(ActionEvent event)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage(getInputField().getClientId(context),
                       new FacesMessage("Test summary", "Test detail"));
  }


  public void sendHelloFile(FacesContext context,
                            OutputStream outputStream) throws IOException
  {
    Writer out = new OutputStreamWriter(outputStream, "UTF-8");
    out.write("Hi there!");
    out.close();
  }

  public void errorHelloFile(FacesContext context,
                            OutputStream outputStream) throws IOException
  {
    throw new IOException("Error occurred");
  }

  public void sendLargeFile(FacesContext context, OutputStream outputStream) throws IOException
  {
    int bytes = 200 * 1024; // 200k file
    for (int i = 0; i < bytes; i++)
    {
      outputStream.write('a'+(i % 128));
    }
  }
  
  public void sendTooLargeFile(FacesContext context, OutputStream outputStream) throws IOException
  {
    // fill up the buffer until we trigger an IOException
    while (true)
    {
      outputStream.write('a');
    }
  }
  
  public void doSessionClear()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    context.getExternalContext().getSessionMap().clear();
    context.responseComplete();    
  }
  
  // A dumb Map that merely indicates when properties are retrieved during PPR
  public Map<Object, Object> getPprLogger()
  {
    return new HashMap<Object, Object>()
    {
      @Override
      public Object get(Object key)
      {
        RenderingContext rc = RenderingContext.getCurrentInstance();
        if ((rc != null) && (rc.getPartialPageContext() != null))
        {
          _LOG.warning("PPR retrieval of {0}", key);
        }

        return null;
      }
    };
  }



  private String _value;
  private int _counter = 0;
  private RichInputText _inputField;

  private static final String _MESSAGE = "Hello, world!";
  private static final ADFLogger _LOG = ADFLogger.createADFLogger(
                                           TestPostbackBean.class);



  //***************************************************
  // formPpr.jspx: Hiding conent
  //***************************************************
  
  public void setCopies2(int _copies)
  {
    this._copies2 = _copies;
  }

  public int getCopies2()
  {
    return _copies2;
  }

  public boolean getCollateRendered()
  {
    return _copies2 > 1;
  }

  private int _copies2 = 1;
  
  //***************************************************
  // formPpr.jspx: Enable/Disable
  //***************************************************
  
  public void setCopies(int _copies)
  {
    this._copies = _copies;
  }

  public int getCopies()
  {
    return _copies;
  }

  public boolean getCollateDisabled()
  {
    return _copies < 2;
  }

  private int _copies = 1;  

  
  //***************************************************
  // formPpr.jspx: Switcher
  //***************************************************
  

  public void setMediaType(String mediaType)
  {
    _mediaType = mediaType;
  }

  public String getMediaType()
  {
    return _mediaType;
  }  
  
  private String _mediaType = "book";  
  
  //***************************************************
  // formPpr.jspx: data switch
  //***************************************************
  

  public void setState(String state)
  {
    _state = state;
  }

  public String getState()
  {
    return _state;
  }  
  
  public SelectItem[] getCityList()
  {
    String state = getState();
    return _cityMap.get(state);
  }  
  
  private String _state = "CA";
  
  private static Map<String, SelectItem[]> _cityMap = new HashMap<String, SelectItem[]>();

  static{
    SelectItem[] calCities = new SelectItem[4];
    SelectItem[] nyCities = new SelectItem[2];

    calCities[0] = new SelectItem("la", "Los Angeles");
    calCities[1] = new SelectItem("sd", "San Diego");
    calCities[2] = new SelectItem("sf", "San Francisco");
    calCities[3] = new SelectItem("sj", "San Jose");
    
    nyCities[0] = new SelectItem("bu", "Buffalo");
    nyCities[1] = new SelectItem("ny", "New York City");
    
    _cityMap.put("CA", calCities);
    _cityMap.put("NY", nyCities);
  }

  public void forceFullSubmitListener(LaunchEvent launchEvent)
  {
    // Add event code here...
  }
  
  /**
   * This is for testing setPropertyListener and actionListener ordering. We expect that the setPropertyListener
   * that is a child of a button gets called before the actionListener that is an attribute on the button.
   * The setPropertyListener sets the "name" on the session, and the actionListener calls this method to read it.
   * If the ordering is incorrect, you'll see Hello null the first time the button is pressed.
   * @param actionEvent
   */
  public void sayHello(ActionEvent actionEvent)
  {
    // handler that gets called when you press the button.
    _helloName = getHelloNameSession();
    System.out.println("Hello "+ _helloName);
  }
  
  public String getHelloName()
  {
    return _helloName;
  }
  
  public String getHelloNameSession()
  {
    FacesContext facesCtx = FacesContext.getCurrentInstance(); 
    ExternalContext ectx = facesCtx.getExternalContext(); 
    Map<String, Object> sessionParamsVar = ectx.getSessionMap();
    return (String)sessionParamsVar.get("name");
  }
  
  public void clearHello(ActionEvent actionEvent)
  {
    FacesContext facesCtx = FacesContext.getCurrentInstance(); 
    ExternalContext ectx = facesCtx.getExternalContext(); 
    Map<String, Object> sessionParamsVar = ectx.getSessionMap();
    sessionParamsVar.put("name", "");
    _helloName = getHelloNameSession();
  }
  
  private String _helloName = "";
  

}
