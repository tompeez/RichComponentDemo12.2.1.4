/** Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import oracle.adf.view.rich.model.AsyncFetch;
import oracle.adf.view.rich.model.AsyncFetcher;

/**
 * Streaming model for the af:streaming component in the demo page. Implements the AsyncFetch
 * interface to be able to fetch the data asynchronously from a streaming request.
 */
public class StreamingDataStore
  implements AsyncFetch
{
  /**
   * Constructor
   * @param index Index used as a simple "key" in this demo to decide what data to load
   */
  public StreamingDataStore(int index)
  {
    _index = index;
  }

  /**
   * This demo class does not require any specify fetch constraint, so true is always returned
   * @param fetchConstraintsClass The class of the fetch constraints to check
   * @return This class always returns true
   */
  @Override
  public boolean isSupportedFetchConstraint(Class<?> fetchConstraintsClass)
  {
    return true;
  }

  /**
   * Returns a fetcher that will be used to load the data asynchronously
   * @param fetchConstraint The fetch constraint, should be null for the purposes of this demo
   *        class
   * @return The fetcher to use to fetch the data
   */
  @Override
  public AsyncFetcher getAsyncFetcher(Object fetchConstraint)
  {
    return new StreamingDataFetcher();
  }

  /**
   * Get the index used to determine what demo data to load
   * @return The index
   */
  int getIndex()
  {
    return _index;
  }

  /**
   * Get the data to be loaded by streaming
   * @return the streaming data or null if not yet loaded
   */
  public StreamingData getStreamingData()
  {
    return _streamingData;
  }

  /**
   * Sets the streaming data
   * @param data the streaming data
   */
  void __setStreamingData(StreamingData data)
  {
    _streamingData = data;
  }

  private volatile StreamingData _streamingData;
  private final int _index;
}
