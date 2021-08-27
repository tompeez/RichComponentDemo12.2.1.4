/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.webapp.rich;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

/**
 * Bean used by the DemoFindServlet (used by the browser's native OpenSearch search provider search
 * box) and also used in the search inputText found on the demo index pages.
 */
public class DemoFindBean implements Serializable
{
  public boolean isGalleryEnabled()
  {
    Object galleryParameter =
      FacesContext.getCurrentInstance().getExternalContext()
                  .getRequestParameterMap().get("gallery");

    if (galleryParameter != null)
    {
      _galleryEnabled = !("false".equals(galleryParameter.toString()));
    }

    return _galleryEnabled;
  }

  public String getText()
  {
    return isGalleryEnabled() ? "Tabbed presentation" : "Gallery presentation";
  }

  public String getShortDesc()
  {
    return isGalleryEnabled() ?
      "Click to present the demos in a tabbed layout" :
      "Click to present the demos with a side bar listing";
  }

  public String getIcon()
  {
    return isGalleryEnabled() ? "/images/tabbed.png" : "/images/gallery.png";
  }

  public void toggle(ActionEvent ae)
  {
    _galleryEnabled = !isGalleryEnabled();
  }

  /**
   * Used in the the demoIndexTemplate.jspx to provide search text for the query.
   * Also used in the findResults.jspx page to display the query used to match with.
   * This is slightly different (cleaned-up version) from the value typed in by the user.
   * @return the query used to find matches
   */
  public String getSearchText()
  {
    Object searchParameter =
      FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(
        "q");
    if (searchParameter != null)
    {
      String searchText = searchParameter.toString();
      if (!searchText.equals(_searchText))
      {
        _searchText = searchText;
        execute();
      }
    }
    return DemoFindServlet.getMatchableQuery(_searchText);
  }

  /**
   * Used in the the demoIndexTemplate.jspx to provide search text for the query.
   * @param searchText the raw value the user submitted
   */
  public void setSearchText(String searchText)
  {
    _searchText = searchText;
  }

  /**
   * Used in the the demoIndexTemplate.jspx to initiate a query.
   * @return the outcome to use
   */
  public String execute()
  {
    // Using the same list of data that the OpenSearch search provider is using, figure out what
    // matches we have:
    Map<String,String> rawMatches = DemoFindServlet.getMatches(getSearchText()); // link data
    ArrayList<FoundMatch> foundMatches = new ArrayList<FoundMatch>();
    for (String key : rawMatches.keySet())
    {
      foundMatches.add(new FoundMatch(key, rawMatches.get(key)));
    }
    _foundMatches = foundMatches;
    return "findResults"; // the findResults.jspx page
  }

  /**
   * Used in the findResults.jspx page to display the list of matches found.
   * @return the list of matches found.
   */
  public List<FoundMatch> getFoundMatches()
  {
    if (_foundMatches == null)
    {
      return Collections.emptyList();
    }
    return _foundMatches;
  }

  /**
   * Used in the findResults.jspx page to display how many matches were found.
   * @return the number of matches found.
   */
  public int getFoundMatchCount()
  {
    if (_foundMatches == null)
    {
      return 0;
    }
    return _foundMatches.size();
  }

  public List<SelectItem> suggestedComponents(String searchString)
  {
    // show a maximum of 10 suggested components.
    List<String> matches = DemoFindServlet.getSuggestions(searchString, 10);
    List<SelectItem> items = new ArrayList<SelectItem>();

    for (String match : matches)
    {
      SelectItem item = new SelectItem(match);
      items.add(item);
    }
    return items;
  }

  public static class FoundMatch implements Serializable
  {
    public FoundMatch(String text, String rawMatch)
    {
      _text = text;
      if (rawMatch.indexOf("/") == -1)
      {
        _outcome = rawMatch;
      }
      else
      {
        _destination = rawMatch;
      }
    }

    public String getText()
    {
      return _text;
    }

    public String getDestination()
    {
      return _destination;
    }

    public String getOutcome()
    {
      return _outcome;
    }

    private String _text;
    private String _destination;
    private String _outcome;

    @SuppressWarnings("compatibility:1618511774627430107")
    private static final long serialVersionUID = 1L;
  }

  private String _searchText;
  private List<FoundMatch> _foundMatches;
  private boolean _galleryEnabled = true; // For now, the default is false

  @SuppressWarnings("compatibility:-1033261095976340159")
  private static final long serialVersionUID = 1L;
}
