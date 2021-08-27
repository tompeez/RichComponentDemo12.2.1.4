package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Random;

import oracle.adf.view.faces.bi.component.thematicMap.UIAreaDataLayer;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adfdemo.view.feature.rich.thematicMap.ElectionBean.Candidate;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class OfficesBean {

  private int numFloors = 6;
  private List<Floor> floorData = new ArrayList<Floor>();
  private Floor currentSelectedFloor;
  private CollectionModel floorModel;
  private RowKeySet selected;
  
  public OfficesBean() {
    createFloors();
    floorModel = ModelUtils.toCollectionModel(floorData);
    Object currRowKey = floorModel.getRowKey();
    selected = new RowKeySetImpl();
    selected.add(currRowKey);
    currentSelectedFloor = (Floor) floorModel.getRowData(currRowKey);
  }

  private void createFloors() {
    for (int i = 0; i < numFloors; i++)
      floorData.add(new Floor(i+1));
  }
  
  public RowKeySet getSelected() {
    return selected;
  }
  
  public CollectionModel getFloors() {
    return floorModel;
  }
  
  public Floor getCurrentFloor() {
    return currentSelectedFloor;
  }

  public void processSelection(SelectionEvent selectionEvent) {
    RichTable table = (RichTable) selectionEvent.getSource();
    RowKeySet selectedKeys = table.getSelectedRowKeys();
    for (Object rowKey : selectedKeys) {
      // should just be one selected row key
      currentSelectedFloor = (Floor) floorModel.getRowData(rowKey);
      break;
    }
  }
  
  public static class Floor {
    private int floor;
    private List officeData = new ArrayList();
    private int numLocations = 31;
    private int numAvail = 0;
    private CollectionModel officeModel;

    public Floor(int floor) {
      this.floor = floor;
      createFloorplan();
    }
    
    private void createFloorplan() {
      Random random = new Random();
      int officeNum = floor * 100;
      int officeId = 100;
      for (int i = 0; i < numLocations; i++) {
        boolean isOpen = random.nextInt(5) == 0;
        if (isOpen) {
          officeData.add(new SampleMapData.MapData(Integer.toString(officeId+i), new String[]{"Available", Integer.toString(officeNum+i)}, 0, new Color(104, 193, 130)));
          numAvail++;
        } else {
          officeData.add(new SampleMapData.MapData(Integer.toString(officeId+i), new String[]{"Occupied", Integer.toString(officeNum+i)}, 1, new Color(237, 102, 71)));
        }
      }
    }

    public int getFloor() {
      return floor;
    }
    
    public int getOpenOffices() {
      return numAvail;
    }
    
    public CollectionModel getOffices() {
      if (officeModel == null)
        officeModel = ModelUtils.toCollectionModel(officeData);
      return officeModel;
    }
  }
}
