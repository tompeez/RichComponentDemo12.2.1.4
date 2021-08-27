/* Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.*/

/**
 * Sample implementation that provides a wrapper XHR object. All the calls to the wrapper object
 * will be delegated to the wrapped xhr object that is passed in to the getXhr method.
 */
function AdfCustomRestResponseHandlerXhrFactory()
{
  this.Init();
}

AdfObject.createSubclass(AdfCustomRestResponseHandlerXhrFactory, AdfRestXhrFactory);

/**
 * @Override
 */
AdfCustomRestResponseHandlerXhrFactory.prototype.getXhr = function(xhr, properties)
{
  var url = properties[AdfRestXhrFactory.URL_KEY];
  var pathname = document.location.pathname.slice(1);
  var contextRoot = pathname.substr(0, pathname.indexOf('/'));

  // Identify the REST endpoint that needs to be authenticated here
  if (url.indexOf(contextRoot) != -1 && url.indexOf("custom_response=true") != -1)
  {
    return new CustomXhr(xhr);
  }
  return null;
}

// Register as a factory class with AdfRestXhrFactory
AdfRestXhrFactory.register(new AdfCustomRestResponseHandlerXhrFactory());

/**
 * Wrapper class for {XMLHttpRequest}
 */
function CustomXhr(xhr)
{
  this.Init(xhr);
}

CustomXhr.prototype.Init = function(xhr)
{
  this._xhr = xhr;
  this.readyState = this._xhr.readyState;

  var self = this;
  this._xhr.onreadystatechange = function()
  {
    self.readyState = self._xhr.readyState;
    self.status = self._xhr.status;
    self.statusText = self._xhr.statusText;

    if (self.readyState === XMLHttpRequest.DONE)
    {
      if (self.status == 200)
      {
        var responseObj = self._xhr.response;
        responseObj = responseObj.response.items;
        self.response = responseObj;
      }
      else
      {
        self.response = self._xhr.response;
      }
    }

    if (self.onreadystatechange)
      self.onreadystatechange();
  }
}

CustomXhr.prototype.abort = function()
{
  return this._xhr.abort();
}

CustomXhr.prototype.getAllResponseHeaders = function()
{
  return this._xhr.getAllResponseHeaders();
}

CustomXhr.prototype.getResponseHeader = function(header)
{
  return this._xhr.getResponseHeader(header);
}

CustomXhr.prototype.overrideMimeType = function(mimeType)
{
  return this._xhr.overrideMimeType(mimeType);
}

CustomXhr.prototype.send = function(data)
{
  this._xhr.responseType = this.responseType;
  return this._xhr.send(data);
}

CustomXhr.prototype.setRequestHeader = function(header, value)
{
  return this._xhr.setRequestHeader(header, value);
}
