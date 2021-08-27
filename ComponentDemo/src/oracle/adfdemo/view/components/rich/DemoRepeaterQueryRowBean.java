/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

public class DemoRepeaterQueryRowBean
{
  public DemoRepeaterQueryRowBean(String criteria)
  {
    _criteria = criteria;
  }

  public boolean isTextCriteriaChosen()
  {
    return ("Subject".equals(_criteria) || "From".equals(_criteria));
  }

  public boolean isDateCriteriaChosen()
  {
    return "Date".equals(_criteria);
  }

  public boolean isDateCriteriaAndBetweenQualifierChosen()
  {
    return ("Date".equals(_criteria) && "between".equals(_dateQualifier));
  }

  public boolean isPriorityCriteriaChosen()
  {
    return "Priority".equals(_criteria);
  }

  public void setCriteria(String criteria)
  {
    //avoid null updates from UIXEditableValue.updateModel()
    if (criteria == null || criteria == "")
      return;
    
    //if the criteria changes, reset all the data
    if (_criteria != criteria)
      _resetRowData();
  
    _criteria = criteria;
  }

  public String getCriteria()
  {
    return _criteria;
  }

  public void setTextQualifier(String qualifier)
  {
    _textQualifier = qualifier;
  }

  public String getTextQualifier()
  {
    return _textQualifier;
  }

  public void setDateQualifier(String qualifier)
  {
    _dateQualifier = qualifier;
  }

  public String getDateQualifier()
  {
    return _dateQualifier;
  }

  public void setPriorityQualifier(String qualifier)
  {
    _priorityQualifier = qualifier;
  }

  public String getPriorityQualifier()
  {
    return _priorityQualifier;
  }

  public void setTextEntry(String textEntry)
  {
    _textEntry = textEntry;
  }

  public String getTextEntry()
  {
    return _textEntry;
  }

  public void setPriorityEntry(String priorityEntry)
  {
    _priorityEntry = priorityEntry;
  }

  public String getPriorityEntry()
  {
    return _priorityEntry;
  }

  //actually a Date object
  public void setDateEntry1(Object dateEntry1)
  {
    _dateEntry1 = dateEntry1;
  }

  //actually a Date object
  public Object getDateEntry1()
  {
    return _dateEntry1;
  }

  //actually a Date object
  public void setDateEntry2(Object dateEntry2)
  {
    _dateEntry2 = dateEntry2;
  }

  //actually a Date object
  public Object getDateEntry2()
  {
    return _dateEntry2;
  }

  private void _resetRowData()
  {
    _textQualifier = null;
    _dateQualifier = null;
    _priorityQualifier = null;
    _textEntry = null;
    _priorityEntry = null;
    _dateEntry1 = null;
    _dateEntry2 = null;
  }

  private String _criteria;
  private String _textQualifier;
  private String _dateQualifier;
  private String _priorityQualifier;
  private String _textEntry;
  private String _priorityEntry; 
  private Object _dateEntry1; 
  private Object _dateEntry2;
}
