/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */

function ssnValidate(value, label)
{ 
  if (!value)return;
  
 if (value.toString().indexOf('123') != 0)
 {
    var detail = this._detail;   
    if (detail != null)
    {
      detail = TrFastMessageFormatUtils["format"](detail, label, value);
    }
    var facesMessage = new TrFacesMessage(
                        this._summary,
                        detail,
                        TrFacesMessage.SEVERITY_ERROR)
   throw new TrValidatorException(facesMessage);
 }
}
 
 
function SSNValidator(summary, detail)
  {this._detail = detail;
   this._summary = summary;}
SSNValidator.prototype = new TrValidator();
SSNValidator.prototype.validate = ssnValidate;