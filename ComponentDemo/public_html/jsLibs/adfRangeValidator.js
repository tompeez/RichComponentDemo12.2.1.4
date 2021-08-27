/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
function AdfRangeValidator()
{
  // for debugging
  this._class = "AdfRangeValidator";
}

AdfRangeValidator.prototype = new TrValidator();


AdfRangeValidator.prototype.validate = function(value, label, converter)
{
	var max = value.getMaximum();
	var min = value.getMinimum();
	var dif = max-min;
	if (dif == 2)
	{
		var fm = new TrFacesMessage("Range", 
                          "not a clever range!", 
                          TrFacesMessage.SEVERITY_ERROR);
		throw new TrValidatorException(fm);
	}
}