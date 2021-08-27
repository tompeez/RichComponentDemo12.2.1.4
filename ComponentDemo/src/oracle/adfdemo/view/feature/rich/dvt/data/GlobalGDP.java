package oracle.adfdemo.view.feature.rich.dvt.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Map;
import java.util.HashMap;

import oracle.adfdemo.view.feature.rich.thematicMap.SampleMapData.MapData;

import oracle.adfdemo.view.feature.rich.thematicMap.SampleMapData.MapDataComparator;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

public class GlobalGDP {

  private static ArrayList<MapData> gdpModel;

  public static String[] COUNTRIES = {
    "AFG", "ALB", "DZA", "AGO", "ATG", "ARG", "ARM", "AUS", "AUT", "AZE", "BHS", "BHR", "BGD", "BRB", "BLR", "BEL",
    "BLZ", "BEN", "BTN", "BOL", "BIH", "BWA", "BRN", "BGR", "BFA", "MMR", "BDI", "KHM", "CMR", "CAN", "CPV", "CAF",
    "TCD", "CHL", "COL", "COM", "COD", "COG", "CRI", "CIV", "HRV", "CYP", "CZE", "DNK", "DJI", "DMA", "DOM", "ECU",
    "EGY", "SLV", "GNQ", "ERI", "EST", "ETH", "FJI", "FIN", "GAB", "GMB", "GEO", "GHA", "GRC", "GRD", "GTM", "GIN",
    "GNB", "GUY", "HTI", "HND", "HUN", "ISL", "IND", "IDN", "IRN", "IRQ", "IRL", "ISR", "JAM", "JOR", "KAZ", "KEN",
    "KIR", "KWT", "KGZ", "LAO", "LVA", "LBN", "LSO", "LBR", "LBY", "LTU", "LUX", "MKD", "MDG", "MWI", "MYS", "MDV",
    "MLI", "MLT", "MRT", "MUS", "MEX", "MDA", "MNG", "MNE", "Mar", "MOZ", "NAM", "NPL", "NLD", "NZL", "NIC", "NER",
    "NGA", "NOR", "OMN", "PAK", "PAN", "PNG", "PRY", "PER", "PHL", "POL", "PRT", "QAT", "ROU", "RWA", "KNA", "LCA",
    "VCT", "WSM", "SMR", "STP", "SAU", "SEN", "SCG", "SYC", "SLE", "SGP", "SVK", "SVN", "SLB", "ZAF", "PRK", "SSD",
    "ESP", "LKA", "SDN", "SUR", "SWZ", "SWE", "CHE", "TWN", "TJK", "TZA", "THA", "TLS", "TGO", "TON", "TTO", "TUN",
    "TUR", "TKM", "TUV", "UGA", "UKR", "ARE", "URY", "UZB", "VUT", "VEN", "VNM", "YEM", "ZMB", "ZWE", "BRA", "CHN",
    "FRA", "DEU", "HKG", "ITA", "JPN", "RUS", "GBR", "USA"
  };

  public static int[] GDP = {
    19906, 12688, 207794, 118719, 1176, 474954, 10066, 1541797, 398594, 68804, 8043, 27026, 122724, 4490, 63259, 484692,
    1554, 7429, 2196, 27429, 17326, 17624, 16628, 51020, 10646, 53140, 2475, 14241, 25005, 1819081, 1899, 2172, 10806,
    268177, 366020, 600, 17703, 13692, 45134, 24627, 57102, 23006, 196072, 313637, 1354, 497, 58996, 80927, 256729,
    23816, 17206, 3092, 21863, 41906, 3996, 250126, 18376, 918, 15934, 38939, 249201, 790, 49880, 5632, 870, 2788, 7902,
    18388, 126873, 13654, 1824832, 878198, 548895, 212501, 210416, 240894, 15249, 31209, 196419, 41117, 173, 173424,
    6473, 9217, 28380, 41345, 2439, 1735, 81915, 42164, 56738, 9676, 10117, 4212, 303527, 2209, 10319, 8689, 4199,
    11466, 1177116, 7252, 10258, 4280, 97530, 14600, 12299, 19415, 773116, 169680, 10506, 6575, 268708, 501101, 76464,
    231879, 36253, 15786, 199003, 199003, 250436, 487674, 212720, 183378, 169680, 7223, 734, 1220, 712, 683, 1855, 264,
    727307, 13864, 37399, 1031, 3777, 276520, 91916, 45617, 1010, 384315, 1155872, 12202, 1352057, 59408, 59941, 4738,
    3751, 526192, 632400, 473971, 7592, 28247, 365564, 4173, 3685, 476, 25277, 45611, 794468, 33679, 37, 21002, 176235,
    358940, 49404, 51168, 783, 382424, 138071, 35641, 20517, 9802, 2395968, 8227037, 2608699, 3400579, 263021, 2014079,
    5963969, 2021960, 2440505, 15684750
  };

