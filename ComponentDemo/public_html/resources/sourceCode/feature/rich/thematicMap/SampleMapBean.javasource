package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.faces.bi.component.thematicMap.UIAreaDataLayer;
import oracle.adf.view.faces.bi.component.thematicMap.UIAreaLayer;
import oracle.adf.view.faces.bi.component.thematicMap.UIPointDataLayer;
import oracle.adf.view.faces.bi.component.thematicMap.UIThematicMap;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.datatransfer.SimpleTransferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adfdemo.view.feature.rich.thematicMap.UnemploymentData.ColorData;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;


public class SampleMapBean {
    private CollectionModel m_colorModel;
    private CollectionModel m_pointModel;
    private CollectionModel m_subRegionModel;
    private CollectionModel m_ddRegionTableModel;
    private CollectionModel m_ddMarkerTableModel;    
    private CollectionModel m_attributeGroupModel;
    private CollectionModel m_countyModel;
    private CollectionModel m_cityModel;
    private CollectionModel m_markerThemeModel;
    private CollectionModel m_latlongModel;
    private CollectionModel m_drillableState;
    private CollectionModel m_drillableCounty;
    private CollectionModel m_drillableCity;

    private Object _o = null;
    private String animationOnDisplay = "zoomIn";

    private boolean _applyPan = false;
    private boolean _applyZoom = false;
    private boolean _applyZoomToFit = false;
    
    private Set<String> _featuresOff = null;

    private List m_patternNames = new ArrayList();
    private String m_pattern = "smallDiagonalLeft";
    private List m_shapeNames;
    private String m_shape1 = "diamond";
    private String m_shape2 = "plus";
    private String m_drillBehavior = "none";
    private boolean m_maintainDrillState = false;

    public SampleMapBean() {
      super();

      m_patternNames = new ArrayList<SelectItem>();
      String[] patterns = {"smallDiagonalLeft", "smallChecker", "smallDiagonalRight", "smallTriangle", "smallCrosshatch", "smallDiamond", 
                           "largeDiagonalLeft", "largeChecker", "largeDiagonalRight", "largeTriangle", "largeCrosshatch", "largeDiamond"};
      for(String pattern : patterns) {
          SelectItem item = new SelectItem(pattern,pattern);
          m_patternNames.add(item);
      }
      
      m_shapeNames = new ArrayList<SelectItem>();
      String[] shapes = {"human", "plus", "diamond", "square", "circle", "triangleDown", "triangleUp"};
      for (String shape: shapes) {
          SelectItem item = new SelectItem(shape, shape);
          m_shapeNames.add(item);
      }
      
      this.getCountyModel();
      m_markerThemeModel = ModelUtils.toCollectionModel(new ArrayList());
      m_latlongModel = ModelUtils.toCollectionModel(new ArrayList());
    }

    public CollectionModel getAttributeGroupModel() {
        if (m_attributeGroupModel == null) {
            m_attributeGroupModel =
                    ModelUtils.toCollectionModel(new SampleAttributeGroupData());
        }
        return m_attributeGroupModel;
    }


    public CollectionModel getColorModel() {
        if (m_colorModel == null) {
            m_colorModel = ModelUtils.toCollectionModel(new SampleColorData());
        }
        return m_colorModel;
    }

    public CollectionModel getPointModel() {
        if (m_pointModel == null) {
            m_pointModel = ModelUtils.toCollectionModel(new SamplePointData());
        }

        return m_pointModel;
    }

    public CollectionModel getSubRegionModel() {
        if (m_subRegionModel == null) {
            m_subRegionModel =
                    ModelUtils.toCollectionModel(new SampleSubRegionData());
        }

        return m_subRegionModel;
    }

    public CollectionModel getCountyModel() {
        if (m_countyModel == null) {
            m_countyModel = ModelUtils.toCollectionModel(new SampleCountyData());
        }
        
        return m_countyModel;
    }
    
    public CollectionModel getCityModel() {
        if (m_cityModel == null) {
            m_cityModel = ModelUtils.toCollectionModel(new SampleCityData());
        }
        
        return m_cityModel;
    }   
    
