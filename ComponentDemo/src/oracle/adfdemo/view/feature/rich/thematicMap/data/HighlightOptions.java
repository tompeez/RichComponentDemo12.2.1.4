package oracle.adfdemo.view.feature.rich.thematicMap.data;

public class HighlightOptions {
    public HighlightOptions() {
        super();
    }
    
    public static String NONE = "None";
    
    private String highlightBy = NONE;
    private boolean highlighted;

    private String highlightValue = NONE;

    private AttributeChoices.Type type = AttributeChoices.Type.NONE;

    public void setHighlightBy(String highlightBy) {
        this.highlightBy = highlightBy;
    }

    public String getHighlightBy() {
        return highlightBy;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlightValue(String highlightValue) {
        this.highlightValue = highlightValue;
    }

    public String getHighlightValue() {
        return highlightValue;
    }

    public void setType(AttributeChoices.Type type) {
        this.type = type;
    }

    public AttributeChoices.Type getType() {
        return type;
    }

    public String getTypeString() {
        return type.toString();
    }
}
