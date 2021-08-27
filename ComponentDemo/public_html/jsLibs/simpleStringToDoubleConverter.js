/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
function SimpleStringToDoubleConverter()
{
  // for debugging
  this._class = "SimpleStringToDoubleConverter";

}

SimpleStringToDoubleConverter.prototype = new TrConverter();

SimpleStringToDoubleConverter.prototype.getFormatHint = function()
{
	return null;
}

SimpleStringToDoubleConverter.prototype.getAsString = function(
  number,
  label
  )
{
	var numberString = "" + number;
	if(numberString=="1" || numberString == "1.0")
	{
		return "ONE";
	}
	else if(numberString=="2" || numberString == "2.0")
	{
		return "TWO";
	}
	else if(numberString=="3" || numberString == "3.0")
	{
		return "THREE";
	}
	else if(numberString=="4" || numberString == "4.0")
	{
		return "FOUR";
	}
	else if(numberString=="5" || numberString == "5.0")
	{
		return "FIVE";
	}
	else if(numberString=="6" || numberString == "6.0")
	{
		return "SIX";
	}
	else if(numberString=="7" || numberString == "7.0")
	{
		return "SEVEN";
	}
	else if(numberString=="8" || numberString == "8.0")
	{
		return "EIGHT";
	}
	else if(numberString=="9" || numberString == "9.0")
	{
		return "NINE";
	}
	else if(numberString=="10" || numberString == "10.0")
	{
		return "TEN";
	}
	else
		return numberString;
}

SimpleStringToDoubleConverter.prototype.getAsObject = function(
  numberString,
  label
  )
{

	if(numberString=="ONE")
	{
		return 1;
	}
	else if(numberString=="TWO")
	{
		return 2;
	}
	else if(numberString=="THREE")
	{
		return 3;
	}
	else if(numberString=="FOUR")
	{
		return 4;
	}
	else if(numberString=="FIVE")
	{
		return 5;
	}
	else if(numberString=="SIX")
	{
		return 6;
	}
	else if(numberString=="SEVEN")
	{
		return 7;
	}
	else if(numberString=="EIGHT")
	{
		return 8;
	}
	else if(numberString=="NINE")
	{
		return 9;
	}
	else if(numberString=="TEN")
	{
		return 10;
	}
      // TODO throw TrConverterException when
      // numberString is not correct...
	return parseFloat(numberString);
}