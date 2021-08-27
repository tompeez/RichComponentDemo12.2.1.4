/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
// Utilities required for turning pages to be offline in attachment mode.

var Offline = new Object();

Offline.takeOffline = function(event)
{
  var newLocation = document.location.href;

  // rip fragment off so we can shove it back onto the end after appending params
  var fragment      = "";
  var fragmentIndex = newLocation.lastIndexOf("#");
  
  if (fragmentIndex != -1)
  {
    fragment    = newLocation.substring(fragmentIndex);
    newLocation = newLocation.substring(0, fragmentIndex);
  }
  
  // now rip off the query parameters
  var queryParameters = "";
  var queryStart      = newLocation.lastIndexOf("?");
  
  if (queryStart != -1)
  {
    queryParameters = newLocation.substring(queryStart);
    newLocation     = newLocation.substring(0, queryStart);
  }            
          
  if (fragment)
  {
    // if we are using partial page navigation, convert the fragment back into a path
    if (true)
    {
      // strip off part between the leading '#' and trailing "@"
      var terminatorIndex = fragment.indexOf("%40");
      
      if (terminatorIndex != -1)
      {
        // convert the partial navigation part of the path
        var fragmentPath = fragment.substring(1, terminatorIndex);
        fragmentPath = decodeURIComponent(fragmentPath);
        newLocation  = AdfPage.PAGE._facesPath + fragmentPath;
        
        // copy over the rest of the fragment, if any
        var realFragmentStart = terminatorIndex + "%40".length;
        
        if (fragment.length > realFragmentStart)
        {
          fragment = "#" + fragment.substring(realFragmentStart);
        }
        else
        {
          fragment="";
        }
      }
    }
  }
  
  // append param separator
  // =-= bts we should handle the case where there is already an outputmode param
  queryParameters += ((queryParameters) ? "&" : "?");
  queryParameters += "org.apache.myfaces.trinidad.outputMode=attachment";
  
  newLocation += queryParameters;
  newLocation += fragment;
  
  window.open(newLocation);
  event.cancel();
}