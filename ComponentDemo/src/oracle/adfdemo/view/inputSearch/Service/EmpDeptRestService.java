package oracle.adfdemo.view.inputSearch.Service;


import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import java.io.IOException;
import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.adfdemo.view.inputSearch.model.DemoSearchModel;
import oracle.adfdemo.view.inputSearch.model.FilterDef;
import oracle.adfdemo.view.inputSearch.model.SearchModelBase;
import oracle.adfdemo.view.inputSearch.model.SearchModelChangeListener;

import org.apache.myfaces.trinidadinternal.util.JsonUtils;


/**
 * Mocked REST service faking a service that operating on Employee table
 * from reference HR database.
 */
@SuppressWarnings("unchecked")
public class EmpDeptRestService extends HttpServlet implements Serializable
{
  @Override
  public void init() throws ServletException
  {
    super.init();

    _searchModel = DemoSearchModel.getInstance();
    _searchModel.addChangeListener(new DemoSearchModelChangeListener());
  }

  /**
   * URL endpoints:
   *    /departments - returns a list of Departments filtered by URL paramerers, if any
   *    /employees   - returns a list of Employees filtered by URL paramerers, if any
   *
   * URL query format supported in usual form: ?attribute1=value1; attribute2=value2;
   *
   * Reserved params: cache=expiry|Etag|nocache to simulate different caching behaviors for testing
   *
   * @param httpServletRequest
   * @param httpServletResponse
   * @throws ServletException
   * @throws IOException
   */
  @Override
  @SuppressWarnings("unchecked")
  protected void doGet(
    HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse)
    throws ServletException, IOException
  {
    Map<String, String[]> reqParams = httpServletRequest.getParameterMap();
    boolean isAuthEnabled = reqParams.containsKey(_SECURITY_CMD);
    if (isAuthEnabled)
    {
      String authToken = httpServletRequest.getHeader(DEMO_MOCK_HEADER_TOKEN_KEY);

      // If the authorization token in the header does not match the server token,
      // respond back with a HTTP 401 status
      if (!DEMO_MOCK_HEADER_TOKEN_VALUE.equals(authToken))
      {
        // This should be ideally the case
        // httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        httpServletResponse.getWriter().write("{\"items\":[{\"dateOfBirth\":\"\",\"tags\":\"" +
          "Not Authorized to view\"," +
          "\"hireDate\":\"Not Authorized to view\",\"lName\":\"\",\"" +
          "fName\":\"Not Authorized to view\"," +
          "\"mgrId\":\"\",\"id\":\"-1\",\"email\":\"" +
          "Not Authorized to view\",\"profileKey\":\"\",\"mName\":\"\",\"genderCode\":\"\"," +
          "\"deptId\":\"-1\",\"jobTitle\":\"\"}]}");

        return;
      }
    }

    _setHeaders(httpServletResponse, reqParams);
    if (_isDataUnchanged(httpServletRequest))
    {
      httpServletResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);