    public CollectionModel getDragAndDropMarkerTableModel() {
        if (m_ddMarkerTableModel == null) {
            m_ddMarkerTableModel =
                    ModelUtils.toCollectionModel(new ArrayList<SampleColorData.ColorData>());
        }

        return m_ddMarkerTableModel;
    }

    public CollectionModel getDragAndDropRegionTableModel() {
        if (m_ddRegionTableModel == null) {
            m_ddRegionTableModel =
                    ModelUtils.toCollectionModel(new ArrayList<SampleColorData.ColorData>());
        }

        return m_ddRegionTableModel;
    }

    public CollectionModel getMarkerThemeModel() 
    {
        return m_markerThemeModel;
    }

    public CollectionModel getDropPointModel() 
    {
        return m_latlongModel;
    }
            
    public CollectionModel getDrillableState() {
        if (m_drillableState == null) 
        {
            String states[] = new String[]{
                "AK", "CA", "CO", "GA", "HI","ID", 
                "IL", "MI", "NY", "TX", 
            };
                                            
            m_drillableState = ModelUtils.toCollectionModel(new SampleColorData(states));
        }
                
        return m_drillableState;
    }
    
    public CollectionModel getDrillableCounty() {
        if (m_drillableCounty == null) 
        {
            m_drillableCounty = ModelUtils.toCollectionModel(new SampleCountyData(new String[]{}));
        }
        
        return m_drillableCounty;
    }
    
    public CollectionModel getDrillableCity() {
        if (m_drillableCity == null) {
            m_drillableCity = ModelUtils.toCollectionModel(new SamplePointData(new String[]{}));
        }
        
        return m_drillableCity;
    }

    public UIThematicMap getThematicMap (UIComponent comp) {
        UIComponent curComponent = comp;
        
        while (curComponent != null && !(curComponent instanceof UIThematicMap)) {
            curComponent = curComponent.getParent();
        }
        
        if (curComponent instanceof UIThematicMap)
            return (UIThematicMap) curComponent;
        else
            return null;
    }


    private UIPointDataLayer getPointDataLayer(UIThematicMap map, String layerName) 
    {
        if (map != null && layerName != null)
        {            
            List <UIComponent> children = map.getChildren();
            Iterator <UIComponent> iterator = children.iterator();
            while (iterator.hasNext()) 
            {
                UIComponent child = iterator.next();            
                if (child instanceof UIPointDataLayer) 
                {
                    String layer = ((UIPointDataLayer) child).getId();
                    if (layerName.equalsIgnoreCase(layer))   
                    {
                        return (UIPointDataLayer) child;
                    }
                }
            }
        }
    
        return null;    
    }
    
    private UIAreaDataLayer getAreaDataLayer (UIThematicMap map, String layerName) 
    {
        if (map != null && layerName != null)
        {            
            List <UIComponent> children = map.getChildren();
            Iterator <UIComponent> iterator = children.iterator();
            while (iterator.hasNext()) 
            {
                UIComponent child = iterator.next();            
                if (child instanceof UIAreaLayer) 
                {
                    String layer = ((UIAreaLayer) child).getLayer();
                    if (layerName.equalsIgnoreCase(layer))   
                    {
                        UIAreaDataLayer areaLayer = (UIAreaDataLayer) (child).getChildren().get(0);
                        return areaLayer;
                    }
                }
            }
        }
        
        return null;
    }
    
    public void processDrill(RowDisclosureEvent rowDisclosureEvent) {
        // Add event code here...
        RowKeySet addSet = ((UIAreaDataLayer) rowDisclosureEvent.getComponent()).getDisclosedRowKeys();
        CollectionModel model = getDrillableState();
  
        Iterator iter = addSet.iterator();
        String state[] = new String[addSet.size()];
        int index = 0;
        while (iter.hasNext())
        {
            model.setRowKey(iter.next());
            Object data = model.getRowData();
            if (data instanceof SampleColorData.ColorData) 
            {
                state[index] = ((SampleColorData.ColorData) data).getName();
            }
            
            index++;
        }        

        m_drillableCity = ModelUtils.toCollectionModel(new SamplePointData(state));
        m_drillableCounty = ModelUtils.toCollectionModel(new SampleCountyData(state));
    }
    
    private static boolean m_rendered = false;

    public boolean getRenderState() {
        return m_rendered;
    }

