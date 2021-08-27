/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/CustomToolTipCallbackSample.java /main/3 2009/04/28 13:01:23 hzhang Exp $ */

/* Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hzhang      01/08/09 - Creation
 */

package oracle.adfdemo.view.feature.rich.graph;

import oracle.dss.dataView.Attributes;
import oracle.dss.dataView.ComponentHandle;
import oracle.dss.dataView.DataComponentHandle;
import oracle.dss.graph.CustomToolTipCallback;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/CustomToolTipCallbackSample.java /main/3 2009/04/28 13:01:23 hzhang Exp $
 *  @author  hzhang  
 *  @since   release specific (what release of product did this appear in)
 */
public class CustomToolTipCallbackSample {
    
    private CustomToolTipCallback m_callback;
    public CustomToolTipCallback getCallback() {
        if(m_callback == null)
            m_callback = new MyCustomToolTipCallback();
        
        return m_callback;
    }
    
    public class MyCustomToolTipCallback implements CustomToolTipCallback { 
      private int m_counter = 0;

      public String getToolTipText(String defaultToolTipText, ComponentHandle handle) {
          if(handle instanceof DataComponentHandle) {
              StringBuilder tooltip = new StringBuilder("My Custom Tooltip #");
              // Vary the tooltip to show that customization is possible on an
              // object by object basis.
              tooltip.append(m_counter).append('\n');
              m_counter++;
              DataComponentHandle dHandle = (DataComponentHandle) handle;
              // Write out the row, column, and value
              int row = dHandle.getRow()+1;
              int col = dHandle.getColumn()+1;
              Object value = dHandle.getValue(DataComponentHandle.UNFORMATTED_VALUE);
              tooltip.append("Bar value is ").append(value.toString()).append('\n');
              tooltip.append("Row ").append(row).append(" Column ").append(col).append('\n');
              
              return tooltip.toString();
          }
          else
              return null;
      }
    }
}

