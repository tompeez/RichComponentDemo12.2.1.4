package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.view;

import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.faces.bi.component.diagram.UIDiagram;
import oracle.adf.view.faces.bi.component.diagram.UIDiagramNodes;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichPanelGridLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelStretchLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramConditionNode;
import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramDropNode;
import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramModel;
import oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model.RuleDiagramNode;

/**
 * This class provides the client side functionality for the rule editor - handling functions such as drop events
 */
public class RuleEditorHelper {
    private static ADFLogger _logger = ADFLogger.createADFLogger(RuleEditorHelper.class);
    private UIDiagramNodes ruleDiagramNodes;
    private UIDiagram ruleDiagram;
    private RichPanelStretchLayout mainPanel;
    private RuleDiagramModel _model;
    private RuleEditorUiStateManager _uiStateManager;
    private RichPopup dropCreatePopup;

    public RuleEditorHelper() {
        super();
    }

    public void setRuleDiagramNodes(UIDiagramNodes ruleDiagramNodes) {
        this.ruleDiagramNodes = ruleDiagramNodes;
    }

    public UIDiagramNodes getRuleDiagramNodes() {
        return ruleDiagramNodes;
    }

    public void setRuleDiagram(UIDiagram ruleDiagram) {
        this.ruleDiagram = ruleDiagram;
    }

    public UIDiagram getRuleDiagram() {
        return ruleDiagram;
    }

    public void setMainPanel(RichPanelStretchLayout mainPanel) {
        this.mainPanel = mainPanel;
    }

    public RichPanelStretchLayout getMainPanel() {
        return mainPanel;
    }

    public void setDropCreatePopup(RichPopup dropCreatePopup) {
        this.dropCreatePopup = dropCreatePopup;
    }

    public RichPopup getDropCreatePopup() {
        return dropCreatePopup;
    }


    public void setModel(RuleDiagramModel model) {
        _model = model;
    }

    public RuleDiagramModel getModel() {
        return _model;
    }

    public void setUiStateManager(RuleEditorUiStateManager uiManager) {
        this._uiStateManager = uiManager;
    }

    public RuleEditorUiStateManager getUiStateManager() {
        return _uiStateManager;
    }


    /* Event Handlers */

    public DnDAction factDropHandler(DropEvent dropEvent) {
        Transferable payload = dropEvent.getTransferable();
        String droppedFact = "NONE";
        if (payload.isDataFlavorSupported(DataFlavor.STRING_FLAVOR)) {
            droppedFact = (String) dropEvent.getTransferable().getData(String.class);
        }

        String[] splitPayload = droppedFact.split("\\s*,\\s*");
        getUiStateManager().setEditFact(splitPayload[0]);
        getUiStateManager().setEditFactDataType(splitPayload[1]);
        getUiStateManager().setEditFactValue(splitPayload[3]);
        getUiStateManager().setEditFactOperand(splitPayload[2]);

        getUiStateManager().setEditExistingMode(false);


        Map dropSite = (Map) dropEvent.getDropSite();
        String dropTargetClientRowKey = (String) dropSite.get("clientRowKey");
        //Note: null == dropped onto main body of diagram
        if (dropTargetClientRowKey !=
            null) {
            //Translate into ther actual node identifier
            Integer droppedKey =
                             (Integer) getRuleDiagramNodes().getClientRowKeyManager().getRowKey(FacesContext.getCurrentInstance(),
                                                                                                getRuleDiagramNodes(),
                                                                                                dropTargetClientRowKey);
            RuleDiagramNode dropNode = (RuleDiagramNode) getModel().getDiagramNodesModel().getRowData(droppedKey);
            //Pay attention to drops on drop nodes and conditions only
            if (dropNode instanceof RuleDiagramDropNode || dropNode instanceof RuleDiagramConditionNode) {

                getUiStateManager().setEditNodeId(dropNode.getRuleNodeId());

                //Show the editor popup
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                this.getDropCreatePopup().show(hints);
            }

        } else {
            //In the case where the user just drops on the diagram then we add this as a top level node
            getUiStateManager().setEditNodeId(-1);
            //Show the editor popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            this.getDropCreatePopup().show(hints);

        }


        return DnDAction.COPY;
    }


    public void nodeMenuEditListener(ActionEvent actionEvent) {
        getUiStateManager().setEditExistingMode(true);

        Object[] keys = this.getRuleDiagramNodes().getSelectedRowKeys().toArray();

        RuleDiagramNode editNode = (RuleDiagramNode) getModel().getDiagramNodesModel().getRowData(keys[0]);

        getUiStateManager().setEditNodeId(editNode.getRuleNodeId());
        getUiStateManager().setEditFact(editNode.getRuleFact());
        getUiStateManager().setEditFactDataType(editNode.getRuleValueType());
        getUiStateManager().setEditFactValue((String) editNode.getRuleValue());
        getUiStateManager().setEditFactOperand(editNode.getRuleExpression().toString());

        //Show the editor popup
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getDropCreatePopup().show(hints);
    }


    public void editDialogHandler(DialogEvent dialogEvent) {
        if (getUiStateManager().getEditFactOperand().endsWith("NULL")) {
            getUiStateManager().setEditFactValue(null);
        }

        if (getUiStateManager().isEditExistingMode()) {
            RuleDiagramNode node = getModel().findNodeById(getUiStateManager().getEditNodeId());
            node.setRuleExpression(getUiStateManager().getEditFactOperand());
            node.setRuleValue(getUiStateManager().getEditFactValue());
        } else {
            int dropNodeId = getUiStateManager().getEditNodeId();
            if (dropNodeId >= 0) {
                boolean insertBefore = false;
                String dropConjunction = "OR";
                RuleDiagramNode dropNode = getModel().findNodeById(dropNodeId);
                if (dropNode instanceof RuleDiagramDropNode) {
                    if (dropNode.getDropNodePosition().equals("N")) {
                        insertBefore = true;
                        dropConjunction = "AND";
                    } else if (dropNode.getDropNodePosition().equals("W")) {
                        insertBefore = true;
                    } else if (dropNode.getDropNodePosition().equals("S")) {
                        dropConjunction = "AND";
                    }
                } else {
                    RuleDiagramNode group = getModel().findNodeById(dropNode.getRuleGroupId());
                    dropConjunction = group.getRuleGroupConjuctionType();
                    getUiStateManager().setEditNodeId(dropNode.getDropTargetE().getRuleNodeId());
                }

                getModel().addDroppedCondition(getUiStateManager().getEditNodeId(), getUiStateManager().getEditFact(),
                                               getUiStateManager().getEditFactDataType(),
                                               getUiStateManager().getEditFactValue(),
                                               getUiStateManager().getEditFactOperand(), dropConjunction, insertBefore);
            } else {
                getModel().addDroppedCondition(null, getUiStateManager().getEditFact(),
                                               getUiStateManager().getEditFactDataType(),
                                               getUiStateManager().getEditFactValue(),
                                               getUiStateManager().getEditFactOperand(), null, false);

            }
        }

        //And refresh the relevant bits of the screen
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.addPartialTarget(this.getMainPanel());
    }

    public void nodeMenuDeleteListener(ActionEvent actionEvent) {
        Object[] keys = this.getRuleDiagramNodes().getSelectedRowKeys().toArray();
        RuleDiagramNode deleteNode = (RuleDiagramNode) getModel().getDiagramNodesModel().getRowData(keys[0]);
        this.getModel().removeNode(deleteNode);
        //And refresh the relevant bits of the screen
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.addPartialTarget(this.getMainPanel());

    }
}
