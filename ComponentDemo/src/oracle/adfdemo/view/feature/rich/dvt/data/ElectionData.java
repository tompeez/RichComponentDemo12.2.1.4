package oracle.adfdemo.view.feature.rich.dvt.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Class exposing United States presidential election data by state over the last several elections.  This class is
 * designed to provide everything that a demo builder may need to create a demo, including recommended colors and
 * formatted strings. However, it isn't tied to any specific demo implementation.
 */
public class ElectionData {
  public static final String PARTY_DEMOCRAT = "Democrat";  
  public static final String PARTY_REPUBLICAN = "Republican";  
  public static final String PARTY_OTHER = "Other";

  private static final String COLOR_DEMOCRAT = "#267db3";  
  private static final String COLOR_DEMOCRAT_SMALL = "#bfdef1";   
  private static final String COLOR_DEMOCRAT_MEDIUM = "#6eb5e0";   
  private static final String COLOR_DEMOCRAT_LARGE = "#2e93d3";   
  private static final String COLOR_REPUBLICAN = "#ed6647";
  private static final String COLOR_REPUBLICAN_SMALL = "#f9cec4"; 
  private static final String COLOR_REPUBLICAN_MEDIUM = "#f0846b";
  private static final String COLOR_REPUBLICAN_LARGE = "#ea4823";
  private static final String COLOR_OTHER = "#BBBBBB";
  
  private static final double POPULAR_WIN_SMALL = 0.1;  
  private static final double POPULAR_WIN_MEDIUM = 0.2;
  
  private static final String FILE_PREFIX = "election/";
  private static final String[] FILES = new String[] {"1980.csv", "1984.csv", "1988.csv", "1992.csv", "1996.csv", 
                                                      "2000.csv", "2004.csv", "2008.csv", "2012.csv"};

  /**
   * The list used to reference all the data, sorted by election year.
   */
  private final List<PresidentialModel> modelList;
  
  /**
   * The map used to reference all the data, indexed by election year.
   */
  private final Map<String, PresidentialModel> modelMap;
  
  /**
   * A filter that is shared between all PresidentialModel instances.
   */
  private final PresidentialModelFilter filter;
  
  /**
   * Constructor that creates and populates a <code>Map</code> of <code>PresidentialModel</code> instances representing
   * United States presidential election results.
   * @param filter optional filter that can be used to control the data shown from the component.
   */
  public ElectionData() {
    // Initialize the shared filter
    filter = new PresidentialModelFilter();
    
    // Initialize the data
    SortedMap<String, PresidentialModel> modelMap = new TreeMap<String, PresidentialModel>();
    for(String filePath : FILES) {
      PresidentialModel model = parseFile(FILE_PREFIX + filePath);
      model.setFilter(filter);
      modelMap.put(String.valueOf(model.getYear()), model);
    }
    
    // Initialize the map and list
    this.modelMap = Collections.unmodifiableSortedMap(modelMap);
    this.modelList = Collections.unmodifiableList(new ArrayList<PresidentialModel>(modelMap.values()));
  }
  
  /**
   * Returns a list of presidential election models sorted by year in increasing order.
   */
  public List<PresidentialModel> getModelList() {
    return modelList;
  }

  /**
   * Returns a map containing the model for presidential election results by year.
   */
  public Map<String, PresidentialModel> getModelMap() {
    return modelMap;
  }
  
  /**
   * Specifies whether states won by Democrats should be shown when calling <code>getStateResults</code> from the 
   * <code>PresidentialModel</code> instances.
   */
  public void setShowDemocrat(Boolean value) {
    this.filter.showDemocrat = value;
  }
  
  /**
   * Returns whether states won by Democrats should be shown when calling <code>getStateResults</code> from the 
   * <code>PresidentialModel</code> instances.
   */
  public Boolean getShowDemocrat() {
    return this.filter.showDemocrat;
  }
  