    public void updateRenderState() {
        if (m_rendered == true) {
            m_rendered = false;
        } else {
            m_rendered = true;
        }
    }

    public Object getSource() {
        return _o;
    }

    public void setSource(Object o) {
        _o = o;
    }

    
    public DnDAction markerDrop(DropEvent dropEvent) {
        // Add event code here...        
        if (dropEvent.getDragComponent() instanceof UIAreaDataLayer) {
            UIAreaDataLayer dataLayer =
                (UIAreaDataLayer)dropEvent.getDragComponent();
            CollectionModel model = dataLayer.getModel();

            SimpleTransferable transfer =
                (SimpleTransferable)dropEvent.getTransferable();
            Object obj = transfer.getData(Object.class);
            RowKeySet keySet = transfer.getData(RowKeySet.class);

            Iterator iterator = keySet.iterator();
            while (iterator.hasNext()) {
                Object rowKey = iterator.next();
                model.setRowKey(rowKey);
                ColorData rowData = (ColorData)model.getRowData();
                
                CollectionModel colModel = (CollectionModel)((RichTable) dropEvent.getDropComponent()).getValue();
                Collection collection = (Collection) colModel.getWrappedData();

                collection.add(rowData);
            }

            RequestContext currentInstance =
                RequestContext.getCurrentInstance();
            currentInstance.addPartialTarget(dropEvent.getDropComponent());
        }
        return DnDAction.NONE;
    }

    private String getRegionIdFromEvent (DropEvent event) 
    {
        if (event.getDropSite() instanceof Map) {
            return (String) ((Map) event.getDropSite()).get("regionId");
        }
        
        return null;
    }

    public DnDAction addMarker(DropEvent dropEvent) {
        String regionId = getRegionIdFromEvent(dropEvent);
        if (regionId != null)
        {
            SampleColorData.ColorData row = new SampleColorData.ColorData (regionId, regionId, new Color(255, 0, 0), "category1", 20);
            ((Collection)m_markerThemeModel.getWrappedData()).add(row);
        }
        
        UIComponent dropComp = dropEvent.getDropComponent();
        if (dropComp instanceof UIAreaLayer) 
        {
            List <UIComponent> children = dropComp.getChildren();
            Iterator <UIComponent> childIter = children.iterator();
            while (childIter.hasNext()) 
            {
                UIComponent comp = childIter.next();
                if (comp instanceof UIAreaDataLayer) 
                {
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.addPartialTarget(comp);
                    break;
                }
            }
        }
        
        return DnDAction.NONE;        
    }
    
    private String m_legendDisplay = "showHide";

    public void setLegendDisplay(String m_legendDisplay) {
        this.m_legendDisplay = m_legendDisplay;
    }

    public String getLegendDisplay() {
        return m_legendDisplay;
    }

    private String m_renderFormat = "png";

    public void setRenderFormat(String m_renderFormat) {
        this.m_renderFormat = m_renderFormat;
    }

    public String getRenderFormat() {
        return m_renderFormat;
    }
    
  
    public void setPatternNames(List patternNames) {
        this.m_patternNames = patternNames;
    }
  
    public List getPatternNames() {
        return m_patternNames;
    }
    
    public void setPattern(String pattern){
        m_pattern = pattern;
    }
    
    public String getPattern(){
        return m_pattern;
    }
    
    private String m_gradientEffect = "none";
    
    public void setGradientEffect(String gradientEffect){
        m_gradientEffect = gradientEffect;
    }
    
    public String getGradientEffect(){
        return m_gradientEffect;
    }

    public void changeGradientEffect(ValueChangeEvent valueChangeEvent) {
        Boolean checked = (Boolean)valueChangeEvent.getNewValue();
        if(checked)
          m_gradientEffect = "auto";
        else
          m_gradientEffect = "none";
    }

    public String getAnimationOnDisplay() {
        return animationOnDisplay;
    }

    public void setAnimationOnDisplay(String animationOnDisplay) {
        this.animationOnDisplay = animationOnDisplay;
    }

    public void setApplyPan(boolean applyPan) {
      if (applyPan != _applyPan) {
        _applyPan = applyPan;

        setFeaturesOff(applyPan, UIThematicMap.PAN);
      }
    }
    public boolean getApplyPan() {
      return _applyPan;
    }

