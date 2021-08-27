/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/nBox/NBoxHighlighting.java /bibeans_root/1 2014/04/10 19:01:14 jramanat Exp $ */

/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jramanat    04/10/14 - Creation
 */

package oracle.adfdemo.view.feature.rich.nBox;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.nBox.UINBox;

import oracle.adfdemo.view.feature.rich.nBox.data.TriangleData;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/nBox/NBoxHighlighting.java /bibeans_root/1 2014/04/10 19:01:14 jramanat Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class NBoxHighlighting {
  private CollectionModel model;
  private UINBox nBox;
  private String searchText;
  
  public CollectionModel getModel() {
    if (model == null) {
      model = ModelUtils.toCollectionModel(TriangleData.getData(50, 3, 3));
    }
    return model;
  }

  public void setNBox(UINBox nBox) {
    this.nBox = nBox;
  }

  public UINBox getNBox() {
    return nBox;
  }

  public void setSearchText(String searchText) {
    this.searchText = searchText == null ? null : searchText.trim();
  }

  public String getSearchText() {
    return searchText;
  }

  public void search(ActionEvent actionEvent) {
    if (searchText == null || searchText.length() == 0) {
      nBox.setHighlightedRowKeys(null);
    }
    else {
      RowKeySetImpl highlightedRowKeys = new RowKeySetImpl();
      highlightedRowKeys.setCollectionModel(getModel());
      int rowCount = nBox.getRowCount();
      for (int i = 0; i < rowCount; i++) {
        Map<String, Object> node = (Map<String, Object>)nBox.getRowData(i);
        String name = node.get("name").toString().toUpperCase();
        String job = node.get("job").toString().toUpperCase();
        String upperCaseSearch = searchText.toUpperCase();
        if (name.contains(upperCaseSearch) || job.contains(upperCaseSearch)) {
          highlightedRowKeys.add(i);
        }
      }
      nBox.setHighlightedRowKeys(highlightedRowKeys);
    }
  }
}
