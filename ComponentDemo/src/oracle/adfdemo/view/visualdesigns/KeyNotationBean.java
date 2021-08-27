/*
** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved.
**
**345678901234567890123456789012345678901234567890123456789012345678901234567890
*/
package oracle.adfdemo.view.visualdesigns;

import java.util.HashMap;
import java.util.Map;

import org.apache.myfaces.trinidad.event.DisclosureEvent;


public class KeyNotationBean
{
  private Map<String, Boolean> _disclosedMap = new HashMap<String, Boolean>();

  public Map<String, Boolean> getDisclosedMap()
  {
    return _disclosedMap;
  }

  public void handleDisclose(DisclosureEvent disclosureEvent)
  {
    String key = (String)disclosureEvent.getComponent().getAttributes().get(
      "disclosedKey");
    _disclosedMap.put(key, Boolean.FALSE.equals(_disclosedMap.get(key)));
  }
}
