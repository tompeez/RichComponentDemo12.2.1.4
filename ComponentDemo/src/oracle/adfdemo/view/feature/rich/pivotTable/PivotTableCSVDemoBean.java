/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableCSVDemoBean.java /main/8 2012/07/09 13:46:40 jievans Exp $ */

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
    jievans     12/23/10 - fix exceptions in demo
    jievans     08/27/10 - make Attribute Editor functional
    ahadi       10/17/08 - pivot table model
    ahadi       10/17/08 - pivot table model cleanup
    dahmed      09/26/08 - fix color bug when totals are turned on
    dahmed      08/07/08 - 
    jievans     01/22/08 - implement selection
    dahmed      06/14/07 - update demo and fix bug when data item is removed.
    dahmed      05/10/07 -
    jievans     03/26/07 - Creation
 */

package oracle.adfdemo.view.feature.rich.pivotTable;

import java.util.ArrayList;

import java.util.Date;

import java.util.HashMap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.BooleanConverter;
import javax.faces.convert.NumberConverter;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;
import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.faces.bi.component.pivotTable.CellFormat;
import oracle.adf.view.faces.bi.component.pivotTable.DataCellContext;
import oracle.adf.view.faces.bi.component.pivotTable.DataCellRange;
import oracle.adf.view.faces.bi.component.pivotTable.HeaderCellContext;
import oracle.adf.view.faces.bi.component.pivotTable.HeaderCellSelectionSet;
import oracle.adf.view.faces.bi.model.CellIndex;
import oracle.adf.view.faces.bi.model.CellKey;
import oracle.adf.view.faces.bi.model.DataCellIndex;
import oracle.adf.view.faces.bi.model.DataCellKey;

import oracle.adf.view.faces.bi.model.GraphDataModel;
import oracle.adf.view.faces.bi.model.HeaderCellKey;

import oracle.adf.view.faces.bi.model.PivotTableModel;
import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;
import oracle.adf.view.rich.model.NumberRange;

import oracle.dss.util.DataAccess;
import oracle.dss.util.DataDirector;

import oracle.dss.util.DataException;
import oracle.dss.util.DataMap;

import oracle.dss.util.MetadataMap;
import oracle.dss.util.QDR;
import oracle.dss.util.QDRMember;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableCSVDemoModel;

import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.fake.FakeDataSource;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.rowset.FilterSpec;

import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.rowset.RowsetDataSource;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.convert.DateTimeConverter;
import org.apache.myfaces.trinidad.model.UploadedFile;

