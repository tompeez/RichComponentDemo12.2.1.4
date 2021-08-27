/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
var TestPanelStretchLayout = new Object();

// Value change event listener for toggling facet visibility.
TestPanelStretchLayout.toggleVisibility = function(evt)
{
  TestPanelStretchLayout._toggleVisibility(evt, true);
}

// Value change event listener for toggling facet visibility.
TestPanelStretchLayout.toggleVisibility2 = function(evt)
{
  TestPanelStretchLayout._toggleVisibility(evt, false);
}

// Modifies the height style of the event source:
TestPanelStretchLayout.styleChange = function(evt)
{
  evt.cancel();
  var commandLink = evt.getSource();
  var inlineStyle = commandLink.getInlineStyle();
  if (inlineStyle == null || inlineStyle == "")
  {
    commandLink.setInlineStyle("height: 3em; display: block; background-color: #FFFFAA;");
  }
  else
  {
    commandLink.setInlineStyle("");
  }
}

// Modifies the text attribute of the event source:
TestPanelStretchLayout.textChange = function(evt)
{
  evt.cancel();
  var commandLink = evt.getSource();
  var text = commandLink.getText();
  var suffix = " abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz";
  var indexOfSuffix = text.indexOf(suffix);
  if (indexOfSuffix == -1)
  {
    commandLink.setText(text + suffix);
  }
  else
  {
    commandLink.setText(text.substring(0, indexOfSuffix));
  }
}

// Modifies the style of the input component:
TestPanelStretchLayout.styleChangeOnInput = function(evt)
{
  evt.cancel();
  var commandLink = evt.getSource();
  var commandLinkId = commandLink.getId();
  var inputTextId = commandLinkId.substring(0, commandLinkId.indexOf("CommandLink1")) + "Field";
  var inputText = commandLink.findComponent(inputTextId);
  var contentStyle = inputText.getContentStyle();
  if (contentStyle == null || contentStyle == "")
  {
    inputText.setContentStyle("width: 20em; background-color: #FFFFAA;");
  }
  else
  {
    inputText.setContentStyle("");
  }
}

// Modifies the label attribute of the panelLabelAndMessage component:
TestPanelStretchLayout.labelChange = function(evt)
{
  evt.cancel();
  var commandLink = evt.getSource();
  var commandLinkId = commandLink.getId();
  var panelLabelAndMessageId = commandLinkId.substring(0, commandLinkId.indexOf("CommandLink2")) + "Child2";
  var panelLabelAndMessage = commandLink.findComponent(panelLabelAndMessageId);
  var label = panelLabelAndMessage.getLabel();
  var suffix = " longer than before";
  var indexOfSuffix = label.indexOf(suffix);
  if (indexOfSuffix == -1)
  {
    panelLabelAndMessage.setLabel(label + suffix);
  }
  else
  {
    panelLabelAndMessage.setLabel(label.substring(0, indexOfSuffix));
  }
}

// Value change event listener for toggling facet visibility.
TestPanelStretchLayout._toggleVisibility = function(evt, firstSet)
{
  // Check to see if we should handle visible property changes
  // locally.  If not, they will be dealt with via PPR.
  if (!TestPanelStretchLayout._isVisibleHandledLocally(evt, firstSet))
  {
    var eventSource = evt.getSource();
    var stretchExample = eventSource.findComponent("stretchExample");
    var autoHeightExample = eventSource.findComponent("autoHeightExample");
    if (stretchExample != null)
      AdfPage.PAGE.addPartialTargets(stretchExample);
    else if (autoHeightExample != null)
      AdfPage.PAGE.addPartialTargets(autoHeightExample);
    else
      alert("Error - the page may have changed because TestPanelStretchLayout.js cannot find a component to redraw")
    return;
  }

  evt.cancel();

  var targetComp = TestPanelStretchLayout._getToggleVisibilityTarget(evt, firstSet);

  // Update visible state
  var source = evt.getSource();
  var sourceId = source.getClientId();
  var newVisible = (sourceId.indexOf("Hide") == -1);
  targetComp.setVisible(newVisible);
}

// Returns the child component that to target for the visibility change
TestPanelStretchLayout._getToggleVisibilityTarget = function(evt, firstSet)
{
  var source = evt.getSource();
  var sourceId = source.getClientId();

  // Derive the target id from the source id
  var endIndex = sourceId.indexOf("ShowButton");
  if (endIndex == -1)
  {
    endIndex = sourceId.indexOf("HideButton")
  }
  var targetId = sourceId.substring(0, endIndex) + "Child" + (firstSet?"":"2");

  return AdfPage.PAGE.findComponent(targetId);
}

// Tests whether to handle visibility toggling locally on the client
TestPanelStretchLayout._isVisibleHandledLocally = function(evt, firstSet)
{
  var checkbox = evt.getSource().findComponent("localVisibleCheckbox" + (firstSet?"":"2"));

  return checkbox.getValue();
}