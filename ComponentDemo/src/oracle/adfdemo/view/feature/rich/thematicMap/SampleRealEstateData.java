package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import oracle.adfdemo.view.feature.rich.thematicMap.SampleMapData.MapData;

public class SampleRealEstateData extends ArrayList<SampleMapData.MapData> {
  private static String[] realEstateCities = {
    "IL_CHICAGO", "MI_DETROIT", "TX_DALLAS", "PA_PHILADELPHIA", "CA_LOS_ANGELES", "MA_BOSTON",
    "GA_ATLANTA", "FL_TAMPA", "FL_ORLANDO", "FL_FORT_LAUDERDALE", "CT_NEW_HAVEN", "AZ_PHOENIX",
    "TX_FORT_WORTH", "NV_LAS_VEGAS", "FL_WEST_PALM_BEACH", "CA_RIVERSIDE", "CO_DENVER",
    "FL_MIAMI", "CA_SAN_DIEGO", "NC_RALEIGH", "OH_COLUMBUS", "CA_ORANGE_COUNTY", "NY_NEW_YORK",
    "MD_BALTIMORE", "CT_HARTFORD", "MO_ST_LOUIS", "OH_CLEVELAND", "TX_AUSTIN", "TX_HOUSTON",
    "NJ_MONMOUTH", "PA_PITTSBURGH", "TX_SAN_ANTONIO", "NJ_NEWARK", "CA_OAKLAND", "NJ_MIDDLESEX",
    "CA_SACRAMENTO", "NC_CHARLOTTE", "FL_JACKSONVILLE", "LA_NEW_ORLEANS", "TN_NASHVILLE",
    "MN_MINNEAPOLIS", "FL_SARASOTA", "IN_INDIANAPOLIS", "ME_PORTLAND", "MI_ANN_ARBOR", "SC_CHARLESTON",
    "FL_FORT_MYERS", "OK_OKLAHOMA_CITY", "VA_RICHMOND", "SC_GREENVILLE", "WA_SEATTLE",
    "CA_SAN_FRANCISCO", "NC_GREENSBORO", "FL_DAYTONA_BEACH", "TN_KNOXVILLE", "CA_VENTURA",
    "MO_KANSAS_CITY", "CA_SAN_JOSE", "WI_MILWAUKEE", "OR_PORTLAND", "KY_LOUISVILLE", "OH_DAYTON",
    "CO_COLORADO_SPRINGS", "SC_MYRTLE_BEACH", "FL_MELBOURNE", "MI_GRAND_RAPIDS", "FL_FORT_PIERCE",
    "OH_CINCINNATI", "AL_BIRMINGHAM", "NC_WILMINGTON", "OH_AKRON", "OK_TULSA", "PA_HARRISBURG",
    "DE_WILMINGTON", "FL_NAPLES", "IN_FORT_WAYNE", "TN_MEMPHIS", "FL_LAKELAND", "PA_ALLENTOWN",
    "NY_ROCHESTER", "AL_MOBILE", "NY_BUFFALO", "SC_COLUMBIA", "CA_FRESNO", "NY_ALBANY",
    "NY_SYRACUSE", "AR_LITTLE_ROCK", "IL_PEORIA", "PA_YORK", "OH_TOLEDO", "LA_BATON_ROUGE",
    "ID_BOISE_CITY", "KS_WICHITA", "NM_ALBUQUERQUE", "FL_PENSACOLA", "NC_ASHEVILLE", "UT_SALT_LAKE_CITY",
    "HI_HONOLULU", "NJ_TRENTON", "IA_DES_MOINES", "AZ_TUCSON", "CA_STOCKTON", "CO_BOULDER",
    "CA_SANTA_BARBARA", "NE_OMAHA", "WI_MADISON", "NV_RENO", "VA_ROANOKE", "CO_FORT_COLLINS",
    "CA_BAKERSFIELD", "PA_READING", "TX_CORPUS_CHRISTI", "TN_CHATTANOOGA", "NJ_JERSEY_CITY",
    "FL_OCALA", "WA_SPOKANE", "FL_PUNTA_GORDA", "WV_CHARLESTON", "KY_LEXINGTON", "GA_MACON",
    "TX_EL_PASO", "VA_NORFOLK", "FL_TALLAHASSEE", "IL_SPRINGFIELD", "TN_SOUTH_BEND", "TX_TYLER",
    "LA_SHREVEPORT", "NM_SANTA_FE", "FL_GAINESVILLE", "AL_HUNTSVILLE", "NC_FAYETTEVILLE",
    "AK_ANCHORAGE", "MO_COLUMBIA", "OR_SALEM", "IA_CEDAR_RAPIDS", "IA_IOWA_CITY", "CO_PUEBLO"
  };
  private static int[] realEstatePrice = {
    205000,137900,219900,229000,442700,344990,180805,169000,188900,179900,339000,235000,179500,
    165000,239000,259900,299005,279000,459990,228000,149900,575000,359900,267000,245000,165900,
    124900,259836,205000,325000,144000,199973,286000,479000,324900,299999,189900,210990,174285,
    219900,229900,250000,137500,265000,199000,249990,228000,160000,214925,170000,379950,796500,
    149900,188080,179900,499000,142000,649500,187500,295000,149900,102950,240000,169900,159500,
    149900,169000,139900,169900,245000,119900,149900,169900,209900,339000,99900,154900,139990,
    179000,144000,189000,139900,159900,227900,229900,155000,157000,129500,165000,109839,179900,
    196695,130775,200000,179000,250000,234900,488695,225000,164900,189000,230000,375900,795000,
    155000,229000,262900,173500,274000,175000,175000,186700,179900,299000,129900,184900,184000,
    149000,159900,134900,152973,234900,157000,114900,96150,182250,175000,399000,149000,179900,
    147950,299500,174900,224000,139900,220000,139900
  };
  private static double[] realEstatePriceChange = {
    -2.29,2.22,0.00,-2.14,0.61,-1.40,-2.21,-0.53,1.56,-5.80,-0.29,2.62,0.39,1.23,-4.02,3.05,-0.33,
    -0.36,2.45,0.18,-2.66,0.00,-5.29,2.73,0.00,2.09,-0.08,1.66,2.50,0.00,1.77,0.49,-4.63,-1.24,
    -0.03,3.48,-2.62,0.95,-0.40,1.81,2.18,-3.81,-1.72,0.00,0.51,3.30,0.06,0.06,2.35,0.06,-3.57,
    -0.31,0.00,1.72,2.80,0.00,0.00,-2.33,3.59,0.03,0.00,-1.95,2.13,-1.74,6.33,0.00,2.49,-0.07,0.00,
    -1.61,0.00,0.27,0.00,-2.33,-3.12,-4.77,3.27,-1.42,0.01,2.93,3.25,0.00,0.25,3.12,3.12,3.40,0.06,
    0.00,0.00,0.77,0.00,0.87,-1.15,0.00,0.62,0.04,2.58,-1.87,-6.15,-0.06,2.16,2.22,-3.82,2.32,0.06,
    0.00,5.16,2.10,-3.86,0.06,-2.72,6.53,0.00,-8.00,0.70,0.00,-1.87,0.00,0.00,3.85,0.67,3.25,1.29,
    -2.54,-2.88,1.31,-2.72,1.01,-1.26,0.00,-1.30,-0.13,2.88,2.31,-2.17,2.34,-5.47
  };

  protected static ArrayList<SampleMapData.MapData> getSampleRealEstateData() {
    ArrayList<SampleMapData.MapData> sampleData = new ArrayList();
    for (int i = 0; i < realEstateCities.length; i++) {
      Object[] dataCategories = {realEstatePriceChange[i]};
        sampleData.add(new MapData(realEstateCities[i], dataCategories, realEstatePrice[i], null));
    }
    return sampleData;
  }
}