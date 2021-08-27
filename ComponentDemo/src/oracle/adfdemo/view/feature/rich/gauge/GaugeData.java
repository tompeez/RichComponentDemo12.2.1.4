/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gauge/GaugeData.java /main/4 2010/06/22 11:44:09 ytang Exp $ */

/* Copyright (c) 2009, 2010, Oracle and/or its affiliates. 
All rights reserved. */

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

package oracle.adfdemo.view.feature.rich.gauge;

import java.util.ArrayList;
import java.util.List;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/gauge/GaugeData.java /main/4 2010/06/22 11:44:09 ytang Exp $
 *  @author  hzhang
 *  @since   release specific (what release of product did this appear in)
 */
public class GaugeData {

    private double m_gaugeValue = 75;

    public GaugeData (){

        ArrayList<CountryData> list = new ArrayList<CountryData>(11);
        list.add(new CountryData("China", 1338134000,139.434));
        list.add(new CountryData("India", 1182276000, 359.654));
        list.add(new CountryData("USA", 309527000,32.145));
        list.add(new CountryData("Indonesia", 231369500,121.481));
        list.add(new CountryData("Brazil", 193087500, 22.676));
        list.add(new CountryData("Pakistan", 169784000, 211.19));
        list.add(new CountryData("Bangladesh", 162221000,1126.55));
        list.add(new CountryData("Nigeria", 154729000, 167.498));
        list.add(new CountryData("Russia", 141927000,8.301));
        list.add(new CountryData("Japan", 127380000,337.097));
        list.add(new CountryData("Mexico", 107550500,54.923)); 
        
        _gaugeTableData = list;
        
    }
    public double getGaugeValue() {
        return m_gaugeValue;
    }

    public void setGaugeValue(double value) {
        m_gaugeValue = value;
    }
    public List getGaugeSetData() 
    {
        ArrayList list = new ArrayList();
        String[] rowLabels  = new String[] {"London", "Paris", "New York"};
        String[] colLabels  = new String[] {"Quota", "Sales", "Margin", "Costs", "Units"};
        double [] [] values = new double[][]{
            {20, 90, 135},
            {50, 20, 80},
            {130, 140, 150},
            {70, 80, 90},
            {110, 120, 130}
            };
        for (int c = 0; c < colLabels.length; c++)
        {
         for (int r = 0; r < rowLabels.length; r++)
           {
            list.add (new Object [] {colLabels[c], rowLabels[r], 
                new Double (values [c][r])});
           }
        }
        return list;
    }

    public List<CountryData> getGaugeTableData() 
    {
        return _gaugeTableData;
    }
    public static class CountryData {
        private String _name;
        private long _population;
        private double _percent;
        private double _density;
        private static long worldPopulation = 6697254000L;
        
        public CountryData(String name, int population, double density) {
          _name = name;
          _population = population;
          _density = density;
          _percent = (double)population / (double)worldPopulation;
        }

        public String getName() {
            return _name;
        }
        
        public double getPercent() {
            return _percent;
        }

        public long getPopulation() {
            return _population;
        }
        
        public double getDensity() {
            return _density;
        }
    }
    
    private List<CountryData> _gaugeTableData;
}

