/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.webapp.rich;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Collections;

import java.util.Comparator;
import java.util.List;

import java.util.Map;

import java.util.Set;

import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.adfdemo.view.nav.rich.DemoIndexBean;

/**
 * To use:
 * 1.) Go to a page like this:
 *     http://127.0.0.1:7101/adf-richclient-demo-context-root/install.demofind?mode=install
 * 2.) Install the search provider via the search box (if not already installed)
 * 3.) Perform a search and notice the suggested items as you type
 */
public class DemoFindServlet extends HttpServlet
{
  @Override
  public void doGet(
    HttpServletRequest  request,
    HttpServletResponse response)
    throws IOException, ServletException
  {
    String rawQuery = request.getParameter(_PARAM_QUERY);
    String mode = request.getParameter(_PARAM_MODE);
    List<String> found = Collections.emptyList();
    if (rawQuery == null)
    {
      if (mode != null && mode.equals(_MODE_SEARCH_PROVIDER))
      {
        _doSearchProviderMode(request, response);
        return;
      }
      else // mode.equals("install")
      {
        _doInstallMode(request, response);
        return;
      }
    }
    else if (mode != null && mode.equals(_MODE_QUERY))
    {
      _doQueryMode(request, response, rawQuery);
      return;
    }
    else // do JSON suggestion query mode
    {
      found = getSuggestions(rawQuery, _MAX_RESULTS);
    }

    // JSON suggestions
    response.setContentType("application/x-suggestions+json");

    ServletOutputStream out = response.getOutputStream();
    out.print("[\"");
    out.print(rawQuery);
    out.print("\",[");
    boolean first = true;
    for (String item : found)
    {
      if (first)
      {
        first = false;
      }
      else
      {
        out.print(",");
      }
      out.print("\"");
      out.print(item);
      out.print("\"");
    }
    out.println("]]");
  }

  private static void _doSearchProviderMode(
    HttpServletRequest  request,
    HttpServletResponse response)
    throws IOException, ServletException
  {
    String serverName = request.getServerName();
    String requestUrl = request.getRequestURL().toString();
    String suggestPage =
      requestUrl.substring(0, requestUrl.lastIndexOf(_SERVLET_NAME) + _SERVLET_NAME.length());

    response.setContentType("application/opensearchdescription+xml");
    ServletOutputStream out = response.getOutputStream();
    out.println("<?xml version=\"1.0\"?>");
    out.println("<OpenSearchDescription");
    out.println("  xmlns=\"http://a9.com/-/spec/opensearch/1.1/\"");
    out.println("  xmlns:moz=\"http://www.mozilla.org/2006/browser/search/\">");
    out.print("  <ShortName>");
    out.print(_SHORT_NAME);
    out.print(" ");
    out.print(serverName);
    out.println("</ShortName>");
    out.println("  <Description>Open an ADF Faces Rich Client tag demo.</Description>");
    out.println("  <InputEncoding>UTF-8</InputEncoding>");
    out.println("  <Image width=\"16\" height=\"16\">data:image/png,%89PNG%0D%0A%1A%0A%00%00%00%0DIHDR%00%00%00%10%00%00%00%10%08%06%00%00%00%1F%F3%FFa%00%00%00%04gAMA%00%00%AF%C87%05%8A%E9%00%00%00%19tEXtSoftware%00Adobe%20ImageReadyq%C9e%3C%00%00%01VIDATx%DA%9CS%CDj%C2%40%10%9E%24Kj%40%0F%BE%81%D7R%0A%3E%40%7D%84%14%7C%01%F1I%3C%F8%22%82%0F%E1I%3C%8A%87b%C4B%E9%A9X%E8%A9w%D1%D4%DD%9D%EE%ECO%DC4%8D%B4%0Elf%C8%7C%3B%FB%CD7%BB%C1%0Bc%08%7F0%F4%BC%8B%EF9%0F%18%05%B7I%02%FF%B5%ED%E1%40.e%95%0CS%BF%BA%DD%EA%8E%CD%06%80%F33%23%B4%3C%A8%05l%B5%10%DBm%C4%C9%04%2F%1A%E5%15N4%9B%98E%11%96%19%2C%16%E6%E4%DD%0E%60%3A%AD2%18%0C%00%86C%8D%E1%BD%5E%A1%83a%D0%EF%9B%13%B2%CC0!F%DE%92%8E!%E5%C9%14%FE%C92%08u%15%D7%F3x%5C%EA%93L%AA%5Ei%7D%9DN%26o%F1%8E%01%BB46%12J*%2F%AC%FFm%AC!%3A%85%C9F%23%3D%05w*q%E1%D6'ql%F2%16%EF%0A%9A%5B4%9F%9B%22%D4%CAz%0D%A1%151%FA)b%A7%A3q%1F%B3%D9Y%C4%E7%2B%C6%F8%16%C7%B8%0C5%F94%D8%AA%02w%8DF%D1%EB%0DQ%AD%B9H%EF%FB%3D%08%8B%FB%14%02%1E%A4%7C%D4-p%AB6%25%F3%3C%07%B1Z%15%40a5p%B1%FB_L%01%5D%C2K%D6mr%B1%2C%15P%9F%D7%E3%B1xe%D2%7Bq%E8%8D%13k%5Ed%60%7D%0AW%DA%B7%00%03%00%DA%7D%3E%9B%02*5%E2%00%00%00%00IEND%AEB%60%82</Image>");
    out.println("  <Url type=\"text/html\" method=\"GET\"");
    out.print("    template=\"");
    out.print(suggestPage);
    out.println("\">");
    out.print("    <Param name=\"");
    out.print(_PARAM_MODE);
    out.print("\" value=\"");
    out.print(_MODE_QUERY);
    out.println("\"/>");
    out.print("    <Param name=\"");
    out.print(_PARAM_QUERY);
    out.println("\" value=\"{searchTerms}\"/>");
    out.println("  </Url>");
    out.println("  <Url type=\"application/x-suggestions+json\" method=\"GET\"");
    out.print("    template=\"");
    out.print(suggestPage);
    out.print("?");
    out.print(_PARAM_QUERY);
    out.println("={searchTerms}\"/>");
    out.print("  <moz:SearchForm>");
    out.print(suggestPage);
    out.print("?");
    out.print(_PARAM_MODE);
    out.print("=");
    out.print(_MODE_QUERY);
    out.print("&amp;");
    out.print(_PARAM_QUERY);
    out.print("=index");
    out.println("</moz:SearchForm>");
    out.println("</OpenSearchDescription>");
  }

