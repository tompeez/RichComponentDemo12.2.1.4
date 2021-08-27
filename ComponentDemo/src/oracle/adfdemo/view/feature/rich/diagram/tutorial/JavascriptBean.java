/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/tutorial/JavascriptBean.java /bibeans_root/2 2014/02/06 08:31:50 nbalatsk Exp $ */

/* Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    nbalatsk    12/17/13 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/diagram/tutorial/JavascriptBean.java /bibeans_root/2 2014/02/06 08:31:50 nbalatsk Exp $
 *  @author  nbalatsk
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.diagram.tutorial;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class JavascriptBean {
  String _sourceName="DemoVerticalLayout.js";
  String _fileContent="";
  int _rows = 0;

  public JavascriptBean() {
    _sourceName="DemoVerticalLayout.js";
    setSource();
  }

  public String getSource() {
    return _fileContent;
  }

  public int getRows() {
    return _rows;
  }
  
  public void disclosureListener(DisclosureEvent disclosureEvent) {
    if (disclosureEvent.isExpanded()) {
      String tabId = ((RichShowDetailItem)disclosureEvent.getSource()).getId();
      if ("step2".equals(tabId))
        _sourceName="DemoVerticalLayoutSortedNodes.js";
      else if ("step3".equals(tabId))
        _sourceName="DemoVerticalLayoutWithLinks.js"; 
      else if ("step4".equals(tabId))
        _sourceName="DemoVerticalLayoutWithSideLinks.js";
      else if ("step5".equals(tabId))
        _sourceName="DemoContainersLayout.js";        
      else if ("step6".equals(tabId))
        _sourceName="DemoCrossContainerLinksLayout.js";                
      else
        _sourceName="DemoVerticalLayout.js";
    } 
    setSource();
  }
  
  private void setSource() {
    String fileContent = "";
    int rows = 0;
    try {
      BufferedReader reader =
        new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/jsLibs/diagram/tutorialLayouts/" + _sourceName)));
      StringBuilder contents = new StringBuilder();

      String line = null;
      while ((line = reader.readLine()) != null) {
        contents.append(line);
        contents.append("\n");
        rows++;
      }
      fileContent = contents.toString();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    _fileContent = fileContent;
    _rows = rows;
  }

}


