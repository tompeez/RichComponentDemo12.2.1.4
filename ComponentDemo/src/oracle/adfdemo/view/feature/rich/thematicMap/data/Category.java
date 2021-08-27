package oracle.adfdemo.view.feature.rich.thematicMap.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oracle.adfdemo.view.feature.rich.thematicMap.XMLTreeProvider;

import oracle.adfdemo.view.feature.rich.thematicMap.data.MajorNodeXML;

import oracle.xml.parser.v2.XMLElement;

public class Category extends MajorNodeXML {

    public static String NAME = "name";

    public static String SPENDING = "spending";
    public static String BUDGET = "budget";
    public static String ASSIGNED = "assigned";
    public static String COMPLETED = "completed";

    public static String ACTUAL = "actual";
    public static String TARGET = "target";


    public static String BUDGET_SPENDING = "budget_spending";
    public static String ASSIGNED_COMPLETED = "assigned_completed";

    public static String TARGET_ACTUAL = "target_actual";


    public static Map<String, String> attributes =
        new HashMap<String, String>();

    public static Map<String, TargetActualPair> itemPairs =
        new HashMap<String, TargetActualPair>();


    static {

        attributes.put(SPENDING, "Spending");
        attributes.put(BUDGET, "Budget");
        attributes.put(ACTUAL, "Actual");
        attributes.put(TARGET, "Target");
        attributes.put(ASSIGNED, "Assigned");
        attributes.put(COMPLETED, "Completed");

        attributes.put(BUDGET_SPENDING, "Budget vs Spending");
        attributes.put(TARGET_ACTUAL, "Target vs Actual");
        attributes.put(ASSIGNED_COMPLETED, "Assigned vs Completed");

        itemPairs.put(BUDGET_SPENDING,
                      new TargetActualPair(BUDGET, SPENDING, "$"));
        itemPairs.put(ASSIGNED_COMPLETED,
                      new TargetActualPair(ASSIGNED, COMPLETED, ""));
        itemPairs.put(TARGET_ACTUAL, new TargetActualPair(TARGET, ACTUAL, ""));


    }

    public Category(XMLTreeProvider xmlProvider, XMLElement ele) {
        super(xmlProvider, ele);
        if (ele == null)
            System.out.println("ERROR NULL");
    }

    public Category(XMLTreeProvider xmlProvider, XMLElement ele,
                    boolean isAnchor) {
        super(xmlProvider, ele, isAnchor);
        if (ele == null)
            System.out.println("ERROR NULL");

    }

    public Category(Category c) {
        this(c._xmlProvider, c._ele);
    }

    public MajorNodeXML createNew(XMLTreeProvider xmlProvider,
                                  XMLElement elem) {

        return new Category(xmlProvider, elem);
    }

    public MajorNodeXML createNew(XMLTreeProvider xmlProvider, XMLElement ele,
                                  boolean isAnchor) {
        return new Category(xmlProvider, ele, isAnchor);
    }

    public Set<String> getAttributeListing() {
        return attributes.keySet();
    }

    public boolean isHighlighted() {
        return false;
    }

    public String getName() {
        return getAttribute(NAME);
    }

    private int width = -1;

    public int getWidth() {
        if (width == -1) {
            return 200;
        } else {
            return width;
        }
    }

    private Map<String, Double> values = new HashMap<String, Double>();

    public double getValue(String item) {
        if (!values.containsKey(item)) {
            generateValues(item);
        }
        return values.get(item);
    }

    public void generateWidths(String item, int maxWidth) {
        width = maxWidth;
        if (childElements != null) {

            Map<Category, Double> vals = new HashMap<Category, Double>();
            double total = 0;
            for (AbstractNode n : childElements) {
                Category c = (Category)n;
                double v = c.getValue(item);
                vals.put(c, v);
                total += v;
            }
            for (Category c : vals.keySet()) {
                int newWidth = (int)((vals.get(c) / total) * maxWidth);
                c.generateWidths(item, newWidth);
            }
            Collections.sort(childElements);
        }
    }

    public void generateValues(String item) {
        if (childElements == null || childElements.size() == 0) {
            String attr = getAttribute(item);
            if (attr == null || "".equals(attr)) {
                values.put(item,0.0);
            } else {
                values.put(item, Double.parseDouble(attr));
            }
        } else {
            double total = 0;
            for (AbstractNode n : childElements) {
                Category c = (Category)n;
                total += c.getValue(item);
            }
            values.put(item, total);
        }
    }

    public int getMaxDeviation(String pair) {
        int max =  (int)Math.abs(getValue(itemPairs.get(pair).getTarget()) -
                             getValue(itemPairs.get(pair).getActual()));
        if (childElements == null || childElements.size() == 0) {
            return max;
        }
        
        for (AbstractNode n : childElements) {
            Category c = (Category)n;
            int val = c.getMaxDeviation(pair);
            if (val > max) {
                max = val;
            }
        }
        return max;
    }
    

    public String getNodeId() {

        return getName();

    }


    public int compareTo(Object o) {
        if (o instanceof Category) {
            Category c = (Category) o;
            if (width > c.width) {
                return 1;
            } else if (width == c.width) {
                return 0;
            } else {
                return -1;
            }
        }
        return 0;
    }
}
