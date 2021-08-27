/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
var TestObjectPerf = new Object();

TestObjectPerf._create = function()
{
  // Force assertions to be disabled
  AdfAssert.DEBUG = false;
  
  var countInput = document.getElementById("countInput");
  var count = parseInt(countInput.value);
  if (isNaN(count))
  {
    alert("Count must be a number");
    return;
  }

  var typeSelect = document.getElementById("typeSelect");
  var type = typeSelect.value;

  var createFunc = null;

  // The "ObjectFast" type avoids the function call overhead, which can
  // be significant in IE
  if (type == "ObjectFast")
  {
    TestObjectPerf._createObjectsFast(count);
    return;
  }
   
  if (type == "Object")
    createFunc = TestObjectPerf._createPOJSO;
  else if (type == "AdfObject")
    createFunc = TestObjectPerf._createRichObject;
  else if (type == "AdfUIComponent")
    createFunc = TestObjectPerf._createUIComponent;
  else if (type == "AdfUICommand")
    createFunc = TestObjectPerf._createUICommand;
  else if (type == "AdfRichCommandButton")
    createFunc = TestObjectPerf._createRichCommandButton;
  else if (type == "AdfUIPeer")
    createFunc = TestObjectPerf._createUIPeer;
  else if (type == "AdfRichUIPeer")
    createFunc = TestObjectPerf._createDhtmlUIPeer;
  else if (type == "AdfDhtmlCommandButtonPeer")
    createFunc = TestObjectPerf._createDhtmlCommandButtonPeer;    
  else if (type == "PseudoRichObject")
    createFunc = TestObjectPerf._createPseudoRichObject;  
  else if (type == "PseudoUIComponent")
    createFunc = TestObjectPerf._createPseudoUIComponent;
  else if (type == "PseudoUICommand")
    createFunc = TestObjectPerf._createPseudoUICommand;
  else if (type == "PseudoRichCommandButton")
    createFunc = TestObjectPerf._createPseudoRichCommandButton;

  TestObjectPerf._createObjects(count, createFunc);
}

TestObjectPerf._createObjects = function(count, createFunc)
{
  var objects = new Array(count);

  // Force a GC before we start timing
  TestObjectPerf._collectGarbage();
  
  var startCreateTime = new Date();

  for (var i = 0; i < count; i++)
  {
    var object = createFunc();
    objects[i] = object;
  }

  var endCreateTime = new Date();

  TestObjectPerf._logElapsedTime(startCreateTime, endCreateTime);
}

TestObjectPerf._createObjectsFast = function(count)
{
  var objects = new Array(count);

  // Force a GC before we start timing
  TestObjectPerf._collectGarbage();

  var startCreateTime = new Date();

  for (var i = 0; i < count; i++)
  {
    var object = new Object();
  }

  var endCreateTime = new Date();

  TestObjectPerf._logElapsedTime(startCreateTime, endCreateTime);
}

TestObjectPerf._createPOJSO = function()
{
  return new Object();
}

TestObjectPerf._createRichObject = function()
{
  return new AdfObject();
}

TestObjectPerf._createUIComponent = function()
{
  return new AdfUIComponent();
}

TestObjectPerf._createUICommand = function()
{
  return new AdfUICommand();
}

TestObjectPerf._createRichCommandButton = function()
{
  return new AdfRichCommandButton();
}

TestObjectPerf._createUIPeer = function()
{
  return new AdfUIPeer();
}

TestObjectPerf._createDhtmlUIPeer = function()
{
  return new AdfRichUIPeer();
}

TestObjectPerf._createDhtmlCommandButtonPeer = function()
{
  return new AdfDhtmlCommandButtonPeer();
}

TestObjectPerf._createPseudoRichObject = function()
{
  return new PseudoRichObject();
}

TestObjectPerf._createPseudoUIComponent = function()
{
  return new PseudoUIComponent();
}

TestObjectPerf._createPseudoUICommand = function()
{
  return new PseudoUICommand();
}

TestObjectPerf._createPseudoRichCommandButton = function()
{
  return new PseudoRichCommandButton();
}

// Forces a garbage collection (if possible)
TestObjectPerf._collectGarbage = function()
{
  if (window.CollectGarbage)
    window.CollectGarbage();
}

TestObjectPerf._logElapsedTime = function(
  startCreateTime,
  endCreateTime
  )
{
  var startCreateTimeMillis = startCreateTime.getTime();
  var endCreateTimeMillis = endCreateTime.getTime();
  var elapsedCreateTimeMillis = (endCreateTimeMillis - startCreateTimeMillis);
  var message = "Create: " +  elapsedCreateTimeMillis + "ms";

  alert(message);
}


function PseudoRichObject()
{
}

PseudoRichObject.prototype = new Object(); 
PseudoRichObject.prototype.constructor = PseudoRichObject;

function PseudoUIComponent()
{
}

PseudoUIComponent.prototype = new PseudoRichObject(); 
PseudoUIComponent.prototype.constructor = PseudoUIComponent;

function PseudoUICommand()
{
}

PseudoUICommand.prototype = new PseudoUIComponent; 
PseudoUICommand.prototype.constructor = PseudoUICommand;

function PseudoRichCommandButton()
{
}

PseudoRichCommandButton.prototype = new PseudoUICommand; 
PseudoRichCommandButton.prototype.constructor = PseudoRichCommandButton;
