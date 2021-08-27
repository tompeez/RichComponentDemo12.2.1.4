/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/TimeBlackoutSample.java /main/2 2012/04/06 12:53:45 baoyu Exp $ */

/* Copyright (c) 2009, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    mguirgui      09/15/10 - Creation
 */

package oracle.adfdemo.view.feature.rich.graph;


import java.sql.Date;
import java.util.List;

import java.util.ArrayList;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/TimeBlackoutSample.java /main/2 2012/04/06 12:53:45 baoyu Exp $
 *  @author  mguirgui
 *  @since   release specific (what release of product did this appear in)
 */
public class TimeBlackoutSample {

    /**
     * @return
     */
    public List getTimeData(){
        List list = new ArrayList();
        
        list.add(new Object[] {Date.valueOf("1990-01-01"),"series1",new Double(20)});
        list.add(new Object[] {Date.valueOf("1991-01-01"),"series1",new Double(50)});
        list.add(new Object[] {Date.valueOf("1992-01-01"),"series1",new Double(40)});
        list.add(new Object[] {Date.valueOf("1993-01-01"),"series1",new Double(30)});
        list.add(new Object[] {Date.valueOf("1994-01-01"),"series1",null});
        list.add(new Object[] {Date.valueOf("1995-01-01"),"series1",new Double(30)});
        list.add(new Object[] {Date.valueOf("1996-01-01"),"series1",new Double(20)});
        list.add(new Object[] {Date.valueOf("1997-01-01"),"series1",new Double(20)});
        list.add(new Object[] {Date.valueOf("1998-01-01"),"series1",new Double(30)});
        
        list.add(new Object[] {Date.valueOf("1990-01-02"),"series2",new Double(10)});
        list.add(new Object[] {Date.valueOf("1991-02-01"),"series2",new Double(30)});
        list.add(new Object[] {Date.valueOf("1992-03-01"),"series2",new Double(20)});
        list.add(new Object[] {Date.valueOf("1993-04-01"),"series2",null});
        list.add(new Object[] {Date.valueOf("1994-05-01"),"series2",new Double(40)});
        list.add(new Object[] {Date.valueOf("1995-05-01"),"series2",new Double(40)});
        list.add(new Object[] {Date.valueOf("1996-06-01"),"series2",new Double(50)});
        list.add(new Object[] {Date.valueOf("1997-07-01"),"series2",new Double(50)});
        list.add(new Object[] {Date.valueOf("1998-08-01"),"series2",new Double(30)});
        return list;
    }

    /**
     * @return
     */
    public List getBrokenTimeData(){
        List list = new ArrayList();
        list.add(new Object[] {Date.valueOf("1990-01-01"),"series1",new Double(20)});
        list.add(new Object[] {Date.valueOf("1991-01-01"),"series1",new Double(60)});
        list.add(new Object[] {Date.valueOf("1992-01-01"),"series1",new Double(40)});
        list.add(new Object[] {Date.valueOf("1993-01-01"),"series1",new Double(30)});
        list.add(new Object[] {Date.valueOf("1994-01-01"),"series1",null});
        list.add(new Object[] {Date.valueOf("1995-01-01"),"series1",new Double(30)});
        list.add(new Object[] {Date.valueOf("1996-01-01"),"series1",new Double(20)});
        list.add(new Object[] {Date.valueOf("1997-01-01"),"series1",new Double(20)});
        list.add(new Object[] {Date.valueOf("1998-01-01"),"series1",new Double(30)});
        
        list.add(new Object[] {Date.valueOf("1990-01-01"),"series2",new Double(10)});
        list.add(new Object[] {Date.valueOf("1991-01-01"),"series2",new Double(30)});
        list.add(new Object[] {Date.valueOf("1992-01-01"),"series2",new Double(20)});
        list.add(new Object[] {Date.valueOf("1993-01-01"),"series2",new Double(40)});
        list.add(new Object[] {Date.valueOf("1994-01-01"),"series2",new Double(30)});
        list.add(new Object[] {Date.valueOf("1995-01-01"),"series2",new Double(40)});
        list.add(new Object[] {Date.valueOf("1996-01-01"),"series2",new Double(50)});
        list.add(new Object[] {Date.valueOf("1997-01-01"),"series2",new Double(50)});
        list.add(new Object[] {Date.valueOf("1998-01-01"),"series2",new Double(30)});
        return list;
    }

    /**
     * @return
     */
    public List getMixedTimeData(){  
        ArrayList list = new ArrayList();   
        list.add(new Object[] {"group1","series1",Date.valueOf("2007-09-01")});   
        list.add(new Object[] {"group11","series1",new Double(20)});   
        list.add(new Object[] {"group2","series1",Date.valueOf("2007-09-08")});   
        list.add(new Object[] {"group22","series1",new Double(30)});   
        list.add(new Object[] {"group3","series1",Date.valueOf("2007-11-01")});   
        list.add(new Object[] {"group33","series1",new Double(20)});   
        list.add(new Object[] {"group4","series1",Date.valueOf("2007-12-01")});   
        list.add(new Object[] {"group44","series1",new Double(30)});   
        return list;   
    }   
         

public Date getStartDate(){
return Date.valueOf("1993-01-01");
}

public Date getEndDate(){
return Date.valueOf("1995-01-01");
}

}

