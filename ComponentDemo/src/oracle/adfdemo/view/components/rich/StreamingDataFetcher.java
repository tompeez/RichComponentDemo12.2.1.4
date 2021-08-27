/** Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import oracle.adf.view.rich.model.AsyncFetch;
import oracle.adf.view.rich.model.AsyncFetcher;

/**
 * Class used to fetch the demo streaming data asynchronously.
 */
public class StreamingDataFetcher
  extends AsyncFetcher
{
  public StreamingDataFetcher()
  {
    super();
  }

  /**
   * Check if the data has been fetched or not
   * @param model The fetch model
   * @param fetchConstraint The fetch constraint, not used in this demo
   * @return If the data has been fetched
   */
  @Override
  public boolean isFetched(AsyncFetch model, Object fetchConstraint)
  {
    StreamingDataStore ds = (StreamingDataStore)model;
    return ds.getStreamingData() != null;
  }

  /**
   * Fetches the data. For the purposes of this demo, a random thread sleep is being used to
   * delay the time from somewhere between 1 and 6 seconds. This is to simulate a long running
   * query to load the data and allows the demo to show that the data is indeed streaming and
   * rendering the components as it becomes available instead of in the rendering order.
   *
   * @param model The fetch model
   * @param fetchConstraint The fetch constraint, not used in this demo
   * @param fetchContext The fetch context, not used in this demo
   * @return The streaming data based on the index of the data store (index from the model)
   * @throws InterruptedException
   */
  @Override
  public Object fetch(AsyncFetch model, Object fetchConstraint, Object fetchContext)
    throws InterruptedException
  {
    StreamingDataStore ds = (StreamingDataStore)model;

    int time = (int)(Math.random() * 5000 + 1000);
    Thread.sleep(time);

    StreamingData sd = new StreamingData(ds.getIndex());
    ds.__setStreamingData(sd);

    return sd;
  }
}
