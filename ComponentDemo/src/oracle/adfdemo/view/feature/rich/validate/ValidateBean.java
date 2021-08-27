/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich.validate;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.UIXInputPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.ReturnPopupEvent;

public class ValidateBean implements Serializable
{
  public ValidateBean()
  {
  }

  public BigDecimal getBigDecimal()
  {
    return _bigDecimal ;
  }

  public void setBigDecimal(BigDecimal bigDecimal)
  {
    _bigDecimal = bigDecimal;
  }


  public String getLengthValue()
  {
    return _lengthValue;
  }

  public void setLengthValue(String lengthValue)
  {
    _lengthValue = lengthValue;
  }

  public String getLengthValue2()
  {
    return _lengthValue2;
  }

  public void setLengthValue2(String lengthValue)
  {
    _lengthValue2 = lengthValue;
  }

  public String getLengthValue3()
  {
    return _lengthValue3;
  }

  public void setLengthValue3(String lengthValue)
  {
    _lengthValue3 = lengthValue;
  }

  public String getLengthValue4()
  {
    return _lengthValue4;
  }

  public void setLengthValue4(String lengthValue)
  {
    _lengthValue4 = lengthValue;
  }

  public String getLengthValue5()
  {
    return _lengthValue5;
  }

  public void setLengthValue5(String lengthValue)
  {
    _lengthValue5 = lengthValue;
  }

  public String getLengthValue6()
  {
    return _lengthValue6;
  }

  public void setLengthValue6(String lengthValue)
  {
    _lengthValue6 = lengthValue;
  }

  public String getLengthValue7()
  {
    return _lengthValue7;
  }

  public void setLengthValue7(String lengthValue)
  {
    _lengthValue7 = lengthValue;
  }

  public String getLengthValue8()
  {
    return _lengthValue8;
  }

  public void setLengthValue8(String lengthValue)
  {
    _lengthValue8 = lengthValue;
  }

  public String getLengthValue9()
  {
    return _lengthValue9;
  }

  public void setLengthValue9(String lengthValue)
  {
    _lengthValue9 = lengthValue;
  }

  public String getLengthValue10()
  {
    return _lengthValue10;
  }

  public void setLengthValue10(String lengthValue)
  {
    _lengthValue10 = lengthValue;
  }

  public Integer getInteger()
  {
    return _integer ;
  }

  public void setInteger(Integer integer)
  {
    _integer = integer;
  }

  public Integer getInteger2()
  {
    return _integer2 ;
  }

  public void setInteger2(Integer integer)
  {
    _integer2 = integer;
  }

  public Integer getInteger3()
  {
    return _integer3 ;
  }

  public void setInteger3(Integer integer)
  {
    _integer3 = integer;
  }

  public Integer getInteger4()
  {
    return _integer4 ;
  }

  public void setInteger4(Integer integer)
  {
    _integer4 = integer;
  }

  public Integer getInteger5()
  {
    return _integer5 ;
  }

  public void setInteger5(Integer integer)
  {
    _integer5 = integer;
  }

  public Integer getInteger6()
  {
    return _integer6 ;
  }

  public void setInteger6(Integer integer)
  {
    _integer6 = integer;
  }

  public Integer getInteger7()
  {
    return _integer7 ;
  }

  public void setInteger7(Integer integer)
  {
    _integer7 = integer;
  }

  public Integer getInteger8()
  {
    return _integer8 ;
  }

  public void setInteger8(Integer integer)
  {
    _integer8 = integer;
  }

  public Integer getInteger9()
  {
    return _integer9 ;
  }

  public void setInteger9(Integer integer)
  {
    _integer9 = integer;
  }

  public Short getShort()
  {
    return _short ;
  }

  public void setShort(Short shortObj)
  {
    _short = shortObj;
  }

  public Byte getByte()
  {
    return _byte ;
  }

