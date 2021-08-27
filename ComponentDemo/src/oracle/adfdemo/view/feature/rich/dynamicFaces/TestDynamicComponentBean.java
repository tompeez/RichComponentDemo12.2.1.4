package oracle.adfdemo.view.feature.rich.dynamicFaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.model.AttributeDescriptor.ComponentType;
import oracle.adf.view.rich.model.BaseAttributeDescriptor;
import oracle.adf.view.rich.model.AttributesModel;

import oracle.adf.view.rich.model.Descriptor;

import oracle.adf.view.rich.model.GroupAttributeDescriptor;

import oracle.adfdemo.view.feature.rich.dynamicFaces.TestDynamicComponentPageDef.TestAttributeDef;


public class TestDynamicComponentBean {
    public TestDynamicComponentBean() {
        _createPageDef();
        _attrsModel = new TestAttributesModel();
        _setupData();
    }

    public AttributesModel getAttributesModel() {
        return _attrsModel;
    }

    public Map[] getValues() {
        return _DATA;
    }

    public Map getValue() {
        return _DATA[currentRowIndex];
    }

    public void next(ActionEvent actionEvent) {
        if (currentRowIndex < _DATA.length - 1)
            currentRowIndex++;
    }

    public boolean getNextEnabled() {
        return (currentRowIndex < (_DATA.length - 1));
    }

    public void previous(ActionEvent actionEvent) {
        if (currentRowIndex > 0)
            currentRowIndex--;
    }

    public boolean getPreviousEnabled() {
        return currentRowIndex > 0;
    }

    /**
     * Creates a DemoPageDef object, containing a list of attributes, saved searches and each saved
     * search definintion object containing a list of search field definitions.
     */
    private void _createPageDef() {
        _pageDef = new TestDynamicComponentPageDef();
        _pageDef.setupAttributes();
    }

