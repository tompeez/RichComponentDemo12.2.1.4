/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/GraphSharedDataSource.java /main/3 2011/07/01 16:45:25 amdai Exp $ */

/* Copyright (c) 2009, 2011, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    Generates random data samples of different sizes for Graph. Here's how it works:


    1.  In EL ("value=..."), start out with "graphData."
    2.  Next, you can chain an arbitrary number of keyword arguments by using the
        special square bracket syntax, e.g.,

            randomData.keywordArg[value].keywordArg2[value2]....

        Although you can specify any keyword-value pair to be passed along to the
        data generator, there are five "special" keywords that are preprocessed 
        specially. You can specify any of them in any order. If you exclude any 
        of them, sensible defaults are used. 
        
        --  rows. Expects an int. The number of rows to generate. Default is 2
        --  cols. Expects an int. The number of columns to generate. Default is 6
        --  seed. Expects a long. The number to seed the RNG with. This allows 
            predictable, "static" random data to be generated. The magic value 
            "-1" means "randomize"--each run will produce different results. 
            Default is -1.
        --  rowLabels. Accepts three argument types:
            --  null. A magic value that means "auto-incrementing integer"
                that starts at 1.
            --  a String. Treated as a printf/String.format()-style format string.
                An integer, representing an auto-incrementing value starting from
                1, will be passed in as the first parameter to String.format().
                "Number %d", for example, might produce the output "Number 4".
            --  an Iterator or Iterable. Used as a generator to create labels in
                order. The first iterator.next() output will be used as the first
                label, etc..
            The default for rowLabels is the format string "Series %d".
        --  colLabels. Exactly like rowLabels, except the default is null.

        All the keyword arguments are passed along to the data generator, however,
        and specific data generators may allow or require other configuration
        options than the five listed above.

    3.  Lastly, you specify what type of data you want. Right now, the class 
        supports six types:
        --  continuous. Each point varies with some normally distributed delta
        --  random. Completely random data
        --  gaussian. Normally distributed data w/ random mean, standard deviation
        --  time. Same as "continuous," but with a time axis
        --  correlatedPair. Pairs of correlated data. Useful for scatter plots
        --  correlatedTriple. Like correlatedPair, but with a Z coordinate. 
            Useful for bubble plots

        The data generated is cached, so a request for data with the same keyword
        arguments and the same data type will retrieve exactly the same data.
        The caching is performed independent of the order of the arguments.

    EXAMPLES:

        graphData.random
            Produces completely random data with 2 rows and 6 columns.

        graphData.cols[21].seed[123456789].correlatedTriple
            Produces static correlated triples with 2 rows and 21 columns
            (produces 7 data points on a bubble graph)

        graphData.seed[999].rows[5].cols[3].time
            Produces static time data with 5 rows and 3 columns.

        graphData.rows[4].rowLabels['Custom Label %d'].cols[100].       (cont'd)
        colLabels[time.start['July 31, 1980'].daily].continuous
            Produces continuous data with 4 rows and 100 columns. The row labels
            will be "Custom Label 1" to "Custom Label 4". The column labels will
            be the dates July 31, 1980 to November 7, 1980. 
            
            Note: The "time..." functionality is provided by TimeIterator, which
            extends Iterator and generates sequential dates with a natural EL sytnax


   MODIFIED    (MM/DD/YY)
    carjacks    07/21/09 - Creation
 */

package oracle.adfdemo.view.feature.rich.graph;

import java.util.Date;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import oracle.adf.view.faces.bi.model.GraphDataModel;
import oracle.dss.dataView.LocalXMLDataSource;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Arrays;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/GraphSharedDataSource.java /main/1 2009/08/05 14:11:50 srkalyan Exp $
 *  @author  carjacks
 *  @since   release specific (what release of product did this appear in)
 */
public class GraphSharedDataSource implements Map{
    
    // Hash of all the keyword arguments (aka named parameters)
    private HashMap<String, Object> m_params;    
    // Stores the previous parameter specified
    private String m_paramBuffer = null;
    
    // A cache of the generated data
    private HashMap<String, GraphDataModel> m_cache = new HashMap<String, GraphDataModel>();
    
