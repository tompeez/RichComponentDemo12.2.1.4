/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
// Utilities required for testFileExplorer.jspx

var TestFileUtils = new Object();

// Action listener for the unimplemented actions
TestFileUtils.notImplemented = function(event)
{
  alert("Not yet implemented: " + event);
}

// Action listener for the search button
TestFileUtils.searchButtonClicked = function(event)
{
  this._toggleAccordionItem(event, ":searchAccordionItem");
}

// Action listener for the folders button
TestFileUtils.foldersButtonClicked = function(event)
{
  this._toggleAccordionItem(event, ":foldersAccordionItem");
}

// Action listener for the reports button
TestFileUtils.reportsButtonClicked = function(event)
{
  this._toggleAccordionItem(event, ":reportsAccordionItem");
}

// Action listener for the search button
TestFileUtils._toggleAccordionItem = function(event, accordionItemId)
{
  var button;
  try
  {
    button = event.getCurrentTarget();
  }
  catch (problem) {}
  
  if (button == null)
  {
    // The "component" should be replaced with "event.getCurrentTarget()" but if
    // it isn't yet implemented, let's fall back on "component":
    button = component;
  }
  var accordionItem = button.findComponent(accordionItemId);
  var currentDisclosed = accordionItem.getDisclosed();
  accordionItem.setDisclosed(!currentDisclosed);
  
  event.cancelDefaultAction();
}


// Selection listener for folder tree
TestFileUtils.directorySelectionChanged = function(event)
{  
  // Changing directory means table selection is
  // cleared out - update menus to reflect this.
  TestFileUtils.updateAllMenus("none");
}


// Expansion listener for folder tree
TestFileUtils.directoryExpansionChanged = function(event)
{
  // if we've set our flag, purge all collapsed subtrees from cache
  if (window["refreshOnCollapse"])
  {
    var removedSet = event.getRemovedSet();
    
    if (removedSet)
    {
      var treeModel = event.getSource().getValue();
          
      for (var collapsedPaths in removedSet)
      {
        treeModel.reload(collapsedPaths);
      }
    }
  }
}

// Selection listener for table.  
TestFileUtils.tableSelectionChanged = function(event)
{
  // Update enabled state for all menus
  var selectionType = TestFileUtils.getSelectionType();
  TestFileUtils.updateAllMenus(selectionType);
}

TestFileUtils.activateCommand = function(event, commandName)
{
  // Use the event source to find the command component by name
  var source = event.getSource();
  var command = source.findComponent(commandName);

  AdfAssert.assert(command);

  // Activate the command
  var actionEvent = new AdfActionEvent(command);
  command.dispatchEvent(actionEvent);
}

// Determine the selection type ("none", "folder" or "document")
// by checking the table's selection state.
TestFileUtils.getSelectionType = function()
{
  var selectionType = "none";
  
  // Until we have support for active component/secondary 
  // selection, items must be selected in the table (not the tree) 
  // in order for selection-dependent operations to be enabled.
  var table = AdfPage.PAGE.findComponentByAbsoluteId("folderTable");
  var selection = table.getSelectedRowKeys();
  var model = table.getValue();
  
  if (selection != null && model)
  {
    // Save away old row index first, since we need
    // to modify the row index in order to get the "type"
    var oldRowKey = model.getRowKey();

    // Loop over selected rows to get the type of file that
    // is selected (either "folder" or "document".  Temporarily
    // treat multiple selections as "none" until we can add support
    // for handling multiple selections.
    for (var rowKey in selection)
    {
      // First, make sure we don't already have a selection.
      // If we've already found a selection, that means multiple
      // items are selected, so just reset selection type to
      // "none" and finish up.
      if (selectionType != "none")
      {
        selectionType = "none";
        break;
      }
  
      // Now, get the "type" value for the selected row.
      // This is our "selectionType" - either "folder" or "document".
      model.setRowKey(rowKey);
      var rowData = model.getRowData();
      selectionType = rowData["type"];
    }
    // Restore old row index
    model.setRowKey(oldRowKey);
  }
  
  return selectionType;
}

