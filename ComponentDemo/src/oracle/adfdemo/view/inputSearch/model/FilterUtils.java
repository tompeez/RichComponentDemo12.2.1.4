package oracle.adfdemo.view.inputSearch.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oracle.adf.view.rich.component.rich.input.RichInputSearch;


/**
 * Provides the server side filtering capability for af:inputSearch component.
 */
public class FilterUtils
{
  public static List<? extends SearchModelBase> filter(
    List<? extends SearchModelBase>  rows,
    FilterDef                        filterDef)
  {
    if (filterDef != null)
    {
      if (filterDef.getFilterType() == RichInputSearch.Criteria.STARTS_WITH)
      {
        return _startsWithFilter(rows, filterDef);
      }
      else
      {
        return _containsFilter(rows, filterDef);
      }
    }
    return rows;
  }

  /**
   * Builds a space concatenated string of filter attribute values
   */
  private static String _buildFilterAttributeValues(
    List<String> filterAttributes, SearchModelBase suggestionItem)
  {
    String filterStr = "";
    for (int j=0, lenj=filterAttributes.size(); j<lenj; j++)
    {
      String filterKey = filterAttributes.get(j);
      Object value = suggestionItem.getProperty(filterKey);
      filterStr += value + " ";
    }
    return filterStr;
  }

  /**
   * This method matches the contains filtering done by the component.
   * In addition to contains filtering, it also ranks the startsWith matches higher.
   */
  private static List<? extends SearchModelBase> _containsFilter(
    List<? extends SearchModelBase> rows, FilterDef filterDef)
  {
    Pattern re = _getContainsFilterRegex(filterDef.getSearchTerms());
    List<String> filterAttributes = filterDef.getFilterAttributes();

    // 0th index will hold suggestion items with zero startsWith matches
    // 1st index will hold suggestion items with one startsWith match, and so on...
    int size = filterDef.getSearchTerms().size() + 1;
    List<List<SearchModelBase>> scoredCollection =
      new ArrayList<List<SearchModelBase>>(size);
    for (int i = 0; i < size; i++)
    {
      scoredCollection.add(new ArrayList<SearchModelBase>());
    }

    for (SearchModelBase row : rows)
    {
      String filterStr = _buildFilterAttributeValues(filterAttributes, row);
      Matcher m = re.matcher(filterStr);
      if (m.find(0))
      {
        int score = 0;
        for (int k = 1, len = m.groupCount(); k < len; k += 2)
        {
          if ("".equals(m.group(k))) // startsWith match
          {
            score++;
          }
        }
        scoredCollection.get(score).add(row);
      }
    }

    // build a single array with startsWith matches scored higher.
    List<SearchModelBase> scoredOrderedSuggestions = new ArrayList<SearchModelBase>();
    for (int i = scoredCollection.size() - 1; i >= 0; i--)
      scoredOrderedSuggestions.addAll(scoredCollection.get(i));
    return scoredOrderedSuggestions;
  }

