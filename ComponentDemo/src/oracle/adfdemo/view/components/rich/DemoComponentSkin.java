/** Copyright (c) 2008, 2019, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.DefaultHintDisplay;
import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;

/**
 * Get the skin-family to use. This returns the demoComponents when
 * the skin component demos are run.
 */
public class DemoComponentSkin implements Serializable
{
  public String getSkinFamily()
  {
    return _skinFamily;
  }

  public void setSkinFamily(String family)
  {
    _skinFamily = family;
  }

  public String getSkinVersion()
  {
    return _skinVersion;
  }

  public void setSkinVersion(String skinVersion)
  {
    _skinVersion = skinVersion;
  }
  
  public boolean isAltaBased()
  {
    // TODO ALTA
    // Temporary Hack until we figure out a mechanism to detect if our skin is inheriting from alta 
    return _skinFamily != null && (_skinFamily.contains("alta") || "richDemo".equals(_skinFamily)) ;    
  }
  
  public void skinMenuAction(ActionEvent ae)
  {
    RichCommandMenuItem menuItem = (RichCommandMenuItem) ae.getComponent();

    // This allows the text of the menuItem to be different from the skin family
    String skinFamily = (String)menuItem.getAttributes().get("skinFamily");
    String skinVersion = (String)menuItem.getAttributes().get("skinVersion");

    // Use the extended skin instead of the original skin to include skin from DVT
    if (skinFamily == null)
    {
      skinFamily = menuItem.getText();
      // look for a space; the text after the space is the version. like 'fusionFx v2'
      String[] menuItemValues = skinFamily.split("\\s");
      setSkinFamily(menuItemValues[0]);
      if (menuItemValues.length > 1)
        setSkinVersion(menuItemValues[1]);
      else
        setSkinVersion(null);
    }
    else
    {
      setSkinFamily(skinFamily);
      setSkinVersion(skinVersion);  
    }
    
    Object flip = menuItem.getAttributes().get("flip");
    
    if (flip == null)
    {
      this.setTheme1("contentBody");
      this.setTheme2("medium");
      this.setTheme3("light");
      this.setTheme3("dark");
      this.setTheme3("default");
    }
    else
    {
      this.setTheme1("medium");
      this.setTheme2("dark");
      this.setTheme3("medium");
    }
    
    reloadThePage();
  }

  public static void reloadThePage()
  {
    FacesContext fContext = FacesContext.getCurrentInstance();
    String viewId = fContext.getViewRoot().getViewId();
    String actionUrl = fContext.getApplication().getViewHandler().getActionURL(fContext, viewId);
    try
    {
      ExternalContext eContext = fContext.getExternalContext();
      String resourceUrl = actionUrl; //eContext.encodeResourceURL(actionUrl);
      // Use the action URL directly since the encoding a resource URL will NPE in isEmailablePage()
      eContext.redirect(resourceUrl);
    }
    catch (IOException ioe)
    {
      System.err.println("Problem trying to reload the page:");
      ioe.printStackTrace();
    }
  }
  
  public void setTheme1(String theme1)
  {
    _theme1 = theme1;
  }

  public String getTheme1()
  {
    return _theme1;
  }

  public void setTheme2(String theme2)
  {
    _theme2 = theme2;
  }

  public String getTheme2()
  {
    return _theme2;
  }

  public void setTheme3(String _theme3)
  {
    this._theme3 = _theme3;
  }

  public String getTheme3()
  {
    return _theme3;
  }

  public void setTheme4(String _theme4)
  {
    this._theme4 = _theme4;
  }

  public String getTheme4()
  {
    return _theme4;
  }
  
  public void setTheme5(String _theme5)
  {
    this._theme5 = _theme5;
  }

  public String getTheme5()
  {
    return _theme5;
  }
  
  public void setHintNone(ActionEvent ae) 
  {
    this._hintDisplayMode = DefaultHintDisplay.NONE;
  }
  
  public void setHintAuto(ActionEvent ae) 
  {
    this._hintDisplayMode = DefaultHintDisplay.AUTO;
  }
  
  public boolean isShowTips() 
  {
    return this._hintDisplayMode == DefaultHintDisplay.AUTO || this._hintDisplayMode == null;
  }
  
  // This method is supplied to web.xml context-param "oracle.adf.view.rich.component.DEFAULT_HINT_DISPLAY"
  // in form of an EL expression
  public DefaultHintDisplay getDefaultHintDisplay() 
  {
    return this._hintDisplayMode;
  }
  
  private String _skinFamily = _RICH_DEMO_SKIN;
  private String _skinVersion;
  
  // in certain skins we want to change the dark theme to medium and medium to dark.
  private String _theme1 = "contentBody";
  private String _theme2 = "medium";
  private String _theme3 = "light";
  private String _theme4 = "dark";
  private String _theme5 = "default";
  
  // Hint presentation mode for the demo application
  private DefaultHintDisplay _hintDisplayMode = DefaultHintDisplay.AUTO;

  static private final String _TEST_SKIN_INPUT_SELECT = "demoComponentsInputSelect";
  static private final String _TEST_SKIN_INPUT_OTHER = "demoComponentsOther";
  static private final String _RICH_DEMO_SKIN = "richDemo";

  private static final long serialVersionUID = 1L;

}