      // Client's cache is still valid, Do not send any further response
      return;
    }

    String entity = _getEntity(httpServletRequest);
    int rowLimit = _getRowLimit(reqParams);
    Multimap<String, String> attributeValueFilterMap = _getAttributeFilterMap(reqParams);
    List<SearchModelBase> responseItems = new ArrayList<SearchModelBase>();
    FilterDef filterDef = _getFilterDef(reqParams);
    if (entity.equalsIgnoreCase("departments"))
    {
      responseItems =
        (List<SearchModelBase>) _searchModel.getDepartments(attributeValueFilterMap, rowLimit);
    }
    else if (entity.equalsIgnoreCase("employees"))
    {
      responseItems =
        (List<SearchModelBase>) _searchModel.getEmployees(attributeValueFilterMap, rowLimit, filterDef);
    }

    boolean isMRURequest = reqParams.containsKey(_MRU_CMD);
    boolean needCustomResponse = reqParams.containsKey(_CUSTOM_RESPONSE_CMD);
    _sendResponse(httpServletResponse, responseItems, isMRURequest, needCustomResponse,
      rowLimit > DemoSearchModel.DEFAULT_ROW_LIMIT &&
      responseItems.size() == DemoSearchModel.DEFAULT_ROW_LIMIT);
  }

  private static FilterDef _getFilterDef(Map<String, String[]> reqParams)
  {
    String[] filterParamArr = reqParams.get(_FILTER_CMD);
    if (filterParamArr != null && filterParamArr.length != 0)
    {
      String filterParam = filterParamArr[0];
      String[] filterDefStrings = filterParam.split(";");
      if (filterDefStrings.length != 3)
      {
        throw new IllegalArgumentException("Malformed filter query parameter");
      }

      return new FilterDef(filterDefStrings[0],
                           Arrays.asList(filterDefStrings[1].split(",")),
                           Arrays.asList(filterDefStrings[2].split(",")));
    }
    return null;
  }

  private static Multimap<String, String> _getAttributeFilterMap(Map<String, String[]> reqParams)
  {
    Multimap<String, String> attributeValueFilterMap =
      MultimapBuilder.hashKeys().arrayListValues().build();
    for(Map.Entry<String, String[]> entry : reqParams.entrySet())
    {
      if (_isPredefinedAttribute(entry.getKey()))
      {
        continue;
      }

      String[] values = entry.getValue();
      for (String value : values)
      {
        attributeValueFilterMap.put(entry.getKey(), value);
      }
    }
    return attributeValueFilterMap;
  }

  private static int _getRowLimit(Map<String, String[]> reqParams)
  {
    String[] limitCmd = reqParams.get(_LIMIT_CMD);
    int rowLimit = DemoSearchModel.DEFAULT_ROW_LIMIT;
    if (limitCmd != null && limitCmd.length > 0)
    {
      rowLimit = Integer.parseInt(limitCmd[0]);
    }
    return rowLimit;
  }

  private static boolean _isPredefinedAttribute(String attribute)
  {
    return attribute.equals(_CACHE_CMD) ||
           attribute.equals(_SECURITY_CMD) ||
           attribute.equals(_LIMIT_CMD) ||
           attribute.equals(_MRU_CMD) ||
           attribute.equals(_HYBRID_MRU_CMD) ||
           attribute.equals(_FILTER_CMD) ||
           attribute.equals(_CUSTOM_RESPONSE_CMD);
  }

  private String _getEntity(HttpServletRequest httpServletRequest)
  {
    String url = httpServletRequest.getRequestURL().toString();
    int s = url.lastIndexOf('/');
    int q = url.indexOf('?');
    return (s == -1)? "" : url.substring(s + 1, (q == -1)? url.length() : q);
  }

  private boolean _isDataUnchanged(HttpServletRequest httpServletRequest)
  {
    String ifNoneMatch = httpServletRequest.getHeader("If-None-Match");
    return ifNoneMatch != null && ifNoneMatch.equals(_etag.toString());
  }

  private void _sendResponse(
    HttpServletResponse httpServletResponse,
    List<SearchModelBase> responseItems,
    boolean isMRURequest,
    boolean needCustomResponse,
    boolean hasMore) throws IOException
  {
    DateFormat format = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM);

    StringBuilder jsonRoot = new StringBuilder();
    jsonRoot.append('{');
    JsonUtils.writeString(jsonRoot, "items", false, true);
    jsonRoot.append(":[");

    for (SearchModelBase item : responseItems)
    {
      jsonRoot.append('{');
      if (isMRURequest)
      {
        JsonUtils.writeString(jsonRoot, "id", false, true);
        jsonRoot.append(':');
        JsonUtils.writeInt(jsonRoot, (Integer) item.getProperty("id"));
      }
      else
      {
        for (Map.Entry<String, Object> entry : item.getEntrySet())
        {
          JsonUtils.writeString(jsonRoot, entry.getKey(), false, true);
          jsonRoot.append(':');

          Object value = entry.getValue();
          if (value == null)
          {
            value = "";
          }
          else if (value instanceof Date)
          {
            value = format.format(value);
          }
          JsonUtils.writeObject(jsonRoot, value, false, true);
          jsonRoot.append(',');
        }
        jsonRoot.deleteCharAt(jsonRoot.length() - 1);
      }
      jsonRoot.append("},");
    }
    
    if (responseItems.size() != 0)
    {
      jsonRoot.deleteCharAt(jsonRoot.length() - 1);
    }
    jsonRoot.append("],");

    JsonUtils.writeString(jsonRoot, "hasMore", false, true);
    jsonRoot.append(':');
    JsonUtils.writeBoolean(jsonRoot, hasMore);
    jsonRoot.append(',');

    JsonUtils.writeString(jsonRoot, "count", false, true);
    jsonRoot.append(':');
    JsonUtils.writeInt(jsonRoot, responseItems.size());

    jsonRoot.append('}');

    if (needCustomResponse)
    {
      StringBuilder newRoot = new StringBuilder(jsonRoot.length());
      newRoot.append('{');
      JsonUtils.writeString(newRoot, "response", false, true);
      newRoot.append(':');
      newRoot.append(jsonRoot);
      newRoot.append('}');
      jsonRoot = newRoot;
    }

    httpServletResponse.getOutputStream().print(jsonRoot.toString());
  }

  private void _setHeaders(HttpServletResponse httpServletResponse, Map<String, String[]> reqParams)
  {
    // Default cache strategy is expires header with a 15 min cache interval
    String cacheStrategy = "expiry";
    String[] cacheCmd = reqParams.get(_CACHE_CMD);
    if (cacheCmd != null && cacheCmd.length > 0)
    {
      cacheStrategy = cacheCmd[0];
    }

    SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    format.setTimeZone(TimeZone.getTimeZone("GMT"));
    httpServletResponse.setHeader("Last-Modified", format.format(_lastModified));

    if (cacheStrategy.equalsIgnoreCase("expiry"))
    {
      httpServletResponse.setHeader("cache-control", "max-age=900");
    }
    else if (cacheStrategy.equalsIgnoreCase("nocache"))
    {
      httpServletResponse.setHeader("cache-control", "no-store");
    }
    else if (cacheStrategy.equalsIgnoreCase("etag"))
    {
      httpServletResponse.setHeader("etag", _etag.toString());
      httpServletResponse.setHeader("cache-control", "no-cache");
    }
  }

  public class DemoSearchModelChangeListener
    implements SearchModelChangeListener
  {
    @Override
    public synchronized void modelChanged(SearchModelBase dirtyModel)
    {
      // change the etag, indicating a model change
      _etag.incrementAndGet();
      _lastModified = new Date();
    }
  }

  private DemoSearchModel       _searchModel;
  private AtomicInteger         _etag         = new AtomicInteger((int) (Math.random() * 1000));
  private volatile Date         _lastModified = new Date();

  private static final String _CACHE_CMD                     = "cache";
  private static final String _SECURITY_CMD                  = "auth";
  private static final String _MRU_CMD                       = "mru";
  private static final String _HYBRID_MRU_CMD                = "hybridmru";
  private static final String _LIMIT_CMD                     = "limit";
  private static final String _FILTER_CMD                    = "filter";
  private static final String _CUSTOM_RESPONSE_CMD           = "custom_response";

  // Mock JWT token
  // The same needs to be present in the request headers
  public static final String DEMO_MOCK_HEADER_TOKEN_KEY   = "authToken";
  public static final String DEMO_MOCK_HEADER_TOKEN_VALUE = "__DEMO__TOKEN__";
}