  /**
   * Specifies whether states won by Republican should be shown when calling <code>getStateResults</code> from the 
   * <code>PresidentialModel</code> instances.
   */
  public void setShowRepublican(Boolean value) {
    this.filter.showRepublican = value;
  }
  
  /**
   * Returns whether states won by Republican should be shown when calling <code>getStateResults</code> from the 
   * <code>PresidentialModel</code> instances.
   */
  public Boolean getShowRepublican() {
    return this.filter.showRepublican;
  }

  /**
   * Specifies the String describing which states should be shown when calling <code>getStateResults</code> from the 
   * <code>PresidentialModel</code> instances. The name or abbreviations of the desired states should be provided in a 
   * space delimited list.  Use 'All States' to prevent filtering.
   */
  public void setStateFilter(String filter) {
    if(filter != null && filter.trim().length() > 0)
      this.filter.state = filter;
    else
      this.filter.state = PresidentialModelFilter.ALL_STATES;
  }
  
  /**
   * Returns the String describing which states should be shown when calling <code>getStateResults</code> from the 
   * <code>PresidentialModel</code> instances. The name or abbreviations of the desired states should be provided in a 
   * space delimited list.  Use 'All States' to prevent filtering.
   */
  public String getStateFilter() {
    return this.filter.state;
  }
  
  public String getColorDemocrat() {
    return ElectionData.COLOR_DEMOCRAT;
  }
  
  public String getColorDemocratSmall() {
    return ElectionData.COLOR_DEMOCRAT_SMALL;
  }
  
  public String getColorDemocratMedium() {
    return ElectionData.COLOR_DEMOCRAT_MEDIUM;
  }
  
  public String getColorDemocratLarge() {
    return ElectionData.COLOR_DEMOCRAT_LARGE;
  }
  
  public String getColorRepublican() {
    return ElectionData.COLOR_REPUBLICAN;
  }
  
  public String getColorRepublicanSmall() {
    return ElectionData.COLOR_REPUBLICAN_SMALL;
  }  
  
  public String getColorRepublicanMedium() {
    return ElectionData.COLOR_REPUBLICAN_MEDIUM;
  }
  
  public String getColorRepublicanLarge() {
    return ElectionData.COLOR_REPUBLICAN_LARGE;
  }
  
  public String getColorOther() {
    return ElectionData.COLOR_OTHER;
  }

