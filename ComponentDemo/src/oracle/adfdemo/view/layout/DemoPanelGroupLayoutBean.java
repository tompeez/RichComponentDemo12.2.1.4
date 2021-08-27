package oracle.adfdemo.view.layout;


public class DemoPanelGroupLayoutBean
{
  public String getMaxWidth()
  {
    return _maxWidth;
  }

  public String getLayout()
  {
    return _layout;
  }

  private String _layout = "vertical";
  private String _maxWidth = "1024px";
}
