/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.components.rich;

import java.io.Serializable;

import java.util.logging.Level;

import java.util.logging.Logger;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.myfaces.trinidad.model.BoundedRangeModel;
import org.apache.myfaces.trinidad.model.DefaultBoundedRangeModel;

public class DemoProgressIndicatorBean 
{

  public DemoProgressIndicatorBean()
  {
  }

  public AlwaysRunningIndeterminateModel getAlwaysRunningIndeterminateModel()
  {
    if (_alwaysRunningIndeterminateModel == null)
      _alwaysRunningIndeterminateModel = new AlwaysRunningIndeterminateModel();
    return _alwaysRunningIndeterminateModel;
  }

  public FinishedRunningIndeterminateModel getFinishedRunningIndeterminateModel()
  {
    if (_finishedRunningIndeterminateModel == null)
      _finishedRunningIndeterminateModel = new FinishedRunningIndeterminateModel();
    return _finishedRunningIndeterminateModel;
  }

  public InteractiveModel getInteractiveModel()
  {
    if (_interactiveModel == null)
      _interactiveModel =  new InteractiveModel();
    return _interactiveModel;
  }

  public FiftyPercentDeterminateModel getFiftyPercentDeterminateModel()
  {
    if (_fiftyPercentDeterminateModel == null)
      _fiftyPercentDeterminateModel = new FiftyPercentDeterminateModel();
    return _fiftyPercentDeterminateModel;
  }

  /**
   * Get model that runs for about a minute
   */
  public BoundedRangeModel getAutomatedModel()
  {
    if (_automatedModel == null)
      _automatedModel = new AutomatedModel(1000, 500);
    return _automatedModel;
  }
  
  public StartMeModel getStartMeModel()
  {
    if (_startMeModel == null)
      _startMeModel = new StartMeModel();
    return _startMeModel;
  }
  
  private AlwaysRunningIndeterminateModel _alwaysRunningIndeterminateModel;
  private FinishedRunningIndeterminateModel _finishedRunningIndeterminateModel;
  private InteractiveModel _interactiveModel;
  private FiftyPercentDeterminateModel _fiftyPercentDeterminateModel;
  private AutomatedModel _automatedModel;
  private StartMeModel _startMeModel;

  public String actionListener()
  {
    _LOG.warning("actionListener called");
    return null;
  }

  public void actionListener(ActionEvent actionEvent)
  {
    _LOG.warning("actionListener(event) called");
  }

  /**
   * Interactive model that allows setting of it's value and maximum fields
   */

  public class InteractiveModel extends BoundedRangeModel
  {
    public long getMaximum()
    {
      return _maximum;
    }
  
    public long getValue()
    {
      return _value;
    }

    public void setValue(long value)
    {
      _value = value;
    }
    
    public void setMaximum(long maximum)
    {
      _maximum = maximum;
    }
    
    /** 
     * Is the process running. 
     * Determinate case, maximum is greater than value; Indeterminate case both less than 0.
     */
    public boolean isRunning()
    {
      return (_value < _maximum) || (_value < 0 && _maximum < 0);
    }
    
    public void handleValueChange(ValueChangeEvent event)
    {
      Object newValueObj = event.getNewValue();
      try
      {
        long newValue = Long.valueOf(newValueObj.toString());
        setValue(newValue);
      } 
      catch (NumberFormatException e)
      {
        setValue(-1);
      }
    }  

    public void handleMaximumChange(ValueChangeEvent event)
    {
      Object newMaxObj = event.getNewValue();
      try
      {
        long newMax = Long.valueOf(newMaxObj.toString());
        setMaximum(newMax);
      } 
      catch (NumberFormatException e)
      {
        setMaximum(-1);
      }
    }  

    private long _value = 0;
    private long _maximum = 100;
  }

  
  /**
   * Determinate state model set at 50%.
   */
  public class FiftyPercentDeterminateModel extends BoundedRangeModel
  {
    public long getMaximum()
    {
      return 2;
    }
  
    public long getValue()
    {
      return 1;
    }
  }

