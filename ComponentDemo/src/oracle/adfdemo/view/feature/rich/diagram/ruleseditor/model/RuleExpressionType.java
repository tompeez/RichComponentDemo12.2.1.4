package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model;

/**
 * ENUM which defines the range of expressions that can be used in constructing a rule string
 * Each type has a text version (label) that is used for display in the diagram and a value which is actually used 
 * in the generated expression e.g. the label is "is not empty" and the generated expression will read "!= null"
 * The type can also wrap the value in a prefix suffix pair - only used here for "like" to add wildcards but it could 
 * be a function call wrapper as well.
 * @see RuleDiagramConditionNode getFormattedNodeExpression()
 */
public enum RuleExpressionType {
    EQ      ("is","=="),
    NEQ     ("is not","!="),
    GT      ("greater than", ">"),
    LT      ("less than", "<"),
    GTE     ("greater than or equal to", ">="),
    LTE     ("less than or equal to", "<="),
    NOTNULL ("is not empty", "!= null",false,"",""),
    NULL    ("is empty", "== null",false,"",""),
    CNT     ("contains", "like",true,"%","%")
    ;
    
    private final String _label;  
    private final String _value;
    private String _prefix = "";
    private String _suffix = "";
    private boolean _incValue = true;
    
    RuleExpressionType(String label, String value) {
        _label = label;
        _value = value;
    }

    RuleExpressionType(String label, String value,boolean includeValue, String prefix, String suffix) {
        _label = label;
        _value = value;
        _prefix = prefix;
        _suffix = suffix;
        _incValue = includeValue;
    }
    
    public String getLabel(){
        return _label;
    }
    
    public String getValue(){
        return _value;
    }
    
    public String getPrefix() {
        return _prefix;
    }

    public String getSuffix() {
        return _suffix;
    }  
    
    public boolean showValue(){
        return _incValue;
    }                       
}
