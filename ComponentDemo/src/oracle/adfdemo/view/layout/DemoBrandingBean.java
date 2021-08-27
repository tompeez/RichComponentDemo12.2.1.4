package oracle.adfdemo.view.layout;

import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidad.skin.Skin;

/**
 * Sample branding bean for fetching the branding bar separator and other misc. properties.
 */
public class DemoBrandingBean
{
  public String getNavigationTabBarHeight()
  {
    if (_cachedTabBarHeight == null)
    {
      RenderingContext rc = RenderingContext.getCurrentInstance();
      if (rc != null)
      {
        Skin skin = RenderingContext.getCurrentInstance().getSkin();
        Object height = skin.getProperty("af|navigationPane-tabs-tr-tab-bar-height");
        if (height == null)
        {
          _cachedTabBarHeight = "2em";
        }
        else
        {
          _cachedTabBarHeight = height.toString();
        }
      }
    }
    return _cachedTabBarHeight;
  }

  private String _cachedTabBarHeight = null;
}
