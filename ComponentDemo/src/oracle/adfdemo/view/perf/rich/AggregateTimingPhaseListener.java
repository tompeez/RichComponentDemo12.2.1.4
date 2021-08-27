/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.perf.rich;

import javax.faces.event.PhaseId;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseListener;

/**
 * Just a little diagnostic PhaseListener which logs the time it takes
 * to execute each phase.
 */
public class AggregateTimingPhaseListener implements PhaseListener
{
  public PhaseId getPhaseId()
  {
    return PhaseId.ANY_PHASE;
  }

  public void beforePhase(PhaseEvent event)
  {
    // Get the start time
    _startTime = System.currentTimeMillis();
  }

  public void afterPhase(PhaseEvent event)
  {
    // Skip the first request
    if (_count >= 1)
    {
      long delta = System.currentTimeMillis() - _startTime;
      _aggregate[event.getPhaseId().getOrdinal()] += delta;
    }
    
    if (event.getPhaseId() == PhaseId.RENDER_RESPONSE ||
        event.getFacesContext().getResponseComplete())
    {
      _count++;
      if ((_count % 100) == 0)
      {
        double total = 0;
        for (Object phase : PhaseId.VALUES)
        {
          if (phase == PhaseId.ANY_PHASE)
            continue;
          double average = ((double) _aggregate[((PhaseId) phase).getOrdinal()])/_count;
          total += average;
          System.out.println("Phase " + phase.toString() + " length is " + average);
        }
        
        System.out.println("TOTAL (after " + _count + ") is " + total);
      }
    }    
  }

  private int  _count = 0;
  private long _startTime;
  private long[] _aggregate = new long[PhaseId.VALUES.size()];
}
