/* Copyright (c) 2007, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jievans     12/05/10 - Bugs 10271194 and 10368448
    hbroek      11/20/08 - New Pivottable samples
    hbroek      11/20/08 - First version
    hbroek      08/10/23 - 
 */

package oracle.adfdemo.view.feature.rich.pivotTable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.adf.view.faces.bi.component.pivotTable.CellRange;
import oracle.adf.view.faces.bi.component.pivotTable.Selection;
import oracle.adf.view.faces.bi.component.pivotTable.SelectionSet;
import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;

import oracle.adf.view.faces.bi.model.CellIndex;
import oracle.adf.view.faces.bi.model.DataCellIndex;
import oracle.adf.view.faces.bi.model.HeaderCellIndex;
import oracle.adf.view.faces.bi.model.DataModel;

import oracle.dss.util.DataAccess;
import oracle.dss.util.DataDirector;
import oracle.dss.util.DataException;
import oracle.dss.util.DataMap;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.QDR;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableSampleModel;

import org.apache.myfaces.trinidad.context.RequestContext;

// Commented out for dvt-adf integration work
//import oracle.dvtdemo.utils.tageditor.ComponentEditorHandler;


/**
 * The PivotTableSampleModel is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableSelectionBean.java /main/8 2012/11/27 15:16:37 ccchow Exp $
 *  @author  hbroek
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableSelectionBean {

    public PivotTableSelectionBean() {
        m_dm = new PivotTableSampleModel();
    }
        
    public PivotTableSampleModel getDataModel() {
        return m_dm;
    }
    
    public void handleCustomEvent(ClientEvent event)
    {
        UIPivotTable _pivotTable = (UIPivotTable)event.getComponent();
        UIComponent _outputText = _pivotTable.getParent().findComponent("ot1");
        if (_outputText != null)
            RequestContext.getCurrentInstance().addPartialTarget(_outputText);
    }

    public String getSelectedCells() {
        UIPivotTable _pivotTable = (UIPivotTable)FacesContext.getCurrentInstance().getViewRoot().findComponent("demo:pt1");        
        if (_pivotTable != null)
            return getSelectedData(_pivotTable);
        else
            return "";
    }

    public String getSelectedData(UIPivotTable pt) {
        Selection selection = pt.getSelection();

//        String selectionElement;
        SelectionUtils selectionUtils;
        
        DataModel model = pt.getDataModel();
        
        if (selection.getDataCells().size() > 0) {
            selectionUtils = new DatabodySelectionUtils(selection, model);
        } else if (selection.getColumnHeaderCells().size() > 0) {
            selectionUtils = new ColumnHeaderSelectionUtils(selection, model);
        } else if (selection.getRowHeaderCells().size() > 0) {
            selectionUtils = new RowHeaderSelectionUtils(selection, model);
        } else {
            return "No cells selected";
        }
        
        String overviewString = selectionUtils.getOverviewString();
        String singleCellString = selectionUtils.getSingleCellString();
        String fromString = selectionUtils.getFromString();
        String toString = "to ";

        StringBuilder buffer = new StringBuilder(overviewString);
        int rangeNum = 0;
        
        for (CellRange range : selectionUtils.getRanges()) {
            buffer.append("Range " + rangeNum++ + ":\n");
            CellIndex start = range.getStartIndex(model);

            try {
                if (range.isSingleCell()) {
                    buffer.append(singleCellString);
                    selectionUtils.writeCell(start, buffer);
                    buffer.append('\n');
                } else {
                    CellIndex end = range.getEndIndex(model);        
                    buffer.append(fromString);
                    selectionUtils.writeCell(start, buffer);
                    buffer.append(toString);
                    selectionUtils.writeCell(end, buffer);
                    buffer.append('\n');
                }
            } catch (DataException e) {
                e.printStackTrace();
            }
        }
            
        return buffer.toString();
    }
    
    private abstract static class SelectionUtils {
        public SelectionUtils(DataModel model) {
            da = model.getDataAccess();
        }
        
        public abstract void writeCell(CellIndex index, StringBuilder buffer) throws DataException;
        
        public SelectionSet<? extends CellRange> getRanges() {
            return ranges;
        }
        
        public String getOverviewString() {
            String plural = (ranges.size() > 1) ? "s" : "";
            return "Selection consists of " + ranges.size() + " " + selectionElement +  " range" + plural + ":\n\n";
        }
        
        public String getSingleCellString() {
            return "The " + selectionElement + " ";
        }
        
        public String getFromString() {
            return "The " + selectionElement + "s from ";
        }
        
        protected SelectionSet<? extends CellRange> ranges;
        protected String selectionElement;
        protected DataAccess da;
    }
    
    private static class DatabodySelectionUtils extends SelectionUtils {
        public DatabodySelectionUtils(Selection selection, DataModel model) {
            super(model);
            ranges = selection.getDataCells();
            selectionElement = "data cell";
        }

        public void writeCell(CellIndex index, StringBuilder buffer) throws DataException {
            DataCellIndex dci = (DataCellIndex)index;
            int row = dci.getRow();
            int col = dci.getColumn();
            String rowLabel = (String)da.getSliceLabel(DataDirector.ROW_EDGE, row, MetadataMap.METADATA_LONGLABEL);
            String colLabel = (String)da.getSliceLabel(DataDirector.COLUMN_EDGE, col, MetadataMap.METADATA_LONGLABEL);                        
            Object value = da.getValue(row, col, DataMap.DATA_FORMATTED);
            buffer.append("[ROW: ").append(rowLabel).append(", COLUMN:").append(colLabel).append(", VALUE: ").append(value).append("]\n");
        }
    }
    
    private abstract static class HeaderSelectionUtils extends SelectionUtils {
        public HeaderSelectionUtils(DataModel model) {
            super(model);
        }

        public void writeCell(CellIndex index, StringBuilder buffer) throws DataException {
            HeaderCellIndex hci = (HeaderCellIndex)index;
            int layer = hci.getLayer();
            int slice = hci.getSlice();
            QDR memberQdr = da.getMemberQDR(edge, layer, slice, DataAccess.QDR_WITHOUT_PAGE);
            buffer.append('[').append(memberQdr.getDimMemberPairs()).append(']');
        }
        
        protected int edge;
    }
    
    private static class ColumnHeaderSelectionUtils extends HeaderSelectionUtils {
        public ColumnHeaderSelectionUtils(Selection selection, DataModel model) {
            super(model);
            ranges = selection.getColumnHeaderCells();
            selectionElement = "column header cell";
            edge = DataDirector.COLUMN_EDGE;
        }
    }
    
    private static class RowHeaderSelectionUtils extends HeaderSelectionUtils {
        public RowHeaderSelectionUtils(Selection selection, DataModel model) {
            super(model);
            ranges = selection.getRowHeaderCells();
            selectionElement = "row header cell";
            edge = DataDirector.ROW_EDGE;
        }
    }
    
    public void handleSelectedTask(ActionEvent evt)
    {
        UIComponent button  = (UIComponent)evt.getSource();
        UIComponent container  = button.getParent();
        UIPivotTable pt = null;
        for (UIComponent component: container.getChildren()) {
            if (component instanceof UIPivotTable) {
                pt= (UIPivotTable)component;
            }
        }
        if (pt != null)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(getSelectedData(pt)));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Can't find PivotTable"));
    }
    
    public UIPivotTable getPivotTable() {
        return m_pivotTable;
    }

    public void setPivotTable(UIPivotTable pivotTable) {
        m_pivotTable = pivotTable;
        
        // This call is only necessary when in the sample framework
        wireUpEditorToComponent(m_pivotTable);
    }
    
    
    /* Set the UIComponent on the Property Editor */
    private void wireUpEditorToComponent(UIComponent component) {
        /*FacesContext context = FacesContext.getCurrentInstance();
        ComponentEditorHandler editor = 
            (ComponentEditorHandler)context.getELContext().getELResolver().getValue(context.getELContext(), 
                                                                                    null, 
                                                                                    "editor");
        //Update Editor with UIComponent
        if (editor != null && !m_editorInitialized) {
          m_editorInitialized = true;
          editor.setComponent(component);
        }*/
    }

    boolean m_editorInitialized = false;
    PivotTableSampleModel m_dm = null;
    UIPivotTable m_pivotTable = null;
}