  @SuppressWarnings("oracle.jdeveloper.java.nested-assignment")
  private static PresidentialModel parseFile(String filePath) {
    try {
      InputStream input = ElectionData.class.getResourceAsStream(filePath);
      BufferedReader br = new BufferedReader(new InputStreamReader(input));
      
      // Retrieve the top level information about the election
      String firstLine = br.readLine();
      String[] summary = firstLine.split(",");
      int year = Integer.valueOf(summary[0]);
      String democrat = summary[1];
      String republican = summary[2];
      
      // Parse the state results
      List<PresidentialStateResult> stateResults = new ArrayList<PresidentialStateResult>();
      String currentLine;
      while((currentLine = br.readLine()) != null) {
        stateResults.add(new PresidentialStateResult(currentLine));
      }
      
      // Create and return the model
      return new PresidentialModel(year, democrat, republican, stateResults);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    
    // Error occurred
    return null;
  }
  
  /**
   * Represents the result of a presidential election.
   */
  public static class PresidentialModel implements Comparable {
    private final int year;
    private final String candidateDemocrat;
    private final String candidateRepublican;
    private final String electoralWinner;
    private final List<PresidentialStateResult> stateResults;
    private final List<PresidentialCandidateResult> candidateResults;
    private PresidentialModelFilter filter;
    
    public PresidentialModel(int year, String candidateDemocrat, String candidateRepublican, List<PresidentialStateResult> stateResults) {
      this.year = year;
      this.candidateDemocrat = candidateDemocrat;
      this.candidateRepublican = candidateRepublican;
      this.stateResults = stateResults;
      
      // Create the candidate results list based on the state results
      int electoralDem = 0, electoralRep = 0, electoralTotal = 0;
      int popularDem = 0, popularRep = 0, popularTotal = 0;
      for(PresidentialStateResult stateResult : stateResults) {
        electoralDem += stateResult.getElectoralDemocrat();
        electoralRep += stateResult.getElectoralRepublican();
        electoralTotal += stateResult.getElectoralTotal();
        popularDem += stateResult.getPopularDemocrat();
        popularRep += stateResult.getPopularRepublican();
        popularTotal += stateResult.getPopularTotal();
      }
      
      candidateResults = new ArrayList<PresidentialCandidateResult>();
      candidateResults.add(new PresidentialCandidateResult(candidateDemocrat, PARTY_DEMOCRAT, electoralDem, popularDem, popularTotal));
      candidateResults.add(new PresidentialCandidateResult(candidateRepublican, PARTY_REPUBLICAN, electoralRep, popularRep, popularTotal));
      candidateResults.add(new PresidentialCandidateResult(PARTY_OTHER, PARTY_OTHER, electoralTotal - electoralDem - electoralRep, popularTotal - popularDem - popularRep, popularTotal));
      
      // Keep track of the national results
      if(electoralDem > electoralRep)
        electoralWinner = PARTY_DEMOCRAT;
      else if(electoralRep > electoralDem)
        electoralWinner = PARTY_REPUBLICAN;
      else
        electoralWinner = PARTY_OTHER;
    }

    public int getYear() {
      return year;
    }

    public String getCandidateDemocrat() {
      return candidateDemocrat;
    }

    public String getCandidateRepublican() {
      return candidateRepublican;
    }
    
    /**
     * Returns the winner of the electoral vote.  Either "Democrat", "Republican", or "Other".
     */
    public String getElectoralWinner() {
      return electoralWinner;
    }

    /**
     * Returns a list of state results taking into account the party filters.
     */
    public List<ElectionData.PresidentialStateResult> getStateResults() {
      if(filter == null)
        return getUnfilteredStateResults();
      
      List<ElectionData.PresidentialStateResult> filteredResults = new ArrayList<ElectionData.PresidentialStateResult>(getUnfilteredStateResults());
      Iterator<ElectionData.PresidentialStateResult> iter = filteredResults.iterator();
      
      // Remove the objects to be filtered out
      while(iter.hasNext()) {
        PresidentialStateResult result = iter.next();
        if((!filter.showDemocrat && PARTY_DEMOCRAT.equals(result.getElectoralWinner())) || 
           (!filter.showRepublican && PARTY_REPUBLICAN.equals(result.getElectoralWinner())))
          iter.remove();
      }
      
      return filteredResults;
    }

    /**
     * Returns a list of state results for the currently visible states.
     */
    public List<ElectionData.PresidentialStateResult> getUnfilteredStateResults() {
      if(filter == null)
        return stateResults;
      
      List<ElectionData.PresidentialStateResult> filteredResults = new ArrayList<ElectionData.PresidentialStateResult>(stateResults);
      Iterator<ElectionData.PresidentialStateResult> iter = filteredResults.iterator();
      
      // Remove the objects to be filtered out
      while(iter.hasNext()) {
        PresidentialStateResult result = iter.next();
        if(filter != null && !filter.state.contains(ElectionData.PresidentialModelFilter.ALL_STATES)) {
          // State filters are a space delimited list, upon which we'll search the abbreviation and full state name.
          boolean isRemoved = true;
          String[] splits = filter.state.split("\\s");
          for(String split : splits) {
            split = split.toLowerCase();
            if(result.getState().toLowerCase().contains(split) || result.getStateLabel().toLowerCase().contains(split)) {
              isRemoved = false;
              break;
            }
          }
          
          if(isRemoved)
            iter.remove();
        }
      }
      
      return filteredResults;
    }
    
    public List<ElectionData.PresidentialCandidateResult> getCandidateResults() {
      return candidateResults;
    }
    
    public void setFilter(PresidentialModelFilter filter) {
      this.filter = filter;
    }

    @Override
    public int compareTo(Object obj) {
      return getYear() - ((PresidentialModel) obj).getYear();
    }
    
    @Override
    public boolean equals(Object obj) {
      if(!(obj instanceof PresidentialModel)) 
        return false;
      
      return getYear() == ((PresidentialModel) obj).getYear();
    }
  }
  
  /**
   * Represents the result of a presidential election for a candidate.
   */
  public static class PresidentialCandidateResult {
    private final String name;
    private final String party;
    private final int electoralVotes;
    private final int popularVotes;
    private final int popularTotal;
    
    public PresidentialCandidateResult(String name, String party, int electoralVotes, int popularVotes, int popularTotal) {
      this.name = name;
      this.party = party;
      this.electoralVotes = electoralVotes;
      this.popularVotes = popularVotes;
      this.popularTotal = popularTotal;
    }

    /**
     * Returns the name of the candidate.
     */
    public String getName() {
      return name;
    }
    
    /**
     * Returns the party affiliation of the candidate.  Either "Democrat", "Republican", or "Other".
     */
    public String getParty() {
      return party;
    }

    /**
     * Returns the total number of electoral votes obtained by the candidate.
     */
    public int getElectoralVotes() {
      return electoralVotes;
    }

    /**
     * Returns the total number of votes obtained by the candidate.
     */
    public int getPopularVotes() {
      return popularVotes;
    }
    
    /**
     * Returns the total number of votes.
     */
    public int getPopularTotal() {
      return popularTotal;
    }    
    
    /**
     * Returns a formatted label describing the percentage of the popular vote obtained by the candidate.
     */
    public String getPopularPercentageLabel() {
      NumberFormat percentFormat = NumberFormat.getPercentInstance();
      percentFormat.setMaximumFractionDigits(2);
      return percentFormat.format((1.0*popularVotes)/popularTotal);
    }

    /**
     * Returns a formatted label describing the number of vote obtained by the candidate.
     */
    public String getPopularVoteLabel() {
      DecimalFormat integerFormat = new DecimalFormat();
      integerFormat.setMaximumFractionDigits(0);
      return integerFormat.format(popularVotes);
    }
  }
  
  /**
   * Represents the result of a presidential election for a state.
   */
  public static class PresidentialStateResult {
    private final String state;
    private final int electoralDemocrat;
    private final int electoralRepublican;
    private final int popularDemocrat;
    private final int popularRepublican;
    private final int popularTotal;
    // Tmap context menus demo
    private String tempWinner;

    public PresidentialStateResult(String stateResult) {
      String[] results = stateResult.split(",");
      state = results[0];
      electoralDemocrat = results[1].length() > 0 ? Integer.valueOf(results[1]) : 0;
      electoralRepublican = results[2].length() > 0 ? Integer.valueOf(results[2]) : 0;
      popularDemocrat = Integer.valueOf(results[3]);
      popularRepublican = Integer.valueOf(results[4]);
      popularTotal = Integer.valueOf(results[5]);
      tempWinner = getElectoralWinner();
      
    }

    public String getState() {
      return state;
    }
    
    public String getStateLabel() {
      return StateData.getName(state);
    }

    public int getElectoralDemocrat() {
      return electoralDemocrat;
    }

    public int getElectoralRepublican() {
      return electoralRepublican;
    }
    
    public int getElectoralTotal() {
      return electoralDemocrat + electoralRepublican;
    }
    
    /**
     * Returns the winner of the electoral vote.  Either "Democrat", "Republican", or "Other".
     */
    public String getElectoralWinner() {
      if(electoralDemocrat > electoralRepublican)
        return PARTY_DEMOCRAT;
      else if(electoralRepublican > electoralDemocrat)
        return PARTY_REPUBLICAN;
      else
        return PARTY_OTHER;
    }
    
    /**
     * Returns a tooltip describing the electoral result for this state.
     * @return
     */
    public String getElectoralTooltip() {
      StringBuilder sb = new StringBuilder();
      sb.append("<b>").append(getStateLabel()).append("</b><br/>");
      sb.append(getElectoralTotal()).append(" Electoral Votes");
      return sb.toString();
    }
    
    public String getTempWinner() {
      return tempWinner;
    }
    
    public void setTempWinner(String winner) {
      tempWinner = winner;
    }
    
    public void toggleTempWinner() {
      if (tempWinner.equals(PARTY_REPUBLICAN)) {
        tempWinner = PARTY_DEMOCRAT;
      }
      else if (tempWinner.equals(PARTY_DEMOCRAT)) {
        tempWinner = PARTY_REPUBLICAN;
      }
    }
    
    public void resetTempWinner() {
      tempWinner = getElectoralWinner();
    }

    public int getPopularDemocrat() {
      return popularDemocrat;
    }

    public int getPopularRepublican() {
      return popularRepublican;
    }
    
    public int getPopularOther() {
      return popularTotal - (popularDemocrat + popularRepublican);
    }

    public int getPopularTotal() {
      return popularTotal;
    }
    
    /**
     * Returns the difference in popular vote count between the winner and the runner up.
     */
    public int getPopularWinMargin() { 
      return Math.abs(popularDemocrat - popularRepublican);
    }
    
    /**
     * Returns the difference in popular vote percentage between the winner and the runner up as a double between 0 and 1.
     */
    public double getPopularWinPercentage() {
      double difference = Math.abs(popularDemocrat - popularRepublican);
      return difference/popularTotal;
    }

    /**
     * Returns the winner of the popular vote.  Either "Democrat", "Republican", or "Other".
     */
    public String getPopularWinner() {
      if(popularDemocrat > popularRepublican)
        return PARTY_DEMOCRAT;
      else if(popularRepublican > popularDemocrat)
        return PARTY_REPUBLICAN;
      else
        return PARTY_OTHER;
    }
    
    /**
     * Returns a color indicating the degree of the win in the popular vote.
     */
    public String getPopularColor() {
      String winner = getPopularWinner();
      double winPercentage = getPopularWinPercentage();
      if(PARTY_DEMOCRAT.equals(winner)) {
        if(winPercentage < POPULAR_WIN_SMALL)
          return COLOR_DEMOCRAT_SMALL;
        else if(winPercentage < POPULAR_WIN_MEDIUM)
          return COLOR_DEMOCRAT_MEDIUM;
        else
          return COLOR_DEMOCRAT_LARGE;
      }
      else if(PARTY_REPUBLICAN.equals(winner)) {
        if(winPercentage < POPULAR_WIN_SMALL)
          return COLOR_REPUBLICAN_SMALL;
        else if(winPercentage < POPULAR_WIN_MEDIUM)
          return COLOR_REPUBLICAN_MEDIUM;
        else
          return COLOR_REPUBLICAN_LARGE;
      }
      else
        return COLOR_OTHER;
    }
    
    /**
     * Returns a tooltip describing the popular voting result for this state.
     * @return
     */
    public String getPopularTooltip() {
      StringBuilder sb = new StringBuilder();
      sb.append("<b>").append(getStateLabel()).append("</b><br/>Win Margin: ");
      
      NumberFormat percentFormat = NumberFormat.getPercentInstance();
      sb.append(percentFormat.format(getPopularWinPercentage()));
      return sb.toString();
    }
    
    @Override
    public String toString() {
      // Provided for debugging purposes.
      return state + ": " + electoralDemocrat + ", " + electoralRepublican + "; " + popularDemocrat + ", " + popularRepublican + ", " + popularTotal;
    }
  }
  
  /**
   * Represents a filter for data from a presidential election.
   */
  public static class PresidentialModelFilter {
    /**
     * Constant for the filter state when all states are shown.
     */
    private static final String ALL_STATES = "All States";

    private boolean showDemocrat;
    private boolean showRepublican;
    private String state;
    
    public PresidentialModelFilter() {
      showDemocrat = true;
      showRepublican = true;
      state = ALL_STATES;
    }
  }
}
