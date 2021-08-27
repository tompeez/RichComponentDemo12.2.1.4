
/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/EmptyGraph.java /main/2 2011/11/08 15:23:16 ytang Exp $ */

/* Copyright (c) 2010, 2011, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ytang       04/29/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/EmptyGraph.java /main/1 2010/04/30 09:06:07 ytang Exp $
 *  @author  ytang   
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.graph;
import java.util.Collections;
import java.util.List;

public class EmptyGraph{
    public List getTestTable(){
        return Collections.emptyList();
    }
    
    private String _emptyText = "Custom Text";
    public String getEmptyText(){
        return _emptyText;
    }
    public void setEmptyText(String emptyText){
        _emptyText = emptyText;
    }
}