    public GraphSharedDataSource(){
        m_params = defaultParams();
    }
    
    // This function intercepts and processes all EL method calls
    public Object get(Object o){
        if(o instanceof String && m_paramBuffer == null){
            String s = (String) o;
            // first check to see if this is one of the special "stop words"
            if(
                s.equals("continuous") || 
                s.equals("random") || 
                s.equals("gaussian") || 
                s.equals("time") || 
                s.equals("correlatedPairs") || 
                s.equals("correlatedTriples")
            ){
                
                // Does a cached version of what we're looking for exist?
                String hashKey = getParamsHash(m_params, s);
                if(m_cache.containsKey(hashKey)){
                    cleanUp();
                    return m_cache.get(hashKey);
                }
                
                // mangle the params (handles a bunch of the common logic)
                mangleParams();
                
                GraphDataModel gdm = null;
                
                
                // Call the appropriate data generator
                
                // ########## ACHTUNG! ##########
                // If you're adding more data generators here, be sure to add
                // them to the "stop words" check above!
                // ##############################
                if(s.equals("continuous")){ 
                    gdm = GraphSharedDataSource.getContinuous(m_params);
                }else if(s.equals("random")){
                    gdm = GraphSharedDataSource.getRandom(m_params);
                }else if(s.equals("gaussian")){
                    gdm = GraphSharedDataSource.getGaussian(m_params);
                }else if(s.equals("time")){
                    gdm = GraphSharedDataSource.getTime(m_params);
                }else if(s.equals("correlatedPairs")){
                    gdm = GraphSharedDataSource.getCorrelatedPairs(m_params);
                }else if(s.equals("correlatedTriples")){
                    gdm = GraphSharedDataSource.getCorrelatedTriples(m_params);
                }
                
                // This check shouldn't be needed, but it makes the compiler happy
                if(gdm != null) m_cache.put(hashKey, gdm);
                cleanUp();
                
                return gdm;
            }else{ // Otherwise it's a keyword arg
                m_paramBuffer = s;
            }
        }else if(m_paramBuffer != null){
            // Add the keyword-value pair to the params HashMap
            m_params.put(m_paramBuffer, o);
            m_paramBuffer = null;
        }
        // The missing else case means someone did something unallowed in EL.
        // Fail silently and recover gracefully.
        
        return this;
    }
    
    private HashMap<String, Object> defaultParams(){
        HashMap<String, Object> out = new HashMap<String, Object>();
        out.put("rows", 2L);
        out.put("rowLabels", "Series %d");
        out.put("cols", 6L);
        out.put("colLabels", null);
        out.put("seed", -1L);
        
        return out;
    }
    
    // Mangle the parameters hash before it's sent to the data generator.
    // Allows us to factor out a bunch of the common logic, such as dealing with
    // labels and seeds and stuff.
    private void mangleParams(){
        // Convert to ints to save us casting headaches later
        int rows = ((Long) m_params.get("rows")).intValue();
        int cols = ((Long) m_params.get("cols")).intValue();
        m_params.put("rows", rows);
        m_params.put("cols", cols);
        
        // Row and Column labels
        makeLabels("rowLabels", rows);
        makeLabels("colLabels", cols);
        
        // Random Number Generator
        long seed = (Long) m_params.get("seed");
        if(seed == -1){ // -1 is the special "make something up for me" value
            m_params.put("random", new Random( (long) Math.floor(Long.MAX_VALUE * 0.8) ));
        }else{
            m_params.put("random", new Random(seed));
        }
    }
    
    private void makeLabels(String type, int howMany){
        Object[] labels = new Object[howMany];
        Object labelGenerator = m_params.get(type);
        if(labelGenerator == null){ // null is a magic param for auto-incrementing integer
            for(int i = 0; i < howMany; i++){
                labels[i] = i+1;
            }
        }else if(labelGenerator instanceof String){ // Strings are treated as format strings
            String s = (String) labelGenerator;
            for(int i = 0; i < howMany; i++){
                labels[i] = String.format(s, i+1);
            }
        }else if(labelGenerator instanceof Iterator){ // Iterators and Iterables are iterated over
            Iterator it = ((Iterator) labelGenerator);
            for(int i = 0; i < howMany; i++){
                labels[i] = it.next();
            }
        }else if(labelGenerator instanceof Iterable){
            Iterator it = ((Iterable) labelGenerator).iterator();
            for(int i = 0; i < howMany; i++){
                labels[i] = it.next();
            }
        }else{ // Else the label is just the passed parameter
            for(int i = 0; i < howMany; i++){
                labels[i] = labelGenerator;
            }
        }
        m_params.put(type, labels);
    }
    