  /**
   * Model that is always in a running indeterminate state.
   */
  public class AlwaysRunningIndeterminateModel extends BoundedRangeModel
  {
    public long getMaximum()
    {
      return -1;
    }
  
    public long getValue()
    {
      return -1;
    }
  }

  /**
   * Model that is always in a finished running indeterminate state.
   */
  public class FinishedRunningIndeterminateModel extends BoundedRangeModel
  {
    public long getMaximum()
    {
      return -1;
    }
  
    public long getValue()
    {
      return 1;
    }
  }
  
  /**
   * Determinate model that runs for a specified amount of time.
   */
  public class AutomatedModel extends DefaultBoundedRangeModel
  {
    /**
     * Construct with sleep and update value.
     */
    public AutomatedModel(int sleepValue, int updateValue)
    {
      super(0, 100);
      _sleepValue = sleepValue;
      _updateValue = updateValue;
      _processThread = new ProcessThread(_sleepValue, _updateValue, 0, this);
    }

    public void cancelProcess(ActionEvent event)
    {
      _processThread.stopProcess();
      _processThread = null;
      setValue(0);
      setMaximum(100);
      _processThread = new ProcessThread(_sleepValue, _updateValue, 0, this);
    }
    
    public void startProcess(ActionEvent event)
    {
      if (!_processThread.isAlive())
        _processThread.start();
    }
    
    public void restartProcess(ActionEvent event)
    {
      cancelProcess(event);
      startProcess(event);
    }

    protected volatile ProcessThread _processThread;
    private int _sleepValue;
    private int _updateValue;
  }

  /**
   * Model that is always in a finished running indeterminate state.
   */
  public class StartMeModel extends DefaultBoundedRangeModel
  {
    public StartMeModel()
    {
      super(0, 100);
    }

    public void start(ActionEvent event)
    {
      cancelProcess(null);
      _processThread = new ProcessThread(2000, 300, 0, this);
      _processThread.start();
    }

    public void cancelProcess(ActionEvent event)
    {
      if (_processThread != null)
      {
        _processThread.stopProcess();
      }
      setValue(0);
      setMaximum(100);
    }
    
    protected volatile ProcessThread _processThread;
  }

  protected class ProcessThread extends Thread implements Serializable
  {
    /**
     * @param updateIntervalFactor - controls the speed of the thread
     * @param updateValueFactor - The value by which the 'value' from the 
     *    model should be incremented for every cycle. Randomizes the increment
     *    if updateValueFactor supplied is '0'.
     */
    ProcessThread(
      int initialSleepValue,
      long updateIntervalFactor, 
      long updateValueFactor, 
      DefaultBoundedRangeModel model)
    {
      _initialSleepValue = initialSleepValue;
      _updateIntervalFactor = updateIntervalFactor;
      _updateValueFactor = updateValueFactor;
      _model = model;
    }
    
    @Override
    public void run()
    {
      try
      {
        //Be in indeterminate mode for some time to start with
        sleep(_initialSleepValue);
        //Take care to get out if we are the discarded thread upon endProcess()
        
        while ( (_model != null) && (_model.getValue() < _model.getMaximum()) )
        {
          long sleepFactor = Math.round(Math.random()*10);
          long updatedValue = _model.getValue() + 
            ((_updateValueFactor == 0) ? sleepFactor:_updateValueFactor);
          long maximum = _model.getMaximum();
          if (updatedValue > maximum)
          {
            updatedValue = maximum;
          }
          _model.setValue(updatedValue);
          sleep(sleepFactor * _updateIntervalFactor);
        }
      }
      catch (InterruptedException ie)
      {
        _LOG.log(Level.WARNING, "Background task thread interrupted", ie);
      }
    }
    
    public void stopProcess()
    {
      _model = null;
    }

    private int _initialSleepValue;
    private long _updateIntervalFactor;
    private long _updateValueFactor;
    
    protected volatile DefaultBoundedRangeModel _model;
    
  }
  
  static private final Logger _LOG = Logger.getLogger(
    DemoProgressIndicatorBean.class.getName());
}
