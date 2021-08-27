/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
function ssnGetAsString(value, label)
{
  return value.substring(0,3) + '-' + value.substring(3,5) + '-' + value.substring(5);
}

function ssnGetAsObject(value, label)
{ 
  if (!value)return null;
  var len=value.length;
  var messageKey = SSNConverter.NOT;
  if (len < 9 )
    messageKey = SSNConverter.SHORT;
  else if (len > 11)
    messageKey = SSNConverter.LONG;
  else if (len == 9)
  { if (!isNaN(value))
      return value;
  }
  else if (len == 11 && value.charAt(3) == '-' && 
            value.charAt(6) == '-')
  {
    var result = value.substring(0,3) + value.substring(4,6) + 
                value.substring(7);
    if (!isNaN(result))
      return result;
  }
  if (messageKey!=null && this._messages!=null)
  { 
    // format the detail error string
    var detail = this._messages[messageKey];
    if (detail != null)
    {
      detail = TrFastMessageFormatUtils["format"](detail,label,value);
    }
  
    var facesMessage = new TrFacesMessage(
                        this._messages[SSNConverter.SUMMARY],
                        detail,
                        TrFacesMessage.SEVERITY_ERROR)
   throw new TrConverterException(facesMessage);
 }
 return null;
}
function SSNConverter(messages)
  {this._messages = messages;}
SSNConverter.prototype = new TrConverter();
SSNConverter.prototype.getAsString = ssnGetAsString;
SSNConverter.prototype.getAsObject = ssnGetAsObject;
SSNConverter.SUMMARY = 'SUM';
SSNConverter.SHORT = 'S';
SSNConverter.LONG  = 'L';
SSNConverter.NOT   = 'N';