    private void cleanUp(){
        m_params.clear();
        m_params = defaultParams();
        m_paramBuffer = null;
    }
    
    // Allows caching to be done independently of the order of the parameters
    private String getParamsHash(HashMap<String, Object> params, String stopWord){
        String[] keys = params.keySet().toArray(new String[1]);
        Arrays.sort(keys);
        StringBuffer b = new StringBuffer(stopWord + ":");
        for(String key : keys){
            b.append("[");
            b.append(key);
            b.append("=");
            b.append(params.get(key));
            b.append("]");
        }
        return b.toString();
    }
    
    ///// Data Generation methods /////
    
    /*
     * Continuous
     * 
     * A dataset in which each point only differs from the previous point by a
     * small amount.
     */
    public static GraphDataModel getContinuous(HashMap<String, Object> params){
        int rowSize = (Integer) params.get("rows");
        int colSize = (Integer) params.get("cols");
        Float[][] data = new Float[colSize][rowSize];
        
        Random rand = (Random) params.get("random");
        
        for(int i = 0; i < rowSize; i++){
            float nextVal = rand.nextFloat() * 100;
            for(int j = 0; j < colSize; j++){
                // Set the point to the previous one plus some normally distributed delta
                data[j][i] = nextVal;
                nextVal += rand.nextGaussian() * 10;
            }
        }
        return new GraphDataModel(new LocalXMLDataSource((Object[]) params.get("colLabels"), (Object[]) params.get("rowLabels"), data));
    }
    
    /*
     * Random
     * 
     * A completely random dataset
     */
    public static GraphDataModel getRandom(HashMap<String, Object> params){
        int rowSize = (Integer) params.get("rows");
        int colSize = (Integer) params.get("cols");
        Float[][] data = new Float[colSize][rowSize];
        
        Random rand = (Random) params.get("random");
        
        for(int i = 0; i < rowSize; i++){
            for(int j = 0; j < colSize; j++){
                data[j][i] = rand.nextFloat() * 100;
            }
        }
        return new GraphDataModel(new LocalXMLDataSource((Object[]) params.get("colLabels"), (Object[]) params.get("rowLabels"), data));
    }
    
    /*
     * Gaussian (Normally distributed)
     * 
     * A dataset that is normally distributed about some random mean.
     */
    public static GraphDataModel getGaussian(HashMap<String, Object> params){
        int rowSize = (Integer) params.get("rows");
        int colSize = (Integer) params.get("cols");
        Float[][] data = new Float[colSize][rowSize];
        
        Random rand = (Random) params.get("random");
        
        for(int i = 0; i < rowSize; i++){
            float mean   = rand.nextFloat() * 100;
            float stddev = 30 * (float) (rand.nextFloat() + .5);
            for(int j = 0; j < colSize; j++){
                data[j][i] = mean + (float)rand.nextGaussian() * stddev;
            }
        }
        return new GraphDataModel(new LocalXMLDataSource((Object[]) params.get("colLabels"), (Object[]) params.get("rowLabels"), data));
    }
    
