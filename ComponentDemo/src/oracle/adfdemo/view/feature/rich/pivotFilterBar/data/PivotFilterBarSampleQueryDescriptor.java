/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/data/PivotFilterBarSampleQueryDescriptor.java /main/3 2011/12/23 08:32:06 jayturne Exp $ */

/* Copyright (c) 2009, 2011, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    ccchow      05/05/09 - use name
    ccchow      05/04/09 - handle empty page edge case
    ccchow      05/01/09 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/data/PivotFilterBarSampleQueryDescriptor.java /main/2 2010/03/08 11:06:43 jayturne Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotFilterBar.data;

import java.util.Map;

import oracle.adf.view.faces.bi.model.PivotableQueryDescriptor;
import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;
import oracle.adf.view.rich.model.QueryDescriptor;

import oracle.dss.util.DataAccess;
import oracle.dss.util.DataAvailableEvent;
import oracle.dss.util.DataChangedEvent;
import oracle.dss.util.DataDirector;
import oracle.dss.util.DataDirectorListener;
import oracle.dss.util.LayerMetadataMap;
import oracle.dss.util.PollingRequiredEvent;
import oracle.dss.util.WaitDataAvailableEvent;

/**
 * This QueryDescriptor implementation represents a PivotFilterBar's value. It handles pivoting
 * and provides access to a ConjunctionCriterion object which is responsible for populating the
 * PivotFilterBar's filters along with thier available values.
 */
public class PivotFilterBarSampleQueryDescriptor extends PivotableQueryDescriptor implements DataDirectorListener
{
    private ConjunctionCriterion m_conjunctionCriterion;
    private AttributeCriterion m_currentCriterion;
    private DataAccess m_dataAccess;
    private DataDirector m_dataDirector;
    private boolean m_dataAvailable;
    
    public PivotFilterBarSampleQueryDescriptor(DataAccess dataAccess, DataDirector dataDirector, boolean dataAvailable)
    {
        m_dataAccess = dataAccess;
        m_dataDirector = dataDirector;
        m_dataAvailable = dataAvailable;
        
        m_dataDirector.addDataDirectorListener(this);
    }
    
    public boolean isPivotableQueryDescriptorLocallyAvailable() {
        return this.m_dataAvailable;
    }
    
    public void setDataAvailable(boolean dataAvailable) {
        this.m_dataAvailable = dataAvailable;
    }
    
    public void addCriterion(String criterion)
    {
    }
    
    public void removeCriterion(Criterion criterion)
    {
    }

    public void pivotCriterion(String from, String to, PivotOperation operation)
    {
        try
        {
            int _fromEdge = -1;
            int _fromLayer = findLayer(DataDirector.ROW_EDGE, from);
            if (_fromLayer != -1)
                _fromEdge = DataDirector.ROW_EDGE;
            else
            {
                _fromLayer = findLayer(DataDirector.COLUMN_EDGE, from);
                if (_fromLayer != -1)
                    _fromEdge = DataDirector.COLUMN_EDGE;
                else
                {
                    _fromLayer = findLayer(DataDirector.PAGE_EDGE, from);
                    _fromEdge = DataDirector.PAGE_EDGE;
                }
            }
            
            if (_fromLayer == -1)
                return;

            int _toEdge = DataDirector.PAGE_EDGE;
            // see if this is a drop to an empty page edge
            if (to == null) {
                m_dataDirector.pivot(_fromEdge, _toEdge, _fromLayer, 0, DataDirector.PIVOT_MOVE_TO);                
            }
            else
            {
                int _toLayer = findLayer(DataDirector.PAGE_EDGE, to);
                
                if (_toLayer == -1)
                    return;
                
                int _flag = DataDirector.PIVOT_SWAP;
                if (operation.equals(PivotOperation.MOVE_BEFORE))
                    _flag = DataDirector.PIVOT_MOVE_BEFORE;
                else if (operation.equals(PivotOperation.MOVE_AFTER))
                    _flag = DataDirector.PIVOT_MOVE_AFTER;
    
                m_dataDirector.pivot(_fromEdge, _toEdge, _fromLayer, _toLayer, _flag);
            }
            
            // reset criterions
            m_conjunctionCriterion = null;        
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    private int findLayer(int edge, String dimension) throws Exception
    {
        int layerCount = m_dataAccess.getLayerCount(edge);
        for (int dim = 0; dim < layerCount; dim++) 
        {
            //get the dimension name
            String dimName = (String)m_dataAccess.getLayerMetadata(edge, dim, LayerMetadataMap.LAYER_METADATA_NAME);
            if (dimName.toLowerCase().equals(dimension.toLowerCase()))
                return dim;
        }
        
        return -1;
    }
    
    public ConjunctionCriterion getConjunctionCriterion()
    {
        if (m_conjunctionCriterion == null)
            m_conjunctionCriterion = new PivotFilterBarConjunctionCriterion(m_dataAccess, m_dataDirector);
        return m_conjunctionCriterion;
    }
    
    public AttributeCriterion getCurrentCriterion()
    {
        return m_currentCriterion;
    }

    public void setCurrentCriterion(AttributeCriterion criterion)
    {
        m_currentCriterion = criterion;
    }
    
    public String getName()
    {
        return null;
    }
    
    public Map<String, Object> getUIHints()
    {
        return null;
    }

    public void changeMode(QueryDescriptor.QueryMode mode)
    {
    }

    public void pollingRequired(PollingRequiredEvent event){}
    public void waitDataAvailable(WaitDataAvailableEvent event){}
    public void viewDataAvailable(DataAvailableEvent event){}
    public void viewDataChanged(DataChangedEvent event)
    {
        m_conjunctionCriterion = null;
    }    
}
