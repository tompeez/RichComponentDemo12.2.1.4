package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;


public class SampleAttributeGroupData extends ArrayList<SampleAttributeGroupData.AttributeGroupData> {
    public SampleAttributeGroupData() {
        for (int i = 0; i < attributeGroupData.length; i++) {
            Object data[] = attributeGroupData[i];
            this.add(new AttributeGroupData((String)data[0], (String)data[1],
                                            (String)data[2], (String)data[3],
                                            (Integer)data[4]));
        }

    }

    public static final String GROUP_A = "Group A";
    public static final String GROUP_B = "Group B";
    public static final String GROUP_C = "Group C";
    public static final String GROUP_1 = "Group 1";
    public static final String GROUP_2 = "Group 2";
    public static final String GROUP_3 = "Group 3";
    public static final String GROUP_4 = "Group 4";

    private static final Object attributeGroupData[][] =
    { { "AL", "Alabama", GROUP_A, GROUP_1, 24 },
      { "AK", "Alaska", GROUP_B, GROUP_1, 63 },
      { "AZ", "Arizona", GROUP_C, GROUP_1, 15 },
      { "AR", "Arkansas", GROUP_A, GROUP_1, 76 },
      { "CA", "California", GROUP_B, GROUP_1, 3 },
      { "CO", "Colorado", GROUP_C, GROUP_1, 44 },
      { "CT", "Connecticut", GROUP_A, GROUP_1, 82 },
      { "DE", "Delaware", GROUP_B, GROUP_2, 55 },
      { "FL", "Florida", GROUP_C, GROUP_2, 61 },
      { "GA", "Georgia", GROUP_A, GROUP_2, 13 },
      { "HI", "Hawaii", GROUP_B, GROUP_2, 71 },
      { "ID", "Idaho", GROUP_C, GROUP_2, 9 },
      { "IL", "Illinois", GROUP_A, GROUP_2, 34 },
      { "IN", "Indiana", GROUP_B, GROUP_2, 17 },
      { "IA", "Iowa", GROUP_C, GROUP_2, 76 },
      { "KS", "Kansas", GROUP_A, GROUP_2, 52 },
      { "KY", "Kentucky", GROUP_B, GROUP_2, 42 },
      { "LA", "Louisiana", GROUP_C, GROUP_3, 12 },
      { "ME", "Maine", GROUP_A, GROUP_3, 43 },
      { "MD", "Maryland", GROUP_B, GROUP_3, 23 },
      { "MA", "Massachusetts", GROUP_C, GROUP_3, 13 },
      { "MI", "Michigan", GROUP_A, GROUP_3, 77 },
      { "MN", "Minnesota", GROUP_B, GROUP_3, 90 },
      { "MS", "Mississippi", GROUP_C, GROUP_3, 17 },
      { "MO", "Missouri", GROUP_A, GROUP_3, 8 },
      { "MT", "Montana", GROUP_B, GROUP_3, 65 },
      { "NE", "Nebraska", GROUP_C, GROUP_3, 54 },
      { "NV", "Nevada", GROUP_A, GROUP_3, 57 },
      { "NH", "New Hampshire", GROUP_B, GROUP_3, 43 },
      { "NJ", "New Jersey", GROUP_C, GROUP_3, 21 },
      { "NM", "New Mexico", GROUP_A, GROUP_3, 29 },
      { "NY", "New York", GROUP_B, GROUP_3, 43 },
      { "NC", "North Carolina", GROUP_C, GROUP_3, 39 },
      { "ND", "North Dakota", GROUP_A, GROUP_3, 87 },
      { "OH", "Ohio", GROUP_B, GROUP_3, 52 },
      { "OK", "Oklahoma", GROUP_C, GROUP_3, 11 },
      { "OR", "Oregon", GROUP_A, GROUP_3, 4 },
      { "PA", "Pennsylvania", GROUP_B, GROUP_3, 84 },
      { "RI", "Rhode Island", GROUP_C, GROUP_4, 63 },
      { "SC", "South Carolina", GROUP_A, GROUP_4, 23 },
      { "SD", "South Dakota", GROUP_B, GROUP_4, 73 },
      { "TN", "Tennessee", GROUP_C, GROUP_4, 55 },
      { "TX", "Texas", GROUP_A, GROUP_4, 51 },
      { "UT", "Utah", GROUP_B, GROUP_4, 61 },
      { "VT", "Vermont", GROUP_C, GROUP_4, 45 },
      { "VA", "Virginia", GROUP_A, GROUP_4, 37 },
      { "WA", "Washington", GROUP_B, GROUP_4, 56 },
      { "WV", "West Virginia", GROUP_C, GROUP_4, 58 },
      { "WI", "Wisconsin", GROUP_A, GROUP_4, 18 },
      { "WY", "Wyoming", GROUP_B, GROUP_4, 12 } };


    public static class AttributeGroupData {
        private String name;
        private String category1;
        private String category2;
        private int value;
        private String fullName;

        AttributeGroupData(String name, String fullName, String category1,
                           String category2, int value) {
            this.name = name;
            this.category1 = category1;
            this.category2 = category2;
            this.value = value;
            this.fullName = fullName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }


        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String toString() {
            return "ColorData[name=" + name + "]";
        }

        public void setCategory1(String category1) {
            this.category1 = category1;
        }

        public String getCategory1() {
            return category1;
        }

        public void setCategory2(String category2) {
            this.category2 = category2;
        }

        public String getCategory2() {
            return category2;
        }
    }
}
