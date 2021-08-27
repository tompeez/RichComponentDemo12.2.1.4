/*
** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved.
*/
function ShuttleValidator()
{
  // for debugging
  this._class = "ShuttleValidator";
}

ShuttleValidator.prototype = new TrValidator();

ShuttleValidator.prototype.validate = function(value, label, converter)
{
  var nActors = value.size();
   
  if (size > 5)
  {
    var fm = new TrFacesMessage("Please limit your selection to 5 file types!", 
                                "client", 
                                TrFacesMessage.SEVERITY_ERROR);
    throw new TrValidatorException(fm);
  }
}