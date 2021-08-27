/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttPrinting.java /main/4 2012/01/24 10:27:25 mguirgui Exp $ */

/* Copyright (c) 2008, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ccchow      01/13/11 - specify cache-control directives
    hbroek      08/28/08 - Add Gantt samples
    ccchow      06/23/08 - change output stream to show pdf
    ccchow      06/17/08 - Backing bean for printing
    ccchow      06/17/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gantt/ProjectGanttPrinting.java /main/2 2009/03/19 21:37:42 teclee Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.gantt;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletResponse;

import oracle.adf.view.faces.bi.component.gantt.GanttPrinter;
import oracle.adf.view.faces.bi.component.gantt.UIProjectGantt;
import oracle.adf.view.faces.bi.event.GanttActionEvent;

import oracle.xdo.template.FOProcessor;

import org.apache.myfaces.trinidad.model.TreeModel;

import oracle.adfdemo.view.feature.rich.gantt.data.SampleModelFactory;


public class ProjectGanttPrinting
{
    private TreeModel m_model;
    private Object m_lock = new Object();

    public void handleGanttAction(GanttActionEvent event)
    {
        int _type = event.getActionType();
        if (_type == GanttActionEvent.PRINT)
        {
            synchronized(m_lock)
            {
                UIProjectGantt _gantt = (UIProjectGantt)event.getComponent();
                GanttPrinter _printer = new GanttPrinter(_gantt); //gantt is a reference to the UISchedulingGanttt or the UIProjectGantt.

                try
                {
                    File _file = File.createTempFile("gantt", "fo");
                    OutputStream _out = new FileOutputStream(_file);
                    _printer.print(_out);
                    _out.close();
                    
                    // generate pdf
                    FOProcessor _processor = new FOProcessor();
                    _processor.setData(new InputStreamReader(new FileInputStream(_file), "UTF-8"));
                    
                    FacesContext fc = FacesContext.getCurrentInstance();
                    HttpServletResponse _response = (HttpServletResponse)fc.getExternalContext().getResponse();
                    _response.setContentType("application/pdf");

                    // Application should specify cache control directives in the http response.
                    // Depending on the usage scenario, in this case we have make sure the                            // generated content is not cached.  But if you have for example a read only Gantt,                           // then you should cache the content
                    // for HTTP 1.0
                    _response.setHeader( "Pragma", "no-cache" );
                    // for HTTP 1.1
                    _response.setHeader( "Cache-Control", "no-cache" );
                    _response.setDateHeader( "Expires", 0 );

                    OutputStream _os = _response.getOutputStream();
                    
                    _processor.setOutput(_os);
                    _processor.setOutputFormat(FOProcessor.FORMAT_PDF);
                    _processor.generate();
                    _os.close();
                    fc.responseComplete();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public TreeModel getModel()
    {
        if (m_model == null)
            m_model = SampleModelFactory.getProjectGanttModel();

        return m_model;
    }
}

