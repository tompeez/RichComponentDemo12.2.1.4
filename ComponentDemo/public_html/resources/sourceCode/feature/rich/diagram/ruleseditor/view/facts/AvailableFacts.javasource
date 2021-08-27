package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.view.facts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleExpressionType;
import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleValueType;


public class AvailableFacts {
    private List<RuleFact> _facts;

    public AvailableFacts() {
    }

    public void setFacts(List<RuleFact> _facts) {
        this._facts = _facts;
    }

    public List<RuleFact> getFacts() {
        if (_facts == null) {
            initData();
        }
        return _facts;
    }


    private void initData() {
        _facts = new ArrayList<RuleFact>(10);
        _facts.add(new RuleFact("CustomerName", RuleValueType.STRING,RuleExpressionType.EQ,"Some Customer Name"));
        _facts.add(new RuleFact("CustomerClass", RuleValueType.STRING,RuleExpressionType.EQ,"GOLD"));
        _facts.add(new RuleFact("Status", RuleValueType.STRING,RuleExpressionType.EQ,"FULFILLED"));
        _facts.add(new RuleFact("OrderValue", RuleValueType.FLOAT,RuleExpressionType.GT,"0"));
        _facts.add(new RuleFact("ClosedDays", RuleValueType.INTEGER,RuleExpressionType.GT,"0"));
        _facts.add(new RuleFact("InLoyaltyProgram", RuleValueType.BOOLEAN,RuleExpressionType.EQ,"TRUE"));
        _facts.add(new RuleFact("CustomerRegion", RuleValueType.STRING,RuleExpressionType.EQ,"EMEA"));
        _facts.add(new RuleFact("CustomerCountry", RuleValueType.STRING,RuleExpressionType.EQ,"UK"));
        _facts.add(new RuleFact("OrderDate", RuleValueType.DATE,RuleExpressionType.GT,"01-JAN-2014"));
        _facts.add(new RuleFact("DispatchDate", RuleValueType.DATE,RuleExpressionType.GT,"01-JAN-2014"));

        Collections.sort(_facts);

    }


}
