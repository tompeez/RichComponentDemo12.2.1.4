/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/fake/FakeDataSource.java /main/7 2012/07/09 13:46:39 jievans Exp $ */

/* Copyright (c) 2006, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jievans     05/31/11 - Major revision: add realistic header/data values, varying member counts by layer, pivoting, header/data sorting
    jievans     02/26/10 - support isFetched()
    ahadi       03/19/09 - implement getEdgeCount
    jievans     12/04/08 - allow edge extent of 0
    dahmed      08/27/08 - 
    jievans     08/14/08 - ensure 0-layer edge has exactly 1 slice, handle bad params
    jievans     07/08/08 - impl QDRs properly to fix demo
    dahmed      01/21/08 - 
    jievans     03/30/07 - adjustable extents, better edge case behavior
    dahmed      03/27/07 - add getDataAccess()
    dahmed      03/18/07 - add slice qdr support
    bmoroze     11/01/06 - Move files back to oracle.dss.util
    dahmed      10/30/06 - rename crosstab to pivotTable
    dahmed      10/13/06 - Creation
 */
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.fake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import oracle.dss.util.ColumnOutOfRangeException;
import oracle.dss.util.CubeDataAccess;
import oracle.dss.util.CubeDataDirector;
import oracle.dss.util.DataAccessAdapter;
import oracle.dss.util.DataAvailableEvent;
import oracle.dss.util.DataDirector;
import oracle.dss.util.DataDirectorException;
import oracle.dss.util.DataDirectorListener;
import oracle.dss.util.DataMap;
import oracle.dss.util.DataSource;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerMetadataMap;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.QDR;
import oracle.dss.util.QDRInterface;
import oracle.dss.util.QDRSliceSortInfo;
import oracle.dss.util.RelationalDataAccess;
import oracle.dss.util.RelationalDataDirector;
import oracle.dss.util.RowOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;
import oracle.dss.util.SortInfo;


/**
 * This data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/fake/FakeDataSource.java /main/7 2012/07/09 13:46:39 jievans Exp $
 *  @author  dahmed  
 *  @since   release specific (what release of product did this appear in)
 */
public class FakeDataSource extends DataAccessAdapter implements CubeDataDirector, CubeDataAccess, RelationalDataDirector, RelationalDataAccess, DataSource
{                           // LocalDataSource

    // *************   PARAMS (Change at will for testing)   *******************
  
    // The 2 arrays are for the column and row edge, respectively.  In both cases, the n'th array element indicates the number 
    // of members in layer n, and the array length indicates the number of layers on the edge.
    // 
    // E.g. if the array is {6,4}, then the edge has 2 layers, containing 6 and 4 members, respectively, for a total of 6x4=24 slices on the edge.  
    // If the array is {20}, then the edge has 1 layer containing 20 members, so that the edge has 20 slices.
    // If the array is {} (the empty array), then the edge has 0 layers, so that the edge has 1 slice.
    // 
    // - The number of layers per edge is unlimited, but an absurdly large layer count is likely to hose the component.
    // - Large member counts for individual layers may slow down calls to getSlicesFromQDR(), but otherwise are fine.  TBD: depending on how we deal (or don't deal) with the problem, a very large member count for a layer might also negatively impact the data sort solution, e.g. by holding a large array in memory.
    // - The number of slices per edge (i.e. edge extent, i.e. product of all the numbers in the array) must not exceed approximately 2 billion (i.e. must fit in an int).
    //   Large edge extents are fine in and of themselves, but a large edge extent can only come from a large layer count or large member counts in some or all of the 
    //   layers, both of which can have a performance impact as mentioned previously.  
    private int[][] memberCounts = {{9,2,3},{15,5}};
    
    // Cached for efficiency.  
    // Only setMemberCounts should set these.
    // Only _getMemberExtent should read these.  All others should call _getMemberExtent or getMemberExtent.
    // 
    // Indexes are off by 1!!!!!  For each inner array, the first element is for layer -1 (the root layer), the next is for 
    // layer 0, and so on, so that the array length is edgeExtent + 1.
    // 
    // Member extents are the running product of member counts, reading from the right.  E.g.
    // layer         -1 0 1 2 
    //               -- - - -
    // member count     9 2 3
    // member extent 54 6 3 1
    // 
    // I.e. if member counts for an edge are  {9,2,3}, the running products reading from the right are 3, 6, 54, 
    // so member extents for that edge are {54,6,3,1}
    private int[][] memberExtents = new int[2][];

    /**
     * @param edge
     * @param counts comma-separated string of positive integers, such as "9,8,7"
     */
    public void setMemberCountString(int edge, String counts) {
        String[] strArray = (counts==null || "".equals(counts.trim())) ? new String[0] : counts.split(",");
        int[] edgeMemberCounts = new int[strArray.length];
        
        try {
            for (int i=0; i<strArray.length; ++i) {
                int count = Integer.parseInt(strArray[i].trim());
                if (count<0) 
                    count=0;
                edgeMemberCounts[i] = count;
            }
            
            // all parsed correctly, so set it
            memberCounts[edge] = edgeMemberCounts;
        } catch (NumberFormatException e) {
            ; // don't update member counts
        }

        init(); // apply the new member counts.  They're basically getting a new pivot table, so clear cell edits, sorts, etc.
    }
    
    public String getMemberCountString(int edge) {
        int [] edgeMemberCounts = memberCounts[edge];
        boolean notFirst = false;
        StringBuilder builder = new StringBuilder();
        for (int count : edgeMemberCounts) {
            if (notFirst)
                builder.append(',');
            notFirst = true;
            builder.append(count);
        }
        return builder.toString();
    }

    private void populateMemberCounts() {
        populateMemberCounts(DataDirector.COLUMN_EDGE);
        populateMemberCounts(DataDirector.ROW_EDGE);
    }
    
    private void populateMemberCounts(int edge) {
        List<Layer> edgeLayers = layers.get(edge);
        int layerCount = edgeLayers.size();
        int[] edgeMemberCounts = new int[layerCount];
        
        for (int i=0; i<layerCount; ++i) {
            Layer layer = edgeLayers.get(i);
            edgeMemberCounts[i] = layer.getMemberCount();
        }
        
        memberCounts[edge] = edgeMemberCounts;
        populateMemberExtents(edge);
    }
    
    // call this AFTER setting (all or part of) memberCounts
    private void populateMemberExtents() {
        populateMemberExtents(DataDirector.COLUMN_EDGE);
        populateMemberExtents(DataDirector.ROW_EDGE);
    }
    
