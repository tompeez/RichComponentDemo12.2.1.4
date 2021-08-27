package oracle.adfdemo.view.inputSearch.model;

import java.util.List;

import oracle.adf.view.rich.component.rich.input.RichInputSearch;


/**
 * "filter" url parameter values holder of EmpDeptRestService
 */
public class FilterDef
{
  public FilterDef(
    String        filterType,
    List<String>  filterAttributes,
    List<String>  searchTerms)
  {
    _filterType = RichInputSearch.Criteria.valueOfDisplayName(filterType);
    _filterAttributes = filterAttributes;
    _searchTerms = searchTerms;
  }

  public RichInputSearch.Criteria getFilterType()
  {
    return _filterType;
  }

  public List<String> getFilterAttributes()
  {
    return _filterAttributes;
  }

  public List<String> getSearchTerms()
  {
    return _searchTerms;
  }

  private RichInputSearch.Criteria _filterType;
  private List<String> _filterAttributes;
  private List<String> _searchTerms;
}
