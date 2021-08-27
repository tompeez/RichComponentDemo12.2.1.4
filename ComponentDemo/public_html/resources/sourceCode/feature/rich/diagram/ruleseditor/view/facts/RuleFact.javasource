package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.view.facts;

import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleExpressionType;
import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleValueType;

public class RuleFact implements Comparable<RuleFact> {
    private String _factName;
    private RuleValueType _factType;
    private RuleExpressionType _defaultExpression;
    private String _initialValue;
    
    
    public RuleFact() {
    }   
    
    public RuleFact(String name, RuleValueType type, RuleExpressionType defaultType, String initialValue) {
        setFactName(name);
        setFactType(type);
        setDefaultExpression(defaultType);
        setInitialValue(initialValue);
    }   
    
    public void setFactName(String _factName) {
        this._factName = _factName;
    }

    public String getFactName() {
        return _factName;
    }

    public void setFactType(RuleValueType _factType) {
        this._factType = _factType;
    }

    public RuleValueType getFactTypeRaw() {
        return _factType;
    }
    
    public String getFactType() {
        return _factType.toString();
    }  

    public void setDefaultExpression(RuleExpressionType _defaultExpression) {
        this._defaultExpression = _defaultExpression;
    }

    public String getDefaultExpression() {
        return _defaultExpression.toString();
    }

    public void setInitialValue(String _initialValue) {
        this._initialValue = _initialValue;
    }

    public String getInitialValue() {
        return _initialValue;
    }

    @Override
    public int compareTo(RuleFact o) {
        return _factName.compareTo(o.getFactName());
    }
}
