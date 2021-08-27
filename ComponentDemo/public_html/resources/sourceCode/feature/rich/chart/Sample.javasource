package oracle.adfdemo.view.feature.rich.chart;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.model.SelectItem;

import oracle.adf.view.faces.bi.component.chart.UIChartBase;
import oracle.adf.view.faces.bi.event.chart.ChartDrillEvent;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class Sample {

  //******************************* Time Axis Sample *************************/


    public Date getStartDate() {
    return getDate(2014, 0, 1);
  }

  public Date getEndDate() {
    return getDate(2018, 0, 1);
  }

  private static Date getDate(int year, int month, int date) {
    GregorianCalendar cal = new GregorianCalendar(year, month, date);
    return cal.getTime();
  }

  //******************************* Selection Sample *************************/

  private String m_selection = "No Nodes Selected";
  private String dataSelection = "multiple";
  private String m_actionResult = "";
  private int counter = 0;

  public String getSelectionState() {
    return m_selection;
  }

  public void setDataSelection(String dataSelection) {
    this.dataSelection = dataSelection;
  }

  public String getDataSelection() {
    return dataSelection;
  }

  //******************************* Drilling Sample *************************/

  private String m_drillState = "No Nodes drilled";
  private String drilling = "on";
  private String dataItemDrilling = "inherit";
  private String seriesDrilling = "inherit";
  private String groupDrilling = "inherit";

  public String getDrillState() {
    return m_drillState;
  }

  public void setDrilling(String drilling) {
    this.drilling = drilling;
  }

  public String getDrilling() {
    return drilling;
  }

  public void setDataItemDrilling(String drilling) {
    this.dataItemDrilling = drilling;
  }

  public String getDataItemDrilling() {
    return dataItemDrilling;
  }

  public void setSeriesDrilling(String drilling) {
    this.seriesDrilling = drilling;
  }

  public String getSeriesDrilling() {
    return seriesDrilling;
  }

  public void setGroupDrilling(String drilling) {
    this.groupDrilling = drilling;
  }

  public String getGroupDrilling() {
    return groupDrilling;
  }

  private List seriesEffectList;

  public List getSeriesEffectList() {
    seriesEffectList = new ArrayList();
    SelectItem effect = new SelectItem("color", "Color");
    seriesEffectList.add(effect);
    effect = new SelectItem("gradient", "Gradient");
    seriesEffectList.add(effect);
    effect = new SelectItem("pattern", "Pattern");
    seriesEffectList.add(effect);
    return seriesEffectList;
  }

  private String seriesEffectType = "color";

  public String getSeriesEffectType() {
    return seriesEffectType;
  }

  public void setSeriesEffectType(String type) {
    seriesEffectType = type;
  }

  public void selectionListener(SelectionEvent event) {
    UIChartBase chart = (UIChartBase) event.getComponent();
    RowKeySet rowKeySet = chart.getSelectedRowKeys();
    if (rowKeySet != null && rowKeySet.size() > 0) {
      StringBuilder sb = new StringBuilder("Selection: ");
      for (Object rowKey : rowKeySet) {
        sb.append(rowKey).append(", ");
      }

      // Remove the trailing comma and set the selection string
      sb.setLength(sb.length() - 2);
      m_selection = sb.toString();
    } else
      m_selection = "No Nodes Selected";
  }

  public void drillListener(ChartDrillEvent event) {
    if (event.getRowKey() != null && event.getGroup() != null && event.getSeries() != null)
      m_drillState = "Drill on Data Item: " + event.getRowKey().toString();
    else if (event.getSeries() != null)
      m_drillState = "Drill on Series: " + event.getSeries().toString();
    else if (event.getGroup() != null)
      m_drillState = "Drill on Group: " + event.getGroup().toString();
  }
  
  public String chartAction(){
    m_actionResult = "Chart Action Invoked " + ++counter + " times.";
    return null;
  }
  
  public String getActionString(){
    return m_actionResult;
  }

  //******************************* Zoom and Scroll Sample *************************/

  private String zoomAndScroll = "live";

  public void setZoomAndScroll(String zoomAndScroll) {
    this.zoomAndScroll = zoomAndScroll;
  }

  public String getZoomAndScroll() {
    return zoomAndScroll;
  }

  private String dataSelectionZS = "none";

  public void setDataSelectionZS(String dataSelection) {
    this.dataSelectionZS = dataSelection;
  }

  public String getDataSelectionZS() {
    return dataSelectionZS;
  }

  //******************************* Pie Chart Sample *************************/
  private String sorting = "off";

  public void setSorting(String sorting) {
    this.sorting = sorting;
  }

  public String getSorting() {
    return sorting;
  }

  private String centerLabel = "";

  public void setCenterLabel(String centerLabel) {
    this.centerLabel = centerLabel;
  }

  public String getCenterLabel() {
    return centerLabel;
  }

  private String centerLabelStyle;

  public void setCenterLabelStyle(String centerLabelStyle) {
    this.centerLabelStyle = centerLabelStyle;
  }

  public String getCenterLabelStyle() {
    return centerLabelStyle;
  }

  private Number innerRadius = 0;

  public void setInnerRadius(Number innerRadius) {
    this.innerRadius = innerRadius;
  }

  public Number getInnerRadius() {
    return innerRadius;
  }

  private String threeDEffect = "off";

  public void setThreeDEffect(String threeDEffect) {
    this.threeDEffect = threeDEffect;
  }

  public String getThreeDEffect() {
    return threeDEffect;
  }

  private String otherColor = "#e91959";

  public void setOtherColor(String otherColor) {
    this.otherColor = otherColor;
  }

  public String getOtherColor() {
    return otherColor;
  }

  private double otherThreshold = 0.0;

  public void setOtherThreshold(double otherThreshold) {
    this.otherThreshold = otherThreshold;
  }

  public double getOtherThreshold() {
    return otherThreshold;
  }

  private String sliceLabelPosition = "auto";

  public void setSliceLabelPosition(String sliceLabelPosition) {
    this.sliceLabelPosition = sliceLabelPosition;
  }

  public String getSliceLabelPosition() {
    return sliceLabelPosition;
  }

  private Number sliceGaps = 0;

  public void setSliceGaps(Number sliceGaps) {
    this.sliceGaps = sliceGaps;
  }

  public Number getSliceGaps() {
    return sliceGaps;
  }

  private String sliceLabelStyle;

  public void setSliceLabelStyle(String sliceLabelStyle) {
    this.sliceLabelStyle = sliceLabelStyle;
  }

  public String getSliceLabelStyle() {
    return sliceLabelStyle;
  }

  private String label = "Custom Label";

  public void setLabel(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  private String sliceLabel = "Custom Slice Label";

  public void setSliceLabel(String sliceLabel) {
    this.sliceLabel = sliceLabel;
  }

  public String getSliceLabel() {
    return sliceLabel;
  }

  private String color = "#8000ff";

  public void setColor(String color) {
    this.color = color;
  }

  public String getColor() {
    return color;
  }

  private String borderColor = "#ff0080";

  public void setBorderColor(String borderColor) {
    this.borderColor = borderColor;
  }

  public String getBorderColor() {
    return borderColor;
  }

  private double explode = 0.5;

  public void setExplode(double explode) {
    this.explode = explode;
  }

  public double getExplode() {
    return explode;
  }

  private List patternList;

  public List getPatternList() {
    patternList = new ArrayList();
    SelectItem effect = new SelectItem("auto", "auto");
    patternList.add(effect);
    effect = new SelectItem("smallChecker", "smallChecker");
    patternList.add(effect);
    effect = new SelectItem("smallCrosshatch", "smallCrosshatch");
    patternList.add(effect);
    effect = new SelectItem("smallDiagonalLeft", "smallDiagonalLeft");
    patternList.add(effect);
    effect = new SelectItem("smallDiagonalRight", "smallDiagonalRight");
    patternList.add(effect);
    effect = new SelectItem("smallDiamond", "smallDiamond");
    patternList.add(effect);
    effect = new SelectItem("smallTriangle", "smallTriangle");
    patternList.add(effect);
    effect = new SelectItem("largeChecker", "largeChecker");
    patternList.add(effect);
    effect = new SelectItem("largeCrosshatch", "largeCrosshatch");
    patternList.add(effect);
    effect = new SelectItem("largeDiagonalLeft", "largeDiagonalLeft");
    patternList.add(effect);
    effect = new SelectItem("largeDiagonalRight", "largeDiagonalRight");
    patternList.add(effect);
    effect = new SelectItem("largeDiamond", "largeDiamond");
    patternList.add(effect);
    effect = new SelectItem("largeTriangle", "largeTriangle");
    patternList.add(effect);
    return patternList;
  }

  private String pattern = "auto";

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public String getPattern() {
    return pattern;
  }

  //******************************* Number Format Sample *************************/
  private CustomNumberFormat m_xFormat = new CustomNumberFormat("x");
  private CustomNumberFormat m_yFormat = new CustomNumberFormat("y");
  private CustomNumberFormat m_y2Format = new CustomNumberFormat("y2");
  private CustomNumberFormat m_zFormat = new CustomNumberFormat("z");
  private CustomNumberFormat m_labelFormat = new CustomNumberFormat("Label");
  private CustomNumberFormat m_valueLabelFormat = new CustomNumberFormat("Value");
  private CustomNumberFormat m_targetValueLabelFormat = new CustomNumberFormat("Target Value");

  private CustomNumberFormat m_seriesFormat = new CustomNumberFormat("Series");
  private CustomNumberFormat m_groupFormat = new CustomNumberFormat("Group");
  private CustomNumberFormat m_lowFormat = new CustomNumberFormat("Low");
  private CustomNumberFormat m_highFormat = new CustomNumberFormat("High");
  private CustomNumberFormat m_openFormat = new CustomNumberFormat("Open");
  private CustomNumberFormat m_closeFormat = new CustomNumberFormat("Close");
  private CustomNumberFormat m_volumeFormat = new CustomNumberFormat("Volume");
  
  private CustomNumberFormat m_xLabelFormat = new CustomNumberFormat("x");
  private CustomNumberFormat m_yLabelFormat = new CustomNumberFormat("y");
  private CustomNumberFormat m_y2LabelFormat = new CustomNumberFormat("y2");

  public CustomNumberFormat getxFormat() {
    return m_xFormat;
  }

  public CustomNumberFormat getyFormat() {
    return m_yFormat;
  }

  public CustomNumberFormat gety2Format() {
    return m_y2Format;
  }

  public CustomNumberFormat getzFormat() {
    return m_zFormat;
  }

  public CustomNumberFormat getlabelFormat() {
    return m_labelFormat;
  }

  public CustomNumberFormat getvalueLabelFormat() {
    return m_valueLabelFormat;
  }

  public CustomNumberFormat gettargetValueFormat() {
    return m_targetValueLabelFormat;
  }

  public CustomNumberFormat getxLabelFormat() {
    return m_xLabelFormat;
  }

  public CustomNumberFormat getyLabelFormat() {
    return m_yLabelFormat;
  }

  public CustomNumberFormat gety2LabelFormat() {
    return m_y2LabelFormat;
  }

  public CustomNumberFormat getSeriesFormat() {
    return m_seriesFormat;
  }

  public CustomNumberFormat getGroupFormat() {
    return m_groupFormat;
  }

  public CustomNumberFormat getLowFormat() {
    return m_lowFormat;
  }

  public CustomNumberFormat getHighFormat() {
    return m_highFormat;
  }

  public CustomNumberFormat getOpenFormat() {
    return m_openFormat;
  }

  public CustomNumberFormat getCloseFormat() {
    return m_closeFormat;
  }

  public CustomNumberFormat getVolumeFormat() {
    return m_volumeFormat;
  }
  
  private String m_chartType = "barChart";

  public String getChartType() {
    return m_chartType;
  }

  public void setChartType(String type) {
    m_chartType = type;
  }

  public static class CustomNumberFormat {

    private String m_groupingUsed;
    private String m_scaling;
    private String m_pattern;

    private String m_type;
    private String m_currencySymbol;
    private String m_currencyCode;
    private int m_minIntegerDigits = 0;
    private int m_minFractionDigits = 0;
    private int m_maxIntegerDigits = 0;
    private int m_maxFractionDigits = 0;
    private String m_tooltipDisplay = "auto";
    private String m_tooltipLabel;


    public CustomNumberFormat(String label) {
      m_groupingUsed = "true";
      m_scaling = "auto";
      m_pattern = null;

      m_type = "number";
      m_currencySymbol = "$";
      m_currencyCode = "USD";
      m_minIntegerDigits = 0;
      m_minFractionDigits = 0;
      m_maxIntegerDigits = 0;
      m_maxFractionDigits = 0;
      m_tooltipLabel = label;

    }

    public String getGroupingUsed() {
      return m_groupingUsed;
    }

    public void setGroupingUsed(String autoPrecision) {
      m_groupingUsed = autoPrecision;
    }

    public String getScaling() {
      return m_scaling;
    }

    public void setScaling(String scaling) {
      m_scaling = scaling;
    }

    public String getPattern() {
      return m_pattern;
    }

    public void setPattern(String pattern) {
      m_pattern = pattern;
    }

    public String getType() {
      return m_type;
    }

    public void setType(String type) {
      m_type = type;
    }

    public String getCurrencySymbol() {
      return m_currencySymbol;
    }

    public void setCurrencySymbol(String symbol) {
      m_currencySymbol = symbol;
    }

    public String getCurrencyCode() {
      return m_currencyCode;
    }

    public void setCurrencyCode(String code) {
      m_currencyCode = code;
    }

    public String getTooltipDisplay() {
      return m_tooltipDisplay;
    }

    public void setTooltipDisplay(String tooltipDisplay) {
      m_tooltipDisplay = tooltipDisplay;
    }

    public String getTooltipLabel() {
      return m_tooltipLabel;
    }

    public void setTooltipLabel(String tooltipLabel) {
      m_tooltipLabel = tooltipLabel;
    }
  }
}
