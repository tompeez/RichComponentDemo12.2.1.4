/** Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.nav.rich;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * Bean used with the index list views throughout the demo.
 * See also the /templates/pageTemplateDefs/linkViews.jsff file.
 */
public class DemoIndexListBean implements Serializable
{
  /**
   * Construct a new index list bean with the provided serializable list of serializable items.
   * @param listOfIndexData the non-null list of index data
   */
  public DemoIndexListBean(List listOfIndexData)
  {
    if (listOfIndexData == null)
      throw new IllegalStateException("Illegal use of DemoIndexListBean; the list must be non-null.");
    _listOfIndexData = listOfIndexData;
  }

  /**
   * Gets the non-null list of index data.
   * @return the non-null list of index data
   */
  public List getList()
  {
    return _listOfIndexData;
  }

  /**
   * Gets the total number of items in the list (not just what is displayed for the current page).
   * @return the total number of items in the list
   */
  public int getListSize()
  {
    return getList().size();
  }

  /**
   * Gets the first list index (zero-based) to be displayed.
   * Must be within the range of actual indicies for the list.
   * @return the first list index (zero-based) to be displayed
   */
  public int getFirstIndex()
  {
    return _pageFirstIndex;
  }

  /**
   * Gets the last list index (zero-based) to be displayed.
   * Must be within the range of actual indicies for the list.
   * @return the last list index (zero-based) to be displayed
   */
  public int getLastIndex()
  {
    int first = getFirstIndex();
    int rowsToShow = getPageSize();
    int last = first + rowsToShow;
    return Math.min(getListSize(), last) - 1;
  }

  /**
   * Gets the current page number (one-based) to be displayed.
   * Must be within the range of actual page numbers for the list.
   * @return the current page number (one-based) to be displayed
   */
  public int getCurrentPage()
  {
    return 1 + (int)Math.floor((double)getFirstIndex() / (double)getPageSize());
  }

  /**
   * Sets the current page number (one-based) to be displayed.
   * Must be within the range of actual page numbers for the list.
   * @param newCurrentPage the current page number (one-based) to be displayed
   */
  public void setCurrentPage(int newCurrentPage)
  {
    _pageFirstIndex = (newCurrentPage - 1) * getPageSize();
  }

  /**
   * The list of non-negative page integers to display to the user.
   * Zero is used to indicate a gap in the list and should be represented as "..." instead of a link.
   * @return a list of non-negative page integers to display to the user (zero indicates a gap)
   */
  public List<Integer> getPageNumbers()
  {
    // Always show:
    // - first
    // - possible "..." gap
    // - previous
    // - current
    // - next
    // - possible "..." gap
    // - last

    // Fill in about equally around the gaps until the maxPageCount is reached.

    int pageSize = getPageSize();
    int pageCount = (int)Math.ceil((double)getListSize() / (double)pageSize);

    ArrayList<Integer> pageNumbers = new ArrayList<Integer>();
    if (pageCount <= 5) // no gaps needed
    {
      for (int i=1; i<=pageCount; i++)
        pageNumbers.add(i);
    }
    else // gaps needed
    {
      int maxPageCount = Math.min(7, pageCount);
      int firstPage = 1;
      int lastPage = pageCount;
      int currentPage = getCurrentPage();
      int previousPage = currentPage-1;
      int nextPage = currentPage+1;
      int gapAtFirst = Math.max(0, currentPage - firstPage - 2);
      int gapAtLast = Math.max(0, lastPage - 2 - currentPage);
      if (gapAtFirst < 1 && gapAtLast < 1)
        throw new IllegalStateException("Illegal state, gaps were expected but not realized: " + gapAtFirst + " and " + gapAtLast);

      // count the used pages:
      int pagesUsed = 1; // start with 1 for the current page
      if (firstPage != currentPage) // first and current are not shared
        pagesUsed++;
      if (previousPage > firstPage) // previous exists and is not shared with the first page
        pagesUsed++;
      if (nextPage < lastPage) // next exists and is not shared with the last page
        pagesUsed++;
      if (lastPage != currentPage) // last and current are not shared
        pagesUsed++;

      int pagesToFill = maxPageCount - pagesUsed;
      int fillAtFirst = 0;
      int fillAtLast = 0;
      if (gapAtFirst > 0 && gapAtLast > 0)
      {
        // fill batch evenly as long as there is space, then distribute the remaining where possible
        fillAtFirst = (int)Math.ceil((double)pagesToFill / 2.0d);
        fillAtLast = (int)Math.floor((double)pagesToFill / 2.0d);
        if (fillAtFirst > gapAtFirst)
        {
          gapAtLast += fillAtFirst - gapAtFirst; // add excess on the first gap to the last gap
        }
        else if (fillAtLast > gapAtLast)
        {
          gapAtFirst += fillAtLast - gapAtLast; // add excess on the last gap to the first gap
        }
      }
      else if (gapAtFirst > 0)
      {
        // fill entire batch on the "first" side
        fillAtFirst = pagesToFill;
      }
      else if (gapAtLast > 0)
      {
        // fill entire batch on the "last" side
        fillAtLast = pagesToFill;
      }

      if (firstPage != currentPage) // first and current are not shared
        pageNumbers.add(firstPage);
      if (previousPage - fillAtFirst > 1 + firstPage) // really had a gap at first
        pageNumbers.add(0);
      for (int i=fillAtFirst; i>0; i--) // add the fillers for the gap at first
        pageNumbers.add(previousPage - i);
      if (previousPage > firstPage) // previous exists and is not shared with the first page
        pageNumbers.add(previousPage);
      pageNumbers.add(currentPage); // add the current page
      if (nextPage < lastPage) // next exists and is not shared with the last page
        pageNumbers.add(nextPage);
      for (int i=1; i<=fillAtLast; i++) // add the fillers for the gap at last
        pageNumbers.add(nextPage + i);
      if (nextPage + fillAtLast < lastPage - 1) // really had a leftover gap at last
        pageNumbers.add(0);
      if (lastPage != currentPage) // last and current are not shared
        pageNumbers.add(lastPage);
    }
    return pageNumbers;
  }

