/** Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved. */

// TestGeometryManagement component class
var TestGeometryManagement = AdfUIComponents.createComponentClass("TestGeometryManagement",
{
  componentType:"oracle.adf.TestGeometryManagement",
  propertyKeys:[{name:"inlineStyle",type:"String"},
  {name:"styleClass",type:"String"},
  {name:"visible",type:"boolean","default":true},
  {name:"shortDesc",type:"String"},
  {name:"disabled",type:"boolean"},
  {name:"stretchChildren",type:"String"}],
  superclass:AdfUIPanel
});

/**
 * We want events queued from this component to be processed immediately.
 */
TestGeometryManagement.prototype.getImmediate = function()
{
  return true;
}