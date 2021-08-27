/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
// Library containing custom components/peers

  // testPanelAddScript component class
var TestPanelAddScript = AdfUIComponents.createComponentClass("TestPanelAddScript",
{
  componentType:"oracle.adf.TestPanelAddScript",
  propertyKeys:[{name:"inlineStyle",type:"String"},
  {name:"styleClass",type:"String"},
  {name:"visible",type:"boolean","default":true},
  {name:"shortDesc",type:"String"},
  {name:"disabled",type:"boolean"}],
  superclass:AdfUIPanel
});

// panelAddScript peer
var TestPanelAddScriptPeer = AdfRichUIPeer.createPeerClass(AdfRichUIPeer, 
                               "TestPanelAddScriptPeer",
                               true
                               );

// Register the peer constructor
AdfPage.PAGE.getLookAndFeel().registerPeerConstructor(
  "oracle.adf.TestPanelAddScript",
  "TestPanelAddScriptPeer");