  /**
   * Gets the current chosen page size (number of items to show).
   * @return the current chosen page size (number of items to show)
   */
  public int getPageSize()
  {
    if (_pageSize == 0)
    {
      // See what the actual biggest page size is:
      List<SelectItem> pageSizes = getPageSizes();
      SelectItem lastPageSizeItem = pageSizes.get(pageSizes.size()-1);
      int biggestPageSize = Integer.valueOf(lastPageSizeItem.getValue().toString()).intValue();
      if (biggestPageSize >= 20)
        _pageSize = 20; // default to 20 if we can
      else
        _pageSize = biggestPageSize; // 20 was too big, use the biggest possible page size (smaller than 20)
    }
    return _pageSize;
  }

  /**
   * Sets the current chosen page size (number of items to show) and resets the current page to be the first one.
   * @param pageSize the current chosen page size (number of items to show)
   */
  public void setPageSize(int pageSize)
  {
    _pageSize = pageSize;
    setCurrentPage(1); // reset the currently selected page or else we could be pointing to a non-existant page
  }

  /**
   * Gets the list of page size options you want to expose to users in a selectOneChoice.
   * @return a list of SelectItem objects that will be exposed to users in a selectOneChoice
   */
  public List<SelectItem> getPageSizes()
  {
    if (_pageSizes == null)
    {
      ArrayList<SelectItem> pageSizes = new ArrayList<SelectItem>();
      int maxPageSize = getListSize();
      int previousSizeLimit = _addPageSizeOption(4, 0, maxPageSize, pageSizes);
      previousSizeLimit = _addPageSizeOption(10, previousSizeLimit, maxPageSize, pageSizes);
      previousSizeLimit = _addPageSizeOption(20, previousSizeLimit, maxPageSize, pageSizes);
      previousSizeLimit = _addPageSizeOption(50, previousSizeLimit, maxPageSize, pageSizes);
      previousSizeLimit = _addPageSizeOption(100, previousSizeLimit, maxPageSize, pageSizes);
      previousSizeLimit = _addPageSizeOption(150, previousSizeLimit, maxPageSize, pageSizes);
      previousSizeLimit = _addPageSizeOption(200, previousSizeLimit, maxPageSize, pageSizes);
      _pageSizes = pageSizes;
    }
    return _pageSizes;
  }

  /**
   * Adds another page size option if the previously-added size is smaller than the maximum-allowed page size.
   * @param desiredSize the page size that this option will represent
   * @param previousSize the page size that was previously-added
   * @param maxPageSize either the total number of items in the list or some smaller maximum size you want to enforce for performanc reasons
   * @param pageSizes the list of page sizes that this desired size will be added to if allowed
   * @return the specified desiredSize
   */
  private int _addPageSizeOption(int desiredSize, int previousSize, int maxPageSize, List<SelectItem> pageSizes)
  {
    if (maxPageSize > previousSize)
    {
      pageSizes.add(new SelectItem(desiredSize));
    }
    return desiredSize;
  }

  @SuppressWarnings("compatibility:-5678350118022165961")
  private static final long serialVersionUID = 1L;

  private List             _listOfIndexData;
  private int              _pageFirstIndex = 0;
  private int              _pageSize = 0;
  private List<SelectItem> _pageSizes;
}
