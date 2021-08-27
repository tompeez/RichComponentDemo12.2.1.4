/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
function _runGCTest()
{    
  // First force a gc
  var gcTime = _collectGarbage();

  // Try creating a bunch of objects and see how long it takes
  var startObjTime = new Date();

  for (var i = 0; i < 5000; i++)
    var newObject = new Object();

  var endObjTime = new Date();
  var objTime = endObjTime.getTime() - startObjTime.getTime();
  
  // Force another gc
  _collectGarbage();
  
  // Try creating a bunch of DOM nodes
  var startDomTime = new Date();
  
  for (var i = 0; i < 1000; i++)
    var domNode = document.createElement("span");
    
  var endDomTime = new Date();
  var domTime = endDomTime.getTime() - startDomTime.getTime();
  
  // Log the results
  var results = "GC Test: ";
  
  if (gcTime != undefined)
    results += ("gc=" + gcTime + "ms,");
   
  results += ("object creation="  + objTime + "ms,");
  results += ("dom creation=" + domTime + "ms");
  
  alert(results);                    
}

// Forces a garbage collection if possible.  Returns time spent
// collecting garbage, or undefined if garbage can't be collected.
function _collectGarbage()
{
  if (window.CollectGarbage)
  {
    var gcStart = new Date();
    window.CollectGarbage();
    var gcEnd = new Date();
    return (gcEnd.getTime() - gcStart.getTime());
  }
  
  return undefined;
}