    // call this AFTER setting (all or part of) memberCounts
    private void populateMemberExtents(int edge) {
        int[] edgeMemberCounts = memberCounts[edge];
        int[] edgeMemberExtents = new int[edgeMemberCounts.length + 1]; // off by 1:  first array elem is for layer "-1", next elem is for layer 0, ...
        edgeMemberExtents[edgeMemberCounts.length] = 1; // innermost layer always has memberExtent 1

        for (int i=edgeMemberCounts.length-1; i>=0; --i) {
            edgeMemberExtents[i] = edgeMemberCounts[i] * edgeMemberExtents[i+1];
        }
        
        memberExtents[edge] = edgeMemberExtents;
    }
    
    private boolean coordinateMode = false;

    public boolean getCoordinateMode() {
        return coordinateMode;
    }

    public void setCoordinateMode(boolean mode) {
        coordinateMode = mode;
    }

    // *************   BACKING DATA   *******************
  
    private abstract static class Layer {
        /**
         * @return the label (and ID) of this layer
         */
        public abstract String getLabel();

        /**
         * @param index >=0
         * @return the label (and ID) of the specified member
         */
        public String getMemberLabel(int index) {
            index = getIndexGivenSortDirection(index);
            Member[] membArray = (isHeaderSort) ? headerSortedMembers : dataSortedMembers;
            return membArray[index].getLabel();
            // OLD, FROM ENUMERATEDLAYER OVERRIDE: return (index < members.length) ? members[index] : layerLabel + index;
        }
       
        /**
         * @param memberLabel
         * @return the index of the member with the specified ID/label, or -1 if not found
         */
        public int getMemberIndex(String memberLabel) {
            for (int i=0; i<getMemberCount(); ++i) {
                if (getMemberLabel(i).equals(memberLabel))
                    return i;
            }

            return -1;
        }
       
        /**
         * @return the number of unique members in this layer
         */
        public int getMemberCount() {
            return memberCount;
        }
       
        /**
         * @param count the number of unique members in this layer
         */
        protected void setMemberCount(int count) {
            this.memberCount = count;
        }

        // Must be final since called from constructors of subclasses.  If they need to override, make it non-final, and every subclass 
        // whose constructor calls it must have a final override of it (even if it just calls super()).
        protected final void populateMembers() {
            // TODO: optimize for huge memberCounts so don't allocate huge array.  Possibilities: cap memberCount at some reasonable value, or only put the first n members in the arrays and figure out how to handle the remaining members, e.g. make them basically immutable: they stay in place when sorting, they don't drill or rename, etc.
            int limit = getMemberCount(); // Math.min(memberCount, 50);
            headerSortedMembers = new Member[limit];
            for (int i=0; i<limit; ++i) {
                String membLabel = getInitialMemberLabel(i); 
                int value = getMemberValue(membLabel); // the last 3 decimal digits of the label's hashcode, dropping sign
                headerSortedMembers[i] = new Member(membLabel, value);
            }
            dataSortedMembers = Arrays.copyOf(headerSortedMembers, headerSortedMembers.length); // shallow copy, which is what we want
            Arrays.sort(dataSortedMembers);
        }
        
        // every override of this must be final, since it is called indirectly from the subclasses' constructors
        protected abstract String getInitialMemberLabel(int index);
        
        // TBD: doesn't handle ungrouped sorts.  Maybe show warning in demo if an ungrouped sort is requested.
        /**
         * @param ascending true for ascending, false for descending
         */
        public void sort(boolean isHeader, boolean ascending) {
            this.ascending = ascending;
            this.isHeaderSort = isHeader;
        }
        
        // TODO: Add reordering support as follows:
        // - Rather than having getMembeLabel switch on isHeader to decide which array to look in, do this: 
        // - New ivar: Member[] currentMembers
        // - sort(): for both data- and header-sorts, removes reorders on this layer, as follows;
        //   - sets currentMembers to point to either headerSortedMembers or dataSortedMembers, orphaning any 3rd array it might have pointed to.
        //   - sets isReordered ivar to false.  
        // - public [abstract?] void reorder()
        //   - if (!isReordered) { currentMembers = Arrays.copyOf(currentMembers, currentMembers.length); isReordered = true;} 
        //   - applies the reorder to currentMembers.
        // - setSorts and clearSorts
        //   - ensure clearSorts() still clears/resets everything it needs to, and that having it call sort(true, true) on each layer is still the right thing.  
        //   - Remember: setSorts() applies sorts to original data, so on every call to it, we call clearSorts() first.
        //   - alternately, could detect that new sort array is just the old one with one more sort appended, and only apply the new sort, without calling clearSorts first.
        // - jspx:  mention that reorders are symmetric (like drills, header cell edits, ...) due to data model.  Other data models can be asymmetric.  
        // 
        // public [abstract?] void reorder();

        /**
         * Given the index of a member when layer is sorted in one direction, returns the index of the same member when layer is sorted in the opposite direction.
         */
        protected int getIndexInOppositeSortDirection(int i) {
            return getMemberCount()-1-i;
        }

        /**
         * Given the index of a member when layer is sorted in ascending direction, returns the index of that member in the current sort direction.  Or equivalently, 
         * given the index of a member when layer is sorted in current direction, returns the index of that member in ascending sort direction.  
         */
        protected int getIndexGivenSortDirection(int i) {
            return (ascending) ? i : getIndexInOppositeSortDirection(i);
        }

        /**
         * @return a copy of the specified layer.
         */
        public abstract Layer copy(int memberCount);
       
        private static List<Layer> layers = new ArrayList<Layer>();
      
