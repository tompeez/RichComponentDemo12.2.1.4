/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/thematicMap/SampleCountryData.java /main/2 2010/12/14 14:48:18 ytang Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    kchow       04/30/10 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/thematicMap/SampleCountryData.java /main/2 2010/12/14 14:48:18 ytang Exp $
 *  @author  kchow   
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Random;

import oracle.adfdemo.view.feature.rich.thematicMap.SampleCountryData.CountryData;

public class SampleCountryData extends ArrayList <CountryData> {

    public SampleCountryData(long seed, int percentageFull) {
        
   
            Random random = new Random(seed);
            
            
            for(int i=0; i < colorData.length; i++){
                if ( random.nextInt(100) <= percentageFull ){
                    Object data[] = colorData[i];
                    int colorType = random.nextInt(6);
                    Color color;
                    String category;
                    if ( seed % 3 == 0 ){
                        color = SampleContinentData.colorSet[colorType];
                        category = SampleContinentData.categorySet[colorType];
                    }
                    else if ( seed % 3 == 1 ){
                        color = SampleColorData.colorSet[colorType];
                        category = SampleColorData.categorySet[colorType];
                    }
                    else {
                        color = SampleColorData2.colorSet[colorType];
                        category = SampleColorData2.categorySet[colorType];
                    }
                    Integer value = random.nextInt(100);
                    this.add(
                        new CountryData(
                            (String)data[0],
                            (String)data[1],
                            (String)data[2],
                            (Integer)data[3],
                            (String)data[4],
                            color,
                            category,
                            value));
            }
        }
    }
    
    public class CountryData{
        private String m_continentCode;
        private String m_countryCode2letter;
        private String m_countryCode3letter;
        private int m_countryNumber;
        private String m_countryName;
        private String m_category;
        private Color m_color;
        private int m_value;
        
        public CountryData(String continentCode, String countryCode2letter, String countryCode3letter, int countryNumber, String countryName, Color color, String category, int value){
            m_continentCode = continentCode;
            m_countryCode2letter = countryCode2letter;
            m_countryCode3letter = countryCode3letter;
            m_countryNumber = countryNumber;
            m_countryName = countryName;
            m_category = category;
            m_color = color;
            m_value = value;
        }
        
        public String getCoutryCode(){
            return m_countryCode3letter;
        }
        
        public String getContinentCode(){
            return m_continentCode;
        }
        
        public int getCoutryNumber(){
            return m_countryNumber;
        }
        
        public String getName(){
            return getCoutryCode();
        }
        
        public String getFullName(){
            return m_countryName.replaceAll("_", " ");
        }
        
        public Color getColor(){
            return m_color;
        }
        
        public String getCategory(){
            return m_category;
        }
        
        public int getValue(){
            return m_value;
        }
        
        public String toString(){
            return getFullName();
        }
    }
        
        
    
    private static Color color1 = new Color(180, 48, 48);
    private static Color color2 = new Color(64, 86, 149);
    private static Color color3 = new Color(122, 158, 198);
    
