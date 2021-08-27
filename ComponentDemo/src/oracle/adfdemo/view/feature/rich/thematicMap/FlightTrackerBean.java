package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.geom.Point2D;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.thematicMap.UIPointDataLayer;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.PollEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;


public class FlightTrackerBean {
  
  
  private List<Flight> flights = new ArrayList<Flight>();
  private int currentInterval = -1;
  private boolean play = false;
  private boolean isStarted = false;
  private boolean isPaused = false;
  private RichCommandButton playButton;
  private RichCommandButton pauseButton;
  private RowKeySet selectedKeys;
  private boolean showFlightNo = false;
  
  public FlightTrackerBean() {
    createFlights();
  }
  
  public List<Flight> getFlights() {
    return flights;
  }
  
  private void createFlights() {
    flights.add(new Flight(235, "San Francisco", "Beijing", new Point2D.Double(-122.4167, 37.7833), new Point2D.Double(116.3917, 39.9139), 42300000)); //11h 45 m
    flights.add(new Flight(658, "Boston", "San Francisco", new Point2D.Double(-71.0636, 42.3581), new Point2D.Double(-122.4167, 37.7833), 21600000)); //6 h
    flights.add(new Flight(1687, "Houston", "Miami", new Point2D.Double(-95.3831, 29.7628), new Point2D.Double(-80.2241, 25.7877), 9000000)); //2h 30 m
    flights.add(new Flight(954, "New York City", "London", new Point2D.Double(-73.94, 40.67), new Point2D.Double(-0.1275, 51.5072), 27600000)); //7h 40m
    flights.add(new Flight(124, "New York City", "Tokyo", new Point2D.Double(-73.94, 40.67), new Point2D.Double(139.6917, 35.6895), 48000000)); //13 h 20m
    flights.add(new Flight(6647, "Los Angeles", "Honolulu", new Point2D.Double(-118.25, 34.05), new Point2D.Double(-157.8167, 21.3), 19800000)); //5h 30m
    
    flights.add(new Flight(9436, "Mexico City", "Rio de Janeiro", new Point2D.Double(-102.3667, 19), new Point2D.Double(-43.1964, -22.9083), 45060000)); //12h 31m
    flights.add(new Flight(6647, "Sydney", "Auckland", new Point2D.Double(151.2111, -33.8600), new Point2D.Double(174.7399, -36.8404), 10800000)); //3h
  }
  
  public void pollListener(PollEvent event) {
    if (this.play) {
      currentInterval++;
      boolean allFlightsLanded = true;
      for (int i=0; i<flights.size(); i++) {
        Flight flight = flights.get(i);
        flight.setCurrentLongLat(currentInterval);
        boolean flightLanded = flight.isDestinationReached();
        if (!flightLanded)
          allFlightsLanded = false;
      }
      
      RequestContext rc = RequestContext.getCurrentInstance();
      if (allFlightsLanded) {
        isStarted = false;
        this.play = false;
        rc.addPartialTarget(this.playButton);
        rc.addPartialTarget(this.pauseButton);      
        UIComponent poll = FacesContext.getCurrentInstance().getViewRoot().findComponent("demoTemplate").findComponent("poll1");
        rc.addPartialTarget(poll);
      }
      
      UIComponent pdl = FacesContext.getCurrentInstance().getViewRoot().findComponent("demoTemplate").findComponent("thematicMap").findComponent("thematicMap:al1:pdl1");
      rc.addPartialTarget(pdl);
      
      UIComponent it = FacesContext.getCurrentInstance().getViewRoot().findComponent("demoTemplate").findComponent("it1");
      rc.addPartialTarget(it);
    }
  }
  
  public void processSelection(SelectionEvent selectionEvent) {
    //clear old selection
    for (int i=0; i<flights.size(); i++) {
      Flight flight = flights.get(i);
      flight.setSelected(false);
    }
    
    //set new selection
    UIPointDataLayer dataLayer = (UIPointDataLayer) selectionEvent.getSource();
    CollectionModel model = ModelUtils.toCollectionModel(dataLayer.getValue());
    this.selectedKeys = dataLayer.getSelectedRowKeys();
    Iterator i = selectedKeys.iterator();
    while (i.hasNext()) {
      Object rowKey = i.next();
      model.setRowKey(rowKey);
      if (model.getRowData() instanceof Flight) {
        Flight selected = (Flight) model.getRowData();
        selected.setSelected(true);
      }
    }
    
    UIComponent it = FacesContext.getCurrentInstance().getViewRoot().findComponent("demoTemplate").findComponent("it1");
    RequestContext.getCurrentInstance().addPartialTarget(it);
  }
  
  public boolean isShowFlightNo() {
    return this.showFlightNo;
  }
  
  public void setShowFlightNo(boolean bShow) {
    this.showFlightNo = bShow;
  }
  
  public RowKeySet getSelectedKeys() {
    return this.selectedKeys;
  }
  
  public void setPlayButton(RichCommandButton btn) {
    this.playButton = btn;
  }
  
  public RichCommandButton getPlayButton() {
    return this.playButton;
  }
  
  public void setPauseButton(RichCommandButton btn) {
    this.pauseButton = btn;
  }
  
