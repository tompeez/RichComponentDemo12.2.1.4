/*
** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved.
*/

/**
 * Client-side event handling code for Inline Editing Test
 */

/**
 * One time initialization of client side code
 */ 
function initEditing()
{
  // listen for PropertyChanges on the Page
  AdfPage.PAGE.addPropertyChangeListener(null, _pagePropertyChanged);
}

/**
 * Called to toggle inline editing of the subtree
 */
function toggleInlineEditing(actionEvent)
{
  actionEvent.cancel();
  
  var editableSubtree;
  var newButtonText;
  
  var select1Button = AdfPage.PAGE.findComponentByAbsoluteId("select1");
  var select2Button = AdfPage.PAGE.findComponentByAbsoluteId("select2");
  var isEditing = window.isEditing;

  // toggle window flag
  window.isEditing = !isEditing;
   
  if (isEditing)
  {
    editableSubtree = null;
    newButtonText = "Stop Inline Edit";
  }
  else
  {
    editableSubtree = AdfPage.PAGE.findComponentByAbsoluteId("editMe");
    newButtonText = "Start Inline Edit";
  }
  
  //select1Button.setProperty("disabled", isEditing);
  //select2Button.setProperty("disabled", isEditing);
             
  // set new text
  actionEvent.getSource().setText(newButtonText);
  
  // update the editable subtree
  AdfPage.PAGE.setEditableSubtree(editableSubtree);
}

/**
 * Selects the first outputText
 */
function selectThing1(event)
{
  AdfPage.PAGE.setSelectedEditingComponents([AdfPage.PAGE.findComponentByAbsoluteId("selectable1")]);            
  event.cancel();          
}

/**
 * Selects the second outputText
 */
function selectThing2(event)
{
  AdfPage.PAGE.setSelectedEditingComponents([AdfPage.PAGE.findComponentByAbsoluteId("selectable2")]);            
  event.cancel();          
}

/**
 * Returns the default property edited by the inline editor for a component
 */
function _getEditedProperty(component)
{  
  var editedProperty = null;
  
  if (component != null)
  {
    var inlineEditor = component.getPeer().getInlineEditor(component, null);
    
    if (inlineEditor != null)
      editedProperty = inlineEditor.getEditedProperty();      
  }
  
  return editedProperty;
}

/**
 * Updates the edited proprty of the edited component with the new value
 */
function _updateEditedValue(newValue)
{
  var editedComponent = AdfPage.PAGE.getEditedComponent();
  var editedProperty = _getEditedProperty(editedComponent);
  
  if (editedProperty != null)
    editedComponent.setProperty(editedProperty, newValue);
}

/**
 * Called by PI inputText to set the value of the current selected editing component
 */
function setSelectedPropertyValue(propertyChangeEvent)
{    
  //AdfLogger.LOGGER.severe("Change property to:", propertyChangeEvent);
  
  if (propertyChangeEvent.getPropertyName() == "value")
  {
    _updateEditedValue(propertyChangeEvent.getNewValue());
  }
}


/**
 * Called by PI inputText to set the value of the current selected editing component
 * This code is currently unused because of a nasty ordering bug between property change and
 * value change events
 */
function setSelectedValue(valueChangeEvent)
{  
  valueChangeEvent.cancel();
  
  //AdfLogger.LOGGER.severe("Change value to:", valueChangeEvent);
  _updateEditedValue(valueChangeEvent.getNewValue());
}


/**
 * Called to toggle the editable filter on the subtree on and off
 */
function toggleFilter(valueChangeEvent)
{
  var newValue = valueChangeEvent.getNewValue();
  
  if (newValue)
  {
    AdfPage.PAGE.setEditableSubtreeFilter(_noSomtimesFilter, null);    
  }
  else
  {
    // turn off filter
    AdfPage.PAGE.setEditableSubtreeFilter(null, null);
  }
}

/**
 * Filter callback function that makes the "sometimesEditable" component non-editable
 */
function _noSomtimesFilter(component)
{
  return  (component.getClientId() == "sometimesEditable")
            ? AdfRichInlineEditor.FILTER_UNSELECTABLE
            : AdfRichInlineEditor.FILTER_EDITABLE;
}
 

/**
 * Update the value in the valueInput whenever the property on the selected
 * component changes
 */
function _selectedPropertyChange(propertyChange)
{
  // AdfLogger.LOGGER.severe("Selected property change :", propertyChange);

  if (propertyChange.getPropertyName() == _getEditedProperty(propertyChange.getSource()))
  {
    var valueInput = AdfPage.PAGE.findComponentByAbsoluteId("valueEditor");
    valueInput.setProperty("value", propertyChange.getNewValue());
  }
}

/**
 * Synchronizes the PI side of the page in response to changes to the selected components.
 * @param {AdfPropertyChangEvent} propertyChange
 */
function _pagePropertyChanged(propertyChange)
{
  // AdfLogger.LOGGER.severe("Page change event:", propertyChange);

  var thePage = propertyChange.getSource();
  var propertyName = propertyChange.getPropertyName();
  
  if (AdfPage.SELECTED_EDITING_COMPONENTS_PROPERTY == propertyName)
  {
    var showSelected = thePage.findComponentByAbsoluteId("selectedComponent");
    var valueInput = thePage.findComponentByAbsoluteId("valueEditor");
    
    var newSelectionArray = propertyChange.getNewValue();
    var newSelectedComponent = (newSelectionArray != null)
                                 ? newSelectionArray[0]
                                 : null;

    var oldSelectionArray = propertyChange.getOldValue();
    var oldSelectedComponent = (oldSelectionArray != null)
                                 ? oldSelectionArray[0]
                                 : null;
    
    if (oldSelectedComponent != null)
    {
      // remove our synchronizing property change listener from the old selected
      // component
      oldSelectedComponent.removeEventListener("propertyChange", _selectedPropertyChange);
    }
    
    //
    // Update the displayed Id of the selected component and the value of its edited property
    //
    var newSelectedId;
    var newSelectedValue;
    
    if (newSelectedComponent != null)
    {
      newSelectedId = newSelectedComponent.getClientId();
      newSelectedValue = null;
      
      var editedProperty = _getEditedProperty(newSelectedComponent);
      
      if (editedProperty != null)
      {
        newSelectedValue = newSelectedComponent.getProperty(editedProperty);
      }
      
      // add a property change listener on the newly selected component so
      // that we can synchronize and changes to its value
      newSelectedComponent.addEventListener("propertyChange", _selectedPropertyChange);
    }
    else
    {
      // no component selected
      newSelectedId = "None";
      newSelectedValue = null;
    }
    
    showSelected.setProperty("value", newSelectedId);
    valueInput.setProperty("value", newSelectedValue);
  }
}