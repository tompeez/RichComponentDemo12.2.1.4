/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
function StringBuilder(size)
{
  this.data = new Array(size?size:100);
  this.iStr = 0;
}

StringBuilder.prototype.append = function(obj)
{
  this.data[this.iStr++] = obj;
  if (this.iStr > StringBuilder.MAX_SIZE)
  {
    this.data = [this.data.join("")];
    this.data.length = 100;
    this.iStr = 1;
  }
  return this;
}

StringBuilder.prototype.toString = function()
{
  return this.data.join("");
}

StringBuilder.MAX_SIZE = document.all?500:10000;