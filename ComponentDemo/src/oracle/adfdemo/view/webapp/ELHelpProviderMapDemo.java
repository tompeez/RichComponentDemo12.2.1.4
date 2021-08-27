/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.webapp;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides a Map&lt;String, String> of Help strings for demo purposes. 
 * This is used to demonstrate the ELHelpProvider. This will return a Map with
 * keys/values to be used in help. You can EL bind to anything as long as it 
 * returns a Map. EL is a good way to provide logic to decide which Map you
 * might want to return. Your Map containing help
 * could be based on locale, on a user's role, or whatever.
 * XLIF files are converted to Maps in Jdeveloper, so the ELHelpProvider 
 * can be used if your help is in XLIF files.
 */
public class ELHelpProviderMapDemo
{
  public ELHelpProviderMapDemo()
  {
  }
  
  /* To use the ELHelpProvider, the EL expression must point to a Map, otherwise 
   * you'll get a coerceToType error. */
  public Map<String, String> getHelpMap()
  {
    return _HELP_MAP;
  }
  
    static private final Map<String, String> _HELP_MAP = new HashMap<String, String>();
    static 
    {
      _HELP_MAP.put("MAPHELP_CREDIT_CARD_DEFINITION", "Map value for credit card definition");
      _HELP_MAP.put("MAPHELP_CREDIT_CARD_INSTRUCTIONS", "Map value for credit card instructions");
      _HELP_MAP.put("MAPHELP_SHOPPING_DEFINITION", "Map value for shopping definition");
      _HELP_MAP.put("MAPHELP_SHOPPING_INSTRUCTIONS", "Map value for shopping instructions");
    }  

}
