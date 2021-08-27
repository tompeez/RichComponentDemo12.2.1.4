/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/timeline/model/UpdateableModel.java /bibeans_root/3 2011/07/13 15:39:02 ccchow Exp $ */

/* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ccchow      07/08/11 - Creation
 */

/**
 *  @version $Header: UpdateableModel.java 08-jul-2011.15:38:51 ccchow   Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.timeline.model; 

/**
 * APIs for an updateable model (CollectionModel or TreeModel) which supports
 * insert/update/delete operations.  For a TreeModel the insert/update/delete operations
 * are performed on each child collection.
 */
public interface UpdateableModel
{
  /**
   * Insert a row before the "current" row. If current rowKey is null, append the new row
   * to the end
   * @param rowData an object containing the data for the inserted row
   */
  public void insertRow(Object rowData);

  /**
   * Insert a row before the row indentified by rowKey.  If rowKey is null, append the new row
   * to the end
   * @param rowKey the new row will be inserted before the row indentified by rowKey
   * @param rowData an object containing the data for the inserted row
   * @return row key for the inserted row
   */
  public void insertRow(Object rowKey, Object rowData);

  /**
   * Delete the "current" row
   */
  public void deleteRow();

  /**
   * Delete the row identified by rowKey
   * @param rowKey the rowKey for the row to delete
   */
  public void deleteRow(Object rowKey);
  
  /**
   * Update the "current" row
   * @param rowData an object containing updated row data
   */
  public void updateRow(Object rowData);

  /**
   * Update the row identified by rowKey
   * @param rowKey the rowKey for the row to update
   * @param rowData an object containg updated row data
   */
  public void updateRow(Object rowKey, Object rowData);
}