  public void setByte(Byte byteObj)
  {
    _byte = byteObj;
  }

  public Long getLong()
  {
    return _long ;
  }

  public void setLong(Long longObj)
  {
    _long = longObj;
  }
  
  public Long getLong2()
  {
    return _long2 ;
  }
  
  public void setLong2(Long longObj)
  {
    _long2 = longObj;
  }

  public Long getLongValue()
  {
    return _longValue ;
  }

  public void setLongValue(Long longObj)
  {
    _longValue = longObj;
  }

  public Float getFloat()
  {
    return _float ;
  }

  public void setFloat(Float floatObj)
  {
    _float = floatObj;
  }

  public Double getDouble()
  {
    return _double ;
  }

  public void setDouble(Double doubleObj)
  {
    _double = doubleObj;
  }

  public Double getDouble2()
  {
    return _double2 ;
  }

  public void setDouble2(Double doubleObj)
  {
    _double2 = doubleObj;
  }

  public Double getDouble3()
  {
    return _double3;
  }

  public void setDouble3(Double doubleObj)
  {
    _double3 = doubleObj;
  }

  public Double getDouble4()
  {
    return _double4;
  }

  public void setDouble4(Double doubleObj)
  {
    _double4 = doubleObj;
  }

  public Double getDouble5()
  {
    return _double5 ;
  }

  public void setDouble5(Double doubleObj)
  {
    _double5 = doubleObj;
  }

  public Double getDouble6()
  {
    return _double6;
  }

  public void setDouble6(Double doubleObj)
  {
    _double6 = doubleObj;
  }

  public Double getDouble7()
  {
    return _double7;
  }

  public void setDouble7(Double doubleObj)
  {
    _double7 = doubleObj;
  }

  public Double getDouble8()
  {
    return _double8 ;
  }

  public void setDouble8(Double doubleObj)
  {
    _double8 = doubleObj;
  }

  public Double getDouble9()
  {
    return _double9 ;
  }

  public void setDouble9(Double doubleObj)
  {
    _double9 = doubleObj;
  }

  public Double getDouble10()
  {
    return _double10 ;
  }

  public void setDouble10(Double doubleObj)
  {
    _double10 = doubleObj;
  }

  public Date getDate()
  {
    return _date ;
  }

  public void setDate(Date date)
  {
    _date = date;
  }

  public Date getDate2()
  {
    return _date2 ;
  }

  public void setDate2(Date date)
  {
    _date2 = date;
  }

  public Date getDate3()
  {
    return _date3 ;
  }

  public void setDate3(Date date)
  {
    _date3 = date;
  }

  public Date getDate4()
  {
    return _date4 ;
  }

  public void setDate4(Date date)
  {
    _date4 = date;
  }

  public Date getDate5()
  {
    return _date5;
  }

  public void setDate5(Date date)
  {
    _date5 = date;
  }

  public Date getDate6()
  {
    return _date6;
  }

  public void setDate6(Date date)
  {
    _date6 = date;
  }

  public Date getDate7()
  {
    return _date7 ;
  }

  public void setDate7(Date date)
  {
    _date7 = date;
  }

  public Date getDate8()
  {
    return _date8 ;
  }

  public void setDate8(Date date)
  {
    _date8 = date;
  }

  public Date getDate9()
  {
    return _date9 ;
  }

  public void setDate9(Date date)
  {
    _date9 = date;
  }

  public Date getDate10()
  {
    return _date10;
  }

  public void setDate10(Date date)
  {
    _date10 = date;
  }

  public Date getDate11()
  {
    return _date11 ;
  }

  public void setDate11(Date date)
  {
    _date11 = date;
  }

  public Date getDate12()
  {
    return _date12 ;
  }

  public void setDate12(Date date)
  {
    _date12 = date;
  }

  public Date getDate13()
  {
    return _date13 ;
  }

  public void setDate13(Date date)
  {
    _date13 = date;
  }

