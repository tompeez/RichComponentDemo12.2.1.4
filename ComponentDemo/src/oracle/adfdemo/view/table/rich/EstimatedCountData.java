package oracle.adfdemo.view.table.rich;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.LocalRowKeyIndex;
import org.apache.myfaces.trinidad.model.SortableModel;

public class EstimatedCountData
{
  /**
   * Create a CollectionModel with a true row count of _ROW_COUNT,
   * but return and estimated row count of _EST_ROW_COUNT from the
   * getRowCount() API
   * @return
   */
  public CollectionModel getCollectionModel()
  {
    TableTestData data = new TableTestData(_ROW_COUNT, true);
    
    return new SortableModel(data)
    {
      @Override
      public int getEstimatedRowCount()
      {
        return _EST_ROW_COUNT;
      }
      
      @Override
      public Confidence getEstimatedRowCountConfidence()
      {
        return Confidence.ESTIMATE;
      }
      
      @Override
      public int getRowCount()
      {
        return _ROW_COUNT;
      }
      
    };
  }

  public int getRowCount()
  {
    return _ROW_COUNT;
  }

  public int getEstimatedRowCount()
  {
    return _EST_ROW_COUNT;
  }

  private static final int _ROW_COUNT = 500;
  private static final int _EST_ROW_COUNT = 100;

}
