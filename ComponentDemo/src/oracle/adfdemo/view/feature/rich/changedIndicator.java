package oracle.adfdemo.view.feature.rich;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

public class changedIndicator {
    public changedIndicator() {
    }

    /*
     * Action listener when submit button is pressed on changedIndicator.jspx
     * 
     * Clear the changed properties for all the components in the form.
     */
    public void submitted(ActionEvent actionEvent) {
      
      RichInputText inputText = (RichInputText)FacesContext.getCurrentInstance().getViewRoot().findComponent("dmoTpl:it1");
      inputText.setChanged(false);

      RichSelectBooleanCheckbox selectBooleanCheckbox = (RichSelectBooleanCheckbox)FacesContext.getCurrentInstance().getViewRoot().findComponent("dmoTpl:sbc1");
      selectBooleanCheckbox.setChanged(false);
    }
}