  public Date getDate14()
  {
    return _date14;
  }

  public void setDate14(Date date)
  {
    _date14 = date;
  }

  public Date getDate15()
  {
    return _date15;
  }

  public void setDate15(Date date)
  {
    _date15 = date;
  }

  public Date getDate16()
  {
    return _date16 ;
  }

  public void setDate16(Date date)
  {
    _date16 = date;
  }

  public Date getDate17()
  {
    return _date17 ;
  }

  public void setDate17(Date date)
  {
    _date17 = date;
  }
  public Date getDate18()
  {
    return _date18 ;
  }

  public void setDate18(Date date)
  {
    _date18 = date;
  }
  public Date getDate19()
  {
    return _date19 ;
  }

  public void setDate19(Date date)
  {
    _date19 = date;
  }
  public Date getDate20()
  {
    return _date20 ;
  }

  public void setDate20(Date date)
  {
    _date20 = date;
  }
  public Date getDate21()
  {
    return _date21 ;
  }

  public void setDate21(Date date)
  {
    _date21 = date;
  }
  public Date getDate22()
  {
    return _date22 ;
  }

  public void setDate22(Date date)
  {
    _date22 = date;
  }
  public Date getDate23()
  {
    return _date23 ;
  }

  public void setDate23(Date date)
  {
    _date23 = date;
  }
  public Date getDate24()
  {
    return _date24 ;
  }

  public void setDate24(Date date)
  {
    _date24 = date;
  }

  public Color getColor()
  {
    return _color ;
  }

  public void setColor(Color color)
  {
    _color = color;
  }

  public Color getColor3()
  {
    return _color3 ;
  }

  public void setColor3(Color color)
  {
    _color3 = color;
  }

  public Color getColor4()
  {
    return _color4;
  }

  public void setColor4(Color color)
  {
    _color4 = color;
  }

  public Color getColor2()
  {
    return _color2 ;
  }

  public void setColor2(Color color)
  {
    _color2 = color;
  }

  public String getText()
  {
    return _text ;
  }

  public void setText(String text)
  {
    _text = text;
  }

  public Integer getSsn()
  {
    return _ssn ;
  }

  public void setSsn(Integer ssn)
  {
    _ssn = ssn;
  }


  public String getRegExpValue()
  {
    return _regExpValue;
  }

  public void setRegExpValue(String regExpValue)
  {
    _regExpValue = regExpValue;
  }

  public String getRegExpValue2()
  {
    return _regExpValue2;
  }

  public void setRegExpValue2(String regExpValue)
  {
    _regExpValue2 = regExpValue;
  }

  public String getRegExpValue3()
  {
    return _regExpValue3;
  }

  public void setRegExpValue3(String regExpValue)
  {
    _regExpValue3 = regExpValue;
  }

  public String getRegExpValue4()
  {
    return _regExpValue4;
  }

  public void setRegExpValue4(String regExpValue)
  {
    _regExpValue4 = regExpValue;
  }

  public String getByteLengthValue()
  {
    return _byteLegthValue;
  }

  public void setByteLengthValue(String value)
  {
    _byteLegthValue = value;
  }

  public String getByteLengthValue2()
  {
    return _byteLegthValue2;
  }

  public void setByteLengthValue2(String value)
  {
    _byteLegthValue2 = value;
  }

  public Number getCurrency()
  {
    return _currencyValue;
  }

  public void setCurrency(Number value)
  {
    _currencyValue = value;
  }
  
  public Number getCurrency1()
  {
    return _currencyValue1;
  }

  public void setCurrency1(Number value)
  {
    _currencyValue1 = value;
  }
  
  public Number getCurrency2()
  {
    return _currencyValue2;
  }

  public void setCurrency2(Number value)
  {
    _currencyValue2 = value;
  }
  
  public Number getCurrency3()
  {
    return _currencyValue3;
  }

