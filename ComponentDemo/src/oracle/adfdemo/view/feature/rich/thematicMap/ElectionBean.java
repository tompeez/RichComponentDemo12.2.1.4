package oracle.adfdemo.view.feature.rich.thematicMap;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oracle.adf.view.faces.bi.component.thematicMap.UIAreaDataLayer;

import oracle.adfdemo.view.feature.rich.thematicMap.SampleMapData.MapData;

import oracle.dss.dataView.Model;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class ElectionBean {
  
  private List<Candidate> candidates = new ArrayList<Candidate>();    
  private List<SampleMapData.MapData> counties = new ArrayList<SampleMapData.MapData>();  
  private List<SampleMapData.MapData> state = new ArrayList<SampleMapData.MapData>();  
  private static String dem = "FL_ALACHUA,FL_BROWARD,FL_GADSDEN,FL_JEFFERSON,FL_LEON,FL_MIAMI_DADE,FL_MONROE,FL_ORANGE,FL_OSCEOLA,FL_PALM_BEACH,FL_PINELLAS,FL_ST_LUCIE,FL_HILLSBOROUGH";
  private static String rep = "FL_SEMINOLE,FL_SUMTER,FL_OKALOOSA,FL_MANATEE,FL_POLK,FL_MADISON,FL_WASHINGTON,FL_LIBERTY,FL_LEVY,FL_COLUMBIA,FL_NASSAU,FL_CLAY,FL_HOLMES,FL_DESOTO,FL_VOLUSIA,FL_BREVARD,FL_ST_JOHNS,FL_BRADFORD,FL_CALHOUN,FL_GULF,FL_PASCO,FL_HENDRY,FL_TAYLOR,FL_MARTIN,FL_FLAGLER,FL_SUWANNEE,FL_HAMILTON,FL_SANTA_ROSA,FL_JACKSON,FL_SARASOTA,FL_LAKE,FL_ESCAMBIA,FL_GLADES,FL_CHARLOTTE,FL_DIXIE,FL_WAKULLA,FL_INDIAN_RIVER,FL_DUVAL,FL_FRANKLIN,FL_COLLIER,FL_PUTNAM,FL_OKEECHOBEE,FL_GILCHRIST,FL_HERNANDO,FL_UNION,FL_LEE,FL_LAFAYETTE,FL_BAKER,FL_HARDEE,FL_BAY,FL_MARION,FL_WALTON,FL_CITRUS,FL_HIGHLANDS";
  
  public ElectionBean() {
    createCandidateList();
    createCountiesList();
    createStateList();
  }
  
  public CollectionModel getCandidates() {
    return ModelUtils.toCollectionModel(candidates);
  }
  
  public CollectionModel getCounties() {
    return ModelUtils.toCollectionModel(counties);
  }  
  
  public CollectionModel getState() {
    return ModelUtils.toCollectionModel(state);
  }  
  
  public Object getIsolatedRowKey() {
    return new Integer(0);
  }
  
  public RowKeySet getDisclosedRowKey() {
    RowKeySet disclosedRowKeySet = new RowKeySetImpl();
    disclosedRowKeySet.add(new Integer(0));
    return disclosedRowKeySet;
  }
  
  private void createStateList() {
    String[] repAr = rep.split(",");
    for (int i=0; i<repAr.length; i++)
      counties.add(new SampleMapData.MapData(repAr[i], null, 0, new Color(197, 51, 51)));
    String[] demAr = dem.split(",");
    for (int i=0; i<demAr.length; i++)
      counties.add(new SampleMapData.MapData(demAr[i], null, 0, new Color(51, 103, 145)));
  }
  
  private void createCountiesList() {
    state.add(new SampleMapData.MapData("FL", null, 0, new Color(51, 103, 145)));
  }
  
  private void createCandidateList() {
    candidates.add(new Candidate("B. Obama", "Dem", 50, 4235270, 29, new Color(51, 103, 145)));
    candidates.add(new Candidate("M. Romney", "GOP", 49.1, 4162081, 0, new Color(197, 51, 51)));
    candidates.add(new Candidate("G. Johnson", "Lib", .5, 44681, 0, new Color(0, 0, 0)));
    candidates.add(new Candidate("J. Stein", "Gm", .1, 8933, 0, new Color(0, 0, 0)));
    candidates.add(new Candidate("R. Barr", "PFP", .1, 8147, 0, new Color(0, 0, 0)));
    candidates.add(new Candidate("T. Stevens", "Obj", 0, 3855, 0, new Color(0, 0, 0)));
    candidates.add(new Candidate("V. Goode", "CST", 0, 2603, 0, new Color(0, 0, 0)));
    candidates.add(new Candidate("R. Anderson", "JP", 0, 1753, 0, new Color(0, 0, 0)));
    candidates.add(new Candidate("T. Hoefling", "AIP", 0, 944, 0, new Color(0, 0, 0)));
    candidates.add(new Candidate("A. Barnett", "RP", 0, 819, 0, new Color(0, 0, 0)));
    candidates.add(new Candidate("S. Alexander", "Soc", 0, 797, 0, new Color(0, 0, 0)));
    candidates.add(new Candidate("P. Lindsay", "PSL", 0, 322, 0, new Color(0, 0, 0)));
  }

  public static class Candidate {
    private String name;
    private String party;
    private double popularVotePercent;
    private int popularVote;
    private int electoralVote;
    private Color color;

    public Candidate(String name, String party, double popularVotePercent, int popularVote, int electoralVote, Color color) {
      this.name = name;
      this.party = party;
      this.popularVotePercent = popularVotePercent;
      this.popularVote = popularVote;
      this.electoralVote = electoralVote;
      this.color = color;
    }

    public String getName() {
      return name;
    }

    public String getParty() {
      return party;
    }

    public double getPopularVotePercent() {
      return popularVotePercent;
    }
    
    public int getPopularVote() {
      return popularVote;
    }

    public int getElectoralVote() {
      return electoralVote;
    }
    
    public Color getColor() {
      return color;
    }
  }
}