// Update menus in response to selection changes
TestFileUtils.updateAllMenus = function(selectionType)
{
//  Logger.LOGGER.severe("TestFileUtils.updateAllMenus()");
 
  // Loop over all items in the menu system, selectively
  // setting enabled state based on selection type
  var thePage = AdfPage.PAGE;
  var menuBar = thePage.findComponentByAbsoluteId("menuBar");
  if (menuBar != null)
  {
    var children = menuBar.getChildren();
    for (var i = 0; i < children.length; i++)
    {
      var menu = children[i];
      
      if (menu instanceof AdfRichMenu)
        TestFileUtils.updateSubmenu(menu.getFacet("popupMenu"), selectionType);
    }
  }
  else // the testFileExplorerTablet.jspx case:
  {
    for (var i = 0; i < 6; i++)
    {
      var popupMenu = thePage.findComponentByAbsoluteId("popupMenu" + i);
      
      TestFileUtils.updateSubmenu(popupMenu.getChild(0).getFacet("popupMenu"), selectionType);
    }
  }
}

TestFileUtils.updateSubmenu = function(popupMenu, selectionType)
{
  if (popupMenu)
  {
    var children = popupMenu.getChildren();
    if (children && (children.length > 0))
    {
      for (var i = 0; i < children.length; i++)
      {
        var child = children[i];        

        // Update the enabled state for the current item
        TestFileUtils.updateMenuItem(child, selectionType);

        // Recursively descend into submenus
        if (child instanceof AdfRichMenu)
          TestFileUtils.updateSubmenu(child.getFacet("popupMenu"), selectionType);
      }
    }
  }
}


// Updates the enabled state for a single menu item
TestFileUtils.updateMenuItem = function(menuItem, selectionType)
{
  // We determine whether a particular menu item should be enabled/disabled
  // by looking at two pieces of information: the "requiresSelection"
  // property of the menu item and the "selectionType" corresponding to
  // the currently selected item.  "requiresSelection" can be one of
  // three values: "any", "folder" or "document", depending on whether
  // the menu item requires any item to be selected, a folder item to
  // be selected, or a document to be selected.  The selectionType is
  // "none", "folder" or "document", depending on whether there is
  // no selection, a selected folder, or a selected document.
  // If "requiresSelection" is set to "any", the menu item is enabled as 
  // long as selectionType is not "none".  If "requiresSelection" is
  // set to "folder" or "document, the menu item is enabled only if
  // the selectionType matches.  If "requiresSelection" is not specified,
  // the enabled state of the menu item is not modified.
  var requiresSelection = menuItem.getProperty("requiresSelection");
        
  if (requiresSelection)
  {
    var disabled = false;
          
    if (requiresSelection == "any")
    {
      // If requiresSelection is "any", the item is enabled as long
      // as selectionType is not "none".
      if (selectionType == "none")
        disabled = true;
    }
    else
    {
      // Otherwise, requiresSelection is either "folder" or
      // "document".  Just make sure the selectionType matches...
      if (requiresSelection != selectionType)
        disabled = true;
    }
 
    menuItem.setProperty("disabled", disabled);
  }
}

// function to update the tree component when the table changes
TestFileUtils.updateFolderTree = function(event) 
{
  var source = event.getSource();
  var tree = source.findComponent("folderTree");
  var table = source.findComponent("folderTable");
  
  // get the "first" selected index (well...assume there's only one row selected for now)
  var selectionModel = table.getSelectedRowKeys();
  var selectedKey;
  for (var index in selectionModel) 
  {
    selectedKey = index; 
    break;
  }

  // get the name of the directory/file in the table that was drilled into
  var tableData = table.getValue();
  tableData.setRowKey(selectedKey);
  var tableRowData = tableData.getRowData();
  var selectedRowName = tableRowData['name']; 

  // retrieve the selected path in the tree (currently, there's only one)
  var treeSelectedPaths = tree.getSelectedRowKeys();
  var treePath = treeSelectedPaths[0];

  // construct new path by appending the new row key  
  var newPath = AdfTreeModel.getChildPath(treePath, selectedRowName);
  
  // set the selection and expansion path
  //tree.setSelectionPath(newPath);
  tree.expandPath(newPath);
}