    public void setApplyZoom(boolean applyZoom) {
      if (applyZoom != _applyZoom) {
        _applyZoom = applyZoom;

        setFeaturesOff(applyZoom, UIThematicMap.ZOOM);
      }
    }
    public boolean getApplyZoom() {
      return _applyZoom;
    }

    public void setApplyZoomToFit(boolean applyZoomToFit) {
      if (applyZoomToFit != _applyZoomToFit) {
        _applyZoomToFit = applyZoomToFit;

        setFeaturesOff(applyZoomToFit, UIThematicMap.ZOOM_TO_FIT);
      }
    }
    public boolean getApplyZoomToFit() {
      return _applyZoomToFit;
    }

    public void setFeaturesOff(boolean applied, String feature) {
      if (applied) {
        if (_featuresOff == null || _featuresOff.isEmpty()) {
          _featuresOff = new HashSet<String>();
        }
        _featuresOff.add(feature);
      }
      else if (_featuresOff != null)
        _featuresOff.remove(feature);
    }    
    
    public Set<String> getFeaturesOff()
    {
        return _featuresOff;
    }

    public List getShapeNames() {
        return m_shapeNames;
    }

    public void setShapeNames(List shapeNames) {
        this.m_shapeNames = shapeNames;
    }

    public String getShape1() {
        return m_shape1;
    }

    public void setShape1(String shape) {
        this.m_shape1 = shape;
    }
    
    public String getShape2() {
        return m_shape2;
    }

    public void setShape2(String shape) {
        this.m_shape2 = shape;
    }

    public String getDrillBehavior() {
        return m_drillBehavior;
    }

    public void setDrillBehavior(String drillBehavior) {
        this.m_drillBehavior = drillBehavior;
    }

    public void setMaintainDrillState(boolean m_maintainDrillState) {
        this.m_maintainDrillState = m_maintainDrillState;
    }

    public boolean isMaintainDrillState() {
        return m_maintainDrillState;
    }

    public void clearSelection(ActionEvent actionEvent) {
        // Add event code here...        
        UIComponent button = (UIComponent) actionEvent.getSource();
        
        if (button != null && button.getParent() != null)
        {
            List <UIComponent> childrenList = button.getParent().getChildren();
            Iterator <UIComponent> childIterator = childrenList.iterator();
            while (childIterator.hasNext()) 
            {                
               UIComponent nextChild = childIterator.next();    
               if (nextChild instanceof RichTable)
               {
                   CollectionModel model = (CollectionModel)((RichTable) nextChild).getValue();
                   if (m_ddRegionTableModel == model)
                       m_ddRegionTableModel = null;
                   else if (m_ddMarkerTableModel == model)
                       m_ddMarkerTableModel = null;
                   
                   RequestContext currentInstance =
                       RequestContext.getCurrentInstance();
                   currentInstance.addPartialTarget(nextChild);   
                   break;
               }
            }
        }
    }

    public DnDAction imageDrop(DropEvent dropEvent) {
        // Add event code here...
        UIComponent dropComp = dropEvent.getDropComponent();
        Object dropSite = dropEvent.getDropSite();
        
        Object coordX = null;
        Object coordY = null;
                
        if (dropSite instanceof Map) 
        {
            coordX = ((Map) dropSite).get("localX");
            coordY = ((Map) dropSite).get("localY");
        }
        
        if (dropComp instanceof UIAreaLayer) 
        {
            ArrayList arrList = (ArrayList) m_latlongModel.getWrappedData();
            arrList.add(new SamplePointData.TestData("name1", (Double)coordY, (Double)coordX, Color.BLACK, "category1"));
            m_latlongModel = ModelUtils.toCollectionModel(arrList);
        }
        
        if (dropComp.getParent() != null &&
            dropComp.getParent() instanceof UIThematicMap)
        {
            List <UIComponent> children = dropComp.getParent().getChildren();
            Iterator <UIComponent> childIter = children.iterator();
            while (childIter.hasNext()) 
            {
                UIComponent comp = childIter.next();
                if (comp instanceof UIPointDataLayer)
                {
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.addPartialTarget(comp);
                    break;
                }
            }
        }
        
        return DnDAction.NONE;
    }
}