    private void _setupData() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        _DATA = new Map[30];
        try {
            _addOneRow(0, 1234, "SMITH", 10, "RESEARCH", null, formatter.parse("07/12/2004"), 150000);
            _addOneRow(1, 2345, "SCOTT", 10, "RESEARCH", 1234, formatter.parse("04/25/2006"), 120000);
            _addOneRow(2, 5678, "THOMAS", 20, "SALES", null, formatter.parse("05/11/2010"), 90000);
            _addOneRow(3, 3234, "Mike", 10, "RESEARCH", null, formatter.parse("07/12/2004"), 150000);
            _addOneRow(4, 4345, "Tom", 10, "RESEARCH", 1234, formatter.parse("04/25/2006"), 120000);
            _addOneRow(5, 5578, "Calvin", 20, "SALES", null, formatter.parse("05/11/2010"), 90000);
            _addOneRow(6, 6234, "John", 10, "RESEARCH", null, formatter.parse("07/12/2004"), 150000);
            _addOneRow(7, 7345, "Jerry", 10, "RESEARCH", 1234, formatter.parse("04/25/2006"), 120000);
            _addOneRow(8, 8678, "Ann", 20, "SALES", null, formatter.parse("05/11/2010"), 90000);
            _addOneRow(9, 9234, "Michael", 10, "RESEARCH", null, formatter.parse("07/12/2004"), 150000);
            _addOneRow(10, 2355, "Liza", 10, "RESEARCH", 1234, formatter.parse("04/25/2006"), 120000);
            _addOneRow(11, 5668, "Linda", 20, "SALES", null, formatter.parse("05/11/2010"), 90000);
            _addOneRow(12, 1244, "Richael", 10, "RESEARCH", null, formatter.parse("07/12/2004"), 150000);
            _addOneRow(13, 2335, "Amber", 10, "RESEARCH", 1234, formatter.parse("04/25/2006"), 120000);
            _addOneRow(14, 5628, "Tina", 20, "SALES", null, formatter.parse("05/11/2010"), 90000);
            _addOneRow(15, 3234, "Shery", 10, "RESEARCH", null, formatter.parse("07/12/2004"), 150000);
            _addOneRow(16, 1345, "Jery", 10, "RESEARCH", 1234, formatter.parse("04/25/2006"), 120000);
            _addOneRow(17, 7678, "Kristy", 20, "SALES", null, formatter.parse("05/11/2010"), 90000);
            _addOneRow(18, 7234, "Kristine", 10, "RESEARCH", null, formatter.parse("07/12/2004"), 150000);
            _addOneRow(19, 7245, "Adam", 10, "RESEARCH", 1234, formatter.parse("04/25/2006"), 120000);
            _addOneRow(20, 7578, "Abby", 20, "SALES", null, formatter.parse("05/11/2010"), 90000);
            _addOneRow(21, 7234, "Abhay", 10, "RESEARCH", null, formatter.parse("07/12/2004"), 150000);
            _addOneRow(22, 9345, "Pal", 10, "RESEARCH", 1234, formatter.parse("04/25/2006"), 120000);
            _addOneRow(23, 6678, "Noah", 20, "SALES", null, formatter.parse("05/11/2010"), 90000);
            _addOneRow(24, 3244, "Panya", 10, "RESEARCH", null, formatter.parse("07/12/2004"), 150000);
            _addOneRow(25, 6355, "Emma", 10, "RESEARCH", 1234, formatter.parse("04/25/2006"), 120000);
            _addOneRow(26, 6668, "Olivia", 20, "SALES", null, formatter.parse("05/11/2010"), 90000);
            _addOneRow(27, 6244, "Sophia", 10, "RESEARCH", null, formatter.parse("07/12/2004"), 150000);
            _addOneRow(28, 6335, "Amber", 10, "RESEARCH", 1234, formatter.parse("04/25/2006"), 120000);
            _addOneRow(29, 6628, "Eva", 20, "SALES", null, formatter.parse("05/11/2010"), 90000);
            
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void _addOneRow(int rowNumber, Number empNo, String empName, Number deptNo, String deptName, Number manager,
                            Date hireDate, Number salary) {
        Map rowData = new HashMap<String, Object>(7);
        rowData.put("Empno", empNo);
        rowData.put("Ename", empName);
        rowData.put("Deptno", deptNo);
        rowData.put("Deptname", deptName);
        rowData.put("Manager", manager);
        rowData.put("Hiredate", hireDate);
        rowData.put("Salary", salary);
        _DATA[rowNumber] = rowData;
        return;
    }

    public class TestAttributesModel extends AttributesModel {
        public TestAttributesModel() {
            _flatAttributes = new ArrayList<BaseAttributeDescriptor>();
            _hierAttributes = new ArrayList<Descriptor>();
            _setupAttributesFromDefinition();
        }

        public List<BaseAttributeDescriptor> getAttributes() {
            return _flatAttributes;
        }

        public List<Descriptor> getHierarchicalAttributes() {
            return _hierAttributes;
        }

        public List<BaseAttributeDescriptor> getLinkedViewAttributes(String string) {
            return Collections.emptyList();
        }

        private void _setupAttributesFromDefinition() {
            Map<String, List<BaseAttributeDescriptor>> groupMap = new HashMap<String, List<BaseAttributeDescriptor>>();

            List<TestDynamicComponentPageDef.TestAttributeDef> attributeList = _pageDef.getAttributeDefs();

            for (TestDynamicComponentPageDef.TestAttributeDef demoAttrDef : attributeList) {
                TestAttributeDescriptor attrDesc = new TestAttributeDescriptor(demoAttrDef);
                _flatAttributes.add(attrDesc);
                String groupName = attrDesc.getGroupName();
                if (groupName != null && !groupName.isEmpty()) {
                    List<BaseAttributeDescriptor> list = groupMap.get(groupName);
                    if (list == null) {
                        list = new ArrayList<BaseAttributeDescriptor>();
                        groupMap.put(groupName, list);
                    }
                    list.add(attrDesc);
                } else {
                    _hierAttributes.add(attrDesc);
                }
            }

            for (String groupName : groupMap.keySet()) {
                TestGroupAttributeDescriptor groupMetadata =
                    new TestGroupAttributeDescriptor(groupName, groupMap.get(groupName));
                _hierAttributes.add(groupMetadata);
            }
        }

        private List<BaseAttributeDescriptor> _flatAttributes = null;
        private List<Descriptor> _hierAttributes = null;

        public String getName() {
            return null;
        }

        public List<BaseAttributeDescriptor> getGroupAttributes(String string) {
            return Collections.emptyList();
        }
    }

    public class TestAttributeDescriptor extends BaseAttributeDescriptor {
        public TestAttributeDescriptor(TestAttributeDef attributeDef) {
            _attributeDef = attributeDef;
        }

        public Object getId() {
            return getName();
        }

        public String getName() {
            return _attributeDef.getName();
        }

        public String getGroupName() {
            return _attributeDef.getCategory();
        }

        public Class getDataType() {
            return _attributeDef.getDataType();
        }

        public String getDescription() {
            return _attributeDef.getDescription();
        }

        public String getLabel() {
            return _attributeDef.getLabel();
        }

        public int getLength() {
            return _attributeDef.getLength();
        }

        public int getMaximumLength() {
            return _attributeDef.getMaxLength();
        }

        @Override
        public String getFormat() {
            return null;
        }

        @Override
        public boolean isRequired() {
            return false;
        }

        @Override
        public boolean isReadOnly() {
            return false;
        }

        @Override
        public Class getType() {
            return _attributeDef.getDataType();
        }

        @Override
        public ComponentType getComponentType() {
            return _getComponentTypeByDatatype();
        }

        @Override
        public Object getModel() {
            return null;
        }

        private ComponentType _getComponentTypeByDatatype() {
            Class cls = getType();


            if (cls == java.sql.Date.class || cls == java.sql.Time.class || cls == java.sql.Timestamp.class) {
                return ComponentType.inputDate;
            }

            return ComponentType.inputText;
        }


        private TestAttributeDef _attributeDef;
    }

    public class TestGroupAttributeDescriptor extends GroupAttributeDescriptor {
        public TestGroupAttributeDescriptor(String groupName, List<BaseAttributeDescriptor> attributeList) {
            _groupName = groupName;
            _flatAttributes = attributeList;
        }


        @Override
        public String getName() {
            return _groupName;
        }

        public List<? extends Descriptor> getDescriptors() {
            return _flatAttributes;
        }

        public String getDescription() {
            return "This is a group";
        }

        public String getLabel() {
            return _groupName;
        }


        private List<BaseAttributeDescriptor> _flatAttributes;
        private String _groupName;

    }

    private TestDynamicComponentPageDef _pageDef;
    private TestAttributesModel _attrsModel;
    private int currentRowIndex = 0;
    private static Map[] _DATA;
}
