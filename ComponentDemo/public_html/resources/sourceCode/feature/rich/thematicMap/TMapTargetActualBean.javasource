package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.faces.bi.component.geoMap.MapUtil;
import oracle.adf.view.faces.bi.component.thematicMap.UIThematicMap;

import oracle.adfdemo.view.feature.rich.thematicMap.data.Category;
import oracle.adfdemo.view.feature.rich.thematicMap.data.TargetActualPair;
import oracle.adfdemo.view.feature.rich.hv.util.HVUtils;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;


public class TMapTargetActualBean {
    private UIThematicMap tm = null;
    private DecompList decompData = new DecompList(this);
    private CollectionModel m_model;
    private String[] stateNames;
    private String[] stateAbbrevs;
    private Integer[] target;
    private Integer[] actual;
    private Integer[] budget;
    private Integer[] spending;
    private Integer[] assigned;
    private Integer[] completed;
    
    private String targetActualPair = Category.BUDGET_SPENDING;
    private String preferredOrder = "TargetGreater";
    private Color targetHigherStartColor = new Color(173, 250, 47);
    private Color targetHigherEndColor = new Color(34, 139, 34);
  
    private Color targetLowerStartColor = new Color(238, 180, 180);
    private Color targetLowerEndColor = new Color(165, 42, 42);
  
    public static double[] gradient = HVUtils.generateGradient(10);
    private List<SelectItem> decompOptions = null;
    private boolean slow = false;

    public TMapTargetActualBean() {
        stateNames = TMapUtils.stateNames;
        stateAbbrevs = TMapUtils.stateAbbrevs;
        int size = stateNames.length;
        
        target = TMapUtils.getRandomInts(size, 1000);
        actual = TMapUtils.getRandomInts(size, 1000);
        budget = TMapUtils.getRandomInts(size, 10000);
        spending = TMapUtils.getRandomInts(size, 10000);
        assigned = TMapUtils.getRandomInts(size, 10);
        completed = TMapUtils.getRandomInts(size, 10);
        
        for(int i=0; i < stateNames.length; i++){
            decompData.add(
                new DecompData(
                    stateAbbrevs[i],
                    stateNames[i],
                    target[i],
                    actual[i],
                    budget[i],
                    spending[i], 
                    assigned[i], 
                    completed[i]));
        }
    }
    
    public CollectionModel getModel(){
        if(m_model == null){
            m_model = ModelUtils.toCollectionModel(decompData);
        }
        
        return m_model;
    }

    
    public String getTooltip(){
        String tooltip;
        DecompData rowData = (DecompData)getModel().getRowData();

        TargetActualPair pair =
            Category.itemPairs.get(targetActualPair);
      
        String actualLabel = Category.attributes.get(pair.getActual());
        String targetLabel = Category.attributes.get(pair.getTarget());
      
        double actual = rowData.getValue(pair.getActual());
        double target = rowData.getValue(pair.getTarget());
        
        tooltip = "[ " + targetLabel + ": " + target + " ][ " + actualLabel + ": " + actual +" ]";
        
        return tooltip;
    }
    
    public Color getColorObj(){
        return MapUtil.convertToColor(getColor());
    }
    
    public String getColor(){
      String color;
      DecompData rowData = (DecompData)getModel().getRowData();
      String itemPrefix =
          Category.itemPairs.get(targetActualPair).getItemPrefix();
      
      TargetActualPair pair =
          Category.itemPairs.get(targetActualPair);
      
      FacesContext context = FacesContext.getCurrentInstance();
      ExpressionFactory factory =
          context.getApplication().getExpressionFactory();
      
      String actualLabel = Category.attributes.get(pair.getActual());
      String targetLabel = Category.attributes.get(pair.getTarget());
      
      double actual = rowData.getValue(pair.getActual());
      double target = rowData.getValue(pair.getTarget());
      
      double diff = target - actual;

      if (preferredOrder.equals("TargetGreater")) {
          diff = diff;
      } else {
          diff = -diff;
      }
      
      double offBy = Math.abs(diff);
      double gradientScale = 0;

      double mult = offBy / (target);

      // double maxDiff = 10000 - threshold;

      if (mult > 1) {
          gradientScale = 1;
      } else {
          gradientScale = mult;
      }

      if (diff > 0) {

          Color c =
              HVUtils.getColor(targetHigherStartColor, targetHigherEndColor,
                               gradientScale);

          color = HVUtils.toHex(c);
      } else {
          Color c =
              HVUtils.getColor(targetLowerStartColor, targetLowerEndColor,
                               gradientScale);

          color = HVUtils.toHex(c);
      }
      return "#" + color;
    }
    
    public ValueExpression getColorExpression(){
        ValueExpression ve = null;
        
        return ve;
    }
    
    public List<SelectItem> getDecompOptions() {
        if (decompOptions == null) {
            decompOptions = new ArrayList<SelectItem>();
            for (String key : Category.itemPairs.keySet()) {
                SelectItem item = new SelectItem();
                item.setValue(key);
                item.setLabel(Category.attributes.get(key));
                decompOptions.add(item);
            }
        }
        return decompOptions;
    }
  
