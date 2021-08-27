/*
** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved.
*/
var Explorer = new Object();

Explorer.showAboutFileExplorerPopup = function(event)
{
  var source = event.getSource();
  var alignType = source.getProperty("align");
  var alignCompId = source.getProperty("alignId");
  var popupCompId = source.getProperty("popupCompId");

  var aboutPopup = event.getSource().findComponent(popupCompId);
  //alert("showAboutFileExplorerPopup, popupCompId: " + popupCompId + ", obj on page: " + aboutPopup);
  aboutPopup.show({align:alignType, alignId:alignCompId});

  event.cancel();
}

Explorer.showHelpFileExplorerPopup = function(event)
{
  var helpPopup = event.getSource().findComponent("fe:help:helpPopup");

  //alert("showHelpFileExplorerPopup, obj on page: " + helpPopup);
  helpPopup.show({align:AdfRichPopup.ALIGN_END_AFTER});

  event.cancel();
}

Explorer.searchNameHandleKeyPress = function (event)
{
  if (event.getKeyCode()==AdfKeyStroke.ENTER_KEY)
  {
    var source = event.getSource();
    AdfCustomEvent.queue(source,
                         "enterPressedOnSearch",
                         {},
                         false);
  }
}