TestFileUtils.onLoadCallback = function()
{
  var page = AdfPage.PAGE;

  //Logger.LOGGER.severe("TestFileUtils.onLoadCallback\n" + parentNode);
}

TestFileUtils.getTreeComponent = function()
{
  return AdfPage.PAGE.findComponentByAbsoluteId("folderTree");
}

TestFileUtils.getTableComponent = function()
{
  return AdfPage.PAGE.findComponentByAbsoluteId("folderTable");
}

/**
 * Returns a fully qualified path based on a local path in the table
 */
TestFileUtils.getFullyQualifiedPath = function(
  tableRowKey)
{
  var tree = TestFileUtils.getTreeComponent();
  // In the testFileExplorer demo, we have a switcher that switches between
  // a tree and a virtualized tree as the master file navigation ui.
  // we get the fully qualified path using the component that is rendered --
  // the tree or the virtualized tree (which is a rich table component).
  if (tree)
  {
    var treeSelectedPaths = tree.getSelectedRowKeys();
    var treePath = null;
    for(var p in treeSelectedPaths)
    {
      treePath = p;
      break;
    }
    
    // =-= bts necessary because demo bogusly does not force the initial tree
    //         selection to match the table
    if (treePath)
    {
      // construct new path by appending the new table's rowKey  
      return AdfTreeModel.getChildPath(treePath, tableRowKey);
    }
  }
  else
  {
    var virtTree = TestFileUtils.getVirtualTreeComponent();
    if (virtTree)
    {
      var fullyQualified = TestFileUtils.getFullRowKey(tableRowKey);
      return fullyQualified;
    }
  }
  
  return null;
}

// Tests whether the descendentRowKey is a descendent of the
// ancestorRowKey
TestFileUtils._isDescendent = function(
  descendentRowKey,
  ancestorRowKey
  )
{
  // todo: =-=ags Removed dependency on FlattenedTreeCollectionModel
  //              row key implementation.  We should not rely on the
  //              fact that row keys are ":" separated strings.
  if (ancestorRowKey.charAt(ancestorRowKey.length - 1) != ":")
    ancestorRowKey = (ancestorRowKey + "|");

  return (descendentRowKey.indexOf(ancestorRowKey) == 0);
}

// Utility method - retrieves the first selected row in the specified
// table.  Should only be one row selected at a time in the virtualized
// tree.  Returns -1 if no row is selected
TestFileUtils._getFirstSelectedRow = function(table)
{
  var selectionState = table.getSelectedRowKeys();
  
  if (selectionState != null)
  {
    for (rowIndex in selectionState)
    {
      // todo: =-=ags sadly, selection state is stored as properties
      //       of the selection state object, and since property names
      //       are strings we need to convert to int ourselves. Sigh.
      return parseInt(rowIndex);
    }
  }
  
  return -1;
}

// Given a "child" row key from the detail table, returns a full
// row key suitable for use with the virtualized tree
TestFileUtils.getFullRowKey = function(childRowKey)
{
  // The full row key is the row key of the currently selected
  // parent row plus the child row key.  Find the selected
  // row in the virtualized tree.
  var virtTree = TestFileUtils.getVirtualTreeComponent();
  var parentRow = TestFileUtils._getFirstSelectedRow(virtTree);
  
  // If we don't have a parent, we can't produce a full row key
  if (parentRow == -1)
    return null;
    
  // Get the parent row key
  var model = virtTree.getValue();
  var oldRowIndex = model.getRowIndex();
  model.setRowIndex(parentRow);
  var parentRowKey = model.getRowKey();
  model.setRowIndex(oldRowIndex);
  
  // Produce the full row key
  // todo: =-=ags This code relies on an implementation detail of
  //       the FlattenedTreeCollectionModel - ie. that flattened
  //       row keys are assembled by separating row keys with ":".
  //       Need to find a better way to do this which doesn't rely
  //       on this implementation detail!
  return parentRowKey + "|" + childRowKey;
}
/** Copyright (c) 2008, 2009, Oracle and/or its affiliates. All rights reserved. */
// Utilities required for testFileExplorer.jspx