    public void setTargetActualPair(String targetActualPair) {
        this.targetActualPair = targetActualPair;
    }
  
    public String getTargetActualPair() {
        return targetActualPair;
    }
  
    public void updateTargetActualPair(ValueChangeEvent event) {
        String newVal = (String)event.getNewValue();
        targetActualPair = newVal;
    }
  
    public void updatePreferredOrder(ValueChangeEvent event) {
        String newVal = (String)event.getNewValue();
        preferredOrder = newVal;
    }
  
    public void setPreferredOrder(String preferredOrder) {
        this.preferredOrder = preferredOrder;
    }
  
    public String getPreferredOrder() {
        return preferredOrder;
    }
  
    public List<SelectItem> getPreferredOrderOptions() {
        List<SelectItem> ops = new ArrayList<SelectItem>();
  
        TargetActualPair pair = Category.itemPairs.get(targetActualPair);
        String target = pair.getTarget();
        String actual = pair.getActual();
        String targetLabel = Category.attributes.get(target);
        String actualLabel = Category.attributes.get(actual);
  
        SelectItem item1 = new SelectItem();
        item1.setLabel(actualLabel + " < " + targetLabel);
        item1.setValue("TargetGreater");
        ops.add(item1);
  
        SelectItem item2 = new SelectItem();
        item2.setLabel(actualLabel + " > " + targetLabel);
        item2.setValue("ActualGreater");
  
        ops.add(item2);
        return ops;
    }
  
  
    public double getWorseMax() {
        return 1.4;
    }
  
    public double getBetterMax() {
        return 1.4;
    }
  
    public List<String> getPositiveColorGradient() {
        List<String> colors = new ArrayList<String>();
        for (int i = 0; i < gradient.length; i++) {
            Color c =
                HVUtils.getColor(targetHigherStartColor, targetHigherEndColor,
                                 gradient[i]);
            String s = HVUtils.toHex(c);
            colors.add(s);
        }
        Collections.reverse(colors);
        return colors;
    }
  
    public List<String> getNegativeColorGradient() {
        List<String> colors = new ArrayList<String>();
        for (int i = 0; i < gradient.length; i++) {
            Color c =
                HVUtils.getColor(targetLowerStartColor, targetLowerEndColor,
                                 gradient[i]);
            String s = HVUtils.toHex(c);
            colors.add(s);
        }
        return colors;
    }
  
    public String getTargetHigherStartColor() {
        return HVUtils.toHex(targetHigherStartColor);
    }
  
  
    public String getTargetHigherEndColor() {
        return HVUtils.toHex(targetHigherEndColor);
    }
  
  
    public String getTargetLowerStartColor() {
        return HVUtils.toHex(targetLowerStartColor);
    }
  
  
    public String getTargetLowerEndColor() {
        return HVUtils.toHex(targetLowerEndColor);
    }

    public void setSlow(boolean slow) {
        this.slow = slow;
    }

    public boolean getSlow() {
        return slow;
    }

    public static class DecompList extends ArrayList <DecompData> 
    {
        TMapTargetActualBean m_bean = null;
        
        public DecompList(TMapTargetActualBean bean) {
            super();            
            m_bean = bean;
        }
        
        public DecompData get(int index) 
        {
            if (m_bean.getSlow())
            {
                double time = System.currentTimeMillis();
                double newtime = time;
                while (newtime - time < 10) {
                    newtime = System.currentTimeMillis();
                }
            }
            
            return super.get(index);            
        }
    }
    
    public static class DecompData {
        private String name;
        private String fullName;
        private int target;
        private int actual;
        private int budget;
        private int spending;
        private int assigned;
        private int completed;
        private HashMap<String, Integer> values = new HashMap<String, Integer>();
        
        DecompData(String name, String fullName, 
                   int target, int actual, 
                   int budget, int spending, 
                   int assigned, int completed){
            this.name = name;
            this.fullName = fullName;
            this.target = target;
            this.actual = actual;
            this.budget = budget;
            this.spending = spending;
            this.assigned = assigned;
            this.completed = completed;
            
            values.put("target", target);
            values.put("actual", actual);
            values.put("budget", budget);
            values.put("spending", spending);
            values.put("assigned", assigned);
            values.put("completed", completed);
        }
                
        public int getValue(String item){
            return values.get(item);
        }
        
        public String getName(){
            return name;
        }
        
        public void setName(String name){
            this.name = name;
        }
        
        public String getFullName(){
            return fullName;
        }
        
        public void setFullName(String fullName){
            this.fullName = fullName;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public int getTarget() {
            return target;
        }

        public void setActual(int actual) {
            this.actual = actual;
        }

        public int getActual() {
            return actual;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }

        public int getBudget() {
            return budget;
        }

        public void setSpending(int spending) {
            this.spending = spending;
        }

        public int getSpending() {
            return spending;
        }

        public void setAssigned(int assigned) {
            this.assigned = assigned;
        }

        public int getAssigned() {
            return assigned;
        }

        public void setCompleted(int completed) {
            this.completed = completed;
        }

        public int getCompleted() {
            return completed;
        }
    }
}
