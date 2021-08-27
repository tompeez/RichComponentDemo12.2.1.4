/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/nBox/NBoxAttributeGroups.java /bibeans_root/3 2015/11/02 08:00:58 tuagarwa Exp $ */

/* Copyright (c) 2014, 2015, Oracle and/or its affiliates. 
All rights reserved.*/

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

import java.util.ArrayList;
import java.util.List;

import oracle.adf.view.faces.bi.component.nBox.UINBox;

import oracle.adfdemo.view.feature.rich.nBox.data.TriangleData;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/nBox/NBoxAttributeGroups.java /bibeans_root/3 2015/11/02 08:00:58 tuagarwa Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class NBoxAttributeGroups {
  private CollectionModel model;
  private UINBox nBox;
  private boolean displayDepartment = false;
  private boolean displayRole = false;
  private boolean displayExp = false;
  private boolean displayLoc = false;
  private boolean groupDepartment = false;
  private boolean groupRole = false;
  private boolean groupExp = false;
  private boolean groupLoc = false;
  private String groupBehavior = "withinCell";
  
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

  public void setDisplayDepartment(boolean displayDepartment) {
    this.displayDepartment = displayDepartment;
    if (!displayDepartment) {
      setGroupDepartment(false);
    }
  }

  public boolean isDisplayDepartment() {
    return displayDepartment;
  }

  public void setDisplayRole(boolean displayRole) {
    this.displayRole = displayRole;
    if (!displayRole) {
      setGroupRole(false);
    }
  }

  public boolean isDisplayRole() {
    return displayRole;
  }

  public void setDisplayExp(boolean displayExp) {
    this.displayExp = displayExp;
    if (!displayExp) {
      setGroupExp(false);
    }
  }

  public boolean isDisplayExp() {
    return displayExp;
  }

  public void setDisplayLoc(boolean displayLoc) {
    this.displayLoc = displayLoc;
    if (!displayLoc) {
      setGroupLoc(false);
    }
  }

  public boolean isDisplayLoc() {
    return displayLoc;
  }
  
  public void setGroupDepartment(boolean groupDepartment) {
    this.groupDepartment = groupDepartment;
  }

  public boolean isGroupDepartment() {
    return groupDepartment;
  }

  public void setGroupRole(boolean groupRole) {
    this.groupRole = groupRole;
  }

  public boolean isGroupRole() {
    return groupRole;
  }
  
  public void setGroupExp(boolean groupExp) {
    this.groupExp = groupExp;
  }

  public boolean isGroupExp() {
    return groupExp;
  }

  public void setGroupLoc(boolean groupLoc) {
    this.groupLoc = groupLoc;
  }

  public boolean isGroupLoc() {
    return groupLoc;
  }
  
  public String[] getGroupBy() {
    List<String> groupBy = new ArrayList<String>();
    if (isGroupDepartment()) {
      groupBy.add("department");
    }
    if (isGroupRole()) {
      groupBy.add("role");
    }
    if (isGroupExp()) {
      groupBy.add("experience");
    }
    if (isGroupLoc()) {
      groupBy.add("location");
    }    
    return groupBy.size() > 0 ? (String[])groupBy.toArray(new String[groupBy.size()]): null;
  }

  public void setGroupBehavior(String groupBehavior) {
    this.groupBehavior = groupBehavior;
  }

  public String getGroupBehavior() {
    return groupBehavior;
  }
}