  private static void _doInstallMode(
    HttpServletRequest  request,
    HttpServletResponse response)
    throws IOException, ServletException
  {
    String serverName = request.getServerName();
    response.setContentType("text/html");
    ServletOutputStream out = response.getOutputStream();
    out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
    out.println("<html>");
    out.println("  <head>");
    out.println("    <title>ADF Faces Tag Name Search Provider Installation</title>");
    out.println("    <script>");
    out.println("    function installSearchEngine()");
    out.println("    {");
    out.println("      if (window.external && (\"AddSearchProvider\" in window.external))");
    out.println("      {");
    out.print("        window.external.AddSearchProvider(\"install.");
    out.print(_SERVLET_NAME);
    out.print("?");
    out.print(_PARAM_MODE);
    out.print("=");
    out.print(_MODE_SEARCH_PROVIDER);
    out.println("\");");
    out.println("      }");
    out.println("      else");
    out.println("      {");
    out.println("        alert(\"Sorry this web browser does not appear to support OpenSearch providers.\");");
    out.println("      }");
    out.println("    }");
    out.println("    </script>");
    out.print("    <link rel=\"search\" type=\"application/opensearchdescription+xml\" title=\"");
    out.print(_SHORT_NAME);
    out.print(" ");
    out.print(serverName);
    out.print("\" href=\"install.");
    out.print(_SERVLET_NAME);
    out.print("?");
    out.print(_PARAM_MODE);
    out.print("=");
    out.print(_MODE_SEARCH_PROVIDER);
    out.println("\">");
    out.println("  </head>");
    out.println("  <body>");
    out.println("    <h1>ADF Faces Tag Name Search Provider Installation</h1>");
    out.println("    <p>Check your search box for a new search provider (unless already installed).</p>");
    /* TODO consider adding this:
    out.print("    <a href=\"javascript:installSearchEngine()\">Install Search Provider - ");
    out.print(_SHORT_NAME);
    out.print(" ");
    out.print(serverName);
    out.println("</a>");
    */
    out.println("  </body>");
    out.println("</html>");
  }

  private static void _doQueryMode(
    HttpServletRequest  request,
    HttpServletResponse response,
    String              rawQueryValue)
    throws IOException, ServletException
  {
    String contextPath = request.getContextPath();
    String requestUrl = request.getRequestURL().toString();
    String demoPage = requestUrl.substring(0, requestUrl.lastIndexOf(contextPath));
    String matchableQuery = getMatchableQuery(rawQueryValue);

    response.setContentType("text/html");
    ServletOutputStream out = response.getOutputStream();
    out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
    out.println("<html>");
    out.println("  <head>");
    out.print("    <title>ADF Faces Tag Name Query - \"");
    out.print(matchableQuery);
    out.println("\"</title>");
    out.print("    <meta http-equiv=\"Refresh\" content=\"0;url=");
    out.print(demoPage);
    out.print(contextPath);
    out.print("/faces/components/findResults.jspx?q=");
    out.print(matchableQuery);
    out.println("\"/>");
    out.println("  </head>");
    out.println("  <body>");
    out.println("    Loading...");
    out.println("  </body>");
    out.println("</html>");
  }

