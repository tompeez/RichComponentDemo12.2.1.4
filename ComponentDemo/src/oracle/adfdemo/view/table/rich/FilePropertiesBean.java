/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import java.util.Map;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.component.UIXOutput;
import org.apache.myfaces.trinidad.context.RequestContext;

/**
 * Backing bean for file explorer properties dialog.
 */
public class FilePropertiesBean
{
  public void cancel()
  {
    RequestContext.getCurrentInstance().returnFromDialog(null, null);
  }
  
  public void update()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    UIViewRoot view = context.getViewRoot();
    Map viewAttrs = view.getAttributes();
    viewAttrs.put("fileProperties.updated", "Updated");
    
    // make sure new state is visible immediately after an update.
    refresh();
  }
  
  public void refresh()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    UIViewRoot view = context.getViewRoot();
    Map viewAttrs = view.getAttributes();
    Object updated = viewAttrs.get("fileProperties.updated");;
    _output.setValue(updated);
  }
  
  public void setOutput(
    UIXOutput output)
  {
    _output = output;
  }
  
  public UIXOutput getOutput()
  {
    return _output;
  }
  
  private UIXOutput _output;
}
