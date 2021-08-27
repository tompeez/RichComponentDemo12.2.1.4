/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/GanttConstants.java /main/4 2010/06/18 */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ytang    06/18/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/GanttConstants.java /main/1 2010/06/22 11:44:09 ytang Exp $
 *  @author  ytang
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.gantt;

/**
 * This class is used for the demo directly
 * Defines all constants used in the Gantt.
 */
public interface GanttConstants
{
    // Types of tasks
    // Developers should use the style constants defined in the Gantt component classes.
    /**
     * Task type for a summary task
     */
    public static final String SUMMARY = "Summary";
    /**
     * Task type for a normal task.
     */
    public static final String NORMAL = "Normal";
    /**
     * Task type for a milestone.
     */
    public static final String MILESTONE = "Milestone";

    // Alignment constants
    // Developers should use the placement constants defined in the Gantt component classes.
    /**
     * Left alignment for icons and labels in the task bar.
     */
    public static final int LEFT = 0;
    /**
     * Right alignment for icons and labels in the task bar.
     */
    public static final int RIGHT = 1;
    
    // Types of dependency
    /**
     * Dependency type: Finish-to-Start.
     */
    public static final String FINISH_START = "fs";
}
