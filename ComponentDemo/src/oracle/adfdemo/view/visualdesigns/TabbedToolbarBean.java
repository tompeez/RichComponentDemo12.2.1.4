package oracle.adfdemo.view.visualdesigns;

import java.io.Serializable;

/**
 * Bean used in the tabbed toolbar visual design demo.
 */
public class TabbedToolbarBean implements Serializable
{
  /**
   * Gets the currently selected chart style.
   * @return the currently selected chart style
   */
  public NamedImage getCurrentChartStyle()
  {
    if (_currentChartStyle == null)
      return _DEFAULT_CHART_STYLE;
    return _currentChartStyle;
  }

  /**
   * Sets the currently selected chart style.
   * @param newChartStyle the newly selected chart style
   */
  public void setCurrentChartStyle(NamedImage newChartStyle)
  {
    _currentChartStyle = newChartStyle;
  }

  /**
   * Gets the currently selected chart type.
   * @return the currently selected chart type
   */
  public NamedImage getCurrentChartType()
  {
    if (_currentChartType == null)
      return _DEFAULT_CHART_TYPE;
    return _currentChartType;
  }

  /**
   * Sets the currently selected chart type.
   * @param newChartType the newly selected chart type
   */
  public void setCurrentChartType(NamedImage newChartType)
  {
    _currentChartType = newChartType;
  }

  /**
   * Gets the list of possible chart styles.
   * @return an array of NamedImage objects
   */
  public NamedImage[][] getChartStyles()
  {
    return _chartStyles;
  }

  /**
   * Gets the list of possible chart area types.
   * @return an array of NamedImage objects
   */
  public NamedImage[][] getChartAreaTypes()
  {
    return _chartAreaTypes;
  }

  /**
   * Gets the list of possible chart bar types.
   * @return an array of NamedImage objects
   */
  public NamedImage[][] getChartBarTypes()
  {
    return _chartBarTypes;
  }

  /**
   * Gets the list of possible chart bubble types.
   * @return an array of NamedImage objects
   */
  public NamedImage[][] getChartBubbleTypes()
  {
    return _chartBubbleTypes;
  }

  /**
   * Gets the list of possible chart line types.
   * @return an array of NamedImage objects
   */
  public NamedImage[][] getChartLineTypes()
  {
    return _chartLineTypes;
  }

  /**
   * Gets the list of possible chart pie types.
   * @return an array of NamedImage objects
   */
  public NamedImage[][] getChartPieTypes()
  {
    return _chartPieTypes;
  }

  /**
   * Gets the list of possible chart Radar types.
   * @return an array of NamedImage objects
   */
  public NamedImage[][] getChartRadarTypes()
  {
    return _chartRadarTypes;
  }

  private NamedImage _currentChartStyle;
  private NamedImage _currentChartType;

  public final static class NamedImage implements Serializable
  {
    public NamedImage()
    {
      _spacer = true;
    }

    public NamedImage(String text, String source)
    {
      _spacer = false;
      _text = text;
      _source = source;
    }

    public boolean isSpacer()
    {
      return _spacer;
    }

    public String getText()
    {
      return _text;
    }

    public String getSource()
    {
      return _source;
    }

    private boolean _spacer;
    private String  _text;
    private String  _source;

    @SuppressWarnings("compatibility:-4999782079269658824")
    private static final long serialVersionUID = 1L;
  }

  private final static NamedImage _DEFAULT_CHART_TYPE =
    new NamedImage("Vertical Bar", "/images/chartTypes/BAR_VERT_CLUST.png");

  private final static NamedImage _DEFAULT_CHART_STYLE =
    new NamedImage("Default", "/images/chartStyles/default.png");

  private final static NamedImage[][] _chartStyles = {
    {
      _DEFAULT_CHART_STYLE,
      new NamedImage("Autumn", "/images/chartStyles/autumn.png"),
      new NamedImage("Comet", "/images/chartStyles/comet.png"),
      new NamedImage("Confetti", "/images/chartStyles/confetti.png")
    },
    {
      new NamedImage("Earth", "/images/chartStyles/earth.png"),
      new NamedImage("Executive", "/images/chartStyles/executive.png"),
      new NamedImage("Financial", "/images/chartStyles/financial.png"),
      new NamedImage("Glass", "/images/chartStyles/glass.png")
    },
    {
      new NamedImage("Gray", "/images/chartStyles/bw.png"),
      new NamedImage("Nautical", "/images/chartStyles/nautical.png"),
      new NamedImage("Projection", "/images/chartStyles/projection.png"),
      new NamedImage("Regatta", "/images/chartStyles/regatta.png")
    },
    {
      new NamedImage("Spring", "/images/chartStyles/april.png"),
      new NamedImage("Southwest", "/images/chartStyles/southwest.png"),
      new NamedImage(),
      new NamedImage()
    }
  };

