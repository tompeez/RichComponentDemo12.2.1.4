package oracle.adfdemo.view.feature.rich.thematicMap;

import java.util.List;

import oracle.adf.view.faces.bi.component.thematicMap.mapProvider.MapProvider;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

public class MapProviderBean {

  protected static String[] territoryNames = new String[]{"Northwest Territories", "Yukon Territory", "Nunavut", 
                                                         "British Columbia", "Alberta", "Saskatchewan", "Manitoba", 
                                                         "Ontario", "Qu\u00E9bec", "New Brunswick", "Nova Scotia",
                                                         "Prince Edward Island", "Newfoundland And Labrador"};
  protected List territories;
  private MapProvider m_callback;

  public MapProviderBean() {
    territories = SampleMapData.createData(territoryNames, 100);
  }

  public MapProvider getCallback() {
    if (m_callback == null)
      m_callback = new DemoMapProvider();

    return m_callback;
  }

  public CollectionModel getTerritories() {
    CollectionModel model = ModelUtils.toCollectionModel(territories);
    return model;
  }

}

