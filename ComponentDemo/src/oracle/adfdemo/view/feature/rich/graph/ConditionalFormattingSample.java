/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/ConditionalFormattingSample.java /main/5 2009/08/05 14:11:45 srkalyan Exp $ */

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

import java.awt.Color;

import java.util.Vector;

import oracle.adf.view.faces.bi.component.graph.UIGraph;

import oracle.dss.graph.CommonGraph;
import oracle.dss.graph.GraphStyleManager;
import oracle.dss.graph.managers.GraphStyle;
import oracle.dss.rules.DiscriminatorRule;
import oracle.dss.rules.RuleBundle;
import oracle.dss.rules.discriminator.NumberValueDiscriminator;
import oracle.dss.rules.discriminator.ValueDiscriminator;


/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/ConditionalFormattingSample.java /main/5 2009/08/05 14:11:45 srkalyan Exp $
 *  @author  hzhang
 *  @since   release specific (what release of product did this appear in)
 */
public class ConditionalFormattingSample {
    
    public Vector getValueRuleBundle() {
        RuleBundle bundle = new RuleBundle();
        //create rule for fill color yellow if value < 20
        DiscriminatorRule dr = new DiscriminatorRule();
        dr.setDiscriminator(new NumberValueDiscriminator(20.0, ValueDiscriminator.LT));
        GraphStyle gs = new GraphStyle();
        gs.setFillColor(Color.yellow); 
        dr.setFixedMergeable(gs);
        bundle.addRule(dr);
        //create rule for fill color green if value > 70
        dr = new DiscriminatorRule();
        dr.setDiscriminator(new NumberValueDiscriminator(70.0, ValueDiscriminator.GT));
        gs = new GraphStyle();
        gs.setFillColor(Color.green);
        dr.setFixedMergeable(gs);
        bundle.addRule(dr);
        //put bundle in vector and set on graph
        Vector styleVector = new Vector();
        styleVector.addElement(bundle);
        return styleVector;
    }
}

