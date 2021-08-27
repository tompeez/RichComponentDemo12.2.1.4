/* Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.*/

/**
 * This class is to demonstrate injection the JWT tokens set on the client component
 * into the request header of the REST calls
 */
function AdfJwtTokenInjector()
{
  this.Init();
}

AdfObject.createSubclass(AdfJwtTokenInjector, AdfRestXhrFactory);

/**
 * Injects the JWT Token into the request header
 * @Override
 */
AdfJwtTokenInjector.prototype.getXhr = function(xhr, properties)
{
  var url = properties[AdfRestXhrFactory.URL_KEY];
  var pathname = document.location.pathname.slice(1);
  var contextRoot = pathname.substr(0, pathname.indexOf('/'));

  // Identify the REST endpoint that needs to be authenticated here
  if (url.indexOf(contextRoot) != -1 && url.indexOf("auth=true") != -1)
  {
    var clientId = properties[AdfRestXhrFactory.CLIENT_ID_LIST_KEY][0];
    var component = AdfPage.PAGE.findComponent(clientId);

    // Read the request headers stashed on component using af:clientAttribute
    var componentProp = component.getProperty('jwtToken');
    if (!componentProp)
      return null;

    var reqHeaderMap = JSON.parse(component.getProperty('jwtToken'));

    // Build the request headers
    for (var key in reqHeaderMap)
      xhr.setRequestHeader(key, reqHeaderMap[key]);

    return xhr;
  }
  return null;
}

// Register as a factory class with AdfRestXhrFactory
AdfRestXhrFactory.register(new AdfJwtTokenInjector());