    /*
     * Time
     * 
     * A dataset for irregular time axes, similar to continuous
     */
    public static GraphDataModel getTime(HashMap<String, Object> params){
        int rowSize = (Integer) params.get("rows");
        int colSize = (Integer) params.get("cols");
        Object[][] data = new Object[colSize][rowSize];
        
        Random rand = (Random) params.get("random");
        
        for(int i = 0; i < rowSize; i++){
            float nextVal = rand.nextFloat() * 100;
            long  nextTime = 1000000000000L + (long) (rand.nextFloat() * 365 * 60*60*24*1000) /* 365 days */;
            for(int j = 0; j < colSize; j++){
                if(j % 2 == 0){
                    data[j][i] = new Date(nextTime); // X-coord
                    nextTime += (long) (rand.nextFloat() * 14 * 60*60*24*1000) /* 14 days */;
                }else{
                    // Set the point to the previous one plus some normally distributed delta
                    data[j][i] = nextVal; // Y-coord
                    nextVal += rand.nextGaussian() * 10;
                }
            }
        }
        return new GraphDataModel(new LocalXMLDataSource((Object[]) params.get("colLabels"), (Object[]) params.get("rowLabels"), data));
    }
    
    /*
     * Correlated Pairs
     * 
     * A dataset where each pair of columns is correlated. Especially useful for
     * scatter plots
     */
    public static GraphDataModel getCorrelatedPairs(HashMap<String, Object> params){
        int rowSize = (Integer) params.get("rows");
        int colSize = (Integer) params.get("cols");
        Float[][] data = new Float[colSize][rowSize];
        
        Random rand = (Random) params.get("random");
        
        for(int i = 0; i < rowSize; i++){
            float m = rand.nextFloat() * 2 * (rand.nextFloat() > .5 ? -1 : 1);
            float b = rand.nextInt(61) - 30; // 61 because rand.nextInt is exclusive on the upper bound
            if(m < 0) b += 80; // Adjust for downward-sloping data--else it'll be too negative
            
            for(int j = 0; j < colSize; j++){
                // Randomly pick a point [0-100], compute y=mx+b, then take a normal
                // distribution around it (nextGaussian = normal distrib.)
                if(j % 2 == 0){
                    data[j][i] = rand.nextFloat() * 100; // X-coord
                }else{
                    data[j][i] = data[j-1][i] * m + b + (float) rand.nextGaussian() * 25; // Y-coord
                }
            }
        }
        return new GraphDataModel(new LocalXMLDataSource((Object[]) params.get("colLabels"), (Object[]) params.get("rowLabels"), data));
    }
    
    /*
     * Correlated Triples
     * 
     * A dataset similar to a correlated pair, with an extra column (Z-coord) 
     * in between. Useful for bubble plots
     */
    public static GraphDataModel getCorrelatedTriples(HashMap<String, Object> params){
        int rowSize = (Integer) params.get("rows");
        int colSize = (Integer) params.get("cols");
        Float[][] data = new Float[colSize][rowSize];
        
        Random rand = (Random) params.get("random");
        
        for(int i = 0; i < rowSize; i++){
            float m = rand.nextFloat() * 2 * (rand.nextFloat() > .5 ? -1 : 1);
            int b = rand.nextInt(61) - 30; // 61 because rand.nextInt is exclusive on the upper bound
            if(m < 0) b += 80; // Adjust for downward-sloping data--else it'll be too negative
            
            for(int j = 0; j < colSize; j++){
                // Randomly pick a point [0-100], compute y=mx+b, then take a normal
                // distribution around it (nextGaussian = normal distrib.)
                if(j % 3 == 0){
                    data[j][i] = rand.nextFloat() * 100; // X-coord
                }else if(j % 3 == 1){
                    data[j][i] = data[j-1][i] * m + b + (float) rand.nextGaussian() * 25; // Y-coord
                }else{
                    data[j][i] = (float) Math.abs(rand.nextGaussian()) * 10; // Z-coord
                }
            }
        }
        return new GraphDataModel(new LocalXMLDataSource((Object[]) params.get("colLabels"), (Object[]) params.get("rowLabels"), data));
    }
    
    ///// Dummy Map implementation methods /////
    public int size(){return 0;}
    public boolean isEmpty(){return false;}
    public boolean containsKey(Object key){return true;} // This one is special--must return true in order for EL hack to work
    public boolean containsValue(Object value){return false;}
    public Object put(Object key, Object value){return null;}
    public Object remove(Object key) {return null;}
    public void putAll(Map m){}
    public void clear(){}
    public Set keySet(){return Collections.emptySet();}
    public Collection values(){return Collections.emptySet();}
    public Set entrySet() {return Collections.emptySet();}
}