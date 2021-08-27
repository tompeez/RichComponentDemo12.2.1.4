package oracle.adfdemo.view.components.rich;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputSearch;


/**
 * Demo bean to switch between client templates dynamically.
 * This bean is SessionScoped
 */
public class TemplateCustomizerBean implements Serializable
{
  /**
   * Setter of the value property for selectManyCheckbox that is used to dynamically choose
   * the list of attributes to be displayed in the suggestions panel.
   */
  public void setDisplayInclusions(List<String> displayInclusions)
  {
    this._displayInclusions = new CopyOnWriteArrayList<String>(displayInclusions);
  }

  /**
   * Getter of the value property for selectManyCheckbox that is used to dynamically choose
   * the list of attributes to be displayed in the suggestions panel.
   */
  public List getDisplayInclusions()
  {
    return Collections.unmodifiableList(_displayInclusions);
  }

  /**
   * ValueChangeListener of selectManyCheckbox component present in the page.
   * The template is updated based on the new selected attributes in the selectManyCheckbox
   * component.
   */
  public void displayInclusionsChanged(ValueChangeEvent valueChangeEvent)
  {
    List<String> selections = new ArrayList<String>();

    if (valueChangeEvent.getNewValue() != null)
    {
      selections.addAll((List<String>) valueChangeEvent.getNewValue());
    }

    boolean reqProfilePic = selections.contains(_PHOTO);
    selections.remove(_PHOTO);
    selections.remove(_NAME);

    // 1) Name is mandatory, but it wont be present in selections since its disabled
    // 2) Profile photo is supported only in list mode
    int additionallInfoCount = selections.size();
    switch (additionallInfoCount)
    {
      case 0:
        _setNameOnlyTemplate(reqProfilePic);
        break;

      case 1:
        _setNameAndOneAttributeTemplate(reqProfilePic, selections);
        break;

      case 2:
        _setNameAndTwoAttributesTemplate(reqProfilePic, selections);
        break;

      case 3:
        _setNameAndThreeAttributesTemplate(reqProfilePic, selections);
        break;

      case 4:
        _setAllAttributesTemplate(reqProfilePic, selections);
        break;
    }
  }

  /**
   * Getter for content template's value
   */
  public String getClientTemplate()
  {
    return getTableMode() ? _template.getTrTemplate() : _template.getLiTemplate();
  }

  /**
   * Getter for header template's value
   */
  public String getHeaderTemplate()
  {
    return _template.getHeaderTemplate();
  }

  /**
   * Setter of selectBooleanCheckbox that is used to toggle between table and list display
   */
  public synchronized void setTableMode(boolean isTableMode)
  {
    this._contentMode = isTableMode ? "table": "list";
  }

  /**
   * Getter of selectBooleanCheckbox that is used to toggle between table and list display
   */
  public boolean getTableMode()
  {
    return "table".equals(this._contentMode);
  }

  /**
   * Returns the contentMode as set by the "Enable Table Mode" selectBooleanCheckbox component
   */
  public RichInputSearch.ContentMode getContentMode()
  {
    return RichInputSearch.ContentMode.valueOfDisplayName(this._contentMode);
  }

  /**
   * Provides the filter attributes based on the template chosen
   */
  public List<String> getFilterAttributes()
  {
    List<String> filterAttrs = new ArrayList<String>(Arrays.asList("fName", "lName"));
    if (this._displayInclusions != null)
    {
      filterAttrs.addAll(this._displayInclusions);
    }
    filterAttrs.remove(_PHOTO);

    return Collections.unmodifiableList(filterAttrs);
  }

  public Set<Integer> getEmployeesSet()
  {
    return Collections.unmodifiableSet(_employeesSet);
  }

  public void setEmployeesSet(Set<Integer> employees)
  {
    _employeesSet = new CopyOnWriteArraySet<Integer>(employees);
  }