var TestFileUtils = new Object();

// Action listener for the unimplemented actions
TestFileUtils.notImplemented = function(event)
{
  alert("Not yet implemented: " + event);
}

// Action listener for the search button
TestFileUtils.searchButtonClicked = function(event)
{
  this._toggleAccordionItem(event, ":searchAccordionItem");
}

// Action listener for the folders button
TestFileUtils.foldersButtonClicked = function(event)
{
  this._toggleAccordionItem(event, ":foldersAccordionItem");
}

// Action listener for the reports button
TestFileUtils.reportsButtonClicked = function(event)
{
  this._toggleAccordionItem(event, ":reportsAccordionItem");
}

// Action listener for the search button
TestFileUtils._toggleAccordionItem = function(event, accordionItemId)
{
  var button;
  try
  {
    button = event.getCurrentTarget();
  }
  catch (problem) {}
  
  if (button == null)
  {
    // The "component" should be replaced with "event.getCurrentTarget()" but if
    // it isn't yet implemented, let's fall back on "component":
    button = component;
  }
  var accordionItem = button.findComponent(accordionItemId);
  var currentDisclosed = accordionItem.getDisclosed();
  accordionItem.setDisclosed(!currentDisclosed);
  
  event.cancelDefaultAction();
}


// Selection listener for folder tree
TestFileUtils.directorySelectionChanged = function(event)
{  
  // Changing directory means table selection is
  // cleared out - update menus to reflect this.
  TestFileUtils.updateAllMenus("none");
}


// Expansion listener for folder tree
TestFileUtils.directoryExpansionChanged = function(event)
{
  // if we've set our flag, purge all collapsed subtrees from cache
  if (window["refreshOnCollapse"])
  {
    var removedSet = event.getRemovedSet();
    
    if (removedSet)
    {
      var treeModel = event.getSource().getValue();
          
      for (var collapsedPaths in removedSet)
      {
        treeModel.reload(collapsedPaths);
      }
    }
  }
}

// Selection listener for table.  
TestFileUtils.tableSelectionChanged = function(event)
{
  // Update enabled state for all menus
  var selectionType = TestFileUtils.getSelectionType();
  TestFileUtils.updateAllMenus(selectionType);
}

TestFileUtils.activateCommand = function(event, commandName)
{
  // Use the event source to find the command component by name
  var source = event.getSource();
  var command = source.findComponent(commandName);

  AdfAssert.assert(command);

  // Activate the command
  var actionEvent = new AdfActionEvent(command);
  command.dispatchEvent(actionEvent);
}

// Determine the selection type ("none", "folder" or "document")
// by checking the table's selection state.
TestFileUtils.getSelectionType = function()
{
  var selectionType = "none";
  
  // Until we have support for active component/secondary 
  // selection, items must be selected in the table (not the tree) 
  // in order for selection-dependent operations to be enabled.
  var table = AdfPage.PAGE.findComponentByAbsoluteId("folderTable");
  var selection = table.getSelectedRowKeys();
  var model = table.getValue();
  
  if (selection != null && model)
  {
    // Save away old row index first, since we need
    // to modify the row index in order to get the "type"
    var oldRowKey = model.getRowKey();

    // Loop over selected rows to get the type of file that
    // is selected (either "folder" or "document".  Temporarily
    // treat multiple selections as "none" until we can add support
    // for handling multiple selections.
    for (var rowKey in selection)
    {
      // First, make sure we don't already have a selection.
      // If we've already found a selection, that means multiple
      // items are selected, so just reset selection type to
      // "none" and finish up.
      if (selectionType != "none")
      {
        selectionType = "none";
        break;
      }
  
      // Now, get the "type" value for the selected row.
      // This is our "selectionType" - either "folder" or "document".
      model.setRowKey(rowKey);
      var rowData = model.getRowData();
      selectionType = rowData["type"];
    }
    // Restore old row index
    model.setRowKey(oldRowKey);
  }
  
  return selectionType;
}

