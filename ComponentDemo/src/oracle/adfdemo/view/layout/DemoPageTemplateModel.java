/** Copyright (c) 2009, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.layout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.model.PageTemplateModel;
import oracle.adf.view.rich.component.fragment.PageTemplateSite;

import org.apache.myfaces.trinidad.component.TableUtils;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;


/**
 * This class is both a page template model as well as a managed bean. It illustrates how the
 * page template model can be used to be the source of a view ID as well as be able to change
 * the contextual state of the page template.
 */
public class DemoPageTemplateModel
  extends PageTemplateModel
{
  public DemoPageTemplateModel()
  {
    // Just put some dummy data in the context so that it is not empty on first load.
    for (String[] row : _INITIAL_DATA)
    {
      _contextVariables.put(row[0], row[1]);
    }
  }

  @Override
  public String getViewId(FacesContext facesContext)
  {
    return _viewId;
  }

  /**
   * For use in the page EL
   */
  public String getCurrentViewId()
  {
    return _viewId;
  }

  public void setCurrentViewId(String viewId)
  {
    if (_VIEW_IDS[0].equals(viewId))
    {
      _viewId = _VIEW_IDS[0];
    }
    else
    {
      _viewId = _VIEW_IDS[1];
    }
  }

  @Override
  public void setupContext(
    FacesContext     facesContext,
    PageTemplateSite site)
  {
    super.setupContext(facesContext, site);
    // Illustrate how contextual variables can be setup so that EL expressions can access the
    // data inside of the page template that does not interfere with compononents outside of the
    // template or in a facetRef.
    if (_VIEW_IDS[0].equals(_viewId))
    {
      _oldValue = TableUtils.setupELVariable(facesContext, "context", _contextVariables);
      _oldKeys = TableUtils.setupELVariable(facesContext, "contextKeys",
        new ArrayList<String>(_contextVariables.keySet()));
    }
    else
    {
      _oldValue = TableUtils.setupELVariable(facesContext, "context", _createCollectionModel());
      _oldKeys = null;
    }
  }

  public List<SelectItem> getViewIds()
  {
    List<SelectItem> items = new ArrayList<SelectItem>(2);
    for (int i = 0, size = _VIEW_IDS.length; i < size; ++i)
    {
      items.add(new SelectItem(_VIEW_IDS[i], _VIEW_ID_NAMES[i]));
    }
    return items;
  }

  @Override
  public void tearDownContext(
    FacesContext     facesContext,
    PageTemplateSite site)
  {
    super.tearDownContext(facesContext, site);
    TableUtils.setupELVariable(facesContext, "context", _oldValue);
    TableUtils.setupELVariable(facesContext, "contextKeys", _oldKeys);
  }

  public final Map<String, String> getContextVariables()
  {
    return _contextVariables;
  }

  private CollectionModel _createCollectionModel()
  {
    ModelRow[] items = new ModelRow[_contextVariables.size()];

    Iterator<Map.Entry<String, String>> entryIter = _contextVariables.entrySet().iterator();
    for (int i = 0, size = items.length; i < size; ++i)
    {
      Map.Entry<String, String> entry = entryIter.next();
      items[i] = new ModelRow(entry.getKey(), entry.getValue());
    }

    return ModelUtils.toCollectionModel(items);
  }

  public class ModelRow
  {
    private String _key;
    private String _value;

    ModelRow(String key, String value)
    {
      this._key = key;
      this._value = value;
    }

    public final void setKey(String key)
    {
      _key = key;
    }

    public final String getKey()
    {
      return _key;
    }

    public final void setValue(String value)
    {
      _value = value;
    }

    public final String getValue()
    {
      return _value;
    }
  }

  private final static String[] _VIEW_IDS = new String []
  {
    "/components/pagetemplate/pageTemplateModelDemoList.jspx",
    "/components/pagetemplate/pageTemplateModelDemoTable.jspx"
  };
  private final static String[] _VIEW_ID_NAMES = new String []
  {
    "List", "Table"
  };
  private final static String[][] _INITIAL_DATA = new String[][]
  {
    new String[] { "Monday", "Cloudy, 65F" },
    new String[] { "Tuesday", "Partly Sunny, 69F" },
    new String[] { "Wednesday", "Partly Sunny, 71F" },
    new String[] { "Thursday", "Sunny, 75F" }
  };

  private Object _oldKeys;
  private Object _oldValue;
  private Map<String, String> _contextVariables = new LinkedHashMap<String, String>();
  private String _viewId = _VIEW_IDS[0];
}
