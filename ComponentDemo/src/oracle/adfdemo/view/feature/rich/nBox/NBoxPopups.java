/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/nBox/NBoxPopups.java /bibeans_root/1 2016/03/01 16:33:39 adama Exp $ */

/* Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    adama       02/29/16 - Creation
 */

package oracle.adfdemo.view.feature.rich.nBox;

import oracle.adf.view.faces.bi.component.nBox.UINBox;

import oracle.adfdemo.view.feature.rich.nBox.data.TriangleData;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import org.apache.myfaces.trinidad.context.RequestContext;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/nBox/NBoxPopups.java /bibeans_root/1 2016/03/01 16:33:39 adama Exp $
 *  @author  adama
 *  @since   release specific (what release of product did this appear in)
 */

public class NBoxPopups {
  private CollectionModel model;
  private UINBox nBox;
  private String popupTrigger = "click";
  private String contextMenuAction = "";
  private RichOutputFormatted outputFormatted;
  private Object _popupSource;
  private Object _menuSource;

  
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
  
  public void setPopupTrigger(String popupTrigger) {
    this.popupTrigger = popupTrigger;
  }

  public String getPopupTrigger() {
    return popupTrigger;
  }
  
  public void setContextMenuAction(String contextMenuAction) {
    this.contextMenuAction = contextMenuAction;
  }

  public String getContextMenuAction() {
    return contextMenuAction;
  }
  
  public void setOutputFormatted(RichOutputFormatted outputFormatted) {
    this.outputFormatted = outputFormatted;
  }

  public RichOutputFormatted getOutputFormatted() {
    return outputFormatted;
  }
  
  public Object getPopupSource() {
      return _popupSource;
  }
  
  public void setPopupSource(Object o) {
      _popupSource = o;
  }

  public Object getMenuSource() {
      return _menuSource;
  }
  
  public void setMenuSource(Object o) {
      _menuSource = o;
  }
  
  /**
   * Called when a commandMenuItem is clicked.  Updates the outputText with information about the menu item clicked.
   * @param actionEvent
   */
  public void menuItemListener(ActionEvent actionEvent) {
    UIComponent component = actionEvent.getComponent();
    if (component instanceof RichCommandMenuItem) {
      RichCommandMenuItem cmi = (RichCommandMenuItem)component;

      // Set the text of the item into the status message
      this.contextMenuAction = _menuSource.toString()  + " - " + cmi.getText();

      // Update the status text component
      RequestContext.getCurrentInstance().addPartialTarget(this.outputFormatted);
    }
  }
}