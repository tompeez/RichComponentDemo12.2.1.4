/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/AnimationBase.java /main/4 2014/01/27 15:59:32 mguirgui Exp $ */

/* Copyright (c) 2009, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hzhang      01/08/09 - Creation
 */

package oracle.adfdemo.view.feature.rich.graph;

import java.util.ArrayList;
import java.util.Random;

import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.model.DataModel;
import oracle.adf.view.faces.bi.model.GraphDataModel;

import oracle.dss.dataView.LocalXMLDataSource;

/**
 *  Generates data for the Graph Animation Demo pages.  The implementation of this class is not
 *  important, as it only generates the data that would usually come from a data control.
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/AnimationBase.java /main/4 2014/01/27 15:59:32 mguirgui Exp $
 *  @author  hzhang
 *  @since   release specific (what release of product did this appear in)
 */
public class AnimationBase {
    final int m_groupsMultiple;

    DataModel m_data;
    ArrayList<String> m_colLabels;
    ArrayList<String> m_rowLabels;
    ArrayList<ArrayList<Double>> m_values;
    int m_numGroups; // The number of unique groups that have been seen in this data
    int m_numSeries; // The number of unique series that have been seen in this data
    Random m_random;

    AnimationBase(int numSeries, int numGroups, int groupsMultiple,
                  int randomSeed) {
        m_numGroups = numGroups;
        m_numSeries = numSeries;
        m_groupsMultiple = groupsMultiple;
        m_random = new Random(randomSeed);

        m_colLabels = new ArrayList<String>();
        m_rowLabels = new ArrayList<String>();
        m_values = new ArrayList<ArrayList<Double>>();
        m_data =
                init(m_colLabels, m_rowLabels, m_values, numSeries, numGroups, m_random);
    }

    public DataModel getDataModel() {
        return m_data;
    }

    public void update(ActionEvent actionEvent) {
        int numGroups = m_colLabels.size() / m_groupsMultiple;
        int group = m_random.nextInt(numGroups) * m_groupsMultiple;
        int series = m_random.nextInt(m_rowLabels.size());
        // Update the data values of the group
        for (int i = 0; i < m_groupsMultiple; i++) {
            ArrayList<Double> list = m_values.get(group + i);
            list.remove(series);
            list.add(series, generateRandomValue(m_random));
        }

        // Update the data model
        m_data = createDataSource(m_colLabels, m_rowLabels, m_values);
    }

    public void insertGroups(ActionEvent actionEvent) {
        m_data =
                insertGroups(m_colLabels, m_rowLabels, m_values, m_numGroups, m_random,
                             m_groupsMultiple);
        m_numGroups += m_groupsMultiple;
    }

    public void deleteGroups(ActionEvent actionEvent) {
        m_data =
                deleteGroups(m_colLabels, m_rowLabels, m_values, m_random, m_groupsMultiple);
        m_numGroups -= m_groupsMultiple;
    }

    public void insertSeries(ActionEvent actionEvent) {
        m_data =
                insertSeries(m_colLabels, m_rowLabels, m_values, m_numSeries, m_random);
        m_numSeries++;
    }

    public void deleteSeries(ActionEvent actionEvent) {
        m_data = deleteSeries(m_colLabels, m_rowLabels, m_values, m_random);
        m_numSeries--;
    }

    /**
     * Inserts series into the given data set.
     * @param colLabels the ArrayList of Group labels
     * @param rowLabels the ArrayList of Series labels
     * @param values the table of data values
     * @param numSeries the initial number of series
     * @param numGroups the initial number of _groupsOptions
     * @param random the random number generator to be used
     */
    private static DataModel init(ArrayList<String> colLabels,
                                  ArrayList<String> rowLabels,
                                  ArrayList<ArrayList<Double>> values,
                                  int numSeries, int numGroups,
                                  Random random) {
        for (int i = 0; i < numSeries; i++)
            rowLabels.add("Series " + (i + 1));

        for (int i = 0; i < numGroups; i++) {
            colLabels.add("Group " + (i + 1));
            values.add(new ArrayList<Double>());
        }

        for (int i = 0; i < numGroups; i++) {
            ArrayList<Double> list = values.get(i);
            for (int j = 0; j < numSeries; j++) {
                list.add(generateRandomValue(random));
            }
        }
        return createDataSource(colLabels, rowLabels, values);
    }

