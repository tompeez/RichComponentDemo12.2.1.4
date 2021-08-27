package oracle.adfdemo.view.inputSearch.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class SearchModelBase implements Serializable
{
  public SearchModelBase()
  {
    this._listeners = null;
  }

  public SearchModelBase(List<SearchModelChangeListener> listeners) 
  {
    this._listeners = listeners;
  }

  public Object getProperty(String key) 
  {
    return _props.get(key);
  }

  public void setProperty(String key, Object value) 
  {
    Object prev = getProperty(key);
    _props.put(key, value);

    if (_listeners != null && prev != null && !prev.equals(value))
    {
      for(SearchModelChangeListener listener : _listeners) 
      {
        listener.modelChanged(this);
      }
    }
  }

  public Set<Map.Entry<String, Object>> getEntrySet()
  {
    return _props.entrySet();
  }

  @Override
  public String toString() 
  {
    return _props.toString();
  }

  private final Map<String, Object> _props =
    new ConcurrentHashMap<String, Object>();
  private final List<SearchModelChangeListener> _listeners;
  private static final long serialVersionUID = 883882091364402872L;
}