  public void setCurrency3(Number value)
  {
    _currencyValue3 = value;
  }

  public Number getIntegerOnly()
  {
    return _intOnlyValue;
  }

  public void setIntegerOnly(Number value)
  {
    _intOnlyValue =  value;
  }


  public void setIntegerOnly1(Number integerOnly1)
  {
    this._integerOnly = integerOnly1;
  }

  public Number getIntegerOnly1()
  {
    return _integerOnly;
  }

  public void setConverterIntVal(Number integerValue)
  {
    this._converterIntVal = integerValue;
  }

  public Number getConverterIntVal()
  {
    return _converterIntVal;
  }

  public void setPercent(Number value)
  {
    _percentValue = value;
  }

  public Number getPercent()
  {
    return _percentValue;
  }

  public Number getPercent1()
  {
    return _percentValue1;
  }
  
  public void setPercent1(Number value)
  {
    _percentValue1 = value;
  }
  
  public Number getPercent2()
  {
    return _percentValue2;
  }
  
  public void setPercent2(Number value)
  {
    _percentValue2 = value;
  }
  
  public Number getPercent3()
  {
    return _percentValue3;
  }
  
  public void setPercent3(Number value)
  {
    _percentValue3 = value;
  }

  public Number getGroup()
  {
    return _groupValue;
  }

  public void setGroup(Number value)
  {
    _groupValue = value;
  }


  public void setShow(boolean show)
  {
    this._show = show;
  }

  public boolean isShow()
  {
    return _show;
  }

  public void setHide(boolean hide)
  {
    this._hide = hide;
  }

  public boolean isHide()
  {
    return _hide;
  }

  public void setShow2(boolean show2)
  {
    this._show2 = show2;
  }

  public boolean isShow2()
  {
    return _show2;
  }

  public void setHide2(boolean hide2)
  {
    this._hide2 = hide2;
  }

  public boolean isHide2()
  {
    return _hide2;
  }

  public void setShow3(boolean show3)
  {
    this._show3 = show3;
  }

  public boolean isShow3()
  {
    return _show3;
  }

  public void setHide3(boolean hide3)
  {
    this._hide3 = hide3;
  }

  public boolean isHide3()
  {
    return _hide3;
  }
  
  public void setShow4(boolean show4)
  {
    this._show4 = show4;
  }

  public boolean isShow4()
  {
    return _show4;
  }

  public void setHide4(boolean hide4)
  {
    this._hide4 = hide4;
  }

  public boolean isHide4()
  {
    return _hide4;
  }  

  public void toggle(ValueChangeEvent vce)
  {
    setShow3(Boolean.TRUE.equals(vce.getNewValue()));
    FacesContext.getCurrentInstance().renderResponse();
  }

  /**
   * This listener is used to demonstrate how users can add the dependent components that are 
   * marked required, as a partial target of the LOV component at runtime. When the user picks a 
   * value from the LOV popup it queues a ReturnPopupEvent and a listener for this event is a good 
   * place to add the components as partial targets so they get refreshed but not validated. 
   * @param event
   */
  public void processReturnPopup(ReturnPopupEvent event)
  {
    UIXInputPopup lovComponent = (UIXInputPopup)event.getSource();
    if(lovComponent != null)
    {
      if (_lovDependent1 != null)
      {
        AdfFacesContext afContext = AdfFacesContext.getCurrentInstance();
        afContext.addPartialTarget(_lovDependent1);
      }
    }    
  }
  
  public void setLovDependent1(RichInputText lovDependent1)
  {
    this._lovDependent1 = lovDependent1;
  }

  public RichInputText getLovDependent1()
  {
    return _lovDependent1;
  }

  public void setLovDependent2(RichInputText lovDependent2)
  {
    this._lovDependent2 = lovDependent2;
  }  

  public RichInputText getLovDependent2()
  {
    return _lovDependent2;
  }
  
