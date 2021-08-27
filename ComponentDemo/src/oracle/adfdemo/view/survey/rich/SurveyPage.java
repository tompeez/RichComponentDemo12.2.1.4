/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.survey.rich;

public class SurveyPage implements java.io.Serializable
{
  public SurveyPage()
  {
  }

  public SurveyPage(String viewId, String label)
  {
    setViewId(viewId);
    setLabel(label);
    setDisabled(false);
  }

  public void setViewId(String viewId)
  {
    _viewId = viewId;
  }

  public String getViewId()
  {
    return _viewId;
  }

  public void setOutcome(String outcome)
  {
    _outcome = outcome;
  }

  public String getOutcome()
  {
    return _outcome;
  }

  public void setLabel(String label)
  {
    _label = label;
  }

  public String getLabel()
  {
    return _label;
  }

  public void setDisabled(boolean disabled)
  {
    _disabled = disabled;
  }

  public boolean isDisabled()
  {
    return _disabled;
  }

  private String _viewId;
  private String _outcome;
  private String _label;
  private boolean _disabled;
}