  /**
   * Provides the header labels for the header template
   */
  private String _getAttributeLabel(String attribute)
  {
    if (_NAME.equals(attribute))
    {
      return "Name";
    }

    if (_PHOTO.equals(attribute))
    {
      return "Profile";
    }

    if (_DESIGNATION.equals(attribute))
    {
      return "Designation";
    }

    if (_DEPARTMENT.equals(attribute))
    {
      return "Department";
    }

    if (_LOCATION.equals(attribute))
    {
      return "Location";
    }

    if (_EMAIL.equals(attribute))
    {
      return "Email";
    }

    return "";
  }

  /**
   * Sets the template when all attributes are to be displayed.
   * List mode: Show the first two attributes with corresponding label.
   * One of the attribute inside parenthesis, and the other just inline
   */
  private void _setAllAttributesTemplate(boolean reqProfilePic, List<String> selections)
  {
    String liTemplate = reqProfilePic ? _TMPLT_BASE_PLUS_4: _TMPLT_NO_PROFILE_BASE_PLUS_4;
    liTemplate = liTemplate.replace("#s1#", selections.get(0));

    String s2 = selections.get(1);
    String s2Lbl = (_EMAIL.equals(s2))? "Email": (_LOCATION.equals(s2) ? "Location" : "Department");
    liTemplate = liTemplate.replace("#s2-LBL#", s2Lbl);
    liTemplate = liTemplate.replace("#s2#", selections.get(1));

    String s3 = selections.get(2);
    String s3Lbl =
      (_EMAIL.equals(s3))? "Email": ((_LOCATION.equals(s3))?
                                     "Location":
                                     ((_DEPARTMENT.equals(s3))?
                                      "Department": "Desig."));
    liTemplate = liTemplate.replace("#s3-LBL#", s3Lbl);
    liTemplate = liTemplate.replace("#s3#", s3);

    String s4 = selections.get(3);
    String s4Lbl =
      (_EMAIL.equals(s4))? "Email": ((_LOCATION.equals(s4))?
                                     "Location":
                                     ((_DEPARTMENT.equals(s4))?
                                      "Department": "Desig."));
    liTemplate = liTemplate.replace("#s4-LBL#", s4Lbl);
    liTemplate = liTemplate.replace("#s4#", s4);

    String headerTemplate =
        "<th>Name</th><th>" + _getAttributeLabel(selections.get(0)) +
        "</th><th>" + _getAttributeLabel(s2) + "</th><th>" +
        _getAttributeLabel(s3) + "</th><th>" +
        _getAttributeLabel(s4) + "</th>";
    String trTemplate =
        "<td>{{fName}} {{lName}}</td><td>{{" + selections.get(0) +
        "}}</td><td>{{" + s2 + "}}</td><td>{{" + s3 +
        "}}</td><td>{{" + s4 + "}}</td>";

    _setTemplate(liTemplate, trTemplate, headerTemplate);
  }

  /**
   * Sets the template when Name + one other attribute is to be displayed
   */
  private void _setNameAndOneAttributeTemplate(boolean reqProfilePic, List<String> selections)
  {
    String liTemplate = reqProfilePic ? _TMPLT_BASE_PLUS_1: _TMPLT_NO_PROFILE_BASE_PLUS_1;
    liTemplate = liTemplate.replace("#s1#", selections.get(0));

    String headerTemplate =
        "<th>Name</th><th>" + _getAttributeLabel(selections.get(0)) +
        "</th>";
    String trTemplate =
        "<td>{{fName}} {{lName}}</td><td>{{" + selections.get(0) +
        "}}</td>";

    _setTemplate(liTemplate, trTemplate, headerTemplate);
  }

