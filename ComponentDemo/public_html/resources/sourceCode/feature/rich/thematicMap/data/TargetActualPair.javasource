package oracle.adfdemo.view.feature.rich.thematicMap.data;

import java.util.HashSet;
import java.util.Set;

public class TargetActualPair {

    
    private String itemPrefix;
    private String target;
    private String actual;

    public TargetActualPair(String target, String actual,String itemPrefix) {
        super();
        this.itemPrefix = itemPrefix;
        this.target = target;
        this.actual = actual;
    }

    public void setItemPrefix(String itemPrefix) {
        this.itemPrefix = itemPrefix;
    }

    public String getItemPrefix() {
        return itemPrefix;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getActual() {
        return actual;
    }

    public Set<String> getSizeAttributes() {
        Set<String> values = new HashSet<String>();
        values.add(actual);
        values.add(target);
        return values;
    }
}
