/** Copyright (c) 2008, 2015, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.webapp.rich;

import java.io.IOException;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import java.util.StringTokenizer;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.render.RichRenderer;

import oracle.adf.view.rich.util.BuildInfo;

import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.skin.Skin;

/**
 * Bean used by the about dialog.
 */
public class AboutBean implements Serializable
{
  public String getSmallIconSource()
  {
    // Use the default image from the framework:
    return "/afr/favicon.png /afr/favicon.ico";
  }

  public String getLargeIconSource()
  {
    // Use the default image from the framework:
    return "/afr/touchicon.png";
  }

  public String goHome()
  {
    FacesContext       context          = FacesContext.getCurrentInstance();
    ExternalContext    external         = context.getExternalContext();
    Map<String,String> requestHeaderMap = external.getRequestHeaderMap();
    Object             referrerObject   = requestHeaderMap.get("Referer");

    // Use the documentation on OTN by default:
    String homeUrl = "http://www.oracle.com/technology/products/adf/adffaces/index.html";

    if (referrerObject != null)
    {
      String referrer = referrerObject.toString();
      if (referrer.length() > 0)
      {
        // If the webapp is being hosted on "localhost, 127.0.0.1, or jdevadf.oracle.com", use OTN.
        if (referrer.indexOf("localhost") == -1 &&
          referrer.indexOf("127.0.0.1") == -1 &&
          referrer.indexOf("jdevadf.oracle.com") == -1)
        {
          // Otherwise, go to the home page of the current server.
          // This might be used for example if there is an internal network home page for the RCF.
          int    endOfDoubleSlash = referrer.indexOf("//") + 2;
          String protocol         = referrer.substring(0, endOfDoubleSlash);
          String remainder        = referrer.substring(endOfDoubleSlash);

          int colonIndex = remainder.indexOf(":");
          if (colonIndex != -1)
          {
            remainder = remainder.substring(0, colonIndex);
          }

          int slashIndex = remainder.indexOf("/");
          if (slashIndex != -1)
          {
            remainder = remainder.substring(0, slashIndex);
          }

          // This should be the protocol plus the server name (no port nor context paths):
          homeUrl = protocol + remainder;
        }
      }
    }

    if (_redirectTo(homeUrl))
    {
      // The redirect was a success.
      return null;
    }
    else
    {
      // If the redirect failed, go to the index outcome of this app:
      return "index";
    }
  }

  private static final boolean _redirectTo(String desiredUrl)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext external = context.getExternalContext();
    try
    {
      external.redirect(desiredUrl);
      return true; // redirect succeeded
    }
    catch (IllegalStateException ise)
    {
      _LOG.warning(ise);
    }
    catch (IOException e)
    {
      _LOG.warning(e);
    }

    return false; // redirect failed
  }

  public String getAboutLabel()
  {
    if (_aboutLabel == null)
    {
      String  apiVersion = "";
      Package apiPackage = _getApiPackage();
      if (apiPackage != null)
      {
        apiVersion = apiPackage.getImplementationVersion();
      }

      if (apiVersion == null)
      {
        apiVersion = "Unknown Version";
      }

      _aboutLabel = "About " + apiVersion + "...";
    }
    return _aboutLabel;
  }

  public List<NameValuePair> getVersionInformation()
  {
    List<NameValuePair> info = new ArrayList<NameValuePair>();

    // The API info and implementation info doesn't change per session.
    if (_apiInfo == null || _implInfo == null)
    {
      Package apiPackage = _getApiPackage();
      Package implPackage = _getImplPackage();

      // The apiPackage or implPackage might be null in dev environment, when we have manually-built
      // jars in use.
      // Also, the version strings won't always match! The impl string will usually have repository
      // revision information and/or build number info whereas the api will usually just have the
      // milestone or release number.
      String packageTitle = null;
      String packageVersion = null;

      // Gather the API information:
      if (apiPackage != null)
      {
        packageTitle = apiPackage.getSpecificationTitle();
        packageVersion = apiPackage.getImplementationVersion();
      }
      if (packageTitle == null)
      {
        packageTitle = "ADF Faces API";
      }
      if (packageVersion == null)
      {
        packageVersion = "Unknown version";
      }
      _apiInfo = new NameValuePair(packageTitle, packageVersion);

      // Gather the implementation information:
      packageTitle   = null;
      packageVersion = null;
      if (implPackage == null)
      {
        packageVersion = "Unavailable";
      }
      else
      {
        packageTitle = implPackage.getSpecificationTitle();
        packageVersion = implPackage.getImplementationVersion();
      }
      if (packageTitle == null)
      {
        packageTitle = "ADF Faces Implementation";
      }
      if (packageVersion == null)
      {
        packageVersion = "Unknown version";
      }
      _implInfo = new NameValuePair(packageTitle, packageVersion);
    }

    info.add(_apiInfo);
    info.add(_implInfo);

    RenderingContext rc = RenderingContext.getCurrentInstance();
    if (rc != null)
    {
      // Gather skin information (dynamic within a session):
      RequestContext requestContext = RequestContext.getCurrentInstance();
      Skin skin = rc.getSkin();
      String skinId = skin.getId();
      if (skinId != null)
      {
        String skinFamily = requestContext.getSkinFamily();
        if (skinFamily != null)
        {
          info.add(new NameValuePair("Skin", skinId + " (" + skinFamily + ")"));
        }
        else
        {
          info.add(new NameValuePair("Skin", skinId));
        }
      }

      // Gather user agent information
      info.add(new NameValuePair("User Agent", rc.getAgent().toString()));

      //Adding an empty pair before the Version Info to allow nicer spacing
      info.add(new NameValuePair("", ""));
      
      // Grabbing the version info that's in the comments. I expect 4 pieces of information here when
      // parsing on "/" (API Name, ADF Faces Impl, Version Info, JSF Version) and of these I'm only
      // interested in the 3rd item.
      String versionInfo = BuildInfo.getVersionInformation(requestContext);
      StringTokenizer tokenizer = new StringTokenizer(versionInfo, "/");

      //If we don't find the 4 tokens, bail
      if (tokenizer.countTokens() >= 4)
      {
        tokenizer.nextToken(); //API Name
        tokenizer.nextToken(); //ADF Faces Impl
        NameValuePair versionInfoPair = new NameValuePair("Version Information", tokenizer.nextToken()); //Version Info
        info.add(versionInfoPair);    
      }
    }

    return info;
  }

  private Package _getApiPackage()
  {
    Class<RichRenderer> apiClass = RichRenderer.class;
    return apiClass.getPackage();
  }

  private Package _getImplPackage()
  {
    try
    {
      // HACK - Use of adfinternal classes is not supported; code of this package will change
      //        without notice.
      Class implClass = Class.forName("oracle.adfinternal.view.faces.renderkit.rich.RichRenderKit");
      return implClass.getPackage();
    }
    catch (ClassNotFoundException ex)
    {
      return null;
    }
  }

  public static class NameValuePair implements Serializable
  {
    NameValuePair(String name, String value)
    {
      _name = name;
      _value = value;
    }

    public String getName()
    {
      return _name;
    }

    public String getValue()
    {
      return _value;
    }

    private String _name;
    private String _value;

    private static final long serialVersionUID = 1L;
  }

  private String        _aboutLabel;
  private NameValuePair _apiInfo;
  private NameValuePair _implInfo;

  private static final ADFLogger _LOG = ADFLogger.createADFLogger(AboutBean.class);

  private static final long serialVersionUID = 1L;
}