// Update menus in response to selection changes
TestFileUtils.updateAllMenus = function(selectionType)
{
//  Logger.LOGGER.severe("TestFileUtils.updateAllMenus()");
 
  // Loop over all items in the menu system, selectively
  // setting enabled state based on selection type
  var thePage = AdfPage.PAGE;
  var menuBar = thePage.findComponentByAbsoluteId("menuBar");
  if (menuBar != null)
  {
    var children = menuBar.getChildren();
    for (var i = 0; i < children.length; i++)
    {
      var menu = children[i];
      
      if (menu instanceof AdfRichMenu)
        TestFileUtils.updateSubmenu(menu.getFacet("popupMenu"), selectionType);
    }
  }
  else // the testFileExplorerTablet.jspx case:
  {
    for (var i = 0; i < 6; i++)
    {
      var popupMenu = thePage.findComponentByAbsoluteId("popupMenu" + i);
      
      TestFileUtils.updateSubmenu(popupMenu.getChild(0).getFacet("popupMenu"), selectionType);
    }
  }
}

TestFileUtils.updateSubmenu = function(popupMenu, selectionType)
{
  if (popupMenu)
  {
    var children = popupMenu.getChildren();
    if (children && (children.length > 0))
    {
      for (var i = 0; i < children.length; i++)
      {
        var child = children[i];        

        // Update the enabled state for the current item
        TestFileUtils.updateMenuItem(child, selectionType);

        // Recursively descend into submenus
        if (child instanceof AdfRichMenu)
          TestFileUtils.updateSubmenu(child.getFacet("popupMenu"), selectionType);
      }
    }
  }
}


// Updates the enabled state for a single menu item
TestFileUtils.updateMenuItem = function(menuItem, selectionType)
{
  // We determine whether a particular menu item should be enabled/disabled
  // by looking at two pieces of information: the "requiresSelection"
  // property of the menu item and the "selectionType" corresponding to
  // the currently selected item.  "requiresSelection" can be one of
  // three values: "any", "folder" or "document", depending on whether
  // the menu item requires any item to be selected, a folder item to
  // be selected, or a document to be selected.  The selectionType is
  // "none", "folder" or "document", depending on whether there is
  // no selection, a selected folder, or a selected document.
  // If "requiresSelection" is set to "any", the menu item is enabled as 
  // long as selectionType is not "none".  If "requiresSelection" is
  // set to "folder" or "document, the menu item is enabled only if
  // the selectionType matches.  If "requiresSelection" is not specified,
  // the enabled state of the menu item is not modified.
  var requiresSelection = menuItem.getProperty("requiresSelection");
        
  if (requiresSelection)
  {
    var disabled = false;
          
    if (requiresSelection == "any")
    {
      // If requiresSelection is "any", the item is enabled as long
      // as selectionType is not "none".
      if (selectionType == "none")
        disabled = true;
    }
    else
    {
      // Otherwise, requiresSelection is either "folder" or
      // "document".  Just make sure the selectionType matches...
      if (requiresSelection != selectionType)
        disabled = true;
    }
 
    menuItem.setProperty("disabled", disabled);
  }
}

// function to update the tree component when the table changes
TestFileUtils.updateFolderTree = function(event) 
{
  var source = event.getSource();
  var tree = source.findComponent("folderTree");
  var table = source.findComponent("folderTable");
  
  // get the "first" selected index (well...assume there's only one row selected for now)
  var selectionModel = table.getSelectedRowKeys();
  var selectedKey;
  for (var index in selectionModel) 
  {
    selectedKey = index; 
    break;
  }

  // get the name of the directory/file in the table that was drilled into
  var tableData = table.getValue();
  tableData.setRowKey(selectedKey);
  var tableRowData = tableData.getRowData();
  var selectedRowName = tableRowData['name']; 

  // retrieve the selected path in the tree (currently, there's only one)
  var treeSelectedPaths = tree.getSelectedRowKeys();
  var treePath = treeSelectedPaths[0];

  // construct new path by appending the new row key  
  var newPath = AdfTreeModel.getChildPath(treePath, selectedRowName);
  
  // set the selection and expansion path
  //tree.setSelectionPath(newPath);
  tree.expandPath(newPath);
}