        static {
            // IDEAS: 
            // - geography: country, region, airport code, 
            //   - geo-related: currency, university, 
            // - business: channel
            // - arts: artist, author, book, film, actor, director, character (book movie tv), genre, superhero, villain, 
            // - misc: mode of transportation, holiday, elements
            // NOTE: all the constructor calls in this block should pass memberCount 0, to minimize the memory used by these canonical copies.
            // TBD: to eliminate the need for that, and for all the "list" boilerplate in these calls, consider adding constructor overloads that don't take a memberCount, don't attempt to do the related setup, and which take a String[] rather than List<String>, so that this List boilerplate can live in the c'r instead.  The existing c'rs could even stop being public, since only copy() would call them.  
            layers.add(new EnumeratedLayer("Measure", Collections.unmodifiableList(Arrays.asList(new String[]{"Acres", "Bytes", "Cubits", "Decibels", "Electronvolts", "FLOPS", "Gallons", "Hectares", "Inches", "Joules", "KLOCs", "Light years", "Miles", "Newtons", "Ounces", "Proof", "Quarts", "Radians", "Scovilles", "Tablespoons", "Units", "Volts", "Watts", "X's", "Yards", "Zaks"})), 0));
            layers.add(new EnumeratedLayer("US City", Collections.unmodifiableList(Arrays.asList(new String[]{"Atlanta", "Boston", "Chicago", "Denver", "Evanston", "Fargo", "Glendale", "Honolulu", "Indianapolis", "Jacksonville", "Knoxville", "Lexington", "Memphis", "New York", "Omaha", "Philadelphia", "Quantico", "Richmond", "San Francisco", "Tallahassee", "Urbana", "Virginia Beach", "Washington", "Xenia", "Yorktown", "Zephyr"})), 0));
            layers.add(new EnumeratedLayer("Product", Collections.unmodifiableList(Arrays.asList(new String[]{"Avocados", "Bobbins", "Canoes", "Drills", "Electronics", "Flashlights", "Girdles", "Hitches", "Insulation", "Jewelry", "Kitchen", "Lamps", "Meat", "Nursery", "Organizers", "Pants", "Quilts", "Rugs", "Snorkels", "Tchotchkes", "Umbrellas", "Vacuums", "Weathervanes", "Xylophones", "Y cables", "Zippers"})), 0));
            layers.add(new EnumeratedLayer("Car", Collections.unmodifiableList(Arrays.asList(new String[]{"Aston Martin", "Bentley", "Corvette", "DeLorean", "Edsel", "Ferrari", "Geo", "Harley-Davidson", "Isdera", "Jaguar", "Kia", "Lamborghini", "McLaren", "Nova", "Opel", "Pagani", "Quadrant", "Rolls-Royce", "Shelby", "Tesla", "Ultima", "Vespa", "Wrangler", "Xenia", "Yale", "Zip"})), 0));
            layers.add(new EnumeratedLayer("Salesperson", Collections.unmodifiableList(Arrays.asList(new String[]{"Archie", "Betty", "Chet", "Daphne", "Ezra", "Fred", "Gertrude", "Huckleberry", "Iola", "Joe", "Katie", "Luna", "Minerva", "Nancy", "Oliver", "Phil", "Quentin", "Ron", "Scooby", "Trillian", "Ursula", "Velma", "Wulfric", "Xavier", "Yvonne", "Zaphod"})), 0));
            layers.add(new NumberLayer("Year", Calendar.getInstance().get(Calendar.YEAR), 0, false)); // first year is always the current year
            layers.add(new EnumeratedLayer("Month", Collections.unmodifiableList(Arrays.asList(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December", "Neptunuary", "Thorch", "Nero", "Urember", "Bacchanalius", "Plutoc", "Mercurious", "Heran", "Athenary", "Vestubrary", "Dianis", "Undecember", "Duodecember", "Tredecember", "Quattuordecember", "Quinquadecember", "Sedecember", "Septendecember", "Octodecember", "Novemdecember", "Vigintember"})), 0));
            layers.add(new NumberLayer("Day", 1, 0, true));
            layers.add(new EnumeratedLayer("Format", Collections.unmodifiableList(Arrays.asList(new String[]{"AIFF", "Blu-ray", "CD", "DAT", "Eight-Track", "FourK", "Gramophone", "HD-DVD", "iKlax", "JPEG", "K-Lite", "Laserdisc", "MiniDisc", "N/A", "Ogg Vorbis", "PCM", "Quadraphonic", "Reel-to-Reel", "SACD", "TrueHD", "UMD", "VHS", "WMA", "Xvid", "Yellow Book", "Zip disk"})), 0));
            layers.add(new EnumeratedLayer("City", Collections.unmodifiableList(Arrays.asList(new String[]{"Athens", "Bangkok", "Cairo", "Delhi", "Edinburgh", "Firenze", "Genoa", "Hong Kong", "Istanbul", "Jakarta", "Krakow", "London", "Madrid", "Nairobi", "Ostrava", "Paris", "Quito", "Rio de Janeiro", "Saint Petersburg", "Tokyo", "Utrecht", "Vienna", "Warsaw", "Xiamen", "York", "Zaragoza"})), 0));
            layers.add(new EnumeratedLayer("Color", Collections.unmodifiableList(Arrays.asList(new String[]{"Azure", "Black", "Cyan", "Deep Purple", "Ecru", "Fuchsia", "Green", "Hot pink", "Indigo", "Jade", "Khaki", "Lavender", "Magenta", "Navy", "Orange", "Plum", "Quicksand", "Red", "Scarlet", "Turquoise", "Ultramarine", "Violet", "White", "Xanadu", "Yellow", "Zaffre"})), 0));
            layers.add(new NumberLayer("Week", 1, 0, true));
            // TEMPLATE: ADD LAYER NAME AND MEMBER NAMES: layers.add(new EnumeratedLayer("Some layer name", Collections.unmodifiableList(Arrays.asList(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"})), 0));
        }
  
        /**
         * Returns a copy of the specified layer.  We return a copy so the caller can mutate it, e.g by sorting, reordering, or editing the values.
         *
         * @param index index into the internal list of layers.  Must be >=0.
         * @return the layer at that index
         */
        public static Layer getCopyOfLayer(int index, int memberCount) {
            if (index < layers.size()) {
                return layers.get(index).copy(memberCount);
            } else {
                return new GeneralLayer(index, memberCount);
            }
        }

        private int memberCount;
        protected Member[] headerSortedMembers;
        protected Member[] dataSortedMembers;
        
        // initially sorted by ascending member labels
        private boolean ascending = true;
        protected boolean isHeaderSort = true;
    }
  
    private static class EnumeratedLayer extends Layer {
        /**
         * @param label unique non-null layer label, capitalized and singular, e.g. "City", not "cities".  Also serves as layer ID.
         * @param members non-null, immutable List.  Each member must be unique and non-null, and should not match any of the auto-generated names for members whose index >= the length of this list.  
         *                Must initially be in ascending sort order (logically, not necessarily alphabetically, e.g. Month).
         *                Preferably all capitalized.  For alphabetically sorted layers, preferably length 26 with one starting with each letter.  E.g. {"Atlanta", "Boston", "Chicago", ...}.
         */
        public EnumeratedLayer(String label, List<String> members, int memberCount) {
            this.layerLabel = label;
            this.members = members;
            setMemberCount(memberCount);
            populateMembers();
        }
        
        @Override
        public String getLabel() {
            return layerLabel;
        }
   
        // must be final, since it is called indirectly from the constructor
        @Override
        protected final String getInitialMemberLabel(int index) {
            return (index < members.size()) ? members.get(index) : layerLabel + " " + (index+1); // use index+1 in label to make it 1-based for users, e.g. the member after Z displays as 27, not 26
        }
        
        // This override still has to loop over the array, but avoids looping over the "overflow" members by assuming that they are all at the end in the original order with the original naming convention.  
        // Unclear if this can be tweaked to work with decreasing sorts, reorders, header cell editing (when available), etc.
//        @Override
//        public int getMemberIndex(String memberLabel) {
//            if (members.length < memberCount && memberLabel.startsWith(layerLabel)) {
//                String stringIndex = memberLabel.substring(layerLabel.length());
//                try {
//                    int index = Integer.parseInt(stringIndex);
//                    if (index>=members.length && index<memberCount) 
//                        return index;
//                } catch (NumberFormatException e) {
//                    ; // fallthru
//                }
//            }
//
//            int loopBound = Math.min(getMemberCount(), members.length);
//            for (int i=0; i<loopBound; ++i) {
//                if (members[i].equals(memberLabel))
//                    return i;
//            }
//
//            return -1;
//        }
       
        @Override
        public EnumeratedLayer copy(int memberCount) {
            return new EnumeratedLayer(layerLabel, members, memberCount); // as long as the members list is immutable as required by constructor, it can be shared by all copies of this layer.  In any case, we never try to change it.
        }
      
        private String layerLabel;
        private List<String> members;
    }
   
    private static class GeneralLayer extends Layer {
        public GeneralLayer(int layerIndex, int memberCount) {
            this.layerIndex = layerIndex;
            setMemberCount(memberCount);
            populateMembers();
        }
   
        @Override
        public String getLabel() {
            return "Layer " + (layerIndex + 1); // use layerIndex+1 in label to make it 1-based for users, e.g. if there are 10 regular layers, then the 11th says "Layer 11", not "Layer 10".
        }
   
        // must be final, since it is called indirectly from the constructor
        @Override
        protected final String getInitialMemberLabel(int index) {
//            return (index < 26) ? String.valueOf((char)('A' + index)) : memberLabelPrefix + (index-25);
            return (index < 26) ? new String(new char[]{(char)('A' + index), (char)('a' + index), (char)('a' + index)}) : memberLabelPrefix + (index-25);
        }
        
        // TODO: This code assumes that the members are in the original order with the original naming convention.  
        //       Needs to be tweaked to work with decreasing sorts, reorders, and header cell editing (when available).
//        @Override
//        public int getMemberIndex(String memberLabel) {
//            if (memberLabel.length()==1) {
//                char c = memberLabel.charAt(0);
//                if (c>='A' && c<='Z')
//                    return c - 'A';
//                else
//                    return -1;
//            } else if (memberLabel.startsWith(memberLabelPrefix)) {
//                String stringIndex = memberLabel.substring(memberLabelPrefix.length());
//                try {
//                    int index = Integer.parseInt(stringIndex);
//                    if (index>=26 && index<getMemberCount()) 
//                        return index;
//                    else
//                        return -1;
//                } catch (NumberFormatException e) {
//                    return -1;
//                }
//            } else {
//                return -1;
//            }
//        }

        @Override
        public GeneralLayer copy(int memberCount) {
            return new GeneralLayer(layerIndex, memberCount);
        }
      
        private int layerIndex;
        private String memberLabelPrefix = "Zzz";
    }
   
    /**
     * Use for Years, Weeks, etc. 
     */
    private static class NumberLayer extends Layer {
        public NumberLayer(String label, int startNum, int memberCount, boolean prefixLayerLabel) {
            this.label = label;
            this.startNum = startNum;
            setMemberCount(memberCount);
            this.prefixLayerLabel = prefixLayerLabel;
            this.memberLabelPrefix = (prefixLayerLabel) ? label + " " : "";
            populateMembers();
        }
   
        @Override
        public String getLabel() {
            return label;
        }
   
        // must be final, since it is called indirectly from the constructor
        @Override
        protected final String getInitialMemberLabel(int index) {
            return memberLabelPrefix + Integer.toString(startNum + index);
        }
        
//        // TODO: This code assumes that the members are in the original order with the original naming convention.  
//        //       Needs to be tweaked to work with decreasing sorts, reorders, and header cell editing (when available).
//        @Override
//        public int getMemberIndex(String memberLabel) {
//            try {
//                int index = Integer.parseInt(memberLabel);
//                int retVal = index - startNum;
//                if (retVal>=0 && retVal<getMemberCount()) 
//                    return retVal;
//                else
//                    return -1;
//            } catch (NumberFormatException e) {
//                return -1;
//            }
//        }

        @Override
        public NumberLayer copy(int memberCount) {
            return new NumberLayer(label, startNum, memberCount, prefixLayerLabel);
        }
      
        private String label;
        private int startNum;
        private boolean prefixLayerLabel;
        private String memberLabelPrefix;
    }
    
    // TODO: If instances of Member ever live in the canonical layers, and Member objects become mutable (e.g. hold drill state), then must provide a copy method so that callers can't alter the Members in the canonical Layers.
    // For each member in the layer, there must be one copy shared by the original layer's 2 arrays, and a separate copy shared by the copied layer's 2 arrays.
    private static class Member implements Comparable {
        public Member(String label, int value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public int getValue() {
            return value;
        }
        
        private String label;
        private int value;

        @Override
        public int compareTo(Object o) {
            if (o instanceof Member) {
                Member other = (Member)o;
                return value - other.value; // won't work if huge values cause integer overflow, but our values are small
            } else {
                throw new ClassCastException();
            }
        }
    }
    
    // computes the "value" of a member, which is used for computing the fake value of any data cell having the member in its QDR.  See the comments in getValue().
    private static int getMemberValue(String memberLabel) {
        // the last 3 decimal digits of the hashcode, ignoring sign
        // TODO: The hashcode means that for NumberLayers like Year, each value is 1 more than the previous one.  So maybe use a different function.
        return Math.abs(memberLabel.hashCode()) % 1000;
    }

    // Doesn't check whether the params are in-bounds.  Caller should do that.
    private Layer getLayer(int edge, int layer) {
        return layers.get(edge).get(layer);
    }

    // *******************   CONSTRUCTOR, DATADIRECTOR METHODS, ETC.  **********************************

    public FakeDataSource() {
        layers = new ArrayList<List<Layer>>(2);
        layers.add(new ArrayList<Layer>());
        layers.add(new ArrayList<Layer>());
        init();
    }               

    /**
     * Initializes data source by clearing all state, as follows.  
     * Discards existing layers and gets fresh new layers from the factory.
     * Discards cell edits, sorts, reorders, pivots, drills, etc.
     * 
     * Call this method from constructor and from the param setters that change the shape of the PT (since
     * changing those params basically means getting a new PT).
     * 
     * memberCounts should already be set to the desired values before calling this method.
     * This method populates the member extents from those counts.
     */
    private void init() {
        populateMemberExtents();
        layerMap.clear();
        int index=0;
        for (int edge=0; edge<2; ++edge) {
            List<Layer> edgeLayers = layers.get(edge);
            edgeLayers.clear();
            int[] edgeMemberCounts = memberCounts[edge];
            
            for (int layer=0; layer<edgeMemberCounts.length; ++layer) {
                Layer layerObj = Layer.getCopyOfLayer(index++, edgeMemberCounts[layer]);
                edgeLayers.add(layerObj);
                layerMap.put(layerObj.getLabel(),layerObj);
            }
        }
        
        m_updatedValues.clear();
        sorts=null; // above code gets new layer objects with default sorting, so no need to have clearSorts() walk the layers.
    }

     // TBD: accommodate page edge too, if want to use with PFB
    @Override
    public boolean pivot(int fromEdge, int toEdge, int fromLayer, int toLayer,
                         int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException {
        checkEdge(fromEdge);
        checkEdge(toEdge);
        checkLayer(fromEdge, fromLayer);
        checkLayer(toEdge, toLayer);
        
        Layer from;

        switch (flags) {
            case PIVOT_MOVE_BEFORE:
                from = layers.get(fromEdge).remove(fromLayer);
                if (fromEdge==toEdge && fromLayer<toLayer) // then removal shifted index of TO layer
                    --toLayer;
                layers.get(toEdge).add(toLayer, from);
                break;
            case PIVOT_MOVE_AFTER:
                from = layers.get(fromEdge).remove(fromLayer);
                if (fromEdge==toEdge && fromLayer<=toLayer) // then removal shifted index of TO layer
                    --toLayer;
                layers.get(toEdge).add(toLayer+1, from);
                break;
            case PIVOT_MOVE_TO:
                if (_getLayerCount(toEdge)!=0)
                    throw new DataDirectorException("TO flag passed to pivot() when TO edge not empty", null);
                from = layers.get(fromEdge).remove(fromLayer);
                layers.get(toEdge).add(from);
                break;
            case PIVOT_SWAP:
                if (fromEdge==toEdge && fromLayer==toLayer) // the only case where the following ordering of actions doesn't do the right thing.  Since there's nothing to do in this case, just bail out.
                    return true;
                int minEdge, minLayer, maxEdge, maxLayer;
                if (fromLayer<toLayer) {
                    minEdge = fromEdge;
                    minLayer = fromLayer;
                    maxEdge = toEdge;
                    maxLayer = toLayer;
                } else {
                    minEdge = toEdge;
                    minLayer = toLayer;
                    maxEdge = fromEdge;
                    maxLayer = fromLayer;
                }
                // If we do things in this precise order, all the shifting indexes work out, regardless of whether fromEdge==toEdge.
                Layer max = layers.get(maxEdge).remove(maxLayer); // 1: remove max.
                Layer min = layers.get(minEdge).remove(minLayer); // 2: remove min.
                layers.get(minEdge).add(minLayer, max);           // 3: add max before min location.
                layers.get(maxEdge).add(maxLayer, min);           // 4: add min before max location. 
                break;
            case PIVOT_EDGES:
                if (fromEdge!=toEdge) {
                    List<Layer> colLayers = layers.remove(0);
                    layers.add(colLayers);
                } else {
                    return true;
                }
                break;
            default:
                throw new DataDirectorException("invalid flags passed to pivot()", null);
        }
        
        if (fromEdge==toEdge) 
            populateMemberCounts(fromEdge);
        else
            populateMemberCounts();
        clearSorts(); // TBD: maybe be smart about which ones it makes sense to keep
        return true;
    }
    
    // TBD: accommodate page edge too, if want to use with PFB
    @Override
    public int pivotCheck(int fromEdge, int toEdge, int fromLayer, int toLayer,
                          int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException {
        checkEdge(fromEdge);
        checkEdge(toEdge);
        checkLayer(fromEdge, fromLayer);
        checkLayer(toEdge, toLayer);
        return PIVOT_CHECK_UNKNOWN; // commented out until pivot is working: (coordinateMode) ? PIVOT_CHECK_UNKNOWN : PIVOT_CHECK_OK;
    }

    // TBD: accommodate page edge too, if want to use with PFB
    @Override
    public boolean pivotOK(int fromEdge, int toEdge, int fromLayer, int toLayer,
                           int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException {
        checkEdge(fromEdge);
        checkEdge(toEdge);
        checkLayer(fromEdge, fromLayer);
        checkLayer(toEdge, toLayer);
        return false; // commented out until pivot is working: !coordinateMode;
    }
    
    @Override
    public SortInfo[] getSorts() throws LayerOutOfRangeException, DataDirectorException {
        return sorts;
    }

    @Override
    public boolean setSorts(SortInfo[] sortInfo) throws LayerOutOfRangeException, DataDirectorException {
        // TBD: Consider this optimization:  if param.subarray(all but last element).logicallyEquals(sorts ivar) (not reference equality), then just do the one new sort.  (Does SortINfo impl equals correctly?)
        clearSorts();
        boolean retVal = true;
        
        if (sortInfo!=null) {
            List<SortInfo> sortList = Arrays.asList(sortInfo); // to enable iterator.remove(), which writes thru
            for (Iterator<SortInfo> iter = sortList.iterator(); iter.hasNext(); ) {
                boolean keep = false;
                SortInfo _sort = iter.next();
                if (_sort instanceof QDRSliceSortInfo) {
                    QDRSliceSortInfo sort = (QDRSliceSortInfo)_sort;
                    QDRInterface _qdr = sort.getQDR();
                    if (_qdr instanceof QDR) {
                        QDR qdr = (QDR)_qdr;
                        int[] direction = sort.getDirection();
                        if (direction!=null && direction.length>0) {
                            boolean ascending = direction[0]==SortInfo.ASCENDING;
                            Layer layer;
                            boolean isHeader;
                            if (qdr.isDimensionOnlyQDR()) { // header sort
                                isHeader = true;
                                String sortLayerName = qdr.getDimMemberPairs();
                                layer = layerMap.get(sortLayerName);
                            } else { // data sort
                                isHeader = false;
                                int edge = sort.getEdge();
                                layer = getLayer(edge, _getLayerCount(edge)-1); // innermost layer of edge being reordered, e.g. row edge for a column data sort
                            }
                            layer.sort(isHeader, ascending);
                            keep = true;
                        }
                    }
                }
                if (!keep) {
                    iter.remove(); // writes thru to array
                    retVal = false;
                }
            }
        }
        
        // TBD: also prune overridden sorts (not just invalid sorts) before storing.
        sorts = sortInfo; // now pruned
        return retVal;
    }

    private void clearSorts() {
        for (int edge=0; edge<2; ++edge) {
            int layerCount = _getLayerCount(edge);
            for (int layer=0; layer<layerCount; ++layer) {
                getLayer(edge, layer).sort(true, true); // our layers are sorted ascending by member label by default, so this is "clearing" any decreasing sorts or reorders.
            }
        }
        sorts = null;
    }
    
    @Override
    public Object getProperty(String name) throws DataDirectorException {
        if (DataDirector.PROP_SORTS_SUPPORTED.equals(name))
            return !coordinateMode;
        return super.getProperty(name);
    }

    // Other constructors will allow custom local data cubes to be set up,
    // and other methods will allow them to be filled with data more efficiently
    public DataDirector createDataDirector() {
        return this;
    }
  
    public CubeDataDirector createCubeDataDirector() {
        return this;
    }
  
    public RelationalDataDirector createRelationalDataDirector() {
        return this;
    }
  
    public void addDataDirectorListener(DataDirectorListener l) {
        listener = l;
        l.viewDataAvailable(new DataAvailableEvent(this, this));
    }

    public void removeDataDirectorListener(DataDirectorListener l) {
    }
  
    public Object clone() {
        return new FakeDataSource();
    }
  
  
    public DataMap getDataMap() {
        return new DataMap("");
    }

    public MetadataMap getMetadataMap(int edge, int layer) throws EdgeOutOfRangeException, LayerOutOfRangeException {
        return new MetadataMap((String)null);
    }
    

    // *******************   HELPER METHODS   **********************************
  
    // call before calling checkLayer or checkSlice
    private void checkEdge(int edge) throws EdgeOutOfRangeException {
        if (edge<0 || edge>1) throw new EdgeOutOfRangeException(edge, 1);
    }
  
    private void checkLayer(int edge, int layer) throws LayerOutOfRangeException {
        if (layer<0 || layer>=_getLayerCount(edge)) throw new LayerOutOfRangeException(layer, _getLayerCount(edge) - 1);
    }
  
    private void checkSlice(int edge, int slice) throws SliceOutOfRangeException {
        if (slice<0 || slice>=_getEdgeExtent(edge)) throw new SliceOutOfRangeException(slice, _getEdgeExtent(edge) - 1);
    }
  
    private void checkRowColumn(int row, int column) throws RowOutOfRangeException, ColumnOutOfRangeException {
        if (row<0 || row>=_getEdgeExtent(DataDirector.ROW_EDGE))
            throw new RowOutOfRangeException(row, _getEdgeExtent(DataDirector.ROW_EDGE) - 1);
        if (column<0 || column>=_getEdgeExtent(DataDirector.COLUMN_EDGE))
            throw new ColumnOutOfRangeException(column, _getEdgeExtent(DataDirector.COLUMN_EDGE) - 1);
    }
  
  
    // ********   DATAACCESS1/2/3 METHODS THAT ARE IMPLEMENTED   ***************
  
    // This version doesn't check whether the params are in-bounds.  The main version does that.
    public int getEdgeExtent(int edge) throws EdgeOutOfRangeException {
        checkEdge(edge);
        return _getEdgeExtent(edge);
    }

    private int _getEdgeExtent(int edge) {
        // "layer -1" is the root layer of the edge tree, containing the root member (the root node of the tree), which is a single member that spans the entire edge, i.e. its extent is the edge extent.
        return _getMemberExtent(edge, -1, 0);
    }

    // Note that the type param is ignored for now.  We only care about "the" value in the cell.
    public Object getValue(int row, int col, String type) throws RowOutOfRangeException, ColumnOutOfRangeException {
        checkRowColumn(row, col);
        if (coordinateMode) 
            return row + ", " + col;
        
        QDR qdr = _getValueQDR(row, col, 0); // Must use qdr keys to work with pivoting, etc.
        if (m_updatedValues.containsKey(qdr))
            return m_updatedValues.get(qdr);

        // The function used to compute return values must be a function of the value QDR, not the row/col index, to work with pivoting, etc.
        // 
        // To additionally work with data sorting in a symmetric data model like this one, this function must satisfy the following requirement: 
        // For each layer L,
        //   Let n be the number of members in L.
        //   There must exist an ordering X of the members of layer L, such that
        //     If L's members are in that order, then
        //       For each set of layers containing L,
        //         Let S be the size of that set, where S>=1.
        //         For each assignment of member values to all the other S-1 layers in the set, (i.e. for each set of S-1 key/value pairs where the S-1 layers are the keys and their members are the values)
        //           For the ordered list A of n QDR's formed by combining that assignment of values (that set of S-1 key/value pairs) with each of the n members of L in order X,
        //           the data values of those QDR's are in non-decreasing order. 
        //
        // E.g.:
        // 
        // If you do a column data sort on column C,
        //   And the innermost row header layer is Year (so L=Year),
        //   And the set of Year members repeats R times in the PT due to all the parent layers of Year (i.e. R unique parent QDR's are formed by the parent layers of Year on the row edge),
        // Then
        //   For each of the R occurrences of the Year members (i.e. for each of the R parent QDR's),
        //     The n years must be in the same order (order X) (because our model is symmetric),
        //     and that order must be consistent with a valid data sort, i.e. the data values in column C for the years within that parent QDR must be in non-decreasing order for an ascending 
        //     sort, and in non-increasing order for a descending sort.
        //
        // A simple approach is this:  for each layer L, assign a number to each of the n members in that layer, e.g. the hashcode of the member label. The data value
        // for any data cell is the sum of the numbers of the members in its QDR.
        
        int value = 0;
        for (Object entryObj : qdr.entrySet()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) entryObj;
            value += getMemberValue(entry.getValue()); // TODO: consider looking up the member value in the member object rather than recomputing it
        }
        return value;
    }

    // Note that the type param is ignored for now.  We only care about "the" value in the cell.
    // TODO: cell editing (setValue) and data sorting can't be enabled at the same time.
    public boolean setValue(Object data, int row, int col, String type) throws RowOutOfRangeException, ColumnOutOfRangeException {
        if (!coordinateMode) {
            checkRowColumn(row, col);
            QDR key = _getValueQDR(row, col, 0); // Must use qdr keys to work with pivoting, etc.
            m_updatedValues.put(key,data);
    
            return true;
        } else {
            // Must return true iff we set the value.  examples of when we hit this false case:
            // - if we only honor the set request if DataMap.DATA_VALUE.equals(type).
            // - if cell editing disabled due to data sort mode or coordinate mode
            return false;
        }
    }
  
    public int getLayerCount(int edge) throws EdgeOutOfRangeException {
        checkEdge(edge);
        return _getLayerCount(edge);
    }

    // Unlike the main version, this version throws an *unchecked* (ArrayIndexOutOfBoundsException)
    // exception if the param is out of bounds.
    private int _getLayerCount(int edge) {
        return memberCounts[edge].length;
    }

    public int getSliceMemberCount(int edge, int slice) throws EdgeOutOfRangeException, SliceOutOfRangeException {
        checkEdge(edge);
        checkSlice(edge, slice);
        return _getLayerCount(edge);
    }

    public int getSliceOutlineLayer(int edge, int slice) throws EdgeOutOfRangeException, SliceOutOfRangeException {
        checkEdge(edge);
        checkSlice(edge, slice);
        return 0;
    }

    public int getMemberDepth(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        checkSlice(edge, slice);
        return _getMemberDepth(edge, layer, slice);
    }

    // This version doesn't check whether the params are in-bounds.  The main version does that.  In fact, this version supports layer -1, the root layer (returns 1, which is correct).
    private int _getMemberDepth(int edge, int layer, int slice) {
        return 1;
    }

    public int getMemberStartLayer(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        checkSlice(edge, slice);
        return _getMemberStartLayer(edge, layer, slice);
    }

    // This version doesn't check whether the params are in-bounds.  The main version does that.  In fact, this version supports layer -1, the root layer (returns -1, which is correct).
    private int _getMemberStartLayer(int edge, int layer, int slice) {
        return layer;
    }

    public int getMemberLogicalLayer(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        checkSlice(edge, slice);
        return layer;
    }

    // Note that the type param is ignored for now.  We only care about the ID and label, and we use the same string for both.
    public Object getLayerMetadata(int edge, int layer, String type) throws EdgeOutOfRangeException, LayerOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        return _getLayerMetadata(edge, layer, type);
    }

    // This version doesn't check whether the params are in-bounds.  The main version does that.
    // This version returns a String, not an Object.
    // Note that the type param is ignored for now.  We only care about the ID and label, and we use the same string for both.
    private String _getLayerMetadata(int edge, int layer, String type) {
        return getLayer(edge, layer).getLabel();
    }

    public int getMemberExtent(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        checkSlice(edge, slice);
        return _getMemberExtent(edge, layer, slice);
    }
  
    // This version doesn't check whether the params are in-bounds.  The main version does that.  In fact, internal callers expect this version to support layer -1, the root layer (returns the edge extent, which is correct).
    // 
    private int _getMemberExtent(int edge, int layer, int slice) {
        return memberExtents[edge][layer+1];
        
        // non-optimized version:
//        int[] edgeMemberCounts = memberCounts[edge];
//        int layerCount = _getLayerCount(edge);
//        int memberExtent = 1;
//
//        for (int i=layer+1; i<layerCount; ++i) {
//            memberExtent *= edgeMemberCounts[i];
//        }
//        
//        return memberExtent;
    }

    public int getMemberStartSlice(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        checkSlice(edge, slice);
        return _getMemberStartSlice(edge, layer, slice);
    }

    // This version doesn't check whether the params are in-bounds.  The main version does that.  In fact, internal callers expect this version to support layer -1, the root layer (if other params are in-bounds, returns 0, which is correct).
    private int _getMemberStartSlice(int edge, int layer, int slice) {
        return slice - (slice % _getMemberExtent(edge, layer, slice));
    }

    public Object getMemberMetadata(int edge, int layer, int slice, String type) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        checkSlice(edge, slice);
        
        return _getMemberMetadata(edge, layer, slice, type) ;
    }

    // This version doesn't check whether the params are in-bounds.  The main version does that.
    private Object _getMemberMetadata(int edge, int layer, int slice, String type) {
        if (MetadataMap.METADATA_DRILLSTATE.equals(type))
            return DataDirector.DRILLSTATE_NOT_DRILLABLE;
        if (MetadataMap.METADATA_ISTOTAL.equals(type))
            return Boolean.valueOf(false);
        if (MetadataMap.METADATA_INDENT.equals(type))
            return Integer.valueOf(0);
        if (MetadataMap.METADATA_EXTENT.equals(type))
            return _getEdgeExtent(edge);
        if (MetadataMap.METADATA_START.equals(type))
            return _getMemberStartSlice(edge, layer, slice);
        
        if (coordinateMode) {
            int startLayer = _getMemberStartLayer(edge, layer, slice);
            int startSlice = _getMemberStartSlice(edge, layer, slice);
            switch (edge) {
                case DataDirector.COLUMN_EDGE:
                    return startLayer + ", " + startSlice;
                case DataDirector.ROW_EDGE:
                    return startSlice + ", " + startLayer;
                default:
                    return "Error";
            }
        }
               
        int parentStartSlice = _getMemberStartSlice(edge, layer-1, slice); // works even when layer==0
        int sliceWithinParent = slice - parentStartSlice; // equals (slice % parentMemberExtent)
        int memberExtent = _getMemberExtent(edge, layer, slice);
        int memberWithinParent = sliceWithinParent / memberExtent; // integer division drops remainder.  This relies on the fact that all members of a layer have the same extent.

        // Return the same String for both the member ID and member label:
        return getLayer(edge, layer).getMemberLabel(memberWithinParent);
    }

    // TODO: make both of these methods throw SOORE when slice is OOR.
    public boolean isFetched(int[] startSlice, int[] endSlice) throws SliceOutOfRangeException {
        return true;
    }

    public boolean forceFetch(int[] startSlice, int[] endSlice) throws SliceOutOfRangeException {
        return true;
    }

    public boolean isMemberExtentComplete(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        checkSlice(edge, slice);
        return true;
    }

    public int getEdgeCount() {
        return 2; // only support DataDirector.COLUMN_EDGE and DataDirector.ROW_EDGE
    }
  
    // ****************   UNIMPLEMENTED DATAACCESS METHODS   *******************
      
    public Object getSliceLabel(int edge, int slice, String type) throws EdgeOutOfRangeException, SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public int getEdgeCurrentSlice(int edge) throws EdgeOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public int[] getEdgeCurrentHPos(int edge) throws EdgeOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }  
  
    /**
     * Per the DataAccess contract, this method returns:
     *   {-1, -1} if qdr is empty or invalid
     *   {c,  -1} if qdr is a column QDR
     *   {-1,  r} if qdr is a row QDR
     *   {c,   r} if qdr is a databody QDR
     * where r and c are the row number and column number of the QDR
     */
    public int[] getSlicesFromQDR(QDRInterface qdr, int[] startSlices, int[] endSlices) {
        int[] retVal = {-1, -1};
        int entriesFound = 0;

        for (int edge=0; edge<2; ++edge) {
            boolean edgeParticipates = false;
            int slice = 0; // at start of each iteration of the inner loop, slice is the startSlice of the member in the previous layer.  For the first iteration, the "previous layer" is layer -1, the root layer, whose one-and-only member has startSlice 0.  At end of inner loop, if edgeParticipates, slice is the startSlice for that edge.
            int layerCount = _getLayerCount(edge);
            for (int layer=0; layer<layerCount; layer+=_getMemberDepth(edge,layer,slice)) {
                String layerLabel = _getLayerMetadata(edge, layer, LayerMetadataMap.LAYER_METADATA_NAME);
                String memberLabel = qdr.get(layerLabel).toString();
                if (memberLabel != null) { // layer present in QDR
                    int memberIndex = getLayer(edge, layer).getMemberIndex(memberLabel);
                    if (memberIndex != -1) { // the QDR specifies a valid member for the layer
                        ++entriesFound;
                        edgeParticipates = true;
                        slice += memberIndex * _getMemberExtent(edge, layer, slice); // startSlice = parentStartSlice + (memberIndex*memberExtent).  Both the overall logic and the slice arg to _gME rely on fact that all members of a layer have the same extent.
                    } else { // the QDR specifies an invalid member for the layer
                        return new int[]{-1,-1};
                    }
                } else { // layer not present in QDR, so we're done with this edge
                    break;
                }
            }
            if (edgeParticipates) 
                retVal[edge] = slice;
        }

        // if QDR has entries not on either edge, or on an edge but after a layer of that edge that is skipped in the QDR, then it isn't valid.
        if (qdr.size() > entriesFound) {
            return new int[] {-1, -1};
        } else {
            return retVal;
        }
    }
  
    public int getMemberSiblingCount(int edge, int[] hPos, int memberLayer) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public Object getMemberMetadata(int edge, int[] hPos, int memberLayer, int hIndex, String type) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public int[] getMemberHPos(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public int[] getFirstHPos(int edge) throws EdgeOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public int[] getLastHPos(int edge) throws EdgeOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public int[] getPrevHPos(int edge, int[] hPos) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public int[] getNextHPos(int edge, int[] hPos) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public int findMember(int edge, int[] hPos, int memberLayer, String s, String type, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public void release() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public void startGroupEdit() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public void endGroupEdit() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public boolean submitChanges() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public boolean undoEdit() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public boolean redoEdit() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public boolean dropChanges() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public void setAutoSubmit(boolean bValue) {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public boolean isAutoSubmit() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public Vector getQDRoverrideCollection() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    // ****************   UNIMPLEMENTED DATAACCESS2 METHODS   ******************
  
    public List getUniqueMemberMetadata(int edge, int layer, String[] types, int start, int count) throws EdgeOutOfRangeException, LayerOutOfRangeException{
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public List getUniqueMemberMetadata(String layerName, String[] types, int start, int count) throws LayerOutOfRangeException, SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public List getCorrespondingMemberMetadata(int edge, int layer, String[] values, String[] types, boolean inDataAccess) throws EdgeOutOfRangeException, LayerOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public List getCorrespondingMemberMetadata(String layerName, String[] values, String[] types, boolean inDataAccess) throws LayerOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public List getUniqueDataValues(String name, String[] types, int start, int count) throws LayerOutOfRangeException, SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }
    public boolean allSlicesFetched(int edge) throws EdgeOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    // ****************   UNIMPLEMENTED DATAACCESS3 METHODS   ******************
  
    public boolean isFetched(int[] startSlice, int[] endSlice, int flag) throws SliceOutOfRangeException {
        return true; //
    }
    public boolean forceFetch(int[] startSlice, int[] endSlice, int flag) throws SliceOutOfRangeException {

      return true; //
    } 

    public DataMap getSupportedDataMap()
    { 
        String supported[] = {DataMap.DATA_UNFORMATTED};
        return new DataMap(supported);
    }
  
    public MetadataMap getSupportedMetadataMap()
    {
        String supported[] = {MetadataMap.METADATA_LONGLABEL,
                              MetadataMap.METADATA_DRILLSTATE};
        return new MetadataMap(supported);
    }
  
    public LayerMetadataMap getSupportedLayerMetadataMap()
    {
        String supported[] = {LayerMetadataMap.LAYER_METADATA_LONGLABEL};
        return new LayerMetadataMap(supported);
    }
  
    // Note that flags param is ignored.
    public QDR getMemberQDR(int edge, int layer, int slice, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException {
         checkEdge(edge);
         checkLayer(edge, layer);
         checkSlice(edge, slice);

         return _getMemberQDR(edge, layer, slice, flags);
     }

    // This version doesn't check whether the params are in-bounds.  The main version does that.  In fact, this version supports layer -1, the root layer (returns empty QDR, which is correct).
    // Note that flags param is ignored.
    private QDR _getMemberQDR(int edge, int layer, int slice, int flags) {
        QDR qdr = new QDR();

        for (int lyr=0; lyr<=layer; lyr+=_getMemberDepth(edge, lyr, slice)) { // note <=
            String layerName = _getLayerMetadata(edge, lyr, LayerMetadataMap.LAYER_METADATA_NAME);
            String memberName = _getMemberMetadata(edge, lyr, slice, MetadataMap.METADATA_LONGLABEL).toString();
            qdr.addDimMemberPair(layerName, memberName);
        }
        return qdr;      
    }

    // Note that flags param is ignored.
    public QDR getValueQDR(int row, int column, int flags) throws RowOutOfRangeException, ColumnOutOfRangeException {
        checkRowColumn(row, column);
        return _getValueQDR(row, column, flags);
    }
    
    // This version doesn't check whether the params are in-bounds.  The main version does that.
    // Note that flags param is ignored.
    private QDR _getValueQDR(int row, int column, int flags) {
        QDR rowQdr = _getSliceQDR(DataDirector.ROW_EDGE, row, flags);
        QDR colQdr = _getSliceQDR(DataDirector.COLUMN_EDGE, column, flags);
      
        // combine the two slice QDR's into a single QDR.  We have to do it manually since QDR doesn't seem to support any useful bulk operations like putAll().
        for (Object entryObj : colQdr.entrySet()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) entryObj;
            rowQdr.addDimMemberPair(entry.getKey(), entry.getValue());
        }

        return rowQdr; // now contains the column entries too
    }
  
    // Note that flags param is ignored.
    public QDR getSliceQDR(int edge, int slice, int flags) throws EdgeOutOfRangeException, SliceOutOfRangeException {
        checkEdge(edge);
        checkSlice(edge, slice);
        return _getSliceQDR(edge, slice, flags);
    }

    // This version doesn't check whether the params are in-bounds.  The main version does that.
    // Note that flags param is ignored.
    private QDR _getSliceQDR(int edge, int slice, int flags) {
        return _getMemberQDR(edge, _getLayerCount(edge)-1, slice, flags);
    }

  
    protected DataDirectorListener listener;
    private HashMap<QDR,Object> m_updatedValues = new HashMap<QDR,Object>();
    private List<List<Layer>> layers;
    private Map<String,Layer> layerMap = new HashMap<String,Layer>();
    private SortInfo[] sorts;
}

