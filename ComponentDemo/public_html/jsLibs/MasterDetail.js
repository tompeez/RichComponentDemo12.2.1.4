/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
// Utilities required for feature/masterDetail.jspx

var MasterDetail = new Object();
MasterDetail._CURR_MASTER_ROWINDEX = "";

/**
 * Updates the employee details of the department head for the selected row in master table
 * @param event 
 */
MasterDetail.updateDeptHeader = function(event) 
{
  var deptTable = event.getSource();
  MasterDetail.updateEmpDetails(deptTable, "dt", "dh");
  // MasterDetail.showHideEmpTable(deptTable);
}

MasterDetail.updateDeptHeaderAndDetails = function(event) 
{
  var deptTable = event.getSource();
  MasterDetail.updateEmpDetails(deptTable, "dt", "dh");
  MasterDetail.showHideEmpTable(deptTable);
}


/**
 * Updates the employee details for the selected employee in the detail table
 * @param event 
 */
MasterDetail.updateEmpHeader = function(event) 
{
  var empTable = event.getSource();
  MasterDetail.updateEmpDetails(empTable, "et", "ef");
}

MasterDetail.updateEmpDetails = function(sourceComp, srcIdPrefix, tarIdPrefix)
{
  var _EMP_DETAIL_NAMES = ['empno', 'empname', 'jobtitle', 'salary', 'manager', 'commission', 'hiredate', 'phone', 'email'];
  var empTable = sourceComp;
  var selectedRowKeys = empTable.getSelectedRowKeys();
  var rowkey;
  for (rowkey in selectedRowKeys)
    break;
     
  if (!rowkey) 
  {
    event.cancel();
    return;
  }
    
  for (var i = 0; i < _EMP_DETAIL_NAMES.length; i++)
  {
    var srcId = srcIdPrefix +_EMP_DETAIL_NAMES[i];
    var source = empTable.findComponent(srcId, rowkey);
    
    // this code assumes that the target and the source table are within the same container 
    // or the root.
    var tarId = tarIdPrefix + _EMP_DETAIL_NAMES[i];
    var parentContainer = empTable.getParent();
    var target = ((source != null) && parentContainer) ? parentContainer.findComponent(tarId) : null;
    
    if (source && target)
      target.setValue(source.getValue());
  }                    
}

/**
 * Controls visibility of child detail tables based on the master row selection
 */
MasterDetail.showHideEmpTable = function(deptTable)
{
  // get selected row
  var selectedRowKeys = deptTable.getSelectedRowKeys();
  var rowkey;
  for (rowkey in selectedRowKeys)
    break;
     
  if (!rowkey) 
  {
    event.cancel();
    return;
  }
  
  var rowIndex = deptTable.getRowIndex(rowkey); 
  MasterDetail._CURR_MASTER_ROWINDEX = rowIndex;
  
  // here again the wrapper container is assumed to be a sibling of the master table; so we use the 
  // common ancestor to find wrapper component
  var parentContainer = deptTable.getParent();
  var wrapperForTables = (parentContainer) ? parentContainer.findComponent("pglETWrap") : 
                                              deptTable.findComponent("pglETWrap");
  if (wrapperForTables)
  {
    wrapperForTables.visitChildren(MasterDetail.visitChildTableCallback, this, false);
  }
  MasterDetail._CURR_MASTER_ROWINDEX = "";
}

/**
 * Visit callback used to locate table components within the wrapper.
 */
MasterDetail.visitChildTableCallback = function(component)
{
  if (component instanceof AdfRichTable)
  {
    var masterRowIndex = component.getProperty("masterIndex");
    if (masterRowIndex == MasterDetail._CURR_MASTER_ROWINDEX)
    {
      component.setProperty("visible", true);
    }
    else 
    {
      component.setProperty("visible", false);
    }
  }
  
  return 1; // skip iterating over this component's children
}