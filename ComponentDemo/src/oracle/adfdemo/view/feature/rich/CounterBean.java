/** Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.feature.rich;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import oracle.adf.view.rich.activedata.ActiveModelContext;
import oracle.adf.view.rich.activedata.BaseActiveDataModel;
import oracle.adf.view.rich.event.ActiveDataEntry;
import oracle.adf.view.rich.event.ActiveDataUpdateEvent;

import oracle.adfinternal.view.faces.activedata.ActiveDataEventUtil;

public class CounterBean extends BaseActiveDataModel
{
  public String getState()
  {
      ActiveModelContext context = ActiveModelContext.getActiveModelContext();
      Object[] keyPath = new String[0];
      context.addActiveModelInfo(this, keyPath, "state");
      
      timer.schedule(new UpdateTask(), 2000, 2000);
    return String.valueOf(counter);
  }

  // not needed as we do not need to connect to a (real) active data source...
  protected void startActiveData(Collection<Object> rowKeys, int startChangeCount) {}

  // not needed as we do not need to disconnect from a (real) active data source...
  protected void stopActiveData(Collection<Object> rowKeys) {}

  public int getCurrentChangeCount()
  {
    return counter.get();
  }

  protected class UpdateTask extends TimerTask
  {
    public void run()
    {
      counter.incrementAndGet();

      ActiveDataUpdateEvent event =
        ActiveDataEventUtil.buildActiveDataUpdateEvent(
          ActiveDataEntry.ChangeType.UPDATE,
          counter.get(),
          new String[0], 
          null,
          new String[] { "state" },
          new Object[] { counter.get() });
      fireActiveDataUpdate(event);
    }
  }

  private static final Timer timer = new Timer();
  private final AtomicInteger counter = new AtomicInteger(0);
}
