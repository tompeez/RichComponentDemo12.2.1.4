package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.geom.Point2D;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;


public class MarkerAnimationBean {
  private double scaleX = 2.5;
  private double scaleY = 2.5;
  
  private double rotation = 0;
  private double rotationIncrement = 90;
  
  private String color;
  private String[] colors = new String[]{"#267db3", "#68c182", "#fad55c", "#ed6647", "#8561c8"};
  private int colorIndex = 0;
  private List<Point2D.Double> points = new ArrayList<Point2D.Double>();
  private List<Point2D.Double> pointsInit = new ArrayList<Point2D.Double>();
  
  public MarkerAnimationBean() {
    color = colors[colorIndex];
    createPoints();
  }
  
  private void createPoints() {
    points.add(new Point2D.Double(100, 50));
    points.add(new Point2D.Double(160, 110));
    points.add(new Point2D.Double(240, 70));
    points.add(new Point2D.Double(260, 170));
    points.add(new Point2D.Double(120, 230));
    points.add(new Point2D.Double(400, 110));
    points.add(new Point2D.Double(140, 30));
    points.add(new Point2D.Double(280, 150));
    points.add(new Point2D.Double(320, 90));
    
    points.add(new Point2D.Double(380, 200));
    points.add(new Point2D.Double(440, 320));
    points.add(new Point2D.Double(490, 220));
    points.add(new Point2D.Double(410, 280));

    for (Point2D.Double p : points) {
      pointsInit.add(new Point2D.Double(p.x, p.y));
    }
  }
  
  private void resetPoints() {
    for (int i = 0; i < points.size(); i++) {
      points.get(i).x = pointsInit.get(i).x;
      points.get(i).y = pointsInit.get(i).y;
    }
  }
  
  
  public void move(ActionEvent actionEvent) {
    for (Point2D.Double p : points) {
      p.x += Math.random() * 100 - 50;
      p.x = Math.min(Math.max(p.x, 100), 500);
      p.y += Math.random() * 100 - 50;
      p.y = Math.min(Math.max(p.y, 50), 300);      
    }
  }
  
  public void increaseSize(ActionEvent actionEvent) {
    scaleX *= 3/2.0;
    scaleY *= 3/2.0;
  }
  
  public void decreaseSize(ActionEvent actionEvent) {
    scaleX *= 2/3.0;
    scaleY *= 2/3.0;
  }
  
  public void changeColor(ActionEvent actionEvent) {
    colorIndex = (colorIndex + 1) % colors.length;
    color = colors[colorIndex];
  }
  
  public void rotate(ActionEvent actionEvent) {
    rotation = (rotation + rotationIncrement) % 360;
  }
  
  public void reset(ActionEvent actionEvent) {
    colorIndex = 0;
    color = colors[colorIndex];
    scaleX = 2;
    scaleY = 2;
    rotation = 0;
    resetPoints();
  }
  
  public double getScaleX() {
    return scaleX;
  } 
  public void setScaleX(double s) {
    scaleX = s;
  } 

  public double getScaleY() {
    return scaleY;
  } 
  public void setScaleY(double s) {
    scaleY = s;
  }
  
  public double getRotation() {
    return rotation;
  }
  public void setRotation(double r) {
    rotation = r;
  }

  public String getColor() {
    return color;
  }
  public void setColor(String s) {
    color = s;
  }
  
  public List<Point2D.Double> getPoints() {
    return points;
  }
  public void setPoints(List<Point2D.Double> p) {
    points = p;
  }
  
}