/**
 * The PivotTableCSVDemoModel data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableCSVDemoBean.java /main/8 2012/07/09 13:46:40 jievans Exp $
 *  @author  dahmed
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableCSVDemoBean {
    
    public PivotTableCSVDemoBean() {
        m_dm = new PivotTableCSVDemoModel();
    }

    /**
     * Resets component properties that can become invalid upon changing the datasource.
     * Resets the startRow and StartColumn properties to null, and clears the selection.  
     * This should be called by handlers that change the data source, change the shape of the data, etc.
     */
    private void resetPivotTableState() {
        UIPivotTable pivotTable = getPivotTable();
        if (pivotTable != null) {
            FacesBean bean = pivotTable.getFacesBean();
            bean.setProperty(UIPivotTable.START_ROW_KEY, null);
            bean.setProperty(UIPivotTable.START_COLUMN_KEY, null);
            pivotTable.getSelection().clear();
        }
    }
        
    // In demos where we are bound to the editor rather than the PT, the setter never gets called, so that 
    // the ivar is null.  In these cases we get the component a different way, which requires that the 
    // component id be "demo:goodPT".
    public UIPivotTable getPivotTable() {
        return (m_pivotTable == null) 
               ? (UIPivotTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("demo:goodPT")
               : m_pivotTable;
    }

    public void setPivotTable(UIPivotTable pivotTable) {
        m_pivotTable = pivotTable;
    }

    protected String _getPivotTableDemoTitle() {
        String title = "Pivot Table - ";
        if (m_fileName == null)
            title += "Sample File Based Rowset Datasource";
        else
            title += "File Based Rowset Datasource ( " + m_fileName + " )";
        return title;
    }

    public String getPivotTableDemoTitle() {
        return "<b>" + _getPivotTableDemoTitle() + "</b>";
    }

    //////////////////////////////////////////////////////////////////
    // ROWSET METHODS

    public void loadData() {
        PivotTableCSVDemoModel model = getDataModel();
        try {
            model.setCSVFile(m_file);        
            model.loadData();
        } catch (Throwable t) {
            t.printStackTrace();
            if(!isCustomRowset())
                throw new RuntimeException("Upload of sample data file failed!");
            else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, 
                                   new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                                    "Displaying sample data file, since " + 
                                                    m_fileName + 
                                                    " is an invalid csv data file.", 
                                                    ""));
                // change to sample data, if upload failed!
                model.setCSVFile(null);
                model.loadData();
            }

        }
    }

    public void refreshData() throws DataException {
        resetPivotTableState();        
        getDataModel().refreshData();
    }
    
    public void handleRowsetDataSourceChange(ActionEvent event) {
        loadData();
    }

    
    public String select(ActionEvent e) {
        loadData();
        return null;
    }

    public void valueChanged(ValueChangeEvent e) {
        boolean bLimitChanged = false;
        QDR qdr = getCurrentValueQDR();
        bLimitChanged = getRowsetDataSource().valueChanged(qdr, e.getNewValue(), e.getOldValue());
        
        RequestContext afContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        if (bLimitChanged) {
            afContext.addPartialTarget(context.getViewRoot().findComponent("demo:rowsetOptions"));
            afContext.addPartialTarget(context.getViewRoot().findComponent("demo:goodPT"));
        }
        if (isGraphEnabled())
            afContext.addPartialTarget(context.getViewRoot().findComponent("demo:goodGraph"));
    }


    protected String getDataAttributeName(QDR qdr) {
        String dataAttribute = null;
        String measureDim = qdr.getMeasureDim();
        if (measureDim != null) {
            Object _dataAttribute = qdr.getDimMember(measureDim);
            if (_dataAttribute != null)
                dataAttribute = _dataAttribute.toString();
        }
        return dataAttribute;
    }

    public CellFormat getHeaderFormat(HeaderCellContext cxt) {       
       if(cxt.getEdge()==DataDirector.COLUMN_EDGE  && cxt.getLayerCount()-1==cxt.getLayer())
       {
           // show vertical text for color coded cells. 
           String dataAttribute = getDataAttributeName(cxt.getQDR());
           if (m_colorCodingDataAttributes.contains(dataAttribute))
           {
               String textStyle = "writing-mode:tb-rl;filter:flipV flipH;";           
               CellFormat format = new CellFormat(null, "", textStyle);
               return format;
           }
       }
       return null;
    }


    // use the LazyDataCellFormat to avoid format processing during
    // decode/validate/update phases, since stamps are fetched during every phase, but 
    // formats are only typically only fetched during rendering
    private class LazyDataCellFormat extends CellFormat 
                                                  {
         public LazyDataCellFormat(DataCellContext dataCellContext, 
                                   String dataAttribute, 
                                   Set<String> colorCodingDataAttributes, 
                                   Set<String> dataBarsDataAttributes){ 
             _dataCellContext = dataCellContext;
             _dataAttribute = dataAttribute;
             _colorCodingDataAttributes = colorCodingDataAttributes;
             _dataBarDataAttributes = dataBarsDataAttributes;
         }
         
         public String getStyle() {
             Object _value = getDataDependentProperty("style");
             if(_value !=null)
                 return _value.toString();                                          
             return null;
         }
         
         public String getTextStyle() {
             Object _value = getDataDependentProperty("textStyle");
             if(_value !=null)
                 return _value.toString();                                          
             return null;             
         }
         
         
         Object getDataDependentProperty(String propertyName) {
             if(_dataDependentProperties==null)
             {   // calculate data dependent properties on demand. 
                 _dataDependentProperties = new HashMap<String, Object>();
                 if(_dataAttribute!=null) {

                     // get the min/max threshold based on the data attribute being displayed by the cell
                     double minValue = 0;
                     double maxValue = 0;

                     try {
                         minValue = ((Number)_dataCellContext.getValue("dataAttributeMinimum")).doubleValue();
                         maxValue = ((Number)_dataCellContext.getValue("dataAttributeMaximum")).doubleValue();
                     } catch (DataException e) {
                         e.printStackTrace();
                     }

                     // format the cell based on 5 buckets / percentages
                     String inlineStyle = "";
                     String textStyle = "";
                     Object _value = _dataCellContext.getValue();
                     if(_value instanceof Number)
                     {
                         Number value = (Number)_value;                         
                         if (_colorCodingDataAttributes.contains(_dataAttribute)) {
                             if (value != null) {
                                 double range = maxValue - minValue;
                                 double diffValue = value.doubleValue() - minValue;
                                 double percentage = diffValue / range;
                                 
                                 int numColors = PivotTableCSVDemoBean.BLUEGREEN_COLOR_RAMP.length;
                                 double colorIndex = numColors*percentage;
                                 int _colorIndex = (int)Math.round(colorIndex)-1;
                                 if(_colorIndex<0)
                                     _colorIndex=0;
                                 else if(_colorIndex>numColors-1)
                                     _colorIndex = numColors-1;

                                 String color = PivotTableCSVDemoBean.BLUEGREEN_COLOR_RAMP[_colorIndex];
                                 inlineStyle = "background-color:" + color + ";";
                                 textStyle = "color:" + color + ";";
                             } else {
                                 inlineStyle = "background-color:#EEEEEE;";
                                 textStyle = "color:#EEEEEE;";
                             }
                         } else if (_dataBarDataAttributes.contains(_dataAttribute)) {
    
                             if (value != null) {
                                 double range = maxValue - minValue;
                                 double diffValue = value.doubleValue() - minValue;
                                 double percentage = diffValue / range;
                                 int width = new Double(percentage * 100).intValue();
                                 int margin = 100 - width;
                                 inlineStyle = "text-align:left;vertical-align:middle;";
                                 textStyle = 
                                         "width:" + width + "px;height:12px;margin-right:" + 
                                         margin + "px;";
                             } else {
                                 textStyle = "width:0px;height:0px;";
                             }
                         }
                         _dataDependentProperties.put("style",inlineStyle);
                         _dataDependentProperties.put("textStyle",textStyle);
                     }
                 }
             }
             return _dataDependentProperties.get(propertyName);                                                                  
         }
         
         String _dataAttribute;
         Set<String> _colorCodingDataAttributes;
         Set<String> _dataBarDataAttributes;         
         private DataCellContext _dataCellContext;
         HashMap<String,Object> _dataDependentProperties;
    }
    
    public CellFormat getDataFormat(DataCellContext cxt) {
        // Calculate the style and textStyle on demand in the LazyDataCellFormat
        // since:
        // - the style and textStyle atributes are only needed during rendering 
        // - getDataFormat can  be called during any JSF phase ( decode, validate, update, invoke application ) 
        // Instead of calculating the cell styling in this routine, delay the calculation
        // of cell styles until the stamp requests them ( e.g cellFormat.getStyle()/getTextStyle() are called ).
        // The delayed calculation of formats is done inside the LazyDataCellFormat class. 
        //
        // Please note that the LazyDataCellFormat class lazily calculates attributes such as style/textStyle, 
        // since they are typically not requested until rendering, while CellFormats are requested during 
        // every JSF phase ( decode, validate, update model, invoke application ) 
        CellFormat cellFormat = new LazyDataCellFormat(cxt,
                                            getDataAttributeName(cxt.getQDR()), 
                                            m_colorCodingDataAttributes,
                                            m_dataBarDataAttributes);
        Object value = cxt.getValue();
        if(value instanceof Number)
        {
            NumberConverter convert = new NumberConverter();
            convert.setMinFractionDigits(3);
            cellFormat.setConverter(convert);
        } else if(value instanceof Date) 
        {
            DateTimeConverter convert = new DateTimeConverter();
            cellFormat.setConverter(convert);
        } else if(value instanceof Boolean) {
            BooleanConverter convert = new BooleanConverter();
            cellFormat.setConverter(convert);            
        } 
        return cellFormat;
    }

    public Set<String> getDataBarDataAttributeSet() {
        return m_dataBarDataAttributes;
    }

    public Set<String> getColorCodingDataAttributeSet() {
        return m_colorCodingDataAttributes;
    }
    public String getShowTextDescription() {
        if (isTextEnabled())
            return "Text displayed for " + getSelectedDataAttribute();
        else
            return "Show Text for " + getSelectedDataAttribute();
    }

    public String getShowColorsDescription() {
        if (isColorsEnabled())
            return "Colors displayed for " + getSelectedDataAttribute();
        else
            return "Show Colors for " + getSelectedDataAttribute();
    }

    public String getShowBarsDescription() {
        if (isBarsEnabled())
            return "Bars displayed for " + getSelectedDataAttribute();
        else
            return "Show Bars for " + getSelectedDataAttribute();
    }
    
    public RichCommandMenuItem getTextMenuItem() {
        return m_textMenuItem;
    }

    public void setTextMenuItem(RichCommandMenuItem menuItem) {
        m_textMenuItem = menuItem;
    }

    public RichCommandMenuItem getColorsMenuItem() {
        return m_colorsMenuItem;
    }

    public void setColorsMenuItem(RichCommandMenuItem menuItem) {
        m_colorsMenuItem = menuItem;
    }

    public RichCommandMenuItem getBarsMenuItem() {
        return m_barsMenuItem;
    }

    public void setBarsMenuItem(RichCommandMenuItem menuItem) {
        m_barsMenuItem = menuItem;
    }

    public boolean isTextEnabled() {
        String dataAttr = getSelectedDataAttribute();
        return !(m_colorCodingDataAttributes.contains(dataAttr) || 
                 m_dataBarDataAttributes.contains(dataAttr));
    }

    public boolean isFormatted() {
        return m_colorCodingDataAttributes.size() > 0 || 
            m_dataBarDataAttributes.size() > 0;
    }

    public boolean isColorsEnabled() {
        String dataAttr = getSelectedDataAttribute();
        return m_colorCodingDataAttributes.contains(dataAttr);
    }

    public boolean isBarsEnabled() {
        String dataAttr = getSelectedDataAttribute();
        return m_dataBarDataAttributes.contains(dataAttr);
    }

    protected void clearLocalMenuItemValues() {
        // reset the selected property to null, so that it still fetches its values from the ValueExpression / this model 
        m_textMenuItem.getFacesBean().setProperty(RichCommandMenuItem.SELECTED_KEY,null);
        m_colorsMenuItem.getFacesBean().setProperty(RichCommandMenuItem.SELECTED_KEY,null);
        m_barsMenuItem.getFacesBean().setProperty(RichCommandMenuItem.SELECTED_KEY,null);        
    }

    public void showText(ActionEvent event) {
        String dataAttr = getSelectedDataAttribute();
        m_colorCodingDataAttributes.remove(dataAttr);
        m_dataBarDataAttributes.remove(dataAttr);

        clearLocalMenuItemValues();
    }

    public void showColors(ActionEvent event) {
        String dataAttr = getSelectedDataAttribute();
        m_colorCodingDataAttributes.add(dataAttr);
        m_dataBarDataAttributes.remove(dataAttr);

        clearLocalMenuItemValues();
    }

    public void showBars(ActionEvent event) {
        String dataAttr = getSelectedDataAttribute();
        m_colorCodingDataAttributes.remove(dataAttr);
        m_dataBarDataAttributes.add(dataAttr);

        clearLocalMenuItemValues();
    }

    public void clearAllFormatting(ActionEvent event) {
        m_colorCodingDataAttributes.clear();
        m_dataBarDataAttributes.clear();
    }

    public boolean isEditEnabled() {
        return m_editEnabled;
    }

    public void toggleEditEnabled(ActionEvent event) {
        m_editEnabled = !m_editEnabled;
    }

    public void toggleStatusBarRendered(ActionEvent event) {
        getPivotTable().setStatusBarRendered(!getPivotTable().isStatusBarRendered());
    }

    protected String getSelectedDataAttribute() {
        UIPivotTable pt = getPivotTable();
        if (pt == null)
            return null;
        HeaderCellSelectionSet headerCells = null;
        int edge = -1;
        if (pt.getSelection().getColumnHeaderCells().size() > 0) {
            headerCells = pt.getSelection().getColumnHeaderCells();
            edge = DataDirector.COLUMN_EDGE;
        } else if (pt.getSelection().getRowHeaderCells().size() > 0) {
            headerCells = pt.getSelection().getRowHeaderCells();
            edge = DataDirector.ROW_EDGE;
        }

        // find the dataAttribute in the selection
        String dataAttribute = null;

        // make sure that there is a selected header cell range. 
        if (edge != -1 && headerCells.size() > 0) {

            try {
                DataAccess da = pt.getDataModel().getDataAccess();
                Iterator<HeaderCellKey> iter = headerCells.cellKeyIterator();
                while (iter.hasNext()) {
                    HeaderCellKey headerCellKey = iter.next();
                    String _dataAttribute = getDataAttribute(headerCellKey);
                    if (_dataAttribute != null) {
                        // don't allow multiple dataAttributes to be selected at one time. 
                        if ((dataAttribute != null) && 
                            !dataAttribute.equals(_dataAttribute))
                            return null;
                        dataAttribute = _dataAttribute;
                    }
                }
            } catch (DataException e) {
                e.printStackTrace();
            }
        }
        return dataAttribute;
    }

    protected String getDataAttribute(HeaderCellKey headerCellKey) {
        return getDataAttribute(headerCellKey.getQdr());
    }

    protected String getDataAttribute(QDR qdr) {
        String measureDim = qdr.getMeasureDim();
        // TODO - fix hardcoding of the measure dimension name.  Right now selection QDRs are not guaranteed to 
        // have populated the measure dim, so the fix is to always populate the measure dim properly. 
        //        QDRMember member = qdr.getDimMember(measureDim);
        QDRMember member = qdr.getDimMember("MeasDim");
        if (member != null)
            return member.toString();
        else
            return null;
    }

    public String getSelectedDataY1Title() {
        Set<DataCellRange> selectedData = getPivotTable().getSelection().getDataCells();
        DataAccess da= getPivotTable().getDataModel().getDataAccess();
        String title = "";
        if(selectedData.size()==1)
        {
            DataCellRange range = selectedData.iterator().next();
            DataCellIndex start = range.getStartIndex(getPivotTable().getDataModel());
            DataCellIndex end = range.getEndIndex(getPivotTable().getDataModel());
            int startCol = start.getColumn();
            int endCol = (end==null) ? startCol : end.getColumn();
            if(endCol-startCol>0)
            {
                try {
                   title = (String)da.getSliceLabel(DataDirector.COLUMN_EDGE, startCol+1, MetadataMap.METADATA_LONGLABEL);                        
                } catch(DataException e) {
                    e.printStackTrace();
                }
            }                 
        }
        return title;
    }
    
    public String getSelectedDataX1Title() {
        Set<DataCellRange> selectedData = getPivotTable().getSelection().getDataCells();
        DataAccess da= getPivotTable().getDataModel().getDataAccess();
        String title = "";
        if(selectedData.size()==1)
        {
            DataCellRange range = selectedData.iterator().next();
            int startCol = range.getStartIndex(getPivotTable().getDataModel()).getColumn();
            DataCellIndex end = range.getEndIndex(getPivotTable().getDataModel());
            int endCol = (end==null) ? startCol : end.getColumn();
            if(endCol-startCol>0)
            {
                try {
                   title = (String)da.getSliceLabel(DataDirector.COLUMN_EDGE, startCol, MetadataMap.METADATA_LONGLABEL);                        
                } catch(DataException e) {
                    e.printStackTrace();
                }
            } 
        }
        return title;        
    }
    
    public boolean isGraphDataAvailable() {
        UIPivotTable pt = getPivotTable();
        Set<DataCellRange> selectedData = pt.getSelection().getDataCells();
        if(selectedData.size()==1)
        {
            DataCellRange range = selectedData.iterator().next();
            // only display 3 data values in a graph 
            return getNumGroups(range)<=3;
        }
        return false;
    }

    protected int getNumGroups(DataCellRange range) {
        DataCellIndex start = range.getStartIndex(getPivotTable().getDataModel());
        DataCellIndex end = range.getEndIndex(getPivotTable().getDataModel());
        int startCol = start.getColumn();
        int endCol = (end==null) ? startCol : end.getColumn();
        return endCol-startCol+1;        
    }

    public int getSelectedDataGraphType() {
        Set<DataCellRange> selectedData = getPivotTable().getSelection().getDataCells();
        if(selectedData.size()==1)
        {
            DataCellRange range = selectedData.iterator().next();
            int numGroups = getNumGroups(range);
            if(numGroups==1)
                return UIGraph.BAR_VERT_CLUST;
            else if(numGroups==2)
                return UIGraph.SCATTER;
            else if(numGroups==3)
                return UIGraph.BUBBLE;
        }
        return UIGraph.BAR_HORIZ_CLUST;                    
    }
    
    public List getSelectedGraphData() {
        ArrayList list = new ArrayList();
        
        UIPivotTable pt = getPivotTable();
        DataAccess da = pt.getDataModel().getDataAccess();
        Set<DataCellRange> selectedData = pt.getSelection().getDataCells();
        DataCellRange range = selectedData.iterator().next();
        DataCellIndex start = range.getStartIndex(getPivotTable().getDataModel());
        DataCellIndex end = range.getEndIndex(getPivotTable().getDataModel());        
        int startRow = start.getRow();
        int startCol = start.getColumn();
        int endRow = (end==null) ? startRow : end.getRow();
        int endCol = (end==null) ? startCol : end.getColumn();
        
        try  {                         
            for(int r=startRow;r<=endRow;r++) {
                String rowLabel = (String)da.getSliceLabel(DataDirector.ROW_EDGE, r, MetadataMap.METADATA_LONGLABEL);
                for(int c=startCol;c<=endCol;c++) {
                    String colLabel = (String)da.getSliceLabel(DataDirector.COLUMN_EDGE, c, MetadataMap.METADATA_LONGLABEL);                        
                    Object value = da.getValue(r,c,DataMap.DATA_VALUE);
                    list.add(new Object[]{colLabel, rowLabel, value});
                }
            }
        } catch ( DataException e) {
            e.printStackTrace();
        }
        return list;

    }

    public String getStampFacet() {
        String dataAttr = getCurrentDataAttribute();
        // if the current data cell should show a data bar, then change the facet that is rendered in the cell!
        if (m_dataBarDataAttributes.contains(dataAttr))
            return "image";
        else if (m_colorCodingDataAttributes.contains(dataAttr))
            return "color";
        else {
            if (isEditEnabled())
                return "inputText";
            else
                return "text";
        }
    }

    public QDR getCurrentValueQDR() {
        UIPivotTable pt = getPivotTable();
        CellIndex cellIndex = pt.getCellIndex();

        DataCellIndex dataCellIndex = (DataCellIndex)cellIndex;
        try {
            return pt.getDataModel().getDataAccess().getValueQDR(dataCellIndex.getRow(), 
                                                                       dataCellIndex.getColumn(), 
                                                                       DataAccess.QDR_WITH_PAGE);
        } catch (DataException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCurrentDataAttribute() {
        UIPivotTable pt = getPivotTable();
        CellKey cellKey = pt.getCellKey();
        String dataAttr = null;
        if (cellKey instanceof DataCellKey) {
            DataCellKey dataCellKey = (DataCellKey)cellKey;
            // try to find the data attribute on the column edge
            dataAttr = getDataAttributeName(dataCellKey.getColumnKey());

            // otherwise try to find the data attribute on the row edge
            if (dataAttr == null)
                dataAttr = getDataAttributeName(dataCellKey.getRowKey());
        }

        // if the dataAttr is null, then the dataAttr must be on the page edge, so try to find it there!
        if (dataAttr == null) {
            try {
                DataAccess da = pt.getDataModel().getDataAccess();
                int pageSlice = da.getEdgeCurrentSlice(DataDirector.PAGE_EDGE);
                QDR qdr = 
                    da.getSliceQDR(DataDirector.PAGE_EDGE, pageSlice, DataAccess.QDR_WITH_PAGE);
                dataAttr = getDataAttribute(qdr);
            } catch (DataException e) {
                e.printStackTrace();
            }
        }


        return dataAttr;
    }

    public String getSelectionPopupFacet() {
        if (isDataAttributeSelected())
            return "dataAttributeMenu";
        else if (isDataSelected())
            return "dataCellsMenu";
        else
            return "empty";
    }

    public boolean isDataAttributeSelected() {
        if (isHeaderSelected()) {
            String dataAttr = getSelectedDataAttribute();
            if (dataAttr != null)
                return true;
        }
        return false;
    }

    public boolean isHeaderSelected() {
        UIPivotTable pt = getPivotTable();
        if ((pt.getSelection().getColumnHeaderCells().size() > 0) || 
            (pt.getSelection().getRowHeaderCells().size() > 0))
            return true;
        return false;
    }

    public boolean isDataSelected() {
        UIPivotTable pt = getPivotTable();
        if (pt.getSelection().getDataCells().size() > 0)
            return true;
        return false;
    }

    public String getRowsetDataSourceDescription() {
        String desc = "<b>File:</b> ";
        if (m_fileName == null)
            return desc + "Sample CSV File";
        else
            return desc + m_fileName;
    }

    public String getSampleRowsetButtonText() {
        return "Update Rowset CSV File";
    }

    public boolean isCustomRowset() {        
        return m_customRowset;
    }

    public void setCustomRowset(boolean customRowset) {
         m_customRowset = customRowset;
         if(!customRowset)
            setFile(null);
    }

    public boolean isNotCustomRowset() {
        return !isCustomRowset();
    }

    public void setNotCustomRowset(boolean notCustomRowset) {
        setCustomRowset(!notCustomRowset);
    }

    public void setDataRendered_0(boolean dataRendered) {
    }

    public void setDataLabel_0(String dataLabel) {
    }

    public void setDataMinimum_0(Number dataMinimum) {
    }

    public void setDataMaximum_0(Number dataMaximum) {
    }

    public void setDataMajorIncrement_0(Number dataMajorIncrement) {
    }

    public void setDataMinorIncrement_0(Number dataMinorIncrement) {
    }

    public boolean isDataRendered_0() {
        return isDataAvailable(0);
    }

    public String getDataLabel_0() {
        return getDataLabel(0);
    }

    public Number getDataMinimum_0() {
        return getDataMinimum(0);
    }

    public Number getDataMaximum_0() {
        return getDataMaximum(0);
    }

    public Number getDataMajorIncrement_0() {
        return getDataMajorIncrement(0);
    }

    public Number getDataMinorIncrement_0() {
        return getDataMinorIncrement(0);
    }

    public NumberRange getDataValue_0() {
        return getDataValue(0);
    }

    public void setDataValue_0(NumberRange value) {
        setDataValue(0, value);
    }

    public boolean isDataDisabled_0() {
        return !isDataEnabled_0();
    }

    public boolean isDataEnabled_0() {
        return isDataEnabled(0);
    }

    public void setDataEnabled_0(boolean value) {
        setDataEnabled(0, value);
    }

    public void setDataRendered_1(boolean dataRendered) {
    }

    public void setDataLabel_1(String dataLabel) {
    }

    public void setDataMinimum_1(Number dataMinimum) {
    }

    public void setDataMaximum_1(Number dataMaximum) {
    }

    public void setDataMajorIncrement_1(Number dataMajorIncrement) {
    }

    public void setDataMinorIncrement_1(Number dataMinorIncrement) {
    }

    public boolean isDataRendered_1() {
        return isDataAvailable(1);
    }

    public String getDataLabel_1() {
        return getDataLabel(1);
    }

    public Number getDataMinimum_1() {
        return getDataMinimum(1);
    }

    public Number getDataMaximum_1() {
        return getDataMaximum(1);
    }

    public Number getDataMajorIncrement_1() {
        return getDataMajorIncrement(1);
    }

    public Number getDataMinorIncrement_1() {
        return getDataMinorIncrement(1);
    }

    public NumberRange getDataValue_1() {
        return getDataValue(1);
    }

    public void setDataValue_1(NumberRange value) {
        setDataValue(1, value);
    }

    public boolean isDataDisabled_1() {
        return !isDataEnabled_1();
    }

    public boolean isDataEnabled_1() {
        return isDataEnabled(1);
    }

    public void setDataEnabled_1(boolean value) {
        setDataEnabled(1, value);
    }

    public void setDataRendered_2(boolean dataRendered) {
    }

    public void setDataLabel_2(String dataLabel) {
    }

    public void setDataMinimum_2(Number dataMinimum) {
    }

    public void setDataMaximum_2(Number dataMaximum) {
    }

    public void setDataMajorIncrement_2(Number dataMajorIncrement) {
    }

    public void setDataMinorIncrement_2(Number dataMinorIncrement) {
    }

    public boolean isDataRendered_2() {
        return isDataAvailable(2);
    }

    public String getDataLabel_2() {
        return getDataLabel(2);
    }

    public Number getDataMinimum_2() {
        return getDataMinimum(2);
    }

    public Number getDataMaximum_2() {
        return getDataMaximum(2);
    }

    public Number getDataMajorIncrement_2() {
        return getDataMajorIncrement(2);
    }

    public Number getDataMinorIncrement_2() {
        return getDataMinorIncrement(2);
    }

    public NumberRange getDataValue_2() {
        return getDataValue(2);
    }

    public void setDataValue_2(NumberRange value) {
        setDataValue(2, value);
    }

    public boolean isDataDisabled_2() {
        return !isDataEnabled_2();
    }

    public boolean isDataEnabled_2() {
        return isDataEnabled(2);
    }

    public void setDataEnabled_2(boolean value) {
        setDataEnabled(2, value);
    }

    public boolean isDataAvailable(int index) {
        return getRowsetDataSource().getDataAttributes().size() > index;
    }

    public String getDataLabel(int index) {
        FilterSpec filter = getFilterSpec(index);
        if (filter != null)
            return filter.getLabel();
        else
            return "";
    }

    public Number getDataMinimum(int index) {
        FilterSpec filter = getFilterSpec(index);
        if (filter != null)
            return filter.getMin();
        else
            return 0;
    }

    public Number getDataMaximum(int index) {
        FilterSpec filter = getFilterSpec(index);
        if (filter != null)
            return filter.getMax();
        else
            return 0;
    }

    public Number getDataMajorIncrement(int index) {
        FilterSpec filter = getFilterSpec(index);
        if (filter != null)
            return (filter.getMax().doubleValue() - 
                    filter.getMin().doubleValue()) / 2;
        else
            return 0;
    }

    public Number getDataMinorIncrement(int index) {
        FilterSpec filter = getFilterSpec(index);
        if (filter != null)
            return (filter.getMax().doubleValue() - 
                    filter.getMin().doubleValue()) / 20;
        else
            return 0;
    }

    public void setDataValue(int index, NumberRange value) {
        FilterSpec filter = getFilterSpec(index);
        if (filter != null) {
            long minValue = value.getMinimum().longValue();
            long maxValue = value.getMaximum().longValue();
            boolean minChanged = 
                (minValue != filter.getRangeMin().longValue());
            boolean maxChanged = 
                (maxValue != filter.getRangeMax().longValue());
            boolean bFilterChanged = (minChanged || maxChanged);
            // don't update the datasource unless the filter values changed. 
            if (bFilterChanged) {
                filter.setRangeMin(minValue);
                filter.setRangeMax(maxValue);
                try {
                    refreshData();
                } catch(DataException e) {
                    Throwable t = new Throwable("Filtered rowset failed to load.", e);
                }
            }
        }
    }

    public NumberRange getDataValue(int index) {
        FilterSpec filter = getFilterSpec(index);
        if (filter != null)
            return new NumberRange(filter.getRangeMin(), filter.getRangeMax());
        else
            return new NumberRange(0, 0);
    }

    public void setDataEnabled(int index, boolean dataEnabled) {
        FilterSpec filter = getFilterSpec(index);
        if (filter != null) {
            filter.setDataEnabled(dataEnabled);
            try {
                refreshData();
            } catch(DataException e) {
                Throwable t = new Throwable("Filtered rowset failed to load.", e);
            }
        }
    }

    FilterSpec getFilterSpec(int index) {
        if (!isDataAvailable(index))
            return null;
        String dataAttribute = 
            getRowsetDataSource().getDataAttributes().get(index);
        return getRowsetDataSource().getFilterSpecs().get(dataAttribute);
    }

    public boolean isDataEnabled(int index) {
        if (!isDataAvailable(index))
            return Boolean.FALSE;
        String dataAttribute = 
            getRowsetDataSource().getDataAttributes().get(index);
        return getRowsetDataSource().getFilterSpecs().get(dataAttribute).isDataEnabled();
    }

    public void setNumberConverter(NumberConverter converter) {
    }

    public NumberConverter getNumberConverter() {
        if (m_converter == null) {
            m_converter = new NumberConverter();
            m_converter.setIntegerOnly(true);
            m_converter.setMinFractionDigits(1);
        }
        return m_converter;
    }

    public boolean isGraphEnabled() {
        return m_graphEnabled;
    }

    public void setGraphEnabled(boolean graphEnabled) {
        m_graphEnabled = graphEnabled;
    }

    public boolean isGraphDisabled() {
        return !isGraphEnabled();
    }

    public void setGraphDisabled(boolean graphDisabled) {
    }

    public Integer getGraphType() {
        return m_graphType;
    }

    public void setGraphType(Integer graphType) {
        m_graphType = graphType;
    }

    public String getGraphTypeValue() {
        switch (m_graphType) {
        case UIGraph.BAR_HORIZ_CLUST:
            return "BAR_HORIZ_CLUST";
        case UIGraph.BAR_HORIZ_CLUST_2Y:
            return "BAR_HORIZ_CLUST_2Y";
        case UIGraph.BAR_HORIZ_STACK_2Y:
            return "BAR_HORIZ_STACK_2Y";
        case UIGraph.BAR_HORIZ_STACK:
            return "BAR_HORIZ_STACK";
        case UIGraph.BAR_VERT_CLUST:
            return "BAR_VERT_CLUST";
        case UIGraph.BAR_VERT_CLUST2Y:
            return "BAR_VERT_CLUST2Y";
        case UIGraph.BAR_VERT_STACK2Y:
            return "BAR_VERT_STACK2Y";
        case UIGraph.BAR_VERT_STACK:
            return "BAR_VERT_STACK";
        case UIGraph.LINE_VERT_ABS:
            return "LINE_VERT_ABS";
        case UIGraph.LINE_HORIZ_ABS:
            return "LINE_HORIZ_ABS";
        case UIGraph.LINE_HORIZ_STACK:
            return "LINE_HORIZ_STACK";
        case UIGraph.PIE:
            return "PIE";
        case UIGraph.PIE_MULTI:
            return "PIE_MULTI";
        case UIGraph.AREA_HORIZ_STACK:
            return "AREA_HORIZ_STACK";
        case UIGraph.SCATTER:
            return "SCATTER";
        case UIGraph.SCATTER_2Y:
            return "SCATTER_2Y";
        case UIGraph.BUBBLE:
            return "BUBBLE";
        case UIGraph.BUBBLE_2Y:
            return "BUBBLE_2Y";
        }
        return "BAR_HORIZ_CLUST";
    }

    public void setGraphTypeValue(String graphType) {
        if (graphType.equals("BAR_HORIZ_CLUST"))
            m_graphType = UIGraph.BAR_HORIZ_CLUST;
        if (graphType.equals("BAR_HORIZ_CLUST_2Y"))
            m_graphType = UIGraph.BAR_HORIZ_CLUST_2Y;
        if (graphType.equals("BAR_HORIZ_STACK_2Y"))
            m_graphType = UIGraph.BAR_HORIZ_STACK_2Y;
        else if (graphType.equals("BAR_HORIZ_STACK"))
            m_graphType = UIGraph.BAR_HORIZ_STACK;
        else if (graphType.equals("BAR_VERT_CLUST2Y"))
            m_graphType = UIGraph.BAR_VERT_CLUST2Y;
        else if (graphType.equals("BAR_VERT_STACK2Y"))
            m_graphType = UIGraph.BAR_VERT_STACK2Y;
        else if (graphType.equals("BAR_VERT_CLUST"))
            m_graphType = UIGraph.BAR_VERT_CLUST;
        else if (graphType.equals("BAR_VERT_STACK"))
            m_graphType = UIGraph.BAR_VERT_STACK;
        else if (graphType.equals("LINE_VERT_ABS"))
            m_graphType = UIGraph.LINE_VERT_ABS;
        else if (graphType.equals("LINE_HORIZ_ABS"))
            m_graphType = UIGraph.LINE_HORIZ_ABS;
        else if (graphType.equals("LINE_HORIZ_STACK"))
            m_graphType = UIGraph.LINE_HORIZ_STACK;
        else if (graphType.equals("AREA_HORIZ_STACK"))
            m_graphType = UIGraph.AREA_HORIZ_STACK;
        else if (graphType.equals("PIE"))
            m_graphType = UIGraph.PIE;
        else if (graphType.equals("PIE_MULTI"))
            m_graphType = UIGraph.PIE_MULTI;
        else if (graphType.equals("SCATTER"))
            m_graphType = UIGraph.SCATTER;
        else if (graphType.equals("SCATTER_2Y"))
            m_graphType = UIGraph.SCATTER_2Y;
        else if (graphType.equals("BUBBLE"))
            m_graphType = UIGraph.BUBBLE;
        else if (graphType.equals("BUBBLE_2Y"))
            m_graphType = UIGraph.BUBBLE_2Y;
        else
            m_graphType = UIGraph.BAR_HORIZ_CLUST;
    }

    public boolean isTotalsEnabled() {
        return getRowsetDataSource().isTotalsEnabled();
    }

    public void setTotalsEnabled(boolean totalsEnabled) {
        if (getRowsetDataSource().isTotalsEnabled() != totalsEnabled)
        {
            getRowsetDataSource().setTotalsEnabled(totalsEnabled);
            try {
                refreshData();
            } catch(DataException e) {
                Throwable t = new Throwable("Enable totals - rowset failed to load.", e);
            }            
        }
    }

    public UploadedFile getFile() {
        return null; 
    }

    public void setFile(UploadedFile file) {
        m_file = file;
        if (m_file != null)
            m_fileName = m_file.getFilename();
    }

    public PivotTableCSVDemoModel getDataModel() {
        return m_dm;
    }

    public PivotTableModel getFakeDataModel() {
        PivotTableModel dm = new PivotTableModel();
        dm.setDataSource(new FakeDataSource());
        return dm;
    }

    public GraphDataModel getGraphDataModel() {
        return new GraphDataModel(getDataModel().getDataSource());
    }

    public RowsetDataSource getRowsetDataSource() {
        return (RowsetDataSource)getDataModel().getDataSource();
    }

    UIPivotTable m_pivotTable = null;
    PivotTableCSVDemoModel m_dm = null;
    private UploadedFile m_file;
    private String m_fileName = null;
    private boolean m_graphEnabled = false;
    private int m_graphType = UIGraph.BAR_VERT_CLUST;
    private NumberConverter m_converter = null;
    private boolean m_customRowset = false;
    

    protected static final String[] BLUEGREEN_COLOR_RAMP = new String[]{"#FFFFFF",
                                      "#FFFFCC",
                                      "#FFFF99", 
                                      "#FFFF66",
                                      "#FFFF33",
                                      "#FFFF00",
                                      "#CCFFFF",
                                      "#CCFFCC",
                                      "#CCFF99",
                                      "#CCFF66",
                                      "#CCFF33",
                                      "#CCFF00",
                                      "#99FFFF",
                                      "#99FFCC",
                                      "#99FF99",
                                      "#99FF66",
                                      "#99FF33",
                                      "#99FF00",
                                      "#66FFFF",
                                      "#66FFCC",
                                      "#66FF99",
                                      "#66FF66",
                                      "#66FF33",
                                      "#66FF00",
                                      "#33FFFF",
                                      "#33FFCC",
                                      "#33FF99",
                                      "#33FF66",
                                      "#33FF33",
                                      "#33FF00",
                                      "#00FFFF",
                                      "#00FFCC",
                                      "#00FF99",
                                      "#00FF66",
                                      "#00FF33",
                                      "#00FF00"};

    protected Set<String> m_colorCodingDataAttributes = new HashSet<String>();
    protected Set<String> m_dataBarDataAttributes = new HashSet<String>();
    protected RichCommandMenuItem m_textMenuItem = null;
    protected RichCommandMenuItem m_barsMenuItem = null;
    protected RichCommandMenuItem m_colorsMenuItem = null;

    protected boolean m_editEnabled = false;

}