  /**
   * Sets the templates when Name + 2 attributes are to be displayed.
   * One of the attributes will be Email, Location or Department.
   * List mode: The template will display the corresponding label for one of these attributes.
   */
  private void _setNameAndTwoAttributesTemplate(boolean reqProfilePic, List<String> selections)
  {
    String liTemplate = reqProfilePic ? _TMPLT_BASE_PLUS_2: _TMPLT_NO_PROFILE_BASE_PLUS_2;
    liTemplate = liTemplate.replace("#s1#", selections.get(0));

    String s2 = selections.get(1);
    String s2Lbl = _EMAIL.equals(s2) ? "Email": (_LOCATION.equals(s2) ? "Location": "Department");
    liTemplate = liTemplate.replace("#s2-LBL#", s2Lbl);
    liTemplate = liTemplate.replace("#s2#", s2);

    String headerTemplate =
        "<th>Name</th><th>" + _getAttributeLabel(selections.get(0)) +
        "</th><th>" + _getAttributeLabel(s2) + "</th>";
    String trTemplate =
        "<td>{{fName}} {{lName}}</td><td>{{" + selections.get(0) +
        "}}</td><td>{{" + s2 + "}}</td>";

    _setTemplate(liTemplate, trTemplate, headerTemplate);
  }

  /**
   * Sets the template when Name + 3 attributes are to be displayed.
   * Two attributes are guaranteed to be Email, Location or Department.
   * List Mode: The template will display the corresponding label for one of these attributes,
   * and other attribute value will be wrapped in parenthesis.
   */
  private void _setNameAndThreeAttributesTemplate(boolean reqProfilePic, List<String> selections)
  {
    String liTemplate = reqProfilePic ? _TMPLT_BASE_PLUS_3: _TMPLT_NO_PROFILE_BASE_PLUS_3;
    liTemplate = liTemplate.replace("#s1#", selections.get(0));

    String s2 = selections.get(1);
    String s2Lbl = _EMAIL.equals(s2)? "Email": (_LOCATION.equals(s2) ? "Location" : "Department");
    liTemplate = liTemplate.replace("#s2-LBL#", s2Lbl);
    liTemplate = liTemplate.replace("#s2#", s2);

    String s3 = selections.get(2);
    String s3Lbl = _EMAIL.equals(s3)? "Email": (_LOCATION.equals(s3) ? "Location" : "Department");
    liTemplate = liTemplate.replace("#s3-LBL#", s3Lbl);
    liTemplate = liTemplate.replace("#s3#", s3);

    String headerTemplate =
        "<th>Name</th><th>" + _getAttributeLabel(selections.get(0)) +
        "</th><th>" + _getAttributeLabel(s2) + "</th><th>" +
        _getAttributeLabel(s3) + "</th>";
    String trTemplate =
        "<td>{{fName}} {{lName}}</td><td>{{" + selections.get(0) +
        "}}</td><td>{{" + s2 + "}}</td><td>{{" + s3 + "}}</td>";

    _setTemplate(liTemplate, trTemplate, headerTemplate);
  }

  private void _setNameOnlyTemplate(boolean reqProfilePic)
  {
    String liTemplate = reqProfilePic ? _TMPLT_BASE_ONLY: _TMPLT_NO_PROFILE_BASE_ONLY;
    String headerTemplate = "<th>Name</th>";
    String trTemplate = "<td>{{fName}} {{lName}}</td>";

    _setTemplate(liTemplate, trTemplate, headerTemplate);
  }

  private synchronized void _setTemplate(
    String liTemplate, String trTemplate, String headerTemplate)
  {
    _template = new Template(liTemplate, trTemplate, headerTemplate);
  }

  private static class Template implements Serializable
  {
    private Template(String liTemplate, String trTemplate, String headerTemplate)
    {
      _liTemplate = liTemplate;
      _trTemplate = trTemplate;
      _headerTemplate = headerTemplate;
    }

    public String getLiTemplate()
    {
      return _liTemplate;
    }

    public String getTrTemplate()
    {
      return _trTemplate;
    }

    public String getHeaderTemplate()
    {
      return _headerTemplate;
    }

    private String _liTemplate;
    private String _trTemplate;
    private String _headerTemplate;

    @SuppressWarnings("compatibility:-4274101947984769900")
    private static final long serialVersionUID = 1L;
  }

  private List<String> _displayInclusions =
    new CopyOnWriteArrayList<String>(Arrays.asList(_NAME, _PHOTO));
  private Set<Integer> _employeesSet = Collections.emptySet();
  private volatile Template _template = new Template(_TMPLT_BASE_ONLY,
                                                     "<th>{{fName}} {{lName}}</th>",
                                                     "<th>Name</th>");
  private volatile String _contentMode = "list";

