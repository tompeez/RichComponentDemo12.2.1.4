package oracle.adfdemo.view.feature.rich.graph;

import java.util.Arrays;
import java.util.Random;

import java.awt.Color;

import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.bi.component.graph.GraphSelection;
import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.faces.bi.event.ClickEvent;
import oracle.adf.view.faces.bi.model.DataModel;
import oracle.adf.view.faces.bi.model.GraphDataModel;

import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;

import oracle.dss.dataView.LocalXMLDataSource;

import oracle.dss.graph.CommonGraph;

import org.apache.myfaces.trinidad.context.RequestContext;

public class PieGraphFeaturesBean {
    private int numSlices = 7;
    private int explodePercentage = 0;
    private int maxIntDigits = 3;
    private int maxFracDigits = 3;
    private int fontSize = 12;
    private int gradientNumStops = 0;

    private DataModel pieModel;

    private String[] pieLabels;

    private String groupLabel = "Group Label";
    private String dataModel = "Random";
    private String labelPos = "LP_OUTSIDE_WITH_FEELER";
    private String selection = "none";
    private String pieLabelPrefix = "Series ";
    private String labelTextType = "LD_PERCENT";
    private String explodeBehavior = "none";
    private String autoPrec = "on";
    private String sliceType = "number";
    private String fillType = "FT_COLOR";
    private String gradientDir = "GD_RIGHT";
    private String stringBorderColor = "#FFFFFF";
    private String stringColor = "#FF0000";
    private String seriesEffect = "SE_GRADIENT";
    private String stringFeelerColor = "#000000";
    private String stringFontColor = "#000000";
    private String fontName = "SansSerif";
    private String markerTT = "MTT_VALUES_TEXT";
    private String seriesTT = "TLT_MEMBER";
    private String groupTT = "TLT_MEMBER";
    private String markerTooltipTemplate = "Series: SERIES_LABEL NEW_LINEGroup: GROUP_LABEL NEW_LINEValue: PIE_VALUE";
    private String styleClass = "";
    private String resize = "DYNAMIC_SIZE";
    private String visualEffects = "AUTO";
    private String animOnDisplay = "none";
    private String hideAndShowBehavior = "none";
    private String animOnDataChange = "none";
    private String animIndicators = "ALL";
    private String leftImageFormat = "FLASH";
    private String rightImageFormat = "HTML5";
    private String seriesRollover = "RB_NONE";
    private String listenerText = "";

    private Color borderColor = Color.decode(stringBorderColor);
    private Color color = Color.decode(stringColor);
    private Color feelerColor = Color.decode(stringFeelerColor);
    private Color fontColor = Color.decode(stringFontColor);

    private boolean threeD = false;
    private boolean drilling = false;
    private boolean showLegend = true;
    private boolean borderTransparent = false;
    private boolean fontBold = false;
    private boolean fontItalic = false;
    private boolean fontUnderline = false;
    private int pieRotation = 0;

    public PieGraphFeaturesBean() {
    }

    public void clickListener(ClickEvent ce) {
        this.listenerText = ce.getComponentHandle().getComponentInfo().toString();
    }

    public void setNumSlices(int numSlices) {
        this.numSlices = numSlices;
    }

    public int getNumSlices() {
        return numSlices;
    }

    public DataModel getPieModel() {
        this.setPieLabels();
        if ("Random".equals(dataModel)) {
            pieModel = getRandomModel();
        } else {
            pieModel = getUniformModel();
        }
        return pieModel;
    }

    private DataModel getRandomModel() {
        Object[][] dataArray = new Object[1][numSlices];
        Random r = new Random(7);
        for (int i = 0; i < numSlices; i++) {
            dataArray[0][i] = r.nextInt(100);
        }
        return new GraphDataModel(new LocalXMLDataSource(new String[] { groupLabel }, pieLabels, dataArray));
    }

    private DataModel getUniformModel() {
        Object[][] dataArray = new Object[1][numSlices];
        for (int i = 0; i < numSlices; i++) {
            dataArray[0][i] = 10;
        }
        return new GraphDataModel(new LocalXMLDataSource(new String[] { groupLabel }, pieLabels, dataArray));
    }

    public void setGroupLabel(String seriesName) {
        this.groupLabel = seriesName;
    }

    public String getGroupLabel() {
        return groupLabel;
    }

    private void setPieLabels() {
        pieLabels = new String[numSlices];
        for (int i = 0; i < numSlices; i++) {
            pieLabels[i] = pieLabelPrefix + (i + 1);
        }
    }

    public String[] getPieLabels() {
        return pieLabels;
    }

    public void setPieLabelPrefix(String pieLabelPrefix) {
        this.pieLabelPrefix = pieLabelPrefix;
    }

    public String getPieLabelPrefix() {
        return pieLabelPrefix;
    }

    public void setThreeD(boolean threeD) {
        this.threeD = threeD;
    }

    public boolean isThreeD() {
        return threeD;
    }

    public void setDataModel(String dataModel) {
        this.dataModel = dataModel;
    }

    public String getDataModel() {
        return dataModel;
    }