  /**
   * Builds a regex from the passed in searchTerms to match a string which has words that contains
   * the words present in searchText. The regex also provides a capture group to retrieve the letters
   * preceding the matched portion of the word.
   * This capture group is to help identify if the match is a startsWith match
   */
  private static Pattern _getContainsFilterRegex(List<String> searchTerms)
  {
    // If searchTextList contains "Ad Ade A", then the regex would be
    // Contains Matches: with a preference for startsWith match
    // ^(?=.*?\b(\w*?)(Ade(?!.*?\bAde)\w*?\b.*))
    // (?=.*?\b(\w*?)(?!\2$)(Ad(?:(?=.*?\b\2$)|(?!.*?\bAd))\w*?\b.*))
    // (?=.*?\b(\w*?)(?!\2$|\4$)(A(?:(?=.*?\b(?:\2$|\4$))|(?!.*?\bA))\w*?\b.*))

    // Regex explanation:
    // Line 1: Iterates the string to be matched checking if there is a word containing 'Ade'.
    // If there is a match, iterate further to verify there is no word that is starting with 'Ade'.
    // This is to ensure startsWith matches get a preference in matching.
    // On a match, capture the letters preceding the matched portion of the word. Also the
    // entire string from the matched portion is captured.
    // The 1st capture text is useful in determining if the match was a startsWith match.
    // The 2nd captured text is used in Line 2 and Line 3 to not match the same word again.

    // Line 2: Again iterate as in Line 1, looking for a word containg 'Ad', but by avoiding
    // the string that was matched in Line 1. On finding such a word, iterate further to verify
    // there is NO word starting with 'Ad', unless it is already matched by Line 1.

    // Line 3: Iterate the string again, looking for 'A' this time, and by avoiding strings matched
    // in Line 1 and Line 2. On finding such a word, iterate further to verify
    // there is NO word starting with 'A', unless it is already matched by Line 1 or Line 2.

    // Note: The longer word in the searchText is matched first. This is ensure correct matching
    // when the search terms are subsets.
    // Tip: Use regex101.com to make changes and verify the regex is doing whatever is intended.

    searchTerms = _sortByLength(searchTerms);
    String regex = "^";
    for (int z = 0, lenz = searchTerms.size(); z < lenz; z++)
    {
      regex += "(?=.*?\\b(\\w*?)(";
      if (z != 0)
      {
        regex += "?!";
        for (int y=1; y<=z; y++)
          regex += "\\" + 2*y + "$|";
        regex = regex.substring(0, regex.length()-1) + ")(";
        regex += searchTerms.get(z) + "(?:(?=.*?\\b(?:";
        for (int y=1; y<=z; y++)
          regex += "\\" + 2*y + "$|";
        regex = regex.substring(0, regex.length()-1) + "))|(?!.*?\\b" + searchTerms.get(z) + "))";
      }
      else
      {
        regex += searchTerms.get(z) + "(?!.*?\\b" + searchTerms.get(z) + ")";
      }
      regex += "\\w*?\\b.*))";
    }

    return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
  }

  /**
   * Builds a regex from the passed in searchText to match a string which has words that start-with
   * words present in searchText
   */
  private static Pattern _getStartsWithFilterRegex(List<String> searchTerms)
  {
    // If searchTextList contains "Ad Ade A", then the regex would be
    // ^(?=.*?\b(Ade\w*?\b.*))
    // (?=.*?\b(?!\1$)(Ad\w*?\b.*))
    // (?=.*?\b(?!\1$|\2$)(A\w*?\b.*))

    // Regex explanation:
    // Line 1: Iterates the string to be matched checking if there is a word starting with 'Ade'.
    // If there is match, entire string from the matched portion is captured.
    // The captured text is used in Line 2 and Line 3 to not match the same word again

    // Line 2: Again iterate as in Line 1, looking for a word starting with 'Ad', but by avoiding
    // the string that was matched in Line 1.

    // Line 3: Iterate the string again, looking for 'A' this time, and by avoiding strings matched
    // in Line 1 and Line 2.

    // Note: The longer word in the searchText is matched first. This is ensure correct matching
    // when the search terms are subsets.
    // Tip: Use regex101.com to make changes and verify the regex is doing whatever is intended.

    searchTerms = _sortByLength(searchTerms);
    String regex = "^";
    for (int z = 0, lenz = searchTerms.size(); z < lenz; z++)
    {
      regex += "(?=.*?\\b(";
      if (z != 0)
      {
        regex += "?!";
        for (int y=1; y<=z; y++)
          regex += "\\" + y + "$|";
        regex = regex.substring(0, regex.length()-1) + ")(";
      }
      regex += searchTerms.get(z) + "\\w*?\\b.*))";
    }

    return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
  }

  private static List<String> _sortByLength(List<String> searchTerms)
  {
    Collections.sort(searchTerms, new Comparator<String>() 
    {
      public int compare(String a, String b)
      {
        return b.length() - a.length();
      }
    });
    return searchTerms;
  }

  /**
   * This method matches the startsWith filtering done by the component.
   */
  private static List<? extends SearchModelBase> _startsWithFilter(
    List<? extends SearchModelBase> rows, FilterDef filterDef)
  {
    Pattern re = _getStartsWithFilterRegex(filterDef.getSearchTerms());
    List<String> filterAttributes = filterDef.getFilterAttributes();

    List<SearchModelBase> filterList = new ArrayList<SearchModelBase>();
    for (SearchModelBase row : rows)
    {
      String filterStr = _buildFilterAttributeValues(filterAttributes, row);
      if (re.matcher(filterStr).find(0))
      {
        filterList.add(row);
      }
    }
    return filterList;
  }
}
