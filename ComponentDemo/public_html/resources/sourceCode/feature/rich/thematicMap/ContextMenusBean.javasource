/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/thematicMap/ContextMenusBean.java /bibeans_root/1 2014/08/12 13:53:42 adama Exp $ */

/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

package oracle.adfdemo.view.feature.rich.thematicMap;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;

import oracle.adfdemo.view.feature.rich.dvt.data.ElectionData;

import oracle.adfdemo.view.feature.rich.dvt.data.ElectionData.PresidentialCandidateResult;
import oracle.adfdemo.view.feature.rich.dvt.data.ElectionData.PresidentialStateResult;

import org.apache.myfaces.trinidad.context.RequestContext;


public class ContextMenusBean extends ElectionData {
  private ArrayList<ContextMenusCandidateResult> candidates;
  private int electionYear = 2012;
  private int republicanElectoral;
  private int democratElectoral;
  private int rowKey;

  public ContextMenusBean() {
    super();
    
    resetElection();
  }
  
  private void resetElection() {
    republicanElectoral = 0;
    democratElectoral = 0;
    for (PresidentialStateResult state : getStateResults()) {
      republicanElectoral += state.getElectoralRepublican();
      democratElectoral += state.getElectoralDemocrat();
      state.resetTempWinner();
    }

    candidates = new ArrayList<ContextMenusCandidateResult>();
    for (PresidentialCandidateResult r : getCurrentModel().getCandidateResults()) {
      ContextMenusCandidateResult candidate = new ContextMenusCandidateResult(r);
      candidates.add(candidate);
    }
    
    updateCandidateResults();
  }
  
  public PresidentialModel getCurrentModel() {
    return getModelMap().get(String.valueOf(electionYear));
  }
  public List<PresidentialStateResult> getStateResults() {
    return getCurrentModel().getStateResults();
  }
  public ArrayList<ContextMenusCandidateResult> getCandidateResults() {
    return candidates;
  }  
  public int getRepublicanElectoral() {
    return republicanElectoral;
  }
  public int getDemocratElectoral() {
    return democratElectoral;
  }
  public int getRowKey() {
      return rowKey;
  }
  public void setRowKey(int key) {
      rowKey = key;
  }
  public int getElectionYear() {
     return electionYear;
  }
  public void setElectionYear(int year) {     
    electionYear = year; 
    resetElection();
  }  

  public void stateContextMenuListener(ActionEvent event) {
    PresidentialStateResult state = getStateResults().get(rowKey);
    
    int r = state.getElectoralRepublican();
    int d = state.getElectoralDemocrat();
    if (state.getTempWinner().equals(state.getElectoralWinner())) {
      republicanElectoral = republicanElectoral - r + d; 
      democratElectoral = democratElectoral - d + r;      
    }
    else {
      republicanElectoral = republicanElectoral + r - d; 
      democratElectoral = democratElectoral + d - r;      
    }

    
    updateCandidateResults();
    state.toggleTempWinner();
  }
  
  public void updateCandidateResults() {
    for (ContextMenusCandidateResult candidate : candidates) {
      if (candidate.getParty().equals(PARTY_REPUBLICAN)) {
        candidate.setTempElectoralVotes(republicanElectoral);
      }
      else if (candidate.getParty().equals(PARTY_DEMOCRAT)) {
        candidate.setTempElectoralVotes(democratElectoral);
      }
    }
  }
  
  public void backgroundContextMenuListener(ActionEvent event) {
    resetElection();
  }

  public class ContextMenusCandidateResult extends PresidentialCandidateResult {
    private int tempElectoralVotes;
    public ContextMenusCandidateResult(PresidentialCandidateResult r) {
      super(r.getName(), r.getParty(), r.getElectoralVotes(), r.getPopularVotes(), r.getPopularTotal());
      tempElectoralVotes = r.getElectoralVotes();
    }
    
    public void setTempElectoralVotes(int votes) {
      tempElectoralVotes = votes;
    }
    public int getTempElectoralVotes() {
      return tempElectoralVotes;
    }
    
    
  }
}










  
  
  