    public void setLabelPos(String labelPos) {
        this.labelPos = labelPos;
    }

    public String getLabelPos() {
        return labelPos;
    }

    public void setExplodePercentage(int explodePercentage) {
        this.explodePercentage = explodePercentage;
    }

    public int getExplodePercentage() {
        return explodePercentage;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getSelection() {
        return selection;
    }

    public void setDrilling(boolean drilling) {
        this.drilling = drilling;
    }

    public boolean isDrilling() {
        return drilling;
    }

    public void setLabelTextType(String labelTextType) {
        this.labelTextType = labelTextType;
    }

    public String getLabelTextType() {
        return labelTextType;
    }

    public void setShowLegend(boolean showLegend) {
        this.showLegend = showLegend;
    }

    public boolean isShowLegend() {
        return showLegend;
    }

    public void setExplodeBehavior(String explodeBehavior) {
        this.explodeBehavior = explodeBehavior;
    }

    public String getExplodeBehavior() {
        return explodeBehavior;
    }


    public void setAutoPrec(String autoPrec) {
        this.autoPrec = autoPrec;
    }

    public String getAutoPrec() {
        return autoPrec;
    }

    public void setMaxIntDigits(int maxIntDigits) {
        this.maxIntDigits = maxIntDigits;
    }

    public int getMaxIntDigits() {
        return maxIntDigits;
    }

    public void setMaxFracDigits(int maxFracDigits) {
        this.maxFracDigits = maxFracDigits;
    }

    public int getMaxFracDigits() {
        return maxFracDigits;
    }

    public void setSliceType(String sliceType) {
        this.sliceType = sliceType;
    }

    public String getSliceType() {
        return sliceType;
    }

    public void setBorderTransparent(boolean borderTransparent) {
        this.borderTransparent = borderTransparent;
    }

    public boolean isBorderTransparent() {
        return borderTransparent;
    }

    public void setFillType(String fillType) {
        this.fillType = fillType;
    }

    public String getFillType() {
        return fillType;
    }

    public void setGradientDir(String gradientDir) {
        this.gradientDir = gradientDir;
    }

    public String getGradientDir() {
        return gradientDir;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = Color.decode(borderColor);
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setColor(String color) {
        this.color = Color.decode(color);
    }

    public Color getColor() {
        return color;
    }

    public void setStringBorderColor(String stringBorderColor) {
        this.stringBorderColor = stringBorderColor;
        setBorderColor(stringBorderColor);
    }

    public String getStringBorderColor() {
        return stringBorderColor;
    }

    public void setStringColor(String stringColor) {
        this.stringColor = stringColor;
        setColor(stringColor);
    }

    public String getStringColor() {
        return stringColor;
    }

    public void setSeriesEffect(String seriesEffect) {
        this.seriesEffect = seriesEffect;
    }

    public String getSeriesEffect() {
        return seriesEffect;
    }

    public void setStringFeelerColor(String stringFeelerColor) {
        this.stringFeelerColor = stringFeelerColor;
        setFeelerColor(stringFeelerColor);
    }

    public String getStringFeelerColor() {
        return stringFeelerColor;
    }

    public void setFeelerColor(String feelerColor) {
        this.feelerColor = Color.decode(feelerColor);
    }

    public Color getFeelerColor() {
        return feelerColor;
    }

    public void setStringFontColor(String stringFontColor) {
        this.stringFontColor = stringFontColor;
        setFontColor(stringFontColor);
    }

    public String getStringFontColor() {
        return stringFontColor;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontColor(String stringFontColor) {
        this.fontColor = Color.decode(stringFontColor);
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontBold(boolean fontBold) {
        this.fontBold = fontBold;
    }

    public boolean isFontBold() {
        return fontBold;
    }

    public void setFontItalic(boolean fontItalic) {
        this.fontItalic = fontItalic;
    }

    public boolean isFontItalic() {
        return fontItalic;
    }

    public void setFontUnderline(boolean fontUnderline) {
        this.fontUnderline = fontUnderline;
    }

    public boolean isFontUnderline() {
        return fontUnderline;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setMarkerTT(String markerTT) {
        this.markerTT = markerTT;
    }

    public String getMarkerTT() {
        return markerTT;
    }

    public void setSeriesTT(String seriesTT) {
        this.seriesTT = seriesTT;
    }

    public String getSeriesTT() {
        return seriesTT;
    }

    public void setGroupTT(String groupTT) {
        this.groupTT = groupTT;
    }

    public String getGroupTT() {
        return groupTT;
    }

    public void setMarkerTooltipTemplate(String markerTooltipTemplate) {
        this.markerTooltipTemplate = markerTooltipTemplate;
    }

    public String getMarkerTooltipTemplate() {
        return markerTooltipTemplate;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setResize(String resize) {
        this.resize = resize;
    }

    public String getResize() {
        return resize;
    }

    public void setGradientNumStops(int gradientNumStops) {
        this.gradientNumStops = gradientNumStops;
    }

    public int getGradientNumStops() {
        return gradientNumStops;
    }

    public void setVisualEffects(String visualEffects) {
        this.visualEffects = visualEffects;
    }

    public String getVisualEffects() {
        return visualEffects;
    }

    public void setAnimOnDisplay(String animOnDisplay) {
        this.animOnDisplay = animOnDisplay;
    }

    public String getAnimOnDisplay() {
        return animOnDisplay;
    }

    public void setHideAndShowBehavior(String hideAndShowBehavior) {
        this.hideAndShowBehavior = hideAndShowBehavior;
    }

    public String getHideAndShowBehavior() {
        return hideAndShowBehavior;
    }

    public void setAnimOnDataChange(String animOnDataChange) {
        this.animOnDataChange = animOnDataChange;
    }

    public String getAnimOnDataChange() {
        return animOnDataChange;
    }

    public void setAnimIndicators(String animIndicators) {
        this.animIndicators = animIndicators;
    }

    public String getAnimIndicators() {
        return animIndicators;
    }

    public void setLeftImageFormat(String leftImageFormat) {
        this.leftImageFormat = leftImageFormat;
    }

    public String getLeftImageFormat() {
        return leftImageFormat;
    }

    public void setRightImageFormat(String rightImageFormat) {
        this.rightImageFormat = rightImageFormat;
    }

    public String getRightImageFormat() {
        return rightImageFormat;
    }

    public void setSeriesRollover(String seriesRollover) {
        this.seriesRollover = seriesRollover;
    }

    public String getSeriesRollover() {
        return seriesRollover;
    }

    public String getString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Data model: ");
        sb.append(dataModel);
        sb.append("<br>");
        sb.append("Number of slices: ");
        sb.append(numSlices);
        sb.append("<br>");
        
        sb.append("Group label: ");
        sb.append(groupLabel);
        sb.append("<br>");
        sb.append("Pie label prefix: ");
        sb.append(pieLabelPrefix);
        sb.append("<br>");
        sb.append("3D: ");
        sb.append(threeD);
        sb.append("<br>");
        sb.append("Show legend: ");
        sb.append(showLegend);
        sb.append("<br>");
        sb.append("Style class: ");
        sb.append(styleClass);
        sb.append("<br>");
        sb.append("Visual effects: ");
        sb.append(visualEffects);
        sb.append("<br>");
        sb.append("Series effect: ");
        sb.append(seriesEffect);
        sb.append("<br>");
        sb.append("Feeler color: ");
        sb.append(stringFeelerColor);
        sb.append("<br>");
        
        sb.append("Label [position: ").append(labelPos).append(", type: ").append(sliceType).append(", text type: ");
        sb.append(labelTextType).append("]");
        sb.append("<br>");
        
        sb.append("Interactivity [selection: ").append(selection).append(", pie explode behavior: ");
        sb.append(explodeBehavior).append(", series rollover effect: ").append(seriesRollover);
        sb.append(", hide and show behavior: ").append(hideAndShowBehavior).append("]");
        sb.append("<br>");
        
        sb.append("Number Formatting [auto precision: ").append(autoPrec).append(", max integer digits: ");
        sb.append(maxIntDigits).append(", max fraction digits: ").append(maxFracDigits).append("]");
        sb.append("<br>");
        
        sb.append("Slice [color: ").append(stringColor).append(", fill type: ").append(fillType);
        sb.append(", gradient direction: ").append(gradientDir).append(", gradient stops: ").append(gradientNumStops);
        sb.append(", explode percentage: ").append(explodePercentage).append(", border color: ").append(stringBorderColor);
        sb.append(", border transparency: ").append(borderTransparent).append("]");
        sb.append("<br>");
        
        sb.append("Tooltip [marker: ").append(markerTT).append(", series: ").append(seriesTT).append(", group: ");
        sb.append(groupTT).append("]");
        sb.append("<br>");
        sb.append("Marker tooltip template: ");
        sb.append(markerTooltipTemplate);
        sb.append("<br>");
       
        sb.append("Animation [on data change: ").append(animOnDataChange).append(", on display: ").append(animOnDisplay);
        sb.append("]");
        sb.append("<br>");
        
        sb.append("Font [name: ").append(fontName).append(", size: ").append(fontSize).append(", color: ");
        sb.append(stringFontColor).append(", bold: ").append(fontBold).append(", italic: ").append(fontItalic);
        sb.append(", underlined ").append(fontUnderline).append("]");
        return sb.toString();
    }

    public void setListenerText(String clickListenerString) {
        this.listenerText = clickListenerString;
    }

    public String getListenerText() {
        return listenerText;
    }

    public void setPieRotation(int pieRotation) {
        this.pieRotation = pieRotation;

      ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
      
      PieGraphBindingBean pieRotationState = (PieGraphBindingBean) context.getRequestMap().get("pieGraphLeft");
      pieRotationState.updatePieRotation(this.pieRotation);
      
      pieRotationState = (PieGraphBindingBean) context.getRequestMap().get("pieGraphRight");
      pieRotationState.updatePieRotation(this.pieRotation);
        
    }

    public int getPieRotation() {
        return pieRotation;
    }

}

