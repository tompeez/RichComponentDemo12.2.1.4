package oracle.adfdemo.view.feature.rich.thematicMap.data;

import java.util.List;

public class AttributeChoices {
    public enum Type { NONE, SELECTONE, NUMBER };
    
    Type type;
    List<String> options;
    
    public AttributeChoices(Type t, List<String> options) {
        super();
        this.type = t;
        this.options = options;
        
    }
    public void setType(AttributeChoices.Type type) {
        this.type = type;
    }

    public AttributeChoices.Type getType() {
        return type;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }

   
}