  public String getClickCount()
  {
    return Integer.toString(_clickCount);
  }
  
  public void incrementClickCount()
  {
    _clickCount++;
  }
  
  public void handleCancel(ActionEvent event)
  {
    incrementClickCount();
  }
  
  RichInputText _lovDependent1;
  RichInputText _lovDependent2;

  private Number _currencyValue  = new Double(78.57);
  private Number _currencyValue1 = new Double(78.51);
  private Number _currencyValue2 = new Double(78.52);
  private Number _currencyValue3 = new Double(78.53);
 
  private Number _percentValue  = new Double(0.55);
  private Number _percentValue1 = new Double(0.51);
  private Number _percentValue2 = new Double(0.52);
  private Number _percentValue3 = new Double(0.53);
  
  private Number _intOnlyValue = new Double(99.99);

  private Number _groupValue = new Double(77777.89);

  private Number _converterIntVal = new Double(10);

  private boolean _show ;
  private boolean _hide =true;
  private boolean _show2 =true;
  private boolean _hide2 ;
  private boolean _show3 =true;
  private boolean _hide3 ;
  private boolean _show4 =true;
  private boolean _hide4 ;
  private Integer _ssn = null;
  private String  _text = "This is bound text";
  private Integer _integer = null;
  private Integer _integer2 = null;
  private Integer _integer3 = null;
  private Integer _integer4 = null;
  private Integer _integer5 = null;
  private Integer _integer6 = null;
  private Integer _integer7 = null;
  private Integer _integer8 = null;
  private Integer _integer9 = null;
  private BigDecimal _bigDecimal = null;
  private Long _long = null;
  private Long _long2 = null;
  private Long _longValue = null;
  private Short _short = null;
  private Byte _byte = null;
  private Double _double = null;
  private Double _double2 = null;
  private Double _double3 = null;
  private Double _double4 = null;
  private Double _double5 = null;
  private Double _double6 = null;
  private Double _double7 = null;
  private Double _double8 = null;
  private Double _double9 = null;
  private Double _double10 = 5.52;
  private Number _integerOnly  = new Double(1);
  private Float _float = null;
  private Date _date = new Date();
  private Date _date2 = new Date();
  private Date _date3 = new Date();
  private Date _date4 = new Date();
  private Date _date5 = new Date();
  private Date _date6 = new Date();
  private Date _date7 = new Date();
  private Date _date8 = new Date();
  private Date _date9 = new Date();
  private Date _date10 = new Date();
  private Date _date11 = new Date();
  private Date _date12 = new Date();
  private Date _date13 = new Date();
  private Date _date14 = new Date();
  private Date _date15 = new Date();
  private Date _date16 = new Date();
  private Date _date17 = new Date();
  private Date _date18 = new Date();
  private Date _date19 = new Date();
  private Date _date20 = new Date();
  private Date _date21 = new Date(1198000000000L); // 1/1/08
  private Date _date22 = new Date(1198000000000L);
  private Date _date23 = new Date(1198000000000L);
  private Date _date24 = new Date(1198000000000L);
  private Color _color = new Color(255, 0, 0);
  private Color _color2 = new Color(255, 0, 0);
  private Color _color3 = new Color(255, 0, 0);
  private Color _color4 = new Color(255, 0, 0);

  private String _regExpValue = null;
  private String _regExpValue2 = null;
  private String _regExpValue3 = null;
  private String _regExpValue4 = null;

  private String _byteLegthValue = null;
  private String _byteLegthValue2 = null;
  private String _lengthValue = null;
  private String _lengthValue2 = null;
  private String _lengthValue3 = null;
  private String _lengthValue4 = null;
  private String _lengthValue5 = null;
  private String _lengthValue6 = null;
  private String _lengthValue7 = null;
  private String _lengthValue8 = null;
  private String _lengthValue9 = null;
  private String _lengthValue10 = null;
  private int _clickCount = 0;
}
