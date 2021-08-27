package oracle.adfdemo.view.feature.rich.dvt;

import oracle.adfdemo.view.feature.rich.dvt.data.ElectionData;

/**
 * Class supporting a concrete implementation of an election mashup demo that uses the data from 
 * <code>ElectionData</code>.
 */
public class ElectionMashup extends ElectionData {
  private String componentType = "thematicMapAreas";
  private int currentYear = 2012;

  /**
   * Returns the model for the current year.
   */
  public ElectionData.PresidentialModel getCurrentModel() {
    return getModelMap().get(String.valueOf(currentYear));
  }

  public void setComponentType(String componentType) {
    this.componentType = componentType;
  }
  
  public String getComponentType() {
    return componentType;
  }
  
  public void setCurrentYear(int currentYear) {
    this.currentYear = currentYear;
  }

  public int getCurrentYear() {
    return currentYear;
  }
}