  public static String[] FORMATTED_GDP = {
    "19,906", "12,688", "207,794", "118,719", "1,176", "474,954", "10,066", "1,541,797", "398,594", "68,804", "8,043",
    "27,026", "122,724", "4,490", "63,259", "484,692", "1,554", "7,429", "2,196", "27,429", "17,326", "17,624",
    "16,628", "51,020", "10,646", "53,140", "2,475", "14,241", "25,005", "1,819,081", "1,899", "2,172", "10,806",
    "268,177", "366,020", "600", "17,703", "13,692", "45,134", "24,627", "57,102", "23,006", "196,072", "313,637",
    "1,354", "497", "58,996", "80,927", "256,729", "23,816", "17,206", "3,092", "21,863", "41,906", "3,996", "250,126",
    "18,376", "918", "15,934", "38,939", "249,201", "790", "49,880", "5,632", "870", "2,788", "7,902", "18,388",
    "126,873", "13,654", "1,824,832", "878,198", "548,895", "212,501", "210,416", "240,894", "15,249", "31,209",
    "196,419", "41,117", "173", "173,424", "6,473", "9,217", "28,380", "41,345", "2,439", "1,735", "81,915", "42,164",
    "56,738", "9,676", "10,117", "4,212", "303,527", "2,209", "10,319", "8,689", "4,199", "11,466", "1,177,116",
    "7,252", "10,258", "4,280", "97,530", "14,600", "12,299", "19,415", "773,116", "169,680", "10,506", "6,575",
    "268,708", "501,101", "76,464", "231,879", "36,253", "15,786", "199,003", "199,003", "250,436", "487,674",
    "212,720", "183,378", "169,680", "7,223", "734", "1,220", "712", "683", "1,855", "264", "727,307", "13,864",
    "37,399", "1,031", "3,777", "276,520", "91,916", "45,617", "1,010", "384,315", "1,155,872", "12,202", "1,352,057",
    "59,408", "59,941", "4,738", "3,751", "526,192", "632,400", "473,971", "7,592", "28,247", "365,564", "4,173",
    "3,685", "476", "25,277", "45,611", "794,468", "33,679", "37", "21,002", "176,235", "358,940", "49,404", "51,168",
    "783", "382,424", "138,071", "35,641", "20,517", "9,802", "2,395,968", "8,227,037", "2,608,699", "3,400,579",
    "263,021", "2,014,079", "5,963,969", "2,021,960", "2,440,505", "15,684,750"
  };

  public static String[] COUNTRY_NAMES = {
    "Afghanistan", "Albania", "Algeria", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria",
    "Azerbaijan", "Bahamas, The", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin",
    "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brunei", "Bulgaria", "Burkina Faso", "Burma", "Burundi",
    "Cambodia", "Cameroon", "Canada", "Cape Verde", "Central African Republic", "Chad", "Chile", "Colombia", "Comoros",
    "Congo, Democratic Republic of the", "Congo, Republic of the", "Costa Rica", "Cote d'Ivoire", "Croatia", "Cyprus",
    "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador",
    "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "Gabon", "Gambia, The", "Georgia",
    "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary",
    "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Jamaica", "Jordan", "Kazakhstan", "Kenya",
    "Kiribati", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Lithuania",
    "Luxembourg", "Macedonia, Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta",
    "Mauritania", "Mauritius", "Mexico", "Moldova", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Namibia",
    "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Norway", "Oman", "Pakistan", "Panama",
    "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Rwanda",
    "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino",
    "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia",
    "Slovenia", "Solomon Islands", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan",
    "Suriname", "Swaziland", "Sweden", "Switzerland", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste",
    "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine",
    "United Arab Emirates", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe",
    "Brazil ", "China", "France", "Germany ", "Hong Kong", "Italy  ", "Japan ", "Russia", "United Kingdom",
    "United States"
  };

  public GlobalGDP() {
    gdpModel = new ArrayList<MapData>();
    for (int i = 0; i < COUNTRIES.length; i++) {
      gdpModel.add(new MapData(COUNTRIES[i], new Object[] { Math.sqrt(GDP[i]), FORMATTED_GDP[i], COUNTRY_NAMES[i] },
                               GDP[i], null));
    }
    Collections.sort(gdpModel, new MapDataComparator());
    Collections.reverse(gdpModel);
  }

  public List<MapData> getGlobalGDP() {
    return gdpModel;
  }
  
}
