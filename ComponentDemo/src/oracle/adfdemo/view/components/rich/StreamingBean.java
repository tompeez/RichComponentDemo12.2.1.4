/** Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 * Request-scoped managed bean used by the af:streaming demo page. Uses 6 diffenent test data sets
 * that are used to stream in the different data types during the page load. The bean is stored
 * in the request by the data is store into the user's session map.
 */
public class StreamingBean
  implements HttpSessionListener
{
  /**
   * Return the indexes that are used in this demo to load different data. Allows the demo to loop
   * over the indexes in an iterator to create multiple streaming areas on the page.
   * @return list of integers
   */
  public List<Integer> getIndexes()
  {
    return _indexes;
  }

  /**
   * List of the streaming data classes that provides the data to be loaded asynchronously via
   * streaming.
   * @return List of the data for each of the indexes
   */
  public List<StreamingData> getStreamingData()
  {
    ArrayList<StreamingData> list = new ArrayList<StreamingData>(6);
    Map<Integer, StreamingDataStore> map = getStreamingModels();

    for (int i = 0; i < 6; ++i)
    {
      StreamingDataStore ds = map.get(i);
      list.add(ds.getStreamingData());
    }

    return list;
  }

  /**
   * Map of the index to the data store that is to be used to load the streaming data
   * asynchronously.
   * @return Map of the index to the data stores
   */
  public Map<Integer, StreamingDataStore> getStreamingModels()
  {
    if (_streamingModels == null)
    {
      // Get the data store map from the user's session and store it on this bean so we
      // don't need to keep getting it from the session during the current request
      _streamingModels = _getDataStoreMap();
    }

    return _streamingModels;
  }

  public void handleReset(@SuppressWarnings("unused") ActionEvent evt)
  {
    _streamingModels = null;

    Map<Integer, StreamingDataStore> map  = _getDataStoreMap();

    synchronized (map)
    {
      // Force the data to be reloaded, allows the demo to be run more than once per user session
      map.clear();
      _populateMap(map);
    }
  }

  /**
   * By using a HttpSessionListener, the bean is able to ensure that the data stores are only
   * created by one request.
   * @param se The session event
   */
  public void sessionCreated(HttpSessionEvent se)
  {
    HttpSession session = se.getSession();

    Map<Integer, StreamingDataStore> map = new HashMap<Integer, StreamingDataStore>(6);

    _populateMap(map);

    session.setAttribute("_STREAMING", Collections.synchronizedMap(map));
  }

  public void sessionDestroyed(HttpSessionEvent se)
  {
  }

  private void _populateMap(Map<Integer, StreamingDataStore> map)
  {
    for (int i = 0; i < 6; ++i)
    {
      map.put(i, new StreamingDataStore(i));
    }
  }

  private Map<Integer, StreamingDataStore> _getDataStoreMap()
  {
    Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext()
      .getSessionMap();

    @SuppressWarnings("unchecked")
    Map<Integer, StreamingDataStore> map = (Map<Integer, StreamingDataStore>)
      sessionMap.get("_STREAMING");

    return map;
  }

  private Map<Integer, StreamingDataStore> _streamingModels;
  private final List<Integer> _indexes = Arrays.asList(new Integer[] { 0, 1, 2, 3, 4, 5 });
}