    /**
     * Inserts _groupsOptions into the given data set.  Groups are inserted based on a multiple, which is used
     * for scatter and bubble graphs.
     * @param colLabels the ArrayList of Group labels
     * @param rowLabels the ArrayList of Series labels
     * @param values the table of data values
     * @param nextGroupID the id of the next group to be inserted
     * @param random the random number generator to be used
     * @param groupMultiple the multiple that the _groupsOptions should be inserted in
     */
    private static DataModel insertGroups(ArrayList<String> colLabels,
                                          ArrayList<String> rowLabels,
                                          ArrayList<ArrayList<Double>> values,
                                          int nextGroupID, Random random,
                                          int groupMultiple) {
        int numSeries = rowLabels.size();
        int numGroups = colLabels.size() / groupMultiple;
        int groups = random.nextInt(numGroups) * groupMultiple;

        // Create the new group and insert
        for (int i = 0; i < groupMultiple; i++) {
            String groupStr = "Group " + (nextGroupID + i + 1);
            colLabels.add(groups + i, groupStr);
            ArrayList<Double> list = new ArrayList<Double>();
            for (int j = 0; j < numSeries; j++)
                list.add(generateRandomValue(random));
            values.add(groups, list);
        }
        // Update the data model
        return createDataSource(colLabels, rowLabels, values);
    }

    /**
     * Deletes _groupsOptions from the given data set.  Groups are deleted based on a multiple, which is used
     * for scatter and bubble graphs.
     * @param colLabels the ArrayList of Group labels
     * @param rowLabels the ArrayList of Series labels
     * @param values the table of data values
     * @param random the random number generator to be used
     * @param groupMultiple the multiple that the _groupsOptions should be deleted in
     */
    private static DataModel deleteGroups(ArrayList<String> colLabels,
                                          ArrayList<String> rowLabels,
                                          ArrayList<ArrayList<Double>> values,
                                          Random random, int groupMultiple) {
        int numGroups = colLabels.size() / groupMultiple;
        int groups = random.nextInt(numGroups) * groupMultiple;

        // Don't remove the last groups
        if (numGroups <= 1)
            return createDataSource(colLabels, rowLabels, values);

        // Remove the groups
        for (int i = 0; i < groupMultiple; i++) {
            // We do not increment here since the deleted group effectively increments the index
            colLabels.remove(groups);
            values.remove(groups);
        }
        return createDataSource(colLabels, rowLabels, values);
    }

    /**
     * Inserts series into the given data set.
     * @param colLabels the ArrayList of Group labels
     * @param rowLabels the ArrayList of Series labels
     * @param values the table of data values
     * @param nextSeriesID the id of the next series to be inserted
     * @param random the random number generator to be used
     */
    private static DataModel insertSeries(ArrayList<String> colLabels,
                                          ArrayList<String> rowLabels,
                                          ArrayList<ArrayList<Double>> values,
                                          int nextSeriesID, Random random) {
        int numSeries = rowLabels.size();
        int numGroups = colLabels.size();
        int series = random.nextInt(numSeries);

        // Create the new series
        String seriesStr = "Series " + (nextSeriesID + 1);

        // Insert the series
        rowLabels.add(series, seriesStr);
        for (int i = 0; i < numGroups; i++) {
            ArrayList<Double> list = values.get(i);
            list.add(series, generateRandomValue(random));
        }

        return createDataSource(colLabels, rowLabels, values);
    }

    /**
     * Deletes series from the given data set.
     * @param colLabels the ArrayList of Group labels
     * @param rowLabels the ArrayList of Series labels
     * @param values the table of data values
     * @param random the random number generator to be used
     */
    private static DataModel deleteSeries(ArrayList<String> colLabels,
                                          ArrayList<String> rowLabels,
                                          ArrayList<ArrayList<Double>> values,
                                          Random random) {
        int numSeries = rowLabels.size();
        int numGroups = colLabels.size();
        int series = random.nextInt(numSeries);

        // Don't remove the last series
        if (numSeries <= 1)
            return createDataSource(colLabels, rowLabels, values);
        ;

        // Remove the series
        rowLabels.remove(series);
        for (int i = 0; i < numGroups; i++) {
            ArrayList<Double> list = values.get(i);
            list.remove(series);
        }

        return createDataSource(colLabels, rowLabels, values);
    }

    /**
     * Returns a random number that is a multiple of 10 between 10 and 1000.
     * @param random
     * @return
     */
    private static double generateRandomValue(Random random) {
        return (random.nextInt(100) + 1) * 10;
    }

    private static DataModel createDataSource(ArrayList<String> cols,
                                              ArrayList<String> rows,
                                              ArrayList<ArrayList<Double>> data) {
        Object[] colLabels = cols.toArray();
        Object[] rowLabels = rows.toArray();
        Object[] dataList = data.toArray();
        Object[][] dataArray = new Object[dataList.length][];
        for (int i = 0; i < dataList.length; i++) {
            dataArray[i] = ((ArrayList<Double>)dataList[i]).toArray();
        }
        LocalXMLDataSource dataSource =
            new LocalXMLDataSource(colLabels, rowLabels, dataArray);
        return new GraphDataModel(dataSource);
    }
}
