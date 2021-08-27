package oracle.adfdemo.view.feature.rich.dynamicFaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.adf.view.rich.model.AttributeDescriptor.ComponentType;


public class TestDynamicComponentPageDef {
    public TestDynamicComponentPageDef() {
        super();
    }
    
    /**
     * Adds attributes to the pageDef
     */
    public void setupAttributes()
    {
      _attributes = new HashMap<String, TestAttributeDef>();
      addAttributeDef("Ename", "Employee Name", null, "Name", 10, 20, null, String.class, 
                      "Empolyee Personal");
      addAttributeDef("Empno", "Employee Number", null,"Employee Number", 10, 20, null,
                      Number.class, "Empolyee Personal");
      addAttributeDef("Deptno", "Department Number", null, "Department Number", 10, 20, null,
                      Number.class, "Department Info");
      addAttributeDef("Deptname", "Department Name", null, "Department Name", 10, 20, null,
                      String.class, "Department Info");
      addAttributeDef("Manager", "Manager", null, "Manager", 10, 20, null, Number.class, 
                      null);
      addAttributeDef("Hiredate", "Hire Date", null, "Hier Date", 10, 20, null, Date.class, null);
      addAttributeDef("Salary", "Salary", null, "Salary", 10, 20, null, Number.class,
                      "Empolyee Personal");
    }

    public void addAttributeDef (String name, String description, String format, String label, 
                                 int length, int maxLength, String charCountBy, Class dataType, 
                                 String category) {
        TestAttributeDef attributeDef = new TestAttributeDef(name, description, format, label,
                                                             length, maxLength, charCountBy, dataType, category);
        _attributes.put(name, attributeDef);
    }

    /**
     * Gets the AttributeDescriptor objects by its name.
     * @return
     */
    public List<TestAttributeDef> getAttributeDefs()
    {
      if (_attributes != null)
      {
        List<TestAttributeDef> attrDescList =
          new ArrayList<TestAttributeDef>(_attributes.values());
        return attrDescList;
      }

      return Collections.emptyList();
    }
    
    public String[] getAttributeNames() {
        return _attributes.keySet().toArray(new String[0]);
    }

    public class TestAttributeDef {
        public TestAttributeDef(String name, String description, String format, String label,
                                int length, int maxLength, String charCountBy, Class dataType,
                                String category) {
            _name = name;
            _description = description;
            _format = format;
            _label = label;
            _length = length;
            _maxLength = maxLength;
            _charCountBy = charCountBy;
            _dataType = dataType;
            _category = category;
        }
        
        public String getName() {
            return _name;
        }
        
        public String getDescription() {
            return _description;
        }
        
        public String getFormat() {
            return _format;
        }
        
        public String getLabel() {
            return _label;
        }
        
        public int getLength() {
            return _length;
        }
        
        public int getMaxLength() {
            return _maxLength;
        }
        
        public String getCharCountBy() 
        {
            return _charCountBy;
        }
        
        public ComponentType getComponentType() {
            return _componentType;
        }
        
        public Class getDataType(){
            return _dataType;
        }
        
        public void setCategory(String category)
        {
          this._category = category;
        }

        public String getCategory()
        {
          return _category;
        }      
    
        private String _name;
        private String _description;
        private String _format;
        private String _label;
        private int _length;
        private int _maxLength;
        private String _charCountBy;
        private ComponentType _componentType;
        private Class _dataType;
        private String _category;  // group name
  }
    
    
    // Map of attributes for the page
    private Map<String, TestAttributeDef> _attributes;
}