TestFileUtils.onLoadCallback = function()
{
  var page = AdfPage.PAGE;

  //Logger.LOGGER.severe("TestFileUtils.onLoadCallback\n" + parentNode);
}

TestFileUtils.getTreeComponent = function()
{
  return AdfPage.PAGE.findComponentByAbsoluteId("folderTree");
}

TestFileUtils.getTableComponent = function()
{
  return AdfPage.PAGE.findComponentByAbsoluteId("folderTable");
}

/**
 * Returns a fully qualified path based on a local path in the table
 */
TestFileUtils.getFullyQualifiedPath = function(
  tableRowKey)
{
  var tree = TestFileUtils.getTreeComponent();
  // In the testFileExplorer demo, we have a switcher that switches between
  // a tree and a virtualized tree as the master file navigation ui.
  // we get the fully qualified path using the component that is rendered --
  // the tree or the virtualized tree (which is a rich table component).
  if (tree)
  {
    var treeSelectedPaths = tree.getSelectedRowKeys();
    var treePath = null;
    for(var p in treeSelectedPaths)
    {
      treePath = p;
      break;
    }
    
    // =-= bts necessary because demo bogusly does not force the initial tree
    //         selection to match the table
    if (treePath)
    {
      // construct new path by appending the new table's rowKey  
      return AdfTreeModel.getChildPath(treePath, tableRowKey);
    }
  }
  else
  {
    var virtTree = TestFileUtils.getVirtualTreeComponent();
    if (virtTree)
    {
      var fullyQualified = TestFileUtils.getFullRowKey(tableRowKey);
      return fullyQualified;
    }
  }
  
  return null;
}

// Tests whether the descendentRowKey is a descendent of the
// ancestorRowKey
TestFileUtils._isDescendent = function(
  descendentRowKey,
  ancestorRowKey
  )
{
  // todo: =-=ags Removed dependency on FlattenedTreeCollectionModel
  //              row key implementation.  We should not rely on the
  //              fact that row keys are ":" separated strings.
  if (ancestorRowKey.charAt(ancestorRowKey.length - 1) != ":")
    ancestorRowKey = (ancestorRowKey + "|");

  return (descendentRowKey.indexOf(ancestorRowKey) == 0);
}

// Utility method - retrieves the first selected row in the specified
// table.  Should only be one row selected at a time in the virtualized
// tree.  Returns -1 if no row is selected
TestFileUtils._getFirstSelectedRow = function(table)
{
  var selectionState = table.getSelectedRowKeys();
  
  if (selectionState != null)
  {
    for (rowIndex in selectionState)
    {
      // todo: =-=ags sadly, selection state is stored as properties
      //       of the selection state object, and since property names
      //       are strings we need to convert to int ourselves. Sigh.
      return parseInt(rowIndex);
    }
  }
  
  return -1;
}

// Given a "child" row key from the detail table, returns a full
// row key suitable for use with the virtualized tree
TestFileUtils.getFullRowKey = function(childRowKey)
{
  // The full row key is the row key of the currently selected
  // parent row plus the child row key.  Find the selected
  // row in the virtualized tree.
  var virtTree = TestFileUtils.getVirtualTreeComponent();
  var parentRow = TestFileUtils._getFirstSelectedRow(virtTree);
  
  // If we don't have a parent, we can't produce a full row key
  if (parentRow == -1)
    return null;
    
  // Get the parent row key
  var model = virtTree.getValue();
  var oldRowIndex = model.getRowIndex();
  model.setRowIndex(parentRow);
  var parentRowKey = model.getRowKey();
  model.setRowIndex(oldRowIndex);
  
  // Produce the full row key
  // todo: =-=ags This code relies on an implementation detail of
  //       the FlattenedTreeCollectionModel - ie. that flattened
  //       row keys are assembled by separating row keys with ":".
  //       Need to find a better way to do this which doesn't rely
  //       on this implementation detail!
  return parentRowKey + "|" + childRowKey;
}
