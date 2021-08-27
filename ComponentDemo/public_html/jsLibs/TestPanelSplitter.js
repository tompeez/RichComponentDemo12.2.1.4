/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
var TestPanelSplitter = new Object();

// Value change event listener for toggling facet visibility.
TestPanelSplitter.toggleVisibility = function(evt)
{
  // Check to see if we should handle visible property changes
  // locally.  If not, they will be dealt with via PPR.
  if (!TestPanelSplitter._isVisibleHandledLocally(evt))
  {
    var splitterParent = evt.getSource().findComponent("innerVerticalSplitter").getParent();
    AdfPage.PAGE.addPartialTargets(splitterParent);
    return;
  }
  evt.cancel();
  var targetComp = TestPanelSplitter._getToggleVisibilityTarget(evt);

  // Update visible state
  targetComp.setVisible(evt.getNewValue());
}

// Returns the child component that to target for the visibility change
TestPanelSplitter._getToggleVisibilityTarget = function(evt)
{
  var source = evt.getSource();
  var sourceId = source.getClientId();

  // Derive the target id from the source id
  var endIndex = sourceId.indexOf("VisibleCheckbox");
  var targetId = sourceId.substring(0, endIndex) + "Child";

  return AdfPage.PAGE.findComponent(targetId);
}

// Tests whether to handle visibility toggling locally on the client
TestPanelSplitter._isVisibleHandledLocally = function(evt)
{
  var checkbox = evt.getSource().findComponent("localVisibleCheckbox");

  return checkbox.getValue();
}