  public static String getMatchableQuery(String rawQueryValue)
  {
    if (rawQueryValue == null)
    {
      return "";
    }

    // Remove characters that would allow code injection:
    String matchableQuery = rawQueryValue;
    matchableQuery = matchableQuery.replace('<',' ');
    matchableQuery = matchableQuery.replace('&',' ');
    matchableQuery = matchableQuery.trim();
    return matchableQuery;
  }

  public static List<String> getSuggestions(String matchableQuery, int maxResults)
  {
    ArrayList<String> found = new ArrayList<String>(maxResults);
    String find = matchableQuery.trim().toLowerCase();
    Map<String,String> searchIndex = _getSearchIndex();
    Set<String> searchKeys = searchIndex.keySet();
    int resultCount = 0;
    for (String key : searchKeys)
    {
      if (key.toLowerCase().indexOf(find) != -1)
      {
        found.add(key);
        resultCount++;
      }
      if (resultCount >= maxResults)
      {
        break;
      }
    }

    // Sort the suggestions:
    Collections.sort(found, _SUGGESTIONS_COMPARATOR);

    return found;
  }

  public static Map<String,String> getMatches(String matchableQuery)
  {
    if (matchableQuery.length() == 0)
    {
      return Collections.emptyMap();
    }
    String find = matchableQuery.toLowerCase();
    TreeMap<String,String> found = new TreeMap<String,String>(_SUGGESTIONS_COMPARATOR);
    Map<String,String> searchIndex = _getSearchIndex();
    Set<String> searchKeys = searchIndex.keySet();

    // Look for exact matches:
    for (String key : searchKeys)
    {
      if (key.toLowerCase().equals(find))
      {
        found.put(key, searchIndex.get(key));
      }
    }

    // Look for parital matches:
    if (found.size() == 0)
    {
      for (String key : searchKeys)
      {
        if (key.toLowerCase().indexOf(find) != -1)
        {
          found.put(key, searchIndex.get(key));
        }
      }
    }

    return found;
  }

  private static Map<String,String> _getSearchIndex()
  {
    if (_SEARCH_INDEX.size() == 0)
    {
      // Try building the search index (it might fail and if so will be of length zero):
      _SEARCH_INDEX = DemoIndexBean.getFindServletPaths();
    }
    return _SEARCH_INDEX;
  }

  static class SuggestionsComparator implements Comparator<String>
  {
    public int compare(String a, String b)
    {
      String leadingA = a.substring(0, 3);
      String leadingB = b.substring(0, 3);
      if (leadingA.equals(leadingB))
      {
        // They are of the same category, natural compare them:
        return a.compareTo(b);
      }

      // Otherwise, let's give a special ordering that matches the category ordering:
      int categoryCount = _CATEGORY_ORDERING.length;
      int weightA = categoryCount;
      int weightB = categoryCount;
      for (int i=0; i<categoryCount; i++)
      {
        if (_CATEGORY_ORDERING[i].equals(leadingA))
        {
          weightA = i;
          break;
        }
      }
      for (int i=0; i<categoryCount; i++)
      {
        if (_CATEGORY_ORDERING[i].equals(leadingB))
        {
          weightB = i;
          break;
        }
      }
      if (weightA < weightB)
      {
        return -1;
      }
      return 1;
    }

    private static String[] _CATEGORY_ORDERING = {
      "Mai", // for the "Main - " prefix
      "Tag", // for the "Tag Guide - " prefix
      "Fea", // for the "Feature Demos - " prefix
      "Sam", // for the "Sample Page Templates - " prefix
      "Sty", // for the "Styles - " prefix
      "Vis", // for the "Visual Designs - " prefix
      "Com"  // for the "Commonly Confused Components - " prefix
    };
  }

  private static Comparator<String> _SUGGESTIONS_COMPARATOR = new SuggestionsComparator();
  private static Map<String,String> _SEARCH_INDEX   = Collections.emptyMap();
  private static final int _MAX_RESULTS = 10;
  private static final String _SHORT_NAME = "ADF Faces Tag Name";
  private static final String _SERVLET_NAME = "demofind";
  private static final String _PARAM_MODE = "mode";
  private static final String _PARAM_QUERY = "q";
  private static final String _MODE_SEARCH_PROVIDER = "searchprovider";
  private static final String _MODE_QUERY = "query";

  @SuppressWarnings("compatibility:7229634980410997860")
  private static final long serialVersionUID = 1L;
}
