package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.view;

import oracle.adf.share.logging.ADFLogger;

/**
 * Holds state for the diagram relating to UI activities such as editing a node
 */
public class RuleEditorUiStateManager {
    private String _editFact;
    private String _editFactValue;
    private String _editFactDataType;
    private String _editFactOperand;
    private Integer _editNodeId;
    private boolean _editExistingMode;
    private static ADFLogger _logger = ADFLogger.createADFLogger(RuleEditorUiStateManager.class);


    public RuleEditorUiStateManager(){

    }

    
    /* Accessors used by the editing function on the diagram */
    public void setEditFact(String fact) {
        this._editFact = fact;
    }

    public String getEditFact() {
        return _editFact;
    }

    public void setEditFactValue(String value) {
        this._editFactValue = value;
    }

    public String getEditFactValue() {
        return _editFactValue;
    }

    public void setEditFactDataType(String dataType) {
        this._editFactDataType = dataType;
    }

    public String getEditFactDataType() {
        return _editFactDataType;
    }

    public void setEditFactOperand(String factOperand) {
        this._editFactOperand = factOperand;
    }

    public String getEditFactOperand() {
        return _editFactOperand;
    }
    
    public void setEditNodeId(Integer _dropTargetNodeId) {
        this._editNodeId = _dropTargetNodeId;
    }

    public Integer getEditNodeId() {
        return _editNodeId;
    }
    
    public void setEditExistingMode(boolean mode) {
        this._editExistingMode = mode;
    }

    public boolean isEditExistingMode() {
        return _editExistingMode;
    } 
    
    public boolean isEditableValue(){
        if (_editFactOperand != null && _editFactOperand.endsWith("NULL")){
            return false;
        }
        else {
            return true;
        }
    }
}
