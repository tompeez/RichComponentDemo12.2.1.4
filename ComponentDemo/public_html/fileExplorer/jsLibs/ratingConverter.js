/*
** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved.
*/
function RatingConverter()
{
  // for debugging
  this._class = "RatingConverter";
}

RatingConverter.prototype = new TrConverter();

RatingConverter.prototype.getFormatHint = function()
{
  return null;
}

RatingConverter.prototype.getAsString = function(number, label)
{
  var numberString = "" + number;
  
  if(numberString=="0" || numberString == "0.0")
  {
    return "Needs Significant Improvement";
  }
  else if(numberString=="1" || numberString == "1.0")
  {
    return "1";
  }
  else if(numberString=="2" || numberString == "2.0")
  {
    return "2";
  }
  else if(numberString=="3" || numberString == "3.0")
  {
    return "3";
  }
  else if(numberString=="4" || numberString == "4.0")
  {
    return "4";
  }
  else if(numberString=="5" || numberString == "5.0")
  {	
    return "Average";
  }
  else if(numberString=="6" || numberString == "6.0")
  {
    return "6";
  }
  else if(numberString=="7" || numberString == "7.0")
  {
    return "7";
  }
  else if(numberString=="8" || numberString == "8.0")
  {
    return "8";
  }
  else if(numberString=="9" || numberString == "9.0")
  {
    return "9";
  }
  else if(numberString=="10" || numberString == "10.0")
  {
    return "The Best!";
  }
  return numberString;
}

RatingConverter.prototype.getAsObject = function(numberString, label)
{
  if(numberString=="Needs Significant Improvement")
  {
    return 0;
  }
  else if(numberString=="1")
  {
    return 1;
  }
  else if(numberString=="2")
  {
    return 2;
  }
  else if(numberString=="3")
  {
    return 3;
  }
  else if(numberString=="4")
  {
    return 4;
  }
  else if(numberString=="Average")
  {
    return 5;
  }
  else if(numberString=="6")
  {
    return 6;
  }
  else if(numberString=="7")
  {
    return 7;
  }
  else if(numberString=="8")
  {
    return 8;
  }
  else if(numberString=="9")
  {
    return 9;
  }	
  else if(numberString=="The Best!")
  {
    return 10;
  }

  return numberString;
}