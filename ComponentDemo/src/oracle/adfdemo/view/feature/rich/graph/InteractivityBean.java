/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/graph/InteractivityBean.java /bibeans_root/1 2011/08/11 10:05:31 byoshimo Exp $ */

/* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    byoshimo    08/10/11 - Creation
 */

/**
 *  @version $Header: InteractivityBean.java 10-aug-2011.11:35:57 byoshimo Exp $
 *  @author  byoshimo
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.graph;

import java.awt.Color;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.graph.UIGraph;

import oracle.dss.graph.CommonGraph;


public class InteractivityBean {
    public InteractivityBean() {
        super();
    }

    private static Date m_startDate = null;
    private static Date m_endDate = null;

    public List getTimeData() {
        Random random = new Random(100);
        ArrayList list = new ArrayList();
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        Calendar calendar = Calendar.getInstance();
        Date start = java.sql.Date.valueOf("2010-10-18");
        String[] rowLabels = twoPhoneBrands;

        List<Date> dates = new ArrayList<Date>();
        //String [] colLabels = new String[] { "10/18/10", "10/19/10", "10/20/10", "10/21/10", "10/22/10", /*"10/25/10", "10/26/10", "10/28/10"*/ };
        for (int i = 0; i < 8; i++) {
            Date d = new Date(start.getTime() + i * 24 * 60 * 60 * 1000L);
            if (i == 1) {
                m_startDate = d;
            } else if (i == 6) {
                m_endDate = d;
            }
            dates.add(d);
        }
        Object[] colLabels = dates.toArray();

        //try {
        for (int c = 0; c < colLabels.length; c++) {
            Date d1 = (Date)colLabels[c]; //df.parse(colLabels[c]);
            for (int r = 0; r < rowLabels.length; r++) {
                list.add(new Object[] { d1, rowLabels[r], new Double(random.nextDouble()) });
            }
        }
        /*
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */
        return list;
    }

    public Date getTimeAxisStartDate() {
        return m_startDate;
    }

    public Date getTimeAxisEndDate() {
        return m_endDate;
    }

    public List getManySeriesData() {
        List list = new ArrayList();
        Random random = new Random(3);

        List<String> groupNames = new ArrayList<String>();
        List<String> seriesNames = new ArrayList<String>();

        for (int i = 0; i < 5; i++) {
            groupNames.add("Group " + i);
        }

        for (int i = 1; i <= 30; i++) {
            seriesNames.add("Series " + i);
        }

        for (int i = 0; i < groupNames.size(); i++) {
            for (int j = 0; j < seriesNames.size(); j++) {
                list.add(new Object[] { groupNames.get(i), seriesNames.get(j), new Double(random.nextInt(50)) });
            }
        }
        return list;
    }

    public List getBarDataMedium() {
        List list = new ArrayList();
        Random random = new Random(3);

        List<String> groupNames = new ArrayList<String>();
        List<String> seriesNames = new ArrayList<String>();

        for (int i = 0; i < 20; i++) {
            groupNames.add("Group " + i);
        }

        for (int i = 1; i <= 2; i++) {
            seriesNames.add("Series " + i);
        }

        for (int i = 0; i < groupNames.size(); i++) {
            for (int j = 0; j < seriesNames.size(); j++) {
                list.add(new Object[] { groupNames.get(i), seriesNames.get(j), new Double(random.nextInt(50)) });
            }
        }
        return list;
    }
    private static final String[] twoRegionGroups = new String[] { "East Coast", "West Coast" };
    private static final String[] mediumRegionGroups =
        new String[] { "New England", "Midwest", "Northwest", "Southwest", "California", "Other" };
    private static final String[] manyRegionGroups =
        new String[] { "New England", "Northern Rockies", "Rocky Mountains", "Western Great Basin", "Midwest",
                       "Northwest", "Southwest", "California", "Gulf States", "Appalachia", "Hawaii", "Alaska",
                       "US Territories", "Great Plains", "Carolinas", "Coastal States", "East Central" };
    private static final String[] allPhoneBrands =
        new String[] { "Nokia", "RIM", "Samsung", "iPhone", "LG" }; //new String[]{"Nokia", "Motorola", "Apple", "RIM", "Samsung", "iPhone", "LG", "Sony Ericsson", "Others"};
    private static final String[] twoPhoneBrands =
        new String[] { "iPhone", "RIM" }; //, "LG", "Sony Ericsson", "Others"};
    private static final String[] lotsOfPhones = new String[] { "Nokia", "Motorola", "Samsung", "LG", "Others" };

    public List getPhoneData1() {
        UIGraph graph = (UIGraph)FacesContext.getCurrentInstance().getViewRoot().findComponent("bar_ContextMenu");
        CommonGraph cg = (CommonGraph)graph.getImageView();
        cg.getToolTips().setFillColor(Color.black);
        Random random = new Random(0);
        return generateTwoRegionData(random, 5, 80);
    }

    public List getPhoneData2() {
        Random random = new Random(1);
        return generateTwoRegionData(random, 5, 80);
    }

    public List getPhoneData3() {
        Random random = new Random(2);
        return generateTwoRegionData(random, 10, 20);
    }

    public List getPhoneData4() {
        Random random = new Random(3);
        return generateTwoRegionData(random, 5, 20);
    }

    public List getPhoneData5() {
        Random random = new Random(4);
        return generateManyRegionData(random, 5, 20);
    }

    public List getPhoneData6() {
        Random random = new Random(5);
        return generateManyRegionTwoSeriesData(random, 5, 20);
    }

    public List getPhoneData7() {
        Random random = new Random(6);
        return generateManyRegionData(random, 5, 20);
    }

    public List getPhoneData8() {
        Random random = new Random(7);
        return generateManyRegionData(random, 5, 70);
    }

    public List getPhoneData9() {
        Random rand = new Random(8);

        List list = new ArrayList();
        for (int j = 0; j < lotsOfPhones.length; j++) {
            int xStart = 100;
            int yStart = 100 * 100;
            int zMax = 10;
            int zMin = 5;
            for (int i = 0; i < manyRegionGroups.length; i++) {
                double xVal = 0;
                double yVal = 0;
                if (j == 1) {
                    xVal = rand.nextDouble() * 200 + 150;
                    yVal = rand.nextDouble() * 200 * 100 + 150 * 100;
                } else if (j == 2) {
                    xVal = rand.nextDouble() * 200 + 500;
                    yVal = rand.nextDouble() * 200 * 100 + 500 * 100;
                } else {
                    xVal = rand.nextDouble() * 700 + 150;
                    yVal = rand.nextDouble() * 500 * 100 + 150 * 100;
                }
                list.add(new Object[] { manyRegionGroups[i], lotsOfPhones[j], new Double(xVal) });
                list.add(new Object[] { manyRegionGroups[i] + "1", lotsOfPhones[j], new Double(yVal) });
                list.add(new Object[] { manyRegionGroups[i] + "2", lotsOfPhones[j],
                                        new Double(rand.nextDouble() * (zMax - zMin) + zMin) });
            }
        }
        return list;
    }

    public List getPhoneData10() {
        Random rand = new Random(8);

        List list = new ArrayList();
        for (int j = 0; j < lotsOfPhones.length; j++) {
            int xStart = 100;
            int yStart = 100 * 100;
            int zMax = 10;
            int zMin = 5;
            for (int i = 0; i < manyRegionGroups.length; i++) {
                double xVal = 0;
                double yVal = 0;
                if (j == 1) {
                    xVal = rand.nextDouble() * 200 + 150;
                    yVal = rand.nextDouble() * 200 * 100 + 150 * 100;
                } else if (j == 2) {
                    xVal = rand.nextDouble() * 200 + 500;
                    yVal = rand.nextDouble() * 200 * 100 + 500 * 100;
                } else {
                    xVal = rand.nextDouble() * 700 + 150;
                    yVal = rand.nextDouble() * 500 * 100 + 150 * 100;
                }
                list.add(new Object[] { manyRegionGroups[i], lotsOfPhones[j], new Double(xVal) });
                list.add(new Object[] { manyRegionGroups[i] + "1", lotsOfPhones[j], new Double(yVal) });
            }
        }
        return list;
    }

    private List generateManyRegionTwoSeriesData(Random rand, double minValue, double maxValue) {
        return generateData(rand, manyRegionGroups, twoPhoneBrands, minValue, maxValue);
    }

    private List generateManyRegionData(Random rand, double minValue, double maxValue) {
        return generateData(rand, manyRegionGroups, allPhoneBrands, minValue, maxValue);
    }

    private List generateTwoRegionData(Random rand, double minValue, double maxValue) {
        return generateData(rand, twoRegionGroups, allPhoneBrands, minValue, maxValue);
    }

    private List generateData(Random rand, String[] regions, String[] phoneBrands, double minValue, double maxValue) {
        List list = new ArrayList();
        for (int i = 0; i < regions.length; i++) {
            for (int j = 0; j < phoneBrands.length; j++) {
                list.add(new Object[] { regions[i], phoneBrands[j],
                                        new Double(rand.nextDouble() * (maxValue - minValue) + minValue) });
            }
        }
        return list;
    }

    public List getBarDataLarge() {
        List list = new ArrayList();
        Random random = new Random(0);

        List<String> groupNames = new ArrayList<String>();
        List<String> seriesNames = new ArrayList<String>();

        for (int i = 0; i < 20; i++) {
            groupNames.add("Group " + i);
        }

        for (int i = 1; i <= 5; i++) {
            seriesNames.add("Series " + i);
        }

        for (int i = 0; i < groupNames.size(); i++) {
            for (int j = 0; j < seriesNames.size(); j++) {
                list.add(new Object[] { groupNames.get(i), seriesNames.get(j), new Double(random.nextInt(50)) });
            }
        }
        return list;
    }

    ////// OTHER

    public List getLineData() {
        List list = new ArrayList();
        Random random = new Random(0);

        List<String> groupNames = new ArrayList<String>();
        List<String> seriesNames = new ArrayList<String>();

        groupNames.add("Group A");
        groupNames.add("Group B");
        groupNames.add("Group C");
        groupNames.add("Group D");
        groupNames.add("Group E");
        groupNames.add("Group F");

        for (int i = 1; i <= 1; i++) {
            seriesNames.add("Series " + i);
        }

        for (int i = 0; i < groupNames.size(); i++) {
            for (int j = 0; j < seriesNames.size(); j++) {
                list.add(new Object[] { groupNames.get(i), seriesNames.get(j), new Double(random.nextInt(50)) });
            }
        }
        return list;
    }

    public List getLineData2() {
        List list = new ArrayList();
        Random random = new Random(0);

        List<String> groupNames = new ArrayList<String>();
        List<String> seriesNames = new ArrayList<String>();

        for (int i = 0; i < 20; i++) {
            groupNames.add("Group " + i);
        }

        for (int i = 1; i <= 1; i++) {
            seriesNames.add("Series " + i);
        }

        for (int i = 0; i < groupNames.size(); i++) {
            for (int j = 0; j < seriesNames.size(); j++) {
                list.add(new Object[] { groupNames.get(i), seriesNames.get(j), new Double(random.nextInt(50)) });
            }
        }
        return list;
    }

    private Date startDate = null;
    private Date endDate = null;

    public Long getTimeRange() {
        return new Long(14 * 60 * 1000);
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

}
