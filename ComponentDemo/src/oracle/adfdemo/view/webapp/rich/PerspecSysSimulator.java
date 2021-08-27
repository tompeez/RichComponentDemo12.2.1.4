/*
** Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
*/
package oracle.adfdemo.view.webapp.rich;

import java.io.IOException;

import java.io.PrintWriter;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;


/**
 * A filter that sits before all other filters to simulate what PerspecSys does
 * by tokenize / detokenize request / response for protected components.
 */
public class PerspecSysSimulator implements Filter
{
  public void init(FilterConfig filterConfig)
    throws ServletException
  {
  }

  public void destroy()
  {
  }

  public void doFilter(ServletRequest servletRequest, 
                       ServletResponse servletResponse, FilterChain chain)
    throws IOException, ServletException
  {
    PrintWriter out = servletResponse.getWriter();
    //create our own response writter so we can detokenize the response later.
    CharArrayResponseWrapper responseWrapper = new CharArrayResponseWrapper((HttpServletResponse)servletResponse);

    String tokenizedNamesStr = servletRequest.getParameter("oracle.adf.view.rich.TOKENIZED");
    if (tokenizedNamesStr != null)
    {
      // if this request contains submitted value for protected attribute, we need to create our own request to tokenize
      // value of the proteced attribute.
      Map<String, String> clientIdToProtectionKeyMap = _parseTokenizedNameMap(tokenizedNamesStr);
      chain.doFilter(new FilteredRequest(servletRequest, clientIdToProtectionKeyMap, _sTokensForAttributesMap), responseWrapper);
    }
    else
    {
      chain.doFilter(servletRequest, responseWrapper);
    }

    // now detokenize the response
    String passage = responseWrapper.toString();
    String detokenizedMsg = _detokenizeResponse(passage);
    out.write(detokenizedMsg);
    out.close();
  }

  // detokenize the response. Let each TokensForAttribute check whether there are any tokens
  // tokenized by it.
  private String _detokenizeResponse(String responseContent)
  {
    for (TokensForAttribute tokenForAttr : _sTokensForAttributesMap.values())
    {
      responseContent = tokenForAttr.detokenize(responseContent);
    }

    return responseContent;
  }

  // parse the tokenizedName string into a map
  // example: tokenizeNameStr = {"r1:0:it1":"EMP_OBJ/Ename_FLD", "r1:0:it3":"EMP_OBJ/Job_FLD"}
  // returned map: r1:0:it1 -> EMP_OBJ/Ename_FLD
  //               r1:0:it3 -> EMP_OBJ/Job_FLD
  private Map<String, String> _parseTokenizedNameMap(String tokenizedNamesStr)
  {
    Map<String, String> clientIdToProtectionKeyMap = new HashMap<String, String>();

    // first, get ride of { and }
    String workingStr = tokenizedNamesStr.substring(1, tokenizedNamesStr.length() - 1);

    // then split the whole string into an array, each element is a client id to protection key JSON string
    String[] idKeyPairs = workingStr.split(",");
    for (String onePair : idKeyPairs)
    {
      // get clientId and Key value from each pair and put them on the map
      String[] idKeyArray = onePair.split(":");
      if (idKeyArray.length == 2)
      {
        String clientIdWithQ = idKeyArray[0];
        String protectionKeyWithQ = idKeyArray[1];
        clientIdToProtectionKeyMap.put(clientIdWithQ.substring(1, clientIdWithQ.length() - 1),
                                       protectionKeyWithQ.substring(1, protectionKeyWithQ.length() - 1));
      }
      else
      {
        assert(false);
      }
    }
    return clientIdToProtectionKeyMap;
  }

  // filter request to tokenize any submitted value for protected attribute. 
  static class FilteredRequest extends HttpServletRequestWrapper
  {
    public FilteredRequest(ServletRequest request, Map<String, String> clientIdToProtectionKeyMap,
                           Map<String, TokensForAttribute> tokensForAttributesMap)
    {
      super((HttpServletRequest)request);
      _clientIdToProtectionKeyMap = clientIdToProtectionKeyMap;
      _tokensForAttributesMap = tokensForAttributesMap;
    }

    public String tokenize(String clientId, String value)
    {
      String protectionKey = _clientIdToProtectionKeyMap.get(clientId);
      TokensForAttribute tokensForAttr  = _tokensForAttributesMap.get(protectionKey);
      return tokensForAttr.getToken(value);
    }

    @Override
    // override this method, if asking for value of proteced attribute, we need to tokenize the value
    public String getParameter(String paramName)
    {
      String value = super.getParameter(paramName);
      if (_clientIdToProtectionKeyMap.containsKey(paramName))
      {
        value = tokenize(paramName, value);
      }

      return value;
    }

    @Override
    // override this method, if asking for value of proteced attribute, we need to tokenize the value
    public String[] getParameterValues(String paramName)
    {
      String values[] = super.getParameterValues(paramName);
      if (_clientIdToProtectionKeyMap.containsKey(paramName))
      {
        for (int index = 0; index < values.length; index++)
        {
          values[index] = tokenize(paramName, values[index]);
        }
      }
      return values;
    }

    private final Map<String, String> _clientIdToProtectionKeyMap;
    private final Map<String, TokensForAttribute> _tokensForAttributesMap;
  }

  // The global map from the protection key to TokensForAttribute instance. 
  private static Map<String, TokensForAttribute> _sTokensForAttributesMap;
  
  // The list of all the protection keys defined by the protected attribute
  // used in this demo.
  private static List<String> _PROTECTED_ATTRIBUTES; 
  
  static
  {
    _PROTECTED_ATTRIBUTES = new ArrayList<String>(7);
    _PROTECTED_ATTRIBUTES.add("DEPT_OBJ/Name_FLD");
    _PROTECTED_ATTRIBUTES.add("EMP_OBJ/Fname_FLD");
    _PROTECTED_ATTRIBUTES.add("EMP_OBJ/Lname_FLD");
    _PROTECTED_ATTRIBUTES.add("EMP_OBJ/Email_FLD");
    _PROTECTED_ATTRIBUTES.add("EMP_OBJ/Phone_FLD");
    _PROTECTED_ATTRIBUTES.add("EMP_OBJ/Job_FLD");

    _sTokensForAttributesMap = new HashMap<String, TokensForAttribute>();
    for (int index = 0 ; index < _PROTECTED_ATTRIBUTES.size(); index++)
    {
      String attributeName = _PROTECTED_ATTRIBUTES.get(index);
      _sTokensForAttributesMap.put(attributeName, new TokensForAttribute(attributeName, String.valueOf(index)));
    }
  }
}
