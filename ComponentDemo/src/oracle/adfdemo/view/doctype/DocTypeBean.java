/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.doctype;

import java.io.Serializable;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adfdemo.view.components.rich.DemoComponentSkin;

import org.apache.myfaces.trinidad.context.RequestContext;

public class DocTypeBean implements Serializable
{
  public boolean isStrictDTD()
  {
    Boolean isStrict = (Boolean)_getApplicationScopedConcurrentMap().get(_DOCTYPE_STRICT_PROP);
    
    // strict defaults on
    return !Boolean.FALSE.equals(isStrict);
  }

  public void setStrictDTD(boolean useStrict)
  {
    _getApplicationScopedConcurrentMap().put(_DOCTYPE_STRICT_PROP, Boolean.valueOf(useStrict));
  }

  public void toggleStrictDTDMenuAction(ActionEvent ae)
  {
    setStrictDTD(!isStrictDTD());
    DemoComponentSkin.reloadThePage();
  }
  
  private Map<String, Object> _getApplicationScopedConcurrentMap()
  {
    return RequestContext.getCurrentInstance().getApplicationScopedConcurrentMap();
  }

  private static final String _DOCTYPE_STRICT_PROP = 
                                                    "oracle.adfinternal.view.faces.USE_STRICT_DTD";
  
  private static final long serialVersionUID = 1L;
}
