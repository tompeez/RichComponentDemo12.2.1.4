/** Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved. */

// TestGeometryManagement peer
AdfRichUIPeer.createPeerClass(
  AdfRichUIPeer,
  "TestGeometryManagementPeer",
  false); // not stateless (aka stateful)

/**
 * Initialize event handlers.
 */
TestGeometryManagementPeer.InitSubclass = function()
{
  AdfRichUIPeer.addComponentEventHandlers(this,
    AdfUIInputEvent.CLICK_EVENT_TYPE);
}

TestGeometryManagementPeer.prototype.BindToComponent = function(component, domElement)
{
  // Do superclass binding:
  TestGeometryManagementPeer.superclass.BindToComponent.call(this, component, domElement);

  // Create sub-ids for the Dom elements that we bind to.
  var clientId             = component.getClientId();
  var bodyId               = AdfRichUIPeer.createSubId(clientId, "body");
  var controlsId           = AdfRichUIPeer.createSubId(clientId, "controls");
  var switchStretchId      = AdfRichUIPeer.createSubId(clientId, "swstretch");
  var switchFlowYesWidthId = AdfRichUIPeer.createSubId(clientId, "swflowyw");
  var switchFlowNoWidthId  = AdfRichUIPeer.createSubId(clientId, "swflownw");

  // Save references to the Dom elements that we care about
  var agent                   = AdfAgent.AGENT;
  this._bodyDom               = agent.getElementById(bodyId);
  this._controlsDom           = agent.getElementById(controlsId);
  this._switchStretchDom      = agent.getElementById(switchStretchId);
  this._switchFlowYesWidthDom = agent.getElementById(switchFlowYesWidthId);
  this._switchFlowNoWidthDom  = agent.getElementById(switchFlowNoWidthId);
}

TestGeometryManagementPeer.prototype.UnbindFromComponent = function()
{
  // Do superclass unbinding:
  TestGeometryManagementPeer.superclass.UnbindFromComponent.call(this);

  this._bodyDom               = null;
  this._controlsDom           = null;
  this._switchStretchDom      = null;
  this._switchFlowYesWidthDom = null;
  this._switchFlowNoWidthDom  = null;
}

// Toggle collapsed in response to click on the stretchChildren switch link.
TestGeometryManagementPeer.prototype.HandleComponentClick = function(componentEvent)
{
  var eventTarget        = componentEvent.getNativeEventTarget();
  var newStretchChildren = null;

  if (AdfDomUtils.isAncestorOrSelf(this._switchStretchDom, eventTarget))
  {
    newStretchChildren = "first";
  }
  else if (AdfDomUtils.isAncestorOrSelf(this._switchFlowYesWidthDom, eventTarget))
  {
    newStretchChildren = "none";
  }
  else if (AdfDomUtils.isAncestorOrSelf(this._switchFlowNoWidthDom, eventTarget))
  {
    newStretchChildren = "noneWithZeroWidth";
  }

  if (newStretchChildren != null)
  {
    var component = this.getComponent();

    // We call setProperty() instead of setStretchChildren() so that we can specify persist="true",
    // since we want to persist user-specified changes.
    component.setProperty(TestGeometryManagement.STRETCH_CHILDREN, newStretchChildren, true);

    // Mark this component as one that needs to be re-rendered:
    AdfPage.PAGE.addPartialTargets(component);

    // Queue an event to the server in order to force the partial targets to take effect;
    // to redraw using the newly selected stretching mechanism:
    AdfActionEvent.queue(component, true);
  }
}

/**
 * Override to indicate interest in resize notifications.
 * @Override
 */
TestGeometryManagementPeer.prototype.needsResizeNotify = function(component)
{
  return true;
}

/**
 * Resize notification hook.
 * Note that widths are clientWidths and heights are clientHeights.
 * @Override
 */
TestGeometryManagementPeer.prototype.ResizeNotify = function(
  oldWidth,
  oldHeight,
  newWidth,
  newHeight)
{
  var controlsDom = this._controlsDom;

  // If there is a single stretched child (we can tell by what we have on our controlsDom style as
  // written by our own renderer):
  if (controlsDom.style.position == "absolute")
  {
    // Resize the wrapper around the stretched child:
    var controlsHeight   = controlsDom.offsetHeight;
    var bodyDom          = this._bodyDom;
    bodyDom.style.bottom = controlsHeight + "px";

    // Then resize the stretched child:
    var childElement = TestGeometryManagementPeer.getFirstChildElement(bodyDom);
    if (childElement != null)
    {
      AdfAgent.AGENT.resizeStretchedChild(childElement, "0px");
    }
  }
}

TestGeometryManagementPeer.getFirstChildElement = function(parentElement)
{
  var childNodes = parentElement.childNodes;
  if (childNodes)
  {
    var length = childNodes.length;
    for (var i=0; i<length; i++)
    {
      var child = childNodes[i];
      if (child.nodeType == 1)
      {
        return child;
      }
    }
  }
  return null;
}

// Register the peer constructor
AdfPage.PAGE.getLookAndFeel().registerPeerConstructor(
  "oracle.adf.TestGeometryManagement",
  "TestGeometryManagementPeer");