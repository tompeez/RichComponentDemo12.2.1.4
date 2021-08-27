function MyBrandingBar(containerId,brandingCompactClass)
{
  this.Init(containerId, brandingCompactClass);
}

AdfObject.createSubclass(MyBrandingBar, AdfObject);

MyBrandingBar._BBAR_INNER_NC = "branding";
MyBrandingBar._APP_NAVIG = "navig";
MyBrandingBar._LOGO_CONTAINER = "logo";
MyBrandingBar._LOGO_LINK = "logoLink";
MyBrandingBar._LOGO_LINK_ALT = "logoLinkAlt";
MyBrandingBar._LOGO_LINK_SM = "logoLinkSm";
MyBrandingBar._LOGO_LINK_ALT_SM = "logoLinkAltSm";

MyBrandingBar.prototype.Init = function (containerId, brandingCompactClass) 
{
  MyBrandingBar.superclass.Init.call(this);

  AdfAssert.assertString(containerId);
  this._containerId = containerId;
  this._brandingCompactClass = brandingCompactClass;
  this._resizeCallback = this.createCallback(this._resize);
  this._loadCallback = this.createCallback(this._handleLoad);

  var agent = AdfAgent.AGENT;
  var win = agent.getDomWindow();
  agent.addBubbleEventListener(win, "load", this._loadCallback);
  agent.addBubbleEventListener(win, "resize", this._resizeCallback);
}

MyBrandingBar.prototype._handleLoad = function () 
{
  if (!AdfPage.PAGE.isPageFullyLoaded())
  {
    this._handleLoadCallback = this.createCallback(this._handleLoad);
    setTimeout(this._handleLoadCallback, 100);
    return;
  }
  this._resizeOnTimeOut();
}

MyBrandingBar.prototype._resize = function() 
{
  if (this._resizeOnTimeOutCallback == null)
  {
    this._resizeOnTimeOutCallback = this.createCallback(this._resizeOnTimeOut);
  }
  
  if (this._resizeTimerId != null)
  {
    window.clearTimeout(this._resizeTimerId);
  }
  
  this._resizeTimerId = window.setTimeout(this._resizeOnTimeOutCallback, 100);
}

MyBrandingBar.prototype._visitChildrenPage = function(component)
{
  if (component.getId() == this.containerId)
  {
    this.brandingBarClientId = component.getClientId();
  }
}

MyBrandingBar.prototype._calculateClientId = function()
{
  var callbackState = {containerId: this._containerId, brandingBarClientId: null};
  AdfPage.PAGE.findComponent((AdfPage.PAGE.getDocument().forms[0].id)).visitChildren(this._visitChildrenPage, callbackState , true);
  
  return callbackState.brandingBarClientId;
}

MyBrandingBar.prototype._resizeOnTimeOut = function() 
{
  delete this._resizeTimerId;

  var brandingBarClientId = this._brandingBarClientId

  if (!brandingBarClientId)
  {
    brandingBarClientId = this._calculateClientId();
    
    if (!brandingBarClientId)
      return;
      
    this._brandingBarClientId = brandingBarClientId;
  }
  
  var doc = AdfAgent.AGENT.getDomDocument();
  var brandingBar = doc.getElementById(brandingBarClientId);

  if (!brandingBar)
    return;
    
  var splitClientId = brandingBarClientId.split(":");
  var pageTemplateNC = splitClientId[0];
  var bBarName = splitClientId[1];

  var appNavContainerId = pageTemplateNC + ":" + bBarName + ":" + MyBrandingBar._APP_NAVIG;
  var navBarContainer   = doc.getElementById(appNavContainerId);

  var fitsOnOneLine = this._fitsOnOneLine(doc, pageTemplateNC, bBarName, navBarContainer);
  var showBigLogo = fitsOnOneLine;
  
  if (navBarContainer)
  {
    AdfDomUtils.addOrRemoveCSSClassName(!fitsOnOneLine, brandingBar, this._brandingCompactClass);     
  }
  else
  {
    showBigLogo = false;
  }

  var logoLinkContainer = this._getLogoLinkContainer(doc, pageTemplateNC, bBarName);
  var logoLinkSmallContainer = this._getLogoLinkSmallContainer(doc, pageTemplateNC, bBarName);

  this._updateLogoVisibility(logoLinkContainer, logoLinkSmallContainer, showBigLogo);
}

MyBrandingBar.prototype._fitsOnOneLine = function(doc, pageTemplateNC, bBarName, navBarContainer)
{
  if (navBarContainer)
  {
    var brandingMaxWidthId =  pageTemplateNC + ":" + bBarName + ":" + MyBrandingBar._BBAR_INNER_NC;
    var maxWidthContainer = doc.getElementById(brandingMaxWidthId);
    var maxWidth = maxWidthContainer.offsetWidth;
    
    var logoContainerId = pageTemplateNC + ":" + bBarName + ":" + MyBrandingBar._LOGO_CONTAINER;
    var logoContainer = doc.getElementById(logoContainerId);
    var logoWidth = (logoContainer != null) ? logoContainer.offsetWidth : 0;
    
    var navBarWidth = navBarContainer.offsetWidth;

    //just see whether app nav bar and logo title container can be in 1 line
    return (maxWidth > logoWidth + navBarWidth + 30);
  }
  else
  {
    return false;
  }
}

MyBrandingBar.prototype._getLogoLinkContainer = function(doc, pageTemplateNC, bBarName)
{
  var logoLinkId = pageTemplateNC + ":" + bBarName + ":" + MyBrandingBar._LOGO_LINK;
  var logoLink = doc.getElementById(logoLinkId);

  if (logoLink)
    return logoLink;
    
  var logoLinkAltId = pageTemplateNC + ":" + bBarName + ":" + MyBrandingBar._LOGO_LINK_ALT;

  return doc.getElementById(logoLinkAltId);
}

MyBrandingBar.prototype._getLogoLinkSmallContainer = function(doc, pageTemplateNC, bBarName)
{
  var logoLinkSmallId = pageTemplateNC + ":" + bBarName + ":" + MyBrandingBar._LOGO_LINK_SM;
  var logoLinkSmall = doc.getElementById(logoLinkSmallId);

  if (logoLinkSmall)
    return logoLinkSmall;
    
  var logoLinkAltSmallId = pageTemplateNC + ":" + bBarName + ":" + MyBrandingBar._LOGO_LINK_ALT_SM;

  return doc.getElementById(logoLinkAltSmallId);
}

MyBrandingBar.prototype._updateLogoVisibility = function(logoLinkContainer, logoSmallLinkContainer, showBigLogo)
{  
  if (logoLinkContainer)
  {
    AdfDomUtils.setVisible(logoLinkContainer, showBigLogo);
  }
  
  if (logoSmallLinkContainer)
  {
    AdfDomUtils.setVisible(logoSmallLinkContainer, !showBigLogo);
  }
}