  private final static NamedImage[][] _chartAreaTypes = {
    {
      new NamedImage("Area", "/images/chartTypes/AREA_VERT_ABS.png"),
      new NamedImage("Stacked Area", "/images/chartTypes/AREA_VERT_STACK.png"),
      new NamedImage("Percent Area", "/images/chartTypes/AREA_VERT_PERCENT.png"),
      new NamedImage("Split Area", "/images/chartTypes/AREA_VERT_ABS_SPLIT2Y.png"),
      new NamedImage("Stacked Split Area", "/images/chartTypes/AREA_VERT_STACK_SPLIT2Y.png")
    }
  };

  private final static NamedImage[][] _chartBarTypes = {
    {
      new NamedImage("Bar", "/images/chartTypes/BAR_HORIZ_CLUST.png"),
      new NamedImage("Percent Bar", "/images/chartTypes/BAR_HORIZ_PERCENT.png"),
      new NamedImage("Stacked Bar", "/images/chartTypes/BAR_HORIZ_STACK.png"),
      new NamedImage("Split Dual-Y Stacked Bar", "/images/chartTypes/BAR_HORIZ_STACK_SPLIT2Y.png"),
      _DEFAULT_CHART_TYPE
    },
    {
      new NamedImage("Vertical Split Dual-Y Bar", "/images/chartTypes/BAR_VERT_CLUST_SPLIT2Y.png"),
      new NamedImage("Vertical Dual-Y Bar", "/images/chartTypes/BAR_VERT_CLUST2Y.png"),
      new NamedImage("Vertical Percent Bar", "/images/chartTypes/BAR_VERT_PERCENT.png"),
      new NamedImage("Vertical Stacked Bar", "/images/chartTypes/BAR_VERT_STACK.png"),
      new NamedImage("Vertical Split Dual-Y Stacked Bar", "/images/chartTypes/BAR_VERT_STACK_SPLIT2Y.png")
    },
    {
      new NamedImage("Vertical Stacked Dual-Y Bar", "/images/chartTypes/BAR_VERT_STACK2Y.png"),
      new NamedImage("Vertical Combination", "/images/chartTypes/COMBINATION_VERT_ABS.png"),
      new NamedImage("Vertical Dual-Y Combination", "/images/chartTypes/COMBINATION_VERT_ABS_2Y.png"),
      new NamedImage("Pareto", "/images/chartTypes/PARETO.png"),
      new NamedImage()
    }
  };

  private final static NamedImage[][] _chartBubbleTypes = {
    {
      new NamedImage("Bubble", "/images/chartTypes/BUBBLE.png"),
      new NamedImage("Bubble Dual-Y", "/images/chartTypes/BUBBLE_2Y.png")
    }
  };

  private final static NamedImage[][] _chartLineTypes = {
    {
      new NamedImage("Line", "/images/chartTypes/LINE_VERT_ABS.png"),
      new NamedImage("Stacked Line", "/images/chartTypes/LINE_VERT_STACK.png"),
      new NamedImage("Split Stacked Line", "/images/chartTypes/LINE_VERT_STACK_SPLIT2Y.png"),
      new NamedImage("Percent Line", "/images/chartTypes/LINE_VERT_PERCENT.png")
    },
    {
      new NamedImage("Dual-Y Line", "/images/chartTypes/LINE_VERT_ABS_2Y.png"),
      new NamedImage("Dual-Y Stacked Line", "/images/chartTypes/LINE_VERT_STACK_2Y.png"),
      new NamedImage("Split Dual-Y Line", "/images/chartTypes/LINE_VERT_ABS_SPLIT2Y.png"),
      new NamedImage()
    }
  };

  private final static NamedImage[][] _chartPieTypes = {
    {
      new NamedImage("Pie", "/images/chartTypes/PIE.png"),
      new NamedImage("Multiple Pies", "/images/chartTypes/PIE_MULTI.png"),
      new NamedImage("Pie Bar", "/images/chartTypes/PIE_BAR.png")
    },
    {
      new NamedImage("Ring", "/images/chartTypes/RING.png"),
      new NamedImage("Multiple Rings", "/images/chartTypes/RING_MULTI.png"),
      new NamedImage("Ring Bar", "/images/chartTypes/RING_BAR.png")
    }
  };

  private final static NamedImage[][] _chartRadarTypes = {
    {
      new NamedImage("Radar", "/images/chartTypes/RADAR_LINE.png"),
      new NamedImage("Polar", "/images/chartTypes/POLAR.png")
    }
  };

  @SuppressWarnings("compatibility:891797130677866198")
  private static final long serialVersionUID = 1L;
}