  private static final String _NAME = "name";
  private static final String _PHOTO = "photo";
  private static final String _DESIGNATION = "jobTitle";
  private static final String _DEPARTMENT = "deptName";
  private static final String _LOCATION = "deptLocation";
  private static final String _EMAIL = "email";

  private static final String _TMPLT_NO_PROFILE_BASE_ONLY =
    "<span style='font-size: 16px;padding-top: 3px;padding-bottom: 3px;display: inline-block;'>" +
    "{{fName}} {{lName}}" + "</span>";

  private static final String _TMPLT_NO_PROFILE_BASE_PLUS_1 =
    "<div " +
      "style=\"display: inline-block;line-height: 22px;padding-top: 4px; padding-bottom: 4px;\">" +
      "<div style=\"font-size: 18px;\">{{fName}} {{lName}}</div>" +
      "<div style=\"font-size: 15px;\">{{#s1#}}</div>" +
    "</div>";

  private static final String _TMPLT_NO_PROFILE_BASE_PLUS_2 =
    "<div " +
      "style=\"display: inline-block;line-height: 22px;padding-top: 4px; padding-bottom: 4px;\">" +
      "<div style=\"font-size: 18px;\">{{fName}} {{lName}}</div>" +
      "<div style=\"font-size: 15px;\">{{#s1#}}</div>" +
      "<div style=\"font-size: 15px;\">" +
        "<span style=\"font-size: 12px;font-variant: small-caps;\">#s2-LBL#: </span>{{#s2#}}" +
      "</div>" +
    "</div>";
  private static final String _TMPLT_NO_PROFILE_BASE_PLUS_3 =
    "<div " +
      "style=\"display: inline-block;line-height: 22px;padding-top: 4px; padding-bottom: 4px;\">" +
      "<div style=\"font-size: 18px;\">{{fName}} {{lName}}</div>" +
      "<div style=\"font-size: 15px;\">{{#s1#}} ({{#s2#}})</div>" +
      "<div style=\"font-size: 15px;\">" +
        "<span style=\"font-size: 12px;font-variant: small-caps;\">#s3-LBL#: </span>{{#s3#}}" +
      "</div>" +
    "</div>";
  private static final String _TMPLT_NO_PROFILE_BASE_PLUS_4 =
    "<div " +
      "style=\"display: inline-block;line-height: 22px;padding-top: 4px; padding-bottom: 4px;\">" +
      "<div style=\"font-size: 18px;\">{{fName}} {{lName}} " +
        "<span style=\"font-size: 12px;font-style: italic;\">({{#s1#}})</span>" +
      "</div>" +
      "<div style=\"font-size: 15px;\">{{#s2#}}</div>" +
      "<div style=\"font-size: 15px;\">" +
        "<span style=\"font-size: 12px;font-variant: small-caps;\">#s3-LBL#: </span>{{#s3#}}" +
      "</div>" +
      "<div style=\"font-size: 15px;\">" +
        "<span style=\"font-size: 12px;font-variant: small-caps;\">#s4-LBL#: </span>{{#s4#}}" +
      "</div>" +
    "</div>";