  public RichCommandButton getPauseButton() {
    return this.pauseButton;
  }
  
  public void playListener(ActionEvent actionEvent) {
    currentInterval = -1;
    play = true;
    isStarted = true;
    isPaused = false;
    RequestContext rc = RequestContext.getCurrentInstance();
    rc.addPartialTarget(this.pauseButton);
    rc.addPartialTarget(this.playButton);
    UIComponent poll = FacesContext.getCurrentInstance().getViewRoot().findComponent("demoTemplate").findComponent("poll1");
    rc.addPartialTarget(poll);
  }
  
  public void stopListener(ActionEvent actionEvent) {
    play = false;
    isPaused = true;
    RequestContext rc = RequestContext.getCurrentInstance();
    rc.addPartialTarget(this.pauseButton);
    rc.addPartialTarget(this.playButton);   
    UIComponent poll = FacesContext.getCurrentInstance().getViewRoot().findComponent("demoTemplate").findComponent("poll1");
    rc.addPartialTarget(poll);
  }
  
  public boolean isStarted() {
    return isStarted;
  }
  
  public boolean isPaused() {
    return isPaused;
  }
  
  public static class Flight {
    private int flight;
    private String from;
    private String to;
    private Date fromTime;
    private int flightTimeMs;
    private Point2D.Double fromLatLong;
    private Point2D.Double toLatLong;
    private Point2D.Double currentLongLat;
    private int incrementDist = 10; // amount we move each poll interval
    private double longIncrement;
    private double latIncrement;
    private int numIntervals;
    private boolean flipLat = false;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd hh:mm");
    private DecimalFormat latLongFormat = new DecimalFormat("0.####");
    private boolean isSelected = false;
    
    public Flight(int flight, String from, String to, Point2D.Double fromLatLong, Point2D.Double toLatLong, int flightTimeMs) {
      this.flight = flight;
      this.from = from;
      this.to = to;
      this.fromLatLong = fromLatLong;
      this.toLatLong = toLatLong;
      this.currentLongLat = fromLatLong;
      this.fromTime = new Date();
      this.flightTimeMs = flightTimeMs;
      double totalDistance = Math.sqrt(Math.pow(toLatLong.x-fromLatLong.x,2)+Math.pow(toLatLong.y-fromLatLong.y, 2));
      if (totalDistance > 180) {
        totalDistance = 360 - totalDistance;
        flipLat = true;
      }
      this.numIntervals = (int)Math.ceil(totalDistance/incrementDist); // total number of poll intervals it takes to reach destination
      if (flipLat)
        this.latIncrement = -(360 - (toLatLong.x-fromLatLong.x))/numIntervals;
      else
        this.latIncrement = (toLatLong.x-fromLatLong.x)/numIntervals;
      this.longIncrement = (toLatLong.y-fromLatLong.y)/numIntervals;
    }
    
    public void setSelected(boolean bSelected) {
      isSelected = bSelected;
    }
    
    public boolean isSelected() {
      return this.isSelected;
    }
    
    public int getFlightNumber() {
      return this.flight;
    }
    
    public String getDepartureCityName() {
      return this.from;
    }
    
    public String getArrivalCityName() {
      return this.to;
    }
    
    public Point2D.Double getDepartureCity() {
      return this.fromLatLong;
    }
    
    public Point2D.Double getArrivalCity() {
      return this.toLatLong;
    }
    
    public String getDepartureTime() {
      return dateFormat.format(this.fromTime);
    }
    
    public String getArrivalTime() {
      return dateFormat.format(this.fromTime.getTime()+flightTimeMs);
    }
    
    public String getFlightDuration() {
      int hours = (int)Math.floor(this.flightTimeMs/3600000);
      int minutes = (int)(this.flightTimeMs/3600000 - hours)*60;
      return Integer.toString(hours) + "h " + (minutes > 0 ? Integer.toString(minutes) + "m" : "");
    }
    
    public boolean isDestinationReached() {
      return this.currentLongLat.equals(toLatLong);
    }
    
    public void setCurrentLongLat(int currentInterval) {
      //increment once every time long lat is retrieved by poll
      if (currentInterval >= this.numIntervals) {
        this.currentLongLat = toLatLong;
      } else {
        double newLat = fromLatLong.x+(latIncrement*currentInterval);
        if (flipLat && newLat <= -180)
          newLat = 360 + newLat;
        this.currentLongLat = new Point2D.Double(newLat, fromLatLong.y+(longIncrement*currentInterval));
      }
    }
    
    public double getRotation() {
      if (fromLatLong.x > toLatLong.x || flipLat)
        return 180 - (fromLatLong.y-toLatLong.y);
      else
        return 0 + (fromLatLong.y-toLatLong.y);
    }
    
    public Point2D.Double getCurrentLongLat() {
      return this.currentLongLat;
    }
    
    public String getLatString() {
      return latLongFormat.format(this.currentLongLat.x > 0 ? this.currentLongLat.x : -this.currentLongLat.x);
    }
    
    public String getLongString() {
      return latLongFormat.format(this.currentLongLat.y > 0 ? this.currentLongLat.y : -this.currentLongLat.y);
    }
    
  }
}
