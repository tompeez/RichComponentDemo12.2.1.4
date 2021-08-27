/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/data/PivotFilterBarConjunctionCriterion.java /main/2 2011/12/23 08:32:06 jayturne Exp $ */

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
    ccchow      05/01/09 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotFilterBar/data/PivotFilterBarConjunctionCriterion.java /main/1 2009/05/05 15:56:19 ccchow Exp $
 *  @author  ccchow  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotFilterBar.data;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.view.rich.model.ActiveDataModel;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;

import oracle.dss.util.DataAccess;
import oracle.dss.util.DataDirector;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerMetadataMap;
import oracle.dss.util.LayerOutOfRangeException;

import org.apache.myfaces.trinidad.logging.TrinidadLogger;

/**
 * This ConjunctionCriterion implementation provides a list of AttributeCriterion objects. Each
 * of these AttributeCriterions corresponds to a given filter of a PivotFilterBar, and provides
 * the PivotFilterBar with the values available in the given filter.
 */
public class PivotFilterBarConjunctionCriterion extends ConjunctionCriterion
{
    private List<Criterion> m_criterions;
    private DataAccess m_dataAccess;
    private DataDirector m_dataDirector;

    public PivotFilterBarConjunctionCriterion(DataAccess dataAccess, DataDirector dataDirector)
    {
        m_dataAccess = dataAccess;
        m_dataDirector = dataDirector;
    }

    public Conjunction getConjunction()
    {
        return Conjunction.AND;
    }
    
    public void setConjunction(Conjunction conjunction)
    {
    }
    
    public Criterion getCriterion(Object key)
    {
        Integer _key = (Integer)key;
        return m_criterions.get(_key.intValue());
    }
    
    public Object getKey(Criterion criterion)
    {
        return Integer.valueOf(m_criterions.indexOf(criterion));
    }
    
    public List<Criterion> getCriterionList()
    {
        if (m_criterions == null)
        {
            String dimensionName = null;
            int contextSlice[] = null;
            
            //get the relative indexes of all the layers for the current slice
            try 
            {
                contextSlice = m_dataAccess.getEdgeCurrentHPos(DataDirector.PAGE_EDGE);
            }
            catch (EdgeOutOfRangeException e) 
            {
                _LOG.warning(e);
            }
            
            int layerCount = -1;
            if (contextSlice != null)
                layerCount = contextSlice.length;
            
            m_criterions = new ArrayList<Criterion>(layerCount);
            
            for (int dim = 0; dim < layerCount; dim++) 
            {
                dimensionName = null;
            
                try 
                {
                    Object dimName = null;
                    //get the dimension name
                    dimName = m_dataAccess.getLayerMetadata(DataDirector.PAGE_EDGE, dim, LayerMetadataMap.LAYER_METADATA_LONGLABEL);
                    if (dimName == null)
                        dimensionName = "";
                    else
                        dimensionName = dimName.toString();
                    
                    //now get the number of items in that dimension                    
                    PivotFilterBarListOfValuesModel _lovModel = new PivotFilterBarListOfValuesModel(m_dataDirector, m_dataAccess, contextSlice, dim);
                    PivotFilterBarAttributeCriterion _criterion = new PivotFilterBarAttributeCriterion(dimensionName, _lovModel);
                    m_criterions.add(_criterion);
                }
                catch (LayerOutOfRangeException e)
                {
                    _LOG.warning(e);
                }
                catch (EdgeOutOfRangeException ex2)
                {
                    _LOG.warning(ex2);
                }
            }                   
        }
        
        return m_criterions;
    }

    static private final TrinidadLogger _LOG = TrinidadLogger.createTrinidadLogger(PivotFilterBarConjunctionCriterion.class);
}
