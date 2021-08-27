/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/AnimationScatter.java /main/2 2009/03/19 21:37:42 teclee Exp $ */

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

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/AnimationScatter.java /main/2 2009/03/19 21:37:42 teclee Exp $
 *  @author  hzhang  
 *  @since   release specific (what release of product did this appear in)
 */
public class AnimationScatter extends AnimationBase {
    private static final int INITIAL_SERIES = 2;
    private static final int INITIAL_GROUPS = 6;
    private static final int GROUPS_MULTIPLE = 2;
    private static final int RANDOM_SEED = 2;
    
    public AnimationScatter() {
        super(INITIAL_SERIES, INITIAL_GROUPS, GROUPS_MULTIPLE, RANDOM_SEED);
    }
}