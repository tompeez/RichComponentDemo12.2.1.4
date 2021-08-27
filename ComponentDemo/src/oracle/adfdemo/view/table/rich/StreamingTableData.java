/* Copyright (c) 2007, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
package oracle.adfdemo.view.table.rich;

import oracle.adf.view.rich.model.AsyncFetch;
import oracle.adf.view.rich.model.AsyncFetcher;

import oracle.adf.view.rich.model.NumberRange;

public class StreamingTableData extends TableTestData implements AsyncFetch
{
  public StreamingTableData()
  {
    _fetcher = new TestFetcher();
  }
  
  public void setTimeout(int timeout) 
  {
    _fetcher.timeout = timeout;  
  }
  
  /**
   * Implement support for fetchConstraint of NumberRange
   */
  public boolean isSupportedFetchConstraint(Class<?> fetchConstraintsClass)
  {
    return _NUMBER_RANGE_CLASS.isAssignableFrom(fetchConstraintsClass);
  }
  
  /**
   * Return our NumberRange-based fetcher
   * @param fetchConstraint
   * @return
   */
  public AsyncFetcher getAsyncFetcher(Object fetchConstraint)
  {
    if (fetchConstraint instanceof NumberRange)
    {
      return _fetcher;
    }
    else
    {
      return null;
    }
  }
    
  private static final class TestFetcher extends AsyncFetcher
  {
    public TestFetcher()
    {
    }

    public boolean isFetched(AsyncFetch model, Object fetchRequest)
    {
      NumberRange range = (NumberRange)fetchRequest;
      int minimum = range.getMinimum().intValue();
      return isFetched(minimum, range.getMaximum().intValue() - minimum);
    }

    /*
     * Tells the model to fetch a range of rows. That range of rows will subsequently be
     * accessed on the request thread.
     * The method will return after all rows in the range have been fetched and cached.
     * @param first the index of the first row in the range
     * @param rows the number of rows in the range
     * @return the number of Objects in the specified range that are now available
     */
    public Object fetch(AsyncFetch model, Object fetchRequest, Object fetchContext) throws InterruptedException
    {
      NumberRange range = (NumberRange)fetchRequest;
      int minimum = range.getMinimum().intValue();
      return (Integer)fetch(minimum, range.getMaximum().intValue() - minimum);
    }
    
    public boolean isFetched(int first, int rows) 
    {
      return false;  
    }
    
    public int fetch(int first, int rows) 
    {
      if (timeout > 0) 
      {
        try
        {
          Thread.sleep(timeout);
        }
        catch (Exception e) 
        {  
        }
      }
      return 25;  
    }

    public int timeout = 0;
  }
  
  private final TestFetcher _fetcher;

  private static final Class _NUMBER_RANGE_CLASS = NumberRange.class;
}
