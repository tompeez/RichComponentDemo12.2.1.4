/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/SampleModelFactory.java /main/15 2016/03/23 12:11:56 kiancu Exp $ */

/* Copyright (c) 2008, 2016, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    kiancu      01/21/10 - Add Generic Converter Demo
    hbroek      07/22/09 - Remove label alignment from task and add set seom
                           additional labels on the Gantt test model
    hbroek      08/28/08 - Add Gantt samples
    kiancu      07/22/08 - remove summary task from taskbarformat model
    kiancu      07/17/08 - add ProjectGanttTaskbarFormat
    ccchow      06/17/08 - model factory for samples
    ccchow      06/17/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/data/SampleModelFactory.java /main/15 2016/03/23 12:11:56 kiancu Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt.data;

import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;

import oracle.adfdemo.view.feature.rich.gantt.GanttConstants;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.TreeModel;

public class SampleModelFactory
{
    public static TreeModel getProjectGanttModel()
    {
        ArrayList _tasks = new ArrayList<Task>(10);
        Task _task1 = new Task("t1", "Project 1", null, null, parseDate("05/15/2008"), parseDate("08/20/2008"), GanttConstants.SUMMARY);
        _task1.setLabel(_task1.getTaskName());
        Task _task2 = new Task("t2", "Task A-P1", "00001", "ccchow", parseDate("05/15/2008"), parseDate("06/20/2008"), GanttConstants.NORMAL);
        _task2.setLabel(_task2.getTaskName());
        Task _task3 = new Task("t3", "Task C-P1", "00002", "kmachos", parseDate("06/27/2008"), parseDate("07/15/2008"), GanttConstants.NORMAL);
        _task3.setPercentComplete(75);
        Task _task4 = new Task("t4", "Task D-P1", "00003", "mstarets", parseDate("08/20/2008"), parseDate("09/20/2008"), GanttConstants.NORMAL);
        Task _task5 = new Task("t5", "Milestone 1", "00003", "mstarets", parseDate("06/20/2008"), null, GanttConstants.MILESTONE);
        _task5.setLabel("Milestone 1");
        Task _task6 = new Task("t6", "Variance E-P1", "00001", "ccchow", parseDate("07/20/2008"), parseDate("08/10/2008"), GanttConstants.NORMAL);
        _task6.setActualStartTime(parseDate("07/27/2008"));
        _task6.setActualEndTime(parseDate("08/17/2008"));
        
        _task1.addSubtask(_task2);
        _task1.addSubtask(_task3);
        _task1.addSubtask(_task4);
        _task1.addSubtask(_task5);
        _task1.addSubtask(_task6);
        
        Task _task7 = new Task("t7", "Project 3", null, null, parseDate("04/20/2008"), parseDate("07/20/2008"), GanttConstants.SUMMARY);
        Task _task8 = new Task("t8", "Task A-P3", "00003", "mstarets", parseDate("04/10/2008"), parseDate("06/20/2008"), GanttConstants.NORMAL);
        Task _task9 = new Task("t9", "Task B-P3", "00004", "hkosuru", parseDate("06/28/2008"), parseDate("07/20/2008"), GanttConstants.NORMAL);
        Task _task10 = new Task("t10", "Split Task", "00001", "ccchow", parseDate("05/20/2008"), parseDate("06/28/2008"), GanttConstants.NORMAL);
        _task10.addSplits(parseDate("05/20/2008"), parseDate("06/01/2008"));
        _task10.addSplits(parseDate("06/15/2008"), parseDate("06/28/2008"));
        
        Task _task11 = new Task("t11", "Milestone 2", "00011", "mstarets", null, parseDate("06/20/2008"), GanttConstants.MILESTONE);
        _task11.setLabel("Milestone 2");
        
        

        _task7.addSubtask(_task8);
        _task7.addSubtask(_task9);
        _task7.addSubtask(_task10);
        _task7.addSubtask(_task11);
        
        _tasks.add(_task1);
        _tasks.add(_task7);
        
        addRelationship(_task2, _task9);
        addRelationship(_task8, _task4);

        return new SampleProjectModel(_tasks, "subTasks");
    }
    
    public static TreeModel getProjectGanttLabelIconModel()
    {
        ArrayList _tasks = new ArrayList<Task>(10);
        String _icon1 = "/adf/images/info.png";
        String _icon2 = "/adf/images/warning.png";
        String _icon3 = "/adf/images/error.png";
        Task _task1 = new Task("t1", "Project 1", null, null, parseDate("05/15/2008"), parseDate("08/20/2008"), GanttConstants.SUMMARY);
        _task1.setLabel(_task1.getTaskName());
        _task1.setIcon1(_icon1);
        _task1.setIcon2(_icon2);
        _task1.setIcon3(_icon3);
        _task1.setIconAlign(-1);
        Task _task2 = new Task("t2", "Task A-P1", "00001", "ccchow", parseDate("05/15/2008"), parseDate("06/20/2008"), GanttConstants.NORMAL);
        _task2.setLabel(_task2.getTaskName());
        _task2.setIcon1(_icon1);
        _task2.setIcon2(_icon2);
        _task2.setIcon3(_icon3);
        _task2.setIconAlign(-1);
        Task _task3 = new Task("t3", "Task C-P1", "00002", "kmachos", parseDate("06/27/2008"), parseDate("07/15/2008"), GanttConstants.NORMAL);
        _task3.setPercentComplete(75);
        _task3.setLabel(_task3.getTaskName());
        _task3.setIcon1(_icon1);
        _task3.setIcon2(_icon2);
        _task3.setIcon3(_icon3);
        _task3.setIconAlign(-1);
        Task _task4 = new Task("t4", "Task D-P1", "00003", "mstarets", parseDate("08/20/2008"), parseDate("09/20/2008"), GanttConstants.NORMAL);
        _task4.setLabel(_task4.getTaskName());
        _task4.setIcon1(_icon1);
        _task4.setIcon2(_icon2);
        _task4.setIcon3(_icon3);
        _task4.setIconAlign(-1);
        Task _task5 = new Task("t5", "Milestone 1", "00003", "mstarets", parseDate("06/20/2008"), null, GanttConstants.MILESTONE);
        _task5.setLabel("Milestone 1");
        _task5.setIcon1(_icon1);
        _task5.setIcon2(_icon2);
        _task5.setIcon3(_icon3);
        _task5.setIconAlign(-1);
        Task _task6 = new Task("t6", "Variance E-P1", "00001", "ccchow", parseDate("07/20/2008"), parseDate("08/10/2008"), GanttConstants.NORMAL);
        _task6.setActualStartTime(parseDate("07/27/2008"));
        _task6.setActualEndTime(parseDate("08/17/2008"));
        _task6.setLabel(_task6.getTaskName());
        _task6.setIcon1(_icon1);
        _task6.setIcon2(_icon2);
        _task6.setIcon3(_icon3);
        _task6.setIconAlign(-1);
        
        _task1.addSubtask(_task2);
        _task1.addSubtask(_task3);
        _task1.addSubtask(_task4);
        _task1.addSubtask(_task5);
        _task1.addSubtask(_task6);
        
        Task _task7 = new Task("t7", "Project 3", null, null, parseDate("04/20/2008"), parseDate("07/20/2008"), GanttConstants.SUMMARY);
        _task7.setLabel(_task7.getTaskName());
        _task7.setIcon1(_icon1);
        _task7.setIcon2(_icon2);
        _task7.setIcon3(_icon3);
        _task7.setIconAlign(-1);
        Task _task8 = new Task("t8", "Task A-P3", "00003", "mstarets", parseDate("04/10/2008"), parseDate("06/20/2008"), GanttConstants.NORMAL);
        _task8.setLabel(_task8.getTaskName());
        _task8.setIcon1(_icon1);
        _task8.setIcon2(_icon2);
        _task8.setIcon3(_icon3);
        _task8.setIconAlign(-1);
        Task _task9 = new Task("t9", "Task B-P3", "00004", "hkosuru", parseDate("06/28/2008"), parseDate("07/20/2008"), GanttConstants.NORMAL);
        _task9.setLabel(_task9.getTaskName());
        _task9.setIcon1(_icon1);
        _task9.setIcon2(_icon2);
        _task9.setIcon3(_icon3);
        _task9.setIconAlign(-1);
        Task _task10 = new Task("t10", "Split Task", "00001", "ccchow", parseDate("05/20/2008"), parseDate("06/28/2008"), GanttConstants.NORMAL);
        _task10.addSplits(parseDate("05/20/2008"), parseDate("06/01/2008"));
        _task10.addSplits(parseDate("06/15/2008"), parseDate("06/28/2008"));
        _task10.setLabel(_task10.getTaskName());
        _task10.setIcon1(_icon1);
        _task10.setIcon2(_icon2);
        _task10.setIcon3(_icon3);
        _task10.setIconAlign(-1);

        _task7.addSubtask(_task8);
        _task7.addSubtask(_task9);
        _task7.addSubtask(_task10);
        
        _tasks.add(_task1);
        _tasks.add(_task7);
        
        addRelationship(_task2, _task9);
        addRelationship(_task8, _task4);

        return new SampleProjectModel(_tasks, "subTasks");
    }

    public static TreeModel getProjectGanttModelCalendar()
    {
        ArrayList _tasks = new ArrayList<Task>(10);
        Task _task1 = new CalendarTask("t1", "Project 1", null, null, parseDateCalendar("05/15/2008"), parseDateCalendar("08/20/2008"), GanttConstants.SUMMARY);
        _task1.setLabel(_task1.getTaskName());
        Task _task2 = new CalendarTask("t2", "Task A-P1", "00001", "ccchow", parseDateCalendar("05/15/2008"), parseDateCalendar("06/20/2008"), GanttConstants.NORMAL);
        _task2.setLabel(_task2.getTaskName());
        Task _task3 = new Task("t3", "Task C-P1", "00002", "kmachos", parseDate("06/27/2008"), parseDate("07/15/2008"), GanttConstants.NORMAL);
        _task3.setPercentComplete(75);
        Task _task4 = new Task("t4", "Task D-P1", "00003", "mstarets", parseDate("08/20/2008"), parseDate("09/20/2008"), GanttConstants.NORMAL);
        Task _task5 = new Task("t5", "Milestone 1", "00003", "mstarets", parseDate("06/20/2008"), null, GanttConstants.MILESTONE);
        _task5.setLabel("Milestone 1");
        Task _task6 = new Task("t6", "Variance E-P1", "00001", "ccchow", parseDate("07/20/2008"), parseDate("08/10/2008"), GanttConstants.NORMAL);
        _task6.setActualStartTime(parseDate("07/27/2008"));
        _task6.setActualEndTime(parseDate("08/17/2008"));
        
        _task1.addSubtask(_task2);
        _task1.addSubtask(_task3);
        _task1.addSubtask(_task4);
        _task1.addSubtask(_task5);
        _task1.addSubtask(_task6);
        
        Task _task7 = new Task("t7", "Project 3", null, null, parseDate("04/20/2008"), parseDate("07/20/2008"), GanttConstants.SUMMARY);
        Task _task8 = new Task("t8", "Task A-P3", "00003", "mstarets", parseDate("04/10/2008"), parseDate("06/20/2008"), GanttConstants.NORMAL);
        Task _task9 = new Task("t9", "Task B-P3", "00004", "hkosuru", parseDate("06/28/2008"), parseDate("07/20/2008"), GanttConstants.NORMAL);
        Task _task10 = new Task("t10", "Split Task", "00001", "ccchow", parseDate("05/20/2008"), parseDate("06/28/2008"), GanttConstants.NORMAL);
        _task10.addSplits(parseDate("05/20/2008"), parseDate("06/01/2008"));
        _task10.addSplits(parseDate("06/15/2008"), parseDate("06/28/2008"));

        _task7.addSubtask(_task8);
        _task7.addSubtask(_task9);
        _task7.addSubtask(_task10);
        
        _tasks.add(_task1);
        _tasks.add(_task7);
        
        addRelationship(_task2, _task9);
        addRelationship(_task8, _task4);

        return new SampleProjectModel(_tasks, "subTasks");
    }
    
    public static TreeModel getProjectGanttModelEditableTasks()
    {
        ArrayList _tasks = new ArrayList<Task>(10);
        Task _task1 = new Task("t1", "Project 1", null, null, parseDate("05/15/2008"), parseDate("08/20/2008"), GanttConstants.SUMMARY);
        _task1.setLabel(_task1.getTaskName());
        Task _task2 = new Task("t2", "Task A-P1", "00001", "ccchow", parseDate("05/15/2008"), parseDate("06/20/2008"), GanttConstants.NORMAL, "none");
        _task2.setLabel(_task2.getTaskName());
        Task _task3 = new Task("t3", "Task C-P1", "00002", "kmachos", parseDate("06/27/2008"), parseDate("07/15/2008"), GanttConstants.NORMAL, "none");
        _task3.setPercentComplete(75);
        Task _task4 = new Task("t4", "Task D-P1", "00003", "mstarets", parseDate("08/20/2008"), parseDate("09/20/2008"), GanttConstants.NORMAL, "none");
        Task _task5 = new Task("t5", "Milestone 1", "00003", "mstarets", parseDate("06/20/2008"), null, GanttConstants.MILESTONE);
        _task5.setLabel("Milestone 1");
        Task _task6 = new Task("t6", "Variance E-P1", "00001", "ccchow", parseDate("07/20/2008"), parseDate("08/10/2008"), GanttConstants.NORMAL, "none");
        _task6.setActualStartTime(parseDate("07/27/2008"));
        _task6.setActualEndTime(parseDate("08/17/2008"));
        
        _task1.addSubtask(_task2);
        _task1.addSubtask(_task3);
        _task1.addSubtask(_task4);
        _task1.addSubtask(_task5);
        _task1.addSubtask(_task6);
        
        Task _task7 = new Task("t7", "Project 3", null, null, parseDate("04/20/2008"), parseDate("07/20/2008"), GanttConstants.SUMMARY);
        Task _task8 = new Task("t8", "Task A-P3", "00003", "mstarets", parseDate("04/10/2008"), parseDate("06/20/2008"), GanttConstants.NORMAL);
        Task _task9 = new Task("t9", "Task B-P3", "00004", "hkosuru", parseDate("06/28/2008"), parseDate("07/20/2008"), GanttConstants.NORMAL);
        Task _task10 = new Task("t10", "Split Task", "00001", "ccchow", parseDate("05/20/2008"), parseDate("06/28/2008"), GanttConstants.NORMAL);
        _task10.addSplits(parseDate("05/20/2008"), parseDate("06/01/2008"));
        _task10.addSplits(parseDate("06/15/2008"), parseDate("06/28/2008"));

        _task7.addSubtask(_task8);
        _task7.addSubtask(_task9);
        _task7.addSubtask(_task10);
        
        _tasks.add(_task1);
        _tasks.add(_task7);
        
        addRelationship(_task2, _task9);
        addRelationship(_task8, _task4);

        return new SampleProjectModel(_tasks, "subTasks");
    }

    public static TreeModel getSchedulingGanttModel()
    {
        Employee _r1 = new Employee("r1", "David Schmidt", "Accounting", "06:00", "15:00", null);
        Task _t11 = new Task("t11", "Hibbings West", _r1.getResourceId(), _r1.getResourceName(), parseDate("12/21/2006 14:00"), parseDate("12/21/2006 15:30"), "blue");
        _t11.setLabel(_t11.getTaskName());
        _r1.addTask(_t11);
        Task _t12 = new Task("t12", "Hibbings West", _r1.getResourceId(), _r1.getResourceName(), parseDate("12/22/2006 10:00"), parseDate("12/22/2006 14:30"), "blue");
        _t12.setLabel(_t11.getTaskName());
        _r1.addTask(_t12);

        Employee _r2 = new Employee("r2", "Glen Abboline", "Accounting", "07:00", "16:00", null);
        Task _t21 = new Task("t21", "Cymer Inc.", _r2.getResourceId(), _r2.getResourceName(), parseDate("12/21/2006 09:00"), parseDate("12/21/2006 10:30"), "gold");
        _t21.setLabel(_t21.getTaskName());
        _t21.setStartupTime(30);
        _r2.addTask(_t21);
        Task _t22 = new Task("t22", "Cymer Inc.", _r2.getResourceId(), _r2.getResourceName(), parseDate("12/22/2006 08:00"), parseDate("12/22/2006 11:30"), "gold");
        _t22.setLabel(_t22.getTaskName());
        _t22.setStartupTime(30);
        _r2.addTask(_t22);
        Task _t23 = new Task("t23", "Cymer Inc.", _r2.getResourceId(), _r2.getResourceName(), parseDate("12/22/2006 14:00"), parseDate("12/22/2006 16:30"), "gold");
        _t23.setLabel(_t23.getTaskName());
        _r2.addTask(_t23);

        Employee _r3 = new Employee("r3", "Lingle Adam", "Consulting", "14:00", "16:00", null);
        Task _t31 = new Task("t31", "Hibbings West", _r3.getResourceId(), _r3.getResourceName(), parseDate("12/21/2006 10:30"), parseDate("12/21/2006 11:15"), "plum");
        _t31.setLabel(_t31.getTaskName());
        _t31.setStartupTime(20);
        Task _t32 = new Task("t32", "Hibbings West", _r3.getResourceId(), _r3.getResourceName(), parseDate("12/21/2006 12:30"), parseDate("12/21/2006 13:30"), "red");
        _t32.setLabel(_t32.getTaskName());
        _t32.setStartupTime(20);
        _r3.addTask(_t31);
        _r3.addTask(_t32);
        Task _t33 = new Task("t33", "Hibbings West", _r3.getResourceId(), _r3.getResourceName(), parseDate("12/22/2006 9:30"), parseDate("12/22/2006 11:15"), "plum");
        _t33.setLabel(_t33.getTaskName());
        _t33.setStartupTime(15);
        Task _t34 = new Task("t34", "Hibbings West", _r3.getResourceId(), _r3.getResourceName(), parseDate("12/22/2006 14:30"), parseDate("12/22/2006 17:30"), "red");
        _t34.setLabel(_t34.getTaskName());
        _r3.addTask(_t33);
        _r3.addTask(_t34);

        Employee _r4 = new Employee("r4", "Joe Block", "Consulting", "06:00", "15:00", null);
        Task _t41 = new Task("t41", "Harwell", _r4.getResourceId(), _r4.getResourceName(), parseDate("12/21/2006 09:30"), parseDate("12/21/2006 10:30"), "teal");
        _t41.setLabel(_t41.getTaskName());
        Task _t42 = new Task("t42", "Service.com", _r4.getResourceId(), _r4.getResourceName(), parseDate("12/21/2006 15:00"), parseDate("12/21/2006 16:30"), "indigo");
        _t42.setLabel(_t42.getTaskName());
        _t42.setStartupTime(15);
        _r4.addTask(_t41);
        _r4.addTask(_t42);
        Task _t43 = new Task("t43", "Harwell", _r4.getResourceId(), _r4.getResourceName(), parseDate("12/22/2006 11:30"), parseDate("12/22/2006 12:30"), "teal");
        _t43.setLabel(_t43.getTaskName());
        Task _t44 = new Task("t44", "Service.com", _r4.getResourceId(), _r4.getResourceName(), parseDate("12/22/2006 15:30"), parseDate("12/22/2006 16:30"), "indigo");
        _t44.setLabel(_t44.getTaskName());
        _t43.setStartupTime(15);
        _r4.addTask(_t43);
        _r4.addTask(_t44);

        Employee _r5 = new Employee("r5", "Stacey Edwards", "Support", "06:00", "15:00", null);
        Task _t51 = new Task("t51", "Hewitt Pacific", _r5.getResourceId(), _r5.getResourceName(), parseDate("12/21/2006 13:00"), parseDate("12/21/2006 14:30"), "green");
        _t51.setLabel(_t51.getTaskName());
        _r5.addTask(_t51);
        Task _t52 = new Task("t52", "Hewitt Pacific", _r5.getResourceId(), _r5.getResourceName(), parseDate("12/22/2006 13:00"), parseDate("12/22/2006 15:30"), "green");
        _t52.setLabel(_t52.getTaskName());
        Task _t53 = new Task("t53", "Hewitt Pacific", _r5.getResourceId(), _r5.getResourceName(), parseDate("12/22/2006 16:00"), parseDate("12/22/2006 17:30"), "green");
        _t53.setLabel(_t53.getTaskName());
        _t53.setStartupTime(30);
        _r5.addTask(_t52);
        _r5.addTask(_t53);

        Employee _r6 = new Employee("r6", "Jimmy Jones", "Support", "06:00", "15:00", null);
        Task _t61 = new Task("t61", "Harwell", _r6.getResourceId(), _r6.getResourceName(), parseDate("12/21/2006 10:00"), parseDate("12/21/2006 11:00"), "orange");
        _t61.setLabel(_t61.getTaskName());
        _r6.addTask(_t61);

        Employee _r7 = new Employee("r7", "Dominique Monstratelli", "Technical Staff", "06:00", "15:00", null);
        Task _t71 = new Task("t71", "State Machine", _r7.getResourceId(), _r7.getResourceName(), parseDate("12/21/2006 13:00"), parseDate("12/21/2006 15:30"), "blue");
        _t71.setLabel(_t71.getTaskName());
        Task _t72 = new Task("t72", "Johnson", _r7.getResourceId(), _r7.getResourceName(), parseDate("12/21/2006 15:00"), parseDate("12/21/2006 16:30"), "indigo");
        _t72.setLabel(_t72.getTaskName());
        _r7.addTask(_t71);
        _r7.addTask(_t72);
        Task _t73 = new Task("t73", "State Machine", _r7.getResourceId(), _r7.getResourceName(), parseDate("12/22/2006 10:00"), parseDate("12/22/2006 11:30"), "lavender");
        _t73.setLabel(_t73.getTaskName());
        Task _t74 = new Task("t74", "Johnson", _r7.getResourceId(), _r7.getResourceName(), parseDate("12/22/2006 15:00"), parseDate("12/22/2006 16:30"), "blue");
        _t74.setLabel(_t74.getTaskName());
        _r7.addTask(_t73);
        _r7.addTask(_t74);

        Employee _r8 = new Employee("r8", "Sue Morgan", "Technical Staff", "06:00", "15:00", null);
        Task _t81 = new Task("t81", "Hewitt Pacific", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/21/2006 11:15"), parseDate("12/21/2006 12:30"), "gold");
        _t81.setLabel(_t81.getTaskName());
        Task _t82 = new Task("t82", "Hewitt Pacific", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/21/2006 13:30"), parseDate("12/21/2006 15:00"), "plum");
        _t82.setLabel(_t82.getTaskName());
        _t82.setStartupTime(15);
        Task _t83 = new Task("t83", "Johnson", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/21/2006 15:15"), parseDate("12/21/2006 16:00"), "red");
        _t83.setLabel(_t83.getTaskName());
        _r8.addTask(_t81);
        _r8.addTask(_t82);
        _r8.addTask(_t83);
        Task _t84 = new Task("t84", "Johnson", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/22/2006 14:15"), parseDate("12/22/2006 16:00"), "lime");
        _t84.setLabel(_t84.getTaskName());
        _t84.setStartupTime(30);
        _r8.addTask(_t84);

        ArrayList _resources = new ArrayList(10);        
        _resources.add(_r1);
        _resources.add(_r2);
        _resources.add(_r3);
        _resources.add(_r4);
        _resources.add(_r5);
        _resources.add(_r6);
        _resources.add(_r7);
        _resources.add(_r8);

        return ModelUtils.toTreeModel(_resources);
    }
    
    public static TreeModel getSchedulingGanttTaskbarFormatModel()
    {
        Employee _r1 = new Employee("r1", "David Schmidt", "Accounting", "06:00", "15:00", null);
        Task _t11 = new Task("t11", "Hibbings West", _r1.getResourceId(), _r1.getResourceName(), parseDate("12/21/2006 14:00"), parseDate("12/21/2006 15:30"), "blue");
        _t11.setLabel(_t11.getTaskName());
        _r1.addTask(_t11);
        Task _t12 = new Task("t12", "Hibbings West", _r1.getResourceId(), _r1.getResourceName(), parseDate("12/22/2006 10:00"), parseDate("12/22/2006 14:30"), "blue");
        _t12.setLabel(_t11.getTaskName());
        _r1.addTask(_t12);

        Employee _r2 = new Employee("r2", "Glen Abboline", "Accounting", "07:00", "16:00", null);
        Task _t21 = new Task("t21", "Cymer Inc.", _r2.getResourceId(), _r2.getResourceName(), parseDate("12/21/2006 09:00"), parseDate("12/21/2006 10:30"), "gold");
        _t21.setLabel(_t21.getTaskName());
        _t21.setStartupTime(30);
        _r2.addTask(_t21);
        Task _t22 = new Task("t22", "Cymer Inc.", _r2.getResourceId(), _r2.getResourceName(), parseDate("12/22/2006 08:00"), parseDate("12/22/2006 11:30"), "gold");
        _t22.setLabel(_t22.getTaskName());
        _t22.setStartupTime(30);
        _r2.addTask(_t22);
        Task _t23 = new Task("t23", "Cymer Inc.", _r2.getResourceId(), _r2.getResourceName(), parseDate("12/22/2006 14:00"), parseDate("12/22/2006 16:30"), "gold");
        _t23.setLabel(_t23.getTaskName());
        _r2.addTask(_t23);

        Employee _r3 = new Employee("r3", "Lingle Adam", "Consulting", "14:00", "16:00", null);
        Task _t31 = new Task("t31", "Hibbings West", _r3.getResourceId(), _r3.getResourceName(), parseDate("12/21/2006 10:30"), parseDate("12/21/2006 11:15"), "gray");
        _t31.setLabel(_t31.getTaskName());
        _t31.setStartupTime(20);
        Task _t32 = new Task("t32", "Hibbings West", _r3.getResourceId(), _r3.getResourceName(), parseDate("12/21/2006 12:30"), parseDate("12/21/2006 13:30"), "tan");
        _t32.setLabel(_t32.getTaskName());
        _t32.setStartupTime(20);
        _r3.addTask(_t31);
        _r3.addTask(_t32);
        Task _t33 = new Task("t33", "Hibbings West", _r3.getResourceId(), _r3.getResourceName(), parseDate("12/22/2006 9:30"), parseDate("12/22/2006 11:15"), "gray");
        _t33.setLabel(_t33.getTaskName());
        _t33.setStartupTime(15);
        Task _t34 = new Task("t34", "Hibbings West", _r3.getResourceId(), _r3.getResourceName(), parseDate("12/22/2006 14:30"), parseDate("12/22/2006 17:30"), "tan");
        _t34.setLabel(_t34.getTaskName());
        _r3.addTask(_t33);
        _r3.addTask(_t34);

        Employee _r4 = new Employee("r4", "Joe Block", "Consulting", "06:00", "15:00", null);
        Task _t41 = new Task("t41", "Harwell", _r4.getResourceId(), _r4.getResourceName(), parseDate("12/21/2006 09:30"), parseDate("12/21/2006 10:30"), "purple");
        _t41.setLabel(_t41.getTaskName());
        Task _t42 = new Task("t42", "Service.com", _r4.getResourceId(), _r4.getResourceName(), parseDate("12/21/2006 15:00"), parseDate("12/21/2006 16:30"), "aqua");
        _t42.setLabel(_t42.getTaskName());
        _t42.setStartupTime(15);
        _r4.addTask(_t41);
        _r4.addTask(_t42);
        Task _t43 = new Task("t43", "Harwell", _r4.getResourceId(), _r4.getResourceName(), parseDate("12/22/2006 11:30"), parseDate("12/22/2006 12:30"), "purple");
        _t43.setLabel(_t43.getTaskName());
        Task _t44 = new Task("t44", "Service.com", _r4.getResourceId(), _r4.getResourceName(), parseDate("12/22/2006 15:30"), parseDate("12/22/2006 16:30"), "aqua");
        _t44.setLabel(_t44.getTaskName());
        _t43.setStartupTime(15);
        _r4.addTask(_t43);
        _r4.addTask(_t44);

        Employee _r5 = new Employee("r5", "Stacey Edwards", "Support", "06:00", "15:00", null);
        Task _t51 = new Task("t51", "Hewitt Pacific", _r5.getResourceId(), _r5.getResourceName(), parseDate("12/21/2006 13:00"), parseDate("12/21/2006 14:30"), "green");
        _t51.setLabel(_t51.getTaskName());
        _r5.addTask(_t51);
        Task _t52 = new Task("t52", "Hewitt Pacific", _r5.getResourceId(), _r5.getResourceName(), parseDate("12/22/2006 13:00"), parseDate("12/22/2006 15:30"), "green");
        _t52.setLabel(_t52.getTaskName());
        Task _t53 = new Task("t53", "Hewitt Pacific", _r5.getResourceId(), _r5.getResourceName(), parseDate("12/22/2006 16:00"), parseDate("12/22/2006 17:30"), "green");
        _t53.setLabel(_t53.getTaskName());
        _t53.setStartupTime(30);
        _r5.addTask(_t52);
        _r5.addTask(_t53);

        Employee _r6 = new Employee("r6", "Jimmy Jones", "Support", "06:00", "15:00", null);
        Task _t61 = new Task("t61", "Harwell", _r6.getResourceId(), _r6.getResourceName(), parseDate("12/21/2006 10:00"), parseDate("12/21/2006 11:00"), "orange");
        _t61.setLabel(_t61.getTaskName());
        _r6.addTask(_t61);

        Employee _r7 = new Employee("r7", "Dominique Monstratelli", "Technical Staff", "06:00", "15:00", null);
        Task _t71 = new Task("t71", "State Machine", _r7.getResourceId(), _r7.getResourceName(), parseDate("12/21/2006 13:00"), parseDate("12/21/2006 15:30"), "blue");
        _t71.setLabel(_t71.getTaskName());
        Task _t72 = new Task("t72", "Johnson", _r7.getResourceId(), _r7.getResourceName(), parseDate("12/21/2006 15:00"), parseDate("12/21/2006 16:30"), "aqua");
        _t72.setLabel(_t72.getTaskName());
        _r7.addTask(_t71);
        _r7.addTask(_t72);
        Task _t73 = new Task("t73", "State Machine", _r7.getResourceId(), _r7.getResourceName(), parseDate("12/22/2006 10:00"), parseDate("12/22/2006 11:30"), "lavender");
        _t73.setLabel(_t73.getTaskName());
        Task _t74 = new Task("t74", "Johnson", _r7.getResourceId(), _r7.getResourceName(), parseDate("12/22/2006 15:00"), parseDate("12/22/2006 16:30"), "blue");
        _t74.setLabel(_t74.getTaskName());
        _r7.addTask(_t73);
        _r7.addTask(_t74);

        Employee _r8 = new Employee("r8", "Sue Morgan", "Technical Staff", "06:00", "15:00", null);
        Task _t81 = new Task("t81", "Hewitt Pacific", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/21/2006 11:15"), parseDate("12/21/2006 12:30"), "gold");
        _t81.setLabel(_t81.getTaskName());
        Task _t82 = new Task("t82", "Hewitt Pacific", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/21/2006 13:30"), parseDate("12/21/2006 15:00"), "gray");
        _t82.setLabel(_t82.getTaskName());
        _t82.setStartupTime(15);
        Task _t83 = new Task("t83", "Johnson", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/21/2006 15:15"), parseDate("12/21/2006 16:00"), "tan");
        _t83.setLabel(_t83.getTaskName());
        _r8.addTask(_t81);
        _r8.addTask(_t82);
        _r8.addTask(_t83);
        Task _t84 = new Task("t84", "Johnson", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/22/2006 14:15"), parseDate("12/22/2006 16:00"), "lime");
        _t84.setLabel(_t84.getTaskName());
        _t84.setStartupTime(30);
        _r8.addTask(_t84);

        ArrayList _resources = new ArrayList(10);        
        _resources.add(_r1);
        _resources.add(_r2);
        _resources.add(_r3);
        _resources.add(_r4);
        _resources.add(_r5);
        _resources.add(_r6);
        _resources.add(_r7);
        _resources.add(_r8);

        return ModelUtils.toTreeModel(_resources);
    }
    
    public static TreeModel getSchedulingGanttBackgroundBarsModel()
    {
        Employee _r1 = new Employee("r1", "David Schmidt", "Accounting", null, null, null);
        Task _t11 = new Task("t11", "Hibbings West", _r1.getResourceId(), _r1.getResourceName(), parseDate("12/21/2006 14:00"), parseDate("12/21/2006 15:30"), "blue");
        _t11.setLabel(_t11.getTaskName());
        _r1.addTask(_t11);
        BackgroundBar _b1 = new BackgroundBar(parseDate("12/21/2006 11:00"), parseDate("12/21/2006 17:00"), "fillColor");
        _r1.addBackgroundBar(_b1);
        
        Employee _r2 = new Employee("r2", "Glen Abboline", "Accounting", null, null, null);
        Task _t21 = new Task("t21", "Cymer Inc.", _r2.getResourceId(), _r2.getResourceName(), parseDate("12/21/2006 09:00"), parseDate("12/21/2006 10:30"), "gold");
        _t21.setLabel(_t21.getTaskName());
        _t21.setStartupTime(30);
        _r2.addTask(_t21);
        BackgroundBar _b2 = new BackgroundBar(parseDate("12/21/2006 7:00"), parseDate("12/21/2006 12:00"), "fillColor2");
        _r2.addBackgroundBar(_b2);

        Employee _r3 = new Employee("r3", "Lingle Adam", "Consulting", null, null, null);
        Task _t31 = new Task("t31", "Hibbings West", _r3.getResourceId(), _r3.getResourceName(), parseDate("12/21/2006 10:30"), parseDate("12/21/2006 11:15"), "gold");
        _t31.setLabel(_t31.getTaskName());
        _t31.setStartupTime(20);
        Task _t32 = new Task("t32", "Hibbings West", _r3.getResourceId(), _r3.getResourceName(), parseDate("12/21/2006 12:30"), parseDate("12/21/2006 13:30"), "aqua");
        _t32.setLabel(_t32.getTaskName());
        _t32.setStartupTime(20);
        _r3.addTask(_t31);
        _r3.addTask(_t32);
        BackgroundBar _b3 = new BackgroundBar(parseDate("12/21/2006 8:00"), parseDate("12/21/2006 14:00"), null);
        _r3.addBackgroundBar(_b3);

        Employee _r4 = new Employee("r4", "Joe Block", "Consulting", null, null, null);
        Task _t41 = new Task("t41", "Harwell", _r4.getResourceId(), _r4.getResourceName(), parseDate("12/21/2006 09:30"), parseDate("12/21/2006 10:30"), "purple");
        _t41.setLabel(_t41.getTaskName());
        Task _t42 = new Task("t42", "Service.com", _r4.getResourceId(), _r4.getResourceName(), parseDate("12/21/2006 15:00"), parseDate("12/21/2006 16:30"), "aqua");
        _t42.setLabel(_t42.getTaskName());
        _t42.setStartupTime(15);
        _r4.addTask(_t41);
        _r4.addTask(_t42);

        Employee _r5 = new Employee("r5", "Stacey Edwards", "Support", null, null, null);
        Task _t51 = new Task("t51", "Hewitt Pacific", _r5.getResourceId(), _r5.getResourceName(), parseDate("12/21/2006 13:00"), parseDate("12/21/2006 14:30"), "green");
        _t51.setLabel(_t51.getTaskName());
        _r5.addTask(_t51);
        _r5.addBackgroundBar(_b1);

        Employee _r6 = new Employee("r6", "Jimmy Jones", "Support", null, null, null);
        Task _t61 = new Task("t61", "Harwell", _r6.getResourceId(), _r6.getResourceName(), parseDate("12/21/2006 10:00"), parseDate("12/21/2006 11:00"), "orange");
        _t61.setLabel(_t61.getTaskName());
        _r6.addTask(_t61);
        _r6.addBackgroundBar(_b2);

        Employee _r7 = new Employee("r7", "Dominique Monstratelli", "Technical Staff", null, null, null);
        Task _t71 = new Task("t71", "State Machine", _r7.getResourceId(), _r7.getResourceName(), parseDate("12/21/2006 10:00"), parseDate("12/21/2006 11:30"), "blue");
        _t71.setLabel(_t71.getTaskName());
        Task _t72 = new Task("t72", "Johnson", _r7.getResourceId(), _r7.getResourceName(), parseDate("12/21/2006 12:30"), parseDate("12/21/2006 13:30"), "aqua");
        _t72.setLabel(_t72.getTaskName());
        _r7.addTask(_t71);
        _r7.addTask(_t72);
        _r7.addBackgroundBar(_b3);

        Employee _r8 = new Employee("r8", "Sue Morgan", "Technical Staff", null, null, null);
        Task _t81 = new Task("t81", "Hewitt Pacific", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/21/2006 11:15"), parseDate("12/21/2006 12:30"), "gold");
        _t81.setLabel(_t81.getTaskName());
        Task _t82 = new Task("t82", "Hewitt Pacific", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/21/2006 13:30"), parseDate("12/21/2006 15:00"), "gray");
        _t82.setLabel(_t82.getTaskName());
        _t82.setStartupTime(15);
        Task _t83 = new Task("t83", "Johnson", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/21/2006 15:15"), parseDate("12/21/2006 16:00"), "tan");
        _t83.setLabel(_t83.getTaskName());
        _r8.addTask(_t81);
        _r8.addTask(_t82);
        _r8.addTask(_t83);
        Task _t84 = new Task("t84", "Johnson", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/22/2006 14:15"), parseDate("12/22/2006 16:00"), "lime");
        _t84.setLabel(_t84.getTaskName());
        _t84.setStartupTime(30);
        _r8.addTask(_t84);

        ArrayList _resources = new ArrayList(10);        
        _resources.add(_r1);
        _resources.add(_r2);
        _resources.add(_r3);
        _resources.add(_r4);
        _resources.add(_r5);
        _resources.add(_r6);
        _resources.add(_r7);
        _resources.add(_r8);

        return ModelUtils.toTreeModel(_resources);
    }
    
    
    public static TreeModel getSchedulingGanttResourceActionModel()
    {
        Employee _r1 = new Employee("r1", "David Schmidt", "Accounting", null, null, null);
        Task _t11 = new Task("t11", "Hibbings West", _r1.getResourceId(), _r1.getResourceName(), parseDate("12/21/2006 14:00"), parseDate("12/21/2006 15:30"), "blue");
        _t11.setLabel(_t11.getTaskName());
        _r1.addTask(_t11);
        
        Employee _r2 = new Employee("r2", "Glen Abboline", "Accounting", null, null, null);
        Task _t21 = new Task("t21", "Cymer Inc.", _r2.getResourceId(), _r2.getResourceName(), parseDate("12/21/2006 09:00"), parseDate("12/21/2006 10:30"), "blue");
        _t21.setLabel(_t21.getTaskName());
        _t21.setStartupTime(30);
        _r2.addTask(_t21);
        
        Employee _r3 = new Employee("r3", "Lingle Adam", "Consulting", null, null, null);
        Task _t31 = new Task("t31", "Hibbings West", _r3.getResourceId(), _r3.getResourceName(), parseDate("12/21/2006 10:30"), parseDate("12/21/2006 11:15"), "blue");
        _t31.setLabel(_t31.getTaskName());
        _t31.setStartupTime(20);
        Task _t32 = new Task("t32", "Hibbings West", _r3.getResourceId(), _r3.getResourceName(), parseDate("12/21/2006 12:30"), parseDate("12/21/2006 13:30"), "blue");
        _t32.setLabel(_t32.getTaskName());
        _t32.setStartupTime(20);
        _r3.addTask(_t31);
        _r3.addTask(_t32);
        
        Employee _r4 = new Employee("r4", "Joe Block", "Consulting", null, null, null);
        Task _t41 = new Task("t41", "Harwell", _r4.getResourceId(), _r4.getResourceName(), parseDate("12/21/2006 09:30"), parseDate("12/21/2006 10:30"), "blue");
        _t41.setLabel(_t41.getTaskName());
        Task _t42 = new Task("t42", "Service.com", _r4.getResourceId(), _r4.getResourceName(), parseDate("12/21/2006 15:00"), parseDate("12/21/2006 16:30"), "blue");
        _t42.setLabel(_t42.getTaskName());
        _t42.setStartupTime(15);
        _r4.addTask(_t41);
        _r4.addTask(_t42);

        Employee _r5 = new Employee("r5", "Stacey Edwards", "Support", null, null, null);
        Task _t51 = new Task("t51", "Hewitt Pacific", _r5.getResourceId(), _r5.getResourceName(), parseDate("12/21/2006 13:00"), parseDate("12/21/2006 14:30"), "blue");
        _t51.setLabel(_t51.getTaskName());
        _r5.addTask(_t51);
        
        Employee _r6 = new Employee("r6", "Jimmy Jones", "Support", null, null, null);
        Task _t61 = new Task("t61", "Harwell", _r6.getResourceId(), _r6.getResourceName(), parseDate("12/21/2006 10:00"), parseDate("12/21/2006 11:00"), "blue");
        _t61.setLabel(_t61.getTaskName());
        _r6.addTask(_t61);
        
        Employee _r7 = new Employee("r7", "Dominique Monstratelli", "Technical Staff", null, null, null);
        Task _t71 = new Task("t71", "State Machine", _r7.getResourceId(), _r7.getResourceName(), parseDate("12/21/2006 10:00"), parseDate("12/21/2006 11:30"), "blue");
        _t71.setLabel(_t71.getTaskName());
        Task _t72 = new Task("t72", "Johnson", _r7.getResourceId(), _r7.getResourceName(), parseDate("12/21/2006 12:30"), parseDate("12/21/2006 13:30"), "blue");
        _t72.setLabel(_t72.getTaskName());
        _r7.addTask(_t71);
        _r7.addTask(_t72);
        
        Employee _r8 = new Employee("r8", "Sue Morgan", "Technical Staff", null, null, null);
        Task _t81 = new Task("t81", "Hewitt Pacific", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/21/2006 11:15"), parseDate("12/21/2006 12:30"), "blue");
        _t81.setLabel(_t81.getTaskName());
        Task _t82 = new Task("t82", "Hewitt Pacific", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/21/2006 13:30"), parseDate("12/21/2006 15:00"), "blue");
        _t82.setLabel(_t82.getTaskName());
        _t82.setStartupTime(15);
        Task _t83 = new Task("t83", "Johnson", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/21/2006 15:15"), parseDate("12/21/2006 16:00"), "blue");
        _t83.setLabel(_t83.getTaskName());
        _r8.addTask(_t81);
        _r8.addTask(_t82);
        _r8.addTask(_t83);
        Task _t84 = new Task("t84", "Johnson", _r8.getResourceId(), _r8.getResourceName(), parseDate("12/22/2006 14:15"), parseDate("12/22/2006 16:00"), "blue");
        _t84.setLabel(_t84.getTaskName());
        _t84.setStartupTime(30);
        _r8.addTask(_t84);

        ArrayList _resources = new ArrayList(10);        
        _resources.add(_r1);
        _resources.add(_r2);
        _resources.add(_r3);
        _resources.add(_r4);
        _resources.add(_r5);
        _resources.add(_r6);
        _resources.add(_r7);
        _resources.add(_r8);

        return ModelUtils.toTreeModel(_resources);
    }

    public static TreeModel getResourceUtilizationGanttModel()
    {
        Employee _r1 = new Employee("r1", "David Schmidt", "Accounting");
        Employee _r2 = new Employee("r2", "Glen Abboline", "Consulting");
    
        _r1.addBucket(new TimeBucket(parseDate("01/09/2008"), 4, 4, 16));
        _r1.addBucket(new TimeBucket(parseDate("01/10/2008"), 4, 4, 16));
        _r1.addBucket(new TimeBucket(parseDate("01/11/2008"), 4, 4, 16));
        _r1.addBucket(new TimeBucket(parseDate("01/12/2008"), 4, 4, 16));
        _r1.addBucket(new TimeBucket(parseDate("01/13/2008"), 4, 4, 16));
        _r1.addBucket(new TimeBucket(parseDate("01/14/2008"), 4, 4, 16));
        _r1.addBucket(new TimeBucket(parseDate("01/15/2008"), 4, 4, 16));
        _r1.addBucket(new TimeBucket(parseDate("01/16/2008"), 4, 4, 16));

        _r2.addBucket(new TimeBucket(parseDate("01/10/2008"), 10, 10, 16));
        _r2.addBucket(new TimeBucket(parseDate("01/11/2008"), 8, 8, 16));

        _r2.addBucket(new TimeBucket(parseDate("01/12/2008"), 8, 8, 16));
        _r2.addBucket(new TimeBucket(parseDate("01/13/2008"), 8, 8, 16));
        _r2.addBucket(new TimeBucket(parseDate("01/14/2008"), 8, 8, 16));
        _r2.addBucket(new TimeBucket(parseDate("01/16/2008"), 8, 8, 16));
        _r2.addBucket(new TimeBucket(parseDate("01/17/2008"), 8, 8, 16));
        _r2.addBucket(new TimeBucket(parseDate("01/19/2008"), 8, 8, 16));

        ArrayList _resources = new ArrayList<Employee>(2);
        _resources.add(_r1);
        _resources.add(_r2);

        return ModelUtils.toTreeModel(_resources);
    }
    
    public static TreeModel getResourceUtilizationGanttSteppedLineModel()
    {
        Employee _r1 = new Employee("r1", "David Schmidt", "Accounting");
        Employee _r2 = new Employee("r2", "Glen Abboline", "Consulting");
    
        _r1.addBucket(new TimeBucket(parseDate("01/09/2008"), 4, 4, 16, 6));
        _r1.addBucket(new TimeBucket(parseDate("01/10/2008"), 4, 4, 16, 10));
        _r1.addBucket(new TimeBucket(parseDate("01/11/2008"), 4, 4, 16, 4));
        _r1.addBucket(new TimeBucket(parseDate("01/12/2008"), 4, 4, 16, 7));
        _r1.addBucket(new TimeBucket(parseDate("01/13/2008"), 4, 4, 16, 7));
        _r1.addBucket(new TimeBucket(parseDate("01/14/2008"), 4, 4, 16, 7));
        _r1.addBucket(new TimeBucket(parseDate("01/15/2008"), 4, 4, 16, 7));
        _r1.addBucket(new TimeBucket(parseDate("01/16/2008"), 4, 4, 16, 7));

        _r2.addBucket(new TimeBucket(parseDate("01/10/2008"), 10, 10, 16, 7));
        _r2.addBucket(new TimeBucket(parseDate("01/11/2008"), 8, 8, 16, 7));

        _r2.addBucket(new TimeBucket(parseDate("01/12/2008"), 8, 8, 16, 7));
        _r2.addBucket(new TimeBucket(parseDate("01/13/2008"), 8, 8, 16, 7));
        _r2.addBucket(new TimeBucket(parseDate("01/14/2008"), 8, 8, 16, 7));
        _r2.addBucket(new TimeBucket(parseDate("01/15/2008"), 0, 0, 0, 7));
        _r2.addBucket(new TimeBucket(parseDate("01/16/2008"), 8, 8, 16, 7));
        _r2.addBucket(new TimeBucket(parseDate("01/17/2008"), 8, 8, 16, 7));
        _r2.addBucket(new TimeBucket(parseDate("01/18/2008"), 0, 0, 0, 7));
        _r2.addBucket(new TimeBucket(parseDate("01/19/2008"), 8, 8, 16, 7));

        ArrayList _resources = new ArrayList<Employee>(2);
        _resources.add(_r1);
        _resources.add(_r2);

        return ModelUtils.toTreeModel(_resources);
    }

    public static TreeModel getResourceUtilizationGanttAttributeDetailModel()
    {
        Employee _r1 = new Employee("r1", "David Schmidt", "Accounting");
        Employee _r2 = new Employee("r2", "Glen Abboline", "Consulting");
    
        _r1.addBucket(new TimeBucketAttributeDetail(parseDate("01/09/2008"), new double[] {4, 4, 6, 4, 4}, new String[] {"Nike", "Rbk", "Puma", "Fila", "Asics"}));
        _r1.addBucket(new TimeBucketAttributeDetail(parseDate("01/10/2008"), new double[] {2, 4, 6, 2}, new String[] {"Wilson", "Rbk", "Umbro", "Asics"}));
        _r1.addBucket(new TimeBucketAttributeDetail(parseDate("01/11/2008"), new double[] {4, 4}, new String[] {"Puma", "Rbk"}));
        _r1.addBucket(new TimeBucketAttributeDetail(parseDate("01/12/2008"), new double[] {2, 2, 4, 2, 2, 4, 4}, new String[] {"Mitre", "Adidas", "Umbro", "Puma", "Asics", "Nike", "Rbk"}));
        _r1.addBucket(new TimeBucketAttributeDetail(parseDate("01/13/2008"), new double[] {4, 2, 4, 2}, new String[] {"Puma", "Wilson", "Fila", "Asics"}));
        _r1.addBucket(new TimeBucketAttributeDetail(parseDate("01/14/2008"), new double[] {4, 4, 2, 2}, new String[] {"Nike", "Rbk", "Puma", "Fila"}));
        _r1.addBucket(new TimeBucketAttributeDetail(parseDate("01/15/2008"), new double[] {2, 4, 4}, new String[] {"Adidas", "Ellesse", "Mitre"}));
        _r1.addBucket(new TimeBucketAttributeDetail(parseDate("01/16/2008"), new double[] {4, 4, 2, 6}, new String[] {"Ellesse", "Diadora", "Rbk", "Umbro"}));

        _r2.addBucket(new TimeBucketAttributeDetail(parseDate("01/10/2008"), new double[] {4, 2, 2, 2}, new String[] {"Wilson", "Rbk", "Puma", "Fila"}));
        _r2.addBucket(new TimeBucketAttributeDetail(parseDate("01/11/2008"), new double[] {4, 4, 4, 6}, new String[] {"Nike", "Rbk", "Wilson", "Fila"}));

        _r2.addBucket(new TimeBucketAttributeDetail(parseDate("01/12/2008"), new double[] {4, 4}, new String[] {"Puma", "Fila"}));
        _r2.addBucket(new TimeBucketAttributeDetail(parseDate("01/13/2008"), new double[] {4, 4, 6, 6, 4}, new String[] {"Asics", "Rbk", "Puma", "Fila", "Mizuno"}));
        _r2.addBucket(new TimeBucketAttributeDetail(parseDate("01/14/2008"), new double[] {4, 4, 2, 6}, new String[] {"Adidas", "Rbk", "Puma", "Fila"}));
        _r2.addBucket(new TimeBucketAttributeDetail(parseDate("01/15/2008"), new double[] {4, 4, 2, 6}, new String[] {"Nike", "Mizuno", "Puma", "Fila"}));
        _r2.addBucket(new TimeBucketAttributeDetail(parseDate("01/16/2008"), new double[] {4, 2, 4, 6}, new String[] {"Nike", "Ellesse", "Mizuno", "Fila"}));
        _r2.addBucket(new TimeBucketAttributeDetail(parseDate("01/17/2008"), new double[] {4, 2, 4, 2}, new String[] {"Nike", "Umbro", "Puma", "Fila"}));
        _r2.addBucket(new TimeBucketAttributeDetail(parseDate("01/18/2008"), new double[] {2, 2, 4, 2, 2, 4, 4}, new String[] {"Adidas", "Asics", "Umbro", "Puma", "Mitre", "Nike", "Rbk"}));
        _r2.addBucket(new TimeBucketAttributeDetail(parseDate("01/19/2008"), new double[] {4, 4, 2, 6}, new String[] {"Nike", "Rbk", "Puma", "Fila"}));

        ArrayList _resources = new ArrayList<Employee>(2);
        _resources.add(_r1);
        _resources.add(_r2);

        return ModelUtils.toTreeModel(_resources);
    }

    public static TreeModel getProjectGantTaskbarFormattModel()
    {
        ArrayList _tasks = new ArrayList<Task>(10);
        Task _task2 = new Task("t2", "Task A-P1", "00001", "ccchow", parseDate("05/15/2008"), parseDate("06/20/2008"), "custom");
        Task _task3 = new Task("t3", "Task C-P1", "00002", "kmachos", parseDate("06/27/2008"), parseDate("07/15/2008"), GanttConstants.NORMAL);
        _task3.setPercentComplete(75);
        Task _task4 = new Task("t4", "Task D-P1", "00003", "test", parseDate("08/20/2008"), parseDate("09/20/2008"), "custom");
        Task _task5 = new Task("t5", "Milestone 1", "00003", "mstarets", parseDate("06/20/2008"), null, GanttConstants.MILESTONE);
        _task5.setLabel("Milestone 1");
        Task _task6 = new Task("t6", "Variance E-P1", "00001", "ccchow", parseDate("07/20/2008"), parseDate("08/10/2008"), GanttConstants.NORMAL);
        _task6.setActualStartTime(parseDate("07/27/2008"));
        _task6.setActualEndTime(parseDate("08/17/2008"));
        
        _tasks.add(_task2);
        _tasks.add(_task3);
        _tasks.add(_task4);
        _tasks.add(_task5);
        _tasks.add(_task6);
        
        return new SampleProjectModel(_tasks, "subTasks");
    }

    private static void addRelationship(Task task1, Task task2)
    {
        TaskDependency _dep = new TaskDependency(task1, task2, GanttConstants.FINISH_START);
        task1.addDependency(_dep);
        task2.addDependency(_dep);
    }

    private static Calendar parseDateCalendar(String date)
    {
        Date ret = null;
        Calendar c = Calendar.getInstance();
        try
        {
            if (date.indexOf(':') > -1)
                ret = HOUR_FORMAT.parse(date);
            else
                ret = DAY_FORMAT.parse(date);
            
            c.setTime(ret);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            }catch(Exception e){
                e.printStackTrace();
            }
        return c;
    }
    
    private static Date parseDate(String date)
    {
        Date ret = null;
        try
        {
            if (date.indexOf(':') > -1)
                ret = HOUR_FORMAT.parse(date);
            else
                ret = DAY_FORMAT.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    private static DateFormat DAY_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private static DateFormat HOUR_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");
}
