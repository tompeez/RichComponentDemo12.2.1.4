/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */

// panelResizeNotify component class
var TestPanelResizeNotify = AdfUIComponents.createComponentClass("TestPanelResizeNotify",
{
  componentType:"oracle.adf.TestPanelResizeNotify",
  propertyKeys:[{name:"inlineStyle",type:"String"},
  {name:"styleClass",type:"String"},
  {name:"visible",type:"boolean","default":true},
  {name:"shortDesc",type:"String"},
  {name:"disabled",type:"boolean"}],
  superclass:AdfUIPanel
});
