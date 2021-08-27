package oracle.adfdemo.view.feature.rich.hv.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeResult {
    public EmployeeResult(EmployeeNode employee, String matchingAttribute,
                          String matchingValue, String pattern) {
        this.employee = employee;
        this.matchingAttribute = matchingAttribute;
        this.matchingValue = matchingValue;
        this.pattern = pattern;
    }

    private EmployeeNode employee;

    private String matchingAttribute;

    private String matchingValue;

    private String pattern;

    public void setEmployee(EmployeeNode employee) {
        this.employee = employee;
    }

    public EmployeeNode getEmployee() {
        return employee;
    }

    public void setMatchingAttribute(String matchingAttribute) {
        this.matchingAttribute = matchingAttribute;
    }

    public String getMatchingAttribute() {
        return matchingAttribute;
    }

    public void setMatchingValue(String matchingValue) {
        this.matchingValue = matchingValue;
    }

    public String getMatchingValue() {
        return matchingValue;
    }

    public String getMatchingHtml() {
        String item = pattern.replace("*", "").replace(".", "");

        Pattern p = Pattern.compile(item, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(matchingValue);
        StringBuffer sb = new StringBuffer();
        sb.append("<p>");
        boolean result = m.find();
        while (result) {
            m.appendReplacement(sb, m.group());
            result = m.find();
        }
        m.appendTail(sb);
        sb.append("</p>");

        String html = sb.toString();
        return html;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