    private static final Object colorData[][] = 
    {
        {"AS","AF","AFG",4,"Afghanistan"},
        {"EU","AX","ALA",248,"Aland_Islands"},
        {"EU","AL","ALB",8,"Albania"},
        {"AF","DZ","DZA",12,"Algeria"},
        {"OC","AS","ASM",16,"American_Samoa"},
        {"EU","AD","AND",20,"Andorra"},
        {"AF","AO","AGO",24,"Angola"},
        {"NA","AI","AIA",660,"Anguilla"},
        {"AN","AQ","ATA",10,"Antarctica"},
        {"NA","AG","ATG",28,"Antigua_and_Barbuda"},
        {"SA","AR","ARG",32,"Argentina"},
        {"AS","AM","ARM",51,"Armenia"},
        {"NA","AW","ABW",533,"Aruba"},
        {"OC","AU","AUS",36,"Australia"},
        {"EU","AT","AUT",40,"Austria"},
        {"AS","AZ","AZE",31,"Azerbaijan"},
        {"NA","BS","BHS",44,"Bahamas"},
        {"AS","BH","BHR",48,"Bahrain"},
        {"AS","BD","BGD",50,"Bangladesh"},
        {"NA","BB","BRB",52,"Barbados"},
        {"EU","BY","BLR",112,"Belarus"},
        {"EU","BE","BEL",56,"Belgium"},
        {"NA","BZ","BLZ",84,"Belize"},
        {"AF","BJ","BEN",204,"Benin"},
        {"NA","BM","BMU",60,"Bermuda"},
        {"AS","BT","BTN",64,"Bhutan"},
        {"SA","BO","BOL",68,"Bolivia"},
        {"EU","BA","BIH",70,"Bosnia_and_Herzegovina"},
        {"AF","BW","BWA",72,"Botswana"},
        {"AN","BV","BVT",74,"Bouvet_Island"},
        {"SA","BR","BRA",76,"Brazil"},
        {"AS","IO","IOT",86,"British_Indian_Ocean_Territory"},
        {"NA","VG","VGB",92,"British_Virgin_Islands"},
        {"AS","BN","BRN",96,"Brunei_Darussalam"},
        {"EU","BG","BGR",100,"Bulgaria"},
        {"AF","BF","BFA",854,"Burkina_Faso"},
        {"AF","BI","BDI",108,"Burundi"},
        {"AS","KH","KHM",116,"Cambodia"},
        {"AF","CM","CMR",120,"Cameroon"},
        {"NA","CA","CAN",124,"Canada"},
        {"AF","CV","CPV",132,"Cape_Verde"},
        {"NA","KY","CYM",136,"Cayman_Islands"},
        {"AF","CF","CAF",140,"Central_African_Republic"},
        {"AF","TD","TCD",148,"Chad"},
        {"SA","CL","CHL",152,"Chile"},
        {"AS","CN","CHN",156,"China"},
        {"AS","CX","CXR",162,"Christmas_Island"},
        {"AS","CC","CCK",166,"Cocos"},
        {"SA","CO","COL",170,"Colombia"},
        {"AF","KM","COM",174,"Comoros"},
        {"AF","CD","COD",180,"Congo"},
        {"AF","CG","COG",178,"Congo"},
        {"OC","CK","COK",184,"Cook_Islands"},
        {"NA","CR","CRI",188,"Costa_Rica"},
        {"AF","CI","CIV",384,"Cote_d'Ivoire"},
        {"EU","HR","HRV",191,"Croatia"},
        {"NA","CU","CUB",192,"Cuba"},
        {"AS","CY","CYP",196,"Cyprus"},
        {"EU","CZ","CZE",203,"Czech_Republic"},
        {"EU","DK","DNK",208,"Denmark"},
        {"AF","DJ","DJI",262,"Djibouti"},
        {"NA","DM","DMA",212,"Dominica"},
        {"NA","DO","DOM",214,"Dominican_Republic"},
        {"SA","EC","ECU",218,"Ecuador"},
        {"AF","EG","EGY",818,"Egypt"},
        {"NA","SV","SLV",222,"El_Salvador"},
        {"AF","GQ","GNQ",226,"Equatorial_Guinea"},
        {"AF","ER","ERI",232,"Eritrea"},
        {"EU","EE","EST",233,"Estonia"},
        {"AF","ET","ETH",231,"Ethiopia"},
        {"EU","FO","FRO",234,"Faroe_Islands"},
        {"SA","FK","FLK",238,"Falkland_Islands"},
        {"OC","FJ","FJI",242,"Fiji"},
        {"EU","FI","FIN",246,"Finland"},
        {"EU","FR","FRA",250,"France"},
        {"SA","GF","GUF",254,"French_Guiana"},
        {"OC","PF","PYF",258,"French_Polynesia"},
        {"AN","TF","ATF",260,"French_Southern_Territories"},
        {"AF","GA","GAB",266,"Gabon"},
        {"AF","GM","GMB",270,"Gambia"},
        {"AS","GE","GEO",268,"Georgia"},
        {"EU","DE","DEU",276,"Germany"},
        {"AF","GH","GHA",288,"Ghana"},
        {"EU","GI","GIB",292,"Gibraltar"},
        {"EU","GR","GRC",300,"Greece"},
        {"NA","GL","GRL",304,"Greenland"},
        {"NA","GD","GRD",308,"Grenada"},
        {"NA","GP","GLP",312,"Guadeloupe"},
        {"OC","GU","GUM",316,"Guam"},
        {"NA","GT","GTM",320,"Guatemala"},
        {"EU","GG","GGY",831,"Guernsey"},
        {"AF","GN","GIN",324,"Guinea"},
        {"AF","GW","GNB",624,"Guinea-Bissau"},
        {"SA","GY","GUY",328,"Guyana"},
        {"NA","HT","HTI",332,"Haiti"},
        {"AN","HM","HMD",334,"Heard_Island_and_McDonald_Islands"},
        {"EU","VA","VAT",336,"Holy_See_"},
        {"NA","HN","HND",340,"Honduras"},
        {"AS","HK","HKG",344,"Hong_Kong"},
        {"EU","HU","HUN",348,"Hungary"},
        {"EU","IS","ISL",352,"Iceland"},
        {"AS","IN","IND",356,"India"},
        {"AS","ID","IDN",360,"Indonesia"},
        {"AS","IR","IRN",364,"Iran"},
        {"AS","IQ","IRQ",368,"Iraq"},
        {"EU","IE","IRL",372,"Ireland"},
        {"EU","IM","IMN",833,"Isle_of_Man"},
        {"AS","IL","ISR",376,"Israel"},
        {"EU","IT","ITA",380,"Italy"},
        {"NA","JM","JAM",388,"Jamaica"},
        {"AS","JP","JPN",392,"Japan"},
        {"EU","JE","JEY",832,"Jersey"},
        {"AS","JO","JOR",400,"Jordan"},
        {"AS","KZ","KAZ",398,"Kazakhstan"},
        {"AF","KE","KEN",404,"Kenya"},
        {"OC","KI","KIR",296,"Kiribati"},
        {"AS","KP","PRK",408,"Korea"},
        {"AS","KR","KOR",410,"Korea"},
        {"AS","KW","KWT",414,"Kuwait"},
        {"AS","KG","KGZ",417,"Kyrgyz_Republic"},
        {"AS","LA","LAO",418,"Lao_People's_Democratic_Republic"},
        {"EU","LV","LVA",428,"Latvia"},
        {"AS","LB","LBN",422,"Lebanon"},
        {"AF","LS","LSO",426,"Lesotho"},
        {"AF","LR","LBR",430,"Liberia"},
        {"AF","LY","LBY",434,"Libyan_Arab_Jamahiriya"},
        {"EU","LI","LIE",438,"Liechtenstein"},
        {"EU","LT","LTU",440,"Lithuania"},
        {"EU","LU","LUX",442,"Luxembourg"},
        {"AS","MO","MAC",446,"Macao"},
        {"EU","MK","MKD",807,"Macedonia"},
        {"AF","MG","MDG",450,"Madagascar"},
        {"AF","MW","MWI",454,"Malawi"},
        {"AS","MY","MYS",458,"Malaysia"},
        {"AS","MV","MDV",462,"Maldives"},
        {"AF","ML","MLI",466,"Mali"},
        {"EU","MT","MLT",470,"Malta"},
        {"OC","MH","MHL",584,"Marshall_Islands"},
        {"NA","MQ","MTQ",474,"Martinique"},
        {"AF","MR","MRT",478,"Mauritania"},
        {"AF","MU","MUS",480,"Mauritius"},
        {"AF","YT","MYT",175,"Mayotte"},
        {"NA","MX","MEX",484,"Mexico"},
        {"OC","FM","FSM",583,"Micronesia"},
        {"EU","MD","MDA",498,"Moldova"},
        {"EU","MC","MCO",492,"Monaco"},
        {"AS","MN","MNG",496,"Mongolia"},
        {"EU","ME","MNE",499,"Montenegro"},
        {"NA","MS","MSR",500,"Montserrat"},
        {"AF","MA","MAR",504,"Morocco"},
        {"AF","MZ","MOZ",508,"Mozambique"},
        {"AS","MM","MMR",104,"Myanmar"},
        {"AF","NA","NAM",516,"Namibia"},
        {"OC","NR","NRU",520,"Nauru"},
        {"AS","NP","NPL",524,"Nepal"},
        {"NA","AN","ANT",530,"Netherlands_Antilles"},
        {"EU","NL","NLD",528,"Netherlands"},
        {"OC","NC","NCL",540,"New_Caledonia"},
        {"OC","NZ","NZL",554,"New_Zealand"},
        {"NA","NI","NIC",558,"Nicaragua"},
        {"AF","NE","NER",562,"Niger"},
        {"AF","NG","NGA",566,"Nigeria"},
        {"OC","NU","NIU",570,"Niue"},
        {"OC","NF","NFK",574,"Norfolk_Island"},
        {"OC","MP","MNP",580,"Northern_Mariana_Islands"},
        {"EU","NO","NOR",578,"Norway"},
        {"AS","OM","OMN",512,"Oman"},
        {"AS","PK","PAK",586,"Pakistan"},
        {"OC","PW","PLW",585,"Palau"},
        {"AS","PS","PSE",275,"Palestinian_Territory"},
        {"NA","PA","PAN",591,"Panama"},
        {"OC","PG","PNG",598,"Papua_New_Guinea"},
        {"SA","PY","PRY",600,"Paraguay"},
        {"SA","PE","PER",604,"Peru"},
        {"AS","PH","PHL",608,"Philippines"},
        {"OC","PN","PCN",612,"Pitcairn_Islands"},
        {"EU","PL","POL",616,"Poland"},
        {"EU","PT","PRT",620,"Portugal"},
        {"NA","PR","PRI",630,"Puerto_Rico"},
        {"AS","QA","QAT",634,"Qatar"},
        {"AF","RE","REU",638,"Reunion"},
        {"EU","RO","ROU",642,"Romania"},
        {"EU","RU","RUS",643,"Russian_Federation"},
        {"AF","RW","RWA",646,"Rwanda"},
        {"NA","BL","BLM",652,"Saint_Barthelemy"},
        {"AF","SH","SHN",654,"Saint_Helena"},
        {"NA","KN","KNA",659,"Saint_Kitts_and_Nevis"},
        {"NA","LC","LCA",662,"Saint_Lucia"},
        {"NA","MF","MAF",663,"Saint_Martin"},
        {"NA","PM","SPM",666,"Saint_Pierre_and_Miquelon"},
        {"NA","VC","VCT",670,"Saint_Vincent_and_the_Grenadines"},
        {"OC","WS","WSM",882,"Samoa"},
        {"EU","SM","SMR",674,"San_Marino"},
        {"AF","ST","STP",678,"Sao_Tome_and_Principe"},
        {"AS","SA","SAU",682,"Saudi_Arabia"},
        {"AF","SN","SEN",686,"Senegal"},
        {"EU","RS","SRB",688,"Serbia"},
        {"AF","SC","SYC",690,"Seychelles"},
        {"AF","SL","SLE",694,"Sierra_Leone"},
        {"AS","SG","SGP",702,"Singapore"},
        {"EU","SK","SVK",703,"Slovakia"},
        {"EU","SI","SVN",705,"Slovenia"},
        {"OC","SB","SLB",90,"Solomon_Islands"},
        {"AF","SO","SOM",706,"Somalia"},
        {"AF","ZA","ZAF",710,"South_Africa"},
        {"AN","GS","SGS",239,"South_Georgia_and_the_South_Sandwich_Islands"},
        {"EU","ES","ESP",724,"Spain"},
        {"AS","LK","LKA",144,"Sri_Lanka"},
        {"AF","SD","SDN",736,"Sudan"},
        {"SA","SR","SUR",740,"Suriname"},
        {"EU","SJ","SJM",744,"Svalbard_&_Jan_Mayen_Islands"},
        {"AF","SZ","SWZ",748,"Swaziland"},
        {"EU","SE","SWE",752,"Sweden"},
        {"EU","CH","CHE",756,"Switzerland"},
        {"AS","SY","SYR",760,"Syrian_Arab_Republic"},
        {"AS","TW","TWN",158,"Taiwan"},
        {"AS","TJ","TJK",762,"Tajikistan"},
        {"AF","TZ","TZA",834,"Tanzania"},
        {"AS","TH","THA",764,"Thailand"},
        {"AS","TL","TLS",626,"Timor-Leste"},
        {"AF","TG","TGO",768,"Togo"},
        {"OC","TK","TKL",772,"Tokelau"},
        {"OC","TO","TON",776,"Tonga"},
        {"NA","TT","TTO",780,"Trinidad_and_Tobago"},
        {"AF","TN","TUN",788,"Tunisia"},
        {"AS","TR","TUR",792,"Turkey"},
        {"AS","TM","TKM",795,"Turkmenistan"},
        {"NA","TC","TCA",796,"Turks_and_Caicos_Islands"},
        {"OC","TV","TUV",798,"Tuvalu"},
        {"AF","UG","UGA",800,"Uganda"},
        {"EU","UA","UKR",804,"Ukraine"},
        {"AS","AE","ARE",784,"United_Arab_Emirates"},
        {"EU","GB","GBR",826,"United_Kingdom_of_Great_Britain_&_Northern_Ireland"},
        {"NA","US","USA",840,"United_States_of_America"},
        {"OC","UM","UMI",581,"United_States_Minor_Outlying_Islands"},
        {"NA","VI","VIR",850,"United_States_Virgin_Islands"},
        {"SA","UY","URY",858,"Uruguay"},
        {"AS","UZ","UZB",860,"Uzbekistan"},
        {"OC","VU","VUT",548,"Vanuatu"},
        {"SA","VE","VEN",862,"Venezuela"},
        {"AS","VN","VNM",704,"Vietnam"},
        {"OC","WF","WLF",876,"Wallis_and_Futuna"},
        {"AF","EH","ESH",732,"Western_Sahara"},
        {"AS","YE","YEM",887,"Yemen"},
        {"AF","ZM","ZMB",894,"Zambia"},
        {"AF","ZW","ZWE",716,"Zimbabwe"},
    };


}
