package oracle.adfdemo.view.table.rich;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.LocalRowKeyIndex;
import org.apache.myfaces.trinidad.model.SortableModel;

public class WhenAvailableData
{
  public WhenAvailableData()
  {
  }
  
  public CollectionModel getCollectionModel()
  {
    TableTestData data = new TableTestData(30, true);
    
    return new SortableModel(data)
    {
      //
      // return true for local APIs indicating that data is cached locally
      @Override
      public boolean isRowLocallyAvailable(int rowIndex)
      {
        return true;
      }

      @Override
      public boolean isRowLocallyAvailable(Object rowKey)
      {
        return true;
      }

      @Override
      public boolean areRowsLocallyAvailable(int startIndex, int rowCount)
      {
        return true;
      }

      @Override
      public boolean areRowsLocallyAvailable(Object startRowKey, int rowCount)
      {
        return true;
      }
      
      @Override
      public boolean areRowsLocallyAvailable(int rowCount)
      {
        return true;
      }
      
      @Override      
      public Confidence getEstimatedRowCountConfidence()
      {
        return Confidence.EXACT;
      }
      
      @Override      
      public LocalCachingStrategy getCachingStrategy()
      {
        return LocalCachingStrategy.ALL;
      }

    };
  }
  
}
