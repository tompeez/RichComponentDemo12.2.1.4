package oracle.adfdemo.view.feature.rich.dvt;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.List;

public class RetirementCalculator {
  private int currentAge = 25;
  private int retirementAge = 67;
  private int lifeExpectancy = 90;
  private double currentSavings = 25000;
  private double annualContribution = 10000;
  private double rateOfReturn = 0.06;
  private double retiredRateOfReturn = 0.03;
  private double retirementSpending = 80000;
  private double socialSecurityIncome = 30000;

  private static final double INFLATION_RATE = 1.03;
  
  public void setCurrentAge(int currentAge) {
    this.currentAge = currentAge;
  }

  public int getCurrentAge() {
    return currentAge;
  }

  public void setRetirementAge(int retirementAge) {
    this.retirementAge = Math.max(retirementAge, currentAge);
  }

  public int getRetirementAge() {
    return retirementAge;
  }

  public void setLifeExpectancy(int lifeExpectancy) {
    this.lifeExpectancy = Math.max(retirementAge, lifeExpectancy);
  }

  public int getLifeExpectancy() {
    return lifeExpectancy;
  }

  public void setCurrentSavings(double currentSavings) {
    this.currentSavings = currentSavings;
  }

  public double getCurrentSavings() {
    return currentSavings;
  }

  public void setAnnualContribution(double annualContribution) {
    this.annualContribution = annualContribution;
  }

  public double getAnnualContribution() {
    return annualContribution;
  }

  public void setRateOfReturn(double rateOfReturn) {
    this.rateOfReturn = rateOfReturn;
  }

  public double getRateOfReturn() {
    return rateOfReturn;
  }

  public void setRetiredRateOfReturn(double retiredRateOfReturn) {
    this.retiredRateOfReturn = retiredRateOfReturn;
  }

  public double getRetiredRateOfReturn() {
    return retiredRateOfReturn;
  }

  public void setRetirementSpending(double retirementSpending) {
    this.retirementSpending = retirementSpending;
  }

  public double getRetirementSpending() {
    return retirementSpending;
  }

  public void setSocialSecurityIncome(double socialSecurityIncome) {
    this.socialSecurityIncome = socialSecurityIncome;
  }

  public double getSocialSecurityIncome() {
    return socialSecurityIncome;
  }
  
  /**
   * Returns the data model containing the calculated data.
   * @return
   */
  public List<YearlyData> getChartData() {
    List<YearlyData> model = new ArrayList<YearlyData>();
    
    // Calculate the savings for each year from current to retirement
    double totalContribution = 0;
    double totalSavings = currentSavings;
    for(int i=currentAge; i<=lifeExpectancy; i++) {
      // Create the data item for the year
      model.add(new YearlyData(i, totalContribution, totalSavings));
      
      // Update the savings totals
      if(i < retirementAge) {
        // Before Retirement: Increase the savings by annual contribution and interest
        totalContribution += annualContribution;
        totalSavings = (totalSavings * (1 + rateOfReturn) + annualContribution)/INFLATION_RATE;
      }
      else {
        // After Retirement: Decrease the savings by annual spend - social security
        double income = socialSecurityIncome - retirementSpending;
        totalSavings = ((totalSavings + income) * (1 + retiredRateOfReturn))/INFLATION_RATE;
      }
    }
    
    return model;
  }
  
  /**
   * Returns the minimum amount needed to retire with the information specified in this bean.
   */
  public double getMinimumNeededToRetire() {
    int yearsAfterRetirement = lifeExpectancy - retirementAge;
    double spend = retirementSpending - socialSecurityIncome;
    double savingsNeeded = 0;
    for(int i=yearsAfterRetirement; i>0; i--) {
      savingsNeeded += spend * Math.pow(INFLATION_RATE, i)/Math.pow(1 + retiredRateOfReturn, i);
    }
    return savingsNeeded;
  }

  /**
   * Returns the maximum value to display on the y-Axis.
   */
  public double getMaxSavings() {
    List<YearlyData> model = getChartData();
    
    // Calculate the maximum value of the retirement savings
    double maxSavings = Double.NEGATIVE_INFINITY;
    for(YearlyData item : model) {
      maxSavings = Math.max(maxSavings, item.getTotalSavings());
    }
    
    return Math.max(maxSavings, getMinimumNeededToRetire());
  }
  
  /**
   * Returns the text string indicating whether the savings goals were reached.
   * @return
   */
  public String getMessage() {
    double amountAtEnd = getAmountAtLifeExpectancy();
    if(amountAtEnd > 1000000)
      return "Congratulations! You will have more than enough to retire. Please consider making a donation to your favorite data visualization developers.";
    else if(amountAtEnd >= 0) 
      return "Congratulations! You will have saved enough to enjoy your retirement.";
    else {
      double difference = getMinimumNeededToRetire() - getAmountAtRetirement();
      double additionalPerYear = getAdditionalSavingsNeeded();
      DecimalFormat decFormat = new DecimalFormat();
      decFormat.setMaximumFractionDigits(0);
      return "You will need to save an additional $" + decFormat.format(difference) + 
             " to retire.  Increase your annual contribution by $" + decFormat.format(Math.ceil(additionalPerYear)) + 
             " to meet your goals."; 
    }
  }
  
  /**
   * Returns true if the savings is not depleted during retirement.
   * @return
   */
  public boolean isSavingEnough() {
    return getAmountAtLifeExpectancy() >= 0;
  }
  
  /**
   * Helper to return the value of the account at retirement.
   */
  private double getAmountAtRetirement() {
    List<YearlyData> model = getChartData();
    return model.get(retirementAge - currentAge).getTotalSavings();
  }
  
  /**
   * Helper to return the value of the account at life expectancy.
   */
  private double getAmountAtLifeExpectancy() {
    List<YearlyData> model = getChartData();
    return model.get(model.size()-1).getTotalSavings();
  }
  
  /**
   * Helper to return the additional contribution per year needed to meet retirement goals.
   */
  private double getAdditionalSavingsNeeded() {
    double difference = getMinimumNeededToRetire() - getAmountAtRetirement();
    if(difference <= 0)
      return 0; // Saving enough
    else {
      // Calculate the number of contribution multiples taking insurance and inflation into account
      double multiple = 0;
      double yearsToContribute = retirementAge - currentAge;
      for(int i = 0; i<yearsToContribute; i++) {
        multiple += (Math.pow(1 + rateOfReturn, i)/Math.pow(INFLATION_RATE, i+1));
      }
      
      // Additional per year is the difference/multiple
      return multiple > 0 ? difference/multiple : difference;
    }
  }
  
  /**
   * Class representing the calculated data for a single year.
   */
  public static class YearlyData {
    private final int age;
    private final double totalContribution;
    private final double totalSavings;

    public YearlyData(int age, double totalContribution, double totalSavings) {
      this.age = age;
      this.totalContribution = totalContribution;
      this.totalSavings = totalSavings;
    }

    public int getAge() {
      return age;
    }

    public double getTotalContribution() {
      return totalContribution;
    }

    public double getTotalSavings() {
      return totalSavings;
    }
    
    public String getShortDesc() {
      DecimalFormat decFormat = new DecimalFormat();
      decFormat.setMaximumFractionDigits(0);
      return "Age: <b>" + age + "</b><br/>" + "Savings: <b>$" + decFormat.format(totalSavings) + "</b>";
    }
  }
}
