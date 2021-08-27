package oracle.adfdemo.view.feature.rich.graph;

public class GraphNumberFormatBean {
    private CustomNumberFormat m_x1Format = new CustomNumberFormat();
    private CustomNumberFormat m_y1Format = new CustomNumberFormat();
    private CustomNumberFormat m_y2Format = new CustomNumberFormat();
    private CustomNumberFormat m_zFormat = new CustomNumberFormat();
    private CustomNumberFormat m_stockVolumeFormat = new CustomNumberFormat();
    
    private CustomNumberFormat m_x1Label = new CustomNumberFormat();
    private CustomNumberFormat m_y1Label = new CustomNumberFormat();
    private CustomNumberFormat m_y2Label = new CustomNumberFormat();
    private CustomNumberFormat m_sliceLabel = new CustomNumberFormat();
    
    public CustomNumberFormat getX1Format(){
        return m_x1Format;
    }
    public CustomNumberFormat getY1Format(){
        return m_y1Format;
    }
    public CustomNumberFormat getY2Format(){
        return m_y2Format;
    }
    public CustomNumberFormat getZFormat(){
        return m_zFormat;
    }
    public CustomNumberFormat getStockVolumeFormat(){
        return m_stockVolumeFormat;
    }
    public CustomNumberFormat getX1Label(){
        return m_x1Label;
    }
    public CustomNumberFormat getY1Label(){
        return m_y1Label;
    }
    public CustomNumberFormat gety2Label(){
        return m_y2Label;
    }
    public CustomNumberFormat getSliceLabel(){
        return m_sliceLabel;
    }
    
    
    
    
    private String m_graphType = "SCATTER_2Y";
    
    public String getGraphType(){
       return m_graphType;
    }
    public void setGraphType(String graphType){
       m_graphType = graphType;
    }
    
    public static class CustomNumberFormat{
        
        private String m_autoPrecision;
        private String m_scaling;
        private String m_pattern;
        
        private String m_type;
        private String m_currencySymbol;
        private int m_minIntegerDigits = 0;
        private int m_minFractionDigits = 0;
        private int m_maxIntegerDigits = 0;
        private int m_maxFractionDigits = 0;
        
        
        public CustomNumberFormat(){
            m_autoPrecision = "on";
            m_scaling = "auto";
            m_pattern = "";
            
            m_type = "number";
            m_currencySymbol = "$";
            m_minIntegerDigits = 0;
            m_minFractionDigits = 0;
            m_maxIntegerDigits = 0;
            m_maxFractionDigits = 0;
            
        }
        public String getAutoPrecision(){
            return m_autoPrecision;
        }
        public void setAutoPrecision(String autoPrecision){
            m_autoPrecision = autoPrecision;
        } 
        public String getScaling(){
            return m_scaling;
        }
        public void setScaling(String scaling){
            m_scaling = scaling;
        }
        public String getPattern(){
            return m_pattern;
        }
        public void setPattern(String pattern){
            m_pattern = pattern;
        }
        public String getType(){
            return m_type;
        }
        public void setType(String type){
            m_type = type;
        }
    }
}