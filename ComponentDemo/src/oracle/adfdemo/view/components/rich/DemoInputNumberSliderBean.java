/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import oracle.adf.view.rich.model.NumberRange;

public class DemoInputNumberSliderBean
{

  public Double getNumberValue()
  {
    return _numberValue;
  }
  
  public void setNumberValue(Double value)
  {
    _numberValue = value;
  }  

  public NumberRange getRangeValue()
  {  
    return _rangeValue;
  }
  
  public void setRangeValue(NumberRange value)
  {
    _rangeValue = new NumberRange(value.getMaximum(), value.getMinimum());
  }  

  public NumberRange getRangeValue2()
  {  
    return _rangeValue2;
  }
  
  public void setRangeValue2(NumberRange value)
  {
    _rangeValue2 = new NumberRange(value.getMaximum(), value.getMinimum());
  }  
  
  private Double _numberValue = 4.0;
  private NumberRange _rangeValue = new NumberRange(2, 6);
  private NumberRange _rangeValue2 = new NumberRange(8, 9);
}
