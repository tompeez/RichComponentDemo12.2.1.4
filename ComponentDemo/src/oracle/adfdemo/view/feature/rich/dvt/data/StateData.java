package oracle.adfdemo.view.feature.rich.dvt.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class exposing the names and abbreviations of US states for lookup and conversion.
 */
public class StateData {
  private static final String DATA =
    "AL,Alabama\n" + "AK,Alaska\n" + "AZ,Arizona\n" + "AR,Arkansas\n" + "CA,California\n" + "CO,Colorado\n" +
    "CT,Connecticut\n" + "DE,Delaware\n" + "DC,Washington D.C.\n" + "FL,Florida\n" + "GA,Georgia\n" + "HI,Hawaii\n" +
    "ID,Idaho\n" + "IL,Illinois\n" + "IN,Indiana\n" + "IA,Iowa\n" + "KS,Kansas\n" + "KY,Kentucky\n" + "LA,Louisiana\n" +
    "ME,Maine\n" + "MD,Maryland\n" + "MA,Massachusetts\n" + "MI,Michigan\n" + "MN,Minnesota\n" + "MS,Mississippi\n" +
    "MO,Missouri\n" + "MT,Montana\n" + "NE,Nebraska\n" + "NV,Nevada\n" + "NH,New Hampshire\n" + "NJ,New Jersey\n" +
    "NM,New Mexico\n" + "NY,New York\n" + "NC,North Carolina\n" + "ND,North Dakota\n" + "OH,Ohio\n" + "OK,Oklahoma\n" +
    "OR,Oregon\n" + "PA,Pennsylvania\n" + "RI,Rhode Island\n" + "SC,South Carolina\n" + "SD,South Dakota\n" +
    "TN,Tennessee\n" + "TX,Texas\n" + "UT,Utah\n" + "VT,Vermont\n" + "VA,Virginia\n" + "WA,Washington\n" +
    "WV,West Virginia\n" + "WI,Wisconsin\n" + "WY,Wyoming";
  
  private static final List<State> stateList = parseStateData();
  private static final Map<String, State> abbreviationMap = createAbbreviationMap(stateList);
  
  /**
   * Returns the name of the state corresponding to the given abbreviation.
   * @param abbreviation
   */
  public static String getName(String abbreviation) {
    State state = abbreviationMap.get(abbreviation);
    return state != null ? state.name : null;
  }

  /**
   * Parses the data object and creates the list of State instances.
   */
  private static List<State> parseStateData() {
    List<State> stateList = new ArrayList<State>();
    
    // Split the data into lines, each of which represents a single state or territory
    String[] stateStrings = DATA.split("\n");
    for(String stateString : stateStrings) {
      String[] splits = stateString.split(",");
      stateList.add(new State(splits[0], splits[1]));
    }
    
    return Collections.unmodifiableList(stateList);
  }
  
  /**
   * Creates a map of state abbrevation String to State instances for quick lookup of the full state names.
   */
  private static Map<String, State> createAbbreviationMap(List<State> stateList) {
    Map<String, State> map = new HashMap<String, State>();
    for(State state : stateList) {
      map.put(state.abbreviation, state);
    }
    return Collections.unmodifiableMap(map);
  }
  
  /**
   * Class representing a single state.
   */
  private static class State {
    private final String abbreviation;
    private final String name;
    
    public State(String abbreviation, String name) {
      this.abbreviation = abbreviation;
      this.name = name;
    }
  }
}
