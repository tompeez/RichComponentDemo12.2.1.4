package oracle.adfdemo.view.feature.rich.hv.util;

import java.awt.Color;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.faces.bi.component.imageView.UIImageView;

import oracle.dss.dataView.ImageView;

import oracle.dss.util.BASE64Encoder;

import org.apache.myfaces.trinidad.context.RenderingContext;

public class HVUtils {
   
    public static Color[] colors =
    { Color.decode("0xCD8500"), Color.decode("0x8C1717"),
      Color.decode("0xFFC125"), Color.decode("0xA2BC13"),
      Color.decode("0x1B6453"), Color.decode("0x9AC0CD"),
      Color.decode("0x506987"), Color.decode("0x5959AB"),
      Color.decode("0xECC8EC"), Color.decode("0x003F87"),
      Color.decode("0x385E0F"), Color.decode("0x8A8A8A"),
      Color.decode("0x030303"), Color.decode("0x5F9F9F")};


    public static String toHex(Color c) {
        String color =
            Integer.toHexString((c.getRGB() & 0xffffff) | 0x1000000).substring(1);

        return color;
    }

    public static String getComponentImageUrl(UIImageView component) {
        component.transferProperties();
            
        if (RenderingContext.getCurrentInstance() == null || !(component instanceof UIGraph)) {
            return null;
        }
        
        UIGraph graph = (UIGraph) component;

        try {
            
            ImageView imageView = graph.getImageView();        
            ByteArrayOutputStream stream = new ByteArrayOutputStream(10240);
            try {
                imageView.exportToPNGWithException(stream);
            }
            catch (Exception e) {
              System.out.println("Could not export image to stream");
            }
            stream.close();

            return "data:image/png;base64," + (new BASE64Encoder()).encode(stream.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }
    
    public static String generateBoxHtml(Color color) {
        return generateBoxHtml(toHex(color));
    }
    
    public static String generateBoxHtml(String color) {
        String size = "10";
        int opacity = 1;
        String borderColor = "8A8A8A";
        String pointHTML = "<p style=\"border: 2px #" + borderColor;

        pointHTML +=
                " solid; text-align: center; vertical-align: middle; background-color: #" +
                color + "; width : " + size;
        pointHTML += "px; height : " + size + "px;";


        pointHTML +=
                "opacity:" + opacity + ";filter:alpha(opacity=" + 100 * opacity +
                ")\">" +

            "</p>";
        return pointHTML;
    }
    

    public static Color getColor(Color start, Color end, double value) {
        int r1 = start.getRed();
        int g1 = start.getGreen();
        int b1 = start.getBlue();

        int r2 = end.getRed();
        int g2 = end.getGreen();
        int b2 = end.getBlue();

        int dr = (int)(((double)(r2 - r1)) * value);
        int dg = (int)(((double)(g2 - g1)) * value);
        int db = (int)(((double)(b2 - b1)) * value);

        int newR = r1 + dr;
        int newG = g1 + dg;
        int newB = b1 + db;

        return new Color(newR, newG, newB);

    }
    
    public static double[] generateGradient(int size) {
        double[] values = new double[size];
        double inc = 1.0/size;
        double current = 0;
        for (int i=0; i<values.length; i++) {
            values[i] = current;
            current+=inc;
        }
        return values;
    }
}
