/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

package oracle.adfdemo.view.explorer.rich.bean;

import java.awt.Color;

import java.util.List;

public class HelpBean 
{
  public HelpBean() 
  {
  }

  public void setTopFive (List topFive) 
  {
    this._topFive = topFive;
  }

  public List getTopFive() 
  {
    return _topFive;
  }

  public void setUserRole(String userRole) 
  {
    this._userRole = userRole;
  }

  public String getUserRole() 
  {
    return _userRole;
  }

  public void setPrimaryColor(Color paramPrimaryColor) 
  {
    this._primaryColor = paramPrimaryColor;
  }

  public Color getPrimaryColor() 
  {
    return _primaryColor;
  }

  public void setSecondaryColor(Color secondaryColor) 
  {
    this._secondaryColor = secondaryColor;
  }

  public Color getSecondaryColor() 
  {
    return _secondaryColor;
  }

  public void setPerformance(int performance) 
  {
    this._performance = performance;
  }

  public int getPerformance() 
  {
    return _performance;
  }

  public void setVisualAppeal(int visualAppeal) 
  {
    this._visualAppeal = visualAppeal;
  }

  public int getVisualAppeal() 
  {
    return _visualAppeal;
  }

  public void setEaseOfUse(int easeOfUse) 
  {
    this._easeOfUse = easeOfUse;
  }

  public int getEaseOfUse() 
  {
    return _easeOfUse;
  }

  public void setFunctionality(double functionality) 
  {
    this._functionality = functionality;
  }

  public double getFunctionality() 
  {
    return _functionality;
  }
  
  private List   _topFive;
  private String _userRole;
  private Color  _primaryColor;
  private Color  _secondaryColor;
  private int    _performance      = 5;
  private int    _visualAppeal     = 5;
  private int    _easeOfUse        = 5;
  private double _functionality    = 5.0;
}
