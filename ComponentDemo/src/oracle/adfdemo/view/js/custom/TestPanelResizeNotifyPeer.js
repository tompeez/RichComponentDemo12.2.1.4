/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */

// panelResizeNotify peer
AdfRichUIPeer.createPeerClass(AdfRichUIPeer, 
                               "TestPanelResizeNotifyPeer",
                               false // not stateless
                               );
                               
// Override to indicate interest in resize notifications
TestPanelResizeNotifyPeer.prototype.needsResizeNotify = function(component)
{
  return true;
}

// Resize notification callback - we perform geometry management here 
TestPanelResizeNotifyPeer.prototype.resizeNotify = function(
  component,
  oldWidth,
  oldHeight,
  newWidth,
  newHeight
  )
{
  // Get the count of the # of resize that have occurred so far
  var resizeCount = this._resizeCount;
  if (!resizeCount)
    resizeCount = 1;
  else
    resizeCount++;

  // Construct the message to display
  var resizeMessage = "Count: " + resizeCount;
  resizeMessage += "<br>" + "Old Width: ";
  resizeMessage += oldWidth ? oldWidth : "n/a";
  resizeMessage += "<br>" + "Old Height: ";
  resizeMessage += oldHeight ? oldHeight : "n/a";
  resizeMessage += "<br>" + "New Width: ";
  resizeMessage += newWidth ? newWidth : "n/a";
  resizeMessage += "<br>" + "New Height: ";
  resizeMessage += newHeight ? newHeight : "n/a";


  // Display the message
  var clientId = component.getClientId();
  var messageId = AdfRichUIPeer.createSubId(clientId, "message");
  var domElement = document.getElementById(messageId);
  domElement.innerHTML = resizeMessage;

  // Update the resize counter
  this._resizeCount = resizeCount;
}

// Register the peer constructor
AdfPage.PAGE.getLookAndFeel().registerPeerConstructor(
  "oracle.adf.TestPanelResizeNotify",
  "TestPanelResizeNotifyPeer");
