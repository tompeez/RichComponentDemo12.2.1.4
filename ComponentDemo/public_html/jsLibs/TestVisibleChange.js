/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
var TestVisibleChange = new Object();

// Action event listener for toggling visibility.
TestVisibleChange.toggleResizeVisibility = function(evt)
{
  TestVisibleChange._setVisibility(evt, "resizeNotifyPanel");
}

// Action event listener for toggling visibility in region.
TestVisibleChange.toggleResizeVisibilityInRegion = function(evt)
{
  TestVisibleChange._setVisibility(evt, "testRegion:resizeNotifyPanel");
}

// Action event listener for toggling visibility.
TestVisibleChange.toggleParentVisibility = function(evt)
{
  TestVisibleChange._setVisibility(evt, "parentPanel");
}


TestVisibleChange._setVisibility = function(evt, targetId)
{
  var source = evt.getSource();
  var visible = source.getValue();
  
  var targetComp = AdfPage.PAGE.findComponentByAbsoluteId(targetId);

  // Update visible state
  targetComp.setVisible(visible);
  
  evt.cancel();
}