  private static final String _TMPLT_BASE_ONLY =
    "<div style=\"height: 42px; display: inline-block; line-height: 18px; white-space: nowrap\">" +
      "<div style=\"padding: 0px; margin: 0px; width: 42px; display: inline-block;\">" +
        "<img src=\"../../images/people/{{profileKey}}\" height=\"34\" width=\"34\" " +
          "style=\"vertical-align: bottom; border: 1px solid grey; padding: 2px; " +
                  "border-radius: 34px;\"/>" +
      "</div>" +
      "<div style=\"padding-left: 6px;padding-top: 13px;" +
                   "vertical-align: top;display: inline-block;\">" +
        "<div style=\"font-size: 16px;\">{{fName}} {{lName}}</div>" +
      "</div>" +
    "</div>";
  private static final String _TMPLT_BASE_PLUS_1 =
    "<div style=\"height: 42px; display: inline-block; line-height: 18px; white-space: nowrap\">" +
      "<div style=\"padding: 0px; margin: 0px; width: 42px; display: inline-block;\">" +
        "<img src=\"../../images/people/{{profileKey}}\" height=\"34\" width=\"34\" " +
          "style=\"vertical-align: bottom; border: 1px solid grey; padding: 2px; " +
                  "border-radius: 34px;\"/>" +
      "</div>" +
      "<div " +
        "style=\"padding-left: 6px;padding-top: 4px;vertical-align: top;display: inline-block;\">" +
        "<div style=\"font-size: 16px;\">{{fName}} {{lName}}</div>" +
        "<div style=\"font-size: 12px;\">{{#s1#}}</div>" +
      "</div>" +
    "</div>";
  private static final String _TMPLT_BASE_PLUS_2 =
    "<div style=\"height: 68px; line-height: 18px; white-space: nowrap\">" +
      "<div style=\"padding: 0px; margin: 0px; width: 64px; display: inline-block;\">" +
        "<img src=\"../../images/people/{{profileKey}}\" height=\"56\" width=\"56\" " +
          "style=\"vertical-align: bottom; border: 1px solid grey; padding: 2px; " +
                  "border-radius: 10px;\"/>" +
      "</div>" +
      "<div style=\"padding-left: 10px;display: inline-block;\">" +
        "<div style=\"font-size: 16px;\">{{fName}} {{lName}}</div>" +
        "<div style=\"font-size: 12px;\">{{#s1#}}</div>" +
        "<div style=\"font-size: 11px;\">" +
          "<span style=\"font-size: 12px;font-variant: small-caps;\">#s2-LBL#: </span>{{#s2#}}" +
        "</div>" +
      "</div>" +
    "</div>";
  private static final String _TMPLT_BASE_PLUS_3 =
    "<div style=\"height: 68px; line-height: 18px; white-space: nowrap\">" +
      "<div style=\"padding: 0px; margin: 0px; width: 64px; display: inline-block;\">" +
       "<img src=\"../../images/people/{{profileKey}}\" height=\"56\" width=\"56\" " +
          "style=\"vertical-align: bottom; border: 1px solid grey; padding: 2px; " +
                  "border-radius: 10px;\"/>" +
      "</div>" +
      "<div style=\"padding-left: 10px;display: inline-block;\">" +
        "<div style=\"font-size: 16px;\">{{fName}} {{lName}}</div>" +
        "<div style=\"font-size: 12px;\">{{#s1#}} ({{#s2#}})</div>" +
        "<div style=\"font-size: 11px;\">" +
          "<span style=\"font-size: 12px;font-variant: small-caps;\">#s3-LBL#: </span>{{#s3#}}" +
        "</div>" +
      "</div>" +
    "</div>";
  private static final String _TMPLT_BASE_PLUS_4 =
    "<div style=\"height: 84px; line-height: 18px; white-space: nowrap\">" +
      "<div style=\"padding: 0px; margin: 0px; width: 64px; display: inline-block;\">" +
        "<img src=\"../../images/people/{{profileKey}}\" height=\"56\" width=\"56\" " +
          "style=\"vertical-align: bottom; border: 1px solid grey; padding: 2px; " +
                  "border-radius: 10px;\"/>" +
      "</div>" +
      "<div style=\"padding-left: 10px;display: inline-block;\">" +
        "<div style=\"font-size: 16px;\">{{fName}} {{lName}}</div>" +
        "<div style=\"font-size: 14px;\">" +
          "<span style=\"font-size: 12px;font-variant: small-caps;\">#s3-LBL#: </span>{{#s3#}}" +
        "</div>" +
        "<div style=\"font-size: 11px;\">" +
          "<span style=\"font-size: 12px;font-variant: small-caps;\">#s4-LBL#: </span>{{#s4#}}" +
        "</div>" +
      "</div>" +
      "<div style=\"font-size: 12px;\">{{#s1#}} ({{#s2#}})</div>" +
    "</div>";

  @SuppressWarnings("compatibility:6152415652478790772")
  private static final long serialVersionUID = 3330852804200645918L;
}
